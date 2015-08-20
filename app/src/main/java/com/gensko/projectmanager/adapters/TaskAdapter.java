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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private Context context;
    private TaskList data = new TaskList();
    private TaskViewHolder.OnTaskClickListener listener;

    public TaskAdapter(
            Context context,
            TaskList data,
            TaskViewHolder.OnTaskClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout., viewGroup, false);
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

    public Task get(int position) {
        return data.get(position);
    }
}
