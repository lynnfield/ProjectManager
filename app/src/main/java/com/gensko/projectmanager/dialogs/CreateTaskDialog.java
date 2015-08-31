package com.gensko.projectmanager.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.SimpleTaskListAdapter;
import com.gensko.projectmanager.models.HierarchyTask;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.repositories.TaskRepository;

import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class CreateTaskDialog {
    private AlertDialog dialog;

    public CreateTaskDialog(final Context context, final OnTaskCreatedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.add_task);
        View view = LayoutInflater.from(context).inflate(R.layout.add_task, null);
        final EditText taskNameView = ButterKnife.findById(view, R.id.task_name);
        final Spinner parentTaskView = ButterKnife.findById(view, R.id.parent_task);
        parentTaskView.setAdapter(
                new SimpleTaskListAdapter(context, TaskRepository.getInstance().getData()));
        builder.setView(view);
        builder.setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE &&
                        !taskNameView.getText().toString().isEmpty()) {
                    HierarchyTask task = new HierarchyTask();
                    task.setName(taskNameView.getText().toString().trim());
                    task.setParentId(parentTaskView.getSelectedItemId());
                    task.setParent((HierarchyTask) parentTaskView.getSelectedItem());
                    listener.onTaskCreated(task);
                } else
                    dialogInterface.cancel();

                taskNameView.setText("");
                parentTaskView.setSelection(0);
            }
        });
        dialog = builder.create();
    }

    public void show() {
        dialog.show();
    }

    public interface OnTaskCreatedListener {
        void onTaskCreated(Task task);
    }
}
