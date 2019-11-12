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
