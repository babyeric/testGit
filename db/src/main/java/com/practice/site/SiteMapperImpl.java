package com.practice.site;

import com.practice.reverseLookup.SiteTagToIdLookupMapper;
import org.juric.sharding.annotation.ShardParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/6/15
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SiteMapperImpl implements SiteMapper {
    private SiteMapper siteMapper;
    private SiteTagToIdLookupMapper siteTagToIdLookupMapper;

    public void setSiteMapper(SiteMapper siteMapper) {
        this.siteMapper = siteMapper;
    }

    public void setSiteTagToIdLookupMapper(SiteTagToIdLookupMapper siteTagToIdLookupMapper) {
        this.siteTagToIdLookupMapper = siteTagToIdLookupMapper;
    }

    @Override
    public int save(@ShardParam("siteDB") SiteDB siteDB) {
        SiteDB oldOne = null;
        if (siteDB.getSiteId() != null && siteDB.getSiteTag() != null) {
            oldOne = siteMapper.getSiteById(siteDB.getSiteId());
        }

        int ret = siteMapper.save(siteDB);
        if (ret > 0 && siteDB.getSiteTag() != null) {
            if (oldOne != null) {
                siteTagToIdLookupMapper.delete(oldOne.getSiteTag(), oldOne.getSiteId());
            }
            siteTagToIdLookupMapper.insert(siteDB.getSiteTag(), siteDB.getSiteId());
        }

        return ret;
    }

    @Override
    public SiteDB getSiteById(@ShardParam("siteId") long siteId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SiteDB getSiteByTag(String siteTag) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<SiteDB> getSitesByUserId(@ShardParam("userId") long userId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
