package data;

import collection.Coordinates;
import collection.DragonHead;
import collection.DragonType;

/**
 * Передаваемые в методы аргументы валидны и нуждаются только в приведении к типу
 * соответствующего поля {@link collection.Dragon}
 * */

public class FieldConverter {
    public static String parseName(Object obj) {
        return (String) obj;
    }

    public static Coordinates parseCoordinate(Object x, Object y) {
        Double X = (Double) x;
        Double Y = (Double) y;
        return new Coordinates(X, Y);
    }

    public static Integer parseAge(Object obj) {
        Long val = (Long) obj;
        return (Integer) val.intValue();
    }

    public static Long parseWeight(Object obj) {
        return (Long) obj;
    }

    public static Boolean parseSpeaking(Object obj){
        return (Boolean) obj;
    }

    public static DragonType parseType(Object obj) {
        // Получение типа дракона по его строковому представлению
        return DragonType.valueOf((String) obj);
    }

    public static DragonHead parseHead(Object obj) {
        long H = (long) obj;
        return new DragonHead(H);
    }
}
