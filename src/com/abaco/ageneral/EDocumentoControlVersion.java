package com.abaco.ageneral;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EDocumentoControlVersion {
	private int codigoTipoEvaluacion;
	private int codigoDocumento;
	private String nombreDocumento;
	private String observacionDocumento;
	private byte[] dataDocumento;
	private EUsuario usuarioRegistro;
}
