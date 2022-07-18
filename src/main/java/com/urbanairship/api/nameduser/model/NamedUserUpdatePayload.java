package com.urbanairship.api.nameduser.model;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.urbanairship.api.channel.model.attributes.Attribute;

import java.util.List;

public class NamedUserUpdatePayload extends NamedUserModelObject {
    private final ImmutableList<Attribute> attributes;
    private final ImmutableMap<String, List<String>> addTags;
    private final ImmutableMap<String, List<String>> setTags;
    private final ImmutableMap<String, List<String>> removeTags;
    private final ImmutableList<NamedUserUpdateChannel> channels;
    private final NamedUserUpdateChannelAction action;

    private NamedUserUpdatePayload(ImmutableList<Attribute> attributes, 
    ImmutableMap<String, List<String>> addTags,
    ImmutableMap<String, List<String>> setTags,
    ImmutableMap<String, List<String>> removeTags,
    ImmutableList<NamedUserUpdateChannel> channels,
    NamedUserUpdateChannelAction action) {
        this.attributes = attributes;
        this.addTags = addTags;
        this.setTags = setTags;
        this.removeTags = removeTags;
        this.channels = channels;
        this.action = action;

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the list of channels to be associated or disassociated to the named user.
     *
     * @return ImmutableList of NamedUserUpdateChannel channels
     */
    public ImmutableList<NamedUserUpdateChannel> getChannels() {
        return channels;
    }

    /**
     * Get the list of attributes to be added to the named user.
     *
     * @return ImmutableList of Attribute attributes
     */
    public ImmutableList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Get the list of tags to be added to the named user.
     *
     * @return ImmutableMap of addTags
     */
    public ImmutableMap<String, List<String>> getAddTags() {
        return addTags;
    }

    /**
     * Get the list of tags to be set to the named user.
     *
     * @return ImmutableMap of setTags
     */
    public ImmutableMap<String, List<String>> getSetTags() {
        return setTags;
    }

    /**
     * Get the list of tags to be removed to the named user.
     *
     * @return ImmutableMap of removeTags
     */
    public ImmutableMap<String, List<String>> getRemoveTags() {
        return removeTags;
    }

    /**
     * Get the action to associate or disassociate the named user.
     *
     * @return NamedUserUpdateChannelAction removeTags
     */
    public NamedUserUpdateChannelAction getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NamedUserUpdatePayload that = (NamedUserUpdatePayload) o;
        return Objects.equal(attributes, that.attributes)
            && Objects.equal(channels, that.channels)
            && Objects.equal(addTags, that.addTags)
            && Objects.equal(setTags, that.setTags)
            && Objects.equal(removeTags, that.removeTags)
            && Objects.equal(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attributes, channels, addTags, setTags, removeTags, action);
    }

    @Override
    public String toString() {
        return "NamedUserUpdatePayload{" +
                "attributes=" + attributes +
                "channels=" + channels +
                "addTags=" + addTags +
                "setTags=" + setTags +
                "removeTags=" + removeTags +
                "action=" + action +
                '}';
    }

    /**
     * NamedUserUpdatePayload Builder
     */
    public static class Builder {
        ImmutableList.Builder<NamedUserUpdateChannel> namedUserUpdateChannelBuilder = ImmutableList.builder();
        ImmutableList.Builder<Attribute> attributeBuilder = ImmutableList.builder();
        ImmutableMap.Builder<String, List<String>> addTagBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, List<String>> setTagBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, List<String>> removeTagBuilder = ImmutableMap.builder();
        NamedUserUpdateChannelAction action;


        /**
         * Add a namedUserUpdateChannel to associate/disassociate with the named user.
         *
         * @param namedUserUpdatechannel NamedUserUpdateChannel
         * @return Builder
         */
        public Builder addNamedUserUpdateChannel(NamedUserUpdateChannel namedUserUpdatechannel){
            namedUserUpdateChannelBuilder.add(namedUserUpdatechannel);
            return this;
        }

        /**
         * Add an attribute to associate with the named user.
         *
         * @param attribute Attribute
         * @return Builder
         */
        public Builder addAttribute(Attribute attribute) {
            attributeBuilder.add(attribute);
            return this;
        }

         /**
         * Add a list of tags to associate with the named user.
         *
         * @param tagGroup String
         * @param addTags List
         * @return Builder
         */
        public Builder addTags(String tagGroup, List<String> addTags){
            addTagBuilder.put(tagGroup, addTags);
            return this;
        }

         /**
         * Set a list of tags to associate with the named user.
         *
         * @param tagGroup String
         * @param setTags List
         * @return Builder
         */
        public Builder setTags(String tagGroup, List<String> setTags){
            setTagBuilder.put(tagGroup, setTags);
            return this;
        }

        /**
         * Remove a list of tags to associate with the named user.
         *
         * @param tagGroup String
         * @param removeTags List
         * @return Builder
         */
        public Builder removeTags(String tagGroup, List<String> removeTags){
            removeTagBuilder.put(tagGroup, removeTags);
            return this;
        }

        /**
         * Set the action for associating or disassociating the named user.
         *
         * @param action NamedUserUpdateChannelAction
         * @return Builder
         */
        public Builder setAction(NamedUserUpdateChannelAction action){
            this.action = action;
            return this;
        }

        public NamedUserUpdatePayload build() {
            ImmutableList<NamedUserUpdateChannel> updateChannels = namedUserUpdateChannelBuilder.build();
            ImmutableList<Attribute> attributes = attributeBuilder.build();
            ImmutableMap<String, List<String>> addTags = addTagBuilder.build();
            ImmutableMap<String, List<String>> setTags = setTagBuilder.build();
            ImmutableMap<String, List<String>> removeTags = removeTagBuilder.build();

            Preconditions.checkArgument(updateChannels.size() > 0, "At least one channels must be set.");

            return new NamedUserUpdatePayload(
                attributes, 
                addTags,
                setTags,
                removeTags,
                updateChannels,
                action);
        }
    }
}
