package com.abaco.ageneral;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;

public @Data class ERepresentanteLegal{
	private int codigoOrden;
	private int codigoAccion;
	
	private int codigoTipoCliente;
	private int codigoCliente;
	private int codigoRepresentante;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String documento;
	private String ruc;
	private String nombreCorte;
	private String nombreLargo;
	private String nominativo;
	
	private String direccionComercial;
	private String codigoPostalComercial;
	private String telefonoComercial;
	private String faxComercial;
	private String estadoProvinciaComercial;
	private String ciudadComercial;
	private int codigoUbigeoComercial;

	private String direccionDomiciliaria;
	private String codigoPostalDomiciliaria;
	private String telefonoDomiciliaria;
	private String faxDomiciliaria;
	private String estadoProvinciaDomiciliaria;
	private String ciudadDomiciliaria;
	private int codigoUbigeoDomiciliaria;

	private String direccionCorrespondencia;
	private String codigoPostalCorrespondencia;
	private String telefonoCorrespondencia;
	private String faxCorrespondencia;
	private String estadoProvinciaCorrespondencia;
	private String ciudadCorrespondencia;
	private int codigoUbigeoCorrespondencia;

	//Datos Detalle
	private String codigoPaisNacionalidad;
	private int codigoPaisNacimiento;
	private int codigoCondicionTributaria;
	private String codigoGenero;
	private String codigoEstadoCivil;
	private int codigoNivelInstruccion;
	private int codigoProfesion;
	private Date fechaIngreso;
	private Date fechaNacimiento;
	//private int codigoPaisNacimiento;
	private int codigoUbigeoNacimiento;
	private String descripcionComunidad;
	private Date fechaRetiro;
	
	private String centroTrabajo;
	private String direccionLaboral;
	private String cargoLaboral;
	private String telefonoDirecto;
	private String telefonoCentral;
	private int anexo;
	private String fax;
	
	//private String inscripcionPoder;
	//private int codigoResidente;
	
	//Datos Conyugue
	private String nombreConyugue;
	private String codigoTipoDocumentoConyugue;
	private String documentoConyugue;
	private int codigoNacionalidadConyugue;//*
	
	//Datos Poder
	private int relacion1;
	private int relacion2;
	private int tipoVinculacion;
	
	private String observacion;
	private String numeroFichaPoder;
	private String registroPoder;
	private String inscripcionPoder1; //Cabecera
	private String inscripcionPoder2; //Pie
	private int indicadorFirma;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	/*
	private Date fechaRegistro;
	private EUsuario usuarioRegistro;
	*/
	
	/*
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String primerNombre;
	private String segundoNombre;
	private String denomicacion;
	private String email1;
	private String email2;
	
	private String registro;
	private String sede;
	private Date fechaRegistro;
	private String asientoPartidaRegistral;
	private int paginaPartidaRegistral;
	
	private String nombreLargo2;
	private int codigoTipoCliente2;
	private String documento2;
	private int codigoZonaDireccion;
	private String celular;
	private int indicadorPEP;
	
	private int codigoPaisNacionalidad;
	private int codigoPaisResidencia;
	private int indicadorLaboraEmpresa;
	private int indicadorRepresentantePrincipal;
	*/
	
	private String apellidoPaternoRepresentante;
	private String apellidoMaternoRepresentante;
	private String nombreRepresentante;
	private String razonSocialRepresentante;
	/*Ç
	private int codigoOcupacion;
	private String codigoTipoDocumento;
	private String documento;
	private int tipo;
	*/
	
	private String documentoRelacion;
	
	//Extras
	private String descripcionIndicadorFirma;
	private List<EFacultadPoder> lstFacultadPoder;
	
	private int codigoDepartamentoComercial;
	private int codigoProvinciaComercial;
	private int codigoDistritoComercial;
	private int codigoDepartamentoDomiciliaria;
	private int codigoProvinciaDomiciliaria;
	private int codigoDistritoDomiciliaria;
	private int codigoDepartamentoCorrespondencia;
	private int codigoProvinciaCorrespondencia;
	private int codigoDistritoCorrespondencia;
}
