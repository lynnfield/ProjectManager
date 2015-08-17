package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.TaskProjection;
import com.gensko.projectmanager.models.TaskProjectionsList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Lynnfield on 15.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TaskProjectonsListAdapter extends RecyclerView.Adapter<TaskProjectonsListAdapter.ViewHolder> {
    private Context context;
    private TaskProjectionsList list = new TaskProjectionsList();

    public TaskProjectonsListAdapter(Context context) {
        this.context = context;
    }

    public boolean addAll(Collection<? extends TaskProjection> collection) {
        boolean ret = list.addAll(collection);
        notifyDataSetChanged();
        return ret;
    }

    public void clear() {
        list.clear();
    }

    public TaskProjection get(int index) {
        return list.get(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewTypr) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bindData(get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.customer)
        TextView customerView;
        @Bind(R.id.project)
        TextView projectView;
        @Bind(R.id.parent_task)
        TextView parentTaskView;
        @Bind(R.id.name)
        TextView nameView;
        @Bind(R.id.date)
        TextView dateView;
        @Bind(R.id.time)
        TextView timeView;
        @Bind(R.id.status)
        TextView statusView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(TaskProjection taskProjection) {
            customerView.setText(taskProjection.getCustomer());
            projectView.setText(taskProjection.getProject());
            if (taskProjection.hasParent()) {
                parentTaskView.setVisibility(View.VISIBLE);
                parentTaskView.setText(taskProjection.getParent().getName());
            } else {
                parentTaskView.setVisibility(View.GONE);
            }
            nameView.setText(taskProjection.getName());
            showDate(taskProjection.getBegin());
            showTime(taskProjection.getBegin(), taskProjection.getEnd());
            statusView.setText(taskProjection.getStatus().getStringResourceId());
        }

        private void showDate(Date begin) {
            String text = "";
            if (begin != null)
                text = SimpleDateFormat.getDateInstance().format(begin);
            dateView.setText(text);
        }

        private void showTime(Date begin, Date end) {
            DateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String text = "";
            if (begin != null && end != null)
                text = String.format(
                        "%s - %s",
                        format.format(begin),
                        format.format(end));
            else if (begin != null)
                text = SimpleDateFormat.getTimeInstance().format(begin);
            timeView.setText(text);
        }
    }
}
