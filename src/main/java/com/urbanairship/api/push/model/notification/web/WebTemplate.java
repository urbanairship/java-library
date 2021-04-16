package com.urbanairship.api.push.model.notification.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Optional;

@JsonDeserialize(builder = WebTemplate.Builder.class)
public class WebTemplate extends PushModelObject {
    private final Optional<String> templateId;
    private final Optional<WebFields> webFields;

    private WebTemplate(Builder builder) {
        templateId = Optional.ofNullable(builder.templateId);
        webFields = Optional.ofNullable(builder.webFields);
    }

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
     * Get the web fields.
     *
     * Items in the field object are personalizable with handlebars.
     *
     * @return Optional WebFields webFields
     */
    @JsonProperty("fields")
    public Optional<WebFields> getWebFields() {
        return webFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebTemplate that = (WebTemplate) o;
        return Objects.equal(templateId, that.templateId) &&
                Objects.equal(webFields, that.webFields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(templateId, webFields);
    }

    @Override
    public String toString() {
        return "WebTemplate{" +
                "templateId=" + templateId +
                ", webFields=" + webFields +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder {
        private String templateId = null;
        private WebFields webFields = null;

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
         * Set the web fields.
         *
         * Items in the field object are personalizable with handlebars.
         *
         * @param webFields WebFields
         * @return Builder
         */
        @JsonProperty("fields")
        public Builder setFields(WebFields webFields) {
            this.webFields = webFields;
            return this;
        }

        public WebTemplate build() {
            Preconditions.checkArgument(!(templateId != null && webFields != null), "templateID and ios fields cannot both be set.");

            return new WebTemplate(this);
        }
    }
}
