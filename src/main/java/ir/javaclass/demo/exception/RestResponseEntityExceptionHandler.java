package ir.javaclass.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(
            Exception ex, WebRequest request) {
        if (ex instanceof DataNotFoundException) {
            return new ResponseEntity<Object>(
                    ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Object>(
                    ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT);
        }
    }
}
