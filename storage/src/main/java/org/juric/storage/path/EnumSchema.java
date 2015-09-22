package org.juric.storage.path;

import com.practice.def.ShardGeneratedIdGroup;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/21/15
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public enum EnumSchema {
    IMAGE("image", ShardGeneratedIdGroup.IMAGE_FILE_ID_GROUP);

    private final ShardGeneratedIdGroup idGroup;
    private final String name;

    EnumSchema(String name, ShardGeneratedIdGroup idGroup) {
        this.name = name;
        this.idGroup = idGroup;
    }

    public ShardGeneratedIdGroup getIdGroup() {
        return idGroup;
    }

    public String getName() {
        return name;
    }
}
