package data;

import annotations.GreaterThan;
import annotations.HasLength;
import annotations.NotNull;
import collection.Coordinates;
import collection.DragonHead;
import collection.DragonType;

import java.lang.reflect.Field;

public class FieldChecker {
    public static Pair<Boolean, ?> checkName(Pair pair) {

        Field field = (Field) pair.getFirst();
        Object obj = pair.getSecond();

        if (field.isAnnotationPresent(NotNull.class) && obj == null) {
            return new Pair<>(false, null);
        }

        if (obj.getClass() != String.class || (field.isAnnotationPresent(HasLength.class)
                && ((String) obj).length() < field.getAnnotation(HasLength.class).minLength())) {
            return new Pair<>(false, obj);
        }

        String value = (String) obj;

        return new Pair<>(true, value);
    }

    public static Pair<Boolean, ?> checkCoordinates(Pair pair) throws NoSuchFieldException {

        Field field = (Field) pair.getFirst();
        Object[] coords = (Object[]) pair.getSecond();

        Object x = coords[0];
        Object y = coords[1];

        if (field.isAnnotationPresent(NotNull.class) && (x == null || y == null)) {
            return new Pair<>(false, coords);
        }

        Double X;
        Double Y;

        if (x.getClass() == String.class && y.getClass() == String.class) {
            try {
                X = Double.parseDouble((String) x);
                Y = Double.parseDouble((String) y);
            } catch (ClassCastException | NumberFormatException e) {
                return new Pair<>(false, coords);
            }
        } else {
            try {
                X = (Double) x;
                Y = (Double) y;
            } catch (ClassCastException | NumberFormatException e) {
                return new Pair<>(false, coords);
            }
        }

        boolean answerX = true;
        boolean answerY = true;
        if (field.isAnnotationPresent(GreaterThan.class)) {
            answerX = X > (Double) Coordinates.class.getDeclaredField("x")
                    .getAnnotation(GreaterThan.class).minValue();
            answerY = Y > (Double) Coordinates.class.getDeclaredField("y")
                    .getAnnotation(GreaterThan.class).minValue();
        }

        return new Pair<>(answerX && answerY, new Coordinates(X, Y));

    }

    public static Pair<Boolean, ?> checkAge(Pair pair) {

        Field field = (Field) pair.getFirst();
        Object obj = pair.getSecond();

        if (obj == null) {
            return new Pair<>(true, null);
        }

        Long age;

        if (obj.getClass() == String.class) {
            try {
                age = Long.parseLong((String) obj);
            } catch (ClassCastException | NumberFormatException e) {
                return new Pair<>(false, obj);
            }
        } else {
            try {
                age = (Long) obj;
            } catch (ClassCastException e) {
                return new Pair<>(false, obj);
            }
        }

        boolean answer = true;

        if (field.isAnnotationPresent(GreaterThan.class)) {
            answer = age > field.getAnnotation(GreaterThan.class).minValue();
        }

        return new Pair<>(answer, (Integer) age.intValue());
    }

    public static Pair<Boolean, ?> checkWeight(Pair pair) {
        Field field = (Field) pair.getFirst();
        Object obj = pair.getSecond();

        if (field.isAnnotationPresent(NotNull.class) && obj == null) {
            return new Pair<>(false, null);
        }

        Long weight;

        if (obj.getClass() == String.class) {
            try {
                weight = Long.parseLong((String) obj);
            } catch (ClassCastException | NumberFormatException e) {
                return new Pair<>(false, obj);
            }
        } else {
            try {
                weight = (Long) obj;
            } catch (ClassCastException e) {
                return new Pair<>(false, obj);
            }
        }

        boolean answer = true;

        if (field.isAnnotationPresent(GreaterThan.class)) {
            answer = weight > field.getAnnotation(GreaterThan.class).minValue();
        }

        return new Pair<>(answer, weight);
    }

    public static Pair<Boolean, ?> checkSpeaking(Pair pair) {
        Field field = (Field) pair.getFirst();
        Object obj = pair.getSecond();

        if (field.isAnnotationPresent(NotNull.class) && obj == null) {
            return new Pair<>(false, null);
        }

        if (obj.getClass() == String.class && (obj.equals("true") || obj.equals("false"))) {
            return new Pair<>(true, Boolean.parseBoolean((String) obj));
        } else if (obj.getClass() == String.class) {
            return new Pair<>(false, obj);
        }

        try {
            return new Pair<>(true, (Boolean) obj);
        } catch (ClassCastException e) {
            return new Pair<>(false, obj);
        }
    }

    public static Pair<Boolean, ?> checkType(Pair pair) {
        Field field = (Field) pair.getFirst();
        Object obj = pair.getSecond();

        if (obj == null) {
            return new Pair<>(true, null);
        }
        if (obj.getClass() == String.class) {
            String value = (String) obj;
            for (DragonType type : DragonType.values()) {
                if (type.toString().equals(value)) {
                    return new Pair<>(true, DragonType.valueOf(value));
                }
            }
            return new Pair<>(false, value);
        } else {
            return new Pair<>(false, obj);
        }
    }

    public static Pair<Boolean, ?> checkHead(Pair pair) {
        Field field = (Field) pair.getFirst();
        Object obj = pair.getSecond();

        if (field.isAnnotationPresent(NotNull.class) && obj == null) {
            return new Pair<>(false, null);
        }

        long head;

        if (obj.getClass() == String.class) {
            try {
                head = Long.parseLong((String) obj);
            } catch (ClassCastException | NumberFormatException e) {
                return new Pair<>(false, obj);
            }
        } else {
            try {
                head = (Long) obj;
            } catch (ClassCastException e) {
                return new Pair<>(false, obj);
            }
        }

        boolean answer = true;

        if (field.isAnnotationPresent(GreaterThan.class)) {
            answer = head > field.getAnnotation(GreaterThan.class).minValue();
        }

        return new Pair<>(answer, new DragonHead((long) head));
    }

}
