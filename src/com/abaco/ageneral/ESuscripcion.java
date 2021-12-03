package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ESuscripcion {
	private int codigoOrden;
	//private int codigoAccion;
	
	private long numeroSolicitud;
	private int codigoCliente;
	private int codigoTipoCliente;
	private String numeroDocumento;
	private int numeroSuscripcion;
	private String nombreLargo;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}