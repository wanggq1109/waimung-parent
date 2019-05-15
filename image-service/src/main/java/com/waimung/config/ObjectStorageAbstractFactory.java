package com.waimung.config;

import com.waimung.service.ObjectStorageService;

public abstract class ObjectStorageAbstractFactory {

    private static final TencentOSSFactory tencentOSSFactory = new TencentOSSFactory();

    private static final AliyunOSSFactory aliyunOSSFactory = new AliyunOSSFactory();


    public abstract ObjectStorageService createObjectStorageService(ObjectStorageProperties properties);

    public static ObjectStorageAbstractFactory getFactory(ServiceProvider provider){
        ObjectStorageAbstractFactory factory = aliyunOSSFactory;
        switch (provider){
            case TENCENT:
                factory = tencentOSSFactory;
                break;
            case ALIYUN:
                factory = aliyunOSSFactory;
                break;
        }
        return factory;
    }

    public enum ServiceProvider{
        TENCENT,ALIYUN
    }
}
