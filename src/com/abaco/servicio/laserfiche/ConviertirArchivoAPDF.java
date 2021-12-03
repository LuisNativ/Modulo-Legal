
package com.abaco.servicio.laserfiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para conviertirArchivoAPDF complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="conviertirArchivoAPDF">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rutaArchivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "conviertirArchivoAPDF", propOrder = {
    "rutaArchivo"
})
public class ConviertirArchivoAPDF {

    protected String rutaArchivo;

    /**
     * Obtiene el valor de la propiedad rutaArchivo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * Define el valor de la propiedad rutaArchivo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRutaArchivo(String value) {
        this.rutaArchivo = value;
    }

}
