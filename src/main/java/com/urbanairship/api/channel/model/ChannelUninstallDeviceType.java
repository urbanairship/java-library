/*
 * Copyright (c) 2013-2021.  Urban Airship and Contributors
 */

package com.urbanairship.api.channel.model;

import java.util.Optional;

    /**
     * Enum of authorized channel types for uninstall endpoint.
     *
     */
    public enum ChannelUninstallDeviceType {
        IOS("ios"),
        ANDROID("android"),
        WEB("web"),
        AMAZON("amazon"),
        OPEN("open");
    
        private final String identifier;

        private ChannelUninstallDeviceType() {
            this(null);
        }
    
        private ChannelUninstallDeviceType(String identifier) {
            this.identifier = identifier;
        }
    
        public static Optional<ChannelUninstallDeviceType> find(String identifier) {
            for (ChannelUninstallDeviceType channelType : values()) {
                if (channelType.getIdentifier().equals(identifier)) {
                    return Optional.of(channelType);
                }
            }
    
            return Optional.empty();
        }
    
        public String getIdentifier() {
            return identifier;
        }
    
    }