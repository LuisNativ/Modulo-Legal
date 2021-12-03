package com.abaco.bo;

import java.util.List;

import com.abaco.servicio.laserfiche.*;
import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.negocio.util.UConstante.UTipoBusqueda;
import com.abaco.negocio.util.UManejadorLog;

public class BOLaserFiche {
	
	public Archivo obtenerArchivo(Archivo archivo) {
		GestionDocumentoService oGestionDocumentoService = new GestionDocumentoService();
		GestionDocumento oGestionDocumento = oGestionDocumentoService.getGestionDocumentoPort();

		Archivo archivoRespuesta = null;
		try {
			archivoRespuesta = oGestionDocumento.obtenerArchivo(archivo);
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return archivoRespuesta;
	}
	
	public Documento obtenerDocumento(String codigoLaserFiche) {
		GestionDocumentoService oGestionDocumentoService = new GestionDocumentoService();
		GestionDocumento oGestionDocumento = oGestionDocumentoService.getGestionDocumentoPort();

		Documento respuesta = null;
		try {
			respuesta = oGestionDocumento.obtenerDoumento(codigoLaserFiche);
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return respuesta;
	}
	
	public Mensaje guardarArchivo(Archivo archivo) {
		GestionDocumentoService oGestionDocumentoService = new GestionDocumentoService();
		GestionDocumento oGestionDocumento = oGestionDocumentoService.getGestionDocumentoPort();
		 
		Mensaje mensaje = null;	
		try{
			mensaje = oGestionDocumento.guardarArchivo(archivo);
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return mensaje;
	}

	public Mensaje guardarDocumento(Documento documento) {
		GestionDocumentoService oGestionDocumentoService = new GestionDocumentoService();
		GestionDocumento oGestionDocumento = oGestionDocumentoService.getGestionDocumentoPort();
		 
		Mensaje mensaje = null;
		try{
			mensaje = oGestionDocumento.guardarDocumento(documento);
		} catch (Exception objEx) {
			UManejadorLog.log("Problema al conectar con el Web Service LaserFiche: " + objEx.getMessage());
		}
		return mensaje;
	}
}
