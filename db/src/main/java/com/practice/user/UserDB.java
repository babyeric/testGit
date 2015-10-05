package com.practice.user;

import com.juric.carbon.schema.article.Article;
import com.juric.carbon.schema.user.User;
import com.practice.def.ShardGeneratedIdGroup;
import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.annotation.ShardAwareId;
import org.juric.sharding.policy.HashShardAwarePolicy;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 8/12/15
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserDB implements Serializable {
    private User user;

    public UserDB() {
        this(new User());
    }

    public UserDB(User user) {
        this.user = user;
    }

    @ShardAwareId
    public Long getUserId() {
        return user.getUserId();
    }

    @ShardGeneratedId(ShardGeneratedIdGroup.USER_ID_GROUP)
    public void setUserId(Long userId) {
        this.user.setUserId(userId);
    }

    @ShardAwareId(HashShardAwarePolicy.class)
    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public Date getBirthday() {
        return user.getBirthday();
    }

    public void setBirthday(Date birthday) {
        user.setBirthday(birthday);
    }
}
