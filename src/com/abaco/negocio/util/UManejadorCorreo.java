package com.abaco.negocio.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.abaco.entidad.EArchivo;
import com.abaco.negocio.util.UConstante.UFormatoTexto;

public class UManejadorCorreo {

	public void enviarCorreoRechazo(List<Object> lstParametrosContenido, String emailDestino) {				
		Properties properties = new Properties();
		try {
			UEmail email = new UEmail();			
			properties.load(getClass().getClassLoader().getResourceAsStream(
					"com/abaco/presentacion/properties/configuracion.properties"));
			
			String asunto = properties.getProperty("correo.msg.rechazo_correo.asunto");
			String cuerpoPrev = UFuncionesGenerales.completarParametrosContenido(lstParametrosContenido,properties.getProperty("correo.msg.rechazo_correo.cuerpo"));
			
			StringBuilder sbCuerpo = new StringBuilder();
			sbCuerpo.append(cuerpoPrev);
			sbCuerpo.append(properties.getProperty("correo.msg.rechazo_correo.firma"));
			sbCuerpo.append(properties.getProperty("correo.msg.rechazo_correo.privacidad"));
			
			UManejadorLog.log("Asunto:" + asunto);
			UManejadorLog.log("Cuerpo:" + sbCuerpo.toString());
			UManejadorLog.log("Destin:" + emailDestino);
			
			email.mail_alist(sbCuerpo.toString(), asunto , emailDestino, properties, UFormatoTexto.FORMATO_TEXTO_PLANO);
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
	}

	public void enviarCorreoAlerta(List<Object> lstParametrosContenido,
			String emailDestino) {
		Properties properties = new Properties();
		try {
			UEmail email = new UEmail();			
			properties.load(getClass().getClassLoader().getResourceAsStream(
					"com/abaco/presentacion/properties/configuracion.properties"));
			
			String asunto = properties.getProperty("correo.msg.alerta_correo.asunto");
			String cuerpoPrev = UFuncionesGenerales.completarParametrosContenido(
					lstParametrosContenido,properties.getProperty("correo.msg.alerta_correo.cuerpo"));
			
			StringBuilder sbCuerpo = new StringBuilder();
			sbCuerpo.append(cuerpoPrev);
			sbCuerpo.append(properties.getProperty("correo.msg.alerta_correo.firma"));
			sbCuerpo.append(properties.getProperty("correo.msg.alerta_correo.privacidad"));
			
			UManejadorLog.log("Asunto:" + asunto);
			UManejadorLog.log("Cuerpo:" + sbCuerpo.toString());
			UManejadorLog.log("Destin:" + emailDestino);
			
			email.mail_alist(sbCuerpo.toString(), asunto , emailDestino, properties, UFormatoTexto.FORMATO_TEXTO_PLANO);
		} catch (IOException ex) {
			ex.printStackTrace();
		}		
		
	}

	public void enviarCorreo(List<Object> lstParametrosContenido, String asunto, String cuerpo, String emailDestino, String firma, String privacidad) {
		try {
			UEmail email = new UEmail();					
			StringBuilder sbCuerpo = new StringBuilder();
			String cuerpoPrev = UFuncionesGenerales.completarParametrosContenido(lstParametrosContenido,cuerpo);
			sbCuerpo.append(cuerpoPrev);
			sbCuerpo.append(firma);
			sbCuerpo.append(privacidad);
						
			email.mail_alist(sbCuerpo.toString(), asunto , emailDestino, UFormatoTexto.FORMATO_TEXTO_PLANO);
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		
	}
	
	/*
	public void enviarCorreoConAdjunto(List<Object> lstParametrosContenido, String asunto, String cuerpo, String emailDestino, String firma, String privacidad, boolean adjunto , String rutaArchivoAdjunto, String nombreArchivoAdjunto, ArrayList<EArchivo> archivosAdjuntoAdi) {
		try {
			UEmail email = new UEmail();					
			StringBuilder sbCuerpo = new StringBuilder();
			String cuerpoPrev = UFuncionesGenerales.completarParametrosContenido(lstParametrosContenido,cuerpo);
			sbCuerpo.append(cuerpoPrev);
			sbCuerpo.append(firma);
			sbCuerpo.append(privacidad);
						
			email.mail_alist(sbCuerpo.toString(), asunto , emailDestino, UFormatoTexto.FORMATO_TEXTO_PLANO, adjunto, rutaArchivoAdjunto, nombreArchivoAdjunto, archivosAdjuntoAdi);
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		
	}
	*/
}


