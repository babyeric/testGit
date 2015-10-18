package com.practice.http;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.schema.article.Article;
import com.practice.article.ArticleDB;
import com.practice.article.ArticleMapper;
import com.practice.function.ChainedMethod;
import com.practice.wysiwyg.Doc;
import com.practice.wysiwyg.media.Image;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 9/24/2015.
 */
@Controller
public class EditorController extends EditorControllerSupport{
    @Resource(name="mediaProcessors")
    ChainedMethod<Doc> mediaProcessors;

    @Resource(name="articleService")
    ArticleService articleService;

    @RequestMapping("/editor")
    String home(Map<String, Object> model) {
        return "editor";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleUpdate(HttpServletRequest request) throws IOException, ServletException {
        Doc doc = new Doc(request);
        mediaProcessors.invoke("process", doc);
        saveArticle(doc.html());
        return "redirect:editor";
    }

    private void saveArticle(String content) {
        Article article = new Article();
        article.setContent(content);
        article.setTitle("test article");
        article.setModifiedBy("dev");
        articleService.create(article);
    }
}
