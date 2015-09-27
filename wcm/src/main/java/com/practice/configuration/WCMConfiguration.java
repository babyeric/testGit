package com.practice.configuration;

import com.practice.function.ChainedMethod;
import com.practice.wysiwyg.Doc;
import com.practice.wysiwyg.processor.ImageProcessor;
import com.practice.wysiwyg.processor.MediaProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Eric on 9/27/2015.
 */
@Configuration
public class WCMConfiguration {
    @Bean(name="imageProcessor")
    public ImageProcessor imageProcessor() {
        ImageProcessor imageProcessor = new ImageProcessor();
        return imageProcessor;
    }

    @Bean(name="mediaProcessors")
    public ChainedMethod mediaProcessors() throws NoSuchMethodException {
        ChainedMethod<MediaProcessor> cm = new ChainedMethod(MediaProcessor.class.getMethod("process", Doc.class));
        cm.add(imageProcessor());
        return cm;
    }
}
