package com.abaco.ageneral;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOperacionMensaje {
	private long codigoSolicitud;
	private int codigoMensaje;
	private int codigoRevision;
	private String descripcionMensaje;
	private String abreviacionMensaje;
	private int numeroDocumento;
	//private int codigoAreaEnvio;
	//private int codigoUsuarioEnvio;
	private Date fechaEnvio;
	private String horaEnvio;
	private String descripcionUsuarioEnvio;
	private Date fechaRegistro;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
}
