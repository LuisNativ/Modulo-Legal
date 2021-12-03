package com.abaco.ageneral;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;

public class CPermisoUsuario {
	
	public EMensaje agregar(EPermisoUsuario ePermisoUsuario){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOPermisoUsuario oDAOPermisoUsuario= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOPermisoUsuario= new DAOPermisoUsuario(oIConexion);
			
			mensaje = oDAOPermisoUsuario.agregar(ePermisoUsuario);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar usuario: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificar(EPermisoUsuario ePermisoUsuario){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOPermisoUsuario oDAOPermisoUsuario= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOPermisoUsuario= new DAOPermisoUsuario(oIConexion);
			
			mensaje = oDAOPermisoUsuario.modificar(ePermisoUsuario);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar usuario: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje eliminar(EPermisoUsuario ePermisoUsuario){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOPermisoUsuario oDAOPermisoUsuario= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOPermisoUsuario= new DAOPermisoUsuario(oIConexion);
			
			mensaje = oDAOPermisoUsuario.eliminar(ePermisoUsuario);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar usuario: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EPermisoUsuario>listar(){
		IConexion oIConexion = null;
		List<EPermisoUsuario> resultado = null;
		DAOPermisoUsuario oDAOPermisoUsuario= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOPermisoUsuario = new DAOPermisoUsuario(oIConexion);
			resultado = oDAOPermisoUsuario.listar();
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPermisoUsuario>listarPorArea(EUsuario eUsuario){
		IConexion oIConexion = null;
		List<EPermisoUsuario> resultado = null;
		DAOPermisoUsuario oDAOPermisoUsuario= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOPermisoUsuario = new DAOPermisoUsuario(oIConexion);
			resultado = oDAOPermisoUsuario.listarPorArea(eUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
