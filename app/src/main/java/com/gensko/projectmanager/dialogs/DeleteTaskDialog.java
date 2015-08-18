package com.gensko.projectmanager.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.models.domain.Task;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class DeleteTaskDialog {
    private AlertDialog dialog;
    private Task task;

    public DeleteTaskDialog(final Context context, final OnDeleteTaskListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.delete_task);
        builder.setMessage(context.getString(R.string.delete_task_confirmation_message));

        builder.setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int button) {
                if (button == DialogInterface.BUTTON_POSITIVE && listener != null) {
                    dialogInterface.dismiss();
                    listener.onDeleteTask(task);
                } else
                    dialogInterface.cancel();
            }
        });

        dialog = builder.create();
    }

    public DeleteTaskDialog setTask(Task task) {
        this.task = task;
        return this;
    }

    public void show() {
        dialog.show();
    }

    public static interface OnDeleteTaskListener {
       void onDeleteTask(Task task);
    }
}
