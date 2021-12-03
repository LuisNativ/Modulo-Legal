package com.abaco.entidad;

import java.util.List;

public class EUsuario {

	private List<EPermiso> lstPermisos;
	private List<EMenu> lstMenu;
	private int idUsuario;
	private String nombreUsuario;
	private String contrasena;
	private long codigoCliente;
	private EUsuarioDetalle usuarioDetalle;
	private String nombreCompleto;
	private String idUsuarioSiaf;
	private List<ERol> listaRoles;
	private ERol rolActivo;
	private String nombreUsuarioSIAF;
	private String nombreAplicacion;
	private String nombreconyuge;
	private String correo;
	private int codigoArea;
	private int codigoAutonomia;
	
	public List<EPermiso> getLstPermisos() {
		return lstPermisos;
	}
	
	public void setLstPermisos(List<EPermiso> lstPermisos) {
		this.lstPermisos = lstPermisos;
	}
	
	public List<EMenu> getLstMenu() {
		return lstMenu;
	}

	public void setLstMenu(List<EMenu> lstMenu) {
	
		this.lstMenu = lstMenu;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public long getCodigoCliente() {
		return codigoCliente;
	}
	
	public void setCodigoCliente(long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public EUsuarioDetalle getUsuarioDetalle() {
		return usuarioDetalle;
	}

	public void setUsuarioDetalle(EUsuarioDetalle usuarioDetalle) {
		this.usuarioDetalle = usuarioDetalle;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getIdUsuarioSiaf() {
		return idUsuarioSiaf;
	}
	
	public void setIdUsuarioSiaf(String idUsuarioSiaf) {
		this.idUsuarioSiaf = idUsuarioSiaf;
	}
	
	public List<ERol> getListaRoles() {
		return listaRoles;
	}
	
	public void setListaRoles(List<ERol> listaRoles) {
		this.listaRoles = listaRoles;
	}
	
	public ERol getRolActivo() {
		return rolActivo;
	}
	
	public void setRolActivo(ERol rolActivo) {
		this.rolActivo = rolActivo;
	}
	
	public String getNombreUsuarioSIAF() {
		return nombreUsuarioSIAF;
	}
	
	public void setNombreUsuarioSIAF(String nombreUsuarioSIAF) {
		this.nombreUsuarioSIAF = nombreUsuarioSIAF;
	}
	
	public String getNombreAplicacion() {
		return nombreAplicacion;
	}
	
	public void setNombreAplicacion(String nombreAplicacion) {
		this.nombreAplicacion = nombreAplicacion;
	}
	
	public String getNombreconyuge() {
		return nombreconyuge;
	}
	
	public void setNombreconyuge(String nombreconyuge) {
		this.nombreconyuge = nombreconyuge;	
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public int getCodigoArea() {
		return codigoArea;
	}

	public void setCodigoArea(int codigoArea) {
		this.codigoArea = codigoArea;
	}

	public int getCodigoAutonomia() {
		return codigoAutonomia;
	}

	public void setCodigoAutonomia(int codigoAutonomia) {
		this.codigoAutonomia = codigoAutonomia;
	}
}
