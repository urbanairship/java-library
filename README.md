Airship Java Client Library
=================================

This is the official supported Java library for the Airship API. Please reach out to
http://support.airship.com directly with any issues that need attention.


Documentation
=============

General documentation can be found here: http://docs.airship.com/
Java client library documentation can be found here: https://docs.airship.com/api/libraries/java/


Workflows / Deployment
======================

CI runs automatically on every pull request (`maven-tests` workflow, Java 11).

### Publishing a release

1. Ensure master is in sync with `java-library` (public repo) and the version in `pom.xml` is correct.
2. Trigger the [Sonatype Release workflow](https://github.com/urbanairship/java-library-dev/actions/workflows/sonatype-release.yaml) manually via **Run workflow → master**.
3. Once the workflow completes, open the **Summary** tab of the workflow run — it prints the direct link to approve and publish the staged bundle on [Sonatype Central](https://central.sonatype.com/publishing).
4. Approve the bundle on Sonatype. The public `java-library` repo is synced automatically by the workflow (master + tag).


Installation
====================


Manual installation
-------------------

Clone the repository, and use

```
    mvn package
```

to build the jar. Add the jar, located at a path similar to:

```
    target/java-client-<version>.jar
```

If you would like a copy of the javadocs, use

```
    mvn javadoc:javadoc
```


Maven Installation
------------------

Add the following to your pom.xml

```xml
    <!-- Urban Airship Library Dependency-->
    <dependency>
        <groupId>com.urbanairship</groupId>
        <artifactId>java-client</artifactId>
        <version>VERSION</version>
        <!-- Replace VERSION with the version you want to use -->
    </dependency>
```

Upgrading to 6.X.X
------------------

Schedule requests now require a SchedulePayload object:

```
    SchedulePayload schedulePayload = SchedulePayload.newBuilder()
        .setName("optionalName")
        .setSchedule(Schedule.newBuilder()
            .setScheduledTimestamp(dateTime)
            .build())
        .setPushPayload(PushPayload.newBuilder()
            .setDeviceTypes(DeviceTypeData.of(DeviceType.ANDROID))
            .setNotification(Notifications.alert("Simple alert"))
            .setAudience(Selectors.tag("tag"))
            .build())
        .build();

    ScheduleRequest scheduleRequest = ScheduleRequest.newRequest(schedulePayload);
```

Schedule responses now contain an Immutable list of SchedulePayloadResponse objects instead of SchedulePayload objects
inside the response body.

```
    Response<ScheduleResponse> response = client.execute(scheduleRequest);
    ImmutableList<SchedulePayloadResponse> schedulePayloadResponses = response.getBody().get().getSchedulePayloadResponses();
```

When creating a custom event request the CustomEventBody now requires CustomEventPropertyValue objects to support complex property objects.

```
    CustomEventPropertyValue customEventProperty = CustomEventPropertyValue.of("victory");

    List<CustomEventPropertyValue> items = new ArrayList<>();
    items.add(CustomEventPropertyValue.of("la croix"));
    items.add(CustomEventPropertyValue.of("more la croix"));

    CustomEventBody customEventBody = CustomEventBody.newBuilder()
        .setName("purchased")
        .addPropertiesEntry("brand", customEventProperty)
        .addPropertiesEntry("items", CustomEventPropertyValue.of(items))
        .build();

    CustomEventPayload customEventPayload = CustomEventPayload.newBuilder()
        .setCustomEventBody(customEventBody)
        .setCustomEventUser(customEventUser)
        .setOccurred(occurred)
        .build();
```

CustomEventBody.getSessionId() will now return an Optional String instead of a String.