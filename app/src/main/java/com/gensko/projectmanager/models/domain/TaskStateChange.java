package com.gensko.projectmanager.models.domain;

import java.util.Calendar;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskStateChange extends Record {
    public static final String TASK_ID_FIELD = "TaskId";
    public static final String OLD_STATE_FIELD = "OldState";
    public static final String NEW_STATE_FIELD = "NewState";
    public static final String TIME_FIELD = "Time";

    public static TaskStateChange NULL = new TaskStateChange();

    private long taskId = -1;
    private Status oldStatus = Status.NULL;
    private Status newStatus = Status.NULL;
    private Calendar time = Calendar.getInstance();

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Status getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Status oldStatus) {
        this.oldStatus = oldStatus;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(Status newStatus) {
        this.newStatus = newStatus;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
