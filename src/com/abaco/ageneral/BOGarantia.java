package com.abaco.ageneral;
import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.ageneral.ESolicitudCredito;

public class BOGarantia {

	CGarantia oCGarantia= new CGarantia();
	
	public EMensaje agregarGarantiaPendienteRegistro(EGarantiaSolicitud eGarantiaSolicitud, EGarantia eGarantia){
		EMensaje resultado=oCGarantia.agregarGarantiaPendienteRegistro(eGarantiaSolicitud,eGarantia);
		return resultado;
	}
	public EMensaje agregarGarantiaMantenimiento(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.agregarGarantiaMantenimiento(eGarantia);
		return resultado;
	}
	
	public EMensaje agregarGarantiaDetalle(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.agregarGarantiaDetalle(eGarantia);
		return resultado;
	}
	
	public EMensaje registrarGarantiaSolicitudAnexoF7325(){
		EMensaje resultado=oCGarantia.registrarGarantiaSolicitudAnexoF7325();
		return resultado;
	}
	
	public EMensaje registrarTipoGarantia(EGeneral tipoGarantia){
		EMensaje resultado=oCGarantia.registrarTipoGarantia(tipoGarantia);
		return resultado;
	}
	
	public EMensaje registrarPoliza(EPoliza ePoliza) {
		EMensaje resultado=oCGarantia.registrarPoliza(ePoliza);
		return resultado;
	}
	
