package com.abaco.ageneral;

import lombok.Data;

public @Data class ESaldoServicio {

	private int claseServicio;
	private int codigoGrupoEconomico;
	private int codigoCliente;
	private int codigoServicio;
	private int codigoMoneda;
	private double montoSaldo;
	private double montoAcumulado;
	private double montoTotalComision;
	private double montoTotalInteres;
	private double montoTotalCuenta;
}
