package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.holders.HierarchyTaskViewHolder;
import com.gensko.projectmanager.adapters.holders.TaskViewHolder;
import com.gensko.projectmanager.models.HierarchyTask;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.repositories.TaskRepository;

/**
 * Created by Genovich V.V. on 27.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class HierarchyTaskListAdapter extends TimedTaskListAdapter {
    public HierarchyTaskListAdapter(Context context) {
        super(context);
    }

    public HierarchyTaskListAdapter(Context context, OnTaskClickListener listener) {
        super(context, listener);
    }

    public HierarchyTaskListAdapter(
            Context context,
            TaskList data,
            OnTaskClickListener listener) {
        super(context, data, listener);
    }

    @Override
    public int getItemViewType(int position) {
        if (get(position) instanceof HierarchyTask)
            return R.layout.list_item_hierarchy_task;
        return super.getItemViewType(position);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == R.layout.list_item_hierarchy_task) {
            View view =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.list_item_hierarchy_task, viewGroup, false);
            return new HierarchyTaskViewHolder(view);
        }
        return super.onCreateViewHolder(viewGroup, viewType);
    }

    private static final String delimiter = " <- ";

    @Override
    public void onBindViewHolder(TaskViewHolder viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        if (viewHolder instanceof HierarchyTaskViewHolder) {
            HierarchyTaskViewHolder holder = (HierarchyTaskViewHolder) viewHolder;
            HierarchyTask task = (HierarchyTask) get(position);
            StringBuilder builder = new StringBuilder();
            while (task.hasParent()) {
                if (task.getParent() == null)
                    TaskRepository.getInstance().loadParentFor(task);
                builder
                        .append(task.getParent().getName())
                        .append(delimiter);
                task = task.getParent();
            }
            int i = builder.lastIndexOf(delimiter);
            if (i >= 0)
                builder.delete(i, builder.length() - 1);
            holder.parentView.setText(builder.toString());
        }
    }
}
