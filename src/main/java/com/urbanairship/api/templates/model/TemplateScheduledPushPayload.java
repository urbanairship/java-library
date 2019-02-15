package com.urbanairship.api.templates.model;


import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.DeviceTypeData;
import com.urbanairship.api.push.model.audience.Selector;
import com.urbanairship.api.schedule.model.Schedule;

public class TemplateScheduledPushPayload {
    private final Selector audience;
    private final DeviceTypeData deviceTypes;
    private final TemplateSelector mergeData;
    private final Schedule schedule;

    private TemplateScheduledPushPayload(TemplateScheduledPushPayload.Builder builder) {
        this.audience = builder.audience;
        this.deviceTypes = builder.deviceTypes;
        this.mergeData = builder.mergeData;
        this.schedule = builder.schedule;
    }

    /**
     * Generate a new TemplatePushPayload builder.
     *
     * @return A TemplatePushPayload builder
     */
    public static TemplateScheduledPushPayload.Builder newBuilder() {
        return new TemplateScheduledPushPayload.Builder();
    }

    /**
     * Get the template push audience.
     *
     * @return An audience selector
     */
    public Selector getAudience() {
        return audience;
    }

    /**
     * Get the template push device type specification.
     *
     * @return A DeviceTypeData object
     */
    public DeviceTypeData getDeviceTypes() {
        return deviceTypes;
    }

    /**
     * Get the merge data (variable specification) for a template push.
     *
     * @return A TemplateSelector object
     */
    public TemplateSelector getMergeData() {
        return mergeData;
    }

    /**
     * Get the merge data (variable specification) for a template push.
     *
     * @return A TemplateSelector object
     */
    public Schedule getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "TemplatePushPayload{" +
                "audience=" + audience +
                ", deviceTypes=" + deviceTypes +
                ", mergeData=" + mergeData +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemplateScheduledPushPayload that = (TemplateScheduledPushPayload) o;

        if (!audience.equals(that.audience)) return false;
        if (!deviceTypes.equals(that.deviceTypes)) return false;
        if (!mergeData.equals(that.mergeData)) return false;
        if (!schedule.equals(that.schedule)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = audience.hashCode();
        result = 31 * result + deviceTypes.hashCode();
        result = 31 * result + mergeData.hashCode();
        return result;
    }

    public static class Builder {
        private Selector audience = null;
        private DeviceTypeData deviceTypes = null;
        private TemplateSelector mergeData = null;
        private Schedule schedule = null;

        /**
         * Set the template push schedule.
         *
         * @param schedule A Schedule object
         * @return Builder
         */
        public TemplateScheduledPushPayload.Builder setSchedule(Schedule schedule) {
            this.schedule = schedule;
            return this;
        }

        /**
         * Set the template push audience.
         *
         * @param audience A Selector object
         * @return Builder
         */
        public TemplateScheduledPushPayload.Builder setAudience(Selector audience) {
            this.audience = audience;
            return this;
        }

        /**
         * Set the template push device types.
         *
         * @param deviceTypes A DeviceTypeData object.
         * @return Builder
         */
        public TemplateScheduledPushPayload.Builder setDeviceTypes(DeviceTypeData deviceTypes) {
            this.deviceTypes = deviceTypes;
            return this;
        }

        /**
         * Specify the template push variable replacements with a TemplateSelector.
         *
         * @param mergeData A TemplateSelector object
         * @return Builder
         */
        public TemplateScheduledPushPayload.Builder setMergeData(TemplateSelector mergeData) {
            this.mergeData = mergeData;
            return this;
        }

        /**
         * Build the TemplatePushPayload.
         * <pre>
         *     1. Audience, deviceTypes, and mergeData cannot be null.
         * </pre>
         *
         * @return A TemplatePushPayload object
         */
        public TemplateScheduledPushPayload build() {
            Preconditions.checkArgument(
                    audience != null && deviceTypes != null && mergeData != null,
                    "audience, deviceTypes, and mergeData cannot be null."
            );

            return new TemplateScheduledPushPayload(this);
        }
    }

}
