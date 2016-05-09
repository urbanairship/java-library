###############
Getting Started
###############

The basic workflow for making requests via the Java Library is as follows:

#. Configure an UrbanAirshipClient to authenticate, send, and return a response.
#. Configure a request to one of the Engage API :ref:`endpoints`.
#. Pass the request to the client, and handle the response or exception appropriately.

We will walk through each step for sending a push notification. If you are already
familiar with the Java Library, feel free to skip to the :ref:`endpoint reference
<endpoints>`.


***************************************
Step 1: Configure an UrbanAirshipClient
***************************************

The ``UrbanAirshipClient`` handles the interaction between the client and the API. The
client will throw an exception if there is an issue with the request, or if it is improperly
configured.

The following is the minimum-viable ``UrbanAirshipClient`` configuration:

.. code-block:: java

   UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
       .setKey("your-app-key-here")
       .setSecret("your-app-secret-here")
       .build();


************************
Step 2: Create a Request
************************

Next, you are going to create a request. To see the full list of request types, please
see the :ref:`endpoint reference <endpoints>`. In the example below, we are going to
create a request to the push API:

.. sourcecode:: java

   PushPayload payload = PushPayload.newBuilder()
       .setAudience(Selectors.all())
       .setNotification(Notifications.alert("Here's a push!"))
       .setDeviceType(DeviceTypeData.of(DeviceType.IOS))
       .build();

   PushRequest request = PushRequest.newRequest(payload);


**********************************************
Step 3: Send the Request and Handle Exceptions
**********************************************

Once you have created a request, you pass it to be executed in the client created in step 1:

.. sourcecode:: java

   Response<PushResponse> response = null;
   try {
       response = client.execute(request);
       logger.debug(String.format("Response %s", response.toString()));
   } catch (IOException e) {
       logger.error("IOException in API request " + e.getMessage());
   }


*****************************
Complete Example Push Request
*****************************

Here is a complete example of sending a simple push notification:

.. code-block:: java

   // Import some things
   import com.urbanairship.api.client.*;
   import com.urbanairship.api.push.model.DeviceType;
   import com.urbanairship.api.push.model.DeviceTypeData;
   import com.urbanairship.api.push.model.PushPayload;
   import com.urbanairship.api.push.model.audience.Selectors;
   import com.urbanairship.api.push.model.notification.Notifications;

   public void sendPush() {

       String appKey = "applicationKey";
       String appSecret = "applicationMasterSecret";

       // Step 1: Build and configure an APIClient.
       UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
               .setKey(appKey)
               .setSecret(appSecret)
               .build();

       // Step 2: Setup a payload for the message you want to send, and create the
       // push request.
       PushPayload payload = PushPayload.newBuilder()
           .setAudience(Selectors.all())
           .setNotification(Notifications.alert("Here's a push!"))
           .setDeviceType(DeviceTypeData.of(DeviceType.IOS))
           .build();

       PushRequest request = PushRequest.newRequest(payload);

       // Step 3: Use the client to execute the request, and try/catch for any issues, any
       // non-200 response, or non-library-related exceptions.
       try {
           Response<PushResponse> response = client.execute(request);
           logger.debug(String.format("Response %s", response.toString()));
       } catch (IOException e) {
           logger.error("IOException in API request " + e.getMessage());
       }

       // Cleanup: Close the HTTP client's thread pool.
       client.close()
   }
