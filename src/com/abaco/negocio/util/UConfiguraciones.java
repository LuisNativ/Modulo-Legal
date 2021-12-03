package com.abaco.negocio.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class UConfiguraciones {

	private static UConfiguraciones configuraciones = new UConfiguraciones();
	private Map<String, String> mapConfiguraciones;

	private UConfiguraciones() {
		mapConfiguraciones = new ConcurrentHashMap<String, String>();
	}

	public static UConfiguraciones obtieneInstancia() {
		return configuraciones;
	}

	public void registraClave(String strClave, String strValor) {
		mapConfiguraciones.put(strClave, strValor);
	}

	public String obtieneValor(String strClave) {
		return mapConfiguraciones.get(strClave);
	}
}
