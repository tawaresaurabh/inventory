package fi.plasmonics.inventory.model.response.product;

import fi.plasmonics.inventory.model.response.MessageType;

public class DeleteProductResponse {
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

    public DeleteProductResponse(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }

}