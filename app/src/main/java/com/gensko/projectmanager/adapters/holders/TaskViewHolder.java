package com.gensko.projectmanager.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.domain.Task;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.name)
    TextView nameView;
    @Bind(R.id.status)
    ImageView statusView;

    public TaskViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void bindData(final Task task, final OnTaskClickListener listener) {
        nameView.setText(task.getName());
        statusView.setImageResource(task.getStatus().getDrawableResourceId());
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

    public static interface OnTaskClickListener {
        void onTaskClick(Task task);
        boolean onTaskLongClick(Task task);
    }
}
