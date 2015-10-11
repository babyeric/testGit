package com.practice.user;

import org.juric.sharding.annotation.ShardAwareId;

import java.util.Date;

/**
 * Created by Eric on 10/4/2015.
 */
public class UserPasswordDB {
    private Long userId;
    private String password;
    private String salt;
    private int version;
    private Date createDate;
    private Date modifiedDate;
    private String modifiedBy;

    @ShardAwareId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
        return "UserPasswordDB{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", version=" + version +
                ", createDate=" + createDate +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
