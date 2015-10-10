package com.juric;

import com.juric.carbon.schema.site.Site;
import com.practice.site.SiteDB;
import com.practice.site.SiteMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eric on 10/10/2015.
 */
public class SiteMapperTests extends AbstractMapperTest{
    @Autowired
    private SiteMapper siteMapper;

    @Test
    public void testInsertSite() {
        SiteDB siteDB = newSiteDB(123456L);
        int ret = siteMapper.insert(siteDB);
        Assert.assertEquals(1, ret);
        Assert.assertNotNull(siteDB.getSiteId());

        SiteDB db = siteMapper.getSiteById(siteDB.getSiteId());
        Assert.assertEquals(siteDB.toString(), db.toString());
    }

    @Test
    public void testUpdateSite() {
        SiteDB siteDB = newSiteDB(123456L);
        int ret = siteMapper.insert(siteDB);
        Assert.assertEquals(1, ret);
        Assert.assertNotNull(siteDB.getSiteId());


        SiteDB newSiteDB = new SiteDB();
        newSiteDB.setSiteId(siteDB.getSiteId());
        newSiteDB.setUserId(siteDB.getUserId());
        newSiteDB.setName("new Name");
        newSiteDB.setDescription("new Description");
        newSiteDB.setSiteTag(UUID.randomUUID().toString());
        newSiteDB.setModifiedDate(new Date());
        newSiteDB.setModifiedBy("Unit Test");
        ret = siteMapper.update(newSiteDB);
        Assert.assertEquals(1, ret);

        siteDB = siteMapper.getSiteById(newSiteDB.getSiteId());
        Assert.assertEquals(newSiteDB.toString(), siteDB.toString());
    }

    @Test
    public void testGetSiteByTag() {
        SiteDB siteDB = newSiteDB(123456L);
        int ret = siteMapper.insert(siteDB);
        Assert.assertEquals(1, ret);
        Assert.assertNotNull(siteDB.getSiteId());

        SiteDB db = siteMapper.getSiteByTag(siteDB.getSiteTag());
        Assert.assertEquals(siteDB.toString(), db.toString());

        db.setSiteId(siteDB.getSiteId());
        db.setUserId(siteDB.getUserId());
        db.setName("new Name");
        db.setDescription("new Description");
        db.setSiteTag(UUID.randomUUID().toString());
        db.setModifiedDate(new Date());
        db.setModifiedBy("Unit Test");
        ret = siteMapper.update(db);
        Assert.assertEquals(1, ret);

        SiteDB newSiteDB = siteMapper.getSiteByTag(siteDB.getSiteTag());
        Assert.assertNull(newSiteDB);

        newSiteDB = siteMapper.getSiteByTag(db.getSiteTag());
        Assert.assertEquals(db.toString(), newSiteDB.toString());
    }

    @Test
    public void testGetByUserId() {
        long userId = 789098L;
        SiteDB siteDB = newSiteDB(userId);
        int ret = siteMapper.insert(siteDB);
        Assert.assertEquals(1, ret);

        SiteDB siteDB2 = newSiteDB(userId);
        ret = siteMapper.insert(siteDB2);
        Assert.assertEquals(1, ret);

        List<SiteDB> results = siteMapper.getSitesByUserId(userId);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(siteDB.toString(), results.get(0).toString());
        Assert.assertEquals(siteDB2.toString(), results.get(1).toString());
    }

    @Test
    public void testGetSiteByTagNotExist() {
        SiteDB siteDB = siteMapper.getSiteByTag("TAG_NOT_EXISTS");
        Assert.assertNull(siteDB);
    }

    @Test
    public void testGetSiteByIdNotExist() {
        SiteDB siteDB = siteMapper.getSiteById(99999999L);
        Assert.assertNull(siteDB);
    }

    private SiteDB newSiteDB(long userId) {
        SiteDB siteDB = new SiteDB();
        siteDB.setName("site name");
        siteDB.setDescription("site description");
        siteDB.setSiteTag(UUID.randomUUID().toString());
        siteDB.setUserId(userId);
        siteDB.setCreateDate(new Date());
        siteDB.setModifiedDate(new Date());
        siteDB.setModifiedBy("UT");
        return siteDB;
    }

}
