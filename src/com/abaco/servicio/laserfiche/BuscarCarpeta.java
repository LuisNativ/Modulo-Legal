
package com.abaco.servicio.laserfiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para buscarCarpeta complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="buscarCarpeta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoBusqueda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreArchivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "buscarCarpeta", propOrder = {
    "tipoBusqueda",
    "nombreArchivo"
})
public class BuscarCarpeta {

    protected String tipoBusqueda;
    protected String nombreArchivo;

    /**
     * Obtiene el valor de la propiedad tipoBusqueda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    /**
     * Define el valor de la propiedad tipoBusqueda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoBusqueda(String value) {
        this.tipoBusqueda = value;
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

}
