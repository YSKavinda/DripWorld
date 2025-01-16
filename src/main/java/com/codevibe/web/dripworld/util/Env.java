package com.codevibe.web.dripworld.util;

import java.io.InputStream;
import java.util.Properties;

public class Env {
    private static Properties properties = new Properties();
    static {
        try {

            InputStream stream = Env.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(stream);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return properties.getProperty(key);
    }
    public static void set(String key,String value){
        properties.setProperty(key,value);
    }
    public static Properties getProperties() {
            return properties;
    }

}

