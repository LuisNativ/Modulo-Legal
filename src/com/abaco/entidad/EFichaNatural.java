package com.abaco.entidad;

import java.util.Date;
import java.util.List;

import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.entidad.ENombre;

public class EFichaNatural {

	// DATOS PERSONALES

	private String codigo;
	private String idTipoDocumento;
	private String desTipoDocumento;
	private String documento;
	private Date fechaNacimiento;
	private String nombres;
	private String nombreLargo;
	private String nombreCorto;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String idPaisNacionalidad;
	private boolean dobleNacionalidad;
	private String idPaisOtraNacionalidad;
	private String idGenero;
	private String idEstadoCivil;
	private String idResidencia;
	private String idPaisResidencia;
	private String desPaisResidencia;
	private String idTipoDireccion;
	private String direccion;
	private String ubigeoDireccion;
	private int idZona;
	private String idNivelInstruccion;
	private String idTipoVivienda;
	private String otroTipoVivienda;
	private String idOcupacion;
	private String idProfesion;
	private String telefonoFijo;
	private String celular;
	private String email;
	private String email2;
	private String ruc;
	private String idClasePersona;
	private int cantidadHijos;
	private EHijo hijo1;
	private EHijo hijo2;
	private EHijo hijo3;
	private EHijo hijo4;
	private EHijo hijo5;
	private EHijo hijo6;
	private EHijo hijo7;
	private EHijo hijo8;
	private EHijo hijo9;
	private EHijo hijo10;
	private int idProveedor;
	private int idAceptante;
	private int idAportacion;
	private int idTipoRelacion;
	private String idComunidadOrigen;
	private int Tipocliente;

	// DATOS LABORALES

	private boolean indepeSinNegocio;
	private String idActividadISN;
	private Date fechaInicioActiviISN;
	private double ingresoMensualISN;
	private boolean depenEmpleador;
	private int idCentroTrabajo;
	private String empresaEmpleador;
	private Date fechaIngreso;
	private double ingresoMensualD;
	private String rucEmpleador;
	private String idCondiTrabajo;
	private String idFrecuencia;
	private String idCargoLaboral;
	private String idPaisResidenciaEmpleador;
	private String idTipoDireccionEmpleador;
	private String direccionEmpleador;
	private String ubigeoDireccionEmpleador;
	private int idZonaEmpleador;
	private String telefonoEmpleador;
	private boolean indepeConNegocio;
	private String idActividadICN;
	private String idRegistradaSUNAT;
	private String idRegimenTributario;
	private String razonSocialICN;
	private String giroNegocio;
	private Date fechaInicioActiviICN;
	private String idTipoIngreso;
	private double ingresoMensualICN;
	private String idTipoPropiedad;
	private String idPaisResidenciaICN;
	private String idTipoDireccionICN;
	private boolean direccionDomicilio;
	private String direccionNegocio;
	private String ubigeoDireccionNegocio;
	private int idZonaICN;

	// PERSONAS EXPUESTAS POLITICAMENTE - PEP

	private int PEP1;
	private int PEP2;
	private int PEP3;
	private int PEP4;
	private int PEP5;
	private int PEP6;
	private int UIF1;

	// DATOS DEL CONYUGUE

	private String idTipoDocumentoConyugue;
	private String documentoConyugue;
	private Date fechaNacimientoConyugue;
	private String nombresConyugue;
	private String nombreLargoConyugue;
	private String apellidoPaternoConyugue;
	private String apellidoMaternoConyugue;
	private String idGeneroConyugue;
	private String idOcupacionConyugue;
	private String idProfesionConyugue;
	private String telefonoConyugue;
	private String celularConyugue;
	private String emailConyugue;
	private String centroTrabajoConyugue;
	private String telefonoTrabajoConyugue;
	private String idPaisResidenciaConyugue;
	private String idTipoDireccionTrabajoConyugue;
	private String direccionTrabajoConyugue;
	private String ubigeoDireccionTrabajoConyugue;
	private int idZonaTrabajoConyugue;
	private String trabajoAnteriorConyugue;
	private String telTrabajoAnteriorConyugue;
	private double ingresoFijoMensualSConyugue;
	private double ingresoFijoMensualDConyugue;
	private double otroIngresoMensualSConyugue;
	private double otroIngresoMensualDConyugue;
	private int nroMesesOtrosIngresosConyugue;
	private Date fechaIngresoConyugue;

	private String idEstado;
	private int idEstadoAutoriza;
	private String desEstado;
	private EUsuario usuario;
	private Date fechaRegistro;
	private Date horaRegistro;

	// DATOS COMPLEMENTARIOS
	private String idDivision;
	private String idUnidad;
	private String idSubUnidad;
	private String idGrupoEconomico;
	private String idMasterGrupoE;
	private String idGrupoFamiliar;
	private String idMasterGrupoF;
	private int idDireccionPostal;
	private int idDireccionTrabajoPostal;
	private String idPaisResidenciaPostal;
	private String idTipoDireccionPostal;
	private String direccionPostal;
	private String ubigeoDireccionPostal;
	private int idZonaPostal;
	private ENombre beneficiario1;
	private ENombre beneficiario2;
	private ENombre beneficiario3;
	private ENombre beneficiario4;
	private ENombre beneficiario5;
	private ENombre beneficiario6;
	private int cantidadBeneficiario;
	private String banco1;
	private int idMonedaBanco1;
	private int idTipoCtaBanco1;
	private String ctaBanco1;
	private String banco2;
	private int idMonedaBanco2;
	private int idTipoCtaBanco2;
	private String ctaBanco2;
	private String banco3;
	private int idMonedaBanco3;
	private int idTipoCtaBanco3;
	private String ctaBanco3;
	private String banco4;
	private int idMonedaBanco4;
	private int idTipoCtaBanco4;
	private String ctaBanco4;
	private String tarjetaCredito1;
	private int idMonedaTarjetaCredito1;
	private double limiteCredito1;
	private String tarjetaCredito2;
	private int idMonedaTarjetaCredito2;
	private double limiteCredito2;
	private String tarjetaCredito3;
	private int idMonedaTarjetaCredito3;
	private double limiteCredito3;
	private String comercial1;
	private String comercial2;
	private String comercial3;
	private int idTipoOtraRefe1;
	private String desTipoOtraRefe1;
	private String desOtraRefe1;
	private double montoOtraRefe1;
	private int idTipoOtraRefe2;
	private String desTipoOtraRefe2;
	private String desOtraRefe2;
	private double montoOtraRefe2;
	private int idTipoOtraRefe3;
	private String desTipoOtraRefe3;
	private String desOtraRefe3;
	private double montoOtraRefe3;
	private int idTipoOtraRefe4;
	private String desTipoOtraRefe4;
	private String desOtraRefe4;
	private double montoOtraRefe4;
	private int idInstitucion1;
	private String desInstitucion1;
	private int antiguedadInsti1;
	private int idInstitucion2;
	private String desInstitucion2;
	private int antiguedadInsti2;
	private int idInstitucion3;
	private String desInstitucion3;
	private int antiguedadInsti3;
	private int idInstitucion4;
	private String desInstitucion4;
	private int antiguedadInsti4;
	private int idInstitucion5;
	private String desInstitucion5;
	private int antiguedadInsti5;
	private long codigoPatrocinante1;
	private String nombrePatrocinante1;
	private long codigoPatrocinante2;
	private String nombrePatrocinante2;

