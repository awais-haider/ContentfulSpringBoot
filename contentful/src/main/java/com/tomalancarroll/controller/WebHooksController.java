package com.tomalancarroll.controller;

import com.tomalancarroll.service.ContentfulService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/webhooks")
public class WebHooksController {

    @Autowired
    private ContentfulService contentfulService;

    /**
     * This is testing controller for web hooks, currently using NGROK for mapping local-ip to live
     * @return
     */
    @RequestMapping(value="/test", method= RequestMethod.GET)
    @ResponseBody
    public String testWebHook() {

        return contentfulService.testHook();
    }

}
