package com.codevibe.web.dripworld.config;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

public class AppConfig extends ResourceConfig {
    public AppConfig(){
        System.out.println("Working");
        packages("com.codevibe.web.dripworld.controller");
        packages("com.codevibe.web.dripworld.middleware");
        packages("com.codevibe.web.dripworld.repositories");
        packages("com.codevibe.web.dripworld.settingsRepository");
        packages("com.codevibe.web.dripworld.service");

        register(JspMvcFeature.class);
        register(MultiPartFeature.class);
        register(DependencyBinder.class);
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/views");
    }
}
