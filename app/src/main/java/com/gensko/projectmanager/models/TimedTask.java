package com.gensko.projectmanager.models;

import com.gensko.projectmanager.utils.Jsonable;
import com.gensko.projectmanager.utils.TaskStateChangeComparator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Genovich V.V. on 20.0 8.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTask extends Task {
    private static final String BEGIN_FIELD = "Begin";
    private static final String END_FIELD = "End";
    private static final String TOTAL_FIELD = "Total";

    private Calendar begin;
    private Calendar end;
    private long total;
    private ArrayList<TaskStateChange> changes = new ArrayList<>();

    public TimedTask() {
    }

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

    public long getTotal() {
        return total;
    }

    protected void setTotal(long total) {
        this.total = total;
    }

    public void addChange(TaskStateChange change) {
        linkChange(change);
        if (change.getNewState().isBegin())
            computeBegin();
        else if (change.getNewState().isEnd())
            computeEnd();
        if (changes.size() >= 2)
            computeTotal();
    }

    public void linkChange(TaskStateChange change) {
        changes.add(change);
        Collections.sort(changes, TaskStateChangeComparator.getInstance());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Task && super.equals(o);
    }

    @Override
    public void fillFrom(JSONObject obj) throws JSONException {
        super.fillFrom(obj);
        try {
            begin = getCalendar(obj, BEGIN_FIELD);
            end = getCalendar(obj, END_FIELD);
        } catch (ParseException ignored) {}
        total = obj.optLong(TOTAL_FIELD, 0l);
    }

    private static Calendar getCalendar(JSONObject obj, String field)
            throws ParseException, JSONException {
        String read = obj.optString(field, null);
        if (read != null) {
            Calendar parsed = Calendar.getInstance();
            parsed.setTime(Jsonable.FORMATTER.parse(read));
            return parsed;
        }
        return null;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject ret = super.toJson();
        if (begin == null)
            ret.put(BEGIN_FIELD, null);
        else
            ret.put(BEGIN_FIELD, Jsonable.FORMATTER.format(begin.getTime()));

        if (end == null)
            ret.put(END_FIELD, null);
        else
            ret.put(END_FIELD, Jsonable.FORMATTER.format(end.getTime()));

        ret.put(TOTAL_FIELD, total);

        return ret;
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
        setTotal(0);
        for (int i = 0; i < changes.size(); ++ i) {
            TaskStateChange current = changes.get(i);

            if (current.getNewState().isBegin()) {
                start = current.getTime();
            } else if (current.getNewState().isEnd() && start != null) {
                long total = current.getTime().getTimeInMillis() - start.getTimeInMillis();
                total += getTotal();
                setTotal(total);
            }
        }
    }
}
