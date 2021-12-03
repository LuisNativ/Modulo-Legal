package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.ageneral.EFacultadPoder;
import com.abaco.ageneral.EOperacionDocumento;
import com.abaco.ageneral.ERepresentanteLegal;
import com.abaco.entidad.EUsuario;


public @Data class EInformeLegalAdicional {
	private long numeroSolicitud;
	private int codigoCliente;
	private int codigoTipoCliente;
	private String codigoEstadoCivil;
	private String numeroPartida;
	private String oficinaRegistral;
	private int codigoTipoDuracionPartida;
	private String registroPartida;
	private String descripcionConstitucion;
	private Date fechaConstitucion;
	private int codigoNotario;
	private String descripcionNotario;
	private int numeroAcciones;
	private int codigoTipoValorSuscripcion;
	private double montoValorNominal;
	private String descripcionAporte;
	private String estatuto;
	private String descripcionPatrimonio;
	private int codigoTipoNumeracionEstatuto;
	private int numeracionEstatuto;
	private String asiento;
	private Date fechaPeriodoInicio;
	private Date fechaPeriodoVencimiento;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}
