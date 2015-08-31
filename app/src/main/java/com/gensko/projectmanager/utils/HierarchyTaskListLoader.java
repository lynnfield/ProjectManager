package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.HierarchyTask;
import com.gensko.projectmanager.models.Task;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lynnfield on 30.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class HierarchyTaskListLoader extends TimedTaskListLoader {
    @Override
    protected Task parse(JSONObject obj) throws JSONException {
        try {
            HierarchyTask task = new HierarchyTask();
            task.fillFrom(obj);
            return task;
        } catch (JSONException ignored) {
            return super.parse(obj);
        }
    }
}
