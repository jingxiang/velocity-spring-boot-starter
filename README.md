### Introduction

A velocity spring boot starter to support spring boot 1.5+ .

The velocity version based on 

[velocity engine 2.1](http://velocity.apache.org/engine/2.1/) , [velocity tools 3.0](http://velocity.apache.org/tools/3.0/)

### Guides

1.Add maven dependency to pom.xml.

```xml
<dependency>
    <groupId>com.kalman03</groupId>
    <artifactId>velocity-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

2.Add scan base packages 

```java
@SpringBootApplication(scanBasePackages={"com.kalman03.web.springboot.velocity"})
```



3.Custom the application.properties

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



4.If necessary,add velocity toolbox xml at <u>src/main/resources/config/velocity-toolbox.xml</u> .The tookbox xml location base on property **spring.velocity.toolboxConfigLocation** setting.Below is a example from velocity site([see more](http://velocity.apache.org/tools/devel/config-xml.html)).

```xml
<?xml version="1.0"?>
<tools>
    <toolbox scope="request" xhtml="true">
        <tool key="customBean" class="your owner class" />
    	<data key="startdate" value="Mon Sep 17 10:08:03 PDT 2007" class="java.util.Date"   converter="org.apache.commons.beanutils.locale.converters.DateLocaleConverter"/>
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

### License

velocity-spring-boot-starter is released under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0).