package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.ageneral.EFacultadPoder;
import com.abaco.ageneral.EOperacionDocumento;
import com.abaco.ageneral.ERepresentanteLegal;
import com.abaco.entidad.EUsuario;


public @Data class EEvaluacionSolicitudCreditoLegal {

	private long numeroSolicitud;
	
	//Datos Persona
	private int codigoCliente;
	private int codigoTipoCliente;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String numeroDocumento;
	private String ruc;
	private String nombreLargo;
	private String nombreCorto;
	
	//Datos Conyugue
	private String codigoTipoDocumentoConyugue;
	private String documentoConyugue;
	private String nombreLargoConyuge;
	
	//Datos Direcciones
	private int codigoUbigeoReal;
	private String direccionReal;
	private int codigoUbigeoContractual;
	private String direccionContractual;
	
	//Datos PJ
	private String codigoTipoPersonaJuridica;
	private double montoCapitalSocialRegistroPublicos;
	private double montoCapitalSocialActual;
	private String codigoFacultadOperar;
	private String codigoTipoSuscripcionPago;
	private int numeroSuscripcionPago;
	private int numeroAcciones;
	private String indicadorAvalarTercero;
	private String indicadorGrabarBien;
	private String descripcionAvalarTercero;
	private String observacionAvalarTercero;
	private String observacionGrabarBien;
	private String observacionConstanciaSocial;
	private String observacionSolicitud;
	
	//Datos de Evaluacion
	private String codigoEstadoActual;
	private Date fechaPrimeraEvaluacion;
	private String horaPrimeraEvaluacion;
	private String usuarioPrimeraEvaluacion;
	
	private Date fechaRegistroLegal;
	private String horaRegistroLegal;
	private String usuarioRegistroLegal;
	
	private Date fechaUltimaRevision;
	private String horaUltimaRevision;
	private String usuarioUltimaRevision;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String abreviacionTipoPersona;
	private String descripcionTipoCliente;
	private String descripcionEstadoActual;
	private String apellidoPaternoConyugue;
	private String apellidoMaternoConyugue;
	private String nombreConyugue;
	
	private int codigoAreaRegistroLegal;
	private int codigoUsuarioRegistroLegal;
	private String descripcionUsuarioRegistroLegal;
	
	private String observacionLegal;
	private String observacionNegocios;
	private String usuarioEvaluacionLegal;
	
	private EInformeLegalAdicional informeLegalAdicional;
	
	private List<ESuscripcion> lstSuscripcion;
	private List<ERepresentanteLegal> lstRepresentanteLegal;
	private List<EDeudor> lstDeudor;
	private List<EDeudor> lstDeudorRecycle;
	private List<ESolicitudLogMovimiento> lstSolicitudLogMovimiento;
}
