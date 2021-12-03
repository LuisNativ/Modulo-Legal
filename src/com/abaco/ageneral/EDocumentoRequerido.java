package com.abaco.ageneral;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EDocumentoRequerido {
	private int codigoDocumentoRequerido;
	private String descripcionDocumentoRequerido;
	private EUsuario usuarioRegistro;
}
