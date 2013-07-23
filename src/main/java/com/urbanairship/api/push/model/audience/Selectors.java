package com.urbanairship.api.push.model.audience;

import com.urbanairship.api.push.model.audience.location.*;
import com.urbanairship.api.push.model.audience.location.PresenceTimeFrame;
import com.google.common.collect.ImmutableList;
import java.util.Collection;

/**
 * A collection of factory methods for building audience selector
 * expressions.
 */
public class Selectors {

    /**
     * Create an atomic selector of the given type. Atomic expressions are
     * combined to form compound expressions.
     * @param type SelectorType from the SelectorCategory.ATOMIC
     * @return BasicSelector
     */
    public static final Selector atomic(SelectorType type) {
        return BasicSelector.newBuilder()
            .setType(type)
            .build();
    }

    /**
     * Create a value selector with a VALUE type. Value selectors are atomic
     * selectors that express a specific value, such as tag, device_token, alias
     * segment, etc.
     *
     * <pre>
     * Example:
     * {@code
     * Selectors.value(SelectorType.DEVICE_TOKEN, "deviceToken")
     * }
     * </pre>
     *
     * @param type SelectorType from the SelectorCategory.VALUE
     * @param value Value for the Selector
     * @return BasicValueSelector
     */
    public static final Selector value(SelectorType type, String value) {
        return BasicValueSelector.newBuilder()
            .setType(type)
            .setValue(value)
            .build();
    }

    /**
     * Create a compound selector with a single COMPOUND type and at least one
     * child selector.
     *
     * @param type SelectorType from the COMPOUND category
     * @param children 1..N additional selectors
     * @return BasicCompoundSelectors
     */
    public static final Selector compound(SelectorType type, final Selector ... children) {
        BasicCompoundSelector.Builder builder = BasicCompoundSelector.newBuilder()
            .setType(type);
        for (Selector child : children) {
            builder.addSelector(child);
        }
        return builder.build();
    }

    /**
     * Create a compound selector from a single COMPOUND type and a collection
     * of children
     *
     * @param type SelectorType from the COMPOUND category.
     * @param children 1..N collection of children
     * @return BasicCompoundSelector
     */
    public static final Selector compound(SelectorType type, final Collection<Selector> children) {
        return compound(type, children.toArray(new Selector[children.size()]));
    }

    /**
     * Create a compound selector from a single COMPOUND type and multiple
     * child types. The final list of values will be used to create multiple
     * child types.
     * @param type SelectorType from the COMPOUND category.
     * @param childType SelectorType from the COMPOUND category that will be matched
     *                  with the values in the values list.
     * @param values List of values for child types.
     * @return BasicCompoundSelector
     */
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

    /**
     * Create a compound selector from a single COMPOUND type and multiple
     * child types. The final collection of values will be used to create multiple
     * child types.
     * @param type SelectorType from the COMPOUND category.
     * @param childType SelectorType from the COMPOUND category that will be matched
     *                  with the values in the values collection.
     * @param values Collection of child values.
     * @return BasicCompoundSelector
     */
    public static final Selector compound(SelectorType type, SelectorType childType, final Collection<String> values) {
        return compound(type, childType, values.toArray(new String[values.size()]));
    }

    /* Atomic */

    /**
     * Selector for a broadcast push. (Push to all devices)
     * @return Selector
     */
    public static final Selector broadcast() {
        return atomic(SelectorType.ALL);
    }

    /**
     * Selector for all.
     * @return Selector
     */
    public static final Selector all() {
        return atomic(SelectorType.ALL);
    }

//    /**
//     * Selector for triggered pushes.
//     * @return Selector
//     */
//    public static final Selector triggered() {
//        return atomic(SelectorType.TRIGGERED);
//    }

    /* Tags */

    /**
     * Selector for the given tag
     * @param tag Tag value for the selector.
     * @return Selector
     */
    public static final Selector tag(String tag) {
        return value(SelectorType.TAG, tag);
    }

    /**
     * Compound OR selector for multiple tags
     * @param tags Tag values for the selector
     * @return Selector
     */
    public static final Selector tags(String ... tags) {
        return compound(SelectorType.OR, SelectorType.TAG, tags);
    }

    /**
     * Compound OR selector for multiple tags.
     * @param tags Collection of tag values.
     * @return Selector
     */
    public static final Selector tags(Collection<String> tags) {
        return compound(SelectorType.OR, SelectorType.TAG, tags);
    }

//    /**
//     * Tag with given class
//     * @param tag Tag value
//     * @param tagClass Tag class value
//     * @return Selector
//     */
//    public static final Selector tagWithClass(String tag, String tagClass) {
//        return BasicValueSelector.newBuilder()
//            .setType(SelectorType.TAG)
//            .setValue(tag)
//            .addAttribute("tag_class", tagClass)
//            .build();
//    }

//    /**
//     * Autogroup selector
//     * @param value Value that identifies the autogroup.
//     * @return
//     */
//    public static final Selector autogroup(int value) {
//        return autogroup(Integer.toString(value));
//    }
//
//
//    public static final Selector autogroup(String value) {
//        return tagWithClass(value, "autogroup");
//    }

