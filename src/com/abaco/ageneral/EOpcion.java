package com.abaco.ageneral;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EOpcion implements Serializable{
	private int indicadorEnviar;
	
	//Revision
	private int indicadorReenviar;
	private int indicadorCerrar;
	private int indicadorDescartar;
	private int indicadorCancelar;
	private int indicadorAutorizar;
	private int indicadorSolicitarAutorizar;
	private int indicadorRechazarAutorizar;
	private int indicadorConfirmarAutorizar;
	private int indicadorConfirmarAutorizarCompletado;
	
	//Operacion
	private int indicadorGrabar;
	private int indicadorRechazarLevantamiento;
	private int indicadorConfirmarLevantamiento;
	private int indicadorLiberar;
	private int indicadorEntregar;
	private int indicadorConfirmarCartaFianza;
	private int indicadorEnviarCartaFianza;
}
