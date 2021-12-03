package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOperacionClienteDocumento {
	private long numeroSolicitud;
	private int codigoCliente;
	private int codigoTipoCliente;
	private int codigoDocumento;
	private String codigoDocumentoLaserFiche;
	private String nombreDocumento;
	private String nombreDocumentoLaserFiche;
	private String nombreDocumentoOriginal;
	private byte[] dataDocumento;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}
