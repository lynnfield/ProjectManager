package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.Task;

import java.util.List;

/**
 * Created by Genovich V.V. on 27.08.2015.
 */
public class SimpleTaskListAdapter extends BaseAdapter {
    private Context context;
    private List<Task> data;

    public SimpleTaskListAdapter(Context context, List<Task> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView nameView;
        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.list_item_task_name, viewGroup, false);
        }

        nameView = (TextView) view;

        nameView.setText(data.get(i).getName());

        return view;
    }
}
