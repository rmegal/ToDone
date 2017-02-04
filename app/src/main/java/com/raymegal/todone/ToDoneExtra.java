package com.raymegal.todone;

import java.io.Serializable;

/**
 * Created by ray on 1/29/2017.
 */

public class ToDoneExtra implements Serializable {
    public int pos;
    public ToDoneTask task;

    public ToDoneExtra(int pos, ToDoneTask task) {
        this.pos = pos;
        this.task = task;
    }
}
