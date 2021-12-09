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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

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
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hibernate.validator.Email;
import org.primefaces.context.RequestContext;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.ageneral.BOOperacion;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UCalificacion;
import com.abaco.negocio.util.UConstante.UCorreoEnvio;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoOperacionLevantamiento;
import com.abaco.negocio.util.UConstante.UFormatoAbaco;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorSolicitud;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMensajeValidacion;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.UNivel;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.USituacionGarantia;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UMensajeConfirmacion;
import com.abaco.negocio.util.UConstante.UMotivo;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UPlantillaEmail;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UTipoFirma;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.URegla;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UGeneradorSelectItem;
import com.abaco.negocio.util.ULectorDeParametros;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorCorreo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.servicio.laserfiche.Documento;
import com.laserfiche.repositoryaccess.Document;

@ManagedBean(name = "mbregistrooperacionlevantamientogarantia")
@ViewScoped
public class MBRegistroOperacionLevantamientoGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private EOperacionLevantamientoGarantia oEOperacionLevantamientoGarantiaLoad;
	//private EOperacionSolicitud oEOperacionSolicitudLoad;
	//private EOpcion oEOpcionLoad;
	private EGarantiaSolicitud oEGarantiaSolicitudLoad;
	
	//private EOperacionSolicitud oEOperacionSolicitudData;
	private ECliente oEClienteData;
	private EGarantia oEGarantiaData;
	private EGarantiaTramite oEGarantiaTramiteData;
	
	private EOperacionMensaje oEOperacionMensajeSelected;
	private EPersona oEPersonaSelected;
	private EGeneral oEReceptorSelected;
	
	private BOOperacion oBOOperacion;
	private BOGeneral oBOGeneral;
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EOperacionLevantamientoGarantiaMensaje> lstOperacionLevantamientoGarantiaMensaje;
	@Getter @Setter private List<EOperacionMensaje> lstOperacionMensaje;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	//@Getter @Setter private List<EGarantiaCreditoRelacionado> lstCreditoRelacionado;
	@Getter @Setter private List<EGarantiaCreditoRelacionado> lstCreditoVigenteRelacionado;
	@Getter @Setter private List<EGarantiaCreditoRelacionado> lstCreditoCanceladoRelacionado;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstEnvio;
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private List<EGeneral> lstAreaReceptor;
	@Getter @Setter private List<EGeneral> lstUsuarioReceptor;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoPrendaGarantia;
	@Getter @Setter private List<EGeneral> lstTipoMoneda;
	@Getter @Setter private List<EGeneral> lstTipoCiaSeguro;
	
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia;
	
	/* Variables Interfaz */
	
	//Datos de formulario General
	@Getter @Setter private String mensajeValidacion;
	@Getter @Setter private String mensajeConfirmacion;
	@Getter @Setter private String descripcionMensaje;
	@Getter @Setter private String descripcionMensajeSeleccion;
	@Getter @Setter private String descripcionDocumentoCarga;
	
	//Datos de formulario Garantia
	@Getter @Setter private int codigoDepartamentoGarantia;
	@Getter @Setter private int codigoProvinciaGarantia;
	@Getter @Setter private int codigoDistritoGarantia;
	
	//Panel Garantia
	@Getter @Setter private boolean visualizarFrmGarantiaVehicular;
	@Getter @Setter private boolean visualizarFrmGarantiaPredio;
	@Getter @Setter private boolean visualizarFrmGarantiaMaquinaria;
	@Getter @Setter private boolean deshabilitarFrmGarantia;
	
	//Dialog Envio Digitalizacion
	@Getter @Setter private boolean visualizarDescripcionMensajeDigitalizacion;
	
	//Dialog Desaprobar Solicitud
	@Getter @Setter private boolean visualizarDescripcionMotivo;
	
	//Indicadores
	@Getter @Setter private int indicadorDigitalizacion;
	private int indicadorTemporal;
	@Getter @Setter private int indicadorValidarBtnEnviar;
	
	//Control de Acciones
	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean deshabilitarBtnGrabar;
	@Getter @Setter private boolean deshabilitarBtnAdjuntar;
	
	@Getter @Setter private boolean visualizar;
	@Getter @Setter private boolean visualizarTblMensaje;
	@Getter @Setter private boolean visualizarPnlInforme;
	@Getter @Setter private boolean visualizarPnlDocumentoLevantamiento;
	@Getter @Setter private boolean visualizarPnlCreditoRelacionado;
	
	@Getter @Setter private boolean visualizarBtnSalir;
	@Getter @Setter private boolean visualizarBtnAdjuntar;
	@Getter @Setter private boolean visualizarBtnEnviar;
	@Getter @Setter private boolean visualizarBtnRechazar;
	@Getter @Setter private boolean visualizarBtnConfirmar;
	@Getter @Setter private boolean visualizarBtnLiberar;
	@Getter @Setter private boolean visualizarBtnEntregar;
	@Getter @Setter private boolean visualizarBtnArchivar;
	@Getter @Setter private boolean visualizarBtnGrabar;
	@Getter @Setter private boolean visualizarBtnEnProcesoLevantamiento;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento1;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento2;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento3;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento4;

	/* Variables Internas */
	private long correlativoSesion;
	private int codigoEstado;
	private int codigoEstadoDocumento;
	private boolean indicadorLiberarGarantia;
	
	private Date fechaOrigenSesion;
	private int accionExterna;

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	
	private String rutaBaseFormato;
	private String rutaBasePlantilla;
	
	//Adicional 
	@Getter @Setter private boolean deshabilitarGrabarDocumento;
	@Getter @Setter private boolean deshabilitarSolFirma;
	private EOperacionDocumento oEOperacionDocumento;
	@Getter @Setter private int codigoFirmaSiNo;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EAsignacionContratoGarantia> lstCreditoGarantia;
	@Getter @Setter private Double montoAcumuladoAsignadoCredito;    
	@Getter @Setter private Double montoAcumuladoSaldoCredito;  
	@Getter @Setter private Double montoAcumuladoCoberturado;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oEOperacionLevantamientoGarantiaLoad = new EOperacionLevantamientoGarantia();
		//this.oEOperacionSolicitudLoad = new EOperacionSolicitud();
		//this.oEOpcionLoad = new EOpcion();
		this.oEGarantiaSolicitudLoad = new EGarantiaSolicitud();
		
		//this.oEOperacionSolicitudData = new EOperacionSolicitud();
		this.oEClienteData = new ECliente();
		this.oEGarantiaData = new EGarantia();
		
		oEOperacionMensajeSelected = new EOperacionMensaje();
		oEPersonaSelected = new EPersona();
		oEReceptorSelected = new EGeneral();
		
		oBOOperacion = new BOOperacion();
		oBOGeneral = new BOGeneral();
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstOperacionLevantamientoGarantiaMensaje = new ArrayList<EOperacionLevantamientoGarantiaMensaje>();
		lstOperacionMensaje = new ArrayList<EOperacionMensaje>();
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		//lstCreditoRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
		lstCreditoVigenteRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
		lstCreditoCanceladoRelacionado = new ArrayList<EGarantiaCreditoRelacionado>();
		lstNivel = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstEnvio = new ArrayList<EGeneral>();
		lstAreaReceptor = new ArrayList<EGeneral>();
		lstUsuarioReceptor = new ArrayList<EGeneral>();
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoPrendaGarantia = new ArrayList<EGeneral>();
		lstTipoMoneda = new ArrayList<EGeneral>();
		lstTipoCiaSeguro = new ArrayList<EGeneral>();
		
		files = new ArrayList<UploadedFile>();
		
		//Adicional
		oEOperacionDocumento = new EOperacionDocumento();
		lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
		
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
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEClienteData = oBOCliente.buscarSocio(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getCodigoGarantia());
					
					deshabilitarFrmGarantia = true;
					visualizarPnlCreditoRelacionado = true;
					visualizarBtnSalir = true;
					visualizarBtnEnviar = true;

				}
			}else if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEClienteData = oBOCliente.buscarSocio(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getCodigoGarantia());
					oEGarantiaTramiteData = oBOGarantia.buscarGarantiaTramite(oEGarantiaSolicitudLoad.getCodigoGarantia());
					lstCreditoVigenteRelacionado = oBOGarantia.listarCreditoVigenteRelacionado(oEGarantiaSolicitudLoad.getCodigoGarantia());
					
					if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.SOLICITADO){
						visualizarBtnRechazar = true;
						visualizarBtnConfirmar = true;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.CONFIRMADO){
						visualizarPnlInforme = true;
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnLiberar = true;
						visualizarBtnEnProcesoLevantamiento = true;
						
						if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR){
							visualizarBtnGenerarDocumento1 = true;
						}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.MAQUINARIA){
							visualizarBtnGenerarDocumento2 = true;
						}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){							
							if(lstCreditoVigenteRelacionado.size() > 0){
								visualizarBtnGenerarDocumento4 = true;
							}else{
								visualizarBtnGenerarDocumento2 = true;
								visualizarBtnGenerarDocumento3 = true;
							}
						}
						indicadorValidarBtnEnviar = 1;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.OBSERVADO){
						visualizarPnlInforme = true;
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnEntregar = true;
						visualizarBtnArchivar = true;
						indicadorValidarBtnEnviar = 1;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.ENEVALUACION){
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnEnProcesoLevantamiento = true;
						visualizarBtnLiberar = true;
						indicadorValidarBtnEnviar = 0;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.LIBERADO){
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnGrabar = true;
					}
					
					deshabilitarFrmGarantia = true;
					
					//deshabilitarBtnGrabar = true;
					visualizarTblMensaje = true;
					visualizarPnlCreditoRelacionado = true;
					visualizarBtnSalir = true;
					visualizarBtnAdjuntar = true;
				}
			}else if(UAccionExterna.VER == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEClienteData = oBOCliente.buscarSocio(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getCodigoGarantia());
															
					deshabilitarFrmGarantia = true;
						
					visualizarTblMensaje = true;
					visualizarPnlDocumentoLevantamiento = true;
					visualizarPnlCreditoRelacionado = true;
					visualizarBtnSalir = true;
				}
			}
			
			if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR){
				visualizarFrmGarantiaVehicular = true;
			}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){
				visualizarFrmGarantiaPredio = true;
			}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.MAQUINARIA){
				visualizarFrmGarantiaMaquinaria = true;
			}
			
			listarDesplegable();
			listarMensaje();
			listarMensajeTemporal();
			listarDocumento();
			listarDocumentoTemporal();
			listarUbigeoGarantia();
			habilitarAccionEnviar();
			//filtrarDocumento();
			//Adicional
			listarCreditosAsociadosGarantia();
		}
	}
	
	public void obtenerEstado(int ind){
		if(validar()){
			if (ind == 1) {
				codigoEstado = UEstadoOperacionLevantamiento.SOLICITADO;
				indicadorLiberarGarantia = false;
				mensajeConfirmacion = UMensajeConfirmacion.MSJ_1;
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
			}else if(ind == 2){
				codigoEstado = UEstadoOperacionLevantamiento.RECHAZADO;
				indicadorLiberarGarantia = false;
				mensajeConfirmacion = UMensajeConfirmacion.MSJ_2;
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
			}else if(ind == 3){
				codigoEstado = UEstadoOperacionLevantamiento.CONFIRMADO;
				indicadorLiberarGarantia = false;
				if(validarLiberarGarantiaCreditoCancelado()){
					mensajeConfirmacion = UMensajeConfirmacion.MSJ_3;
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
				}else{
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
				}
			}else if(ind == 4){
				codigoEstado = UEstadoOperacionLevantamiento.LIBERADO;
				indicadorLiberarGarantia = true;
				if(validarLiberarGarantiaCreditoVigente()){
					mensajeConfirmacion = UMensajeConfirmacion.MSJ_4;
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
				}else{
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
				}
			}else if(ind == 5){
				codigoEstado = UEstadoOperacionLevantamiento.ENEVALUACION;
				indicadorLiberarGarantia = false;
				guardar();
			}else if(ind == 6){
				codigoEstado = UEstadoOperacionLevantamiento.OBSERVADO;
				indicadorLiberarGarantia = false;
				guardar();
			}else if(ind == 7){
				codigoEstado = UEstadoOperacionLevantamiento.ARCHIVADO;
				indicadorLiberarGarantia = false;
				guardar();
			}else if(ind == 8){
				codigoEstado = oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento();
				indicadorLiberarGarantia = false;
				guardar();
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void guardar() {
		obtenerEstadoDocumentoLevantamiento();
		EOperacionLevantamientoGarantia oEOperacionLevantamientoGarantia = new EOperacionLevantamientoGarantia();
		EGarantia oEGarantia = new EGarantia();
		
		oEGarantia = oEGarantiaData;
			
		oEOperacionLevantamientoGarantia.setCodigoCliente(oEGarantiaData.getCodigoCliente());
		oEOperacionLevantamientoGarantia.setCodigoServicio(oEGarantiaData.getCodigoServicio());
		oEOperacionLevantamientoGarantia.setCodigoGarantia(oEGarantiaData.getCodigoGarantia());
		oEOperacionLevantamientoGarantia.setCodigoMoneda(oEGarantiaData.getCodigoMoneda());
		oEOperacionLevantamientoGarantia.setCodigoEstadoSolicitud(codigoEstado);
		oEOperacionLevantamientoGarantia.setCodigoEstadoDocumento(codigoEstadoDocumento);
		oEOperacionLevantamientoGarantia.setDescripcionMensaje(descripcionMensaje);
		oEOperacionLevantamientoGarantia.setFechaRegistro(new Date());
		oEOperacionLevantamientoGarantia.setUsuarioRegistro(oEUsuario);
		
		oEGarantia.setFechaRegistro(oEOperacionLevantamientoGarantia.getFechaRegistro());
		oEGarantia.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.NUEVO == accionExterna){
			oEMensaje = oBOOperacion.agregarEvaluacionLevantamientoGarantia(oEOperacionLevantamientoGarantia);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarEvaluacionLevantamientoGarantia(oEOperacionLevantamientoGarantia, indicadorLiberarGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	public void obtenerEstadoDocumentoLevantamiento() {
		if(lstOperacionDocumento != null){
			for(int i=0;i<lstOperacionDocumento.size();i++){
				if(lstOperacionDocumento.get(i).getEstadoDocumento() == UEstado.SOLICITADOFIRMA){
					codigoEstadoDocumento = lstOperacionDocumento.get(i).getEstadoDocumento();
					break;
				}
			}
			for(int i=0;i<lstOperacionDocumento.size();i++){
				if(lstOperacionDocumento.get(i).getEstadoDocumento() == UEstado.DOCUMENTOFIRMADO){
					codigoEstadoDocumento = lstOperacionDocumento.get(i).getEstadoDocumento();
					break;
				}
			}
		}
		
	}

	public boolean validar() {
		boolean ind=true;
		mensajeValidacion = "";
        return ind;
	}
	
	public boolean validarLiberarGarantiaCreditoCancelado() {
		boolean ind=true;
		mensajeValidacion = "";
		
		lstCreditoCanceladoRelacionado = oBOGarantia.listarCreditoCanceladoRelacionado(oEGarantiaSolicitudLoad.getCodigoGarantia());
		
		if(lstCreditoCanceladoRelacionado !=null){
			if(lstCreditoCanceladoRelacionado.size() > 0){
				mensajeValidacion = UMensajeValidacion.MSJ_11;
				
				for(int i=0;i<lstCreditoCanceladoRelacionado.size();i++){
					mensajeValidacion = mensajeValidacion + "\n" +lstCreditoCanceladoRelacionado.get(i).getCodigoCliente()+"-"+lstCreditoCanceladoRelacionado.get(i).getCodigoServicio()+"-"+lstCreditoCanceladoRelacionado.get(i).getNumeroOperacion();
				}
				ind = false;
			}
		}
        return ind;
	}
	
	public boolean validarLiberarGarantiaCreditoVigente() {
		boolean ind=true;
		mensajeValidacion = "";
		
		lstCreditoVigenteRelacionado = oBOGarantia.listarCreditoVigenteRelacionado(oEGarantiaSolicitudLoad.getCodigoGarantia());
		
		if(lstCreditoVigenteRelacionado !=null){
			if(lstCreditoVigenteRelacionado.size() > 0){
				mensajeValidacion = UMensajeValidacion.MSJ_12;
				
				for(int i=0;i<lstCreditoVigenteRelacionado.size();i++){
					mensajeValidacion = mensajeValidacion + "\n" +lstCreditoVigenteRelacionado.get(i).getCodigoCliente()+"-"+lstCreditoVigenteRelacionado.get(i).getCodigoServicio()+"-"+lstCreditoVigenteRelacionado.get(i).getNumeroOperacion();
				}
				ind = false;
			}
		}
        return ind;
	}
	
	/*
	public boolean validarLiberarGarantiaCreditoRelacionado() {
		boolean ind=true;
		mensajeValidacion = "";
		
		lstCreditoRelacionado = oBOGarantia.listarCreditoRelacionado(1);
		
		if(lstCreditoRelacionado !=null){
			if(lstCreditoRelacionado.size() > 0){
				mensajeValidacion = UMensajeValidacion.MSJ_11;
				
				for(int i=0;i<lstCreditoRelacionado.size();i++){
					mensajeValidacion = mensajeValidacion + "\n" +lstCreditoRelacionado.get(i).getCodigoCliente()+"-"+lstCreditoRelacionado.get(i).getCodigoServicio()+"-"+lstCreditoRelacionado.get(i).getNumeroOperacion();
				}
				ind = false;
			}
		}
        return ind;
	}
	*/
	
	public void controlarSesion() {
		//enviarSesion();
	}
	
	/*
	public void enviarSesion() {
		if(UAccionExterna.EDITAR == accionExterna) {
		if(oEMensaje.getDescMensaje() == null || oEMensaje.getDescMensaje().length()==0){
			if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO) != null) {
				EOperacionSesion oEOperacionSesion = new EOperacionSesion();
				oEOperacionSesion.setCodigoSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
				oEOperacionSesion.setCorrelativoSesion(correlativoSesion);
				oEOperacionSesion.setFechaRegistro(new Date());
				oEOperacionSesion.setUsuarioRegistro(oEUsuario);
				oBOOperacion.modificarSesion(oEOperacionSesion);
			}
		}
		}
	}
	*/
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){;
			ruta = "ListaOperacionLevantamiento.xhtml";
		}else{
			ruta = "ListaOperacionLevantamientoOtros.xhtml";
		}
		
		//enviarSesion();
		
		if(UAccionExterna.EDITAR == accionExterna) {
			//oBOOperacion.liberarSolicitudSesion(oEOperacionSolicitudLoad.getCodigoSolicitud());
		}
		
		inicializar();
		/*Cerrar Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	public void listarDesplegable(){
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstMotivo = oUManejadorListaDesplegable.obtieneMotivo();
		lstEnvio = oUManejadorListaDesplegable.obtieneTipoEnvio(accionExterna, oEUsuario.getIdUsuario());
		lstAreaReceptor = oUManejadorListaDesplegable.obtieneAreaReceptor();
		lstTipoGarantia = oUManejadorListaDesplegable.obtieneTipoGarantia();
		lstTipoPrendaGarantia = oUManejadorListaDesplegable.obtieneTipoPrendaGarantia();
		lstTipoMoneda = oUManejadorListaDesplegable.obtieneTipoMoneda();
		lstTipoCiaSeguro = oUManejadorListaDesplegable.obtieneTipoCiaSeguro();
		//Adicional
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
	}
	
	public void listarMensaje() {
		lstOperacionLevantamientoGarantiaMensaje = oBOOperacion.listarMensajeLevantamientoGarantia(oEGarantiaData.getCodigoServicio(), oEGarantiaData.getCodigoGarantia(), oEGarantiaData.getCodigoMoneda());
		//lstOperacionMensaje = oBOOperacion.listarMensaje(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarMensajeTemporal() {
		//oEOperacionSolicitudData.setDescripcionMensaje(oBOOperacion.buscarMensajeTemporal(oEOperacionSolicitudLoad.getCodigoSolicitud()));
	}
	/*
	public void listarDocumento() {
		//lstOperacionDocumento = oBOOperacion.listarDocumento(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}*/
	
	public void listarDocumentoTemporal() {
		//lstDocumentoCarga = oBOOperacion.listarDocumentoTemporal(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	//*************************************//
	//Metodos para Documento
	//*************************************//
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
		//Adicional
		if(lstDocumentoCarga.size()==0 ||lstDocumentoCarga == null) deshabilitarGrabarDocumento = true;
	}
	
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
		//Adicional
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
					oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCarga.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCarga.add(oEDocumentoCarga);
			}
		}
		if(lstDocumentoCarga.size()>0) deshabilitarGrabarDocumento = false;
		descripcionDocumentoCarga = "";
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
		habilitarAccionEnviar();
	}
	/*
	public void generarCorrelativoDocumentoCarga() {
		
		int correlativoDocumento = 0;
		if(oEOperacionSolicitudLoad.getCodigoSolicitud() > 0){
			for(int i=0;i<lstOperacionDocumento.size();i++){
				correlativoDocumento = lstOperacionDocumento.get(i).getCodigoDocumento();
			}
		}
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
		
	}*/
	
	public void descargarDocumento(EOperacionDocumento oEOperacionDocumentoItem) {
		//Adicional
		if (oEOperacionDocumentoItem != null) {
			UManejadorArchivo manejoArchivo = new UManejadorArchivo();
			Documento archivo = manejoArchivo.obtenerDocumento(oEOperacionDocumentoItem.getCodigoDocumentoLaserFiche());
			if (archivo != null && archivo.getArchivoBinario() != null && archivo.getArchivoBinario().length > 0) {
				InputStream stream = new ByteArrayInputStream(archivo.getArchivoBinario());
				fileDownload = new DefaultStreamedContent(stream, "image/png", oEOperacionDocumentoItem.getNombreDocumento());
			} else {
				if (oEOperacionDocumentoItem.getDataDocumento() != null && oEOperacionDocumentoItem.getDataDocumento().length > 0) {
					UFuncionesGenerales.descargaArchivo(oEOperacionDocumentoItem.getNombreDocumento(), oEOperacionDocumentoItem.getDataDocumento());
				}
			}
		}
	}
	
	public void visualizarMensaje(String mensaje) {
		descripcionMensajeSeleccion = mensaje;
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleMensaje').show();");
	}
	
	public void filtrarDocumento() {
		if (oEOperacionMensajeSelected != null) {			
			lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
	        for (EOperacionDocumento oEOperacionDocumento: lstOperacionDocumento) {
	            if (oEOperacionDocumento.getCodigoMensaje() == oEOperacionMensajeSelected.getCodigoMensaje()) {
	            	lstOperacionDocumentoFiltro.add(oEOperacionDocumento);
	            }
	        }
		}
	}
	
	/*
	public void obtenerMotivo() {
		if (oEOperacionSolicitudData.getCodigoMotivo() == UMotivo.OTROS) {
			oEOperacionSolicitudData.setDescripcionAdicionalMotivo("");
			visualizarDescripcionMotivo = true;
		}else {
			oEOperacionSolicitudData.setDescripcionAdicionalMotivo("-");
			visualizarDescripcionMotivo = false;
		}
	}
	
	public void obtenerDigitalizacion() {
		if (indicadorDigitalizacion == UIndicadorDigitalizacion.SI) {
			oEOperacionSolicitudData.setDescripcionMensajeDigitalizacion("");
			visualizarDescripcionMensajeDigitalizacion = true;
		}else {
			oEOperacionSolicitudData.setDescripcionMensajeDigitalizacion("-");
			visualizarDescripcionMensajeDigitalizacion = false;
		}
	}	
	*/
	
	//*************************************//
	//Metodos para validar envio
	//*************************************//
	public void habilitarAccionEnviar(){
		if(indicadorValidarBtnEnviar == 1){
			//if(lstFirmante.size() >= URegla.LIBERAR_NUMERO_MIN_FIRMANTE & lstDocumentoCarga.size() >= URegla.LIBERAR_NUMERO_MIN_ADJUNTA){
			if(lstDocumentoCarga.size() >= URegla.LIBERAR_NUMERO_MIN_ADJUNTA){
				deshabilitarBtnGrabar = false;
			}else{
				deshabilitarBtnGrabar = true;
			}
		}else {
			deshabilitarBtnGrabar = false;
		}
	}
	/*
	public void habilitarAccionAdjuntar(){
		if(lstFirmante.size() >= URegla.LIBERAR_NUMERO_MIN_FIRMANTE){
			deshabilitarBtnAdjuntar = false;
		}else{
			deshabilitarBtnAdjuntar = true;
		}
	}
	*/
	
	//*************************************//
	//Metodos para Garantia
	//*************************************//
	public void listarUbigeoGarantia() {
		String codigoUbigeoGarantia = oEGarantiaData.getCodigoUbigeo()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 5) {
			codigoDepartamentoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 1));
			codigoProvinciaGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(1, 3));
			codigoDistritoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 6) {
			codigoDepartamentoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 2));
			codigoProvinciaGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(2, 4));
			codigoDistritoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(4, 6));
		}
		
		lstDepartamentoGarantia = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaGarantia = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia);
		lstDistritoGarantia = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia);
	}
	
	//*************************************//
	//Metodos para Generar Documento
	//*************************************//
	/*
	public void imprimir(int indicadorDocumento) {
		
		if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR){
			imprimirDocumento1();
		}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.MAQUINARIA || 
				oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){
			imprimirDocumento2(indicadorDocumento);
		}
	}
	*/
	
	public void imprimirDocumento1() {
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
        
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		
		String plantilla = "";
		String nombreArchivo = "";
		UNumeroLetra NumLetra = new UNumeroLetra();

		plantilla = "FormatoCancelacionyLevantamiento_001.docx";
		nombreArchivo = "CancelacionyLevantamiento_";
		
		String rutaPlantillaFichaSocio = rutaBasePlantilla + File.separator + plantilla;
		String codigoSocio = oEClienteData.getCodigoCliente()+"";
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantillaFichaSocio);
		
		String placa = (oEGarantiaData.getPlaca() != null ? oEGarantiaData.getPlaca():"");
		String marca = (oEGarantiaData.getMarca() != null ? oEGarantiaData.getMarca():"");
		String modelo = (oEGarantiaData.getModelo() != null ? oEGarantiaData.getModelo():"");
		String motor = (oEGarantiaData.getUbicacion2() != null ? oEGarantiaData.getUbicacion2():"");
		String serie = (oEGarantiaData.getUbicacion1() != null ? oEGarantiaData.getUbicacion1():"");
		String montoLetra = NumLetra.convertir(oEGarantiaData.getMontoGarantia()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String abreviacionMoneda = (oEGarantiaData.getAbreviacionMoneda() != null ? oEGarantiaData.getAbreviacionMoneda():"");
		String monto = abreviacionMoneda +" "+ UFuncionesGenerales.convertirEnteroACadenaDecimal(oEGarantiaData.getMontoGarantia());
		//String monto = "("+ oEGarantiaData.getAbreviacionMoneda() +" "+ String.format("%,.2f", oEGarantiaData.getMontoGarantizado()) +")";
		String nombreSocio = (oEClienteData.getNombreLargo() != null ? oEClienteData.getNombreLargo():"");
		String documentoSocio = (oEClienteData.getDocumento() != null ? oEClienteData.getDocumento():"");
		String fechaInscripcion = (UFuncionesGenerales.convertirFechaACadena(oEGarantiaTramiteData.getFechaInscripcion(), "dd 'de' MMMM 'de' yyyy"));
		String fichaInscripcion = (oEGarantiaTramiteData.getFichaInscripcion() != null ? oEGarantiaTramiteData.getFichaInscripcion():"");
		
		String sociedadSocio = "";
		String tipoDocumentoSocio = "";
		if(oEClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
			sociedadSocio = "a favor ";
			tipoDocumentoSocio = "RUC ";
		}else{
			tipoDocumentoSocio = "DNI ";
			sociedadSocio = "a favor ";
			if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
			if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
				sociedadSocio = "a favor de la sociedad conyugal conformada por el señor(a) ";
			}
			}
		}
		
		String nombreConyugue = "";
		String documentoConyugue = "";
		if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
		if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
			nombreConyugue = "y por la señora "+ oEClienteData.getNombreConyugue() +",";
			documentoConyugue = "identificada con DNI "+ oEClienteData.getDocumentoConyugue() +",";
		}
		}
		
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@001", placa);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@002", marca);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@003", modelo);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@004", motor);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@005", serie);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", montoLetra);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", monto);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@008", UFuncionesGenerales.convertirFechaACadena(new Date(), "dd 'de' MMMM 'de' yyyy"));
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@009", sociedadSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@010", tipoDocumentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@011", nombreSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@012", documentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@013", nombreConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@014", documentoConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@015", fechaInscripcion);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@016", fichaInscripcion);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".pdf");

		String rutaWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaBaseFormato + nombreArchivoWord);
		String rutaPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		//UManejadorArchivo.conviertirArchivoAPDF(rutaWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaWordGenerado);
		//UFuncionesGenerales.borrarArchivo(rutaPdfGenerado);
	}
	
	public void imprimirDocumento2(int indicadorDocumento) {
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
        
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		
		String plantilla = "";
		String nombreArchivo = "";
		UNumeroLetra NumLetra = new UNumeroLetra();

		if(indicadorDocumento == 1){
			plantilla = "FormatoCancelacionyLevantamiento_002.docx";
		}else if(indicadorDocumento == 2){
			plantilla = "FormatoCancelacionyLevantamiento_003.docx";
		}else if(indicadorDocumento == 3){
			plantilla = "FormatoCancelacionyLevantamiento_004.docx";
		}
		
		nombreArchivo = "CancelacionyLevantamiento_";
		
		String rutaPlantillaFichaSocio = rutaBasePlantilla + File.separator + plantilla;
		String codigoSocio = oEClienteData.getCodigoCliente()+"";
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantillaFichaSocio);
		
		String montoLetra = NumLetra.convertir(oEGarantiaData.getMontoGarantia()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String abreviacionMoneda = (oEGarantiaData.getAbreviacionMoneda() != null ? oEGarantiaData.getAbreviacionMoneda():"");
		String monto = abreviacionMoneda +" "+ UFuncionesGenerales.convertirEnteroACadenaDecimal(oEGarantiaData.getMontoGarantia());
		String nombreSocio = (oEClienteData.getNombreLargo() != null ? oEClienteData.getNombreLargo():"");
		String documentoSocio = (oEClienteData.getDocumento() != null ? oEClienteData.getDocumento():"");
		String fechaInscripcion = (UFuncionesGenerales.convertirFechaACadena(oEGarantiaTramiteData.getFechaInscripcion(), "dd 'de' MMMM 'de' yyyy"));
		String fichaInscripcion = (oEGarantiaTramiteData.getFichaInscripcion() != null ? oEGarantiaTramiteData.getFichaInscripcion():"");
		
		String sociedadSocio = "";
		String tipoDocumentoSocio = "";
		if(oEClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
			sociedadSocio = "a favor ";
			tipoDocumentoSocio = "RUC ";
		}else{
			tipoDocumentoSocio = "DNI ";
			sociedadSocio = "a favor ";
			if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
				if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
					sociedadSocio = "a favor de la sociedad conyugal conformada por el señor(a) ";
				}
			}
		}
		
		String nombreConyugue = "";
		String documentoConyugue = "";
		if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
			if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
				nombreConyugue = "y por la señora "+ oEClienteData.getNombreConyugue() +",";
				documentoConyugue = "identificada con DNI "+ oEClienteData.getDocumentoConyugue() +",";
			}
		}
		
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@001", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@002", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@003", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@004", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@005", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", montoLetra);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", monto);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@008", UFuncionesGenerales.convertirFechaACadena(new Date(), "dd 'de' MMMM 'de' yyyy"));
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@009", sociedadSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@010", tipoDocumentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@011", nombreSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@012", documentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@013", nombreConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@014", documentoConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@015", fechaInscripcion);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@016", fichaInscripcion);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".pdf");
		
		String rutaArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaArchivoWord);
		String rutaPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		//UManejadorArchivo.conviertirArchivoAPDF(rutaWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaWordGenerado);
		//UFuncionesGenerales.borrarArchivo(rutaPdfGenerado);
	}
	
	public void enviarCorreo() {
	}
	
	public void inicializar() {
		Date hoy = new Date();
		fechaOrigenSesion = hoy;
		codigoEstadoDocumento = 0;
		
		indicadorDigitalizacion = UIndicadorDigitalizacion.NO;
		indicadorTemporal = UIndicadorTemporal.NO;
		
		deshabilitar = true;
		deshabilitarBtnGrabar = true;
		deshabilitarBtnAdjuntar = true;
		
		visualizar = false;
		visualizarTblMensaje = false;
		visualizarPnlInforme = false;
		visualizarPnlDocumentoLevantamiento = false;
		visualizarPnlCreditoRelacionado = false;

		visualizarBtnSalir = false;
		visualizarBtnAdjuntar = false;
		visualizarBtnEnviar = false;
		visualizarBtnRechazar = false;
		visualizarBtnConfirmar = false;
		visualizarBtnLiberar = false;
		visualizarBtnEntregar = false;
		visualizarBtnArchivar = false;
		visualizarBtnGrabar = false;
		visualizarBtnGenerarDocumento1 = false;
		visualizarBtnGenerarDocumento2 = false;
		visualizarBtnGenerarDocumento3 = false;
		visualizarBtnGenerarDocumento4 = false;
		//Adicional
		deshabilitarGrabarDocumento = true;
		deshabilitarSolFirma = false;
	}
	
	/***********************Adicional************************/
	public void generarCorrelativoDocumentoCarga() {
		int correlativoDocumento = 0;
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
	}
	
	//*************************************//
	//Metodos para documento de carga
    //*************************************//

	public void grabarDocumento(){
		generarCorrelativoDocumentoCarga();
		List<EDocumentoCarga> elstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		elstDocumentoCarga = lstDocumentoCarga;
		EGarantia oEGarantia = new EGarantia();
		oEGarantia = oEGarantiaData;
		
		oEGarantia.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia))));	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
		oEGarantia.setAreaEmisora(UArea.LEGAL_DESC);
		if(lstOperacionDocumento != null){
			if(lstOperacionDocumento.size()>0){
				oEGarantia.setLstOperacionDocumento(lstOperacionDocumento);
			}	
		}
		

		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.agregarDocumentoGarantia(oEGarantia, elstDocumentoCarga);	
		}
		deshabilitarGrabarDocumento = true;
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		
		
	}
	
	public void listarDocumento() {
		EGarantia eGarantia = new EGarantia();
		eGarantia = oEGarantiaData;
		eGarantia.setUsuarioRegistro(oEUsuario);
		lstOperacionDocumento = oBOOperacion.listarDocumentoGarantia(eGarantia,1);
		lstOperacionDocumentoFiltro = lstOperacionDocumento.stream()
				.filter(x -> !x.getAreaReceptora().equals("NOTARIA"))
				.collect(Collectors.toList());
	}
	
	public void visualizarObservacionFirma(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			if(oEOperacionDocumentoItem.getEstadoDocumento() == 56){
				deshabilitarSolFirma = true;
			}
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgSolicitudFirma').show();");
		}
		
	}
	
	public void verificarFirma(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			if(oEOperacionDocumentoItem.getFirmaDocumento().equals("SI")) codigoFirmaSiNo = 1;		
			else codigoFirmaSiNo = 2;
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgFirma').show();");
		}
	}
	
    public void guardarSolicitudFirma(){
		
		RequestContext.getCurrentInstance().execute("PF('dlgSolicitudFirma').hide();");
		
		EGarantia oEGarantia = new EGarantia();
		oEGarantia = oEGarantiaData;
	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
		oEGarantia.setAreaEmisora(UArea.LEGAL_DESC);
		oEGarantia.setAreaReceptora(UArea.NEGOCIOS_DESC);
		oEGarantia.setEstadoDocumento(UEstado.SOLICITADOFIRMA);
		oEGarantia.setFirmaDocumento("NO");
		oEGarantia.setOperacionDocumento(oEOperacionDocumento);
		oEGarantia.setObservacionDocumento(oEOperacionDocumento.getObervacionDocumento());
		/*if(lstOperacionDocumento != null){
			if(lstOperacionDocumento.size()>0){
				oEGarantia.setLstOperacionDocumento(lstOperacionDocumento);
			}	
		}*/
		

		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarDocumentoGarantia(oEGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	
	}
    
    public void procesarFirma(){
		RequestContext.getCurrentInstance().execute("PF('dlgFirma').hide();");
		EOperacionDocumento eoperacionDocumento = new EOperacionDocumento();
		
		eoperacionDocumento = oEOperacionDocumento;
		if(codigoFirmaSiNo == 1){
			eoperacionDocumento.setFirmaDocumento("SI");
			eoperacionDocumento.setEstadoDocumento(UEstado.DOCUMENTOFIRMADO);
		}else{
			eoperacionDocumento.setFirmaDocumento("NO");
			eoperacionDocumento.setEstadoDocumento(UEstado.PENDIENTEFIRMA);
		}
		
		
		
		eoperacionDocumento.setUsuarioRegistro(oEUsuario);
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarFirmaDocumentoGarantia(eoperacionDocumento);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		
		
	}
    
    
    public void actualizarDatoasAjax(){
		listarDocumento();
	}
    
    public void listarCreditosAsociadosGarantia(){
		double montoAcumAsignadoCredito= 0;
		double montoAcumSaldoCredito = 0;
		double montoAcumCoberturado = 0;
		lstCreditoGarantia = oBOGarantia.listarCreditosAsociadosGarantia(oEGarantiaData.getCodigoGarantia());
		for(int i=0;i<lstCreditoGarantia.size();i++){
			montoAcumAsignadoCredito += lstCreditoGarantia.get(i).getMontoImporteCubierto();
			montoAcumSaldoCredito += lstCreditoGarantia.get(i).getMontoSaldoCredito();
		}
		
		montoAcumuladoAsignadoCredito=montoAcumAsignadoCredito;
		montoAcumuladoSaldoCredito = montoAcumSaldoCredito;
		
		for(int j=0;j<lstCreditoGarantia.size();j++){
			//Calculo de Monto Coberturado y % Coberturado
			double montoCoberturado = 0;
			double porcentajeCoberturado = 0;
			
			if(montoAcumuladoSaldoCredito != 0){
				if(oEGarantiaData.getCodigoMoneda() == lstCreditoGarantia.get(j).getCodigoMoneda()){
					montoCoberturado = (lstCreditoGarantia.get(j).getMontoSaldoCredito()/montoAcumuladoSaldoCredito) * oEGarantiaData.getMontoVariable1();
				}else{
					if(oEGarantiaData.getCodigoMoneda() == UMoneda.COD_SOLES && lstCreditoGarantia.get(j).getCodigoMoneda() == UMoneda.COD_DOLARES){
						montoCoberturado = (lstCreditoGarantia.get(j).getMontoSaldoCredito()/montoAcumuladoSaldoCredito) * (oEGarantiaData.getMontoVariable1()*4.1);
					}else if(oEGarantiaData.getCodigoMoneda() == UMoneda.COD_DOLARES && lstCreditoGarantia.get(j).getCodigoMoneda() == UMoneda.COD_SOLES){
						montoCoberturado = (lstCreditoGarantia.get(j).getMontoSaldoCredito()/montoAcumuladoSaldoCredito) * (oEGarantiaData.getMontoVariable1()*4.1);
					}
				}
			}
			
			
			
			lstCreditoGarantia.get(j).setMontoCoberturado(montoCoberturado);
			
			if(lstCreditoGarantia.get(j).getMontoSaldoCredito() != 0){
				porcentajeCoberturado = (lstCreditoGarantia.get(j).getMontoCoberturado() / lstCreditoGarantia.get(j).getMontoSaldoCredito());
			}
			
			lstCreditoGarantia.get(j).setPorcentajeCoberturado(porcentajeCoberturado);
			
			montoAcumCoberturado += lstCreditoGarantia.get(j).getMontoCoberturado();
			
		}
		
		montoAcumuladoCoberturado = montoAcumCoberturado;
		
		
	}

	

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
	
	public EGarantiaSolicitud getoEGarantiaSolicitudLoad() {
		return oEGarantiaSolicitudLoad;
	}

	public void setoEGarantiaSolicitudLoad(
			EGarantiaSolicitud oEGarantiaSolicitudLoad) {
		this.oEGarantiaSolicitudLoad = oEGarantiaSolicitudLoad;
	}
	
	public ECliente getoEClienteData() {
		return oEClienteData;
	}

	public void setoEClienteData(ECliente oEClienteData) {
		this.oEClienteData = oEClienteData;
	}
	
	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}

	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}
	
	public EOperacionMensaje getoEOperacionMensajeSelected() {
		return oEOperacionMensajeSelected;
	}

	public void setoEOperacionMensajeSelected(
			EOperacionMensaje oEOperacionMensajeSelected) {
		this.oEOperacionMensajeSelected = oEOperacionMensajeSelected;
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

	//Adicional
	public EOperacionDocumento getoEOperacionDocumento() {
		return oEOperacionDocumento;
	}

	public void setoEOperacionDocumento(EOperacionDocumento oEOperacionDocumento) {
		this.oEOperacionDocumento = oEOperacionDocumento;
	}
	
	
	
	
	
}
