package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.holders.TaskViewHolder;
import com.gensko.projectmanager.adapters.holders.TimedTaskViewHolder;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.observers.TimedTaskCreator;
import com.gensko.projectmanager.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskListAdapter extends TaskListAdapter {
    private static final Calendar NULL_CALENDAR;

    static {
        NULL_CALENDAR = Calendar.getInstance();
        NULL_CALENDAR.setTimeInMillis(
                NULL_CALENDAR.getTimeInMillis() - NULL_CALENDAR.getTimeInMillis());
    }

    public TimedTaskListAdapter(Context context) {
        super(context);
    }

    public TimedTaskListAdapter(Context context, OnTaskClickListener listener) {
        super(context, listener);
    }

    public TimedTaskListAdapter(
            Context context,
            TaskList data,
            OnTaskClickListener listener) {
        super(context, data, listener);
    }

    @Override
    public int getItemViewType(int position) {
        if (get(position) instanceof TimedTask)
            return R.layout.list_item_timed_task;
        return super.getItemViewType(position);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == R.layout.list_item_timed_task) {
            View view =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.list_item_timed_task, viewGroup, false);
            return new TimedTaskViewHolder(view);
        }
        return super.onCreateViewHolder(viewGroup, viewType);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        if (viewHolder instanceof TimedTaskViewHolder) {
            TimedTask task = (TimedTask) get(position);
            String time;
            switch (task.getStatus()) {
                case NEW:
                    time = getTimeString(task.getBegin());
                    break;
                case STARTED:
                    time = getTimeString(task.getBegin());
                    break;
                case PAUSED:
                    time = getTimeRange(task.getBegin(), task.getEnd());
                    break;
                case DONE:
                    time = getTotal(task.getTotal());
                    break;
                case NULL:
                default:
                    time = "";
                    break;
            }
            ((TimedTaskViewHolder)viewHolder).timeView.setText(time);
        }
    }

    private String getTimeString(Calendar time) {
        String text = "";

        if (time != null) {
            DateFormat formatter;
            Calendar now = Calendar.getInstance();

            if (Utils.equals(now, time, Calendar.YEAR) &&
                    Utils.equals(now, time, Calendar.DAY_OF_YEAR))
                formatter = SimpleDateFormat.getTimeInstance();
            else
                formatter = SimpleDateFormat.getDateInstance();

            text = formatter.format(time.getTime());
        }

        return text;
    }

    private String getTimeRange(Calendar from, Calendar to) {
        String fromString = getTimeString(from);
        String toString = getTimeString(to);
        return fromString + " - " + toString;
    }

    private String getTotal(Calendar total) {
        String ret;
        ret = getField(total, Calendar.YEAR, " years");
        ret += (" " + getField(total, Calendar.MONTH, " months"));
        ret = ret.trim();
        ret += (" " + getField(total, Calendar.DAY_OF_MONTH, " days"));
        ret = ret.trim();
        ret += (" " + getField(total, Calendar.HOUR_OF_DAY, " hours"));
        ret = ret.trim();
        ret += (" " + getField(total, Calendar.MINUTE, " minutes"));
        ret = ret.trim();
        ret += (" " + getField(total, Calendar.SECOND, " seconds"));

        return ret;
    }

    private String getField(Calendar calendar, int field, String suffix) {
        String ret = "";
        int val = calendar.get(field);

        if (field == Calendar.YEAR || field == Calendar.HOUR_OF_DAY)
            val -= NULL_CALENDAR.get(field);

        if (val > 0)
            ret = val + suffix;

        return ret;
    }
}
