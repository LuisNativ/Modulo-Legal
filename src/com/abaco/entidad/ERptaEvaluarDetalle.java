package com.abaco.entidad;

import java.util.Date;

public class ERptaEvaluarDetalle {

	private long codigoRptaEvaluar;
	private long codigoProspecto;
	private long numeroConsulta;
	private int numeroRegistro;
	private int prioridad;
	private String tipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private int codigoLista;
	private String nombreLista;
	private String codigoColor;
	private String fuente;
	private String delito;
	private String link;
	private int grupo;
	private Date fechaRegistro;
	private Date horaRegistro;
	private String usuarioRegistro;

	public long getCodigoRptaEvaluar() {
		return codigoRptaEvaluar;
	}

	public void setCodigoRptaEvaluar(long codigoRptaEvaluar) {
		this.codigoRptaEvaluar = codigoRptaEvaluar;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public int getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(int prioridad) {
		this.prioridad = prioridad;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public int getCodigoLista() {
		return codigoLista;
	}

	public void setCodigoLista(int codigoLista) {
		this.codigoLista = codigoLista;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getDelito() {
		return delito;
	}

	public void setDelito(String delito) {
		this.delito = delito;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getNombreLista() {
		return nombreLista;
	}

	public void setNombreLista(String nombreLista) {
		this.nombreLista = nombreLista;
	}

	public String getCodigoColor() {
		return codigoColor;
	}

	public void setCodigoColor(String codigoColor) {
		this.codigoColor = codigoColor;
	}

	public long getCodigoProspecto() {
		return codigoProspecto;
	}

	public void setCodigoProspecto(long codigoProspecto) {
		this.codigoProspecto = codigoProspecto;
	}

	public long getNumeroConsulta() {
		return numeroConsulta;
	}

	public void setNumeroConsulta(long numeroConsulta) {
		this.numeroConsulta = numeroConsulta;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	
}
