package com.gensko.projectmanager.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.TaskAdapter;
import com.gensko.projectmanager.dialogs.CreateTaskDialog;
import com.gensko.projectmanager.dialogs.DeleteTaskDialog;
import com.gensko.projectmanager.dialogs.EditTaskDialog;
import com.gensko.projectmanager.models.TaskList;
import com.gensko.projectmanager.models.domain.Status;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.models.domain.TaskStateChange;
import com.gensko.projectmanager.observers.TaskRepositoryObserver;
import com.gensko.projectmanager.repositories.TaskRepository;
import com.gensko.projectmanager.repositories.TaskStateChangeRepository;
import com.gensko.projectmanager.utils.ListSaver;

import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TasksActivity
        extends AppCompatActivity
        implements TaskAdapter.OnTaskClickListener,
        CreateTaskDialog.OnTaskCreatedListener,
        EditTaskDialog.OnTaskEditedListener, DeleteTaskDialog.OnDeleteTaskListener {
    private EditTaskDialog editTaskDialog;
    private DeleteTaskDialog deleteTaskDialog;
    private TaskAdapter adapter;
    private Observer observer;
    private Status preEditTaskStatus;

    @Bind(R.id.list)
    RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ButterKnife.bind(this);

        editTaskDialog = new EditTaskDialog(this, this);
        deleteTaskDialog = new DeleteTaskDialog(this, this);
        adapter = new TaskAdapter(this, (TaskList) TaskRepository.getInstance().getData(), this);
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
        TaskRepository.getInstance().add(task);
        TaskRepository.getInstance().save();
        adapter.notifyDataSetChanged();
        onTaskStatusChanged(task, Status.NULL);
    }

    @Override
    public void onTaskEdited(Task task) {
        TaskRepository.getInstance().save();
        adapter.notifyDataSetChanged();
        if (!task.getStatus().equals(preEditTaskStatus))
            onTaskStatusChanged(task, preEditTaskStatus);
    }

    @Override
    public void onDeleteTask(Task task) {
        TaskRepository.getInstance().remove(task);
        TaskRepository.getInstance().save();
        adapter.notifyDataSetChanged();
        TaskStateChangeRepository.getInstance().onTaskRemoved(task);
        TaskStateChangeRepository.getInstance().save();
    }

    @Override
    public void onTaskClick(Task task) {
        preEditTaskStatus = task.getStatus();
        editTaskDialog.setTask(task).show();
    }

    @Override
    public boolean onTaskLongClick(Task task) {
        deleteTaskDialog.setTask(task).show();
        return true;
    }

    private void onTaskStatusChanged(Task task, Status oldStatus) {
        TaskStateChange change = new TaskStateChange();
        change.setTaskId(task.getId());
        change.setOldStatus(oldStatus);
        change.setNewStatus(task.getStatus());
        TaskStateChangeRepository.getInstance().add(change);
        TaskStateChangeRepository.getInstance().save();
    }
}
