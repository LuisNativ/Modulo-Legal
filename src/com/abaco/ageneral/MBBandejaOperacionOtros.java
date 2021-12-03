package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;






import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.UnsupportedAudioFileException;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.json.JSONObject;

import com.abaco.bo.BOGeneral;
import com.abaco.bo.BOUsuario;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UMensajeValidacion;
import com.abaco.negocio.util.UConstante.UTipoAutonomia;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mbbandejaoperacionotros")
@ViewScoped
public class MBBandejaOperacionOtros implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EOperacionSolicitud oEOperacionSolicitudData;
	
	private EGeneral oEUsuarioSelected;
	
	private BOOperacion oBOOperacion;
	private BOGeneral oBOGeneral;
	
	
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitud;
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitudPendiente;
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitudHistorico;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstEstado;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstUsuario;
	@Getter @Setter private List<EGeneral> lstUsuarioFiltro;
	@Getter @Setter private List<EGeneral> lstUsuarioEvaluacion;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoNivel;
	@Getter @Setter private int codigoEstado;
	@Getter @Setter private int codigoMotivo;
	@Getter @Setter private int codigoUsuarioEvaluacion;
	@Getter @Setter private String descripcionMensaje;
	@Getter @Setter private String descripcionUsuarioReasignar;
	@Getter @Setter private int codigoFechaInicio;
	@Getter @Setter private Date fechaInicioDesde;
	@Getter @Setter private Date fechaInicioHasta;
	@Getter @Setter private String nombrePersona;
	@Getter @Setter private String descripcionAsunto;
	@Getter @Setter private int indicadorTabSeleccion;
	
	@Getter @Setter private boolean deshabilitarFechaInicio;
	@Getter @Setter private String mensajeValidacion;
	
	/* Variables Internas */
	private long codigoSolicitudSeleccion;
	private String indicadorTipoReasignar;
	
	private BOCliente oBOCliente;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEOperacionSolicitudData = new EOperacionSolicitud();
		
		oEUsuarioSelected = new EGeneral();
		
		oBOOperacion = new BOOperacion();
		oBOGeneral = new BOGeneral();
		oBOCliente = new BOCliente();
		
		lstOperacionSolicitud = new ArrayList<EOperacionSolicitud>();
		lstOperacionSolicitudPendiente = new ArrayList<EOperacionSolicitud>();
		lstOperacionSolicitudHistorico = new ArrayList<EOperacionSolicitud>();
		lstNivel = new ArrayList<EGeneral>();
		lstEstado = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstUsuario = new ArrayList<EGeneral>();
		lstUsuarioFiltro = new ArrayList<EGeneral>();
		lstUsuarioEvaluacion = new ArrayList<EGeneral>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		
		/*
		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.OPERACION_SESION) != null) {
			EOperacionSolicitud oEOperacionSolicitud = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.OPERACION_SESION);
			oBOOperacion.liberarSolicitudSesion(oEOperacionSolicitud.getCodigoSolicitud());
			UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		}
		*/
		
		inicializar();
		/*
		verificarSolicitudSesion();
		obtenerCodigoFechaInicio(0);
		listarNivel();
		listarEstado();
		listarMotivo();
		listarUsuario();
		listarUsuarioEvaluacion();
		*/
		verificarSolicitudSiaf();
		listarSolicitud();
	}
	
	public boolean validar() {
		boolean ind=true;
		mensajeValidacion = "";
		if(oEUsuario.getCodigoArea()==0){
			mensajeValidacion = UMensajeValidacion.MSJ_5;
			ind = false;
		}
        return ind;
	}
	
	public void agregar(int ind) {
		String ruta = "";
		if(validar()){
			if(ind == 1){
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
				ruta = "RegistroEvaluacionPoder.xhtml";
			}
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}else {
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void modificar(EOperacionSolicitud oEOperacionSolicitudItem) {
		String ruta = "";
		if (oEOperacionSolicitudItem != null) {
			oEOperacionSolicitudItem.setUsuarioRegistro(oEUsuario);
			
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitudItem);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudItem);
			
			if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.SOLICITUDCREDITO){
				ruta = "RegistroOperacionSolicitudCredito.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.PODERES){
				ruta = "RegistroEvaluacionPoder.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.LEVANTAMIENTO){
				ruta = "RegistroOperacionLevantamientoGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CONSTITUCION){
				ruta = "RegistroOperacionConstitucionGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CARTAFIANZA){
				ruta = "RegistroOperacionCreditoIndirecto.xhtml";
			}
			
			verificarSolicitudSiaf();
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			/*
			this.oEMensaje = new EMensaje();
			oEMensaje = oBOOperacion.redigirSolicitud(oEOperacionSolicitudSelected);
			
			if(oEMensaje.getIdMensaje()<0){
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}else{
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitudSelected);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudSelected);
				
				if(oEOperacionSolicitudSelected.getCodigoEstado() == UEstado.PENDIENTEDEREGISTROMANUAL){
					ruta = "ir_RegistroOperacionManual";
					//ruta = "RegistroOperacionManual";
				}else{
					ruta = "ir_RegistroOperacion";
					//ruta = "RegistroOperacion";
				}
			}
			*/
		}
	}
	
	public void visualizar(EOperacionSolicitud oEOperacionSolicitudItem) {
		String ruta = "";
		if (oEOperacionSolicitudItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudItem);
			
			if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.SOLICITUDCREDITO){
				ruta = "RegistroOperacionSolicitudCredito.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.PODERES){
				//ruta = "RegistroEvaluacionPoder.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.LEVANTAMIENTO){
				ruta = "ConsultaOperacionLevantamientoGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CONSTITUCION){
				ruta = "ConsultaOperacionConstitucionGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CARTAFIANZA){
				ruta = "ConsultaOperacionCreditoIndirecto.xhtml";
			}
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void verificarSolicitudSiaf() {
		oBOOperacion.verificarSolicitudSiaf();
	}
	
	public void listarSolicitud() {
		EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
		oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
		oEUsuario.setCodigoAutonomia(oBOCliente.buscarPermisoUsuario(oEUsuario.getNombreUsuario()).getCodigoAutonomia());
		/*
		oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
		oEOperacionSolicitud.setNombrePersona(nombrePersona);
		oEOperacionSolicitud.setDescripcionAsunto(descripcionAsunto);
		oEOperacionSolicitud.setCodigoNivel(codigoNivel);
		oEOperacionSolicitud.setCodigoEstado(codigoEstado);
		oEOperacionSolicitud.setFechaInicioDesde(fechaInicioDesde);
		oEOperacionSolicitud.setFechaInicioHasta(fechaInicioHasta);
		oEOperacionSolicitud.setCodigoUsuarioEvaluacion(codigoUsuarioEvaluacion);
		*/
		oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
		lstOperacionSolicitud = oBOOperacion.listarSolicitud(oEOperacionSolicitud, 3);
		
		if (lstOperacionSolicitud != null){
			lstOperacionSolicitudPendiente = new ArrayList<EOperacionSolicitud>();
	        for (EOperacionSolicitud obj: lstOperacionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.PENDIENTEDEEVALUACION ||
	            		obj.getCodigoEstado() == UEstado.OBSERVADO) {
	            	lstOperacionSolicitudPendiente.add(obj);
	            }
	            if (obj.getCodigoEstado() == UEstado.ENEVALUACION && obj.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.SOLICITADO) {
	            	lstOperacionSolicitudPendiente.add(obj);
	            }
	            
	            if(oEUsuario.getCodigoAutonomia() == UTipoAutonomia.JEFE){
	            	if (obj.getCodigoEstado() == UEstado.CERRADO  && obj.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.AUTORIZADO) {
		            	lstOperacionSolicitudPendiente.add(obj);
		            }
	            }
	            
	        }
	        
	        lstOperacionSolicitudHistorico = new ArrayList<EOperacionSolicitud>();
	        for (EOperacionSolicitud obj: lstOperacionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.CERRADO || 
	            	obj.getCodigoEstado() == UEstado.DESAPROBADO ||
	            	obj.getCodigoEstado() == UEstado.APROBADO) {
	            	lstOperacionSolicitudHistorico.add(obj);
	            }
	            if (obj.getCodigoEstado() == UEstado.ENEVALUACION && obj.getCodigoAutorizaJefe() != UEstadoAutorizacionJefe.SOLICITADO) {
	            	lstOperacionSolicitudHistorico.add(obj);
	            }
	            if(oEUsuario.getCodigoAutonomia() == UTipoAutonomia.JEFE){
	            	if (obj.getCodigoEstado() == UEstado.CERRADO && obj.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.AUTORIZADO) {
	            		lstOperacionSolicitudHistorico.remove(obj);
		            }
	            }
	        }
		}
	}
	
	public void inicializar() {
		indicadorTabSeleccion = 0;
		codigoFechaInicio = 2;
		nombrePersona= "";
		descripcionAsunto="";
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
}
