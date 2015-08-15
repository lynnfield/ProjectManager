package com.gensko.projectmanager.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.gensko.projectmanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskListActivity extends AppCompatActivity {
    @Bind(R.id.list)
    RecyclerView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        ButterKnife.bind(this);

        taskListView.setLayoutManager(new LinearLayoutManager(this));
    }
}
