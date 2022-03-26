package data;

import collection.Coordinates;
import collection.DragonHead;
import collection.DragonType;

import java.util.HashSet;
import java.util.Set;

/**
 * Проверка полей класса {@link collection.Dragon}
 */

public class FieldChecker {
    private Set<Integer> idSet = new HashSet<>();

    public Pair<Boolean, Integer> checkId(Object obj) {
        try {
            Integer id = Integer.valueOf(String.valueOf(obj));
            if (!idSet.contains(id)) {
                idSet.add(id);
                return new Pair<>(id > 0, id);
            }
            throw new Exception();
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Поле name не может быть null, Строка не может быть пустой
     * {@link collection.Dragon}
     */
    public Pair<Boolean, String> checkName(Object obj) {
        try {
            String name = (String) obj;
            return new Pair<>(name.trim().length() > 0, name);
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Поле не может быть null
     * X - значение поля должно быть больше -972, Поле не может быть null
     * Y - значение поля должно быть больше -290, Поле не может быть null
     * {@link collection.Dragon}
     * {@link collection.Coordinates}
     */
    public Pair<Boolean, Coordinates> checkCoordinates(Object obj) {
        try {
            String coords = (String) obj;
            Double X = Double.parseDouble(coords.split("\\s")[0]);
            Double Y = Double.parseDouble(coords.split("\\s")[1]);
            return new Pair<>(X > -972 && Y > -290, new Coordinates(X, Y));
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Значение поля должно быть больше 0, поле может быть null
     */
    public Pair<Boolean, Integer> checkAge(Object obj) {
        if (obj == null) {
            return new Pair<>(true, null);
        }
        try {
            Integer age = Integer.valueOf(String.valueOf(obj));
            return new Pair<>(age > 0, age);
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Значение поля должно быть больше 0, поле не может быть null
     */
    public Pair<Boolean, Long> checkWeight(Object obj) {
        try {
            Long weight = Long.valueOf(String.valueOf(obj));
            return new Pair<>(weight > 0, weight);
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Поле не может быть null
     */
    public Pair<Boolean, Boolean> checkSpeaking(Object obj) {
        try {
            String boolS = String.valueOf(obj).toLowerCase();

            if (boolS.equals("true")) return new Pair<>(true, Boolean.TRUE);
            if (boolS.equals("false")) return new Pair<>(true, Boolean.FALSE);

            throw new Exception();
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Поле может быть null
     */
    public Pair<Boolean, DragonType> checkType(Object obj) {
        try {
            String sType = String.valueOf(obj).toUpperCase();
            DragonType type = DragonType.getTypeByString(sType);
            return new Pair<>(type != null, type);
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }

    /**
     * Поле не может быть null
     */
    public Pair<Boolean, DragonHead> checkHead(Object obj) {
        if (obj == null) return new Pair<>(false, null);

        if (obj.getClass() == DragonHead.class) {
            obj = ((DragonHead) obj).getSize();
        }

        try {
            Long head = Long.valueOf(String.valueOf(obj));
            return new Pair<>(head > 0, new DragonHead(head));
        } catch (Exception e) {
            return new Pair<>(false, null);
        }
    }
}
