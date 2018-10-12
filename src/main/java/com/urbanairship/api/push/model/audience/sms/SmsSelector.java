package com.urbanairship.api.push.model.audience.sms;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.PushModelObject;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.push.model.audience.SelectorType;
import com.urbanairship.api.push.model.audience.SelectorVisitor;

public class SmsSelector extends PushModelObject implements Selector {
    private final String msisdn;
    private final String sender;

    private SmsSelector(Builder builder) {
        this.msisdn = builder.msisdn;
        this.sender = builder.sender;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the recipient phone number as msisdn.
     * @return String msisdn
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Get the sender that the app is configured to send SMS messages from.
     * @return String sender
     */
    public String getSender() {
        return sender;
    }

    @Override
    public SelectorType getType() {
        return SelectorType.SMS;
    }

    @Override
    public DeviceTypeData getApplicableDeviceTypes() {
        return DeviceTypeData.of(DeviceType.SMS);
    }

    @Override
    public void accept(SelectorVisitor visitor) { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmsSelector that = (SmsSelector) o;
        return Objects.equal(msisdn, that.msisdn) &&
                Objects.equal(sender, that.sender);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(msisdn, sender);
    }

    public static class Builder {
        private String msisdn = null;
        private String sender = null;

        /**
         * Set the recipient phone number.
         * @param msisdn String Must be numerical characters with no leading zeros.
         * @return SmsSelector Builder
         */
        public Builder setMsisdn(String msisdn) {
            this.msisdn = msisdn;
            return this;
        }

        /**
         * Set the sender that the app is configured to send SMS messages from.
         * @param sender String Must be numeric characters only.
         * @return SmsSelector Builder
         */
        public Builder setSender(String sender) {
            this.sender = sender;
            return this;
        }

        public SmsSelector build() {
            Preconditions.checkNotNull(msisdn, "msisdn must not be null.");
            Preconditions.checkNotNull(sender, "sender must not be null.");

            return new SmsSelector(this);
        }
    }
}
