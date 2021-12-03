package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EObservacionNegocios {
	private long numeroSolicitud;
	private int secuencia;
	private String observacion;
	private String nombreUsuario;
	
	private Date fechaEvaluacion;
	private String horaEvaluacion;
}
