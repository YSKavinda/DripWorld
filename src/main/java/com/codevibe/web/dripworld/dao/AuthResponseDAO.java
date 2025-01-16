package com.codevibe.web.dripworld.dao;

public class AuthResponseDAO {

    private String accessToken;
    private String refreshToken;
    private String expiresIn;

    public AuthResponseDAO(){

    }
    public AuthResponseDAO(String accessToken,String refreshToken,String expiresIn){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }






}
