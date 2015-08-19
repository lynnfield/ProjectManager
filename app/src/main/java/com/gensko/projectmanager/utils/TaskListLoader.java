package com.gensko.projectmanager.utils;

import android.content.Context;

import com.gensko.projectmanager.models.domain.Task;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class TaskListLoader extends ListLoader<Task> {
    public TaskListLoader(Context context) {
        super(context);
    }

    public void load() {
        super.load("Task");
    }

    @Override
    protected Task parse(JSONObject obj) throws JSONException {
        Task task = new Task();
        task.setId(obj.getLong("Id"));
        task.setName(obj.getString("Name"));
        task.setStatus(com.gensko.projectmanager.models.domain.Status.valueOf(obj.getString("Status")));
        return task;
    }
}
