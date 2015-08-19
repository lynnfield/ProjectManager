package com.gensko.projectmanager.utils;

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
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());

    public void save(TaskStateChange[] models) {
        save("TaskStateChange", models);
    }

    @Override
    protected JSONObject createJsonFrom(TaskStateChange model) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(TaskStateChange.ID_FIELD, model.getId());
        obj.put(TaskStateChange.TASK_ID_FIELD, model.getTaskId());
        obj.put(TaskStateChange.OLD_STATE_FIELD, model.getOldStatus().toString());
        obj.put(TaskStateChange.NEW_STATE_FIELD, model.getNewStatus().toString());
        obj.put(TaskStateChange.TIME_FIELD, formatter.format(model.getTime().getTime()));
        return obj;
    }
}
