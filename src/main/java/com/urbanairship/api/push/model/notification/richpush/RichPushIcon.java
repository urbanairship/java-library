/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.richpush;

import com.google.common.base.Preconditions;

public final class RichPushIcon {


    private final String listIcon;

    private RichPushIcon(String listIcon) {
        this.listIcon = listIcon;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

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

    public static final class Builder {
        private String listIcon;

        private Builder() {
        }

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
