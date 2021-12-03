package com.abaco.bo;

import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPermiso;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EUsuarioParametro;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.ageneral.ERevisionMensaje;
import com.abaco.control.CUsuario;

public class BOUsuario {
	CUsuario oCUsuario = new CUsuario();
	
	public EMensaje autenticaUsuarioSistema(EUsuarioParametro objEUsuarioParam) {
		EMensaje resultado = oCUsuario.autenticaUsuarioSistema(objEUsuarioParam);
		return resultado;
	}
	public List<EPermiso> listarOpcion(int intIdUsuario, int intIdMenu) {
		List<EPermiso> lista = oCUsuario.listarOpcion(intIdUsuario, intIdMenu);
		return lista;
	}
	public String buscarCodigoAutorizacionUsuario(String nombreUsuario) {
		String resultado = oCUsuario.buscarCodigoAutorizacionUsuario(nombreUsuario);
		return resultado;
	}
	public EUsuario buscarUsuario(EUsuarioParametro objEUsuarioParam) {		
		EUsuario resultado = oCUsuario.buscarUsuario(objEUsuarioParam);
		return resultado;
	}
	public EUsuario buscarUsuarioSinValidacionClave(EUsuarioParametro objEUsuarioParam) {
		EUsuario resultado = oCUsuario.buscarUsuarioSinValidacionClave(objEUsuarioParam);
		return resultado;
	}
	public EUsuario buscarMenuUsuario(EUsuarioParametro objEUsuarioParam) {	
		EUsuario resultado = oCUsuario.buscarMenuUsuario(objEUsuarioParam);
		return resultado;
	}
}

