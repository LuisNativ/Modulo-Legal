package com.abaco.ageneral;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ERevisionDocumento {
	private long codigoSolicitud;
	private int codigoMensaje;
	private int codigoDocumento;
	private String codigoDocumentoLaserFiche;
	//private int secuencia;
	//private String observacion;
	//private int calificacion;
	//private String descripcionCalificacion;
	private String nombreDocumento;
	private String nombreDocumentoLaserFiche;
	private String nombreDocumentoOriginal;
	private byte[] dataDocumento;
	private EUsuario usuarioRegistro;
}
