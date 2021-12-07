package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOperacionSolicitud extends ESolicitud{	
	private int codigoTipoEvaluacion;
	private int codigoSeguimiento;
	
	private String codigoTipoPersona;
	private long codigoSolicitudCredito;
	private long codigoGarantia;
	private long codigoCreditoIndirecto;
	
	//private long codigoSolicitudRevision;
	
	private int codigoTipoEnvioRevision;
	private int codigoAreaRevision;
	private int codigoUsuarioRevision;
	
	private int codigoUsuarioAutorizacion;
	private String observacionAutorizacion;
	private Date fechaAutorizacion;
	private String horaAutorizacion;
	
	private List<EOperacionDocumento> lstOperacionDocumento;
	private List<EOperacionDocumentoRevision> lstOperacionDocumentoRevision;
	
	//Extras
	//private int indicadorConfirmar;
	private String abreviacionTipoCliente;
	private String descripcionTipoCliente;
	private String descripcionTipoEvaluacion;
	private String nombreUsuarioAutorizacion;
	private String abreviacionTipoProducto;
}
