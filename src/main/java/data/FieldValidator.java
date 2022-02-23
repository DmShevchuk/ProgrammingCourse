package data;

import annotations.*;
import collection.DragonType;

import java.lang.reflect.Field;

public class FieldValidator {


    public static boolean checkField(Field field, Object value) {

        if (field.isAnnotationPresent(NotNull.class) && value == null) {
            return false;
        }

        if (!field.isAnnotationPresent(NotNull.class) && value == null) {
            return true;
        }

        if (field.isAnnotationPresent(HasLength.class) &&
                ((String) value).length() >= field.getAnnotation(HasLength.class).minLength()) {
            return true;
        }

        if (field.isAnnotationPresent(GreaterThan.class)) {
            double minValue = field.getAnnotation(GreaterThan.class).minValue();

            if (Number.class.isAssignableFrom(field.getType())) {
                return ((Number) value).doubleValue() >= minValue;
            }
            try {
                return (double) value >= minValue;
            } catch (Exception e) {
                return (long) value >= (long) minValue;
            }

        }

        if (field.isAnnotationPresent(EnumType.class)) {
            try {
                DragonType.valueOf((String) value);
                return true;
            } catch (Exception e) {
                System.out.println("111");
            }
        }

        if (field.getType() == Boolean.class) {
            try {
                Boolean bool = (Boolean) value;
                return true;
            } catch (Exception e) {
                System.out.println("");
            }
        }

        return false;
    }
}
