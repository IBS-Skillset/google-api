package com.google.api.service;

public enum VaultService {

    INSTANCE();

    private String token;
    private String key;

    VaultService() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public VaultService getInstance() {
        return INSTANCE;
    }
}