	// DATOS PARA AUTORIZACION
	private boolean domicilioExtranjero;
	private boolean pertenecePEP;
	private String imprimirWaiver;
	private List<EAcontecimiento> lstAcontecimiento;
	private EUsuario usuarioAutoriza;
	private Date fechaAutoriza;
	private boolean envioMsgGerencia;
	private boolean perteneceLista;

	// Datos Auditoria
	private String fechaRegistroSocio;
	private String registradoPor;
	private String fechaUltModificacion;
	private String modificadoPor;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getNombreLargo() {
		if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(apellidoMaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
			nombreLargo = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(apellidoMaterno)).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(nombres));

		} else if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
			nombreLargo = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(nombres));

		}

		if (UFuncionesGenerales.revisaCadena(nombreLargo).length() > 60) {
			nombreLargo = nombreLargo.substring(0, 59);
		}
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public String getNombreCorto() {
		if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(apellidoMaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
			nombreCorto = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(apellidoMaterno)).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(nombres));

		} else if (UFuncionesGenerales.revisaCadena(apellidoPaterno).length() > 0
				&& UFuncionesGenerales.revisaCadena(nombres).length() > 0) {
			nombreCorto = UFuncionesGenerales.revisaCadena(apellidoPaterno).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(nombres));

		}

		if (UFuncionesGenerales.revisaCadena(nombreCorto).length() > 30) {
			nombreCorto = nombreCorto.substring(0, 29);
		}
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
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

	public String getIdPaisNacionalidad() {
		return idPaisNacionalidad;
	}

	public void setIdPaisNacionalidad(String idPaisNacionalidad) {
		this.idPaisNacionalidad = idPaisNacionalidad;
	}

	public String getIdPaisOtraNacionalidad() {
		return idPaisOtraNacionalidad;
	}

	public void setIdPaisOtraNacionalidad(String idPaisOtraNacionalidad) {
		this.idPaisOtraNacionalidad = idPaisOtraNacionalidad;
	}

	public String getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(String idGenero) {
		this.idGenero = idGenero;
	}

	public String getIdEstadoCivil() {
		return idEstadoCivil;
	}

	public void setIdEstadoCivil(String idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}

	public String getIdResidencia() {
		return idResidencia;
	}

	public void setIdResidencia(String idResidencia) {
		this.idResidencia = idResidencia;
	}

	public String getIdTipoDireccion() {
		return idTipoDireccion;
	}

	public void setIdTipoDireccion(String idTipoDireccion) {
		this.idTipoDireccion = idTipoDireccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUbigeoDireccion() {
		return ubigeoDireccion;
	}

	public void setUbigeoDireccion(String ubigeoDireccion) {
		this.ubigeoDireccion = ubigeoDireccion;
	}

	public String getIdNivelInstruccion() {
		return idNivelInstruccion;
	}

	public void setIdNivelInstruccion(String idNivelInstruccion) {
		this.idNivelInstruccion = idNivelInstruccion;
	}

	public String getIdTipoVivienda() {
		return idTipoVivienda;
	}

	public void setIdTipoVivienda(String idTipoVivienda) {
		this.idTipoVivienda = idTipoVivienda;
	}

	public String getIdProfesion() {
		return idProfesion;
	}

	public void setIdProfesion(String idProfesion) {
		this.idProfesion = idProfesion;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
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

	public String getRuc() {
		if (UFuncionesGenerales.revisaCadena(idTipoDocumento).equalsIgnoreCase(UTipoDocumento.RUC)) {
			ruc = UFuncionesGenerales.revisaCadena(documento);
		} else {
			ruc = UFuncionesGenerales.revisaCadena(ruc);
		}
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public int getCantidadHijos() {
		return cantidadHijos;
	}

	public void setCantidadHijos(int cantidadHijos) {
		this.cantidadHijos = cantidadHijos;
	}

	public EHijo getHijo1() {
		return hijo1;
	}

	public void setHijo1(EHijo hijo1) {
		this.hijo1 = hijo1;
	}

	public EHijo getHijo2() {
		return hijo2;
	}

	public void setHijo2(EHijo hijo2) {
		this.hijo2 = hijo2;
	}

	public EHijo getHijo3() {
		return hijo3;
	}

	public void setHijo3(EHijo hijo3) {
		this.hijo3 = hijo3;
	}

	public EHijo getHijo4() {
		return hijo4;
	}

	public void setHijo4(EHijo hijo4) {
		this.hijo4 = hijo4;
	}

	public EHijo getHijo5() {
		return hijo5;
	}

	public void setHijo5(EHijo hijo5) {
		this.hijo5 = hijo5;
	}

	public EHijo getHijo6() {
		return hijo6;
	}

	public void setHijo6(EHijo hijo6) {
		this.hijo6 = hijo6;
	}

	public EHijo getHijo7() {
		return hijo7;
	}

	public void setHijo7(EHijo hijo7) {
		this.hijo7 = hijo7;
	}

	public EHijo getHijo8() {
		return hijo8;
	}

	public void setHijo8(EHijo hijo8) {
		this.hijo8 = hijo8;
	}

	public EHijo getHijo9() {
		return hijo9;
	}

	public void setHijo9(EHijo hijo9) {
		this.hijo9 = hijo9;
	}

	public EHijo getHijo10() {
		return hijo10;
	}

	public void setHijo10(EHijo hijo10) {
		this.hijo10 = hijo10;
	}

	public String getIdActividadISN() {
		return idActividadISN;
	}

	public void setIdActividadISN(String idActividadISN) {
		this.idActividadISN = idActividadISN;
	}

	public Date getFechaInicioActiviISN() {
		return fechaInicioActiviISN;
	}

	public void setFechaInicioActiviISN(Date fechaInicioActiviISN) {
		this.fechaInicioActiviISN = fechaInicioActiviISN;
	}

	public double getIngresoMensualISN() {
		return ingresoMensualISN;
	}

	public void setIngresoMensualISN(double ingresoMensualISN) {
		this.ingresoMensualISN = ingresoMensualISN;
	}

	public String getEmpresaEmpleador() {
		return empresaEmpleador;
	}

	public void setEmpresaEmpleador(String empresaEmpleador) {
		this.empresaEmpleador = empresaEmpleador;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public double getIngresoMensualD() {
		return ingresoMensualD;
	}

	public void setIngresoMensualD(double ingresoMensualD) {
		this.ingresoMensualD = ingresoMensualD;
	}

	public String getRucEmpleador() {
		return rucEmpleador;
	}

	public void setRucEmpleador(String rucEmpleador) {
		this.rucEmpleador = rucEmpleador;
	}

	public String getIdCondiTrabajo() {
		return idCondiTrabajo;
	}

	public void setIdCondiTrabajo(String idCondiTrabajo) {
		this.idCondiTrabajo = idCondiTrabajo;
	}

	public String getIdFrecuencia() {
		return idFrecuencia;
	}

	public void setIdFrecuencia(String idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
	}

	public String getIdCargoLaboral() {
		return idCargoLaboral;
	}

	public void setIdCargoLaboral(String idCargoLaboral) {
		this.idCargoLaboral = idCargoLaboral;
	}

	public String getDireccionEmpledor() {
		return direccionEmpleador;
	}

	public void setDireccionEmpledor(String direccionEmpledor) {
		this.direccionEmpleador = direccionEmpledor;
	}

	public String getUbigeoDireccionEmpleador() {
		return ubigeoDireccionEmpleador;
	}

	public void setUbigeoDireccionEmpleador(String ubigeoDireccionEmpleador) {
		this.ubigeoDireccionEmpleador = ubigeoDireccionEmpleador;
	}

	public String getTelefonoEmpleador() {
		return telefonoEmpleador;
	}

	public void setTelefonoEmpleador(String telefonoEmpleador) {
		this.telefonoEmpleador = telefonoEmpleador;
	}

	public String getIdActividadICN() {
		return idActividadICN;
	}

	public void setIdActividadICN(String idActividadICN) {
		this.idActividadICN = idActividadICN;
	}

	public String getIdRegistradaSUNAT() {
		return idRegistradaSUNAT;
	}

	public void setIdRegistradaSUNAT(String idRegistradaSUNAT) {
		this.idRegistradaSUNAT = idRegistradaSUNAT;
	}

	public String getIdRegimenTributario() {
		return idRegimenTributario;
	}

	public void setIdRegimenTributario(String idRegimenTributario) {
		this.idRegimenTributario = idRegimenTributario;
	}

	public String getRazonSocialICN() {
		return razonSocialICN;
	}

	public void setRazonSocialICN(String razonSocialICN) {
		this.razonSocialICN = razonSocialICN;
	}

	public String getGiroNegocio() {
		return giroNegocio;
	}

	public void setGiroNegocio(String giroNegocio) {
		this.giroNegocio = giroNegocio;
	}

	public Date getFechaInicioActiviICN() {
		return fechaInicioActiviICN;
	}

	public void setFechaInicioActiviICN(Date fechaInicioActiviICN) {
		this.fechaInicioActiviICN = fechaInicioActiviICN;
	}

	public String getIdTipoIngreso() {
		return idTipoIngreso;
	}

	public void setIdTipoIngreso(String idTipoIngreso) {
		this.idTipoIngreso = idTipoIngreso;
	}

	public double getIngresoMensualICN() {
		return ingresoMensualICN;
	}

	public void setIngresoMensualICN(double ingresoMensualICN) {
		this.ingresoMensualICN = ingresoMensualICN;
	}

	public String getIdTipoPropiedad() {
		return idTipoPropiedad;
	}

	public void setIdTipoPropiedad(String idTipoPropiedad) {
		this.idTipoPropiedad = idTipoPropiedad;
	}

	public String getDireccionNegocio() {
		return direccionNegocio;
	}

	public void setDireccionNegocio(String direccionNegocio) {
		this.direccionNegocio = direccionNegocio;
	}

	public String getUbigeoDireccionNegocio() {
		return ubigeoDireccionNegocio;
	}

	public void setUbigeoDireccionNegocio(String ubigeoDireccionNegocio) {
		this.ubigeoDireccionNegocio = ubigeoDireccionNegocio;
	}

	public int getPEP1() {
		return PEP1;
	}

	public void setPEP1(int pEP1) {
		PEP1 = pEP1;
	}

	public int getPEP2() {
		return PEP2;
	}

	public void setPEP2(int pEP2) {
		PEP2 = pEP2;
	}

	public int getPEP3() {
		return PEP3;
	}

	public void setPEP3(int pEP3) {
		PEP3 = pEP3;
	}

	public int getPEP4() {
		return PEP4;
	}

	public void setPEP4(int pEP4) {
		PEP4 = pEP4;
	}

	public int getPEP5() {
		return PEP5;
	}

	public void setPEP5(int pEP5) {
		PEP5 = pEP5;
	}

	public int getPEP6() {
		return PEP6;
	}

	public void setPEP6(int pEP6) {
		PEP6 = pEP6;
	}

	public int getUIF1() {
		return UIF1;
	}

	public void setUIF1(int uIF1) {
		UIF1 = uIF1;
	}

	public String getIdTipoDocumentoConyugue() {
		return idTipoDocumentoConyugue;
	}

	public void setIdTipoDocumentoConyugue(String idTipoDocumentoConyugue) {
		this.idTipoDocumentoConyugue = idTipoDocumentoConyugue;
	}

	public String getDocumentoConyugue() {
		return documentoConyugue;
	}

	public void setDocumentoConyugue(String documentoConyugue) {
		this.documentoConyugue = documentoConyugue;
	}

	public Date getFechaNacimientoConyugue() {
		return fechaNacimientoConyugue;
	}

	public void setFechaNacimientoConyugue(Date fechaNacimientoConyugue) {
		this.fechaNacimientoConyugue = fechaNacimientoConyugue;
	}

	public String getNombresConyugue() {
		return nombresConyugue;
	}

	public void setNombresConyugue(String nombresConyugue) {
		this.nombresConyugue = nombresConyugue;
	}

	public String getNombreLargoConyugue() {

		if (UFuncionesGenerales.revisaCadena(apellidoPaternoConyugue).length() > 0
				&& UFuncionesGenerales.revisaCadena(nombresConyugue).length() > 0) {
			nombreLargoConyugue = UFuncionesGenerales.revisaCadena(apellidoPaternoConyugue).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(apellidoMaternoConyugue)).concat(" ")
					.concat(UFuncionesGenerales.revisaCadena(nombresConyugue));
			if (nombreLargoConyugue.length() > 40) {
				nombreLargoConyugue = nombreLargoConyugue.substring(0, 39);
			}
		}
		return nombreLargoConyugue;
	}

	public void setNombreLargoConyugue(String nombreLargoConyugue) {
		this.nombreLargoConyugue = nombreLargoConyugue;
	}

	public String getApellidoPaternoConyugue() {
		return apellidoPaternoConyugue;
	}

	public void setApellidoPaternoConyugue(String apellidoPaternoConyugue) {
		this.apellidoPaternoConyugue = apellidoPaternoConyugue;
	}

	public String getApellidoMaternoConyugue() {
		return apellidoMaternoConyugue;
	}

	public void setApellidoMaternoConyugue(String apellidoMaternoConyugue) {
		this.apellidoMaternoConyugue = apellidoMaternoConyugue;
	}

	public String getIdGeneroConyugue() {
		return idGeneroConyugue;
	}

	public void setIdGeneroConyugue(String idGeneroConyugue) {
		this.idGeneroConyugue = idGeneroConyugue;
	}

	public String getIdProfesionConyugue() {
		return idProfesionConyugue;
	}

	public void setIdProfesionConyugue(String idProfesionConyugue) {
		this.idProfesionConyugue = idProfesionConyugue;
	}

	public String getTelefonoConyugue() {
		return telefonoConyugue;
	}

	public void setTelefonoConyugue(String telefonoConyugue) {
		this.telefonoConyugue = telefonoConyugue;
	}

	public String getCelularConyugue() {
		return celularConyugue;
	}

	public void setCelularConyugue(String celularConyugue) {
		this.celularConyugue = celularConyugue;
	}

	public String getEmailConyugue() {
		return emailConyugue;
	}

	public void setEmailConyugue(String emailConyugue) {
		this.emailConyugue = emailConyugue;
	}

	public boolean isDobleNacionalidad() {
		return dobleNacionalidad;
	}

	public void setDobleNacionalidad(boolean dobleNacionalidad) {
		this.dobleNacionalidad = dobleNacionalidad;
	}

	public boolean isIndepeSinNegocio() {
		return indepeSinNegocio;
	}

	public void setIndepeSinNegocio(boolean indepeSinNegocio) {
		this.indepeSinNegocio = indepeSinNegocio;
	}

	public boolean isDepenEmpleador() {
		return depenEmpleador;
	}

	public void setDepenEmpleador(boolean depenEmpleador) {
		this.depenEmpleador = depenEmpleador;
	}

	public boolean isIndepeConNegocio() {
		return indepeConNegocio;
	}

	public void setIndepeConNegocio(boolean indepeConNegocio) {
		this.indepeConNegocio = indepeConNegocio;
	}

	public boolean isDireccionDomicilio() {
		return direccionDomicilio;
	}

	public void setDireccionDomicilio(boolean direccionDomicilio) {
		this.direccionDomicilio = direccionDomicilio;
	}

	public String getIdPaisResidencia() {
		return idPaisResidencia;
	}

	public void setIdPaisResidencia(String idPaisResidencia) {
		this.idPaisResidencia = idPaisResidencia;
	}

	public String getOtroTipoVivienda() {
		return otroTipoVivienda;
	}

	public void setOtroTipoVivienda(String otroTipoVivienda) {
		this.otroTipoVivienda = otroTipoVivienda;
	}

	public String getIdClasePersona() {
		return idClasePersona;
	}

	public void setIdClasePersona(String idClasePersona) {
		this.idClasePersona = idClasePersona;
	}

	public EUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(EUsuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(String idEstado) {
		this.idEstado = idEstado;
	}

	public boolean isDomicilioExtranjero() {
		return domicilioExtranjero;
	}

	public void setDomicilioExtranjero(boolean domicilioExtranjero) {
		this.domicilioExtranjero = domicilioExtranjero;
	}

	public boolean isPertenecePEP() {
		return pertenecePEP;
	}

	public void setPertenecePEP(boolean pertenecePEP) {
		this.pertenecePEP = pertenecePEP;
	}

	public String getImprimirWaiver() {
		return imprimirWaiver;
	}

	public void setImprimirWaiver(String imprimirWaiver) {
		this.imprimirWaiver = imprimirWaiver;
	}

	public String getDesTipoDocumento() {
		return desTipoDocumento;
	}

	public void setDesTipoDocumento(String desTipoDocumento) {
		this.desTipoDocumento = desTipoDocumento;
	}

	public String getDesEstado() {
		return desEstado;
	}

	public void setDesEstado(String desEstado) {
		this.desEstado = desEstado;
	}

	public String getDesPaisResidencia() {
		return desPaisResidencia;
	}

	public void setDesPaisResidencia(String desPaisResidencia) {
		this.desPaisResidencia = desPaisResidencia;
	}

	public EUsuario getUsuarioAutoriza() {
		return usuarioAutoriza;
	}

	public void setUsuarioAutoriza(EUsuario usuarioAutoriza) {
		this.usuarioAutoriza = usuarioAutoriza;
	}

	public Date getFechaAutoriza() {
		return fechaAutoriza;
	}

	public void setFechaAutoriza(Date fechaAutoriza) {
		this.fechaAutoriza = fechaAutoriza;
	}

	public int getIdEstadoAutoriza() {
		return idEstadoAutoriza;
	}

	public void setIdEstadoAutoriza(int idEstadoAutoriza) {
		this.idEstadoAutoriza = idEstadoAutoriza;
	}

	public String getDireccionEmpleador() {
		return direccionEmpleador;
	}

	public void setDireccionEmpleador(String direccionEmpleador) {
		this.direccionEmpleador = direccionEmpleador;
	}

	public String getCentroTrabajoConyugue() {
		return centroTrabajoConyugue;
	}

	public void setCentroTrabajoConyugue(String centroTrabajoConyugue) {
		this.centroTrabajoConyugue = centroTrabajoConyugue;
	}

	public String getTelefonoTrabajoConyugue() {
		return telefonoTrabajoConyugue;
	}

	public void setTelefonoTrabajoConyugue(String telefonoTrabajoConyugue) {
		this.telefonoTrabajoConyugue = telefonoTrabajoConyugue;
	}

	public String getDireccionTrabajoConyugue() {
		return direccionTrabajoConyugue;
	}

	public void setDireccionTrabajoConyugue(String direccionTrabajoConyugue) {
		this.direccionTrabajoConyugue = direccionTrabajoConyugue;
	}

	public String getTrabajoAnteriorConyugue() {
		return trabajoAnteriorConyugue;
	}

	public void setTrabajoAnteriorConyugue(String trabajoAnteriorConyugue) {
		this.trabajoAnteriorConyugue = trabajoAnteriorConyugue;
	}

	public String getTelTrabajoAnteriorConyugue() {
		return telTrabajoAnteriorConyugue;
	}

	public void setTelTrabajoAnteriorConyugue(String telTrabajoAnteriorConyugue) {
		this.telTrabajoAnteriorConyugue = telTrabajoAnteriorConyugue;
	}

	public double getIngresoFijoMensualSConyugue() {
		return ingresoFijoMensualSConyugue;
	}

	public void setIngresoFijoMensualSConyugue(double ingresoFijoMensualSConyugue) {
		this.ingresoFijoMensualSConyugue = ingresoFijoMensualSConyugue;
	}

	public double getIngresoFijoMensualDConyugue() {
		return ingresoFijoMensualDConyugue;
	}

	public void setIngresoFijoMensualDConyugue(double ingresoFijoMensualDConyugue) {
		this.ingresoFijoMensualDConyugue = ingresoFijoMensualDConyugue;
	}

	public double getOtroIngresoMensualSConyugue() {
		return otroIngresoMensualSConyugue;
	}

	public void setOtroIngresoMensualSConyugue(double otroIngresoMensualSConyugue) {
		this.otroIngresoMensualSConyugue = otroIngresoMensualSConyugue;
	}

	public double getOtroIngresoMensualDConyugue() {
		return otroIngresoMensualDConyugue;
	}

	public void setOtroIngresoMensualDConyugue(double otroIngresoMensualDConyugue) {
		this.otroIngresoMensualDConyugue = otroIngresoMensualDConyugue;
	}

	public int getNroMesesOtrosIngresosConyugue() {
		return nroMesesOtrosIngresosConyugue;
	}

	public void setNroMesesOtrosIngresosConyugue(int nroMesesOtrosIngresosConyugue) {
		this.nroMesesOtrosIngresosConyugue = nroMesesOtrosIngresosConyugue;
	}

	public int getIdZona() {
		return idZona;
	}

	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}

	public String getIdSubUnidad() {
		return idSubUnidad;
	}

	public void setIdSubUnidad(String idSubUnidad) {
		this.idSubUnidad = idSubUnidad;
	}

	public String getIdGrupoEconomico() {
		return idGrupoEconomico;
	}

	public void setIdGrupoEconomico(String idGrupoEconomico) {
		this.idGrupoEconomico = idGrupoEconomico;
	}

	public String getIdMasterGrupoE() {
		return idMasterGrupoE;
	}

	public void setIdMasterGrupoE(String idMasterGrupoE) {
		this.idMasterGrupoE = idMasterGrupoE;
	}

	public String getIdGrupoFamiliar() {
		return idGrupoFamiliar;
	}

	public void setIdGrupoFamiliar(String idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	public String getIdMasterGrupoF() {
		return idMasterGrupoF;
	}

	public void setIdMasterGrupoF(String idMasterGrupoF) {
		this.idMasterGrupoF = idMasterGrupoF;
	}

	public String getDireccionPostal() {
		return direccionPostal;
	}

	public void setDireccionPostal(String direccionPostal) {
		this.direccionPostal = direccionPostal;
	}

	public String getUbigeoDireccionPostal() {
		return ubigeoDireccionPostal;
	}

	public void setUbigeoDireccionPostal(String ubigeoDireccionPostal) {
		this.ubigeoDireccionPostal = ubigeoDireccionPostal;
	}

	public int getIdZonaPostal() {
		return idZonaPostal;
	}

	public void setIdZonaPostal(int idZonaPostal) {
		this.idZonaPostal = idZonaPostal;
	}

	public ENombre getBeneficiario1() {
		return beneficiario1;
	}

	public void setBeneficiario1(ENombre beneficiario1) {
		this.beneficiario1 = beneficiario1;
	}

	public ENombre getBeneficiario2() {
		return beneficiario2;
	}

	public void setBeneficiario2(ENombre beneficiario2) {
		this.beneficiario2 = beneficiario2;
	}

	public ENombre getBeneficiario3() {
		return beneficiario3;
	}

	public void setBeneficiario3(ENombre beneficiario3) {
		this.beneficiario3 = beneficiario3;
	}

	public ENombre getBeneficiario4() {
		return beneficiario4;
	}

	public void setBeneficiario4(ENombre beneficiario4) {
		this.beneficiario4 = beneficiario4;
	}

	public ENombre getBeneficiario5() {
		return beneficiario5;
	}

	public void setBeneficiario5(ENombre beneficiario5) {
		this.beneficiario5 = beneficiario5;
	}

	public ENombre getBeneficiario6() {
		return beneficiario6;
	}

	public void setBeneficiario6(ENombre beneficiario6) {
		this.beneficiario6 = beneficiario6;
	}

	public String getBanco1() {
		return banco1;
	}

	public void setBanco1(String banco1) {
		this.banco1 = banco1;
	}

	public String getCtaBanco1() {
		return ctaBanco1;
	}

	public void setCtaBanco1(String ctaBanco1) {
		this.ctaBanco1 = ctaBanco1;
	}

	public String getBanco2() {
		return banco2;
	}

	public void setBanco2(String banco2) {
		this.banco2 = banco2;
	}

	public String getCtaBanco2() {
		return ctaBanco2;
	}

	public void setCtaBanco2(String ctaBanco2) {
		this.ctaBanco2 = ctaBanco2;
	}

	public String getBanco3() {
		return banco3;
	}

	public void setBanco3(String banco3) {
		this.banco3 = banco3;
	}

	public String getCtaBanco3() {
		return ctaBanco3;
	}

	public void setCtaBanco3(String ctaBanco3) {
		this.ctaBanco3 = ctaBanco3;
	}

	public String getTarjetaCredito1() {
		return tarjetaCredito1;
	}

	public void setTarjetaCredito1(String tarjetaCredito1) {
		this.tarjetaCredito1 = tarjetaCredito1;
	}

	public double getLimiteCredito1() {
		return limiteCredito1;
	}

	public void setLimiteCredito1(double limiteCredito1) {
		this.limiteCredito1 = limiteCredito1;
	}

	public String getTarjetaCredito2() {
		return tarjetaCredito2;
	}

	public void setTarjetaCredito2(String tarjetaCredito2) {
		this.tarjetaCredito2 = tarjetaCredito2;
	}

	public double getLimiteCredito2() {
		return limiteCredito2;
	}

	public void setLimiteCredito2(double limiteCredito2) {
		this.limiteCredito2 = limiteCredito2;
	}

	public String getTarjetaCredito3() {
		return tarjetaCredito3;
	}

	public void setTarjetaCredito3(String tarjetaCredito3) {
		this.tarjetaCredito3 = tarjetaCredito3;
	}

	public double getLimiteCredito3() {
		return limiteCredito3;
	}

	public void setLimiteCredito3(double limiteCredito3) {
		this.limiteCredito3 = limiteCredito3;
	}

	public String getComercial1() {
		return comercial1;
	}

	public void setComercial1(String comercial1) {
		this.comercial1 = comercial1;
	}

	public String getComercial2() {
		return comercial2;
	}

	public void setComercial2(String comercial2) {
		this.comercial2 = comercial2;
	}

	public String getComercial3() {
		return comercial3;
	}

	public void setComercial3(String comercial3) {
		this.comercial3 = comercial3;
	}

	public int getIdTipoOtraRefe1() {
		return idTipoOtraRefe1;
	}

	public void setIdTipoOtraRefe1(int idTipoOtraRefe1) {
		this.idTipoOtraRefe1 = idTipoOtraRefe1;
	}

	public String getDesTipoOtraRefe1() {
		return desTipoOtraRefe1;
	}

	public void setDesTipoOtraRefe1(String desTipoOtraRefe1) {
		this.desTipoOtraRefe1 = desTipoOtraRefe1;
	}

	public String getDesOtraRefe1() {
		return desOtraRefe1;
	}

	public void setDesOtraRefe1(String desOtraRefe1) {
		this.desOtraRefe1 = desOtraRefe1;
	}

	public double getMontoOtraRefe1() {
		return montoOtraRefe1;
	}

	public void setMontoOtraRefe1(double montoOtraRefe1) {
		this.montoOtraRefe1 = montoOtraRefe1;
	}

	public int getIdTipoOtraRefe2() {
		return idTipoOtraRefe2;
	}

	public void setIdTipoOtraRefe2(int idTipoOtraRefe2) {
		this.idTipoOtraRefe2 = idTipoOtraRefe2;
	}

	public String getDesTipoOtraRefe2() {
		return desTipoOtraRefe2;
	}

	public void setDesTipoOtraRefe2(String desTipoOtraRefe2) {
		this.desTipoOtraRefe2 = desTipoOtraRefe2;
	}

	public String getDesOtraRefe2() {
		return desOtraRefe2;
	}

	public void setDesOtraRefe2(String desOtraRefe2) {
		this.desOtraRefe2 = desOtraRefe2;
	}

	public double getMontoOtraRefe2() {
		return montoOtraRefe2;
	}

	public void setMontoOtraRefe2(double montoOtraRefe2) {
		this.montoOtraRefe2 = montoOtraRefe2;
	}

	public int getIdTipoOtraRefe3() {
		return idTipoOtraRefe3;
	}

	public void setIdTipoOtraRefe3(int idTipoOtraRefe3) {
		this.idTipoOtraRefe3 = idTipoOtraRefe3;
	}

	public String getDesTipoOtraRefe3() {
		return desTipoOtraRefe3;
	}

	public void setDesTipoOtraRefe3(String desTipoOtraRefe3) {
		this.desTipoOtraRefe3 = desTipoOtraRefe3;
	}

	public String getDesOtraRefe3() {
		return desOtraRefe3;
	}

	public void setDesOtraRefe3(String desOtraRefe3) {
		this.desOtraRefe3 = desOtraRefe3;
	}

	public double getMontoOtraRefe3() {
		return montoOtraRefe3;
	}

	public void setMontoOtraRefe3(double montoOtraRefe3) {
		this.montoOtraRefe3 = montoOtraRefe3;
	}

	public int getIdTipoOtraRefe4() {
		return idTipoOtraRefe4;
	}

	public void setIdTipoOtraRefe4(int idTipoOtraRefe4) {
		this.idTipoOtraRefe4 = idTipoOtraRefe4;
	}

	public String getDesTipoOtraRefe4() {
		return desTipoOtraRefe4;
	}

	public void setDesTipoOtraRefe4(String desTipoOtraRefe4) {
		this.desTipoOtraRefe4 = desTipoOtraRefe4;
	}

	public String getDesOtraRefe4() {
		return desOtraRefe4;
	}

	public void setDesOtraRefe4(String desOtraRefe4) {
		this.desOtraRefe4 = desOtraRefe4;
	}

	public double getMontoOtraRefe4() {
		return montoOtraRefe4;
	}

	public void setMontoOtraRefe4(double montoOtraRefe4) {
		this.montoOtraRefe4 = montoOtraRefe4;
	}

	public int getIdInstitucion1() {
		return idInstitucion1;
	}

	public void setIdInstitucion1(int idInstitucion1) {
		this.idInstitucion1 = idInstitucion1;
	}

	public String getDesInstitucion1() {
		return desInstitucion1;
	}

	public void setDesInstitucion1(String desInstitucion1) {
		this.desInstitucion1 = desInstitucion1;
	}

	public int getAntiguedadInsti1() {
		return antiguedadInsti1;
	}

	public void setAntiguedadInsti1(int antiguedadInsti1) {
		this.antiguedadInsti1 = antiguedadInsti1;
	}

	public int getIdInstitucion2() {
		return idInstitucion2;
	}

	public void setIdInstitucion2(int idInstitucion2) {
		this.idInstitucion2 = idInstitucion2;
	}

	public String getDesInstitucion2() {
		return desInstitucion2;
	}

	public void setDesInstitucion2(String desInstitucion2) {
		this.desInstitucion2 = desInstitucion2;
	}

	public int getAntiguedadInsti2() {
		return antiguedadInsti2;
	}

	public void setAntiguedadInsti2(int antiguedadInsti2) {
		this.antiguedadInsti2 = antiguedadInsti2;
	}

	public int getIdInstitucion3() {
		return idInstitucion3;
	}

	public void setIdInstitucion3(int idInstitucion3) {
		this.idInstitucion3 = idInstitucion3;
	}

	public String getDesInstitucion3() {
		return desInstitucion3;
	}

	public void setDesInstitucion3(String desInstitucion3) {
		this.desInstitucion3 = desInstitucion3;
	}

	public int getAntiguedadInsti3() {
		return antiguedadInsti3;
	}

	public void setAntiguedadInsti3(int antiguedadInsti3) {
		this.antiguedadInsti3 = antiguedadInsti3;
	}

	public int getIdInstitucion4() {
		return idInstitucion4;
	}

	public void setIdInstitucion4(int idInstitucion4) {
		this.idInstitucion4 = idInstitucion4;
	}

	public String getDesInstitucion4() {
		return desInstitucion4;
	}

	public void setDesInstitucion4(String desInstitucion4) {
		this.desInstitucion4 = desInstitucion4;
	}

	public int getAntiguedadInsti4() {
		return antiguedadInsti4;
	}

	public void setAntiguedadInsti4(int antiguedadInsti4) {
		this.antiguedadInsti4 = antiguedadInsti4;
	}

	public int getIdInstitucion5() {
		return idInstitucion5;
	}

	public void setIdInstitucion5(int idInstitucion5) {
		this.idInstitucion5 = idInstitucion5;
	}

	public String getDesInstitucion5() {
		return desInstitucion5;
	}

	public void setDesInstitucion5(String desInstitucion5) {
		this.desInstitucion5 = desInstitucion5;
	}

	public int getAntiguedadInsti5() {
		return antiguedadInsti5;
	}

	public void setAntiguedadInsti5(int antiguedadInsti5) {
		this.antiguedadInsti5 = antiguedadInsti5;
	}

	public long getCodigoPatrocinante1() {
		return codigoPatrocinante1;
	}

	public void setCodigoPatrocinante1(long codigoPatrocinante1) {
		this.codigoPatrocinante1 = codigoPatrocinante1;
	}

	public String getNombrePatrocinante1() {
		return nombrePatrocinante1;
	}

	public void setNombrePatrocinante1(String nombrePatrocinante1) {
		this.nombrePatrocinante1 = nombrePatrocinante1;
	}

	public long getCodigoPatrocinante2() {
		return codigoPatrocinante2;
	}

	public void setCodigoPatrocinante2(long codigoPatrocinante2) {
		this.codigoPatrocinante2 = codigoPatrocinante2;
	}

	public String getNombrePatrocinante2() {
		return nombrePatrocinante2;
	}

	public void setNombrePatrocinante2(String nombrePatrocinante2) {
		this.nombrePatrocinante2 = nombrePatrocinante2;
	}

	public int getCantidadBeneficiario() {
		return cantidadBeneficiario;
	}

	public void setCantidadBeneficiario(int cantidadBeneficiario) {
		this.cantidadBeneficiario = cantidadBeneficiario;
	}

	public int getIdDireccionPostal() {
		return idDireccionPostal;
	}

	public void setIdDireccionPostal(int idDireccionPostal) {
		this.idDireccionPostal = idDireccionPostal;
	}

	public int getIdDireccionTrabajoPostal() {
		return idDireccionTrabajoPostal;
	}

	public void setIdDireccionTrabajoPostal(int idDireccionTrabajoPostal) {
		this.idDireccionTrabajoPostal = idDireccionTrabajoPostal;
	}

	public String getBanco4() {
		return banco4;
	}

	public void setBanco4(String banco4) {
		this.banco4 = banco4;
	}

	public String getCtaBanco4() {
		return ctaBanco4;
	}

	public void setCtaBanco4(String ctaBanco4) {
		this.ctaBanco4 = ctaBanco4;
	}

	public String getIdOcupacion() {
		return idOcupacion;
	}

	public void setIdOcupacion(String idOcupacion) {
		this.idOcupacion = idOcupacion;
	}

	public String getIdOcupacionConyugue() {
		return idOcupacionConyugue;
	}

	public void setIdOcupacionConyugue(String idOcupacionConyugue) {
		this.idOcupacionConyugue = idOcupacionConyugue;
	}

	public String getIdTipoDireccionEmpleador() {
		return idTipoDireccionEmpleador;
	}

	public void setIdTipoDireccionEmpleador(String idTipoDireccionEmpleador) {
		this.idTipoDireccionEmpleador = idTipoDireccionEmpleador;
	}

	public String getIdTipoDireccionICN() {
		return idTipoDireccionICN;
	}

	public void setIdTipoDireccionICN(String idTipoDireccionICN) {
		this.idTipoDireccionICN = idTipoDireccionICN;
	}

	public String getIdTipoDireccionTrabajoConyugue() {
		return idTipoDireccionTrabajoConyugue;
	}

	public void setIdTipoDireccionTrabajoConyugue(String idTipoDireccionTrabajoConyugue) {
		this.idTipoDireccionTrabajoConyugue = idTipoDireccionTrabajoConyugue;
	}

	public String getUbigeoDireccionTrabajoConyugue() {
		return ubigeoDireccionTrabajoConyugue;
	}

	public void setUbigeoDireccionTrabajoConyugue(String ubigeoDireccionTrabajoConyugue) {
		this.ubigeoDireccionTrabajoConyugue = ubigeoDireccionTrabajoConyugue;
	}

	public String getIdTipoDireccionPostal() {
		return idTipoDireccionPostal;
	}

	public void setIdTipoDireccionPostal(String idTipoDireccionPostal) {
		this.idTipoDireccionPostal = idTipoDireccionPostal;
	}

	public List<EAcontecimiento> getLstAcontecimiento() {
		return lstAcontecimiento;
	}

	public void setLstAcontecimiento(List<EAcontecimiento> lstAcontecimiento) {
		this.lstAcontecimiento = lstAcontecimiento;
	}

	public int getIdMonedaTarjetaCredito1() {
		return idMonedaTarjetaCredito1;
	}

	public void setIdMonedaTarjetaCredito1(int idMonedaTarjetaCredito1) {
		this.idMonedaTarjetaCredito1 = idMonedaTarjetaCredito1;
	}

	public int getIdMonedaTarjetaCredito2() {
		return idMonedaTarjetaCredito2;
	}

	public void setIdMonedaTarjetaCredito2(int idMonedaTarjetaCredito2) {
		this.idMonedaTarjetaCredito2 = idMonedaTarjetaCredito2;
	}

	public int getIdMonedaTarjetaCredito3() {
		return idMonedaTarjetaCredito3;
	}

	public void setIdMonedaTarjetaCredito3(int idMonedaTarjetaCredito3) {
		this.idMonedaTarjetaCredito3 = idMonedaTarjetaCredito3;
	}

	public int getIdMonedaBanco1() {
		return idMonedaBanco1;
	}

	public void setIdMonedaBanco1(int idMonedaBanco1) {
		this.idMonedaBanco1 = idMonedaBanco1;
	}

	public int getIdTipoCtaBanco1() {
		return idTipoCtaBanco1;
	}

	public void setIdTipoCtaBanco1(int idTipoCtaBanco1) {
		this.idTipoCtaBanco1 = idTipoCtaBanco1;
	}

	public int getIdMonedaBanco2() {
		return idMonedaBanco2;
	}

	public void setIdMonedaBanco2(int idMonedaBanco2) {
		this.idMonedaBanco2 = idMonedaBanco2;
	}

	public int getIdTipoCtaBanco2() {
		return idTipoCtaBanco2;
	}

	public void setIdTipoCtaBanco2(int idTipoCtaBanco2) {
		this.idTipoCtaBanco2 = idTipoCtaBanco2;
	}

	public int getIdMonedaBanco3() {
		return idMonedaBanco3;
	}

	public void setIdMonedaBanco3(int idMonedaBanco3) {
		this.idMonedaBanco3 = idMonedaBanco3;
	}

	public int getIdTipoCtaBanco3() {
		return idTipoCtaBanco3;
	}

	public void setIdTipoCtaBanco3(int idTipoCtaBanco3) {
		this.idTipoCtaBanco3 = idTipoCtaBanco3;
	}

	public int getIdMonedaBanco4() {
		return idMonedaBanco4;
	}

	public void setIdMonedaBanco4(int idMonedaBanco4) {
		this.idMonedaBanco4 = idMonedaBanco4;
	}

	public int getIdTipoCtaBanco4() {
		return idTipoCtaBanco4;
	}

	public void setIdTipoCtaBanco4(int idTipoCtaBanco4) {
		this.idTipoCtaBanco4 = idTipoCtaBanco4;
	}

	public int getIdZonaEmpleador() {
		return idZonaEmpleador;
	}

	public void setIdZonaEmpleador(int idZonaEmpleador) {
		this.idZonaEmpleador = idZonaEmpleador;
	}

	public int getIdZonaICN() {
		return idZonaICN;
	}

	public void setIdZonaICN(int idZonaICN) {
		this.idZonaICN = idZonaICN;
	}

	public int getIdZonaTrabajoConyugue() {
		return idZonaTrabajoConyugue;
	}

	public void setIdZonaTrabajoConyugue(int idZonaTrabajoConyugue) {
		this.idZonaTrabajoConyugue = idZonaTrabajoConyugue;
	}

	public boolean isEnvioMsgGerencia() {
		return envioMsgGerencia;
	}

	public void setEnvioMsgGerencia(boolean envioMsgGerencia) {
		this.envioMsgGerencia = envioMsgGerencia;
	}

	public String getIdDivision() {
		return idDivision;
	}

	public void setIdDivision(String idDivision) {
		this.idDivision = idDivision;
	}

	public String getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(String idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Date getHoraRegistro() {
		return horaRegistro;
	}

	public void setHoraRegistro(Date horaRegistro) {
		this.horaRegistro = horaRegistro;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public int getIdAceptante() {
		return idAceptante;
	}

	public void setIdAceptante(int idAceptante) {
		this.idAceptante = idAceptante;
	}

	public int getIdAportacion() {
		return idAportacion;
	}

	public void setIdAportacion(int idAportacion) {
		this.idAportacion = idAportacion;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public int getIdTipoRelacion() {
		return idTipoRelacion;
	}

	public void setIdTipoRelacion(int idTipoRelacion) {
		this.idTipoRelacion = idTipoRelacion;
	}

	public String getIdComunidadOrigen() {
		return idComunidadOrigen;
	}

	public void setIdComunidadOrigen(String idComunidadOrigen) {
		this.idComunidadOrigen = idComunidadOrigen;
	}

	public String getFechaRegistroSocio() {
		return fechaRegistroSocio;
	}

	public void setFechaRegistroSocio(String fechaRegistroSocio) {
		this.fechaRegistroSocio = fechaRegistroSocio;
	}

	public String getRegistradoPor() {
		return registradoPor;
	}

	public void setRegistradoPor(String registradoPor) {
		this.registradoPor = registradoPor;
	}

	public String getFechaUltModificacion() {
		return fechaUltModificacion;
	}

	public void setFechaUltModificacion(String fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public Date getFechaIngresoConyugue() {
		return fechaIngresoConyugue;
	}

	public void setFechaIngresoConyugue(Date fechaIngresoConyugue) {
		this.fechaIngresoConyugue = fechaIngresoConyugue;
	}

	public int getIdCentroTrabajo() {
		return idCentroTrabajo;
	}

	public void setIdCentroTrabajo(int idCentroTrabajo) {
		this.idCentroTrabajo = idCentroTrabajo;
	}

	public String getIdPaisResidenciaEmpleador() {
		return idPaisResidenciaEmpleador;
	}

	public void setIdPaisResidenciaEmpleador(String idPaisResidenciaEmpleador) {
		this.idPaisResidenciaEmpleador = idPaisResidenciaEmpleador;
	}

	public String getIdPaisResidenciaICN() {
		return idPaisResidenciaICN;
	}

	public void setIdPaisResidenciaICN(String idPaisResidenciaICN) {
		this.idPaisResidenciaICN = idPaisResidenciaICN;
	}

	public String getIdPaisResidenciaConyugue() {
		return idPaisResidenciaConyugue;
	}

	public void setIdPaisResidenciaConyugue(String idPaisResidenciaConyugue) {
		this.idPaisResidenciaConyugue = idPaisResidenciaConyugue;
	}

	public String getIdPaisResidenciaPostal() {
		return idPaisResidenciaPostal;
	}

	public void setIdPaisResidenciaPostal(String idPaisResidenciaPostal) {
		this.idPaisResidenciaPostal = idPaisResidenciaPostal;
	}

	public boolean isPerteneceLista() {
		return perteneceLista;
	}

	public void setPerteneceLista(boolean perteneceLista) {
		this.perteneceLista = perteneceLista;
	}

	public int getTipocliente() {
		return Tipocliente;
	}

	public void setTipocliente(int tipocliente) {
		Tipocliente = tipocliente;
	}

}
