package com.abaco.ageneral;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EDeudorEstado {
	private int codigoCliente;
	private String codigoEstado;
	private String observacion;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String codigoEstadoUltimo;
	private String descripcionEstadoUltimo;
	private String usuarioUltimo;
	private String observacionUltimo;
	private Date fechaRegistroUltimo;
	private String horaRegistroUltimo;
}
