package com.juric.carbon.schema.site;

import com.juric.carbon.schema.base.BaseSchema;

import javax.validation.constraints.NotNull;

/**
 * Created by Eric on 10/3/2015.
 */
public class Site extends BaseSchema {
    private Long siteId;
    @NotNull
    private Long userId;
    @NotNull
    private String name;
    @NotNull
    private String siteTag;
    private String description;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteTag() {
        return siteTag;
    }

    public void setSiteTag(String siteTag) {
        this.siteTag = siteTag;
    }

    @Override
    public String toString() {
        return "Site{" +
                "siteId=" + siteId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", siteTag='" + siteTag + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
