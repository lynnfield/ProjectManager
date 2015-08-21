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

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskListAdapter extends TaskListAdapter {
    public TimedTaskListAdapter(Context context) {
        super(context);
    }

    public TimedTaskListAdapter(Context context, TaskViewHolder.OnTaskClickListener listener) {
        super(context, listener);
    }

    public TimedTaskListAdapter(
            Context context,
            TaskList data,
            TaskViewHolder.OnTaskClickListener listener) {
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
        return new TimedTaskViewHolder(super.onCreateViewHolder(viewGroup, viewType));
    }
}
