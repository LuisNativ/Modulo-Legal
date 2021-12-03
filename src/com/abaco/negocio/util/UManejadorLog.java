package com.abaco.negocio.util;

import org.jboss.logging.Logger;

public class UManejadorLog {

	static final Logger logger = Logger.getLogger(UManejadorLog.class.getName());

	public static void log(String strMensaje) {
		logger.info(strMensaje);
	}

	public static void error(String strMensaje) {
		logger.error(strMensaje);
	}

	public static void fatal(String strMensaje) {
		logger.fatal(strMensaje);
	}

	public static void debug(String strMensaje) {
		logger.debug(strMensaje);
	}

	public static void warn(String strMensaje) {
		logger.warn(strMensaje);
	}

	public static void trace(String strMensaje) {
		logger.trace(strMensaje);
	}

	public static void log(Object objeto) {
		logger.info(objeto);
	}

	public static void error(String strMensaje, Exception objEx) {
		logger.error(strMensaje, objEx);
	}

	public static void error(Object objeto) {
		logger.error(objeto);
	}

	public static void fatal(Object objeto) {
		logger.fatal(objeto);
	}

	public static void debug(Object objeto) {
		logger.debug(objeto);
	}

	public static void warn(Object objeto) {
		logger.warn(objeto);
	}

	public static void trace(Object objeto) {
		logger.trace(objeto);
	}
}
