package com.gensko.projectmanager.models;

import com.gensko.projectmanager.models.domain.Task;

import java.util.Calendar;

/**
 * Created by Genovich V.V. on 20.0 8.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTask extends Task {
    private Calendar begin;
    private Calendar end;
    private Calendar total;

    public TimedTask(Task task) {
        setId(task.getId());
        setName(task.getName());
        setStatus(task.getStatus());
    }

    public Calendar getBegin() {
        return begin;
    }

    public void setBegin(Calendar begin) {
        this.begin = begin;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public Calendar getTotal() {
        return total;
    }

    public void setTotal(Calendar total) {
        this.total = total;
    }
}
