##########################
Urban Airship Java Library
##########################

The Urban Airship Java Library provides a set of Java classes and methods for interacting with
the `Engage API <https://docs.urbanairship.com/api/ua.html>`__.


************
Installation
************

Manual Installation
===================

Clone the `Java Library <https://github.com/urbanairship/java-library>`__ repo, and run::

   mvn package

to build the jar. Add the jar, located at a path similar to::

   target/java-client-<version>.jar

If you would like a copy of the javadocs, use::

   mvn javadoc:javadoc


Maven Installation
==================

Add the following to your pom.xml:

.. sourcecode:: xml

    <!-- Urban Airship Library Dependency-->
    <dependency>
        <groupId>com.urbanairship</groupId>
        <artifactId>java-client</artifactId>
        <version>1.0.0</version>
    </dependency>


*******
Logging
*******

Logging is done using the `Simple Logging Facade for Java <http://www.slf4j.org>`_.
Using the logging facade allows for flexibility in logging choices. For example,
to use log4j, you would add the following to your ``pom.xml``:

.. code-block:: xml

   <dependency>
     <groupId>org.slf4j</groupId>
     <artifactId>slf4j-log4j12</artifactId>
     <version>1.7.5</version>
   </dependency>

   <dependency>
     <groupId>log4j</groupId>
     <artifactId>log4j</artifactId>
     <version>1.2.17</version>
   </dependency>

Note the logging framework plus the adapter. For more info, see the `Simple Logging Facade
documentation <http://www.slf4j.org/manual.html>`__. Simply add the log handler of your choice.
Again, with log4j:

.. code-block:: java

   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   import org.apache.log4j.BasicConfigurator;

   Logger logger = LoggerFactory.getLogger("identifier");
   // Use any configuration you need.
   BasicConfigurator.configure();
   logger.debug("Log all the things!");


********
Contents
********

.. _introduction:

Introduction
============

.. toctree::
   :maxdepth: 1

   getting-started
   client


.. _endpoints:

Endpoint Reference
==================

.. toctree::
   :maxdepth: 1

   push
   channels
   segments
   schedules
   reports
   named-user
   static-lists
   location
   templates
