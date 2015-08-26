package com.gensko.projectmanager.models;

import com.gensko.projectmanager.utils.TaskStateChangeComparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Genovich V.V. on 20.0 8.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTask extends Task {
    private Calendar begin;
    private Calendar end;
    private Date total;
    private ArrayList<TaskStateChange> changes = new ArrayList<>();

    public TimedTask(Task task) {
        super(task);
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

    public Date getTotal() {
        return total;
    }

    protected void setTotal(Date total) {
        this.total = total;
    }

    public void addChange(TaskStateChange data) {
        changes.add(data);
        Collections.sort(changes, TaskStateChangeComparator.getInstance());
        if (data.getNewState().isBegin())
            computeBegin();
        else if (data.getNewState().isEnd())
            computeEnd();
        if (changes.size() >= 2)
            computeTotal();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Task && super.equals(o);
    }

    private void computeBegin() {
        for (int i = changes.size() - 1; i >= 0; --i)
            if (changes.get(i).getNewState().isBegin()) {
                setBegin(changes.get(i).getTime());
                return;
            }
    }

    private void computeEnd() {
        for (int i = changes.size() - 1; i >= 0; --i)
            if (changes.get(i).getNewState().isEnd()) {
                setEnd(changes.get(i).getTime());
                return;
            }
    }

    private void computeTotal() {
        Calendar start = null;
        for (int i = 0; i < changes.size(); ++ i) {
            TaskStateChange current = changes.get(i);

            if (current.getNewState().isBegin()) {
                start = current.getTime();
            } else if (current.getNewState().isEnd() && start != null) {
                long total = current.getTime().getTimeInMillis() - start.getTimeInMillis();
                if (getTotal() != null)
                    total += getTotal().getTime();
                setTotal(total);
            }
        }
    }

    private void setTotal(long millis) {
        if (total != null)
            total.setTime(millis);
        else
            total = new Date(millis);
    }
}
