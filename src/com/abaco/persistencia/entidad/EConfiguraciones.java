package com.abaco.persistencia.entidad;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EConfiguraciones {

	private static EConfiguraciones configuraciones = new EConfiguraciones();
	private Map<String, String> mapConfiguraciones;

	private EConfiguraciones() {
		mapConfiguraciones = new ConcurrentHashMap<String, String>();
	}

	public static EConfiguraciones obtieneInstancia() {
		return configuraciones;
	}

	public void registraClave(String strClave, String strValor) {
		mapConfiguraciones.put(strClave, strValor);
	}

	public String obtieneValor(String strClave) {
		return mapConfiguraciones.get(strClave);
	}
}
