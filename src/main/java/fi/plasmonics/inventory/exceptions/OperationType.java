package fi.plasmonics.inventory.exceptions;

import lombok.Getter;

@Getter
public enum OperationType {
    CREATE(1, "create"),
    FIND(2, "find"),
    READ(3, "read"),
    UPDATE(4, "update"),
    DELETE(5, "remove"),
    ENCODE(6, "encode"),
    DECODE(7, "decode"),
    MERGE(8, "merge"),
    UNDEFINED(9, "undefined"),
    ;

    private final int code;
    private final String operation;

    OperationType(int code, String operation) {
        this.code = code;
        this.operation = operation;
        if (code > 99 || code < 1) {
            throw new IllegalArgumentException("Code out of bounds: " + code);
        }
    }
}
