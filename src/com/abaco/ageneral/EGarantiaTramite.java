package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EGarantiaTramite {
	/* Correspondiente a la tabla F9212 */
	private int nroAsiento;
	private int codigoServicio;
	private long codigoGarantia;
	private int codigoMoneda;
	private int codigoEtapaTramite;
	private int numeroHojaIngresoLegal;
	
	private Date fechaIngreso;
	private int evaluacionDocumento;
	private Date fechaElaboracionContrato;
	private String usuarioElaboracionContrato;
	private Date fechaFirmaContrato;
	private Date fechaIngresoNotaria;
	private Date fechaLegalizacionFirma;
	private int numeroKardex;
	private int codigoNotario;
	private Date fechaIngresoRegistro;
	private Date fechaVigenciaAsiento;
	private String observacion1;
	private String observacion2;
	private Date fechaObservacion;
	private Date fechaTacha;
	private Date fechaInscripcion;
	private String fichaInscripcion;
	private String tomoInscripcion;
	private String ciudadInscripcion;
	
	private String descripcionObservacionBloqueo1;
	private String descripcionObservacionBloqueo2;
	private Date fechaIngresoRegistroB;
	private Date fechaVigenciaAsientoB;
	private Date fechaObservacionB;
	private Date fechaTachaB;
	private Date fechaInscripcionB;
	private String fichaInscripcionB;
	private String tomoInscripcionB;
	private String ciudadInscripcionB;

	//Adicionl
	private String descripcionNotario;
	private Date fechaRegistro;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
	private int tipoGarantia;
	private int secuenciaHistorica;
	private String tituloA;
	private String tituloB;
	
	
	//Para Historico
	private boolean validarfechaIngreso;
	private boolean validarObservacion1;
	private boolean validarObservacion2;
	
	
}
