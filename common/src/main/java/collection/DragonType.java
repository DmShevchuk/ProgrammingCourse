package collection;

import java.io.Serializable;

/**
 * Каждый объект класса {@link collection.Dragon} имеет свой тип (WATER, UNDERGROUND, AIR, FIRE)
 * */
public enum DragonType implements Serializable {
    WATER("WATER"),
    UNDERGROUND("UNDERGROUND"),
    AIR("AIR"),
    FIRE("FIRE");

    private final String stringEq;

    DragonType(String stringEq){
        this.stringEq = stringEq;
    }

    public static DragonType getTypeByString(String title){
        return switch (title) {
            case ("WATER") -> DragonType.WATER;
            case ("UNDERGROUND") -> DragonType.UNDERGROUND;
            case ("AIR") -> DragonType.AIR;
            case ("FIRE") -> DragonType.FIRE;
            default -> null;
        };
    }

    @Override
    public String toString(){
        return stringEq;
    }
}
