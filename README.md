Airship Java Client Library
=================================

This is the official supported Java library for the Airship API. Please reach out to
http://support.airship.com directly with any issues that need attention.


Documentation
=============

General documentation can be found here: http://docs.airship.com/
Java client library documentation can be found here: https://docs.airship.com/api/libraries/java/


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

Upgrading to 5.X.X
------------------

Changed the way all API responses that are not 2XX, 401, or 403 are handled (these will remain as they were previously).
For all other response codes, instead of throwing exceptions the library will now return a response that contains the status code that was received.
Exceptions will continue to be thrown when the library encounters 401s and 403s.
Please examine any retry logic in your implementation to see if you need to make changes.