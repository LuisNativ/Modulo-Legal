package com.abaco.ageneral;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOperacionDocumento {
	private long codigoSolicitud;
	private int codigoMensaje;
	private int codigoDocumento;
	private String codigoDocumentoLaserFiche;
	private String nombreDocumento;
	private String nombreDocumentoLaserFiche;
	private String nombreDocumentoOriginal;
	private byte[] dataDocumento;
	private EUsuario usuarioRegistro;
	
	//Adicionales
	private String areaEmisora;
	private String areaReceptora;
	private String descripcionDocumento;
	private int codigofirmaDocumento;
	private String firmaDocumento;
	private String obervacionDocumento;
	private int estadoDocumento;
	private String descripcionEstadoDocumento;
	private int codigoGarantia;
	private String usuarioRegist;
	private Date fechaRegistro;
	private String horaRegistro;
	private String usuarioModif;
	private Date fechaModificacion;
	private String horaModificacion;
}
