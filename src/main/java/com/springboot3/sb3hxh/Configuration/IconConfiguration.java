package com.springboot3.sb3hxh.Configuration;

import org.springframework.context.annotation.*;
import org.springframework.core.io.*;
import org.springframework.web.servlet.handler.*;
import org.springframework.web.servlet.resource.*;

import java.util.Collections;

@Configuration
public class IconConfiguration {

    @Bean
    public SimpleUrlHandlerMapping customIcon () {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(Integer.MIN_VALUE);
        simpleUrlHandlerMapping.setUrlMap(Collections.singletonMap("/static/favicon.ico", favIconRequest()));
        return simpleUrlHandlerMapping;
    }

    protected ResourceHttpRequestHandler favIconRequest() {
        ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
        resourceHttpRequestHandler.setLocations(Collections.singletonList(new ClassPathResource("/")));
        return resourceHttpRequestHandler;
    }

}