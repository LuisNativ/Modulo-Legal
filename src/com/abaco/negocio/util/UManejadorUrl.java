package com.abaco.negocio.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.negocio.util.UConstante.UVariablesSesion;

public class UManejadorUrl {
	public static EMensaje validarUrl(String url) {
		EMensaje mensaje = new EMensaje();
		
		try {
			mensaje.setIdMensaje(0);
			mensaje.setDescMensaje("");
			
			if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.NAVEGACION_URL) != null) {
				if (!UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.NAVEGACION_URL).equals(url)) {
					mensaje.setIdMensaje(-1);
					mensaje.setDescMensaje("Acceso no autorizado");
				}
			}else{
				mensaje.setIdMensaje(-1);
				mensaje.setDescMensaje("Acceso no autorizado");
			}
			
		} catch (Exception objEx) {
			UManejadorLog.error("Problema al validar url: " + objEx.getMessage());
		}
		return mensaje;
	}
}
