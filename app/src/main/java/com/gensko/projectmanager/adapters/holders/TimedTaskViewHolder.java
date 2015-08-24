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
    public
    TextView timeView;

    public TimedTaskViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public TimedTaskViewHolder(TaskViewHolder holder) {
        this(holder.itemView);
    }
}
