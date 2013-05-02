
Bindings
-------------------------------------------

When using the Pliant XML library to generate a domain project, add the following dependancy to your POM:

```xml
	<dependency>
		<groupId>org.jvnet.jaxb2_commons</groupId>
		<artifactId>jaxb2-basics-runtime</artifactId>
		<version>0.6.3</version>
	</dependency>
```

And add the following plugin to your POM:

```xml
	<!-- Generates the java classes from the XSD. -->
	<!-- See http://confluence.highsource.org/display/MJIIP/Maven+JAXB2+Plugin  -->
	<plugin>
		<groupId>org.jvnet.jaxb2.maven2</groupId>
		<artifactId>maven-jaxb2-plugin</artifactId>
		<configuration>
			<extension>true</extension>
			<args>
				<arg>-XtoString</arg>
				<arg>-Xequals</arg>
				<arg>-XhashCode</arg>
				<arg>-Xcopyable</arg>
				<arg>-Xmergeable</arg>
			</args>
			<plugins>
				<plugin>
					<groupId>org.jvnet.jaxb2_commons</groupId>
					<artifactId>jaxb2-basics</artifactId>
					<version>0.6.3</version>
				</plugin>
			</plugins>
		</configuration>
	</plugin>
```

