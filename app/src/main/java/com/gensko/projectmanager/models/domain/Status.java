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
    },
    STARTED {
        @Override
        public int getStringResourceId() {
            return R.string.status_started;
        }
    },
    DONE {
        @Override
        public int getStringResourceId() {
            return R.string.status_done;
        }
    },
    NULL;

    public int getStringResourceId() {
        return R.string.empty;
    }

    public int getDrawableResourceId() {
        return R.mipmap.ic_launcher;
    }
}
