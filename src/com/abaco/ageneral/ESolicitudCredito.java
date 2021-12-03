package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ESolicitudCredito {
	private long numeroSolicitud;
	private int codigoTipoCliente;
	private int codigoCliente;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String numeroDocumento;
	private String nombreCorto;
	
	private int codigoServicio;
	private int codigoMonedaSolicitud;
	
	private double montoSolicitud;
	private double tasaSolicitud;
	private int codigoTipoTasaSolicitud;
	private int plazoSolicitud;
	private double montoGarantiaSolesSolicitud;
	private double montoGarantiaDolaresSolicitud;
	
	private double montoAprobado;
	private double tasaAprobado;
	private int codigoTipoTasaAprobado;
	private int plazoAprobado;
	private double montoGarantiaSolesAprobado;
	private double montoGarantiaDolaresAprobado;
	
	private double deudaSoles;
	private double deudaDolares;
	private int codigoTipoProducto;
	private int codigoTipoCredito;
	private int planPagos;
	private int numeroCuotas;
	private int codigoFrecuencia;
	private int primerVencimiento;
	private double tasaInteres;
	private int indicadorCobroSeguro;
	
	private int referenciaLineaCredito;
	private int codigoModalidadLineaCredito;
	
	
	private String nombreUsuarioEnvio;
	private String nombreUsuarioPromotor;
	
	private String objeto;
	private String criterios;
	
	private Date fechaOrigen;
	private Date horaOrigen;
	private Date fechaInicio;
	private Date horaInicio;
	
	private Date fechaVencimiento;
	private Date fechaDesembolso;
	private Date fechaCancelacion;
	private Date fechaRevision;
	private String horaRevision;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String nombreLargo;
	
	private String abreviacionTipoPersona;
	private String abreviacionTipoProducto;
	
	private String descripcionServicio;
	private String descripcionTipoCliente;
	private String descripcionTipoProducto;
	private String descripcionMonedaSolicitud;
	private String descripcionTipoDocumento;
	private String descripcionModalidadLineaCredito;
	private String descripcionFrecuencia;
	
	private int codigoAreaEnvio;
	private int codigoUsuarioEnvio;
	private String descripcionUsuarioEnvio;
	
	private int codigoAreaPromotor;
	private int codigoUsuarioPromotor;
	private String descripcionUsuarioPromotor;
	
	//Adicionales
	private int nroRevision;
	private int codigoMonedaSolicitada;
	private String descripcionMonedaSolicitada;
	private int codigoTipoDocumentoFactoring;
	private String descripcionTipoDocumentoFactoring;
	private double tasaComisionFlatDesembolso;
	private String cobroComisionDesembolso;
	private String fecVenc;
	private String descripcionTipoTasaSolicitud;
	private String deudorGirador;
	private String tipoGarantia; //Nuevo Contrato
	private String usuarioRevision;
	
	//Carta Fianza 
	private int codigoTipoDocumentoCartaFianza;
	private String descripcionTipoDocumentoCartaFianza;
	private int codigoClaseDocumentoCartaFianza;
	private String descripcionClaseDocumentoCartaFianza;
	private int codigoAfianzado;
	private String descripcionAfianzado;
	private int codigoBeneficiario;
	private String descripcionBeneficiario;
	private int codigoBancoFianza;
	private String descripcionBancoFianza;
	private int codigoMonedaFianza;
	private String descripcionMonedaFianza;
	private int numeroFianza;
	private int cuentaCargoFianza;
	private double montoFianza;
	private Date fechaVigenciaFianza;
	private Date fechaVencimientoFianza;	
}
