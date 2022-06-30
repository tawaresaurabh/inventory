package fi.plasmonics.inventory.model.response.item;

import fi.plasmonics.inventory.model.response.MessageType;

public class DeleteItemResponse {
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

    public DeleteItemResponse(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

}
