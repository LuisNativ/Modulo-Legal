package com.abaco.entidad;

import java.util.Date;
import java.util.List;

import com.abaco.negocio.util.UFuncionesGenerales;

public class ERptaEvaluar {

	private long codigoRptaEvaluar;
	private long codigoProspecto;
	private long numeroConsulta;
	private String nombreArchivo;
	private String rutaRepositorio;
	private int cantidadRegistrosEncontrado;
	private String observacion;
	private int tipoCliente;	
	private EBoletinado boletinado;
	private List<ERptaEvaluarDetalle> listas;
	private Date fechaRegistro;
	private Date horaRegistro;
	private String usuarioRegistro;


	public long getCodigoRptaEvaluar() {
		return codigoRptaEvaluar;
	}

	public void setCodigoRptaEvaluar(long codigoRptaEvaluar) {
		this.codigoRptaEvaluar = codigoRptaEvaluar;
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

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getRutaRepositorio() {
		return rutaRepositorio;
	}

	public void setRutaRepositorio(String rutaRepositorio) {
		this.rutaRepositorio = rutaRepositorio;
	}

	public int getCantidadRegistrosEncontrado() {
		return cantidadRegistrosEncontrado;
	}

	public void setCantidadRegistrosEncontrado(int cantidadRegistrosEncontrado) {
		this.cantidadRegistrosEncontrado = cantidadRegistrosEncontrado;
	}

	public List<ERptaEvaluarDetalle> getListas() {
		return listas;
	}

	public void setListas(List<ERptaEvaluarDetalle> listas) {
		this.listas = listas;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public EBoletinado getBoletinado() {
		return boletinado;
	}

	public void setBoletinado(EBoletinado boletinado) {
		this.boletinado = boletinado;
	}

	public int getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(int tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getHoraRegistroCadena() {
		return UFuncionesGenerales.convertirFechaACadena(horaRegistro, "HH:mm:ss");
	}
}
