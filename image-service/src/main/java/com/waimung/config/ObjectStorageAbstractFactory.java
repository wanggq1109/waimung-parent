package com.waimung.config;

import com.waimung.service.ObjectStorageService;

public abstract class ObjectStorageAbstractFactory {


    public abstract ObjectStorageService createObjectStorageService(ObjectStorageProperties properties);

    public static ObjectStorageAbstractFactory getFactory(ServiceProvider provider){
        ObjectStorageAbstractFactory factory = new TencentCOSFactory();

        return factory;
    }

    public enum ServiceProvider{
        TENCENT,ALIYUN
    }
}
