package com.gensko.projectmanager.utils;

import com.gensko.framework.utils.ListLoader;
import com.gensko.projectmanager.models.Task;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskListLoader extends ListLoader<Task> {
    public void load(OnLoaderEventsListener<Task> listener) {
        super.load("Task", listener);
    }

    @Override
    protected Task parse(JSONObject obj) throws JSONException {
        Task task = new Task();
        task.fillFrom(obj);
        return task;
    }
}
