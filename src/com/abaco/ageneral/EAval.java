package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EAval {
	
	private int codigoCliente;
	private int codigoAval;
	private String nombreAval;
	private long numeroSolicitud;
	private int numeroContrato;
	private int numeroPagare;
	private double montoAvalado;
	private double saldoAvalado;

}