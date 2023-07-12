package com.mvailati84.diagram_challenge.service;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationShutdown {

    private final ApplicationContext appContext;

    public ApplicationShutdown(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public void shutdown(int errorCode) {
        SpringApplication.exit(appContext, () -> errorCode);
    }

}
