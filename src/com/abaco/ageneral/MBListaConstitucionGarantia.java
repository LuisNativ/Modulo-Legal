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
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UProcesoMantePostulante;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mblistaconstituciongarantia")
@ViewScoped
public class MBListaConstitucionGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private BOOperacion oBOOperacion;
	private BOGarantia oBOGarantia;
	
	private BOGeneral oBOGeneral;
	private BOSolicitudCredito oBOSolicitudCredito;
	private EGarantia oEGarantiaSelected;
	
	@Getter @Setter private List<EEvaluacionSolicitudCreditoLegal> lstEvaluacionSolicitudCreditoLegal;
	@Getter @Setter private List<EOperacionDocumento> lstSolicitudDocumento;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;
	
	/* Variables Internas */

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oBOGeneral = new BOGeneral();
		oBOGarantia = new BOGarantia();
		oBOOperacion = new BOOperacion();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oEGarantiaSelected = new EGarantia();
		lstEvaluacionSolicitudCreditoLegal = new ArrayList<EEvaluacionSolicitudCreditoLegal>();
		lstSolicitudDocumento = new ArrayList<EOperacionDocumento>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		listarSolicitudDocumento();
	}
	
	public void modificar(EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegalItem) {
		String ruta = "";
		if (oEEvaluacionSolicitudCreditoLegalItem != null) {
			//UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitud);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEEvaluacionSolicitudCreditoLegalItem);
			
			ruta = "RegistroOperacionCliente.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void listarSolicitudDocumento() {
		EGarantia eGarantia = new EGarantia();
		eGarantia.setUsuarioRegistro(oEUsuario);
		lstSolicitudDocumento = oBOOperacion.listarDocumentoGarantia(eGarantia,2);
	}
	
	public void evaluarSolicitudDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		
		if (oEOperacionDocumentoItem != null) {
			
			oEGarantiaSelected = oBOGarantia.buscarGarantia(oEOperacionDocumentoItem.getCodigoGarantia());

			String ruta = "";
			if (oEGarantiaSelected != null) {
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSelected);
				
				ruta = "MantenimientoOperacionGarantia.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}
	}
	

}
