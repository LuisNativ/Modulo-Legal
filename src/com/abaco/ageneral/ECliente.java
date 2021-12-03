package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class ECliente {
	private int codigoCliente;
	private int codigoReservado;
	private int codigoClienteTemporal;
	private int codigoTipoCliente;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String documento;
	private String codigoTipoDocumento2;
	private String documento2;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String nombreCorto;
	private String nombreLargo;
	private String nombreLargo2;
	private String siglaComercial;
	private String ruc;
	private String rucAntiguo;
	
	private String direccion;
	private String referenciaDireccion;
	private int codigoUbigeo;
	private String codigoPostal;
	private int zonaGeografica;
	private String telefono;
	private String telefono2;
	private int anexo;
	private String fax;
	private String direccionPostal;
	private int codigoUbigeoPostal;
	private int zonaGeograficaPostal;
	
	private String direccion2; //Verificar con direccionPostal 
	private int codigoUbigeo2; //Verificar con codigoUbigeoPostal 
	private Date fechaApertura;
	private int sucursalApertura;
	private int servicioApertura;
	private Date fechaUltTransaccion;
	private String comunidad;
	private int motivoRetiro;
	
	private int codigoCCIU; 
	private String codigoDivision;
	private String codigoBanca;
	private String ejecutivoControl;
	private int clasificacionEspecial;
	private int tipoRelacion;
	private int tipoProveedor;
	private int tipoAceptante;
	private int informacionCompleta;
	private String nombreSuperLargo;
	//PN
	private Date fechaNacimiento;
	//private String nombreConyuge;	
	//private String codigoTipoDocumentoConyugue;
	//private String documentoConyugue;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String descripcionTipoCliente;
	private String codigoEstadoCivil;
	private String descripcionEstadoCivil;
	private String codigoGenero;
	private String descripcionGenero;
	private int codigoProfesion;
	private String descripcionProfesion;
	private String descripcionTipoDocumento;
	
	private String codigoTipoDocumentoConyugue;
	private String documentoConyugue;
	private String apellidoPaternoConyugue;
	private String apellidoMaternoConyugue;
	private String nombreConyugue;
	private String nombreSuperLargoConyugue;
}
