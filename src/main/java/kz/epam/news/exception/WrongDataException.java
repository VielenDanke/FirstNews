package kz.epam.news.exception;

public class WrongDataException extends RuntimeException {

    public WrongDataException(String message) {
        super(message);
    }
}
