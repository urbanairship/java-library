/*
 * Copyright (c) 2013-2022. Airship and Contributors
 */

package com.urbanairship.api.email.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;


public class EmailAttachmentResponse {
    private final boolean ok;
    private final ImmutableList<String> attachmentIds;

    public EmailAttachmentResponse(@JsonProperty("ok") boolean ok, @JsonProperty("attachment_ids") ImmutableList<String> attachmentIds) {
        this.ok = ok;
        this.attachmentIds = attachmentIds;
    }

    /**
     * Get the OK status as a boolean
     *
     * @return boolean
     */
    public boolean getOk() {
        return ok;
    }

    /**
     * Get a list of Attachment IDs.
     *
     * @return An ImmutableList of String
     */
    public ImmutableList<String> getAttachmentIds() {
        return attachmentIds;
    }

    @Override
    public String toString() {
        return "EmailAttachmentResponse{" +
                "ok=" + ok +
                ", attachmentIds=" + attachmentIds +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, attachmentIds);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final EmailAttachmentResponse other = (EmailAttachmentResponse) obj;
        return Objects.equal(this.ok, other.ok) &&
                Objects.equal(this.attachmentIds, other.attachmentIds);
    }
}