package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mbbandejaoperacionlegal")
@ViewScoped
public class MBBandejaOperacionLegal implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EOperacionSolicitud oEOperacionSolicitudData;
	
	private EGeneral oEUsuarioSelected;
	
	private BOOperacion oBOOperacion;
	private BOSolicitudCredito oBOSolicitudCredito;
	private BOGeneral oBOGeneral;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitud;
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitudPendiente;
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitudHistorico;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstEstado;
	@Getter @Setter private List<EGeneral> lstAutorizaJefe;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstUsuario;
	@Getter @Setter private List<EGeneral> lstUsuarioFiltro;
	@Getter @Setter private List<EGeneral> lstUsuarioEvaluacion;
	
	/* Variables Interfaz */
	@Getter @Setter private int numeroSolicitud;
	@Getter @Setter private int codigoNivel;
	@Getter @Setter private int codigoEstado;
	@Getter @Setter private int codigoAutorizaJefe;
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

	/* Variables Internas */
	private long codigoSolicitudSeleccion;
	private String indicadorTipoReasignar;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEOperacionSolicitudData = new EOperacionSolicitud();
		
		oEUsuarioSelected = new EGeneral();
		
		oBOOperacion = new BOOperacion();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oBOGeneral = new BOGeneral();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstOperacionSolicitud = new ArrayList<EOperacionSolicitud>();
		lstOperacionSolicitudPendiente = new ArrayList<EOperacionSolicitud>();
		lstOperacionSolicitudHistorico = new ArrayList<EOperacionSolicitud>();
		lstNivel = new ArrayList<EGeneral>();
		lstEstado = new ArrayList<EGeneral>();
		lstAutorizaJefe = new ArrayList<EGeneral>();
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
		verificarSolicitudCredito();
		verificarSolicitudSiaf();
		listarSolicitud();
		listarDesplegable();
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
				ruta = "RegistroOperacionEvaluacionPoder.xhtml";
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
	
	public void verificarSolicitudCredito() {
		List<EEvaluacionSolicitudCreditoLegal> lstEvaluacionSolicitudCreditoLegal = oBOSolicitudCredito.listarInformeLegalPorRegistrar();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		for (EEvaluacionSolicitudCreditoLegal obj: lstEvaluacionSolicitudCreditoLegal) {
			this.oEMensaje = new EMensaje();
			
			EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
			oEOperacionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONSOLICITUD, "", "", ""));
			oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
			oEOperacionSolicitud.setCodigoSolicitudCredito(obj.getNumeroSolicitud());
			oEOperacionSolicitud.setCodigoClientePersona(obj.getCodigoCliente());
			oEOperacionSolicitud.setCodigoTipoPersona(obj.getCodigoTipoPersona());
			oEOperacionSolicitud.setCodigoTipoClientePersona(obj.getCodigoTipoCliente());
			oEOperacionSolicitud.setCodigoTipoDocumentoPersona(obj.getCodigoTipoDocumento());
			oEOperacionSolicitud.setNumeroDocumentoPersona(obj.getNumeroDocumento());
			oEOperacionSolicitud.setNombrePersona(obj.getNombreCorto());
			
			oEOperacionSolicitud.setCodigoAreaEmisor(obj.getCodigoAreaRegistroLegal());
			oEOperacionSolicitud.setCodigoEmisor(obj.getCodigoUsuarioRegistroLegal());
			oEOperacionSolicitud.setDescripcionEmisor(obj.getDescripcionUsuarioRegistroLegal());
			
			oEOperacionSolicitud.setCodigoTipoEnvio(UTipoEnvio.PUBLICO);
			oEOperacionSolicitud.setCodigoAreaReceptor(UArea.LEGAL);
			oEOperacionSolicitud.setCodigoReceptor(0);
			oEOperacionSolicitud.setDescripcionReceptor("");
			
			oEOperacionSolicitud.setDescripcionAsunto("-");
			//Buscar ultimo observacion de negocios
			oEOperacionSolicitud.setDescripcionMensaje(oBOSolicitudCredito.buscarObservacionNegocios(obj.getNumeroSolicitud(), 1));
			oEOperacionSolicitud.setFechaOrigen(obj.getFechaRegistroLegal());
			oEOperacionSolicitud.setFechaInicio(obj.getFechaRegistroLegal());
			
			try {
				oEOperacionSolicitud.setHoraOrigen(formato.parse(UFuncionesGenerales.convertirCadenaATime(obj.getHoraRegistroLegal())));
				oEOperacionSolicitud.setHoraInicio(formato.parse(UFuncionesGenerales.convertirCadenaATime(obj.getHoraRegistroLegal())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			oEOperacionSolicitud.setFechaRegistro(new Date());
			oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBOOperacion.agregarEvaluacionSolicitudCredito(oEOperacionSolicitud);
		}
	}
	
	/*
	public void verificarSolicitudCredito() {
		List<ESolicitudCredito> lstSolicitudCredito = oBOSolicitudCredito.listarSolicitudCreditoPorRegistrar();
		
		for (ESolicitudCredito obj: lstSolicitudCredito) {
			this.oEMensaje = new EMensaje();
			
			EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
			oEOperacionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONSOLICITUD, "", "", ""));
			oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
			oEOperacionSolicitud.setCodigoSolicitudCredito(obj.getNumeroSolicitud());
			oEOperacionSolicitud.setCodigoClientePersona(obj.getCodigoCliente());
			oEOperacionSolicitud.setCodigoTipoPersona(obj.getCodigoTipoPersona());
			oEOperacionSolicitud.setCodigoTipoClientePersona(obj.getCodigoTipoCliente());
			oEOperacionSolicitud.setCodigoTipoDocumentoPersona(obj.getCodigoTipoDocumento());
			oEOperacionSolicitud.setNumeroDocumentoPersona(obj.getNumeroDocumento());
			oEOperacionSolicitud.setNombrePersona(obj.getNombreLargo());
			
			oEOperacionSolicitud.setCodigoAreaEmisor(obj.getCodigoAreaPromotor());
			oEOperacionSolicitud.setCodigoEmisor(obj.getCodigoUsuarioPromotor());
			oEOperacionSolicitud.setDescripcionEmisor(obj.getDescripcionUsuarioPromotor());
			
			oEOperacionSolicitud.setCodigoTipoEnvio(UTipoEnvio.PUBLICO);
			oEOperacionSolicitud.setCodigoAreaReceptor(UArea.LEGAL);
			oEOperacionSolicitud.setCodigoReceptor(0);
			oEOperacionSolicitud.setDescripcionReceptor("");
			
			oEOperacionSolicitud.setDescripcionAsunto("-");
			//Buscar ultimo observacion de negocios
			oEOperacionSolicitud.setDescripcionMensaje(oBOSolicitudCredito.buscarObservacionNegocios(obj.getNumeroSolicitud(), 1));
			oEOperacionSolicitud.setFechaOrigen(obj.getFechaOrigen());
			oEOperacionSolicitud.setHoraOrigen(obj.getHoraOrigen());
			oEOperacionSolicitud.setFechaInicio(obj.getFechaInicio());
			oEOperacionSolicitud.setHoraInicio(obj.getHoraInicio());
			oEOperacionSolicitud.setFechaRegistro(new Date());
			oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBOOperacion.agregarEvaluacionSolicitudCredito(oEOperacionSolicitud);
		}
	}
	*/
	
	public void verificarSolicitudSiaf() {
		oBOOperacion.verificarSolicitudSiaf();
	}
	
	public void listarSolicitud() {
		EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
		oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
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
		oEOperacionSolicitud.setNombrePersona(nombrePersona);
		oEOperacionSolicitud.setCodigoSolicitudCredito(numeroSolicitud);
		oEOperacionSolicitud.setCodigoEstado(codigoEstado);
		oEOperacionSolicitud.setCodigoAutorizaJefe(codigoAutorizaJefe);
		oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
		
		lstOperacionSolicitud = oBOOperacion.listarSolicitud(oEOperacionSolicitud, 2);
		
		if (lstOperacionSolicitud != null){
			lstOperacionSolicitudPendiente = new ArrayList<EOperacionSolicitud>();
	        for (EOperacionSolicitud obj: lstOperacionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.PENDIENTEDEEVALUACION ||
	            	obj.getCodigoEstado() == UEstado.ENEVALUACION ) {
	            	lstOperacionSolicitudPendiente.add(obj);
	            }
	        }
	        
	        lstOperacionSolicitudHistorico = new ArrayList<EOperacionSolicitud>();
	        for (EOperacionSolicitud obj: lstOperacionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.OBSERVADO ||
	            	obj.getCodigoEstado() == UEstado.CERRADO ||
	            	obj.getCodigoEstado() == UEstado.DESAPROBADO ||
	            	obj.getCodigoEstado() == UEstado.APROBADO) {
	            	lstOperacionSolicitudHistorico.add(obj);
	            }
	        }
		}
	}
	
	public void listarDesplegable(){
		lstEstado = oUManejadorListaDesplegable.obtieneEstadoOperacion();
		lstAutorizaJefe = oUManejadorListaDesplegable.obtieneAutorizaJefe();
	}
	
	public void inicializar() {
		indicadorTabSeleccion = 0;
		codigoFechaInicio = 2;
		nombrePersona= "";
		descripcionAsunto="";
		//codigoAutorizaJefe = 0;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
}
