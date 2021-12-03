package com.abaco.entidad;

public class EUsuarioDetalle {

	private int idUsuario;
	private String razonSocial;
	private String ruc;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private EDocumentoIdentidad documentoEntidad;
	private EDireccion direccion;
	private String telefono;
	private String anexo;
	private String celular;
	private String email;
	private int codigoSocio;
	private String codigoAutorizacion;

	public int getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getRuc() {
		return ruc;
	}
	
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public EDocumentoIdentidad getDocumentoEntidad() {
		return documentoEntidad;
	}
	
	public void setDocumentoEntidad(EDocumentoIdentidad documentoEntidad) {
		this.documentoEntidad = documentoEntidad;
	}
	
	public EDireccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(EDireccion direccion) {
		this.direccion = direccion;
	
	}

	public String getTelefono() {	
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getAnexo() {
		return anexo;
	}
	
	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getCodigoSocio() {
		return codigoSocio;
	}
	
	public void setCodigoSocio(int codigoSocio) {
		this.codigoSocio = codigoSocio;
	}
	
	public String getCodigoAutorizacion() {
		return codigoAutorizacion;
	}
	
	public void setCodigoAutorizacion(String codigoAutorizacion) {
		this.codigoAutorizacion = codigoAutorizacion;
	}	
}
