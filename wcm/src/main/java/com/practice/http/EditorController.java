package com.practice.http;

import com.practice.wysiwyg.Doc;
import com.practice.wysiwyg.Image;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 9/24/2015.
 */
@Controller
public class EditorController extends EditorControllerSupport{
    @RequestMapping("/editor")
    String home(Map<String, Object> model) {
        return "editor";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleUpdate(HttpServletRequest request) throws IOException, ServletException {
        Doc doc = new Doc(request);
        List<Image> images = doc.getMedias(Image.class);
        return "redirect:editor";
    }
}
