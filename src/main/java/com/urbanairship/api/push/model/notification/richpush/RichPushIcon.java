/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.richpush;

import com.google.common.base.Preconditions;

/**
 * Represents the a icon to be used in a rich push message.
 */
public final class RichPushIcon {


    private final String listIcon;

    private RichPushIcon(String listIcon) {
        this.listIcon = listIcon;
    }

    /**
     * New RichPushIcon Builder
     *
     * @return Builder
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Get the listIcon string representation of the URI/URL to the icon resource.
     *
     * @return String
     */
    public String getListIcon() {
        return listIcon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RichPushIcon that = (RichPushIcon) o;

        return listIcon != null ? listIcon.equals(that.listIcon) : that.listIcon == null;
    }

    @Override
    public int hashCode() {
        return listIcon != null ? listIcon.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RichPushIcon{" +
                "listIcon='" + listIcon + '\'' +
                '}';
    }

    /**
     * RichPushIcon Builder
     */
    public static final class Builder {
        private String listIcon;

        private Builder() {
        }

        /**
         * Set the listIcon string representation of the URI/URL to the icon resource.
         *
         * @param value String
         * @return RichPushIcon Builder
         */
        public Builder setListIcon(String value) {
            this.listIcon = value;
            return this;
        }


        public RichPushIcon build() {
            Preconditions.checkNotNull(listIcon, "Must supply a value for 'listIcon'");
            return new RichPushIcon(listIcon);
        }
    }
}
