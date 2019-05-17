package com.waimung.service;

import com.google.common.io.ByteStreams;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.waimung.constant.BucketConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TencentOSSService implements ObjectStorageService {

    private static final Logger logger = LoggerFactory.getLogger(TencentOSSService.class);

    private String  accessKey;

    private String secretKey;

    private String bucketName;

    private String region;

    private String anonymousBucketName;


    @Override
    public String putObject(String subBucketName, String key, byte[] content, String contentType) {
        COSClient cosClient = null;
        try {
            COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            cosClient = new COSClient(cred,clientConfig);

            String realBucketName = Boolean.TRUE.equals(BucketConstants.authorize(subBucketName)) ? bucketName : anonymousBucketName;
            if (StringUtils.isNotBlank(subBucketName)) {
                realBucketName = realBucketName.concat("/").concat(StringUtils.strip(subBucketName, "/"));
            }
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(content.length);
            objectMetadata.setContentType(contentType);
            cosClient.putObject(bucketName, key, new ByteArrayInputStream(content), objectMetadata);

            return key;
        } catch (CosClientException oe) {
            logger.error("failed to put object,Message:{}", oe.getMessage());
            throw new RuntimeException("failed to put object", oe);
        } finally {
            cosClient.shutdown();
        }


    }

    @Override
    public byte[] getObject(String key) {
        COSClient cosClient = null;
        InputStream in = null;
        byte[] content = null;
        try {
            COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            cosClient = new COSClient(cred,clientConfig);
            COSObject ossObject = cosClient.getObject(bucketName, key);
            in = ossObject.getObjectContent();
            content = ByteStreams.toByteArray(in);
        } catch (CosClientException oe) {
            logger.error("failed to get object,Code:{},Message:{}", oe.getMessage());
            throw new RuntimeException("failed to get object", oe);
        } catch (IOException e) {
            throw new RuntimeException("failed to read object from inputstream", e);
        } finally {
            cosClient.shutdown();

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAnonymousBucketName() {
        return anonymousBucketName;
    }

    public void setAnonymousBucketName(String anonymousBucketName) {
        this.anonymousBucketName = anonymousBucketName;
    }
}
