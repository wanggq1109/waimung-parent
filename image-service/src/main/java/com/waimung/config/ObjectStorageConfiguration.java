package com.waimung.config;

import com.waimung.service.ObjectStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectStorageConfiguration {


    @Bean
    public static ObjectStorageService createObjectStorageService(ObjectStorageProperties properties){
        ObjectStorageAbstractFactory factory = null;
        if(properties.getProvider().equals("tencent")){
            factory = ObjectStorageAbstractFactory.getFactory(ObjectStorageAbstractFactory.ServiceProvider.TENCENT);
        }
        return factory.createObjectStorageService(properties);
    }
}
