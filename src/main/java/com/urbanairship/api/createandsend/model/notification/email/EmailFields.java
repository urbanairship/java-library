package com.urbanairship.api.createandsend.model.notification.email;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;
/**
 * Represends the email fields in the notification.
 */
public class EmailFields {
    private final String subject;
    private final String plainTextBody;
    private final Optional<String> htmlBody;


    private EmailFields(
        @JsonProperty("subject") String subject,
        @JsonProperty("plaintext_body") String plainTextBody,
        @JsonProperty("html_body") String htmlBody
        ) {
        this.subject = subject;
        this.plainTextBody = plainTextBody;
        this.htmlBody = Optional.ofNullable(htmlBody);
    }

    /**
     * EmailFields Builder
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * a string representing the subject of the notification.
     *
     * @return String subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * a string value for providing a the plaintext Body of the notification.
     *
     * @return String plaintextBody
     */
    public String getPlainTextBody() {
        return plainTextBody;
    }

    /**
     * Optional, a string value for providing a the HTML body of the notification.
     *
     * @return Optional String htmlBody
     */
    public Optional<String> getHtmlBody() {
        return htmlBody;
    }

    @Override
    public String toString() {
        return "EmailFields{" +
                "subject=" + subject +
                ", htmlBody=" + htmlBody +
                ", plainTextBody=" + plainTextBody +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailFields)) return false;
        EmailFields that = (EmailFields) o;
        return Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getHtmlBody(), that.getHtmlBody()) &&
                Objects.equals(getPlainTextBody(), that.getPlainTextBody());
            }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject(), getHtmlBody(), getPlainTextBody());
    }
    /**
     * EmailFields Builder.
     */
    public static class Builder {
        private String subject = null;
        private String htmlBody = null;
        private String plainTextBody = null;

        private Builder() {
        }

        /**
         * Optional, a string representing the subject of the notification.
         *
         * @param subject Optional String
         * @return EmailFields Builder
         */
        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * Optional, a string representing the HTML body of the notification.
         *
         * @param htmlBody Optional String
         * @return EmailFields Builder
         */
        public Builder setHtmlBody(String htmlBody) {
            this.htmlBody = htmlBody;
            return this;
        }

        /**
         * Optional, a string representing the plaintext body of the notification.
         *
         * @param plainTextBody Optional String
         * @return EmailFields Builder
         */
        public Builder setPlainTextBody(String plainTextBody) {
            this.plainTextBody = plainTextBody;
            return this;
        }

        public EmailFields build() {
            return new EmailFields(subject, plainTextBody, htmlBody);
        }
    }
}
