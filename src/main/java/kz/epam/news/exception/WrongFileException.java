package kz.epam.news.exception;

public class WrongFileException extends RuntimeException {

    public WrongFileException(String message) {
        super(message);
    }
}
