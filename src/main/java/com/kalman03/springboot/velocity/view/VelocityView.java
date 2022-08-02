package com.kalman03.springboot.velocity.view;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.kalman03.springboot.velocity.VelocityProperty;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @since 2022-07-12
 * @author kalman03
 */
public class VelocityView extends AbstractTemplateView {

	private final static String SCREEN_CONTENT_KEY = "screen_content";
	private final static String LAYOUT_KEY = "layout";

	private VelocityEngine velocityEngine;
	private VelocityProperty velocityProperty;

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	protected void initApplicationContext(ApplicationContext context) {
		super.initApplicationContext();
		if (this.velocityEngine == null) {
			setVelocityEngine(autodetectVelocityEngine());
		}
		if (this.velocityProperty == null) {
			velocityProperty = BeanFactoryUtils.beanOfTypeIncludingAncestors(obtainApplicationContext(),
					VelocityProperty.class, true, false);
		}
	}

	protected VelocityEngine autodetectVelocityEngine() throws BeansException {
		try {
			return BeanFactoryUtils.beanOfTypeIncludingAncestors(obtainApplicationContext(), VelocityEngine.class, true,
					false);
		} catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException("Expected a single VelocityEngine bean in the current "
					+ "Servlet web application context or the parent root context: VelocityConfiguration is "
					+ "the usual implementation. This bean may have any name.", ex);
		}
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Context velocityContext = new VelocityContext(model);
		doRender(velocityContext, response);
	}

	private void doRender(Context context, HttpServletResponse response) throws Exception {
		renderScreenContent(context);

		// #set( $layout = "MyLayout.vm" )
		String layoutUrlToUse = (String) context.get(LAYOUT_KEY);
		if (layoutUrlToUse != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Screen content template has requested layout [" + layoutUrlToUse + "]");
			}
		} else {
			// No explicit layout URL given -> use default layout of this view.
			String layoutUrl = "layout.vm";
			if (velocityProperty != null && isNotBlank(velocityProperty.getLayoutUrl())) {
				layoutUrl = velocityProperty.getLayoutUrl();
			}
			layoutUrlToUse = layoutUrl;
		}
		Template template = getTemplate(layoutUrlToUse);
		response.setCharacterEncoding("UTF-8");
		template.merge(context, response.getWriter());
	}

	/**
	 * The resulting context contains any mappings from render, plus screen content.
	 */
	private void renderScreenContent(Context velocityContext) throws Exception {
		logger.debug("Rendering screen content template [" + getUrl() + "]");

		StringWriter sw = new StringWriter();
		Template screenContentTemplate = getTemplate(getUrl());
		screenContentTemplate.merge(velocityContext, sw);
		// Put rendered content into Velocity context.
		velocityContext.put(SCREEN_CONTENT_KEY, sw.toString());
	}

	private Template getTemplate(String url) {
		return velocityEngine.getTemplate(url, "UTF-8");
	}
}
