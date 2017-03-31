
/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.urbanairship.api.push.model.PushModelObject;

public class ShareAction extends PushModelObject implements Action<String> {

    private final String shareText;

    public ShareAction(String shareText) {
        this.shareText = shareText;
    }

    @Override
    public String getValue() {
        return shareText;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.SHARE;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shareText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShareAction that = (ShareAction) o;
        return Objects.equal(shareText, that.shareText);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("shareText", shareText)
                .toString();
    }
}
