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
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.ETipoCliente;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOCreditoIndirecto extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_SEL_CREDITOINDIRECTOPORSOLICITAR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITOINDIRECTOPORSOLICITAR("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CREDITOINDIRECTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CREDITOINDIRECTO("+parametrosSP(1)+") }";
	
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
	
	public DAOCreditoIndirecto(IConexion objConexion) {
		super(objConexion);
	}
	
	public List<ECreditoIndirecto> listarCreditoIndirectoPorSolicitar(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ECreditoIndirecto oECreditoIndirecto= null;
		List<ECreditoIndirecto> lstCreditoIndirecto = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITOINDIRECTOPORSOLICITAR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstCreditoIndirecto = new ArrayList<ECreditoIndirecto>();
				while (oResultSet.next()) {
					oECreditoIndirecto = new ECreditoIndirecto();
					oECreditoIndirecto.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oECreditoIndirecto.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oECreditoIndirecto.setNumero(oResultSet.getInt("NUMERO"));
					oECreditoIndirecto.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oECreditoIndirecto.setDescripcionServicio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSERV")));
					oECreditoIndirecto.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oECreditoIndirecto.setMontoCredito(oResultSet.getDouble("MONCRE"));
					oECreditoIndirecto.setMontoFianza(oResultSet.getDouble("MONTAV"));
					
					lstCreditoIndirecto.add(oECreditoIndirecto);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstCreditoIndirecto;
	}
	
	public ECreditoIndirecto buscarCreditoIndirecto(long codigo){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ECreditoIndirecto oECreditoIndirecto= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CREDITOINDIRECTO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oECreditoIndirecto=new ECreditoIndirecto();
					oECreditoIndirecto.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oECreditoIndirecto.setNumero(oResultSet.getInt("NUMERO"));
					
					oECreditoIndirecto.setCodigoBeneficiario(oResultSet.getInt("CODBEN"));
					oECreditoIndirecto.setNombreBeneficiario(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBEN")));
					oECreditoIndirecto.setCodigoAfianzado(oResultSet.getInt("CODAFI"));
					oECreditoIndirecto.setNombreAfianzado(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMAFI")));
					
					oECreditoIndirecto.setMontoCredito(oResultSet.getDouble("MONCRE"));
					oECreditoIndirecto.setMontoFianza(oResultSet.getDouble("MONTAV"));
					oECreditoIndirecto.setMontoHonramiento(oResultSet.getDouble("MONTHO"));
					oECreditoIndirecto.setMontoGastoHonramiento(oResultSet.getDouble("GASFIA"));
					oECreditoIndirecto.setMontoGastoBancoHonramiento(oResultSet.getDouble("GAFBCO"));
					oECreditoIndirecto.setMontoGarantizado(oResultSet.getDouble("MGARDO"));
					oECreditoIndirecto.setMontoAcumuladoComision(oResultSet.getDouble("MTOCOB"));
					
					oECreditoIndirecto.setFechaIngreso(oResultSet.getDate("FECING"));
					oECreditoIndirecto.setFechaVigencia(oResultSet.getDate("FECVIG"));
					oECreditoIndirecto.setFechaImpresion(oResultSet.getDate("FECIMP"));
					oECreditoIndirecto.setFechaUltimoProrroga(oResultSet.getDate("FECPRO"));
					oECreditoIndirecto.setFechaVencimiento(oResultSet.getDate("FECVTO"));
					oECreditoIndirecto.setFechaCancelacion(oResultSet.getDate("FECCAN"));
					oECreditoIndirecto.setFechaRequerimiento(oResultSet.getDate("FECREQ"));
					oECreditoIndirecto.setFechaUltimoComision(oResultSet.getDate("FECCOM"));
					
					oECreditoIndirecto.setDescripcionServicio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSERV")));
					oECreditoIndirecto.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECreditoIndirecto;
	}
}
