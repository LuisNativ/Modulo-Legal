package com.abaco.ageneral;
import java.util.List;

import com.abaco.entidad.EFichaNatural;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EUsuario;

public class BOCliente {
	CCliente oCCliente= new CCliente();
	
	public EMensaje registrarTercero(ETercero eTercero){
		EMensaje resultado=oCCliente.registrarTercero(eTercero);
		return resultado;
	}
	public EMensaje modificarTercero(ETercero eTercero){
		EMensaje resultado=oCCliente.modificarTercero(eTercero);
		return resultado;
	}
	public List<EPersona> listarSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lista = oCCliente.listarSocio(objEPersonaParam);
		return lista;
	}
	public List<EPersona> listarSocioyTercero(int codigo, String descripcion){
		List<EPersona> lista = oCCliente.buscarSocioyTercero(codigo,descripcion);
		return lista;
	}
	public List<ETercero> listarNotarios(int codigo, String descripcion){
		List<ETercero> lista = oCCliente.listarNotarios(codigo,descripcion);
		return lista;
	}
	public List<EPersona> listarPostulante(EPersonaParametro objEPersonaParam) {
		List<EPersona> lista = oCCliente.listarPostulante(objEPersonaParam);
		return lista;
	}
	public List<EPersona> listarTercero(EPersonaParametro objEPersonaParam, int codigoTipoBusqueda) {
		List<EPersona> lista = oCCliente.listarTercero(objEPersonaParam, codigoTipoBusqueda);
		return lista;
	}
	public List<EPersona> listarNoSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lista = oCCliente.listarNoSocio(objEPersonaParam);
		return lista;
	}
	public ECliente buscarSocio(int codigoCliente){
		ECliente resultado = oCCliente.buscarSocio(codigoCliente);
		return resultado;
	}
	public ECliente buscarUsuarioDetalle(EUsuario eUsuario){
		ECliente resultado = oCCliente.buscarUsuarioDetalle(eUsuario);
		return resultado;
	}
	public ECliente buscarPostulante(int codigoCliente){
		ECliente resultado = oCCliente.buscarPostulante(codigoCliente);
		return resultado;
	}
	public ETercero buscarTercero(long codigoCliente){
		ETercero resultado = oCCliente.buscarTercero(codigoCliente);
		return resultado;
	}
	public ECliente buscarNoSocio(int codigoCliente){
		ECliente resultado = oCCliente.buscarNoSocio(codigoCliente);
		return resultado;
	}
	public ETercero buscarTerceroAnexo(long codigoCliente){
		ETercero resultado = oCCliente.buscarTerceroAnexo(codigoCliente);
		return resultado;
	}
	public ETercero buscarCliente_Info_PersonaNatural(long codigoCliente){
		ETercero resultado = oCCliente.buscarCliente_Info_PersonaNatural(codigoCliente);
		return resultado;
	}
	public EClienteConstitucionEmpresa buscarConstitucionEmpresa(long codigoCliente){
		EClienteConstitucionEmpresa resultado = oCCliente.buscarConstitucionEmpresa(codigoCliente);
		return resultado;
	}
	public EUsuario buscarPermisoUsuario(String nombreUsuario){
		EUsuario resultado = oCCliente.buscarPermisoUsuario(nombreUsuario);
		return resultado;
	}
}
