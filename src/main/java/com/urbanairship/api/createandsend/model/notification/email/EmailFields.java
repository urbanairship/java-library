package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Preconditions;

public class EmailFields {
    private final String plainTextBody;
    private final String subject;

    private EmailFields(Builder builder) {
        plainTextBody = builder.plainTextBody;
        subject = builder.subject;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getPlainTextBody() {
        return plainTextBody;
    }

    public String getSubject() {
        return subject;
    }

    public static class Builder {
        private String plainTextBody;
        private String subject;

        public Builder setPlainTextBody(String plainTextBody) {
            this.plainTextBody = plainTextBody;
            return this;
        }

        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailFields build() {
            Preconditions.checkNotNull(plainTextBody, "plain text body cannot be null.");
            Preconditions.checkNotNull(subject, "subject cannot be null.");

            return new EmailFields(this);
        }
    }
}
