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

@ManagedBean(name = "mblistaoperacionlevantamiento")
@ViewScoped
public class MBListaOperacionLevantamiento implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private BOOperacion oBOOperacion;
	private BOGarantia oBOGarantia;
	private BOGeneral oBOGeneral;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaPendiente;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaCredito;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaLiberada;
	
	//Control de Acciones
	@Getter @Setter private boolean visualizarTabGarantiaVigente;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoBuscarGarantia;
	@Getter @Setter private String descripcionBuscarGarantia;
	
	/* Variables Internas */
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oBOOperacion = new BOOperacion();
		oBOGarantia = new BOGarantia();
		oBOGeneral = new BOGeneral();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstGarantiaPendiente = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaCredito = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaLiberada = new ArrayList<EGarantiaSolicitud>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		
		inicializar();
		listarLevantamiento();
		listarDesplegable();
	}
	
	public void agregar(EGarantiaSolicitud oEGarantiaSolicitudItem) {
		String ruta = "";
		if (oEGarantiaSolicitudItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSolicitudItem);
			
			ruta = "RegistroOperacionLevantamientoGarantia.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void modificar(EGarantiaSolicitud oEGarantiaSolicitudItem) {
		String ruta = "";
		if (oEGarantiaSolicitudItem != null) {
			oEGarantiaSolicitudItem.setUsuarioRegistro(oEUsuario);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSolicitudItem);
			
			ruta = "RegistroOperacionLevantamientoGarantia.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		
		}
	}
	
	public void visualizar(EGarantiaSolicitud oEGarantiaSolicitudItem) {
		String ruta = "";
		if (oEGarantiaSolicitudItem != null) {
			oEGarantiaSolicitudItem.setUsuarioRegistro(oEUsuario);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSolicitudItem);
			
			ruta = "RegistroOperacionLevantamientoGarantia.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		
		}
	}
	
	public void listarLevantamiento() {
		lstGarantiaPendiente = oBOOperacion.listarEvaluacionLevantamientoGarantia(0, "", oEUsuario, 1);
		lstGarantiaCredito = oBOGarantia.listarGarantiaPorLiberar(0, "");
		lstGarantiaLiberada = oBOGarantia.listarGarantiaLiberada(0, "");
	}
	
	public void buscarGarantia() {
		lstGarantiaPendiente = oBOOperacion.listarEvaluacionLevantamientoGarantia(codigoBuscarGarantia, descripcionBuscarGarantia, oEUsuario, 1);
		lstGarantiaCredito = oBOGarantia.listarGarantiaPorLiberar(codigoBuscarGarantia, descripcionBuscarGarantia);
		lstGarantiaLiberada = oBOGarantia.listarGarantiaLiberada(codigoBuscarGarantia, descripcionBuscarGarantia);
	}
	
	public void listarDesplegable(){
	}
	
	public void inicializar() {
		visualizarTabGarantiaVigente = false;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
}
