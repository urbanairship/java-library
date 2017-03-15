/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

public class DeepLinkAction implements Action.OpenAction<String> {

    private final String link;
    private final Optional<String> fallbackUrl;

    public DeepLinkAction(String link) {
        this(link, Optional.<String>absent());
    }

    public DeepLinkAction(String link, Optional<String> fallbackUrl) {
        Preconditions.checkNotNull(link, "link should not be null.");
        Preconditions.checkNotNull(fallbackUrl, "fallbackUrl should not be null.");
        this.link = link;
        this.fallbackUrl = fallbackUrl;
    }

    @Override
    public int hashCode() {
        int result = (link != null ? link.hashCode() : 0);
        result = 31 * result + (fallbackUrl != null ? fallbackUrl.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DeepLinkAction other = (DeepLinkAction) obj;

        if(!fallbackUrl.equals(other.fallbackUrl)) return false;

        return Objects.equal(this.link, other.link);
    }

    @Override
    public String toString() {
        return "DeepLinkAction{" +
                "link='" + link + '\'' +
                ", fallbackUrl=" + fallbackUrl +
                '}';
    }

    @Override
    public String getValue() {
        return link;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPEN_DEEP_LINK;
    }

    public String getLink() {
        return link;
    }

    public Optional<String> getFallbackUrl() {
        return fallbackUrl;
    }
}
