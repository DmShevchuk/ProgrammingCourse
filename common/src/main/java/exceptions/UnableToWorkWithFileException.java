package exceptions;

public class UnableToWorkWithFileException extends FileReadException{
    public UnableToWorkWithFileException(String message) {
        super(message);
    }
}
