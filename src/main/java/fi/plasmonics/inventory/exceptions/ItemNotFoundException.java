package fi.plasmonics.inventory.exceptions;

public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException() {
        super("Item not found or has been deleted from the system");
    }
}
