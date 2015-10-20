package com.practice.http;

import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.site.Site;
import com.juric.carbon.schema.user.User;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Eric on 10/19/2015.
 */
@Controller
public class SiteController extends ControllerSupport {
    @Resource(name = "siteService")
    private SiteService siteService;

    @RequestMapping("/")
    String siteListView(Model model) {
        User user = currentUser();
        List<Site> sites = siteService.getSitesByUserId(user.getUserId());
        model.addAttribute("sites", sites);
        return "sites";
    }

    @RequestMapping("/sites/create")
    String siteCreateView(Model model) {
        User user = currentUser();
        Site site = new Site();
        site.setUserId(user.getUserId());
        model.addAttribute("site", site);
        return "siteEditor";
    }
}
