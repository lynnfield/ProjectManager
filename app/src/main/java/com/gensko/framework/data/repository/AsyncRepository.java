package com.gensko.framework.data.repository;

import android.os.Handler;
import android.os.Looper;

import com.gensko.framework.utils.RunnableProcessor;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Genovich V.V. on 31.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class AsyncRepository<Model> implements Repository<Model> {
    private RunnableProcessor processor = new RunnableProcessor();
    private Repository<Model> repository;

    public AsyncRepository(Repository<Model> repository) {
        this.repository = repository;
        processor.start();
    }

    @Override
    public void add(Model model, Callback<Model> callback) {
        processor.process(new AddTask<>(repository, model, new SyncCallback<>(callback)));
    }

    @Override
    public void remove(Model model, Callback<Model> callback) {
        processor.process(new RemoveTask<>(repository, model, new SyncCallback<>(callback)));
    }

    @Override
    public void replace(ReplaceData<Model> input, Callback<ReplaceData<Model>> callback) {
        processor.process(new ReplaceTask<>(repository, input, new SyncCallback<>(callback)));
    }

    @Override
    public void find(Condition<Model> condition, Callback<Model> callback) {
        processor.process(new FindTask<>(repository, condition, new SyncCallback<>(callback)));
    }

    @Override
    public void filter(Condition<Model> condition, Callback<List<Model>> callback) {
        processor.process(new FilterTask<>(repository, condition, new SyncCallback<>(callback)));
    }

    @Override
    public void sort(Comparator<Model> comparator, Callback<List<Model>> callback) {
        processor.process(new SortTask<>(repository, comparator, callback));
    }

    @Override
    public void getData(Interval interval, Callback<List<Model>> callback) {
        processor.process(new GetDataTask<>(repository, interval, new SyncCallback<>(callback)));
    }

    @Override
    public void save(Callback<String> callback) {
        processor.process(new SaveTask<>(repository, new SyncCallback<>(callback)));
    }

    private static class SyncCallback<T> implements Callback<T> {
        private static final Handler handler = new Handler(Looper.getMainLooper());
        private Callback<T> callback;

        private SyncCallback(Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onDone(final T result) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onDone(result);
                }
            });
        }

        @Override
        public void onFail(final Exception e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onFail(e);
                }
            });
        }
    }

    private static class AddTask<T> implements Runnable {
        private Repository<T> repository;
        private T model;
        private Callback<T> callback;

        private AddTask(Repository<T> repository, T model, Callback<T> callback) {
            this.repository = repository;
            this.model = model;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.add(model, callback);
        }
    }

    private static class RemoveTask<T> implements Runnable {
        private Repository<T> repository;
        private T model;
        private Callback<T> callback;

        public RemoveTask(Repository<T> repository, T model, Callback<T> callback) {
            this.repository = repository;
            this.model = model;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.remove(model, callback);
        }
    }

    private static class ReplaceTask<T> implements Runnable {
        private Repository<T> repository;
        private ReplaceData<T> input;
        private Callback<ReplaceData<T>> callback;

        private ReplaceTask(Repository<T> repository, ReplaceData<T> model, Callback<ReplaceData<T>> callback) {
            this.repository = repository;
            this.input = model;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.replace(input, callback);
        }
    }

    private static class FindTask<T> implements Runnable {
        private Repository<T> repository;
        private Condition<T> condition;
        private Callback<T> callback;

        private FindTask(Repository<T> repository, Condition<T> condition, Callback<T> callback) {
            this.repository = repository;
            this.condition = condition;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.find(condition, callback);
        }
    }

    private static class FilterTask<T> implements Runnable {
        private Repository<T> repository;
        private Condition<T> condition;
        private Callback<List<T>> callback;

        private FilterTask(Repository<T> repository, Condition<T> condition, Callback<List<T>> callback) {
            this.repository = repository;
            this.condition = condition;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.filter(condition, callback);
        }
    }

    private static class SortTask<T> implements Runnable {
        private Repository<T> repository;
        private Comparator<T> comparator;
        private Callback<List<T>> callback;

        private SortTask(Repository<T> repository, Comparator<T> comparator, Callback<List<T>> callback) {
            this.repository = repository;
            this.comparator = comparator;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.sort(comparator, callback);
        }
    }

    private static class GetDataTask<T> implements Runnable {
        private Repository<T> repository;
        private Interval interval;
        private Callback<List<T>> callback;

        private GetDataTask(Repository<T> repository, Interval interval, Callback<List<T>> callback) {
            this.repository = repository;
            this.interval = interval;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.getData(interval, callback);
        }
    }

    private static class SaveTask<T> implements Runnable {
        private Repository<T> repository;
        private Callback<String> callback;

        private SaveTask(Repository<T> repository, Callback<String> callback) {
            this.repository = repository;
            this.callback = callback;
        }

        @Override
        public void run() {
            repository.save(callback);
        }
    }
}
