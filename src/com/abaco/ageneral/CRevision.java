package com.abaco.ageneral;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.ageneral.DAORevision;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoServicio;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;
import com.abaco.servicio.laserfiche.Mensaje;

public class CRevision {
	public EMensaje redigirSolicitud(ERevisionSolicitud eRevisionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		ERevisionSolicitud oERevision = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oERevision = new ERevisionSolicitud();
			
			//Validacion de usuario cuente con una area relacionada
			if(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea() == 0){
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			oERevision = oDAORevision.buscarSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			if (oERevision.getIndicadorSesion() == (UIndicadorSesion.OCUPADO)) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_3);
				return mensaje;
			}
			
			if(eRevisionSolicitud.getCodigoEstado() == UEstado.PENDIENTEDEREGISTRO && eRevisionSolicitud.getCodigoServicio() != UTipoServicio.SISTEMA){
				mensaje = oDAORevision.modificarEstado(eRevisionSolicitud, UEstado.ENPROCESODEEVALUACION);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}else{
				mensaje = oDAORevision.modificarEstado(eRevisionSolicitud, UEstado.ENPROCESODEEVALUACION);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				mensaje = oDAORevision.modificarSolicitudSesion(eRevisionSolicitud.getCodigoSolicitud(), UIndicadorSesion.OCUPADO, eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al ingresar a la solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarSolicitud(ERevisionSolicitud eRevisionSolicitud, ERevisionSesion eRevisionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAORevision oDAORevision= null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oBOGeneral = new BOGeneral();
			
			//Validacion de usuario cuente con una area relacionada
			if(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea() == 0){
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			//eRevisionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONSOLICITUD, "", "", ""));
			eRevisionSolicitud.setNumeroMensaje(1);
			
			mensaje = oDAORevision.agregarSolicitud(eRevisionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(eRevisionSolicitud.getIndicadorTemporal() == UIndicadorTemporal.SI){
				mensaje = oDAORevision.eliminarDocumentoTemporal(eRevisionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eRevisionSolicitud.getLstDocumentoCarga().size() > 0){
					mensaje = oDAORevision.agregarDocumentoTemporal(eRevisionSolicitud);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}else{
				mensaje = oDAORevision.eliminarDocumentoTemporal(eRevisionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eRevisionSolicitud.getLstDocumentoCarga() != null){
					for(EDocumentoCarga oEDocumentoCarga: eRevisionSolicitud.getLstDocumentoCarga()){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eRevisionSolicitud, oEDocumentoCarga, 0);
						mensaje.setIdMensaje(mensajeLaserFiche.getCodigo());
						mensaje.setDescMensaje(mensajeLaserFiche.getDescripcion());
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAORevision.agregarDocumento(eRevisionSolicitud, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
				
				/* BK ULTIMO
				if(eRevisionSolicitud.getLstDocumentoCarga().size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: eRevisionSolicitud.getLstDocumentoCarga()){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eRevisionSolicitud, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAORevision.agregarDocumento(eRevisionSolicitud, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
				*/
				
				/*
				if(eRevisionSolicitud.getLstDocumentoCarga().size() > 0){
					mensaje = oDAORevision.agregarDocumento(eRevisionSolicitud);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
					
					mensaje = oDAORevision.agregarDocumentoLaserFiche(eRevisionSolicitud);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
				*/
			}
			
			eRevisionSesion.setCodigoSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			mensaje = oDAORevision.agregarSesion(eRevisionSesion);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarSolicitudManual(ERevisionSolicitud eRevisionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oBOGeneral = new BOGeneral();
			
			//Validacion de usuario cuente con una area relacionada
			if(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea() == 0){
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			//eRevisionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONSOLICITUD, "", "", ""));
			eRevisionSolicitud.setNumeroMensaje(2);
			
			mensaje = oDAORevision.agregarSolicitudManual(eRevisionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitud(ERevisionSolicitud eRevisionSolicitud, ERevisionSesion eRevisionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAORevision oDAORevision= null;
		//ERevisionSolicitud oERevision = null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oBOGeneral = new BOGeneral();
			//oERevision = new ERevisionSolicitud();
			
			//oERevision = oDAORevision.buscarSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			/*
			if(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario() != oERevision.getCodigoEmisor()){
				mensaje.setDescMensaje("Esta consulta fue asignado a otro usuario");
				return mensaje;
			}
			*/
			
			eRevisionSolicitud.setNumeroMensaje(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONMENSAJE, eRevisionSolicitud.getCodigoSolicitud()+"", "", ""));
			
			eRevisionSesion.setCodigoSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			mensaje = oDAORevision.modificarSesion(eRevisionSesion);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAORevision.modificarSolicitud(eRevisionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(eRevisionSolicitud.getIndicadorTemporal() == UIndicadorTemporal.SI){
				mensaje = oDAORevision.eliminarDocumentoTemporal(eRevisionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eRevisionSolicitud.getLstDocumentoCarga().size() > 0){
					mensaje = oDAORevision.agregarDocumentoTemporal(eRevisionSolicitud);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}else{
				mensaje = oDAORevision.eliminarDocumentoTemporal(eRevisionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eRevisionSolicitud.getLstDocumentoCarga().size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: eRevisionSolicitud.getLstDocumentoCarga()){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eRevisionSolicitud, oEDocumentoCarga, 1);
						mensaje.setIdMensaje(mensajeLaserFiche.getCodigo());
						mensaje.setDescMensaje(mensajeLaserFiche.getDescripcion());
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAORevision.agregarDocumento(eRevisionSolicitud, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}
			
			mensaje = oDAORevision.modificarSolicitudSesion(eRevisionSolicitud.getCodigoSolicitud(), UIndicadorSesion.NOOCUPADO, 0);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudManual(ERevisionSolicitud eRevisionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			
			eRevisionSolicitud.setNumeroMensaje(2);
					
			mensaje = oDAORevision.modificarSolicitudManual(eRevisionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudDigitalizacion(ERevisionSolicitud eRevisionSolicitud, ERevisionSesion eRevisionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			
			mensaje = oDAORevision.modificarSolicitudDigitalizacion(eRevisionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(eRevisionSolicitud.getLstDocumentoCarga().size() > 0){
				for(EDocumentoCarga oEDocumentoCarga: eRevisionSolicitud.getLstDocumentoCarga()){
					mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eRevisionSolicitud, oEDocumentoCarga, 1);
					mensaje.setIdMensaje(mensajeLaserFiche.getCodigo());
					mensaje.setDescMensaje(mensajeLaserFiche.getDescripcion());
					if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
						throw new Exception(mensajeLaserFiche.getDescripcion());
					}
					
					oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
					mensaje = oDAORevision.agregarDocumento(eRevisionSolicitud, oEDocumentoCarga);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			eRevisionSesion.setCodigoSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			mensaje = oDAORevision.modificarSesion(eRevisionSesion);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarEstadoCancelar(ERevisionSolicitud eRevisionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		ERevisionSolicitud oERevision = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oERevision = new ERevisionSolicitud();
			
			//Validacion de usuario cuente con una area relacionada
			if(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea() == 0){
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			oERevision = oDAORevision.buscarSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			
			if (oERevision.getIndicadorSesion() == UIndicadorSesion.OCUPADO) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_3);
				return mensaje;
			}
			if (oERevision.getCodigoEstado() == UEstado.ENPROCESODEEVALUACION) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_6);
				return mensaje;
			}
			if (oERevision.getNumeroRevision() > 0) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_5);
				return mensaje;
			}
			mensaje = oDAORevision.modificarEstado(eRevisionSolicitud, UEstado.CANCELADO);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar estado: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarEstadoEliminar(ERevisionSolicitud eRevisionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		ERevisionSolicitud oERevision = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oERevision = new ERevisionSolicitud();
			
			//Validacion de usuario cuente con una area relacionada
			if(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea() == 0){
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			oERevision = oDAORevision.buscarSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			
			if (oERevision.getIndicadorSesion() == UIndicadorSesion.OCUPADO) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_3);
				return mensaje;
			}
			if (oERevision.getNumeroRevision() > 0) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_5);
				return mensaje;
			}
			mensaje = oDAORevision.modificarEstado(eRevisionSolicitud, UEstado.ELIMINADO);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}

			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar estado: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarEnvio(ERevisionSolicitud eRevisionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		ERevisionSolicitud oERevision = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oERevision = new ERevisionSolicitud();
			
			//Validacion de usuario cuente con una area relacionada
			if(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea() == 0){
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			oERevision = oDAORevision.buscarSolicitud(eRevisionSolicitud.getCodigoSolicitud());

			if (oERevision.getIndicadorSesion() == UIndicadorSesion.OCUPADO) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_3);
				return mensaje;
			}
			if (oERevision.getNumeroRevision() > 0) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_5);
				return mensaje;
			}
			mensaje = oDAORevision.modificarEnvio(eRevisionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}

			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar envio: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarSesion(ERevisionSesion eRevisionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision= new DAORevision(oIConexion);
			mensaje = oDAORevision.agregarSesion(eRevisionSesion);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al agregar sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSesion(ERevisionSesion eRevisionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision= new DAORevision(oIConexion);
			mensaje = oDAORevision.modificarSesion(eRevisionSesion);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al agregar sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje liberarSolicitudSesion(long codigoSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision= new DAORevision(oIConexion);
			mensaje = oDAORevision.modificarSolicitudSesion(codigoSolicitud, UIndicadorSesion.NOOCUPADO, 0);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al modificar indicador sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudSesion(long codigoSolicitud, int indicadorSesion, int codigoUsuario){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision= new DAORevision(oIConexion);
			mensaje = oDAORevision.modificarSolicitudSesion(codigoSolicitud, indicadorSesion, codigoUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al modificar indicador sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje verificarSolicitudSesion(){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision= new DAORevision(oIConexion);
			mensaje = oDAORevision.verificarSolicitudSesion();
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al verificar indicador sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarReasignacion(ERevisionSolicitud eRevisionSolicitud, int codigoArea, int codigo, String descripcion, String indTipo){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAORevision oDAORevision= null;
		ERevisionSolicitud oERevision = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAORevision= new DAORevision(oIConexion);
			oERevision = new ERevisionSolicitud();
			
			oERevision = oDAORevision.buscarSolicitud(eRevisionSolicitud.getCodigoSolicitud());
			if (oERevision.getIndicadorSesion() == (UIndicadorSesion.OCUPADO)) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_3);
				return mensaje;
			}
			
			if (codigoArea == 0) {
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_7);
				return mensaje;
			}
			
			mensaje = oDAORevision.agregarReasignacion(eRevisionSolicitud, codigoArea, codigo, descripcion, indTipo);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar reasignacion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EOpcion buscarOpcionPorSolicitud(long codigoSolicitud, EUsuario eUsuario){
		IConexion oIConexion = null;
		EOpcion resultado = null;
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.buscarOpcionPorSolicitud(codigoSolicitud, eUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERevisionSolicitud> listarSolicitud(ERevisionSolicitud eRevisionSolicitud, int indicadorConsulta, int indicadorTipoBusqueda){
		IConexion oIConexion = null;
		List<ERevisionSolicitud> resultado = null;
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.listarSolicitud(eRevisionSolicitud, indicadorConsulta, indicadorTipoBusqueda);	
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud historico: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERevisionTiempo> listarTiempoResumen(long codigo){
		IConexion oIConexion = null;
		List<ERevisionTiempo> resultado = null;
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.listarTiempoResumen(codigo);		
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar tiempo resumen solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERevisionSesion> listarTiempoDetalle(long codigoSolicitud, int codigoArea){
		IConexion oIConexion = null;
		List<ERevisionSesion> resultado = null;
		DAORevision oDAORevision= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.listarTiempoDetalle(codigoSolicitud, codigoArea);		
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar tiempo detalle solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERevisionMensaje> listarMensaje(long codigoSolicitud){
		IConexion oIConexion = null;
		DAORevision oDAORevision= null;
		List<ERevisionMensaje> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.listarMensaje(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar mensaje: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarMensajeTemporal(long codigoSolicitud){
		IConexion oIConexion = null;
		DAORevision oDAORevision= null;
		String resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.buscarMensajeTemporal(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar mensaje: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERevisionDocumento> listarDocumento(long codigoSolicitud){
		IConexion oIConexion = null;
		DAORevision oDAORevision= null;
		List<ERevisionDocumento> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.listarDocumento(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EDocumentoCarga> listarDocumentoTemporal(long codigoSolicitud){
		IConexion oIConexion = null;
		DAORevision oDAORevision= null;
		List<EDocumentoCarga> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORevision = new DAORevision(oIConexion);
			resultado = oDAORevision.listarDocumentoTemporal(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
