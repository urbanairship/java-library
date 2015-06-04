/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import org.codehaus.jackson.node.ObjectNode;

public final class AppDefinedAction extends PushModelObject implements Action<ObjectNode> {

    private final ObjectNode payload;

    public AppDefinedAction(ObjectNode payload) {
        Preconditions.checkNotNull(payload, "'payload' cannot be null.");
        this.payload = payload;
    }

    @Override
    public ObjectNode getValue() {
        return payload;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.APP_DEFINED;
    }

    @Override
    public String toString() {
        return "AppDefinedAction{" +
                "payload=" + payload +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(payload);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AppDefinedAction other = (AppDefinedAction) obj;
        return Objects.equal(this.payload, other.payload);
    }
}
