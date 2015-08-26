package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.TaskStateChange;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeListLoader extends ListLoader<TaskStateChange> {

    public void load() {
        load("TaskStateChange");
    }

    @Override
    protected TaskStateChange parse(JSONObject obj) throws JSONException {
        TaskStateChange ret = new TaskStateChange();
        ret.fillFrom(obj);
        return ret;
    }
}
