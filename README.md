### Introduction

A velocity spring boot starter to support spring boot 3.0.0+

The velocity version based on 

[velocity engine 2.3](http://velocity.apache.org/engine/2.3/)

### Guides

1.Add maven dependency to pom.xml.

```xml
<dependency>
    <groupId>com.kalman03</groupId>
    <artifactId>velocity-spring-boot-starter</artifactId>
    <version>3.2.0</version>
</dependency>
```

2.Config the application.properties

```properties
#velocity
spring.velocity.enabled=true
spring.velocity.resource-loader-path=classpath:/templates/
spring.velocity.suffix=.vm
spring.velocity.layout-url=layout.vm
```

3.Done.



### License

velocity-spring-boot-starter is released under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0).
