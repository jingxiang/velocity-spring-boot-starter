package com.bushangbuxia.web.springboot.velocity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.template.PathBasedTemplateAvailabilityProvider;

public class VelocityTemplateAvailabilityProvider extends
		PathBasedTemplateAvailabilityProvider {

	public VelocityTemplateAvailabilityProvider(String className,
			Class<? extends TemplateAvailabilityProperties> propertiesClass,
			String propertyPrefix) {
		super("org.apache.velocity.app.VelocityEngine",
				VelocityTemplateAvailabilityProperties.class, "spring.velocity");
	}

	static final class VelocityTemplateAvailabilityProperties extends
			TemplateAvailabilityProperties {

		private List<String> templateLoaderPath = new ArrayList<String>(
				Arrays.asList(VelocityProperties.DEFAULT_TEMPLATE_LOADER_PATH));

		VelocityTemplateAvailabilityProperties() {
			super(VelocityProperties.DEFAULT_PREFIX,
					VelocityProperties.DEFAULT_SUFFIX);
		}

		@Override
		protected List<String> getLoaderPath() {
			return this.templateLoaderPath;
		}

		public List<String> getTemplateLoaderPath() {
			return this.templateLoaderPath;
		}

		public void setTemplateLoaderPath(List<String> templateLoaderPath) {
			this.templateLoaderPath = templateLoaderPath;
		}

	}

}
