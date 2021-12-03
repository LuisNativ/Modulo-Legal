package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EDireccion;
import com.abaco.entidad.EDocumentoIdentidad;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EProductoCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.ETipoCliente;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAORepresentanteLegal extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_REPRESENTANTELEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REPRESENTANTELEGAL("+parametrosSP(63)+") }";
	private static final String SP_ABACOINLEGAL_INS_FACULTADPODER="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_FACULTADPODER("+parametrosSP(15)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REPRESENTANTELEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REPRESENTANTELEGAL("+parametrosSP(63)+") }";
	private static final String SP_ABACOINLEGAL_DEL_REPRESENTANTELEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_REPRESENTANTELEGAL(" +parametrosSP(5)+ ") }";
	private static final String SP_ABACOINLEGAL_DEL_FACULTADPODER="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_FACULTADPODER("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_SEL_REPRESENTANTELEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_REPRESENTANTELEGAL("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_FACULTADPODER="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_FACULTADPODER("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_INS_REPRESENTANTELEGALSOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_REPRESENTANTELEGALSOLICITUDCREDITO("+parametrosSP(58)+") }";
	private static final String SP_ABACOINLEGAL_UPD_REPRESENTANTELEGALSOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_REPRESENTANTELEGALSOLICITUDCREDITO("+parametrosSP(58)+") }";
	private static final String SP_ABACOINLEGAL_DEL_REPRESENTANTELEGALSOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_REPRESENTANTELEGALSOLICITUDCREDITO("+parametrosSP(6)+") }";
	
	private static String parametrosSP(int numeroDeParametros) {
        StringBuilder cadena = new StringBuilder();

        for (int i = 1; i <= numeroDeParametros; i++) {
            cadena.append("?,");
        }

        int longitudCadena = cadena.length();
        StringBuilder removeUltimoCaracter = new StringBuilder(cadena);
        StringBuilder cadenaFinal = removeUltimoCaracter.deleteCharAt(longitudCadena - 1);
        return cadenaFinal.toString();
    }
	
	public DAORepresentanteLegal(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregarRepresentanteLegal(ERepresentanteLegal eRepresentanteLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoPersona());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreLargo());
			lstParametrosEntrada.add(eRepresentanteLegal.getNominativo());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoRelacion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCentroTrabajo());
			lstParametrosEntrada.add(eRepresentanteLegal.getCargoLaboral());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPaisNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoGenero());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoNivelInstruccion());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoProfesion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxComercial());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxDomiciliaria());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxCorrespondencia());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDirecto());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCentral());
			lstParametrosEntrada.add(eRepresentanteLegal.getAnexo());
			lstParametrosEntrada.add(eRepresentanteLegal.getFax());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getIndicadorFirma());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaIngreso());
			lstParametrosEntrada.add(eRepresentanteLegal.getNumeroFichaPoder());
			lstParametrosEntrada.add(eRepresentanteLegal.getRegistroPoder());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),1));
			lstParametrosEntrada.add(eRepresentanteLegal.getInscripcionPoder2());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getObservacion(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getObservacion(),1));
			//lstParametrosEntrada.add(eRepresentanteLegal.getObservacion().replaceAll("\n", ""));
			
			lstParametrosEntrada.add(eRepresentanteLegal.getApellidoPaternoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getApellidoMaternoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getRazonSocialRepresentante());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRepresentanteLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REPRESENTANTELEGAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarRepresentanteLegalSolicitudCredito(ERepresentanteLegal eRepresentanteLegal,long numeroSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoPersona());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreLargo());
			lstParametrosEntrada.add(eRepresentanteLegal.getNominativo());
			//lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoRelacion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCentroTrabajo());
			lstParametrosEntrada.add(eRepresentanteLegal.getCargoLaboral());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPaisNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoGenero());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoNivelInstruccion());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoProfesion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxComercial());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxDomiciliaria());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxCorrespondencia());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDirecto());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCentral());
			lstParametrosEntrada.add(eRepresentanteLegal.getAnexo());
			lstParametrosEntrada.add(eRepresentanteLegal.getFax());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getIndicadorFirma());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaIngreso());
			lstParametrosEntrada.add(eRepresentanteLegal.getNumeroFichaPoder());
			lstParametrosEntrada.add(eRepresentanteLegal.getRegistroPoder());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),1));
			lstParametrosEntrada.add(eRepresentanteLegal.getInscripcionPoder2());
			lstParametrosEntrada.add(eRepresentanteLegal.getObservacion().replaceAll("\n", ""));
			
			lstParametrosEntrada.add(eRepresentanteLegal.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRepresentanteLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_REPRESENTANTELEGALSOLICITUDCREDITO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarFacultadPoder(ERepresentanteLegal eRepresentanteLegal, EFacultadPoder eFacultadPoder) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoFacultad());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoOtorgamiento());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoServicio());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoMoneda());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoTipoRelacion());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoRepresentanteRelacion());
			lstParametrosEntrada.add(eFacultadPoder.getCodigoTipoFirma());
			lstParametrosEntrada.add(eFacultadPoder.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eFacultadPoder.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eFacultadPoder.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_FACULTADPODER, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarRepresentanteLegal(ERepresentanteLegal eRepresentanteLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoPersona());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreLargo());
			lstParametrosEntrada.add(eRepresentanteLegal.getNominativo());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoRelacion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCentroTrabajo());
			lstParametrosEntrada.add(eRepresentanteLegal.getCargoLaboral());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPaisNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoGenero());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoNivelInstruccion());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoProfesion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxComercial());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxDomiciliaria());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxCorrespondencia());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDirecto());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCentral());
			lstParametrosEntrada.add(eRepresentanteLegal.getAnexo());
			lstParametrosEntrada.add(eRepresentanteLegal.getFax());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getIndicadorFirma());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaIngreso());
			lstParametrosEntrada.add(eRepresentanteLegal.getNumeroFichaPoder());
			lstParametrosEntrada.add(eRepresentanteLegal.getRegistroPoder());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),1));
			lstParametrosEntrada.add(eRepresentanteLegal.getInscripcionPoder2());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getObservacion(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getObservacion(),1));
			//lstParametrosEntrada.add(eRepresentanteLegal.getObservacion().replaceAll("\n", ""));
			
			lstParametrosEntrada.add(eRepresentanteLegal.getApellidoPaternoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getApellidoMaternoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getRazonSocialRepresentante());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRepresentanteLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REPRESENTANTELEGAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarRepresentanteLegalSolicitudCredito(ERepresentanteLegal eRepresentanteLegal,long numeroSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoPersona());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumento());
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreLargo());
			lstParametrosEntrada.add(eRepresentanteLegal.getNominativo());
			//lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoRelacion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCentroTrabajo());
			lstParametrosEntrada.add(eRepresentanteLegal.getCargoLaboral());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPaisNacimiento());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoGenero());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoNivelInstruccion());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoProfesion());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoComercial());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxComercial());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDomiciliaria());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxDomiciliaria());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getDireccionCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoUbigeoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCiudadCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getEstadoProvinciaCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoPostalCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCorrespondencia());
			lstParametrosEntrada.add(eRepresentanteLegal.getFaxCorrespondencia());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getNombreConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getDocumentoConyugue());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoDirecto());
			lstParametrosEntrada.add(eRepresentanteLegal.getTelefonoCentral());
			lstParametrosEntrada.add(eRepresentanteLegal.getAnexo());
			lstParametrosEntrada.add(eRepresentanteLegal.getFax());
			
			lstParametrosEntrada.add(eRepresentanteLegal.getIndicadorFirma());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaIngreso());
			lstParametrosEntrada.add(eRepresentanteLegal.getNumeroFichaPoder());
			lstParametrosEntrada.add(eRepresentanteLegal.getRegistroPoder());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eRepresentanteLegal.getInscripcionPoder1(),1));
			lstParametrosEntrada.add(eRepresentanteLegal.getInscripcionPoder2());
			lstParametrosEntrada.add(eRepresentanteLegal.getObservacion().replaceAll("\n", ""));
			
			lstParametrosEntrada.add(eRepresentanteLegal.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eRepresentanteLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eRepresentanteLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_REPRESENTANTELEGALSOLICITUDCREDITO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarRepresentanteLegal(ERepresentanteLegal eRepresentanteLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_REPRESENTANTELEGAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarRepresentanteLegalSolicitudCredito(ERepresentanteLegal eRepresentanteLegal,long numeroSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_REPRESENTANTELEGALSOLICITUDCREDITO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarFacultadPoder(ERepresentanteLegal eRepresentanteLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoCliente());
			lstParametrosEntrada.add(eRepresentanteLegal.getCodigoRepresentante());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_FACULTADPODER, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<ERepresentanteLegal> listarRepresentanteLegal(int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERepresentanteLegal oERepresentanteLegal= null;
		List<ERepresentanteLegal> lstRepresentanteLegal = null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_REPRESENTANTELEGAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRepresentanteLegal = new ArrayList<ERepresentanteLegal>();
				while (oResultSet.next()) {
					oERepresentanteLegal=new ERepresentanteLegal();
					oERepresentanteLegal.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oERepresentanteLegal.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oERepresentanteLegal.setCodigoRepresentante(oResultSet.getInt("CODREP"));
					oERepresentanteLegal.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLAREP")));
					oERepresentanteLegal.setDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oERepresentanteLegal.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBRE")));
					oERepresentanteLegal.setDescripcionIndicadorFirma(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCFLGFIR")));
					oERepresentanteLegal.setDocumentoRelacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCPER")));
					
					
					oERepresentanteLegal.setCodigoRepresentante(oResultSet.getInt("CODREP"));
					oERepresentanteLegal.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLAREP")));
					oERepresentanteLegal.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oERepresentanteLegal.setDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oERepresentanteLegal.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBRE")));
					oERepresentanteLegal.setNominativo(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESNOM")));
					oERepresentanteLegal.setDocumentoRelacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCPER")));
					oERepresentanteLegal.setDescripcionIndicadorFirma(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCFLGFIR")));
					
					oERepresentanteLegal.setFechaNacimiento(oResultSet.getDate("FECNAC"));
					oERepresentanteLegal.setCentroTrabajo(UFuncionesGenerales.revisaCadena(oResultSet.getString("TRAREP")));
					oERepresentanteLegal.setCargoLaboral(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARREP")));
					oERepresentanteLegal.setCodigoPaisNacimiento(oResultSet.getInt("REPNAC"));
					oERepresentanteLegal.setCodigoGenero(UFuncionesGenerales.revisaCadena(oResultSet.getString("SEXO")));
					oERepresentanteLegal.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(oResultSet.getString("ECIREP")));
					oERepresentanteLegal.setCodigoNivelInstruccion(oResultSet.getInt("NIVREP"));
					oERepresentanteLegal.setCodigoProfesion(oResultSet.getInt("PROREP"));
					
					oERepresentanteLegal.setDireccionComercial(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oERepresentanteLegal.setCodigoUbigeoComercial(oResultSet.getInt("CCIUDC"));
					oERepresentanteLegal.setCiudadComercial(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUDAC")));
					oERepresentanteLegal.setEstadoProvinciaComercial(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADC")));
					oERepresentanteLegal.setCodigoPostalComercial(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODP1C")));
					oERepresentanteLegal.setTelefonoComercial(UFuncionesGenerales.revisaCadena(oResultSet.getString("TELF1C")));
					oERepresentanteLegal.setFaxComercial(UFuncionesGenerales.revisaCadena(oResultSet.getString("NFAX1C")));
					
					oERepresentanteLegal.setDireccionDomiciliaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECD")));
					oERepresentanteLegal.setCodigoUbigeoDomiciliaria(oResultSet.getInt("CCIUDD"));
					oERepresentanteLegal.setCiudadDomiciliaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUDAD")));
					oERepresentanteLegal.setEstadoProvinciaDomiciliaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADD")));
					oERepresentanteLegal.setCodigoPostalDomiciliaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODP1D")));
					oERepresentanteLegal.setTelefonoDomiciliaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("TELF1D")));
					oERepresentanteLegal.setFaxDomiciliaria(UFuncionesGenerales.revisaCadena(oResultSet.getString("NFAX1D")));
					
					oERepresentanteLegal.setDireccionCorrespondencia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					oERepresentanteLegal.setCodigoUbigeoCorrespondencia(oResultSet.getInt("CCIUDP"));
					oERepresentanteLegal.setCiudadCorrespondencia(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUDAP")));
					oERepresentanteLegal.setEstadoProvinciaCorrespondencia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADP")));
					oERepresentanteLegal.setCodigoPostalCorrespondencia(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODP1P")));
					oERepresentanteLegal.setTelefonoCorrespondencia(UFuncionesGenerales.revisaCadena(oResultSet.getString("TELF1P")));
					oERepresentanteLegal.setFaxCorrespondencia(UFuncionesGenerales.revisaCadena(oResultSet.getString("NFAX1P")));
					
					oERepresentanteLegal.setNombreConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NCOREP")));
					oERepresentanteLegal.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TCOREP")));
					oERepresentanteLegal.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DCOREP")));
					oERepresentanteLegal.setTelefonoDirecto(UFuncionesGenerales.revisaCadena(oResultSet.getString("TE1REP")));
					oERepresentanteLegal.setTelefonoCentral(UFuncionesGenerales.revisaCadena(oResultSet.getString("TE2REP")));
					oERepresentanteLegal.setAnexo(oResultSet.getInt("ANEREP"));
					oERepresentanteLegal.setFax(UFuncionesGenerales.revisaCadena(oResultSet.getString("FAXREP")));
					
					oERepresentanteLegal.setIndicadorFirma(oResultSet.getInt("FLGFIR"));
					oERepresentanteLegal.setFechaIngreso(oResultSet.getDate("FECING"));
					oERepresentanteLegal.setNumeroFichaPoder(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICPOD")));
					oERepresentanteLegal.setRegistroPoder(UFuncionesGenerales.revisaCadena(oResultSet.getString("REGPOD")));
					oERepresentanteLegal.setInscripcionPoder1(UFuncionesGenerales.revisaCadena(oResultSet.getString("IN1POD")) +"\n"+
															UFuncionesGenerales.revisaCadena(oResultSet.getString("IN2POD")));
					oERepresentanteLegal.setInscripcionPoder2(UFuncionesGenerales.revisaCadena(oResultSet.getString("PIEPOD")));
					oERepresentanteLegal.setObservacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")) +"\n"+
														UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));
					
					oERepresentanteLegal.setApellidoPaternoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("APATRL")));
					oERepresentanteLegal.setApellidoMaternoRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("AMATRL")));
					oERepresentanteLegal.setNombreRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBRL")));
					oERepresentanteLegal.setRazonSocialRepresentante(UFuncionesGenerales.revisaCadena(oResultSet.getString("RZSORL")));
					
					lstRepresentanteLegal.add(oERepresentanteLegal);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRepresentanteLegal;
	}
	
	public List<EFacultadPoder> listarFacultadPoder(int codigoTipoCliente, int codigoCliente, int codigoRepresentante) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EFacultadPoder oEFacultadPoder= null;
		List<EFacultadPoder> lstFacultadPoder = null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(codigoRepresentante);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_FACULTADPODER, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstFacultadPoder = new ArrayList<EFacultadPoder>();
				while (oResultSet.next()) {
					oEFacultadPoder = new EFacultadPoder();
					oEFacultadPoder.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEFacultadPoder.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEFacultadPoder.setCodigoRepresentante(oResultSet.getInt("CODREP"));
					oEFacultadPoder.setCodigoFacultad(oResultSet.getInt("FACPOD"));
					oEFacultadPoder.setCodigoOtorgamiento(oResultSet.getInt("CODOTO"));
					oEFacultadPoder.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEFacultadPoder.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEFacultadPoder.setCodigoTipoFirma(oResultSet.getInt("FIRMAN"));
					//oEFacultadPoder.setCodigoRepresentanteRelacion(oResultSet.getInt(""));
					oEFacultadPoder.setObservaciones(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSPO1")));
					oEFacultadPoder.setFechaEmision(oResultSet.getDate("FECEMI"));
					
					oEFacultadPoder.setDescripcionFacultad(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCFACPOD")));
					oEFacultadPoder.setDescripcionOtorgamiento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCOTO")));
					oEFacultadPoder.setDescripcionServicio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSERVIC")));
					oEFacultadPoder.setDescripcionTipoFirma(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCFIRMAN")));
					oEFacultadPoder.setDescripcionRepresentanteRelacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCREPREL")));
					oEFacultadPoder.setCodigoTipoRelacion(oResultSet.getInt("CODTIPREL"));
					
					lstFacultadPoder.add(oEFacultadPoder);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstFacultadPoder;
	}
}
