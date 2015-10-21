package com.practice.article;

import org.apache.ibatis.annotations.Param;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.IdStrategy;

import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 9/27/2015.
 */
@LogicalDbName("test")
public interface ArticleMapper {
    @ShardMethod(IdStrategy.class)
    ArticleDB getById(@ShardParam("articleId") long articleId);

    @ShardMethod(IdStrategy.class)
    List<ArticleDB> getBySite(@ShardParam("siteId") long siteId,
                              @Param("lastDate") Date lastDate,
                              @Param("lastId") Long lastId,
                              @Param("forward") boolean forward,
                              @Param("pageSize") int pageSize);

    @ShardMethod(IdStrategy.class)
    int insert(@ShardParam("articleDB") ArticleDB articleDB);

    @ShardMethod(IdStrategy.class)
    int update(@ShardParam("articleDB") ArticleDB articleDB);
}
