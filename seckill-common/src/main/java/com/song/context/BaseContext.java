package com.song.context;

import java.util.Map;

public class BaseContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getUserId(){
        return threadLocal.get();
    }


    public static void setUserId(String userId){
        threadLocal.set(userId);
    }
}
