package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EDeudor {
	private int codigoOrden;
	private long numeroSolicitud;
	private int codigoCliente;
	private int codigoDeudor;
	private String nombreCorte;
	private String nombreLargo;
	
	private ETercero tercero;
	private EDeudorAdicional adicional;
	private EDeudorEstado estado;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private int indicadorTemporal;
}
