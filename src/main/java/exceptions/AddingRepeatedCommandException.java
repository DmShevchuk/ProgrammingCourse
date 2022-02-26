package exceptions;

import java.io.IOException;

public class AddingRepeatedCommandException extends Exception {
    public AddingRepeatedCommandException(String message){
        super(message);
    }
}
