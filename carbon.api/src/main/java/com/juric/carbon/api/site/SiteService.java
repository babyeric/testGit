package com.juric.carbon.api.site;

import com.juric.carbon.schema.site.Site;

import java.util.List;

/**
 * Created by Eric on 10/3/2015.
 */
public interface SiteService {
    Site createSite(Site site);
    void updateSite(Site site);
    Site getSiteById(long siteId);
    Site getSiteByTag(String siteTag);
    List<Site> getSitesByUserId(long userId);
}
