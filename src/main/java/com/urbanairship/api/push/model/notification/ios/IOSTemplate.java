package com.urbanairship.api.push.model.notification.ios;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = IOSTemplate.Builder.class)
public class IOSTemplate extends PushModelObject {
    private final Optional<String> templateId;
    private final Optional<IOSFields> iosFields;

    private IOSTemplate(Builder builder) {
        this.templateId = Optional.ofNullable(builder.templateId);
        this.iosFields = Optional.ofNullable(builder.iosFields);
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
    public Optional<IOSFields> getIosFields() {
        return iosFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOSTemplate that = (IOSTemplate) o;
        return Objects.equal(templateId, that.templateId) &&
                Objects.equal(iosFields, that.iosFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateId, iosFields);
    }

    @Override
    public String toString() {
        return "IOSTemplate{" +
                "templateId=" + templateId +
                ", iosFields=" + iosFields +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        String templateId = null;
        IOSFields iosFields = null;

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
         * Set the ios fields.
         *
         * Items in the field object are personalizable with handlebars.
         *
         * @param iosFields IOSFields
         * @return Builder
         */
        public Builder setFields(IOSFields iosFields) {
            this.iosFields = iosFields;
            return this;
        }

        public IOSTemplate build() {
            Preconditions.checkArgument(!(templateId != null && iosFields != null), "templateID and ios fields cannot both be set.");

            return new IOSTemplate(this);
        }
    }
}
