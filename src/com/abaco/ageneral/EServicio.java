package com.abaco.ageneral;
import lombok.Data;

public @Data class EServicio {

	private int codigoProducto;
	private int codigoSubProducto;
	private int codigoMoneda;
	private String abreviacion;
	private String descripcion;
	private int codigoServicio;

	//Extras
	private String descripcionMoneda;
}
