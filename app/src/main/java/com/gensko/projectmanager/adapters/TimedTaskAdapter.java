package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.holders.TimedTaskViewHolder;
import com.gensko.projectmanager.models.TimedTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TimedTaskAdapter extends RecyclerView.Adapter<TimedTaskViewHolder> {
    private Context context;
    private List<TimedTask> data = new ArrayList<>();
    private TimedTaskViewHolder.OnTaskClickListener listener;

    public TimedTaskAdapter(
            Context context,
            List<TimedTask> data,
            TimedTaskViewHolder.OnTaskClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public TimedTaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_task, parent, false);
        return new TimedTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimedTaskViewHolder holder, int position) {
        holder.bindData(get(position), listener);
    }

    public TimedTask get(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
