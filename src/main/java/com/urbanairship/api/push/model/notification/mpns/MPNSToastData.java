/*
 * Copyright 2013 Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;

public class MPNSToastData
{
    private final Optional<String> text1;
    private final Optional<String> text2;
    private final Optional<String> param;

    private MPNSToastData(Optional<String> text1,
                          Optional<String> text2,
                          Optional<String> param)
    {
        this.text1 = text1;
        this.text2 = text2;
        this.param = param;
    }

    public Optional<String> getText1() {
        return text1;
    }

    public Optional<String> getText2() {
        return text2;
    }

    public Optional<String> getParam() {
        return param;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPNSToastData that = (MPNSToastData)o;
        if (text1 != null ? !text1.equals(that.text1) : that.text1 != null) {
            return false;
        }
        if (text2 != null ? !text2.equals(that.text2) : that.text2 != null) {
            return false;
        }
        if (param != null ? !param.equals(that.param) : that.param != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (text1 != null ? text1.hashCode() : 0);
        result = 31 * result + (text2 != null ? text2.hashCode() : 0);
        result = 31 * result + (param != null ? param.hashCode() : 0);
        return result;
    }

    public static class Builder {
        private String text1;
        private String text2;
        private String param;

        private Builder() { }

        public Builder setText1(String value) {
            this.text1 = value;
            return this;
        }

        public Builder setText2(String value) {
            this.text2 = value;
            return this;
        }

        public Builder setParam(String value) {
            this.param = value;
            return this;
        }

        public MPNSToastData build() {
            checkArgument(text1 != null || text2 != null, "At least one of text1 or text2 must be set");
            Validation.validateStringValue(text1, "text1");
            Validation.validateStringValue(text2, "text2");
            Validation.validatePath(param, "param");

            return new MPNSToastData(Optional.fromNullable(text1),
                                 Optional.fromNullable(text2),
                                 Optional.fromNullable(param));
        }
    }
}
