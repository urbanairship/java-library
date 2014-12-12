/*
 * Copyright (c) 2013-2014.  Urban Airship and Contributors
 */

package com.urbanairship.api.schedule.parse;


import com.google.common.collect.ImmutableSet;

public class TimeZones {

    // At the time of creation of this map (2013-06-17), all of these IDs mapped to successfully to Joda timezones. I found these on
    // on the Internet listed as iOS's timezones (normalized to Joda's canonical spelling) which seems like a fairly good place to start.
    public static final ImmutableSet<String> KNOWN_TIMEZONE_IDS = new ImmutableSet.Builder<String>()
            .add("Africa/Addis_Ababa")
            .add("Africa/Harare")
            .add("Africa/Lagos")
            .add("America/Argentina/Buenos_Aires")
            .add("America/Bogota")
            .add("America/Caracas")
            .add("America/Chicago")
            .add("America/Costa_Rica")
            .add("America/Denver")
            .add("America/Halifax")
            .add("America/Juneau")
            .add("America/Lima")
            .add("America/Los_Angeles")
            .add("America/New_York")
            .add("America/Phoenix")
            .add("America/Santiago")
            .add("America/Sao_Paulo")
            .add("Asia/Bangkok")
            .add("Asia/Dhaka")
            .add("Asia/Dubai")
            .add("Asia/Hong_Kong")
            .add("Asia/Jakarta")
            .add("Asia/Karachi")
            .add("Asia/Kolkata")
            .add("Asia/Manila")
            .add("Asia/Seoul")
            .add("Asia/Singapore")
            .add("Asia/Tehran")
            .add("Asia/Tokyo")
            .add("Asia/Vladivostok")
            .add("Australia/Adelaide")
            .add("Australia/Brisbane")
            .add("Australia/Sydney")
            .add("Etc/GMT")
            .add("Europe/Istanbul")
            .add("Europe/Lisbon")
            .add("Europe/London")
            .add("Europe/Moscow")
            .add("Europe/Paris")
            .add("Pacific/Auckland")
            .add("Pacific/Honolulu")
            .add("UTC")
            .build();
}
