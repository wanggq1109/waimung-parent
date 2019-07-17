package com.waimung.config;

import com.waimung.service.ObjectStorageService;
import com.waimung.service.TencentOSSService;

import java.util.Map;

public class TencentCOSFactory extends ObjectStorageAbstractFactory {

    @Override
    public ObjectStorageService createObjectStorageService(ObjectStorageProperties properties) {

        TencentOSSService service = new TencentOSSService();
        Map<String, String> settings = properties.getSettings();

        if (settings != null && settings.containsKey("accessKey")) {
            service.setAccessKey(settings.get("accessKey"));
        }

        if (settings != null && settings.containsKey("secretKey")) {
            service.setSecretKey(settings.get("secretKey"));
        }

        if (settings != null && settings.containsKey("region")) {
            service.setRegion(settings.get("region"));
        }

        if (settings != null && settings.containsKey("anonymousBucketName")) {
            service.setAnonymousBucketName(settings.get("anonymousBucketName"));
        }

        if (settings != null && settings.containsKey("bucketName")) {
            service.setBucketName(settings.get("bucketName"));
        }

        return service;

    }
}
