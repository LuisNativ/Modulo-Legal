package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EGarantiaCreditoRelacionado {
	/* Correspondiente a la tabla F7409 */
	private int codigoCliente;
	private int codigoServicio;
	private long codigoGarantia;
	private int numeroOperacion;
}
