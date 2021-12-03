package com.abaco.entidad;

public class EDireccion {

	private EDepartamento departamento;
	private EProvincia provincia;
	private EDistrito distrito;
	private EZona zona;
	private String codigoPostal;
	private String idTipoDireccion;
	private String direccionComercial;
	private String telefono1;
	private String telefono2;
	private String anexo;
	private String fax;
	private String movil1;
	private String movil2;
	private int ubigeo;
	private String email1;
	private String email2;
	private String email3;

	public EDepartamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(EDepartamento departamento) {
		this.departamento = departamento;
	}

	public EProvincia getProvincia() {
		return provincia;
	}

	public void setProvincia(EProvincia provincia) {
		this.provincia = provincia;
	}

	public EDistrito getDistrito() {
		return distrito;
	}

	public void setDistrito(EDistrito distrito) {
		this.distrito = distrito;
	}

	public EZona getZona() {
		return zona;
	}

	public void setZona(EZona zona) {
		this.zona = zona;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getDireccionComercial() {
		return direccionComercial;
	}

	public void setDireccionComercial(String direccionComercial) {
		this.direccionComercial = direccionComercial;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;

	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public int getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(int ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getIdTipoDireccion() {
		return idTipoDireccion;
	}

	public void setIdTipoDireccion(String idTipoDireccion) {
		this.idTipoDireccion = idTipoDireccion;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getMovil1() {
		return movil1;
	}

	public void setMovil1(String movil1) {
		this.movil1 = movil1;
	}

	public String getMovil2() {
		return movil2;
	}

	public void setMovil2(String movil2) {
		this.movil2 = movil2;
	}

}
