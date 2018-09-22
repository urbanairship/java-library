package com.urbanairship.api.push.model.notification.email;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.notification.DevicePayloadOverride;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

public class EmailPayload extends PushModelObject implements DevicePayloadOverride {

    //Make HTML Option, everything else required. If someone tries to build one, throw an error...if that's the way we want to go about doing it.
    private final Optional<String> alert;
    private final Optional<String> subject;
    private final Optional<String> htmlBody;
    private final Optional<String> plaintextBody;
    private final Optional<MessageType> messageType;
    private final Optional<String> senderName;
    private final Optional<String> senderAddress;
    private final Optional<String> replyTo;
    private final DeviceType deviceType;

    private EmailPayload(Builder builder) {
        this.alert = Optional.fromNullable(builder.alert);
        this.subject = Optional.fromNullable(builder.subject);
        this.htmlBody = Optional.fromNullable(builder.htmlBody);
        this.plaintextBody = Optional.fromNullable(builder.plaintextBody);
        this.messageType = Optional.fromNullable(builder.messageType);
        this.senderName = Optional.fromNullable(builder.senderName);
        this.senderAddress = Optional.fromNullable((builder.senderAddress));
        this.replyTo = Optional.fromNullable((builder.replyTo));
        this.deviceType = builder.deviceType;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public DeviceType getDeviceType() { return deviceType.EMAIL; }

    /**
     * Optional, override the alert value provided at the top level, if any.
     *
     * @return Optional String alert.
     */
    public Optional<String> getAlert() { return alert; }

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
    public Optional<MessageType> getMessageType() { return messageType; }

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
     * Optional, a string representing the "reply to" address of the notification.
     *
     * @return Optional String replyTo.
     */
    public Optional<String> getReplyTo() {
        return replyTo;
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
                ", replyTo=" + replyTo +
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
                Objects.equals(getReplyTo(), that.getReplyTo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubject(), getHtmlBody(), getPlaintextBody(), getMessageType(), getSenderName(),
                getSenderAddress(), getReplyTo());
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
        private String replyTo = null;
        private DeviceType deviceType = null;

        private Builder() { }

        /**
         * Optional, override the alert value provided at the top level, if any.
         *
         * @param alert String.
         * @return EmailPayload Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
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
         * Must be set up by Urban Airship before use.
         * @return EmailPayload Builder
         */
        public Builder setSenderName(String senderName) {
            this.senderName = senderName;
            return this;
        }

        /**
         * Set the device type for the email channel payload.
         *
         * @param deviceType DeviceType
         * @return EmailPayload Builder
         */
        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

//        private final Pattern PLAINTEXT_PATTERN = Pattern.compile("(?i)(\\[\\[.*?ua-unsubscribe.*?\\]\\])");
//        private boolean validPlaintext = PLAINTEXT_PATTERN.matcher(plaintextBody).find();
//
//        private final Pattern HTML_PATTERN = Pattern.compile("(?i)(<a[^>]*?data-ua-unsubscribe.*?>.*?\\S+?.*?</a>)");
//        private boolean validHtml = HTML_PATTERN.matcher(htmlBody).find();

        public EmailPayload build() {
            //Set precondition for message type : commercial which requires unsubscribe links.
            Preconditions.checkNotNull(deviceType, "DeviceType must be set.");

            Preconditions.checkNotNull(subject, "Subject must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(subject),
                    "Subject must not be blank");

            Preconditions.checkNotNull(plaintextBody, "PlaintextBody must be set.");

            Preconditions.checkArgument(StringUtils.isNotBlank(plaintextBody),
                    "Plaintext Body must not be blank");
//
//            if(messageType == messageType.COMMERCIAL) {
//                Preconditions.checkArgument(validPlaintext == true,
//                        "PlaintextBody must contain Unsubscribe Link");
//                if(!htmlBody.isEmpty()){
//                    Preconditions.checkArgument(validHtml == true && validPlaintext ,
//                            "PlaintextBody and HtmlBody must contain unsubscribe links" );
//                }
//            }

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

            return new EmailPayload(this);
        }
    }
}
