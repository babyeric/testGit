package com.practice.article;

import com.juric.carbon.schema.article.Article;
import org.apache.ibatis.annotations.Param;
import org.juric.sharding.annotation.LogicalDbName;
import org.juric.sharding.annotation.ShardMethod;
import org.juric.sharding.annotation.ShardParam;
import org.juric.sharding.strategy.IdStrategy;

/**
 * Created by Eric on 10/10/2015.
 */
@LogicalDbName("test")
public interface ArticleHistoryMapper {
    @ShardMethod(IdStrategy.class)
    int append(@ShardParam("articleDB") ArticleDB articleDB);

    @ShardMethod(IdStrategy.class)
    Article getHistory(@ShardParam("articleId") long articleId, @Param("version") Integer version);
}
