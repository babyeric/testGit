package com.practice.http;

import com.juric.carbon.api.article.ArticleService;
import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.article.ArticlePagerResult;
import com.juric.carbon.schema.site.Site;
import com.practice.function.ChainedMethod;
import com.practice.model.NavItem;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 9/24/2015.
 */
@Controller
public class ArticleController extends ControllerSupport {
    private final static String PARAM_ARTICLE_ID = "articleId";
    private final static String PARAM_SITE_ID = "siteId";
    private final static String PARAM_ARTICLE_TITLE = "articleTitle";
    private final static String PARAM_REDIRECT_URL = "redirectUrl";

    @Resource(name="mediaProcessors")
    ChainedMethod<Doc> mediaProcessors;

    @Resource(name="articleService")
    ArticleService articleService;

    @Resource(name="siteService")
    SiteService siteService;

    @RequestMapping("/sites/{siteTag}/articles/edit/{articleId}")
    String articleEditView(@PathVariable String siteTag, @PathVariable long articleId, Model model, @RequestParam String redirectUrl) {
        Article article = articleService.getById(articleId);
        if (article != null) {
            model.addAttribute("article", processForEdit(article));
            model.addAttribute(PARAM_REDIRECT_URL, redirectUrl);
        }

        List<NavItem> navItems = new ArrayList<>();
        navItems.add(new NavItem("Sites", "/sites"));
        navItems.add(new NavItem(siteTag, "/sites/"+siteTag+"/articles"));
        navItems.add(new NavItem("edit", null));
        model.addAttribute("navItems", navItems);

        return "articleEditor";
    }

    @RequestMapping("/sites/{siteTag}/articles/create")
    String articleCreateView(@PathVariable String siteTag, Model model, @RequestParam String redirectUrl) {
        model.addAttribute("siteTag", siteTag);
        Site site = siteService.getSiteByTag(siteTag);
        Article article = new Article();
        article.setSiteId(site.getSiteId());
        model.addAttribute("article", article);
        model.addAttribute(PARAM_REDIRECT_URL, redirectUrl);

        List<NavItem> navItems = new ArrayList<>();
        navItems.add(new NavItem("Sites", "/sites"));
        navItems.add(new NavItem(siteTag, "/sites/"+siteTag+"/articles"));
        navItems.add(new NavItem("create", null));
        model.addAttribute("navItems", navItems);

        return "articleEditor";
    }

    @RequestMapping("/sites/{siteTag}/articles")
    String articleListView(HttpServletRequest request, @PathVariable String siteTag,
                           @RequestParam(required = false) String pivot,
                           @RequestParam(required = false) Boolean forward,
                           Model model) {
        Site site = siteService.getSiteByTag(siteTag);
        if (forward == null) {
            forward = true;
        }
        Date lastDate = null;
        Long lastId = null;
        if (!StringUtils.isEmpty(pivot)) {
            String[] parts = pivot.split(",");
            lastDate = new Date(Long.parseLong(parts[0]));
            lastId = Long.parseLong(parts[1]);
        }
        ArticlePagerResult result = articleService.getArticlesBySite(site.getSiteId(), lastDate, lastId, forward, 2);
        model.addAttribute("articles", result.getArticles());
        if (result.getForwardDate() != null) {
            model.addAttribute("fPivot", result.getForwardDate().getTime()+","+ result.getForwardId());
        }

        if (result.getBackwardDate() != null) {
            model.addAttribute("bPivot", result.getBackwardDate().getTime()+","+ result.getBackwardId());
        }

        model.addAttribute("thisUrl", thisUrl(request));
        model.addAttribute("uri", request.getRequestURI());

        List<NavItem> navItems = new ArrayList<>();
        navItems.add(new NavItem("Sites", "/sites"));
        navItems.add(new NavItem(siteTag, null));
        model.addAttribute("navItems", navItems);

        return "articleList";
    }

    @RequestMapping(value = "/articles/edit", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String handleUpdate(HttpServletRequest request) throws IOException, ServletException {
        Doc doc = new Doc(request);
        mediaProcessors.invoke("process", doc);
        saveArticle(parseLongParam(request, PARAM_ARTICLE_ID), parseLongParam(request, PARAM_SITE_ID), getTitle(request), doc.html());
        String redirectUrl = request.getParameter(PARAM_REDIRECT_URL);
        return "redirect:" + redirectUrl;
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
