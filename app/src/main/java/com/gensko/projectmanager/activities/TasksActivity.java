package com.gensko.projectmanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.TaskAdapter;
import com.gensko.projectmanager.dialogs.CreateTaskDialog;
import com.gensko.projectmanager.dialogs.EditTaskDialog;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.observers.TaskRepositoryObserver;
import com.gensko.projectmanager.repositories.TaskRepository;

import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener, CreateTaskDialog.OnTaskCreatedListener, EditTaskDialog.OnTaskEditedListener {
    private EditTaskDialog editTaskDialog;
    private TaskAdapter adapter;
    private Observer observer;

    @Bind(R.id.list)
    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        editTaskDialog = new EditTaskDialog(this, this);
        adapter = new TaskAdapter(this, TaskRepository.getInstance().getTasks(), this);
        observer = new TaskRepositoryObserver(adapter);

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TaskRepository.getInstance().addObserver(observer);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TaskRepository.getInstance().deleteObserver(observer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                new CreateTaskDialog(this, this).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCreated(Task task) {
        TaskRepository.getInstance().addTask(task);
        TaskRepository.getInstance().save(this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskEdited(Task task) {
        TaskRepository.getInstance().save(TasksActivity.this);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskClick(Task task) {
        editTaskDialog.setTask(task).show();
    }
}
