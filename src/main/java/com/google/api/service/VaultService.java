package com.google.api.service;

//import lombok.Getter;
//import lombok.Setter;


import io.quarkus.vault.VaultKVSecretEngine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

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


//public final class VaultService {
//
//    private static VaultService INSTANCE;
//    private String token;// = vaultSecretMap.get("token");
//    private String key;// = vaultSecretMap.get("key");
//
//    private VaultService() {
//    }
//
//    public static VaultService getInstance() {
//        if(INSTANCE == null) {
//            INSTANCE = new VaultService();
//        }
//
//        return INSTANCE;
//    }
//
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//}