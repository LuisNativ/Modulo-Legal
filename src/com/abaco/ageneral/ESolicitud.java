package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ESolicitud implements Serializable{
	private long codigoSolicitud;
	private int codigoNivel;
	private int codigoEstado;
	private int codigoServicio;
	private int codigoAutorizaJefe;
	private int numeroMensaje;
	private int numeroRevision;
	
	private int codigoClientePersona;
	private int codigoTipoClientePersona;
	private String codigoTipoDocumentoPersona;
	private String numeroDocumentoPersona;
	private String nombrePersona;
	
	private int codigoAreaEmisorOrigen;
	private int codigoEmisorOrigen; //codigoUsuarioEmisorOrigen
	private String descripcionEmisorOrigen; //abreviacionUsuarioEmisorOrigen
	
	private int codigoAreaEmisor;
	private int codigoEmisor; //codigoUsuarioEmisor
	private String descripcionEmisor; //abreviacionUsuarioEmisor
	
	private int codigoTipoEnvio;
	private int codigoAreaReceptor;
	private int codigoReceptor; //codigoUsuarioReceptor
	private String descripcionReceptor; //abreviacionUsuarioReceptor
	
	private String descripcionAsunto;
	private int codigoUsuarioEvaluacion;
	private int indicadorSesion;
	//private int indicadorSolicitud;
	private int indicadorDigitalizacion;
	private int codigoUsuarioEnvioDigitalizacion;
	private int codigoAreaEnvioDigitalizacion;
	private String descripcionMensajeDigitalizacion;
	private Date fechaOrigen;
	private Date horaOrigen;
	private Date fechaInicio;
	private Date horaInicio;
	private Date fechaFin;
	private Date horaFin;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	private List<EDocumentoCarga> lstDocumentoCarga;
	
	//Extras
	private int codigoMotivo;
	private String tiempoTranscurrido;
	private int diaTranscurrido;
	private int indicadorTemporal;
	private int indicadorEditar;
	private int indicadorVisualizar;
	/*
	private int indicadorEnviar;
	private int indicadorObservar;
	private int indicadorAutorizar;
	private int indicadorCerrar;
	private int indicadorDesaprobar;
	private int indicadorCancelar;
	*/
	private int indicadorPublicar;
	private int indicadorCancelarAtajo;
	private int indicadorEliminarAtajo;
	private int indicadorReasignarEmisor;
	private int indicadorReasignarReceptor;
	
	private String abreviacionAsunto;
	private String descripcionNivel;
	private String descripcionEstado;
	private String descripcionEstadoAutorizacion;
	private String descripcionTipoEnvio;
	private String descripcionAreaEmisorOrigen;
	private String descripcionAreaEmisor;
	private String descripcionAbreviacionEmisorOrigen; //abreviacionUsuarioEmisorOrigen
	private String descripcionAbreviacionEmisor; //abreviacionUsuarioEmisor
	private String descripcionUsuarioEnvioDigitalizacion; //abreviacionUsuarioEnvioDigitalizacion
	private String descripcionIndicadorDigitalizacion;
	
	private String descripcionUsuarioEvaluacion; //abreviacionUsuarioEvaluacion
	private String descripcionUsuarioSesion; //abreviacionUsuarioSesion
	private String descripcionMensaje;
	private String descripcionAdicionalMotivo;
	
	//Parametros para Buscar
	private Date fechaInicioDesde;
	private Date fechaInicioHasta;
	
	//Adicional
	private String tiempoTranscurridoAJF;
	private int diaTranscurridoAJF;
}
