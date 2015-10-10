package com.practice.site;

import com.practice.reverseLookup.ReverseLookupTables;
import com.practice.reverseLookup.StringToLongLookupMapper;
import com.practice.user.UserDB;
import com.practice.utils.MapperUtils;
import org.juric.sharding.annotation.ShardParam;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/6/15
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SiteMapperImpl implements SiteMapper, InitializingBean{
    private StringToLongLookupMapper stringToLongLookupMapper;
    private SiteMapper impl;

    public void setStringToLongLookupMapper(StringToLongLookupMapper stringToLongLookupMapper) {
        this.stringToLongLookupMapper = stringToLongLookupMapper;
    }

    public void setSiteMapper(SiteMapper impl) {
        this.impl = impl;
    }

    @Override
    public int update(@ShardParam("siteDB") SiteDB siteDB) {
        SiteDB oldOne = null;
        if (siteDB.getSiteTag() != null) {
            oldOne = impl.getSiteById(siteDB.getSiteId());
        }
        if (oldOne == null) {
            return 0;
        }
        int ret = impl.update(siteDB);

        if (ret > 0 && MapperUtils.keyUpdated(oldOne.getSiteTag(), siteDB.getSiteTag())) {
            stringToLongLookupMapper.delete(oldOne.getSiteTag(), oldOne.getSiteId(), ReverseLookupTables.SITE_TAG_TO_ID_LOOKUP);
            stringToLongLookupMapper.insert(siteDB.getSiteTag(), siteDB.getSiteId(), ReverseLookupTables.SITE_TAG_TO_ID_LOOKUP);
        }

        return ret;
    }

    @Override
    public int insert(@ShardParam("siteDB") SiteDB siteDB) {
        int ret = impl.insert(siteDB);
        if (ret > 0) {
            stringToLongLookupMapper.insert(siteDB.getSiteTag(), siteDB.getSiteId(), ReverseLookupTables.SITE_TAG_TO_ID_LOOKUP);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(impl);
        Assert.notNull(stringToLongLookupMapper);
    }
}
