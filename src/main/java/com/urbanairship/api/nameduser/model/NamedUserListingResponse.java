package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public class NamedUserListingResponse {
    private final boolean ok;
    private final Optional<String> nextPage;
    private final Optional<NamedUserView> namedUserView;
    private final Optional<ImmutableSet<NamedUserView>> namedUserViews;

    private NamedUserListingResponse(boolean ok, Optional<String> nextPage, Optional<NamedUserView> namedUserView, Optional<ImmutableSet<NamedUserView>> namedUserViews) {
        this.ok = ok;
        this.nextPage = nextPage;
        this.namedUserView = namedUserView;
        this.namedUserViews = namedUserViews;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean getOk() {
        return ok;
    }

    public Optional<String> getNextPage() {
        return nextPage;
    }

    public Optional<NamedUserView> getNamedUserView() {
        return namedUserView;
    }

    public Optional<ImmutableSet<NamedUserView>> getNamedUserViews() {
        return namedUserViews;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ok, nextPage, namedUserView, namedUserViews);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final NamedUserListingResponse other = (NamedUserListingResponse) obj;
        return Objects.equal(this.ok, other.ok) && Objects.equal(this.nextPage, other.nextPage) && Objects.equal(this.namedUserView, other.namedUserView) && Objects.equal(this.namedUserViews, other.namedUserViews);
    }

    @Override
    public String toString() {
        return "APIListSingleChannelResponse{" +
            "ok=" + ok +
            ", nextPage=" + nextPage +
            ", namedUserView=" + namedUserView +
            ", namedUserViews=" + namedUserViews +
            '}';
    }

    public static class Builder {

        private boolean ok;
        private String nextPage = null;
        private NamedUserView namedUserView = null;
        private ImmutableSet<NamedUserView> namedUserViews = null;

        private Builder() {
        }

        public Builder setOk(boolean value) {
            this.ok = value;
            return this;
        }

        public Builder setNextPage(String value) {
            this.nextPage = value;
            return this;
        }

        public Builder setNamedUserView(NamedUserView value) {
            this.namedUserView = value;
            return this;
        }

        public Builder setNamedUserViews(ImmutableSet<NamedUserView> value) {
            this.namedUserViews = value;
            return this;
        }

        public NamedUserListingResponse build() {
            if (namedUserView != null) {
                Preconditions.checkArgument(namedUserViews == null);
            }

            return new NamedUserListingResponse(ok, Optional.fromNullable(nextPage), Optional.fromNullable(namedUserView), Optional.fromNullable(namedUserViews));
        }
    }
}