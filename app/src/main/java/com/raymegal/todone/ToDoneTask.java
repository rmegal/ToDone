package com.raymegal.todone;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.Date;

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
    int priority;

    @Column
    Date dueDate;

    @Column
    Boolean done;

    @Column
    String notes;

    public ToDoneTask() {
    }

    public ToDoneTask(String name) {
        this.name = name;
    }

    public ToDoneTask(String name, Date dueDate, int priority) {
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
        this.done = false;
        this.notes = "";
    }

    public void setName(String name) {
        this.name = name;
    }
}
