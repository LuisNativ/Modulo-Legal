package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.ageneral.EFacultadPoder;
import com.abaco.ageneral.EOperacionDocumento;
import com.abaco.ageneral.ERepresentanteLegal;
import com.abaco.entidad.EUsuario;


public @Data class EOperacionLevantamientoGarantia {
	private int codigoCliente;
	private int codigoServicio;
	private long codigoGarantia;
	private int codigoMoneda;
	
	private int codigoEstadoSolicitud;
	private int codigoEstadoDocumento;
	
	private int codigoUbicacionOrigen;
	private String nombreUsuarioOrigen;
	private int codigoUbicacionInicio;
	private String nombreUsuarioInicio;
	private int codigoUbicacionEvaluacion;
	private String nombreUsuarioEvaluacion;
	private int codigoUbicacionRevision;
	private String nombreUsuarioRevision;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
}
