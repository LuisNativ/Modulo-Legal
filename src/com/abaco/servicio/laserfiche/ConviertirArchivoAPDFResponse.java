
package com.abaco.servicio.laserfiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para conviertirArchivoAPDFResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="conviertirArchivoAPDFResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rutaSalida" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conviertirArchivoAPDFResponse", propOrder = {
    "rutaSalida"
})
public class ConviertirArchivoAPDFResponse {

    protected String rutaSalida;

    /**
     * Obtiene el valor de la propiedad rutaSalida.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutaSalida() {
        return rutaSalida;
    }

    /**
     * Define el valor de la propiedad rutaSalida.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutaSalida(String value) {
        this.rutaSalida = value;
    }

}
