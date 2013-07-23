
Urban Airship Java Client Library
=================================

This is the official supported Java library for the Urban Airship API.

Using the library is straightforward. Create a client, setup a request payload,
handle the results.

```
    String appKey = "appKey";
    String appSecret = "appSecret";

    // Setup an authenticated APIClient with your application key and
    // application master secret.
    APIClient apiClient = APIClient.newBuilder()
            .setKey(appKey)
            .setSecret(appSecret)
            .build();

    // Setup a push payload to send to the API with our handy builders
    PushPayload payload = PushPayload.newBuilder()
                                     .setAudience(Selectors.deviceToken(deviceToken))
                                     .setNotification(Notifications.notification("UA Push"))
                                     .setPlatforms(PlatformData.of(Platform.IOS))
                                     .build();
    // Try and send, handle anything that comes up
    try {
        APIClientResponse<APIPushResponse> response = apiClient.push(payload);
        log.info("Sent a push message!");
    }
    // Non 200 responses throw an APIRequestException. Check the documentation
    // to debug your request.
    catch (APIRequestException ex){
        logger.error("Non 200 request, checking error details and taking action");
    }
    // An underlying error occurred, most likely outside of the scope of the
    // UA library, do some HTTP debugging
    catch (IOException e){
        logger.error("Broken pipe what?");
    }
```

Documentation
=============

General documentation can be found here: http://docs.urbanairship.com/
Jave client library documentation can be found here:
  http://docs.urbanairship.com/reference/libraries/java/index.html

Installation
====================

Clone the repository, and use
```
    mvn package
```

to build the jar. If you would like a copy of the javadocs, use
```
    mvn javadoc:javadoc
```

More detailed documentation is available at the site listed above.

Notes on Maven Central
----------------------

We will be deploying the library to Maven Central soon. We will update this
README when that is complete.