### Introduction

一个velocity的springboot支持和实现，基于springboot2.0+

### Guides

pom.xml

```xml
<dependency>
    <groupId>com.kalman03</groupId>
	<artifactId>velocity-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

SpringBootApplication.java

```java
@SpringBootApplication(scanBasePackages={"com.kalman03.web.springboot.velocity"})
```

application.properties

```properties
#velocity
spring.velocity.enabled=true
spring.velocity.charset=UTF-8
spring.velocity.properties.input.encoding=UTF-8
spring.velocity.properties.output.encoding=UTF-8
spring.velocity.resourceLoaderPath=classpath:/templates/
spring.velocity.suffix=.vm
spring.velocity.toolboxConfigLocation=/config/velocity-toolbox.xml
spring.velocity.layoutUrl=layout.vm
```

src/main/resources/config/velocity-toolbox.xml

```xml
<?xml version="1.0"?>
<tools>
    <toolbox scope="request" xhtml="true">
        <tool key="custom" class="com.kalman.web.springboot.Ydhd" />
        
    <data key="startdate" value="Mon Sep 17 10:08:03 PDT 2007" class="java.util.Date" 
             converter="org.apache.commons.beanutils.locale.converters.DateLocaleConverter"/>
    </toolbox>

    <toolbox scope="session">
        <property name="create-session" value="true" type="boolean"/>
        <tool key="map" class="java.util.HashMap"/>
    </toolbox>

    <toolbox scope="application">
        <tool class="org.apache.velocity.tools.generic.DateTool"/>
    </toolbox>

</tools>
```

