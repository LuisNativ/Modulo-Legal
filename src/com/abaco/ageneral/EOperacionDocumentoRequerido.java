package com.abaco.ageneral;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOperacionDocumentoRequerido {
	private long codigoSolicitud;
	private int codigoDocumento;
	private String codigoDocumentoLaserFiche;
	private String nombreDocumento;
	private String nombreDocumentoLaserFiche;
	private String nombreDocumentoOriginal;
	private byte[] dataDocumento;
	private EUsuario usuarioRegistro;
}