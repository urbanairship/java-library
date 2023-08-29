package com.urbanairship.api.inbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageIdError {
    private final String messageId;
    private final String errorMessage;
    private final Integer errorCode;

    public MessageIdError(@JsonProperty("message_id") String messageId,
                          @JsonProperty("error_message") String errorMessage,
                          @JsonProperty("error_code") Integer errorCode) {
        this.messageId = messageId;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Get the message id.
     *
     * @return String messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Get the error message.
     *
     * @return String errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Get the value for the error code.
     *
     * @return Integer errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }
}
