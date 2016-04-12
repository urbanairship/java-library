/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class DeepLinkAction implements Action.OpenAction<String> {

    private final String link;

    public DeepLinkAction(String link) {
        Preconditions.checkNotNull(link, "link should not be null.");
        this.link = link;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(link);
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
        return Objects.equal(this.link, other.link);
    }

    @Override
    public String toString() {
        return "DeepLinkAction{" +
                "link='" + link + '\'' +
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
}
