package com.urbanairship.api.inbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;


public class InboxBatchDeleteResponse {
    private final List<String> deletedMessageIds;
    private final List<MessageIdError> messageIdErrors;

    public InboxBatchDeleteResponse(@JsonProperty("deleted_message_ids") List<String> deletedMessageIds,
                                    @JsonProperty("errors") List<MessageIdError> messageIdErrors) {
        this.deletedMessageIds = deletedMessageIds;
        this.messageIdErrors = messageIdErrors;
    }

    public List<String> getDeletedMessageIds() {
        return deletedMessageIds;
    }

    public List<MessageIdError> getMessageIdErrors() {
        return messageIdErrors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InboxBatchDeleteResponse that = (InboxBatchDeleteResponse) o;
        return Objects.equals(deletedMessageIds, that.deletedMessageIds) &&
                Objects.equals(messageIdErrors, that.messageIdErrors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deletedMessageIds, messageIdErrors);
    }

    @Override
    public String toString() {
        return "InboxBatchDeleteResponse{" +
                "deletedMessageIds=" + deletedMessageIds +
                ", messageIdErrors=" + messageIdErrors +
                '}';
    }
}
