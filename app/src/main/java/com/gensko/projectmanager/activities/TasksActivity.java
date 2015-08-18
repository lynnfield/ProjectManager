package com.gensko.projectmanager.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.Status;
import com.gensko.projectmanager.models.domain.Task;
import com.gensko.projectmanager.repositories.TasksRepository;

import butterknife.ButterKnife;

public class TasksActivity extends AppCompatActivity implements DialogInterface.OnClickListener {
    private AlertDialog addTaskDialog = newAddTaskDialog(this);
    private EditText taskNameView;

    private AlertDialog newAddTaskDialog(TasksActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.add_task);
        View view = LayoutInflater.from(context).inflate(R.layout.add_task, null);
        taskNameView = ButterKnife.findById(view, R.id.task_name);
        builder.setView(view);
        builder.setPositiveButton(R.string.action_add, context);
        return builder.create();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
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
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE && taskNameView != null) {
            Task task = new Task();
            task.setName(taskNameView.getText().toString());
            task.setStatus(Status.NEW);
            TasksRepository.getInstance().addTask(task);
            TasksRepository.getInstance().save(this);
        }
    }
}
