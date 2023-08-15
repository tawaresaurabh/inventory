package fi.plasmonics.inventory.exceptions;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class InventoryException extends RuntimeException {

    private final ErrorType errorType;
    private final OperationType operationType;
    private List<String> details = new ArrayList<>();



    public InventoryException(ErrorType errorType, OperationType operationType) {
        this.errorType = errorType;
        this.operationType = operationType;
    }


    public InventoryException addKeyValueDetail(String key, String value) {
        details.add(key + "=" + value);
        return this;
    }

}
