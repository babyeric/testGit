package com.practice.def;

/**
 * Created by Eric on 9/13/2015.
 */
public enum ShardGeneratedIdGroup {
    USER_ID_GROUP(1000),
    ARTICLE_ID_GROUP(1001),
    /////FileId groups start by 100000
    IMAGE_FILE_ID_GROUP(10000);


    private int value;

    ShardGeneratedIdGroup(int value) {
        this.value = value;
    }

    public int value() { return value;}
}
