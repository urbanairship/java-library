/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.reports.model;


import com.google.common.base.Objects;

import java.util.List;

public final class ReportsAPIOpensResponse {

    private final List<Opens> object;

    private ReportsAPIOpensResponse(List<Opens> object) {
        this.object = object;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<Opens> getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "ReportsAPIOpensResponse{" +
                "object=" + object +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(object);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ReportsAPIOpensResponse other = (ReportsAPIOpensResponse) obj;
        return Objects.equal(this.object, other.object);
    }

    public static class Builder {
        private List<Opens> object;

        private Builder() {
        }

        public Builder setObject(List<Opens> value) {
            this.object = value;
            return this;
        }

        public ReportsAPIOpensResponse build() {
            return new ReportsAPIOpensResponse(object);
        }
    }

}
