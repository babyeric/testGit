package com.practice.wysiwyg.processor;

import com.practice.wysiwyg.Doc;
import com.practice.wysiwyg.media.Image;

import java.util.List;

/**
 * Created by Eric on 9/27/2015.
 */
public class ImageProcessor implements MediaProcessor {
    @Override
    public void process(Doc doc) {
        List<Image> images = doc.getMedias(Image.class);
    }
}
