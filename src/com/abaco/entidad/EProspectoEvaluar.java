package com.abaco.entidad;

import java.util.Date;
import java.util.List;

public class EProspectoEvaluar {

	private long codigoEvaluacion;
	private EProspecto prospectoEvaluar;
	private List<ERptaEvaluar> respuestaEvaluarLista;
	private Date fechaRegistro;
	private Date horaRegistro;
	private String usuarioRegistro;

	public long getCodigoEvaluacion() {
		return codigoEvaluacion;
	}

	public void setCodigoEvaluacion(long codigoEvaluacion) {
		this.codigoEvaluacion = codigoEvaluacion;
	}

	public EProspecto getProspectoEvaluar() {
		return prospectoEvaluar;
	}

	public void setProspectoEvaluar(EProspecto prospectoEvaluar) {
		this.prospectoEvaluar = prospectoEvaluar;
	}

	public List<ERptaEvaluar> getRespuestaEvaluarLista() {
		return respuestaEvaluarLista;
	}

	public void setRespuestaEvaluarLista(List<ERptaEvaluar> respuestaEvaluarLista) {
		this.respuestaEvaluarLista = respuestaEvaluarLista;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getHoraRegistro() {
		return horaRegistro;
	}

	public void setHoraRegistro(Date horaRegistro) {
		this.horaRegistro = horaRegistro;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	
}
