package com.abaco.ageneral;

import java.util.ArrayList;
import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.bo.BOGeneral;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;
import com.abaco.servicio.laserfiche.Mensaje;

public class CGarantia {
	
	public EMensaje agregarGarantiaPendienteRegistro(EGarantiaSolicitud eGarantiaSolicitud,EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(eGarantiaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.agregarGarantiaPendienteRegistro(eGarantiaSolicitud,eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaMantenimiento(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaDetalle(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaDetalle(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar detalle garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	
	public EMensaje registrarGarantiaSolicitudAnexoF7325(){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.registrarGarantiaSolicitudAnexoF7325();
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al registrar Garantía Solicitud Anexo F7325: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje  registrarTipoGarantia(EGeneral tipoGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.registrarTipoGarantia(tipoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al registrar Tipo Garantía: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje  registrarPoliza(EPoliza ePoliza)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.registrarPoliza(ePoliza);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al registrar Poliza: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje  modificarTipoGarantia(EGeneral tipoGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarTipoGarantia(tipoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Tipo Garantía: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaPoliza(EGarantia eGarantia)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaPoliza(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Poliza Seguro: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarPoliza(EPoliza ePoliza)   {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarPoliza(ePoliza) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Poliza: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarTipoIngresoPorcentaje(EGarantiaSolicitud eGarantiaSolicitud)   {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarTipoIngresoPorcentaje(eGarantiaSolicitud) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaTramite(eGarantiaTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarGarantiaTramite(EGarantiaTramite eGarantiaTramite,EGarantiaTramite eGarantiaTramiteAsiento) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarModificarGarantiaTramite(eGarantiaTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			/*
			if(eGarantiaTramiteAsiento != null){
				mensaje = oDAOGarantia.agregarAsientoGarantiaTramite(eGarantiaTramiteAsiento);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}*/
			
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar/Modificar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarAsientoGarantiaTramite(EGarantiaTramite eGarantiaTramiteAsiento) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.agregarAsientoGarantiaTramite(eGarantiaTramiteAsiento);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar Asiento Tramite Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
		
	public EMensaje modificarGarantiaMantenimiento(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			if(lstDocumentoCarga != null){
				if(lstDocumentoCarga.size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						eGarantia.setFirmaDocumento("NO");
						eGarantia.setEstadoDocumento(UEstado.PENDIENTEFIRMA);
						mensaje = oDAOOperacion.agregarDocumentoGarantia(eGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}	
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar Documento Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaTramite(eGarantiaTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Trámite garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(eGarantiaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Trámite garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitudSIAF(EGarantiaSolicitud eGarantiaSolicitud,EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitudSIAF(eGarantiaSolicitud,eGarantiaDetalleSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Trámite garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje actualizarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud,
			EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud,EGarantiaSolicitud oEGarantiaAsociadaSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitudSIAF(eGarantiaSolicitud,eGarantiaDetalleSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(oEGarantiaAsociadaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Garantia Solicitud : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorLiberar(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaPorLiberar(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia por Liberar " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaLiberada(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaLiberada(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia Liberadas " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaConCreditoVigente(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaConCreditoVigente(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia por Liberar " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarGarantiaVinculada(EGarantia eGarantia){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaVinculada(eGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarGarantiaDetalle(EGarantia eGarantia ) {
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaDetalle(eGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EAsignacionContratoGarantia> listarCreditosAsociadosGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		List<EAsignacionContratoGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditosAsociadosGarantia(codigoGarantia);			
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje eliminarPoliza(EPoliza ePoliza) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.eliminarPoliza(ePoliza) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar Poliza: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EAsignacionContratoGarantia> listarClientesGarantizados(EGarantia eGarantia ) {
		IConexion oIConexion = null;
		List<EAsignacionContratoGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarClientesGarantizados(eGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaTramite> listarHistoricoTramiteGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		List<EGarantiaTramite> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarHistoricoTramiteGarantia(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al Historico Tramite Garantia" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaTramite> listarAsientosTramiteGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		List<EGarantiaTramite> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarAsientosTramiteGarantia(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al Historico Tramite Garantia" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorConstituir(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaPorConstituir(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarHistoricoGarantiaSolicitud(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarHistoricoGarantiaSolicitud(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Historico Garantia Solicitud  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje agregaGarantiaPendienteRegistro(EGarantiaSolicitud eOGarantiaSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregaGarantiaPendienteRegistro(eOGarantiaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar Garantía Pendiente de Registro : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EGarantia> listarGarantiaVigente(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaVigente(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantias Vigentes " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarGarantia(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantia(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantias " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarSolicitudAsociadaGarantia(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudAsociadaGarantia(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar. " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaSolicitud(long numeroSolicitud){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaSolicitud(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar garantia " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPoliza> listarPolizaSeguro(int codigo, String descripcion, String descripcion2){
		IConexion oIConexion = null;
		List<EPoliza> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarPolizaSeguro(codigo, descripcion,descripcion2);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	

	public List<EPoliza> buscarPolizaSeguro(EPoliza ePoliza){
		IConexion oIConexion = null;
		List<EPoliza> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarPolizaSeguro(ePoliza);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Póliza Seguro " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EPoliza buscarPoliza(EPoliza ePoliza){
		IConexion oIConexion = null;
		EPoliza resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarPoliza(ePoliza);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Póliza " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarUltimaGarantiaGenerada(){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarUltimaGarantiaGenerada();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaSolicitud buscarSolicitudxGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarSolicitudxGarantia(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarAnexoGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		EPersona ePersona = null;
		List<EPersona> lstPersona = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarAnexoGarantia(codigoGarantia);	
			if(resultado != null){
				lstPersona = new ArrayList<EPersona>();
				for(int i=1;i<6;i++){
					ePersona = new EPersona();
					ePersona.setCodigo(i== 1 ? resultado.getCodigoPropietario2(): 
						               i== 2 ? resultado.getCodigoPropietario3():
						               i== 3 ? resultado.getCodigoPropietario4():
						               i== 4 ? resultado.getCodigoPropietario5():
						               i== 5 ? resultado.getCodigoPropietario6():0);
					ePersona.setNombreCorte(i== 1 ? resultado.getDescripcionPropietario2(): 
			               i== 2 ? resultado.getDescripcionPropietario3():
			               i== 3 ? resultado.getDescripcionPropietario4():
			               i== 4 ? resultado.getDescripcionPropietario5():
			               i== 5 ? resultado.getDescripcionPropietario6():"");
					if(ePersona.getCodigo() != 0){
						lstPersona.add(ePersona);
					}
					
					
				}
				resultado.setLstPropietario(lstPersona);
				
			}
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaTramite buscarUltimoAsientoGarantiaTramite(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantiaTramite resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarUltimoAsientoGarantiaTramite(codigoGarantia);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	
	public List<ESaldoServicio> obtenerSaldosServiciosCliente(int codigoCliente){
		IConexion oIConexion = null;
		List<ESaldoServicio> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.obtenerSaldosServiciosCliente(codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al Listar Saldos de Servicios por Clientes" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaDocumentoSolicitado> listarDocumentoSolicitado(long numeroSolicitud){
		IConexion oIConexion = null;
		List<EGarantiaDocumentoSolicitado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarDocumentoSolicitado(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar documento solicitado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoRelacionado(int codigo){
		IConexion oIConexion = null;
		List<EGarantiaCreditoRelacionado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditoRelacionado(codigo);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar credito relacionado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoVigenteRelacionado(int codigo){
		IConexion oIConexion = null;
		List<EGarantiaCreditoRelacionado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditoVigenteRelacionado(codigo);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar credito relacionado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantia(codigoGarantia);
			
			switch(resultado.getCodigoTipoGarantia()){
			case UTipoGarantia.PREDIO:		
				resultado.setObervacionGarantia
				       (UFuncionesGenerales.revisaCadena(resultado.getUbicacion2())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionB())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionC())+
						UFuncionesGenerales.revisaCadena(resultado.getComentario()));
				break;
			case UTipoGarantia.VEHICULAR:
				resultado.setObervacionGarantia
			       (UFuncionesGenerales.revisaCadena(resultado.getDescripcionA())+
					UFuncionesGenerales.revisaCadena(resultado.getDescripcionB())+
					UFuncionesGenerales.revisaCadena(resultado.getDescripcionC()));
				break;
			default:
			}
		
			if(resultado.getCodigoTipoGarantia()>21 && resultado.getCodigoTipoGarantia()!=88){
				resultado.setObervacionGarantia
			       (UFuncionesGenerales.revisaCadena(resultado.getUbicacion2())+
					UFuncionesGenerales.revisaCadena(resultado.getDescripcionB())+
					UFuncionesGenerales.revisaCadena(resultado.getDescripcionC())+
					UFuncionesGenerales.revisaCadena(resultado.getComentario()));
			}
			
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia asociada " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ETipoCambio buscarTipoCambioDiario(){
		IConexion oIConexion = null;
		ETipoCambio resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarTipoCambioDiario();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Tipo de Cambio Diario " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaTramite buscarGarantiaTramite(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantiaTramite resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaTramite(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia tramite " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarGarantiaSolicitudCompleto(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud eGarantiaSolicitudF7325 = null;
		EGarantiaDetalleSolicitud eGarantiaSolicitudF7363 = null;
		DAOGarantia oDAOGarantia= null;
		EGarantia resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			eGarantiaSolicitudF7325 = oDAOGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia) != null ?
					oDAOGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia) : new EGarantiaSolicitud();
			eGarantiaSolicitudF7363 = oDAOGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia) != null ?
					oDAOGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia) : new EGarantiaDetalleSolicitud();
			resultado = new EGarantia();
			resultado.setCodigoTipoGarantia(eGarantiaSolicitudF7325.getCodigoTipoGarantiaReal());
			resultado.setCodigoMoneda(eGarantiaSolicitudF7325.getCodigoMonedaGarantia());
			resultado.setCuenta(eGarantiaSolicitudF7325.getCuentaGarantia());
			resultado.setNumeroDocumento(eGarantiaSolicitudF7325.getDocumentoGarantia()+"");
			resultado.setMontoOriginalGarantia(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoGarantia(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoGravamen(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoVariable1(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoValoracion(eGarantiaSolicitudF7325.getMontoValorizacion());
			resultado.setDescripcionMoneda(eGarantiaSolicitudF7325.getDescripcionMonedaGarantia());
			resultado.setAbreviacionMoneda(eGarantiaSolicitudF7325.getAbreviacionMonedaGarantia());
			switch(eGarantiaSolicitudF7325.getCodigoTipoGarantiaReal()){
			 case UTipoGarantia.MAQUINARIA:
				 resultado.setDescripcionA(eGarantiaSolicitudF7363.getMotor());
				 resultado.setDescripcionB(eGarantiaSolicitudF7363.getSerie());
				 resultado.setCodigoTipoBien(eGarantiaSolicitudF7363.getCodigoTipoPrenda());
				 resultado.setMarca(eGarantiaSolicitudF7363.getMarca());
				 resultado.setModelo(eGarantiaSolicitudF7363.getModelo());
				 resultado.setNumeroVariable2(eGarantiaSolicitudF7363.getCantidad()); 
				 //resultado.setDescripcionC("");
				 break;
			 case UTipoGarantia.PREDIO:
				 resultado.setDescripcionA(eGarantiaSolicitudF7363.getUsoPredio());
				 resultado.setCodigoUbigeo(eGarantiaSolicitudF7363.getCodigoUbigeo());
				 resultado.setMontoVariable2(eGarantiaSolicitudF7363.getAreaTerreno());
				 resultado.setMontoVariable3(eGarantiaSolicitudF7363.getAreaConstruida());
				 resultado.setCodigoTipoBien(eGarantiaSolicitudF7363.getCodigoTipoPrenda2());
				 resultado.setNumeroVariable1(eGarantiaSolicitudF7363.getNumeroPisos());
				 resultado.setUbicacion1(eGarantiaSolicitudF7363.getDireccion());	
				// resultado.setDescripcionB("");resultado.setDescripcionC("");
				 break;
			 case UTipoGarantia.VEHICULAR:
				 //resultado.setDescripcionA(""); resultado.setDescripcionB(""); resultado.setDescripcionC("");
					resultado.setPlaca(eGarantiaSolicitudF7325.getPlaca());
					resultado.setClase(eGarantiaSolicitudF7325.getClase());
					resultado.setMarca(eGarantiaSolicitudF7325.getMarca());
					resultado.setModelo(eGarantiaSolicitudF7325.getModelo());
					resultado.setUbicacion2(eGarantiaSolicitudF7363.getMotor());
					resultado.setCarroceria(eGarantiaSolicitudF7325.getCarroceria());
					resultado.setComentario(eGarantiaSolicitudF7325.getColor());
					resultado.setAnioFabricacion(eGarantiaSolicitudF7325.getAnioFabricacion()); 
					resultado.setUbicacion1(eGarantiaSolicitudF7363.getSerie());	
				 break;
			}



		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	
	public EGarantiaSolicitud buscarGarantiaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaSolicitud buscarGarantiaAsociadaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaAsociadaSolicitud(numeroSolicitud, secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaDetalleSolicitud buscarGarantiaDetalleSolicitud(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaDetalleSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia detalle solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
