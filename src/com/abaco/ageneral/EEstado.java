package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EEstado implements Serializable{
	private int codigoEstado;
	private String descripcionEstado;
	private String descripcionEstadoAccion;
}
