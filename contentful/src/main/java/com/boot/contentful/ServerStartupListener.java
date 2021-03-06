package com.boot.contentful;

import com.boot.contentful.service.ContentfulInitializerService;
import com.boot.contentful.service.ContentfulSynchronizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ServerStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ContentfulInitializerService contentfulInitializerService;

    @Autowired
    private ContentfulSynchronizerService contentfulSynchronizerService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        contentfulInitializerService.initialize();
        contentfulSynchronizerService.synchronize();
    }
}