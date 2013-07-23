Exeptions
=========
These are the primary exceptions that are possible in the client
library.


APIRequestException
-------------------

APIRequestExceptions are thrown in cases where the server returns a non 200
response.

.. code-block:: java

   APIClient apiClient = APIClient.newBuilder()
                                  .setKey("pvNYHR9ZSGGk1LwuPl4kQWw")
                                  .setSecret("badFoo")
                                  .build();
   // Setup request
    try {
        APIClientResponse<APIPushResponse> response = apiClient.push(payload);
        logger.debug(String.format("Response %s", response.toString()));
    }
    catch (APIRequestException ex){
        // Exeption thrown here
    }

The code above will throw an APIResponseException

::

    1717 [main] ERROR com.urbanairship.api  - APIRequestException
    APIRequestException:
    Message:Unauthorized
    HttpResponse:HTTP/1.1 401 Unauthorized ......
    Error:APIError:Unauthorized
    Code:Optional.absent()

APIErrorDetails
---------------

The APIErrorDetails object contains information on errors for requests
that are syntactically valid but are otherwise malformed. For example,
setting the platform value for a PushPayload to include both
Platform.IOS and Platform.ANDROID but only providing a single
IOSDevicePayloadf for the notification would be an error.


.. code-block:: json

 // This is a syntactically valid request, but is missing an
 // Android payload override.
 {
     "audience" : "all",
     "device_types" : [ "ios", "android" ],
     "notification" : {
         "ios" : {
             "alert" : "Boo"
         }
     }
 }

This will thrown an APIException that can be used to log or debug
errors.

.. code-block:: java

    try {
      APIClientResponse<APIPushResponse> response = apiClient.push(payload);
      logger.debug(String.format("Response %s", response.toString()));
    }
    catch (APIRequestException ex){
      logger.error(String.format("APIRequestException " + ex));
      logger.error("Exception " + ex.toString());

      APIError apiError = ex.getError().get();
      APIErrorDetails apiErrorDetails = apiError.getDetails().get();
      logger.error("Error " + apiError.getError());
      logger.error("Error details " + apiErrorDetails.getError());

    }
    catch (IOException e){
      logger.error("IOException in API request " + e.getMessage());
    }



will produce

::

 1722 [main] ERROR com.urbanairship.api  - Exception
 APIRequestException:
 Message:Bad Request
 HttpResponse:HTTP/1.1 400 Bad Request [Content-Type: application/vnd.urbanairship+json; version=3, Server: Jetty(8.0.y.z-SNAPSHOT), X-Request-Id: ff38e040-f310-11e2-9f25-d4bed9a88504, Date: Mon, 22 Jul 2013 20:55:10 GMT, Transfer-Encoding:  chunked, Connection: close, Connection: Transfer-Encoding]
 Error:APIError:Could not parse request body.
 Code:Optional.of(40000)
 Details:
 APIErrorDetails:
 Path:Optional.absent()
 Error:Platform 'android' was referenced by 'device_types', but no payload was provided.
 Optional Location:Optional.absent()
 1722 [main] ERROR com.urbanairship.api  - Error Could not parse request body.
 1722 [main] ERROR com.urbanairship.api  - Error details Platform
 'android' was referenced by 'device_types', but no payload was
 provided.


The APIRequestException contains both the raw HttpResponse from the
underlying Apache request and the APIError. The APIError is specific
to Urban Airship functionality, and the APIErrorDetails provides
extended details for badly formed API requests. Providing this level
of detail allows for more customization. 


APIParsingException
-------------------

APIParsingExceptions are thrown in response to parsing errors while
serializing or deserializing JSON. If this is thrown outside of
development it is most likely an issue with the library or the server,
and should be sent to the Urban Airship support team. Please include
as much information as possible, including the operation id if
present, and the request or API operation that threw the exception.

IOException
-----------

In the context of this library, IOExceptions are thrown by the Apache
HttpComponents library, usually in response to a problem with the HTTP connection.
See the Apache `documentation <https://hc.apache.org>`_ for more
details.


