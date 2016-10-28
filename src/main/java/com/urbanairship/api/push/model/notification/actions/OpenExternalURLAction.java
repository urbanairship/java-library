/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

import java.net.URI;

public class OpenExternalURLAction extends PushModelObject implements Action.OpenAction<URI> {
    private final URI uri;

    public OpenExternalURLAction(URI uri) {
        Preconditions.checkNotNull(uri, "uri should not be null.");
        this.uri = uri;
    }

    @Override
    public URI getValue() {
        return uri;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.OPEN_EXTERNAL_URL;
    }

    @Override
    public String toString() {
        return "OpenExternalURLAction{" +
                "data=" + uri +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uri);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OpenExternalURLAction other = (OpenExternalURLAction) obj;
        return Objects.equal(this.uri, other.uri);
    }
}
