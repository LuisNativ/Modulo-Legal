package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ECredito {
	private long numeroSolicitud;
	private long numeroPagare;
	private int codigoTipoCliente;
	private int codigoCliente;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String numeroDocumento;
	private String nombreCorto;
	private String nombreLargo;
	
	private int codigoMoneda;
	private double montoDesembolso;
	private int codigoProducto;
	private int codigoTipoProducto;
	private int codigoTipoCredito;
	private int numeroCuotas;
	private int frecuencia;
	private int primerVencimiento;
	private int tipoTasa;
	private int tasaInteres;
	
	private Date fechaDesembolso;
	private Date fechaCancelacion;
	
	private EUsuario usuarioRegistro;
	
	//Extras
	private double totalDesembolso;
	private Date ultimoDesembolso;
	
	private String abreviacionTipoCliente;
	private String abreviacionTipoPersona;
	private String abreviacionProducto;
	private String descripcionProducto;
	private String descripcionMoneda;
	private String descripcionTipoCredito;
	private String abreviacionMoneda;
}
