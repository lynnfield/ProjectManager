package com.gensko.projectmanager.adapters.holders;

import android.view.View;
import android.widget.TextView;

import com.gensko.projectmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 27.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class HierarchyTaskViewHolder extends TimedTaskViewHolder {
    @Bind(R.id.parent)
    public TextView parentView;

    public HierarchyTaskViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public HierarchyTaskViewHolder(TimedTaskViewHolder holder) {
        this(holder.itemView);
    }
}
