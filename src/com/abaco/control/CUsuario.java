package com.abaco.control;

import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPermiso;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EUsuarioParametro;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.ConexionAS400;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.ageneral.DAORevision;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.ageneral.ERevisionMensaje;
import com.abaco.dao.DAOUsuario;
import com.abaco.persistencia.interfaces.IConexion;

public class CUsuario {
	
	public EMensaje autenticaUsuarioSistema(EUsuarioParametro objEUsuarioParam) {				
		ConexionAS400 conexion = new ConexionAS400();
		CUsuario objCUsuario = new CUsuario();
				
		String usuario = objEUsuarioParam.getNombreUsuario();				
		String codAutorizacion =  objCUsuario.buscarCodigoAutorizacionUsuario(usuario);
						
		String strClaveRegistrada = UFuncionesGenerales.DesencriptarCredenciales(
				UFuncionesGenerales.revisaCadena(objEUsuarioParam.getContrasena()),UFuncionesGenerales.revisaCadena(codAutorizacion));
					
		EMensaje eMensaje = conexion.openConnection(usuario, strClaveRegistrada);		
		conexion.closeConnection();		
		return eMensaje;
	}
	
	public List<EPermiso> listarOpcion(int intIdUsuario, int intIdMenu) {
		List<EPermiso> listaOpcion = null;
		IConexion objConexion = null;
		DAOUsuario objAUsuario = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			objAUsuario = new DAOUsuario(objConexion);
			listaOpcion = objAUsuario.listarOpcion(intIdUsuario, intIdMenu);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener usuario: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return listaOpcion;
	}
	
	public String buscarCodigoAutorizacionUsuario(String nombreUsuario){
		String strResultado = "";
		IConexion objConexion = null;
		DAOUsuario objAUsuario = null;
		
		try {
			objConexion = FabricaConexion.creaConexion();
			objAUsuario = new DAOUsuario(objConexion);
			strResultado = objAUsuario.buscarCodigoAutorizacionUsuario(nombreUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar el codigo autorización: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return strResultado;
	}
	
	public EUsuario buscarUsuario(EUsuarioParametro objEUsuarioParam) {
		EUsuario objEUsuario = null;
		IConexion objConexion = null;
		DAOUsuario objAUsuario = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			objAUsuario = new DAOUsuario(objConexion);
			objEUsuario = objAUsuario.buscarUsuario(objEUsuarioParam);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener usuario: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return objEUsuario;
	}
	
	public EUsuario buscarUsuarioSinValidacionClave(EUsuarioParametro oEUsuarioParam) {
		EUsuario objEUsuario = null;
		IConexion objConexion = null;
		DAOUsuario objAUsuario = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			objAUsuario = new DAOUsuario(objConexion);
			objEUsuario = objAUsuario.buscarUsuarioSinValidacionClave(oEUsuarioParam);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener usuario: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return objEUsuario;
	}
	
	public EUsuario buscarMenuUsuario(EUsuarioParametro objEUsuarioParam) {
		EUsuario objEUsuario = null;
		IConexion objConexion = null;
		DAOUsuario objAUsuario = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			objAUsuario = new DAOUsuario(objConexion);
			objEUsuario = objAUsuario.buscarMenuUsuario(objEUsuarioParam);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener usuario: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return objEUsuario;
	}
}
