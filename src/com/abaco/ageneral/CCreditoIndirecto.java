package com.abaco.ageneral;

import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.bo.BOGeneral;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;

public class CCreditoIndirecto {
	public List<ECreditoIndirecto> listarCreditoIndirectoPorSolicitar(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<ECreditoIndirecto> resultado = null;
		DAOCreditoIndirecto oDAOCreditoIndirecto= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOCreditoIndirecto = new DAOCreditoIndirecto(oIConexion);
			resultado = oDAOCreditoIndirecto.listarCreditoIndirectoPorSolicitar(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ECreditoIndirecto buscarCreditoIndirecto(long codigo){
		IConexion oIConexion = null;
		ECreditoIndirecto resultado = null;
		DAOCreditoIndirecto oDAOCreditoIndirecto= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOCreditoIndirecto = new DAOCreditoIndirecto(oIConexion);
			resultado = oDAOCreditoIndirecto.buscarCreditoIndirecto(codigo);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener credito indirecto " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
