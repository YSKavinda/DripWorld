package com.codevibe.web.dripworld.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    public String encrypt(String source) {
        String md5 = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes(), 0, source.length());
            final BigInteger integer = new BigInteger(1, digest.digest());
            md5 = integer.toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return md5;
    }
}