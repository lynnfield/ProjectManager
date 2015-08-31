package com.gensko.projectmanager.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genovich V.V. on 27.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class HierarchyTask extends TimedTask {
    private static final String PARENT_ID_FIELD = "ParentId";
    private Long parentId;
    private HierarchyTask parent;
    private List<HierarchyTask> children = new ArrayList<>();

    public HierarchyTask(long parentId) {
        this.parentId = parentId;
    }

    public HierarchyTask(Task task, long parentId) {
        super(task);
        this.parentId = parentId;
    }

    public HierarchyTask() {}

    public long getParentId() {
        if (parentId == null)
            return 0l;
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public HierarchyTask getParent() {
        return parent;
    }

    public void setParent(HierarchyTask parent) {
        this.parent = parent;
        if (parent != null)
            parentId = parent.getId();
        else
            parentId = 0l;
    }

    public List<HierarchyTask> getChildren() {
        return children;
    }

    public void addChild(HierarchyTask child) {
        children.add(child);
    }

    @Override
    public void fillFrom(JSONObject obj) throws JSONException {
        super.fillFrom(obj);
        parentId = obj.optLong(PARENT_ID_FIELD);
    }

    @Override
    public JSONObject toJson() throws JSONException {
        return super.toJson()
                .put(PARENT_ID_FIELD, parentId);
    }

    public boolean hasParent() {
        return parentId > 0;
    }
}
