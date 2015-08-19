package com.gensko.projectmanager.utils;

import com.gensko.projectmanager.models.domain.TaskStateChange;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONObject;

public class TaskStateChangeListSaverTest extends TestCase {
    private TaskStateChangeListSaver saver = new TaskStateChangeListSaver();
    TaskStateChange change = new TaskStateChange();

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testCreateJsonFrom() throws Exception {
        JSONObject obj = saver.createJsonFrom(change);

        Assert.assertEquals(change.getId(), obj.getLong(TaskStateChange.ID_FIELD));
        Assert.assertEquals(change.getTaskId(), obj.getLong(TaskStateChange.TASK_ID_FIELD));
        Assert.assertEquals(change.getOldStatus().toString(), obj.getString(TaskStateChange.OLD_STATE_FIELD));
        Assert.assertEquals(change.getNewStatus().toString(), obj.getString(TaskStateChange.NEW_STATE_FIELD));
        Assert.assertEquals(change.getTime().toString(), obj.getString(TaskStateChange.TIME_FIELD));
    }
}