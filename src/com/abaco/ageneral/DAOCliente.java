package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EDireccion;
import com.abaco.entidad.EDocumentoIdentidad;
import com.abaco.entidad.EFichaNatural;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EHijo;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.ENombre;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EProductoCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.ETipoCliente;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UEstadoCivil;
import com.abaco.negocio.util.UConstante.UFlagResultado;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOCliente extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_TERCERO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_TERCERO("+parametrosSP(28)+") }";
	private static final String SP_ABACOINLEGAL_UPD_SOCIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_SOCIO("+parametrosSP(14)+") }";
	private static final String SP_ABACOINLEGAL_UPD_POSTULANTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_POSTULANTE("+parametrosSP(14)+") }";
	private static final String SP_ABACOINLEGAL_UPD_TERCERO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_TERCERO("+parametrosSP(29)+") }";
	private static final String FICHA_SEL_SOCIO = "{ CALL INTRANET.FICHA_SEL_SOCIO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_SOCIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOCIO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_POSTULANTE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_POSTULANTE(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_TERCERO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_TERCERO(?,?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_NOSOCIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_NOSOCIO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_SOCIOTERCERO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOCIOTERCERO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_NOTARIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_NOTARIA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_SOCIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOCIO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_POSTULANTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_POSTULANTE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_TERCERO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_TERCERO(?) }";
	private static final String SP_ABACOINLEGAL_BUS_NOSOCIO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_NOSOCIO(?) }";
	private static final String SP_ABACOINLEGAL_BUS_TERCERO_ANEXO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_TERCERO_ANEXO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CLIENTE_INFO_PERSONA_NATURAL ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CLIENTE_INFO_PERSONA_NATURAL("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_USUARIO_DETALLE ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_USUARIO_DETALLE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_SOCIO_CONSTITUCIONEMPRESA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOCIO_CONSTITUCIONEMPRESA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_AUTONOMIA_USUARIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_AUTONOMIA_USUARIO("+parametrosSP(1)+") }";

	
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
	
	public DAOCliente(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje registrarTercero(ETercero eTercero) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eTercero.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eTercero.getDocumento());
			lstParametrosEntrada.add(eTercero.getApellidoPaterno());
			lstParametrosEntrada.add(eTercero.getApellidoMaterno());
			lstParametrosEntrada.add(eTercero.getNombres());
			lstParametrosEntrada.add(eTercero.getNombreSuperLargo());
			lstParametrosEntrada.add(eTercero.getRuc());
			lstParametrosEntrada.add(eTercero.getDireccion());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeo());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeografica());
			lstParametrosEntrada.add(eTercero.getTelefono1());
			lstParametrosEntrada.add(Integer.parseInt(eTercero.getAnexo().isEmpty() ? "0": eTercero.getAnexo() ));
			lstParametrosEntrada.add(eTercero.getFax());
			lstParametrosEntrada.add(eTercero.getDireccionPostal());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeoPostal());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeograficaPostal());
			lstParametrosEntrada.add(eTercero.getEmail1());
			lstParametrosEntrada.add(eTercero.getEmail2());
			lstParametrosEntrada.add(eTercero.getCodigoTipoPersona());
			lstParametrosEntrada.add(eTercero.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eTercero.getCodigoSexo());
			lstParametrosEntrada.add(eTercero.getCodigoComunidad());
			lstParametrosEntrada.add(eTercero.getCodigoBanca());
			lstParametrosEntrada.add(eTercero.getTipoProveedor());
			lstParametrosEntrada.add(eTercero.getTipoAceptante());
			lstParametrosEntrada.add(eTercero.getUsuarioRegistro().getNombreUsuario());

			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_TERCERO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al registrar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarTercero(ETercero eTercero) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eTercero.getCodigoCliente());
			lstParametrosEntrada.add(eTercero.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eTercero.getDocumento());
			lstParametrosEntrada.add(eTercero.getApellidoPaterno());
			lstParametrosEntrada.add(eTercero.getApellidoMaterno());
			lstParametrosEntrada.add(eTercero.getNombres());
			lstParametrosEntrada.add(eTercero.getNombreSuperLargo());
			lstParametrosEntrada.add(eTercero.getRuc());
			lstParametrosEntrada.add(eTercero.getDireccion());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeo());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeografica());
			lstParametrosEntrada.add(eTercero.getTelefono1());
			lstParametrosEntrada.add(Integer.parseInt(eTercero.getAnexo().isEmpty() ? "0": eTercero.getAnexo() ));
			lstParametrosEntrada.add(eTercero.getFax());
			lstParametrosEntrada.add(eTercero.getDireccionPostal());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeoPostal());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeograficaPostal());
			lstParametrosEntrada.add(eTercero.getEmail1());
			lstParametrosEntrada.add(eTercero.getEmail2());
			lstParametrosEntrada.add(eTercero.getCodigoTipoPersona());
			lstParametrosEntrada.add(eTercero.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eTercero.getCodigoSexo());
			lstParametrosEntrada.add(eTercero.getCodigoComunidad());
			lstParametrosEntrada.add(eTercero.getCodigoBanca());
			lstParametrosEntrada.add(eTercero.getTipoProveedor());
			lstParametrosEntrada.add(eTercero.getTipoAceptante());
			lstParametrosEntrada.add(eTercero.getUsuarioRegistro().getNombreUsuario());

			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_TERCERO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al registrar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSocio(ECliente eCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eCliente.getCodigoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoClienteTemporal());
			lstParametrosEntrada.add(eCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eCliente.getDocumento());
			lstParametrosEntrada.add(eCliente.getRuc());
			lstParametrosEntrada.add(eCliente.getNombreCorto());
			lstParametrosEntrada.add(eCliente.getNombreLargo());
			/*
			lstParametrosEntrada.add(eCliente.getDireccion());
			lstParametrosEntrada.add(eCliente.getCodigoPostal());
			lstParametrosEntrada.add(eCliente.getTelefono());
			lstParametrosEntrada.add(eCliente.getFax());
			lstParametrosEntrada.add(eCliente.getCodigoUbigeo());
			*/
			lstParametrosEntrada.add(eCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_SOCIO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarPostulante(ECliente eCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eCliente.getCodigoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoClienteTemporal());
			lstParametrosEntrada.add(eCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eCliente.getDocumento());
			lstParametrosEntrada.add(eCliente.getRuc());
			lstParametrosEntrada.add(eCliente.getNombreCorto());
			lstParametrosEntrada.add(eCliente.getNombreLargo());
			/*
			lstParametrosEntrada.add(eCliente.getDireccion());
			lstParametrosEntrada.add(eCliente.getCodigoPostal());
			lstParametrosEntrada.add(eCliente.getTelefono());
			lstParametrosEntrada.add(eCliente.getFax());
			lstParametrosEntrada.add(eCliente.getCodigoUbigeo());
			*/
			lstParametrosEntrada.add(eCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_POSTULANTE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	/*
	public List<EPersona> listarSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales
					.convierteCadenaMayuscula(objEPersonaParam
							.getNombrePersona()));

			ResultSet objResultSet = objConexion.ejecutaConsulta(
					FICHA_SEL_SOCIO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objETipoDocumentoPersona.setNombreCorto(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("DESTIPDOC")));
					objETipoDocumentoPersona.setDescripcion(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("DESTIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					objEPersona.setCodigoEstado(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAESP")));
					objEPersona.setDescripcionEstado(UFuncionesGenerales.revisaCadena(objResultSet.getString("STATE")));

					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Socio: ", objEx);
		}
		return lstPersona;
	}
	*/
	
	public List<EPersona> listarSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));

			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOCIO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Tercero: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EPersona> listarSocioyTercero(int codigo, String descripcion) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);

			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOCIOTERCERO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
				/*	objETipoDocumentoPersona.setNombreCorto(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("DOCUME")));
					objETipoDocumentoPersona.setDescripcion(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("DOCUME")));*/
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Socio: ", objEx);
		}
		return lstPersona;
	}
	
	
	public List<ETercero> listarNotarios(int codigo, String descripcion) {
		List<ETercero> lstTercero = new ArrayList<ETercero>();
		ETercero objETercero = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);

			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_NOTARIA, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objETercero = new ETercero();
					
					objETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					objETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objETercero.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objETercero.setNombreCorte(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					objETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					/*objETercero.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					objETercero.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					objETercero.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					objETercero.setCodigoZonaGeografica(objResultSet.getInt("ZONGEO"));
					objETercero.setDireccionPostal(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					objETercero.setCodigoUbigeoPostal(objResultSet.getInt("CCIUDP"));
					objETercero.setCodigoZonaGeograficaPostal(objResultSet.getInt("ZONGEP"));
					objETercero.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					objETercero.setAnexo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELANE")));
					objETercero.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					objETercero.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					objETercero.setCodigoComunidad(UFuncionesGenerales.revisaCadena(objResultSet.getString("COMUNI")));
					objETercero.setCodigoBanca(UFuncionesGenerales.revisaCadena(objResultSet.getString("BANCA")));
					objETercero.setTipoProveedor(objResultSet.getInt("PROVEE"));
					objETercero.setTipoAceptante(objResultSet.getInt("ACEPTA"));
					objETercero.setCodigoEstadoCivil(objResultSet.getString("ESTCIV"));
					objETercero.setCodigoSexo(objResultSet.getString("SEXO"));
					objETercero.setEmail1(objResultSet.getString("EMAIL1"));
					objETercero.setEmail2(objResultSet.getString("EMAIL2"));*/
					

					lstTercero.add(objETercero);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el Listado de Notarios: ", objEx);
		}
		return lstTercero;
	}
	
	public List<EPersona> listarPostulante(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));
			
			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_POSTULANTE, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("TMPCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Postulante: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EPersona> listarTercero(EPersonaParametro objEPersonaParam, int codigoTipoBusqueda) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));
			lstParametrosEntrada.add(codigoTipoBusqueda);

			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_TERCERO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Tercero: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EPersona> listarNoSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));

			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_NOSOCIO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de no Socio: ", objEx);
		}
		return lstPersona;
	}
	
	public ECliente buscarSocio(int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOCIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oECliente.setNombreLargo2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setDireccion2(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeo2(objResultSet.getInt("CCIUDP"));
					oECliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("TDOCON")));
					oECliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCCON")));
					oECliente.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPCY")));
					oECliente.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMCY")));
					oECliente.setNombreConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCY")));
					oECliente.setNombreSuperLargoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMLCY")));
					oECliente.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("ESTCIV")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	
	public ECliente buscarUsuarioDetalle(EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eUsuario.getNombreUsuarioSIAF());
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_USUARIO_DETALLE, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
				    oECliente.setNombreSuperLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setDireccionPostal(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeoPostal(objResultSet.getInt("CCIUDP"));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setComunidad(UFuncionesGenerales.revisaCadena(objResultSet.getString("COMUNI")));
					oECliente.setEjecutivoControl(UFuncionesGenerales.revisaCadena(objResultSet.getString("OFICIA")));
					oECliente.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("ESTCIV")));
					oECliente.setCodigoGenero(UFuncionesGenerales.revisaCadena(objResultSet.getString("SEXO")));
					oECliente.setCodigoProfesion(objResultSet.getInt("PROFES"));
					oECliente.setDescripcionGenero(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCSEXO")));
					oECliente.setDescripcionEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCESTCIV")));
					oECliente.setDescripcionProfesion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCPROF")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	
	public ECliente buscarPostulante(int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_POSTULANTE, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
                    oECliente.setNombreLargo2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setDireccion2(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeo2(objResultSet.getInt("CCIUDP"));
					
					oECliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("TDOCON")));
					oECliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCCON")));
					oECliente.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPCY")));
					oECliente.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMCY")));
					oECliente.setNombreConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCY")));
					oECliente.setNombreSuperLargoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMLCY")));
					oECliente.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("ESTCIV")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	

	public ETercero buscarTercero(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ETercero oETercero = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_TERCERO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oETercero = new ETercero();
					oETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oETercero.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oETercero.setNombreCorte(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oETercero.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oETercero.setRuc2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORU8")));
					oETercero.setSiglas(UFuncionesGenerales.revisaCadena(objResultSet.getString("SIGLAS")));
					oETercero.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oETercero.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oETercero.setCodigoZonaGeografica(objResultSet.getInt("ZONGEO"));
					oETercero.setDireccionPostal(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oETercero.setCodigoUbigeoPostal(objResultSet.getInt("CCIUDP"));
					oETercero.setCodigoZonaGeograficaPostal(objResultSet.getInt("ZONGEP"));
					oETercero.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oETercero.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oETercero.setAnexo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELANE")));
					oETercero.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oETercero.setCodigoCIIU(objResultSet.getString("CODCIU"));
					oETercero.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oETercero.setCodigoComunidad(UFuncionesGenerales.revisaCadena(objResultSet.getString("COMUNI")));
					oETercero.setCodigoBanca(UFuncionesGenerales.revisaCadena(objResultSet.getString("BANCA")));
					oETercero.setTipoProveedor(objResultSet.getInt("PROVEE"));
					oETercero.setTipoAceptante(objResultSet.getInt("ACEPTA"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	public ECliente buscarNoSocio(int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_NOSOCIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					//oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					//oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					//oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					//oECliente.setNombreLargo2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setDireccion2(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeo2(objResultSet.getInt("CCIUDP"));
					/*
					oECliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("TDOCON")));
					oECliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCCON")));
					oECliente.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPCY")));
					oECliente.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMCY")));
					oECliente.setNombreConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCY")));
					oECliente.setNombreSuperLargoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMLCY")));
					*/
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	
	
	public ETercero buscarTerceroAnexo(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ETercero oETercero = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_TERCERO_ANEXO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oETercero = new ETercero();
					oETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oETercero.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oETercero.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oETercero.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oETercero.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oETercero.setNombres(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oETercero.setNombreSuperLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	
	public ETercero buscarCliente_Info_PersonaNatural(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ETercero oETercero = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CLIENTE_INFO_PERSONA_NATURAL, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oETercero = new ETercero();
					oETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oETercero.setCodigoEstadoCivil(objResultSet.getString("ESTCIV"));
					oETercero.setCodigoSexo(objResultSet.getString("SEXO"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	public EClienteConstitucionEmpresa buscarConstitucionEmpresa(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		EClienteConstitucionEmpresa oEClienteConstitucionEmpresa = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOCIO_CONSTITUCIONEMPRESA, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oEClienteConstitucionEmpresa = new EClienteConstitucionEmpresa();
					oEClienteConstitucionEmpresa.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oEClienteConstitucionEmpresa.setInscripcionRegistroPublico(UFuncionesGenerales.revisaCadena(objResultSet.getString("INREPU")));
					oEClienteConstitucionEmpresa.setFechaConstitucion(objResultSet.getDate("FECCIA"));
					oEClienteConstitucionEmpresa.setCodigoNotario(objResultSet.getInt("CODNOT"));
					oEClienteConstitucionEmpresa.setDescripcionNotario(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMNOT")));
					oEClienteConstitucionEmpresa.setOficinaRegistral(UFuncionesGenerales.revisaCadena(objResultSet.getString("OFIREG")));
					oEClienteConstitucionEmpresa.setNumeroAcciones(objResultSet.getInt("ACCION"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEClienteConstitucionEmpresa;
	}
	
	
	
	public EUsuario buscarPermisoUsuario(String nombreUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		EUsuario oEUsuario = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(nombreUsuario);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_AUTONOMIA_USUARIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oEUsuario = new EUsuario();
					oEUsuario.setCodigoCliente(objResultSet.getInt("CODUSU"));
					oEUsuario.setIdUsuario(objResultSet.getInt("CODUSU"));
					oEUsuario.setCodigoAutonomia(objResultSet.getInt("CODAUT"));
					oEUsuario.setCodigoArea(objResultSet.getInt("CODAREA"));

				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEUsuario;
	}
	
	
}
