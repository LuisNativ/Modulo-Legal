package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class EClienteConstitucionEmpresa {
	private int codigoCliente;
	private int codigoTipoEmpresa;
	
	private double montoPatrimonio;
	private int numeroEmpleados;
	private int numeroObreros;
	private Date fechaConstitucion;
	private Date fechaCierre;
	private int codigoCIIU;
	private int codigoNotario;
	private String descripcionNotario;
	private String inscripcionRegistroPublico;
	private double montoCapitalSuscrito;
	private double montoCapitalPagado;
	private int numeroAcciones;
	
	//Extras
	private String oficinaRegistral;
}
