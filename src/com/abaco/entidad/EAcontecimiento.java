package com.abaco.entidad;

import java.util.Date;

import com.abaco.negocio.util.UFuncionesGenerales;

public class EAcontecimiento {

	private int idGravedad;
	private String desGravedad;
	private String desMotivo;
	private String mensaje;
	private Date fechaRegistro;
	private Date horaRegistro;
	private int nroLinea;
	private int proceso;
	private boolean esEditableAcontecimiento;
	private String nombreUsuario;
	

	public int getIdGravedad() {
		return idGravedad;
	}

	public String getDesGravedad() {
		return desGravedad;
	}

	public void setDesGravedad(String desGravedad) {
		this.desGravedad = desGravedad;
	}

	public void setIdGravedad(int idGravedad) {
		this.idGravedad = idGravedad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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

	public int getNroLinea() {
		return nroLinea;
	}

	public void setNroLinea(int nroLinea) {
		this.nroLinea = nroLinea;
	}

	public String getHoraRegistroCadena() {
		return UFuncionesGenerales.convertirFechaACadena(horaRegistro, "HH:mm:ss");
	}

	public String getDesMotivo() {
		return desMotivo;
	}

	public void setDesMotivo(String desMotivo) {
		this.desMotivo = desMotivo;
	}

	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}

	public boolean isEsEditableAcontecimiento() {
		return esEditableAcontecimiento;
	}

	public void setEsEditableAcontecimiento(boolean esEditableAcontecimiento) {
		this.esEditableAcontecimiento = esEditableAcontecimiento;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	
}
