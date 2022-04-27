package collection;

import java.io.Serializable;

/**
 * Голова является полем класса {@link collection.Dragon}
 */
public class DragonHead implements Serializable {
    private final long size;

    public DragonHead(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}