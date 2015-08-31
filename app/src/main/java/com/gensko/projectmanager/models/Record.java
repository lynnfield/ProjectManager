package com.gensko.projectmanager.models;

import com.gensko.framework.data.models.Jsonable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lynnfield on 14.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Record implements Jsonable {
    private static final String ID_FIELD = "Id";

    public static Record NULL = new Record();

    private long Id = 0;

    public Record() {}

    public Record(Record record) {
        Id = record.Id;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    @Override
    public void fillFrom(JSONObject obj) throws JSONException {
        Id = obj.getLong(ID_FIELD);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(ID_FIELD, Id);
        return obj;
    }
}
