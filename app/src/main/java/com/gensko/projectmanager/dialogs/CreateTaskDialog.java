package com.gensko.projectmanager.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.Task;

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
        builder.setView(view);
        builder.setPositiveButton(R.string.action_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE &&
                        !taskNameView.getText().toString().isEmpty()) {
                    Task task = new Task();
                    task.setName(taskNameView.getText().toString().trim());
                    listener.onTaskCreated(task);
                } else
                    dialogInterface.cancel();

                taskNameView.setText("");
            }
        });
        dialog = builder.create();
    }

    public void show() {
        dialog.show();
    }

    public static interface OnTaskCreatedListener {
        void onTaskCreated(Task task);
    }
}
