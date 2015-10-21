package com.practice.http;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.site.Site;
import com.practice.function.ChainedMethod;
import com.practice.wysiwyg.Doc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 9/24/2015.
 */
@Controller
public class ArticleController extends ControllerSupport {
    private final static String PARAM_ARTICLE_ID = "articleId";
    private final static String PARAM_SITE_ID = "siteId";
    private final static String PARAM_ARTICLE_TITLE = "articleTitle";

    @Resource(name="mediaProcessors")
    ChainedMethod<Doc> mediaProcessors;

    @Resource(name="articleService")
    ArticleService articleService;

    @Resource(name="siteService")
    SiteService siteService;

    @RequestMapping("/articles/edit/{articleId}")
    String articleEditView(@PathVariable long articleId, Model model) {
        Article article = articleService.getById(articleId);
        if (article != null) {
            model.addAttribute("article", processForEdit(article));
        }
        return "articleEditor";
    }

    @RequestMapping("/sites/{siteTag}/articles/create")
    String articleCreateView(@PathVariable String siteTag, Model model) {
        model.addAttribute("siteTag", siteTag);
        Site site = siteService.getSiteByTag(siteTag);
        Article article = new Article();
        article.setSiteId(site.getSiteId());
        model.addAttribute("article", article);
        return "articleEditor";
    }

    @RequestMapping("/sites/{siteTag}/articles")
    String articleListView(@PathVariable String siteTag, Model model) {
        Site site = siteService.getSiteByTag(siteTag);
        List<Article> articles = articleService.getArticlesBySite(site.getSiteId(), null, null, 10);
        model.addAttribute("articles", articles);
        model.addAttribute("siteTag", siteTag);
        return "articleList";
    }

    @RequestMapping(value = "/articles/edit", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleUpdate(HttpServletRequest request) throws IOException, ServletException {
        Doc doc = new Doc(request);
        mediaProcessors.invoke("process", doc);
        saveArticle(parseLongParam(request, PARAM_ARTICLE_ID), parseLongParam(request, PARAM_SITE_ID), getTitle(request), doc.html());
        return "redirect:articleEditor";
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
    private void saveArticle(Long articleId, Long siteId, String title, String content) {
        Article article = new Article();
        article.setContent(content);
        article.setTitle(title);
        article.setModifiedBy("dev");
        if (articleId == null) {
            article.setSiteId(siteId);
            articleService.create(article);
        } else {
            article.setArticleId(articleId);
            articleService.update(article);
        }
    }
}
