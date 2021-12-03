package com.abaco.ageneral;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.ERevisionSolicitud;

public class BOOperacion {

	COperacion oCOperacion= new COperacion();
	
	public EMensaje agregarDocumentoGarantia(EGarantia eOGarantia, EDocumentoCarga eDocumentoCarga){
		EMensaje mensaje = oCOperacion.agregarDocumentoGarantia(eOGarantia, eDocumentoCarga);
		return mensaje;
	}
	public EMensaje modificarDocumentoGarantia(EGarantia eOGarantia){
		EMensaje mensaje = oCOperacion.modificarDocumentoGarantia(eOGarantia);
		return mensaje;
	}
	public EMensaje modificarFirmaDocumentoGarantia(EOperacionDocumento eOperacionDocumento){
		EMensaje mensaje = oCOperacion.modificarFirmaDocumentoGarantia(eOperacionDocumento);
		return mensaje;
	}
	public EMensaje agregarSolicitud(EOperacionSolicitud eOperacionSolicitud, EOperacionSesion eOperacionSesion){
		EMensaje mensaje = oCOperacion.agregarSolicitud(eOperacionSolicitud, eOperacionSesion);
		return mensaje;
	}
	public EMensaje agregarEvaluacionSolicitudCredito(EOperacionSolicitud eOperacionSolicitud){
		EMensaje mensaje = oCOperacion.agregarEvaluacionSolicitudCredito(eOperacionSolicitud);
		return mensaje;
	}
	public EMensaje modificarEvaluacionSolicitudCredito(EOperacionSolicitud eOperacionSolicitud, EEvaluacionSolicitudCreditoLegal eEvaluacionSolicitudCreditoLegal){
		EMensaje mensaje = oCOperacion.modificarEvaluacionSolicitudCredito(eOperacionSolicitud, eEvaluacionSolicitudCreditoLegal);
		return mensaje;
	}
	public EMensaje agregarSesion(EOperacionSesion eOperacionSesion){
		EMensaje mensaje = oCOperacion.agregarSesion(eOperacionSesion);
		return mensaje;
	}
	public EMensaje modificarSesion(EOperacionSesion eOperacionSesion){
		EMensaje mensaje = oCOperacion.modificarSesion(eOperacionSesion);
		return mensaje;
	}
	public EMensaje liberarSolicitudSesion(long codigoSolicitud){
		EMensaje mensaje = oCOperacion.liberarSolicitudSesion(codigoSolicitud);
		return mensaje;
	}
	public EMensaje verificarSolicitudSesion(){
		EMensaje mensaje = oCOperacion.verificarSolicitudSesion();
		return mensaje;
	}
	public EMensaje verificarSolicitudSiaf(){
		EMensaje mensaje = oCOperacion.verificarSolicitudSiaf();
		return mensaje;
	}
	public List<EOperacionSolicitud> listarSolicitud(EOperacionSolicitud eOperacionSolicitud, int indicadorConsulta){					
		List<EOperacionSolicitud> resultado=oCOperacion.listarSolicitud(eOperacionSolicitud, indicadorConsulta);
		return resultado;
	}
	public List<EOperacionMensaje> listarMensaje(long codigoSolicitud){
		List<EOperacionMensaje> lista=oCOperacion.listarMensaje(codigoSolicitud);
		return lista;
	}
	public List<EOperacionDocumento> listarDocumento(long codigoSolicitud){
		List<EOperacionDocumento> lista=oCOperacion.listarDocumento(codigoSolicitud);
		return lista;
	}
	public List<EOperacionDocumento> listarDocumentoGarantia(EGarantia eOGarantia, int indicador){
		List<EOperacionDocumento> lista=oCOperacion.listarDocumentoGarantia(eOGarantia,indicador);
		return lista;
	}
	public List<EOperacionDocumentoRequerido> listarDocumentoRequerido(long codigoSolicitud){
		List<EOperacionDocumentoRequerido> lista=oCOperacion.listarDocumentoRequerido(codigoSolicitud);
		return lista;
	}
	public List<EDocumentoCarga> listarDocumentoTemporal(long codigoSolicitud){
		List<EDocumentoCarga> lista=oCOperacion.listarDocumentoTemporal(codigoSolicitud);
		return lista;
	}
	public List<EOperacionDocumentoRevision> listarDocumentoRevision(long codigoSolicitud){
		List<EOperacionDocumentoRevision> lista=oCOperacion.listarDocumentoRevision(codigoSolicitud);
		return lista;
	}
	public List<EEstado> listarEstadoPorSolicitud(long codigoSolicitud, EUsuario eUsuario){
		List<EEstado> resultado=oCOperacion.listarEstadoPorSolicitud(codigoSolicitud, eUsuario);
		return resultado;
	}
	public EOperacionSolicitud buscarSolicitud(long codigoSolicitud){
		EOperacionSolicitud resultado=oCOperacion.buscarSolicitud(codigoSolicitud);
		return resultado;
	}
	public String buscarMensajeTemporal(long codigoSolicitud){
		String resultado=oCOperacion.buscarMensajeTemporal(codigoSolicitud);
		return resultado;
	}
	public EOpcion buscarOpcionPorSolicitud(long codigoSolicitud, int codigoTipoEvaluacion, EUsuario eUsuario){
		EOpcion resultado = oCOperacion.buscarOpcionPorSolicitud(codigoSolicitud, codigoTipoEvaluacion, eUsuario);
		return resultado;
	}
	public EMensaje agregarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia){
		EMensaje mensaje = oCOperacion.agregarEvaluacionLevantamientoGarantia(eOperacionLevantamientoGarantia);
		return mensaje;
	}
	public EMensaje modificarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia, boolean indicadorLiberarGarantia){
		EMensaje mensaje = oCOperacion.modificarEvaluacionLevantamientoGarantia(eOperacionLevantamientoGarantia, indicadorLiberarGarantia);
		return mensaje;
	}
	public List<EGarantiaSolicitud> listarEvaluacionLevantamientoGarantia(int codigo, String descripcion, EUsuario eUsuario){					
		List<EGarantiaSolicitud> lista=oCOperacion.listarEvaluacionLevantamientoGarantia(codigo, descripcion, eUsuario);
		return lista;
	}
	public EMensaje agregarModificarEvaluacionCliente(EOperacionCliente eOperacionCliente){
		EMensaje mensaje = oCOperacion.agregarModificarEvaluacionCliente(eOperacionCliente);
		return mensaje;
	}
	public EMensaje modificarEvaluacionCliente(EOperacionCliente eOperacionCliente){
		EMensaje mensaje = oCOperacion.modificarEvaluacionCliente(eOperacionCliente);
		return mensaje;
	}
	public List<EOperacionCliente> listarEvaluacionCliente(int codigo, String descripcion, EUsuario eUsuario){
		List<EOperacionCliente> lista=oCOperacion.listarEvaluacionCliente(codigo, descripcion, eUsuario);
		return lista;
	}
	public EOperacionCliente buscarEvaluacionCliente(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		EOperacionCliente resultado=oCOperacion.buscarEvaluacionCliente(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return resultado;
	}
}