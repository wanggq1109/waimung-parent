package com.waimung.constant;

import java.util.HashMap;
import java.util.Map;

public class BucketConstants {

    /**
     * 上传文件目录 目录名不支持下划线(_)  错误目录 eg:test_test01
     */
    public static final String AVATAR = "img/avatar";
    public static final String BANNER = "img/banner";
    public static final String EVIDENCE = "img/evidence";

    public static final String KYC_VIDEO = "video/kyc";

    private static final Map<String,Boolean> authorizes=new HashMap<>();

    static {
        authorizes.put(AVATAR,Boolean.TRUE);
        authorizes.put(BANNER,Boolean.FALSE);
        authorizes.put(EVIDENCE,Boolean.TRUE);
        authorizes.put(KYC_VIDEO,Boolean.TRUE);
    }

    public static Boolean authorize(String key){
        if(authorizes.containsKey(key)){
            return authorizes.get(key);
        }
        return Boolean.TRUE;
    }

}