    /* Aliases */

    /**
     * Selector with the given alias
     * @param alias Alias value
     * @return Selector
     */
    public static final Selector alias(String alias) {
        return value(SelectorType.ALIAS, alias);
    }

    /**
     * Selector with the given alias in a compound OR
     *
     * @param aliases Aliases
     * @return Selector
     */
    public static final Selector aliases(String ... aliases) {
        return compound(SelectorType.OR, SelectorType.ALIAS, aliases);
    }

    /**
     * Selector with the given aliases in a compound OR.
     * @param aliases Collection of alias values.
     * @return Selector
     */
    public static final Selector aliases(Collection<String> aliases) {
        return compound(SelectorType.OR, SelectorType.ALIAS, aliases);
    }

    /* Segments */

    /*
     *
     * @param segment
     * @return
     */
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

    /**
     * Selector with the given iOS device token.
     * @param deviceToken Device token as a string
     * @return Selector
     *
     */
    public static final Selector deviceToken(String deviceToken) {
        return value(SelectorType.DEVICE_TOKEN, deviceToken);
    }

    /**
     * Selector with iOS device tokens in a compound OR.
     * @param deviceTokens Device tokens as strings
     * @return Selector
     */
    public static final Selector deviceTokens(String ... deviceTokens) {
        return compound(SelectorType.OR, SelectorType.DEVICE_TOKEN, deviceTokens);
    }

    /**
     * Selector with iOS device tokens from given Collection
     * @param deviceTokens Collection of device token values.
     * @return Selector
     */
    public static final Selector deviceTokens(Collection<String> deviceTokens) {
        return compound(SelectorType.OR, SelectorType.DEVICE_TOKEN, deviceTokens);
    }

    /* Device pins */

    /**
     * Selector with given Blackberry device pin.
     * @param devicePin Device pin.
     * @return Selector
     */
    public static final Selector devicePin(String devicePin) {
        return value(SelectorType.DEVICE_PIN, devicePin);
    }

    /**
     * Selector with given Blackberry device pins
     *
     * @param devicePins Device pins.
     * @return Selector
     */
    public static final Selector devicePins(String ... devicePins) {
        return compound(SelectorType.OR, SelectorType.DEVICE_PIN, devicePins);
    }

    /**
     * Selector with given Blackberry device pins from the Collection.
     * @param devicePins Blackberry device pins
     * @return Selector
     */
    public static final Selector devicePins(Collection<String> devicePins) {
        return compound(SelectorType.OR, SelectorType.DEVICE_PIN, devicePins);
    }

    /* APIDs */

    /**
     * Selector with APID
     * @param apid  APID
     * @return Selector
     */
    public static final Selector apid(String apid) {
        return value(SelectorType.APID, apid);
    }

    /**
     * Selector with APIDs
     * @param apids APIDS
     * @return Selector
     */
    public static final Selector apids(String ... apids) {
        return compound(SelectorType.OR, SelectorType.APID, apids);
    }

    /**
     * Selector with APIDs from Collection
     * @param apids Collection of APIDs
     * @return Selector
     */
    public static final Selector apids(Collection<String> apids) {
        return compound(SelectorType.OR, SelectorType.APID, apids);
    }

    /* WNS APIDs */

    /**
     * Selector with WNS id.
     * @param wns Id.
     * @return Selector
     */
    public static final Selector wns(String wns) {
        return value(SelectorType.WNS, wns);
    }

    /**
     * Selector with WNS ids combined in a compound OR
     * @param ids Ids
     * @return Selector
     */
    public static final Selector wnsDevices(String ... ids) {
        return compound(SelectorType.OR, SelectorType.WNS, ids);
    }

    /**
     * Selector with WNS ids combined in a compound OR
     * @param ids Ids
     * @return Selector
     */
    public static final Selector wnsDevices(Collection<String> ids) {
        return compound(SelectorType.OR, SelectorType.WNS, ids);
    }

    /* MPNS APIDs */

    /**
     * Selector with MPNS id
     * @param mpns Id
     * @return Selector
     */
    public static final Selector mpns(String mpns) {
        return value(SelectorType.MPNS, mpns);
    }

    /**
     * Selector with MPNS ids combined in a compound OR.
     * @param ids Ids
     * @return Selector
     */
    public static final Selector mpnsDevices(String ... ids) {
        return compound(SelectorType.OR, SelectorType.MPNS, ids);
    }

