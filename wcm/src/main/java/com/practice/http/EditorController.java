package com.practice.http;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.schema.article.Article;
import com.practice.article.ArticleDB;
import com.practice.article.ArticleMapper;
import com.practice.function.ChainedMethod;
import com.practice.wysiwyg.Doc;
import com.practice.wysiwyg.media.Image;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final static String PARAM_ARTICLE_ID = "articleId";
    private final static String PARAM_ARTICLE_TITLE = "articleTitle";

    @Resource(name="mediaProcessors")
    ChainedMethod<Doc> mediaProcessors;

    @Resource(name="articleService")
    ArticleService articleService;

    @RequestMapping("/editor/{articleId}")
    String editor(@PathVariable long articleId, Model model) {
        Article article = articleService.getById(articleId);
        if (article != null) {
            model.addAttribute("article", processForEdit(article));
        }
        return "editor";
    }

    @RequestMapping("/editor")
    String home() {
        return "editor";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleUpdate(HttpServletRequest request) throws IOException, ServletException {
        Doc doc = new Doc(request);
        mediaProcessors.invoke("process", doc);
        saveArticle(getArticleId(request), getTitle(request), doc.html());
        return "redirect:editor";
    }

    private Long getArticleId(HttpServletRequest request) {
        String str = request.getParameter(PARAM_ARTICLE_ID);
        if (!StringUtils.isEmpty(str)) {
            return Long.parseLong(str.trim());
        } else {
            return null;
        }
    }

    private String getTitle(HttpServletRequest request) {
        String str = request.getParameter(PARAM_ARTICLE_TITLE);
        if (StringUtils.isEmpty(str)) {
           throw new IllegalArgumentException("empty title");
        }

        return str;
    }

    private Article processForEdit(Article article) {
        Article ret = new Article();
        ret.setArticleId(article.getArticleId());
        ret.setTitle(article.getTitle());
        ret.setContent(article.getContent().replace("\n", "\\\n"));
        return ret;
    }

    private void saveArticle(Long articleId, String title, String content) {
        Article article = new Article();
        article.setContent(content);
        article.setTitle(title);
        article.setModifiedBy("dev");
        article.setSiteId(1234567L);
        if (articleId == null) {
            articleService.create(article);
        } else {
            article.setArticleId(articleId);
            articleService.update(article);
        }
    }
}
