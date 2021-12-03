package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;






import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UMensajeValidacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UManejadorUrl;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mbbandejarevisiondigitalizacion")
@ViewScoped
public class MBBandejaRevisionDigitalizacion implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private ERevisionSolicitud oERevisionSolicitudData;
	
	private BORevision oBORevision;
	
	@Getter @Setter private List<ERevisionSolicitud> lstRevisionSolicitud;
	
	/* Variables Interfaz */
	@Getter @Setter private String mensajeValidacion;
	
	/* Variables Internas */

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oERevisionSolicitudData = new ERevisionSolicitud();
		
		oBORevision = new BORevision();
		
		lstRevisionSolicitud = new ArrayList<ERevisionSolicitud>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEMensaje = UManejadorUrl.validarUrl("BandejaRevisionDigitalizacion.xhtml");
		
		if(oEMensaje.getIdMensaje() == 0){
			if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.REVISION_SESION) != null) {
				ERevisionSolicitud oERevisionSolicitud = (ERevisionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.REVISION_SESION);
				oBORevision.liberarSolicitudSesion(oERevisionSolicitud.getCodigoSolicitud());
				UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.REVISION_SESION);
			}
			
			verificarSolicitudSesion();
			listarSolicitud();
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacionUrl').show();");
		}
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
	
	public void modificar(ERevisionSolicitud oERevisionSolicitudItem) {
		String ruta = "";
		if (oERevisionSolicitudItem != null) {
		if(validar()){
			oERevisionSolicitudItem.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBORevision.redigirSolicitud(oERevisionSolicitudItem);
			if(oEMensaje.getIdMensaje()<0){
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}else{
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.REVISION_SESION, oERevisionSolicitudItem);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oERevisionSolicitudItem);
				
				//ruta = "ir_RegistroRevisionDigitalizacion";
				ruta = "RegistroRevisionDigitalizacion.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}else {
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
		}
		//return ruta;
	}
	
	public void verificarSolicitudSesion() {
		oBORevision.verificarSolicitudSesion();
	}
	
	public void listarSolicitud() {
		ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
		oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
		lstRevisionSolicitud = oBORevision.listarSolicitud(oERevisionSolicitud, 1, 0);
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
}
