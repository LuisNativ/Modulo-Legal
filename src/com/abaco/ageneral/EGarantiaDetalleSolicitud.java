package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EGarantiaDetalleSolicitud {
	/* Correspondiente a la tabla F7363 */
	private long numeroSolicitud;
	private int secuenciaGarantia;
	
	private int codigoTipoGarantia;
	private String descripcionGarantia;
	private double montoGarantia;
	private String marca;
	private String modelo;
	private String serie;
	private String motor;
	private int cantidad;
	private int codigoTipoPrenda; //Maquinaria
	private String direccion;
	private int codigoUbigeo;
	private double areaTerreno;
	private double areaConstruida;
	private int numeroPisos;
	private String usoPredio;
	private int codigoTipoPrenda2; //Predios
	
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	//Extras
	private String descripcionTipoGarantia;
	private String descripcionTipoPrenda;
}
