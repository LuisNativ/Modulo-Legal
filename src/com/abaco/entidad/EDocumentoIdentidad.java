package com.abaco.entidad;

public class EDocumentoIdentidad {

	private String documento;	
	private ETipoDocumentoPersona tipoDocumento;
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}
	
	public ETipoDocumentoPersona getTipoDocumento() {
		return tipoDocumento;
	}
	
	public void setTipoDocumento(ETipoDocumentoPersona tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
}
