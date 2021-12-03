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

public class DAOSolicitudCredito extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_LOGMOVIMIENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_LOGMOVIMIENTO("+parametrosSP(7)+") }";
	private static final String SP_ABACOINLEGAL_INS_SOLICITUDSUSCRIPCION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_SOLICITUDSUSCRIPCION("+parametrosSP(11)+") }";
	private static final String SP_ABACOINLEGAL_INS_CLIENTESUSCRIPCION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_CLIENTESUSCRIPCION("+parametrosSP(9)+") }";
	private static final String SP_ABACOINLEGAL_INS_EVALUACIONNEGOCIOS_MAESTRA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_EVALUACIONNEGOCIOS_MAESTRA("+parametrosSP(13)+") }";
	private static final String SP_ABACOINLEGAL_INS_EVALUACIONXNIVEL_MAESTRA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_EVALUACIONXNIVEL_MAESTRA("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_INS_EVALUACIONXNIVEL_DETALLE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_EVALUACIONXNIVEL_DETALLE("+parametrosSP(7)+") }";
	private static final String SP_ABACOINLEGAL_UPD_SOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_SOLICITUDCREDITO("+parametrosSP(59)+") }";
	private static final String SP_ABACOINLEGAL_UPD_DEUDOR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_DEUDOR("+parametrosSP(34)+") }";
	
	private static final String SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL("+parametrosSP(27)+") }";
	private static final String SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL2="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL2("+parametrosSP(27)+") }";
	
	private static final String SP_ABACOINLEGAL_DEL_DEUDOR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_DEUDOR("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_DEL_EVALUACIONXNIVEL_DETALLE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_EVALUACIONXNIVEL_DETALLE("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_DEL_SOLICITUDSUSCRIPCION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_SOLICITUDSUSCRIPCION("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_DEL_CLIENTESUSCRIPCION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_CLIENTESUSCRIPCION("+parametrosSP(3)+") }";
	
	private static final String SP_ABACOINLEGAL_SEL_SOLICITUDCREDITOPORREGISTRAR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLICITUDCREDITOPORREGISTRAR() }";
	private static final String SP_ABACOINLEGAL_SEL_DEUDOR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_DEUDOR("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_AVALSOLICITUD="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_AVALSOLICITUD("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_EVALUACIONXNIVEL_DETALLE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_EVALUACIONXNIVEL_DETALLE("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_EVALUACIONNEGOCIOS_MAESTRA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_EVALUACIONNEGOCIOS_MAESTRA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOLICITUDSUSCRIPCION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLICITUDSUSCRIPCION("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CLIENTESUSCRIPCION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CLIENTESUSCRIPCION("+parametrosSP(1)+") }";
	
	private static final String SP_ABACOINLEGAL_BUS_SOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOLICITUDCREDITO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_EVALUACIONSOLICITUDCREDITOLEGAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_EVALUACIONSOLICITUDCREDITOLEGAL("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_INFORMELEGALADICIONAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_INFORMELEGALADICIONAL("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_DEUDORESTADO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_DEUDORESTADO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_DEUDORADICIONAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_DEUDORADICIONAL("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_EVALUACIONNEGOCIOS_MAESTRA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_EVALUACIONNEGOCIOS_MAESTRA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CREDITO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_AVALTERCERO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_AVALTERCERO("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_EMAIL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_EMAIL("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_SOLICITUDXCARTAFIANZA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOLICITUDXCARTAFIANZA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOLCREDITO_SOCIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOLCREDITO_SOCIO("+parametrosSP(1)+") }";
	
	
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
	
	public DAOSolicitudCredito(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregarLogMovimiento(ESolicitudLogMovimiento eESolicitudLogMovimiento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eESolicitudLogMovimiento.getNumeroSolicitud());
			lstParametrosEntrada.add(eESolicitudLogMovimiento.getCodigoAccion());			
			lstParametrosEntrada.add(eESolicitudLogMovimiento.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eESolicitudLogMovimiento.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eESolicitudLogMovimiento.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_LOGMOVIMIENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarSuscripcion(ESuscripcion eESuscripcion) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eESuscripcion.getNumeroSolicitud());
			lstParametrosEntrada.add(eESuscripcion.getCodigoCliente());
			lstParametrosEntrada.add(eESuscripcion.getCodigoTipoCliente());
			lstParametrosEntrada.add(eESuscripcion.getCodigoOrden());
			lstParametrosEntrada.add(eESuscripcion.getNumeroSuscripcion());
			lstParametrosEntrada.add(eESuscripcion.getNombreLargo());			
			lstParametrosEntrada.add(eESuscripcion.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eESuscripcion.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eESuscripcion.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_SOLICITUDSUSCRIPCION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarClienteSuscripcion(ESuscripcion eESuscripcion) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eESuscripcion.getNumeroDocumento());
			lstParametrosEntrada.add(eESuscripcion.getCodigoOrden());
			lstParametrosEntrada.add(eESuscripcion.getNumeroSuscripcion());
			lstParametrosEntrada.add(eESuscripcion.getNombreLargo());	
			lstParametrosEntrada.add(eESuscripcion.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eESuscripcion.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eESuscripcion.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_CLIENTESUSCRIPCION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarObservacionNegociosMaestra(EObservacionNegocios eObservacionNegocios) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try{
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eObservacionNegocios.getNumeroSolicitud());
			lstParametrosEntrada.add(eObservacionNegocios.getSecuencia());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eObservacionNegocios.getObservacion(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eObservacionNegocios.getObservacion(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eObservacionNegocios.getObservacion(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eObservacionNegocios.getObservacion(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eObservacionNegocios.getObservacion(),4));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eObservacionNegocios.getObservacion(),5));
			lstParametrosEntrada.add(eObservacionNegocios.getNombreUsuario());
			lstParametrosEntrada.add(eObservacionNegocios.getFechaEvaluacion());
			lstParametrosEntrada.add(formato.format(eObservacionNegocios.getFechaEvaluacion()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_EVALUACIONNEGOCIOS_MAESTRA, lstParametrosEntrada);
		}catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		
		return mensaje; 
	}
	
	public EMensaje agregarObservacionMaestra(EObservacionLegal eObservacionLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try{
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eObservacionLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eObservacionLegal.getSecuencia());
			lstParametrosEntrada.add(eObservacionLegal.getCodigoEstado());
			lstParametrosEntrada.add(eObservacionLegal.getNombreUsuario());
			lstParametrosEntrada.add(eObservacionLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eObservacionLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_EVALUACIONXNIVEL_MAESTRA, lstParametrosEntrada);
		}catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		
		return mensaje; 
	}
	
	/**
	 * Metodo que agrega el Detalle de la evaluacion de Solicitud de Credito x Nivel donde
	 * su funcion principal es agregar la observacion legal linea por linea de acuerdo
	 * a la tabla SIAFD2000.F7322
	 */
	public EMensaje agregarObservacionDetalle(EObservacionLegal eObservacionLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try{
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eObservacionLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eObservacionLegal.getSecuencia());
			lstParametrosEntrada.add(eObservacionLegal.getLineaObservacion());
			lstParametrosEntrada.add(eObservacionLegal.getDescripcionMensaje());
			lstParametrosEntrada.add(eObservacionLegal.getNombreUsuario());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_EVALUACIONXNIVEL_DETALLE, lstParametrosEntrada);
		}catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		
		return mensaje; 
	}
	
	public EMensaje modificarSolicitudCredito(EEvaluacionSolicitudCreditoLegal eEvaluacionSolicitudCreditoLegal, EOperacionSolicitud eOperacionSolicitud) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoCliente());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoPersona());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroDocumento());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getRuc());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoPaterno());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoMaterno());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombre());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombreLargo());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDireccionReal());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoUbigeoReal());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDireccionContractual());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoUbigeoContractual());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDocumentoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoPaternoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoMaternoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombreConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombreLargoConyuge());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroAcciones());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDescripcionAvalarTercero());
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionAvalarTercero(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionGrabarBien(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),4));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),5));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),6));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),7));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),8));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionSolicitud(),9));
			
			lstParametrosEntrada.add(eOperacionSolicitud.getCodigoEstado());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eEvaluacionSolicitudCreditoLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_SOLICITUDCREDITO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarDeudor(EDeudor eDeudor) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eDeudor.getCodigoCliente());
			lstParametrosEntrada.add(eDeudor.getNumeroSolicitud());
			lstParametrosEntrada.add(eDeudor.getCodigoDeudor());
			lstParametrosEntrada.add(eDeudor.getTercero().getCodigoTipoDocumento());
			lstParametrosEntrada.add(eDeudor.getTercero().getDocumento());
			lstParametrosEntrada.add(eDeudor.getTercero().getNombreCorte());
			lstParametrosEntrada.add(eDeudor.getTercero().getNombreLargo());
			lstParametrosEntrada.add(eDeudor.getTercero().getSiglas());
			
			lstParametrosEntrada.add(eDeudor.getTercero().getDireccion());
			lstParametrosEntrada.add(eDeudor.getTercero().getCodigoUbigeo());
			lstParametrosEntrada.add(eDeudor.getTercero().getDireccionPostal());
			lstParametrosEntrada.add(eDeudor.getTercero().getCodigoUbigeoPostal());
			
			lstParametrosEntrada.add(eDeudor.getTercero().getTelefono1());
			lstParametrosEntrada.add(eDeudor.getTercero().getTelefono2());
			lstParametrosEntrada.add(eDeudor.getTercero().getAnexo());
			lstParametrosEntrada.add(eDeudor.getTercero().getFax());
			lstParametrosEntrada.add(eDeudor.getTercero().getCodigoCIIU());
			
			lstParametrosEntrada.add(eDeudor.getAdicional().getMontoLimite());
			lstParametrosEntrada.add(eDeudor.getAdicional().getMontoAdicional());
			lstParametrosEntrada.add(eDeudor.getAdicional().getCodigoTipoPago());
			lstParametrosEntrada.add(eDeudor.getAdicional().getCodigoFormaPago());
			
			lstParametrosEntrada.add(eDeudor.getEstado().getCodigoEstadoUltimo());
			lstParametrosEntrada.add(eDeudor.getEstado().getCodigoEstado());
			lstParametrosEntrada.add(eDeudor.getEstado().getObservacionUltimo());
			lstParametrosEntrada.add(eDeudor.getEstado().getObservacion());
			lstParametrosEntrada.add(eDeudor.getEstado().getUsuarioUltimo());
			lstParametrosEntrada.add(eDeudor.getEstado().getFechaRegistroUltimo());
			lstParametrosEntrada.add(eDeudor.getEstado().getHoraRegistroUltimo());
			
			lstParametrosEntrada.add(eDeudor.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eDeudor.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eDeudor.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eDeudor.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_DEUDOR, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarInformeLegalAdicional(EInformeLegalAdicional eEInformeLegalAdicional) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroSolicitud());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoCliente());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoCliente());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getOficinaRegistral());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoDuracionPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getRegistroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroAcciones());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoValorSuscripcion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getMontoValorNominal());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionPatrimonio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getAsiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoInicio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoVencimiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eEInformeLegalAdicional.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarInformeLegalAdicional2(EInformeLegalAdicional eEInformeLegalAdicional) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroSolicitud());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoCliente());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoCliente());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getOficinaRegistral());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoDuracionPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getRegistroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroAcciones());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoValorSuscripcion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getMontoValorNominal());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionPatrimonio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getAsiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoInicio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoVencimiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eEInformeLegalAdicional.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL2, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarDeudor(EDeudor eDeudor) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eDeudor.getCodigoCliente());
			lstParametrosEntrada.add(eDeudor.getNumeroSolicitud());
			lstParametrosEntrada.add(eDeudor.getCodigoDeudor());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_DEUDOR, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarObservacionDetalle(EObservacionLegal eObservacionLegal) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eObservacionLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eObservacionLegal.getSecuencia());
			lstParametrosEntrada.add(eObservacionLegal.getNombreUsuario());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_EVALUACIONXNIVEL_DETALLE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarSuscripcion(long numeroSolicitud, int codigoCliente, int codigoTipoCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(codigoTipoCliente);
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_SOLICITUDSUSCRIPCION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarClienteSuscripcion(String numeroDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroDocumento);
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_CLIENTESUSCRIPCION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EEvaluacionSolicitudCreditoLegal> listarInformeLegalPorRegistrar() {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegal= null;
		List<EEvaluacionSolicitudCreditoLegal> lstEvaluacionSolicitudCreditoLegal = null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLICITUDCREDITOPORREGISTRAR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstEvaluacionSolicitudCreditoLegal = new ArrayList<EEvaluacionSolicitudCreditoLegal>();
				while (oResultSet.next()) {
					oEEvaluacionSolicitudCreditoLegal=new EEvaluacionSolicitudCreditoLegal();
					oEEvaluacionSolicitudCreditoLegal.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEEvaluacionSolicitudCreditoLegal.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEEvaluacionSolicitudCreditoLegal.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEEvaluacionSolicitudCreditoLegal.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					
					oEEvaluacionSolicitudCreditoLegal.setCodigoAreaRegistroLegal(oResultSet.getInt("CODAREREGL"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoUsuarioRegistroLegal(oResultSet.getInt("CODUSREGL"));
					oEEvaluacionSolicitudCreditoLegal.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEEvaluacionSolicitudCreditoLegal.setDescripcionUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCUSREGL")));
					
					oEEvaluacionSolicitudCreditoLegal.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEEvaluacionSolicitudCreditoLegal.setHoraRegistroLegal(oResultSet.getString("HOREGL"));
					
					lstEvaluacionSolicitudCreditoLegal.add(oEEvaluacionSolicitudCreditoLegal);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstEvaluacionSolicitudCreditoLegal;
	}
	
	public List<EDeudor> listarDeudor(int codigoCliente, long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EDeudor oEDeudor= null;
		ETercero oETercero= null;
		EDeudorAdicional oEDeudorAdicional = null;
		List<EDeudor> lstDeudor = null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_DEUDOR, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstDeudor = new ArrayList<EDeudor>();
				while (oResultSet.next()) {
					oEDeudor = new EDeudor();
					oETercero = new ETercero();
					oEDeudorAdicional = new EDeudorAdicional();
					
					oEDeudor.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEDeudor.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEDeudor.setCodigoDeudor(oResultSet.getInt("CODDEU"));
					oEDeudor.setNombreCorte(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEDeudor.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					
					oETercero.setCodigoCliente(oResultSet.getInt("CODDEU"));
					oETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oETercero.setDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oETercero.setNombreCorte(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oETercero.setSiglas(UFuncionesGenerales.revisaCadena(oResultSet.getString("SIGLAS")));
					oETercero.setDireccion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oETercero.setCodigoUbigeo(oResultSet.getInt("CCIUDA"));
					oETercero.setDireccionPostal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					oETercero.setCodigoUbigeoPostal(oResultSet.getInt("CCIUDP"));
					oETercero.setTelefono1(UFuncionesGenerales.revisaCadena(oResultSet.getString("TELEFO")));
					oETercero.setTelefono2(UFuncionesGenerales.revisaCadena(oResultSet.getString("TELEFB")));
					oETercero.setAnexo(UFuncionesGenerales.revisaCadena(oResultSet.getString("TELANE")));
					oETercero.setFax(UFuncionesGenerales.revisaCadena(oResultSet.getString("FAX")));
					oETercero.setCodigoCIIU(oResultSet.getString("CODCIU"));
					/*
					oEDeudorAdicional.setMontoLimite(oResultSet.getDouble("LIMITE"));
					oEDeudorAdicional.setMontoAdicional(oResultSet.getDouble("ADICIO"));
					oEDeudorAdicional.setFechaVencimiento(oResultSet.getDate("FECVTA"));
					oEDeudorAdicional.setCodigoTipoPago(oResultSet.getInt("TPOPAG"));
					oEDeudorAdicional.setCodigoFormaPago(oResultSet.getInt("FORPAG"));
					*/
					oEDeudor.setTercero(oETercero);
					oEDeudor.setAdicional(oEDeudorAdicional);
					
					lstDeudor.add(oEDeudor);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstDeudor;
	}
	
	public List<EAval> listarAval(int codigoCliente,long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EAval oEAval = null;
		List<EAval> lstAval = null;			
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_AVALSOLICITUD, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstAval = new ArrayList<EAval>();
				while (oResultSet.next()) {
					oEAval = new EAval();
					oEAval.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEAval.setCodigoAval(oResultSet.getInt("CODAVA"));
					oEAval.setNombreAval(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMAVA")));
					oEAval.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEAval.setNumeroContrato(oResultSet.getInt("NROCOT"));
					oEAval.setNumeroPagare(oResultSet.getInt("NROPAG"));
					oEAval.setMontoAvalado(oResultSet.getDouble("MTOAVA"));
					oEAval.setSaldoAvalado(oResultSet.getDouble("SLDAVA"));					
					lstAval.add(oEAval);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstAval;
	}
	
	public ETercero buscarAval(int codigoAval,long nroSolicitud) {
		List<Object> lstParametrosEntrada;		
		ResultSet oResultSet = null;
		ETercero oETercero = null;
		EUsuario oEUsuario = null;		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoAval);
			lstParametrosEntrada.add(nroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_AVALTERCERO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oETercero= new ETercero();
					oEUsuario = new EUsuario();
					oETercero.setCodigoCliente(oResultSet.getInt("CODAVA"));
					oETercero.setNombreLargo(oResultSet.getString("NOMBCL"));
					oETercero.setNombreCorte(oResultSet.getString("NOMABV"));
					oETercero.setCodigoTipoDocumento(oResultSet.getString("TIPDOC"));
					oETercero.setDocumento(oResultSet.getString("DOCUME"));
					oETercero.setDireccion(oResultSet.getString("DIRECC"));
					oETercero.setCodigoUbigeo(oResultSet.getInt("CCIUDA"));
					oETercero.setTelefono1(oResultSet.getString("TELEFO"));
					oETercero.setAnexo(oResultSet.getString("TELANE"));
					oETercero.setFax(oResultSet.getString("FAX"));
					oETercero.setDireccionPostal(oResultSet.getString("DIRECP"));
					oETercero.setCodigoUbigeoPostal(oResultSet.getInt("CCIUDP"));
					oETercero.setCodigoTipoPersona(oResultSet.getString("CLAPER"));
					oETercero.setDescripcionTipoPersona(oResultSet.getString("DESCLASPER"));
					oETercero.setSucursalApertura(oResultSet.getString("SUCAPE"));
					oETercero.setCodigoBanca(oResultSet.getString("BANCA"));
					oETercero.setCodigoCIIU(oResultSet.getString("CODCIU"));
					oETercero.setCodigoComunidad(oResultSet.getString("COMUNI"));
					oETercero.setDescripcionComunidad(oResultSet.getString("DESCOMU"));
					oETercero.setFechaApertura(UFuncionesGenerales.convertirCadenaAFecha(oResultSet.getString("AÑOAPE")+"-"+oResultSet.getString("MESAPE")+"-"+oResultSet.getString("DIAAPE"), "yyyy-MM-dd"));
					oEUsuario.setNombreUsuario(oResultSet.getString("USUARI"));
					oETercero.setUsuarioRegistro(oEUsuario);
											
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	public EEmail buscarEmailCliente(int codigoCliente) {
		List<Object> lstParametrosEntrada;		
		ResultSet oResultSet = null;
		EEmail oEmail = null;		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_EMAIL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEmail = new EEmail();
					oEmail.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEmail.setEmail1(oResultSet.getString("EMAIL1"));
					oEmail.setEmail2(oResultSet.getString("EMAIL2"));
					oEmail.setEmail3(oResultSet.getString("EMAIL3"));														
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEmail;
	}
	
	public ESolicitudCredito buscarSolicitudCredito(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESolicitudCredito oESolicitudCredito= null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOLICITUDCREDITO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oESolicitudCredito=new ESolicitudCredito();
					oESolicitudCredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oESolicitudCredito.setNroRevision(oResultSet.getInt("NREVIS"));
					oESolicitudCredito.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oESolicitudCredito.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVNOMCLI")));
					oESolicitudCredito.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oESolicitudCredito.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					oESolicitudCredito.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					//oESolicitudCredito.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPRODU")));
					oESolicitudCredito.setDescripcionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPRODU")));
					oESolicitudCredito.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));
					oESolicitudCredito.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oESolicitudCredito.setDescripcionMonedaSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMONSOL")));
					oESolicitudCredito.setObjeto(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBJETO")));
					oESolicitudCredito.setCriterios(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2"))+"\n"+
							UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER3"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER4"))+"\n"+
							UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER5"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER6")));
					oESolicitudCredito.setNombreUsuarioPromotor(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREVF")));
					oESolicitudCredito.setReferenciaLineaCredito(oResultSet.getInt("REFLIN"));
					oESolicitudCredito.setPlazoSolicitud(oResultSet.getInt("PLZSOL"));
					oESolicitudCredito.setTasaSolicitud(oResultSet.getInt("TASSOL"));
					oESolicitudCredito.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oESolicitudCredito.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oESolicitudCredito.setCodigoTipoDocumentoFactoring(oResultSet.getInt("TDOLIN"));
					oESolicitudCredito.setDescripcionTipoDocumentoFactoring(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOCFACT")));
					oESolicitudCredito.setTasaComisionFlatDesembolso(oResultSet.getDouble("TACOMI"));
					oESolicitudCredito.setCobroComisionDesembolso(UFuncionesGenerales.revisaCadena(oResultSet.getString("COCOMI")));
					oESolicitudCredito.setCodigoModalidadLineaCredito(oResultSet.getInt("MODLIN"));
					oESolicitudCredito.setDescripcionModalidadLineaCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMODLIN")));
					oESolicitudCredito.setCodigoServicio(oResultSet.getInt("SERLIN"));
					oESolicitudCredito.setDescripcionServicio(UFuncionesGenerales.revisaCadena(oResultSet.getString("SERVICIO")));
					oESolicitudCredito.setFechaVencimiento(oResultSet.getDate("FECVENCLIN"));
					oESolicitudCredito.setCodigoTipoTasaSolicitud(oResultSet.getInt("TTASOL"));
					oESolicitudCredito.setDescripcionTipoTasaSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPTASSOL")));
					oESolicitudCredito.setDeudorGirador(UFuncionesGenerales.revisaCadena(oResultSet.getString("DEUDORGIRADOR")));			
					oESolicitudCredito.setTipoGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIGAAV")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oESolicitudCredito;
	}

	public ESolicitudCredito buscarSolicitudCartaFianza(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESolicitudCredito oESolicitudCredito= null;
		
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOLICITUDXCARTAFIANZA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oESolicitudCredito=new ESolicitudCredito();
					oESolicitudCredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oESolicitudCredito.setNroRevision(oResultSet.getInt("NREVIS"));
					oESolicitudCredito.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oESolicitudCredito.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVNOMCLI")));
					//oESolicitudCredito.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oESolicitudCredito.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					oESolicitudCredito.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					//oESolicitudCredito.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPRODU")));
					oESolicitudCredito.setDescripcionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPPRODU")));
					oESolicitudCredito.setCodigoTipoDocumentoCartaFianza(oResultSet.getInt("TIPDCM"));
					oESolicitudCredito.setDescripcionTipoDocumentoCartaFianza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPDOC")));
					oESolicitudCredito.setCodigoClaseDocumentoCartaFianza(oResultSet.getInt("CLASE"));
					oESolicitudCredito.setDescripcionClaseDocumentoCartaFianza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCLASE")));
					oESolicitudCredito.setCodigoAfianzado(oResultSet.getInt("CODAFI"));
					oESolicitudCredito.setDescripcionAfianzado(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMAFI")));
					oESolicitudCredito.setCodigoBeneficiario(oResultSet.getInt("CODBEN"));
					oESolicitudCredito.setDescripcionBeneficiario(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBEN")));
					oESolicitudCredito.setCodigoBancoFianza(oResultSet.getInt("CODBCO"));
					oESolicitudCredito.setDescripcionBancoFianza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCBANCO")));
					oESolicitudCredito.setCodigoMonedaFianza(oResultSet.getInt("MONEAV"));
					oESolicitudCredito.setDescripcionMonedaFianza(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMONFIA")));
					oESolicitudCredito.setNumeroFianza(oResultSet.getInt("NUMERO"));
					oESolicitudCredito.setCuentaCargoFianza(oResultSet.getInt("CUECAR"));
					oESolicitudCredito.setMontoFianza(oResultSet.getDouble("MONTAV"));
					oESolicitudCredito.setFechaVigenciaFianza(oResultSet.getDate("FECVIG"));
					oESolicitudCredito.setFechaVencimientoFianza(oResultSet.getDate("FECVENC"));
					oESolicitudCredito.setCriterios(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER1"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER2"))+"\n"+
							UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER3"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER4"))+"\n"+
							UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER5"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSER6")));
					oESolicitudCredito.setTasaSolicitud(oResultSet.getInt("TASSOL"));
					oESolicitudCredito.setPlazoSolicitud(oResultSet.getInt("PLZSOL"));

				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oESolicitudCredito;
	}
	
	public List<ESolicitudCredito> listarSolicitudCredito(int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESolicitudCredito oESolicitudCredito = null;
		List<ESolicitudCredito> lstSolCreSocio = null;	
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLCREDITO_SOCIO, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstSolCreSocio = new ArrayList<ESolicitudCredito>();
				
				while (oResultSet.next()) {
					oESolicitudCredito = new ESolicitudCredito();
					oESolicitudCredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oESolicitudCredito.setCodigoTipoProducto(oResultSet.getInt("TPRODU"));
					oESolicitudCredito.setDescripcionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPROD"))); 
					oESolicitudCredito.setFechaRevision(oResultSet.getDate("FECREV"));
					//oESolicitudCredito.setHoraRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREVF")));
					//oESolicitudCredito.setUsuarioRevision(oResultSet.getString("USREVF"));
					oESolicitudCredito.setHoraRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oESolicitudCredito.setUsuarioRevision(oResultSet.getString("USUREV"));	
					oESolicitudCredito.setMontoSolicitud(oResultSet.getDouble("MTOSOL"));
					lstSolCreSocio.add(oESolicitudCredito);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstSolCreSocio;
	}
	
	public List<EObservacionNegocios> listarObservacionNegociosDetalle(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EObservacionNegocios oEObservacionNegocios = null;
		List<EObservacionNegocios> lstObservacionNegocios = null;			
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_EVALUACIONNEGOCIOS_MAESTRA, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstObservacionNegocios = new ArrayList<EObservacionNegocios>();
				while (oResultSet.next()) {
					oEObservacionNegocios = new EObservacionNegocios();
					oEObservacionNegocios.setSecuencia(oResultSet.getInt("SENEVA"));
					oEObservacionNegocios.setObservacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSNE1"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSNE2"))+"\n"+
							UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSNE3"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSNE4"))+"\n"+
							UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSNE5"))+"\n"+UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSNE6")));
					oEObservacionNegocios.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USNEVA")));
					oEObservacionNegocios.setFechaEvaluacion(oResultSet.getDate("FECEVA"));
					oEObservacionNegocios.setHoraEvaluacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HONEVA")));
					lstObservacionNegocios.add(oEObservacionNegocios);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstObservacionNegocios;
	}
	
	public List<ESuscripcion> listarSolicitudSuscripcion(long numeroSolicitud, int codigoCliente, int codigoTipoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESuscripcion oESuscripcion = null;
		List<ESuscripcion> lstSuscripcion = null;	
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(codigoTipoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOLICITUDSUSCRIPCION, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstSuscripcion = new ArrayList<ESuscripcion>();
				while (oResultSet.next()) {
					oESuscripcion = new ESuscripcion();
					oESuscripcion.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oESuscripcion.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oESuscripcion.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oESuscripcion.setCodigoOrden(oResultSet.getInt("CODSUS"));
					oESuscripcion.setNumeroSuscripcion(oResultSet.getInt("NUMSUS"));
					oESuscripcion.setNombreLargo(oResultSet.getString("NOMBSUS"));
					lstSuscripcion.add(oESuscripcion);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstSuscripcion;
	}
	
	public List<ESuscripcion> listarClienteSuscripcion(String numeroDocumento) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ESuscripcion oESuscripcion = null;
		List<ESuscripcion> lstSuscripcion = null;	
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroDocumento);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CLIENTESUSCRIPCION, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstSuscripcion = new ArrayList<ESuscripcion>();
				while (oResultSet.next()) {
					oESuscripcion = new ESuscripcion();
					oESuscripcion.setNumeroDocumento(oResultSet.getString("DOCUME"));
					oESuscripcion.setCodigoOrden(oResultSet.getInt("CODSUS"));
					oESuscripcion.setNumeroSuscripcion(oResultSet.getInt("NUMSUS"));
					oESuscripcion.setNombreLargo(oResultSet.getString("NOMBSUS"));
					lstSuscripcion.add(oESuscripcion);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstSuscripcion;
	}
	
	public ECredito buscarCredito(long numeroSolicitud) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ECredito oECredito= null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CREDITO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oECredito=new ECredito();
					oECredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oECredito.setAbreviacionMoneda(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oECredito.setTotalDesembolso(oResultSet.getDouble("TOTDES"));
					//oECredito.setUltimoDesembolso(oResultSet.getDate("ULTDES"));
					oECredito.setFechaCancelacion(oResultSet.getDate("FECCAN"));
					oECredito.setDescripcionTipoCredito(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTCR")));
					/*
					oESolicitudCredito.setCodigoMonedaSolicitud(oResultSet.getInt("MONSOL"));
					oESolicitudCredito.setDescripcionMonedaSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCMONSOL")));
					oESolicitudCredito.setFechaDesembolso(oResultSet.getDate("FECDES"));
					
					oESolicitudCredito.setCodigoProducto(oResultSet.getInt("TPRODU"));
					oESolicitudCredito.setAbreviacionProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("APRODU")));
					oESolicitudCredito.setDescripcionProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("DPRODU")));
					
					oESolicitudCredito.setCodigoCliente(oResultSet.getInt("CODCLI"));
					*/
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECredito;
	}
	
	public EEvaluacionSolicitudCreditoLegal buscarEvaluacionSolicitudCreditoLegal(long numeroSolicitud, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegal= null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_EVALUACIONSOLICITUDCREDITOLEGAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEEvaluacionSolicitudCreditoLegal=new EEvaluacionSolicitudCreditoLegal();
					oEEvaluacionSolicitudCreditoLegal.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("STALEG")));
					oEEvaluacionSolicitudCreditoLegal.setDescripcionEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSTALEG")));
					
					oEEvaluacionSolicitudCreditoLegal.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEEvaluacionSolicitudCreditoLegal.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEEvaluacionSolicitudCreditoLegal.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEEvaluacionSolicitudCreditoLegal.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEEvaluacionSolicitudCreditoLegal.setRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")));
					
					oEEvaluacionSolicitudCreditoLegal.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEEvaluacionSolicitudCreditoLegal.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCON")));
					oEEvaluacionSolicitudCreditoLegal.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCCON")));
					oEEvaluacionSolicitudCreditoLegal.setNombreLargoConyuge(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCON")));
					
					oEEvaluacionSolicitudCreditoLegal.setCodigoUbigeoReal(oResultSet.getInt("CCIUDA"));
					oEEvaluacionSolicitudCreditoLegal.setDireccionReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oEEvaluacionSolicitudCreditoLegal.setCodigoUbigeoContractual(oResultSet.getInt("CCIUDP"));
					oEEvaluacionSolicitudCreditoLegal.setDireccionContractual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoPersonaJuridica(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPJ")));
					oEEvaluacionSolicitudCreditoLegal.setMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP"));
					oEEvaluacionSolicitudCreditoLegal.setMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC"));
					oEEvaluacionSolicitudCreditoLegal.setCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")));
					oEEvaluacionSolicitudCreditoLegal.setCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")));
					//oEEvaluacionSolicitudCreditoLegal.setCodigoTipoSuscripcionPago(oResultSet.getInt("TIPSPG"));
					oEEvaluacionSolicitudCreditoLegal.setNumeroSuscripcionPago(oResultSet.getInt("SUSPAG"));
					oEEvaluacionSolicitudCreditoLegal.setNumeroAcciones(oResultSet.getInt("NROACC"));
					oEEvaluacionSolicitudCreditoLegal.setIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")));
					oEEvaluacionSolicitudCreditoLegal.setIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")));
					oEEvaluacionSolicitudCreditoLegal.setDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")));
					oEEvaluacionSolicitudCreditoLegal.setObservacionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT"))+"\n"+
																				  UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2AVT"))+"\n"+
																				  UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3AVT")));
					oEEvaluacionSolicitudCreditoLegal.setObservacionGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA"))+"\n"+
																			   UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2GRA"))+"\n"+
																			   UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3GRA")));
					oEEvaluacionSolicitudCreditoLegal.setObservacionConstanciaSocial(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT2PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT3PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT4PJ"))+"\n"+
																					 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT5PJ")));
					oEEvaluacionSolicitudCreditoLegal.setObservacionSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL01"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL02"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL03"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL04"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL05"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL06"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL07"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL08"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL09"))+"\n"+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL10")));
					
					oEEvaluacionSolicitudCreditoLegal.setUsuarioEvaluacionLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMUSUEVALG")));
					oEEvaluacionSolicitudCreditoLegal.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEEvaluacionSolicitudCreditoLegal.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEEvaluacionSolicitudCreditoLegal.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEEvaluacionSolicitudCreditoLegal.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEEvaluacionSolicitudCreditoLegal.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEEvaluacionSolicitudCreditoLegal.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEEvaluacionSolicitudCreditoLegal;
	}
	
	public EInformeLegalAdicional buscarInformeLegalAdicional(long numeroSolicitud, int codigoCliente, int codigoTipoCliente){
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EInformeLegalAdicional oEInformeLegalAdicional= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(codigoTipoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_INFORMELEGALADICIONAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEInformeLegalAdicional=new EInformeLegalAdicional();
					oEInformeLegalAdicional.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEInformeLegalAdicional.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEInformeLegalAdicional.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEInformeLegalAdicional.setCodigoEstadoCivil(oResultSet.getString("CODCIV"));
					oEInformeLegalAdicional.setNumeroPartida(oResultSet.getString("NUMPART"));
					oEInformeLegalAdicional.setOficinaRegistral(oResultSet.getString("OFIREG"));
					oEInformeLegalAdicional.setCodigoTipoDuracionPartida(oResultSet.getInt("CODTPART"));
					oEInformeLegalAdicional.setRegistroPartida(oResultSet.getString("REGPART"));
					oEInformeLegalAdicional.setDescripcionConstitucion(oResultSet.getString("DESCCONS"));
					oEInformeLegalAdicional.setFechaConstitucion(oResultSet.getDate("FECCONS"));
					oEInformeLegalAdicional.setCodigoNotario(oResultSet.getInt("CODNOT"));
					oEInformeLegalAdicional.setDescripcionNotario(oResultSet.getString("DESCNOT"));
					oEInformeLegalAdicional.setNumeroAcciones(oResultSet.getInt("NUMACC"));
					oEInformeLegalAdicional.setCodigoTipoValorSuscripcion(oResultSet.getInt("CODTVAL"));
					oEInformeLegalAdicional.setMontoValorNominal(oResultSet.getDouble("MTOVALN"));
					oEInformeLegalAdicional.setEstatuto(oResultSet.getString("ESTATUTO"));
					oEInformeLegalAdicional.setDescripcionPatrimonio(oResultSet.getString("DESCPAT"));
					oEInformeLegalAdicional.setCodigoTipoNumeracionEstatuto(oResultSet.getInt("CODTNETT"));
					oEInformeLegalAdicional.setNumeracionEstatuto(oResultSet.getInt("NUMETT"));
					oEInformeLegalAdicional.setAsiento(oResultSet.getString("ASIENTO"));
					oEInformeLegalAdicional.setFechaPeriodoInicio(oResultSet.getDate("FECPINI"));
					oEInformeLegalAdicional.setFechaPeriodoVencimiento(oResultSet.getDate("FECPVEN"));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEInformeLegalAdicional;
	}
	
	public EDeudorAdicional buscarDeudorAdicional(int codigoDeudor) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EDeudorAdicional oEDeudorAdicional= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoDeudor);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_DEUDORADICIONAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEDeudorAdicional=new EDeudorAdicional();
					oEDeudorAdicional.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEDeudorAdicional.setMontoLimite(oResultSet.getDouble("LIMITE"));
					oEDeudorAdicional.setMontoAdicional(oResultSet.getDouble("ADICIO"));
					oEDeudorAdicional.setFechaVencimiento(oResultSet.getDate("FECVTA"));
					oEDeudorAdicional.setCodigoTipoPago(oResultSet.getInt("TPOPAG"));
					oEDeudorAdicional.setCodigoFormaPago(oResultSet.getInt("FORPAG"));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEDeudorAdicional;
	}
	
	public EDeudorEstado buscarDeudorEstado(int codigoDeudor) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EDeudorEstado oEDeudorEstado= null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoDeudor);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_DEUDORESTADO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEDeudorEstado=new EDeudorEstado();
					oEDeudorEstado.setCodigoCliente(oResultSet.getInt("CODBLQ"));
					oEDeudorEstado.setCodigoEstadoUltimo(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODEST")));
					oEDeudorEstado.setDescripcionEstadoUltimo(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCEST")));
					oEDeudorEstado.setUsuarioUltimo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMUSU")));
					oEDeudorEstado.setFechaRegistroUltimo(oResultSet.getDate("FECREG"));
					oEDeudorEstado.setHoraRegistroUltimo(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEDeudorEstado.setObservacionUltimo(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSERVACION")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEDeudorEstado;
	}
	
	public List<EObservacionLegal> buscarObservacionDetalle(long numeroSolicitud, int ubicacion, int secuencia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EObservacionLegal oEObservacionLegal = null;
		List<EObservacionLegal> lstObservacionLegal = null;			
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(ubicacion);
			lstParametrosEntrada.add(secuencia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_EVALUACIONXNIVEL_DETALLE, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				lstObservacionLegal = new ArrayList<EObservacionLegal>();
				while (oResultSet.next()) {
					oEObservacionLegal = new EObservacionLegal();
					oEObservacionLegal.setDescripcionMensaje(UFuncionesGenerales.revisaCadena(oResultSet.getString("DETEVA")));			
					lstObservacionLegal.add(oEObservacionLegal);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstObservacionLegal;
	}
	
	public EObservacionNegocios buscarObservacionNegocios(long numeroSolicitud, int secuencia) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EObservacionNegocios oEObservacionNegocios = null;		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(secuencia);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_EVALUACIONNEGOCIOS_MAESTRA, lstParametrosEntrada, null);
			
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEObservacionNegocios=new EObservacionNegocios();
					oEObservacionNegocios.setObservacion(oResultSet.getString("OBSNE1")+"\n"+oResultSet.getString("OBSNE2")+"\n"+
														oResultSet.getString("OBSNE3")+"\n"+oResultSet.getString("OBSNE4")+"\n"+
														oResultSet.getString("OBSNE5")+"\n"+oResultSet.getString("OBSNE6"));
				}							
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEObservacionNegocios;
	}
}
