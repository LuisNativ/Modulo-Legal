package com.abaco.negocio.util;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UManejadorSesionWeb {

	public static String obtieneParametroQueryString(String strNombreParametro) {
		String strResultado = "";
		try {
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			strResultado = request.getParameter(strNombreParametro);
		} catch(Exception objEx) {
			UManejadorLog.error("No se pudo obtener el parámetro de QueryString: " + objEx.getMessage());
		}
        return strResultado;
    }
	
	public static String obtieneVariableWebXML(String strNombreVariable) {
		String strResultado = "";
		try {
			strResultado = (String)UContenedorListas.obtieneInstancia().obtieneObjeto(strNombreVariable);
		} catch(Exception objEx) {
			UManejadorLog.error("No se pudo obtener la variable de web.xml: " + objEx.getMessage());
		}
		return strResultado;
	}
	
	public static void redirigePagina(String strUrlRelativa) {
		try {
			ExternalContext externalContext =
				FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(strUrlRelativa);
		} catch(Exception objEx) {
			UManejadorLog.error("No se pudo redirigir a la ruta: " + strUrlRelativa);
		}
	}
	
	public static void eliminaVariableSesion(String strNombreVariable) {
		try {
			FacesContext objFC = FacesContext.getCurrentInstance();
			Object request = objFC.getExternalContext().getRequest();
			HttpServletRequest objServletRequest = (HttpServletRequest)request;
			HttpSession objSesion = objServletRequest.getSession();
			objSesion.removeAttribute(strNombreVariable);
		}catch (Exception objEx) {
			UManejadorLog.error("Problema al eliminar variable de sesión: " + objEx.getMessage());
		}
	}
	
	public static void registraVariableSesion(String strNombreVariable, Object objVariable) {
		try {
			FacesContext objFC = FacesContext.getCurrentInstance();
			Object request = objFC.getExternalContext().getRequest();
			HttpServletRequest objServletRequest = (HttpServletRequest)request;
			HttpSession objSesion = objServletRequest.getSession();
			objSesion.setAttribute(strNombreVariable, objVariable);
		} catch (Exception objEx) {
			UManejadorLog.error("Problema al registrar variable de sesión: " + objEx.getMessage());
		}
	}
	
	public static Object obtieneVariableSesion(String strNombreVariable) {
		Object objeto = null;
		try {
			FacesContext objFC = FacesContext.getCurrentInstance();
			Object request = objFC.getExternalContext().getRequest();
			HttpServletRequest objServletRequest = (HttpServletRequest)request;
			HttpSession objSesion = objServletRequest.getSession();
			objeto = objSesion.getAttribute(strNombreVariable);
		} catch (Exception objEx) {
			UManejadorLog.error("Problema al obtener variable de sesión: " + objEx.getMessage());
		}
		return objeto;
	}
	
	public static void eliminaSesion() {
		try {
			FacesContext objFC = FacesContext.getCurrentInstance();
			Object request = objFC.getExternalContext().getRequest();
			HttpServletRequest objServletRequest = (HttpServletRequest)request;
			HttpSession objSesion = objServletRequest.getSession();
			objSesion.invalidate();
		} catch(Exception objEx) {
			UManejadorLog.error("No se pudo eliminar los datos de sesión: " + objEx.getMessage());
		}
	}

	public static HttpServletResponse obtieneHttpResponse() {
		FacesContext objFC;
		Object request;
		HttpServletResponse objServletResponse = null;
		try {
			objFC = FacesContext.getCurrentInstance();
			request = objFC.getExternalContext().getResponse();
			objServletResponse = (HttpServletResponse)request;
		} catch (Exception objEx) {
			UManejadorLog.error("No se pudo obtener el request: " + objEx.getMessage());
			UManejadorLog.error("Lugar del problema: " + objEx.getStackTrace()[0]);
		}
		return objServletResponse;
	}
}
