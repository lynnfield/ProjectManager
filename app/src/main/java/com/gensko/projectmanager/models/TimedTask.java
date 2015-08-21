package com.gensko.projectmanager.models;

import android.support.v7.util.SortedList;

import com.gensko.projectmanager.models.domain.Status;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.domain.TaskStateChange;
import com.gensko.projectmanager.utils.TaskStateChangeComparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Genovich V.V. on 20.0 8.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTask extends Task {
    private Calendar begin;
    private Calendar end;
    private Calendar total;
    private ArrayList<TaskStateChange> changes = new ArrayList<>();

    public TimedTask(Task task) {
        setId(task.getId());
        setName(task.getName());
        setStatus(task.getStatus());
    }

    public Calendar getBegin() {
        return begin;
    }

    protected void setBegin(Calendar begin) {
        this.begin = begin;
    }

    public Calendar getEnd() {
        return end;
    }

    protected void setEnd(Calendar end) {
        this.end = end;
    }

    public Calendar getTotal() {
        return total;
    }

    protected void setTotal(Calendar total) {
        this.total = total;
    }

    public void addChange(TaskStateChange data) {
        changes.add(data);
        Collections.sort(changes, TaskStateChangeComparator.getInstance());
        if (data.getNewStatus().isBegin())
            computeBegin();
        else if (data.getNewStatus().isEnd())
            computeEnd();
        if (changes.size() > 2)
            computeTotal();
    }

    private void computeBegin() {
        for (int i = changes.size() - 1; i >= 0; --i)
            if (changes.get(i).getNewStatus().isBegin()) {
                setBegin(changes.get(i).getTime());
                return;
            }
    }

    private void computeEnd() {
        for (int i = changes.size() - 1; i >= 0; --i)
            if (changes.get(i).getNewStatus().isEnd()) {
                setEnd(changes.get(i).getTime());
                return;
            }
    }

    private void computeTotal() {
        Calendar start = null;
        for (int i = 0; i < changes.size(); ++ i) {
            TaskStateChange current = changes.get(i);

            if (current.getNewStatus().isBegin()) {
                start = current.getTime();
            } else if (current.getNewStatus().isEnd() && start != null) {
                Calendar total = current.getTime();
                total.setTimeInMillis(total.getTimeInMillis() - start.getTimeInMillis());
                if (getTotal() != null)
                    total.setTimeInMillis(total.getTimeInMillis() + getTotal().getTimeInMillis());
                setTotal(total);
            }
        }
    }
}
