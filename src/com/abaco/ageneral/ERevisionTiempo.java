package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ERevisionTiempo implements Serializable{
	private long codigoSolicitud;	
	private int codigoArea;
	private String descripcionArea;
	private long tiempoSegundoEnvioTotal;
	private long tiempoSegundoActivoTotal;
	private String tiempoTranscurridoEnvioTotal;
	private String tiempoTranscurridoActivoTotal;
	private List<ERevisionSesion> lstRevisionSesion;
}
