package ir.javaclass.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(
            Exception ex, WebRequest request) {
        if (ex instanceof DataNotFoundException) {
            return new ResponseEntity<Object>(
                    ex.getMessage(), new HttpHeaders(), HttpStatus.MOVED_PERMANENTLY);
        } else if (ex instanceof MethodArgumentNotValidException) {
            return new ResponseEntity<Object>(
                    handleValidationExceptions((MethodArgumentNotValidException) ex), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Object>(
                    ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
