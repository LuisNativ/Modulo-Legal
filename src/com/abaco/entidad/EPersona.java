package com.abaco.entidad;

import java.util.Date;


public class EPersona {

	private int codigo;
	private String nombre;
	private EDocumentoIdentidad documentoIdentidad;
	private String nombreCorte;	
	private String nombreLargo;	
	private String nombreConyuge;	
	private String codigoClasePersona;
	private String codigoEstado;
	private String descripcionEstado;
	private EDireccion direccion;
	private Date fecNacimiento;
	private ETipoCliente tipoCliente;
	private String clasePersona;
	private Date fechaRegistro;
	private Date horaRegistro;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public EDocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}
	
	public void setDocumentoIdentidad(EDocumentoIdentidad documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}
	
	public String getNombreCorte() {
		return nombreCorte;
	}
	
	public void setNombreCorte(String nombreCorte) {
		this.nombreCorte = nombreCorte;
	}
	
	public String getNombreConyuge() {
		return nombreConyuge;
	}
	
	public void setNombreConyuge(String nombreConyuge) {
		this.nombreConyuge = nombreConyuge;
	}
	
	public String getCodigoClasePersona() {
		return codigoClasePersona;
	}
	
	public void setCodigoClasePersona(String codigoClasePersona) {
		this.codigoClasePersona = codigoClasePersona;
	}
	
	public String getCodigoEstado() {
		return codigoEstado;
	}
	
	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}
	
	public String getDescripcionEstado() {
		return descripcionEstado;
	}
	
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}
	
	public EDireccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(EDireccion direccion) {
		this.direccion = direccion;
	}
	
	public Date getFecNacimiento() {
		return fecNacimiento;
	}
	
	public void setFecNacimiento(Date fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}
	
	public ETipoCliente getTipoCliente() {
		return tipoCliente;
	}
	
	public void setTipoCliente(ETipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	
	public String getClasePersona() {
		return clasePersona;
	}

	public void setClasePersona(String clasePersona) {
		this.clasePersona = clasePersona;
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
}
