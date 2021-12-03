package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ERevisionSolicitud extends ESolicitud{
	private int codigoPersonaRelacion;
	private int indicadorSolicitud;
	/*
	private int indicadorReenviar;
	private int indicadorDescartar;
	*/
	private List<ERevisionDocumento> lstRevisionDocumento;
}
