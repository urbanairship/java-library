package com.urbanairship.api.sms.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Represents the payload to be used for using custom response mms payload.
 */
public class CustomSmsResponseMmsPayload extends SmsModelObject{
    private final String mobileOriginatedId;
    private final String fallbackText;
    private final MmsSlides slides;


    //Protected to facilitate subclassing for create and send child object
    protected CustomSmsResponseMmsPayload(Builder builder) {
        this.mobileOriginatedId = builder.mobileOriginatedId;
        this.fallbackText = builder.fallbackText;
        this.slides = builder.slides;
    }

    /**
     * Get the CustomSmsResponseMmsPayloadType.
     *
     * @return String mobileOriginatedId
     */
    public String getMobileOriginatedId() {
        return mobileOriginatedId;
    }

    /**
     * Get the fallbackText text.
     *
     * @return String fallbackText
     */
    public String getFallbackText() {
        return fallbackText;
    }

    /**
     * Get the slides.
     *
     * @return MmsSlides slides
     */
    public MmsSlides getSlides() {
        return slides;
    }

    /**
     * New CustomSmsResponseMmsPayload builder.
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
        CustomSmsResponseMmsPayload that = (CustomSmsResponseMmsPayload) o;
        return mobileOriginatedId == that.mobileOriginatedId &&
                Objects.equal(fallbackText, that.fallbackText) &&
                Objects.equal(slides, that.slides);
    }

    @Override
    public String toString() {
        return "CustomSmsResponseMmsPayload{" +
                "mobileOriginatedId=" + mobileOriginatedId +
                ", fallbackText=" + fallbackText +
                ", slides=" + slides +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mobileOriginatedId, fallbackText, slides);
    }

    /**
     * Create CustomSmsResponseMmsPayload Builder
     */
    public final static class Builder {
        public MmsSlides slides;
        private String mobileOriginatedId;
        private String fallbackText;

        protected Builder() {
        }

        /**
         * Set the mobileOriginatedId
         *
         * @param mobileOriginatedId String
         * @return CustomSmsResponseMmsPayload Builder
         */
        public Builder setMobileOriginatedId(String mobileOriginatedId) {
            this.mobileOriginatedId = mobileOriginatedId;
            return this;
        }

        /**
         * Set the fallbackText
         *
         * @param fallbackText String
         * @return CustomSmsResponseMmsPayload Builder
         */
        public Builder setFallbackText(String fallbackText) {
            this.fallbackText = fallbackText;
            return this;
        }

        /**
         * Set the slides
         *
         * @param slides MmsSlides
         * @return CustomSmsResponseMmsPayload Builder
         */
        public Builder setSlides(MmsSlides slides) {
            this.slides = slides;
            return this;
        }

        public CustomSmsResponseMmsPayload build() {
            Preconditions.checkNotNull(mobileOriginatedId, "mobileOriginatedId cannot be null.");
            Preconditions.checkNotNull(fallbackText, "fallbackText cannot be null.");
            Preconditions.checkNotNull(slides, "slides cannot be null.");

            return new CustomSmsResponseMmsPayload(this);
        }
    }
}
