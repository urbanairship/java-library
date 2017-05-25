/*
 * Copyright (c) 2013-2016.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.notification.mpns;

import com.google.common.base.Optional;

/**
 * @deprecated Marked to be removed in 2.0.0. MPNS is no longer supported by the Urban Airship API.
 */
@Deprecated
public abstract class MPNSTileData
{
    private final Optional<String> id;
    private final Optional<String> title;
    private final Optional<Integer> count;

    protected MPNSTileData(Optional<String> id, Optional<String> title, Optional<Integer> count) {
        this.id = id;
        this.title = title;
        this.count = count;
    }

    public Optional<String> getTitle() {
        return this.title;
    }

    public Optional<String> getId() {
        return this.id;
    }

    public Optional<Integer> getCount() {
        return this.count;
    }

    public abstract String getTemplate();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MPNSTileData that = (MPNSTileData)o;
        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (count != null ? !count.equals(that.count) : that.count != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }
}
