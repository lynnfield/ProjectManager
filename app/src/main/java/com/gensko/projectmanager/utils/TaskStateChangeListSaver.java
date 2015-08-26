package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.TaskStateChange;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeListSaver extends ListSaver<TaskStateChange> {
    public void save(TaskStateChange[] models) {
        save("TaskStateChange", models);
    }

    @Override
    protected JSONObject createJsonFrom(TaskStateChange model) throws JSONException {
        return model.toJson();
    }
}
