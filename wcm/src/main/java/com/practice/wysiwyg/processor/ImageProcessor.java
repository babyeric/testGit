package com.practice.wysiwyg.processor;

import com.juric.carbon.api.storage.path.StoragePathService;
import com.juric.carbon.schema.storage.path.EnumRepository;
import com.juric.carbon.schema.storage.path.EnumSchema;
import com.juric.carbon.schema.storage.path.StoragePath;
import com.practice.configurer.WebMvcConfigurer;
import com.practice.storage.StoragePathWebMvcCodec;
import com.practice.wysiwyg.Doc;
import com.practice.wysiwyg.media.Image;
import org.juric.storage.service.StorageService;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eric on 9/27/2015.
 */
public class ImageProcessor implements MediaProcessor {
    private final static Pattern IMAGE_CONTENT_PATTERN = Pattern.compile("data:image/(png|jpg|gif|jpeg?);base64,");
    private final static Map<String, String> MEDIA_TYPE_TO_EXTENSION = new HashMap<>();

    private StoragePathService storagePathService;

    private StorageService storageService;

    public void setStoragePathService(StoragePathService storagePathService) {
        this.storagePathService = storagePathService;
    }

    public void setStorageService(StorageService storageService) {
        this.storageService = storageService;
    }

    static {
        MEDIA_TYPE_TO_EXTENSION.put("png",".png");
        MEDIA_TYPE_TO_EXTENSION.put("jpg",".jpg");
        MEDIA_TYPE_TO_EXTENSION.put("jpeg",".jpg");
        MEDIA_TYPE_TO_EXTENSION.put("gif",".gif");
    }

    @Override
    public void process(Doc doc) {
        List<Image> images = doc.getMedias(Image.class);
        for(Image image : images) {
            String imageContent = image.getContent();
            if (!StringUtils.isEmpty(imageContent)) {
                Matcher matcher = IMAGE_CONTENT_PATTERN.matcher(imageContent);
                if (!matcher.find()) {
                    throw new IllegalArgumentException("invalid image source=" + image.getSource());
                }
                String type = matcher.group(1);
                String imageData = imageContent.substring(matcher.group().length());
                String extension = MEDIA_TYPE_TO_EXTENSION.get(type);
                String url = saveImage(extension, base64Decode(imageData));
                image.setSource(url);
            }
        }
    }

    private byte[] base64Decode(String encoded) {
        return DatatypeConverter.parseBase64Binary(encoded);
    }

    private String saveImage(String extension, byte[] bytes) {
        StoragePath storagePath = storagePathService.generatePath(EnumRepository.PUBLIC, EnumSchema.IMAGE, null, extension);
        File imageFile = storageService.toFile(storagePath, true);
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        return WebMvcConfigurer.FILE_URL_PRIFIX + StoragePathWebMvcCodec.encode(storagePath);
    }
}
