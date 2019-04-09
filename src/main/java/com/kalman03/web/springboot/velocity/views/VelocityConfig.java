package com.kalman03.web.springboot.velocity.views;

import org.apache.velocity.app.VelocityEngine;


public interface VelocityConfig {

	/**
	 * Return the VelocityEngine for the current web application context.
	 * May be unique to one servlet, or shared in the root context.
	 * @return the VelocityEngine
	 */
	VelocityEngine getVelocityEngine();

}