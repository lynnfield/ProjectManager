package com.gensko.projectmanager.activities;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.TaskListAdapter;
import com.gensko.projectmanager.models.Status;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.models.TaskList;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaskListActivity extends AppCompatActivity {
    @Bind(R.id.list)
    RecyclerView taskListView;

    private TaskListAdapter adapter = new TaskListAdapter(this);

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

    private TaskList generateStubData() {
        TaskList list = new TaskList();
        Task parent;
        Task task = new Task();

        task.setCustomer("First customer");
        task.setProject("First project");
        task.setName("First");
        task.setStatus(Status.NEW);

        list.add(task);

        parent = task;
        task = new Task();
        task.setCustomer("First customer");
        task.setProject("First project");
        task.setName("Second");
        task.setParent(parent);
        task.setBegin(new Date());
        task.setStatus(Status.STARTED);

        list.add(task);

        task = new Task();
        task.setCustomer("First customer");
        task.setProject("First project");
        task.setName("Third");
        task.setParent(parent);
        Date date = new Date();
        task.setBegin(date);
        date.setTime(date.getTime() + 360000);
        task.setEnd(date);
        task.setStatus(Status.DONE);

        list.add(task);

        return list;
    }
}
