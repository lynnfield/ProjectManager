package com.gensko.projectmanager.repositories;

import android.content.Context;

import com.gensko.projectmanager.models.domain.Record;
import com.gensko.projectmanager.utils.ListLoader;
import com.gensko.projectmanager.utils.ListSaver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class RecordRepository<Model extends Record> extends Observable implements ListLoader.OnModelLoadedListener<Model> {
    private long id = 0l;

    private long nextId() {
        return ++id;
    }

    private List<Model> data = createNewList();

    public List<Model> getData() {
        return data;
    }

    public long add(Model model) {
        if (model.getId() == Record.NULL.getId())
            model.setId(nextId());
        data.add(model);
        setChanged();
        notifyObservers();
        return model.getId();
    }

    public boolean remove(Model model) {
        boolean ret = data.remove(model);
        setChanged();
        notifyObservers();
        return ret;
    }

    public boolean removeAll(List<Model> list) {
        return data.removeAll(list);
    }

    public Model findBy(Object object) {
        for (Model item : data)
            if (isEquals(item, object))
                return item;
        return null;
    }

    public List<Model> findAllBy(Object object) {
        ArrayList<Model> ret = new ArrayList<>();
        for (Model item : data)
            if (isEquals(item, object))
                ret.add(item);
        return ret;
    }

    public void save() {
        ListSaver<Model> saver = createNewListSaver();
        save(saver, data.toArray(createNewArray(data.size())));
    }

    public void load() {
        ListLoader<Model> loader = createNewListLoader();
        loader.setModelLoadedListener(this);
        load(loader);
    }

    protected abstract boolean isEquals(Model item, Object object);

    protected abstract List<Model> createNewList();
    protected abstract Model[] createNewArray(int size);

    protected abstract void save(ListSaver<Model> saver, Model[] data);
    protected abstract ListSaver<Model> createNewListSaver();

    protected abstract void load(ListLoader<Model> loader);
    protected abstract ListLoader<Model> createNewListLoader();

    @Override
    public void onModelLoaded(Model model) {
        if (model.getId() > id)
            id = model.getId();
        add(model);
    }
}