	public EMensaje modificarTipoGarantia(EGeneral tipoGarantia){
		EMensaje resultado=oCGarantia.modificarTipoGarantia(tipoGarantia);
		return resultado;
	}
	public EMensaje modificarGarantiaPoliza(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarGarantiaPoliza(eGarantia);
		return resultado;
	}
	public EMensaje modificarPoliza(EPoliza ePoliza) {
		EMensaje resultado=oCGarantia.modificarPoliza(ePoliza) ;
		return resultado;
	}
	public EMensaje modificarTipoIngresoPorcentaje(EGarantiaSolicitud eGarantiaSolicitud){
		EMensaje resultado=oCGarantia.modificarTipoIngresoPorcentaje(eGarantiaSolicitud) ;
		return resultado;
	}
	public EMensaje agregarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje resultado=oCGarantia.agregarGarantiaTramite(eGarantiaTramite);
		return resultado;
	}
	public EMensaje agregarModificarGarantiaTramite(EGarantiaTramite eGarantiaTramite,EGarantiaTramite eGarantiaTramiteAsiento) {
		EMensaje resultado=oCGarantia.agregarModificarGarantiaTramite(eGarantiaTramite,eGarantiaTramiteAsiento);
		return resultado;
	}
	public EMensaje agregarAsientoGarantiaTramite(EGarantiaTramite eGarantiaTramiteAsiento){
		EMensaje resultado=oCGarantia.agregarAsientoGarantiaTramite(eGarantiaTramiteAsiento);
		return resultado;
	}
	public EMensaje modificarGarantiaMantenimiento(EGarantia eGarantia ){
		EMensaje resultado=oCGarantia.modificarGarantiaMantenimiento(eGarantia);
		return resultado;
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		EMensaje resultado=oCGarantia.agregarDocumentoGarantia(eGarantia,lstDocumentoCarga);
		return resultado;
	}
	
	
	public EMensaje modificarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud){
		EMensaje resultado=oCGarantia.modificarGarantiaSolicitud(eGarantiaSolicitud);
		return resultado;
	}
	
	public EMensaje modificarGarantiaSolicitudSIAF(EGarantiaSolicitud eGarantiaSolicitud,EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud){
		EMensaje resultado=oCGarantia.modificarGarantiaSolicitudSIAF(eGarantiaSolicitud,eGarantiaDetalleSolicitud);
		return resultado;
	}
	
	public EMensaje actualizarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud,
			EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud,EGarantiaSolicitud oEGarantiaAsociadaSolicitud){
		EMensaje resultado=oCGarantia.actualizarGarantiaSolicitud(eGarantiaSolicitud,eGarantiaDetalleSolicitud,oEGarantiaAsociadaSolicitud);
		return resultado;
	}
	
	public EMensaje modificarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		EMensaje resultado=oCGarantia.modificarGarantiaTramite(eGarantiaTramite);
		return resultado;
	}
	public List<EGarantiaSolicitud> listarGarantiaPorLiberar(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaPorLiberar(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaLiberada(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaLiberada(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaConCreditoVigente(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaConCreditoVigente(codigo, descripcion);
		return lista;
	}
	public List<EGarantia> listarGarantiaVinculada(EGarantia eGarantia){					
		List<EGarantia> lista=oCGarantia.listarGarantiaVinculada(eGarantia);
		return lista;
	}
	public List<EGarantia> listarGarantiaDetalle(EGarantia eGarantia ) {					
		List<EGarantia> lista=oCGarantia.listarGarantiaDetalle(eGarantia);
		return lista;
	}
	public List<EAsignacionContratoGarantia> listarCreditosAsociadosGarantia(long codigoGarantia) {					
		List<EAsignacionContratoGarantia> lista=oCGarantia.listarCreditosAsociadosGarantia(codigoGarantia);
		return lista;
	}
	public EMensaje eliminarPoliza(EPoliza ePoliza) {
		EMensaje resultado=oCGarantia.eliminarPoliza(ePoliza);
		return resultado;
	}
	public List<EAsignacionContratoGarantia> listarClientesGarantizados(EGarantia eGarantia ) {					
		List<EAsignacionContratoGarantia> lista=oCGarantia.listarClientesGarantizados(eGarantia);
		return lista;
	}
	public List<EGarantiaTramite> listarHistoricoTramiteGarantia(long codigoGarantia){					
		List<EGarantiaTramite> lista=oCGarantia.listarHistoricoTramiteGarantia(codigoGarantia);
		return lista;
	}
	public List<EGarantiaTramite> listarAsientosTramiteGarantia(long codigoGarantia){					
		List<EGarantiaTramite> lista=oCGarantia.listarAsientosTramiteGarantia(codigoGarantia);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaPorConstituir(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaPorConstituir(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarHistoricoGarantiaSolicitud(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarHistoricoGarantiaSolicitud(codigo, descripcion);
		return lista;
	}
	public EMensaje agregaGarantiaPendienteRegistro(EGarantiaSolicitud eOGarantiaSolicitud){					
		EMensaje resultado=oCGarantia.agregaGarantiaPendienteRegistro(eOGarantiaSolicitud);
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarSolicitudAsociadaGarantia(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarSolicitudAsociadaGarantia(codigo, descripcion);
		return lista;
	}
	public List<EGarantia> listarGarantiaVigente(int codigo, String descripcion){					
		List<EGarantia> lista=oCGarantia.listarGarantiaVigente(codigo, descripcion);
		return lista;
	}
	public List<EGarantia> listarGarantia(int codigo, String descripcion){					
		List<EGarantia> lista=oCGarantia.listarGarantia(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaSolicitud(long numeroSolicitud){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaSolicitud(numeroSolicitud);
		return lista;
	}
	public List<EPoliza> listarPolizaSeguro(int codigo, String descripcion,String descripcion2){					
		List<EPoliza> lista=oCGarantia.listarPolizaSeguro(codigo, descripcion,descripcion2);
		return lista;
	}
	public List<EPoliza> buscarPolizaSeguro(EPoliza ePoliza){					
		List<EPoliza> lista=oCGarantia.buscarPolizaSeguro(ePoliza);
		return lista;
	}
	public EPoliza buscarPoliza(EPoliza ePoliza){					
		EPoliza lista=oCGarantia.buscarPoliza(ePoliza);
		return lista;
	}
	public  EGarantia buscarUltimaGarantiaGenerada(){					
		EGarantia lista=oCGarantia.buscarUltimaGarantiaGenerada();
		return lista;
	}
	public  EGarantiaSolicitud buscarSolicitudxGarantia(long codigoGarantia){					
		EGarantiaSolicitud lista=oCGarantia.buscarSolicitudxGarantia( codigoGarantia);
		return lista;
	}
	public  EGarantia buscarAnexoGarantia(long codigoGarantia){					
		EGarantia lista=oCGarantia.buscarAnexoGarantia(codigoGarantia);
		return lista;
	}
	public  EGarantiaTramite buscarUltimoAsientoGarantiaTramite(long codigoGarantia){					
		EGarantiaTramite lista=oCGarantia.buscarUltimoAsientoGarantiaTramite(codigoGarantia);
		return lista;
	}
	public List<ESaldoServicio> obtenerSaldosServiciosCliente(int codigoCliente){
		List<ESaldoServicio> lista=oCGarantia.obtenerSaldosServiciosCliente(codigoCliente);
		return lista;
	}
	public List<EGarantiaDocumentoSolicitado> listarDocumentoSolicitado(long numeroSolicitud){
		List<EGarantiaDocumentoSolicitado> lista=oCGarantia.listarDocumentoSolicitado(numeroSolicitud);
		return lista;
	}
	public List<EGarantiaCreditoRelacionado> listarCreditoRelacionado(int codigo){
		List<EGarantiaCreditoRelacionado> lista=oCGarantia.listarCreditoRelacionado(codigo);
		return lista;
	}
	public List<EGarantiaCreditoRelacionado> listarCreditoVigenteRelacionado(long codigo){
		List<EGarantiaCreditoRelacionado> lista=oCGarantia.listarCreditoVigenteRelacionado(codigo);
		return lista;
	}
	public List<EGarantiaCreditoRelacionado> listarCreditoCanceladoRelacionado(long codigo){
		List<EGarantiaCreditoRelacionado> lista=oCGarantia.listarCreditoCanceladoRelacionado(codigo);
		return lista;
	}
	public EGarantia buscarGarantia(long codigoGarantia){
		EGarantia resultado=oCGarantia.buscarGarantia(codigoGarantia);
		return resultado;
	}
	public ETipoCambio buscarTipoCambioDiario(){
		ETipoCambio resultado=oCGarantia.buscarTipoCambioDiario();
		return resultado;
	}
	public EGarantiaTramite buscarGarantiaTramite(long codigoGarantia){
		EGarantiaTramite resultado=oCGarantia.buscarGarantiaTramite(codigoGarantia);
		return resultado;
	}
	public EGarantia buscarGarantiaSolicitudCompleto(long numeroSolicitud,int secuenciaGarantia){
		EGarantia resultado=oCGarantia.buscarGarantiaSolicitudCompleto(numeroSolicitud,secuenciaGarantia);
		return resultado;
	}
	public EGarantiaSolicitud buscarGarantiaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		EGarantiaSolicitud resultado=oCGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia);
		return resultado;
	}
	public EGarantiaSolicitud buscarGarantiaAsociadaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		EGarantiaSolicitud resultado=oCGarantia.buscarGarantiaAsociadaSolicitud(numeroSolicitud, secuenciaGarantia);
		return resultado;
	}
	public EGarantiaDetalleSolicitud buscarGarantiaDetalleSolicitud(long numeroSolicitud, int secuenciaGarantia){					
		EGarantiaDetalleSolicitud resultado=oCGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia);
		return resultado;
	}
}
