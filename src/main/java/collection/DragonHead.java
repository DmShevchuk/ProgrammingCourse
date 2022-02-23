package collection;

import annotations.GreaterThan;

public class DragonHead {
    @GreaterThan
    private final long size;

    public DragonHead(long size){
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}