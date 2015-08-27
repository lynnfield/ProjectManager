package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TimedTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Genovich V.V. on 26.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskListLoader extends TaskListLoader {
    @Override
    protected Task parse(JSONObject obj) throws JSONException {
        try {
            TimedTask timedTask = new TimedTask();
            timedTask.fillFrom(obj);
            return timedTask;
        } catch (JSONException ignored) {
            return super.parse(obj);
        }
    }
}
