package com.gensko.framework.utils;

import java.util.Calendar;

/**
 * Created by Genovich V.V. on 20.08.2015.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Utils {
    public static int compare(Calendar first, Calendar second, int field) {
        int ret = first.get(field) - second.get(field);
        if (ret > 1) ret = 1;
        else if (ret < -1) ret = -1;
        return ret;
    }

    public static boolean equals(Calendar first, Calendar second, int field) {
        return compare(first, second, field) == 0;
    }
}
