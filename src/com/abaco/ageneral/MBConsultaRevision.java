package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.ageneral.BORevision;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mbconsultarevision")
@ViewScoped
public class MBConsultaRevision implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private ERevisionSolicitud oERevisionSolicitudLoad;
	
	private ERevisionMensaje oERevisionMensajeSelected;
	
	private BORevision oBORevision;
	private BOGeneral oBOGeneral;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<ERevisionMensaje> lstRevisionMensaje;
	@Getter @Setter private List<ERevisionDocumento> lstRevisionDocumento;
	@Getter @Setter private List<ERevisionDocumento> lstRevisionDocumentoFiltro;
	@Getter @Setter private List<ERevisionTiempo> lstTiempo;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstServicio;
	@Getter @Setter private List<EGeneral> lstEnvio;
	@Getter @Setter private List<EGeneral> lstTipoPersonaRelacion;

	/* Variables Interfaz */
	@Getter @Setter private int codigoPersonaRelacion;
	@Getter @Setter private String descripcionMensajeSeleccion;

	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean visualizar;
	@Getter @Setter private boolean visualizarNombrePersona;
	//private String mensajeValidacion;

	/* Variables Internas */
	//private long codigoSolicitud;
	private int accionExterna;

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oERevisionSolicitudLoad = new ERevisionSolicitud();
		
		oERevisionMensajeSelected = new ERevisionMensaje();
		
		oBORevision = new BORevision();
		oBOGeneral = new BOGeneral();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstRevisionMensaje = new ArrayList<ERevisionMensaje>();
		lstRevisionDocumento = new ArrayList<ERevisionDocumento>();
		lstRevisionDocumentoFiltro = new ArrayList<ERevisionDocumento>();
		lstTiempo = new ArrayList<ERevisionTiempo>();
		lstNivel = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstServicio = new ArrayList<EGeneral>();
		lstEnvio = new ArrayList<EGeneral>();
		lstTipoPersonaRelacion = new ArrayList<EGeneral>();
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
			
			if(UAccionExterna.VER == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oERevisionSolicitudLoad = (ERevisionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					codigoPersonaRelacion = oERevisionSolicitudLoad.getCodigoPersonaRelacion();
				}
			}
		}
		
		listarDesplegable();
		listarMensaje();
		listarDocumento();
		listarTiempo();
		filtrarDocumento();
		visualizarPersonaRelacion();
	}
	
	public void listarTiempo() {
		if (oERevisionSolicitudLoad != null) {
			List<ERevisionSesion> lstTiempoDetalle = new ArrayList<ERevisionSesion>();
			lstTiempo = oBORevision.listarTiempoResumen(oERevisionSolicitudLoad.getCodigoSolicitud());
			
			for(int i=0;i<lstTiempo.size();i++){
				lstTiempoDetalle = new ArrayList<ERevisionSesion>();
				lstTiempoDetalle = oBORevision.listarTiempoDetalle(oERevisionSolicitudLoad.getCodigoSolicitud(), lstTiempo.get(i).getCodigoArea());
				if(lstTiempoDetalle != null){
					lstTiempo.get(i).setLstRevisionSesion(lstTiempoDetalle);
				}
			}
		}
	}
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			//ruta = "volver_BandejaRevisionLegal";
			ruta = "BandejaRevisionLegal.xhtml";
		}else{
			//ruta = "volver_BandejaRevisionOtros";
			ruta = "BandejaRevisionOtros.xhtml";
		}
		
		inicializar();
		/*Cerrar Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.REVISION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		//return ruta;
	}
	
	public void listarDesplegable(){
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstMotivo = oUManejadorListaDesplegable.obtieneMotivo();
		lstServicio = oUManejadorListaDesplegable.obtieneTipoServicio();
		lstEnvio = oUManejadorListaDesplegable.obtieneTipoEnvio();
		lstTipoPersonaRelacion = oUManejadorListaDesplegable.obtieneTipoPersonaRelacion();
	}	
	
	public void listarMensaje() {
		lstRevisionMensaje = oBORevision.listarMensaje(oERevisionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarDocumento() {
		lstRevisionDocumento = oBORevision.listarDocumento(oERevisionSolicitudLoad.getCodigoSolicitud());
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
	
	public void visualizarPersonaRelacion(){
		if (codigoPersonaRelacion == UPersonaRelacion.EXTERNO){
			visualizarNombrePersona = true;
			//visualizarBuscarPersona = true;
		}else if (codigoPersonaRelacion == UPersonaRelacion.ABACO){
			visualizarNombrePersona = true;
			//visualizarBuscarPersona = true;
		}else{
			visualizarNombrePersona = false;
			//visualizarBuscarPersona = false;
		}
	}
	
	public void visualizarMensaje(String mensaje) {
		descripcionMensajeSeleccion = mensaje;
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleMensaje').show();");
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
		deshabilitar = false;
		visualizar = true;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public ERevisionSolicitud getoERevisionSolicitudLoad() {
		return oERevisionSolicitudLoad;
	}

	public void setoERevisionSolicitudLoad(
			ERevisionSolicitud oERevisionSolicitudLoad) {
		this.oERevisionSolicitudLoad = oERevisionSolicitudLoad;
	}

	public ERevisionMensaje getoERevisionMensajeSelected() {
		return oERevisionMensajeSelected;
	}

	public void setoERevisionMensajeSelected(
			ERevisionMensaje oERevisionMensajeSelected) {
		this.oERevisionMensajeSelected = oERevisionMensajeSelected;
	}
}
