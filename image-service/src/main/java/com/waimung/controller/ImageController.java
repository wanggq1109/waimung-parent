package com.waimung.controller;

import com.fire.waimung.common.exception.StandardBusinessException;
import com.waimung.domain.User;
import com.waimung.service.ObjectStorageService;
import com.waimung.utils.ErrorCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageController {

    private static final Logger log = LoggerFactory.getLogger(ImageController.class);


    @Autowired
    private ObjectStorageService objectStorageService;

    /**
     * 统一的上传图片api
     *
     * @param file
     * @param userId
     * @param subBucketName
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "user_id", required = false) Long userId, @RequestParam(value = "subBucketName") String subBucketName) throws IOException {
        byte[] fileContent = convert2Png(file.getBytes());
        String key = UUID.randomUUID().toString() + (userId != null ?
                ("@" + org.springframework.util.DigestUtils.md5DigestAsHex(String.valueOf(userId).getBytes())) : StringUtils.EMPTY);
        log.info("key {}", key);
        String realKey = objectStorageService.putObject(subBucketName, key, fileContent, "image/png");
        log.info("realKey {}", realKey);
        return realKey;
    }

    /**
     * 获取图片文件
     *
     * @param user
     * @param key
     * @return
     */
    @RequestMapping(value = "/image/{key}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(User user, @PathVariable String key) {
        if (key.contains("@")) {
            if (!DigestUtils.md5DigestAsHex(String.valueOf(user.getId()).getBytes()).equals(key.substring(key.indexOf("@") + 1))) {
                throw new StandardBusinessException(ErrorCode.INVALID_IMAGE);
            }
        }
        return objectStorageService.getObject(key);
    }

    private byte[] convert2Png(byte[] fileContent) {
        try {
            ByteArrayInputStream originByteArrayInputStream = new ByteArrayInputStream(fileContent);
            BufferedImage originImage = ImageIO.read(originByteArrayInputStream);
            BufferedImage bufferedImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            bufferedImage.createGraphics().drawImage(originImage, 0, 0, new Color(1.0f, 1.0f, 1.0f, 0.0f), null);
            ByteArrayOutputStream newImageByteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", newImageByteArrayOutputStream);
            byte[] pngBytes = newImageByteArrayOutputStream.toByteArray();
            return pngBytes;
        } catch (IOException exception) {
            log.error("Error occurs while converting file format", exception);
            throw new StandardBusinessException("IMAGE_FORMAT_CONVERSION_ERROR");
        }
    }

}
