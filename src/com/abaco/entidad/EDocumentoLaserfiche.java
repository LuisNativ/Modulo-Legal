/**
 * 
 */
package com.abaco.entidad;

import java.util.Date;

import com.abaco.negocio.util.UFuncionesGenerales;

/**
 * @Autor Augusto Bravo
 * @Desde 30/04/2015
 * @Version 1.0.0 
 */
public class EDocumentoLaserfiche {

	private int nroControl;
	private int nroRegistro;
	private int nroSecuencia;
	private Date fechaCarga;
	private String nombreArchivo;
	private String nombreArchivoCompleto;
	private String observacion;
	private byte[] archivoBinario;
	private String usuario;
	private String numeroCtaRendir;
	private String tipoDocumento;
	private String numeroDocumento;
	
	
	/**
	 * @return the nroControl
	 */
	public int getNroControl() {
		return nroControl;
	}
	/**
	 * @param nroControl the nroControl to set
	 */
	public void setNroControl(int nroControl) {
		this.nroControl = nroControl;
	}
	/**
	 * @return the nroRegistro
	 */
	public int getNroRegistro() {
		return nroRegistro;
	}
	/**
	 * @param nroRegistro the nroRegistro to set
	 */
	public void setNroRegistro(int nroRegistro) {
		this.nroRegistro = nroRegistro;
	}
	/**
	 * @return the nroSecuencia
	 */
	public int getNroSecuencia() {
		return nroSecuencia;
	}
	/**
	 * @param nroSecuencia the nroSecuencia to set
	 */
	public void setNroSecuencia(int nroSecuencia) {
		this.nroSecuencia = nroSecuencia;
	}
	/**
	 * @return the fechaCarga
	 */
	public Date getFechaCarga() {
		return fechaCarga;
	}
	/**
	 * @param fechaCarga the fechaCarga to set
	 */
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}
	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	/**
	 * @return the archivoBinario
	 */
	public byte[] getArchivoBinario() {
		return archivoBinario;
	}
	/**
	 * @param archivoBinario the archivoBinario to set
	 */
	public void setArchivoBinario(byte[] archivoBinario) {
		this.archivoBinario = archivoBinario;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the nombreArchivoCompleto
	 */
	public String getNombreArchivoCompleto() {
		return nombreArchivoCompleto;
	}
	/**
	 * @param nombreArchivoCompleto the nombreArchivoCompleto to set
	 */
	public void setNombreArchivoCompleto(String nombreArchivoCompleto) {
		this.nombreArchivoCompleto = nombreArchivoCompleto;
	}
	
	/**
	 * @return the numero
	 */
	
	public String getNumeroCtaRendir() {
		this.numeroCtaRendir = UFuncionesGenerales.completaNumeroCerosIzquierda(String.valueOf(nroControl), 6).concat(UFuncionesGenerales.completaNumeroCerosIzquierda(String.valueOf(nroRegistro), 4));
		return numeroCtaRendir;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	/**
	 * @param numeroDocumento the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	
}
