/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.push.model.audience;

import com.urbanairship.api.push.model.DeviceType;
import com.urbanairship.api.push.model.audience.location.DateRange;
import com.urbanairship.api.push.model.audience.location.DateRangeUnit;
import com.urbanairship.api.push.model.audience.location.RecentDateRange;
import com.urbanairship.api.push.model.audience.location.LocationSelector;
import com.urbanairship.api.push.model.audience.location.LocationIdentifier;
import com.urbanairship.api.push.model.audience.location.PresenceTimeframe;
import com.google.common.collect.ImmutableList;
import java.util.Collection;

/**
 * A collection of factory methods for building audience selector
 * expressions.
 */
public class Selectors {

    public static final Selector atomic(SelectorType type) {
        return BasicSelector.newBuilder()
            .setType(type)
            .build();
    }

    public static final Selector value(SelectorType type, String value) {
        return BasicValueSelector.newBuilder()
            .setType(type)
            .setValue(value)
            .build();
    }

    public static final Selector compound(SelectorType type, final Selector ... children) {
        BasicCompoundSelector.Builder builder = BasicCompoundSelector.newBuilder()
            .setType(type);
        for (Selector child : children) {
            builder.addSelector(child);
        }
        return builder.build();
    }

    public static final Selector compound(SelectorType type, final Collection<Selector> children) {
        return compound(type, children.toArray(new Selector[0]));
    }

    public static final Selector compound(SelectorType type, SelectorType childType, final String ... values) {
        ImmutableList.Builder<Selector> children = ImmutableList.builder();
        for (String value : values) {
            children.add(BasicValueSelector.newBuilder()
                         .setType(childType)
                         .setValue(value)
                         .build());
        }
        return BasicCompoundSelector.newBuilder()
            .setType(type)
            .addAllSelectors(children.build())
            .build();
    }

    public static final Selector compound(SelectorType type, SelectorType childType, final Collection<String> values) {
        return compound(type, childType, values.toArray(new String[0]));
    }

    /* Atomic */

    public static final Selector broadcast() {
        return atomic(SelectorType.ALL);
    }

    public static final Selector all() {
        return atomic(SelectorType.ALL);
    }

    public static final Selector triggered() {
        return atomic(SelectorType.TRIGGERED);
    }

    /* Tags */

    public static final Selector tag(String tag) {
        return value(SelectorType.TAG, tag);
    }

    public static final Selector tags(String ... tags) {
        return compound(SelectorType.OR, SelectorType.TAG, tags);
    }

    public static final Selector tags(Collection<String> tags) {
        return compound(SelectorType.OR, SelectorType.TAG, tags);
    }

    public static final Selector tagWithClass(String tag, String tagClass) {
        return BasicValueSelector.newBuilder()
            .setType(SelectorType.TAG)
            .setValue(tag)
            .addAttribute("tag_class", tagClass)
            .build();
    }

    public static final Selector autogroup(int value) {
        return autogroup(Integer.toString(value));
    }

    public static final Selector autogroup(String value) {
        return tagWithClass(value, "autogroup");
    }

    /* Aliases */

    public static final Selector alias(String alias) {
        return value(SelectorType.ALIAS, alias);
    }

    public static final Selector aliases(String ... aliases) {
        return compound(SelectorType.OR, SelectorType.ALIAS, aliases);
    }

    public static final Selector aliases(Collection<String> aliases) {
        return compound(SelectorType.OR, SelectorType.ALIAS, aliases);
    }

    /* Segments */

    public static final Selector segment(String segment) {
        return value(SelectorType.SEGMENT, segment);
    }

    public static final Selector segments(String ... segments) {
        return compound(SelectorType.OR, SelectorType.SEGMENT, segments);
    }

    public static final Selector segments(Collection<String> segments) {
        return compound(SelectorType.OR, SelectorType.SEGMENT, segments);
    }

    /* Device tokens */

    public static final Selector deviceToken(String deviceToken) {
        return value(SelectorType.DEVICE_TOKEN, deviceToken);
    }

    public static final Selector deviceTokens(String ... deviceTokens) {
        return compound(SelectorType.OR, SelectorType.DEVICE_TOKEN, deviceTokens);
    }

    public static final Selector deviceTokens(Collection<String> deviceTokens) {
        return compound(SelectorType.OR, SelectorType.DEVICE_TOKEN, deviceTokens);
    }

    /* iOS channels */

    public static final Selector iosChannel(String iosChannel) {
        return value(SelectorType.IOS_CHANNEL, iosChannel);
    }

    public static final Selector iosChannel(String ... iosChannels) {
        return compound(SelectorType.OR, SelectorType.IOS_CHANNEL, iosChannels);
    }

    public static final Selector iosChannel(Collection<String> iosChannels) {
        return compound(SelectorType.OR, SelectorType.IOS_CHANNEL, iosChannels);
    }

    /* Device pins */

    public static final Selector devicePin(String devicePin) {
        return value(SelectorType.DEVICE_PIN, devicePin);
    }

    public static final Selector devicePins(String ... devicePins) {
        return compound(SelectorType.OR, SelectorType.DEVICE_PIN, devicePins);
    }

    public static final Selector devicePins(Collection<String> devicePins) {
        return compound(SelectorType.OR, SelectorType.DEVICE_PIN, devicePins);
    }

    /* APIDs */

    public static final Selector apid(String apid) {
        return value(SelectorType.APID, apid);
    }

