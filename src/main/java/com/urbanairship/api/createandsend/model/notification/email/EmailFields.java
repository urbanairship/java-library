package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Preconditions;

/**
 * Represends the email fields in the notification.
 */
public class EmailFields {
    private final String plainTextBody;
    private final String subject;

    private EmailFields(Builder builder) {
        plainTextBody = builder.plainTextBody;
        subject = builder.subject;
    }

    /**
     * EmailFields Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the plain text body of the email you want to send.
     * @return String
     */
    public String getPlainTextBody() {
        return plainTextBody;
    }

    /**
     * Get the subject line of the email you want to send.
     * @return String
     */
    public String getSubject() {
        return subject;
    }

    public static class Builder {
        private String plainTextBody;
        private String subject;

        /**
         * Set the plain text body of the email you want to send.
         * When "message_type": "commercial", the body must contain a [[ua_unsubscribe]] link,
         * which will be replaced by the unsubscribe link in Urban Airship.
         * @param plainTextBody String
         * @return EmailFields Builder
         */
        public Builder setPlainTextBody(String plainTextBody) {
            this.plainTextBody = plainTextBody;
            return this;
        }

        /**
         * Set the subject line of the email you want to send.
         * @param subject String
         * @return EmailFields Builder
         */
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
