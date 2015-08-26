package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.Task;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskListSaver extends ListSaver<Task> {
    public void save(Task[] data) {
        super.save("Task", data);
    }

    @Override
    protected JSONObject createJsonFrom(Task task) throws JSONException {
        return task.toJson();
    }
}
