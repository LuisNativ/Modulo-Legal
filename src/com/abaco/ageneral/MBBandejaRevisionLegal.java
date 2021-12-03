package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;






import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

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
import com.abaco.negocio.util.UConstante.UMensajeValidacion;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoServicio;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UManejadorUrl;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mbbandejarevisionlegal")
@ViewScoped
public class MBBandejaRevisionLegal implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private ERevisionSolicitud oERevisionSolicitudData;
	
	private EUsuario oEUsuarioReasignacionSelected;
	
	private BORevision oBORevision;
	private BOGeneral oBOGeneral;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<ERevisionSolicitud> lstRevisionSolicitud;
	@Getter @Setter private List<ERevisionSolicitud> lstRevisionSolicitudPendiente;
	@Getter @Setter private List<ERevisionSolicitud> lstRevisionSolicitudHistorico;
	@Getter @Setter private List<ERevisionMensaje> lstRevisionMensaje;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstEstado;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EUsuario> lstUsuarioReasignacion;
	@Getter @Setter private List<EUsuario> lstUsuarioReasignacionFiltro;
	@Getter @Setter private List<EGeneral> lstUsuarioEvaluacion;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoNivel;
	@Getter @Setter private int codigoEstado;
	@Getter @Setter private int codigoUsuarioEvaluacion;
	@Getter @Setter private String descripcionUsuarioReasignar;
	@Getter @Setter private int codigoFechaInicio;
	@Getter @Setter private Date fechaInicioDesde;
	@Getter @Setter private Date fechaInicioHasta;
	@Getter @Setter private String nombrePersona;
	@Getter @Setter private String descripcionAsunto;
	@Getter @Setter private int indicadorTabSeleccion;
	@Getter @Setter private String descripcionMensajeSeleccion;
	
	@Getter @Setter private boolean deshabilitarFechaInicio;

	@Getter @Setter private String mensajeValidacion;
	
	/* Variables Internas */
	private long codigoSolicitudSeleccion;
	private String indicadorTipoReasignar;

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oERevisionSolicitudData = new ERevisionSolicitud();
		
		oEUsuarioReasignacionSelected = new EUsuario();
		
		oBORevision = new BORevision();
		oBOGeneral = new BOGeneral();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstRevisionSolicitud = new ArrayList<ERevisionSolicitud>();
		lstRevisionSolicitudPendiente = new ArrayList<ERevisionSolicitud>();
		lstRevisionSolicitudHistorico = new ArrayList<ERevisionSolicitud>();
		lstRevisionMensaje = new ArrayList<ERevisionMensaje>();
		lstNivel = new ArrayList<EGeneral>();
		lstEstado = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstUsuarioReasignacion = new ArrayList<EUsuario>();
		lstUsuarioReasignacionFiltro = new ArrayList<EUsuario>();
		lstUsuarioEvaluacion = new ArrayList<EGeneral>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEMensaje = UManejadorUrl.validarUrl("BandejaRevisionLegal.xhtml");
		
		if(oEMensaje.getIdMensaje() == 0){
			if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.REVISION_SESION) != null) {
				ERevisionSolicitud oERevisionSolicitud = (ERevisionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.REVISION_SESION);
				oBORevision.liberarSolicitudSesion(oERevisionSolicitud.getCodigoSolicitud());
				UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.REVISION_SESION);
			}
			
			inicializar();
			verificarSolicitudSesion();
			obtenerCodigoFechaInicio(0);
			listarDesplegable();
			listarSolicitud();
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacionUrl').show();");
		}
	}
	
	public void obtenerEstado(){
        if (codigoEstado == UEstado.PENDIENTEDEREGISTRO || codigoEstado == UEstado.PENDIENTEDEEVALUACION || codigoEstado == UEstado.ENPROCESODEEVALUACION) {
        	indicadorTabSeleccion = 0;
		}else if (codigoEstado == UEstado.OBSERVADO || codigoEstado == UEstado.AUTORIZADO || codigoEstado == UEstado.CANCELADO || codigoEstado == UEstado.CERRADO || codigoEstado == UEstado.DESAPROBADO) {
        	indicadorTabSeleccion = 1;
        }
		listarSolicitud();
	}
	
	public void obtenerCodigoFechaInicio(int ind){
		Calendar c = Calendar.getInstance();
		Date hoy = new Date();
		deshabilitarFechaInicio = true;
		
		if (codigoFechaInicio == 0){
		}else if (codigoFechaInicio == 1){
			c.add(Calendar.DATE, -7);
		}else if (codigoFechaInicio == 2){
			c.add(Calendar.DATE, -14);
		}else if (codigoFechaInicio == 3){
			c.add(Calendar.DATE, -30);
		}else if (codigoFechaInicio == 4){
			deshabilitarFechaInicio = false;
		}
		fechaInicioDesde = c.getTime();
		fechaInicioHasta = hoy;
		
		if(ind == 1){
			listarSolicitud();
		}
	}
	
	public void validarFechaInicio(){
		 //comprueba si es que fecha inicio desde esta después que fecha inicio hasta       
		if(fechaInicioDesde.after(fechaInicioHasta)){
			fechaInicioHasta = fechaInicioDesde;
		}
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
			if(ind == 2){
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
				//ruta = "ir_RegistroRevisionManual";
				ruta = "RegistroRevisionManual.xhtml";
			}
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}else {
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
		//return ruta;
	}
	
	public void modificar(ERevisionSolicitud oERevisionSolicitudItem) {
		String ruta = "";
		if(oERevisionSolicitudItem != null) {
		if(validar()){
			oERevisionSolicitudItem.setUsuarioRegistro(oEUsuario);
			
			this.oEMensaje = new EMensaje();
			oEMensaje = oBORevision.redigirSolicitud(oERevisionSolicitudItem);
			
			if(oEMensaje.getIdMensaje()<0){
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}else{
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.REVISION_SESION, oERevisionSolicitudItem);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oERevisionSolicitudItem);
				
				if(oERevisionSolicitudItem.getCodigoEstado() == UEstado.PENDIENTEDEREGISTRO && oERevisionSolicitudItem.getCodigoServicio() != UTipoServicio.SISTEMA){
					//ruta = "ir_RegistroRevisionManual";
					ruta = "RegistroRevisionManual.xhtml";
				}else{
					//ruta = "ir_RegistroRevision";
					ruta = "RegistroRevision.xhtml";
				}
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}else {
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
		}
		//return ruta;
	}
	
	public void visualizar(ERevisionSolicitud oERevisionSolicitudItem) {
		String ruta = "";
		if (oERevisionSolicitudItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oERevisionSolicitudItem);
			
			//ruta = "ir_ConsultaRevision";
			ruta = "ConsultaRevision.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
		//return ruta;
	}
	
	public void cancelarSolicitud(){
		if (codigoSolicitudSeleccion != 0){
			ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
			oERevisionSolicitud.setCodigoSolicitud(codigoSolicitudSeleccion);
			oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBORevision.modificarEstadoCancelar(oERevisionSolicitud);
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listarSolicitud();
		}
	}
	
	public void eliminarSolicitud(){
		if (codigoSolicitudSeleccion != 0){
			ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
			oERevisionSolicitud.setCodigoSolicitud(codigoSolicitudSeleccion);
			oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBORevision.modificarEstadoEliminar(oERevisionSolicitud);
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listarSolicitud();
		}
	}
	
	public void publicarSolicitud(){
		if (codigoSolicitudSeleccion != 0){
			ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
			oERevisionSolicitud.setCodigoSolicitud(codigoSolicitudSeleccion);
			oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBORevision.modificarEnvio(oERevisionSolicitud);
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listarSolicitud();
		}
	}
	
	public void obtenerOpcionCancelar(ERevisionSolicitud oERevisionSolicitudItem){
		if (oERevisionSolicitudItem != null) {
			codigoSolicitudSeleccion = oERevisionSolicitudItem.getCodigoSolicitud();
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionCancelar').show();");
		}
	}
	
	public void obtenerOpcionEliminar(ERevisionSolicitud oERevisionSolicitudItem){
		if (oERevisionSolicitudItem != null) {
			codigoSolicitudSeleccion = oERevisionSolicitudItem.getCodigoSolicitud();
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionEliminar').show();");
		}
	}
	
	public void obtenerOpcionPublicar(ERevisionSolicitud oERevisionSolicitudItem){
		if (oERevisionSolicitudItem != null) {
			codigoSolicitudSeleccion = oERevisionSolicitudItem.getCodigoSolicitud();
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionPublicar').show();");
		}
	}
	
	public void filtrarUsuariosReasignar(ERevisionSolicitud oERevisionSolicitudItem, int ind) {
		if (oERevisionSolicitudItem != null) {
			codigoSolicitudSeleccion = oERevisionSolicitudItem.getCodigoSolicitud();
			int codigoUsuarioReasignar = 0;
			if (ind == 1){
				codigoUsuarioReasignar = oERevisionSolicitudItem.getCodigoEmisor();
				descripcionUsuarioReasignar = oERevisionSolicitudItem.getDescripcionEmisor();
				indicadorTipoReasignar = UIndicadorTipoReasignar.EMISOR;
			}else if(ind == 2){
				codigoUsuarioReasignar = oERevisionSolicitudItem.getCodigoReceptor();
				descripcionUsuarioReasignar = oERevisionSolicitudItem.getDescripcionReceptor();
				indicadorTipoReasignar = UIndicadorTipoReasignar.RECEPTOR;
			}
				
			lstUsuarioReasignacionFiltro = new ArrayList<EUsuario>();
	        for (EUsuario oEUsuario: lstUsuarioReasignacion) {
	            if (oEUsuario.getIdUsuario() != codigoUsuarioReasignar) {
	            	lstUsuarioReasignacionFiltro.add(oEUsuario);
	            }
	        }
	        
	        //Seleccion por defecto primera fila
	        if(lstUsuarioReasignacionFiltro != null || lstUsuarioReasignacionFiltro.size() > 0){
	        	oEUsuarioReasignacionSelected = lstUsuarioReasignacionFiltro.get(0);
	        }
			RequestContext.getCurrentInstance().execute("PF('dlgReasignacion').show();");
		}
	}
	
	public void reasignarSolicitud(){
		if (oEUsuarioReasignacionSelected != null) {
			ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
			oERevisionSolicitud.setCodigoSolicitud(codigoSolicitudSeleccion);
			oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBORevision.agregarReasignacion(oERevisionSolicitud, oEUsuarioReasignacionSelected.getCodigoArea(), oEUsuarioReasignacionSelected.getIdUsuario(), oEUsuarioReasignacionSelected.getNombreCompleto(), indicadorTipoReasignar);
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listarSolicitud();
		}
	}
	
	public void visualizarMensaje(ERevisionMensaje oERevisionMensajeItem) {
		descripcionMensajeSeleccion = oERevisionMensajeItem.getDescripcionMensaje();
	}
	
	public void visualizarSolicitudDetalle(ERevisionSolicitud oERevisionSolicitudItem) {
		if (oERevisionSolicitudItem != null) {
			oERevisionSolicitudData = oERevisionSolicitudItem;
			lstRevisionMensaje = oBORevision.listarMensaje(oERevisionSolicitudItem.getCodigoSolicitud());
			RequestContext.getCurrentInstance().execute("PF('dlgDetalleSolicitud').show();");
		}
	}
	
	public void verificarSolicitudSesion() {
		oBORevision.verificarSolicitudSesion();
	}
	
	public void listarSolicitud() {
		ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
		oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
		oERevisionSolicitud.setNombrePersona(nombrePersona);
		oERevisionSolicitud.setDescripcionAsunto(descripcionAsunto);
		oERevisionSolicitud.setCodigoNivel(codigoNivel);
		oERevisionSolicitud.setCodigoEstado(codigoEstado);
		oERevisionSolicitud.setFechaInicioDesde(fechaInicioDesde);
		oERevisionSolicitud.setFechaInicioHasta(fechaInicioHasta);
		oERevisionSolicitud.setCodigoUsuarioEvaluacion(codigoUsuarioEvaluacion);
		
		lstRevisionSolicitud = oBORevision.listarSolicitud(oERevisionSolicitud, 2, 0);
		
		if (lstRevisionSolicitud != null){
			lstRevisionSolicitudPendiente = new ArrayList<ERevisionSolicitud>();
	        for (ERevisionSolicitud obj: lstRevisionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.PENDIENTEDEREGISTRO || obj.getCodigoEstado() == UEstado.PENDIENTEDEEVALUACION ||
	            	obj.getCodigoEstado() == UEstado.ENPROCESODEEVALUACION) {
	            	lstRevisionSolicitudPendiente.add(obj);
	            }
	        }
	        
	        lstRevisionSolicitudHistorico = new ArrayList<ERevisionSolicitud>();
	        for (ERevisionSolicitud obj: lstRevisionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.OBSERVADO || obj.getCodigoEstado() == UEstado.AUTORIZADO ||
	            	obj.getCodigoEstado() == UEstado.CANCELADO || obj.getCodigoEstado() == UEstado.CERRADO ||
	            	obj.getCodigoEstado() == UEstado.DESAPROBADO) {
	            	lstRevisionSolicitudHistorico.add(obj);
	            }
	        }
		}
	}
	
	public void listarDesplegable(){
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstEstado = oUManejadorListaDesplegable.obtieneEstado();
		lstMotivo = oUManejadorListaDesplegable.obtieneMotivo();
		lstUsuarioReasignacion = oBOGeneral.listarUsuarioPorAreaReasignacion(oEUsuario);
		lstUsuarioEvaluacion = oUManejadorListaDesplegable.obtieneUsuarioPorArea(UArea.LEGAL, UTipoEstadoUsuario.TODOS);
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
	
	public ERevisionSolicitud getoERevisionSolicitudData() {
		return oERevisionSolicitudData;
	}

	public void setoERevisionSolicitudData(
			ERevisionSolicitud oERevisionSolicitudData) {
		this.oERevisionSolicitudData = oERevisionSolicitudData;
	}

	public EUsuario getoEUsuarioReasignacionSelected() {
		return oEUsuarioReasignacionSelected;
	}

	public void setoEUsuarioReasignacionSelected(
			EUsuario oEUsuarioReasignacionSelected) {
		this.oEUsuarioReasignacionSelected = oEUsuarioReasignacionSelected;
	}
}
