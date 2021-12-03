package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EGarantiaSolicitud {
	/* Correspondiente a la tabla F7325 */
	private long numeroSolicitud;
	private int numeroRevision;
	
	private int secuenciaGarantia;
	private int cuentaGarantia;
	private int documentoGarantia;
	private int numeroGarantiaReal; //Verificar si es codigo
	private int grupoTanomoshi;
	private int numeroListaTanomoshi;
	private int codigoTipoGarantia;
	private int codigoTipoGarantiaReal;
	private String descripcionTipoGarantiaOtro;
	private String descripcionGarantiaReal;
	private int codigoServicioGarantia;
	private long codigoGarantia;
	private int codigoMonedaGarantia;
	private double montoOrigenGarantia;
	private double tipoCambio;
	private double montoGarantia;
	//private double montoGarantizado;
	//private double montoEjecucion;
	//private int porcentajeGarantizado;
	
	//private Date fechaConstitucion;
	//private Date fechaVencimiento;
	//private Date fechaCancelacion;
	//private Date fechaDesembolso;
	
	//private int codigoInspector;
	//private int depositario;
	//private int almacen;

	private String placa;
	private String clase;
	private String marca;
	private String modelo;
	private String carroceria;
	private String motor;
	private String serie;
	private String color;
	private String conbustible;
	private int codigoClase;
	private int codigoMarca;
	private int codigoModelo;
	private String codigoCombustible;
	private String descripcionCombustible;
	private String codigoNivelRiesgo;
	private int anioFabricacion;
	private double montoValorizacion;
	private String indicadorNuevo;
	
	private Date fechaActualizacion;
	private Date horaActualizacion;
	private EUsuario usuarioActualizacion;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private int codigoCliente;
	private String numeroDocumento;
	private String nombreCorto;
	private String nombreLargo;
	
	private Date fechaRevision;
	private String abreviacionMonedaGarantia;
	private String descripcionMonedaGarantia;
	private String abreviacionMonedaSolicitud;
	private String descripcionMonedaSolicitud;
	private String descripcionTipoGarantia;
	private String descripcionTipoGarantiaReal;
	private String descripcionEstadoGarantia;
	private String descripcionCreditoRelacionado;
	private String descripcionServicio;
	private String descripcionGeneral;
	
	private int codigoEstadoGarantiaSolicitud;
	private String descripcionEstadoGarantiaSolicitud;
	private int codigoTipoProducto;
	private int codigoEstadoSolCredito;
	private String codigoEstadoRevision;
	private int codigoMonedaSolicitud;
	private int codigoNroIngresoGarantia; //GARANI
	
	
	//Adicional
	private String observacion;
	private double montoTasacion;
	private double montoGravamen;
	private double montoSolicitud; //Monto Solicitado de Credito
	private double montoValorRealizacion; //VRI
	private double porcentajeAsignado;
	private String usuarioCredito;
	private Date fechaCredito;
	private String usuarioLegal;
	private Date fechaLegal;
	private String poliza;
	private int correlativoPoliza;
	private int tipoPoliza;
	private Date fechaVencimientoPoliza;
	private double valorPoliza;
	private int codigoCiaSeguro;
	private String descripcionCiaSeguro;
	private int codigoEstadoEvaluacionLegal;
	private double saldoDisponible;
	private String tipoIngreso; //Manual/Automático
	private Date fechaComercial;
	private double montoComercial;
	
	//Atributos para gestion Levantamiento
	private int codigoEstadoLevantamiento;
	private String descripcionEstadoLevantamiento;
	private int codigoEstadoDocumento;
	private String descripcionEstadoDocumento;
	private int indicadorRevisar;
	
	
}
