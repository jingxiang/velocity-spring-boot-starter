package com.kalman03.web.springboot.velocity;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import com.kalman03.web.springboot.velocity.views.EmbeddedVelocityViewResolver;
import com.kalman03.web.springboot.velocity.views.VelocityConfig;
import com.kalman03.web.springboot.velocity.views.VelocityConfigurer;

@Configuration
@ConditionalOnClass({ VelocityEngine.class, VelocityEngineFactory.class })
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(VelocityProperties.class)
public class VelocityAutoConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(VelocityAutoConfiguration.class);

	private final ApplicationContext applicationContext;

	private final VelocityProperties properties;

	public VelocityAutoConfiguration(ApplicationContext applicationContext,
			VelocityProperties properties) {
		this.applicationContext = applicationContext;
		this.properties = properties;
	}

	@PostConstruct
	public void checkTemplateLocationExists() {
		if (this.properties.isCheckTemplateLocation()) {
			TemplateLocation location = new TemplateLocation(
					this.properties.getResourceLoaderPath());
			if (!location.exists(this.applicationContext)) {
				logger.warn("Cannot find template location: " + location
						+ " (please add some templates, check your Velocity "
						+ "configuration, or set spring.velocity."
						+ "checkTemplateLocation=false)");
			}
		}
	}

	protected static class VelocityConfiguration {

		@Autowired
		protected VelocityProperties properties;

		protected void applyProperties(VelocityEngineFactory factory) {
			factory.setResourceLoaderPath(this.properties.getResourceLoaderPath());
			factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
			Properties velocityProperties = new Properties();
			velocityProperties.setProperty("input.encoding",
					this.properties.getCharsetName());
			velocityProperties.putAll(this.properties.getProperties());
			factory.setVelocityProperties(velocityProperties);
		}

	}

	@Configuration
	@ConditionalOnNotWebApplication
	@Deprecated
	public static class VelocityNonWebConfiguration extends VelocityConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public VelocityEngineFactoryBean velocityConfiguration() {
			VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
			applyProperties(velocityEngineFactoryBean);
			return velocityEngineFactoryBean;
		}

	}

	@Configuration
	@ConditionalOnClass(Servlet.class)
	@ConditionalOnWebApplication
	@Deprecated
	public static class VelocityWebConfiguration extends VelocityConfiguration {

		@Bean
		@ConditionalOnMissingBean(VelocityConfig.class)
		public VelocityConfigurer velocityConfigurer() {
			VelocityConfigurer configurer = new VelocityConfigurer();
			applyProperties(configurer);
			return configurer;
		}

		@Bean
		public VelocityEngine velocityEngine(VelocityConfigurer configurer)
				throws VelocityException, IOException {
			return configurer.getVelocityEngine();
		}

		@Bean
		@ConditionalOnMissingBean(name = "velocityViewResolver")
		@ConditionalOnProperty(name = "spring.velocity.enabled", matchIfMissing = true)
		public EmbeddedVelocityViewResolver velocityViewResolver() {
			EmbeddedVelocityViewResolver resolver = new EmbeddedVelocityViewResolver();
			this.properties.applyToMvcViewResolver(resolver);
			return resolver;
		}

		@Bean
		@ConditionalOnMissingBean
		@ConditionalOnEnabledResourceChain
		public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
			return new ResourceUrlEncodingFilter();
		}

	}

}