    /**
     * Selector with MPNS ids combined in a compound OR
     * @param ids Collection of Ids
     * @return Selector
     */
    public static final Selector mpnsDevices(Collection<String> ids) {
        return compound(SelectorType.OR, SelectorType.MPNS, ids);
    }

    /* ADM APIDs */

    /**
     * Selector with ADM (Amazon Device Messaging) id.
     * @param adm Id
     * @return
     */
    public static final Selector adm(String adm) {
        return value(SelectorType.ADM, adm);
    }
    /**
     * Selector with ADM (Amazon Device Messaging) ids.
     * @param ids Ids
     * @return Selector
     */
    public static final Selector admDevices(String ... ids) {
        return compound(SelectorType.OR, SelectorType.ADM, ids);
    }

    /**
     * Selector with ADM (Amazon Device Messaging) ids.
     * @param ids Ids from Collection
     * @return Selector
     */
    public static final Selector admDevices(Collection<String> ids) {
        return compound(SelectorType.OR, SelectorType.ADM, ids);
    }

    /* Logical operators */

    /**
     * Combines the given Selectors in an OR Selector
     *
     * <pre>
     *     {@code
     *     Selector orSl = Selectors.or(SelectorType.DEVICE_TOKEN,
     *                                 deviceToken1, deviceToken2);
     *     Would create or : [deviceToken1, deviceToken2]
     *     }
     * </pre>
     * @param children Selectors that will be combined
     * @return Selector
     */
    public static final Selector or(Selector ... children) {
        return compound(SelectorType.OR, children);
    }

    /**
     * Selector with given Selectors in an OR Selector.
     * @param children Selectors that will be combined.
     * @return Selector
     */
    public static final Selector or(Collection<Selector> children) {
        return compound(SelectorType.OR, children);
    }

    /**
     * Selector with multiple values of the childType combined in an OR
     * @param childType Selector type for children
     * @param values Values for child selectors
     * @return Selector
     */
    public static final Selector or(SelectorType childType, String ... values) {
        return compound(SelectorType.OR, childType, values);
    }

    /**
     * Selector with multiple values of the childType combined in an OR
     * @param childType Selector type for children
     * @param values Collection of values for child selectors
     * @return Selector
     */
    public static final Selector or(SelectorType childType, Collection<String> values) {
        return compound(SelectorType.OR, childType, values);
    }

    /**
     * Selector with given selectors combined with an AND
     * @param children Selectors that will be combined.
     * @return Selector
     */
    public static final Selector and(Selector ... children) {
        return compound(SelectorType.AND, children);
    }

    /**
     * Selector with given selectors combined with an AND
     * @param children Collection of child selectors
     * @return Selector
     */
    public static final Selector and(Collection<Selector> children) {
        return compound(SelectorType.AND, children);
    }

    /**
     * Not selector with given selector
     * @param child Selector for not
     * @return Selector
     */
    public static final Selector not(Selector child) {
        return compound(SelectorType.NOT, child);
    }

    /* Location */

    /**
     * Location selector with given id and date range
     * @param id Location id
     * @param range Date range for location
     * @return Selector
     */
    public static final Selector location(String id, DateRange range) {
        return LocationSelector.newBuilder()
            .setId(LocationIdentifier.newBuilder()
                   .setId(id)
                   .build())
            .setDateRange(range)
            .build();
    }

    /**
     * DateRange from the given minutes
     * @param units Int value representing minutes
     * @return DateRange
     */
    public static final DateRange minutes(int units) {
        return minutes(units, PresenceTimeFrame.ANYTIME);
    }

