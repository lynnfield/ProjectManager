package com.gensko.projectmanager.activities;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.TaskProjectonsListAdapter;
import com.gensko.projectmanager.models.Status;
import com.gensko.projectmanager.models.TaskProjection;
import com.gensko.projectmanager.models.TaskProjectionsList;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskProjectionsListActivity extends AppCompatActivity {
    @Bind(R.id.list)
    RecyclerView taskListView;

    private TaskProjectonsListAdapter adapter = new TaskProjectonsListAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        ButterKnife.bind(this);

        adapter.addAll(generateStubData());

        taskListView.setLayoutManager(new LinearLayoutManager(this));
        taskListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.union(0, 5);
            }
        });
        taskListView.setAdapter(adapter);
    }

    private TaskProjectionsList generateStubData() {
        TaskProjectionsList list = new TaskProjectionsList();
        TaskProjection parent;
        TaskProjection taskProjection = new TaskProjection();

        taskProjection.setCustomer("First customer");
        taskProjection.setProject("First project");
        taskProjection.setName("First");
        taskProjection.setStatus(Status.NEW);

        list.add(taskProjection);

        parent = taskProjection;
        taskProjection = new TaskProjection();
        taskProjection.setCustomer("First customer");
        taskProjection.setProject("First project");
        taskProjection.setName("Second");
        taskProjection.setParent(parent);
        taskProjection.setBegin(new Date());
        taskProjection.setStatus(Status.STARTED);

        list.add(taskProjection);

        taskProjection = new TaskProjection();
        taskProjection.setCustomer("First customer");
        taskProjection.setProject("First project");
        taskProjection.setName("Third");
        taskProjection.setParent(parent);
        Date date = new Date();
        taskProjection.setBegin(date);
        date.setTime(date.getTime() + 360000);
        taskProjection.setEnd(date);
        taskProjection.setStatus(Status.DONE);

        list.add(taskProjection);

        return list;
    }
}
