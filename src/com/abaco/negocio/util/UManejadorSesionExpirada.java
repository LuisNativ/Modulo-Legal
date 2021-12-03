/**
 * FISesionExpirada.java.java 14/12/2011
 * Creado por: B-IT Solutions
 */
package com.abaco.negocio.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @Autor Luiggi Mendoza J.
 * @Desde 14/12/2011
 * @Version 1.0.0
 */

public class UManejadorSesionExpirada implements Filter {

	private String paginaSesionExpirada = "/esquema/sesion_expirada.xhtml";
	private String paginaInicio = "/esquema/login.xhtml";

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if ((request instanceof HttpServletRequest)
				&& (response instanceof HttpServletResponse)) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			if (esSesionInvalida(httpServletRequest)) {
				if (!verificaRecursoNecesitaSesionActiva(httpServletRequest)) {
					String timeoutUrl = httpServletRequest.getContextPath() + "/" + getPaginaSesionExpirada();					
					httpServletResponse.sendRedirect(timeoutUrl);
					return;
				}
			} else {
				String requestPath = httpServletRequest.getRequestURI();		
				boolean controlRequired = contains(requestPath, "login.xhtml");
				if(controlRequired && requestPath.equalsIgnoreCase("/AbacoInLegal/esquema/login.xhtml")== false )
				{
					httpServletResponse.sendRedirect("/AbacoInLegal/esquema/login.xhtml");
					return;
				}
			}
			filterChain.doFilter(request, response);
		}
	}

	private boolean verificaRecursoNecesitaSesionActiva(
			HttpServletRequest httpServletRequest) {

		String requestPath = httpServletRequest.getRequestURI();		
		boolean controlRequired = contains(requestPath,
				getPaginaSesionExpirada())
				|| contains(requestPath, paginaInicio);		
		return controlRequired;
	}

	private boolean esSesionInvalida(HttpServletRequest httpServletRequest) {
		
		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
				&& !httpServletRequest.isRequestedSessionIdValid();
		return sessionInValid;
	}

	public String getPaginaSesionExpirada() {
		return paginaSesionExpirada;
	}

	public void setPaginaSesionExpirada(String paginaSesionExpirada) {
		this.paginaSesionExpirada = paginaSesionExpirada;
	}

	private boolean contains(String strCadena1, String strCadena2) {
		boolean blnResultado = true;
		blnResultado = !(strCadena1 == null || strCadena2 == null);
		if (blnResultado) {
			blnResultado = strCadena1.contains(strCadena2);
		}
		return blnResultado;
	}
}
