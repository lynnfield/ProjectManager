package com.gensko.projectmanager.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.gensko.projectmanager.R;
import com.gensko.projectmanager.adapters.SimpleTaskListAdapter;
import com.gensko.projectmanager.models.HierarchyTask;
import com.gensko.projectmanager.models.State;
import com.gensko.projectmanager.models.Task;
import com.gensko.projectmanager.repositories.TaskRepository;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by Genovich V.V. on 18.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class EditTaskDialog {
    @Bind(R.id.task_name)
    EditText taskNameView;
    @Bind(R.id.task_parent)
    Spinner taskParentView;
    @Bind(R.id.action_start)
    Button actionStart;
    @Bind(R.id.action_pause)
    Button actionPause;
    @Bind(R.id.action_done)
    Button actionDone;

    private final OnTaskEditedListener listener;

    private AlertDialog dialog;
    private HierarchyTask task;
    private State old;

    public EditTaskDialog(final Context context, final OnTaskEditedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.edit_task);

        View view = LayoutInflater.from(context).inflate(R.layout.edit_task, null);

        ButterKnife.bind(this, view);
        taskParentView.setAdapter(
                new SimpleTaskListAdapter(context, TaskRepository.getInstance().getData()));

        builder.setView(view);
        this.listener = listener;
        dialog = builder.create();
    }

    public EditTaskDialog setTask(HierarchyTask task) {
        this.task = task;
        this.old = task.getState();
        taskNameView.setText(task.getName());
        actionStart.setVisibility(task.getState().isStartAvailable() ? View.VISIBLE : View.GONE);
        actionPause.setVisibility(task.getState().isPauseAvailable() ? View.VISIBLE : View.GONE);
        actionDone.setVisibility(task.getState().isDoneAvailable() ? View.VISIBLE : View.GONE);
        return this;
    }

    @OnItemSelected(R.id.task_parent)
    void onItemSelected(int position) {
        HierarchyTask task = (HierarchyTask) taskParentView.getAdapter().getItem(position);
        this.task.setParent(task);
    }

    @OnItemSelected(value = R.id.task_parent, callback = OnItemSelected.Callback.NOTHING_SELECTED)
    void onNothingSelected() {
        task.setParent(null);
    }

    @OnClick(R.id.action_start)
    void onStart() {
        task.start();
        close();
    }

    @OnClick(R.id.action_pause)
    void onPause() {
        task.pause();
        close();
    }

    @OnClick(R.id.action_done)
    void onDone() {
        task.done();
        close();
    }

    public void show() {
        dialog.show();
    }

    private boolean isValid() {
        return !taskNameView.getText().toString().trim().isEmpty();
    }

    private boolean isEdited() {
        return !task.getName().equals(taskNameView.getText().toString()) ||
                !old.equals(task.getState());
    }

    private void close() {
        if (isValid() && isEdited()) {
            task.setName(taskNameView.getText().toString().trim());
            dialog.dismiss();
            if (listener != null)
                listener.onTaskEdited(task);
            return;
        }
        dialog.cancel();
    }

    public interface OnTaskEditedListener {
        void onTaskEdited(Task task);
    }
}
