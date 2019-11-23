package kz.epam.news.exception;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class WrongDataExceptionHandler {

    @Autowired
    private Logger logger;

    @ExceptionHandler(WrongDataException.class)
    public String handleWrongFileException(HttpServletRequest request, Exception ex) {

        logger.info("Exception is happened: " + ex.getMessage());

        request.getSession().setAttribute("error", ex.getMessage());

        return "error";
    }
}
