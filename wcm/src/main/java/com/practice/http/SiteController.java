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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Eric on 10/19/2015.
 */
@Controller
public class SiteController extends ControllerSupport {
    private final static String PARAM_USER_ID = "userId";
    private final static String PARAM_SITE_ID = "siteId";
    private final static String PARAM_NAME = "name";
    private final static String PARAM_SITE_TAG = "siteTag";
    private final static String PARAM_DESCRIPTION = "description";


    @Resource(name = "siteService")
    private SiteService siteService;

    @RequestMapping({"/", "/sites"})
    String siteListView(Model model) {
        User user = currentUser();
        List<Site> sites = siteService.getSitesByUserId(user.getUserId());
        model.addAttribute("sites", sites);
        return "siteList";
    }

    @RequestMapping("/sites/edit")
    String siteCreateView(Model model) {
        Site site = new Site();
        model.addAttribute("site", site);
        return "siteEditor";
    }

    @RequestMapping("/sites/edit/{siteId}")
    String siteEditView(@PathVariable long siteId, Model model) {
        Site site = siteService.getSiteById(siteId);
        model.addAttribute("site", site);
        return "siteEditor";
    }

    @RequestMapping(value = "/sites/edit", method = RequestMethod.POST)
    String handlePost(HttpServletRequest request) {
        Site site = new Site();
        site.setName(request.getParameter(PARAM_NAME));
        site.setDescription(request.getParameter(PARAM_DESCRIPTION));
        site.setSiteTag(request.getParameter(PARAM_SITE_TAG));
        site.setSiteId(parseLongParam(request, PARAM_SITE_ID));
        site.setModifiedBy("WCM");
        if (site.getSiteId() == null) {
            site.setUserId(currentUser().getUserId());
            siteService.createSite(site);
        } else {
            siteService.updateSite(site);
        }

        return "redirect:/sites";
    }
}
