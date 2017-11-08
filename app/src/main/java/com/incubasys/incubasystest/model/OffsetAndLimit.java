package com.incubasys.incubasystest.model;

/**
 * Created by _red_ on 08/11/2017.
 */

public class OffsetAndLimit {
    private int offset;
    private int limit;

    public OffsetAndLimit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "OffsetAndLimit{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
