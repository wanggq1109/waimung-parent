package com.waimung.service;

public class TencentOSSService implements ObjectStorageService {

    @Override
    public String putObject(String subBucketName, String key, byte[] content, String contentType) {
        return null;
    }

    @Override
    public byte[] getObject(String key) {
        return new byte[0];
    }
}
