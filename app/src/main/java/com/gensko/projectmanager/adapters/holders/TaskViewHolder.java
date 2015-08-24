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
    public
    TextView nameView;
    @Bind(R.id.status)
    public
    ImageView statusView;

    public TaskViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
