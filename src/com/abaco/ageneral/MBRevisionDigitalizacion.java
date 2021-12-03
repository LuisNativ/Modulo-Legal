package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.ageneral.BORevision;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UCalificacion;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorSolicitud;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoSolicitud;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mbrevisiondigitalizacion")
@ViewScoped
public class MBRevisionDigitalizacion implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private ERevisionSolicitud oERevisionSolicitudLoad;
	private EOpcion oEOpcionLoad;
	
	private ERevisionSolicitud oERevisionSolicitudData;

	private ERevisionMensaje oERevisionMensajeSelected;
	
	private BORevision oBORevision;
	private BOGeneral oBOGeneral;
	
	@Getter @Setter private List<ERevisionMensaje> lstRevisionMensaje;
	@Getter @Setter private List<ERevisionDocumento> lstRevisionDocumento;
	@Getter @Setter private List<ERevisionDocumento> lstRevisionDocumentoFiltro;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	
	/* Variables Interfaz */
	@Getter @Setter private int indicadorSalir;
	
	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean visualizar;
	@Getter @Setter private String mensajeValidacion;

	/* Variables Internas */
	private long correlativoSesion;
	
	private int accionExterna;

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oERevisionSolicitudLoad = new ERevisionSolicitud();
		this.oEOpcionLoad = new EOpcion();
		
		this.oERevisionSolicitudData = new ERevisionSolicitud();
		
		oERevisionMensajeSelected = new ERevisionMensaje();
		
		oBORevision = new BORevision();
		oBOGeneral = new BOGeneral();
		
		lstRevisionMensaje = new ArrayList<ERevisionMensaje>();
		lstRevisionDocumento = new ArrayList<ERevisionDocumento>();
		lstRevisionDocumentoFiltro = new ArrayList<ERevisionDocumento>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();

		files = new ArrayList<UploadedFile>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
			
			switch (accionExterna) {
			case UAccionExterna.NUEVO:
				deshabilitar = false;
				visualizar = true;
				break;
			case UAccionExterna.EDITAR:
				deshabilitar = true;
				visualizar = true;
				break;
			case UAccionExterna.VER:
				deshabilitar = true;
				visualizar = false;
			}
			
			if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oERevisionSolicitudLoad = (ERevisionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					
					oERevisionSolicitudData = oERevisionSolicitudLoad;
					oEOpcionLoad.setIndicadorEnviar(1);
					indicadorSalir = 1;
					correlativoSesion = oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONSESION, oERevisionSolicitudLoad.getCodigoSolicitud()+"", "", "");
				
					enviarSesion();
				}
			}
			listarMensaje();
			listarDocumento();
			filtrarDocumento();	
		}
	}
	
	public void obtenerCodigoEstadoSeleccion(int ind){
		if(validar()){
			if (ind == 1) {
				if(lstDocumentoCarga.size()==0){
					RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionAdjuntar').show();");
				}else{
					guardar();
				}
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void guardar() {
		generarCorrelativoDocumentoCarga();
		ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
		ERevisionSesion oERevisionSesion = new ERevisionSesion();
		
		oERevisionSolicitud = oERevisionSolicitudData;
		oERevisionSolicitud.setLstDocumentoCarga(lstDocumentoCarga);
		
		oERevisionSolicitud.setFechaRegistro(new Date());
		oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.EDITAR == accionExterna){
			oERevisionSesion.setCorrelativoSesion(correlativoSesion);
			oERevisionSesion.setFechaRegistro(oERevisionSolicitud.getFechaRegistro());
			oERevisionSesion.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBORevision.modificarSolicitudDigitalizacion(oERevisionSolicitud, oERevisionSesion);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}

	public boolean validar() {
		boolean ind=true;
		mensajeValidacion = "";
        return ind;
	}
	
	public void controlarSesion() {
		enviarSesion();
	}
	
	public void enviarSesion() {
		if(UAccionExterna.EDITAR == accionExterna) {
		if(oEMensaje.getDescMensaje() == null || oEMensaje.getDescMensaje().length()==0){
			if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO) != null) {
				ERevisionSesion oERevisionSesion = new ERevisionSesion();
				oERevisionSesion.setCodigoSolicitud(oERevisionSolicitudLoad.getCodigoSolicitud());
				oERevisionSesion.setCorrelativoSesion(correlativoSesion);
				oERevisionSesion.setFechaRegistro(new Date());
				oERevisionSesion.setUsuarioRegistro(oEUsuario);
				oBORevision.modificarSesion(oERevisionSesion);
			}
		}
		}
	}
	
	public void salir() {
		//String ruta = "volver_BandejaRevisionDigitalizacion";
		String ruta = "BandejaRevisionDigitalizacion.xhtml";
		
		enviarSesion();
		
		if(UAccionExterna.EDITAR == accionExterna) {
			oBORevision.liberarSolicitudSesion(oERevisionSolicitudLoad.getCodigoSolicitud());
		}
		
		inicializar();
		/*Cerramos Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.REVISION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		//return ruta;
	}
	
	public void listarMensaje() {
		lstRevisionMensaje = oBORevision.listarMensaje(oERevisionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarDocumento() {
		lstRevisionDocumento = oBORevision.listarDocumento(oERevisionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
	}
	
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
	}
	
	public void listarDocumentoCargaMultiple() {
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCarga.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCarga.size();x++){
					if(lstDocumentoCarga.get(x).getNombre().equals(files.get(i).getFileName())){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(files.get(i).getFileName());
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCarga.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(files.get(i).getFileName());
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCarga.add(oEDocumentoCarga);
			}
		}
		files = new ArrayList<UploadedFile>();
	}
	
	public void generarCorrelativoDocumentoCarga() {
		int correlativoDocumento = 0;
		if(oERevisionSolicitudLoad.getCodigoSolicitud() > 0){
			for(int i=0;i<lstRevisionDocumento.size();i++){
				correlativoDocumento = lstRevisionDocumento.get(i).getCodigoDocumento();
			}
		}
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
	}
	
	public void descargarDocumento(ERevisionDocumento oERevisionDocumentoItem) {
		if (oERevisionDocumentoItem != null) {
			UManejadorArchivo manejoArchivo = new UManejadorArchivo();
			Documento archivo = manejoArchivo.obtenerDocumento(oERevisionDocumentoItem.getCodigoDocumentoLaserFiche());
			if (archivo != null && archivo.getArchivoBinario() != null && archivo.getArchivoBinario().length > 0) {
				InputStream stream = new ByteArrayInputStream(archivo.getArchivoBinario());
				fileDownload = new DefaultStreamedContent(stream, "image/png", oERevisionDocumentoItem.getNombreDocumento());
			} else {
				if (oERevisionDocumentoItem.getDataDocumento() != null && oERevisionDocumentoItem.getDataDocumento().length > 0) {
					UFuncionesGenerales.descargaArchivo(oERevisionDocumentoItem.getNombreDocumento(), oERevisionDocumentoItem.getDataDocumento());
				}
			}
		}
	}
	
	public void filtrarDocumento() {
		if (oERevisionMensajeSelected != null) {			
			lstRevisionDocumentoFiltro = new ArrayList<ERevisionDocumento>();
	        for (ERevisionDocumento oERevisionDocumento: lstRevisionDocumento) {
	            if (oERevisionDocumento.getCodigoMensaje() == oERevisionMensajeSelected.getCodigoMensaje()) {
	            	lstRevisionDocumentoFiltro.add(oERevisionDocumento);
	            }
	        }
		}
	}
	
	public void inicializar() {
		deshabilitar = true;
		visualizar = false;
		indicadorSalir = 0;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public ERevisionMensaje getoERevisionMensajeSelected() {
		return oERevisionMensajeSelected;
	}

	public void setoERevisionMensajeSelected(
			ERevisionMensaje oERevisionMensajeSelected) {
		this.oERevisionMensajeSelected = oERevisionMensajeSelected;
	}

	public ERevisionSolicitud getoERevisionSolicitudLoad() {
		return oERevisionSolicitudLoad;
	}

	public void setoERevisionSolicitudLoad(
			ERevisionSolicitud oERevisionSolicitudLoad) {
		this.oERevisionSolicitudLoad = oERevisionSolicitudLoad;
	}

	public EOpcion getoEOpcionLoad() {
		return oEOpcionLoad;
	}

	public void setoEOpcionLoad(EOpcion oEOpcionLoad) {
		this.oEOpcionLoad = oEOpcionLoad;
	}
}
