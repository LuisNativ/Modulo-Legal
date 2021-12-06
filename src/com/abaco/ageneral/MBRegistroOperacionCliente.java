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

import lombok.Data;
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

import com.abaco.ageneral.BORevision;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UEstadoLegal;
import com.abaco.negocio.util.UConstante.UEstadoOperacionCliente;
import com.abaco.negocio.util.UConstante.UMaximoTamanio;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoClienteSolicitudCredito;
import com.abaco.negocio.util.UConstante.UTipoPersona;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UFlagResultado;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorSolicitud;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMensajeConfirmacion;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UConstante.UMotivo;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UPlantillaEmail;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UTipoFirma;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UTipoPersonaGeneral;
import com.abaco.negocio.util.UConstante.UTipoPersonaJuridica;
import com.abaco.negocio.util.UConstante.UTipoSuscripcionPago;
import com.abaco.negocio.util.UConstante.UTipoValorSuscripcion;
import com.abaco.negocio.util.UConstante.UUbicacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorCorreo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mbregistrooperacioncliente")
@ViewScoped
public class MBRegistroOperacionCliente implements Serializable {
	private static final long serialVersionUID = 1L;
	private EUsuario oEUsuario;
	private EMensaje oEMensaje;
	private EOperacionCliente oEOperacionClienteLoad;
	
	private EOperacionCliente oEOperacionClienteData;
	private EInformeLegalAdicional oEInformeLegalAdicionalData;
	private ECliente oEClienteData;
	private EClienteConstitucionEmpresa oEClienteConstitucionEmpresaData;
	private ERepresentanteLegal oERepresentanteLegalData;
	private EFacultadPoder oEFacultadPoderData;
	private EDeudor oEDeudorData;
	private ESuscripcion oESuscripcionData;
	private ETercero oETerceroData;
	
	private EOperacionMensaje oEOperacionMensajeSelected;
	private ERepresentanteLegal oERepresentanteLegalSelected;
	private ERepresentanteLegal oERepresentanteLegalRelacionSelected;
	private ERepresentanteLegal oERL1Selected;
	private ERepresentanteLegal oERL2Selected;
	private ERepresentanteLegal oERL3Selected;
	private ERepresentanteLegal oERL4Selected;
	private ERepresentanteLegal oERL5Selected;
	private EServicio oEServicioSelected;
	private EGeneral oETerceroSelected;
	private EGeneral oECIIUSelected;
	private EGeneral oECargoLaboralSelected;
	
	private BOOperacion oBOOperacion;
	private BOGeneral oBOGeneral;
	private BOCliente oBOCliente;
	private BOSolicitudCredito oBOSolicitudCredito;
	private BORepresentanteLegal oBORepresentanteLegal;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EDeudor> lstDeudorRecycle;
	
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private List<EOperacionClienteDocumento> lstOperacionClienteDocumento;
	@Getter @Setter private List<ERepresentanteLegal> lstRepresentanteLegal;
	@Getter @Setter private List<ERepresentanteLegal> lstRepresentanteLegalRelacion;
	@Getter @Setter private List<EFacultadPoder> lstFacultadPoder;
	@Getter @Setter private List<ERepresentanteLegal> lstRL1;
	@Getter @Setter private List<ERepresentanteLegal> lstRL2;
	@Getter @Setter private List<ERepresentanteLegal> lstRL3;
	@Getter @Setter private List<ERepresentanteLegal> lstRL4;
	@Getter @Setter private List<ERepresentanteLegal> lstRL5;
	@Getter @Setter private List<EDeudor> lstDeudor;
	@Getter @Setter private List<ESuscripcion> lstSuscripcion;
	@Getter @Setter private List<EGeneral> lstEstado;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstDOI;
	@Getter @Setter private List<EGeneral> lstDOIContratanteFiltro;
	@Getter @Setter private List<EGeneral> lstDOIRepresentanteFiltro;
	@Getter @Setter private List<EGeneral> lstSexo;
	@Getter @Setter private List<EGeneral> lstTipoPersona;
	@Getter @Setter private List<EGeneral> lstTipoPersonaRepresentanteFiltro;
	@Getter @Setter private List<EGeneral> lstEstadoCivil;
	@Getter @Setter private List<EGeneral> lstPais;
	@Getter @Setter private List<EGeneral> lstNivelInstruccion;
	@Getter @Setter private List<EGeneral> lstProfesion;
	@Getter @Setter private List<EGeneral> lstFacultad;
	@Getter @Setter private List<EGeneral> lstOtorgamiento;
	@Getter @Setter private List<EServicio> lstServicio;
	@Getter @Setter private List<EGeneral> lstTipoRelacion;
	@Getter @Setter private List<EGeneral> lstTercero;
	@Getter @Setter private List<EGeneral> lstTipoPago;
	@Getter @Setter private List<EGeneral> lstFormaPago;
	@Getter @Setter private List<EGeneral> lstEstadoDeudor;
	@Getter @Setter private List<EGeneral> lstEstadoDeudorFiltro;
	@Getter @Setter private List<EGeneral> lstCIIU;
	@Getter @Setter private List<EGeneral> lstTipoPersonaJuridica;
	@Getter @Setter private List<EGeneral> lstSuscripcionPago;
	@Getter @Setter private List<EGeneral> lstSuscripcionPagoFiltro;
	@Getter @Setter private List<EGeneral> lstFacultadOperar;
	@Getter @Setter private List<EGeneral> lstTipoDuracionPartida;
	@Getter @Setter private List<EGeneral> lstCargoLaboral;
	@Getter @Setter private List<EGeneral> lstTipoNumeracionEstatuto;
	
	@Getter @Setter private List<EGeneral> lstDepartamentoReal;
	@Getter @Setter private List<EGeneral> lstProvinciaReal;
	@Getter @Setter private List<EGeneral> lstDistritoReal;
	@Getter @Setter private List<EGeneral> lstDepartamentoContractual;
	@Getter @Setter private List<EGeneral> lstProvinciaContractual;
	@Getter @Setter private List<EGeneral> lstDistritoContractual;
	@Getter @Setter private List<EGeneral> lstDepartamentoComercial;
	@Getter @Setter private List<EGeneral> lstProvinciaComercial;
	@Getter @Setter private List<EGeneral> lstDistritoComercial;
	@Getter @Setter private List<EGeneral> lstDepartamentoDomiciliaria;
	@Getter @Setter private List<EGeneral> lstProvinciaDomiciliaria;
	@Getter @Setter private List<EGeneral> lstDistritoDomiciliaria;
	@Getter @Setter private List<EGeneral> lstDepartamentoCorrespondencia;
	@Getter @Setter private List<EGeneral> lstProvinciaCorrespondencia;
	@Getter @Setter private List<EGeneral> lstDistritoCorrespondencia;
	@Getter @Setter private List<EGeneral> lstDepartamentoDeudor;
	@Getter @Setter private List<EGeneral> lstProvinciaDeudor;
	@Getter @Setter private List<EGeneral> lstDistritoDeudor;
	@Getter @Setter private List<EGeneral> lstDepartamentoPostalDeudor;
	@Getter @Setter private List<EGeneral> lstProvinciaPostalDeudor;
	@Getter @Setter private List<EGeneral> lstDistritoPostalDeudor;
	
	/* Variables Interfaz */
	
	//Datos de formulario General
	@Getter @Setter private String mensajeValidacion;
	@Getter @Setter private String mensajeConfirmacion;
	@Getter @Setter private String descripcionDocumentoCarga;
	@Getter @Setter private String nombreBuscar;
	@Getter @Setter private Date fechaMaximo;
	@Getter @Setter private int maxLgnNumeroDocumentoContratante;
	@Getter @Setter private int maxLgnNumeroDocumentoRepresentante;
	
	//Datos de formulario Contratante
	@Getter @Setter private int codigoDepartamentoReal;
	@Getter @Setter private int codigoProvinciaReal;
	@Getter @Setter private int codigoDistritoReal;
	@Getter @Setter private int codigoDepartamentoContractual;
	@Getter @Setter private int codigoProvinciaContractual;
	@Getter @Setter private int codigoDistritoContractual;

	//Datos de formulario Deudor
	@Getter @Setter private String codigoTipoDocumentoDeudor;
	@Getter @Setter private String codigoCIIUDeudor;
	@Getter @Setter private int codigoTipoPagoDeudor;
	@Getter @Setter private int codigoFormaPagoDeudor;
	@Getter @Setter private String codigoEstadoDeudor;
	@Getter @Setter private int codigoDepartamentoDeudor;
	@Getter @Setter private int codigoProvinciaDeudor;
	@Getter @Setter private int codigoDistritoDeudor;
	@Getter @Setter private int codigoDepartamentoPostalDeudor;
	@Getter @Setter private int codigoProvinciaPostalDeudor;
	@Getter @Setter private int codigoDistritoPostalDeudor;
	
	//Panel Informe
	@Getter @Setter private boolean deshabilitarObservacionLegal;
	@Getter @Setter private boolean deshabilitarObservacionNegocio;
	@Getter @Setter private boolean deshabilitarEstado;
	@Getter @Setter private boolean visualizarEstado1;
	@Getter @Setter private boolean visualizarEstado2;
	
	//Panel Contratante
	@Getter @Setter private boolean deshabilitarFrmContratante;
	@Getter @Setter private boolean visualizarFrmContratantePN;
	@Getter @Setter private boolean visualizarFrmContratantePJ;
	@Getter @Setter private boolean visualizarObservacionAval;
	@Getter @Setter private boolean visualizarObservacionGrabarBien;
	@Getter @Setter private boolean visualizarFrmSuscripcion;
	@Getter @Setter private boolean visualizarFrmPatrimonio;
	@Getter @Setter private boolean visualizarDescripcionAporte;
	@Getter @Setter private boolean visualizarLblAccionista;
	@Getter @Setter private boolean visualizarLblParticipacionista;
	
	//Panel Representante Legal Multiple
	@Getter @Setter private boolean visualizarTblRepresentante1;
	@Getter @Setter private boolean visualizarTblRepresentante2;
	@Getter @Setter private boolean visualizarTblRepresentante3;
	@Getter @Setter private boolean visualizarTblRepresentante4;
	@Getter @Setter private boolean visualizarTblRepresentante5;
	
	//Dialog Representante Legal
	@Getter @Setter private int indicadorTipoPersonaRepresentante;
	@Getter @Setter private boolean deshabilitarTipoDocumentoRepresentante;
	@Getter @Setter private boolean deshabilitarNumeroDocumentoRepresentante;
	@Getter @Setter private boolean visualizarFrmRepresentantePN;
	@Getter @Setter private boolean visualizarFrmRepresentantePJ;
	@Getter @Setter private boolean visualizarTabRepresentante1;
	@Getter @Setter private boolean visualizarTabRepresentante2;
	@Getter @Setter private boolean visualizarTabRepresentante3;
	@Getter @Setter private boolean visualizarTabRepresentante4;
	
	//Dialog FacultadPoder
	@Getter @Setter private boolean visualizarFrmRepresentanteRelacion;
	
	//Dialog Suscripcion
	@Getter @Setter private int numeroSuscripcionMaximo;
	
	//Indicadores
	@Getter @Setter private int indicadorTabSeleccion;
	@Getter @Setter private int indicadorTabSeleccionRepresentante;
	@Getter @Setter private int indicadorTblSeleccionRepresentante;
	
	//Control de Acciones
	@Getter @Setter private boolean deshabilitarFrmDeudor;
	@Getter @Setter private boolean visualizarPnlContratante;
	@Getter @Setter private boolean visualizarPnlRepresentanteLegal;
	@Getter @Setter private boolean visualizarPnlDeudor;
	
	@Getter @Setter private boolean minimizarPnlRepresentanteLegal;
	@Getter @Setter private boolean minimizarPnlDeudor;
	@Getter @Setter private boolean minimizarPnlDocumento;
	
	@Getter @Setter private boolean visualizarBtnSalir;
	@Getter @Setter private boolean visualizarBtnGrabar;
	@Getter @Setter private boolean visualizarBtnAdjuntar;
	
	@Getter @Setter private boolean visualizarBtnAgregarSuscripcion;
	@Getter @Setter private boolean visualizarBtnModificarSuscripcion;
	@Getter @Setter private boolean visualizarBtnEliminarSuscripcion;
	@Getter @Setter private boolean visualizarBtnGrabarSuscripcion;
	
	@Getter @Setter private boolean visualizarBtnVisualizarRepresentanteLegal;
	@Getter @Setter private boolean visualizarBtnAgregarRepresentanteLegal;
	@Getter @Setter private boolean visualizarBtnModificarRepresentanteLegal;
	@Getter @Setter private boolean visualizarBtnEliminarRepresentanteLegal;
	@Getter @Setter private boolean visualizarBtnGrabarRepresentanteLegal;
	
	@Getter @Setter private boolean visualizarBtnVisualizarFacultad;
	@Getter @Setter private boolean visualizarBtnAgregarFacultad;
	@Getter @Setter private boolean visualizarBtnEliminarFacultad;
	@Getter @Setter private boolean visualizarBtnGrabarFacultad;
	
