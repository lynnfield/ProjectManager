package com.gensko.projectmanager.utils;

import android.content.Context;
import android.widget.Toast;

import com.gensko.projectmanager.models.domain.Task;

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
        return new JSONObject()
                .put("Id", task.getId())
                .put("Name", task.getName())
                .put("Status", task.getStatus().toString());
    }
}
