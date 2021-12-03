package com.abaco.persistencia.acceso;

import com.abaco.persistencia.interfaces.IConexion;

public class InstanciaAcceso {

	protected IConexion objConexion;

	public InstanciaAcceso(IConexion objConexion) {
		this.objConexion = objConexion;
	}
}
