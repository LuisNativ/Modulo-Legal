package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.bo.BOGeneral;
import com.abaco.bo.BOUsuario;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UManejadorUrl;
import com.abaco.negocio.util.UtilWeb;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mbmantenimientoplantilla")
@ViewScoped
public class MBMantenimientoPlantilla implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EDocumentoControlVersion oEDocumentoControlVersionData;
	
	private EGeneral oEUsuarioSelected;
	
	private BOGeneral oBOGeneral;
	private BODocumentoControlVersion oBODocumentoControlVersion;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EDocumentoControlVersion> lstDocumentoControlVersion;
	@Getter @Setter private List<EGeneral> lstTipoEvaluacion;
	
	/* Variables Interfaz */
	@Getter @Setter private String nombreUsuarioBuscar;
	@Getter @Setter private int accionInterna;
	
	/* Variables Internas */

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEDocumentoControlVersionData = new EDocumentoControlVersion();
		
		oEUsuarioSelected = new EGeneral();
		
		oBOGeneral = new BOGeneral();
		oBODocumentoControlVersion = new BODocumentoControlVersion();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstDocumentoControlVersion = new ArrayList<EDocumentoControlVersion>();
		lstTipoEvaluacion = new ArrayList<EGeneral>();
		
		files = new ArrayList<UploadedFile>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEMensaje = UManejadorUrl.validarUrl("MantenimientoControlVersionDocumento.xhtml");
		
		if(oEMensaje.getIdMensaje() == 0){
			inicializar();
			listarDesplegable();
			listar();
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacionUrl').show();");
		}
	}
	
	public void inicializarDocumento() {
		this.oEDocumentoControlVersionData = new EDocumentoControlVersion();
	}
	
	public void agregarDocumento() {
		accionInterna = UAccionInterna.NUEVO;
		inicializarDocumento();
		RequestContext.getCurrentInstance().execute("PF('dlgAgregarDocumentoControlVersion').show();");
	}
	
	public void guardarDocumento() {
		if (oEDocumentoControlVersionData != null) {
			this.oEMensaje = new EMensaje();
			oEDocumentoControlVersionData.setUsuarioRegistro(oEUsuario);
			
			if (accionInterna == UAccionInterna.NUEVO){
				oEMensaje = oBODocumentoControlVersion.agregar(oEDocumentoControlVersionData);
			}else if (accionInterna == UAccionInterna.EDITAR) {
				//oEMensaje = oBODocumentoControlVersion.modificar(oEDocumentoControlVersionData);
			}
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listar();
		}
	}
	
	public void listar(){
		lstDocumentoControlVersion = oBODocumentoControlVersion.listar();
	}
	
	public void listarDesplegable() {
		lstTipoEvaluacion = oUManejadorListaDesplegable.obtieneTipoEvaluacion();
	}
	
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
	}
	
	public void listarDocumentoCargaMultiple() {
		for(int i=0;i<files.size();i++){
			oEDocumentoControlVersionData.setNombreDocumento(files.get(i).getFileName());
			oEDocumentoControlVersionData.setDataDocumento(files.get(i).getContents());
		}
		files = new ArrayList<UploadedFile>();
		//RequestContext.getCurrentInstance().execute("PF('dlgDescripcionDocumentoCarga').hide();");
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
	
	public void inicializar() {
		nombreUsuarioBuscar = "";
	}

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
	
	public EGeneral getoEUsuarioSelected() {
		return oEUsuarioSelected;
	}

	public void setoEUsuarioSelected(EGeneral oEUsuarioSelected) {
		this.oEUsuarioSelected = oEUsuarioSelected;
	}

	public EDocumentoControlVersion getoEDocumentoControlVersionData() {
		return oEDocumentoControlVersionData;
	}

	public void setoEDocumentoControlVersionData(
			EDocumentoControlVersion oEDocumentoControlVersionData) {
		this.oEDocumentoControlVersionData = oEDocumentoControlVersionData;
	}
}
