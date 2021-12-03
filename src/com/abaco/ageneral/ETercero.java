package com.abaco.ageneral;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EUsuario;

import lombok.AllArgsConstructor;
import lombok.Data;

public @Data class ETercero{
	private int codigoCliente;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String documento;
	private String codigoTipoDocumento2;
	private String documento2;
	private String ruc;
	private String ruc2;
	private String nombreCorte;
	private String nombreLargo;
	private String siglas;
	
	private String direccion;
	private int codigoUbigeo;
	private int codigoZonaGeografica;
	private String direccionPostal;
	private int codigoUbigeoPostal;
	private int codigoZonaGeograficaPostal;
	
	private String telefono1;
	private String telefono2;
	private String anexo;
	private String fax;
	
	private Date fechaApertura;
	private Date fechaModificacion;
	
	private String codigoComunidad;
	private int codigoBCR;
	private int codigoSBS;
	private String codigoCIIU;
	private String codigoBanca;
	private int tipoProveedor;
	private int tipoAceptante;
	
	private int codigoGrupoEconomico;
	
	private Date fechaRegistro;
	//private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
 	//Datos Adicionales para los Avales
	private String descripcionTipoPersona; //Clase de Persona
 	private String sucursalApertura;
	private String descripcionComunidad;
	
	//Adicional 
	private String email1;
	private String email2;
	private String codigoEstadoCivil;
	private String codigoSexo;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private String nombreSuperLargo;
}
