package com.juric.carbon.service.site;

import com.juric.carbon.api.site.SiteService;
import com.juric.carbon.exception.ValidationException;
import com.juric.carbon.schema.site.Site;
import com.practice.function.Functor;
import com.practice.site.SiteDB;
import com.practice.site.SiteMapper;
import com.practice.utils.ListUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 10/5/2015.
 */
public class
        SiteServiceImpl implements SiteService {
    private SiteMapper siteMapper;

    public void setSiteMapper(SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    @Override
    public Site createSite(Site site) {
        Date now = new Date();
        site.setCreateDate(now);
        site.setModifiedDate(now);
        siteMapper.insert(new SiteDB(site));
        return site;
    }

    @Override
    public void updateSite(Site site) {
        site.setModifiedDate(new Date());
        int ret = siteMapper.update(new SiteDB(site));
        if (ret == 0) {
            throw new ValidationException("site doesn't exist, siteId=" + site.getSiteId());
        }
    }

    @Override
    public Site getSiteById(long siteId) {
        SiteDB db  = siteMapper.getSiteById(siteId);
        if (db != null) {
            return db.getSite();
        }
        return null;
    }

    @Override
    public Site getSiteByTag(String siteTag) {
        SiteDB db  = siteMapper.getSiteByTag(siteTag);
        if (db != null) {
            return db.getSite();
        }
        return null;
    }

    @Override
    public List<Site> getSitesByUserId(long userId) {
        List<SiteDB> list = siteMapper.getSitesByUserId(userId);
        return ListUtils.convert(list, new Functor<SiteDB, Site>() {
            @Override
            public Site invoke(SiteDB siteDB) {
                return siteDB.getSite();
            }
        });
    }
}
