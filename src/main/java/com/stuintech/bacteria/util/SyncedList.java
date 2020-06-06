package com.stuintech.bacteria.util;

import java.util.ArrayList;

public class SyncedList<T> extends ArrayList<T> {
    private int next = -1;
    private T none;

    public SyncedList(T none) {
        this.none = none;
    }

    public int addNext(T o) {
        //Add new item
        int out = next + 1;
        if(next > -1) {
            set(next, o);
        } else {
            //Locate best item location
            next = 0;
            while(next < size() && get(next) != none)
                next++;
            out = next + 1;

            //Set new timer
            if(next >= size()) {
                add(o);
                out = size();
            } else
                set(next, o);
        }
        next = -1;
        return out;
    }

    public boolean has(int i) {
        return i >= 0 && i < size() && get(i) != none;
    }

    public void clear(int i) {
        set(i, none);
        if(next < i)
            next = i;
    }
}
