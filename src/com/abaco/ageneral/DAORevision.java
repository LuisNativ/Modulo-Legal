package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAORevision extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_REVISION_SOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REVISION_SOLICITUD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	private static final String SP_ABACOINLEGAL_INS_REVISION_SOLICITUDMANUAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REVISION_SOLICITUDMANUAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	private static final String SP_ABACOINLEGAL_INS_REVISION_SESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REVISION_SESION("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO("+parametrosSP(11)+") }";
	private static final String SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO_TEMP = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO_TEMP("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_INS_REVISION_REASIGNACION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REVISION_REASIGNACION("+parametrosSP(10)+") }";
	
	private static final String SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_SOLICITUDMANUAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_SOLICITUDMANUAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_DIGITALIZACION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_DIGITALIZACION("+parametrosSP(7)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_SESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_SESION("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_ENVIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_ENVIO("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_ESTADO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_ESTADO("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_VERIFICARSESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_VERIFICARSESION("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_SESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_SESION("+parametrosSP(5)+") }";
	
	private static final String SP_ABACOINLEGAL_DEL_REVISION_DOCUMENTO_TEMP = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_REVISION_DOCUMENTO_TEMP("+parametrosSP(3)+") }";
	
	private static final String SP_ABACOINLEGAL_BUS_REVISION_SOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_SOLICITUD("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_MENSAJE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_MENSAJE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_MENSAJE_TEMP="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_MENSAJE_TEMP("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_DOCUMENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_DOCUMENTO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_DOCUMENTO_TEMP="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_DOCUMENTO_TEMP("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_TIEMPORESUMEN = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_TIEMPORESUMEN("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_TIEMPODETALLE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_TIEMPODETALLE("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_REVISION_OPCIONPORSOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_REVISION_OPCIONPORSOLICITUD("+parametrosSP(3)+") }";
	
	private static final String SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_OTROS = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_OTROS("+parametrosSP(10)+") }";
	private static final String SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_LEGAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_LEGAL("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_DIGITALIZACION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_DIGITALIZACION("+parametrosSP(2)+") }";
	
	private static String parametrosSP(int numeroDeParametros) {
        StringBuilder cadena = new StringBuilder();

        for (int i = 1; i <= numeroDeParametros; i++) {
            cadena.append("?,");
        }

        int longitudCadena = cadena.length();
        StringBuilder removeUltimoCaracter = new StringBuilder(cadena);
        StringBuilder cadenaFinal = removeUltimoCaracter.deleteCharAt(longitudCadena - 1);
        return cadenaFinal.toString();
    }
	
	public DAORevision(IConexion objConexion) {
		super(objConexion);
	}

	public EMensaje agregarSolicitud(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoNivel());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEstado());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoAreaReceptor());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoReceptor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionReceptor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAsunto());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorTemporal());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensaje());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaOrigen());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaOrigen()));
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getNombreCompleto());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_SOLICITUD, lstParametrosEntrada);						
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarSolicitudManual(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoNivel());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoServicio());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEstado());
			//lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoAreaEmisor());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEmisor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionEmisor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAsunto());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoMotivo());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAdicionalMotivo());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorTemporal());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorDigitalizacion());
			//lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensaje());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaInicio());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getHoraInicio()));
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaFin());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getHoraFin()));
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getNombreCompleto());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_SOLICITUDMANUAL, lstParametrosEntrada);						
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitud(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoNivel());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEstado());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoAutorizaJefe());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoAreaReceptor());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoReceptor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionReceptor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAsunto());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoMotivo());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAdicionalMotivo());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorTemporal());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensaje());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudManual(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoNivel());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoServicio());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEstado());
			//lstParametrosEntrada.add(eRevisionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoAreaEmisor());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEmisor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionEmisor());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAsunto());
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoMotivo());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAdicionalMotivo());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorTemporal());
			lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorDigitalizacion());
			//lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensaje());
			lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaInicio());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getHoraInicio()));
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaFin());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getHoraFin()));
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getNombreCompleto());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_SOLICITUDMANUAL, lstParametrosEntrada);						
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudDigitalizacion(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getNumeroMensaje());
			//lstParametrosEntrada.add(eRevisionSolicitud.getIndicadorDigitalizacion());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_DIGITALIZACION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	/*
	public EMensaje agregarDocumento(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		List<EDocumentoCarga> lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		
		try {
			lstDocumentoCarga=eRevisionSolicitud.getLstDocumentoCarga();	
			for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoMensaje());
				lstParametrosEntrada.add(oEDocumentoCarga.getNombre());
				lstParametrosEntrada.add(oEDocumentoCarga.getNombreLaserFiche());
				lstParametrosEntrada.add(oEDocumentoCarga.getNombreOriginal());
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
				lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
				lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
				
				mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO, lstParametrosEntrada);
			}
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	*/
	public EMensaje agregarDocumento(ERevisionSolicitud eRevisionSolicitud, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getNumeroMensaje());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoTemporal(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		List<EDocumentoCarga> lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		
		try {
			lstDocumentoCarga=eRevisionSolicitud.getLstDocumentoCarga();	
			for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
				lstParametrosEntrada.add(oEDocumentoCarga.getNombre());
				lstParametrosEntrada.add(oEDocumentoCarga.getData());
				lstParametrosEntrada.add(oEDocumentoCarga.getSize());
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
				lstParametrosEntrada.add(eRevisionSolicitud.getFechaRegistro());
				lstParametrosEntrada.add(formato.format(eRevisionSolicitud.getFechaRegistro()));
				
				mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_DOCUMENTO_TEMP, lstParametrosEntrada);
			}
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarDocumentoTemporal(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_REVISION_DOCUMENTO_TEMP, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	/*
	public EMensaje agregarDocumentoLaserFiche(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();
		UManejadorArchivo3 oUManejoArchivo = new UManejadorArchivo3();
		List<EDocumentoCarga> lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		
		try {
			lstDocumentoCarga=eRevisionSolicitud.getLstDocumentoCarga();
			for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
				mensaje = oUManejoArchivo.guardarArchivoSolicitud(1, eRevisionSolicitud.getCodigoSolicitud()+"", oEDocumentoCarga, 2);
			}
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	*/
	
	public EMensaje modificarEstado(ERevisionSolicitud eRevisionSolicitud, int codigoEstado) {
		EMensaje mensaje = new EMensaje();		
		List<Object> lstParametrosEntrada;
		Date hoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(codigoEstado);
			/*lstParametrosEntrada.add(eRevisionSolicitud.getCodigoMotivo());*/
			/*lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionMensaje());*/
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(hoy);
			lstParametrosEntrada.add(formato.format(hoy));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_ESTADO, lstParametrosEntrada);			
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarEnvio(ERevisionSolicitud eRevisionSolicitud) {
		EMensaje mensaje = new EMensaje();		
		List<Object> lstParametrosEntrada;
		Date hoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(hoy);
			lstParametrosEntrada.add(formato.format(hoy));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_ENVIO, lstParametrosEntrada);			
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarSesion(ERevisionSesion eRevisionSesion) {
		EMensaje mensaje = new EMensaje();			
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSesion.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSesion.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSesion.getFechaRegistro()));
			lstParametrosEntrada.add(eRevisionSesion.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSesion.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSesion.getFechaModificacion());
			lstParametrosEntrada.add(formato.format(eRevisionSesion.getFechaModificacion()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_SESION, lstParametrosEntrada);			
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSesion(ERevisionSesion eRevisionSesion) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			
			lstParametrosEntrada.add(eRevisionSesion.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionSesion.getCorrelativoSesion());
			lstParametrosEntrada.add(eRevisionSesion.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eRevisionSesion.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eRevisionSesion.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRevisionSesion.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_SESION, lstParametrosEntrada);		
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudSesion(long codigoSolicitud, int indicadorSesion, int codigoUsuario) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(indicadorSesion);
			lstParametrosEntrada.add(codigoUsuario);
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_SESION, lstParametrosEntrada);		
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje verificarSolicitudSesion() {
		EMensaje mensaje = new EMensaje();		
		List<Object> lstParametrosEntrada;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REVISION_SOLICITUD_VERIFICARSESION, lstParametrosEntrada);						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarReasignacion(ERevisionSolicitud eRevisionSolicitud, int codigoArea, int codigo, String descripcion, String indTipo) {
		EMensaje mensaje = new EMensaje();		
		List<Object> lstParametrosEntrada;
		Date hoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRevisionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(indTipo);
			lstParametrosEntrada.add(codigoArea);
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(hoy);
			lstParametrosEntrada.add(formato.format(hoy));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REVISION_REASIGNACION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EOpcion buscarOpcionPorSolicitud(long codigoSolicitud, EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EOpcion oEOpcion = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(eUsuario.getCodigoArea());
			lstParametrosEntrada.add(eUsuario.getIdUsuario());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_OPCIONPORSOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					oEOpcion = new EOpcion();
					oEOpcion.setIndicadorEnviar(oResultSet.getInt("INDENVIAR"));
					oEOpcion.setIndicadorReenviar(oResultSet.getInt("INDREENVIAR"));
					oEOpcion.setIndicadorCerrar(oResultSet.getInt("INDCERRAR"));
					oEOpcion.setIndicadorDescartar(oResultSet.getInt("INDDESCARTAR"));
					oEOpcion.setIndicadorCancelar(oResultSet.getInt("INDCANCELAR"));
					oEOpcion.setIndicadorAutorizar(oResultSet.getInt("INDAUTORIZAR"));
					oEOpcion.setIndicadorSolicitarAutorizar(oResultSet.getInt("INDSOLICITARAUTORIZAR"));
					oEOpcion.setIndicadorConfirmarAutorizar(oResultSet.getInt("INDCONFIRMARAUTORIZAR"));
					
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOpcion;
	}
	
	public List<ERevisionSolicitud> listarSolicitud(ERevisionSolicitud eRevisionSolicitud, int indicadorConsulta, int indicadorTipoBusqueda) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionSolicitud oERevision= null;
		List<ERevisionSolicitud> lstRevision = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			if(indicadorConsulta == 1){
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_DIGITALIZACION, lstParametrosEntrada, null);
			}else if(indicadorConsulta == 2){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
				lstParametrosEntrada.add(eRevisionSolicitud.getNombrePersona());
				lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAsunto());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoNivel());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEstado());
				lstParametrosEntrada.add(eRevisionSolicitud.getFechaInicioDesde());
				lstParametrosEntrada.add(eRevisionSolicitud.getFechaInicioHasta());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoUsuarioEvaluacion());
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_LEGAL, lstParametrosEntrada, null); 
			}else if(indicadorConsulta == 3){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getCodigoArea());
				lstParametrosEntrada.add(eRevisionSolicitud.getUsuarioRegistro().getIdUsuario());
				lstParametrosEntrada.add(indicadorTipoBusqueda);
				lstParametrosEntrada.add(eRevisionSolicitud.getNombrePersona());
				lstParametrosEntrada.add(eRevisionSolicitud.getDescripcionAsunto());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoNivel());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoEstado());
				lstParametrosEntrada.add(eRevisionSolicitud.getFechaInicioDesde());
				lstParametrosEntrada.add(eRevisionSolicitud.getFechaInicioHasta());
				lstParametrosEntrada.add(eRevisionSolicitud.getCodigoUsuarioEvaluacion());
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_REVISION_SOLICITUD_OTROS, lstParametrosEntrada, null);
			}
			
			if (oResultSet != null) {
				lstRevision = new ArrayList<ERevisionSolicitud>();
				while (oResultSet.next()) {
					oERevision=new ERevisionSolicitud();
					oERevision.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oERevision.setCodigoNivel(oResultSet.getInt("CODNVL"));
					oERevision.setCodigoEstado(oResultSet.getInt("CODEST"));
					oERevision.setCodigoServicio(oResultSet.getInt("CODSERVICIO"));
					oERevision.setCodigoAutorizaJefe(oResultSet.getInt("CODAJF"));
					oERevision.setNumeroMensaje(oResultSet.getInt("NUMMSJ"));
					oERevision.setNumeroRevision(oResultSet.getInt("NUMREV"));
					oERevision.setCodigoPersonaRelacion(oResultSet.getInt("CODPERREL"));
					
					oERevision.setCodigoClientePersona(oResultSet.getInt("CODPER"));
					oERevision.setCodigoTipoClientePersona(oResultSet.getInt("CODTIPCLIPER"));
					oERevision.setCodigoTipoDocumentoPersona(oResultSet.getString("CODTIPDOCPER"));
					oERevision.setNumeroDocumentoPersona(oResultSet.getString("NUMDOCPER"));
					oERevision.setNombrePersona(oResultSet.getString("NOMPER"));
					
					oERevision.setCodigoEmisorOrigen(oResultSet.getInt("CODEMISORORI"));
					oERevision.setDescripcionEmisorOrigen(oResultSet.getString("DESCEMISORORI"));
					oERevision.setDescripcionAbreviacionEmisorOrigen(oResultSet.getString("DESCABREVEMISORORI"));
					
					oERevision.setCodigoAreaEmisor(oResultSet.getInt("CODAREAEMISOR"));
					oERevision.setDescripcionAreaEmisor(oResultSet.getString("DESCAREAEMISOR"));
					oERevision.setCodigoEmisor(oResultSet.getInt("CODEMISOR"));
					oERevision.setDescripcionEmisor(oResultSet.getString("DESCEMISOR"));
					oERevision.setDescripcionAbreviacionEmisor(oResultSet.getString("DESCABREVEMISOR"));
					
					oERevision.setCodigoTipoEnvio(oResultSet.getInt("CODTENVIO"));
					oERevision.setCodigoReceptor(oResultSet.getInt("CODRECEPTOR"));
					oERevision.setDescripcionReceptor(oResultSet.getString("DESCRECEPTOR"));
					
					oERevision.setDescripcionNivel(oResultSet.getString("DESCNVL"));
					oERevision.setDescripcionEstado(oResultSet.getString("DESCEST"));
					oERevision.setDescripcionTipoEnvio(oResultSet.getString("DESCTENVIO"));
					oERevision.setDescripcionAsunto(oResultSet.getString("DESCASUNTO"));
					oERevision.setAbreviacionAsunto(oResultSet.getString("ABREVASUNTO"));
					oERevision.setDescripcionMensajeDigitalizacion(oResultSet.getString("DESCENVIODIG"));
					oERevision.setIndicadorSesion(oResultSet.getInt("INDSES"));
					oERevision.setIndicadorDigitalizacion(oResultSet.getInt("INDDIG"));
					oERevision.setIndicadorSolicitud(oResultSet.getInt("INDSOL"));
					oERevision.setDescripcionUsuarioEvaluacion(oResultSet.getString("DESCUSUEVAL"));
					oERevision.setDescripcionUsuarioSesion(oResultSet.getString("DESCUSUSES"));
					oERevision.setFechaInicio(oResultSet.getDate("FECINI"));
					oERevision.setFechaFin(oResultSet.getDate("FECFIN"));
					oERevision.setHoraInicio(oResultSet.getTime("HORINI"));
					oERevision.setHoraFin(oResultSet.getTime("HORFIN"));
					oERevision.setDiaTranscurrido(oResultSet.getInt("DIATRANSCURRIDO"));
					oERevision.setTiempoTranscurrido(UFuncionesGenerales.convertirValoresASexagesimal(oResultSet.getInt("DIATRANSCURRIDO"), oResultSet.getInt("SEGUNDOSTRANSCURRIDO")));
					oERevision.setIndicadorEditar(oResultSet.getInt("INDEDITAR"));
					oERevision.setIndicadorVisualizar(oResultSet.getInt("INDVISUALIZAR"));
					/*
					oERevision.setIndicadorEnviar(oResultSet.getInt("INDENVIAR"));
					oERevision.setIndicadorReenviar(oResultSet.getInt("INDREENVIAR"));
					oERevision.setIndicadorCerrar(oResultSet.getInt("INDCERRAR"));
					oERevision.setIndicadorDescartar(oResultSet.getInt("INDDESCARTAR"));
					oERevision.setIndicadorCancelar(oResultSet.getInt("INDCANCELAR"));
					*/
					oERevision.setIndicadorCancelarAtajo(oResultSet.getInt("INDCANCELAR_ATAJO"));
					oERevision.setIndicadorEliminarAtajo(oResultSet.getInt("INDELIMINAR_ATAJO"));
					oERevision.setIndicadorPublicar(oResultSet.getInt("INDPUBLICAR"));
					oERevision.setIndicadorReasignarEmisor(oResultSet.getInt("INDREASIGNAREMISOR"));
					oERevision.setIndicadorReasignarReceptor(oResultSet.getInt("INDREASIGNARRECEPTOR"));
					
					lstRevision.add(oERevision);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevision;
	}
		
	public List<ERevisionTiempo> listarTiempoResumen(long codigo) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionTiempo oERevisionTiempo= null;
		List<ERevisionTiempo> lstRevisionTiempo = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_TIEMPORESUMEN, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstRevisionTiempo = new ArrayList<ERevisionTiempo>();
				while (oResultSet.next()) {
					oERevisionTiempo=new ERevisionTiempo();
					oERevisionTiempo.setCodigoArea(oResultSet.getInt("AREAORI"));
					oERevisionTiempo.setDescripcionArea(oResultSet.getString("DESCAREAORI"));
					oERevisionTiempo.setTiempoSegundoEnvioTotal(oResultSet.getInt("SEGTOTAL"));
					oERevisionTiempo.setTiempoSegundoActivoTotal(oResultSet.getInt("SEGTOTAL2"));
					oERevisionTiempo.setTiempoTranscurridoEnvioTotal(UFuncionesGenerales.convertirSegundosASexagesimal(oResultSet.getInt("SEGTOTAL")));
					oERevisionTiempo.setTiempoTranscurridoActivoTotal(UFuncionesGenerales.convertirSegundosASexagesimal(oResultSet.getInt("SEGTOTAL2")));
					lstRevisionTiempo.add(oERevisionTiempo);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevisionTiempo;
	}
	
	public List<ERevisionSesion> listarTiempoDetalle(long codigoSolicitud, int codigoArea) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionSesion oERevisionSesion= null;
		List<ERevisionSesion> lstRevisionSesion = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(codigoArea);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_TIEMPODETALLE, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstRevisionSesion = new ArrayList<ERevisionSesion>();
				while (oResultSet.next()) {
					oERevisionSesion=new ERevisionSesion();
					oERevisionSesion.setDescripcionAreaOrigen(oResultSet.getString("DESCAREAORI"));
					oERevisionSesion.setDescripcionUsuarioOrigen(oResultSet.getString("DESCUSUORI"));
					oERevisionSesion.setTiempoSegundo(oResultSet.getInt("TIMESEG"));
					oERevisionSesion.setTiempoTranscurrido(UFuncionesGenerales.convertirSegundosASexagesimal(oResultSet.getInt("TIMESEG")));
					lstRevisionSesion.add(oERevisionSesion);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevisionSesion;
	}
	
	public ERevisionSolicitud buscarSolicitud(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionSolicitud oERevision= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_SOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oERevision=new ERevisionSolicitud();
					oERevision.setCodigoEmisor(oResultSet.getInt("CODEMISOR"));
					oERevision.setNumeroMensaje(oResultSet.getInt("NUMMSJ"));
					oERevision.setNumeroRevision(oResultSet.getInt("NUMREV"));
					oERevision.setCodigoEstado(oResultSet.getInt("CODEST"));
					oERevision.setIndicadorSesion(oResultSet.getInt("INDSES"));
				}
			}
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oERevision;
	}
	
	public List<ERevisionMensaje> listarMensaje(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionMensaje oERevisionMensaje= null;
		List<ERevisionMensaje> lstRevisionMensaje = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_MENSAJE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRevisionMensaje = new ArrayList<ERevisionMensaje>();
				while (oResultSet.next()) {
					oERevisionMensaje=new ERevisionMensaje();
					oERevisionMensaje.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oERevisionMensaje.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oERevisionMensaje.setCodigoRevision(oResultSet.getInt("CODREV"));
					oERevisionMensaje.setDescripcionMensaje(oResultSet.getString("DESCMSJ"));
					oERevisionMensaje.setAbreviacionMensaje(oResultSet.getString("ABREVMSJ"));
					oERevisionMensaje.setNumeroDocumento(oResultSet.getInt("NUMDOCUMENTOS"));
					oERevisionMensaje.setDescripcionUsuarioEnvio(oResultSet.getString("DESCUSUENVIO"));
					oERevisionMensaje.setFechaEnvio(oResultSet.getDate("FECENVIO"));
					oERevisionMensaje.setHoraEnvio(oResultSet.getString("HORENVIO"));
					/*
					oERevisionMensaje.setFechaRegistro(oResultSet.getDate("FECREG"));
					oERevisionMensaje.setHoraRegistro(oResultSet.getString("HORREG"));
					*/
					
					lstRevisionMensaje.add(oERevisionMensaje);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevisionMensaje;
	}
	
	public String buscarMensajeTemporal(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		String resultado = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_MENSAJE_TEMP, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					resultado = oResultSet.getString("DESCMSJ");
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return resultado;
	}
	
	public List<ERevisionDocumento> listarDocumento(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionDocumento oERevisionDocumento= null;
		List<ERevisionDocumento> lstRevisionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_DOCUMENTO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRevisionDocumento = new ArrayList<ERevisionDocumento>();
				while (oResultSet.next()) {
					oERevisionDocumento=new ERevisionDocumento();
					oERevisionDocumento.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oERevisionDocumento.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oERevisionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oERevisionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oERevisionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oERevisionDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oERevisionDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					//obj.setObservacion(oResultSet.getString("OBSDOC"));
					//obj.setCalificacion(oResultSet.getInt("INDCAL"));
					//obj.setDescripcionCalificacion(oResultSet.getString("DESCCAL"));
					
					lstRevisionDocumento.add(oERevisionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevisionDocumento;
	}
	
	public List<EDocumentoCarga> listarDocumentoTemporal(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EDocumentoCarga oEDocumentoCarga= null;
		List<EDocumentoCarga> lstArchivoCarga = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_REVISION_DOCUMENTO_TEMP, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstArchivoCarga = new ArrayList<EDocumentoCarga>();
				while (oResultSet.next()) {
					oEDocumentoCarga=new EDocumentoCarga();
					oEDocumentoCarga.setCorrelativo(oResultSet.getInt("CODDOC"));
					oEDocumentoCarga.setNombre(oResultSet.getString("NOMDOC"));
					oEDocumentoCarga.setNombreOriginal(oResultSet.getString("NOMDOC"));
					oEDocumentoCarga.setData(oResultSet.getBytes("NDATA"));
					oEDocumentoCarga.setSize(oResultSet.getInt("NSIZE"));
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(oResultSet.getInt("NSIZE")));
					
					lstArchivoCarga.add(oEDocumentoCarga);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstArchivoCarga;
	}
}
