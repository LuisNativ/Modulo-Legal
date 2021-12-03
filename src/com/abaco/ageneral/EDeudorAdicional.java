package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EDeudorAdicional {
	private int codigoCliente;
	private double montoLimite;
	private double montoAdicional;
	private Date fechaVencimiento;
	private int codigoTipoPago;
	private int codigoFormaPago;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}
