package fi.plasmonics.inventory.exceptions;

import lombok.Getter;

@Getter
public enum ErrorType {

    INTERNAL_ERROR(1, "internal error"),
    BAD_ARGUMENT(2, "invalid arguments"),
    NOT_FOUND(3, "no results found"),
    DUPLICATE(4, "item already exists"),
    EXISTING_DEPENDENCIES(5, "existing dependencies"),
    MAX_COUNT_REACHED(6, "max count reached"),
    CONVERT(7, "unexpected conversion exception"),
    TYPE_MISMATCH(8, "mismatching types"),
    UNDER_MAINTENANCE(9, "under maintenance"),
    NOT_IN_SERVICE(10, "not in service"),
    ;

    private final String reason;
    private final int code;

    ErrorType(int code, String reason) {
        this.code = code;
        this.reason = reason;
        if (code > 99 || code < 1) {
            throw new IllegalArgumentException("Code out of bounds: " + code);
        }
    }
}
