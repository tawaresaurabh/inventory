package fi.plasmonics.inventory.model.response.itemorder;

import fi.plasmonics.inventory.model.response.MessageType;

public class ItemOrderActionResponse {

    private MessageType messageType;
    private String message;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ItemOrderActionResponse(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }
}
