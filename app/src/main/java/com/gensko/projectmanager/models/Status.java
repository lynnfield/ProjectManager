package com.gensko.projectmanager.models;

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
    };

    public abstract int getStringResourceId();
    public abstract int getDrawableResourceId();
}
