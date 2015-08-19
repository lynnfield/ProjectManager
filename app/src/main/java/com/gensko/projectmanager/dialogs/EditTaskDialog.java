package com.gensko.projectmanager.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.StatusAdapter;
import com.gensko.projectmanager.models.domain.Status;
import com.gensko.projectmanager.models.domain.Task;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class EditTaskDialog {
    @Bind(R.id.task_name)
    EditText taskNameView;
    @Bind(R.id.task_status)
    Spinner taskStatusView;

    private AlertDialog dialog;
    private Task task;

    public EditTaskDialog(final Context context, final OnTaskEditedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.edit_task);

        View view = LayoutInflater.from(context).inflate(R.layout.edit_task, null);

        ButterKnife.bind(this, view);

        taskStatusView.setAdapter(new StatusAdapter(context));

        builder.setView(view);
        builder.setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE && isValid() && isEdited()) {
                    task.setName(taskNameView.getText().toString().trim());
                    task.setStatus((Status) taskStatusView.getSelectedItem());
                    dialogInterface.dismiss();
                    if (listener != null)
                        listener.onTaskEdited(task);
                } else
                    dialogInterface.cancel();
            }
        });
        dialog = builder.create();
    }

    public EditTaskDialog setTask(Task task) {
        this.task = task;
        taskNameView.setText(task.getName());
        taskStatusView.setSelection(task.getStatus().ordinal());
        return this;
    }

    public void show() {
        dialog.show();
    }

    private boolean isValid() {
        return !taskNameView.getText().toString().trim().isEmpty() &&
               !Status.NULL.equals(taskStatusView.getSelectedItem());
    }

    private boolean isEdited() {
        return !task.getName().equals(taskNameView.getText().toString()) ||
                !task.getStatus().equals(taskStatusView.getSelectedItem());
    }

    public static interface OnTaskEditedListener {
        void onTaskEdited(Task task);
    }
}
