package com.practice.site;

import com.practice.reverseLookup.ReverseLookupTables;
import com.practice.reverseLookup.StringToLongLookupMapper;
import org.juric.sharding.annotation.ShardParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/6/15
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SiteMapperImpl implements SiteMapper{
    private StringToLongLookupMapper stringToLongLookupMapper;
    private SiteMapper impl;

    public void setStringToLongLookupMapper(StringToLongLookupMapper stringToLongLookupMapper) {
        this.stringToLongLookupMapper = stringToLongLookupMapper;
    }

    public void setSiteMapper(SiteMapper impl) {
        this.impl = impl;
    }

    @Override
    public int save(@ShardParam("siteDB") SiteDB siteDB) {
        SiteDB oldOne = null;
        if (siteDB.getSiteId() != null && siteDB.getSiteTag() != null) {
            oldOne = impl.getSiteById(siteDB.getSiteId());
        }

        int ret = impl.save(siteDB);
        if (ret > 0 && siteDB.getSiteTag() != null) {
            if (oldOne != null) {
                stringToLongLookupMapper.delete(oldOne.getSiteTag(), oldOne.getSiteId(), ReverseLookupTables.SITETAG_TO_SITEID_LOOKUP);
            }
            stringToLongLookupMapper.insert(siteDB.getSiteTag(), siteDB.getSiteId(), ReverseLookupTables.SITETAG_TO_SITEID_LOOKUP);
        }

        return ret;
    }

    @Override
    public SiteDB getSiteById(@ShardParam("siteId") long siteId) {
        return impl.getSiteById(siteId);
    }

    @Override
    public SiteDB getSiteByTag(@ShardParam("siteTag") String siteTag) {
        return impl.getSiteByTag(siteTag);
    }

    @Override
    public List<SiteDB> getSitesByUserId(@ShardParam("userId") long userId) {
        return impl.getSitesByUserId(userId);
    }
}
