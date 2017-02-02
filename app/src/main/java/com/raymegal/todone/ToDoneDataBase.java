package com.raymegal.todone;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by ray on 1/31/2017.
 */

@Database(name = ToDoneDataBase.NAME, version = ToDoneDataBase.VERSION)
public class ToDoneDataBase {
    public static final String NAME = "ToDoneDataBase";

    public static final int VERSION = 1;
}
