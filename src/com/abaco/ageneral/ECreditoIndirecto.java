package com.abaco.ageneral;

import java.sql.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ECreditoIndirecto {
	private int codigoServicio;
	private int codigoMonedaFianza;
	private int numero;
	private int codigoProducto;
	private int codigoSubproducto;
	//clase
	//tipo
	//sucursal
	private int codigoAgenciaEmisor;
	private int codigoDepartamentoEmisor;
	private String nombreUsuarioEmisor;
	//funcionario
	//grupo economico
	
	private int codigoCliente;
	private String nombreCliente;
	private int codigoBeneficiario;
	private String nombreBeneficiario;
	private int codigoAfianzado;
	private String nombreAfianzado;
	
	private double montoCredito;
	private double montoFianza;
	private double montoHonramiento;
	private double montoGastoHonramiento;
	private double montoGastoBancoHonramiento;
	private double montoGarantizado;
	private double montoAcumuladoComision;
	
	private int codigoFormaComision;
	private int codigoEstado;
	private int codigoSituacion;
	
	private Date fechaIngreso;
	private Date fechaVigencia;
	private Date fechaImpresion;
	private Date fechaUltimoProrroga;
	private Date fechaVencimiento;
	private Date fechaCancelacion;
	private Date fechaRequerimiento;
	private Date fechaUltimoComision;
	
	private int codigoUnidadNegocios;
	private int codigoTipoBanca;
	private int codigoTipoSubBanca;
	
	/*
	private int numeroContrato;
	private int numeroRenovacion;
	private long codigoTramite;
	private int numeroPlazo;
	private String tipoPlazo;
	private EUsuario usuarioRegistro;
	*/
	
	//Extras
	private String descripcionServicio;
	private String descripcionMoneda;
}
