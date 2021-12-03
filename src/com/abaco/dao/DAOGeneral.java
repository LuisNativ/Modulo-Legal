package com.abaco.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abaco.ageneral.EDocumentoRequerido;
import com.abaco.ageneral.EOperacionDocumento;
import com.abaco.ageneral.EOperacionDocumentoRequerido;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.ageneral.EServicio;
import com.abaco.ageneral.ETercero;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EGeneralTipoCambio;
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOGeneral extends InstanciaAcceso {
	
	private static final String SP_SEL_GENERAL_TIPO_CAMBIO = "{ CALL INTRANET.SP_SEL_GENERAL_TIPO_CAMBIO (?,?,?) }";
	private static final String SP_GET_PLANTILLA_EMAIL = "{ CALL INTRANET.SP_GET_PLANTILLA_EMAIL(?) }";
	private static final String SP_GET_CORREO_JEFE_INMEDIATO = "{ CALL INTRANET.SP_GET_CORREO_JEFE_INMEDIATO (?) }";
	private static final String SP_ABACOINLEGAL_SEL_USUARIOPORAREAREASIGNACION ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_USUARIOPORAREAREASIGNACION(?,?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_DOCUMENTOREQUERIDOPORTIPOEVALUACION ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DOCUMENTOREQUERIDOPORTIPOEVALUACION(?) }";
	private static final String SP_ABACOINLEGAL_SEL_DOCUMENTOREQUERIDO_SOLICITUDCREDITO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DOCUMENTOREQUERIDO_SOLICITUDCREDITO() }";
	private static final String SP_ABACOINLEGAL_SEL_SERVICIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SERVICIO() }";
	private static final String SP_ABACOINLEGAL_BUS_RUTAWEB ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_RUTAWEB(?) }";
	private static final String SP_ABACOINLEGAL_BUS_CORREOUSUARIO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CORREOUSUARIO(?) }";
	private static final String SP_ABACOINLEGAL_BUS_NOMBREUSUARIOSIAF ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_NOMBREUSUARIOSIAF(?) }";
	private static final String SP_ABACOINLEGAL_BUS_CORRELATIVO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CORRELATIVO(?,?,?,?) }";
	
	public DAOGeneral(IConexion objConexion) {
		super(objConexion);
	}
	
	public List<EUsuario> listarUsuarioPorAreaReasignacion(EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EUsuario oEUsuario = null;
		List<EUsuario> lstUsuario = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eUsuario.getCodigoArea());
			lstParametrosEntrada.add(eUsuario.getIdUsuario());
			lstParametrosEntrada.add(eUsuario.getNombreUsuarioSIAF());
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_USUARIOPORAREAREASIGNACION, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstUsuario = new ArrayList<EUsuario>();
				while (oResultSet.next()) {
					oEUsuario = new EUsuario();
					oEUsuario.setIdUsuario(oResultSet.getInt("CODIGO"));
					oEUsuario.setNombreUsuarioSIAF(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBRE")));
					oEUsuario.setNombreCompleto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRIPCION")));
					oEUsuario.setCodigoArea(oResultSet.getInt("CODAREA"));
					lstUsuario.add(oEUsuario);
				}								
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstUsuario;
	}
	
	/*
	public List<EOperacionDocumentoRequerido> listarDocumentoRequeridoPorTipoEvaluacion(int codigoTipoEvaluacion) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EOperacionDocumentoRequerido oEOperacionDocumentoRequerido = null;
		List<EOperacionDocumentoRequerido> lstDocumento = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoTipoEvaluacion);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DOCUMENTOREQUERIDOPORTIPOEVALUACION, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstDocumento = new ArrayList<EOperacionDocumentoRequerido>();
				while (oResultSet.next()) {
					oEOperacionDocumentoRequerido = new EOperacionDocumentoRequerido();
					oEOperacionDocumentoRequerido.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionDocumentoRequerido.setNombreDocumento(oResultSet.getString("NOMDOC"));
					lstDocumento.add(oEOperacionDocumentoRequerido);
				}								
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstDocumento;
	}
	*/
	
	public List<EDocumentoRequerido> listarDocumentoRequeridoSolicitudCredito() {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EDocumentoRequerido oEDocumentoRequerido = null;
		List<EDocumentoRequerido> lstDocumentoRequerido = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DOCUMENTOREQUERIDO_SOLICITUDCREDITO, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstDocumentoRequerido = new ArrayList<EDocumentoRequerido>();
				while (oResultSet.next()) {
					oEDocumentoRequerido = new EDocumentoRequerido();
					oEDocumentoRequerido.setCodigoDocumentoRequerido(oResultSet.getInt("CODDOCREQ"));
					oEDocumentoRequerido.setDescripcionDocumentoRequerido(oResultSet.getString("DESCDOCREQ"));
					lstDocumentoRequerido.add(oEDocumentoRequerido);
				}								
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstDocumentoRequerido;
	}
	
	public List<EServicio> listarServicio() {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EServicio oEServicio= null;
		List<EServicio> lstServicio = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SERVICIO, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstServicio = new ArrayList<EServicio>();
				while (oResultSet.next()) {
					oEServicio = new EServicio();
					oEServicio.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEServicio.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEServicio.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMONEDA")));
					oEServicio.setDescripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DSERVL")));
					lstServicio.add(oEServicio);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstServicio;
	}
	
	public EGeneralTipoCambio buscarGeneralTipoCambio(int anio, int mes, int moneda, String dia) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EGeneralTipoCambio objeto = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(anio);
			lstParametrosEntrada.add(mes);
			lstParametrosEntrada.add(moneda);
			oResultSet = objConexion.ejecutaConsulta(SP_SEL_GENERAL_TIPO_CAMBIO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					objeto = new EGeneralTipoCambio();
					objeto.setCompra(oResultSet.getDouble("TCAW" + dia));
					objeto.setVenta(oResultSet.getDouble("TCVW" + dia));
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Error al obtener la lista de workflows: ", objEx);
		}
		return objeto;
	}
	
	public EPlantillaEmail buscarPlantillaEmail(int codigoMsg) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		EPlantillaEmail plantillaEmail = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoMsg);
			objResultSet = objConexion.ejecutaConsulta(SP_GET_PLANTILLA_EMAIL, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					plantillaEmail = new EPlantillaEmail();
					plantillaEmail.setCodigoMsg(objResultSet.getDouble("CODMSG"));
					plantillaEmail.setAsunto(UFuncionesGenerales.revisaCadena(objResultSet.getString("ASUNTO")));
					plantillaEmail.setCuerpo(UFuncionesGenerales.revisaCadena(objResultSet.getString("CUERPO")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Error al obtener la plantilla de emails: ", objEx);
		}
		return plantillaEmail;
	}
	
	public String buscarCorreoJefeInmediato(String usuario){
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		String correo = "";
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(usuario);
			oResultSet = objConexion.ejecutaConsulta(SP_GET_CORREO_JEFE_INMEDIATO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					correo = UFuncionesGenerales.revisaCadena(oResultSet.getString("EMAIL"));
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Error al obtener el correo: ", objEx);
		}
		return correo;
	}
	
	public String buscarRutaWeb(long codigoItem) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		String resultado = "";
		try {	
			lstParametrosEntrada = new ArrayList<Object>();						
			lstParametrosEntrada.add(codigoItem);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_RUTAWEB, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					resultado = oResultSet.getString("A018_RUTA_WEB");
				}								
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return resultado;
	}
	
	public String buscarCorreoUsuario(int codigoUsuario){
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		String correo = "";
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoUsuario);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CORREOUSUARIO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					correo = UFuncionesGenerales.revisaCadena(oResultSet.getString("A020_EMAIL"));
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Error al obtener el correo: ", objEx);
		}
		return correo;
	}
	
	public String buscarNombreUsuarioSiaf(int codigoUsuario){
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		String nombreUsuarioSiaf = "";
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoUsuario);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_NOMBREUSUARIOSIAF, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					nombreUsuarioSiaf = UFuncionesGenerales.revisaCadena(oResultSet.getString("A015_USUARI"));
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Error al obtener el nombre usuario siaf: ", objEx);
		}
		return nombreUsuarioSiaf;
	}
	
	public int generarCorrelativo(int tabla, String codigo1, String codigo2, String codigo3) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		int correlativo = 0;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(tabla);
			lstParametrosEntrada.add(codigo1);
			lstParametrosEntrada.add(codigo2);
			lstParametrosEntrada.add(codigo3);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CORRELATIVO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					correlativo = oResultSet.getInt("CORRELATIVO");
				}								
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return correlativo;
	}
}
