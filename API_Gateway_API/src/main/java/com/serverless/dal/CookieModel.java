package com.serverless.dal;

public class CookieModel {

    public String userId;
    public String accessKey;
    public long keyExpirationTime;


    public CookieModel(String userId, String accessKey, long keyExpirationTime)
    {
        this.userId = userId;
        this.accessKey = accessKey;
        this.keyExpirationTime = keyExpirationTime;
    }
}
