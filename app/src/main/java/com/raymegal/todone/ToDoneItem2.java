package com.raymegal.todone;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by ray on 1/31/2017.
 */

@Table(database = ToDoneDataBase.class)
public class ToDoneItem2 extends BaseModel {
    @Column
    @PrimaryKey (autoincrement = true)
    int id;

    @Column
    String text;

    public ToDoneItem2() {
    }

    public ToDoneItem2(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
