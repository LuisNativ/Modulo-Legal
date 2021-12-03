package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOOperacion extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUD("+parametrosSP(33)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUD2 = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUD2("+parametrosSP(29)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SESION("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTO("+parametrosSP(11)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOGARANTIA("+parametrosSP(14)+") }";

	private static final String SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTO_TEMP = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTO_TEMP("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOREVISION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOREVISION("+parametrosSP(11)+") }";
	
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUD("+parametrosSP(27)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_SESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_SESION("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSESION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSESION("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSESION2 = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSESION2("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSIAF = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSIAF("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOGARANTIA("+parametrosSP(15)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOFIRMAGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOFIRMAGARANTIA("+parametrosSP(8)+") }";
	
	private static final String SP_ABACOINLEGAL_DEL_OPERACION_DOCUMENTO_TEMP = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_OPERACION_DOCUMENTO_TEMP("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_DEL_OPERACION_DOCUMENTOREVISION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_OPERACION_DOCUMENTOREVISION("+parametrosSP(3)+") }";
	
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUD_LEGAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUD_LEGAL("+parametrosSP(7)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUD_OTROS = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUD_OTROS("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_MENSAJE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_MENSAJE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOREQUERIDO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOREQUERIDO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOGARANTIA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTO_TEMP="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTO_TEMP("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOREVISION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOREVISION("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_ESTADOPORSOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_ESTADOPORSOLICITUD("+parametrosSP(1)+") }";
	
	private static final String SP_ABACOINLEGAL_BUS_OPERACION_SOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_OPERACION_SOLICITUD("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_OPERACION_MENSAJE_TEMP="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_OPERACION_MENSAJE_TEMP("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_OPERACION_OPCIONPORSOLICITUD = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_OPERACION_OPCIONPORSOLICITUD("+parametrosSP(4)+") }";
	
	//EVALUACION LEVANTAMIENTO GARANTIA
	private static final String SP_ABACOINLEGAL_INS_OPERACION_LEVANTAMIENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_LEVANTAMIENTOGARANTIA("+parametrosSP(10)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_LEVANTAMIENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_LEVANTAMIENTOGARANTIA("+parametrosSP(11)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_OTROS = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_OTROS("+parametrosSP(3)+") }";
	
	//EVALUACION CLIENTE
	private static final String SP_ABACOINLEGAL_INS_UPD_OPERACION_CLIENTE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_OPERACION_CLIENTE("+parametrosSP(10)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_CLIENTE_DOCUMENTO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_CLIENTE_DOCUMENTO("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_CLIENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_CLIENTE("+parametrosSP(54)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE_DOCUMENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE_DOCUMENTO("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_OPERACION_CLIENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_OPERACION_CLIENTE("+parametrosSP(3)+") }";
	
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
	
	public DAOOperacion(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregarSolicitud2(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEvaluacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitudCredito());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitudRevision());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNombrePersona());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoNivel());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEstado());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAreaEmisor());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEmisor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionEmisor());
			
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAreaReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionReceptor());
			
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionAsunto());
			//lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorTemporal());
			//lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorDigitalizacion());
			//lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionMensaje());
			//lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaOrigen());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getHoraOrigen()));
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaInicio());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getHoraInicio()));
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getNombreCompleto());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUD2, lstParametrosEntrada);						
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarSolicitud(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEvaluacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitudCredito());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoCreditoIndirecto());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitudRevision());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoNivel());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEstado());
			
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAreaEmisor());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEmisor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionEmisor());
			
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAreaReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionAsunto());
			lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorTemporal());
			lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorDigitalizacion());
			//lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionMensaje());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaOrigen());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaOrigen()));
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getNombreCompleto());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUD, lstParametrosEntrada);						
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitud(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
			//lstParametrosEntrada.add(eOperacionSolicitud.getCodigoPersonaRelacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoClientePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoDocumentoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNumeroDocumentoPersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getNombrePersona());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoNivel());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEstado());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAutorizaJefe());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEnvio());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAreaReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionReceptor());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionAsunto());
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoMotivo());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionAdicionalMotivo());
			lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorTemporal());
			lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorDigitalizacion());
			//lstParametrosEntrada.add(eOperacionSolicitud.getIndicadorSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionMensaje());
			lstParametrosEntrada.add(eOperacionSolicitud.getDescripcionMensajeDigitalizacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getObservacionAutorizacion());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumento(EOperacionSolicitud eOperacionSolicitud, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitud.getNumeroMensaje());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eOGarantia, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		eOGarantia.setFechaRegistro(new Date());
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eOGarantia.getAreaEmisora());
			lstParametrosEntrada.add(eOGarantia.getAreaReceptora());
			lstParametrosEntrada.add(eOGarantia.getDescripcionDocumento());
			lstParametrosEntrada.add(eOGarantia.getFirmaDocumento());
			lstParametrosEntrada.add(eOGarantia.getEstadoDocumento());
			lstParametrosEntrada.add(eOGarantia.getObservacionDocumento());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al insertar datos del Documento en DB2.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarDocumentoGarantia(EGarantia eOGarantia, EOperacionDocumento eOperacionDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumento.getNombreDocumento());
			lstParametrosEntrada.add(eOperacionDocumento.getNombreDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumento.getNombreDocumentoOriginal());
			lstParametrosEntrada.add(eOGarantia.getAreaEmisora());
			lstParametrosEntrada.add(eOGarantia.getAreaReceptora());
			lstParametrosEntrada.add(eOGarantia.getDescripcionDocumento());
			lstParametrosEntrada.add(eOGarantia.getFirmaDocumento());
			lstParametrosEntrada.add(eOGarantia.getEstadoDocumento());
			lstParametrosEntrada.add(eOGarantia.getObservacionDocumento());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarFirmaDocumentoGarantia(EOperacionDocumento eOperacionDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumento.getFirmaDocumento());
			lstParametrosEntrada.add(eOperacionDocumento.getEstadoDocumento());
			lstParametrosEntrada.add(eOperacionDocumento.getUsuarioRegistro().getNombreUsuarioSIAF());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOFIRMAGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoTemporal(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		List<EDocumentoCarga> lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		
		try {
			lstDocumentoCarga=eOperacionSolicitud.getLstDocumentoCarga();	
			for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
				lstParametrosEntrada.add(oEDocumentoCarga.getNombre());
				lstParametrosEntrada.add(oEDocumentoCarga.getData());
				lstParametrosEntrada.add(oEDocumentoCarga.getSize());
				lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
				lstParametrosEntrada.add(eOperacionSolicitud.getFechaRegistro());
				lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaRegistro()));
				
				mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTO_TEMP, lstParametrosEntrada);
			}
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoRevision(EOperacionSolicitud eOperacionSolicitud, EOperacionDocumentoRevision eOperacionDocumentoRevision) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionDocumentoRevision.getCodigoDocumentoRequerido());
			lstParametrosEntrada.add(eOperacionDocumentoRevision.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumentoRevision.getNombreDocumento());
			lstParametrosEntrada.add(eOperacionDocumentoRevision.getNombreDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumentoRevision.getNombreDocumentoOriginal());
			lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSolicitud.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitud.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOREVISION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarSesion(EOperacionSesion eOperacionSesion) {
		EMensaje mensaje = new EMensaje();			
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSesion.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionSesion.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSesion.getFechaRegistro()));
			lstParametrosEntrada.add(eOperacionSesion.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionSesion.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSesion.getFechaModificacion());
			lstParametrosEntrada.add(formato.format(eOperacionSesion.getFechaModificacion()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SESION, lstParametrosEntrada);			
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSesion(EOperacionSesion eOperacionSesion) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			
			lstParametrosEntrada.add(eOperacionSesion.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionSesion.getCorrelativoSesion());
			lstParametrosEntrada.add(eOperacionSesion.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionSesion.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionSesion.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSesion.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_SESION, lstParametrosEntrada);		
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
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSESION2, lstParametrosEntrada);		
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
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSESION, lstParametrosEntrada);						
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje verificarSolicitudSiaf() {
		EMensaje mensaje = new EMensaje();		
		List<Object> lstParametrosEntrada;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDSIAF, lstParametrosEntrada);						
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarDocumentoTemporal(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_OPERACION_DOCUMENTO_TEMP, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarDocumentoRevision(EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitud());
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_OPERACION_DOCUMENTOREVISION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EOperacionSolicitud> listarSolicitud(EOperacionSolicitud eOperacionSolicitud, int indicadorConsulta) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOperacionSolicitud= null;
		List<EOperacionSolicitud> lstOperacionSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			if(indicadorConsulta == 2){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eOperacionSolicitud.getCodigoSolicitudCredito());
				lstParametrosEntrada.add(eOperacionSolicitud.getNombrePersona());
				lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEstado());
				lstParametrosEntrada.add(eOperacionSolicitud.getCodigoAutorizaJefe());
				lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getCodigoArea());
				lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
				lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEvaluacion());
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUD_LEGAL, lstParametrosEntrada, null);
			}else if(indicadorConsulta == 3){
				lstParametrosEntrada = new ArrayList<Object>();
				lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getCodigoArea());
				lstParametrosEntrada.add(eOperacionSolicitud.getUsuarioRegistro().getIdUsuario());
				
				lstParametrosEntrada.add(eOperacionSolicitud.getCodigoTipoEvaluacion());
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUD_OTROS, lstParametrosEntrada, null);
			}
			
			if (oResultSet != null) {
				lstOperacionSolicitud = new ArrayList<EOperacionSolicitud>();
				while (oResultSet.next()) {
					oEOperacionSolicitud = new EOperacionSolicitud();
					oEOperacionSolicitud.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oEOperacionSolicitud.setCodigoNivel(oResultSet.getInt("CODNVL"));
					oEOperacionSolicitud.setCodigoEstado(oResultSet.getInt("CODEST"));
					oEOperacionSolicitud.setNumeroMensaje(oResultSet.getInt("NUMMSJ"));
					oEOperacionSolicitud.setNumeroRevision(oResultSet.getInt("NUMREV"));
					oEOperacionSolicitud.setCodigoTipoEvaluacion(oResultSet.getInt("CODTEVAL"));
					oEOperacionSolicitud.setCodigoSolicitudCredito(oResultSet.getInt("CODSOLCRE"));
					oEOperacionSolicitud.setCodigoGarantia(oResultSet.getInt("CODGAR"));
					
					oEOperacionSolicitud.setCodigoClientePersona(oResultSet.getInt("CODPER"));
					oEOperacionSolicitud.setCodigoTipoPersona(oResultSet.getString("CODTPER"));
					oEOperacionSolicitud.setCodigoTipoClientePersona(oResultSet.getInt("CODTIPCLIPER"));
					oEOperacionSolicitud.setCodigoTipoDocumentoPersona(oResultSet.getString("CODTIPDOCPER"));
					oEOperacionSolicitud.setNumeroDocumentoPersona(oResultSet.getString("NUMDOCPER"));
					oEOperacionSolicitud.setNombrePersona(oResultSet.getString("NOMPER"));
					
					oEOperacionSolicitud.setCodigoAreaEmisor(oResultSet.getInt("CODAREAEMISOR"));
					oEOperacionSolicitud.setDescripcionAreaEmisor(oResultSet.getString("DESCAREAEMISOR"));
					oEOperacionSolicitud.setCodigoEmisor(oResultSet.getInt("CODEMISOR"));
					oEOperacionSolicitud.setDescripcionEmisor(oResultSet.getString("DESCEMISOR"));
					oEOperacionSolicitud.setDescripcionAbreviacionEmisor(oResultSet.getString("DESCABREVEMISOR"));
					
					oEOperacionSolicitud.setCodigoTipoEnvio(oResultSet.getInt("CODTENVIO"));
					oEOperacionSolicitud.setCodigoReceptor(oResultSet.getInt("CODRECEPTOR"));
					oEOperacionSolicitud.setDescripcionReceptor(oResultSet.getString("DESCRECEPTOR"));
					
					oEOperacionSolicitud.setDescripcionNivel(oResultSet.getString("DESCNVL"));
					oEOperacionSolicitud.setDescripcionEstado(oResultSet.getString("DESCEST"));
					oEOperacionSolicitud.setDescripcionTipoEvaluacion(oResultSet.getString("DESCTEVAL"));
					oEOperacionSolicitud.setDescripcionTipoEnvio(oResultSet.getString("DESCTENVIO"));
					oEOperacionSolicitud.setDescripcionEstadoAutorizacion(oResultSet.getString("DESCAJF"));
					oEOperacionSolicitud.setDescripcionAsunto(oResultSet.getString("DESCASUNTO"));
					oEOperacionSolicitud.setDescripcionMensajeDigitalizacion(oResultSet.getString("DESCENVIODIG"));
					oEOperacionSolicitud.setIndicadorSesion(oResultSet.getInt("INDSES"));
					oEOperacionSolicitud.setIndicadorDigitalizacion(oResultSet.getInt("INDDIG"));
					oEOperacionSolicitud.setDescripcionUsuarioEvaluacion(oResultSet.getString("DESCUSUEVAL"));
					oEOperacionSolicitud.setDescripcionUsuarioSesion(oResultSet.getString("DESCUSUSES"));
					oEOperacionSolicitud.setFechaInicio(oResultSet.getDate("FECINI"));
					oEOperacionSolicitud.setFechaFin(oResultSet.getDate("FECFIN"));
					oEOperacionSolicitud.setDiaTranscurrido(oResultSet.getInt("DIATRANSCURRIDO"));
					oEOperacionSolicitud.setTiempoTranscurrido(UFuncionesGenerales.convertirValoresASexagesimal(oResultSet.getInt("DIATRANSCURRIDO"), oResultSet.getInt("SEGUNDOSTRANSCURRIDO")));
					oEOperacionSolicitud.setIndicadorEditar(oResultSet.getInt("INDEDITAR"));
					oEOperacionSolicitud.setIndicadorVisualizar(oResultSet.getInt("INDVISUALIZAR"));
					oEOperacionSolicitud.setIndicadorPublicar(oResultSet.getInt("INDPUBLICAR"));
					oEOperacionSolicitud.setIndicadorReasignarEmisor(oResultSet.getInt("INDREASIGNAREMISOR"));
					oEOperacionSolicitud.setIndicadorReasignarReceptor(oResultSet.getInt("INDREASIGNARRECEPTOR"));
					oEOperacionSolicitud.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABREVTPRODU")));
					oEOperacionSolicitud.setCodigoAutorizaJefe(oResultSet.getInt("CODAJF"));
					oEOperacionSolicitud.setDiaTranscurridoAJF(oResultSet.getInt("DIATRANSCURRIDOAJF"));
					oEOperacionSolicitud.setTiempoTranscurridoAJF(UFuncionesGenerales.convertirValoresASexagesimal(oResultSet.getInt("DIATRANSCURRIDOAJF"), oResultSet.getInt("SEGUNDOSTRANSCURRIDOAJF")));
					oEOperacionSolicitud.setFechaAutorizacion(oResultSet.getDate("FECAJF"));
					
					lstOperacionSolicitud.add(oEOperacionSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitud;
	}
	
	public List<EOperacionMensaje> listarMensaje(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionMensaje oEOperacionMensaje= null;
		List<EOperacionMensaje> lstOperacionMensaje = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_MENSAJE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionMensaje = new ArrayList<EOperacionMensaje>();
				while (oResultSet.next()) {
					oEOperacionMensaje=new EOperacionMensaje();
					oEOperacionMensaje.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oEOperacionMensaje.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oEOperacionMensaje.setCodigoRevision(oResultSet.getInt("CODREV"));
					oEOperacionMensaje.setDescripcionMensaje(oResultSet.getString("DESCMSJ"));
					oEOperacionMensaje.setAbreviacionMensaje(oResultSet.getString("ABREVMSJ"));
					oEOperacionMensaje.setNumeroDocumento(oResultSet.getInt("NUMDOCUMENTOS"));
					oEOperacionMensaje.setDescripcionUsuarioEnvio(oResultSet.getString("DESCUSUENVIO"));
					oEOperacionMensaje.setFechaEnvio(oResultSet.getDate("FECENVIO"));
					oEOperacionMensaje.setHoraEnvio(oResultSet.getString("HORENVIO"));
					
					lstOperacionMensaje.add(oEOperacionMensaje);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionMensaje;
	}
	
	public List<EOperacionDocumento> listarDocumento(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oEOperacionDocumento.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oEOperacionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public List<EOperacionDocumento> listarDocumentoGarantia(EGarantia eOGarantia,int indicador) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(indicador);
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEOperacionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					oEOperacionDocumento.setAreaEmisora(oResultSet.getString("AREAEMI"));
					oEOperacionDocumento.setAreaReceptora(oResultSet.getString("AREAREC"));
					oEOperacionDocumento.setDescripcionDocumento(oResultSet.getString("DESCRI"));
					oEOperacionDocumento.setFirmaDocumento(oResultSet.getString("FIRMA"));
					oEOperacionDocumento.setEstadoDocumento(oResultSet.getInt("ESTADO"));
					oEOperacionDocumento.setDescripcionEstadoDocumento(oResultSet.getString("DESCEST"));
					oEOperacionDocumento.setObervacionDocumento(oResultSet.getString("OBSERV"));
					oEOperacionDocumento.setUsuarioRegist(oResultSet.getString("USUREG"));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(oResultSet.getString("USUREG"));
					oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public List<EOperacionDocumentoRequerido> listarDocumentoRequerido(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumentoRequerido oEOperacionDocumentoRequerido= null;
		List<EOperacionDocumentoRequerido> lstOperacionDocumentoRequerido = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOREQUERIDO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumentoRequerido = new ArrayList<EOperacionDocumentoRequerido>();
				while (oResultSet.next()) {
					oEOperacionDocumentoRequerido=new EOperacionDocumentoRequerido();
					oEOperacionDocumentoRequerido.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oEOperacionDocumentoRequerido.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionDocumentoRequerido.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionDocumentoRequerido.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionDocumentoRequerido.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumentoRequerido.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionDocumentoRequerido.add(oEOperacionDocumentoRequerido);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumentoRequerido;
	}
	
	public List<EDocumentoCarga> listarDocumentoTemporal(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EDocumentoCarga oEDocumentoCarga= null;
		List<EDocumentoCarga> lstArchivoCarga = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTO_TEMP, lstParametrosEntrada, null);
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
	
	public List<EOperacionDocumentoRevision> listarDocumentoRevision(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumentoRevision oEOperacionDocumentoRevision= null;
		List<EOperacionDocumentoRevision> lstOperacionDocumentoRevision = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOREVISION, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumentoRevision = new ArrayList<EOperacionDocumentoRevision>();
				while (oResultSet.next()) {
					oEOperacionDocumentoRevision=new EOperacionDocumentoRevision();
					oEOperacionDocumentoRevision.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oEOperacionDocumentoRevision.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionDocumentoRevision.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionDocumentoRevision.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionDocumentoRevision.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumentoRevision.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionDocumentoRevision.add(oEOperacionDocumentoRevision);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumentoRevision;
	}
	
	public List<EEstado> listarEstadoPorSolicitud(long codigoSolicitud, EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EEstado oEEstado = null;
		List<EEstado> lstEstado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_ESTADOPORSOLICITUD, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstEstado = new ArrayList<EEstado>();
				while (oResultSet.next()) {
					oEEstado=new EEstado();
					oEEstado.setCodigoEstado(oResultSet.getInt("CODEST"));
					oEEstado.setDescripcionEstado(oResultSet.getString("DESCEST"));
					
					lstEstado.add(oEEstado);
				}								
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstEstado;
	}

	public EOperacionSolicitud buscarSolicitud(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitud oEOperacionSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_OPERACION_SOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOperacionSolicitud=new EOperacionSolicitud();
					
					oEOperacionSolicitud.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oEOperacionSolicitud.setDescripcionAsunto(oResultSet.getString("DESCASUNTO"));
					
					oEOperacionSolicitud.setCodigoAreaEmisor(oResultSet.getInt("CODAREAEMISOR"));
					oEOperacionSolicitud.setCodigoEmisor(oResultSet.getInt("CODEMISOR"));
					oEOperacionSolicitud.setDescripcionEmisor(oResultSet.getString("DESCEMISOR"));
					
					oEOperacionSolicitud.setCodigoTipoEnvio(oResultSet.getInt("CODTENVIO"));
					oEOperacionSolicitud.setCodigoAreaReceptor(oResultSet.getInt("CODAREARECEPTOR"));
					oEOperacionSolicitud.setCodigoReceptor(oResultSet.getInt("CODRECEPTOR"));
					oEOperacionSolicitud.setDescripcionReceptor(oResultSet.getString("DESCRECEPTOR"));
					
					oEOperacionSolicitud.setCodigoTipoEvaluacion(oResultSet.getInt("CODTEVAL"));
					oEOperacionSolicitud.setCodigoSolicitudCredito(oResultSet.getInt("CODSOLCRE"));
					oEOperacionSolicitud.setCodigoGarantia(oResultSet.getInt("CODGAR"));
					oEOperacionSolicitud.setCodigoCreditoIndirecto(oResultSet.getInt("CODCRI"));
					//CODSOLREV
					oEOperacionSolicitud.setCodigoClientePersona(oResultSet.getInt("CODPER"));
					oEOperacionSolicitud.setCodigoTipoClientePersona(oResultSet.getInt("CODTIPCLIPER"));
					oEOperacionSolicitud.setCodigoTipoDocumentoPersona(oResultSet.getString("CODTIPDOCPER"));
					oEOperacionSolicitud.setNumeroDocumentoPersona(oResultSet.getString("NUMDOCPER"));
					oEOperacionSolicitud.setNombrePersona(oResultSet.getString("NOMPER"));
					
					oEOperacionSolicitud.setCodigoNivel(oResultSet.getInt("CODNVL"));
					oEOperacionSolicitud.setCodigoEstado(oResultSet.getInt("CODEST"));
					//CODESTPEN
					oEOperacionSolicitud.setCodigoSeguimiento(oResultSet.getInt("CODSEG"));
					oEOperacionSolicitud.setCodigoServicio(oResultSet.getInt("CODSERVICIO"));
					oEOperacionSolicitud.setCodigoAutorizaJefe(oResultSet.getInt("CODAJF"));
					oEOperacionSolicitud.setNumeroMensaje(oResultSet.getInt("NUMMSJ"));
					oEOperacionSolicitud.setNumeroRevision(oResultSet.getInt("NUMREV"));
					oEOperacionSolicitud.setCodigoUsuarioEvaluacion(oResultSet.getInt("USUEVAL"));
					//USUSES
					oEOperacionSolicitud.setIndicadorSesion(oResultSet.getInt("INDSES"));
					
					oEOperacionSolicitud.setCodigoUsuarioEnvioDigitalizacion(oResultSet.getInt("USUENVIODIG"));
					oEOperacionSolicitud.setCodigoAreaEnvioDigitalizacion(oResultSet.getInt("AREAENVIODIG"));
					oEOperacionSolicitud.setDescripcionMensajeDigitalizacion(oResultSet.getString("DESCENVIODIG"));

					oEOperacionSolicitud.setCodigoTipoEnvioRevision(oResultSet.getInt("CODTENVREV"));
					oEOperacionSolicitud.setCodigoAreaRevision(oResultSet.getInt("CODAREAREV"));
					oEOperacionSolicitud.setCodigoUsuarioRevision(oResultSet.getInt("CODUSUREV"));
					
					oEOperacionSolicitud.setIndicadorDigitalizacion(oResultSet.getInt("INDDIG"));
					
					oEOperacionSolicitud.setCodigoUsuarioAutorizacion(oResultSet.getInt("CODUSUAJF"));
					oEOperacionSolicitud.setObservacionAutorizacion(oResultSet.getString("DESCOBSAJF"));
					oEOperacionSolicitud.setFechaAutorizacion(oResultSet.getDate("FECAJF"));
					oEOperacionSolicitud.setHoraAutorizacion(oResultSet.getString("HORAJF"));
					oEOperacionSolicitud.setFechaInicio(oResultSet.getDate("FECINI"));
					//oEOperacionSolicitud.setHoraInicio(oResultSet.getDate("HORINI"));
					oEOperacionSolicitud.setFechaFin(oResultSet.getDate("FECFIN"));
					//oEOperacionSolicitud.setHoraFin(oResultSet.getDate("HORFIN"));
					
					oEOperacionSolicitud.setDescripcionEstado(oResultSet.getString("DESCEST"));
					oEOperacionSolicitud.setNombreUsuarioAutorizacion(oResultSet.getString("NOMUSUAJF"));
					oEOperacionSolicitud.setDescripcionEstadoAutorizacion(oResultSet.getString("DESCAJF"));
				}
			}
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOperacionSolicitud;
	}
	
	public String buscarMensajeTemporal(long codigoSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		String resultado = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_OPERACION_MENSAJE_TEMP, lstParametrosEntrada, null);
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
	
	public EOpcion buscarOpcionPorSolicitud(long codigoSolicitud, int codigoTipoEvaluacion, EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EOpcion oEOpcion = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoSolicitud);
			lstParametrosEntrada.add(codigoTipoEvaluacion);
			lstParametrosEntrada.add(eUsuario.getCodigoArea());
			lstParametrosEntrada.add(eUsuario.getIdUsuario());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_OPERACION_OPCIONPORSOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					oEOpcion = new EOpcion();
					if(codigoTipoEvaluacion == UTipoEvaluacion.SOLICITUDCREDITO){
						oEOpcion.setIndicadorSolicitarAutorizar(oResultSet.getInt("INDSOLICITARAUTORIZAR"));
						oEOpcion.setIndicadorConfirmarAutorizar(oResultSet.getInt("INDCONFIRMARAUTORIZAR"));
						oEOpcion.setIndicadorConfirmarAutorizarCompletado(oResultSet.getInt("INDCONFIRMARAUTORIZARCOMPLETADO"));
						oEOpcion.setIndicadorRechazarAutorizar(oResultSet.getInt("INDRECHAZARAUTORIZAR"));
						oEOpcion.setIndicadorGrabar(oResultSet.getInt("INDGRABAR"));
					}else if(codigoTipoEvaluacion == UTipoEvaluacion.LEVANTAMIENTO){
						oEOpcion.setIndicadorRechazarLevantamiento(oResultSet.getInt("INDRECHAZARLEVANTAMIENTO"));
						oEOpcion.setIndicadorConfirmarLevantamiento(oResultSet.getInt("INDCONFIRMARLEVANTAMIENTO"));
						oEOpcion.setIndicadorLiberar(oResultSet.getInt("INDLIBERAR"));
						oEOpcion.setIndicadorEntregar(oResultSet.getInt("INDENTREGAR"));
					}else if(codigoTipoEvaluacion == UTipoEvaluacion.CARTAFIANZA){
						oEOpcion.setIndicadorConfirmarCartaFianza(oResultSet.getInt("INDCONFIRMARCARTAFIANZA"));
						oEOpcion.setIndicadorEnviarCartaFianza(oResultSet.getInt("INDENVIARCARTAFIANZA"));
					}
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOpcion;
	}
	
	public EMensaje agregarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoServicio());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoMoneda());
			
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoEstadoSolicitud());
			
			//lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionLevantamientoGarantia.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_LEVANTAMIENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoServicio());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoMoneda());
			
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoEstadoSolicitud());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoEstadoDocumento());
			
			//lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionLevantamientoGarantia.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_LEVANTAMIENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public List<EGarantiaSolicitud> listarEvaluacionLevantamientoGarantia(int codigo, String descripcion,EUsuario eUsuario) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eUsuario.getNombreUsuario());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_OTROS, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMBCL"));
					//oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					//oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					//oEGarantia.setDescripcionCreditoRelacionado(oResultSet.getString("DESCOPE"));
					oEGarantia.setCodigoEstadoLevantamiento(oResultSet.getInt("CODESTSOL"));
					oEGarantia.setDescripcionEstadoLevantamiento(oResultSet.getString("DESCESTSOL"));
					oEGarantia.setCodigoEstadoDocumento(oResultSet.getInt("CODESTDOC"));
					oEGarantia.setDescripcionEstadoDocumento(oResultSet.getString("DESCESTDOC"));
					oEGarantia.setIndicadorRevisar(oResultSet.getInt("INDREV"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public EMensaje agregarModificarEvaluacionCliente(EOperacionCliente eOperacionCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eOperacionCliente.getNombreLargo());
			lstParametrosEntrada.add(eOperacionCliente.getNombreCorto());
			//lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_OPERACION_CLIENTE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarEvaluacionClienteDocumento(EOperacionCliente eOperacionCliente, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoCliente());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eOperacionCliente.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_CLIENTE_DOCUMENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionCliente(EOperacionCliente eOperacionCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoCliente());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoEstadoSolicitud());
			lstParametrosEntrada.add(eOperacionCliente.getObservacionLegal());
			lstParametrosEntrada.add(eOperacionCliente.getObservacionNegocio());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroDocumento());
			lstParametrosEntrada.add(eOperacionCliente.getRuc());
			lstParametrosEntrada.add(eOperacionCliente.getNombreLargo());
			lstParametrosEntrada.add(eOperacionCliente.getDireccionReal());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoUbigeoReal());
			lstParametrosEntrada.add(eOperacionCliente.getDireccionContractual());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoUbigeoContractual());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getDocumentoConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getNombreLargoConyuge());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eOperacionCliente.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eOperacionCliente.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroAcciones());
			lstParametrosEntrada.add(eOperacionCliente.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eOperacionCliente.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eOperacionCliente.getDescripcionAvalarTercero());
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),4));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),5));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),6));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),7));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),8));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),9));
			
			lstParametrosEntrada.add(eOperacionCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_CLIENTE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public List<EOperacionCliente> listarEvaluacionCliente(int codigo, String descripcion, EUsuario eUsuario) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionCliente oEOperacionCliente= null;
		List<EOperacionCliente> lstOperacionCliente = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			//lstParametrosEntrada.add(eUsuario.getNombreUsuario());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionCliente = new ArrayList<EOperacionCliente>();
				while (oResultSet.next()) {
					oEOperacionCliente = new EOperacionCliente();
					oEOperacionCliente.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionCliente.setCodigoEstadoSolicitud(oResultSet.getInt("CODESTSOL"));
					oEOperacionCliente.setDescripcionEstadoSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					
					oEOperacionCliente.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionCliente.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionCliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionCliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionCliente.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionCliente.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionCliente.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOperacionCliente.setFechaPrimeraEvaluacion(oResultSet.getDate("FECIEV"));
					oEOperacionCliente.setHoraPrimeraEvaluacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOAIEV")));
					oEOperacionCliente.setUsuarioPrimeraEvaluacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUIEV")));
					oEOperacionCliente.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionCliente.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionCliente.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionCliente.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionCliente.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionCliente.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
					
					oEOperacionCliente.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEOperacionCliente.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					
					lstOperacionCliente.add(oEOperacionCliente);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionCliente;
	}
	
	public EOperacionCliente buscarEvaluacionCliente(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionCliente oEOperacionCliente= null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_OPERACION_CLIENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOperacionCliente=new EOperacionCliente();
					oEOperacionCliente.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionCliente.setCodigoEstadoSolicitud(oResultSet.getInt("CODESTSOL"));
					//oEOperacionCliente.setDescripcionEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSTALEG")));
					oEOperacionCliente.setObservacionLegal(oResultSet.getString("OBSLEG"));
					oEOperacionCliente.setObservacionNegocio(oResultSet.getString("OBSNEG"));
					
					oEOperacionCliente.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionCliente.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionCliente.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEOperacionCliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionCliente.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					oEOperacionCliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionCliente.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionCliente.setRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")));
					
					oEOperacionCliente.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionCliente.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					
					oEOperacionCliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCON")));
					oEOperacionCliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCCON")));
					oEOperacionCliente.setNombreLargoConyuge(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCON")));
					
					oEOperacionCliente.setCodigoUbigeoReal(oResultSet.getInt("CCIUDA"));
					oEOperacionCliente.setDireccionReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oEOperacionCliente.setCodigoUbigeoContractual(oResultSet.getInt("CCIUDP"));
					oEOperacionCliente.setDireccionContractual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					
					oEOperacionCliente.setCodigoTipoPersonaJuridica(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPJ")));
					oEOperacionCliente.setMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP"));
					oEOperacionCliente.setMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC"));
					oEOperacionCliente.setCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")));
					oEOperacionCliente.setCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")));
					oEOperacionCliente.setNumeroSuscripcionPago(oResultSet.getInt("SUSPAG"));
					oEOperacionCliente.setNumeroAcciones(oResultSet.getInt("NROACC"));
					oEOperacionCliente.setIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")));
					oEOperacionCliente.setIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")));
					oEOperacionCliente.setDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")));
					oEOperacionCliente.setObservacionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT"))+"\n"+
																				  UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2AVT"))+"\n"+
																				  UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3AVT")));
					oEOperacionCliente.setObservacionGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA"))+"\n"+
																			   UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2GRA"))+"\n"+
																			   UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3GRA")));
					oEOperacionCliente.setObservacionConstanciaSocial(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT2PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT3PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT4PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT5PJ")));
					oEOperacionCliente.setObservacionSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL01"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL02"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL03"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL04"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL05"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL06"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL07"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL08"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL09"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL10")));
					
					//oEOperacionCliente.setUsuarioEvaluacionLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMUSUEVALG")));
					oEOperacionCliente.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionCliente.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionCliente.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionCliente.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionCliente.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionCliente.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOperacionCliente;
	}
	
	public List<EOperacionClienteDocumento> listarEvaluacionClienteDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionClienteDocumento oEOperacionClienteDocumento= null;
		List<EOperacionClienteDocumento> lstOperacionClienteDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE_DOCUMENTO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionClienteDocumento = new ArrayList<EOperacionClienteDocumento>();
				while (oResultSet.next()) {
					oEOperacionClienteDocumento=new EOperacionClienteDocumento();
					oEOperacionClienteDocumento.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionClienteDocumento.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionClienteDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionClienteDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionClienteDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionClienteDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionClienteDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionClienteDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionClienteDocumento.add(oEOperacionClienteDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionClienteDocumento;
	}
}
