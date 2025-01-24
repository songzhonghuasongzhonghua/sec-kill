package com.song.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {
    private final static String MD5 = "jskfjl888";

    /**
     * 简单的md5加密
     * @param pass
     * @return
     */
    public static String getMd5(String pass) {
        String str = pass.charAt(0) + pass.charAt(2) + pass +pass.charAt(3) + pass.charAt(4);
        return DigestUtils.md5Hex(str);
    }
}
