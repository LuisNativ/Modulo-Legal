
package com.abaco.servicio.laserfiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para obtenerArchivo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="obtenerArchivo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="archivoBusqueda" type="{http://servicio.ws/}archivo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obtenerArchivo", propOrder = {
    "archivoBusqueda"
})
public class ObtenerArchivo {

    protected Archivo archivoBusqueda;

    /**
     * Obtiene el valor de la propiedad archivoBusqueda.
     * 
     * @return
     *     possible object is
     *     {@link Archivo }
     *     
     */
    public Archivo getArchivoBusqueda() {
        return archivoBusqueda;
    }

    /**
     * Define el valor de la propiedad archivoBusqueda.
     * 
     * @param value
     *     allowed object is
     *     {@link Archivo }
     *     
     */
    public void setArchivoBusqueda(Archivo value) {
        this.archivoBusqueda = value;
    }

}
