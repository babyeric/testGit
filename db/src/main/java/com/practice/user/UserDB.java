package com.practice.user;

import com.juric.carbon.schema.user.User;
import com.practice.def.ShardGeneratedIdGroup;
import com.practice.def.annotation.ShardGeneratedId;
import org.juric.sharding.annotation.ShardAwareId;

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

    public User getUser() {
        return user;
    }

    @ShardAwareId
    public Long getUserId() {
        return user.getUserId();
    }

    @ShardGeneratedId(ShardGeneratedIdGroup.USER_ID_GROUP)
    public void setUserId(Long userId) {
        this.user.setUserId(userId);
    }

    @ShardAwareId
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

    public String getFirstName() {
        return user.getFirstName();
    }

    public void setFirstName(String firstName) {
        user.setFirstName(firstName);
    }

    public String getLastName() {
        return user.getLastName();
    }

    public void setLastName(String lastName) {
        user.setLastName(lastName);
    }

    public String getMobile() {
        return user.getMobile();
    }

    public void setMobile(String mobile) {
        user.setMobile(mobile);
    }

    public String getCountry() {
        return user.getCountry();
    }

    public void setCountry(String country) {
        user.setCountry(country);
    }
}
