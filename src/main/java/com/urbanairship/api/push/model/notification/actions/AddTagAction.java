/*
 * Copyright (c) 2013-2015.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;

public final class AddTagAction extends PushModelObject implements Action<TagActionData> {

    private final TagActionData tagData;

    public AddTagAction(TagActionData tagData) {
        Preconditions.checkNotNull(tagData, "tagData should not be null.");
        this.tagData = tagData;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tagData);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final AddTagAction other = (AddTagAction) obj;
        return Objects.equal(this.tagData, other.tagData);
    }

    @Override
    public TagActionData getValue() {
        return tagData;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.ADD_TAG;
    }

    @Override
    public String toString() {
        return "AddTagAction{" +
                "tagData=" + tagData +
                '}';
    }
}
