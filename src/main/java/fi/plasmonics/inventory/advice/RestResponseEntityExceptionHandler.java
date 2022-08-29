package fi.plasmonics.inventory.advice;


import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "The user does not have access to this resource", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ ExpiredJwtException.class })
    public ResponseEntity<Object> handleExpiredJwtException(
            Exception ex, WebRequest request) {
        logger.info("handling ExpiredJwtException");
        return new ResponseEntity<Object>(
                "The user is unauthorized because the authentication token has expired", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

}
