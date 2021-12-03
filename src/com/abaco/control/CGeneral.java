package com.abaco.control;

import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EGeneralTipoCambio;
import com.abaco.entidad.EGeneralTipoCambioParametro;
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.ageneral.DAOSolicitudCredito;
import com.abaco.ageneral.EDocumentoRequerido;
import com.abaco.ageneral.EOperacionDocumentoRequerido;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.ageneral.EServicio;
import com.abaco.ageneral.ETercero;
import com.abaco.dao.DAOGeneral;
import com.abaco.persistencia.interfaces.IConexion;

public class CGeneral {
	
	public List<EUsuario> listarUsuarioPorAreaReasignacion(EUsuario eUsuario){
		IConexion oIConexion = null;
		List<EUsuario> resultado = null;
		DAOGeneral oDAOGeneral = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.listarUsuarioPorAreaReasignacion(eUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	/*
	public List<EOperacionDocumentoRequerido> listarDocumentoRequeridoPorTipoEvaluacion(int codigoTipoEvaluacion){
		IConexion oIConexion = null;
		List<EOperacionDocumentoRequerido> resultado = null;
		DAOGeneral oDAOGeneral = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.listarDocumentoRequeridoPorTipoEvaluacion(codigoTipoEvaluacion);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	*/
	
	public List<EDocumentoRequerido> listarDocumentoRequeridoSolicitudCredito(){
		IConexion oIConexion = null;
		List<EDocumentoRequerido> resultado = null;
		DAOGeneral oDAOGeneral = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.listarDocumentoRequeridoSolicitudCredito();
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EServicio> listarServicio(){
		IConexion oIConexion = null;
		List<EServicio> resultado = null;
		DAOGeneral oDAOGeneral = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.listarServicio();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar representante legal " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGeneralTipoCambio buscarGeneralTipoCambio(EGeneralTipoCambioParametro objParam) {
		IConexion oIConexion = null;
		DAOGeneral oDAOGeneral = null;
		EGeneralTipoCambio resultado = null;
		try {
			if (objParam == null) {
				objParam = new EGeneralTipoCambioParametro();				
			}
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.buscarGeneralTipoCambio(objParam.getAnio(),	
																						objParam.getMes(),
																						objParam.getMoneda(),
																						objParam.getDia());
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener usuario: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EPlantillaEmail buscarPlantillaEmail(int codigoMsg) {
		IConexion objConexion = null;
		DAOGeneral oAGeneral = null;
		EPlantillaEmail resultado = null;

		try {
			objConexion = FabricaConexion.creaConexion();
			oAGeneral = new DAOGeneral(objConexion);
			resultado = oAGeneral.buscarPlantillaEmail(codigoMsg);
		} catch (Exception e) {
			UManejadorLog.error("Error al obtener la plantilla de email : " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarCorreoJefeInmediato(String usuario){
		IConexion oIConexion = null;
		DAOGeneral oDAOGeneral = null;
		String correo = "";
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			correo = oDAOGeneral.buscarCorreoJefeInmediato(usuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener el correo del jefe: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return correo;
	}
	
	public String buscarRutaWeb(long codigoItem){
		IConexion oIConexion = null;
		String resultado = "";
		DAOGeneral oDAOGeneral = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.buscarRutaWeb(codigoItem);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarCorreoUsuario(int codigoUsuario){
		IConexion oIConexion = null;
		DAOGeneral oDAOGeneral = null;
		String resultado = "";
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.buscarCorreoUsuario(codigoUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener el correo de usuario: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarNombreUsuarioSiaf(int codigoUsuario){
		IConexion oIConexion = null;
		DAOGeneral oDAOGeneral = null;
		String resultado = "";
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.buscarNombreUsuarioSiaf(codigoUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener el nombre de usuario siaf: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public int generarCorrelativo(int tabla, String codigo1, String codigo2, String codigo3){
		IConexion oIConexion = null;
		int resultado = 0;
		DAOGeneral oDAOGeneral = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGeneral = new DAOGeneral(oIConexion);
			resultado = oDAOGeneral.generarCorrelativo(tabla, codigo1, codigo2, codigo3);
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