    public static final Selector apids(String ... apids) {
        return compound(SelectorType.OR, SelectorType.APID, apids);
    }

    public static final Selector apids(Collection<String> apids) {
        return compound(SelectorType.OR, SelectorType.APID, apids);
    }

    /* Android channels */

    public static final Selector androidChannel(String androidChannel) {
        return value(SelectorType.ANDROID_CHANNEL, androidChannel);
    }

    public static final Selector androidChannels(String ... androidChannels) {
        return compound(SelectorType.OR, SelectorType.ANDROID_CHANNEL, androidChannels);
    }

    public static final Selector androidChannels(Collection<String> androidChannels) {
        return compound(SelectorType.OR, SelectorType.ANDROID_CHANNEL, androidChannels);
    }

    /* WNS APIDs */

    public static final Selector wns(String wns) {
        return value(SelectorType.WNS, wns);
    }

    public static final Selector wnsDevices(String ... ids) {
        return compound(SelectorType.OR, SelectorType.WNS, ids);
    }

    public static final Selector wnsDevices(Collection<String> ids) {
        return compound(SelectorType.OR, SelectorType.WNS, ids);
    }

    /* MPNS APIDs */

    public static final Selector mpns(String mpns) {
        return value(SelectorType.MPNS, mpns);
    }

    public static final Selector mpnsDevices(String ... ids) {
        return compound(SelectorType.OR, SelectorType.MPNS, ids);
    }

    public static final Selector mpnsDevices(Collection<String> ids) {
        return compound(SelectorType.OR, SelectorType.MPNS, ids);
    }

    /* Amazon Channels */

    public static final Selector amazon(String id) {
        return value(SelectorType.AMAZON_CHANNEL, id);
    }

    public static final Selector amazonDevices(String ... ids) {
        return compound(SelectorType.OR, SelectorType.AMAZON_CHANNEL, ids);
    }

    public static final Selector amazonDevices(Collection<String> ids) {
        return compound(SelectorType.OR, SelectorType.AMAZON_CHANNEL, ids);
    }

    /* Logical operators */

    public static final Selector or(Selector ... children) {
        return compound(SelectorType.OR, children);
    }

    public static final Selector or(Collection<Selector> children) {
        return compound(SelectorType.OR, children);
    }

    public static final Selector or(SelectorType childType, String ... values) {
        return compound(SelectorType.OR, childType, values);
    }

    public static final Selector or(SelectorType childType, Collection<String> values) {
        return compound(SelectorType.OR, childType, values);
    }

    public static final Selector and(Selector ... children) {
        return compound(SelectorType.AND, children);
    }

    public static final Selector and(Collection<Selector> children) {
        return compound(SelectorType.AND, children);
    }

    public static final Selector not(Selector child) {
        return compound(SelectorType.NOT, child);
    }

    /* Location */

    public static final Selector location(String id, DateRange range) {
        return LocationSelector.newBuilder()
            .setId(LocationIdentifier.newBuilder()
                   .setId(id)
                   .build())
            .setDateRange(range)
            .build();
    }


    public static final DateRange minutes(int units) {
        return minutes(units, PresenceTimeframe.ANYTIME);
    }

    public static final DateRange minutes(int units, PresenceTimeframe timeframe) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.MINUTES)
            .setTimeframe(timeframe)
            .setUnits(units)
            .build();
    }

    public static final DateRange minutesLastSeen(int units) {
        return minutes(units, PresenceTimeframe.LAST_SEEN);
    }

    public static final DateRange hours(int units) {
        return hours(units, PresenceTimeframe.ANYTIME);
    }

    public static final DateRange hours(int units, PresenceTimeframe timeframe) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.HOURS)
            .setTimeframe(timeframe)
            .setUnits(units)
            .build();
    }

    public static final DateRange hoursLastSeen(int units) {
        return hours(units, PresenceTimeframe.LAST_SEEN);
    }

    public static final DateRange days(int units) {
        return days(units, PresenceTimeframe.ANYTIME);
    }

    public static final DateRange days(int units, PresenceTimeframe timeframe) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.DAYS)
            .setTimeframe(timeframe)
            .setUnits(units)
            .build();
    }

    public static final DateRange daysLastSeen(int units) {
        return days(units, PresenceTimeframe.LAST_SEEN);
    }

    public static final DateRange weeks(int units) {
        return weeks(units, PresenceTimeframe.ANYTIME);
    }

    public static final DateRange weeks(int units, PresenceTimeframe timeframe) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.WEEKS)
            .setTimeframe(timeframe)
            .setUnits(units)
            .build();
    }

    public static final DateRange weeksLastSeen(int units) {
        return weeks(units, PresenceTimeframe.LAST_SEEN);
    }

    public static final DateRange months(int units) {
        return months(units, PresenceTimeframe.ANYTIME);
    }

    public static final DateRange months(int units, PresenceTimeframe timeframe) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.MONTHS)
            .setTimeframe(timeframe)
            .setUnits(units)
            .build();
    }

    public static final DateRange monthsLastSeen(int units) {
        return months(units, PresenceTimeframe.LAST_SEEN);
    }

    public static final DateRange years(int units) {
        return years(units, PresenceTimeframe.ANYTIME);
    }

    public static final DateRange years(int units, PresenceTimeframe timeframe) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.YEARS)
            .setTimeframe(timeframe)
            .setUnits(units)
            .build();
    }

    public static final DateRange yearsLastSeen(int units) {
        return years(units, PresenceTimeframe.LAST_SEEN);
    }
}
