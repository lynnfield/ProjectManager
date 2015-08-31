package com.gensko.projectmanager.utils;

import com.gensko.framework.utils.ListSaver;
import com.gensko.projectmanager.models.TaskStateChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeListSaver extends ListSaver<TaskStateChange> {
    public void save(List<TaskStateChange> models, OnListSaverEventsListener<TaskStateChange> listener) {
        save("TaskStateChange", models, listener);
    }

    @Override
    protected JSONObject createJsonFrom(TaskStateChange model) throws JSONException {
        return model.toJson();
    }
}
