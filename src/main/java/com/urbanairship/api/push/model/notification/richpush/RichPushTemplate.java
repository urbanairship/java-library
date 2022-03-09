package com.urbanairship.api.push.model.notification.richpush;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = RichPushTemplate.Builder.class)
public class RichPushTemplate extends PushModelObject {
    private final Optional<String> templateId;
    private final Optional<RichPushFields> richPushFields;

    private RichPushTemplate(Builder builder) {
        this.templateId = Optional.ofNullable(builder.templateId);
        this.richPushFields = Optional.ofNullable(builder.richPushFields);
    }

    /**
     * Get a new IOSTemplate Builder.
     *
     * @return Builder.
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the template id.
     *
     * The ID of a template that you created in the Airship UI.
     *
     * @return Optional String templateId.
     */
    @JsonProperty("template_id")
    public Optional<String> getTemplateId() {
        return templateId;
    }

    /**
     * Get the ios fields.
     *
     * Items in the field object are personalizable with handlebars.
     *
     * @return Optional IOSFields iosFields
     */
    @JsonProperty("fields")
    public Optional<RichPushFields> getRichPushFields() {
        return richPushFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RichPushTemplate that = (RichPushTemplate) o;
        return Objects.equal(templateId, that.templateId) &&
                Objects.equal(richPushFields, that.richPushFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateId, richPushFields);
    }

    @Override
    public String toString() {
        return "RichPushTemplate{" +
                "templateId=" + templateId +
                ", richPushFields=" + richPushFields +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        String templateId = null;
        RichPushFields richPushFields = null;

        /**
         * Set the template id.
         *
         * The ID of a template that you created in the Airship UI.
         *
         * @param templateId String
         * @return Builder
         */
        @JsonProperty("template_id")
        public Builder setTemplateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        /**
         * Set the rich push fields.
         *
         * Items in the field object are personalizable with handlebars.
         *
         * @param richPushFields RichPushFields
         * @return Builder
         */
        public Builder setFields(RichPushFields richPushFields) {
            this.richPushFields = richPushFields;
            return this;
        }

        public RichPushTemplate build() {
            Preconditions.checkArgument(!(templateId != null && richPushFields != null), "templateID and rich push fields cannot both be set.");

            return new RichPushTemplate(this);
        }
    }
}
