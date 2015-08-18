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

    public TaskAdapter(Context context, TaskList data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
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

        public void bindData(Task task) {
            nameView.setText(task.getName());
            statusView.setText(task.getStatus().getStringResourceId());
        }
    }
}
