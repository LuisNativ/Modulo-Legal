package com.abaco.ageneral;

import java.util.List;

import com.abaco.ageneral.DAORepresentanteLegal;
import com.abaco.dao.DAOGeneral;
import com.abaco.entidad.EDocumentoIdentidad;
import com.abaco.entidad.EFichaNatural;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;

public class CCliente {

	public EMensaje registrarTercero(ETercero eTercero) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOCliente = new DAOCliente(oIConexion);
			
			mensaje = oDAOCliente.registrarTercero(eTercero);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
		
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CCliente: Error al registrar Tercero : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarTercero(ETercero eTercero) {
			IConexion oIConexion = null;
			EMensaje mensaje = new EMensaje();
			DAOCliente oDAOCliente = null;
			try {
				oIConexion = FabricaConexion.creaConexion();
				oIConexion.iniciaTransaccion();
				oDAOCliente = new DAOCliente(oIConexion);
				
				mensaje = oDAOCliente.modificarTercero(eTercero);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			
				
				oIConexion.ejecutaCommit();
			} catch (Exception e) {
				if (oIConexion != null) {
					oIConexion.ejecutaRollback();
				}
				mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
				UManejadorLog.error("Control CCliente: Error al modificar Tercero : " + e.getMessage());
			} finally {
				if (oIConexion != null) {
					oIConexion.cierraConexion();
				}
			}
			return mensaje;
	}
	
	/*
	public List<EPersona> listarSocio(EPersonaParametro objEPersonaParam) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<EPersona> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			
			if (objEPersonaParam.getDocumentoPersona() == null) {
				EDocumentoIdentidad objEDocumentoIdentidad = new EDocumentoIdentidad();
				ETipoDocumentoPersona objETipoDocumentoPersona = new ETipoDocumentoPersona();
				objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
				objEPersonaParam.setDocumentoPersona(objEDocumentoIdentidad);
			}
			if (objEPersonaParam.getDocumentoPersona().getTipoDocumento() == null) {
				ETipoDocumentoPersona objETipoDocumentoPersona = new ETipoDocumentoPersona();
				objEPersonaParam.getDocumentoPersona().setTipoDocumento(objETipoDocumentoPersona);
			}
			
			resultado = oDAOCliente.listarSocio(objEPersonaParam);
		} catch (Exception e) {
			UManejadorLog.error("Error al listar socio: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	*/
	
	public List<EPersona> listarSocio(EPersonaParametro objEPersonaParam) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<EPersona> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			resultado = oDAOCliente.listarSocio(objEPersonaParam);
		} catch (Exception e) {
			UManejadorLog.error("Error al listar socio: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPersona> buscarSocioyTercero(int codigo, String descripcion) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<EPersona> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			resultado = oDAOCliente.listarSocioyTercero(codigo,descripcion) ;
		} catch (Exception e) {
			UManejadorLog.error("Error al listar socio: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ETercero> listarNotarios(int codigo, String descripcion) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<ETercero> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			resultado = oDAOCliente.listarNotarios(codigo,descripcion) ;
		} catch (Exception e) {
			UManejadorLog.error("Error al listar Notario: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPersona> listarPostulante(EPersonaParametro objEPersonaParam) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<EPersona> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			resultado = oDAOCliente.listarPostulante(objEPersonaParam);
		} catch (Exception e) {
			UManejadorLog.error("Error al listar socio: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPersona> listarTercero(EPersonaParametro objEPersonaParam, int codigoTipoBusqueda) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<EPersona> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			resultado = oDAOCliente.listarTercero(objEPersonaParam, codigoTipoBusqueda);
		} catch (Exception e) {
			UManejadorLog.error("Error al listar socio: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPersona> listarNoSocio(EPersonaParametro objEPersonaParam) {
		IConexion objConexion = null;
		DAOCliente oDAOCliente = null;
		List<EPersona> resultado = null;
		try {
			objConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(objConexion);
			resultado = oDAOCliente.listarNoSocio(objEPersonaParam);
		} catch (Exception e) {
			UManejadorLog.error("Error al listar no socio: " + e.getMessage());
		} finally {
			if (objConexion != null) {
				objConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ECliente buscarSocio(int codigoCliente){
		IConexion oIConexion = null;
		ECliente resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarSocio(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ECliente buscarUsuarioDetalle(EUsuario eUsuario){
		IConexion oIConexion = null;
		ECliente resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarUsuarioDetalle(eUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ECliente buscarPostulante(int codigoCliente){
		IConexion oIConexion = null;
		ECliente resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarPostulante(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ETercero buscarTercero(long codigoCliente){
		IConexion oIConexion = null;
		ETercero resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarTercero(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ECliente buscarNoSocio(int codigoCliente){
		IConexion oIConexion = null;
		ECliente resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarNoSocio(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ETercero buscarTerceroAnexo(long codigoCliente) {
		IConexion oIConexion = null;
		ETercero resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarTerceroAnexo(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ETercero buscarCliente_Info_PersonaNatural(long codigoCliente){
		IConexion oIConexion = null;
		ETercero resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarCliente_Info_PersonaNatural(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EClienteConstitucionEmpresa buscarConstitucionEmpresa(long codigoCliente){
		IConexion oIConexion = null;
		EClienteConstitucionEmpresa resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarConstitucionEmpresa(codigoCliente);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EUsuario buscarPermisoUsuario(String nombreUsuario){
		IConexion oIConexion = null;
		EUsuario resultado = null;
		DAOCliente oDAOCliente = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOCliente = new DAOCliente(oIConexion);
			resultado = oDAOCliente.buscarPermisoUsuario( nombreUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al recuperar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
