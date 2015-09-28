package com.practice.wysiwyg;

import com.practice.wysiwyg.media.Image;
import com.practice.wysiwyg.media.Media;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.*;

public class Doc {
    public final static String SUBMISSION = "submission";
    private final static Map<Class<? extends Media>, String> MEIDA_TYPE_TO_TAG = new HashMap<>();
    Map<String, Part> parts = new HashMap<>();
    private String codeset;
    Document document;

    static {
        MEIDA_TYPE_TO_TAG.put(Image.class, "img");
    }

    public Doc(HttpServletRequest request) {
        init(request);
    }

    private void init(HttpServletRequest request) {
        codeset = request.getCharacterEncoding();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String name = params.nextElement();

            try {
                Part part = request.getPart(name);
                parts.put(name, part);
                if (name.equals(SUBMISSION)) {
                    initDocument(part.getInputStream(), codeset);
                }
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        if (document == null) {
            throw new IllegalArgumentException("invalid request");
        }
    }

    private void initDocument(InputStream is, String encoding) throws IOException {
        String html = IOUtils.toString(is, encoding);
        document = Jsoup.parse(html);
    }

    public <T extends Media> List<T> getMedias(Class<T> type) {
        String tag = MEIDA_TYPE_TO_TAG.get(type);
        Elements elements = document.getElementsByTag(tag);
        return convertElementsToMedias(elements, type);
    }

    private <T extends Media> List<T> convertElementsToMedias(Elements elements, Class<T> type) {
        List<T> ret = new ArrayList<>();
        for(Element element : elements) {
            T media = newInstance(element, type);
            if (parts.containsKey(media.getSource())) {
                setContent(media);
            }

            ret.add(media);
        }
        return ret;
    }

    private <T extends Media> void setContent(T media) {
        try {
            media.setContent(IOUtils.toString(parts.get(media.getSource()).getInputStream(), codeset));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static <T extends Media> T newInstance(Element element, Class<T> type) {
        try {
            return (T)type.getConstructor(Element.class).newInstance(element);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String html() {
        return document.body().html();
    }
}