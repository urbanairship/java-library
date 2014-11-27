APIClient
=========

.. code-block:: java

    APIClient apiClient = APIClient.newBuilder()
            .setKey(appKey)
            .setSecret(appSecret)
            .build();

The APIClient handles the interaction between the client and the API. The client will throw an
exception if there is an issue with the request, or if it is improperly configured.

Optionally, a client can be created with proxy server support.

.. code-block:: java

    APIClient proxyClient = APIClient.newBuilder()
          .setKey(appKey)
          .setSecret(appSecret)
              .setProxyInfo(ProxyInfo.newBuilder()
              .setProxyHost(new HttpHost("host"))
              .setProxyCredentials(new UsernamePasswordCredentials("user", "password"))
              .build())
          .build();