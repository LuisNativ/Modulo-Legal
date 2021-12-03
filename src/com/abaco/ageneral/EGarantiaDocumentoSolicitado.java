package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EGarantiaDocumentoSolicitado {
	private long numeroSolicitud;
	
	private int codigoTipoProducto;
	private int codigoTipoDocumento;
	private int numeroLineaDocumento;
	
	private String descripcionDocumentoSolicitado;
	private String codigoTipoOrigenSolicitado;
	private String indicadorTraido;
	private String descripcionDocumentoTraido;
	private String codigoTipoOrigenTraido;
	
	private Date fechaTraido;
	
	private Date fechaIngreso;
	private String horaIngreso;
	private EUsuario usuarioIngreso;
	
	private Date fechaRegistro;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String descripcionTipoProducto;
	private String descripcionTipoDocumento;
	private String descripcionTipoOrigenSolicitado;
}
