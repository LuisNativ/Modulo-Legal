package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOPermisoUsuario extends InstanciaAcceso{
	
	private static final String SP_ABACOINLEGAL_INS_PERMISOUSUARIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_PERMISOUSUARIO("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_UPD_PERMISOUSUARIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_PERMISOUSUARIO("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_DEL_PERMISOUSUARIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_PERMISOUSUARIO("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_PERMISOUSUARIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_PERMISOUSUARIO() }";
	private static final String SP_ABACOINLEGAL_SEL_PERMISOUSUARIOPORAREA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_PERMISOUSUARIOPORAREA("+parametrosSP(1)+") }";
		
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
	
	public DAOPermisoUsuario(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregar(EPermisoUsuario ePermisoUsuario) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		Date hoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoUsuario());
			lstParametrosEntrada.add(ePermisoUsuario.getNombreUsuario());
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoAutonomia());
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoArea());
			lstParametrosEntrada.add(ePermisoUsuario.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(hoy);
			lstParametrosEntrada.add(formato.format(hoy));
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_PERMISOUSUARIO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificar(EPermisoUsuario ePermisoUsuario) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		Date hoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoUsuario());
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoAutonomia());
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoArea());
			lstParametrosEntrada.add(ePermisoUsuario.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(hoy);
			lstParametrosEntrada.add(formato.format(hoy));
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_PERMISOUSUARIO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminar(EPermisoUsuario ePermisoUsuario) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePermisoUsuario.getCodigoUsuario());
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_PERMISOUSUARIO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EPermisoUsuario> listar() {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		List<EPermisoUsuario> lstPermisoUsuario = null;
		EPermisoUsuario oEPermisoUsuario= null;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();

			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_PERMISOUSUARIO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstPermisoUsuario = new ArrayList<EPermisoUsuario>();
				while (oResultSet.next()) {
					oEPermisoUsuario=new EPermisoUsuario();
					oEPermisoUsuario.setCodigoUsuario(oResultSet.getInt("CODUSU"));
					oEPermisoUsuario.setNombreUsuario(oResultSet.getString("NOMUSU"));
					oEPermisoUsuario.setCodigoAutonomia(oResultSet.getInt("CODAUT"));
					oEPermisoUsuario.setCodigoArea(oResultSet.getInt("CODAREA"));
					lstPermisoUsuario.add(oEPermisoUsuario);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstPermisoUsuario;
	}
	
	public List<EPermisoUsuario> listarPorArea(EUsuario eUsuario) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		List<EPermisoUsuario> lstPermisoUsuario = null;
		EPermisoUsuario oEPermisoUsuario= null;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eUsuario.getCodigoArea());

			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_PERMISOUSUARIOPORAREA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstPermisoUsuario = new ArrayList<EPermisoUsuario>();
				while (oResultSet.next()) {
					oEPermisoUsuario=new EPermisoUsuario();
					oEPermisoUsuario.setCodigoUsuario(oResultSet.getInt("CODUSU"));
					oEPermisoUsuario.setNombreUsuario(oResultSet.getString("NOMUSU"));
					oEPermisoUsuario.setCodigoAutonomia(oResultSet.getInt("CODAUT"));
					oEPermisoUsuario.setCodigoArea(oResultSet.getInt("CODAREA"));
					lstPermisoUsuario.add(oEPermisoUsuario);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstPermisoUsuario;
	}
}
