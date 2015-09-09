package com.practice.user;

import com.practice.abc.lazyPop.annotation.ShardAwareId;
import com.practice.abc.lazyPop.annotation.ShardParam;

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
    private Long userId;
    private String name;
    private Date birthday;

    @ShardAwareId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
