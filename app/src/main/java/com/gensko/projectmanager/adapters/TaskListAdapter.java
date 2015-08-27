package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.holders.TaskViewHolder;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskList;

import java.util.List;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private Context context;
    private List<Task> data = new TaskList();
    private OnTaskClickListener listener;

    public TaskListAdapter(Context context) {
        this.context = context;
    }

    public TaskListAdapter(Context context, OnTaskClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public TaskListAdapter(
            Context context,
            TaskList data,
            OnTaskClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public List<Task> getData() {
        return data;
    }

    public OnTaskClickListener getListener() {
        return listener;
    }

    public void setListener(OnTaskClickListener listener) {
        this.listener = listener;
    }

    public Task get(int position) {
        return data.get(position);
    }

    public void setData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_item_task;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder viewHolder, int position) {
        final Task task = get(position);
        viewHolder.nameView.setText(task.getName());
        viewHolder.statusView.setImageResource(task.getState().getDrawableResourceId());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.onTaskClick(task);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return listener != null && listener.onTaskLongClick(task);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static interface OnTaskClickListener {
        void onTaskClick(Task task);
        boolean onTaskLongClick(Task task);
    }
}
