package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class ERepresentanteLegalInforme {

	private int codigoOrden;
	private long numeroSolicitud;
	private int codigoCliente;
	private int codigoTipoCliente;
	private int codigoRepresentante;
	private String codigoTipoPersona;
	private String codigoTipoDocumento;
	private String documento;
	private String ruc;
	private String nombreCorte;
	private String nombreLargo;
	
	private int codigoResidente;
	private int indicadorFirma;
	
	private String descripcionIndicadorFirma;
	private ERepresentanteLegal representanteLegal;
}
