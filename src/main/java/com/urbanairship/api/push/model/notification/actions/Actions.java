/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.Lists;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Sets;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the actions specified with the
 * notification. At least one action will
 * always be present.
 */
public class Actions extends PushModelObject {

    private final ClassToInstanceMap<Action> actions;

    private Actions(ClassToInstanceMap<Action> actions) {
        this.actions = ImmutableClassToInstanceMap.copyOf(actions);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return newBuilder().mergeFrom(this);
    }

    public Optional<AppDefinedAction> getAppDefined() {
        return Optional.ofNullable(actions.getInstance(AppDefinedAction.class));
    }

    public Optional<AddTagAction> getAddTags() {
        return Optional.ofNullable(actions.getInstance(AddTagAction.class));
    }

    public Optional<RemoveTagAction> getRemoveTags() {
        return Optional.ofNullable(actions.getInstance(RemoveTagAction.class));
    }

    public Optional<Action.OpenAction> getOpenAction() {
        return Optional.ofNullable(actions.getInstance(Action.OpenAction.class));
    }

    public Iterable<? extends Action> allActions() {
        return actions.values();
    }

    @Override
    public String toString() {
        return "Actions{" +
                "actions=" + actions +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(actions);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Actions other = (Actions) obj;
        return Objects.equal(this.actions, other.actions);
    }

    public static class Builder {

        private ClassToInstanceMap<Action> actions = MutableClassToInstanceMap.create();

        public Builder mergeFrom(Actions other) {
            actions.putAll(other.actions);
            return this;
        }

        public Builder addTags(AddTagAction addTags) {
            this.actions.put(AddTagAction.class, addTags);
            return this;
        }

        public Builder removeTags(RemoveTagAction removeTags) {
            this.actions.put(RemoveTagAction.class, removeTags);
            return this;
        }

        public Builder setOpen(Action.OpenAction open) {
            this.actions.put(Action.OpenAction.class, open);
            return this;
        }

        public Builder addAppDefined(AppDefinedAction appDefined) {
            this.actions.put(AppDefinedAction.class, appDefined);
            return this;
        }

        public Builder setShare(ShareAction shareAction) {
            actions.put(ShareAction.class, shareAction);
            return this;
        }

        public Actions build() {
            Preconditions.checkArgument(actions.size() != 0, "'actions' must contain at least one action.");

            for (Class<? extends Action> action : actions.keySet()) {
                Preconditions.checkNotNull(actions.getInstance(action), action.getName() + " cannot have a null entry in actions.");
            }

            // Determine if the same tag is used both add and remove
            // actions.
            if (actions.containsKey(AddTagAction.class) && actions.containsKey(RemoveTagAction.class)) {
                TagActionData addTagData = actions.getInstance(AddTagAction.class).getValue();
                TagActionData removeTagData = actions.getInstance(RemoveTagAction.class).getValue();

                Set<String> added = addTagData.isSingle() ?
                        Sets.newHashSet(addTagData.getSingleTag()) :
                        addTagData.getTagSet();
                Set<String> removed = removeTagData.isSingle() ?
                        Sets.newHashSet(removeTagData.getSingleTag()) :
                        removeTagData.getTagSet();

                for (String addedTag : added) {
                    Preconditions.checkArgument(!removed.contains(addedTag), "The same tag cannot be specified in the 'add_tag' and 'remove'tag' actions at the same time: " + addedTag);
                }
            }

            // Check for app_defined collisions with top-level actions (or for the 'short name' associated
            // with an action).
            //
            // See the API v3 spec for complete details.
            if (actions.containsKey(AppDefinedAction.class) && actions.size() > 1) {
                List<String> fieldNames = Lists.newArrayList(actions.getInstance(AppDefinedAction.class).getValue().fieldNames());
                for (Class<? extends Action> key : actions.keySet()) {
                    if (key == AppDefinedAction.class) {
                        continue;
                    }

                    Action a = actions.getInstance(key);

                    String actionFieldName = ActionNameRegistry.INSTANCE.getFieldName(a.getActionType());

                    Preconditions.checkArgument(!fieldNames.contains(actionFieldName), "app_defined field '" + actionFieldName + "' collides with top level action.");

                    String actionShortName = ActionNameRegistry.INSTANCE.getShortName(a.getActionType());

                    Preconditions.checkArgument(!fieldNames.contains(actionShortName), "app_defined field '" + actionShortName + "' collides with top level action's 'short name'.");

                }
            }

            return new Actions(actions);
        }
    }
}
