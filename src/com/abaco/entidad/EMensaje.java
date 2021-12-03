package com.abaco.entidad;

public class EMensaje {

	private int idMensaje;
	private String codMensaje;
	private String descMensaje;
	private String tipoMensaje;
	private String codigoSocio;
	
	public EMensaje(){
	}

	public int getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(int idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getCodMensaje() {
		return codMensaje;
	}

	public void setCodMensaje(String codMensaje) {
		this.codMensaje = codMensaje;
	}

	public String getDescMensaje() {
		return descMensaje;
	}

	public void setDescMensaje(String descMensaje) {
		this.descMensaje = descMensaje;
	}	
	
	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	
	public String getCodigoSocio() {
		return codigoSocio;
	}

	public void setCodigoSocio(String codigoSocio) {
		this.codigoSocio = codigoSocio;
	}
}
