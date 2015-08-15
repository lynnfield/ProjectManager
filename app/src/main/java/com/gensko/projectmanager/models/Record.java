package com.gensko.projectmanager.models;

/**
 * Created by Lynnfield on 14.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Record {
    public static Record NULL = new Record();

    private long Id = 0;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
