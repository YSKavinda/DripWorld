package com.codevibe.web.dripworld.listener;

import com.codevibe.web.dripworld.provider.MailServiceProvider;
import com.codevibe.web.dripworld.util.AppUtil;
import com.codevibe.web.dripworld.util.Env;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("BASE_URL",context.getContextPath()+"/");
        context.setAttribute("assets", context.getContextPath() + "/assets/");
        Env.set("server.url", "http://localhost:" + Env.get("server.port") + context.getContextPath());
        System.out.println("Context initialized");

        context.setAttribute("app",new AppUtil(context));
        MailServiceProvider.getInstance().start();


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MailServiceProvider.getInstance().shutdown();
    }
}
