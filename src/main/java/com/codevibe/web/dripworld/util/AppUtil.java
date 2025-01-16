package com.codevibe.web.dripworld.util;

import com.codevibe.web.dripworld.settingsRepository.SettingsRepository;
import jakarta.servlet.ServletContext;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AppUtil {



    private static final Map<String,Object> appSettings = new HashMap<>();
    private static final SettingsRepository settingsRepository = new SettingsRepository();
    private static ServletContext context;

    static {
        load();
    }

    public AppUtil(ServletContext context){AppUtil.context = context;}

    private static void load(){
        System.out.println("Loading Settings");
        appSettings.clear();
        settingsRepository.findAll().forEach(
                setting->{
                    appSettings.put(setting.getName().name(),setting.getValue());
                }
        );
    }

    public static void reload(){
        load();
    }
    public static String getString(String key){return appSettings.get(key).toString();}
    public static Object get(String key) {return appSettings.get(key); }

    public static Map<String, Object> getAppSettings() {
        return appSettings;
    }

    public static ServletContext getServletContext() {
        return context;
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return number + "";
    }

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hash = number.toString(16);
            while (hash.length() < 32) {
                hash = "0" + hash;
            }
            System.out.println(hash+"HASH TESTING");
            return hash.toUpperCase();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
