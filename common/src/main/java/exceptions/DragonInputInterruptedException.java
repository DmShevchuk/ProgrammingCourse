package exceptions;

import java.io.IOException;

public class DragonInputInterruptedException extends IOException {
    public DragonInputInterruptedException(String message) {
        super(message);
    }
}