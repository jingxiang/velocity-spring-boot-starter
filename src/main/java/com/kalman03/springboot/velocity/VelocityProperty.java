package com.kalman03.springboot.velocity;

/**
 * @since 2022-07-12
 * @author kalman03
 */
public class VelocityProperty {

	private String resourceLoaderPath;
	private String suffix;
	private String layoutUrl;

	public String getResourceLoaderPath() {
		return resourceLoaderPath;
	}

	public void setResourceLoaderPath(String resourceLoaderPath) {
		this.resourceLoaderPath = resourceLoaderPath;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getLayoutUrl() {
		return layoutUrl;
	}

	public void setLayoutUrl(String layoutUrl) {
		this.layoutUrl = layoutUrl;
	}
}
