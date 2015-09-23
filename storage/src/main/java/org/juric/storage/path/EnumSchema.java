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
    IMAGE(ShardGeneratedIdGroup.IMAGE_FILE_ID_GROUP);

    private final ShardGeneratedIdGroup idGroup;

    EnumSchema(ShardGeneratedIdGroup idGroup) {
        this.idGroup = idGroup;
    }

    public ShardGeneratedIdGroup getIdGroup() {
        return idGroup;
    }
}
