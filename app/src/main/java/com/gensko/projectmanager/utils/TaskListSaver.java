package com.gensko.projectmanager.utils;

import android.content.Context;

import com.gensko.projectmanager.models.domain.Task;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskListSaver extends ListSaver<Task> {
    public TaskListSaver(Context context) {
        super(context);
    }

    public void save(Task[] data) {
        super.save("Task", data);
    }

    @Override
    protected JSONObject createJsonFrom(Task task) throws JSONException {
        return new JSONObject()
                .put("id", task.getId())
                .put("name", task.getName())
                .put("status", task.getStatus().toString());
    }
}
