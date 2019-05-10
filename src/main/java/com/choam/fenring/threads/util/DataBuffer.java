package com.choam.fenring.threads.util;

import java.util.ArrayList;
import java.util.List;

public class DataBuffer {

    private final static int MAX_ITEMS = 10;

    private final List<String> items = new ArrayList<>();

    public DataBuffer() {}

    public DataBuffer(String prefix) {
        for (int i = 0; i < MAX_ITEMS; i++) {
            String item = prefix + i;
            System.out.printf("Adding %s%n", item);
            items.add(item);
        }
    }

    public synchronized boolean emptyEh() {
        return items.size() == 0;
    }

    public synchronized boolean fullEh() {
        return items.size() == MAX_ITEMS;
    }

    public synchronized void add(String s) {
        if (!fullEh()) {
            items.add(s);
        }
    }

    public synchronized String remove() {
        if (!emptyEh()) {
            return items.remove(0);
        }

        return null;
    }
}
