package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ETipoCambio {

	private int anio;
	private int mes;
	private int moneda;
	private double valorPromedioMes;
	private double compraDiaria;
	private double ventaDiaria;
	private double tipoCambioSBS;
	
}
