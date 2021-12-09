package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

import com.abaco.ageneral.BOSolicitudCredito;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.bo.BOGeneral;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UEstadoOperacionCliente;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UProcesoMantePostulante;
import com.abaco.negocio.util.UConstante.UTipoBusquedaTercero;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mblistaoperacionclienteotros")
@ViewScoped
public class MBListaOperacionClienteOtros implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private ECliente oEClienteData;
	
	private EPersona oEPersonaSelected;
	
	private BOGeneral oBOGeneral;
	private BOCliente oBOCliente;
	private BOOperacion oBOOperacion;
	
	@Getter @Setter private List<EOperacionCliente> lstOperacionCliente;
	@Getter @Setter private List<EOperacionCliente> lstOperacionClientePendiente;
	@Getter @Setter private List<EOperacionCliente> lstOperacionClienteHistorico;
	@Getter @Setter private List<EPersona> lstPersona;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;
	@Getter @Setter private int codigoTipoClienteBuscar;
	@Getter @Setter private int codigoPersonaBuscar;
	@Getter @Setter private String nombrePersonaBuscar;
	
	/* Variables Internas */
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEClienteData = new ECliente();
		
		oEPersonaSelected = new EPersona();
		
		oBOGeneral = new BOGeneral();
		oBOCliente = new BOCliente();
		oBOOperacion = new BOOperacion();
		
		lstOperacionCliente = new ArrayList<EOperacionCliente>();
		lstOperacionClientePendiente = new ArrayList<EOperacionCliente>();
		lstOperacionClienteHistorico = new ArrayList<EOperacionCliente>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		
		inicializar();
		listarOperacionCliente();
	}
	
	public void modificar(EOperacionCliente oEOperacionClienteItem) {
		String ruta = "";
		if (oEOperacionClienteItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionClienteItem);
			
			ruta = "RegistroOperacionCliente.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void visualizar(EOperacionCliente oEOperacionClienteItem) {
		String ruta = "";
		if (oEOperacionClienteItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionClienteItem);
			
			ruta = "RegistroOperacionCliente.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void agregarOperacionCliente(){
		this.oEClienteData = new ECliente();
		RequestContext.getCurrentInstance().execute("PF('dlgAgregarInforme').show();");
	}
	
	public void guardarOperacionCliente(){
		EOperacionCliente oEOperacionCliente = new EOperacionCliente();
		
		oEOperacionCliente.setCodigoCliente(oEClienteData.getCodigoCliente());
		oEOperacionCliente.setCodigoTipoCliente(oEClienteData.getCodigoTipoCliente());
		oEOperacionCliente.setCodigoTipoPersona(oEClienteData.getCodigoTipoPersona());
		oEOperacionCliente.setNombreCorto(oEClienteData.getNombreCorto());
		oEOperacionCliente.setNombreLargo(oEClienteData.getNombreLargo());
		oEOperacionCliente.setFechaRegistro(new Date());
		oEOperacionCliente.setUsuarioRegistro(oEUsuario);
		
		oEMensaje = oBOOperacion.agregarModificarEvaluacionCliente(oEOperacionCliente);
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		listarOperacionCliente();
		
		RequestContext.getCurrentInstance().execute("PF('dlgAgregarInforme').hide();");
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	public void buscarPersona() {
		EPersonaParametro oEPersonaParametro = new EPersonaParametro();
		
		oEPersonaParametro.setCodPersona(codigoPersonaBuscar);
		oEPersonaParametro.setNombrePersona(nombrePersonaBuscar);
		
		if(codigoTipoClienteBuscar == UTipoCliente.COD_SOCIO) {
			lstPersona = oBOCliente.listarSocio(oEPersonaParametro);
		}else if(codigoTipoClienteBuscar == UTipoCliente.COD_POSTULANTE) {
			lstPersona = oBOCliente.listarPostulante(oEPersonaParametro);
		}else if(codigoTipoClienteBuscar == UTipoCliente.COD_TERCERO) {
			lstPersona = oBOCliente.listarTercero(oEPersonaParametro, UTipoBusquedaTercero.TERCERO);
		}else if(codigoTipoClienteBuscar == UTipoCliente.COD_NO_SOCIO) {
			lstPersona = oBOCliente.listarNoSocio(oEPersonaParametro);
		}
	}
	
	public void asignarPersona() {
		if(oEPersonaSelected != null){
			oEClienteData.setCodigoCliente(oEPersonaSelected.getCodigo());
			oEClienteData.setCodigoTipoCliente(codigoTipoClienteBuscar);
			oEClienteData.setCodigoTipoDocumento(oEPersonaSelected.getDocumentoIdentidad().getTipoDocumento().getCodigo());
			oEClienteData.setCodigoTipoPersona(oEPersonaSelected.getClasePersona());
			oEClienteData.setDocumento(oEPersonaSelected.getDocumentoIdentidad().getDocumento());
			oEClienteData.setNombreCorto(oEPersonaSelected.getNombreCorte());
			oEClienteData.setNombreLargo(oEPersonaSelected.getNombreLargo());
		}
	}
	
	public void listarOperacionCliente() {
		lstOperacionCliente = oBOOperacion.listarEvaluacionCliente(2, "", oEUsuario);
		
		if (lstOperacionCliente != null){
			lstOperacionClientePendiente = new ArrayList<EOperacionCliente>();
	        for (EOperacionCliente obj: lstOperacionCliente) {
	            if (obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.OBSERVADO ) {
	            	lstOperacionClientePendiente.add(obj);
	            }
	        }
	        
	        lstOperacionClienteHistorico = new ArrayList<EOperacionCliente>();
	        for (EOperacionCliente obj: lstOperacionCliente) {
	            if (obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.ENEVALUACION ||
	            	obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.EVALUADO ||
	            	obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.DESAPROBADO ||
	            	obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.APROBADO) {
	            	lstOperacionClienteHistorico.add(obj);
	            }
	        }
		}
	}
	
	public void buscarOperacionCliente(){
		lstOperacionCliente = oBOOperacion.listarEvaluacionCliente(codigoBuscar, descripcionBuscar, oEUsuario);
		
		if (lstOperacionCliente != null){
			lstOperacionClientePendiente = new ArrayList<EOperacionCliente>();
	        for (EOperacionCliente obj: lstOperacionCliente) {
	            if (obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.OBSERVADO ) {
	            	lstOperacionClientePendiente.add(obj);
	            }
	        }
	        
	        lstOperacionClienteHistorico = new ArrayList<EOperacionCliente>();
	        for (EOperacionCliente obj: lstOperacionCliente) {
	            if (obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.ENEVALUACION ||
	            	obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.EVALUADO ||
	            	obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.DESAPROBADO ||
	            	obj.getCodigoEstadoSolicitud() == UEstadoOperacionCliente.APROBADO) {
	            	lstOperacionClienteHistorico.add(obj);
	            }
	        }
		}
	}
	
	public void inicializar() {
		codigoTipoClienteBuscar = UTipoCliente.COD_SOCIO;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
	
	public ECliente getoEClienteData() {
		return oEClienteData;
	}

	public void setoEClienteData(ECliente oEClienteData) {
		this.oEClienteData = oEClienteData;
	}
	
	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}
}