    /**
     * DateRange with given minutes and PresenceTimeFrame. The time frame denotes
     * whether the DateRange covers the last seen option or the any time option.
     *
     * @param units Int value representing minutes.
     * @param timeFrame PresenceTimeFrame
     * @return DateRange
     */
    public static final DateRange minutes(int units, PresenceTimeFrame timeFrame) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.MINUTES)
            .setTimeframe(timeFrame)
            .setUnits(units)
            .build();
    }

    /**
     * DateRange consisting of minutes with a LAST_SEEN time frame. A match will
     * occur if the device was last seen at the location within the DateRange.
     * @param units Int value representing minutes
     * @return DateRange
     */
    public static final DateRange minutesLastSeen(int units) {
        return minutes(units, PresenceTimeFrame.LAST_SEEN);
    }

    /**
     * DateRange consisting of minutes with a ANYTIME time frame. A match will
     * occur if the device was at the location at any time within the DateRange.
     * @param units Int value representing minutes
     * @return DateRange
     */
    public static final DateRange hours(int units) {
        return hours(units, PresenceTimeFrame.ANYTIME);
    }

    /**
     * DateRange with given hours and PresenceTimeFrame. The time frame denotes
     * whether the DateRange covers the last seen option or the any time option.
     *
     * @param units Int value representing hours.
     * @param timeFrame PresenceTimeFrame
     * @return DateRange
     */
    public static final DateRange hours(int units, PresenceTimeFrame timeFrame) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.HOURS)
            .setTimeframe(timeFrame)
            .setUnits(units)
            .build();
    }

    /**
     * DateRange representing hours with a LAST_SEEN PresenceTimeFrame
     * @param units Int representing hours
     * @return DateRange
     */
    public static final DateRange hoursLastSeen(int units) {
        return hours(units, PresenceTimeFrame.LAST_SEEN);
    }

    /**
     * DateRange representing days with an ANYTIME PresenceTimeFrame
     * @param units Int representing days
     * @return DateRange
     */
    public static final DateRange days(int units) {
        return days(units, PresenceTimeFrame.ANYTIME);
    }

    /**
     * DateRange with given days and PresenceTimeFrame. The time frame denotes
     * whether the DateRange covers the last seen option or the any time option.
     *
     * @param units Int value representing days.
     * @param timeFrame PresenceTimeFrame
     * @return DateRange
     */
    public static final DateRange days(int units, PresenceTimeFrame timeFrame) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.DAYS)
            .setTimeframe(timeFrame)
            .setUnits(units)
            .build();
    }

    /**
     * DateRange representing days with a LAST_SEEN PresenceTimeFrame
     * @param units Ints representing days
     * @return DateRange
     */
    public static final DateRange daysLastSeen(int units) {
        return days(units, PresenceTimeFrame.LAST_SEEN);
    }

    /**
     * DateRange representing weeks with an ANYTIME PresenceTimeFrame
     * @param units Int representing weeks
     * @return DateRange
     */
    public static final DateRange weeks(int units) {
        return weeks(units, PresenceTimeFrame.ANYTIME);
    }

    /**
     * DateRange with given weeks and PresenceTimeFrame. The time frame denotes
     * whether the DateRange covers the last seen option or the any time option.
     *
     * @param units Int value representing weeks.
     * @param timeFrame PresenceTimeFrame
     * @return DateRange
     */
    public static final DateRange weeks(int units, PresenceTimeFrame timeFrame) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.WEEKS)
            .setTimeframe(timeFrame)
            .setUnits(units)
            .build();
    }

    /**
     * DateRange representing weeks with a LAST_SEEN PresenceTimeFrame
     * @param units Int representing weeks
     * @return DateRange
     */
    public static final DateRange weeksLastSeen(int units) {
        return weeks(units, PresenceTimeFrame.LAST_SEEN);
    }

    /**
     * DateRange representing months with and ANYTIME PresenceTimeFrame
     * @param units Int representing months
     * @return DateRange
     */
    public static final DateRange months(int units) {
        return months(units, PresenceTimeFrame.ANYTIME);
    }

    /**
     * DateRange with given minutes and PresenceTimeFrame. The time frame denotes
     * whether the DateRange covers the last seen option or the any time option.
     *
     * @param units Int value representing minutes.
     * @param timeFrame PresenceTimeFrame
     * @return DateRange
     */
    public static final DateRange months(int units, PresenceTimeFrame timeFrame) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.MONTHS)
            .setTimeframe(timeFrame)
            .setUnits(units)
            .build();
    }

    /**
     * DateRange representing months with a LAST_SEEN PresenceTimeFrame
     * @param units Int value representing months
     * @return DateRange
     */
    public static final DateRange monthsLastSeen(int units) {
        return months(units, PresenceTimeFrame.LAST_SEEN);
    }

    /**
     * DateRange representing years with an ANYTIME PresenceTimeFrame
     * @param units Int representing years
     * @return DateRange
     */
    public static final DateRange years(int units) {
        return years(units, PresenceTimeFrame.ANYTIME);
    }

    /**
     * DateRange with given years and PresenceTimeFrame. The time frame denotes
     * whether the DateRange covers the last seen option or the any time option.
     *
     * @param units Int value representing years.
     * @param timeFrame PresenceTimeFrame
     * @return DateRange
     */
    public static final DateRange years(int units, PresenceTimeFrame timeFrame) {
        return RecentDateRange.newBuilder()
            .setResolution(DateRangeUnit.YEARS)
            .setTimeframe(timeFrame)
            .setUnits(units)
            .build();
    }

    /**
     * DateRange representing years with a LAST_SEEN PrescenceTimeFrame
     * @param units Int representing years
     * @return DateRange
     */
    public static final DateRange yearsLastSeen(int units) {
        return years(units, PresenceTimeFrame.LAST_SEEN);
    }
}
