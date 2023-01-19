package com.urbanairship.api.push.model.notification.email;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.urbanairship.api.createandsend.model.notification.email.EmailTemplate;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.Optional;


/**
 * Represents the payload to be used for registering or updating an email channel.
 */
public class EmailPayload extends PushModelObject implements DevicePayloadOverride {

    private final Optional<String> alert;
    private final Optional<String> subject;
    private final Optional<String> htmlBody;
    private final Optional<String> plaintextBody;
    private final Optional<MessageType> messageType;
    private final Optional<String> senderName;
    private final Optional<String> senderAddress;
    private final Optional<String> uaAddress;
    private final Optional<String> replyTo;
    private final Optional<ImmutableList<Attachment>> attachments;
    private final Optional<EmailTemplate> emailTemplate;
    private final Optional<Boolean> clickTracking;
    private final Optional<Boolean> openTracking;

    private EmailPayload(Builder builder) {
        this.alert = Optional.ofNullable(builder.alert);
        this.subject = Optional.ofNullable(builder.subject);
        this.htmlBody = Optional.ofNullable(builder.htmlBody);
        this.plaintextBody = Optional.ofNullable(builder.plaintextBody);
        this.messageType = Optional.ofNullable(builder.messageType);
        this.senderName = Optional.ofNullable(builder.senderName);
        this.senderAddress = Optional.ofNullable((builder.senderAddress));
        this.uaAddress = Optional.ofNullable((builder.uaAddress));
        this.replyTo = Optional.ofNullable((builder.replyTo));
        this.emailTemplate = Optional.ofNullable(builder.emailTemplate);
        this.clickTracking = Optional.ofNullable(builder.clickTracking);
        this.openTracking = Optional.ofNullable(builder.openTracking);
        if (builder.attachments.build().isEmpty()) {
            this.attachments = Optional.empty();
        } else {
            this.attachments = Optional.ofNullable(builder.attachments.build());
        }
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
        return DeviceType.EMAIL;
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
     * Optional, Get the Attachment objects, each containing an id string which represents an email attachment.
     *
     * @return Optional ImmutableList attachments
     */
    public Optional<ImmutableList<Attachment>> getAttachments() {
        return attachments;
    }

    public Optional<EmailTemplate> getTemplate() {
        return emailTemplate;
    }

    /**
     * Optional, Get the clickTracking value.
     *
     * @return Optional Boolean clickTracking
     */
    public Optional<Boolean> getClickTracking() {
        return clickTracking;
    }

    /**
     * Optional, Get the openTracking value.
     *
     * @return Optional Boolean openTracking
     */
    public Optional<Boolean> getOpenTracking() {
        return openTracking;
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
                ", attachments=" + attachments +
                ", emailTemplate=" + emailTemplate +
                ", clickTracking=" + clickTracking +
                ", openTracking=" + openTracking +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailPayload)) return false;
        EmailPayload that = (EmailPayload) o;
        return Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getHtmlBody(), that.getHtmlBody()) &&
                Objects.equals(getPlaintextBody(), that.getPlaintextBody()) &&
                Objects.equals(getMessageType(), that.getMessageType()) &&
                Objects.equals(getSenderName(), that.getSenderName()) &&
                Objects.equals(getSenderAddress(), that.getSenderAddress()) &&
                Objects.equals(getUaAddress(), that.getUaAddress()) &&
                Objects.equals(getReplyTo(), that.getReplyTo()) &&
                Objects.equals(getAttachments(), that.getAttachments()) &&
                Objects.equals(getTemplate(), that.getTemplate()) &&
                Objects.equals(getClickTracking(), that.getClickTracking()) &&
                Objects.equals(getOpenTracking(), that.getOpenTracking()) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject(), getHtmlBody(), getPlaintextBody(), getMessageType(), getSenderName(),
                getSenderAddress(), getUaAddress(), getReplyTo(), getAttachments(), getTemplate(), getClickTracking(), getOpenTracking());
    }

    /**
     * EmailPayload Builder.
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
        private ImmutableList.Builder<Attachment> attachments = ImmutableList.builder();
        private EmailTemplate emailTemplate = null;
        private Boolean clickTracking = null;
        private Boolean openTracking = null;
        private Builder() {
        }

        /**
         * Optional, a string representing the subject of the notification.
         *
         * @param subject Optional String
         * @return EmailPayload Builder
         */
        public Builder setSubject(String subject) {
            this.subject = subject;
            return this;
        }

        /**
         * Optional, a string representing the HTML body of the notification.
         *
         * @param htmlBody Optional String
         * @return EmailPayload Builder
         */
        public Builder setHtmlBody(String htmlBody) {
            this.htmlBody = htmlBody;
            return this;
        }

        /**
         * Optional, a string representing the plaintext body of the notification.
         *
         * @param plaintextBody Optional String
         * @return EmailPayload Builder
         */
        public Builder setPlaintextBody(String plaintextBody) {
            this.plaintextBody = plaintextBody;
            return this;
        }

        /**
         * Optional, an enum representing the possible message types of the notification.
         *
         * @param value Optional Map of Strings
         * @return EmailPayload Builder
         */
        public Builder setMessageType(MessageType value) {
            this.messageType = value;
            return this;
        }

        /**
         * Optional, a string representing the sender address.
         *
         * @param senderAddress Optional String
         * @return EmailPayload Builder
         */
        public Builder setSenderAddress(String senderAddress) {
            this.senderAddress = senderAddress;
            return this;
        }

        /**
         * Optional, a string representing the reserved UA email address for Create and Send.
         *
         * @param uaAddress Optional String
         * @return EmailPayload Builder
         */
        public Builder setUaAddress(String uaAddress) {
            this.uaAddress = uaAddress;
            return this;
        }

        /**
         * Optional, a string representing the reply-to address.
         *
         * @param replyTo Optional String
         * @return EmailPayload Builder
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
         * @return EmailPayload Builder
         */
        public Builder setSenderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        /**
         * Set the device type for the email channel payload.
         * @deprecated This method is not needed since DeviceType.EMAIL is always returned from EmailPayload.
         *
         * @param deviceType DeviceType
         * @return EmailPayload Builder
         */
        @Deprecated
        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        /**
         * Add an Attachment objects, each containing an id string which represents an email attachment.
         *
         * @param attachment Attachment
         * @return EmailPayload Builder
         */
        public Builder addAttachment(Attachment attachment) {
            this.attachments.add(attachment);
            return this;
        }

        /**
         * Add an emailTemplate objects.
         *
         * @param emailTemplate EmailTemplate
         * @return EmailPayload Builder
         */
        public Builder setTemplate(EmailTemplate emailTemplate) {
            this.emailTemplate = emailTemplate;
            return this;
        }

        /**
         * Optional, True by default. Set to false to prevent click tracking for GDPR compliance.
         * @param clickTracking Boolean
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setClickTracking(Boolean clickTracking) {
            this.clickTracking = clickTracking;
            return this;
        }

        /**
         * Optional, True by default. Set to false to prevent “open” tracking for GDPR compliance.
         * @param openTracking Boolean
         * @return CreateAndSendEmailPayload Builder
         */
        public Builder setOpenTracking(Boolean openTracking) {
            this.openTracking = openTracking;
            return this;
        }

        public EmailPayload build() {

            if(emailTemplate == null) {

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
            }

            return new EmailPayload(this);
        }
    }
}