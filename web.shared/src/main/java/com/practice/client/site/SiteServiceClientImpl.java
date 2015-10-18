package com.practice.client.site;

import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.schema.site.Site;
import com.juric.carbon.schema.user.User;
import com.practice.client.common.AbstractServiceClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric on 10/15/2015.
 */
public class SiteServiceClientImpl extends AbstractServiceClient implements SiteService {
    String url = carbonRoot + "/1/sites";

    @Override
    public Site createSite(Site site) {
        Site ret = restTemplate.postForObject(url, site, Site.class);
        return ret;
    }

    @Override
    public void updateSite(Site site) {
        String url = carbonRoot + "/1/sites";
        restTemplate.put(url, site);
    }

    @Override
    public Site getSiteById(long siteId) {
        Map<String, Object> pathVaribles = new HashMap<>();
        pathVaribles.put("siteId", siteId);

        String url = carbonRoot + "/1/sites/{siteId}";
        return restTemplate.getForObject(url, Site.class, pathVaribles);
    }

    @Override
    public Site getSiteByTag(String siteTag) {
        String url = carbonRoot + "/1/sites";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("siteTag", siteTag);
        return restTemplate.getForObject(builder.toUriString(), Site.class);
    }

    @Override
    public List<Site> getSitesByUserId(long userId) {
        Map<String, Object> pathVaribles = new HashMap<>();
        pathVaribles.put("userId", userId);

        String url = carbonRoot + "/1/users/{userId}/sites";
        Site[] result = restTemplate.getForObject(url, Site[].class, pathVaribles);
        return CollectionUtils.arrayToList(result);
    }
}
