package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.domain.TaskList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 17.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private Context context;
    private TaskList data = new TaskList();
    private OnTaskClickListener listener;

    public TaskAdapter(Context context, TaskList data, OnTaskClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView nameView;
        @Bind(R.id.status)
        TextView statusView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(final Task task, final OnTaskClickListener listener) {
            nameView.setText(task.getName());
            statusView.setText(task.getStatus().getStringResourceId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onTaskClick(task);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return listener != null && listener.onTaskLongClick(task);
                }
            });
        }
    }

    public static interface OnTaskClickListener {
        void onTaskClick(Task task);
        boolean onTaskLongClick(Task task);
    }
}
