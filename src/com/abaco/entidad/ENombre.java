package com.abaco.entidad;

import com.abaco.negocio.util.UFuncionesGenerales;

public class ENombre {

	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompleto;

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

	public String getNombreCompleto() {
		if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
			nombreCompleto = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(apellidoMaterno)).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(nombres));
			if (nombreCompleto.length() > 60) {
				nombreCompleto = nombreCompleto.substring(0, 59);
			}
		}
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	
}
