package com.juric.carbon.rest.site;

import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.rest.mvc.Version;
import com.juric.carbon.schema.site.Site;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Eric on 10/12/2015.
 */
@Version("1")
@RestController
public class SiteController {

    @Resource(name = "siteService")
    private SiteService siteService;

    @RequestMapping(value = "/sites", method = RequestMethod.POST)
    public @ResponseBody
    Site createSite(@Valid @RequestBody Site site) {
        return  siteService.createSite(site);
    }

    @RequestMapping(value = "/sites", method = RequestMethod.PUT)
    public void updateSite(@RequestBody Site site) {
        siteService.updateSite(site);
    }

    @RequestMapping(value = "/sites/{siteId}", method = RequestMethod.GET)
    public  @ResponseBody Site getSiteById(@PathVariable long siteId) {
        return siteService.getSiteById(siteId);
    }

    @RequestMapping(value = "/sites", method = RequestMethod.GET)
    public  @ResponseBody Site searchSiteByTag(@RequestParam String siteTag) {
        return siteService.getSiteByTag(siteTag);
    }

    @RequestMapping(value = "/users/{userId}/sites", method = RequestMethod.GET)
    public  @ResponseBody
    List<Site> getSitesByUserId(@PathVariable long userId) {
        return siteService.getSitesByUserId(userId);
    }
}
