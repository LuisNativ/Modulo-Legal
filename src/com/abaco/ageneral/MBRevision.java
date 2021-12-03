package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.CodingErrorAction;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
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
import org.hibernate.validator.Email;
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
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UCalificacion;
import com.abaco.negocio.util.UConstante.UCorreoEnvio;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorSolicitud;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UTipoBusquedaTercero;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UMaximoTamanio;
import com.abaco.negocio.util.UConstante.UMotivo;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UPlantillaEmail;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.ULectorDeParametros;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorCorreo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.servicio.laserfiche.Documento;
import com.laserfiche.repositoryaccess.Document;

@ManagedBean(name = "mbrevision")
@ViewScoped
public class MBRevision implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private ERevisionSolicitud oERevisionSolicitudLoad;
	private EOpcion oEOpcionLoad;
	
	private ERevisionSolicitud oERevisionSolicitudData;
	
	private ERevisionMensaje oERevisionMensajeSelected;
	private EPersona oEPersonaSelected;
	private EGeneral oEReceptorSelected;
	
	private BORevision oBORevision;
	private BOGeneral oBOGeneral;
	private BOCliente oBOCliente;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<ERevisionMensaje> lstRevisionMensaje;
	@Getter @Setter private List<ERevisionDocumento> lstRevisionDocumento;
	@Getter @Setter private List<ERevisionDocumento> lstRevisionDocumentoFiltro;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstServicio;
	@Getter @Setter private List<EGeneral> lstEnvio;
	@Getter @Setter private List<EGeneral> lstTipoPersonaRelacion;
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private List<EGeneral> lstAreaReceptor;
	@Getter @Setter private List<EGeneral> lstUsuarioReceptor;
	@Getter @Setter private List<EGeneral> lstDOI;

	/* Variables Interfaz */
	@Getter @Setter private String descripcionMensajeSeleccion;
	@Getter @Setter private String descripcionMensajeUltimo;
	@Getter @Setter private String descripcionDocumentoCarga;
	@Getter @Setter private int codigoTipoClientePersonaBuscar;
	@Getter @Setter private int codigoPersonaBuscar;
	@Getter @Setter private String nombrePersonaBuscar;
	
	@Getter @Setter private int indicadorDigitalizacion;
	private int indicadorTemporal;
	@Getter @Setter private int indicadorGuardar;
	@Getter @Setter private int indicadorSalir;
	
	@Getter @Setter private int maxLgnNumeroDocumentoPersona;
	
	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean deshabilitarTipoDocumentoPersona;
	@Getter @Setter private boolean deshabilitarNumeroDocumentoPersona;
	@Getter @Setter private boolean deshabilitarNombrePersona;
	@Getter @Setter private boolean deshabilitarBuscarPersona;
	@Getter @Setter private boolean visualizar;
	@Getter @Setter private boolean visualizarTipoDocumentoPersona;
	@Getter @Setter private boolean visualizarNumeroDocumentoPersona;
	@Getter @Setter private boolean visualizarNombrePersona;
	@Getter @Setter private boolean visualizarBuscarPersona;
	@Getter @Setter private boolean visualizarDescripcionMotivo;
	@Getter @Setter private boolean visualizarDescripcionMensajeDigitalizacion;
	@Getter @Setter private boolean visualizarUsuarioReceptor;
	@Getter @Setter private boolean visualizarDescripcionMensajeUltimo;
	@Getter @Setter private String mensajeValidacion;

	/* Variables Internas */
	private long correlativoSesion;
	private int codigoEstado;
	
	private Date fechaOrigenSesion;
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
		oEPersonaSelected = new EPersona();
		oEReceptorSelected = new EGeneral();
		
		oBORevision = new BORevision();
		oBOGeneral = new BOGeneral();
		oBOCliente = new BOCliente();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstRevisionMensaje = new ArrayList<ERevisionMensaje>();
		lstRevisionDocumento = new ArrayList<ERevisionDocumento>();
		lstRevisionDocumentoFiltro = new ArrayList<ERevisionDocumento>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		lstNivel = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstServicio = new ArrayList<EGeneral>();
		lstEnvio = new ArrayList<EGeneral>();
		lstTipoPersonaRelacion = new ArrayList<EGeneral>();
		lstAreaReceptor = new ArrayList<EGeneral>();
		lstUsuarioReceptor = new ArrayList<EGeneral>();
		lstDOI = new ArrayList<EGeneral>();
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
			
			if(UAccionExterna.NUEVO == accionExterna){
					//oERevisionSolicitudData.setIndicadorEnviar(1);
					oEOpcionLoad.setIndicadorEnviar(1);
					indicadorGuardar = 1;
					indicadorSalir = 1;
					
			}else if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oERevisionSolicitudLoad = (ERevisionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEOpcionLoad = oBORevision.buscarOpcionPorSolicitud(oERevisionSolicitudLoad.getCodigoSolicitud(), oEUsuario);
					
					oERevisionSolicitudData = oERevisionSolicitudLoad;
					indicadorGuardar = 1;
					indicadorSalir = 1;
					correlativoSesion = oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONSESION, oERevisionSolicitudLoad.getCodigoSolicitud()+"", "", "");
					
					if(oERevisionSolicitudLoad.getCodigoEstado() == UConstante.UEstado.PENDIENTEDEREGISTRO){
						deshabilitar = false;
						deshabilitarPersonaRelacion();
					}
					
					if(oEUsuario.getCodigoArea() == UArea.LEGAL){
						visualizarDescripcionMensajeUltimo = true;
					}
					
					enviarSesion();
				}
			}
			
			listarDesplegable();
			listarMensaje();
			listarMensajeTemporal();
			listarDocumento();
			listarDocumentoTemporal();
			filtrarDocumento();
			obtenerMensajeUltimo();
			visualizarUsuarioReceptor();
			visualizarPersonaRelacion();
		}
	}
	
	public void obtenerEstado(int ind){
		if(validar()){
			if (ind == 1) {
				codigoEstado = UEstado.PENDIENTEDEEVALUACION;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				RequestContext.getCurrentInstance().execute("PF('dlgEnviarDigitalizacion').show();");
			}else if(ind == 2){
				codigoEstado = UEstado.OBSERVADO;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				RequestContext.getCurrentInstance().execute("PF('dlgEnviarDigitalizacion').show();");
			}else if(ind == 3){
				codigoEstado = UEstado.CERRADO;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				guardar();
			}else if(ind == 4){
				codigoEstado = UEstado.DESAPROBADO;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				obtenerMotivo();
				RequestContext.getCurrentInstance().execute("PF('dlgDescartar').show();");
			}else if(ind == 5){
				codigoEstado = UEstado.CANCELADO;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				guardar();
			}else if(ind == 7){
				codigoEstado = UEstado.AUTORIZADO;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				RequestContext.getCurrentInstance().execute("PF('dlgEnviarDigitalizacion').show();");
			}else if(ind == 6){
				indicadorTemporal = UIndicadorTemporal.SI;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.NINGUNO);
				guardar();
			}else if(ind == 8){
				codigoEstado = UEstado.OBSERVADO;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.SOLICITADO);
				guardar();
			}else if(ind == 9){
				codigoEstado = UEstado.PENDIENTEDEEVALUACION;
				oERevisionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.AUTORIZADO);
				guardar();
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
		oERevisionSolicitud.setCodigoEstado(codigoEstado);
		oERevisionSolicitud.setIndicadorTemporal(indicadorTemporal);
		oERevisionSolicitud.setIndicadorDigitalizacion(indicadorDigitalizacion);
		oERevisionSolicitud.setLstRevisionDocumento(lstRevisionDocumento);
		oERevisionSolicitud.setLstDocumentoCarga(lstDocumentoCarga);
		
		oERevisionSolicitud.setFechaOrigen(fechaOrigenSesion);
		oERevisionSolicitud.setFechaRegistro(new Date());
		oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.NUEVO == accionExterna){
			oERevisionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONSOLICITUD, "", "", ""));
			oERevisionSolicitud.setCodigoAreaEmisorOrigen(oEUsuario.getCodigoArea());
			oERevisionSolicitud.setCodigoEmisorOrigen(oEUsuario.getIdUsuario());
			oERevisionSolicitud.setDescripcionAbreviacionEmisorOrigen(oEUsuario.getNombreCompleto());
			oERevisionSolicitud.setCodigoAreaEmisor(oEUsuario.getCodigoArea());
			oERevisionSolicitud.setCodigoEmisor(oEUsuario.getIdUsuario());
			oERevisionSolicitud.setDescripcionAbreviacionEmisor(oEUsuario.getNombreCompleto());
			
			oERevisionSesion.setFechaRegistro(oERevisionSolicitud.getFechaOrigen());
			oERevisionSesion.setFechaModificacion(oERevisionSolicitud.getFechaRegistro());
			oERevisionSesion.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBORevision.agregarSolicitud(oERevisionSolicitud, oERevisionSesion);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oERevisionSesion.setCorrelativoSesion(correlativoSesion);
			oERevisionSesion.setFechaRegistro(oERevisionSolicitud.getFechaRegistro());
			oERevisionSesion.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBORevision.modificarSolicitud(oERevisionSolicitud, oERevisionSesion);
		}
		
		if(UFuncionesGenerales.validaMensaje(oEMensaje)){
	        if(codigoEstado == UEstado.PENDIENTEDEEVALUACION ||
	        	codigoEstado == UEstado.AUTORIZADO ||
	        	codigoEstado == UEstado.OBSERVADO && oERevisionSolicitudData.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.NINGUNO ||
	        	codigoEstado == UEstado.CERRADO ||
	        	codigoEstado == UEstado.DESAPROBADO ||
	        	codigoEstado == UEstado.CANCELADO){
	        	enviarCorreo();
	        }
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}

	public boolean validar() {
		boolean ind=true;
		mensajeValidacion = "";
		if(UAccionExterna.NUEVO == accionExterna) {
			if(oERevisionSolicitudData.getCodigoNivel() == 0){
				mensajeValidacion = "Seleccione nivel";
				ind = false;
			}else if(oERevisionSolicitudData.getDescripcionAsunto().equals("")){
				mensajeValidacion = "Ingrese asunto";
				ind = false;
			}else if(oERevisionSolicitudData.getDescripcionMensaje().equals("")){
				mensajeValidacion = "Ingrese mensaje";
				ind = false;
			}else if(oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.EXTERNO){
				if(oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals("0")) {
					mensajeValidacion = "Ingrese tipo documento Persona";
					ind = false;
				}else if(oERevisionSolicitudData.getNumeroDocumentoPersona().equals("")) {
					mensajeValidacion = "Ingrese número documento Persona";
					ind = false;
				}else if(oERevisionSolicitudData.getNombrePersona().equals("")) {
					mensajeValidacion = "Ingrese nombre Persona";
					ind = false;
				}
				
				if(oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.DNI)) {
					if(oERevisionSolicitudData.getNumeroDocumentoPersona().length() < UMaximoTamanio.DNI_MAXLGN) {
						mensajeValidacion = "Ingrese número documento valido";
						ind = false;
					}
				}else if(oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.RUC)) {
					if(oERevisionSolicitudData.getNumeroDocumentoPersona().length() < UMaximoTamanio.RUC_MAXLGN) {
						mensajeValidacion = "Ingrese número documento valido";
						ind = false;
					}
				}
				
			}else if(oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.ABACO){
				if(oERevisionSolicitudData.getCodigoClientePersona() == 0){
					mensajeValidacion = "Ingrese Persona";
					ind = false;
				}else if(oERevisionSolicitudData.getNombrePersona().equals("")) {
					mensajeValidacion = "Ingrese Persona";
					ind = false;
				}
			}else if(oERevisionSolicitudData.getCodigoTipoEnvio() == UTipoEnvio.PUBLICO){
				if(oERevisionSolicitudData.getCodigoAreaReceptor() == 0){
					mensajeValidacion = "Ingrese Área destino";
					ind = false;
				}
			}else if(oERevisionSolicitudData.getCodigoTipoEnvio() == UTipoEnvio.PRIVADO){
				if(oERevisionSolicitudData.getCodigoAreaReceptor() == 0){
					mensajeValidacion = "Ingrese Área destino";
					ind = false;
				}else if(oERevisionSolicitudData.getCodigoReceptor() == 0){
					mensajeValidacion = "Ingrese Usuario destino";
					ind = false;
				}
			}
		}else{
			if(oERevisionSolicitudData.getDescripcionMensaje().equals("")){
				mensajeValidacion = "Ingrese mensaje";
				ind = false;
			}
		}
        return ind;
	}
	
	//Por el momento se usa el required de primefaces
	public boolean validarDocumentoCargaSimple() {
		boolean ind=true;
		mensajeValidacion = "";
		
		if(descripcionDocumentoCarga.equals("")){
			mensajeValidacion = "Ingrese descripción documento";
			ind = false;
		}
		
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
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			//ruta = "volver_BandejaRevisionLegal";
			ruta = "BandejaRevisionLegal.xhtml";
		}else{
			//ruta = "volver_BandejaRevisionOtros";
			ruta = "BandejaRevisionOtros.xhtml";
		}
		
		enviarSesion();
		
		if(UAccionExterna.EDITAR == accionExterna) {
			oBORevision.liberarSolicitudSesion(oERevisionSolicitudLoad.getCodigoSolicitud());
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
	
	public void buscarPersona() {
		EPersonaParametro oEPersonaParametro = new EPersonaParametro();
		
		oEPersonaParametro.setCodPersona(codigoPersonaBuscar);
		oEPersonaParametro.setNombrePersona(nombrePersonaBuscar);
		
		if(codigoTipoClientePersonaBuscar == UTipoCliente.COD_SOCIO) {
			lstPersona = oBOCliente.listarSocio(oEPersonaParametro);
		}else if(codigoTipoClientePersonaBuscar == UTipoCliente.COD_POSTULANTE) {
			lstPersona = oBOCliente.listarPostulante(oEPersonaParametro);
		}else if(codigoTipoClientePersonaBuscar == UTipoCliente.COD_TERCERO) {
			lstPersona = oBOCliente.listarTercero(oEPersonaParametro, UTipoBusquedaTercero.GENERAL);
		}
	}
	
	public void asignarPersona() {
		if(oEPersonaSelected != null){
			oERevisionSolicitudData.setCodigoClientePersona(oEPersonaSelected.getCodigo());
			oERevisionSolicitudData.setCodigoTipoClientePersona(codigoTipoClientePersonaBuscar);
			oERevisionSolicitudData.setCodigoTipoDocumentoPersona(oEPersonaSelected.getDocumentoIdentidad().getTipoDocumento().getCodigo());
			oERevisionSolicitudData.setNumeroDocumentoPersona(oEPersonaSelected.getDocumentoIdentidad().getDocumento());
			oERevisionSolicitudData.setNombrePersona(oEPersonaSelected.getNombre());
		}
	}
	
	public void listarDesplegable(){
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstMotivo = oUManejadorListaDesplegable.obtieneMotivo();
		lstServicio = oUManejadorListaDesplegable.obtieneTipoServicio();
		lstEnvio = oUManejadorListaDesplegable.obtieneTipoEnvio(accionExterna, oEUsuario.getIdUsuario());
		lstTipoPersonaRelacion = oUManejadorListaDesplegable.obtieneTipoPersonaRelacion();
		lstAreaReceptor = oUManejadorListaDesplegable.obtieneAreaReceptor();
		lstDOI = oUManejadorListaDesplegable.obtieneTipoDocumentoGeneral();
	}
	
	public void listarMensaje() {
		lstRevisionMensaje = oBORevision.listarMensaje(oERevisionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void obtenerMensajeUltimo(){
		for(int i=0;i<lstRevisionMensaje.size();i++){
			descripcionMensajeUltimo = lstRevisionMensaje.get(i).getDescripcionMensaje();
		}
	}
	
	public void listarMensajeTemporal() {
		oERevisionSolicitudData.setDescripcionMensaje(oBORevision.buscarMensajeTemporal(oERevisionSolicitudLoad.getCodigoSolicitud()));
	}
	
	public void listarDocumento() {
		lstRevisionDocumento = oBORevision.listarDocumento(oERevisionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarDocumentoTemporal() {
		lstDocumentoCarga = oBORevision.listarDocumentoTemporal(oERevisionSolicitudLoad.getCodigoSolicitud());
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
	
	public void listarDocumentoCargaSimple() {
		if(!descripcionDocumentoCarga.equals("") || descripcionDocumentoCarga.length()> 0){
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCarga.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCarga.size();x++){
					if(lstDocumentoCarga.get(x).getNombre().equals(descripcionDocumentoCarga+UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(descripcionDocumentoCarga + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCarga.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(descripcionDocumentoCarga + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCarga.add(oEDocumentoCarga);
			}
		}
		descripcionDocumentoCarga = "";
		files = new ArrayList<UploadedFile>();
		RequestContext.getCurrentInstance().execute("PF('dlgDescripcionDocumentoCarga').hide();");
		}
	}
	
	public void generarCorrelativoDocumentoCarga() {
		int correlativoDocumento = 0;
		if(oERevisionSolicitudLoad.getCodigoSolicitud() > 0){
			for(int i=0;i<lstRevisionDocumento.size();i++){
				correlativoDocumento = lstRevisionDocumento.get(i).getCodigoDocumento();
			}
			//correlativoDocumento = oBORevisionDocumento.buscarCorrelativoDocumento(codigoSolicitud);
		}
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
	}
	
	/*
	public void descargarDocumento(ERevisionDocumento oERevisionDocumentoItem) {
		if (oERevisionDocumentoItem != null) {
			UManejadorArchivo3 manejoArchivo = new UManejadorArchivo3();
			EArchivo archivo = manejoArchivo.obtenerArchivo(2, String.valueOf(oERevisionDocumentoItem.getCodigoSolicitud()+""), oERevisionDocumentoItem.getNombreDocumentoLaserFiche());
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
	*/
	
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
	
	/*
	public void visualizarDocumento(ERevisionDocumento oERevisionDocumentoItem) {
		if (oERevisionDocumentoItem != null) {
			UManejadorArchivo manejoArchivo = new UManejadorArchivo();
			EArchivo archivo = manejoArchivo.obtenerArchivo(1, String.valueOf(oERevisionDocumentoItem.getCodigoSolicitud()+""), oERevisionDocumentoItem.getNombreDocumentoLaserFiche());
			try {
				byte[] bytes = null; 
				bytes = archivo.getArchivoBinario();
				HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
				response.getOutputStream().write(bytes);
				response.getOutputStream().close();
				
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception e) {
	
			}
		}
	}
	*/
	
	public void obtenerTipoEnvio(){
		inicializarUsuarioReceptor();
		visualizarUsuarioReceptor();
	}
	
	public void visualizarUsuarioReceptor(){
		if(oERevisionSolicitudData.getCodigoTipoEnvio() == UTipoEnvio.PUBLICO){
			visualizarUsuarioReceptor = false;
		}else if(oERevisionSolicitudData.getCodigoTipoEnvio() == UTipoEnvio.PRIVADO){
			visualizarUsuarioReceptor = true;
		}
	}
	
	public void inicializarUsuarioReceptor(){
		oERevisionSolicitudData.setCodigoReceptor(0);
		oERevisionSolicitudData.setDescripcionReceptor("");
	}
	
	public void buscarUsuarioReceptor(){
		lstUsuarioReceptor = oUManejadorListaDesplegable.obtieneUsuarioPorAreaTipoEnvio(oERevisionSolicitudData.getCodigoAreaReceptor());
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarUsuarioReceptor').show();");
	}
	
	public void asignarUsuarioReceptor(){
		if(oEReceptorSelected != null){
			oERevisionSolicitudData.setCodigoReceptor(oEReceptorSelected.getCodigo2());
			oERevisionSolicitudData.setDescripcionReceptor(oEReceptorSelected.getDescripcion());
		}
	}
	
	public void obtenerPersonaRelacion(){
		oERevisionSolicitudData.setCodigoClientePersona(0);
		oERevisionSolicitudData.setCodigoTipoClientePersona(UTipoCliente.COD_SOCIO);
		oERevisionSolicitudData.setCodigoTipoDocumentoPersona("");
		oERevisionSolicitudData.setNumeroDocumentoPersona("");
		oERevisionSolicitudData.setNombrePersona("");
		
		deshabilitarPersonaRelacion();
		visualizarPersonaRelacion();
		/*
		if (oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.EXTERNO){
			deshabilitarNombrePersona = false;
			deshabilitarBuscarPersona = true;
			visualizarTipoDocumentoPersona = true;
			visualizarNumeroDocumentoPersona = true;
			visualizarNombrePersona = true;
			visualizarBuscarPersona = true;
		}else if (oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.ABACO){
			deshabilitarNombrePersona = true;
			deshabilitarBuscarPersona = false;
			visualizarTipoDocumentoPersona = false;
			visualizarNumeroDocumentoPersona = false;
			visualizarNombrePersona = true;
			visualizarBuscarPersona = true;
		}else{
			deshabilitarNombrePersona = true;
			deshabilitarBuscarPersona = true;
			visualizarTipoDocumentoPersona = false;
			visualizarNumeroDocumentoPersona = false;
			visualizarNombrePersona = false;
			visualizarBuscarPersona = false;
		}
		*/
	}
	
	public void deshabilitarPersonaRelacion(){
		if (oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.EXTERNO){
			deshabilitarTipoDocumentoPersona = false;
			deshabilitarNumeroDocumentoPersona = false;
			deshabilitarNombrePersona = false;
			deshabilitarBuscarPersona = true;
		}else if (oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.ABACO){
			deshabilitarTipoDocumentoPersona = true;
			deshabilitarNumeroDocumentoPersona = true;
			deshabilitarNombrePersona = true;
			deshabilitarBuscarPersona = false;
		}else{
			deshabilitarTipoDocumentoPersona = true;
			deshabilitarNumeroDocumentoPersona = true;
			deshabilitarNombrePersona = true;
			deshabilitarBuscarPersona = true;
		}
	}
	
	public void visualizarPersonaRelacion(){
		if (oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.EXTERNO){
			visualizarTipoDocumentoPersona = true;
			visualizarNumeroDocumentoPersona = true;
			visualizarNombrePersona = true;
			visualizarBuscarPersona = true;
		}else if (oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.ABACO){
			visualizarTipoDocumentoPersona = false;
			visualizarNumeroDocumentoPersona = false;
			visualizarNombrePersona = true;
			visualizarBuscarPersona = true;
		}else{
			visualizarTipoDocumentoPersona = false;
			visualizarNumeroDocumentoPersona = false;
			visualizarNombrePersona = false;
			visualizarBuscarPersona = false;
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
	
	public void obtenerMotivo() {
		if (oERevisionSolicitudData.getCodigoMotivo() == UMotivo.OTROS) {
			oERevisionSolicitudData.setDescripcionAdicionalMotivo("");
			visualizarDescripcionMotivo = true;
		}else {
			oERevisionSolicitudData.setDescripcionAdicionalMotivo("-");
			visualizarDescripcionMotivo = false;
		}
	}
	
	public void obtenerDigitalizacion() {
		if (indicadorDigitalizacion == UIndicadorDigitalizacion.SI) {
			oERevisionSolicitudData.setDescripcionMensajeDigitalizacion("");
			visualizarDescripcionMensajeDigitalizacion = true;
		}else {
			oERevisionSolicitudData.setDescripcionMensajeDigitalizacion("-");
			visualizarDescripcionMensajeDigitalizacion = false;
		}
	}
	
	public void obtenerTipoDocumento() {
		if (oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.DNI)){
			maxLgnNumeroDocumentoPersona = UMaximoTamanio.DNI_MAXLGN;
			oERevisionSolicitudData.setNumeroDocumentoPersona("");
			oERevisionSolicitudData.setNombrePersona("");
		}else if (oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.RUC)){
			maxLgnNumeroDocumentoPersona = UMaximoTamanio.RUC_MAXLGN;
			oERevisionSolicitudData.setNumeroDocumentoPersona("");
			oERevisionSolicitudData.setNombrePersona("");
		}
	}
	
	public void enviarCorreo() {
		ULectorDeParametros uLectorDeParametros = ULectorDeParametros.getInstancia();
		
        List<Object> lstParametrosContenido = new ArrayList<>();
        String indicadorProduccion = uLectorDeParametros.getValorParametro("indicador.produccion");
        String nombreUsuarioSiaf = oBOGeneral.buscarNombreUsuarioSiaf(oERevisionSolicitudData.getCodigoEmisor());
        String emailDestino = "";
        String descripcionEstado = "";
        
        if(indicadorProduccion.equals("true")){
            String email1 = oBOGeneral.buscarCorreoUsuario(oERevisionSolicitudData.getCodigoEmisor());
            String email2 = oBOGeneral.buscarCorreoJefeInmediato(nombreUsuarioSiaf);
            String email3 = "";
            
            if(codigoEstado == UEstado.PENDIENTEDEEVALUACION ||
    		codigoEstado == UEstado.AUTORIZADO || codigoEstado == UEstado.OBSERVADO  ||
            codigoEstado == UEstado.CERRADO || codigoEstado == UEstado.DESAPROBADO || 
            codigoEstado == UEstado.CANCELADO){
            	emailDestino = email1;
                if(!email2.equals("")){
                	emailDestino = emailDestino +","+ email2;
                }
            }
            
            if(codigoEstado == UEstado.PENDIENTEDEEVALUACION){
                if(oERevisionSolicitudData.getCodigoTipoEnvio() == UTipoEnvio.PUBLICO){
                    List<EGeneral> lstUsuarioReceptor = new ArrayList<EGeneral>();
                    lstUsuarioReceptor = oUManejadorListaDesplegable.obtieneUsuarioPorArea(UArea.LEGAL, UTipoEstadoUsuario.ACTIVO);
                    
            		for(int i=0;i<lstUsuarioReceptor.size();i++){
            			if(i==0){
            				email3 = oBOGeneral.buscarCorreoUsuario(lstUsuarioReceptor.get(i).getCodigo2());
            			}else{
            				email3 = email3 +","+ oBOGeneral.buscarCorreoUsuario(lstUsuarioReceptor.get(i).getCodigo2());
            			}
            			
            		}
                }else if(oERevisionSolicitudData.getCodigoTipoEnvio() == UTipoEnvio.PRIVADO){
                	email3 = oBOGeneral.buscarCorreoUsuario(oERevisionSolicitudData.getCodigoReceptor());
                }
                
            	if(emailDestino.equals("")){
            		emailDestino = email3;
            	}else{
            		emailDestino = emailDestino +","+ email3;
            	}
            	/*
                if(!email3.equals("")){
                	emailDestino = emailDestino +","+ email3;
                }
                */
            }
        }else if(indicadorProduccion.equals("false")){
        	String email1 = uLectorDeParametros.getValorParametro("correo.prueba");
        	String email2 = uLectorDeParametros.getValorParametro("correo.prueba2");
        	
            if(codigoEstado == UEstado.PENDIENTEDEEVALUACION ||
    		codigoEstado == UEstado.AUTORIZADO || codigoEstado == UEstado.OBSERVADO  ||
            codigoEstado == UEstado.CERRADO || codigoEstado == UEstado.DESAPROBADO || 
            codigoEstado == UEstado.CANCELADO){
            	emailDestino = email1;
            }
            
            if(codigoEstado == UEstado.PENDIENTEDEEVALUACION){
            	if(emailDestino.equals("")){
            		emailDestino = email2;
            	}else{
            		emailDestino = emailDestino +","+ email2;
            	}
            }
        }
		
        if(codigoEstado == UEstado.PENDIENTEDEEVALUACION){
        	descripcionEstado = UEstado.PENDIENTEDEEVALUACION_DESCEST;
        }else if(codigoEstado == UEstado.AUTORIZADO){
        	descripcionEstado = UEstado.AUTORIZADO_DESCEST;
        }else if(codigoEstado == UEstado.OBSERVADO){
        	descripcionEstado = UEstado.OBSERVADO_DESCEST;
        }else if(codigoEstado == UEstado.CERRADO){
        	descripcionEstado = UEstado.EVALUADO_DESCEST;
        }else if(codigoEstado == UEstado.DESAPROBADO){
        	descripcionEstado = UEstado.DESAPROBADO_DESCEST;
        }else if(codigoEstado == UEstado.CANCELADO){
        	descripcionEstado = UEstado.CANCELADO_DESCEST;
        }
        
        //String asunto = "La solicitud #" + oERevisionSolicitudData.getCodigoSolicitud() +" "+ oERevisionSolicitudData.getDescripcionAsunto() +" ha cambiado a estado "+ UFuncionesGenerales.convierteCadenaMayuscula(descripcionEstado);
        String asunto = "La solicitud #" + oERevisionSolicitudData.getCodigoSolicitud() +" "+ oERevisionSolicitudData.getDescripcionAsunto();
        
        lstParametrosContenido.add(oERevisionSolicitudData.getCodigoSolicitud()+"");
        lstParametrosContenido.add(oERevisionSolicitudData.getDescripcionAsunto());
        lstParametrosContenido.add(UFuncionesGenerales.convierteCadenaMayuscula(descripcionEstado));
        lstParametrosContenido.add(oEUsuario.getNombreCompleto());
        
        UManejadorCorreo uManejadorCorreo = new UManejadorCorreo();
        uManejadorCorreo.enviarCorreo(lstParametrosContenido, 
        		asunto, 
        		uLectorDeParametros.getValorParametro("correo.msg.registro_consulta.cuerpo"), 
        		emailDestino, "", "");
	}
	
	public void inicializar() {
		Date hoy = new Date();
		fechaOrigenSesion = hoy;
		
		deshabilitar = false;
		visualizar = true;
		
		deshabilitarTipoDocumentoPersona = true;
		deshabilitarNumeroDocumentoPersona = true;
		deshabilitarNombrePersona = true;
		deshabilitarBuscarPersona = true;
		/*
		deshabilitarNombrePersona = true;
		deshabilitarBuscarPersona = true;
		visualizarNombrePersona = false;
		visualizarBuscarPersona = false;
		visualizarDescripcionMensajeUltimo = false;
		
		codigoPersonaRelacion = UPersonaRelacion.NINGUNO;
		codigoTipoEnvio=UTipoEnvio.PUBLICO;
		codigoMotivo = UMotivo.OP1;
		*/
		codigoTipoClientePersonaBuscar = UTipoCliente.COD_SOCIO;
		indicadorDigitalizacion = UIndicadorDigitalizacion.NO;
		indicadorTemporal = UIndicadorTemporal.NO;
		indicadorGuardar = 0;
		indicadorSalir = 0;
	}

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public EOpcion getoEOpcionLoad() {
		return oEOpcionLoad;
	}

	public void setoEOpcionLoad(EOpcion oEOpcionLoad) {
		this.oEOpcionLoad = oEOpcionLoad;
	}
	
	public ERevisionSolicitud getoERevisionSolicitudData() {
		return oERevisionSolicitudData;
	}

	public void setoERevisionSolicitudData(
			ERevisionSolicitud oERevisionSolicitudData) {
		this.oERevisionSolicitudData = oERevisionSolicitudData;
	}
	
	public ERevisionMensaje getoERevisionMensajeSelected() {
		return oERevisionMensajeSelected;
	}

	public void setoERevisionMensajeSelected(
			ERevisionMensaje oERevisionMensajeSelected) {
		this.oERevisionMensajeSelected = oERevisionMensajeSelected;
	}

	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}

	public EGeneral getoEReceptorSelected() {
		return oEReceptorSelected;
	}

	public void setoEReceptorSelected(EGeneral oEReceptorSelected) {
		this.oEReceptorSelected = oEReceptorSelected;
	}
}
