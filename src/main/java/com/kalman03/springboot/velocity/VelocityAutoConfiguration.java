package com.kalman03.springboot.velocity;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.spring.VelocityEngineFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

import com.kalman03.springboot.velocity.view.VelocityViewResolver;

@AutoConfiguration
public class VelocityAutoConfiguration {
	
	@Bean
	@ConfigurationProperties(prefix = "spring.velocity")
	VelocityProperty velocityProperty() {
		return new VelocityProperty();
	}

	@Bean
	VelocityEngineFactoryBean velocityEngineFactoryBean(VelocityProperty velocityProperty) {
		VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
		bean.setResourceLoaderPath(velocityProperty.getResourceLoaderPath());
		Map<String, Object> velocityPropertiesMap = new HashMap<>();
		velocityPropertiesMap.put(Velocity.ENCODING_DEFAULT, "UTF-8");
		velocityPropertiesMap.put(Velocity.INPUT_ENCODING, "UTF-8");
		bean.setVelocityPropertiesMap(velocityPropertiesMap);
		return bean;
	}

	@Bean
	@ConditionalOnMissingBean(name = "velocityViewResolver")
	@ConditionalOnProperty(name = "spring.velocity.enabled", matchIfMissing = true)
	VelocityViewResolver velocityViewResolver(VelocityProperty velocityProperty) {
		VelocityViewResolver resolver = new VelocityViewResolver(null, velocityProperty.getSuffix());
		resolver.setOrder(Ordered.HIGHEST_PRECEDENCE + 100);
		return resolver;
	}
}
