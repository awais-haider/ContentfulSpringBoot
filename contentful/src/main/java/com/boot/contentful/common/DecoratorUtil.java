package com.boot.contentful.common;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class DecoratorUtil {

    private static final Logger logger = LoggerFactory.getLogger(DecoratorUtil.class);

    public ResponseObject responseToClient(ContentfulDecorator decorator) {
        ResponseObject responseObject = new ResponseObject();
        if ((decorator.getReturnCode() != null && decorator.getReturnCode()
                .equals("500")) || decorator.getErrors().size() > 0 || decorator.getReturnCode()
                .equals("FAILURE")) {
            decorator.getResponseMap().put("Errors", decorator.getErrors());
            responseObject.setReturnType(decorator.getFailure());
            responseObject.setReturnCode(decorator.getReturnCode());
            responseObject.setReturnMessage(decorator.getResponseMessage());
            responseObject.setReturnData(decorator.getResponseMap());
            responseObject.setQueryTimeInMilli(decorator.getQueryTime());
        } else {
            if (decorator.getInfo().size() > 0) {
                decorator.getResponseMap().put("INFO", decorator.getInfo());
            }
            responseObject.setReturnType(decorator.getSuccess());
            responseObject.setReturnCode(decorator.getReturnCode());
            responseObject.setReturnMessage(decorator.getResponseMessage());
            responseObject.setReturnData(decorator.getResponseMap());
            responseObject.setQueryTimeInMilli(decorator.getQueryTime());
        }


        return responseObject;
    }

}