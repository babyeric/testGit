package com.practice.site;

import com.juric.carbon.schema.site.Site;
import com.practice.def.ShardGeneratedIdGroup;
import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.annotation.ShardAwareId;

/**
 * Created by Eric on 10/5/2015.
 */
public class SiteDB {
    private Site site;

    public SiteDB(Site site) {
        this.site = site;
    }

    public SiteDB() {
        this(new Site());
    }

    public Site getSite() {return site;}

    @ShardAwareId
    public Long getSiteId() {
        return site.getSiteId();
    }

    @ShardGeneratedId(ShardGeneratedIdGroup.SITE_ID_GROUP)
    public void setSiteId(Long siteId) {
        site.setSiteId(siteId);
    }

    @ShardAwareId
    public Long getUserId() {
        return site.getUserId();
    }

    public void setUserId(Long userId) {
        site.setUserId(userId);
    }

    public String getName() {
        return site.getName();
    }

    public void setName(String name) {
        site.setName(name);
    }

    public String getDescription() {
        return site.getDescription();
    }

    public void setDescription(String description) {
        site.setDescription(description);
    }

    public String getSiteTag() {
        return site.getSiteTag();
    }

    public void setSiteTag(String siteTag) {
        site.setSiteTag(siteTag);
    }
}
