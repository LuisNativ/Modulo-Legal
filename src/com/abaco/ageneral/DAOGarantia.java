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
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.ETipoCliente;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOGarantia extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_GARANTIA_PENDIENTEREGISTRO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIA_PENDIENTEREGISTRO("+parametrosSP(72)+") }";
	private static final String SP_ABACOINLEGAL_INS_GARANTIAMANTENIMIENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIAMANTENIMIENTO("+parametrosSP(70)+") }"; 
	private static final String SP_ABACOINLEGAL_INS_GARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIATRAMITE("+parametrosSP(36)+") }";
	private static final String SP_ABACOINLEGAL_INS_UPD_GARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_GARANTIATRAMITE("+parametrosSP(38)+") }";
	private static final String SP_ABACOINLEGAL_INS_GARANTIAPENDIENTESOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIAPENDIENTESOLICITUD("+parametrosSP(16)+") }";
	private static final String SP_ABACOINLEGAL_INS_UPD_ASIENTOTRAMITEGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_ASIENTOTRAMITEGARANTIA("+parametrosSP(38)+") }";
	private static final String SP_ABACOINLEGAL_INS_GARANTIADETALLE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIADETALLE("+parametrosSP(61)+") }";
	private static final String SP_ABACOINLEGAL_INS_GARANTIAANEXOF7325="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_GARANTIAANEXOF7325("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_INS_TIPOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_TIPOGARANTIA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_INS_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_POLIZA("+parametrosSP(15)+") }";
	private static final String SP_ABACOINLEGAL_UPD_LIBERARGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_LIBERARGARANTIA("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIAMANTENIMIENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIAMANTENIMIENTO("+parametrosSP(68)+") }"; 
	private static final String SP_ABACOINLEGAL_UPD_GARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIATRAMITE("+parametrosSP(36)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIASOLICITUD("+parametrosSP(21)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIASOLICITUDSIAF="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIASOLICITUDSIAF("+parametrosSP(34)+") }";
	private static final String SP_ABACOINLEGAL_UPD_TIPOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_TIPOGARANTIA("+parametrosSP(4)+") }";
	private static final String SP_ABACOINLEGAL_UPD_GARANTIAPOLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_GARANTIAPOLIZA("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_UPD_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_POLIZA("+parametrosSP(16)+") }";	
	private static final String SP_ABACOINLEGAL_UPD_TIPOINGRESOPORCENTAJE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_TIPOINGRESOPORCENTAJE("+parametrosSP(6)+") }";	
	private static final String SP_ABACOINLEGAL_BUS_GARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIATRAMITE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIAASOCIADASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIAASOCIADASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_GARANTIADETALLESOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_GARANTIADETALLESOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CRED_DIR_IND_GARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CRED_DIR_IND_GARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_TIPOCAMBIODIARIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_TIPOCAMBIODIARIO() }";
	private static final String SP_ABACOINLEGAL_BUS_CIASEGUROPOLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CIASEGUROPOLIZA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_POLIZA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ULTIMAGARANTIAGENERADA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ULTIMAGARANTIAGENERADA() }";
	private static final String SP_ABACOINLEGAL_BUS_SOLICITUDXGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOLICITUDXGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ANEXOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ANEXOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_ULTASIENTOGARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_ULTASIENTOGARANTIATRAMITE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAPORLIBERAR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAPORLIBERAR("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAPORCONSTITUIR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAPORCONSTITUIR("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIASOLICITUD("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAASOCIADASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAASOCIADASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIADOCUMENTOSOLICITADO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIADOCUMENTOSOLICITADO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_HISTORICOGARANTIASOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_HISTORICOGARANTIASOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAVIGENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAVIGENTE("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CIASEGUROPOLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CIASEGUROPOLIZA("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_HISTORICOGARANTIATRAMITE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_HISTORICOGARANTIATRAMITE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_ASIENTOTRAMITEGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_ASIENTOTRAMITEGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIAVINCULADA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIAVINCULADA("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIADETALLE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIADETALLE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CREDITORELACIONADOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITORELACIONADOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CREDITOVIGENTERELACIONADOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITOVIGENTERELACIONADOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIACONCREDITOVIGENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIACONCREDITOVIGENTE("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CREDITOSASOCIADOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CREDITOSASOCIADOGARANTIA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_DEL_POLIZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_POLIZA("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_SEL_GARANTIALIBERADA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_GARANTIALIBERADA("+parametrosSP(2)+") }";
	


	
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
	
	public DAOGarantia(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje liberarGarantia(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			//lstParametrosEntrada.add(eGarantia.getMontoGarantizado());
			//lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eGarantia.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_LIBERARGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaPendienteRegistro(EGarantiaSolicitud eGarantiaSolicitud, EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoCliente());
			lstParametrosEntrada.add(eGarantiaSolicitud.getNombreLargo());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario2());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario3());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario4());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario5());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario6());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getDescripcionC());
			lstParametrosEntrada.add(eGarantia.getComentario());
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getFechaComercial());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoSolicitud());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getAlmacen());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getUbicacion2());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());		
			lstParametrosEntrada.add(eGarantia.getPorcentajeDisponible());
						
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIA_PENDIENTEREGISTRO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaMantenimiento(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eGarantia.getNombreCorto());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario2());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario3());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario4());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario5());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario6());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getCodigoEstado());
			lstParametrosEntrada.add(eGarantia.getCodigoSituacion());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getDescripcionC());
			lstParametrosEntrada.add(eGarantia.getComentario());
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getFechaComercial());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getAlmacen());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getUbicacion2());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());
			lstParametrosEntrada.add(eGarantia.getPorcentajeDisponible());

			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIAMANTENIMIENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoServicio());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoEtapaTramite());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroHojaIngresoLegal());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngreso());
			lstParametrosEntrada.add(eGarantiaTramite.getEvaluacionDocumento());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaFirmaContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoNotaria());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaLegalizacionFirma());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroKardex());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoNotario());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistroB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsientoB());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo1());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTachaB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcionB());
			
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistro());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsiento());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion1());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacion());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTacha());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcion());	
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIATRAMITE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso DAOGarantia: Problemas al agregar Tramite de Garantía", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoServicio());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroHojaIngresoLegal());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngreso());
			lstParametrosEntrada.add(eGarantiaTramite.getEvaluacionDocumento());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaFirmaContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoNotaria());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaLegalizacionFirma());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroKardex());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoNotario());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistroB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsientoB());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo1());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTachaB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTituloB());
			
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistro());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsiento());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion1());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacion());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTacha());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTituloA());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioRegistro().getNombreUsuarioSIAF());	
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_GARANTIATRAMITE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso DAOGarantia: Problemas al agregar/Modificar Tramite de Garantía", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarAsientoGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoServicio());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroHojaIngresoLegal());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngreso());
			lstParametrosEntrada.add(eGarantiaTramite.getEvaluacionDocumento());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaFirmaContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoNotaria());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaLegalizacionFirma());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroKardex());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoNotario());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistroB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsientoB());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo1());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTachaB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTituloB());
			
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistro());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsiento());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion1());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacion());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTacha());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcion());	
			lstParametrosEntrada.add(eGarantiaTramite.getTituloA());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioRegistro().getNombreUsuarioSIAF());	
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_ASIENTOTRAMITEGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso DAOGarantia: Problemas al agregar/Modificar Asiento Tramite de Garantía", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaDetalle(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eGarantia.getNombreCorto());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getCodigoEstado());
			lstParametrosEntrada.add(eGarantia.getCodigoSituacion());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia()<18 ? eGarantia.getDescripcionC():
				UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eGarantia.getObervacionGarantia(),0));
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia()<18 ? eGarantia.getComentario():
				UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eGarantia.getObervacionGarantia(),1));
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia()<18 ? eGarantia.getUbicacion2():
				UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eGarantia.getObervacionGarantia(),2));
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());
			lstParametrosEntrada.add(eGarantia.getPorcentajeCubierto());

			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIADETALLE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}

	public EMensaje registrarGarantiaSolicitudAnexoF7325() {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIAANEXOF7325, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarTipoGarantia(EGeneral tipoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(tipoGarantia.getDescripcion());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_TIPOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarPoliza(EPoliza ePoliza) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getTipoPoliza());
			lstParametrosEntrada.add(ePoliza.getCodigoBrocker());
			lstParametrosEntrada.add(ePoliza.getFechaInicioPoliza());
			lstParametrosEntrada.add(ePoliza.getFechaVencimientoPoliza());
			lstParametrosEntrada.add(ePoliza.getValorPoliza());
			lstParametrosEntrada.add(ePoliza.getNumeroEndoso());
			lstParametrosEntrada.add(ePoliza.getFechaEndoso());
			lstParametrosEntrada.add(ePoliza.getCodigoClienteUltimoEndoso());
			lstParametrosEntrada.add(ePoliza.getNombreClienteUltimoEndoso());	
			lstParametrosEntrada.add(ePoliza.getCodigoMonedaGarantia());
			lstParametrosEntrada.add(ePoliza.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_POLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarTipoGarantia(EGeneral tipoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(tipoGarantia.getCodigo2());
			lstParametrosEntrada.add(tipoGarantia.getDescripcion());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_TIPOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarGarantiaPoliza(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIAPOLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarPoliza(EPoliza ePoliza) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			lstParametrosEntrada.add(ePoliza.getTipoPoliza());
			lstParametrosEntrada.add(ePoliza.getCodigoBrocker());
			lstParametrosEntrada.add(ePoliza.getFechaInicioPoliza());
			lstParametrosEntrada.add(ePoliza.getFechaVencimientoPoliza());
			lstParametrosEntrada.add(ePoliza.getValorPoliza());
			lstParametrosEntrada.add(ePoliza.getNumeroEndoso());
			lstParametrosEntrada.add(ePoliza.getFechaEndoso());
			lstParametrosEntrada.add(ePoliza.getCodigoClienteUltimoEndoso());
			lstParametrosEntrada.add(ePoliza.getNombreClienteUltimoEndoso());	
			lstParametrosEntrada.add(ePoliza.getCodigoMonedaGarantia());
			lstParametrosEntrada.add(ePoliza.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_POLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarTipoIngresoPorcentaje(EGarantiaSolicitud eGarantiaSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getTipoIngreso());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getNombreUsuario());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_TIPOINGRESOPORCENTAJE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	
	public EMensaje modificarGarantiaMantenimiento(EGarantia eGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoMoneda());
			//lstParametrosEntrada.add(eGarantia.getCodigoCliente());
			//lstParametrosEntrada.add(eGarantia.getNombreCorto());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario2());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario3());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario4());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario5());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario6());
			lstParametrosEntrada.add(eGarantia.getPartidaRegistral());
			lstParametrosEntrada.add(eGarantia.getOficinaRegistral());
			lstParametrosEntrada.add(eGarantia.getTipoRegistral());
			//lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getTipoVariable1());
			lstParametrosEntrada.add(eGarantia.getTipoVariable2());
			lstParametrosEntrada.add(eGarantia.getTipoVariable3());
			lstParametrosEntrada.add(eGarantia.getCodigoEstado());
			lstParametrosEntrada.add(eGarantia.getCodigoSituacion());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getNumeroDocumento());
			lstParametrosEntrada.add(eGarantia.getDescripcionA());
			lstParametrosEntrada.add(eGarantia.getDescripcionB());
			lstParametrosEntrada.add(eGarantia.getDescripcionC());
			lstParametrosEntrada.add( eGarantia.getComentario());
			lstParametrosEntrada.add(eGarantia.getCodigoTasador());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getFechaVencimiento());
			lstParametrosEntrada.add(eGarantia.getFechaGravamen());
			lstParametrosEntrada.add(eGarantia.getFechaValoracion());
			lstParametrosEntrada.add(eGarantia.getFechaVariable1());
			lstParametrosEntrada.add(eGarantia.getFechaVariable2());
			lstParametrosEntrada.add(eGarantia.getFechaVariable3());
			lstParametrosEntrada.add(eGarantia.getFechaVariable4());
			lstParametrosEntrada.add(eGarantia.getFechaComercial());
			lstParametrosEntrada.add(eGarantia.getMontoComercial());
			lstParametrosEntrada.add(eGarantia.getMontoOriginalGarantia());
			lstParametrosEntrada.add(eGarantia.getMontoGravamen());
			lstParametrosEntrada.add(eGarantia.getMontoValoracion());
			lstParametrosEntrada.add(eGarantia.getMontoEjecucion());
			lstParametrosEntrada.add(eGarantia.getMontoVariable1());
			lstParametrosEntrada.add(eGarantia.getMontoVariable2());
			lstParametrosEntrada.add(eGarantia.getMontoVariable3());
			lstParametrosEntrada.add(eGarantia.getMontoVariable4());
			lstParametrosEntrada.add(eGarantia.getCodigoInspector());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoBien());
			lstParametrosEntrada.add(eGarantia.getDepositario());
			lstParametrosEntrada.add(eGarantia.getAlmacen());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getClase());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getModelo());
			lstParametrosEntrada.add(eGarantia.getCarroceria());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable1());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable2());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable3());
			lstParametrosEntrada.add(eGarantia.getNumeroVariable4());
			lstParametrosEntrada.add(eGarantia.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantia.getBancoEmisor());
			lstParametrosEntrada.add(eGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getUbicacion2());
			lstParametrosEntrada.add(eGarantia.getPoliza());
			lstParametrosEntrada.add(eGarantia.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantia.getNumeroEndoso());
			lstParametrosEntrada.add(eGarantia.getMontoDisponible());
			lstParametrosEntrada.add(eGarantia.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantia.getTipocambio());
			lstParametrosEntrada.add(eGarantia.getPorcentajeDisponible());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIAMANTENIMIENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			//lstParametrosEntrada.add(indicadorTipoGarantia);
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoEstadoGarantiaSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getObservacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getFechaComercial());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoComercial());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoTasacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoGravamen());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMontoValorRealizacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getPorcentajeAsignado());
			lstParametrosEntrada.add(eGarantiaSolicitud.getPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCorrelativoPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getTipoPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getFechaVencimientoPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getValorPoliza());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoCiaSeguro());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSaldoDisponible());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eGarantiaSolicitud.getUsuarioRegistro().getNombreUsuarioSIAF());
		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIASOLICITUD, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitudSIAF(EGarantiaSolicitud eGarantiaSolicitud,EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoTipoGarantiaReal());
			//Para Vehiculo
			lstParametrosEntrada.add(eGarantiaSolicitud.getPlaca());
			lstParametrosEntrada.add(eGarantiaSolicitud.getClase());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMarca());
			lstParametrosEntrada.add(eGarantiaSolicitud.getModelo());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCarroceria());
			lstParametrosEntrada.add(eGarantiaSolicitud.getMotor());
			lstParametrosEntrada.add(eGarantiaSolicitud.getSerie());
			lstParametrosEntrada.add(eGarantiaSolicitud.getColor());
			lstParametrosEntrada.add(eGarantiaSolicitud.getDescripcionCombustible());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoClase());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoMarca());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoModelo());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoCombustible());
			lstParametrosEntrada.add(eGarantiaSolicitud.getCodigoNivelRiesgo());
			lstParametrosEntrada.add(eGarantiaSolicitud.getAnioFabricacion());
			lstParametrosEntrada.add(eGarantiaSolicitud.getIndicadorNuevo());
			//Para Maquinaria
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getMarca());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getModelo());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getSerie());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getMotor());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCantidad());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCodigoTipoPrenda());
			//Para Predios
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getDireccion());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getAreaTerreno());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getAreaConstruida());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getNumeroPisos());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getUsoPredio());
			lstParametrosEntrada.add(eGarantiaDetalleSolicitud.getCodigoTipoPrenda2());
		
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIASOLICITUDSIAF, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar. Solicitud Garantia SIAF", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoServicio());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoMoneda());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoEtapaTramite());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroHojaIngresoLegal());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngreso());
			lstParametrosEntrada.add(eGarantiaTramite.getEvaluacionDocumento());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getUsuarioElaboracionContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaFirmaContrato());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoNotaria());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaLegalizacionFirma());
			lstParametrosEntrada.add(eGarantiaTramite.getNumeroKardex());
			lstParametrosEntrada.add(eGarantiaTramite.getCodigoNotario());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistroB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsientoB());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo1());
			lstParametrosEntrada.add(eGarantiaTramite.getDescripcionObservacionBloqueo2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTachaB());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcionB());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcionB());
			
			lstParametrosEntrada.add(eGarantiaTramite.getFechaIngresoRegistro());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaVigenciaAsiento());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion1());
			lstParametrosEntrada.add(eGarantiaTramite.getObservacion2());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaObservacion());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaTacha());
			lstParametrosEntrada.add(eGarantiaTramite.getFechaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getFichaInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getTomoInscripcion());
			lstParametrosEntrada.add(eGarantiaTramite.getCiudadInscripcion());	
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_GARANTIATRAMITE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso DaoGarantia: Problemas al modificar Tramite Garantia.", objEx);
		}
		return mensaje;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorLiberar(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAPORLIBERAR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMBCL"));
					//oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					oEGarantia.setDescripcionCreditoRelacionado(oResultSet.getString("DESCOPE"));
					oEGarantia.setCodigoEstadoLevantamiento(oResultSet.getInt("CODESTSOL"));
					oEGarantia.setDescripcionEstadoLevantamiento(oResultSet.getString("DESCESTSOL"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaLiberada(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIALIBERADA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMBCL"));
					oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					oEGarantia.setCodigoEstadoLevantamiento(oResultSet.getInt("CODESTSOL"));
					oEGarantia.setDescripcionEstadoLevantamiento(oResultSet.getString("DESCESTSOL"));
					oEGarantia.setCodigoEstadoDocumento(oResultSet.getInt("CODESTDOC"));
					oEGarantia.setDescripcionEstadoDocumento(oResultSet.getString("DESCESTDOC"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaConCreditoVigente(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIACONCREDITOVIGENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMBCL"));
					oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					oEGarantia.setDescripcionCreditoRelacionado(oResultSet.getString("DESCOPE"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaTramite> listarHistoricoTramiteGarantia(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite= null;
		EUsuario oEUsuario = null;
		List<EGarantiaTramite> lstGarantia = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_HISTORICOGARANTIATRAMITE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaTramite>();
				while (oResultSet.next()) {
					oEGarantiaTramite = new EGarantiaTramite();
					oEGarantiaTramite.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantiaTramite.setTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantiaTramite.setSecuenciaHistorica(oResultSet.getInt("SECUEN"));
					oEUsuario = new EUsuario();
					oEUsuario.setNombreUsuarioSIAF(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEGarantiaTramite.setUsuarioRegistro(oEUsuario);
					oEGarantiaTramite.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEGarantiaTramite.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					
					oEGarantiaTramite.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaTramite.setNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL"));	
					oEGarantiaTramite.setFechaIngreso(oResultSet.getDate("FECINL"));
					oEGarantiaTramite.setEvaluacionDocumento(oResultSet.getInt("EVADOC"));
					oEGarantiaTramite.setFechaElaboracionContrato(oResultSet.getDate("FECELC"));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
					oEGarantiaTramite.setFechaFirmaContrato(oResultSet.getDate("FECFIC"));
					oEGarantiaTramite.setFechaIngresoNotaria(oResultSet.getDate("FECFIN"));
					oEGarantiaTramite.setFechaLegalizacionFirma(oResultSet.getDate("FECLEF"));
					oEGarantiaTramite.setNumeroKardex(oResultSet.getInt("KARDEN"));
					oEGarantiaTramite.setCodigoNotario(oResultSet.getInt("NOTARI"));
					oEGarantiaTramite.setFechaIngresoRegistroB(oResultSet.getDate("FECBINR"));
					oEGarantiaTramite.setFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP"));
					oEGarantiaTramite.setDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")));
					oEGarantiaTramite.setDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")));
					oEGarantiaTramite.setFechaObservacionB(oResultSet.getDate("FECBOBS"));
					oEGarantiaTramite.setFechaTachaB(oResultSet.getDate("FECBTAC"));
					oEGarantiaTramite.setFechaInscripcionB(oResultSet.getDate("FECBINS"));
					oEGarantiaTramite.setFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")));
					oEGarantiaTramite.setTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")));
					oEGarantiaTramite.setCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")));
					
					oEGarantiaTramite.setFechaIngresoRegistro(oResultSet.getDate("FECINR"));
					oEGarantiaTramite.setFechaVigenciaAsiento(oResultSet.getDate("FECVAP"));
					oEGarantiaTramite.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")));
					oEGarantiaTramite.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));				
					oEGarantiaTramite.setFechaObservacion(oResultSet.getDate("FECOBS"));
					oEGarantiaTramite.setFechaTacha(oResultSet.getDate("FECTAC"));
					oEGarantiaTramite.setFechaInscripcion(oResultSet.getDate("FECINS"));
					oEGarantiaTramite.setFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")));
					oEGarantiaTramite.setTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")));
					oEGarantiaTramite.setCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")));
					
					oEGarantiaTramite.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOTARIO")));
					//Validacion para Historico Antes/Despues
					oEGarantiaTramite.setValidarNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL") == oResultSet.getInt("@HOJINL") ? false : true);
					oEGarantiaTramite.setValidarFechaIngreso(oResultSet.getDate("FECINL").equals(oResultSet.getDate("AFECINL"))  ? false: true);
					oEGarantiaTramite.setValidarEvaluacionDocumento(oResultSet.getInt("EVADOC") == oResultSet.getInt("@EVADOC") ? false: true);
					oEGarantiaTramite.setValidarFechaElaboracionContrato(oResultSet.getDate("FECELC").equals(oResultSet.getDate("AFECELC")) ? false: true);
					oEGarantiaTramite.setValidarUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@USRELC"))) ? false : true);
					oEGarantiaTramite.setValidarFechaFirmaContrato(oResultSet.getDate("FECFIC").equals(oResultSet.getDate("AFECFIC")) ? false: true);
					oEGarantiaTramite.setValidarFechaIngresoNotaria(oResultSet.getDate("FECFIN").equals(oResultSet.getDate("AFECFIN")) ? false: true);
					oEGarantiaTramite.setValidarFechaLegalizacionFirma(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECLEF")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("AFECLEF"))) ? false: true);
					oEGarantiaTramite.setValidarNumeroKardex(oResultSet.getInt("KARDEN") == oResultSet.getInt("@KARDEN") ? false : true);
					oEGarantiaTramite.setValidarCodigoNotario(oResultSet.getInt("NOTARI") == oResultSet.getInt("@NOTARI") ? false : true);
					oEGarantiaTramite.setValidarFechaIngresoRegistro(oResultSet.getDate("FECINR").equals(oResultSet.getDate("AFECINR")) ? false: true);
					oEGarantiaTramite.setValidarFechaVigenciaAsiento(oResultSet.getDate("FECVAP").equals(oResultSet.getDate("AFECVAP")) ? false: true);
					oEGarantiaTramite.setValidarObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")).equals(
							                                 UFuncionesGenerales.revisaCadena(oResultSet.getString("@OBSER1"))) ? false : true);
					oEGarantiaTramite.setValidarObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")).equals(
                            UFuncionesGenerales.revisaCadena(oResultSet.getString("@OBSER2"))) ? false : true);
					oEGarantiaTramite.setValidarFechaObservacion(oResultSet.getDate("FECOBS").equals(oResultSet.getDate("AFECOBS")) ? false: true);
					oEGarantiaTramite.setValidarFechaTacha(oResultSet.getDate("FECTAC").equals(oResultSet.getDate("AFECTAC")) ? false: true);
					oEGarantiaTramite.setValidarFechaInscripcion(oResultSet.getDate("FECINS").equals(oResultSet.getDate("AFECINS")) ? false: true);
					oEGarantiaTramite.setValidarFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@FICINS"))) ? false : true);
					oEGarantiaTramite.setValidarTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TOMINS"))) ? false : true);
					oEGarantiaTramite.setValidarCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@CIUINS"))) ? false : true);
					oEGarantiaTramite.setValidarFechaIngresoRegistroB(oResultSet.getDate("FECBINR").equals(oResultSet.getDate("AFECBINR")) ? false: true);
					oEGarantiaTramite.setValidarFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP").equals(oResultSet.getDate("AFECBVAP")) ? false: true);
					oEGarantiaTramite.setValidarDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")).equals(
                            UFuncionesGenerales.revisaCadena(oResultSet.getString("@BOBSE1"))) ? false : true);
					oEGarantiaTramite.setValidarDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")).equals(
                            UFuncionesGenerales.revisaCadena(oResultSet.getString("@BOBSE2"))) ? false : true);
					oEGarantiaTramite.setValidarFechaObservacionB(oResultSet.getDate("FECBOBS").equals(oResultSet.getDate("AFECBOBS")) ? false: true);
					oEGarantiaTramite.setValidarFechaTachaB(oResultSet.getDate("FECBTAC").equals(oResultSet.getDate("AFECBTAC")) ? false: true);
					oEGarantiaTramite.setValidarFechaInscripcionB(oResultSet.getDate("FECBINS").equals(oResultSet.getDate("AFECBINS")) ? false: true);
					oEGarantiaTramite.setValidarFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@BFICIN"))) ? false : true);
					oEGarantiaTramite.setValidarTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@BTOMIN"))) ? false : true);
					oEGarantiaTramite.setValidarCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@BCIUIN"))) ? false : true);
					oEGarantiaTramite.setValidarTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TITULA"))) ? false : true);
					oEGarantiaTramite.setValidarTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TITULB"))) ? false : true);
					lstGarantia.add(oEGarantiaTramite);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener Historico Tramite Garantia", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaTramite> listarAsientosTramiteGarantia(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite= null;
		EUsuario oEUsuario = null;
		List<EGarantiaTramite> lstGarantia = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_ASIENTOTRAMITEGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaTramite>();
				while (oResultSet.next()) {
					oEGarantiaTramite = new EGarantiaTramite();
					oEGarantiaTramite.setNroAsiento(oResultSet.getInt("NASIEN"));
					oEGarantiaTramite.setCodigoGarantia(oResultSet.getInt("GARANT"));
					//oEGarantiaTramite.setTipoGarantia(oResultSet.getInt("TIPGAR"));
					
					oEGarantiaTramite.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaTramite.setNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL"));	
					oEGarantiaTramite.setFechaIngreso(oResultSet.getDate("FECINL"));
					oEGarantiaTramite.setEvaluacionDocumento(oResultSet.getInt("EVADOC"));
					oEGarantiaTramite.setFechaElaboracionContrato(oResultSet.getDate("FECELC"));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
					oEGarantiaTramite.setFechaFirmaContrato(oResultSet.getDate("FECFIC"));
					oEGarantiaTramite.setFechaIngresoNotaria(oResultSet.getDate("FECFIN"));
					oEGarantiaTramite.setFechaLegalizacionFirma(oResultSet.getDate("FECLEF"));
					oEGarantiaTramite.setNumeroKardex(oResultSet.getInt("KARDEN"));
					oEGarantiaTramite.setCodigoNotario(oResultSet.getInt("NOTARI"));
					oEGarantiaTramite.setFechaIngresoRegistroB(oResultSet.getDate("FECBINR"));
					oEGarantiaTramite.setFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP"));
					oEGarantiaTramite.setDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")));
					oEGarantiaTramite.setDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")));
					oEGarantiaTramite.setFechaObservacionB(oResultSet.getDate("FECBOBS"));
					oEGarantiaTramite.setFechaTachaB(oResultSet.getDate("FECBTAC"));
					oEGarantiaTramite.setFechaInscripcionB(oResultSet.getDate("FECBINS"));
					oEGarantiaTramite.setFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")));
					oEGarantiaTramite.setTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")));
					oEGarantiaTramite.setCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")));
					oEGarantiaTramite.setTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")));
					
					oEGarantiaTramite.setFechaIngresoRegistro(oResultSet.getDate("FECINR"));
					oEGarantiaTramite.setFechaVigenciaAsiento(oResultSet.getDate("FECVAP"));
					oEGarantiaTramite.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")));
					oEGarantiaTramite.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));				
					oEGarantiaTramite.setFechaObservacion(oResultSet.getDate("FECOBS"));
					oEGarantiaTramite.setFechaTacha(oResultSet.getDate("FECTAC"));
					oEGarantiaTramite.setFechaInscripcion(oResultSet.getDate("FECINS"));
					oEGarantiaTramite.setFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")));
					oEGarantiaTramite.setTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")));
					oEGarantiaTramite.setCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")));
					oEGarantiaTramite.setTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")));
					
					oEGarantiaTramite.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOTARIO")));
					lstGarantia.add(oEGarantiaTramite);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener Historico Tramite Garantia", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorConstituir(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAPORCONSTITUIR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantia.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMABV"));
					oEGarantia.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantia.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoEstadoRevision(oResultSet.getNString("STATUS"));
					oEGarantia.setFechaRevision(oResultSet.getDate("FECREVF"));
					oEGarantia.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oEGarantia.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));	
					oEGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantia.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					oEGarantia.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public EMensaje agregaGarantiaPendienteRegistro(EGarantiaSolicitud eOGarantiaSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOGarantiaSolicitud.getNumeroSolicitud());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getNumeroRevision());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getNombreLargo());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoTipoProducto());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoEstadoSolCredito());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoEstadoRevision());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getFechaRevision());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoMonedaSolicitud());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getMontoSolicitud());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getSecuenciaGarantia());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoTipoGarantiaReal());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoNroIngresoGarantia());
			lstParametrosEntrada.add(eOGarantiaSolicitud.getCodigoEstadoGarantiaSolicitud());
			
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_GARANTIAPENDIENTESOLICITUD, lstParametrosEntrada);						
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public List<EGarantiaSolicitud> listarSolicitudAsociadaGarantia(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAASOCIADASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMABV"));
					oEGarantia.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantia.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoEstadoRevision(oResultSet.getString("STATUS"));
					oEGarantia.setFechaRevision(oResultSet.getDate("FECREVF"));
					oEGarantia.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oEGarantia.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));	
					oEGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantia.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantia.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantia.setCodigoEstadoGarantiaSolicitud(oResultSet.getInt("ESTADOSG"));
					oEGarantia.setDescripcionEstadoGarantiaSolicitud(oResultSet.getString("DESCESTGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESCTIPGAR"));
					oEGarantia.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					oEGarantia.setAbreviacionMonedaSolicitud(oResultSet.getString("ABRVMON"));
					oEGarantia.setDescripcionMonedaSolicitud(oResultSet.getString("DESCMON"));
					oEGarantia.setCodigoEstadoEvaluacionLegal(oResultSet.getInt("STALEG"));
					//oEGarantia.setObservacion(oResultSet.getString("OBSERV"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarHistoricoGarantiaSolicitud(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_HISTORICOGARANTIASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantia.setNumeroRevision(oResultSet.getInt("NREVIS"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMABV"));
					//oEGarantia.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					//oEGarantia.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					//oEGarantia.setCodigoEstadoRevision(oResultSet.getNString("STATUS"));
					oEGarantia.setFechaRevision(oResultSet.getDate("FECREVF"));
					oEGarantia.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oEGarantia.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));	
					oEGarantia.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantia.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					//oEGarantia.setCodigoEstadoGarantiaSolicitud(oResultSet.getInt("ESTSOLGAR"));
					//oEGarantia.setDescripcionEstadoGarantiaSolicitud(oResultSet.getString("DESCESTGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESTIPGAR"));
					oEGarantia.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantia> listarGarantiaVigente(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAVIGENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setFechaIngreso(oResultSet.getDate("FECING"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADOGARANT")));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantia> listarGarantia(int codigo, String descripcion) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					//oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setFechaIngreso(oResultSet.getDate("FECING"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMONGAR")));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoSituacion(oResultSet.getInt("SITUAC"));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADOGARANT")));
					oEGarantia.setDescripcionSituacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("SITUACGARANT")));
					oEGarantia.setCodigoInspector(oResultSet.getInt("CODISP"));
					oEGarantia.setPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEGarantia.setCodigoTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEGarantia.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEGarantia.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));		
					oEGarantia.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEGarantia.setFechaVencimientoPoliza(oResultSet.getDate("FECVENCPOL"));
					oEGarantia.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaSolicitud(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud= null;
		List<EGarantiaSolicitud> lstGarantiaSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantiaSolicitud = new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					//NUMERO REVISION
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoServicioGarantia(oResultSet.getInt("SERGAR"));
					oEGarantiaSolicitud.setCodigoMonedaGarantia(oResultSet.getInt("MONGAR"));
					oEGarantiaSolicitud.setCuentaGarantia(oResultSet.getInt("CTAGAR"));
					oEGarantiaSolicitud.setDocumentoGarantia(oResultSet.getInt("DOCGAR"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantiaSolicitud.setGrupoTanomoshi(oResultSet.getInt("GRUGAR"));
					oEGarantiaSolicitud.setNumeroListaTanomoshi(oResultSet.getInt("LISGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaOtro(UFuncionesGenerales.revisaCadena(oResultSet.getString("OTRGAR")));
					oEGarantiaSolicitud.setDescripcionGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaSolicitud.setMontoOrigenGarantia(oResultSet.getDouble("MTOORI"));
					//TIPO CAMBIO
					oEGarantiaSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaSolicitud.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantiaSolicitud.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantiaSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantiaSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantiaSolicitud.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantiaSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("NMOTOR")));
					oEGarantiaSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("CHASIS")));
					oEGarantiaSolicitud.setColor(UFuncionesGenerales.revisaCadena(oResultSet.getString("COLORV")));
					oEGarantiaSolicitud.setCodigoClase(oResultSet.getInt("CCLASE"));
					oEGarantiaSolicitud.setCodigoMarca(oResultSet.getInt("CMARCA"));
					oEGarantiaSolicitud.setCodigoModelo(oResultSet.getInt("CMODEL"));
					oEGarantiaSolicitud.setCodigoCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("CCOMBU")));
					oEGarantiaSolicitud.setCodigoNivelRiesgo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRIESG")));
					oEGarantiaSolicitud.setAnioFabricacion(oResultSet.getInt("AÑOFAB"));
					oEGarantiaSolicitud.setMontoValorizacion(oResultSet.getDouble("VALORI"));
					oEGarantiaSolicitud.setIndicadorNuevo(UFuncionesGenerales.revisaCadena(oResultSet.getString("VNUEVO")));
					
					oEGarantiaSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCGARTIP")));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantiaSolicitud.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantiaSolicitud.setDescripcionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantiaSolicitud.setDescripcionServicio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSERGAR")));
					oEGarantiaSolicitud.setDescripcionGeneral(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCGEN")));
					
					lstGarantiaSolicitud.add(oEGarantiaSolicitud);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaSolicitud;
	}
	
	public List<EGarantia> listarGarantiaVinculada(EGarantia eGarantia ) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eGarantia.getCodigoTipoGarantia());
			lstParametrosEntrada.add(eGarantia.getNumeroRegistro());
			lstParametrosEntrada.add(eGarantia.getUbicacion1());
			lstParametrosEntrada.add(eGarantia.getPlaca());
			lstParametrosEntrada.add(eGarantia.getMarca());
			lstParametrosEntrada.add(eGarantia.getCodigoUbigeo());
			lstParametrosEntrada.add(eGarantia.getCodigoPropietario());
				
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIAVINCULADA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia= new EGarantia();
					
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(oResultSet.getString("NOMPRO"));				
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));	
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoSituacion(oResultSet.getInt("SITUAC"));
					oEGarantia.setCodigoUbigeo(oResultSet.getInt("UBIGEO"));
					oEGarantia.setMontoOriginalGarantia(oResultSet.getDouble("MONORI"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MONTOG"));
					oEGarantia.setCodigoTipoBien(oResultSet.getInt("TIPBIE"));
					oEGarantia.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantia.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantia.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantia.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantia.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantia.setAnioFabricacion(oResultSet.getInt("AÑOVAR")); 
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));	
					oEGarantia.setUbicacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAB")));	
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					/*oEGarantia.setDescripcionTipoBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPBIEN")));
					oEGarantia.setDescripcionSituacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSITUAC")));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCEST")));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));*/
					oEGarantia.setPorcentajeDisponible(oResultSet.getDouble("PORDIS"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));

					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EGarantia> listarGarantiaDetalle(EGarantia eGarantia ) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		List<EGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
				
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIADETALLE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantia>();
				while (oResultSet.next()) {
					oEGarantia= new EGarantia();
					
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(oResultSet.getString("NOMPRO"));				
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));	
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoSituacion(oResultSet.getInt("SITUAC"));
					oEGarantia.setCodigoUbigeo(oResultSet.getInt("UBIGEO"));
					oEGarantia.setMontoOriginalGarantia(oResultSet.getDouble("MONORI"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MONTOG"));
					oEGarantia.setCodigoTipoBien(oResultSet.getInt("TIPBIE"));
					oEGarantia.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantia.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantia.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantia.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantia.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantia.setAnioFabricacion(oResultSet.getInt("AÑOVAR")); 
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));	
					oEGarantia.setUbicacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAB")));	
					oEGarantia.setPorcentajeCubierto(oResultSet.getDouble("PORUSO"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
					oEGarantia.setMontoCubierto(oResultSet.getDouble("MTODIS"));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EAsignacionContratoGarantia> listarCreditosAsociadosGarantia(long codigoGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EAsignacionContratoGarantia oEContratoGarantia= null;
		List<EAsignacionContratoGarantia> lstCreditoGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
				
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITOSASOCIADOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
				while (oResultSet.next()) {
					oEContratoGarantia= new EAsignacionContratoGarantia();
					
					oEContratoGarantia.setNumeroContrato(oResultSet.getInt("NROCOT"));
					oEContratoGarantia.setNumeroContratoOtro(oResultSet.getInt("NROCOO"));
					oEContratoGarantia.setSecuenciaAsignacion(oResultSet.getInt("SECASI"));
					oEContratoGarantia.setTipoPrenda(oResultSet.getInt("TIPPDI"));
					oEContratoGarantia.setServicioBase(oResultSet.getInt("SEBASE"));
					oEContratoGarantia.setCodigoMonedaBaseAsignacion(oResultSet.getInt("MOBASE"));
					oEContratoGarantia.setServicio(oResultSet.getInt("SERVIC"));
					oEContratoGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEContratoGarantia.setNumeroOperacion(oResultSet.getLong("NUMOPE"));
					oEContratoGarantia.setNumeroPlanilla(oResultSet.getLong("PLANIL"));
					oEContratoGarantia.setNumeroFianza(oResultSet.getInt("NUMERO"));
					oEContratoGarantia.setNumeroOperacionTanomoshi(oResultSet.getInt("NOPTAN"));
					oEContratoGarantia.setNumeroLista(oResultSet.getInt("NROGRP"));
					oEContratoGarantia.setNumeroDocumentoDPF(oResultSet.getInt("NUMDOC"));
					oEContratoGarantia.setNumeroConsecutivoDPF(oResultSet.getInt("CONSEC"));
					oEContratoGarantia.setNumeroCuenta(oResultSet.getInt("CUENTA"));
					oEContratoGarantia.setMontoImporteDocMonedaOrigen(oResultSet.getDouble("IMPORI"));
					oEContratoGarantia.setTipoCambio(oResultSet.getDouble("CAMBTI"));
					oEContratoGarantia.setMontoImporteCubierto(oResultSet.getDouble("IMPCUB"));
					oEContratoGarantia.setMontoSaldoGarantia(oResultSet.getDouble("SLDGAR"));
					oEContratoGarantia.setPorcentajeCubiertoGarantia(oResultSet.getDouble("PORGAR"));
					oEContratoGarantia.setMontoImporteDocMonedaOrigen2(oResultSet.getDouble("IMPORS"));
					oEContratoGarantia.setMontoImporteCubierto2(oResultSet.getDouble("IMPCUS"));
					oEContratoGarantia.setMontoSaldoGarantia2(oResultSet.getDouble("SLDGAS"));
					oEContratoGarantia.setCodigoCliente(oResultSet.getInt("CODSOCIO"));
					oEContratoGarantia.setNombreCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMSOCIO")));
					oEContratoGarantia.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSE01")));
					oEContratoGarantia.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSE02")));
					oEContratoGarantia.setEstadoRegistro(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTARE")));
					oEContratoGarantia.setTipoRegistro(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIREGI")));
					oEContratoGarantia.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEContratoGarantia.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEContratoGarantia.setMontoSaldoCredito(oResultSet.getDouble("SALDOCREDITO"));
					EUsuario eUsuario = new EUsuario();
					eUsuario.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUARI")));
					oEContratoGarantia.setUsuarioRegistro(eUsuario);
					oEContratoGarantia.setDescripcionEstadoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTADOCREDITO")));

					
					lstCreditoGarantia.add(oEContratoGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstCreditoGarantia;
	}
	
	public EMensaje eliminarPoliza(EPoliza ePoliza) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_POLIZA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EAsignacionContratoGarantia> listarClientesGarantizados(EGarantia eGarantia ) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EAsignacionContratoGarantia oEGarantiaAsignacion= null;
		EUsuario eUsuario = null;
		List<EAsignacionContratoGarantia> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eGarantia.getCodigoGarantia());
				
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIADETALLE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EAsignacionContratoGarantia>();
				while (oResultSet.next()) {
					oEGarantiaAsignacion= new EAsignacionContratoGarantia();
					
					oEGarantiaAsignacion.setNumeroContrato(oResultSet.getInt("NROCOT"));
					oEGarantiaAsignacion.setSecuenciaAsignacion(oResultSet.getInt("SECASI"));
					oEGarantiaAsignacion.setServicio(oResultSet.getInt("SERVIC"));
					oEGarantiaAsignacion.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaAsignacion.setNumeroOperacion(oResultSet.getLong("NUMOPE"));
					oEGarantiaAsignacion.setMontoImporteDocMonedaOrigen(oResultSet.getDouble("IMPORI"));
					oEGarantiaAsignacion.setTipoCambio(oResultSet.getDouble("CAMBTI"));
					oEGarantiaAsignacion.setMontoImporteCubierto(oResultSet.getDouble("IMPCUB"));
					oEGarantiaAsignacion.setMontoSaldoGarantia(oResultSet.getDouble("SLDGAR"));
					oEGarantiaAsignacion.setPorcentajeCubiertoGarantia(oResultSet.getDouble("PORGAR"));
					oEGarantiaAsignacion.setMontoImporteDocMonedaOrigen2(oResultSet.getDouble("IMPORS"));
					oEGarantiaAsignacion.setMontoImporteCubierto2(oResultSet.getDouble("IMPCUS"));
					oEGarantiaAsignacion.setMontoSaldoGarantia2(oResultSet.getDouble("SLDGAS"));
					oEGarantiaAsignacion.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantiaAsignacion.setNombreCliente(oResultSet.getString("NOMADC"));
					oEGarantiaAsignacion.setObservacion1(oResultSet.getString("OBSE01"));
					oEGarantiaAsignacion.setObservacion2(oResultSet.getString("OBSE02"));
					oEGarantiaAsignacion.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEGarantiaAsignacion.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					eUsuario = new EUsuario();
					eUsuario.setNombreUsuario(oResultSet.getString("USUARI"));
					oEGarantiaAsignacion.setUsuarioRegistro(eUsuario);
					oEGarantiaAsignacion.setAbreviacionMoneda(oResultSet.getString("ABRVMON"));
					oEGarantiaAsignacion.setDescripcionMoneda(oResultSet.getString("DESCMON"));
					
		
					
					lstGarantia.add(oEGarantiaAsignacion);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public List<EPoliza> listarPolizaSeguro(int codigo, String descripcion,String descripcion2) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPoliza= null;
		List<EPoliza> lstPoliza = null;
		int codigoPoliza = 0;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(descripcion2);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CIASEGUROPOLIZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstPoliza = new ArrayList<EPoliza>();
				while (oResultSet.next()) {
					codigoPoliza++;
					oEPoliza = new EPoliza();
					oEPoliza.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPoliza.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPoliza.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPoliza.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPoliza.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPoliza.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPoliza.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPoliza.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPoliza.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPoliza.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPoliza.setCodigoClienteUltimoEndoso(oResultSet.getInt("CLIEND"));
					oEPoliza.setNombreClienteUltimoEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMEND")));
					oEPoliza.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
					oEPoliza.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEPoliza.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					oEPoliza.setCodigoPoliza(codigoPoliza);
					lstPoliza.add(oEPoliza);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener. Poliza", objEx);
		}
		return lstPoliza;
	}
	
	public List<EPoliza> buscarPolizaSeguro(EPoliza ePoliza) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPoliza= null;
		List<EPoliza> lstPoliza = null;
		int codigoPoliza = 0;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CIASEGUROPOLIZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstPoliza = new ArrayList<EPoliza>();
				while (oResultSet.next()) {
					codigoPoliza++;
					oEPoliza = new EPoliza();
					oEPoliza.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPoliza.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPoliza.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPoliza.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPoliza.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPoliza.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPoliza.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPoliza.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPoliza.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPoliza.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPoliza.setCodigoClienteUltimoEndoso(oResultSet.getInt("CLIEND"));
					oEPoliza.setNombreClienteUltimoEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMEND")));
					oEPoliza.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
					oEPoliza.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEPoliza.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
					oEPoliza.setCodigoPoliza(codigoPoliza);
					lstPoliza.add(oEPoliza);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener. Poliza", objEx);
		}
		return lstPoliza;
	}
		
	public EPoliza buscarPoliza(EPoliza ePoliza){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EPoliza oEPoliza= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(ePoliza.getCodigoCiaSeguro());
			lstParametrosEntrada.add(ePoliza.getNumeroPoliza());
			lstParametrosEntrada.add(ePoliza.getCorrelativoPoliza());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_POLIZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEPoliza = new EPoliza();
					oEPoliza.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEPoliza.setNumeroPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEPoliza.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEPoliza.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEPoliza.setCodigoBrocker(oResultSet.getInt("BROCKE"));
					oEPoliza.setFechaInicioPoliza(oResultSet.getDate("FECIPO"));
					oEPoliza.setFechaVencimientoPoliza(oResultSet.getDate("FECPOL"));
					oEPoliza.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEPoliza.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEPoliza.setFechaEndoso(oResultSet.getDate("FECEND"));
					oEPoliza.setCodigoClienteUltimoEndoso(oResultSet.getInt("CLIEND"));
					oEPoliza.setNombreClienteUltimoEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMEND")));
					oEPoliza.setCodigoMonedaGarantia(oResultSet.getInt("MONEDA"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEPoliza;
	}
	
	public EGarantia buscarUltimaGarantiaGenerada(){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ULTIMAGARANTIAGENERADA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantia;
	}
	
	public EGarantiaSolicitud buscarSolicitudxGarantia(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOLICITUDXGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaSolicitud = new EGarantiaSolicitud();
					oEGarantiaSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARANI"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaSolicitud;
	}
	
	
	
	public EGarantia buscarAnexoGarantia(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ANEXOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantia = new EGarantia();
					oEGarantia.setCodigoGarantia(oResultSet.getLong("GARANT"));
					oEGarantia.setUbicacion1Largo(oResultSet.getString("UBICAC"));
					oEGarantia.setUbicacion2Largo(oResultSet.getString("UBICAB"));
					oEGarantia.setMontoComercial(oResultSet.getDouble("MONCOM"));
					oEGarantia.setFechaComercial(oResultSet.getDate("FECCOM"));
					oEGarantia.setPartidaRegistral(oResultSet.getString("PARREG"));
					oEGarantia.setOficinaRegistral(oResultSet.getInt("OFIREG"));
					oEGarantia.setTipoRegistral(oResultSet.getInt("TIPREG"));
					oEGarantia.setCodigoPropietario2(oResultSet.getInt("CODPR2"));
					oEGarantia.setCodigoPropietario3(oResultSet.getInt("CODPR3"));
					oEGarantia.setCodigoPropietario4(oResultSet.getInt("CODPR4"));
					oEGarantia.setCodigoPropietario5(oResultSet.getInt("CODPR5"));
					oEGarantia.setCodigoPropietario6(oResultSet.getInt("CODPR6"));
					oEGarantia.setDescripcionPropietario2(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR2DESC")));
					oEGarantia.setDescripcionPropietario3(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR3DESC")));
					oEGarantia.setDescripcionPropietario4(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR4DESC")));
					oEGarantia.setDescripcionPropietario5(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR5DESC")));
					oEGarantia.setDescripcionPropietario6(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODPR6DESC")));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantia;
	}
	
	
	
	public EGarantiaTramite buscarUltimoAsientoGarantiaTramite(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();	
			lstParametrosEntrada.add(codigoGarantia);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_ULTASIENTOGARANTIATRAMITE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaTramite = new EGarantiaTramite();
					oEGarantiaTramite.setTituloA(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULA")));
					oEGarantiaTramite.setTituloB(UFuncionesGenerales.revisaCadena(oResultSet.getString("TITULB")));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaTramite;
	}
	
	
	public List<ESaldoServicio> obtenerSaldosServiciosCliente(int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESaldoServicio oESaldoServicio= null;
		List<ESaldoServicio> lstSaldoServicio = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CRED_DIR_IND_GARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstSaldoServicio = new ArrayList<ESaldoServicio>();
				while (oResultSet.next()) {
					oESaldoServicio = new ESaldoServicio();
					oESaldoServicio.setClaseServicio(oResultSet.getInt("CLASER"));
					oESaldoServicio.setCodigoGrupoEconomico(oResultSet.getInt("GRUPOE"));
					oESaldoServicio.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oESaldoServicio.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oESaldoServicio.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oESaldoServicio.setMontoSaldo(oResultSet.getDouble("MONTOS"));
					oESaldoServicio.setMontoAcumulado(oResultSet.getDouble("MONSAL"));
					oESaldoServicio.setMontoTotalComision(oResultSet.getDouble("MONCOM"));
					oESaldoServicio.setMontoTotalInteres(oResultSet.getDouble("MONINT"));
					oESaldoServicio.setMontoTotalCuenta(oResultSet.getDouble("TOTCUE"));
					
					lstSaldoServicio.add(oESaldoServicio);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener Saldo de Servicio por Cliente", objEx);
		}
		return lstSaldoServicio;
	}
	
	public ETipoCambio buscarTipoCambioDiario(){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ETipoCambio oETipoCambio= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_TIPOCAMBIODIARIO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oETipoCambio=new ETipoCambio();
					oETipoCambio.setAnio(oResultSet.getInt("AÑO"));
					oETipoCambio.setMes(oResultSet.getInt("MES"));
					oETipoCambio.setValorPromedioMes(oResultSet.getDouble("VPROMD"));
					oETipoCambio.setCompraDiaria(oResultSet.getDouble("COMPRADIARIA"));
					oETipoCambio.setVentaDiaria(oResultSet.getDouble("VENTADIARIA"));		
					oETipoCambio.setTipoCambioSBS(oResultSet.getDouble("SBS"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETipoCambio;
	}
	
	public List<EGarantiaDocumentoSolicitado> listarDocumentoSolicitado(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaDocumentoSolicitado oEGarantiaDocumentoSolicitado= null;
		EUsuario oEUsuario = null;
		List<EGarantiaDocumentoSolicitado> lstGarantiaDocumentoSolicitado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_GARANTIADOCUMENTOSOLICITADO, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstGarantiaDocumentoSolicitado = new ArrayList<EGarantiaDocumentoSolicitado>();
				while (oResultSet.next()) {
					oEGarantiaDocumentoSolicitado = new EGarantiaDocumentoSolicitado();
					oEUsuario = new EUsuario();
					
					oEGarantiaDocumentoSolicitado.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaDocumentoSolicitado.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oEGarantiaDocumentoSolicitado.setCodigoTipoDocumento(oResultSet.getInt("TDOCRE"));
					oEGarantiaDocumentoSolicitado.setNumeroLineaDocumento(oResultSet.getInt("NLINDO"));
					oEGarantiaDocumentoSolicitado.setDescripcionDocumentoSolicitado(UFuncionesGenerales.revisaCadena(oResultSet.getString("DDOCUM")));
					oEGarantiaDocumentoSolicitado.setCodigoTipoOrigenSolicitado(UFuncionesGenerales.revisaCadena(oResultSet.getString("ORICOP")));
					oEGarantiaDocumentoSolicitado.setIndicadorTraido(UFuncionesGenerales.revisaCadena(oResultSet.getString("SWTRAE")));
					oEGarantiaDocumentoSolicitado.setDescripcionDocumentoTraido(UFuncionesGenerales.revisaCadena(oResultSet.getString("TRADOC")));
					oEGarantiaDocumentoSolicitado.setCodigoTipoOrigenTraido(UFuncionesGenerales.revisaCadena(oResultSet.getString("TRAOCP")));
					oEGarantiaDocumentoSolicitado.setFechaTraido(oResultSet.getDate("FECINS"));
					oEGarantiaDocumentoSolicitado.setFechaIngreso(oResultSet.getDate("FECTRS"));
					oEGarantiaDocumentoSolicitado.setHoraIngreso(oResultSet.getString("HORINS"));
					oEGarantiaDocumentoSolicitado.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEGarantiaDocumentoSolicitado.setHoraRegistro(oResultSet.getString("HORREG"));
					
					oEGarantiaDocumentoSolicitado.setDescripcionTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTDO")));
					oEGarantiaDocumentoSolicitado.setDescripcionTipoOrigenSolicitado(oResultSet.getString("DESCTORISOL"));
					oEUsuario.setNombreUsuario(oResultSet.getString("USUREG"));
					oEGarantiaDocumentoSolicitado.setUsuarioRegistro(oEUsuario);
					
					lstGarantiaDocumentoSolicitado.add(oEGarantiaDocumentoSolicitado);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaDocumentoSolicitado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoRelacionado(int codigo) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaCreditoRelacionado oEGarantiaCreditoRelacionado= null;
		List<EGarantiaCreditoRelacionado> lstGarantiaCreditoRelacionado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITORELACIONADOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantiaCreditoRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
				while (oResultSet.next()) {
					oEGarantiaCreditoRelacionado = new EGarantiaCreditoRelacionado();
					oEGarantiaCreditoRelacionado.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantiaCreditoRelacionado.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantiaCreditoRelacionado.setNumeroOperacion(oResultSet.getInt("NUMOPE"));
					lstGarantiaCreditoRelacionado.add(oEGarantiaCreditoRelacionado);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaCreditoRelacionado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoVigenteRelacionado(int codigo) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaCreditoRelacionado oEGarantiaCreditoRelacionado= null;
		List<EGarantiaCreditoRelacionado> lstGarantiaCreditoRelacionado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CREDITOVIGENTERELACIONADOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstGarantiaCreditoRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
				while (oResultSet.next()) {
					oEGarantiaCreditoRelacionado = new EGarantiaCreditoRelacionado();
					oEGarantiaCreditoRelacionado.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantiaCreditoRelacionado.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantiaCreditoRelacionado.setNumeroOperacion(oResultSet.getInt("NUMOPE"));
					lstGarantiaCreditoRelacionado.add(oEGarantiaCreditoRelacionado);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantiaCreditoRelacionado;
	}
	
	public EGarantia buscarGarantia(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantia oEGarantia= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantia=new EGarantia();
					oEGarantia.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setCodigoMoneda(oResultSet.getInt("MONEDA"));	
					oEGarantia.setCodigoSucursal(oResultSet.getInt("SUCURS"));
					oEGarantia.setCodigoAgenciaEmisora(oResultSet.getInt("AGEUSR"));
					oEGarantia.setCodigoDepartamentoEmisor(oResultSet.getInt("UNOPCT"));
					oEGarantia.setDescripcionUsuarioEmisor(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUARI")));
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMPRO")));
					oEGarantia.setGrupoEconomico(oResultSet.getInt("GRUPOE"));
					oEGarantia.setCodigoPropietario(oResultSet.getInt("CODPRO"));
					oEGarantia.setCodigoAval2(oResultSet.getInt("CODAV2"));
					oEGarantia.setCodigoAval3(oResultSet.getInt("CODAV3"));
					oEGarantia.setCodigoAval4(oResultSet.getInt("CODAV4"));
					oEGarantia.setCodigoTipoAval1(oResultSet.getInt("TIPAV1"));
					oEGarantia.setCodigoTipoAval2(oResultSet.getInt("TIPAV2"));
					oEGarantia.setCodigoTipoAval3(oResultSet.getInt("TIPAV3"));
					oEGarantia.setCodigoTipoAval4(oResultSet.getInt("TIPAV4"));			
					oEGarantia.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantia.setHojaIngresoLegal(oResultSet.getInt("HOJINL"));
					oEGarantia.setTipoVariable1(oResultSet.getInt("TIPGA1"));
					oEGarantia.setTipoVariable2(oResultSet.getInt("TIPGA2"));
					oEGarantia.setTipoVariable3(oResultSet.getInt("TIPGA3"));
					oEGarantia.setCodigoEstado(oResultSet.getInt("ESTADO"));
					oEGarantia.setCodigoSituacion(oResultSet.getInt("SITUAC"));
					oEGarantia.setOrigenGarantia(oResultSet.getInt("ORIGEN"));
					oEGarantia.setDesplazamiento(oResultSet.getInt("DESPLA"));
					oEGarantia.setJudicial(oResultSet.getInt("JUDICI"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
					oEGarantia.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("NUMDOC")));
					oEGarantia.setDescripcionA(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRI")));
					oEGarantia.setDescripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRB")));
					oEGarantia.setDescripcionC(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRC")));
					oEGarantia.setComentario(UFuncionesGenerales.revisaCadena(oResultSet.getString("COMENT")));
					oEGarantia.setNumeroPropuesta(oResultSet.getInt("NROPRO"));
					oEGarantia.setTransaccionOrigen(oResultSet.getInt("TRAORI"));
					oEGarantia.setMonedaCredito(oResultSet.getInt("MONCRE"));
					oEGarantia.setGarantizadoActivos(oResultSet.getInt("GARACT"));
					oEGarantia.setInformacionAdicional(oResultSet.getInt("INFADI"));
					oEGarantia.setCodigoTasador(oResultSet.getInt("CODTAS"));
					oEGarantia.setCodigoUbigeo(oResultSet.getInt("UBIGEO"));
					oEGarantia.setFechaIngreso(oResultSet.getDate("FECING"));
					oEGarantia.setFechaUltimoMovimiento(oResultSet.getDate("FECVAR1"));
					oEGarantia.setFechaVencimientoPoliza(oResultSet.getDate("FECVENCPOL"));

					oEGarantia.setFechaGravamen(oResultSet.getDate("FECGRAV"));
					oEGarantia.setFechaValoracion(oResultSet.getDate("FECVALORACION"));
					oEGarantia.setFechaVariable1(oResultSet.getDate("FECVAR1"));
					oEGarantia.setFechaVariable2(oResultSet.getDate("FECVAR2"));
					oEGarantia.setFechaVariable3(oResultSet.getDate("FECVAR3"));
					oEGarantia.setFechaVariable4(oResultSet.getDate("FECVAR4"));
					oEGarantia.setFechaVencimiento(oResultSet.getDate("FECVENCIMIENTO"));
					oEGarantia.setFechaEndoso(oResultSet.getDate("FECENDOSO"));
					oEGarantia.setFechaPropuesta(oResultSet.getDate("FECPROPUESTA"));		
					oEGarantia.setFechaUltimaInspeccion(oResultSet.getDate("FECULTINSPECCION"));
					oEGarantia.setFechaUltimoEstadoRegistrado(oResultSet.getDate("FECULTESTREGIST"));
					oEGarantia.setFechaRemate(oResultSet.getDate("FECREMATE"));	
					oEGarantia.setFechaLiberacion(oResultSet.getDate("FECLIBERACION"));
					oEGarantia.setFechaEjecucion(oResultSet.getDate("FECEJECUCION"));

					oEGarantia.setMontoOriginalGarantia(oResultSet.getDouble("MONORI"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MONTOG"));
					oEGarantia.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantia.setMontoValoracion(oResultSet.getDouble("MONTOV"));
					oEGarantia.setMontoCredito(oResultSet.getDouble("MTOCRE"));
					oEGarantia.setMontoEjecucion(oResultSet.getDouble("MTOEJE"));
					oEGarantia.setMontoVariable1(oResultSet.getDouble("MONTO1"));
					oEGarantia.setMontoVariable2(oResultSet.getDouble("MONTO2"));
					oEGarantia.setMontoVariable3(oResultSet.getDouble("MONTO3"));
					oEGarantia.setMontoVariable4(oResultSet.getDouble("MONTO4"));
					
					oEGarantia.setCodigoInspector(oResultSet.getInt("CODISP"));
					oEGarantia.setCodigoTipoBien(oResultSet.getInt("TIPBIE"));
					oEGarantia.setDepositario(oResultSet.getInt("DEPOSI"));
					oEGarantia.setAlmacen(oResultSet.getInt("ALMACE"));
					oEGarantia.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantia.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantia.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantia.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantia.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantia.setNumeroVariable1(oResultSet.getInt("NUMER1")); 
					oEGarantia.setNumeroVariable2(oResultSet.getInt("NUMER2")); 
					oEGarantia.setNumeroVariable3(oResultSet.getInt("NUMER3")); 				
					oEGarantia.setNumeroVariable4(oResultSet.getInt("NUMER4")); 
					oEGarantia.setAnioFabricacion(oResultSet.getInt("AÑOVAR")); 
					oEGarantia.setTipoAlmacen(oResultSet.getInt("TIPALM"));
					oEGarantia.setBancoEmisor(oResultSet.getInt("BANCO"));
					oEGarantia.setCuenta(oResultSet.getInt("SERDOC"));
					oEGarantia.setRangoHipoteca(UFuncionesGenerales.revisaCadena(oResultSet.getString("RANHIP")));
					oEGarantia.setUsuarioUltimaModificacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUMOD")));		
					oEGarantia.setMarcaProtestado(oResultSet.getInt("PROTES"));
					oEGarantia.setMarcaRemate(oResultSet.getInt("REMATE"));
					oEGarantia.setUbicacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAC")));	
					oEGarantia.setUbicacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("UBICAB")));
					oEGarantia.setPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEGarantia.setCodigoTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEGarantia.setValorPoliza(oResultSet.getDouble("VALPOL"));
					oEGarantia.setDescripcionUsoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCRI")));
					oEGarantia.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));				
					oEGarantia.setDescripcionTipoBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPBIEN")));
					oEGarantia.setNumeroEndoso(UFuncionesGenerales.revisaCadena(oResultSet.getString("ENDOSO")));
					oEGarantia.setDescripcionSituacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSITUAC")));
					oEGarantia.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPGAR")));
					oEGarantia.setDescripcionEstadoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCEST")));
					oEGarantia.setDescripcionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
					oEGarantia.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantia.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEGarantia.setDescripcionBanco(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCBANCO")));
					oEGarantia.setDescripcionTipoFiador(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPOFIADOR")));
					oEGarantia.setDescripcionTipoFianza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPOFIANZA")));
					oEGarantia.setDescripcionTipoPlazo(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPOPLAZO")));
					oEGarantia.setDescripcionPropietario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCPROPIETARIOBIEN")));
					oEGarantia.setDescripcionTasador(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTASADOR")));
					oEGarantia.setDescripcionDepositario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCDEPOSITARIO")));
					oEGarantia.setMontoDisponible(oResultSet.getDouble("MTODIS"));
					oEGarantia.setSaldoDisponible(oResultSet.getDouble("SLDDIS"));
					oEGarantia.setPorcentajeDisponible(oResultSet.getDouble("PORDIS"));
					oEGarantia.setNumeroRegistro(oResultSet.getInt("REGIST"));
				}											
		    }
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantia;
	}
	
	
	
	
	public EGarantiaTramite buscarGarantiaTramite(long codigoGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaTramite oEGarantiaTramite = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIATRAMITE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaTramite=new EGarantiaTramite();
					oEGarantiaTramite.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantiaTramite.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEGarantiaTramite.setFechaIngreso(oResultSet.getDate("FECINL"));
					oEGarantiaTramite.setFechaElaboracionContrato(oResultSet.getDate("FECELC"));
					oEGarantiaTramite.setFechaFirmaContrato(oResultSet.getDate("FECFIC"));
					oEGarantiaTramite.setFechaIngresoNotaria(oResultSet.getDate("FECFIN"));
					oEGarantiaTramite.setFechaLegalizacionFirma(oResultSet.getDate("FECLEF"));
					oEGarantiaTramite.setFechaIngresoRegistro(oResultSet.getDate("FECINR"));
					oEGarantiaTramite.setFechaVigenciaAsiento(oResultSet.getDate("FECVAP"));
					oEGarantiaTramite.setFechaObservacion(oResultSet.getDate("FECOBS"));
					oEGarantiaTramite.setFechaTacha(oResultSet.getDate("FECTAC"));
					oEGarantiaTramite.setFechaInscripcion(oResultSet.getDate("FECINS"));
					oEGarantiaTramite.setNumeroKardex(oResultSet.getInt("KARDEN"));
					oEGarantiaTramite.setCodigoNotario(oResultSet.getInt("NOTARI"));
					oEGarantiaTramite.setFichaInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("FICINS")));
					oEGarantiaTramite.setTomoInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("TOMINS")));
					oEGarantiaTramite.setCiudadInscripcion(UFuncionesGenerales.revisaCadena(oResultSet.getString("CIUINS")));
					oEGarantiaTramite.setNumeroHojaIngresoLegal(oResultSet.getInt("HOJINL"));
					oEGarantiaTramite.setDescripcionObservacionBloqueo1(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE1")));
					oEGarantiaTramite.setDescripcionObservacionBloqueo2(UFuncionesGenerales.revisaCadena(oResultSet.getString("BOBSE2")));
					oEGarantiaTramite.setObservacion1(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1")));
					oEGarantiaTramite.setObservacion2(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2")));
					oEGarantiaTramite.setEvaluacionDocumento(oResultSet.getInt("EVADOC"));
					oEGarantiaTramite.setUsuarioElaboracionContrato(UFuncionesGenerales.revisaCadena(oResultSet.getString("USRELC")));
					
					oEGarantiaTramite.setFechaIngresoRegistroB(oResultSet.getDate("FECBINR"));
					oEGarantiaTramite.setFechaVigenciaAsientoB(oResultSet.getDate("FECBVAP"));
					oEGarantiaTramite.setFechaObservacionB(oResultSet.getDate("FECBOBS"));
					oEGarantiaTramite.setFechaTachaB(oResultSet.getDate("FECBTAC"));
					oEGarantiaTramite.setFechaInscripcionB(oResultSet.getDate("FECBINS"));
					oEGarantiaTramite.setFichaInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BFICIN")));
					oEGarantiaTramite.setTomoInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BTOMIN")));
					oEGarantiaTramite.setCiudadInscripcionB(UFuncionesGenerales.revisaCadena(oResultSet.getString("BCIUIN")));
					oEGarantiaTramite.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOTARIO")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaTramite;
	}
	
	public EGarantiaSolicitud buscarGarantiaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaSolicitud=new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					//NUMERO REVISION
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoServicioGarantia(oResultSet.getInt("SERGAR"));
					oEGarantiaSolicitud.setCodigoMonedaGarantia(oResultSet.getInt("MONGAR"));
					oEGarantiaSolicitud.setCuentaGarantia(oResultSet.getInt("CTAGAR"));
					oEGarantiaSolicitud.setDocumentoGarantia(oResultSet.getInt("DOCGAR"));
					oEGarantiaSolicitud.setNumeroGarantiaReal(oResultSet.getInt("GARGAR"));
					oEGarantiaSolicitud.setGrupoTanomoshi(oResultSet.getInt("GRUGAR"));
					oEGarantiaSolicitud.setNumeroListaTanomoshi(oResultSet.getInt("LISGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaOtro(UFuncionesGenerales.revisaCadena(oResultSet.getString("OTRGAR")));
					oEGarantiaSolicitud.setDescripcionGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaSolicitud.setMontoOrigenGarantia(oResultSet.getDouble("MTOORI"));
					//TIPO CAMBIO
					oEGarantiaSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaSolicitud.setPlaca(UFuncionesGenerales.revisaCadena(oResultSet.getString("PLACA")));
					oEGarantiaSolicitud.setClase(UFuncionesGenerales.revisaCadena(oResultSet.getString("CLASE")));
					oEGarantiaSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MARCA")));
					oEGarantiaSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MODELO")));
					oEGarantiaSolicitud.setCarroceria(UFuncionesGenerales.revisaCadena(oResultSet.getString("CARROC")));
					oEGarantiaSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("NMOTOR")));
					oEGarantiaSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("CHASIS")));
					oEGarantiaSolicitud.setColor(UFuncionesGenerales.revisaCadena(oResultSet.getString("COLORV")));
					oEGarantiaSolicitud.setCodigoClase(oResultSet.getInt("CCLASE"));
					oEGarantiaSolicitud.setCodigoMarca(oResultSet.getInt("CMARCA"));
					oEGarantiaSolicitud.setCodigoModelo(oResultSet.getInt("CMODEL"));
					oEGarantiaSolicitud.setCodigoCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("CCOMBU")));
					oEGarantiaSolicitud.setDescripcionCombustible(UFuncionesGenerales.revisaCadena(oResultSet.getString("COMBUS")));
					oEGarantiaSolicitud.setCodigoNivelRiesgo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRIESG")));
					oEGarantiaSolicitud.setAnioFabricacion(oResultSet.getInt("AÑOFAB"));
					oEGarantiaSolicitud.setMontoValorizacion(oResultSet.getDouble("VALORI"));
					oEGarantiaSolicitud.setIndicadorNuevo(UFuncionesGenerales.revisaCadena(oResultSet.getString("VNUEVO")));
					
					oEGarantiaSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTGAR")));
					oEGarantiaSolicitud.setDescripcionTipoGarantiaReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTGARR")));
					oEGarantiaSolicitud.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantiaSolicitud.setDescripcionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMON")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaSolicitud;
	}
	
	public EGarantiaSolicitud buscarGarantiaAsociadaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantiaSolicitud = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIAASOCIADASOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaSolicitud=new EGarantiaSolicitud();
					oEGarantiaSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					//oEGarantiaSolicitud.setNumeroRevision(oResultSet.getInt("NREVIS"));
					//oEGarantiaSolicitud.setNombreCorto(oResultSet.getString("NOMABV"));
					//oEGarantiaSolicitud.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					//oEGarantiaSolicitud.setCodigoEstadoSolCredito(oResultSet.getInt("ESTADO"));
					//oEGarantiaSolicitud.setCodigoEstadoRevision(oResultSet.getString("STATUS"));
					//oEGarantiaSolicitud.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					//oEGarantiaSolicitud.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));
					oEGarantiaSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantiaReal(oResultSet.getInt("TIPGAR"));
					oEGarantiaSolicitud.setCodigoTipoGarantia(oResultSet.getInt("GARTIP"));
					oEGarantiaSolicitud.setCodigoNroIngresoGarantia(oResultSet.getInt("GARANI"));
					oEGarantiaSolicitud.setCodigoEstadoGarantiaSolicitud(oResultSet.getInt("ESTADOSG"));
					oEGarantiaSolicitud.setUsuarioCredito(oResultSet.getString("USUREG"));
					oEGarantiaSolicitud.setFechaCredito(oResultSet.getDate("FECREG"));
					oEGarantiaSolicitud.setUsuarioLegal(oResultSet.getString("USULEG"));
					oEGarantiaSolicitud.setFechaLegal(oResultSet.getDate("FECLEG"));			
					oEGarantiaSolicitud.setObservacion(oResultSet.getString("OBSERV"));
					oEGarantiaSolicitud.setMontoTasacion(oResultSet.getDouble("MONTAS"));
					oEGarantiaSolicitud.setMontoGravamen(oResultSet.getDouble("MONGRA"));
					oEGarantiaSolicitud.setMontoValorRealizacion(oResultSet.getDouble("MONTOV"));
					oEGarantiaSolicitud.setPorcentajeAsignado(oResultSet.getDouble("PORCUSO"));
					oEGarantiaSolicitud.setCodigoCiaSeguro(oResultSet.getInt("CIASEG"));
					oEGarantiaSolicitud.setDescripcionCiaSeguro(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCIASEG")));
					oEGarantiaSolicitud.setPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("POLIZA")));
					oEGarantiaSolicitud.setValorPoliza(oResultSet.getDouble("VALPOL"));	
					oEGarantiaSolicitud.setCorrelativoPoliza(oResultSet.getInt("CORPOL"));
					oEGarantiaSolicitud.setTipoPoliza(oResultSet.getInt("TIPPOL"));
					oEGarantiaSolicitud.setFechaVencimientoPoliza(oResultSet.getDate("FECVENCPOLIZA"));
					oEGarantiaSolicitud.setSaldoDisponible(oResultSet.getDouble("SLDDIS"));
					oEGarantiaSolicitud.setFechaComercial(oResultSet.getDate("FECCOM"));
					oEGarantiaSolicitud.setMontoComercial(oResultSet.getDouble("MONCOM"));
					//oEGarantiaSolicitud.setDescripcionTipoPoliza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPOL")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaSolicitud;
	}
	
	public EGarantiaDetalleSolicitud buscarGarantiaDetalleSolicitud(long numeroSolicitud, int secuenciaGarantia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitud= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuenciaGarantia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_GARANTIADETALLESOLICITUD, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEGarantiaDetalleSolicitud = new EGarantiaDetalleSolicitud();
					oEGarantiaDetalleSolicitud.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEGarantiaDetalleSolicitud.setSecuenciaGarantia(oResultSet.getInt("SECGAR"));
					oEGarantiaDetalleSolicitud.setCodigoTipoGarantia(oResultSet.getInt("TIPGAR"));
					oEGarantiaDetalleSolicitud.setDescripcionGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESGAR")));
					oEGarantiaDetalleSolicitud.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantiaDetalleSolicitud.setMarca(UFuncionesGenerales.revisaCadena(oResultSet.getString("MAMARC")));
					oEGarantiaDetalleSolicitud.setModelo(UFuncionesGenerales.revisaCadena(oResultSet.getString("MAMODE")));
					oEGarantiaDetalleSolicitud.setSerie(UFuncionesGenerales.revisaCadena(oResultSet.getString("MASERI")));
					oEGarantiaDetalleSolicitud.setMotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("MAMOTO")));
					oEGarantiaDetalleSolicitud.setCantidad(oResultSet.getInt("MACANT"));
					oEGarantiaDetalleSolicitud.setCodigoTipoPrenda(oResultSet.getInt("MATPRE"));
					oEGarantiaDetalleSolicitud.setDireccion(UFuncionesGenerales.revisaCadena(oResultSet.getString("PRDIRE")));
					oEGarantiaDetalleSolicitud.setCodigoUbigeo(oResultSet.getInt("PRUBIG"));
					oEGarantiaDetalleSolicitud.setAreaTerreno(oResultSet.getDouble("PRATER"));
					oEGarantiaDetalleSolicitud.setAreaConstruida(oResultSet.getDouble("PRACON"));
					oEGarantiaDetalleSolicitud.setNumeroPisos(oResultSet.getInt("PRPISO"));
					oEGarantiaDetalleSolicitud.setUsoPredio(UFuncionesGenerales.revisaCadena(oResultSet.getString("PRUSOP")));
					oEGarantiaDetalleSolicitud.setCodigoTipoPrenda2(oResultSet.getInt("PRTPRE"));
					
					oEGarantiaDetalleSolicitud.setDescripcionTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTGARR")));
					//oEGarantiaDetalleSolicitud.setDescripcionTipoPrenda(UFuncionesGenerales.revisaCadena(oResultSet.getString("")));
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEGarantiaDetalleSolicitud;
	}
}
