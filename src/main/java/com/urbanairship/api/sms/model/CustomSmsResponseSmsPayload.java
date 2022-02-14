package com.urbanairship.api.sms.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

/**
 * Represents the payload to be used for using custom response sms payload.
 */
public class CustomSmsResponseSmsPayload  extends SmsModelObject {
    private final String mobileOriginatedId;
    private final String alert;
    private final Optional<Boolean> shortenLinks;

    //Protected to facilitate subclassing for create and send child object
    protected CustomSmsResponseSmsPayload(Builder builder) {
        this.mobileOriginatedId = builder.mobileOriginatedId;
        this.alert = builder.alert;
        this.shortenLinks = Optional.fromNullable(builder.shortenLinks);

    }

    /**
     * Get the CustomSmsResponseSmsPayloadType.
     *
     * @return String mobileOriginatedId
     */
    public String getMobileOriginatedId() {
        return mobileOriginatedId;
    }

    /**
     * Get the alert text.
     *
     * @return String alert
     */
    public String getAlert() {
        return alert;
    }

    /**
     * Get the shortenLinks value.
     *
     * @return Boolean shortenLinks
     */
    public Optional<Boolean> getShortenLinks() {
        return shortenLinks;
    }


    /**
     * New CustomSmsResponseSmsPayload builder.
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomSmsResponseSmsPayload that = (CustomSmsResponseSmsPayload) o;
        return mobileOriginatedId == that.mobileOriginatedId &&
                Objects.equal(alert, that.alert) &&
                Objects.equal(shortenLinks, that.shortenLinks);
    }

    @Override
    public String toString() {
        return "CustomSmsResponseSmsPayload{" +
                "mobileOriginatedId=" + mobileOriginatedId +
                ", alert=" + alert +
                ", shortenLinks=" + shortenLinks +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mobileOriginatedId, alert, shortenLinks);
    }

    /**
     * Create CustomSmsResponseSmsPayload Builder
     */
    public final static class Builder {
        private String mobileOriginatedId;
        private String alert;
        private boolean shortenLinks;

        protected Builder() {
        }

        /**
         * Set the mobileOriginatedId
         *
         * @param mobileOriginatedId String
         * @return CustomSmsResponseSmsPayload Builder
         */
        public Builder setMobileOriginatedId(String mobileOriginatedId) {
            this.mobileOriginatedId = mobileOriginatedId;
            return this;
        }

        /**
         * Set the alert
         *
         * @param alert String
         * @return CustomSmsResponseSmsPayload Builder
         */
        public Builder setAlert(String alert) {
            this.alert = alert;
            return this;
        }

        /**
         * Optional
         * Set shortenLinks value
         *
         * @param shortenLinks boolean
         * @return Channel Builder
         */
        public Builder setShortenLinks(boolean shortenLinks) {
            this.shortenLinks = shortenLinks;
            return this;
        }

        public CustomSmsResponseSmsPayload build() {
            Preconditions.checkNotNull(mobileOriginatedId, "mobileOriginatedId cannot be null.");
            Preconditions.checkNotNull(alert, "alert cannot be null.");

            return new CustomSmsResponseSmsPayload(this);
        }
    }
}
