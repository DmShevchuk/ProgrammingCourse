package exceptions;

import java.sql.SQLException;

public class IncorrectLoginDataException extends SQLException {
    public IncorrectLoginDataException(String message){
        super(message);
    }
}
