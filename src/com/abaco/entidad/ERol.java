package com.abaco.entidad;

public class ERol {

	private int idRol;
	private String nombre;
	private String descripcion;
	private int idRolSupervisor;
	private int flagObliga;

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdRolSupervisor() {
		return idRolSupervisor;
	}

	public void setIdRolSupervisor(int idRolSupervisor) {
		this.idRolSupervisor = idRolSupervisor;
	}

	public int getFlagObliga() {
		return flagObliga;
	}

	public void setFlagObliga(int flagObliga) {
		this.flagObliga = flagObliga;
	}
}
