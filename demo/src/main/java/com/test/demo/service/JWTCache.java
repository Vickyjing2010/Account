package com.test.demo.service;

import com.google.gson.Gson;
import com.test.demo.model.UserToken;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JWTCache {

    private static Map<String, String> localTokenMap = new ConcurrentHashMap<>();

    public static void setUserJWTToken(String name, String jwtToken){
        removeUserJWTToken(name);
        localTokenMap.put(name, jwtToken);
    }

    public static UserToken getUserJWTToken(String name){
        if(localTokenMap.get(name) != null){
            UserToken userToken = new Gson().fromJson(localTokenMap.get(name), UserToken.class);
            return userToken;
        }
        return null;
    }

    public static void removeUserJWTToken(String name){
        if(localTokenMap.get(name) != null){
            localTokenMap.remove(name);
        }
    }

    public static Map<String, String> getLocalTokenMap() {
        return localTokenMap;
    }

    public static void setLocalTokenMap(Map<String, String> localTokenMap) {
        JWTCache.localTokenMap = localTokenMap;
    }
}
