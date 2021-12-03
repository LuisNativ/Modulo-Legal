package com.abaco.negocio.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.abaco.negocio.util.UConfiguraciones;
import com.abaco.negocio.util.UConstante.UVariablesWebXML;
import com.abaco.negocio.util.UManejadorLog;



/**
 * Application Lifecycle Listener implementation class LListenerServiciosAplicacion
 *
 */
public class LListenerServiciosAplicacion implements ServletContextListener {

	

    /**
     * Default constructor. 
     */
    public LListenerServiciosAplicacion() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	ServletContext objServletContext = event.getServletContext();
    	registraVariablesWebXML(objServletContext);

    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event) {
    	
    }

	private void registraVariablesWebXML(ServletContext objServletContext) {

		UConfiguraciones.obtieneInstancia().registraClave("MOTOR_BASE_DATOS", objServletContext.getInitParameter("MOTOR_BASE_DATOS"));
		UConfiguraciones.obtieneInstancia().registraClave("DEBUG", objServletContext.getInitParameter("DEBUG"));
		UConfiguraciones.obtieneInstancia().registraClave(UVariablesWebXML.MENU_PRINCIPAL, objServletContext.getInitParameter(UVariablesWebXML.MENU_PRINCIPAL));
		UManejadorLog.log("SERVICIOS_LASERFICHE: " + objServletContext.getInitParameter(UVariablesWebXML.SERVICIOS_LASERFICHE));
		UConfiguraciones.obtieneInstancia().registraClave(UVariablesWebXML.SERVICIOS_LASERFICHE, objServletContext.getInitParameter(UVariablesWebXML.SERVICIOS_LASERFICHE));
    	
	}

  
}
