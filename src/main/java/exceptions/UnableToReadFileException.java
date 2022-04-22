package exceptions;

public class UnableToReadFileException extends FileReadException{
    public UnableToReadFileException(String message) {
        super(message);
    }
}
