package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.holders.TaskViewHolder;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.TaskList;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskListAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private Context context;
    private List<Task> data = new TaskList();
    private TaskViewHolder.OnTaskClickListener listener;

    public TaskListAdapter(Context context) {
        this.context = context;
    }

    public TaskListAdapter(Context context, TaskViewHolder.OnTaskClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public TaskListAdapter(
            Context context,
            TaskList data,
            TaskViewHolder.OnTaskClickListener listener) {
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

    public TaskViewHolder.OnTaskClickListener getListener() {
        return listener;
    }

    public void setListener(TaskViewHolder.OnTaskClickListener listener) {
        this.listener = listener;
    }

    public Task get(int position) {
        return data.get(position);
    }

    public void setData(List<Task> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public boolean add(Task task) {
        boolean ret = data.add(task);
        notifyDataSetChanged();
        return ret;
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
        viewHolder.bindData(get(position), listener);
    }

    @Override
    public long getItemId(int position) {
        return get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
