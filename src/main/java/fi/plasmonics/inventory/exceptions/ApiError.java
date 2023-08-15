package fi.plasmonics.inventory.exceptions;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ApiError {

    private String errorCode;
    private int httpCode;
    private String userMessage;
    private String developerMessage;
    private List<String> stackTrace;
    private Long timestamp;
    private @Nullable List<String> details;

    public ApiError(
        HttpStatus httpCode,
        InventoryException cause,
        int stackSize)
    {
        this(httpCode,
            cause.getMessage(),
            cause,
            stackSize,
            cause.getDetails());
    }

    private ApiError(
        HttpStatus httpCode,
        String userMessage,
        Throwable ex,
        int stackSize,
        @Nullable List<String> details)
    {
        this.httpCode = httpCode.value();
        this.userMessage = userMessage;
        this.stackTrace = getStackTrace(ex, stackSize);
        this.timestamp = System.currentTimeMillis();
        this.details = details;
    }

    private List<String> getStackTrace(Throwable ex, int stackSize) {
        List<String> list = Arrays.asList(ExceptionUtils.getRootCauseStackTrace(ex));
        int len = Math.min(list.size(), stackSize);
        return list.subList(0, len);
    }
}
