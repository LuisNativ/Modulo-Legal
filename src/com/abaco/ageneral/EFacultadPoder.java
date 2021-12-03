package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EFacultadPoder {
	private int codigoOrden;
	private int codigoOrdenRepresentante;
	private int codigoTipoCliente;
	private int codigoCliente;
	private int codigoRepresentante;
	private int codigoFacultad;
	private int codigoOtorgamiento;
	private int codigoServicio;
	private int codigoMoneda;
	private int codigoTipoFirma;
	
	//Mamcomumado
	private int codigoTipoRelacion;
	private int codigoOrdenRelacion;
	private int codigoRepresentanteRelacion;
	
	private String observaciones;
	private Date fechaEmision;
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String descripcionTipoCliente;
	private String descripcionFacultad;
	private String descripcionOtorgamiento;
	private String descripcionServicio;
	private String descripcionTipoFirma;
	private String descripcionTipoRelacion;
	private String descripcionRepresentanteRelacion;
}
