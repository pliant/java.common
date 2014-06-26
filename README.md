## About Java Common

Java Common is a collection of lightweight modules containing reusable code for specific purposes.  The code here 
has been reused in some form or another in multiple projects, and it made sense to just package them up and 
reuse by reference.

#### [Test](tree/master/test)
A collection of utilities and preselected dependencies to use for testing.

#### [XML](tree/master/xml)
Common utilities for working with XML and JAXB modelling.

#### [Core](tree/master/core)
Utilities to resolve common issues when working with Java.

#### [Spring](tree/master/spring)
Some custom classes for working with Spring.  Currently targets release 3.0.5.

#### [Maven](tree/master/maven)
Support utilities utilites and classes for building Maven plugins.

#### [JMS](tree/master/jms)
Support utilities utilites and classes for working with JMS.

#### [Camel](tree/master/camel)
Support utilities and classes for working with [Apache Camel](http://camel.apache.org/)

#### [JPA](tree/master/jpa)
Support utilities utilites and classes for working with JPA.

## Maven Integration

Release artifacts can included as Maven project dependancies by adding the following to your POM or settings.xml file:

```xml
<repositories>
	<repository>
		<id>java.common-mvn-repo</id>
		<url>https://raw.github.com/pliant/java.common/mvn-repo/</url>
		<snapshots>
			<enabled>true</enabled>
			<updatePolicy>always</updatePolicy>
		</snapshots>
	</repository>
</repositories>
```

## License

This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0 (http://opensource.org/licenses/eclipse-1.0.php), which accompanies this distribution.

By using this software in any fashion, you are agreeing to be bound by the terms of this license.