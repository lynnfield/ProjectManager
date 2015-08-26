package com.gensko.projectmanager.models;

import com.gensko.projectmanager.R;

/**
 * Created by Lynnfield on 15.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public abstract class State implements StateChanger {
    protected IState obj;

    protected State() {}

    public State(IState obj) {
        this.obj = obj;
    }

    public abstract int getStringResourceId();

    public abstract int getDrawableResourceId();

    public boolean isBegin() {
        return false;
    }

    public boolean isEnd() {
        return false;
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public boolean isStartAvailable() {
        return false;
    }

    @Override
    public boolean isPauseAvailable() {
        return false;
    }

    @Override
    public boolean isDoneAvailable() {
        return false;
    }

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        return o instanceof State && toString().equals(o.toString());
    }

    public static State valueOf(String name, IState iState) {
        if (name == null)
            return new Null(iState);
        else switch (name) {
            case Created.NAME:
            case "NEW": //for compatibility
                return new Created(iState);
            case Started.NAME:
                return new Started(iState);
            case Paused.NAME:
                return new Paused(iState);
            case Restarted.NAME:
                return new Restarted(iState);
            case Done.NAME:
                return new Done(iState);
            case Null.NAME:
            default:
                return new Null(iState);
        }
    }

    public static State valueOf(String name) {
        if (name == null)
            return new Null();
        else switch (name) {
            case Created.NAME:
            case "NEW": //for compatibility
                return new Created();
            case Started.NAME:
                return new Started();
            case Paused.NAME:
                return new Paused();
            case Restarted.NAME:
                return new Restarted();
            case Done.NAME:
                return new Done();
            case Null.NAME:
            default:
                return new Null();
        }
    }

    public abstract State copyFor(IState iState);

    static interface IState {
        void setState(State state);
    }

    public static class Null extends State {
        public static final String NAME = "NULL";

        public Null() {}

        public Null(IState obj) {
            super(obj);
        }

        @Override
        public int getStringResourceId() {
            return R.string.status_null;
        }

        @Override
        public int getDrawableResourceId() {
            return R.mipmap.ic_launcher;
        }

        @Override
        public String toString() {
            return NAME;
        }

        @Override
        public State copyFor(IState iState) {
            return new Null(iState);
        }
    }

    public static class Created extends State {
        public static final String NAME = "CREATED";

        public Created() {}

        public Created(IState obj) {
            super(obj);
        }

        @Override
        public int getStringResourceId() {
            return R.string.status_created;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_black_48dp;
        }

        @Override
        public boolean isBegin() {
            return true;
        }

        @Override
        public boolean start() {
            obj.setState(new Started(obj));
            return true;
        }

        @Override
        public boolean isStartAvailable() {
            return true;
        }

        @Override
        public String toString() {
            return NAME;
        }

        @Override
        public State copyFor(IState iState) {
            return new Created(iState);
        }
    }

    public static class Started extends State {
        public static final String NAME = "STARTED";

        public Started() {}

        public Started(IState obj) {
            super(obj);
        }

        @Override
        public int getStringResourceId() {
            return R.string.status_started;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_ind_black_48dp;
        }

        @Override
        public boolean isBegin() {
            return true;
        }

        @Override
        public boolean pause() {
            obj.setState(new Paused(obj));
            return true;
        }

        @Override
        public boolean done() {
            obj.setState(new Done(obj));
            return true;
        }

        @Override
        public boolean isPauseAvailable() {
            return true;
        }

        @Override
        public boolean isDoneAvailable() {
            return true;
        }

        @Override
        public String toString() {
            return NAME;
        }

        @Override
        public State copyFor(IState iState) {
            return new Started(iState);
        }
    }

    public static class Paused extends State {
        public static final String NAME = "PAUSED";

        public Paused() {}

        public Paused(IState obj) {
            super(obj);
        }

        @Override
        public int getStringResourceId() {
            return R.string.status_paused;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_returned_black_48dp;
        }

        @Override
        public boolean isEnd() {
            return true;
        }

        @Override
        public boolean start() {
            obj.setState(new Restarted(obj));
            return true;
        }

        @Override
        public boolean done() {
            obj.setState(new Done(obj));
            return true;
        }

        @Override
        public boolean isStartAvailable() {
            return true;
        }

        @Override
        public boolean isDoneAvailable() {
            return true;
        }

        @Override
        public String toString() {
            return NAME;
        }

        @Override
        public State copyFor(IState iState) {
            return new Paused(iState);
        }
    }

    public static class Restarted extends State {
        public static final String NAME = "RESTARTED";

        public Restarted() {}

        public Restarted(IState obj) {
            super(obj);
        }

        @Override
        public int getStringResourceId() {
            return R.string.status_restarted;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_ind_black_48dp;
        }

        @Override
        public boolean isBegin() {
            return true;
        }

        @Override
        public boolean pause() {
            obj.setState(new Paused(obj));
            return true;
        }

        @Override
        public boolean done() {
            obj.setState(new Done(obj));
            return true;
        }

        @Override
        public boolean isPauseAvailable() {
            return true;
        }

        @Override
        public boolean isDoneAvailable() {
            return true;
        }

        @Override
        public String toString() {
            return NAME;
        }

        @Override
        public State copyFor(IState iState) {
            return new Restarted(iState);
        }
    }

    public static class Done extends State {
        public static final String NAME = "DONE";

        public Done() {}

        public Done(IState obj) {
            super(obj);
        }

        @Override
        public int getStringResourceId() {
            return R.string.status_done;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_turned_in_black_48dp;
        }

        @Override
        public boolean isEnd() {
            return true;
        }

        @Override
        public String toString() {
            return NAME;
        }

        @Override
        public State copyFor(IState iState) {
            return new Done(iState);
        }
    }
}
