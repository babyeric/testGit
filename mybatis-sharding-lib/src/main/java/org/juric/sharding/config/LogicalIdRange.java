package org.juric.sharding.config;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 9/8/15
 * Time: 10:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class LogicalIdRange implements Comparable {
    private int low;
    private int high;

    public LogicalIdRange(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int getLow() {
        return this.low;
    }

    public int getHigh() {
        return this.high;
    }

    @Override
    public int compareTo(Object o) {
        if (o== null || !(o instanceof LogicalIdRange)) {
            throw new IllegalArgumentException();
        }

        if(high < ((LogicalIdRange) o).getLow()) {
            return -1;
        } else if (low > ((LogicalIdRange) o).getHigh()) {
            return 1;
        } else {
            return 0;
        }
    }
}
