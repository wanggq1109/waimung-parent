package com.waimung.service;

public interface ObjectStorageService {

    String putObject(String subBucketName, String key, byte[] content, String contentType);

    byte[] getObject(String key);
}
