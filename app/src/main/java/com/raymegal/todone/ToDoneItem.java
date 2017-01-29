package com.raymegal.todone;

import java.io.Serializable;

/**
 * Created by ray on 1/29/2017.
 */

public class ToDoneItem implements Serializable {
    public int pos;
    public String text;

    public ToDoneItem(int pos, String text) {
        this.pos = pos;
        this.text = text;
    }
}
