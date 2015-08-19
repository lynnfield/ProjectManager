package com.gensko.projectmanager.utils;

import android.content.Context;

import com.gensko.projectmanager.models.domain.TaskStateChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChangeListLoader extends ListLoader<TaskStateChange> {
    private DateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());

    public void load() {
        load("TaskStateChange");
    }

    @Override
    protected TaskStateChange parse(JSONObject obj) throws JSONException {
        TaskStateChange ret = new TaskStateChange();
        ret.setId(obj.getLong(TaskStateChange.ID_FIELD));
        ret.setTaskId(obj.getLong(TaskStateChange.TASK_ID_FIELD));
        ret.setOldStatus(
                com.gensko.projectmanager.models.domain.Status.valueOf(
                        obj.getString(TaskStateChange.OLD_STATE_FIELD)));
        ret.setNewStatus(
                com.gensko.projectmanager.models.domain.Status.valueOf(
                        obj.getString(TaskStateChange.NEW_STATE_FIELD)));
        try {
            Calendar time = Calendar.getInstance();
            time.setTime(
                    formatter.parse(
                            obj.getString(TaskStateChange.TIME_FIELD)));
            ret.setTime(time);
        } catch (ParseException ignored) {}
        return ret;
    }
}
