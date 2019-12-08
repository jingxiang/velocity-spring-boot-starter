package com.bushangbuxia.web.springboot.velocity.views;

import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class EmbeddedVelocityViewResolver extends VelocityViewResolver {

	private String toolboxConfigLocation;

	@Override
	protected void initApplicationContext() {
		if (this.toolboxConfigLocation != null) {
			if (VelocityView.class.equals(getViewClass())) {
				this.logger.info("Using VelocityLayoutView instead of "
						+ "default VelocityView due to specified toolboxConfigLocation");
				setViewClass(VelocityLayoutView.class);
			}
		}
		super.initApplicationContext();
	}

	@Override
	public void setToolboxConfigLocation(String toolboxConfigLocation) {
		super.setToolboxConfigLocation(toolboxConfigLocation);
		this.toolboxConfigLocation = toolboxConfigLocation;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		VelocityLayoutView view = (VelocityLayoutView) super.buildView(viewName);
		// Use not-null checks to preserve VelocityLayoutView's defaults.
		if (this.layoutUrl != null) {
			view.setLayoutUrl(this.layoutUrl);
		}
		if (this.layoutKey != null) {
			view.setLayoutKey(this.layoutKey);
		}
		if (this.screenContentKey != null) {
			view.setScreenContentKey(this.screenContentKey);
		}
		return view;
	}


	public static final String DEFAULT_LAYOUT_URL = "layout.vm";
	public static final String DEFAULT_LAYOUT_KEY = "layout";
	public static final String DEFAULT_SCREEN_CONTENT_KEY = "screen_content";

	private String layoutUrl = DEFAULT_LAYOUT_URL;
	private String layoutKey = DEFAULT_LAYOUT_KEY;
	private String screenContentKey = DEFAULT_SCREEN_CONTENT_KEY;

	public void setLayoutUrl(String layoutUrl) {
		this.layoutUrl = layoutUrl;
	}
	
}