	@Getter @Setter private boolean visualizarBtnVisualizarDeudor;
	@Getter @Setter private boolean visualizarBtnAgregarDeudor;
	@Getter @Setter private boolean visualizarBtnModificarDeudor;
	@Getter @Setter private boolean visualizarBtnEliminarDeudor;
	@Getter @Setter private boolean visualizarBtnGrabarDeudor;
	
	/**********************/
	/* Variables Internas */
	/**********************/
	@Getter @Setter private int codigoEstado;
	
	private int accionExterna;
	@Getter @Setter private int accionInternaRepresenanteLegal;
	@Getter @Setter private int accionInternaFacultadPoder;
	@Getter @Setter private int accionInternaDeudor;
	@Getter @Setter private int accionInternaSuscripcion;

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	
	//Dialog Buscar Notario (Socio y/o Tercero)
	@Getter @Setter private List<ETercero> lstNotario;
	@Getter @Setter private int codigoBuscarPersona;
	@Getter @Setter private String descripcionBuscarPersona;
	@Getter @Setter private boolean visualizarFrmNotarioPN;
	private int indicadorDialogNotario;
	private ETercero oENotarioSelected;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oEOperacionClienteLoad = new EOperacionCliente();
		
		this.oEOperacionClienteData = new EOperacionCliente();
		this.oEInformeLegalAdicionalData = new EInformeLegalAdicional();
		this.oEClienteData = new ECliente();
		this.oEClienteConstitucionEmpresaData = new EClienteConstitucionEmpresa();
		this.oERepresentanteLegalData = new ERepresentanteLegal();
		this.oEFacultadPoderData = new EFacultadPoder();
		this.oEDeudorData = new EDeudor();
		this.oESuscripcionData = new ESuscripcion();
		this.oETerceroData = new ETercero();
		
		oEOperacionMensajeSelected = new EOperacionMensaje();
		oERepresentanteLegalSelected = new ERepresentanteLegal();
		oERepresentanteLegalRelacionSelected = new ERepresentanteLegal();
		oERL1Selected = new ERepresentanteLegal();
		oERL2Selected = new ERepresentanteLegal();
		oERL3Selected = new ERepresentanteLegal();
		oERL4Selected = new ERepresentanteLegal();
		oERL5Selected = new ERepresentanteLegal();
		oEServicioSelected = new EServicio();
		oETerceroSelected = new EGeneral();
		oECargoLaboralSelected = new EGeneral();
		
		oBOOperacion = new BOOperacion();
		oBOGeneral = new BOGeneral();
		oBOCliente = new BOCliente();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oBORepresentanteLegal = new BORepresentanteLegal();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstDeudorRecycle = new ArrayList<EDeudor>();
		
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		lstOperacionClienteDocumento = new ArrayList<EOperacionClienteDocumento>();
		lstRepresentanteLegal = new ArrayList<ERepresentanteLegal>();
		lstRepresentanteLegalRelacion = new ArrayList<ERepresentanteLegal>();
		lstFacultadPoder = new ArrayList<EFacultadPoder>();
		lstRL1 = new ArrayList<ERepresentanteLegal>();
		lstRL2 = new ArrayList<ERepresentanteLegal>();
		lstRL3 = new ArrayList<ERepresentanteLegal>();
		lstRL4 = new ArrayList<ERepresentanteLegal>();
		lstRL5 = new ArrayList<ERepresentanteLegal>();
		lstDeudor = new ArrayList<EDeudor>();
		lstSuscripcion = new ArrayList<ESuscripcion>();
		lstEstado = new ArrayList<EGeneral>();
		lstNivel = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstDOI = new ArrayList<EGeneral>();
		lstDOIContratanteFiltro = new ArrayList<EGeneral>();
		lstDOIRepresentanteFiltro = new ArrayList<EGeneral>();
		lstSexo = new ArrayList<EGeneral>();
		lstTipoPersona = new ArrayList<EGeneral>();
		lstTipoPersonaRepresentanteFiltro = new ArrayList<EGeneral>();
		lstEstadoCivil = new ArrayList<EGeneral>();
		lstPais = new ArrayList<EGeneral>();
		lstNivelInstruccion = new ArrayList<EGeneral>();
		lstProfesion = new ArrayList<EGeneral>();
		lstFacultad = new ArrayList<EGeneral>();
		lstOtorgamiento = new ArrayList<EGeneral>();
		lstServicio = new ArrayList<EServicio>();
		lstTipoRelacion = new ArrayList<EGeneral>();
		lstTercero = new ArrayList<EGeneral>();
		lstTipoPago = new ArrayList<EGeneral>();
		lstFormaPago = new ArrayList<EGeneral>();
		lstEstadoDeudor = new ArrayList<EGeneral>();
		lstEstadoDeudorFiltro = new ArrayList<EGeneral>();
		lstCIIU = new ArrayList<EGeneral>();
		lstTipoPersonaJuridica = new ArrayList<EGeneral>();
		lstSuscripcionPago = new ArrayList<EGeneral>();
		lstSuscripcionPagoFiltro = new ArrayList<EGeneral>();
		lstFacultadOperar = new ArrayList<EGeneral>();
		lstTipoDuracionPartida = new ArrayList<EGeneral>();
		lstCargoLaboral = new ArrayList<EGeneral>();
		lstTipoNumeracionEstatuto = new ArrayList<EGeneral>();
		
		lstDepartamentoReal = new ArrayList<EGeneral>();
		lstProvinciaReal = new ArrayList<EGeneral>();
		lstDistritoReal = new ArrayList<EGeneral>();
		lstDepartamentoContractual = new ArrayList<EGeneral>();
		lstProvinciaContractual = new ArrayList<EGeneral>();
		lstDistritoContractual = new ArrayList<EGeneral>();
		lstDepartamentoComercial = new ArrayList<EGeneral>();
		lstProvinciaComercial = new ArrayList<EGeneral>();
		lstDistritoComercial = new ArrayList<EGeneral>();
		lstDepartamentoDomiciliaria = new ArrayList<EGeneral>();
		lstProvinciaDomiciliaria = new ArrayList<EGeneral>();
		lstDistritoDomiciliaria = new ArrayList<EGeneral>();
		lstDepartamentoCorrespondencia = new ArrayList<EGeneral>();
		lstProvinciaCorrespondencia = new ArrayList<EGeneral>();
		lstDistritoCorrespondencia = new ArrayList<EGeneral>();
		lstDepartamentoDeudor = new ArrayList<EGeneral>();
		lstProvinciaDeudor = new ArrayList<EGeneral>();
		lstDistritoDeudor = new ArrayList<EGeneral>();
		lstDepartamentoPostalDeudor = new ArrayList<EGeneral>();
		lstProvinciaPostalDeudor = new ArrayList<EGeneral>();
		lstDistritoPostalDeudor = new ArrayList<EGeneral>();
		
