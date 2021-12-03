package com.abaco.ageneral;

import java.util.List;

import com.abaco.ageneral.DAOSolicitudCredito;
import com.abaco.entidad.EMensaje;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.bo.BOGeneral;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;
import com.abaco.servicio.laserfiche.Mensaje;

public class CSolicitudCredito {
	
	public List<EEvaluacionSolicitudCreditoLegal> listarInformeLegalPorRegistrar(){
		IConexion oIConexion = null;
		List<EEvaluacionSolicitudCreditoLegal> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarInformeLegalPorRegistrar();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EDeudor> listarDeudor(int codigoCliente, long numeroSolicitud){
		IConexion oIConexion = null;
		List<EDeudor> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarDeudor(codigoCliente, numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar deudor " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EAval> listarAval(int codigoCliente,long numeroSolicitud){
		IConexion oIConexion = null;
		List<EAval> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarAval(codigoCliente,numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Aval " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ETercero buscarAval(int codigoAval,long nroSolicitud){
		IConexion oIConexion = null;
		ETercero resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarAval(codigoAval,nroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener informacion de aval:  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EEmail buscarEmailCliente(int codigoCliente){
		IConexion oIConexion = null;
		EEmail resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarEmailCliente(codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Email del Cliente:  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ESolicitudCredito buscarSolicitudCredito(long numeroSolicitud){
		IConexion oIConexion = null;
		ESolicitudCredito resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarSolicitudCredito(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ESolicitudCredito buscarSolicitudCartaFianza(long numeroSolicitud){
		IConexion oIConexion = null;
		ESolicitudCredito resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarSolicitudCartaFianza(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Datos de la Solicitud de Credito" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ESolicitudCredito> listarSolicitudCredito(int codigoCliente){
		IConexion oIConexion = null;
		List<ESolicitudCredito> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarSolicitudCredito(codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Solicitudes de Credito de un Socio " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EObservacionNegocios> listarObservacionNegociosDetalle(long numeroSolicitud){
		IConexion oIConexion = null;
		List<EObservacionNegocios> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarObservacionNegociosDetalle(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar observacion negocios " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ESuscripcion> listarSolicitudSuscripcion(long numeroSolicitud, int codigoCliente, int codigoTipoCliente){
		IConexion oIConexion = null;
		List<ESuscripcion> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarSolicitudSuscripcion(numeroSolicitud, codigoCliente, codigoTipoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Suscripcion " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ESuscripcion> listarClienteSuscripcion(String numeroDocumento){
		IConexion oIConexion = null;
		List<ESuscripcion> resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.listarClienteSuscripcion(numeroDocumento);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Suscripcion " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ECredito buscarCredito(long numeroSolicitud){
		IConexion oIConexion = null;
		ECredito resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarCredito(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	
	public EEvaluacionSolicitudCreditoLegal buscarEvaluacionSolicitudCreditoLegal(long numeroSolicitud, int codigoCliente){
		IConexion oIConexion = null;
		EEvaluacionSolicitudCreditoLegal resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarEvaluacionSolicitudCreditoLegal(numeroSolicitud, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EInformeLegalAdicional buscarInformeLegalAdicional(long numeroSolicitud, int codigoCliente, int codigoTipoCliente){
		IConexion oIConexion = null;
		EInformeLegalAdicional resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarInformeLegalAdicional(numeroSolicitud, codigoCliente, codigoTipoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener informe legal adicional " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EDeudorAdicional buscarDeudorAdicional(int codigoDeudor){
		IConexion oIConexion = null;
		EDeudorAdicional resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarDeudorAdicional(codigoDeudor);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener deudor adicional " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EDeudorEstado buscarDeudorEstado(int codigoDeudor){
		IConexion oIConexion = null;
		EDeudorEstado resultado = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado = oDAOSolicitudCredito.buscarDeudorEstado(codigoDeudor);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener deudor estado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarObservacionDetalle(long numeroSolicitud, int ubicacion, int secuencia){
		IConexion oIConexion = null;
		String resultado = null;
		List<EObservacionLegal> resultado2 = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			resultado2 = oDAOSolicitudCredito.buscarObservacionDetalle(numeroSolicitud, ubicacion, secuencia);
			
			if(resultado2 != null){
				resultado = "";
				for(EObservacionLegal obj: resultado2){
					resultado = resultado + obj.getDescripcionMensaje()+"\n";
				}
			}
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener evaluacion detalle " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarObservacionNegocios(long numeroSolicitud, int secuencia){
		IConexion oIConexion = null;
		String resultado = null;
		EObservacionNegocios oEObservacionNegocios = null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			oEObservacionNegocios = oDAOSolicitudCredito.buscarObservacionNegocios(numeroSolicitud, secuencia);
			
			if(oEObservacionNegocios != null){
				resultado = oEObservacionNegocios.getObservacion();
			}
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener evaluacion negocios " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
