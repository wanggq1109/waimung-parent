package com.waimung.utils;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BucketUtils {

    public static Map<String, String> splitKey(String bucketName, String key) {
        String realKey = StringUtils.strip(key, "_");
        String realBucketName = bucketName;
        if (realKey.contains("_")) {
            int index = key.lastIndexOf("_");
            realKey = key.substring(index + 1);
            String subBucketName = StringUtils.strip(StringUtils.replace(key.substring(0, index), "_", "/"), "/");
            realBucketName = realBucketName.concat("/").concat(subBucketName);
        }
        Map<String, String> map = new HashMap<>();
        map.put("key", realKey);
        map.put("bucketName", realBucketName);
        return map;
    }
}
