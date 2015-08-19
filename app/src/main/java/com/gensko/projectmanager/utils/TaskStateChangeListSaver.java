package com.gensko.projectmanager.utils;

import android.content.Context;

import com.gensko.projectmanager.models.domain.TaskStateChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeListSaver extends ListSaver<TaskStateChange> {
    private DateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault());

    public TaskStateChangeListSaver(Context context) {
        super(context);
    }

    public void save(TaskStateChange[] models) {
        save("TaskStateChange", models);
    }

    @Override
    protected JSONObject createJsonFrom(TaskStateChange model) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("Id", model.getId());
        obj.put("TaskId", model.getTaskId());
        obj.put("OldStatus", model.getOldStatus().toString());
        obj.put("NewStatus", model.getNewStatus().toString());
        obj.put("Time", formatter.format(model.getTime().getTime()));
        return obj;
    }
}
