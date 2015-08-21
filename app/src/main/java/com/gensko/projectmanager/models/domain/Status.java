package com.gensko.projectmanager.models.domain;

import com.gensko.projectmanager.R;

/**
 * Created by Lynnfield on 15.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public enum Status {
    NEW {
        @Override
        public int getStringResourceId() {
            return R.string.status_new;
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
        public boolean isEnd() {
            return false;
        }
    },
    STARTED {
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
        public boolean isEnd() {
            return false;
        }
    },
    PAUSED {
        @Override
        public int getStringResourceId() {
            return R.string.status_paused;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_returned_black_48dp;
        }

        @Override
        public boolean isBegin() {
            return false;
        }

        @Override
        public boolean isEnd() {
            return true;
        }
    },
    DONE {
        @Override
        public int getStringResourceId() {
            return R.string.status_done;
        }

        @Override
        public int getDrawableResourceId() {
            return R.drawable.ic_assignment_turned_in_black_48dp;
        }

        @Override
        public boolean isBegin() {
            return false;
        }

        @Override
        public boolean isEnd() {
            return true;
        }
    },
    NULL {
        @Override
        public int getStringResourceId() {
            return R.string.empty;
        }

        @Override
        public int getDrawableResourceId() {
            return R.mipmap.ic_launcher;
        }

        @Override
        public boolean isBegin() {
            return false;
        }

        @Override
        public boolean isEnd() {
            return false;
        }
    };

    public abstract int getStringResourceId();
    public abstract int getDrawableResourceId();
    public abstract boolean isBegin();
    public abstract boolean isEnd();
}
