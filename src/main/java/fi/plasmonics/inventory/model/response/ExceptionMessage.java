package fi.plasmonics.inventory.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionMessage {
    private final int errorCode;
    private final String message;
}
