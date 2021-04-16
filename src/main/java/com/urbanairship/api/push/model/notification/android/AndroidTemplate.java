package com.urbanairship.api.push.model.notification.android;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = AndroidTemplate.Builder.class)
public class AndroidTemplate extends PushModelObject {

    private final Optional<String> templateId;
    private final Optional<AndroidFields> androidFields;

    private AndroidTemplate(Builder builder) {
        this.templateId = Optional.ofNullable(builder.templateId);
        this.androidFields = Optional.ofNullable(builder.androidFields);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the template id.
     *
     * The ID of a template that you created in the Airship UI.
     *
     * @return Optional String templateId
     */
    @JsonProperty("template_id")
    public Optional<String> getTemplateId() {
        return templateId;
    }

    /**
     * Get the android fields
     *
     * Items in the field object are personalizable with handlebars.
     *
     * @return Optional AndroidFields androidFields
     */
    @JsonProperty("fields")
    public Optional<AndroidFields> getAndroidFields() {
        return androidFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AndroidTemplate template = (AndroidTemplate) o;
        return Objects.equal(templateId, template.templateId) &&
                Objects.equal(androidFields, template.androidFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateId, androidFields);
    }

    @Override
    public String toString() {
        return "AndroidTemplate{" +
                "templateId=" + templateId +
                ", androidFields=" + androidFields +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        String templateId = null;
        AndroidFields androidFields = null;

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
         * Set the android fields
         *
         * Items in the field object are personalizable with handlebars.
         *
         * @param androidFields AndroidFields
         * @return Builder
         */
        public Builder setFields(AndroidFields androidFields) {
            this.androidFields = androidFields;
            return this;
        }

        public AndroidTemplate build() {
            Preconditions.checkArgument(!(templateId != null && androidFields != null), "templateId and android fields cannot both be set.");

            return new AndroidTemplate(this);
        }
    }
}
