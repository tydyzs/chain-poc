package org.git.modules.clm.front.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ErrorHandler;

public class ServiceErrorHandler implements ErrorHandler {

	private static Log logger = LogFactory.getLog(ServiceErrorHandler.class);

	@Override
	public void handleError(Throwable throwable) {
		logger.error("Error calling CLM MQ Listener", throwable);
	}
}
