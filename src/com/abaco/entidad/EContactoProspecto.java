package com.abaco.entidad;

import java.util.Date;

import com.abaco.negocio.util.UFuncionesGenerales;

public class EContactoProspecto {

	private EDocumentoIdentidad documentoContacto;
	private String apePatContacto;
	private String apeMatContacto;
	private String nombresContacto;
	private String telefonoContacto;
	private String correoContacto;
	private Date fechaRegistro;
	private Date horaRegistro;
	private String usuarioRegistro;

	public EDocumentoIdentidad getDocumentoContacto() {
		return documentoContacto;
	}

	public void setDocumentoContacto(EDocumentoIdentidad documentoContacto) {
		this.documentoContacto = documentoContacto;
	}

	public String getApePatContacto() {
		return apePatContacto;
	}

	public void setApePatContacto(String apePatContacto) {
		this.apePatContacto = apePatContacto;
	}

	public String getApeMatContacto() {
		return apeMatContacto;
	}

	public void setApeMatContacto(String apeMatContacto) {
		this.apeMatContacto = apeMatContacto;
	}

	public String getNombresContacto() {
		return nombresContacto;
	}

	public void setNombresContacto(String nombresContacto) {
		this.nombresContacto = nombresContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getCorreoContacto() {
		return correoContacto;
	}

	public void setCorreoContacto(String correoContacto) {
		this.correoContacto = correoContacto;
	}

	public String getNombreCompleto() {
		
		String nombreCompleto = "";
		if(documentoContacto != null && 
				documentoContacto.getTipoDocumento() != null && 
		   !UFuncionesGenerales.revisaCadena(documentoContacto.getTipoDocumento().getCodigo()).equals("")){
			
				if (UFuncionesGenerales.revisaCadena(apePatContacto).length() > 0
						&& UFuncionesGenerales.revisaCadena(apeMatContacto).length() > 0
						&& UFuncionesGenerales.revisaCadena(nombresContacto).length() > 0) {
					nombreCompleto = UFuncionesGenerales.revisaCadena(apePatContacto).concat(" ")
							.concat(UFuncionesGenerales.revisaCadena(apeMatContacto)).concat(" ")
							.concat(UFuncionesGenerales.revisaCadena(nombresContacto));
					
				}else if (UFuncionesGenerales.revisaCadena(apePatContacto).length() > 0
						&& UFuncionesGenerales.revisaCadena(nombresContacto).length() > 0) {
					nombreCompleto = UFuncionesGenerales.revisaCadena(apePatContacto).concat(" ")
							.concat(UFuncionesGenerales.revisaCadena(nombresContacto));
					
				}
			
		}
		
		return nombreCompleto;
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
