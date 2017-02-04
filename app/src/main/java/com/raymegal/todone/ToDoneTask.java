package com.raymegal.todone;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by ray on 1/31/2017.
 */

@Table(database = ToDoneDataBase.class)
public class ToDoneTask extends BaseModel implements Serializable {
    @Column
    @PrimaryKey (autoincrement = true)
    int id;

    @Column
    String name;

    @Column
    String priority;

    public ToDoneTask() {
    }

    public ToDoneTask(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPriority(String priority) { this.priority = priority; }
}
