package com.gensko.projectmanager.repositories;

import com.gensko.projectmanager.models.Record;
import com.gensko.projectmanager.utils.AsyncObservable;
import com.gensko.framework.utils.ListLoader;
import com.gensko.framework.utils.ListSaver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genovich V.V. on 19.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class RecordRepository<Model extends Record>
        extends AsyncObservable
        implements ListLoader.OnLoaderEventsListener<Model> {
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
        notifyObservers(new Notification<>(Notification.State.ADD, model));
        return model.getId();
    }

    public void replace(Model old, Model model) {
        int position = data.indexOf(old);
        data.remove(position);
        data.add(position, model);
        notifyObservers(new Notification<>(Notification.State.REPLACE, old, model));
    }

    public boolean remove(Model model) {
        boolean ret = data.remove(model);
        notifyObservers(new Notification<>(Notification.State.REMOVE, model));
        return ret;
    }

    public boolean removeAll(List<Model> list) {
        boolean ret = data.removeAll(list);
        if (ret) notifyObservers(new Notification<>(Notification.State.REMOVE_SUBLIST));
        return ret;
    }

    public Model findBy(Object object) {
        for (Model item : data)
            if (isSame(item, object))
                return item;
        return null;
    }

    public List<Model> findAllBy(Object object) {
        ArrayList<Model> ret = new ArrayList<>();
        for (Model item : data)
            if (isSame(item, object))
                ret.add(item);
        return ret;
    }

    public void save(ListSaver.OnListSaverEventsListener<Model> listener) {
        ListSaver<Model> saver = createNewListSaver();
        save(saver, data, listener);
    }

    public void load() {
        ListLoader<Model> loader = createNewListLoader();
        load(loader, this);
    }

    protected abstract boolean isSame(Model item, Object object);
    protected abstract List<Model> createNewList();

    protected abstract ListSaver<Model> createNewListSaver();
    protected abstract void save(ListSaver<Model> saver, List<Model> data, ListSaver.OnListSaverEventsListener<Model> listener);

    protected abstract ListLoader<Model> createNewListLoader();
    protected abstract void load(ListLoader<Model> loader, ListLoader.OnLoaderEventsListener<Model> listener);

    @Override
    public void onModelLoaded(Model model) {
        if (model.getId() > id)
            id = model.getId();
        add(model);
    }

    @Override
    public void onDone() {
        notifyObservers(new Notification<>(Notification.State.LOADED));
    }

    @Override
    public void onError(String error) {
        notifyObservers(new Notification<>(Notification.State.LOADING_ERROR, error));
    }

    public int size() {
        return data.size();
    }

    public Model get(int i) {
        return data.get(i);
    }

    public static class Notification<Model extends Record> {
        public static enum State {
            ADD, REMOVE, REMOVE_SUBLIST, LOADING_ERROR, REPLACE, LOADED
        }

        private State state;
        private Model old;
        private Model data;
        private String message;

        public Notification(State state) {
            this.state = state;
        }

        public Notification(State state, Model old, Model data) {
            this.state = state;
            this.old = old;
            this.data = data;
        }

        public Notification(State state, Model data) {
            this.state = state;
            this.data = data;
        }

        public Notification(State state, String message) {
            this.state = state;
            this.message = message;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public Model getOld() {
            return old;
        }

        public void setOld(Model old) {
            this.old = old;
        }

        public Model getData() {
            return data;
        }

        public void setData(Model data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
