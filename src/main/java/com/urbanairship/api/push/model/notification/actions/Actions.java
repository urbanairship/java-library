/*
 * Copyright 2013 Urban Airship and Contributors
 */
package com.urbanairship.api.push.model.notification.actions;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.urbanairship.api.push.model.PushModelObject;

import java.util.Map;

/**
 * Actions
 *
 * @author <a href="mailto:n.sowen@2scale.net">Nils Sowen</a>
 */
public final class Actions extends PushModelObject {

    //\\//\\//\\//\\//\\//\\//\\//
    // contstants               //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // fields                   //
    //\\//\\//\\//\\//\\//\\//\\//

    private final Optional<ImmutableSet<String>> addTag;
    private final Optional<ImmutableSet<String>> removeTag;
    private final Optional<Open> open;
    private final Optional<ImmutableMap<String, String>> appDefined;

    //\\//\\//\\//\\//\\//\\//\\//
    // constructors             //
    //\\//\\//\\//\\//\\//\\//\\//

    private Actions(Optional<ImmutableSet<String>> addTag,
                    Optional<ImmutableSet<String>> removeTag,
                    Optional<Open> open,
                    Optional<ImmutableMap<String, String>> appDefined) {
        this.addTag = addTag;
        this.removeTag = removeTag;
        this.open = open;
        this.appDefined = appDefined;
    }

    //\\//\\//\\//\\//\\//\\//\\//
    // public methods           //
    //\\//\\//\\//\\//\\//\\//\\//

    public Optional<ImmutableSet<String>> getAddTag() {
        return addTag;
    }

    public Optional<ImmutableSet<String>> getRemoveTag() {
        return removeTag;
    }

    public Optional<Open> getOpen() {
        return open;
    }

    public Optional<ImmutableMap<String, String>> getAppDefined() {
        return appDefined;
    }

    public static Actions.Builder newBuilder() {
        return new Actions.Builder();
    }

    public static Actions deepLink(String deeplink) {
        return newBuilder().setOpen(Open.deepLink(deeplink)).build();
    }

    public static Actions url(String url) {
        return newBuilder().setOpen(Open.url(url)).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Actions actions = (Actions) o;

        if (addTag != null ? !addTag.equals(actions.addTag) : actions.addTag != null) return false;
        if (appDefined != null ? !appDefined.equals(actions.appDefined) : actions.appDefined != null) return false;
        if (open != null ? !open.equals(actions.open) : actions.open != null) return false;
        if (removeTag != null ? !removeTag.equals(actions.removeTag) : actions.removeTag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = addTag != null ? addTag.hashCode() : 0;
        result = 31 * result + (removeTag != null ? removeTag.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (appDefined != null ? appDefined.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Actions{");
        sb.append("addTag=").append(addTag);
        sb.append(", removeTag=").append(removeTag);
        sb.append(", open=").append(open);
        sb.append(", appDefined=").append(appDefined);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private ImmutableSet.Builder<String> addTag = null;
        private ImmutableSet.Builder<String> removeTag = null;
        private Open open;
        private ImmutableMap.Builder<String, String> appDefined = null;

        public Builder() { }

        public Builder adddAddTagEntry(String... tag) {
            if (addTag == null)
                addTag = ImmutableSet.builder();
            addTag.add(tag);
            return this;
        }

        public Builder addRemoveTagEntry(String... tag) {
            if (removeTag == null)
                removeTag = ImmutableSet.builder();
            removeTag.add(tag);
            return this;
        }

        public Builder setOpen(Open open) {
            this.open = open;
            return this;
        }

        /**
         * Add an extra key value pair to the notification payload. Maximum
         * payload is 256 bytes.
         * @param key String key
         * @param value String value
         * @return Builder
         */
        public Builder addAppDefinedEntry(String key, String value) {
            if (appDefined == null) {
                appDefined = ImmutableMap.builder();
            }
            this.appDefined.put(key, value);
            return this;
        }

        /**
         * Add key value pairs to payload. Maximum payload is 256 bytes.
         * @param entries Map of key value pairs
         * @return Builder.
         */
        public Builder addAllAppDefinedEntries(Map<String, String> entries) {
            if (appDefined == null) {
                appDefined = ImmutableMap.builder();
            }
            this.appDefined.putAll(entries);
            return this;
        }

        public Actions build() {
            return new Actions(addTag == null ? Optional.<ImmutableSet<String>>absent() : Optional.fromNullable(addTag.build()),
                               removeTag == null ? Optional.<ImmutableSet<String>>absent() : Optional.fromNullable(removeTag.build()),
                               Optional.fromNullable(open),
                               appDefined == null ? Optional.<ImmutableMap<String,String>>absent() : Optional.fromNullable(appDefined.build()));
        }
    }

        //\\//\\//\\//\\//\\//\\//\\//
    // protected methods        //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // package-private methods  //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // private methods          //
    //\\//\\//\\//\\//\\//\\//\\//

    //\\//\\//\\//\\//\\//\\//\\//
    // inner classes            //
    //\\//\\//\\//\\//\\//\\//\\//

}
