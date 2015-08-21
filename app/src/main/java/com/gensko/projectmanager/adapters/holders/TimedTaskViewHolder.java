package com.gensko.projectmanager.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.TimedTask;
import com.gensko.projectmanager.utils.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskViewHolder extends TaskViewHolder {

    @Bind(R.id.time)
    TextView timeView;

    public TimedTaskViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public TimedTaskViewHolder(TaskViewHolder holder) {
        this(holder.itemView);
    }

    public void bindData(TimedTask task, OnTaskClickListener listener) {
        super.bindData(task, listener);
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
        timeView.setText(time);
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
        String ret = "";

        ret = getField(total, Calendar.YEAR, " years");
        ret += (" " + getField(total, Calendar.MONTH, " months"));
        ret += (" " + getField(total, Calendar.DAY_OF_MONTH, " days"));
        ret += (" " + getField(total, Calendar.HOUR_OF_DAY, " hours"));
        ret += (" " + getField(total, Calendar.MINUTE, " minutes"));

        return ret;
    }

    private String getField(Calendar calendar, int field, String suffix) {
        String ret = "";
        int val = calendar.get(field);

        if (val > 0)
            ret = val + suffix;

        return ret;
    }

}
