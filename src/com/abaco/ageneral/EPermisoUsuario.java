package com.abaco.ageneral;

import java.sql.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EPermisoUsuario {
	private int codigoUsuario;
	private String nombreUsuario;
	private int codigoAutonomia;
	private int codigoArea;

	private EUsuario usuarioRegistro;
}
