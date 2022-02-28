package collection;

public enum DragonType {
    WATER("WATER"),
    UNDERGROUND("UNDERGROUND"),
    AIR("AIR"),
    FIRE("FIRE");

    private final String stringEq;

    DragonType(String stringEq){
        this.stringEq = stringEq;
    }

    @Override
    public String toString(){
        return stringEq;
    }
}
