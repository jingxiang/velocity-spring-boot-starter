package com.kalman03.springboot.velocity.view;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

/**
 * @since 2022-07-12
 * @author kalman03
 */
public class VelocityViewResolver extends AbstractTemplateViewResolver {

	public VelocityViewResolver() {
		setViewClass(requiredViewClass());
	}

	public VelocityViewResolver(String prefix, String suffix) {
		this();
		setPrefix(prefix);
		setSuffix(suffix);
	}
	
	@Override
	protected Class<?> requiredViewClass() {
		return VelocityView.class;
	}

	@Override
	protected AbstractUrlBasedView instantiateView() {
		return (getViewClass() == VelocityView.class ? new VelocityView() : super.instantiateView());
	}

}