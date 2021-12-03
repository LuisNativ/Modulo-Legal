package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EObservacionLegal {
	private long numeroSolicitud;
	private int codigoEstado;
	private String descripcionMensaje;
	private String nombreUsuario;
	private int secuencia;
	private int lineaObservacion;
	
	private Date fechaRegistro;
	private Date horaRegistro;
}
