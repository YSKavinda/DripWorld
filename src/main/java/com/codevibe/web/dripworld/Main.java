package com.codevibe.web.dripworld;

import com.codevibe.web.dripworld.listener.ContextListener;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context context = tomcat.addWebapp("/drip-world.com",new File("./src/main/webapp").getAbsolutePath());
//        System.out.println(new File("./src/main/webapp").getAbsolutePath());
        context.setAllowCasualMultipartParsing(true);
        context.addApplicationListener(ContextListener.class.getName());

        try {
            tomcat.start();
            tomcat.getServer().await();
        }catch (LifecycleException le){
            throw new RuntimeException(le);
        }

    }
}
