package com.gensko.projectmanager.models;

import com.gensko.projectmanager.utils.Jsonable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChange extends Record {
    private static final String TASK_ID_FIELD = "TaskId";
    private static final String OLD_STATE_FIELD = "OldState";
    private static final String NEW_STATE_FIELD = "NewState";
    private static final String TIME_FIELD = "Time";

    @SuppressWarnings("UnusedDeclaration")
    public static TaskStateChange NULL = new TaskStateChange();

    private long taskId = -1;
    private State oldState = new State.Null();
    private State newState = new State.Created();
    private Calendar time = Calendar.getInstance();

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public State getOldState() {
        return oldState;
    }

    public void setOldState(State oldState) {
        this.oldState = oldState;
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    @Override
    public void fillFrom(JSONObject obj) throws JSONException {
        super.fillFrom(obj);
        taskId = obj.getLong(TASK_ID_FIELD);
        oldState = State.valueOf(obj.getString(OLD_STATE_FIELD));
        newState = State.valueOf(obj.getString(NEW_STATE_FIELD));
        try {
            Calendar parsed = Calendar.getInstance();
            parsed.setTime(
                    Jsonable.FORMATTER.parse(
                            obj.getString(TaskStateChange.TIME_FIELD)));
            time = parsed;
        } catch (ParseException ignored) {}
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return super.toJson()
                .put(TASK_ID_FIELD, taskId)
                .put(OLD_STATE_FIELD, oldState.toString())
                .put(NEW_STATE_FIELD, newState.toString())
                .put(TIME_FIELD, Jsonable.FORMATTER.format(time.getTime()));
    }
}
