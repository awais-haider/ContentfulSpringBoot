package com.boot.contentful.controller;

import com.boot.contentful.models.EntryMapperModel;
import com.boot.contentful.service.ContentfulService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;


import java.util.Locale;

@RestController
@RequestMapping(value="/webhooks")
public class WebHooksController {

    @Autowired
    private ContentfulService contentfulService;

    @Autowired
    private Gson gson;

//    /**
//     * This is testing controller for web hooks, currently using NGROK for mapping local-ip to live
//     * @return
//     */
//    @RequestMapping(value="/test", method= RequestMethod.GET)
//    @ResponseBody
//    public String testWebHook() {
//
//        return contentfulService.testHook();
//    }


    /**
     * This is testing controller for web hooks, currently using NGROK for mapping local-ip to live
     *
     * @return
     */
    @RequestMapping(value = "/changeEntryContentful", method = RequestMethod.POST)
    @ResponseBody
    public String testWebHookForEntry(@RequestBody String json) {

        try {


            if (json == null) {
                return "No Data sent in request body";
            }
            EntryMapperModel entryMapperModelFromGson = gson.fromJson(json, EntryMapperModel.class);

            if (null != entryMapperModelFromGson && null != entryMapperModelFromGson.getId()) {
                Locale locale = Locale.forLanguageTag("en_US");
                contentfulService.getSingleEntryUpdate(locale, entryMapperModelFromGson.getId());
                locale = Locale.forLanguageTag("pa");
                contentfulService.getSingleEntryUpdate(locale, entryMapperModelFromGson.getId());

            } else {
                return "Entry Id doesn't exists";
            }
        } catch (Exception e) {
            return "Hook execution failure " + e.getMessage();
        }

        return "Hook executed successfully";
    }


}
