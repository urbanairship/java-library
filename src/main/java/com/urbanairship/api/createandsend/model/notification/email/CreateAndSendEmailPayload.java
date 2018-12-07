package com.urbanairship.api.createandsend.model.notification.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import com.urbanairship.api.push.model.notification.email.MessageType;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;


/**
 * Represents the payload to be used for registering or updating an email channel.
 */
public class CreateAndSendEmailPayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<Boolean> bypassOptInLevel;
    private final Optional<String> alert;
    private final Optional<String> subject;
    private final Optional<String> htmlBody;
    private final Optional<String> plaintextBody;
    private final Optional<MessageType> messageType;
    private final Optional<String> senderName;
    private final Optional<String> senderAddress;
    private final Optional<String> uaAddress;
    private final Optional<String> replyTo;
    private final Optional<EmailTemplate> emailTemplate;
    private final DeviceType deviceType;

    private CreateAndSendEmailPayload(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
        this.subject = Optional.fromNullable(builder.subject);
        this.htmlBody = Optional.fromNullable(builder.htmlBody);
        this.plaintextBody = Optional.fromNullable(builder.plaintextBody);
        this.messageType = Optional.fromNullable(builder.messageType);
        this.senderName = Optional.fromNullable(builder.senderName);
        this.senderAddress = Optional.fromNullable((builder.senderAddress));
        this.uaAddress = Optional.fromNullable((builder.uaAddress));
        this.replyTo = Optional.fromNullable((builder.replyTo));
        this.bypassOptInLevel = Optional.fromNullable(builder.byPassOptInLevel);
        this.emailTemplate = Optional.fromNullable(builder.emailTemplate);
        this.deviceType = builder.deviceType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Returns Devicetype: Email when requested
     *
     * @return String email
     */
    @Override
    public DeviceType getDeviceType() {
        return deviceType.EMAIL;
    }

    /**
     * Get the email template, Using a template enables you to provide and populate
     * variables in your notification
     *
     * @return Optional, EmailTemplate
     */
    public Optional<EmailTemplate> getEmailTemplate() {
        return emailTemplate;
    }

    /**
     * Optional, override the alert value provided at the top level, if any.
     *
     * @return Optional String alert.
     */
    public Optional<String> getAlert() {
        return alert;
    }

    /**
     * Optional, a string representing the subject of the notification.
     *
     * @return Optional String subject.
     */
    public Optional<String> getSubject() {
        return subject;
    }

    /**
     * Optional, a string value for providing a the HTML body of the notification.
     *
     * @return Optional String htmlBody
     */
    public Optional<String> getHtmlBody() {
        return htmlBody;
    }

    /**
     * Optional, a string value for providing a the plaintext Body of the notification.
     *
     * @return Optional String plaintextBody
     */
    public Optional<String> getPlaintextBody() {
        return plaintextBody;
    }

    /**
     * Optional, an enum of potential values representing the possible message types.
     *
     * @return Optional enum messageType.
     */
    public Optional<MessageType> getMessageType() {
        return messageType;
    }

    /**
     * Optional, a string representing the name of the sender.
     *
     * @return Optional String senderName.
     */
    public Optional<String> getSenderName() {
        return senderName;
    }

    /**
     * Optional, a string representing the address of the sender.
     *
     * @return Optional String senderAddress.
     */
    public Optional<String> getSenderAddress() {
        return senderAddress;
    }

    /**
     * Optional, a string representing the reserved UA email address of the sender for Create and Send.
     *
     * @return Optional String uaAddress.
     */
    public Optional<String> getUaAddress() {
        return uaAddress;
    }


    /**
     * Optional, a string representing the "reply to" address of the notification.
     *
     * @return Optional String replyTo.
     */
    public Optional<String> getReplyTo() {
        return replyTo;
    }

    /**
     * Optional, a boolean toggle you can set when message_type is set to transactional to send a business critical
     * email. If true, the message will be sent to your entire audience, ignoring  transactional_opted_out status.
     *
     * @return Optional Boolean bypassOptInLevel
     */
    public Optional<Boolean> getBypassOptInLevel() {
        return bypassOptInLevel;
    }

    @Override
    public String toString() {
        return "EmailPayload{" +
                "subject=" + subject +
                ", htmlBody=" + htmlBody +
                ", plaintextBody=" + plaintextBody +
                ", messageType=" + messageType +
                ", senderName=" + senderName +
                ", senderAddress=" + senderAddress +
                ", uaAddress=" + uaAddress +
                ", replyTo=" + replyTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateAndSendEmailPayload)) return false;
        CreateAndSendEmailPayload that = (CreateAndSendEmailPayload) o;
        return Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getHtmlBody(), that.getHtmlBody()) &&
                Objects.equals(getPlaintextBody(), that.getPlaintextBody()) &&
                Objects.equals(getMessageType(), that.getMessageType()) &&
                Objects.equals(getSenderName(), that.getSenderName()) &&
                Objects.equals(getSenderAddress(), that.getSenderAddress()) &&
                Objects.equals(getUaAddress(), that.getUaAddress()) &&
                Objects.equals(getReplyTo(), that.getReplyTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject(), getHtmlBody(), getPlaintextBody(), getMessageType(), getSenderName(),
                getSenderAddress(), getUaAddress(), getReplyTo());
    }

    /**
     * CreateAndSendEmailPayload Builder.
     */
    public static class Builder {
        private String alert = null;
        private String subject = null;
        private String htmlBody = null;
        private String plaintextBody = null;
        private MessageType messageType = null;
        private String senderName = null;
        private String senderAddress = null;
        private String uaAddress = null;
        private String replyTo = null;
        private DeviceType deviceType = null;
        private Boolean byPassOptInLevel = null;
        private EmailTemplate emailTemplate = null;

        private Builder() {
        }

        /**
         * Optional, a string representing the subject of the notification.
         *
         * @param subject Optional String
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * Optional, a boolean you can set this toggle when message_type is set to transactional to send a business critical
         * email. If true, the message will be sent to your entire audience, ignoring  transactional_opted_out status.
         * @param byPassOptInLevel Boolean
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setByPassOptInLevel(Boolean byPassOptInLevel) {
            this.byPassOptInLevel = byPassOptInLevel;
            return this;
        }

        /**
         * Optional, a string representing the HTML body of the notification.
         *
         * @param htmlBody Optional String
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setHtmlBody(String htmlBody) {
            this.htmlBody = htmlBody;
            return this;
        }

        /**
         * Optional, a string representing the plaintext body of the notification.
         *
         * @param plaintextBody Optional String
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setPlaintextBody(String plaintextBody) {
            this.plaintextBody = plaintextBody;
            return this;
        }

        /**
         * Optional, an enum representing the possible message types of the notification.
         *
         * @param value Optional Map of Strings
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setMessageType(MessageType value) {
            this.messageType = value;
            return this;
        }

        /**
         * Optional, a string representing the sender address.
         *
         * @param senderAddress Optional String
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
            return this;
        }

        /**
         * Optional, a string representing the reserved UA email address for Create and Send.
         *
         * @param uaAddress Optional String
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setUaAddress(String uaAddress) {
            this.uaAddress = uaAddress;
            return this;
        }

        /**
         * Optional, a string representing the reply-to address.
         *
         * @param replyTo Optional String
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setReplyTo(String replyTo) {
            this.replyTo = replyTo;
            return this;
        }

        /**
         * Optional, a string representing the sender name.
         *
         * @param senderName Optional String
         *                   Must be set up by Urban Airship before use.
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setSenderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        /**
         * Set the device type for the email channel payload.
         *
         * @param deviceType DeviceType
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        /**
         * Provide the ID or inline fields for a template. Using a template enables you to provide and populate
         * variables in your notification.
         *
         * @param emailTemplate EmailTemplate
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setEmailTemplate(EmailTemplate emailTemplate) {
            this.emailTemplate = emailTemplate;
            return this;
        }

        public CreateAndSendEmailPayload build() {

            Preconditions.checkNotNull(deviceType, "DeviceType must be set.");

            Preconditions.checkNotNull(subject, "Subject must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(subject),
                    "Subject must not be blank");

            Preconditions.checkNotNull(plaintextBody, "PlaintextBody must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(plaintextBody),
                    "Plaintext Body must not be blank");

            Preconditions.checkNotNull(messageType, "MessageType must be set.");

            Preconditions.checkNotNull(senderName, "SenderName must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(senderName),
                    "SenderName must not be blank");

            Preconditions.checkNotNull(senderAddress, "SenderAddress must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(senderAddress),
                    "SenderAddress must not be blank");

            Preconditions.checkNotNull(replyTo, "ReplyTo must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(replyTo),
                    "ReplyTo must not be blank");

            return new CreateAndSendEmailPayload(this);
        }
    }
}