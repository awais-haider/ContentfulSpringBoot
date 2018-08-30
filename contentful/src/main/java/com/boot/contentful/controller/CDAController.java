package com.boot.contentful.controller;


import com.boot.contentful.common.ContentfulDecorator;
import com.boot.contentful.common.DecoratorUtil;
import com.boot.contentful.common.ResponseObject;
import com.boot.contentful.common.ReturnStatusEnum;
import com.boot.contentful.service.ContentfulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping(value="/CDA")
public class CDAController {

    @Autowired
    private ContentfulService contentfulService;

    @Autowired
    private DecoratorUtil decoratorUtil;

    //final CDAEntry one =
    //    client
    //        .fetch(CDAEntry.class)
    //        .one("<entry_id>");


    /**
     * This is testing controller for web hooks, currently using NGROK for mapping local-ip to live
     * @return
     */
    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody
    public String testWebHook() {

        return contentfulService.testFetchContentType();
    }



    @RequestMapping(value="/getContentTypeBasedOnId", method= RequestMethod.GET)
    @ResponseBody
    public String getContentTypeBasedOnId(@RequestParam String id,
                                 @RequestParam(value = "lang") String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        return contentfulService.getContentTypeBasedOnId(locale, id);
    }


    @RequestMapping(value = "/getSingleEntryBasedOnId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResponseObject> getSingleEntryBasedOnId(@RequestParam String id,
                                                  @RequestParam(value = "lang") String languageTag) {
        String result = null;
        long lStartTime = new Date().getTime();
        ContentfulDecorator decorator = new ContentfulDecorator();
        Locale locale = Locale.forLanguageTag(languageTag);

        try {

            result = contentfulService.getSingleEntry(locale, id);

            if (null == result) {
                decorator.setResponseMessage("No Data Found against the searched criteria or Bad request");
                decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
                decorator.setStatus(HttpStatus.BAD_REQUEST);
            } else {
                decorator.setResponseMessage("Data Found");
                decorator.getResponseMap().put("data", result);
                decorator.setStatus(HttpStatus.OK);
                decorator.setReturnCode(ReturnStatusEnum.SUCCESFUL.getValue());
            }
        } catch (Exception e) {
            decorator.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            decorator.setResponseMessage("An issue occurred against the searched criteria");
            decorator.setReturnCode(ReturnStatusEnum.FAILURE.getValue());
        }
        decorator.setQueryTime((System.currentTimeMillis() - lStartTime) + "");
        return new ResponseEntity<>(decoratorUtil.responseToClient(decorator), decorator.getStatus());
    }

}
