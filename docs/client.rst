############################
The UrbanAirshipClient Class
############################

The ``UrbanAirshipClient`` class handles requests to the Urban Airship Engage API. This
document covers the various configuration options, along with different methods for
executing requests within the client.


*************
Configuration
*************

As shown in the :doc:`Getting Started Guide <getting-started>`, the minimum-viable
``UrbanAirshipClient`` client configuration requires an app key and a master secret:

.. code-block:: java

   UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
       .setKey("your-app-key-here")
       .setSecret("your-app-secret-here")
       .build();

In the following sections, we'll explore some of the additional client configuration
options available.


Client With Proxy
=================

Optionally, a client can be created with proxy server support:

.. code-block:: java

   ProxyInfo proxy = ProxyInfo.newBuilder()
       .setHost("proxy.host.com")
       .setProtocol(ProxyInfo.ProxyInfoProtocol.HTTPS)
       .setPrincipal("user")
       .setPassword("password123")
       .setPort(8080)
       .build();

   UrbanAirshipClient proxyClient = UrbanAirshipClient.newBuilder()
       .setKey(appKey)
       .setSecret(appSecret)
       .setProxyInfo(proxy)
       .build();


Client With HTTP Parameter Settings
===================================

A client can also be created with the option to set any of the HTTP parameters configurable through the `Async HTTP client
<https://asynchttpclient.github.io/async-http-client/apidocs/com/ning/http/client/AsyncHttpClientConfig.Builder.html>`__,
such as the protocol and connection parameters, by passing in a ``AsyncHttpClientConfig.Builder``.
In the example below, the socket and connection timeouts are set to be 10ms and 20ms,
respectively, thus overriding their default settings as infinite timeouts.

.. code-block:: java

   AsyncHttpClientConfig.Builder configBuilder = new AsyncHttpClientConfig.Builder()
       .setConnectTimeout(20)
       .setWebSocketTimeout(10);

   UrbanAirshipClient client = UrbanAirshipClient.newBuilder()
       .setKey("key")
       .setSecret("secret")
       .setClientConfigBuilder(configBuilder)
       .build();


Client with Custom Retry Logic
==============================

You may optionally specify a custom retry predicate. This predicate dictates how the
client responds to failure, i.e., when should the client retry a failed request. The default
retry predicate will retry all requests that return responses with status codes of 500 or higher,
assuming they are not ``POST`` requests. We avoid retrying ``POST`` requests in order to
prevent duplicates (e.g., retrying a push request may result in duplicate pushes). Requests are retried
a maximum of 10 times, with an exponentially-increasing backoff period between each attempt.

In the example below, we create a custom predicate that will retry all requests that return
responses with status codes of 500 or greater. Unlike the default retry predicated, this predicate
will retry ``POST`` requests:

.. sourcecode:: java

   Predicate<FilterContext> retryPredicate = new Predicate<FilterContext>() {
      @Override
      public boolean apply(FilterContext filterContext) {
         return input.getResponseStatus().getStatusCode() >= 500;
      }
   };

We now configure an ``UrbanAirshipClient`` to use the above ``retryPredicate``. We also increase the
max number of retry attempts from ``10`` to ``20``:

.. sourcecode:: java

   UrbanAirshipClient retryClient = UrbanAirshipClient.newBuilder()
       .setKey(appKey)
       .setSecret(appSecret)
       .setRetryPredicate(retryPredicate)
       .setMaxRetries(20)
       .build();


******************
Executing Requests
******************

Once you have a client configured and some sort of request created, the ``UrbanAirshipClient``
class supports four different modes of request execution::

   execute(Request<T> request)
   execute(Request<T> request, ResponseCallback callback)
   executeAsync(Request<T> request)
   executeAsync(Request<T> request, ResponseCallback callback)

There are two methods, ``execute`` and ``executeAsync``, and each method supports an optional
callback argument. The simplest method of making a request is to use ``execute`` with no
callback specified:

.. sourcecode:: java

   // Make a request -- assumes ``client`` and ``request`` are already specified, and
   // ``request`` is a push request.
   Response<PushResponse> response = client.execute(request);

Below, we'll cover how to use async requests and how to specify callbacks.


Making Async Requests
=====================

Use the ``executeAsync(..)`` method to initiate a non-blocking call to the Urban Airship API.

.. sourcecode:: java

   // Non-blocking request
   Future<Response> futureResponse = client.executeAsync(request);

   // Do other stuff...

   // Retrieve your response after doing stuff.
   Response<PushResponse> response = futureResponse.get();


Response Callbacks
==================

Both the ``execute`` and ``executeAsync`` methods accept an optional ``ResponseCallback`` argument.
Below, we define a callback that executes the ``doSomething(...)`` function once a request completes,
and the ``doSomethingElse(...)`` function if the request fails:

.. sourcecode:: java

   ResponseCallback callback = new ResponseCallback() {
       @Override
       public void completed(Response response) {
           // Logic specifying what to do upon request completion.
           doSomething(response)
       }

       @Override
       public void error(Throwable throwable) {
           // Logic specifying what to do if the request fails.
           doSomethingElse(throwable)
       }
   };

We can use this callback with either ``execute`` or ``executeAsync``:

**Example (executeAsync)**

.. sourcecode:: java

   // Start the request execution. Once the request has completed (or thrown an error),
   // the appropriate callback function will be triggered. ``executeAsync`` is non-blocking,
   // so you can do other stuff while you wait for the callback to get triggered.
   Future<Response> response = client.executeAsync(request, callback)

   // Do other stuff...

**Example (execute)**

.. sourcecode:: java

   // Start the request execution . Once the request has completed (or thrown an error),
   // the appropriate callback function will be triggered. ``execute`` is blocking, so
   // you must wait for the request to complete (or fail), after which the callback is triggered
   // and the Response<..> is returned.
   Response<PushResponse> response = client.execute(request, callback)


Exceptions
==========

The client will throw different exceptions depending on mode of execution. If you are
not using a callback, all exceptions present as RuntimeExceptions. If you choose to use a
callback, you can customize the ``error`` method to distinguish between ClientExceptions
(4xx responses), ServerExceptions (5xx responses), and any other potential failures.

**Example**

.. sourcecode:: java

   ResponseCallback callback = new ResponseCallback() {
       @Override
       public void completed(Response response) {
           // Logic specifying what to do upon request completion.
           doSomething(response)
       }

       @Override
       public void error(Throwable throwable) {
           if (throwable instanceof ClientException) {
               // Handle a 4xx response
           } else if (throwable instance of ServerException)
               // Handle a 5xx response
           } else {
               // Handle any other failure
           }
       }
   };
