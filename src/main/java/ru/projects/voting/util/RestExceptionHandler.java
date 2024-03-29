package ru.projects.voting.util;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Класс обработчика исключений.
 */

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  public RestExceptionHandler() {
    super();
  }

  @ExceptionHandler({NotFoundException.class
  })
  protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,
        request);
  }

  @ExceptionHandler({IllegalArgumentException.class
  })
  protected ResponseEntity<Object> handleVoting(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.LOCKED,
        request);
  }

  @ExceptionHandler({
      ConstraintViolationException.class,
      DataIntegrityViolationException.class
  })
  public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, ex
        .getLocalizedMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler({IllegalRequestDataException.class
  })
  protected ResponseEntity<Object> handleRequestDataError(Exception ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(),
        HttpStatus.UNPROCESSABLE_ENTITY, request);
  }

}
