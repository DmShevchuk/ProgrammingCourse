package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Указывает на то, что поле объекта {@link collection.Dragon}
 * должно иметь длину(default=1).
 *
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasLength{
    int minLength() default 1;
}