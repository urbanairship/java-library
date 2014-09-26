package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Preconditions;
import com.urbanairship.api.push.model.PushModelObject;
import com.google.common.base.Objects;

public final class RemoveTagAction extends PushModelObject implements Action<TagActionData> {

    private final TagActionData tagData;

    public RemoveTagAction(TagActionData tagData) {
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
        final RemoveTagAction other = (RemoveTagAction) obj;
        return Objects.equal(this.tagData, other.tagData);
    }

    @Override
    public TagActionData getValue() {
        return tagData;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.REMOVE_TAG;
    }

    @Override
    public String toString() {
        return "RemoveTagAction{" +
                "tagData=" + tagData +
                '}';
    }

}
