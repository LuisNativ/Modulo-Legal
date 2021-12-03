package com.abaco.ageneral;

import java.util.List;

import com.abaco.ageneral.DAORepresentanteLegal;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;

public class CRepresentanteLegal {
	public List<ERepresentanteLegal> listarRepresentanteLegal(int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		List<ERepresentanteLegal> resultado = null;
		DAORepresentanteLegal oDAORepresentanteLegal= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORepresentanteLegal = new DAORepresentanteLegal(oIConexion);
			resultado = oDAORepresentanteLegal.listarRepresentanteLegal(codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar representante legal " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EFacultadPoder> listarFacultadPoder(int codigoTipoCliente, int codigoCliente, int codigoRepresentante){
		IConexion oIConexion = null;
		List<EFacultadPoder> resultado = null;
		DAORepresentanteLegal oDAORepresentanteLegal= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAORepresentanteLegal = new DAORepresentanteLegal(oIConexion);
			resultado = oDAORepresentanteLegal.listarFacultadPoder(codigoTipoCliente, codigoCliente, codigoRepresentante);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar representante legal " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
