
package com.abaco.servicio.laserfiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerArchivoResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerArchivoResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="archivoRespuesta" type="{http://servicio.ws/}archivo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerArchivoResponse", propOrder = {
    "archivoRespuesta"
})
public class ObtenerArchivoResponse {

    protected Archivo archivoRespuesta;

    /**
     * Obtiene el valor de la propiedad archivoRespuesta.
     * 
     * @return
     *     possible object is
     *     {@link Archivo }
     *     
     */
    public Archivo getArchivoRespuesta() {
        return archivoRespuesta;
    }

    /**
     * Define el valor de la propiedad archivoRespuesta.
     * 
     * @param value
     *     allowed object is
     *     {@link Archivo }
     *     
     */
    public void setArchivoRespuesta(Archivo value) {
        this.archivoRespuesta = value;
    }

}
