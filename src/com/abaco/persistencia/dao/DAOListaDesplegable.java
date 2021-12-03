package com.abaco.persistencia.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.ConexionDB2;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOListaDesplegable extends InstanciaAcceso {
	
	private static final String SP_ABACOINLEGAL_SEL_GENERAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GENERAL(?) }";
	private static final String SP_ABACOINLEGAL_SEL_TGENERAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_TGENERAL(?) }";
	private static final String SP_ABACOINLEGAL_SEL_F0901 = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_F0901(?) }";
	
	private static final String SP_ABACOINLEGAL_SEL_USUARIO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_USUARIO(?) }";
	private static final String SP_ABACOINLEGAL_SEL_USUARIOPORAREA ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_USUARIOPORAREA(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_USUARIOPORAREATIPOENVIO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_USUARIOPORAREATIPOENVIO(?) }";
	private static final String SP_ABACOINLEGAL_SEL_USUARIOPORAREAREASIGNACION ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_USUARIOPORAREAREASIGNACION(?,?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_USUARIOPORPERMISOUSUARIO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_USUARIOPORPERMISOUSUARIO(?) }";
	private static final String SP_ABACOINLEGAL_SEL_ENVIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_ENVIO(?,?) }";
	
	private static final String SP_SEL_CTAS_ABACO_BANCO = "{ CALL INTRANET.SP_SEL_CTAS_ABACO_BANCO(?) }";	
	private static final String SP_SEL_DEPARTAMENTO = "{ CALL INTRANET.SP_SEL_DEPARTAMENTO() }";
	private static final String SP_SEL_PROVINCIA = "{ CALL INTRANET.SP_SEL_PROVINCIA(?) }";		
	private static final String SP_SEL_DISTRITO = "{ CALL INTRANET.SP_SEL_DISTRITO(?,?) }";	
	private static final String SP_SEL_ACTIVIDAD = "{ CALL INTRANET.FICHA_SEL_ACTIVIDAD(?,?) }";
	private static final String SP_SEL_TIPDOC = "{CALL INTRANET.FICHA_SEL_TIPDOC()}";	
	private static final String SP_SEL_USU_AUTORIZAR = "{ CALL INTRANET.FICHA_SEL_USU_AUTORIZAR(?) }";		
	private static final String SP_SEL_TABLA_GENERICA = "{ CALL INTRANET.SP_SEL_TABLA_GENERICA(?) }";
	private static final String SP_SEL_GRUPO_ECONOMICO = "{ CALL APP_WEBSI.SP_SEL_GRUPOECONOMICO() }";
	private static final String SP_SEL_GRUPO_FAMILIAR = "{ CALL APP_WEBSI.SP_SEL_GRUPO_FAMILIAR() }";
	private static final String SP_SEL_ZONA_GEOGRAFICA = "{ CALL APP_WEBSI.SP_SEL_ZONA_GEOGRAFICA() }";
	private static final String SP_SEL_CORREO_X_ROL = "{ CALL INTRANET.SP_SEL_CORREO_X_ROL(?) }";
	private static final String SP_SEL_MONEDA = "{ CALL INTRANET.SP_SEL_MONEDA }";
	private static final String SP_SEL_UNIDAD = "{ CALL INTRANET.SP_SEL_UNIDAD(?) }";
	private static final String SP_SEL_SUBUNIDAD = "{ CALL INTRANET.SP_SEL_SUBUNIDAD(?,?) }";	
	private static final String SP_SEL_CENTRO_TRABAJO = "{ CALL INTRANET.FICHA_SEL_CENTRO_TRABAJO(?,?) }";
	private static final String SP_SEL_FUNCIONARIO = "{ CALL INTRANET.PP_SEL_FUNCIONARIO }";
	private static final String PP_SEL_LOTE_FUENTE = "{ CALL INTRANET.PP_SEL_LOTE_FUENTE }";
		
	
	public DAOListaDesplegable(IConexion objConexion) {
		super(objConexion);
	}
	
	private List<EGeneral> obtieneListaGeneral1(String strStoredProcedure, List<Object> lstParametrosEntrada) {
		List<EGeneral> lstGeneral = null;
		ConexionDB2 objConexion = new ConexionDB2();
		ResultSet objResultSet = null;
		try {
			objResultSet = objConexion.ejecutaConsulta(strStoredProcedure, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstGeneral = new ArrayList<EGeneral>();
				while (objResultSet.next()) {
					EGeneral objGeneral = new EGeneral();
					objGeneral.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("CODIGO")));
					objGeneral.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRE"))); //NOMBRE
					objGeneral.setDescripcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCRIPCION")));
					lstGeneral.add(objGeneral);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Error al ejecutar el procedimiento " + strStoredProcedure + ": " , objEx);
		}
		objConexion.cierraConexion();
		return lstGeneral;
	}
	
	private List<EGeneral> obtieneListaGeneral2(String strStoredProcedure, List<Object> lstParametrosEntrada) {
		List<EGeneral> lstGeneral = null;
		ConexionDB2 objConexion = new ConexionDB2();
		ResultSet objResultSet = null;
		try {
			objResultSet = objConexion.ejecutaConsulta(strStoredProcedure, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstGeneral = new ArrayList<EGeneral>();
				while (objResultSet.next()) {
					EGeneral objGeneral = new EGeneral();
					objGeneral.setCodigo2(objResultSet.getInt("CODIGO"));
					objGeneral.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRE")));
					objGeneral.setDescripcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCRIPCION")));
					lstGeneral.add(objGeneral);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Error al ejecutar el procedimiento " + strStoredProcedure + ": " , objEx);
		}
		objConexion.cierraConexion();
		return lstGeneral;
	}
	
	private List<EGeneral> obtieneListaGeneral3(String strStoredProcedure, List<Object> lstParametrosEntrada) {
		List<EGeneral> lstGeneral = null;
		ConexionDB2 objConexion = new ConexionDB2();
		ResultSet objResultSet = null;
		try {
			objResultSet = objConexion.ejecutaConsulta(strStoredProcedure, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstGeneral = new ArrayList<EGeneral>();
				while (objResultSet.next()) {
					EGeneral objGeneral = new EGeneral();
					objGeneral.setCodigo3(objResultSet.getInt("CODIGO"));
					objGeneral.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRE")));
					objGeneral.setDescripcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCRIPCION")));
					lstGeneral.add(objGeneral);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Error al ejecutar el procedimiento " + strStoredProcedure + ": " , objEx);
		}
		objConexion.cierraConexion();
		return lstGeneral;
	}
	
	//Se usa para listar procedimientos de intranet
	private List<EGeneral> obtieneListaGeneral5(String strStoredProcedure, List<Object> lstParametrosEntrada) {
		List<EGeneral> lstGeneral = null;
		ConexionDB2 objConexion = new ConexionDB2();
		ResultSet objResultSet = null;
		try {
			objResultSet = objConexion.ejecutaConsulta(strStoredProcedure, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstGeneral = new ArrayList<EGeneral>();
				while (objResultSet.next()) {
					EGeneral objGeneral = new EGeneral();
					objGeneral.setCodigo2(objResultSet.getInt("CODIGO"));
					objGeneral.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRE")));
					objGeneral.setDescripcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRE")));
					lstGeneral.add(objGeneral);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Error al ejecutar el procedimiento " + strStoredProcedure + ": " , objEx);
		}
		objConexion.cierraConexion();
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneListaDesplegableData(int codigo, int indicador) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		List<EGeneral> lstGeneral = null;
		lstParametrosEntrada.add(codigo);
		
		if(indicador == 1){
			lstGeneral = obtieneListaGeneral1(SP_ABACOINLEGAL_SEL_GENERAL, lstParametrosEntrada);
		}else if(indicador == 2){
			lstGeneral = obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_GENERAL, lstParametrosEntrada);
		}					
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneListaDesplegableGeneral(int intDigIde, int indicador) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		List<EGeneral> lstGeneral = null;
		lstParametrosEntrada.add(intDigIde);
		
		if(indicador == 1){
			lstGeneral = obtieneListaGeneral1(SP_ABACOINLEGAL_SEL_TGENERAL, lstParametrosEntrada);
		}else if(indicador == 2){
			lstGeneral = obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_TGENERAL, lstParametrosEntrada);
		}					
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneListaDesplegableF0901(int intDigIde, int indicador) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		List<EGeneral> lstGeneral = null;
		lstParametrosEntrada.add(intDigIde);
		
		if(indicador == 1){
			lstGeneral = obtieneListaGeneral1(SP_ABACOINLEGAL_SEL_F0901, lstParametrosEntrada);
		}else if(indicador == 2){
			lstGeneral = obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_F0901, lstParametrosEntrada);
		}					
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneUsuario(int indicadorEstado) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(indicadorEstado);
		return obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_USUARIO,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneUsuarioPorArea(int codigoArea, int indicadorEstado) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(codigoArea);
		lstParametrosEntrada.add(indicadorEstado);
		return obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_USUARIOPORAREA,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneUsuarioPorAreaTipoEnvio(int codigoArea) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(codigoArea);
		return obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_USUARIOPORAREATIPOENVIO,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneUsuarioPorAreaReasignacion(EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(eUsuario.getCodigoArea());
		lstParametrosEntrada.add(eUsuario.getIdUsuario());
		lstParametrosEntrada.add(eUsuario.getNombreUsuarioSIAF());
		return obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_USUARIOPORAREAREASIGNACION,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneUsuarioPorPermisoUsuario(int indicadorEstado) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(indicadorEstado);
		return obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_USUARIOPORPERMISOUSUARIO,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneTipoEnvio(int accionExterna, long codigoUsuario) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(accionExterna);
		lstParametrosEntrada.add(codigoUsuario);
		return obtieneListaGeneral2(SP_ABACOINLEGAL_SEL_ENVIO,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneCtaAbacoBanco(int idMoneda) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(idMoneda);	
		return obtieneListaGeneral1(SP_SEL_CTAS_ABACO_BANCO,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneListaDepartamento() {		
		return obtieneListaGeneral5(SP_SEL_DEPARTAMENTO,null);
	}
	
	public List<EGeneral> obtieneListaProvincia(int idDepartamento) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(idDepartamento);	
		return obtieneListaGeneral5(SP_SEL_PROVINCIA,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneListaDistrito(int idDepartamento, int idProvincia) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(idDepartamento);	
		lstParametrosEntrada.add(idProvincia);
		return obtieneListaGeneral5(SP_SEL_DISTRITO,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneActividad(int codigoCiu, String desCiu) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(UFuncionesGenerales.completaNumeroCerosIzquierda(String.valueOf(codigoCiu), 4));	
		lstParametrosEntrada.add(UFuncionesGenerales.revisaCadena(desCiu));
		return obtieneListaGeneral1(SP_SEL_ACTIVIDAD, lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneTipoDocumentoFichaSocio() {		
		return obtieneListaGeneral1(SP_SEL_TIPDOC,null);
	}
	
	public List<EGeneral> obtienePersonalAutorizar(int codRol) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(codRol);	
		return obtieneListaGeneral2(SP_SEL_USU_AUTORIZAR,lstParametrosEntrada);
	}
	
	/*
	public List<EGeneral> obtieneListaGeneral(int intCodTab) {
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(intCodTab);
		return obtieneListaGeneral1(SP_SEL_TABLA_GENERICA, lstParametrosEntrada);
	}
	*/
	
	public List<EGeneral> obtieneGrupoEconomico() {		
		return obtieneListaGeneral2(SP_SEL_GRUPO_ECONOMICO, null);
	}
	
	public List<EGeneral> obtieneGrupoFamiliar() {		
		return obtieneListaGeneral2(SP_SEL_GRUPO_FAMILIAR, null);
	}
	
	public List<EGeneral> obtieneZonaGeografica() {		
		return obtieneListaGeneral2(SP_SEL_ZONA_GEOGRAFICA, null);
	}
	
	public List<EGeneral> obtieneCorreoUsuario(int codRol) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(codRol);	
		return obtieneListaGeneral1(SP_SEL_CORREO_X_ROL,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneMoneda() {
		return obtieneListaGeneral1(SP_SEL_MONEDA, null);
	}
	
	public List<EGeneral> obtieneListaUnidad(String idDivision) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(idDivision);	
		return obtieneListaGeneral1(SP_SEL_UNIDAD,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneListaSubUnidad(String idDivision, String idUnidad) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(idDivision);	
		lstParametrosEntrada.add(idUnidad);	
		return obtieneListaGeneral1(SP_SEL_SUBUNIDAD,lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneCentroTrabajo(int codigoCentra, String desCentra) {		
		List<Object> lstParametrosEntrada = new ArrayList<Object>();
		lstParametrosEntrada.add(codigoCentra);	
		lstParametrosEntrada.add(UFuncionesGenerales.revisaCadena(desCentra));
		return obtieneListaGeneral1(SP_SEL_CENTRO_TRABAJO, lstParametrosEntrada);
	}
	
	public List<EGeneral> obtieneFuncionario() {
		return obtieneListaGeneral1(SP_SEL_FUNCIONARIO, null);
	}
	
	public List<EGeneral> obtieneFuenteLoteProspecto() {
		return obtieneListaGeneral1(PP_SEL_LOTE_FUENTE, null);
	}
}
