
package com.abaco.servicio.laserfiche;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "GestionDocumentoService", targetNamespace = "http://servicio.ws/", wsdlLocation = "http://190.216.114.148:8080/ServicioLaserficheEx/GestionDocumento?wsdl")
public class GestionDocumentoService
    extends Service
{

    private final static URL GESTIONDOCUMENTOSERVICE_WSDL_LOCATION;
    private final static WebServiceException GESTIONDOCUMENTOSERVICE_EXCEPTION;
    private final static QName GESTIONDOCUMENTOSERVICE_QNAME = new QName("http://servicio.ws/", "GestionDocumentoService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
        	//Desarrollo
        	url = new URL("http://190.216.114.148:8080/ServicioLaserficheEx/GestionDocumento?wsdl");
        	//Produccion
            //url = new URL("http://192.168.1.50:8080/ServicioLaserficheEx/GestionDocumento?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        GESTIONDOCUMENTOSERVICE_WSDL_LOCATION = url;
        GESTIONDOCUMENTOSERVICE_EXCEPTION = e;
    }

    public GestionDocumentoService() {
        super(__getWsdlLocation(), GESTIONDOCUMENTOSERVICE_QNAME);
    }

    public GestionDocumentoService(WebServiceFeature... features) {
        super(__getWsdlLocation(), GESTIONDOCUMENTOSERVICE_QNAME, features);
    }

    public GestionDocumentoService(URL wsdlLocation) {
        super(wsdlLocation, GESTIONDOCUMENTOSERVICE_QNAME);
    }

    public GestionDocumentoService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, GESTIONDOCUMENTOSERVICE_QNAME, features);
    }

    public GestionDocumentoService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GestionDocumentoService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns GestionDocumento
     */
    @WebEndpoint(name = "GestionDocumentoPort")
    public GestionDocumento getGestionDocumentoPort() {
        return super.getPort(new QName("http://servicio.ws/", "GestionDocumentoPort"), GestionDocumento.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GestionDocumento
     */
    @WebEndpoint(name = "GestionDocumentoPort")
    public GestionDocumento getGestionDocumentoPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://servicio.ws/", "GestionDocumentoPort"), GestionDocumento.class, features);
    }

    private static URL __getWsdlLocation() {
        if (GESTIONDOCUMENTOSERVICE_EXCEPTION!= null) {
            throw GESTIONDOCUMENTOSERVICE_EXCEPTION;
        }
        return GESTIONDOCUMENTOSERVICE_WSDL_LOCATION;
    }

}
