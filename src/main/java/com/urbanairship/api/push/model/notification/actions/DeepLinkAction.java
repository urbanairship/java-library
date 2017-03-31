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

    public DeepLinkAction(String link, String fallbackURL) {
        this(link, Optional.fromNullable(fallbackURL));
    }

    private DeepLinkAction(String link, Optional<String> fallbackURL) {
        Preconditions.checkNotNull(link, "link should not be null.");
        Preconditions.checkNotNull(fallbackURL, "fallbackURL should not be null.");

        this.link = link;
        this.fallbackUrl = fallbackURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DeepLinkAction that = (DeepLinkAction) o;

        return Objects.equal(link, that.link) &&
                Objects.equal(fallbackUrl, that.fallbackUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(link, fallbackUrl);
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
