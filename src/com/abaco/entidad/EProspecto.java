package com.abaco.entidad;

import java.util.Date;
import java.util.List;

import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UFuncionesGenerales;

public class EProspecto {

	private long codigo;
	private EDocumentoIdentidad documento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String razonSocial;
	private String telefono;
	private String correo;
	private int estadoRegistro;
	private int codigoEstadoProceso;
	private String desEstadoProceso;
	private List<EContactoProspecto> contactos;
	private Date fechaRegistro;
	private Date horaRegistro;
	private String usuarioRegistro;
	private boolean asignado;
	private int tipoContribuyente;
	private String desTipoContribuyente;
	private ERptaEvaluar rptaEvalLista;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public EDocumentoIdentidad getDocumento() {
		return documento;
	}

	public void setDocumento(EDocumentoIdentidad documento) {
		this.documento = documento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public List<EContactoProspecto> getContactos() {
		return contactos;
	}

	public void setContactos(List<EContactoProspecto> contactos) {
		this.contactos = contactos;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public int getEstadoRegistro() {
		return estadoRegistro;
	}

	public void setEstadoRegistro(int estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	public int getCodigoEstadoProceso() {
		return codigoEstadoProceso;
	}

	public void setCodigoEstadoProceso(int codigoEstadoProceso) {
		this.codigoEstadoProceso = codigoEstadoProceso;
	}

	public String getDesEstadoProceso() {
		return desEstadoProceso;
	}

	public void setDesEstadoProceso(String desEstadoProceso) {
		this.desEstadoProceso = desEstadoProceso;
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

	public String getNombreCompleto() {

		String nombreCompleto = "";
		if (documento != null && documento.getTipoDocumento() != null
				&& !UFuncionesGenerales.revisaCadena(documento.getTipoDocumento().getCodigo()).equals("")) {

			if (UFuncionesGenerales.revisaCadena(documento.getTipoDocumento().getCodigo()).equals(UTipoDocumento.RUC)) {
				nombreCompleto = razonSocial;
			} else {
				if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
						&& UFuncionesGenerales.revisaCadena(apellidoMaterno).length() > 0
						&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
					nombreCompleto = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
							.concat(UFuncionesGenerales.revisaCadena(apellidoMaterno)).concat(" ")
							.concat(UFuncionesGenerales.revisaCadena(nombres));

				} else if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
						&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
					nombreCompleto = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
							.concat(UFuncionesGenerales.revisaCadena(nombres));

				}
			}
		}

		return nombreCompleto;
	}

	public boolean isAsignado() {
		return asignado;
	}

	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}

	public int getTipoContribuyente() {
		return tipoContribuyente;
	}

	public void setTipoContribuyente(int tipoContribuyente) {
		this.tipoContribuyente = tipoContribuyente;
	}

	public String getDesTipoContribuyente() {
		return desTipoContribuyente;
	}

	public void setDesTipoContribuyente(String desTipoContribuyente) {
		this.desTipoContribuyente = desTipoContribuyente;
	}

	public ERptaEvaluar getRptaEvalLista() {
		return rptaEvalLista;
	}

	public void setRptaEvalLista(ERptaEvaluar rptaEvalLista) {
		this.rptaEvalLista = rptaEvalLista;
	}

}
