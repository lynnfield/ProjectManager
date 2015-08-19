package com.gensko.projectmanager.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gensko.projectmanager.models.domain.Status;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class StatusAdapter extends BaseAdapter {
    private Context context;

    public StatusAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Status.values().length;
    }

    @Override
    public Object getItem(int i) {
        return Status.values()[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = new TextView(context);

        ((TextView)view).setText(Status.values()[i].getStringResourceId());

        return view;
    }
}
