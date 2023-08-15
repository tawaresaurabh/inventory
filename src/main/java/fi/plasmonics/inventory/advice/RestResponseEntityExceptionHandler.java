package fi.plasmonics.inventory.advice;


import static fi.plasmonics.inventory.exceptions.ErrorType.MAX_COUNT_REACHED;
import static fi.plasmonics.inventory.exceptions.ErrorType.NOT_FOUND;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fi.plasmonics.inventory.exceptions.ApiError;
import fi.plasmonics.inventory.exceptions.ErrorType;
import fi.plasmonics.inventory.exceptions.InventoryException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private int stackLen = 30;


    @ExceptionHandler(value = {InventoryException.class})
    protected ResponseEntity<Object> handleInventoryException(InventoryException ex, WebRequest request) {
        var errorType = ex.getErrorType();
        if (errorType.equals(MAX_COUNT_REACHED)) {
            log.debug("Limitation verification failed. Details are {}", ex.getDetails());
        } else if (errorType.equals(NOT_FOUND)) {
            log.debug("not found happened . Details are {}", ex.getDetails());
        } else {
            log.warn("Error happened . Details are {}", ex.getDetails());
        }
        HttpStatus status = getHttpStatus(errorType);
        if (HttpStatus.NOT_FOUND.equals(status)) {
            final ApiError apiError = new ApiError(status, ex, 0);
            return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
        }
        final ApiError apiError = new ApiError(status, ex, stackLen);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
    }

    private HttpStatus getHttpStatus(ErrorType errorType) {
        switch (errorType) {
            case BAD_ARGUMENT:
                return HttpStatus.BAD_REQUEST;
            case EXISTING_DEPENDENCIES:
            case DUPLICATE:
                return HttpStatus.FORBIDDEN;
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case MAX_COUNT_REACHED:
            case UNDER_MAINTENANCE:
                return HttpStatus.CONFLICT;
            case CONVERT:
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
