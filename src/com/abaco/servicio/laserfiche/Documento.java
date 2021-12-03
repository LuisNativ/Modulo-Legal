
package com.abaco.servicio.laserfiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para documento complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="documento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clasificacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tipoCliente" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigoSocio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroOperacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombreArchivo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="archivoBinario" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="nombreUsuario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "documento", propOrder = {
    "clasificacion",
    "tipoCliente",
    "codigoSocio",
    "numeroDocumento",
    "numeroOperacion",
    "nombreArchivo",
    "archivoBinario",
    "nombreUsuario"
})
public class Documento {

    protected int clasificacion;
    protected int tipoCliente;
    @XmlElement(required = true)
    protected String codigoSocio;
    @XmlElement(required = true)
    protected String numeroDocumento;
    @XmlElement(required = true)
    protected String numeroOperacion;
    @XmlElement(required = true)
    protected String nombreArchivo;
    @XmlElement(required = true)
    protected byte[] archivoBinario;
    @XmlElement(required = true)
    protected String nombreUsuario;

    /**
     * Obtiene el valor de la propiedad clasificacion.
     * 
     */
    public int getClasificacion() {
        return clasificacion;
    }

    /**
     * Define el valor de la propiedad clasificacion.
     * 
     */
    public void setClasificacion(int value) {
        this.clasificacion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoCliente.
     * 
     */
    public int getTipoCliente() {
        return tipoCliente;
    }

    /**
     * Define el valor de la propiedad tipoCliente.
     * 
     */
    public void setTipoCliente(int value) {
        this.tipoCliente = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoSocio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoSocio() {
        return codigoSocio;
    }

    /**
     * Define el valor de la propiedad codigoSocio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoSocio(String value) {
        this.codigoSocio = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Define el valor de la propiedad numeroDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroDocumento(String value) {
        this.numeroDocumento = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroOperacion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    /**
     * Define el valor de la propiedad numeroOperacion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroOperacion(String value) {
        this.numeroOperacion = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreArchivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * Define el valor de la propiedad nombreArchivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreArchivo(String value) {
        this.nombreArchivo = value;
    }

    /**
     * Obtiene el valor de la propiedad archivoBinario.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getArchivoBinario() {
        return archivoBinario;
    }

    /**
     * Define el valor de la propiedad archivoBinario.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setArchivoBinario(byte[] value) {
        this.archivoBinario = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreUsuario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Define el valor de la propiedad nombreUsuario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreUsuario(String value) {
        this.nombreUsuario = value;
    }

}
