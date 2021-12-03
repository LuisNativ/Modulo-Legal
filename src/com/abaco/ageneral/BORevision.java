package com.abaco.ageneral;
import java.util.Date;
import java.util.List;

import com.abaco.ageneral.CRevision;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ERevisionSolicitud;

public class BORevision {

	CRevision oCRevision= new CRevision();
	
	public EMensaje redigirSolicitud(ERevisionSolicitud eRevisionSolicitud){
		EMensaje mensaje = oCRevision.redigirSolicitud(eRevisionSolicitud);
		return mensaje;
	}
	public EMensaje agregarSolicitud(ERevisionSolicitud eRevisionSolicitud, ERevisionSesion eRevisionSesion){
		EMensaje mensaje = oCRevision.agregarSolicitud(eRevisionSolicitud, eRevisionSesion);
		return mensaje;
	}
	public EMensaje agregarSolicitudManual(ERevisionSolicitud eRevisionSolicitud){
		EMensaje mensaje = oCRevision.agregarSolicitudManual(eRevisionSolicitud);
		return mensaje;
	}
	public EMensaje modificarSolicitud(ERevisionSolicitud eRevisionSolicitud, ERevisionSesion eRevisionSesion){
		EMensaje mensaje = oCRevision.modificarSolicitud(eRevisionSolicitud, eRevisionSesion);
		return mensaje;
	}
	public EMensaje modificarSolicitudManual(ERevisionSolicitud eRevisionSolicitud){
		EMensaje mensaje = oCRevision.modificarSolicitudManual(eRevisionSolicitud);
		return mensaje;
	}
	public EMensaje modificarSolicitudDigitalizacion(ERevisionSolicitud eRevisionSolicitud, ERevisionSesion eRevisionSesion){
		EMensaje mensaje = oCRevision.modificarSolicitudDigitalizacion(eRevisionSolicitud, eRevisionSesion);
		return mensaje;
	}
	public EMensaje modificarEstadoCancelar(ERevisionSolicitud eRevisionSolicitud){
		EMensaje mensaje = oCRevision.modificarEstadoCancelar(eRevisionSolicitud);
		return mensaje;
	}
	public EMensaje modificarEstadoEliminar(ERevisionSolicitud eRevisionSolicitud){
		EMensaje mensaje = oCRevision.modificarEstadoEliminar(eRevisionSolicitud);
		return mensaje;
	}
	public EMensaje modificarEnvio(ERevisionSolicitud eRevisionSolicitud){
		EMensaje mensaje = oCRevision.modificarEnvio(eRevisionSolicitud);
		return mensaje;
	}
	public EMensaje agregarSesion(ERevisionSesion eRevisionSesion){
		EMensaje mensaje = oCRevision.agregarSesion(eRevisionSesion);
		return mensaje;
	}
	public EMensaje modificarSesion(ERevisionSesion eRevisionSesion){
		EMensaje mensaje = oCRevision.modificarSesion(eRevisionSesion);
		return mensaje;
	}
	public EMensaje liberarSolicitudSesion(long codigoSolicitud){
		EMensaje mensaje = oCRevision.liberarSolicitudSesion(codigoSolicitud);
		return mensaje;
	}
	public EMensaje verificarSolicitudSesion(){
		EMensaje mensaje = oCRevision.verificarSolicitudSesion();
		return mensaje;
	}
	public EMensaje agregarReasignacion(ERevisionSolicitud eRevisionSolicitud, int codigoArea, int codigo, String descripcion, String indTipo){
		EMensaje mensaje = oCRevision.agregarReasignacion(eRevisionSolicitud, codigoArea, codigo, descripcion, indTipo);
		return mensaje;
	}
	public EOpcion buscarOpcionPorSolicitud(long codigoSolicitud, EUsuario eUsuario){
		EOpcion resultado = oCRevision.buscarOpcionPorSolicitud(codigoSolicitud, eUsuario);
		return resultado;
	}
	public List<ERevisionSolicitud> listarSolicitud(ERevisionSolicitud eRevisionSolicitud, int indicadorConsulta, int indicadorTipoBusqueda){					
		List<ERevisionSolicitud> resultado=oCRevision.listarSolicitud(eRevisionSolicitud, indicadorConsulta, indicadorTipoBusqueda);
		return resultado;
	}
	public List<ERevisionTiempo> listarTiempoResumen(long codigo){					
		List<ERevisionTiempo> resultado=oCRevision.listarTiempoResumen(codigo);
		return resultado;
	}
	public List<ERevisionSesion> listarTiempoDetalle(long codigoSolicitud, int codigoArea){					
		List<ERevisionSesion> resultado=oCRevision.listarTiempoDetalle(codigoSolicitud, codigoArea);
		return resultado;
	}
	public List<ERevisionMensaje> listarMensaje(long codigoSolicitud){
		List<ERevisionMensaje> lista=oCRevision.listarMensaje(codigoSolicitud);
		return lista;
	}
	public String buscarMensajeTemporal(long codigoSolicitud){
		String resultado=oCRevision.buscarMensajeTemporal(codigoSolicitud);
		return resultado;
	}
	public List<ERevisionDocumento> listarDocumento(long codigoSolicitud){
		List<ERevisionDocumento> lista=oCRevision.listarDocumento(codigoSolicitud);
		return lista;
	}
	public List<EDocumentoCarga> listarDocumentoTemporal(long codigoSolicitud){
		List<EDocumentoCarga> lista=oCRevision.listarDocumentoTemporal(codigoSolicitud);
		return lista;
	}
}