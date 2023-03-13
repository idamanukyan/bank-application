package com.iunetworks.util;


import com.iunetworks.util.exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;


@ControllerAdvice
public class ExceptionHandlerAdvice {

  final Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);


  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(ApplicationException ex) {
    log.error(ex.getMessage());
    return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), ex.status());
  }

  @ExceptionHandler({ResponseStatusException.class})
  public ResponseEntity<Object> handleResourceNotFoundException(ResponseStatusException ex) {
    log.error(ex.getMessage());
    return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
  }

/*  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(final RuntimeException ex) {
    log.error(ex.getMessage());
    return new ResponseEntity<>("Something went wrong.", new HttpHeaders(), 500);
  }*/

}
