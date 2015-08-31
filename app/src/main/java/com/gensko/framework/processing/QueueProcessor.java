package com.gensko.framework.processing;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Genovich V.V. on 27.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class QueueProcessor<Model> extends Thread {
    private final Queue<Model> queue = new LinkedList<>();
    private boolean halt;

    public void halt() {
        halt = true;
    }

    public void process(Model model) {
        synchronized (queue) {
            queue.offer(model);
        }
    }

    @Override
    public void run() {
        while (!halt) {
            if (hasDataToProcess())
                processNextData();
            else
                pause();
        }
    }

    private boolean hasDataToProcess() {
        synchronized (queue) {
            return !queue.isEmpty();
        }
    }

    private void processNextData() {
        Model model;
        synchronized (queue) {
            model = queue.poll();
        }
        processData(model);
    }

    protected abstract void processData(Model model);

    private void pause() {
        try {
            sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    public void process(List<Model> data) {
        synchronized (queue) {
            queue.addAll(data);
        }
    }
}
