package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ERevisionSesion implements Serializable{
	private long codigoSolicitud;
	private long correlativoSesion;
	private int codigoAreaOrigen;
	private String descripcionAreaOrigen;
	private int codigoUsuarioOrigen;
	private String descripcionUsuarioOrigen;
	private long tiempoSegundo;
	private String tiempoTranscurrido;
	
	private Date fechaRegistro;
	private String horaRegistro;
	private Date fechaModificacion;
	private String horaModificacion;
	private EUsuario usuarioRegistro;
}
