package com.abaco.negocio.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UTipoBusqueda;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.servicio.laserfiche.Archivo;
import com.abaco.servicio.laserfiche.Documento;
import com.abaco.servicio.laserfiche.GestionDocumento;
import com.abaco.negocio.util.UConstante.UTipoClienteSolicitudCredito;
import com.abaco.servicio.laserfiche.GestionDocumentoService;
import com.abaco.servicio.laserfiche.Mensaje;
import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.ageneral.EGarantia;
import com.abaco.ageneral.EOperacionDocumentoRevision;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.bo.BOLaserFiche;

public class UManejadorArchivo {
	
	public Mensaje guardarDocumento(ERevisionSolicitud eRevisionSolicitud, EDocumentoCarga eDocumentoCarga, int indicador){
		BOLaserFiche oBOLaserFiche = new BOLaserFiche();
		Documento documento = new Documento();
		Mensaje mensaje = null;
		
		try{
			String nombreUsuario = "";
			int clasificacion = 12;
			int tipocliente = 3;
			
			if (indicador == 0){
				nombreUsuario = eRevisionSolicitud.getUsuarioRegistro().getNombreUsuarioSIAF();
			/*
			}else if (indicador == 1) {
				nombreUsuario = eRevisionSolicitud.getDescripcionAbreviacionEmisor();
			*/
			}else{
				nombreUsuario = eRevisionSolicitud.getDescripcionAbreviacionEmisorOrigen();
			}
			
			documento.setClasificacion(clasificacion);
			documento.setTipoCliente(tipocliente);
			documento.setNombreUsuario(nombreUsuario);
			documento.setNumeroOperacion(eRevisionSolicitud.getCodigoSolicitud()+"");
			documento.setNombreArchivo(eDocumentoCarga.getNombreLaserFiche());
			documento.setArchivoBinario(eDocumentoCarga.getData());
			mensaje =  oBOLaserFiche.guardarDocumento(documento);
			
			if (mensaje != null){
				if (mensaje.getCodigo() < 0 || mensaje.getDocumentoID() == null) {
					mensaje.setCodigo(-1);
					mensaje.setDescripcion("Problema al guardar archivo en el Web Service LaserFiche");
				}
			}
		} catch (Exception objEx) {
			mensaje = new Mensaje();
			mensaje.setCodigo(-1);
			mensaje.setDescripcion("Problema al conectar con el Web Service LaserFiche");
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return mensaje;
	}
	
	public Mensaje guardarDocumento(EGarantia eGarantia, EDocumentoCarga eDocumentoCarga){
		BOLaserFiche oBOLaserFiche = new BOLaserFiche();
		Documento documento = new Documento();
		Mensaje mensaje = null;
		
		try{
			int clasificacion = 10; //Carpeta Garantias
			int tipocliente = 1;
			
			documento.setClasificacion(clasificacion);
			documento.setTipoCliente(tipocliente);
			documento.setCodigoSocio(eGarantia.getCodigoCliente()+"");
			documento.setNombreUsuario(eGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			documento.setNumeroOperacion(eGarantia.getCodigoGarantia()+"");
			documento.setNombreArchivo(eDocumentoCarga.getNombreLaserFiche());
			documento.setArchivoBinario(eDocumentoCarga.getData());
			mensaje =  oBOLaserFiche.guardarDocumento(documento);
			
			if (mensaje != null){
				if (mensaje.getCodigo() < 0 || mensaje.getDocumentoID() == null) {
					mensaje.setCodigo(-1);
					mensaje.setDescripcion("Problema al guardar archivo en el Web Service LaserFiche");
				}
			}
		} catch (Exception objEx) {
			mensaje = new Mensaje();
			mensaje.setCodigo(-1);
			mensaje.setDescripcion("Problema al conectar con el Web Service LaserFiche");
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return mensaje;
	}
	
	public Mensaje guardarDocumento(EOperacionSolicitud eOperacionSolicitud, EDocumentoCarga eDocumentoCarga){
		BOLaserFiche oBOLaserFiche = new BOLaserFiche();
		Documento documento = new Documento();
		Mensaje mensaje = null;
		
		try{
			int clasificacion = 0;
			int tipocliente = 1;
			
			clasificacion = 1;
			
			/*
			if(eOperacionSolicitud.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.RUC)){
				clasificacion = 9;
			}else{
				clasificacion = 6;
			}
			*/
			
			if(eOperacionSolicitud.getCodigoTipoClientePersona() == UTipoClienteSolicitudCredito.COD_POSTULANTE){
				tipocliente = 2;
			}else if(eOperacionSolicitud.getCodigoTipoClientePersona() == UTipoClienteSolicitudCredito.COD_SOCIO){
				tipocliente = 1;
			}
			
			documento.setClasificacion(clasificacion);
			documento.setTipoCliente(tipocliente);
			documento.setTipoCliente(4);
			documento.setCodigoSocio(eOperacionSolicitud.getCodigoClientePersona()+"");
			documento.setNumeroDocumento(eOperacionSolicitud.getNumeroDocumentoPersona());
			documento.setNumeroOperacion(eOperacionSolicitud.getCodigoSolicitudCredito()+"");
			//documento.setNumeroOperacion("Operacion Nº "+eOperacionSolicitud.getCodigoSolicitud());
			documento.setNombreArchivo(eDocumentoCarga.getNombreLaserFiche());
			documento.setArchivoBinario(eDocumentoCarga.getData());
			mensaje =  oBOLaserFiche.guardarDocumento(documento);
			
			if (mensaje.getCodigo() < 0) {
				throw new Exception("Error al guardar documento laserfiche: " + mensaje.getDescripcion());
			} else {
				mensaje.setCodigo(0);
				mensaje.setDescripcion("Proceso Exitoso");
			}
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return mensaje;
	}
	
	public Mensaje guardarDocumentoRevision(EOperacionSolicitud eOperacionSolicitud, EOperacionDocumentoRevision eOperacionDocumentoRevision){
		BOLaserFiche oBOLaserFiche = new BOLaserFiche();
		Documento documento = new Documento();
		Mensaje mensaje = null;
		
		try{
			int clasificacion = 0;
			int tipocliente = 1;
			
			if(eOperacionSolicitud.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.RUC)){
				clasificacion = 9;
			}else{
				clasificacion = 6;
			}
			
			documento.setClasificacion(clasificacion);
			documento.setTipoCliente(tipocliente);
			documento.setCodigoSocio(eOperacionSolicitud.getCodigoClientePersona()+"");
			documento.setNumeroDocumento(eOperacionSolicitud.getNumeroDocumentoPersona());
			documento.setNumeroOperacion("Operacion Nº "+eOperacionSolicitud.getCodigoSolicitud());
			documento.setNombreArchivo(eOperacionDocumentoRevision.getNombreDocumentoOriginal());
			documento.setArchivoBinario(eOperacionDocumentoRevision.getDataDocumento());
			mensaje =  oBOLaserFiche.guardarDocumento(documento);
			
			if (mensaje.getCodigo() < 0) {
				throw new Exception("Error al guardar documento laserfiche: " + mensaje.getDescripcion());
			} else {
				mensaje.setCodigo(0);
				mensaje.setDescripcion("Proceso Exitoso");
			}
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return mensaje;
	}
	
	public static Documento obtenerDocumento(String codigoLaserFiche) {
		BOLaserFiche oBOLaserFiche = new BOLaserFiche();
		Documento respuesta = new Documento();
				
		try {
			respuesta = oBOLaserFiche.obtenerDocumento(codigoLaserFiche);
			
		} catch (Exception ex) {			
			ex.printStackTrace();
		}

		return respuesta;
	}
	
	public static String conviertirArchivoAPDF(String rutaArchivo){
		GestionDocumentoService oGestionDocumentoService = new GestionDocumentoService();
		GestionDocumento oGestionDocumento = oGestionDocumentoService.getGestionDocumentoPort();
		
		String rutaSalida = "";
		try{
			rutaSalida = oGestionDocumento.conviertirArchivoAPDF(rutaArchivo);
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		
		return rutaSalida;
	}
}
