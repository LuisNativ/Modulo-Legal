package com.abaco.entidad;

public class EPersonaParametro {

	private long codPersona;
	private EDocumentoIdentidad documentoPersona;
	private String nombrePersona;
	private boolean completo;
	private int codigoEstado;
	
	public EPersonaParametro(){
		
	}
	
	public long getCodPersona() {
		return codPersona;
	}
	
	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}
	
	public EDocumentoIdentidad getDocumentoPersona() {
		return documentoPersona;
	}
	
	public void setDocumentoPersona(EDocumentoIdentidad documentoPersona) {
		this.documentoPersona = documentoPersona;
	}
	
	public String getNombrePersona() {
		return nombrePersona;
	}
	
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public boolean isCompleto() {
		return completo;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
	}

	public int getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(int codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	
}
