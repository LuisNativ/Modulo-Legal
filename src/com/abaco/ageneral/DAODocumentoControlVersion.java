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

public class DAODocumentoControlVersion extends InstanciaAcceso{
	
	private static final String SP_ABACOINLEGAL_INS_DOCUMENTOCONTROLVERSION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_DOCUMENTOCONTROLVERSION("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_SEL_DOCUMENTOCONTROLVERSION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DOCUMENTOCONTROLVERSION() }";
		
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
	
	public DAODocumentoControlVersion(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregar(EDocumentoControlVersion eDocumentoControlVersion) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		Date hoy = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eDocumentoControlVersion.getCodigoTipoEvaluacion());
			lstParametrosEntrada.add(eDocumentoControlVersion.getNombreDocumento());
			lstParametrosEntrada.add(eDocumentoControlVersion.getObservacionDocumento());
			lstParametrosEntrada.add(eDocumentoControlVersion.getDataDocumento());
			lstParametrosEntrada.add(eDocumentoControlVersion.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(hoy);
			lstParametrosEntrada.add(formato.format(hoy));
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_DOCUMENTOCONTROLVERSION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public List<EDocumentoControlVersion> listar() {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		List<EDocumentoControlVersion> lstDocumentoControlVersion = null;
		EDocumentoControlVersion oEDocumentoControlVersion= null;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();

			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DOCUMENTOCONTROLVERSION, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstDocumentoControlVersion = new ArrayList<EDocumentoControlVersion>();
				while (oResultSet.next()) {
					oEDocumentoControlVersion=new EDocumentoControlVersion();
					oEDocumentoControlVersion.setCodigoTipoEvaluacion(oResultSet.getInt("CODTEVAL"));
					oEDocumentoControlVersion.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEDocumentoControlVersion.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEDocumentoControlVersion.setObservacionDocumento(oResultSet.getString("OBSDOC"));
					oEDocumentoControlVersion.setDataDocumento(oResultSet.getBytes("NDATA"));
					lstDocumentoControlVersion.add(oEDocumentoControlVersion);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstDocumentoControlVersion;
	}
}
