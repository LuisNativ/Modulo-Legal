
package com.abaco.servicio.laserfiche;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.servicio package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GuardarDocumentoResponse_QNAME = new QName("http://servicio.ws/", "guardarDocumentoResponse");
    private final static QName _ObtenerDoumento_QNAME = new QName("http://servicio.ws/", "obtenerDoumento");
    private final static QName _ConviertirArchivoAPDF_QNAME = new QName("http://servicio.ws/", "conviertirArchivoAPDF");
    private final static QName _ConviertirArchivoAPDFResponse_QNAME = new QName("http://servicio.ws/", "conviertirArchivoAPDFResponse");
    private final static QName _CopiarTodoProspectoASocioResponse_QNAME = new QName("http://servicio.ws/", "copiarTodoProspectoASocioResponse");
    private final static QName _ObtenerArchivoResponse_QNAME = new QName("http://servicio.ws/", "obtenerArchivoResponse");
    private final static QName _GuardarArchivoResponse_QNAME = new QName("http://servicio.ws/", "guardarArchivoResponse");
    private final static QName _CopiarTodoProspectoASocio_QNAME = new QName("http://servicio.ws/", "copiarTodoProspectoASocio");
    private final static QName _GuardarArchivo_QNAME = new QName("http://servicio.ws/", "guardarArchivo");
    private final static QName _ObtenerArchivo_QNAME = new QName("http://servicio.ws/", "obtenerArchivo");
    private final static QName _GuardarDocumento_QNAME = new QName("http://servicio.ws/", "guardarDocumento");
    private final static QName _ObtenerDoumentoResponse_QNAME = new QName("http://servicio.ws/", "obtenerDoumentoResponse");
    private final static QName _BuscarCarpeta_QNAME = new QName("http://servicio.ws/", "buscarCarpeta");
    private final static QName _BuscarCarpetaResponse_QNAME = new QName("http://servicio.ws/", "buscarCarpetaResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.servicio
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CopiarTodoProspectoASocioResponse }
     * 
     */
    public CopiarTodoProspectoASocioResponse createCopiarTodoProspectoASocioResponse() {
        return new CopiarTodoProspectoASocioResponse();
    }

    /**
     * Create an instance of {@link ObtenerArchivoResponse }
     * 
     */
    public ObtenerArchivoResponse createObtenerArchivoResponse() {
        return new ObtenerArchivoResponse();
    }

    /**
     * Create an instance of {@link ConviertirArchivoAPDFResponse }
     * 
     */
    public ConviertirArchivoAPDFResponse createConviertirArchivoAPDFResponse() {
        return new ConviertirArchivoAPDFResponse();
    }

    /**
     * Create an instance of {@link ConviertirArchivoAPDF }
     * 
     */
    public ConviertirArchivoAPDF createConviertirArchivoAPDF() {
        return new ConviertirArchivoAPDF();
    }

    /**
     * Create an instance of {@link GuardarDocumentoResponse }
     * 
     */
    public GuardarDocumentoResponse createGuardarDocumentoResponse() {
        return new GuardarDocumentoResponse();
    }

    /**
     * Create an instance of {@link ObtenerDoumento }
     * 
     */
    public ObtenerDoumento createObtenerDoumento() {
        return new ObtenerDoumento();
    }

    /**
     * Create an instance of {@link BuscarCarpeta }
     * 
     */
    public BuscarCarpeta createBuscarCarpeta() {
        return new BuscarCarpeta();
    }

    /**
     * Create an instance of {@link BuscarCarpetaResponse }
     * 
     */
    public BuscarCarpetaResponse createBuscarCarpetaResponse() {
        return new BuscarCarpetaResponse();
    }

    /**
     * Create an instance of {@link ObtenerDoumentoResponse }
     * 
     */
    public ObtenerDoumentoResponse createObtenerDoumentoResponse() {
        return new ObtenerDoumentoResponse();
    }

    /**
     * Create an instance of {@link GuardarDocumento }
     * 
     */
    public GuardarDocumento createGuardarDocumento() {
        return new GuardarDocumento();
    }

    /**
     * Create an instance of {@link ObtenerArchivo }
     * 
     */
    public ObtenerArchivo createObtenerArchivo() {
        return new ObtenerArchivo();
    }

    /**
     * Create an instance of {@link GuardarArchivoResponse }
     * 
     */
    public GuardarArchivoResponse createGuardarArchivoResponse() {
        return new GuardarArchivoResponse();
    }

    /**
     * Create an instance of {@link CopiarTodoProspectoASocio }
     * 
     */
    public CopiarTodoProspectoASocio createCopiarTodoProspectoASocio() {
        return new CopiarTodoProspectoASocio();
    }

    /**
     * Create an instance of {@link GuardarArchivo }
     * 
     */
    public GuardarArchivo createGuardarArchivo() {
        return new GuardarArchivo();
    }

    /**
     * Create an instance of {@link Archivo }
     * 
     */
    public Archivo createArchivo() {
        return new Archivo();
    }

    /**
     * Create an instance of {@link Documento }
     * 
     */
    public Documento createDocumento() {
        return new Documento();
    }

    /**
     * Create an instance of {@link Mensaje }
     * 
     */
    public Mensaje createMensaje() {
        return new Mensaje();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarDocumentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "guardarDocumentoResponse")
    public JAXBElement<GuardarDocumentoResponse> createGuardarDocumentoResponse(GuardarDocumentoResponse value) {
        return new JAXBElement<GuardarDocumentoResponse>(_GuardarDocumentoResponse_QNAME, GuardarDocumentoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDoumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "obtenerDoumento")
    public JAXBElement<ObtenerDoumento> createObtenerDoumento(ObtenerDoumento value) {
        return new JAXBElement<ObtenerDoumento>(_ObtenerDoumento_QNAME, ObtenerDoumento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConviertirArchivoAPDF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "conviertirArchivoAPDF")
    public JAXBElement<ConviertirArchivoAPDF> createConviertirArchivoAPDF(ConviertirArchivoAPDF value) {
        return new JAXBElement<ConviertirArchivoAPDF>(_ConviertirArchivoAPDF_QNAME, ConviertirArchivoAPDF.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConviertirArchivoAPDFResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "conviertirArchivoAPDFResponse")
    public JAXBElement<ConviertirArchivoAPDFResponse> createConviertirArchivoAPDFResponse(ConviertirArchivoAPDFResponse value) {
        return new JAXBElement<ConviertirArchivoAPDFResponse>(_ConviertirArchivoAPDFResponse_QNAME, ConviertirArchivoAPDFResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopiarTodoProspectoASocioResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "copiarTodoProspectoASocioResponse")
    public JAXBElement<CopiarTodoProspectoASocioResponse> createCopiarTodoProspectoASocioResponse(CopiarTodoProspectoASocioResponse value) {
        return new JAXBElement<CopiarTodoProspectoASocioResponse>(_CopiarTodoProspectoASocioResponse_QNAME, CopiarTodoProspectoASocioResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArchivoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "obtenerArchivoResponse")
    public JAXBElement<ObtenerArchivoResponse> createObtenerArchivoResponse(ObtenerArchivoResponse value) {
        return new JAXBElement<ObtenerArchivoResponse>(_ObtenerArchivoResponse_QNAME, ObtenerArchivoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarArchivoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "guardarArchivoResponse")
    public JAXBElement<GuardarArchivoResponse> createGuardarArchivoResponse(GuardarArchivoResponse value) {
        return new JAXBElement<GuardarArchivoResponse>(_GuardarArchivoResponse_QNAME, GuardarArchivoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopiarTodoProspectoASocio }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "copiarTodoProspectoASocio")
    public JAXBElement<CopiarTodoProspectoASocio> createCopiarTodoProspectoASocio(CopiarTodoProspectoASocio value) {
        return new JAXBElement<CopiarTodoProspectoASocio>(_CopiarTodoProspectoASocio_QNAME, CopiarTodoProspectoASocio.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarArchivo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "guardarArchivo")
    public JAXBElement<GuardarArchivo> createGuardarArchivo(GuardarArchivo value) {
        return new JAXBElement<GuardarArchivo>(_GuardarArchivo_QNAME, GuardarArchivo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerArchivo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "obtenerArchivo")
    public JAXBElement<ObtenerArchivo> createObtenerArchivo(ObtenerArchivo value) {
        return new JAXBElement<ObtenerArchivo>(_ObtenerArchivo_QNAME, ObtenerArchivo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GuardarDocumento }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "guardarDocumento")
    public JAXBElement<GuardarDocumento> createGuardarDocumento(GuardarDocumento value) {
        return new JAXBElement<GuardarDocumento>(_GuardarDocumento_QNAME, GuardarDocumento.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObtenerDoumentoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "obtenerDoumentoResponse")
    public JAXBElement<ObtenerDoumentoResponse> createObtenerDoumentoResponse(ObtenerDoumentoResponse value) {
        return new JAXBElement<ObtenerDoumentoResponse>(_ObtenerDoumentoResponse_QNAME, ObtenerDoumentoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCarpeta }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "buscarCarpeta")
    public JAXBElement<BuscarCarpeta> createBuscarCarpeta(BuscarCarpeta value) {
        return new JAXBElement<BuscarCarpeta>(_BuscarCarpeta_QNAME, BuscarCarpeta.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BuscarCarpetaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servicio.ws/", name = "buscarCarpetaResponse")
    public JAXBElement<BuscarCarpetaResponse> createBuscarCarpetaResponse(BuscarCarpetaResponse value) {
        return new JAXBElement<BuscarCarpetaResponse>(_BuscarCarpetaResponse_QNAME, BuscarCarpetaResponse.class, null, value);
    }

}
