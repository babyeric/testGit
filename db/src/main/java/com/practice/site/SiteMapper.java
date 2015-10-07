package com.practice.site;

import com.juric.carbon.schema.site.Site;
import com.practice.reverseLookup.ReverseLookupContext;
import com.practice.reverseLookup.ReverseLookupStrategy;
import com.practice.reverseLookup.ReverseLookupTables;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.IdStrategy;
import org.juric.sharding.strategy.ShardingStrategy;

import java.util.List;

/**
 * Created by Eric on 10/5/2015.
 */
@LogicalDbName("test")
public interface SiteMapper {
    @ShardMethod(IdStrategy.class)
    int save(@ShardParam("siteDB") SiteDB siteDB);

    @ShardMethod(IdStrategy.class)
    SiteDB getSiteById(@ShardParam("siteId") long siteId);

    @ShardMethod(ReverseLookupStrategy.class)
    @ReverseLookupContext(ReverseLookupTables.SITETAG_TO_SITEID_LOOKUP)
    SiteDB getSiteByTag(@ShardParam("siteTag") String siteTag);

    @ShardMethod(IdStrategy.class)
    List<SiteDB> getSitesByUserId(@ShardParam("userId") long userId);
}
