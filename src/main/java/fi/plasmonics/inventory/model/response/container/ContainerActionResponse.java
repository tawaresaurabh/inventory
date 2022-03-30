package fi.plasmonics.inventory.model.response.container;

import fi.plasmonics.inventory.model.response.MessageType;

public class ContainerActionResponse {
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

    public ContainerActionResponse(MessageType messageType, String message) {
        this.messageType = messageType;
        this.message = message;
    }
}
