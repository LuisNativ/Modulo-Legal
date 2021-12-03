
package com.abaco.servicio.laserfiche;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para archivo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="archivo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nombreArchivo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rutaRepositorio" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="crearFolderRepositorio" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="archivoBinario" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archivo", propOrder = {
    "nombreArchivo",
    "rutaRepositorio",
    "crearFolderRepositorio",
    "archivoBinario"
})
public class Archivo {

    @XmlElement(required = true)
    protected String nombreArchivo;
    @XmlElement(required = true)
    protected List<String> rutaRepositorio;
    protected boolean crearFolderRepositorio;
    @XmlElement(required = true)
    protected byte[] archivoBinario;

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
     * Gets the value of the rutaRepositorio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rutaRepositorio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRutaRepositorio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRutaRepositorio() {
        if (rutaRepositorio == null) {
            rutaRepositorio = new ArrayList<String>();
        }
        return this.rutaRepositorio;
    }

    /**
     * Obtiene el valor de la propiedad crearFolderRepositorio.
     * 
     */
    public boolean isCrearFolderRepositorio() {
        return crearFolderRepositorio;
    }

    /**
     * Define el valor de la propiedad crearFolderRepositorio.
     * 
     */
    public void setCrearFolderRepositorio(boolean value) {
        this.crearFolderRepositorio = value;
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

}
