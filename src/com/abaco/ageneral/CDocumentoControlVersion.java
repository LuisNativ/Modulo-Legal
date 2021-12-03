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

public class CDocumentoControlVersion {
	
	public EMensaje agregar(EDocumentoControlVersion eDocumentoControlVersion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAODocumentoControlVersion oDAODocumentoControlVersion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAODocumentoControlVersion= new DAODocumentoControlVersion(oIConexion);
			
			mensaje = oDAODocumentoControlVersion.agregar(eDocumentoControlVersion);
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
	
	public List<EDocumentoControlVersion>listar(){
		IConexion oIConexion = null;
		List<EDocumentoControlVersion> resultado = null;
		DAODocumentoControlVersion oDAODocumentoControlVersion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAODocumentoControlVersion= new DAODocumentoControlVersion(oIConexion);
			resultado = oDAODocumentoControlVersion.listar();
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
