package com.abaco.ageneral;

import java.util.Date;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class EAsignacionContratoGarantia {

	private int numeroContrato;
	private int numeroContratoOtro;
	private int secuenciaAsignacion;
	private int tipoPrenda;
	private int servicioBase;
	private int codigoMonedaBaseAsignacion;
	private int servicio;
	private int codigoMoneda;
	private long numeroOperacion;
	private long numeroPlanilla;
	private int numeroFianza;
	private int numeroOperacionTanomoshi;
	private int numeroLista;
	private int numeroDocumentoDPF;
	private int numeroConsecutivoDPF;
	private int numeroCuenta;
	private double montoImporteDocMonedaOrigen;
	private double tipoCambio;
	private double montoImporteCubierto;
	private double montoSaldoGarantia;
	private double porcentajeCubiertoGarantia;
	private double montoImporteDocMonedaOrigen2;
	private double montoImporteCubierto2;
	private double montoSaldoGarantia2;
	private int codigoCliente;
	private String nombreCliente;
	private String observacion1;
	private String observacion2;
	private String estadoRegistro;
	private String tipoRegistro;
	private Date fechaRegistro;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
	
	
	//Adicionales
	private String abreviacionMoneda;
	private String descripcionMoneda;
	private Double montoSaldoCredito;
	private Double montoCoberturado;
	private Double porcentajeCoberturado;
	private String descripcionEstadoCredito;

	
}
