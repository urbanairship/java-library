package com.urbanairship.api.push.model.notification.email;

import com.google.common.base.Optional;

/**
 * Enum of Message Types categories
 */
public enum MessageType {

    COMMERCIAL("commercial"),
    TRANSACTIONAL("transactional");

    private final String messageType;

    private MessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Gets the message type
     *
     * @return String messageType
     * */
    public String getMessageType() {
        return messageType;
    }

    public static Optional<MessageType> find(String identifier) {
        for (MessageType messageType : values()) {
            if (messageType.getMessageType().equals(identifier)) {
                return Optional.of(messageType);
            }
        }

        return Optional.absent();
    }
}