		files = new ArrayList<UploadedFile>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);

			switch (accionExterna) {
			case UAccionExterna.NUEVO:
				break;
			case UAccionExterna.EDITAR:
				break;
			case UAccionExterna.VER:
			}
			
			if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEOperacionClienteLoad = (EOperacionCliente) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEOperacionClienteLoad = oBOOperacion.buscarEvaluacionCliente(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente());
					oEOperacionClienteData = oBOOperacion.buscarEvaluacionCliente(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente());
					oEInformeLegalAdicionalData = oBOSolicitudCredito.buscarInformeLegalAdicional(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoCliente(), oEOperacionClienteLoad.getCodigoTipoCliente());
					
					if(oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_SOCIO){
						oEClienteData = oBOCliente.buscarSocio(oEOperacionClienteLoad.getCodigoCliente());
						oEClienteConstitucionEmpresaData = oBOCliente.buscarConstitucionEmpresa(oEOperacionClienteLoad.getCodigoCliente());
					}else if (oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_POSTULANTE){
						oEClienteData = oBOCliente.buscarPostulante(oEOperacionClienteLoad.getCodigoCliente());
						oEClienteConstitucionEmpresaData = oBOCliente.buscarConstitucionEmpresa(oEOperacionClienteLoad.getCodigoCliente());
					}else if (oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_TERCERO){
						oETerceroData = oBOCliente.buscarTercero(oEOperacionClienteLoad.getCodigoCliente());
						//oEClienteConstitucionEmpresaData = oBOCliente.buscarConstitucionEmpresa(oEOperacionClienteLoad.getCodigoCliente());
					}else if (oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_NO_SOCIO){
						oEClienteData = oBOCliente.buscarNoSocio(oEOperacionClienteLoad.getCodigoCliente());
						//oEClienteConstitucionEmpresaData = oBOCliente.buscarConstitucionEmpresa(oEOperacionClienteLoad.getCodigoCliente());
					}
					
					if(oEInformeLegalAdicionalData == null){ this.oEInformeLegalAdicionalData = new EInformeLegalAdicional(); }
					
					if(oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_SOCIO ||
							oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_POSTULANTE ||
							oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_NO_SOCIO
							){
					if(oEClienteData != null){
						oEOperacionClienteData.setCodigoTipoDocumento(oEClienteData.getCodigoTipoDocumento());
						oEOperacionClienteData.setNumeroDocumento(oEClienteData.getDocumento());
						oEOperacionClienteData.setRuc(oEClienteData.getRuc());
						oEOperacionClienteData.setApellidoPaterno(oEClienteData.getApellidoPaterno());
						oEOperacionClienteData.setApellidoMaterno(oEClienteData.getApellidoMaterno());
						oEOperacionClienteData.setNombre(oEClienteData.getNombre());
						oEOperacionClienteData.setNombreLargo(oEClienteData.getNombreLargo());
						oEOperacionClienteData.setNumeroDocumento(oEClienteData.getDocumento());
						oEOperacionClienteData.setDireccionReal(oEClienteData.getDireccion());
						oEOperacionClienteData.setDireccionContractual(oEClienteData.getDireccion2());
						oEOperacionClienteData.setCodigoUbigeoReal(oEClienteData.getCodigoUbigeo());
						oEOperacionClienteData.setCodigoUbigeoContractual(oEClienteData.getCodigoUbigeo2());
						oEOperacionClienteData.setCodigoTipoDocumentoConyugue(oEClienteData.getCodigoTipoDocumentoConyugue());
						oEOperacionClienteData.setDocumentoConyugue(oEClienteData.getDocumentoConyugue());
						oEOperacionClienteData.setApellidoPaternoConyugue(oEClienteData.getApellidoPaternoConyugue());
						oEOperacionClienteData.setApellidoMaternoConyugue(oEClienteData.getApellidoMaternoConyugue());
						oEOperacionClienteData.setNombreConyugue(oEClienteData.getNombreConyugue());
						oEOperacionClienteData.setNombreLargoConyuge(oEClienteData.getNombreSuperLargoConyugue());
						oEInformeLegalAdicionalData.setCodigoEstadoCivil(oEClienteData.getCodigoEstadoCivil());
					}
					}
					
					if (oEOperacionClienteLoad.getCodigoTipoCliente() == UTipoCliente.COD_TERCERO){
					if(oETerceroData != null){
						oEOperacionClienteData.setCodigoTipoDocumento(oETerceroData.getCodigoTipoDocumento());
						oEOperacionClienteData.setNumeroDocumento(oETerceroData.getDocumento());
						oEOperacionClienteData.setRuc(oETerceroData.getRuc());
						oEOperacionClienteData.setApellidoPaterno(oETerceroData.getApellidoPaterno());
						oEOperacionClienteData.setApellidoMaterno(oETerceroData.getApellidoMaterno());
						//oEOperacionClienteData.setNombre(oETerceroData.getNombre());
						oEOperacionClienteData.setNombreLargo(oETerceroData.getNombreLargo());
						oEOperacionClienteData.setNumeroDocumento(oETerceroData.getDocumento());
						oEOperacionClienteData.setDireccionReal(oETerceroData.getDireccion());
						oEOperacionClienteData.setDireccionContractual(oETerceroData.getDireccionPostal());
						oEOperacionClienteData.setCodigoUbigeoReal(oETerceroData.getCodigoUbigeo());
						oEOperacionClienteData.setCodigoUbigeoContractual(oETerceroData.getCodigoUbigeoPostal());
						/*
						oEOperacionClienteData.setCodigoTipoDocumentoConyugue(oETerceroData.getCodigoTipoDocumentoConyugue());
						oEOperacionClienteData.setDocumentoConyugue(oETerceroData.getDocumentoConyugue());
						oEOperacionClienteData.setApellidoPaternoConyugue(oETerceroData.getApellidoPaternoConyugue());
						oEOperacionClienteData.setApellidoMaternoConyugue(oETerceroData.getApellidoMaternoConyugue());
						oEOperacionClienteData.setNombreConyugue(oETerceroData.getNombreConyugue());
						oEOperacionClienteData.setNombreLargoConyuge(oETerceroData.getNombreSuperLargoConyugue());
						*/
						oEInformeLegalAdicionalData.setCodigoEstadoCivil(oETerceroData.getCodigoEstadoCivil());
					}
					}
					
					if(oEClienteConstitucionEmpresaData != null){
						oEInformeLegalAdicionalData.setNumeroPartida(oEClienteConstitucionEmpresaData.getInscripcionRegistroPublico());
						oEInformeLegalAdicionalData.setOficinaRegistral(oEClienteConstitucionEmpresaData.getOficinaRegistral());
						oEInformeLegalAdicionalData.setFechaConstitucion(oEClienteConstitucionEmpresaData.getFechaConstitucion());
						oEInformeLegalAdicionalData.setCodigoNotario(oEClienteConstitucionEmpresaData.getCodigoNotario());
						oEInformeLegalAdicionalData.setDescripcionNotario(oEClienteConstitucionEmpresaData.getDescripcionNotario());
						oEInformeLegalAdicionalData.setNumeroAcciones(oEClienteConstitucionEmpresaData.getNumeroAcciones());
					}
					
					if(oEUsuario.getCodigoArea() == UArea.LEGAL){
						codigoEstado = oEOperacionClienteData.getCodigoEstadoSolicitud();
						deshabilitarObservacionLegal = false;
						deshabilitarObservacionNegocio = true;
						deshabilitarEstado = false;
						deshabilitarFrmContratante = false;
						visualizarEstado1 = false;
						visualizarEstado2 = true;
					}else{
						codigoEstado = UEstadoOperacionCliente.ENEVALUACION;
						deshabilitarObservacionLegal = true;
						deshabilitarObservacionNegocio = false;
						deshabilitarEstado = true;
						deshabilitarFrmContratante = true;
						visualizarEstado1 = true;
						visualizarEstado2 = false;
					}
					
					visualizarPnlContratante = true;
					visualizarPnlRepresentanteLegal = true;
					visualizarPnlDeudor = true;
					
					visualizarBtnSalir = true;
					visualizarBtnGrabar = true;
					visualizarBtnAdjuntar = true;
					visualizarBtnAgregarSuscripcion = true;
					visualizarBtnModificarSuscripcion = true;
					visualizarBtnEliminarSuscripcion = true;
					visualizarBtnGrabarSuscripcion = true;
					visualizarBtnAgregarRepresentanteLegal = true;
					visualizarBtnModificarRepresentanteLegal = true;
					visualizarBtnEliminarRepresentanteLegal = true;
					visualizarBtnGrabarRepresentanteLegal = true;
					visualizarBtnAgregarFacultad = true;
					visualizarBtnEliminarFacultad = true;
					visualizarBtnGrabarFacultad = true;
					visualizarBtnAgregarDeudor = true;
					visualizarBtnModificarDeudor = true;
					visualizarBtnEliminarDeudor = true;
					visualizarBtnGrabarDeudor = true;
					
					listarUbigeoContratante();
				}
			}else if(UAccionExterna.VER == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEOperacionClienteLoad = (EOperacionCliente) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEOperacionClienteLoad = oBOOperacion.buscarEvaluacionCliente(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente());
					oEOperacionClienteData = oBOOperacion.buscarEvaluacionCliente(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente());
					oEInformeLegalAdicionalData = oBOSolicitudCredito.buscarInformeLegalAdicional(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoCliente(), oEOperacionClienteLoad.getCodigoTipoCliente());
					
					if(oEInformeLegalAdicionalData == null){ this.oEInformeLegalAdicionalData = new EInformeLegalAdicional(); }
					
					deshabilitarObservacionLegal = true;
					deshabilitarObservacionNegocio = true;
					deshabilitarEstado = true;
					deshabilitarFrmContratante = true;
					visualizarEstado1 = true;
					visualizarEstado2 = false;
					
					visualizarPnlContratante = true;
					visualizarPnlRepresentanteLegal = true;
					visualizarPnlDeudor = true;
					
					visualizarBtnSalir = true;
					visualizarBtnVisualizarRepresentanteLegal = true;
					visualizarBtnVisualizarFacultad = true;
					visualizarBtnVisualizarDeudor = true;
					listarUbigeoContratante();
				}
			}
			
			listarDesplegable();
			listarDocumento();
			listarRepresentanteLegal();
			listarDeudor();
			listarClienteSuscripcion();
			visualizarFrmContrante();
			visualizarObservacionAval();
			visualizarObservacionGrabarBien();
			visualizarTipoSuscripcionPago();
			
			if(lstRepresentanteLegal.size() > 0){
				minimizarPnlRepresentanteLegal = false;
			}
			if(lstDeudor.size() > 0){
				minimizarPnlDeudor = false;
			}
			if(lstOperacionClienteDocumento.size() > 0){
				minimizarPnlDocumento = false;
			}
		}
	}
	
	public void guardar() {
		generarCorrelativoDocumentoCarga();
		EOperacionCliente oEOperacionCliente = new EOperacionCliente();
		EInformeLegalAdicional oEInformeLegalAdicional = new EInformeLegalAdicional();
		
		oEOperacionCliente = oEOperacionClienteData;
		
		if(oEUsuario.getCodigoArea() != UArea.LEGAL){
			oEOperacionCliente.setCodigoEstadoSolicitud(codigoEstado);
		}
		oEOperacionCliente.setCodigoUbigeoReal(UFuncionesGenerales.convierteCadenaAEntero(
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoReal) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaReal) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoReal)));
		oEOperacionCliente.setCodigoUbigeoContractual(UFuncionesGenerales.convierteCadenaAEntero(
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoContractual) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaContractual) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoContractual)));
		
		if (oEOperacionClienteData.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
			oEOperacionCliente.setNombreLargo(oEOperacionClienteData.getApellidoPaterno() +" "+ 
												oEOperacionClienteData.getApellidoMaterno() +" "+ 
												oEOperacionClienteData.getNombre());
			
			oEOperacionCliente.setNombreLargoConyuge(oEOperacionClienteData.getApellidoPaternoConyugue() +" "+ 
														oEOperacionClienteData.getApellidoMaternoConyugue() +" "+ 
														oEOperacionClienteData.getNombreConyugue());
		}
		
		oEInformeLegalAdicional = oEInformeLegalAdicionalData;
		oEInformeLegalAdicional.setNumeroSolicitud(oEOperacionClienteData.getNumeroSolicitud());
		oEInformeLegalAdicional.setCodigoCliente(oEOperacionClienteData.getCodigoCliente());
		oEInformeLegalAdicional.setCodigoTipoCliente(oEOperacionClienteData.getCodigoTipoCliente());
		oEInformeLegalAdicional.setFechaRegistro(new Date());
		oEInformeLegalAdicional.setUsuarioRegistro(oEUsuario);
		
		oEOperacionCliente.setInformeLegalAdicional(oEInformeLegalAdicional);
		oEOperacionCliente.setLstDocumentoCarga(lstDocumentoCarga);
		oEOperacionCliente.setLstSuscripcion(lstSuscripcion);
		oEOperacionCliente.setLstRepresentanteLegal(lstRepresentanteLegal);
		oEOperacionCliente.setLstDeudor(lstDeudor);
		oEOperacionCliente.setLstDeudorRecycle(lstDeudorRecycle);
		oEOperacionCliente.setFechaRegistro(new Date());
		oEOperacionCliente.setUsuarioRegistro(oEUsuario);
		
		oEMensaje = oBOOperacion.modificarEvaluacionCliente(oEOperacionCliente);
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			ruta = "ListaOperacionCliente.xhtml";
		}else{
			ruta = "ListaOperacionClienteOtros.xhtml";
		}
			
		inicializar();
		/*Cerramos Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	public void listarDesplegable() {
		lstEstado = oUManejadorListaDesplegable.obtieneEstadoOperacionCliente();
		lstMotivo = oUManejadorListaDesplegable.obtieneMotivo();
		lstDOI = oUManejadorListaDesplegable.obtieneTipoDocumento();
		lstSexo = oUManejadorListaDesplegable.obtieneTipoSexo();
		lstTipoPersona = oUManejadorListaDesplegable.obtieneTipoPersona();
		lstEstadoCivil = oUManejadorListaDesplegable.obtieneEstadoCivil();
		lstPais = oUManejadorListaDesplegable.obtienePais();
		lstNivelInstruccion = oUManejadorListaDesplegable.obtieneNivelInstruccion();
		lstProfesion = oUManejadorListaDesplegable.obtieneProfesion();
		lstFacultad = oUManejadorListaDesplegable.obtieneFacultad();
		lstOtorgamiento = oUManejadorListaDesplegable.obtieneOtorgamiento();
		lstServicio = oBOGeneral.listarServicio();
		lstTipoRelacion = oUManejadorListaDesplegable.obtieneTipoRelacionEntreCliente();
		lstTercero = oUManejadorListaDesplegable.obtieneTercero();
		lstTipoPago = oUManejadorListaDesplegable.obtieneTipoPago();
		lstFormaPago = oUManejadorListaDesplegable.obtieneFormaPago();
		lstEstadoDeudor = oUManejadorListaDesplegable.obtieneEstadoDeudor();
		lstCIIU = oUManejadorListaDesplegable.obtieneCIUU();
		lstTipoPersonaJuridica = oUManejadorListaDesplegable.obtieneTipoPersonaJuridica();
		lstSuscripcionPago = oUManejadorListaDesplegable.obtieneSuscripcionPago();
		lstSuscripcionPagoFiltro = lstSuscripcionPago;
		lstFacultadOperar = oUManejadorListaDesplegable.obtieneFacultadOperar();
		lstTipoDuracionPartida = oUManejadorListaDesplegable.obtieneTipoDuracionPartida();
		lstCargoLaboral = oUManejadorListaDesplegable.obtieneCargoLaboral();
		lstTipoNumeracionEstatuto = oUManejadorListaDesplegable.obtieneTipoNumeracionEstatuto();
	}
	
	//*************************************//
	//Metodos para documento de carga
	//*************************************//
	
	public void listarDocumento(){
		lstOperacionClienteDocumento = oBOOperacion.listarEvaluacionClienteDocumento(oEOperacionClienteLoad.getNumeroSolicitud(), oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente());
	}
	
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
	}
	
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
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
		if(lstOperacionClienteDocumento != null){
			for(int i=0;i<lstOperacionClienteDocumento.size();i++){
				correlativoDocumento = lstOperacionClienteDocumento.get(i).getCodigoDocumento();
			}
		}
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
	}
	
	public void descargarDocumento(EOperacionDocumento oEOperacionDocumentoItem) {
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
	
	//*************************************//
	//Metodos para Representante Legal
	//*************************************//
	
	public void listarRepresentanteLegal() {
		lstRepresentanteLegal = oBORepresentanteLegal.listarRepresentanteLegal(oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente());
		List<EFacultadPoder> lstFacultadPoder = null;
		int correlativo = 0;
		for(int i=0;i<lstRepresentanteLegal.size();i++){
			correlativo = correlativo + 1;
			lstFacultadPoder = new ArrayList<EFacultadPoder>();
			lstFacultadPoder = oBORepresentanteLegal.listarFacultadPoder(oEOperacionClienteLoad.getCodigoTipoCliente(), oEOperacionClienteLoad.getCodigoCliente(), lstRepresentanteLegal.get(i).getCodigoRepresentante());
			
			lstRepresentanteLegal.get(i).setCodigoOrden(correlativo);
			lstRepresentanteLegal.get(i).setLstFacultadPoder(lstFacultadPoder);
		}
		listarRepresentanteLegalFiltro(1);
	}
	
	public void listarRepresentanteLegalFiltro(int indicadorTabla) {		
		if(indicadorTabla == 1){
			lstRL1 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oEOperacionClienteLoad.getNumeroDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 2){
			lstRL2 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERL1Selected.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 3){
			lstRL3 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERL2Selected.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 4){
			lstRL4 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERL3Selected.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 5){
			lstRL5 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERL4Selected.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}
		
		visualizarTblRepresentante(indicadorTabla);
	}
	
	public void buscarRepresentanteLegalFiltro(ERepresentanteLegal oERepresentanteLegalItem, int indicadorTabla) {
		if(indicadorTabla == 1){
			lstRL1 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oEOperacionClienteLoad.getNumeroDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 2){
			oERL1Selected = oERepresentanteLegalItem;
			lstRL2 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERepresentanteLegalItem.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 3){
			oERL2Selected = oERepresentanteLegalItem;
			lstRL3 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERepresentanteLegalItem.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 4){
			oERL3Selected = oERepresentanteLegalItem;
			lstRL4 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERepresentanteLegalItem.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 5){
			oERL4Selected = oERepresentanteLegalItem;
			lstRL5 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().matches("(?i).*"+ oERepresentanteLegalItem.getDocumento() +".*"))
					   .collect(Collectors.toList());
		}
		
		visualizarTblRepresentante(indicadorTabla);
	}
	
	public void visualizarTblRepresentante(int indicadorTabla){
		if (indicadorTabla == 1) {
			visualizarTblRepresentante1 = true;
			visualizarTblRepresentante2 = false;
			visualizarTblRepresentante3 = false;
			visualizarTblRepresentante4 = false;
			visualizarTblRepresentante5 = false;
		}else if(indicadorTabla == 2) {
			visualizarTblRepresentante1 = true;
			visualizarTblRepresentante2 = true;
			visualizarTblRepresentante3 = false;
			visualizarTblRepresentante4 = false;
			visualizarTblRepresentante5 = false;
		}else if(indicadorTabla == 3) {
			visualizarTblRepresentante1 = true;
			visualizarTblRepresentante2 = true;
			visualizarTblRepresentante3 = true;
			visualizarTblRepresentante4 = false;
			visualizarTblRepresentante5 = false;
		}else if(indicadorTabla == 4) {
			visualizarTblRepresentante1 = true;
			visualizarTblRepresentante2 = true;
			visualizarTblRepresentante3 = true;
			visualizarTblRepresentante4 = true;
			visualizarTblRepresentante5 = false;
		}else if(indicadorTabla == 5) {
			visualizarTblRepresentante1 = true;
			visualizarTblRepresentante2 = true;
			visualizarTblRepresentante3 = true;
			visualizarTblRepresentante4 = true;
			visualizarTblRepresentante5 = true;
		}
	}
	
	public void obtenerIndicadorTablaRepresentanteLegal(int indicadorTabla){
		indicadorTblSeleccionRepresentante = indicadorTabla;
	}
	
	public String obtenerDocumentoRelacionRepresentante(int indicadorTabla){
		String documento = "";
		if(indicadorTabla == 1){
			documento = oEOperacionClienteLoad.getNumeroDocumento();
		}else if (indicadorTabla == 2){
			if(oERL1Selected != null){
				documento = oERL1Selected.getDocumento();
			}
		}else if (indicadorTabla == 3){
			if(oERL2Selected != null){
				documento = oERL2Selected.getDocumento();
			}
		}else if (indicadorTabla == 4){
			if(oERL3Selected != null){
				documento = oERL3Selected.getDocumento();
			}
		}else if (indicadorTabla == 5){
			if(oERL4Selected != null){
				documento = oERL4Selected.getDocumento();
			}
		}
		return documento;
	}
	
	/*
	public void validarFechaRepresenanteLegal(){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -18);
		Date hoy = new Date();
		fechaMaximo = c.getTime();
		
		if(indicadorTipoPersonaRepresentante == UTipoPersonaGeneral.NATURAL){
			fechaMaximo = c.getTime();
		}else if (indicadorTipoPersonaRepresentante == UTipoPersonaGeneral.JURIDICO){
			fechaMaximo = hoy;
		}
	}
	*/
	
	public void validarTamanioDocumentoRepresenanteLegal(){
		if(oERepresentanteLegalData.getCodigoTipoDocumento() != null){
			if(oERepresentanteLegalData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
				maxLgnNumeroDocumentoRepresentante = UMaximoTamanio.RUC_MAXLGN;
			}else if(oERepresentanteLegalData.getCodigoTipoDocumento().equals(UTipoDocumento.DNI) || oERepresentanteLegalData.getCodigoTipoDocumento().equals(UTipoDocumento.LIBRETA_ELECTORAL)){
				maxLgnNumeroDocumentoRepresentante = UMaximoTamanio.DNI_MAXLGN;
			}else {
				maxLgnNumeroDocumentoRepresentante = UMaximoTamanio.OTROS_MAXLGN;
			}
		}else {
			maxLgnNumeroDocumentoRepresentante = UMaximoTamanio.OTROS_MAXLGN;
		}
	}
	
	public void agregarRepresentanteLegal(){
		accionInternaRepresenanteLegal = UAccionInterna.NUEVO;
		inicializarRepresentanteLegal();
		listarUbigeoRepresentante();
		validarTamanioDocumentoRepresenanteLegal();
		RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegal').show();");
	}
	
	public void modificarRepresentanteLegal(ERepresentanteLegal oERepresentanteLegalItem){
		if (oERepresentanteLegalItem != null) {
			if(oERepresentanteLegalItem.getCodigoTipoDocumento()!=null){
				if(oERepresentanteLegalItem.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
					indicadorTipoPersonaRepresentante = UTipoPersonaGeneral.JURIDICO;
				}else{
					indicadorTipoPersonaRepresentante = UTipoPersonaGeneral.NATURAL;
				}
			}
			
			accionInternaRepresenanteLegal = UAccionInterna.EDITAR;
			inicializarRepresentanteLegal();
			oERepresentanteLegalData = oERepresentanteLegalItem;
			listarUbigeoRepresentante();
			validarTamanioDocumentoRepresenanteLegal();
			RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegal').show();");
		}
	}
	
	public void visualizarRepresentanteLegal(ERepresentanteLegal oERepresentanteLegalItem){
		if (oERepresentanteLegalItem != null) {
			if(oERepresentanteLegalItem.getCodigoTipoDocumento()!=null){
				if(oERepresentanteLegalItem.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
					indicadorTipoPersonaRepresentante = UTipoPersonaGeneral.JURIDICO;
				}else{
					indicadorTipoPersonaRepresentante = UTipoPersonaGeneral.NATURAL;
				}
			}
			
			accionInternaRepresenanteLegal = UAccionInterna.VER;
			inicializarRepresentanteLegal();
			oERepresentanteLegalData = oERepresentanteLegalItem;
			listarUbigeoRepresentante();
			validarTamanioDocumentoRepresenanteLegal();
			RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegal').show();");
		}
	}
	
	public void eliminarRepresentanteLegal(ERepresentanteLegal oERepresentanteLegalItem){
		if (oERepresentanteLegalItem != null){
			oERepresentanteLegalItem.setCodigoAccion(UAccionTabla.ELIMINAR);
		}
		
		if(indicadorTblSeleccionRepresentante == 1){
			for(ERepresentanteLegal obj2: lstRL2){
				if(obj2.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento())){
					obj2.setCodigoAccion(UAccionTabla.ELIMINAR);
					
					for(ERepresentanteLegal obj3: lstRL3){
						if(obj3.getDocumentoRelacion().equals(obj2.getDocumento())){
							obj3.setCodigoAccion(UAccionTabla.ELIMINAR);
							
							for(ERepresentanteLegal obj4: lstRL4){
								if(obj4.getDocumentoRelacion().equals(obj3.getDocumento())){
									obj4.setCodigoAccion(UAccionTabla.ELIMINAR);
									
									for(ERepresentanteLegal obj5: lstRL5){
										if(obj5.getDocumentoRelacion().equals(obj4.getDocumento())){
											obj5.setCodigoAccion(UAccionTabla.ELIMINAR);
										}
									}
								}
							}
						}
					}
				}
			}
		}else if(indicadorTblSeleccionRepresentante == 2){
			for(ERepresentanteLegal obj3: lstRL3){
				if(obj3.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento())){
					obj3.setCodigoAccion(UAccionTabla.ELIMINAR);
					
					for(ERepresentanteLegal obj4: lstRL4){
						if(obj4.getDocumentoRelacion().equals(obj3.getDocumento())){
							obj4.setCodigoAccion(UAccionTabla.ELIMINAR);
							
							for(ERepresentanteLegal obj5: lstRL5){
								if(obj5.getDocumentoRelacion().equals(obj4.getDocumento())){
									obj5.setCodigoAccion(UAccionTabla.ELIMINAR);
								}
							}
						}
					}
				}
			}
		}else if(indicadorTblSeleccionRepresentante == 3){
			for(ERepresentanteLegal obj4: lstRL4){
				if(obj4.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento())){
					obj4.setCodigoAccion(UAccionTabla.ELIMINAR);
					
					for(ERepresentanteLegal obj5: lstRL5){
						if(obj5.getDocumentoRelacion().equals(obj4.getDocumento())){
							obj5.setCodigoAccion(UAccionTabla.ELIMINAR);
						}
					}
				}
			}
		}else if(indicadorTblSeleccionRepresentante == 4){	
			for(ERepresentanteLegal obj5: lstRL5){
				if(obj5.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento())){
					obj5.setCodigoAccion(UAccionTabla.ELIMINAR);
				}
			}
		}
		
		listarRepresentanteLegalFiltro(indicadorTblSeleccionRepresentante);
	}
	
	public void guardarRepresentanteLegal(){
		if (oERepresentanteLegalData != null) {
			//if (validarRepresentante()){
				if (accionInternaRepresenanteLegal == UAccionInterna.NUEVO){
					int correlativo = 0;
					ERepresentanteLegal oERepresentanteLegal = new ERepresentanteLegal();
					for(int i=0;i<lstRepresentanteLegal.size();i++){
						correlativo = lstRepresentanteLegal.get(i).getCodigoOrden();
					}
					oERepresentanteLegal = oERepresentanteLegalData;
					oERepresentanteLegal.setCodigoOrden(correlativo+1);
					oERepresentanteLegal.setCodigoAccion(UAccionTabla.INSERTAR);
					
					oERepresentanteLegal.setCodigoUbigeoComercial(UFuncionesGenerales.convierteCadenaAEntero(
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDepartamentoComercial()) +
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoProvinciaComercial()) +
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDistritoComercial())));
					oERepresentanteLegal.setCodigoUbigeoDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria()) +
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoProvinciaDomiciliaria()) +
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDistritoDomiciliaria())));
					oERepresentanteLegal.setCodigoUbigeoCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(
																		UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia()) +
																		UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoProvinciaCorrespondencia()) +
																		UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDistritoCorrespondencia())));
					
					oERepresentanteLegal.setDescripcionIndicadorFirma(UFuncionesGenerales.obtieneDescripcionDeValorIndicador(oERepresentanteLegalData.getIndicadorFirma()));
					
					if (indicadorTipoPersonaRepresentante == UTipoPersonaGeneral.NATURAL){
						oERepresentanteLegal.setRazonSocialRepresentante(oERepresentanteLegalData.getApellidoPaternoRepresentante() +" "+ 
																			oERepresentanteLegalData.getApellidoMaternoRepresentante() +" "+ 
																			oERepresentanteLegalData.getNombreRepresentante());
					}
					
					oERepresentanteLegal.setNombreLargo(UFuncionesGenerales.recortarCadena(oERepresentanteLegal.getRazonSocialRepresentante(), 0, 40));
					
					String documentoRelacion = obtenerDocumentoRelacionRepresentante(indicadorTblSeleccionRepresentante);
					if(!documentoRelacion.equals("")){
						oERepresentanteLegal.setDocumentoRelacion(documentoRelacion);
						lstRepresentanteLegal.add(oERepresentanteLegal);
						listarRepresentanteLegalFiltro(indicadorTblSeleccionRepresentante);
					}
					
				}else if(accionInternaRepresenanteLegal == UAccionInterna.EDITAR){
					ERepresentanteLegal oERepresentanteLegal = new ERepresentanteLegal();
					oERepresentanteLegal = oERepresentanteLegalData;
					
					if(oERepresentanteLegal.getCodigoRepresentante() != 0){
						oERepresentanteLegal.setCodigoAccion(UAccionTabla.EDITAR);
					}
					
					//oERepresentanteLegal.setCodigoAccion(UAccionTabla.EDITAR);
					
					oERepresentanteLegal.setCodigoUbigeoComercial(UFuncionesGenerales.convierteCadenaAEntero(
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDepartamentoComercial()) +
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoProvinciaComercial()) +
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDistritoComercial())));
					oERepresentanteLegal.setCodigoUbigeoDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria()) +
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoProvinciaDomiciliaria()) +
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDistritoDomiciliaria())));
					oERepresentanteLegal.setCodigoUbigeoCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(
																		UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia()) +
																		UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoProvinciaCorrespondencia()) +
																		UFuncionesGenerales.convertirEnteroACadenaUbigeo(oERepresentanteLegalData.getCodigoDistritoCorrespondencia())));
					 
					oERepresentanteLegal.setDescripcionIndicadorFirma(UFuncionesGenerales.obtieneDescripcionDeValorIndicador(oERepresentanteLegalData.getIndicadorFirma()));
					
					if (indicadorTipoPersonaRepresentante == UTipoPersonaGeneral.NATURAL){
						oERepresentanteLegal.setRazonSocialRepresentante(oERepresentanteLegalData.getApellidoPaternoRepresentante() +" "+ 
																			oERepresentanteLegalData.getApellidoMaternoRepresentante() +" "+ 
																			oERepresentanteLegalData.getNombreRepresentante());
					}
					
					oERepresentanteLegal.setNombreLargo(UFuncionesGenerales.recortarCadena(oERepresentanteLegal.getRazonSocialRepresentante(), 0, 40));
					
					for (ERepresentanteLegal obj : lstRepresentanteLegal) {
						if(obj.getCodigoOrden() == oERepresentanteLegalData.getCodigoOrden()) {
							lstRepresentanteLegal.set(obj.getCodigoOrden()-1, oERepresentanteLegal);
							//oERepresentanteLegalData = oERepresentanteLegal;
							//obj = oERepresentanteLegal;
						}
					}
					//lstRepresentanteLegal.set(oERepresentanteLegalData.getCodigoOrden(), oERepresentanteLegal);
				}
				RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegal').hide();");
		/*	
		}else{
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
		*/
		}
	}
	
	public void inicializarRepresentanteLegal() {
		this.oERepresentanteLegalData = new ERepresentanteLegal();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -18);
		Date hoy = new Date();
		fechaMaximo = c.getTime();
		
		if (indicadorTipoPersonaRepresentante == UTipoPersonaGeneral.NATURAL){
			deshabilitarTipoDocumentoRepresentante = false;
			visualizarFrmRepresentantePN = true;
			visualizarFrmRepresentantePJ = false;
			visualizarTabRepresentante1 = true;
			visualizarTabRepresentante2 = true;
			visualizarTabRepresentante3 = true;
			visualizarTabRepresentante4 = true;
			
			lstTipoPersonaRepresentanteFiltro = lstTipoPersona.stream()
					.filter(x -> !x.getCodigo().equals(UTipoPersona.JURIDICA_F_LUCRO))
					.filter(x -> !x.getCodigo().equals(UTipoPersona.JURIDICA_S_LUCRO))
					.collect(Collectors.toList());
			
			lstDOIRepresentanteFiltro = lstDOI.stream()
					.filter(x -> !x.getCodigo().equals(UTipoDocumento.RUC))
					.collect(Collectors.toList());
			
			oERepresentanteLegalData.setCodigoTipoDocumento(UTipoDocumento.DNI);
			fechaMaximo = c.getTime();
		}else if (indicadorTipoPersonaRepresentante == UTipoPersonaGeneral.JURIDICO) {
			deshabilitarTipoDocumentoRepresentante = true;
			visualizarFrmRepresentantePN = false;
			visualizarFrmRepresentantePJ = true;
			visualizarTabRepresentante1 = true;
			visualizarTabRepresentante2 = true;
			visualizarTabRepresentante3 = false;
			visualizarTabRepresentante4 = true;
			
			lstTipoPersonaRepresentanteFiltro = lstTipoPersona.stream()
					.filter(x -> !x.getCodigo().equals(UTipoPersona.MANCOMUNADO))
					.filter(x -> !x.getCodigo().equals(UTipoPersona.NATURAL))
					.collect(Collectors.toList());
			
			lstDOIRepresentanteFiltro = lstDOI.stream()
					.filter(x -> x.getCodigo().equals(UTipoDocumento.RUC))
					.collect(Collectors.toList());
			
			oERepresentanteLegalData.setCodigoTipoDocumento(UTipoDocumento.RUC);
			fechaMaximo = hoy;
		}
		
		if (accionInternaRepresenanteLegal == UAccionInterna.NUEVO){
			deshabilitarNumeroDocumentoRepresentante = false;
		}else{
			deshabilitarNumeroDocumentoRepresentante = true;
		}
		
		indicadorTabSeleccionRepresentante = 0;
	}
	
	public void obtenerTipoDocumentoRepresentante() {
		validarTamanioDocumentoRepresenanteLegal();
		oERepresentanteLegalData.setDocumento("");
	}
	
	public void obtenerDepartamentoComercial() {
		lstProvinciaComercial = oUManejadorListaDesplegable.obtieneProvincia(oERepresentanteLegalData.getCodigoDepartamentoComercial());
		lstDistritoComercial = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoComercial(), 0);
	}
	
	public void obtenerProvinciaComercial() {
		lstDistritoComercial = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoComercial(), oERepresentanteLegalData.getCodigoProvinciaComercial());
	}
	
	public void obtenerDepartamentoDomiciliaria() {
		lstProvinciaDomiciliaria = oUManejadorListaDesplegable.obtieneProvincia(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria());
		lstDistritoDomiciliaria = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria(), 0);
	}
	
	public void obtenerProvinciaDomiciliaria() {
		lstDistritoDomiciliaria = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria(), oERepresentanteLegalData.getCodigoProvinciaDomiciliaria());
	}
	
	public void obtenerDepartamentoCorrespondencia() {
		lstProvinciaCorrespondencia = oUManejadorListaDesplegable.obtieneProvincia(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia());
		lstDistritoCorrespondencia = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia(), 0);
	}
	
	public void obtenerProvinciaCorrespondencia() {
		lstDistritoCorrespondencia = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia(), oERepresentanteLegalData.getCodigoProvinciaCorrespondencia());
	}
	
	public void listarUbigeoRepresentante() {
		String codigoUbigeoComercial = oERepresentanteLegalData.getCodigoUbigeoComercial()+"";
		String codigoUbigeoDomiciliaria = oERepresentanteLegalData.getCodigoUbigeoDomiciliaria()+"";
		String codigoUbigeoCorrespondencia = oERepresentanteLegalData.getCodigoUbigeoCorrespondencia()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoComercial).length() == 5) {
			oERepresentanteLegalData.setCodigoDepartamentoComercial(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoComercial.substring(0, 1)));
			oERepresentanteLegalData.setCodigoProvinciaComercial(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoComercial.substring(1, 3)));
			oERepresentanteLegalData.setCodigoDistritoComercial(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoComercial.substring(3, 5)));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoComercial).length() == 6) {
			oERepresentanteLegalData.setCodigoDepartamentoComercial(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoComercial.substring(0, 2)));
			oERepresentanteLegalData.setCodigoProvinciaComercial(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoComercial.substring(2, 4)));
			oERepresentanteLegalData.setCodigoDistritoComercial(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoComercial.substring(4, 6)));
		}
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoDomiciliaria).length() == 5) {
			oERepresentanteLegalData.setCodigoDepartamentoDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoDomiciliaria.substring(0, 1)));
			oERepresentanteLegalData.setCodigoProvinciaDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoDomiciliaria.substring(1, 3)));
			oERepresentanteLegalData.setCodigoDistritoDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoDomiciliaria.substring(3, 5)));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoDomiciliaria).length() == 6) {
			oERepresentanteLegalData.setCodigoDepartamentoDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoDomiciliaria.substring(0, 2)));
			oERepresentanteLegalData.setCodigoProvinciaDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoDomiciliaria.substring(2, 4)));
			oERepresentanteLegalData.setCodigoDistritoDomiciliaria(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoDomiciliaria.substring(4, 6)));
		}
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoCorrespondencia).length() == 5) {
			oERepresentanteLegalData.setCodigoDepartamentoCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoCorrespondencia.substring(0, 1)));
			oERepresentanteLegalData.setCodigoProvinciaCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoCorrespondencia.substring(1, 3)));
			oERepresentanteLegalData.setCodigoDistritoCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoCorrespondencia.substring(3, 5)));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoCorrespondencia).length() == 6) {
			oERepresentanteLegalData.setCodigoDepartamentoCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoCorrespondencia.substring(0, 2)));
			oERepresentanteLegalData.setCodigoProvinciaCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoCorrespondencia.substring(2, 4)));
			oERepresentanteLegalData.setCodigoDistritoCorrespondencia(UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoCorrespondencia.substring(4, 6)));
		}
		
		lstDepartamentoComercial = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaComercial = oUManejadorListaDesplegable.obtieneProvincia(oERepresentanteLegalData.getCodigoDepartamentoComercial());
		lstDistritoComercial = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoComercial(), oERepresentanteLegalData.getCodigoProvinciaComercial());
		
		lstDepartamentoDomiciliaria = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaDomiciliaria = oUManejadorListaDesplegable.obtieneProvincia(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria());
		lstDistritoDomiciliaria = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoDomiciliaria(), oERepresentanteLegalData.getCodigoProvinciaDomiciliaria());
		
		lstDepartamentoCorrespondencia = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaCorrespondencia = oUManejadorListaDesplegable.obtieneProvincia(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia());
		lstDistritoCorrespondencia = oUManejadorListaDesplegable.obtieneDistrito(oERepresentanteLegalData.getCodigoDepartamentoCorrespondencia(), oERepresentanteLegalData.getCodigoProvinciaCorrespondencia());
	}
	
	public boolean visualizarBtnBuscarRepresentanteLegal(ERepresentanteLegal oERepresentanteLegalItem){
		boolean ind = false;
		if (oERepresentanteLegalItem != null) {
			if(oERepresentanteLegalItem.getCodigoTipoDocumento()!=null){
				if(oERepresentanteLegalItem.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
					ind = true;
				}else{
					ind = false;
				}
			}
		}
		
		return ind;
	}
	
	public void buscarCargoLaboral(){
		oECargoLaboralSelected = new EGeneral();
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarCargoLaboral').show();");
	}
	
	public void asignarCargoLaboral(){
		oERepresentanteLegalData.setCargoLaboral(oECargoLaboralSelected.getDescripcion());;
	}
	
	//*************************************//
	//Metodos para Facultad Poder
	//*************************************//
	
	public void buscarServicio(){
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarServicio').show();");
	}
	
	public void buscarRepresentanteLegalRelacion(){
		lstRepresentanteLegalRelacion = new ArrayList<ERepresentanteLegal>();
        for (ERepresentanteLegal oERepresentanteLegal: lstRepresentanteLegal) {
            if (oERepresentanteLegal.getCodigoOrden() != oERepresentanteLegalSelected.getCodigoOrden()) {
            	lstRepresentanteLegalRelacion.add(oERepresentanteLegal);
            }
        }
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarRepresentanteRelacion').show();");
	}
	
	public void asignarServicio(){
		if(oEServicioSelected != null){
			oEFacultadPoderData.setCodigoServicio(oEServicioSelected.getCodigoServicio());
			oEFacultadPoderData.setDescripcionServicio(oEServicioSelected.getDescripcion());
			oEFacultadPoderData.setCodigoMoneda(oEServicioSelected.getCodigoMoneda());
		}
	}
	
	public void asignarRepresentanteLegalRelacion(){
		if(oERepresentanteLegalRelacionSelected != null){
			oEFacultadPoderData.setCodigoOrdenRelacion(oERepresentanteLegalRelacionSelected.getCodigoOrden());
			oEFacultadPoderData.setCodigoRepresentanteRelacion(oERepresentanteLegalRelacionSelected.getCodigoRepresentante());
			oEFacultadPoderData.setDescripcionRepresentanteRelacion(oERepresentanteLegalRelacionSelected.getNombreLargo());
		}
	}
	
	public void obtenerTipoFirma(){
		this.oERepresentanteLegalRelacionSelected = new ERepresentanteLegal();
		oEFacultadPoderData.setCodigoOrdenRelacion(0);
		oEFacultadPoderData.setDescripcionRepresentanteRelacion(null);
		
		if(oEFacultadPoderData.getCodigoTipoFirma() == UTipoFirma.INDIVIUAL){
			visualizarFrmRepresentanteRelacion = false;
		}else if(oEFacultadPoderData.getCodigoTipoFirma() == UTipoFirma.MANCOMUNADO){
			visualizarFrmRepresentanteRelacion = true;
		}
	}
	
	public void visualizarFacultadPoder(ERepresentanteLegal oERepresentanteLegalItem){
		if (oERepresentanteLegalItem != null) {
			if(oERepresentanteLegalItem.getLstFacultadPoder() != null){
				for(int i = 0; i<oERepresentanteLegalItem.getLstFacultadPoder().size();i++){
					oERepresentanteLegalItem.getLstFacultadPoder().get(i).
					setDescripcionTipoRelacion(UFuncionesGenerales.
							obtieneDescripcionDeValorSeleccionado2(lstTipoRelacion, oERepresentanteLegalItem.getLstFacultadPoder().get(i).getCodigoTipoRelacion(), true));
				}
				lstFacultadPoder = oERepresentanteLegalItem.getLstFacultadPoder();
			}else{
				lstFacultadPoder = new ArrayList<EFacultadPoder>();
			}
			oERepresentanteLegalSelected = oERepresentanteLegalItem;
			RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegalFacultadPoder').show();");
		}
	}
	
	public void agregarAccionTablaRepresentanteLegal(int codigoOrden){
		for (ERepresentanteLegal obj : lstRepresentanteLegal) {
			if(obj.getCodigoOrden() == codigoOrden) {
				if(obj.getCodigoRepresentante() != 0){
				obj.setCodigoAccion(UAccionTabla.EDITAR);
				}
			}
		}
	}
	
	public void agregarFacultadPoder(){
		if (oERepresentanteLegalSelected != null) {	
			this.oEFacultadPoderData = new EFacultadPoder();
			this.oEServicioSelected = new EServicio();
			this.oERepresentanteLegalRelacionSelected = new ERepresentanteLegal();
			
			accionInternaRepresenanteLegal = UAccionInterna.NUEVO;
			visualizarFrmRepresentanteRelacion = false;
			RequestContext.getCurrentInstance().execute("PF('dlgFacultadPoder').show();");
		}else{
			mensajeValidacion = "Seleccione un Representante";
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void eliminarFacultadPoder(EFacultadPoder oEFacultadPoderItem){
		if (oERepresentanteLegalSelected != null) {
			lstFacultadPoder.remove(oEFacultadPoderItem);
			oERepresentanteLegalSelected.setLstFacultadPoder(lstFacultadPoder);
			agregarAccionTablaRepresentanteLegal(oERepresentanteLegalSelected.getCodigoOrden());
		}
	}
	
	public void guardarFacultadPoder(){
		if (oERepresentanteLegalSelected != null) {	
		if (oEFacultadPoderData != null) {
			if (accionInternaFacultadPoder == UAccionInterna.NUEVO){
				int correlativo = 0;
				EFacultadPoder oEFacultadPoder = new EFacultadPoder();
				
				for(int i=0;i<lstFacultadPoder.size();i++){
					correlativo = lstFacultadPoder.get(i).getCodigoOrden();
				}
				oEFacultadPoder = oEFacultadPoderData;
				oEFacultadPoder.setCodigoOrden(correlativo+1);
				oEFacultadPoder.setCodigoOrdenRepresentante(oERepresentanteLegalSelected.getCodigoOrden());
				oEFacultadPoder.setDescripcionFacultad(UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado2(lstFacultad, oEFacultadPoderData.getCodigoFacultad(), true));
				oEFacultadPoder.setDescripcionOtorgamiento(UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado2(lstOtorgamiento, oEFacultadPoderData.getCodigoOtorgamiento(), true));
				oEFacultadPoder.setDescripcionTipoRelacion(UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado2(lstTipoRelacion, oEFacultadPoderData.getCodigoTipoRelacion(), true));
				if(oEFacultadPoderData.getCodigoTipoFirma()==UTipoFirma.INDIVIUAL){
					oEFacultadPoder.setDescripcionTipoFirma("Individual");
				}else if(oEFacultadPoderData.getCodigoTipoFirma()==UTipoFirma.MANCOMUNADO){
					oEFacultadPoder.setDescripcionTipoFirma("Mancomunado");
				}
				
				lstFacultadPoder.add(oEFacultadPoder);
				oERepresentanteLegalSelected.setLstFacultadPoder(lstFacultadPoder);
				agregarAccionTablaRepresentanteLegal(oERepresentanteLegalSelected.getCodigoOrden());
			}
			RequestContext.getCurrentInstance().execute("PF('dlgFacultadPoder').hide();");
		}
		}
	}
	
	//*************************************//
	//Metodos para Deudor
	//*************************************//
	
	public void listarDeudor(){
		lstDeudor = oBOSolicitudCredito.listarDeudor(oEOperacionClienteLoad.getCodigoCliente(), oEOperacionClienteLoad.getNumeroSolicitud());
		for(int i=0;i<lstDeudor.size();i++){
			EDeudorEstado oEDeudorEstado = new EDeudorEstado();
			EDeudorAdicional oEDeudorAdicional = new EDeudorAdicional();
			oEDeudorEstado = oBOSolicitudCredito.buscarDeudorEstado(lstDeudor.get(i).getCodigoDeudor());
			oEDeudorAdicional = oBOSolicitudCredito.buscarDeudorAdicional(lstDeudor.get(i).getCodigoDeudor());
			if (oEDeudorEstado == null) {
				EDeudorEstado oEDeudorEstado2 = new EDeudorEstado();
				//oEDeudorEstado2.setCodigoEstado("0");
				//oEDeudorEstado2.setObservacion("");
				lstDeudor.get(i).setEstado(oEDeudorEstado2);
			}else{
				lstDeudor.get(i).setEstado(oEDeudorEstado);
			}
			//lstDeudor.get(i).setEstado(oEDeudorEstado);
			
			if (oEDeudorAdicional == null) {
				EDeudorAdicional oEDeudorAdicional2 = new EDeudorAdicional();
				lstDeudor.get(i).setAdicional(oEDeudorAdicional2);
			}else{
				lstDeudor.get(i).setAdicional(oEDeudorAdicional);
			}
		}
	}
	
	public void agregarDeudor(){
		inicializarDeudor();
		accionInternaDeudor = UAccionInterna.NUEVO;
		deshabilitarFrmDeudor = true;
		listarUbigeoDeudor();
		filtrarEstadoDeudor();
		RequestContext.getCurrentInstance().execute("PF('dlgDeudor').show();");
	}
	
	public void modificarDeudor(EDeudor oEDeudorItem){
		if (oEDeudorItem != null) {
			inicializarDeudor();
			oEDeudorData = oEDeudorItem;
			codigoTipoDocumentoDeudor = oEDeudorItem.getTercero().getCodigoTipoDocumento();
			codigoCIIUDeudor = oEDeudorItem.getTercero().getCodigoCIIU();
			codigoTipoPagoDeudor = oEDeudorItem.getAdicional().getCodigoTipoPago();
			codigoFormaPagoDeudor = oEDeudorItem.getAdicional().getCodigoFormaPago();
			if(oEDeudorItem.getEstado() != null){
				codigoEstadoDeudor = oEDeudorItem.getEstado().getCodigoEstado();
				filtrarEstadoDeudor();
			}
			
			accionInternaDeudor = UAccionInterna.EDITAR;
			deshabilitarFrmDeudor = false;
			listarUbigeoDeudor();
			RequestContext.getCurrentInstance().execute("PF('dlgDeudor').show();");
		}
	}
	
	public String eliminarDeudor(EDeudor oEDeudorItem){
		if(oEDeudorItem.getIndicadorTemporal() == 0){
			lstDeudorRecycle.add(oEDeudorItem);
		}
		
		lstDeudor.remove(oEDeudorItem);
		return null;
	}
	
	public void guardarDeudor(){
		if (oEDeudorData != null) {
			if (accionInternaDeudor == UAccionInterna.NUEVO){
				//int correlativo = 0;
				EDeudor oEDeudor = new EDeudor();
				ETercero oETercero = new ETercero();
				EDeudorAdicional oEDeudorAdicional = new EDeudorAdicional();
				EDeudorEstado oEDeudorEstado = new EDeudorEstado();
				/*
				for(int i=0;i<lstDeudor.size();i++){
					correlativo = lstDeudor.get(i).getCodigoOrden();
				}
				oEDeudor.setCodigoOrden(correlativo+1);
				*/
				oEDeudor.setIndicadorTemporal(1);
				oEDeudor.setCodigoDeudor(oEDeudorData.getTercero().getCodigoCliente());
				oEDeudor.setNombreCorte(oEDeudorData.getTercero().getNombreCorte());
				oEDeudor.setNombreLargo(oEDeudorData.getTercero().getNombreLargo());
				
				oETercero = oEDeudorData.getTercero();
				oETercero.setCodigoTipoDocumento(codigoTipoDocumentoDeudor);
				oETercero.setCodigoCIIU(codigoCIIUDeudor);
				oETercero.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
															  UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoDeudor) +
															  UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaDeudor) +
															  UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoDeudor)));
				oETercero.setCodigoUbigeoPostal(UFuncionesGenerales.convierteCadenaAEntero(
																 UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoPostalDeudor) +
																 UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaPostalDeudor) +
																 UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoPostalDeudor)));
				
				oEDeudorAdicional = oEDeudorData.getAdicional();
				oEDeudorAdicional.setCodigoTipoPago(codigoTipoPagoDeudor);
				oEDeudorAdicional.setCodigoFormaPago(codigoFormaPagoDeudor);
				
				oEDeudorEstado = oEDeudorData.getEstado();
				oEDeudorEstado.setCodigoEstado(codigoEstadoDeudor);
				
				oEDeudor.setTercero(oETercero);
				oEDeudor.setAdicional(oEDeudorAdicional);
				oEDeudor.setEstado(oEDeudorEstado);
				
				lstDeudor.add(oEDeudor);
			}else if(accionInternaDeudor == UAccionInterna.EDITAR){
				for(int i=0;i<lstDeudor.size();i++){
				if (lstDeudor.get(i).getCodigoDeudor() == oEDeudorData.getTercero().getCodigoCliente()){
					ETercero oETercero = new ETercero();
					EDeudorAdicional oEDeudorAdicional = new EDeudorAdicional();
					EDeudorEstado oEDeudorEstado = new EDeudorEstado();
					
					lstDeudor.get(i).setNombreCorte(oEDeudorData.getTercero().getNombreCorte());
					lstDeudor.get(i).setNombreLargo(oEDeudorData.getTercero().getNombreLargo());
					
					oETercero = oEDeudorData.getTercero();
					oETercero.setCodigoTipoDocumento(codigoTipoDocumentoDeudor);
					oETercero.setCodigoCIIU(codigoCIIUDeudor);
					oETercero.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoDeudor) +
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaDeudor) +
																  UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoDeudor)));
					oETercero.setCodigoUbigeoPostal(UFuncionesGenerales.convierteCadenaAEntero(
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoPostalDeudor) +
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaPostalDeudor) +
																	 UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoPostalDeudor)));
					oEDeudorAdicional = oEDeudorData.getAdicional();
					oEDeudorAdicional.setCodigoTipoPago(codigoTipoPagoDeudor);
					oEDeudorAdicional.setCodigoFormaPago(codigoFormaPagoDeudor);
					
					oEDeudorEstado = oEDeudorData.getEstado();
					oEDeudorEstado.setCodigoEstado(codigoEstadoDeudor);
					
					lstDeudor.get(i).setTercero(oETercero);
					lstDeudor.get(i).setAdicional(oEDeudorAdicional);
					lstDeudor.get(i).setEstado(oEDeudorEstado);
				}
				}
			}
			RequestContext.getCurrentInstance().execute("PF('dlgDeudor').hide();");
		}
	}
	
	private void inicializarDeudor(){
		this.oEDeudorData = new EDeudor();
		ETercero oETercero = new ETercero();
		EDeudorAdicional oEDeudorAdicional = new EDeudorAdicional();
		EDeudorEstado oEDeudorEstado = new EDeudorEstado();
		
		oEDeudorData.setTercero(oETercero);
		oEDeudorData.setAdicional(oEDeudorAdicional);
		oEDeudorData.setEstado(oEDeudorEstado);
		
		codigoTipoDocumentoDeudor = "0";
		codigoCIIUDeudor = "";
		codigoTipoPagoDeudor = 0;
		codigoFormaPagoDeudor = 0;
		codigoEstadoDeudor = "0";
		codigoDepartamentoDeudor = 0;
		codigoProvinciaDeudor = 0;
		codigoDistritoDeudor = 0;
		codigoDepartamentoPostalDeudor = 0;
		codigoProvinciaPostalDeudor = 0;
		codigoDistritoPostalDeudor = 0;
		
		indicadorTabSeleccion = 0;
	}
	
	public void obtenerDepartamentoDeudor() {
		lstProvinciaDeudor = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoDeudor);
		lstDistritoDeudor = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoDeudor, 0);
	}
	
	public void obtenerProvinciaDeudor() {
		lstDistritoDeudor = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoDeudor, codigoProvinciaDeudor);
	}
	
	public void obtenerDepartamentoPostalDeudor() {
		lstProvinciaPostalDeudor = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoPostalDeudor);
		lstDistritoPostalDeudor = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalDeudor, 0);
	}
	
	public void obtenerProvinciaPostalDeudor() {
		lstDistritoPostalDeudor = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalDeudor, codigoProvinciaPostalDeudor);
	}
	
	public void listarUbigeoDeudor() {
		String codigoUbigeo = oEDeudorData.getTercero().getCodigoUbigeo()+"";
		String codigoUbigeoPostal = oEDeudorData.getTercero().getCodigoUbigeoPostal()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 5) {
			codigoDepartamentoDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 1));
			codigoProvinciaDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(1, 3));
			codigoDistritoDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 6) {
			codigoDepartamentoDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 2));
			codigoProvinciaDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(2, 4));
			codigoDistritoDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(4, 6));
		}
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoPostal).length() == 5) {
			codigoDepartamentoPostalDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(0, 1));
			codigoProvinciaPostalDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(1, 3));
			codigoDistritoPostalDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoPostal).length() == 6) {
			codigoDepartamentoPostalDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(0, 2));
			codigoProvinciaPostalDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(2, 4));
			codigoDistritoPostalDeudor = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(4, 6));
		}
		
		lstDepartamentoDeudor = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaDeudor = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoDeudor);
		lstDistritoDeudor = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoDeudor, codigoProvinciaDeudor);
		
		lstDepartamentoPostalDeudor = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaPostalDeudor = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoPostalDeudor);
		lstDistritoPostalDeudor = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalDeudor, codigoProvinciaPostalDeudor);
	}
	
	public void buscarTercero(){
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarTercero').show();");
	}
	
	public void buscarCIIU(){
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarCIIU').show();");
	}
	
	public void asignarTercero(){
		if (oETerceroSelected != null) {
			ETercero oETercero = new ETercero();
			EDeudorAdicional oEDeudorAdicional = new EDeudorAdicional();
			EDeudorEstado oEDeudorEstado = new EDeudorEstado();
			inicializarDeudor();
			
			oETercero = oBOCliente.buscarTercero(oETerceroSelected.getCodigo2());
			oEDeudorAdicional = oBOSolicitudCredito.buscarDeudorAdicional(oETerceroSelected.getCodigo2());
			oEDeudorEstado = oBOSolicitudCredito.buscarDeudorEstado(oETerceroSelected.getCodigo2());
			
			oEDeudorData.setTercero(oETercero);
			codigoTipoDocumentoDeudor = oETercero.getCodigoTipoDocumento();
			codigoCIIUDeudor = oETercero.getCodigoCIIU();
			
			if (oEDeudorAdicional == null){
				oEDeudorAdicional = new EDeudorAdicional();
			}
			oEDeudorData.setAdicional(oEDeudorAdicional);
			codigoTipoPagoDeudor = oEDeudorAdicional.getCodigoTipoPago();
			codigoFormaPagoDeudor = oEDeudorAdicional.getCodigoFormaPago();
			
			if (oEDeudorEstado == null){
				oEDeudorEstado = new EDeudorEstado();
			}
			oEDeudorData.setEstado(oEDeudorEstado);
			codigoEstadoDeudor = "0";
			filtrarEstadoDeudor();
			/*
			if(oEDeudorData.getEstado() != null){
				codigoEstadoDeudor = "0";
				filtrarEstadoDeudor();
			}
			*/
			listarUbigeoDeudor();
			deshabilitarFrmDeudor = false;
		}
	}
	
	public void asignarCIIU(){
		codigoCIIUDeudor = oECIIUSelected.getCodigo();
	}
	
	public void filtrarEstadoDeudor() {
		if (oEDeudorData != null) {
			lstEstadoDeudorFiltro = new ArrayList<EGeneral>();
			if (oEDeudorData.getEstado().getCodigoEstadoUltimo() == null){
		        for (EGeneral oEGeneral: lstEstadoDeudor) {
		            if (!oEGeneral.getCodigo().equals("D")) {
		            	lstEstadoDeudorFiltro.add(oEGeneral);
		            }
		        }
			}else{
		        for (EGeneral oEGeneral: lstEstadoDeudor) {
		            if (!oEGeneral.getCodigo().equals(oEDeudorData.getEstado().getCodigoEstadoUltimo())) {
		            	lstEstadoDeudorFiltro.add(oEGeneral);
		            }
		        }
			}
			/*
	        for (EGeneral oEGeneral: lstEstadoDeudor) {
	            if (!oEGeneral.getCodigo().equals(oEDeudorData.getEstado().getCodigoEstadoUltimo())) {
	            	lstEstadoDeudorFiltro.add(oEGeneral);
	            }
	        }
	        */
		}
	}
	
	//*************************************//
	//Metodos para Contratante
	//*************************************//
	public void visualizarFrmContrante() {
		if(oEOperacionClienteLoad.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
			visualizarFrmContratantePN = true;
			visualizarFrmContratantePJ = false;
			
			lstDOIContratanteFiltro = lstDOI.stream()
					.filter(x -> !x.getCodigo().equals(UTipoDocumento.RUC))
					.collect(Collectors.toList());
			
			oEOperacionClienteLoad.setCodigoTipoDocumento(UTipoDocumento.DNI);
		}else if(oEOperacionClienteLoad.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||  
				oEOperacionClienteLoad.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
			visualizarFrmContratantePN = false;
			visualizarFrmContratantePJ = true;
			
			lstDOIContratanteFiltro = lstDOI.stream()
					.filter(x -> x.getCodigo().equals(UTipoDocumento.RUC))
					.collect(Collectors.toList());
			
			oEOperacionClienteLoad.setCodigoTipoDocumento(UTipoDocumento.RUC);
		}
		validarTamanioDocumentoContratante();
	}
	
	public void validarTamanioDocumentoContratante(){
		if(oEOperacionClienteData.getCodigoTipoDocumento() != null){
			if(oEOperacionClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
				maxLgnNumeroDocumentoContratante = UMaximoTamanio.RUC_MAXLGN;
			}else if(oEOperacionClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.DNI) || oEOperacionClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.LIBRETA_ELECTORAL)){
				maxLgnNumeroDocumentoContratante = UMaximoTamanio.DNI_MAXLGN;
			}else {
				maxLgnNumeroDocumentoContratante = UMaximoTamanio.OTROS_MAXLGN;
			}
		}else {
			maxLgnNumeroDocumentoContratante = UMaximoTamanio.OTROS_MAXLGN;
		}
	}
	
	public void obtenerTipoDocumentoContratante() {
		validarTamanioDocumentoContratante();
		oEOperacionClienteData.setNumeroDocumento("");
	}

	public void obtenerIndicadorAval() {
		oEOperacionClienteData.setDescripcionAvalarTercero("");
		oEOperacionClienteData.setObservacionAvalarTercero("");
		visualizarObservacionAval();
	}
	
	public void obtenerIndicadorGrabarBien() {
		oEOperacionClienteData.setObservacionGrabarBien("");
		visualizarObservacionGrabarBien();
	}
	
	public void visualizarObservacionAval() {
		if (oEOperacionClienteData.getIndicadorAvalarTercero() != null){
			if (oEOperacionClienteData.getIndicadorAvalarTercero().equals(UFlagResultado.SI)){
				visualizarObservacionAval = true;
			}else{
				visualizarObservacionAval = false;
			}
		}
	}
	
	public void visualizarObservacionGrabarBien() {
		if (oEOperacionClienteData.getIndicadorGrabarBien() != null){
			if (oEOperacionClienteData.getIndicadorGrabarBien().equals(UFlagResultado.SI)){
				visualizarObservacionGrabarBien = true;
			}else{
				visualizarObservacionGrabarBien = false;
			}
		}
	}
	
	public void obtenerDepartamentoContractual() {
		codigoProvinciaContractual = 0;
		codigoDistritoContractual = 0;
		lstProvinciaContractual = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoContractual);
		lstDistritoContractual = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoContractual, codigoProvinciaContractual);
	}
	
	public void obtenerProvinciaContractual() {
		codigoDistritoContractual = 0;
		lstDistritoContractual = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoContractual, codigoProvinciaContractual);
	}
	
	public void obtenerDepartamentoReal() {
		codigoProvinciaReal = 0;
		codigoDistritoReal = 0;
		lstProvinciaReal = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoReal);
		lstDistritoReal = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoReal, codigoProvinciaReal);
	}
	
	public void obtenerProvinciaReal() {
		codigoDistritoReal = 0;
		lstDistritoReal = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoReal, codigoProvinciaReal);
	}
	
	public void listarUbigeoContratante() {
		String codigoUbigeoReal = oEOperacionClienteData.getCodigoUbigeoReal()+"";
		String codigoUbigeoContractual = oEOperacionClienteData.getCodigoUbigeoContractual()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoReal).length() == 5) {
			codigoDepartamentoReal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoReal.substring(0, 1));
			codigoProvinciaReal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoReal.substring(1, 3));
			codigoDistritoReal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoReal.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoReal).length() == 6) {
			codigoDepartamentoReal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoReal.substring(0, 2));
			codigoProvinciaReal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoReal.substring(2, 4));
			codigoDistritoReal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoReal.substring(4, 6));
		}
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoContractual).length() == 5) {
			codigoDepartamentoContractual = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoContractual.substring(0, 1));
			codigoProvinciaContractual = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoContractual.substring(1, 3));
			codigoDistritoContractual = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoContractual.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoContractual).length() == 6) {
			codigoDepartamentoContractual = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoContractual.substring(0, 2));
			codigoProvinciaContractual = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoContractual.substring(2, 4));
			codigoDistritoContractual = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoContractual.substring(4, 6));
		}
		
		lstDepartamentoReal = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaReal = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoReal);
		lstDistritoReal = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoReal, codigoProvinciaReal);
		
		lstDepartamentoContractual = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaContractual = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoContractual);
		lstDistritoContractual = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoContractual, codigoProvinciaContractual);
	}
	
	//*************************************//
	//Metodos para Suscripcion
	//*************************************//
	public void obtenerTipoPersonaJuridica(){
		filtrarTipoSuscripcionPago();
		obtenerTipoSuscripcionPago();
	}
	
	public void iniciarTipoPersonaJuridica(){
		filtrarTipoSuscripcionPago();
		visualizarTipoSuscripcionPago();
	}
	
	public void filtrarTipoSuscripcionPago(){
		if(oEOperacionClienteData.getCodigoTipoPersonaJuridica() != null){
		if(oEOperacionClienteData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SRL)){
			lstSuscripcionPagoFiltro = lstSuscripcionPago.stream()
					   .filter(x -> x.getCodigo().matches("(?i).*"+ UTipoSuscripcionPago.PARTICIPACIONISTAS +".*"))
					   .collect(Collectors.toList());
			oEOperacionClienteData.setCodigoTipoSuscripcionPago(UTipoSuscripcionPago.PARTICIPACIONISTAS);
		}else if (oEOperacionClienteData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SA) ||
				oEOperacionClienteData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SAC) ||
				oEOperacionClienteData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SAA)) {
			lstSuscripcionPagoFiltro = lstSuscripcionPago.stream()
					   .filter(x -> x.getCodigo().matches("(?i).*"+ UTipoSuscripcionPago.ACCIONISTAS +".*"))
					   .collect(Collectors.toList());
			oEOperacionClienteData.setCodigoTipoSuscripcionPago(UTipoSuscripcionPago.ACCIONISTAS);
		}else {
			lstSuscripcionPagoFiltro = lstSuscripcionPago;
		}
		}else {
			lstSuscripcionPagoFiltro = lstSuscripcionPago;
		}
	}
	
	public void listarSolicitudSuscripcion(){
		lstSuscripcion = oBOSolicitudCredito.listarSolicitudSuscripcion(oEOperacionClienteData.getNumeroSolicitud(), oEOperacionClienteData.getCodigoCliente(), oEOperacionClienteData.getCodigoTipoCliente());
	}
	
	public void listarClienteSuscripcion(){
		lstSuscripcion = oBOSolicitudCredito.listarClienteSuscripcion(oEOperacionClienteData.getNumeroDocumento());
	}
	
	public void obtenerTipoSuscripcionPago(){
		if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ACCIONISTAS)){
			oEInformeLegalAdicionalData.setDescripcionPatrimonio("");
			oEInformeLegalAdicionalData.setCodigoTipoNumeracionEstatuto(0);
			oEInformeLegalAdicionalData.setNumeracionEstatuto(0);
			oEInformeLegalAdicionalData.setAsiento("");
			oEInformeLegalAdicionalData.setFechaPeriodoInicio(null);
			oEInformeLegalAdicionalData.setFechaPeriodoVencimiento(null);
			oEInformeLegalAdicionalData.setDescripcionAporte("");
			visualizarFrmSuscripcion = true;
			visualizarFrmPatrimonio = false;
			visualizarDescripcionAporte = false;
			visualizarLblAccionista = true;
			visualizarLblParticipacionista = false;
		}else if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.PARTICIPACIONISTAS)){
			oEInformeLegalAdicionalData.setDescripcionPatrimonio("");
			oEInformeLegalAdicionalData.setCodigoTipoNumeracionEstatuto(0);
			oEInformeLegalAdicionalData.setNumeracionEstatuto(0);
			oEInformeLegalAdicionalData.setAsiento("");
			oEInformeLegalAdicionalData.setFechaPeriodoInicio(null);
			oEInformeLegalAdicionalData.setFechaPeriodoVencimiento(null);
			oEInformeLegalAdicionalData.setDescripcionAporte("");
			visualizarFrmSuscripcion = true;
			visualizarFrmPatrimonio = false;
			visualizarDescripcionAporte = false;
			visualizarLblAccionista = false;
			visualizarLblParticipacionista = true;
		}else if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.TITULARGERENTE)){
			
			oEOperacionClienteData.setNumeroAcciones(0);
			oEInformeLegalAdicionalData.setNumeroAcciones(0);
			oEInformeLegalAdicionalData.setCodigoTipoValorSuscripcion(0);
			oEInformeLegalAdicionalData.setMontoValorNominal(0);
			oEInformeLegalAdicionalData.setDescripcionPatrimonio("");
			oEInformeLegalAdicionalData.setCodigoTipoNumeracionEstatuto(0);
			oEInformeLegalAdicionalData.setNumeracionEstatuto(0);
			oEInformeLegalAdicionalData.setAsiento("");
			oEInformeLegalAdicionalData.setFechaPeriodoInicio(null);
			oEInformeLegalAdicionalData.setFechaPeriodoVencimiento(null);
			visualizarFrmSuscripcion = false;
			visualizarFrmPatrimonio = false;
			visualizarDescripcionAporte = true;
			visualizarLblAccionista = false;
			visualizarLblParticipacionista = false;
		}else if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJODIRECTIVO) ||
				oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJOADMINISTRACION) ||
				oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ASAMBLEAGENERAL) ||
				oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.GERENCIAGENERAL)){
			
			oEInformeLegalAdicionalData.setDescripcionConstitucion("");
			oEInformeLegalAdicionalData.setFechaConstitucion(null);
			oEInformeLegalAdicionalData.setDescripcionNotario("");
			oEOperacionClienteData.setNumeroAcciones(0);
			oEInformeLegalAdicionalData.setNumeroAcciones(0);
			oEInformeLegalAdicionalData.setCodigoTipoValorSuscripcion(0);
			oEInformeLegalAdicionalData.setMontoValorNominal(0);
			visualizarFrmSuscripcion = false;
			visualizarFrmPatrimonio = true;
			visualizarDescripcionAporte = false;	
			visualizarLblAccionista = false;
			visualizarLblParticipacionista = false;
		}
		limpiarSuscripcion();
	}
	
	public void visualizarTipoSuscripcionPago(){
		if(oEOperacionClienteData.getCodigoTipoSuscripcionPago() != null){
			if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ACCIONISTAS)){
				visualizarFrmSuscripcion = true;
				visualizarFrmPatrimonio = false;
				visualizarDescripcionAporte = false;
				visualizarLblAccionista = true;
				visualizarLblParticipacionista = false;
			}else if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.PARTICIPACIONISTAS)){
				visualizarFrmSuscripcion = true;
				visualizarFrmPatrimonio = false;
				visualizarDescripcionAporte = false;
				visualizarLblAccionista = false;
				visualizarLblParticipacionista = true;
			}else if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.TITULARGERENTE)){
				visualizarFrmSuscripcion = false;
				visualizarFrmPatrimonio = false;
				visualizarDescripcionAporte = true;
				visualizarLblAccionista = false;
				visualizarLblParticipacionista = false;
			}else if(oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJODIRECTIVO) ||
					oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJOADMINISTRACION) ||
					oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ASAMBLEAGENERAL) ||
					oEOperacionClienteData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.GERENCIAGENERAL)){
				visualizarFrmSuscripcion = false;
				visualizarFrmPatrimonio = true;
				visualizarDescripcionAporte = false;
				visualizarLblAccionista = false;
				visualizarLblParticipacionista = false;
			}
		}
	}
	
	public void limpiarNumeroSuscripcion(){
		//Si cambia de tipo valor se resetea de las filas el campo numero suscripcion
		for(int i=0;i<lstSuscripcion.size();i++){
			lstSuscripcion.get(i).setNumeroSuscripcion(0);
		}
	}
	
	public void limpiarSuscripcion(){
		lstSuscripcion = new ArrayList<ESuscripcion>();
		calcularNumeroSuscripcion();
	}
	
	public void calcularNumeroSuscripcion(){
		int suscripcionTotal = 0;
		for(int i=0;i<lstSuscripcion.size();i++){
			suscripcionTotal = suscripcionTotal + 1;
			lstSuscripcion.get(i).setCodigoOrden(suscripcionTotal);
		}
		oEOperacionClienteData.setNumeroAcciones(suscripcionTotal);
	}
	
	public boolean validarSuscripcion(){
		boolean ind=true;
		mensajeValidacion = "";
		if(oEInformeLegalAdicionalData != null){
			if(oEInformeLegalAdicionalData.getNumeroAcciones() <= 0){
				mensajeValidacion = "Ingrese número de acciones";
				ind = false;
			}
		}
        return ind;
	}
	
	public void agregarSuscripcion(){
		if(validarSuscripcion()){
			this.oESuscripcionData = new ESuscripcion();
			accionInternaSuscripcion = UAccionInterna.NUEVO;
			inicializarSuscripcion();
			RequestContext.getCurrentInstance().execute("PF('dlgSuscripcion').show();");
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void modificarSuscripcion(ESuscripcion oESuscripcionItem){
		if(validarSuscripcion()){
			if(oESuscripcionItem != null){
				this.oESuscripcionData = new ESuscripcion();
				accionInternaSuscripcion = UAccionInterna.EDITAR;
				oESuscripcionData = oESuscripcionItem;
				/*
				oESuscripcionData.setCodigoOrden(oESuscripcionItem.getCodigoOrden());
				oESuscripcionData.setNumeroSuscripcion(oESuscripcionItem.getNumeroSuscripcion());
				oESuscripcionData.setNombreLargo(oESuscripcionItem.getNombreLargo());
				*/
				inicializarSuscripcion();
				RequestContext.getCurrentInstance().execute("PF('dlgSuscripcion').show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void eliminarSuscripcion(ESuscripcion oESuscripcionItem){
		if (oESuscripcionItem != null) {
			lstSuscripcion.remove(oESuscripcionItem);
			calcularNumeroSuscripcion();
		}
	}

	public void guardarSuscripcion(){
		if (oESuscripcionData != null) {
			if (accionInternaSuscripcion == UAccionInterna.NUEVO){
				int correlativo = 0;
				ESuscripcion oESuscripcion = new ESuscripcion();
				for(int i=0;i<lstSuscripcion.size();i++){
					correlativo = lstSuscripcion.get(i).getCodigoOrden();
				}

				oESuscripcion = oESuscripcionData;
				oESuscripcion.setCodigoOrden(correlativo+1);
				
				lstSuscripcion.add(oESuscripcion);
				calcularNumeroSuscripcion();
			}else if(accionInternaSuscripcion == UAccionInterna.EDITAR){
				for(int i=0;i<lstSuscripcion.size();i++){
					if (lstSuscripcion.get(i).getCodigoOrden() == oESuscripcionData.getCodigoOrden()){
						lstSuscripcion.get(i).setNumeroSuscripcion(oESuscripcionData.getNumeroSuscripcion());
						lstSuscripcion.get(i).setNombreLargo(oESuscripcionData.getNombreLargo());
					}
				}
			}
			RequestContext.getCurrentInstance().execute("PF('dlgSuscripcion').hide();");
		}
	}
	
	public void inicializarSuscripcion(){
		int numeroSuscripcionTotal = 0;
		int numeroSuscripcionRestante = 0;
		
		if(lstSuscripcion != null){
			for(int i=0;i<lstSuscripcion.size();i++){
				numeroSuscripcionTotal = lstSuscripcion.get(i).getNumeroSuscripcion() + numeroSuscripcionTotal;
			}
		}
		
		numeroSuscripcionTotal = numeroSuscripcionTotal - oESuscripcionData.getNumeroSuscripcion();
		
		if(oEInformeLegalAdicionalData.getCodigoTipoValorSuscripcion() == UTipoValorSuscripcion.PORCENTAJE){
			numeroSuscripcionRestante = 100 - numeroSuscripcionTotal;
		}else if(oEInformeLegalAdicionalData.getCodigoTipoValorSuscripcion() == UTipoValorSuscripcion.NUMERO){
			numeroSuscripcionRestante = oEInformeLegalAdicionalData.getNumeroAcciones() - numeroSuscripcionTotal;
		}
		
		numeroSuscripcionMaximo = numeroSuscripcionRestante;
	}
	
	public void inicializar() {
		minimizarPnlRepresentanteLegal = true;
		minimizarPnlDeudor = true;
		minimizarPnlDocumento = true;
		
		visualizarBtnSalir = false;
		visualizarBtnGrabar = false;
		visualizarBtnAdjuntar = false;
		
		visualizarBtnAgregarSuscripcion = false;
		visualizarBtnModificarSuscripcion = false;
		visualizarBtnEliminarSuscripcion = false;
		visualizarBtnGrabarSuscripcion = false;
		
		visualizarBtnVisualizarRepresentanteLegal = false;
		visualizarBtnAgregarRepresentanteLegal = false;
		visualizarBtnModificarRepresentanteLegal = false;
		visualizarBtnEliminarRepresentanteLegal = false;
		visualizarBtnGrabarRepresentanteLegal = false;
		
		visualizarBtnVisualizarFacultad = false;
		visualizarBtnAgregarFacultad = false;
		visualizarBtnEliminarFacultad = false;
		visualizarBtnGrabarFacultad = false;
		
		visualizarBtnVisualizarDeudor = false;
		visualizarBtnAgregarDeudor = false;
		visualizarBtnModificarDeudor = false;
		visualizarBtnEliminarDeudor = false;
		visualizarBtnGrabarDeudor = false;
	}
	
	public void buscarSocio(){
		lstNotario = oBOCliente.listarNotarios(codigoBuscarPersona, descripcionBuscarPersona);
	}
	
	public void buscarNotario(){
		lstNotario = new ArrayList<ETercero>();
		codigoBuscarPersona = 0;
		descripcionBuscarPersona = "";
		indicadorDialogNotario = 1;
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarNotario').show();");
	}
	
	public void asignarNotario(){
		if(oENotarioSelected != null){
			if(indicadorDialogNotario ==1){
				oEInformeLegalAdicionalData.setCodigoNotario(oENotarioSelected.getCodigoCliente());	
				oEInformeLegalAdicionalData.setDescripcionNotario(oENotarioSelected.getNombreLargo());
			}else if(indicadorDialogNotario ==2){
			}
		}
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
	
	public EOperacionCliente getoEOperacionClienteData() {
		return oEOperacionClienteData;
	}

	public void setoEOperacionClienteData(
			EOperacionCliente oEOperacionClienteData) {
		this.oEOperacionClienteData = oEOperacionClienteData;
	}
	
	public EInformeLegalAdicional getoEInformeLegalAdicionalData() {
		return oEInformeLegalAdicionalData;
	}

	public void setoEInformeLegalAdicionalData(
			EInformeLegalAdicional oEInformeLegalAdicionalData) {
		this.oEInformeLegalAdicionalData = oEInformeLegalAdicionalData;
	}
	
	public ERepresentanteLegal getoERepresentanteLegalData() {
		return oERepresentanteLegalData;
	}

	public void setoERepresentanteLegalData(
			ERepresentanteLegal oERepresentanteLegalData) {
		this.oERepresentanteLegalData = oERepresentanteLegalData;
	}

	public EFacultadPoder getoEFacultadPoderData() {
		return oEFacultadPoderData;
	}

	public void setoEFacultadPoderData(EFacultadPoder oEFacultadPoderData) {
		this.oEFacultadPoderData = oEFacultadPoderData;
	}

	public EDeudor getoEDeudorData() {
		return oEDeudorData;
	}

	public void setoEDeudorData(EDeudor oEDeudorData) {
		this.oEDeudorData = oEDeudorData;
	}
	
	public ESuscripcion getoESuscripcionData() {
		return oESuscripcionData;
	}

	public void setoESuscripcionData(ESuscripcion oESuscripcionData) {
		this.oESuscripcionData = oESuscripcionData;
	}
	
	public ETercero getoETerceroData() {
		return oETerceroData;
	}
		
	public void setoETerceroData(ETercero oETerceroData) {
		this.oETerceroData = oETerceroData;
	}
	
	public EOperacionMensaje getoEOperacionMensajeSelected() {
		return oEOperacionMensajeSelected;
	}

	public void setoEOperacionMensajeSelected(
			EOperacionMensaje oEOperacionMensajeSelected) {
		this.oEOperacionMensajeSelected = oEOperacionMensajeSelected;
	}

	public ERepresentanteLegal getoERepresentanteLegalSelected() {
		return oERepresentanteLegalSelected;
	}

	public void setoERepresentanteLegalSelected(
			ERepresentanteLegal oERepresentanteLegalSelected) {
		this.oERepresentanteLegalSelected = oERepresentanteLegalSelected;
	}
	
	public EServicio getoEServicioSelected() {
		return oEServicioSelected;
	}

	public void setoEServicioSelected(EServicio oEServicioSelected) {
		this.oEServicioSelected = oEServicioSelected;
	}

	public ERepresentanteLegal getoERepresentanteLegalRelacionSelected() {
		return oERepresentanteLegalRelacionSelected;
	}

	public void setoERepresentanteLegalRelacionSelected(
			ERepresentanteLegal oERepresentanteLegalRelacionSelected) {
		this.oERepresentanteLegalRelacionSelected = oERepresentanteLegalRelacionSelected;
	}

	public EGeneral getoETerceroSelected() {
		return oETerceroSelected;
	}

	public void setoETerceroSelected(EGeneral oETerceroSelected) {
		this.oETerceroSelected = oETerceroSelected;
	}

	public EGeneral getoECIIUSelected() {
		return oECIIUSelected;
	}

	public void setoECIIUSelected(EGeneral oECIIUSelected) {
		this.oECIIUSelected = oECIIUSelected;
	}

	public EGeneral getoECargoLaboralSelected() {
		return oECargoLaboralSelected;
	}

	public void setoECargoLaboralSelected(EGeneral oECargoLaboralSelected) {
		this.oECargoLaboralSelected = oECargoLaboralSelected;
	}
	
	public ERepresentanteLegal getoERL1Selected() {
		return oERL1Selected;
	}

	public void setoERL1Selected(ERepresentanteLegal oERL1Selected) {
		this.oERL1Selected = oERL1Selected;
	}

	public ERepresentanteLegal getoERL2Selected() {
		return oERL2Selected;
	}

	public void setoERL2Selected(ERepresentanteLegal oERL2Selected) {
		this.oERL2Selected = oERL2Selected;
	}

	public ERepresentanteLegal getoERL3Selected() {
		return oERL3Selected;
	}

	public void setoERL3Selected(ERepresentanteLegal oERL3Selected) {
		this.oERL3Selected = oERL3Selected;
	}

	public ERepresentanteLegal getoERL4Selected() {
		return oERL4Selected;
	}

	public void setoERL4Selected(ERepresentanteLegal oERL4Selected) {
		this.oERL4Selected = oERL4Selected;
	}

	public ERepresentanteLegal getoERL5Selected() {
		return oERL5Selected;
	}

	public void setoERL5Selected(ERepresentanteLegal oERL5Selected) {
		this.oERL5Selected = oERL5Selected;
	}

	public ETercero getoENotarioSelected() {
		return oENotarioSelected;
	}

	public void setoENotarioSelected(ETercero oENotarioSelected) {
		this.oENotarioSelected = oENotarioSelected;
	}
	
	
	
}
