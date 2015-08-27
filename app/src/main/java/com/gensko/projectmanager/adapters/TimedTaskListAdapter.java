package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.holders.TaskViewHolder;
import com.gensko.projectmanager.adapters.holders.TimedTaskViewHolder;
import com.gensko.projectmanager.models.State;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskListAdapter extends TaskListAdapter {
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
            String time = "";
            if (task.getState().isBegin())
                time = getTimeString(task.getBegin());
            else if (State.Paused.NAME.equals(task.getState().toString()))
                time = getTimeRange(task.getBegin(), task.getEnd());
            else if (State.Done.NAME.equals(task.getState().toString()))
                time = getTotal(task.getTotal());

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

    private Converter[] converters = {
            new Converter(1000l, 60l, "sec"),
            new Converter(60l, 60l, "min"),
            new Converter(60l, 24l, "hrs"),
            new Converter(24l, 7l, "wks")
    };

    private String getTotal(long total) {
        String ret = "";
        long rounded;

        for (Converter converter : converters) {
            if (converter.check(total)) {
                total = converter.convert(total);
                rounded = converter.round(total);
                if (rounded > 0l)
                    ret = converter.asString(rounded) + " " + ret;
            } else
                break;
        }

        return ret;
    }

    private static class Converter {
        private long step;
        private long max;
        private String suffix;

        private Converter(long step, long max, String suffix) {
            this.step = step;
            this.max = max;
            this.suffix = suffix;
        }

        public boolean check(long val) {
            return val >= step;
        }

        public long convert(long val) {
            return val / step;
        }

        public long round(long val) {
            return val % max;
        }

        public String asString(long val) {
            return val + " " + suffix;
        }
    }
}
