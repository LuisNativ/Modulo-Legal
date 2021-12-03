package com.abaco.persistencia.control;

import java.util.List;

import com.abaco.dao.DAOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.dao.DAOListaDesplegable;
import com.abaco.persistencia.interfaces.IConexion;

public class CListaDesplegable {

	public List<EGeneral> obtieneListaDesplegableData(int codigo, int indicador) {
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		List<EGeneral> lstGeneral = objAListaDesplegable.obtieneListaDesplegableData(codigo, indicador);
		if (objConexion != null) {
			objConexion.cierraConexion();
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneListaDesplegableGeneral(int intDigIde, int indicador) {
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		List<EGeneral> lstGeneral = objAListaDesplegable.obtieneListaDesplegableGeneral(intDigIde, indicador);
		if (objConexion != null) {
			objConexion.cierraConexion();
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneListaDesplegableF0901(int intDigIde, int indicador) {
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		List<EGeneral> lstGeneral = objAListaDesplegable.obtieneListaDesplegableF0901(intDigIde, indicador);
		if (objConexion != null) {
			objConexion.cierraConexion();
		}
		return lstGeneral;
	}
	
	public List<EGeneral>obtieneUsuario(int indicadorEstado){
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneUsuario(indicadorEstado);
		} catch (Exception objEx) {
			UManejadorLog.error("Control: Error al recuperar: ", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral>obtieneUsuarioPorArea(int codigoArea, int indicadorEstado){
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneUsuarioPorArea(codigoArea, indicadorEstado);
		} catch (Exception objEx) {
			UManejadorLog.error("Control: Error al recuperar: ", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral>obtieneUsuarioPorAreaTipoEnvio(int codigoArea){
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneUsuarioPorAreaTipoEnvio(codigoArea);
		} catch (Exception objEx) {
			UManejadorLog.error("Control: Error al recuperar: ", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral>obtieneUsuarioPorAreaReasignacion(EUsuario eUsuario){
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneUsuarioPorAreaReasignacion(eUsuario);
		} catch (Exception objEx) {
			UManejadorLog.error("Control: Error al recuperar: ", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral>obtieneUsuarioPorPermisoUsuario(int indicadorEstado){
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneUsuarioPorPermisoUsuario(indicadorEstado);
		} catch (Exception objEx) {
			UManejadorLog.error("Control: Error al recuperar: ", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral>obtieneTipoEnvio(int accionExterna, long codigoUsuario){
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneTipoEnvio(accionExterna, codigoUsuario);
		} catch (Exception objEx) {
			UManejadorLog.error("Control: Error al recuperar: ", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneCtaAbacoBanco(int idMoneda) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneCtaAbacoBanco(idMoneda);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de banco.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneDepartamento() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneListaDepartamento();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Departamento.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneProvincia(int idDepartamento) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneListaProvincia(idDepartamento);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Provincia.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneDistrito(int idDepartamento, int idProvincia) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneListaDistrito(idDepartamento, idProvincia);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Distrito.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneActividad(int codigoCiu, String desCiu) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneActividad(codigoCiu, desCiu);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Actividad.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneTipoDocumentoFichaSocio() {		
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneTipoDocumentoFichaSocio();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Tipo de Documento.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtienePersonalAutorizar(int codRol) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtienePersonalAutorizar(codRol);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Provincia.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	/*
	public List<EGeneral> obtieneListaGeneral(int intCodTab) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneListaGeneral(intCodTab);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	*/
	
	public List<EGeneral> obtieneGrupoEconomico() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneGrupoEconomico();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener grupo economico.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneGrupoFamiliar() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneGrupoFamiliar();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener grupo familiar.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneZonaGeografica() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneZonaGeografica();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener zona geografica.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneEmailGerencia(int codRol) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneCorreoUsuario(codRol);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de los email de gerencia.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneMoneda() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(
				objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneMoneda();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de monedas.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneUnidad(String idDivision) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneListaUnidad(idDivision);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Unidad.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneSubUnidad(String idDivision, String idUnidad) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneListaSubUnidad(idDivision, idUnidad);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Sub Unidad.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneCentroTrabajo(int codigoCentra, String desCentra) {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneCentroTrabajo(codigoCentra, desCentra);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de Centro de Trabajo.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneFuncionario() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneFuncionario();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de funcionario.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
	
	public List<EGeneral> obtieneFuenteLoteProspecto() {
		List<EGeneral> lstGeneral = null;
		IConexion objConexion = FabricaConexion.creaConexion();
		DAOListaDesplegable objAListaDesplegable = new DAOListaDesplegable(objConexion);
		try {
			lstGeneral = objAListaDesplegable.obtieneFuenteLoteProspecto();
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Control: Error al obtener listado de las fuentes de los prospectos.", objEx);
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return lstGeneral;
	}
}
