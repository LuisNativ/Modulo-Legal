package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ESolicitudLogMovimiento implements Serializable{
	private long numeroSolicitud;
	private String nombreLargo;
	private int codigoTipoProducto;
	
	private int codigoUbicacionLog;
	private Date fechaRevision;
	private String horaRevision;
	private String usuarioRevision;
	
	private int codigoAccion;
	private int codigoUbicacionActual;
	private int codigoUbicacionDestino;
	
	private Date fechaRegistro;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
	
	private int codigoOrden;
}
