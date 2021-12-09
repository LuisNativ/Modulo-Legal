package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.CodingErrorAction;
import java.text.SimpleDateFormat;
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
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UCalificacion;
import com.abaco.negocio.util.UConstante.UClaseGarantia;
import com.abaco.negocio.util.UConstante.UDocumentoRequerido;
import com.abaco.negocio.util.UConstante.UMaximoTamanio;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoClienteSolicitudCredito;
import com.abaco.negocio.util.UConstante.UTipoPersona;
import com.abaco.negocio.util.UConstante.UCorreoEnvio;
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
import com.abaco.negocio.util.UConstante.UTipoServicio;
import com.abaco.negocio.util.UConstante.UTipoServicioPrestamo;
import com.abaco.negocio.util.UConstante.UTipoSuscripcionPago;
import com.abaco.negocio.util.UConstante.UTipoValorSuscripcion;
import com.abaco.negocio.util.UConstante.UTipologMovimiento;
import com.abaco.negocio.util.UConstante.UUbicacion;
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
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mbregistrooperacionsolicitudcredito")
@ViewScoped
public class MBRegistroOperacionSolicitudCredito implements Serializable {
	private static final long serialVersionUID = 1L;
	private EUsuario oEUsuario;
	private EMensaje oEMensaje;
	private EOperacionSolicitud oEOperacionSolicitudLoad;
	private EOpcion oEOpcionLoad;
	private ESolicitudCredito oESolicitudCreditoLoad;
	private EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegalLoad;
	
	private EOperacionSolicitud oEOperacionSolicitudData;
	private EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegalData;
	private EInformeLegalAdicional oEInformeLegalAdicionalData;
	private ECliente oEClienteData;
	private EClienteConstitucionEmpresa oEClienteConstitucionEmpresaData;
	private ERepresentanteLegal oERepresentanteLegalData;
	private EFacultadPoder oEFacultadPoderData;
	private EDeudor oEDeudorData;
	private EAval oEAvalData;
	private ESuscripcion oESuscripcionData;
	private ESolicitudCredito oESolicitudCreditoData;
	private ETercero oETerceroData;
	private EEmail oEmailData;
	private EGarantiaSolicitud oEGarantiaSolicitudData;
	private EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitudData;
	private EGarantiaDocumentoSolicitado oEGarantiaDocumentoSolicitadoData;
	private EOperacionDocumentoRevision oEOperacionDocumentoRevisionData;
	
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
	private EPersona oEPersonaSelected;
	private ETercero oENotarioSelected;
	
	private BOOperacion oBOOperacion;
	private BOGeneral oBOGeneral;
	private BOCliente oBOCliente;
	private BOSolicitudCredito oBOSolicitudCredito;
	private BORepresentanteLegal oBORepresentanteLegal;
	private BOGarantia oBOGarantia;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EDeudor> lstDeudorRecycle;
	
	@Getter @Setter private List<EOperacionMensaje> lstOperacionMensaje;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro;
	@Getter @Setter private List<EOperacionDocumentoRevision> lstOperacionDocumentoRevision;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private List<EDocumentoRequerido> lstDocumentoRequerido;
	@Getter @Setter private List<EDocumentoRequerido> lstDocumentoRequeridoFiltro;
	@Getter @Setter private List<ERepresentanteLegal> lstRepresentanteLegal;
	@Getter @Setter private List<ERepresentanteLegal> lstRepresentanteLegalRelacion;
	@Getter @Setter private List<EFacultadPoder> lstFacultadPoder;
	@Getter @Setter private List<ERepresentanteLegal> lstRL1;
	@Getter @Setter private List<ERepresentanteLegal> lstRL2;
	@Getter @Setter private List<ERepresentanteLegal> lstRL3;
	@Getter @Setter private List<ERepresentanteLegal> lstRL4;
	@Getter @Setter private List<ERepresentanteLegal> lstRL5;
	@Getter @Setter private List<EDeudor> lstDeudor;
	@Getter @Setter private List<EAval> lstAval;
	@Getter @Setter private List<ESuscripcion> lstSuscripcion;
	@Getter @Setter private List<ESolicitudCredito> lstSolCredito;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitud;
	@Getter @Setter private List<EGarantiaDocumentoSolicitado> lstGarantiaDocumentoSolicitado;
	@Getter @Setter private List<EObservacionNegocios> lstObservacionNegocios;
	@Getter @Setter private List<ESolicitudLogMovimiento> lstSolicitudLogMovimiento;
	@Getter @Setter private List<EEstado> lstEstado;
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
	@Getter @Setter private List<EGeneral> lstClaseVehiculo;
	@Getter @Setter private List<EGeneral> lstMarcaVehiculo;
	@Getter @Setter private List<EGeneral> lstModeloVehiculo;
	@Getter @Setter private List<EGeneral> lstCombustibleVehiculo;
	@Getter @Setter private List<EGeneral> lstRiesgoVehiculo;
	@Getter @Setter private List<EGeneral> lstTipoNumeracionEstatuto;
	@Getter @Setter private List<EGeneral> lstProveedor;
	@Getter @Setter private List<EGeneral> lstAceptante;
	@Getter @Setter private List<EGeneral> lstComunidad;
	@Getter @Setter private List<EGeneral> lstBancaUnidad;
	@Getter @Setter private List<EGeneral> lstZonaGeografica;
	
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
	@Getter @Setter private List<EGeneral> lstDepartamentoAval;
	@Getter @Setter private List<EGeneral> lstProvinciaAval;
	@Getter @Setter private List<EGeneral> lstDistritoAval;
	@Getter @Setter private List<EGeneral> lstDepartamentoPostalAval;
	@Getter @Setter private List<EGeneral> lstProvinciaPostalAval;
	@Getter @Setter private List<EGeneral> lstDistritoPostalAval;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia;
	@Getter @Setter private List<EGeneral> lstDepartamentoNotario;
	@Getter @Setter private List<EGeneral> lstProvinciaNotario;
	@Getter @Setter private List<EGeneral> lstDistritoNotario;
	@Getter @Setter private List<EGeneral> lstDepartamentoPostalNotario;
	@Getter @Setter private List<EGeneral> lstProvinciaPostalNotario;
	@Getter @Setter private List<EGeneral> lstDistritoPostalNotario;
	
	/* Variables Interfaz */
	
	//Datos de formulario General
	@Getter @Setter private String mensajeValidacion;
	@Getter @Setter private String mensajeConfirmacion;
	@Getter @Setter private String descripcionMensajeSeleccion;
	@Getter @Setter private String descripcionDocumentoCarga;
	
	@Getter @Setter private String nombreBuscar;
	@Getter @Setter private Date fechaMaximo;
	
	@Getter @Setter private int maxLgnNumeroDocumentoContratante;
	@Getter @Setter private int maxLgnNumeroDocumentoRepresentante;
	@Getter @Setter private Date maxDateFechaDocumentoRepresentante;
	
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
	
	//Datos de formulario Aval (Combobox)
	@Getter @Setter private String codigoTipoDocumentoAval;
	@Getter @Setter private String codigoCIIUAval;
	@Getter @Setter private int codigoDepartamentoAval;
	@Getter @Setter private int codigoProvinciaAval;
	@Getter @Setter private int codigoDistritoAval;
	@Getter @Setter private int codigoDepartamentoPostalAval;
	@Getter @Setter private int codigoProvinciaPostalAval;
	@Getter @Setter private int codigoDistritoPostalAval;
	
	//Datos de formulario Garantia Relacion
	@Getter @Setter private int codigoDepartamentoGarantia;
	@Getter @Setter private int codigoProvinciaGarantia;
	@Getter @Setter private int codigoDistritoGarantia;
	@Getter @Setter private double montoSolesTotalGarantia;
	@Getter @Setter private double montoDolaresTotalGarantia;
	
	//Datos de formulario Notario
	@Getter @Setter private int codigoDepartamentoNotario,codigoDepartamentoPostalNotario;
	@Getter @Setter private int codigoProvinciaNotario,codigoProvinciaPostalNotario;
	@Getter @Setter private int codigoDistritoNotario,codigoDistritoPostalNotario;	
	
	//Panel Informe
	@Getter @Setter private boolean deshabilitarObservacionSolicitud;
	@Getter @Setter private boolean deshabilitarObservacionLegal;
	@Getter @Setter private boolean deshabilitarEstado;
	@Getter @Setter private boolean visualizarEstado1;
	@Getter @Setter private boolean visualizarEstado2;
	@Getter @Setter private boolean visualizarFrmAutorizacion;
	@Getter @Setter private boolean visualizarObservacionNegocios;
	
	//Panel Contratante
	@Getter @Setter private boolean deshabilitarFrmContratante;
	@Getter @Setter private boolean visualizarApellidoPaternoContratante;
	@Getter @Setter private boolean visualizarApellidoMaternoContratante;
	@Getter @Setter private boolean visualizarNombreContratante;
	@Getter @Setter private boolean visualizarApellidoPaternoConyugue;
	@Getter @Setter private boolean visualizarApellidoMaternoConyugue;
	@Getter @Setter private boolean visualizarNombreConyugue;
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
	
	//Panel Detalle de Crédito 
	@Getter @Setter private boolean visualizarFrmCartaFianza;
	@Getter @Setter private boolean visualizarFrmDetalleCredito;
	
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
	
	//Dialog Deudor
	@Getter @Setter private boolean deshabilitarFrmDeudor;
	
	//Dialog Aval
	@Getter @Setter private boolean deshabilitarFrmAval;
	
	//Dialog Garantia Solicitud
	@Getter @Setter private boolean visualizarFrmGarantiaLiquida;
	@Getter @Setter private boolean visualizarFrmGarantiaReal;
	@Getter @Setter private boolean visualizarFrmGarantiaVehicular;
	@Getter @Setter private boolean visualizarFrmGarantiaPredio;
	@Getter @Setter private boolean visualizarFrmGarantiaMaquinaria;
	
	//Dialog Suscripcion
	@Getter @Setter private int numeroSuscripcionMaximo;
	
	//Dialog Documento Revision
	@Getter @Setter private boolean visualizarNombreDocumentoRevision;
	@Getter @Setter private boolean deshabilitarTipoDocumentoRevision;
	@Getter @Setter private boolean deshabilitarNombreDocumentoRevision;
	
	//Dialog Buscar Notario (Socio y/o Tercero)
	//@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private List<ETercero> lstNotario;
	@Getter @Setter private int codigoBuscarPersona;
	@Getter @Setter private String descripcionBuscarPersona;
	@Getter @Setter private boolean visualizarFrmNotarioPN;
	private int indicadorDialogNotario;
	
	//Dialog Envio Digitalizacion
	@Getter @Setter private boolean visualizarDescripcionMensajeDigitalizacion;
	
	//Dialog Desaprobar Solicitud
	@Getter @Setter private boolean visualizarDescripcionMotivo;
	
	//Indicadores
	@Getter @Setter private int indicadorDigitalizacion;
	private int indicadorTemporal;
	@Getter @Setter private int indicadorGuardar;
	@Getter @Setter private int indicadorTabSeleccion;
	@Getter @Setter private int indicadorTabSeleccionRepresentante;
	@Getter @Setter private int indicadorTblSeleccionRepresentante;
		
	//Control de Acciones
	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean visualizar;
	@Getter @Setter private boolean visualizarTabGarantia;
	@Getter @Setter private boolean visualizarTabCreditos;
	@Getter @Setter private boolean visualizarPnlContratante;
	@Getter @Setter private boolean visualizarPnlRepresentanteLegal;
	@Getter @Setter private boolean visualizarPnlDeudor;
	@Getter @Setter private boolean visualizarPnlAval;
	
	@Getter @Setter private boolean minimizarPnlRepresentanteLegal;
	@Getter @Setter private boolean minimizarPnlDeudor;
	@Getter @Setter private boolean minimizarPnlAval;
	@Getter @Setter private boolean minimizarPnlRevision;
	@Getter @Setter private boolean minimizarPnlDocumento;
	@Getter @Setter private boolean minimizarPnlDocumentoRevision;
	
	@Getter @Setter private boolean visualizarBtnSalir;
	@Getter @Setter private boolean visualizarBtnGrabar;
	@Getter @Setter private boolean visualizarBtnAdjuntar;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento;
	
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
	
	@Getter @Setter private boolean visualizarBtnAgregarDocumentoRevision;
	@Getter @Setter private boolean visualizarBtnEliminarDocumentoRevision;
	
	/* Variables Internas */	
	private long correlativoSesion;
	@Getter @Setter private int codigoEstado;
	@Getter @Setter private int codigoAutorizaJefe;
	@Getter @Setter private int codigoTipoCliente;
	
	private int accionExterna;
	@Getter @Setter private int accionInternaRepresenanteLegal;
	@Getter @Setter private int accionInternaFacultadPoder;
	@Getter @Setter private int accionInternaDeudor;
	@Getter @Setter private int accionInternaSuscripcion;
	@Getter @Setter private int accionInternaDocumentoRevision;

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	@Getter @Setter private List<UploadedFile> filesDocumentoRevision;
	
	private String rutaBaseFormato;
	private String rutaBaseFormatoPdf;
	private String rutaBasePlantilla;
	
	
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oEOperacionSolicitudLoad = new EOperacionSolicitud();
		this.oEOpcionLoad = new EOpcion();
		this.oESolicitudCreditoLoad = new ESolicitudCredito();
		this.oEEvaluacionSolicitudCreditoLegalLoad = new EEvaluacionSolicitudCreditoLegal();
		
		this.oEOperacionSolicitudData = new EOperacionSolicitud();
		this.oEEvaluacionSolicitudCreditoLegalData = new EEvaluacionSolicitudCreditoLegal();
		this.oEInformeLegalAdicionalData = new EInformeLegalAdicional();
		this.oEClienteData = new ECliente();
		this.oEClienteConstitucionEmpresaData = new EClienteConstitucionEmpresa();
		this.oERepresentanteLegalData = new ERepresentanteLegal();
		this.oEFacultadPoderData = new EFacultadPoder();
		this.oEDeudorData = new EDeudor();
		this.oEAvalData = new EAval();
		this.oESuscripcionData = new ESuscripcion();
		this.oESolicitudCreditoData = new ESolicitudCredito();
		this.oETerceroData = new ETercero();
		this.oEmailData = new EEmail();
		this.oEGarantiaSolicitudData = new EGarantiaSolicitud();
		this.oEGarantiaDetalleSolicitudData = new EGarantiaDetalleSolicitud();
		this.oEGarantiaDocumentoSolicitadoData = new EGarantiaDocumentoSolicitado();
		this.oEOperacionDocumentoRevisionData = new EOperacionDocumentoRevision();
		
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
		oBOGarantia = new BOGarantia();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstDeudorRecycle = new ArrayList<EDeudor>();
		
		lstOperacionMensaje = new ArrayList<EOperacionMensaje>();
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoRevision = new ArrayList<EOperacionDocumentoRevision>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		lstDocumentoRequerido = new ArrayList<EDocumentoRequerido>();
		lstDocumentoRequeridoFiltro = new ArrayList<EDocumentoRequerido>();
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
		lstGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaDocumentoSolicitado = new ArrayList<EGarantiaDocumentoSolicitado>();
		lstObservacionNegocios = new ArrayList<EObservacionNegocios>();
		lstSolicitudLogMovimiento = new ArrayList<ESolicitudLogMovimiento>();
		lstNotario = new ArrayList<ETercero>();
		lstEstado = new ArrayList<EEstado>();
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
		lstClaseVehiculo = new ArrayList<EGeneral>();
		lstMarcaVehiculo = new ArrayList<EGeneral>();
		lstModeloVehiculo = new ArrayList<EGeneral>();
		lstCombustibleVehiculo = new ArrayList<EGeneral>();
		lstRiesgoVehiculo = new ArrayList<EGeneral>();
		lstTipoNumeracionEstatuto = new ArrayList<EGeneral>();
		lstProveedor = new ArrayList<EGeneral>();
		lstAceptante = new ArrayList<EGeneral>();
		lstComunidad = new ArrayList<EGeneral>();
	    lstBancaUnidad = new ArrayList<EGeneral>();
	    lstZonaGeografica = new ArrayList<EGeneral>();
		lstSolCredito =  new ArrayList<ESolicitudCredito>();
		
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
		filesDocumentoRevision = new ArrayList<UploadedFile>();
		
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
				/*
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEOperacionSolicitudLoad = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oESolicitudCreditoLoad = oBOSolicitudCredito.buscarSolicitudCredito(oEOperacionSolicitudLoad.getCodigoSolicitudCredito());
					oEEvaluacionSolicitudCreditoLegalLoad = oBOSolicitudCredito.buscarEvaluacionSolicitudCreditoLegal(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona());
					
					oEOperacionSolicitudData = oEOperacionSolicitudLoad;
					oEEvaluacionSolicitudCreditoLegalData = oEEvaluacionSolicitudCreditoLegalLoad;
					
					visualizarBtnSalir = true;
					listarUbigeoContratante();
				}
				*/
			}else if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEOperacionSolicitudLoad = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEOpcionLoad = oBOOperacion.buscarOpcionPorSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud(), oEOperacionSolicitudLoad.getCodigoTipoEvaluacion(), oEUsuario);
					oEOperacionSolicitudLoad = oBOOperacion.buscarSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
					oEOperacionSolicitudData = oBOOperacion.buscarSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
					oESolicitudCreditoLoad = oBOSolicitudCredito.buscarSolicitudCredito(oEOperacionSolicitudLoad.getCodigoSolicitudCredito());
					oEEvaluacionSolicitudCreditoLegalLoad = oBOSolicitudCredito.buscarEvaluacionSolicitudCreditoLegal(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona());
					oEEvaluacionSolicitudCreditoLegalData = oBOSolicitudCredito.buscarEvaluacionSolicitudCreditoLegal(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona());
					oEInformeLegalAdicionalData = oBOSolicitudCredito.buscarInformeLegalAdicional(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona(), oEOperacionSolicitudLoad.getCodigoTipoClientePersona());
					
					if(oEOperacionSolicitudLoad.getCodigoTipoClientePersona() == UTipoClienteSolicitudCredito.COD_SOCIO){
						oEClienteData = oBOCliente.buscarSocio(oEOperacionSolicitudLoad.getCodigoClientePersona());
						oEClienteConstitucionEmpresaData = oBOCliente.buscarConstitucionEmpresa(oEOperacionSolicitudLoad.getCodigoClientePersona());
					}else if (oEOperacionSolicitudLoad.getCodigoTipoClientePersona() == UTipoClienteSolicitudCredito.COD_POSTULANTE){
						oEClienteData = oBOCliente.buscarPostulante(oEOperacionSolicitudLoad.getCodigoClientePersona());
						oEClienteConstitucionEmpresaData = oBOCliente.buscarConstitucionEmpresa(oEOperacionSolicitudLoad.getCodigoClientePersona());
					}
					
					oEEvaluacionSolicitudCreditoLegalLoad.setObservacionLegal(oBOSolicitudCredito.buscarObservacionDetalle(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), UUbicacion.LEGAL, 1));
					oEEvaluacionSolicitudCreditoLegalData.setObservacionLegal(oBOSolicitudCredito.buscarObservacionDetalle(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), UUbicacion.LEGAL, 1));
					//oEEvaluacionSolicitudCreditoLegalLoad.setObservacionNegocios(oBOSolicitudCredito.buscarObservacionNegocios(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), 0));
					
					if(oESolicitudCreditoLoad == null){ this.oESolicitudCreditoLoad = new ESolicitudCredito(); }
					if(oEInformeLegalAdicionalData == null){ this.oEInformeLegalAdicionalData = new EInformeLegalAdicional(); }
					
					if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoCliente() == UTipoClienteSolicitudCredito.COD_SOCIO){
						codigoTipoCliente = UTipoCliente.COD_SOCIO;
					}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoCliente() == UTipoClienteSolicitudCredito.COD_POSTULANTE){
						codigoTipoCliente = UTipoCliente.COD_POSTULANTE;
					}
					
					if(oEClienteData != null){
						oEEvaluacionSolicitudCreditoLegalData.setCodigoTipoDocumento(oEClienteData.getCodigoTipoDocumento());
						oEEvaluacionSolicitudCreditoLegalData.setNumeroDocumento(oEClienteData.getDocumento());
						oEEvaluacionSolicitudCreditoLegalData.setRuc(oEClienteData.getRuc());
						oEEvaluacionSolicitudCreditoLegalData.setApellidoPaterno(oEClienteData.getApellidoPaterno());
						oEEvaluacionSolicitudCreditoLegalData.setApellidoMaterno(oEClienteData.getApellidoMaterno());
						oEEvaluacionSolicitudCreditoLegalData.setNombre(oEClienteData.getNombre());
						oEEvaluacionSolicitudCreditoLegalData.setNombreLargo(oEClienteData.getNombreLargo2());
						oEEvaluacionSolicitudCreditoLegalData.setNumeroDocumento(oEClienteData.getDocumento());
						oEEvaluacionSolicitudCreditoLegalData.setDireccionReal(oEClienteData.getDireccion());
						oEEvaluacionSolicitudCreditoLegalData.setDireccionContractual(oEClienteData.getDireccion2());
						oEEvaluacionSolicitudCreditoLegalData.setCodigoUbigeoReal(oEClienteData.getCodigoUbigeo());
						oEEvaluacionSolicitudCreditoLegalData.setCodigoUbigeoContractual(oEClienteData.getCodigoUbigeo2());
						oEEvaluacionSolicitudCreditoLegalData.setCodigoTipoDocumentoConyugue(oEClienteData.getCodigoTipoDocumentoConyugue());
						oEEvaluacionSolicitudCreditoLegalData.setDocumentoConyugue(oEClienteData.getDocumentoConyugue());
						oEEvaluacionSolicitudCreditoLegalData.setApellidoPaternoConyugue(oEClienteData.getApellidoPaternoConyugue());
						oEEvaluacionSolicitudCreditoLegalData.setApellidoMaternoConyugue(oEClienteData.getApellidoMaternoConyugue());
						oEEvaluacionSolicitudCreditoLegalData.setNombreConyugue(oEClienteData.getNombreConyugue());
						oEEvaluacionSolicitudCreditoLegalData.setNombreLargoConyuge(oEClienteData.getNombreSuperLargoConyugue());
						oEInformeLegalAdicionalData.setCodigoEstadoCivil(oEClienteData.getCodigoEstadoCivil());
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
						codigoEstado = oEOperacionSolicitudLoad.getCodigoEstado();
						deshabilitarObservacionSolicitud = false;
						deshabilitarObservacionLegal = false;
						deshabilitarEstado = false;
						deshabilitarFrmContratante = false;
						visualizarEstado1 = false;
						visualizarEstado2 = true;
						visualizarObservacionNegocios = false;
						
						visualizarTabGarantia = true;
						visualizarTabCreditos = true;
						visualizarPnlContratante = true;
						visualizarPnlRepresentanteLegal = true;
						visualizarPnlDeudor = true;
						/*
						if(oESolicitudCreditoLoad.getCodigoServicio() == UTipoServicioPrestamo.SERV_991){
							visualizarPnlDeudor = true;
						}
						*/
						visualizarPnlAval = true;
						visualizarBtnAdjuntar = true;
						visualizarBtnGenerarDocumento = true;
					}else{
						if(oEOperacionSolicitudLoad.getCodigoAutorizaJefe() != 2){
							codigoEstado = UEstado.ENEVALUACION;
						}
						
						deshabilitarObservacionSolicitud = true;
						deshabilitarObservacionLegal = true;
						deshabilitarEstado = true;
						deshabilitarFrmContratante = true;
						visualizarEstado1 = true;
						visualizarEstado2 = false;
						
						visualizarTabGarantia = false;
						visualizarTabCreditos = false;
						visualizarPnlContratante = false;
						visualizarPnlRepresentanteLegal = false;
						visualizarPnlDeudor = false;
						visualizarPnlAval = false;
					}
					
					visualizarBtnSalir = true;
					visualizarBtnGrabar = true;
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
					visualizarBtnAgregarDocumentoRevision = true;
					visualizarBtnEliminarDocumentoRevision = true;
					
					if (oEOperacionSolicitudLoad.getCodigoEstado() == UEstado.OBSERVADO) {
						visualizarObservacionNegocios = true;
						visualizarBtnAdjuntar = true;
					}

					if(oEOperacionSolicitudLoad.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.AUTORIZADO ||
						oEOperacionSolicitudLoad.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.RECHAZADO || 
						oEOperacionSolicitudLoad.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.AUTORIZADOCOMPLETADO){
						visualizarFrmAutorizacion=true;
					}
					
					//correlativoSesion = oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONSESION, oEOperacionSolicitudLoad.getCodigoSolicitud()+"", "", "");
					
					listarUbigeoContratante();
					listarClienteSuscripcion();
					//enviarSesion();
				}
			}else if(UAccionExterna.VER == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEOperacionSolicitudLoad = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					//oEOpcionLoad = oBOOperacion.buscarOpcionPorSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud(), oEOperacionSolicitudLoad.getCodigoTipoEvaluacion(), oEUsuario);
					oEOperacionSolicitudLoad = oBOOperacion.buscarSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
					oEOperacionSolicitudData = oBOOperacion.buscarSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
					oESolicitudCreditoLoad = oBOSolicitudCredito.buscarSolicitudCredito(oEOperacionSolicitudLoad.getCodigoSolicitudCredito());
					oEEvaluacionSolicitudCreditoLegalLoad = oBOSolicitudCredito.buscarEvaluacionSolicitudCreditoLegal(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona());
					oEEvaluacionSolicitudCreditoLegalData = oBOSolicitudCredito.buscarEvaluacionSolicitudCreditoLegal(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona());
					oEInformeLegalAdicionalData = oBOSolicitudCredito.buscarInformeLegalAdicional(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), oEOperacionSolicitudLoad.getCodigoClientePersona(), oEOperacionSolicitudLoad.getCodigoTipoClientePersona());
					
					oEEvaluacionSolicitudCreditoLegalLoad.setObservacionLegal(oBOSolicitudCredito.buscarObservacionDetalle(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), UUbicacion.LEGAL, 1));
					oEEvaluacionSolicitudCreditoLegalData.setObservacionLegal(oBOSolicitudCredito.buscarObservacionDetalle(oEOperacionSolicitudLoad.getCodigoSolicitudCredito(), UUbicacion.LEGAL, 1));
					
					if(oEInformeLegalAdicionalData == null){ this.oEInformeLegalAdicionalData = new EInformeLegalAdicional(); }
					
					if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoCliente() == UTipoClienteSolicitudCredito.COD_SOCIO){
						codigoTipoCliente = UTipoCliente.COD_SOCIO;
					}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoCliente() == UTipoClienteSolicitudCredito.COD_POSTULANTE){
						codigoTipoCliente = UTipoCliente.COD_POSTULANTE;
					}
					
					deshabilitarObservacionSolicitud = true;
					deshabilitarObservacionLegal = true;
					deshabilitarEstado = true;
					deshabilitarFrmContratante = true;
					visualizarEstado1 = true;
					visualizarEstado2 = false;
					
					visualizarTabGarantia = false;
					visualizarTabCreditos = false;
					
					visualizarPnlContratante = true;
					visualizarPnlRepresentanteLegal = true;
					visualizarPnlDeudor = true;
					visualizarPnlAval = true;
					
					visualizarBtnSalir = true;
					visualizarBtnVisualizarRepresentanteLegal = true;
					visualizarBtnVisualizarFacultad = true;
					visualizarBtnVisualizarDeudor = true;
					
					if(oEOperacionSolicitudLoad.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.AUTORIZADO ||
							oEOperacionSolicitudLoad.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.RECHAZADO ||
							oEOperacionSolicitudLoad.getCodigoAutorizaJefe() == UEstadoAutorizacionJefe.AUTORIZADOCOMPLETADO){
							visualizarFrmAutorizacion=true;
					}
					
					listarUbigeoContratante();
					listarSolicitudSuscripcion();
				}
			}
			
			listarDesplegable();
			listarMensaje();
			listarMensajeTemporal();
			listarDocumento();
			listarDocumentoTemporal();
			listarDocumentoRevision();
			filtrarDocumento();
			listarRepresentanteLegal();
			listarDeudor();
			listarAval();
			//listarSuscripcion();
			listarGarantiaSolicitud();
			listarCreditoDocumentoSolicitado();
			listarSolicitudesCredito();
			listarObservacionNegocios();
			listarEstado();
			visualizarFrmContrante();
			visualizarObservacionAval();
			visualizarObservacionGrabarBien();
			visualizarTipoSuscripcionPago();
			iniciarTipoPersonaJuridica();
			
			if(lstRepresentanteLegal.size() > 0){
				minimizarPnlRepresentanteLegal = false;
			}
			if(lstDeudor.size() > 0){
				minimizarPnlDeudor = false;
			}
			if(lstAval.size() > 0){
				minimizarPnlAval = false;
			}
			if(lstOperacionDocumento.size() > 0){
				minimizarPnlDocumento = false;
			}
			if(lstOperacionDocumentoRevision.size() > 0){
				minimizarPnlDocumentoRevision = false;
			}
			if(lstOperacionMensaje.size() > 0){
				minimizarPnlRevision = false;
			}
		}
	}
	
	public void obtenerEstado(int ind){
		if(validar()){
			if(ind == 1){
				codigoAutorizaJefe = UEstadoAutorizacionJefe.SOLICITADO;
				//oEOperacionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.SOLICITADO);
				guardar();
			}else if(ind == 2){
				codigoAutorizaJefe = UEstadoAutorizacionJefe.RECHAZADO;
				//oEOperacionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.RECHAZADO);
				RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAutorizacion').show();");
			}else if(ind == 3){
				codigoAutorizaJefe = UEstadoAutorizacionJefe.AUTORIZADO;
				//oEOperacionSolicitudData.setCodigoAutorizaJefe(UEstadoAutorizacionJefe.AUTORIZADO);
				RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAutorizacion').show();");
			}else if(ind == 4){
				codigoAutorizaJefe = UEstadoAutorizacionJefe.AUTORIZADOCOMPLETADO;
				RequestContext.getCurrentInstance().execute("PF('dlgConfirmarAutorizacion').show();");
			}else{
				if(codigoEstado == UEstado.ENEVALUACION && oEUsuario.getCodigoArea() == UArea.LEGAL){
					indicadorTemporal = UIndicadorTemporal.SI;
				}
				guardar();
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void guardar() {
		generarCorrelativoDocumentoCarga();
		generarLogMovimiento();
		modificarDocumentoRelacionRepresentanteLegal();
		EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
		//EOperacionSesion oEOperacionSesion = new EOperacionSesion();
		EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegal = new EEvaluacionSolicitudCreditoLegal();
		EInformeLegalAdicional oEInformeLegalAdicional = new EInformeLegalAdicional();
		
		oEOperacionSolicitud = oEOperacionSolicitudData;
		
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			oEOperacionSolicitud.setDescripcionMensaje(oEEvaluacionSolicitudCreditoLegalData.getObservacionLegal());
		}else{
			if (codigoAutorizaJefe == UEstadoAutorizacionJefe.RECHAZADO || codigoAutorizaJefe == UEstadoAutorizacionJefe.AUTORIZADO){
				oEOperacionSolicitud.setDescripcionMensaje(oEOperacionSolicitudData.getObservacionAutorizacion());
			}else{
				oEOperacionSolicitud.setDescripcionMensaje(oEEvaluacionSolicitudCreditoLegalData.getObservacionNegocios());
			}
		}
		
		if (codigoAutorizaJefe > 0){
			oEOperacionSolicitud.setCodigoAutorizaJefe(codigoAutorizaJefe);
		}
		
		oEOperacionSolicitud.setCodigoEstado(codigoEstado);
		oEOperacionSolicitud.setIndicadorTemporal(indicadorTemporal);
		oEOperacionSolicitud.setIndicadorDigitalizacion(indicadorDigitalizacion);
		oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
		oEOperacionSolicitud.setLstOperacionDocumento(lstOperacionDocumento);
		oEOperacionSolicitud.setLstDocumentoCarga(lstDocumentoCarga);
		oEOperacionSolicitud.setLstOperacionDocumentoRevision(lstOperacionDocumentoRevision);
		
		oEOperacionSolicitud.setFechaRegistro(new Date());
		oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
		
		oEEvaluacionSolicitudCreditoLegal = oEEvaluacionSolicitudCreditoLegalData;
		oEEvaluacionSolicitudCreditoLegal.setCodigoUbigeoReal(UFuncionesGenerales.convierteCadenaAEntero(
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoReal) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaReal) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoReal)));
		oEEvaluacionSolicitudCreditoLegal.setCodigoUbigeoContractual(UFuncionesGenerales.convierteCadenaAEntero(
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoContractual) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaContractual) + 
												UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoContractual)));
		
		if (oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
			oEEvaluacionSolicitudCreditoLegal.setNombreLargo(oEEvaluacionSolicitudCreditoLegalData.getApellidoPaterno() +" "+ 
																oEEvaluacionSolicitudCreditoLegalData.getApellidoMaterno() +" "+ 
																oEEvaluacionSolicitudCreditoLegalData.getNombre());
			
			oEEvaluacionSolicitudCreditoLegal.setNombreLargoConyuge(oEEvaluacionSolicitudCreditoLegalData.getApellidoPaternoConyugue() +" "+ 
																		oEEvaluacionSolicitudCreditoLegalData.getApellidoMaternoConyugue() +" "+ 
																		oEEvaluacionSolicitudCreditoLegalData.getNombreConyugue());
		}
		
		oEInformeLegalAdicional = oEInformeLegalAdicionalData;
		oEInformeLegalAdicional.setNumeroSolicitud(oEEvaluacionSolicitudCreditoLegalData.getNumeroSolicitud());
		oEInformeLegalAdicional.setCodigoCliente(oEEvaluacionSolicitudCreditoLegalData.getCodigoCliente());
		oEInformeLegalAdicional.setCodigoTipoCliente(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoCliente());
		oEInformeLegalAdicional.setFechaRegistro(oEOperacionSolicitud.getFechaRegistro());
		oEInformeLegalAdicional.setUsuarioRegistro(oEUsuario);
		
		oEEvaluacionSolicitudCreditoLegal.setInformeLegalAdicional(oEInformeLegalAdicional);
		oEEvaluacionSolicitudCreditoLegal.setLstSuscripcion(lstSuscripcion);
		oEEvaluacionSolicitudCreditoLegal.setLstRepresentanteLegal(lstRepresentanteLegal);
		oEEvaluacionSolicitudCreditoLegal.setLstDeudor(lstDeudor);
		oEEvaluacionSolicitudCreditoLegal.setLstDeudorRecycle(lstDeudorRecycle);
		oEEvaluacionSolicitudCreditoLegal.setLstSolicitudLogMovimiento(lstSolicitudLogMovimiento);
		oEEvaluacionSolicitudCreditoLegal.setFechaRegistro(oEOperacionSolicitud.getFechaRegistro());
		oEEvaluacionSolicitudCreditoLegal.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.NUEVO == accionExterna){
			oEMensaje = oBOOperacion.modificarEvaluacionSolicitudCredito(oEOperacionSolicitud, oEEvaluacionSolicitudCreditoLegal, codigoTipoCliente);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarEvaluacionSolicitudCredito(oEOperacionSolicitud, oEEvaluacionSolicitudCreditoLegal, codigoTipoCliente);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	public boolean validar() {
		boolean ind=true;
		mensajeValidacion = "";
        return ind;
	}
	
	public void generarLogMovimiento() {
		int correlativo = 0;
		ESolicitudLogMovimiento oESolicitudLogMovimiento = new ESolicitudLogMovimiento();
		
		for(int i=0;i<lstRepresentanteLegal.size();i++){
			if(lstRepresentanteLegal.get(i).getCodigoAccion() == UAccionTabla.INSERTAR ||
				lstRepresentanteLegal.get(i).getCodigoAccion() == UAccionTabla.EDITAR ||
				lstRepresentanteLegal.get(i).getCodigoAccion() == UAccionTabla.ELIMINAR){
				
				oESolicitudLogMovimiento = new ESolicitudLogMovimiento();
				oESolicitudLogMovimiento.setCodigoOrden(correlativo +1);
				oESolicitudLogMovimiento.setCodigoAccion(UTipologMovimiento.CAMBIADTOREPRESENTANTE);
				lstSolicitudLogMovimiento.add(oESolicitudLogMovimiento);
				break;
			}
		}
		
		if(codigoEstado == UEstado.ENEVALUACION && oEUsuario.getCodigoArea() == UArea.LEGAL){
			oESolicitudLogMovimiento = new ESolicitudLogMovimiento();
			oESolicitudLogMovimiento.setCodigoOrden(correlativo +1);
			oESolicitudLogMovimiento.setCodigoAccion(UTipologMovimiento.ENEVALUACIONDELEGAL);
			lstSolicitudLogMovimiento.add(oESolicitudLogMovimiento);
		}else if(codigoEstado == UEstado.OBSERVADO && oEUsuario.getCodigoArea() == UArea.LEGAL){
			oESolicitudLogMovimiento = new ESolicitudLogMovimiento();
			oESolicitudLogMovimiento.setCodigoOrden(correlativo +1);
			oESolicitudLogMovimiento.setCodigoAccion(UTipologMovimiento.OBSERVADOPORLEGAL);
			lstSolicitudLogMovimiento.add(oESolicitudLogMovimiento);
		}else if (codigoEstado == UEstado.CERRADO && oEUsuario.getCodigoArea() == UArea.LEGAL) {
			oESolicitudLogMovimiento = new ESolicitudLogMovimiento();
			oESolicitudLogMovimiento.setCodigoOrden(correlativo +1);
			oESolicitudLogMovimiento.setCodigoAccion(UTipologMovimiento.EVALUADOPORLEGAL);
			lstSolicitudLogMovimiento.add(oESolicitudLogMovimiento);
		}
		
		if(!oEEvaluacionSolicitudCreditoLegalLoad.getNombreLargo().equals(oEEvaluacionSolicitudCreditoLegalData.getNombreLargo())){
			oESolicitudLogMovimiento = new ESolicitudLogMovimiento();
			oESolicitudLogMovimiento.setCodigoOrden(correlativo +1);
			oESolicitudLogMovimiento.setCodigoAccion(UTipologMovimiento.CAMBIADTOSOCIO);
			lstSolicitudLogMovimiento.add(oESolicitudLogMovimiento);
		}
	}
	
	public void controlarSesion() {
		enviarSesion();
	}
	
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
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			ruta = "BandejaOperacionLegal.xhtml";
		}else{
			ruta = "BandejaOperacionOtros.xhtml";
		}
		
		//enviarSesion();
		
		if(UAccionExterna.EDITAR == accionExterna) {
			//oBORevision.liberarSolicitudSesion(oEOperacionSolicitudLoad.getCodigoSolicitud());
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
		lstClaseVehiculo = oUManejadorListaDesplegable.obtieneClaseVehiculo();
		lstMarcaVehiculo = oUManejadorListaDesplegable.obtieneMarcaVehiculo();
		lstModeloVehiculo = oUManejadorListaDesplegable.obtieneModeloVehiculo();
		lstCombustibleVehiculo = oUManejadorListaDesplegable.obtieneCombustibleVehiculo();
		lstRiesgoVehiculo = oUManejadorListaDesplegable.obtieneRiesgoVehiculo();
		lstTipoNumeracionEstatuto = oUManejadorListaDesplegable.obtieneTipoNumeracionEstatuto();
		lstDocumentoRequerido = oBOGeneral.listarDocumentoRequeridoSolicitudCredito();
		lstDocumentoRequeridoFiltro = lstDocumentoRequerido;
		lstProveedor =oUManejadorListaDesplegable.obtieneProveedor();
		lstAceptante =oUManejadorListaDesplegable.obtieneAceptante();
		lstComunidad = oUManejadorListaDesplegable.obtieneComunidadOrigen();
	    lstBancaUnidad = oUManejadorListaDesplegable.obtieneUnidad();
	    lstZonaGeografica = oUManejadorListaDesplegable.obtieneZonaGeograficaF0713();
	}
	
	public void listarMensaje() {
		lstOperacionMensaje = oBOOperacion.listarMensaje(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarMensajeTemporal() {
		oEOperacionSolicitudData.setDescripcionMensaje(oBOOperacion.buscarMensajeTemporal(oEOperacionSolicitudLoad.getCodigoSolicitud()));
	}
	
	public void listarDocumento() {
		lstOperacionDocumento = oBOOperacion.listarDocumento(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarDocumentoTemporal() {
		lstDocumentoCarga = oBOOperacion.listarDocumentoTemporal(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarDocumentoRevision() {
		lstOperacionDocumentoRevision = oBOOperacion.listarDocumentoRevision(oEOperacionSolicitudLoad.getCodigoSolicitud());
		if(lstOperacionDocumentoRevision == null){
			lstOperacionDocumentoRevision = new ArrayList<EOperacionDocumentoRevision>();
		}
	}
	
	public void listarEstado() {
		lstEstado = oBOOperacion.listarEstadoPorSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud(), oEUsuario);
	}
	
	public void listarObservacionNegocios(){
		lstObservacionNegocios = oBOSolicitudCredito.listarObservacionNegociosDetalle(oEEvaluacionSolicitudCreditoLegalLoad.getNumeroSolicitud());
	}
	
	//*************************************//
	//Metodos para documento de carga
	//*************************************//
	
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
	//Metodos para Usuario Receptor
	//*************************************//
	
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
	
	//*************************************//
	//Metodos para Representante Legal
	//*************************************//
	
	public void listarRepresentanteLegal() {
		lstRepresentanteLegal = oBORepresentanteLegal.listarRepresentanteLegal(codigoTipoCliente, oEEvaluacionSolicitudCreditoLegalData.getCodigoCliente());
		List<EFacultadPoder> lstFacultadPoder = null;
		int correlativo = 0;
		for(int i=0;i<lstRepresentanteLegal.size();i++){
			correlativo = correlativo + 1;
			lstFacultadPoder = new ArrayList<EFacultadPoder>();
			lstFacultadPoder = oBORepresentanteLegal.listarFacultadPoder(codigoTipoCliente, oEEvaluacionSolicitudCreditoLegalData.getCodigoCliente(), lstRepresentanteLegal.get(i).getCodigoRepresentante());
			
			lstRepresentanteLegal.get(i).setCodigoOrden(correlativo);
			lstRepresentanteLegal.get(i).setLstFacultadPoder(lstFacultadPoder);
		}
		listarRepresentanteLegalFiltro(1);
	}
	
	public void listarRepresentanteLegalFiltro(int indicadorTabla) {		
		if(indicadorTabla == 1){
			lstRL1 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 2){
			lstRL2 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERL1Selected.getDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 3){
			lstRL3 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERL2Selected.getDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 4){
			lstRL4 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERL3Selected.getDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 5){
			lstRL5 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERL4Selected.getDocumento()))
					   .collect(Collectors.toList());
		}
		
		visualizarTblRepresentante(indicadorTabla);
	}
	
	public void buscarRepresentanteLegalFiltro(ERepresentanteLegal oERepresentanteLegalItem, int indicadorTabla) {
		if(indicadorTabla == 1){
			lstRL1 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 2){
			oERL1Selected = oERepresentanteLegalItem;
			lstRL2 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 3){
			oERL2Selected = oERepresentanteLegalItem;
			lstRL3 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 4){
			oERL3Selected = oERepresentanteLegalItem;
			lstRL4 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento()))
					   .collect(Collectors.toList());
		}else if(indicadorTabla == 5){
			oERL4Selected = oERepresentanteLegalItem;
			lstRL5 = lstRepresentanteLegal.stream()
					   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
					   .filter(x -> x.getDocumentoRelacion().equals(oERepresentanteLegalItem.getDocumento()))
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
			documento = oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento();
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
			oERepresentanteLegalData.setDocumentoHistorico(oERepresentanteLegalItem.getDocumento());
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
				}
				/*
				}else{
					mensajeValidacion = "No se puede registrar porque no se encontro documento relacionado a ese Representante.";
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
				}
				*/
				lstRepresentanteLegal.add(oERepresentanteLegal);
				listarRepresentanteLegalFiltro(indicadorTblSeleccionRepresentante);
				RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegal').hide();");
				
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
				
				String documento = oERepresentanteLegal.getDocumento();
				String documentoHistorico = oERepresentanteLegal.getDocumentoHistorico();
				if (!documento.equals(documentoHistorico)){
					List<ERepresentanteLegal> lista = new ArrayList<ERepresentanteLegal>();
					lista = lstRepresentanteLegal.stream()
							.filter(x -> x.getDocumentoRelacion().equals(documentoHistorico))
							.collect(Collectors.toList());
					for (ERepresentanteLegal obj : lista) {
						obj.setDocumentoRelacion(documento);
						lstRepresentanteLegal.set(obj.getCodigoOrden()-1, obj);
					}
				}
				
				for (ERepresentanteLegal obj : lstRepresentanteLegal) {
					if(obj.getCodigoOrden() == oERepresentanteLegalData.getCodigoOrden()) {
						lstRepresentanteLegal.set(obj.getCodigoOrden()-1, oERepresentanteLegal);
						//oERepresentanteLegalData = oERepresentanteLegal;
						//obj = oERepresentanteLegal;
					}
				}
				//lstRepresentanteLegal.set(oERepresentanteLegalData.getCodigoOrden(), oERepresentanteLegal);
				RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteLegal').hide();");
			}
		}
	}
	
	public void modificarDocumentoRelacionRepresentanteLegal() {
		String documento = oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento();
		for (ERepresentanteLegal obj : lstRL1) {
			if(obj.getCodigoAccion() != UAccionTabla.ELIMINAR){
				if(obj.getCodigoAccion() == UAccionTabla.INSERTAR){
					obj.setDocumentoRelacion(documento);
					lstRepresentanteLegal.set(obj.getCodigoOrden()-1, obj);
				}else if(obj.getCodigoAccion() == UAccionTabla.EDITAR){
					obj.setDocumentoRelacion(documento);
					lstRepresentanteLegal.set(obj.getCodigoOrden()-1, obj);
				}else{
					obj.setCodigoAccion(UAccionTabla.EDITAR);
					obj.setDocumentoRelacion(documento);
					lstRepresentanteLegal.set(obj.getCodigoOrden()-1, obj);
				}
			}
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
	
	public void buscarSocio(){
		//lstPersona = oBOCliente.listarSocioyTercero(codigoBuscarPersona, descripcionBuscarPersona);
		lstNotario = oBOCliente.listarNotarios(codigoBuscarPersona, descripcionBuscarPersona);
	}
	
	public void buscarNotario(){
		//lstPersona = new ArrayList<EPersona>();
		lstNotario = new ArrayList<ETercero>();
		codigoBuscarPersona = 0;
		descripcionBuscarPersona = "";
		indicadorDialogNotario = 1;
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarNotario').show();");
	}
	
	public void asignarCargoLaboral(){
		oERepresentanteLegalData.setCargoLaboral(oECargoLaboralSelected.getDescripcion());;
	}
	
	/*
	public void asignarPersona(){
		if(oEPersonaSelected != null){
			if(indicadorDialogNotario ==1){
				oEInformeLegalAdicionalData.setCodigoNotario(oEPersonaSelected.getCodigo());
				oEInformeLegalAdicionalData.setDescripcionNotario(oEPersonaSelected.getNombreLargo());
			}else if(indicadorDialogNotario ==2){
			}
		}
	}
	*/
	
	public void asignarNotario(){
		if(oENotarioSelected != null){
			if(indicadorDialogNotario ==1){
				oEInformeLegalAdicionalData.setCodigoNotario(oENotarioSelected.getCodigoCliente());	
				oEInformeLegalAdicionalData.setDescripcionNotario(oENotarioSelected.getNombreLargo());
			}else if(indicadorDialogNotario ==2){
			}
		}
	}
	
	public void agregarNotario(){
		inicializarNotario();
		listarUbigeoNotario();
		listarUbigeoPostalNotario();
		RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoNotario').show();");
	}
	
	public void modificarNotario(ETercero oETerceroItem){
		if(oETerceroItem != null){
			inicializarNotario();
			oETerceroData = oBOCliente.buscarTercero(oETerceroItem.getCodigoCliente());
			
			EEmail oEmail = oBOSolicitudCredito.buscarEmailCliente(oETerceroItem.getCodigoCliente());
			ETercero oEClienteInforPerNatural = oBOCliente.buscarCliente_Info_PersonaNatural(oETerceroItem.getCodigoCliente());
			
			if(oEmail != null){
				oETerceroData.setEmail1(oEmail.getEmail1());
				oETerceroData.setEmail2(oEmail.getEmail2());
			}
			
			if(oEClienteInforPerNatural != null){
				oETerceroData.setCodigoEstadoCivil(oEClienteInforPerNatural.getCodigoEstadoCivil());
				oETerceroData.setCodigoSexo(oEClienteInforPerNatural.getCodigoSexo());
			}
			validarClasePersona();
			listarUbigeoNotario();
			listarUbigeoPostalNotario();
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoNotario').show();");
		}
	}
	
	public void validarClasePersona(){
		if(oETerceroData.getCodigoTipoPersona().equals("N")){
			visualizarFrmNotarioPN = true;
		}else{
			visualizarFrmNotarioPN = false;
		}
	}
	
	public void guardarNotario(){
		if(oETerceroData != null){
			ETercero oETercero = new ETercero();
			oETercero = oETerceroData;
			oETercero.setUsuarioRegistro(oEUsuario);
			oETercero.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoNotario) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaNotario) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoNotario))));
			oETercero.setCodigoUbigeoPostal((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoPostalNotario) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaPostalNotario) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoPostalNotario))));
			
			if(oETercero.getCodigoCliente()!=0){
				oEMensaje = oBOCliente.modificarTercero(oETercero);
				
			}else{
				oEMensaje = oBOCliente.registrarTercero(oETercero);
			}
			
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			buscarSocio();
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoNotario').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}
	}
	
	private void inicializarNotario(){
		oETerceroData = new ETercero();
		codigoDepartamentoNotario=0;
		codigoProvinciaNotario=0;
		codigoDistritoNotario=0;
		codigoDepartamentoPostalNotario=0;
		codigoProvinciaPostalNotario=0;
		codigoDistritoPostalNotario=0;
	}
	
	public void obtenerDepartamentoNotario() {
		codigoProvinciaNotario = 0;
		codigoDistritoNotario = 0;
		lstProvinciaNotario = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoNotario);
		lstDistritoNotario = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoNotario, codigoProvinciaNotario);
	}
	
	public void obtenerProvinciaNotario() {
		codigoDistritoNotario = 0;
		lstDistritoNotario = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoNotario, codigoProvinciaNotario);
	}
	
	public void obtenerDepartamentoPostalNotario() {
		codigoProvinciaPostalNotario = 0;
		codigoDistritoPostalNotario = 0;
		lstProvinciaPostalNotario = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoPostalNotario);
		lstDistritoPostalNotario = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalNotario, codigoProvinciaPostalNotario);
	}
	
	public void obtenerProvinciaPostalNotario() {
		codigoDistritoPostalNotario = 0;
		lstDistritoPostalNotario = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalNotario, codigoProvinciaPostalNotario);
	}
	
	public void listarUbigeoNotario() {
		String codigoUbigeoNotario = oETerceroData.getCodigoUbigeo()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoNotario).length() == 5) {
			codigoDepartamentoNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoNotario.substring(0, 1));
			codigoProvinciaNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoNotario.substring(1, 3));
			codigoDistritoNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoNotario.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoNotario).length() == 6) {
			codigoDepartamentoNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoNotario.substring(0, 2));
			codigoProvinciaNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoNotario.substring(2, 4));
			codigoDistritoNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoNotario.substring(4, 6));
		}
		
		lstDepartamentoNotario = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaNotario = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoNotario);
		lstDistritoNotario = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoNotario, codigoProvinciaNotario);
	}
	
	public void listarUbigeoPostalNotario() {
		String codigoUbigeoPostalNotario = oETerceroData.getCodigoUbigeoPostal()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoPostalNotario).length() == 5) {
			codigoDepartamentoPostalNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostalNotario.substring(0, 1));
			codigoProvinciaPostalNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostalNotario.substring(1, 3));
			codigoDistritoPostalNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostalNotario.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoPostalNotario).length() == 6) {
			codigoDepartamentoPostalNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostalNotario.substring(0, 2));
			codigoProvinciaPostalNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostalNotario.substring(2, 4));
			codigoDistritoPostalNotario = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostalNotario.substring(4, 6));
		}
		
		lstDepartamentoPostalNotario = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaPostalNotario = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoPostalNotario);
		lstDistritoPostalNotario = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalNotario, codigoProvinciaPostalNotario);
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
		lstDeudor = oBOSolicitudCredito.listarDeudor(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoCliente(), oEEvaluacionSolicitudCreditoLegalLoad.getNumeroSolicitud());
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
	
	public void visualizarDeudor(EDeudor oEDeudorItem){
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
			
			accionInternaDeudor = UAccionInterna.VER;
			deshabilitarFrmDeudor = true;
			listarUbigeoDeudor();
			RequestContext.getCurrentInstance().execute("PF('dlgDeudor').show();");
		}
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
		if(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
			visualizarFrmContratantePN = true;
			visualizarFrmContratantePJ = false;
			
			if(UAccionExterna.EDITAR == accionExterna){
				visualizarApellidoPaternoContratante = true;
				visualizarApellidoMaternoContratante = true;
				visualizarNombreContratante = true;
				visualizarApellidoPaternoConyugue = true;
				visualizarApellidoMaternoConyugue = true;
				visualizarNombreConyugue = true;
			}else if(UAccionExterna.VER == accionExterna){
				visualizarApellidoPaternoContratante = false;
				visualizarApellidoMaternoContratante = false;
				visualizarNombreContratante = false;
				visualizarApellidoPaternoConyugue = false;
				visualizarApellidoMaternoConyugue = false;
				visualizarNombreConyugue = false;
			}
			
			lstDOIContratanteFiltro = lstDOI.stream()
					.filter(x -> !x.getCodigo().equals(UTipoDocumento.RUC))
					.collect(Collectors.toList());
			
			oEEvaluacionSolicitudCreditoLegalLoad.setCodigoTipoDocumento(UTipoDocumento.DNI);
		}else if(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||  
				oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
			visualizarFrmContratantePN = false;
			visualizarFrmContratantePJ = true;
			
			lstDOIContratanteFiltro = lstDOI.stream()
					.filter(x -> x.getCodigo().equals(UTipoDocumento.RUC))
					.collect(Collectors.toList());
			
			oEEvaluacionSolicitudCreditoLegalLoad.setCodigoTipoDocumento(UTipoDocumento.RUC);
		}
		validarTamanioDocumentoContratante();
	}
	
	public void validarTamanioDocumentoContratante(){
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoDocumento() != null){
			if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
				maxLgnNumeroDocumentoContratante = UMaximoTamanio.RUC_MAXLGN;
			}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoDocumento().equals(UTipoDocumento.DNI) || oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoDocumento().equals(UTipoDocumento.LIBRETA_ELECTORAL)){
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
		oEEvaluacionSolicitudCreditoLegalData.setNumeroDocumento("");
	}

	public void obtenerIndicadorAval() {
		oEEvaluacionSolicitudCreditoLegalData.setDescripcionAvalarTercero("");
		oEEvaluacionSolicitudCreditoLegalData.setObservacionAvalarTercero("");
		visualizarObservacionAval();
	}
	
	public void obtenerIndicadorGrabarBien() {
		oEEvaluacionSolicitudCreditoLegalData.setObservacionGrabarBien("");
		visualizarObservacionGrabarBien();
	}
	
	public void visualizarObservacionAval() {
		if (oEEvaluacionSolicitudCreditoLegalData.getIndicadorAvalarTercero() != null){
			if (oEEvaluacionSolicitudCreditoLegalData.getIndicadorAvalarTercero().equals(UFlagResultado.SI)){
				visualizarObservacionAval = true;
			}else{
				visualizarObservacionAval = false;
			}
		}
	}
	
	public void visualizarObservacionGrabarBien() {
		if (oEEvaluacionSolicitudCreditoLegalData.getIndicadorGrabarBien() != null){
			if (oEEvaluacionSolicitudCreditoLegalData.getIndicadorGrabarBien().equals(UFlagResultado.SI)){
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
		String codigoUbigeoReal = oEEvaluacionSolicitudCreditoLegalData.getCodigoUbigeoReal()+"";
		String codigoUbigeoContractual = oEEvaluacionSolicitudCreditoLegalData.getCodigoUbigeoContractual()+"";
		
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
	//Metodos para Garantia Relacion
	//*************************************//
	
	public void listarGarantiaSolicitud() {
		lstGarantiaSolicitud = oBOGarantia.listarGarantiaSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitudCredito());
		calcularMontoTotalGarantia();
	}
	
	public void calcularMontoTotalGarantia() {
		montoSolesTotalGarantia = 0;
		montoDolaresTotalGarantia = 0;
		for(EGarantiaSolicitud oEGarantiaSolicitud: lstGarantiaSolicitud){
			if(oEGarantiaSolicitud.getCodigoMonedaGarantia() == UMoneda.COD_SOLES){
				montoSolesTotalGarantia = montoSolesTotalGarantia + oEGarantiaSolicitud.getMontoGarantia();
			}
			if(oEGarantiaSolicitud.getCodigoMonedaGarantia() == UMoneda.COD_DOLARES){
				montoDolaresTotalGarantia = montoDolaresTotalGarantia + oEGarantiaSolicitud.getMontoGarantia();
			}
		}
	}
	
	public void visualizarGarantiaSolicitud(EGarantiaSolicitud oEGarantiaSolicitudItem){
		if (oEGarantiaSolicitudItem != null) {
			inicializarGarantiaSolicitud();
			oEGarantiaSolicitudData = oEGarantiaSolicitudItem;
			
			if(oEGarantiaSolicitudItem.getCodigoTipoGarantia() == UClaseGarantia.LIQUIDAS){
				visualizarFrmGarantiaLiquida = true;
			}else if(oEGarantiaSolicitudItem.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS || oEGarantiaSolicitudItem.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES){
				visualizarFrmGarantiaReal = true;
				
				if(oEGarantiaSolicitudItem.getCodigoTipoGarantiaReal() == UTipoGarantia.VEHICULAR){
					oEGarantiaDetalleSolicitudData = oBOGarantia.buscarGarantiaDetalleSolicitud(oEGarantiaSolicitudItem.getNumeroSolicitud(), oEGarantiaSolicitudItem.getSecuenciaGarantia());
					visualizarFrmGarantiaVehicular = true;
				}else if(oEGarantiaSolicitudItem.getCodigoTipoGarantiaReal() == UTipoGarantia.PREDIO){
					oEGarantiaDetalleSolicitudData = oBOGarantia.buscarGarantiaDetalleSolicitud(oEGarantiaSolicitudItem.getNumeroSolicitud(), oEGarantiaSolicitudItem.getSecuenciaGarantia());
					visualizarFrmGarantiaPredio = true;
					listarUbigeoGarantia();
				}else if(oEGarantiaSolicitudItem.getCodigoTipoGarantiaReal() == UTipoGarantia.MAQUINARIA){
					oEGarantiaDetalleSolicitudData = oBOGarantia.buscarGarantiaDetalleSolicitud(oEGarantiaSolicitudItem.getNumeroSolicitud(), oEGarantiaSolicitudItem.getSecuenciaGarantia());
					visualizarFrmGarantiaMaquinaria = true;
				}
			}
			
			RequestContext.getCurrentInstance().execute("PF('dlgGarantiaRelacion').show();");
		}
	}
	
	private void inicializarGarantiaSolicitud(){
		visualizarFrmGarantiaLiquida = false;
		visualizarFrmGarantiaReal = false;
		visualizarFrmGarantiaVehicular = false;
		visualizarFrmGarantiaPredio = false;
		visualizarFrmGarantiaMaquinaria = false;
	}
	
	public void listarUbigeoGarantia() {
		String codigoUbigeoGarantia = oEGarantiaDetalleSolicitudData.getCodigoUbigeo()+"";
		
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
	//Metodos para Documento Solicitado
	//*************************************//
	
	public void listarCreditoDocumentoSolicitado() {
		lstGarantiaDocumentoSolicitado = oBOGarantia.listarDocumentoSolicitado(oEOperacionSolicitudLoad.getCodigoSolicitudCredito());
	}
	
	public void visualizarGarantiaDocumentoSolicitado(EGarantiaDocumentoSolicitado oEGarantiaDocumentoSolicitadoItem){
		if (oEGarantiaDocumentoSolicitadoItem != null) {
			oEGarantiaDocumentoSolicitadoData = oEGarantiaDocumentoSolicitadoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgGarantiaDocumentoSolicitado').show();");
		}
	}
	
	//*************************************//
	//Metodos para AVAL 
	//*************************************//	
	
	public void listarAval(){
		lstAval = oBOSolicitudCredito.listarAval(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoCliente(), oEEvaluacionSolicitudCreditoLegalLoad.getNumeroSolicitud());
		
	}
	
	public void visualizarAval(EAval oEAvalItem){
		if (oEAvalItem != null) {
			inicializarAval();
			oEAvalData = oEAvalItem; 
			
			oETerceroData = oBOSolicitudCredito.buscarAval(oEAvalData.getCodigoAval(), oEEvaluacionSolicitudCreditoLegalLoad.getNumeroSolicitud());
						
			oEmailData = oBOSolicitudCredito.buscarEmailCliente(oETerceroData.getCodigoCliente());
			if(oEmailData == null){
				oEmailData = new EEmail();
				oEmailData.setEmail1("");
				oEmailData.setEmail2("");
			}
			codigoTipoDocumentoAval = oETerceroData.getCodigoTipoDocumento();
			codigoCIIUAval = oETerceroData.getCodigoCIIU();
			
			deshabilitarFrmAval = true;
			listarUbigeoAval();
			RequestContext.getCurrentInstance().execute("PF('dlgAval').show();");
		}
	}
	
	
	private void inicializarAval(){
		this.oEAvalData = new EAval();
		this.oEmailData = new EEmail();
		codigoTipoDocumentoAval = "0";
		codigoCIIUAval = "";
		codigoDepartamentoAval = 0;
		codigoProvinciaAval = 0;
		codigoDistritoAval = 0;
		codigoDepartamentoPostalAval = 0;
		codigoProvinciaPostalAval = 0;
		codigoDistritoPostalAval = 0;
		
		indicadorTabSeleccion = 0;
	}
	
	public void obtenerDepartamentoAval() {
		lstProvinciaAval = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoAval);
		lstDistritoAval = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoAval, 0);
	}
	
	public void obtenerProvinciaAval() {
		lstDistritoAval = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoAval, codigoProvinciaAval);
	}
	
	public void obtenerDepartamentoPostalAval() {
		lstProvinciaPostalAval = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoPostalAval);
		lstDistritoPostalAval = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalAval, 0);
	}
	
	public void obtenerProvinciaPostalAval() {
		lstDistritoPostalAval = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalAval, codigoProvinciaPostalAval);
	}
	
	public void listarUbigeoAval() {
		String codigoUbigeo = oETerceroData.getCodigoUbigeo()+"";
		String codigoUbigeoPostal = oETerceroData.getCodigoUbigeoPostal()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 5) {
			codigoDepartamentoAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 1));
			codigoProvinciaAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(1, 3));
			codigoDistritoAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 6) {
			codigoDepartamentoAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 2));
			codigoProvinciaAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(2, 4));
			codigoDistritoAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(4, 6));
		}
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoPostal).length() == 5) {
			codigoDepartamentoPostalAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(0, 1));
			codigoProvinciaPostalAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(1, 3));
			codigoDistritoPostalAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoPostal).length() == 6) {
			codigoDepartamentoPostalAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(0, 2));
			codigoProvinciaPostalAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(2, 4));
			codigoDistritoPostalAval = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoPostal.substring(4, 6));
		}
		
		lstDepartamentoAval = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaAval = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoAval);
		lstDistritoAval = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoAval, codigoProvinciaAval);
		
		lstDepartamentoPostalAval = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaPostalAval = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoPostalAval);
		lstDistritoPostalAval = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoPostalAval, codigoProvinciaPostalAval);
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
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersonaJuridica() != null){
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SRL)){
			lstSuscripcionPagoFiltro = lstSuscripcionPago.stream()
					   .filter(x -> x.getCodigo().matches("(?i).*"+ UTipoSuscripcionPago.PARTICIPACIONISTAS +".*"))
					   .collect(Collectors.toList());
			oEEvaluacionSolicitudCreditoLegalData.setCodigoTipoSuscripcionPago(UTipoSuscripcionPago.PARTICIPACIONISTAS);
		}else if (oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SA) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SAC) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersonaJuridica().equals(UTipoPersonaJuridica.SAA)) {
			lstSuscripcionPagoFiltro = lstSuscripcionPago.stream()
					   .filter(x -> x.getCodigo().matches("(?i).*"+ UTipoSuscripcionPago.ACCIONISTAS +".*"))
					   .collect(Collectors.toList());
			oEEvaluacionSolicitudCreditoLegalData.setCodigoTipoSuscripcionPago(UTipoSuscripcionPago.ACCIONISTAS);
		}else {
			lstSuscripcionPagoFiltro = lstSuscripcionPago;
		}
		}else {
			lstSuscripcionPagoFiltro = lstSuscripcionPago;
		}
	}
	
	public void listarSolicitudSuscripcion(){
		lstSuscripcion = oBOSolicitudCredito.listarSolicitudSuscripcion(oEEvaluacionSolicitudCreditoLegalData.getNumeroSolicitud(), oEEvaluacionSolicitudCreditoLegalData.getCodigoCliente(), oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoCliente());
	}
	
	public void listarClienteSuscripcion(){
		lstSuscripcion = oBOSolicitudCredito.listarClienteSuscripcion(oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento());
	}
	
	public void obtenerTipoSuscripcionPago(){
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ACCIONISTAS)){
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
		}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.PARTICIPACIONISTAS)){
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
		}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.TITULARGERENTE)){
			
			oEEvaluacionSolicitudCreditoLegalData.setNumeroAcciones(0);
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
		}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJODIRECTIVO) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJOADMINISTRACION) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ASAMBLEAGENERAL) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.GERENCIAGENERAL)){
			
			oEInformeLegalAdicionalData.setDescripcionConstitucion("");
			oEInformeLegalAdicionalData.setFechaConstitucion(null);
			oEInformeLegalAdicionalData.setDescripcionNotario("");
			oEEvaluacionSolicitudCreditoLegalData.setNumeroAcciones(0);
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
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago() != null){
			if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ACCIONISTAS)){
				visualizarFrmSuscripcion = true;
				visualizarFrmPatrimonio = false;
				visualizarDescripcionAporte = false;
				visualizarLblAccionista = true;
				visualizarLblParticipacionista = false;
			}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.PARTICIPACIONISTAS)){
				visualizarFrmSuscripcion = true;
				visualizarFrmPatrimonio = false;
				visualizarDescripcionAporte = false;
				visualizarLblAccionista = false;
				visualizarLblParticipacionista = true;
			}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.TITULARGERENTE)){
				visualizarFrmSuscripcion = false;
				visualizarFrmPatrimonio = false;
				visualizarDescripcionAporte = true;
				visualizarLblAccionista = false;
				visualizarLblParticipacionista = false;
			}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJODIRECTIVO) ||
					oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJOADMINISTRACION) ||
					oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ASAMBLEAGENERAL) ||
					oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.GERENCIAGENERAL)){
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
		oEEvaluacionSolicitudCreditoLegalData.setNumeroAcciones(suscripcionTotal);
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
	
	
	//*************************************//
	//Metodos para Documento Revision
	//*************************************//
	public void agregarDocumentoRevision(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		filesDocumentoRevision.add(oUploadedFile);
	}
	
	public void listarDocumentoRevisionSimple() {
		for(int i=0;i<filesDocumentoRevision.size();i++){
			oEOperacionDocumentoRevisionData.setNombreDocumentoOriginal(filesDocumentoRevision.get(i).getFileName());
			oEOperacionDocumentoRevisionData.setDataDocumento(filesDocumentoRevision.get(i).getContents());
		}
	}
	
	public void descargarDocumentoRevision(EOperacionDocumentoRevision oEOperacionDocumentoRevisionItem) {
		if (oEOperacionDocumentoRevisionItem != null) {
			UManejadorArchivo manejoArchivo = new UManejadorArchivo();
			Documento archivo = manejoArchivo.obtenerDocumento(oEOperacionDocumentoRevisionItem.getCodigoDocumentoLaserFiche());
			if (archivo != null && archivo.getArchivoBinario() != null && archivo.getArchivoBinario().length > 0) {
				InputStream stream = new ByteArrayInputStream(archivo.getArchivoBinario());
				fileDownload = new DefaultStreamedContent(stream, "image/png", oEOperacionDocumentoRevisionItem.getNombreDocumentoOriginal());
			} else {
				if (oEOperacionDocumentoRevisionItem.getDataDocumento() != null && oEOperacionDocumentoRevisionItem.getDataDocumento().length > 0) {
					UFuncionesGenerales.descargaArchivo(oEOperacionDocumentoRevisionItem.getNombreDocumentoOriginal(), oEOperacionDocumentoRevisionItem.getDataDocumento());
				}
			}
		}
	}
	
	public void calcularDocumentoRevision(){
		int correlativo = 0;
		for(int i=0;i<lstOperacionDocumentoRevision.size();i++){
			correlativo = correlativo + 1;
			lstOperacionDocumentoRevision.get(i).setCodigoDocumento(correlativo);
		}
	}
	
	public void obtenerDocumentoRequerido(){
		if(oEOperacionDocumentoRevisionData.getCodigoDocumentoRequerido() == UDocumentoRequerido.OTROS){
			visualizarNombreDocumentoRevision = true;
		}else {
			visualizarNombreDocumentoRevision = false;
		}
	}
	
	public void filtrarDocumentoRequerido(){
		lstDocumentoRequeridoFiltro = new ArrayList<EDocumentoRequerido>();
		for(int i=0;i<lstDocumentoRequerido.size();i++){
			int codigoDocumentoRequerido = lstDocumentoRequerido.get(i).getCodigoDocumentoRequerido();
			boolean ind = true;
			for(int x=0;x<lstOperacionDocumentoRevision.size();x++){
				if(lstOperacionDocumentoRevision.get(x).getCodigoDocumentoRequerido() == codigoDocumentoRequerido && lstOperacionDocumentoRevision.get(x).getCodigoDocumentoRequerido() != UDocumentoRequerido.OTROS){
					ind = false;
				}
			}
			if(ind){
				EDocumentoRequerido oEDocumentoRequerido = new EDocumentoRequerido();
				oEDocumentoRequerido.setCodigoDocumentoRequerido(lstDocumentoRequerido.get(i).getCodigoDocumentoRequerido());
				oEDocumentoRequerido.setDescripcionDocumentoRequerido(lstDocumentoRequerido.get(i).getDescripcionDocumentoRequerido());
				lstDocumentoRequeridoFiltro.add(oEDocumentoRequerido);
			}
		}
	}
	
	public void agregarDocumentoRevision(){
		this.oEOperacionDocumentoRevisionData = new EOperacionDocumentoRevision();
		accionInternaDocumentoRevision = UAccionInterna.NUEVO;
		inicializarDocumentoRevision();
		RequestContext.getCurrentInstance().execute("PF('dlgDocumentoRevision').show();");
	}
	
	/*
	public void modificarDocumentoRevision(EOperacionDocumentoRevision oEOperacionDocumentoRevisionItem){
		if(oEOperacionDocumentoRevisionItem != null){
			this.oEOperacionDocumentoRevisionData = new EOperacionDocumentoRevision();
			accionInternaDocumentoRevision = UAccionInterna.EDITAR;
			oEOperacionDocumentoRevisionData = oEOperacionDocumentoRevisionItem;
			inicializarDocumentoRevision();
			//obtenerDocumentoRequerido();
			RequestContext.getCurrentInstance().execute("PF('dlgDocumentoRevision').show();");
		}
	}
	*/
	
	public void eliminarDocumentoRevision(EOperacionDocumentoRevision oEOperacionDocumentoRevisionItem){
		if (oEOperacionDocumentoRevisionItem != null) {
			lstOperacionDocumentoRevision.remove(oEOperacionDocumentoRevisionItem);
			calcularDocumentoRevision();
		}
	}

	public void guardarDocumentoRevision(){
		if (oEOperacionDocumentoRevisionData != null) {
			if (accionInternaDocumentoRevision == UAccionInterna.NUEVO){
				int correlativo = 0;
				EOperacionDocumentoRevision oEOperacionDocumentoRevision = new EOperacionDocumentoRevision();
				if(lstOperacionDocumentoRevision != null){
				for(int i=0;i<lstOperacionDocumentoRevision.size();i++){
					correlativo = lstOperacionDocumentoRevision.get(i).getCodigoDocumento();
				}
				}
				
				oEOperacionDocumentoRevision.setCodigoDocumento(correlativo+1);
				oEOperacionDocumentoRevision.setCodigoDocumentoRequerido(oEOperacionDocumentoRevisionData.getCodigoDocumentoRequerido());
				oEOperacionDocumentoRevision.setNombreDocumentoOriginal(oEOperacionDocumentoRevisionData.getNombreDocumentoOriginal());
				oEOperacionDocumentoRevision.setDataDocumento(oEOperacionDocumentoRevisionData.getDataDocumento());
				
				if(oEOperacionDocumentoRevision.getCodigoDocumentoRequerido() != UDocumentoRequerido.OTROS){
					oEOperacionDocumentoRevision.setNombreDocumento(UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado3(lstDocumentoRequerido, oEOperacionDocumentoRevisionData.getCodigoDocumentoRequerido(), true));
				}else {
					oEOperacionDocumentoRevision.setNombreDocumento(oEOperacionDocumentoRevisionData.getNombreDocumento());
				}
				
				lstOperacionDocumentoRevision.add(oEOperacionDocumentoRevision);
				calcularDocumentoRevision();
			}else if(accionInternaDocumentoRevision == UAccionInterna.EDITAR){
				for(int i=0;i<lstOperacionDocumentoRevision.size();i++){
					if (lstOperacionDocumentoRevision.get(i).getCodigoDocumento() == oEOperacionDocumentoRevisionData.getCodigoDocumento()){
						if(lstOperacionDocumentoRevision.get(i).getCodigoDocumentoRequerido() != UDocumentoRequerido.OTROS){
							lstOperacionDocumentoRevision.get(i).setNombreDocumento(UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado3(lstDocumentoRequerido, oEOperacionDocumentoRevisionData.getCodigoDocumentoRequerido(), true));
						}else {
							lstOperacionDocumentoRevision.get(i).setNombreDocumento(oEOperacionDocumentoRevisionData.getNombreDocumento());
						}
					}
				}
			}
			RequestContext.getCurrentInstance().execute("PF('dlgDocumentoRevision').hide();");
		}
	}
	
	public void inicializarDocumentoRevision(){
		/*
		if (accionInternaDocumentoRevision == UAccionInterna.NUEVO){
			deshabilitarTipoDocumentoRevision = false;
			deshabilitarNombreDocumentoRevision = false;
		}else if(accionInternaDocumentoRevision == UAccionInterna.EDITAR){
			deshabilitarTipoDocumentoRevision = true;
			deshabilitarNombreDocumentoRevision = true;
		}
		*/
		filtrarDocumentoRequerido();
		obtenerDocumentoRequerido();
	}
	
	//*************************************//
	//Metodos para Generar Documento
	//*************************************//	
	public void imprimir() {
		//imprimirDocumento1();
		//imprimirDocumento2();
	}
	
	
	public void imprimirDocumento1() {
		/*
		ULectorDeParametros uLectorDeParametros = ULectorDeParametros.getInstancia();
        String serverSistema = uLectorDeParametros.getValorParametro("server.sistema");
        
        if(serverSistema.equals("linux")){
        	rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
        }else if(serverSistema.equals("windows")){
        	rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
        }
        */
        
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
		
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		
		String plantilla = "";
		String nombreArchivo = "";

		if(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoCliente() == UTipoClienteSolicitudCredito.COD_POSTULANTE){
			plantilla = "FormatoActaAfiliacion_001.docx";
			nombreArchivo = "ActaAfiliacion_";
		}else if(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoCliente() == UTipoClienteSolicitudCredito.COD_SOCIO){
			plantilla = "FormatoActaRactificacion_001.docx";
			nombreArchivo = "ActaRactificacion_";
		}
		
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
		String codigoCliente = oEEvaluacionSolicitudCreditoLegalData.getCodigoCliente()+"";
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		String nombre = (oEEvaluacionSolicitudCreditoLegalData.getNombreLargo() != null ? oEEvaluacionSolicitudCreditoLegalData.getNombreLargo():"");
		String texto1 = "";
				
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
			texto1 = nombre;
		}else{
			texto1 = nombre +" SA (en adelante LA EMPRESA) ";
		}
		
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@001", texto1);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");
		
		String rutaWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaBaseFormato + nombreArchivoWord);
		//String rutaPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		//UManejadorArchivo.conviertirArchivoAPDF(rutaWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaWordGenerado);
		//UFuncionesGenerales.borrarArchivo(rutaPdfGenerado);
		
		/*
		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWindowsWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
		*/
	}
	
	public void imprimirDocumento2() {
		/*
		ULectorDeParametros uLectorDeParametros = ULectorDeParametros.getInstancia();
        String serverSistema = uLectorDeParametros.getValorParametro("server.sistema");
        
        if(serverSistema.equals("linux")){
        	rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
        }else if(serverSistema.equals("windows")){
        	rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
        }
        */
		
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
		
		rutaBaseFormatoPdf = URutaCarpetaCompartida.rutaBaseWindows2;
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		
		String plantilla = "";
		String nombreArchivo = "";
		UNumeroLetra NumLetra = new UNumeroLetra();
		
		//plantilla = "FormatoInformeEvaluacion_001.docx";
		nombreArchivo = "InformeEvaluacion_";
		
		if(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
			plantilla = "FormatoInformeEvaluacion_003.docx";
		}else if(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO)||  
				oEEvaluacionSolicitudCreditoLegalLoad.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
			if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJODIRECTIVO) ||
					oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJOADMINISTRACION) ||
					oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ASAMBLEAGENERAL) ||
					oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.GERENCIAGENERAL)){
					plantilla = "FormatoInformeEvaluacion_002.docx";
			}else{
				plantilla = "FormatoInformeEvaluacion_001.docx";
			}
		}
		
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
		String codigoCliente = oEEvaluacionSolicitudCreditoLegalData.getCodigoCliente()+"";
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		//String texto1 = UFuncionesGenerales.convertirFechaACadena(new Date(), "hh:ss 'horas, del día' dd 'de' MMMM 'de' yyyy");
		//String texto1 = UFuncionesGenerales.convertirFechaACadena(new Date(), "dd'/'mm'/'yyyy");
		//String texto1 = formato.format(oEEvaluacionSolicitudCreditoLegalData.getFechaRegistroLegal());
		String texto1 = formato.format(new Date());
		String texto2 = (oEEvaluacionSolicitudCreditoLegalData.getNombreLargo() != null ? oEEvaluacionSolicitudCreditoLegalData.getNombreLargo():"");
		String texto3 = (oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento() != null ? oEEvaluacionSolicitudCreditoLegalData.getNumeroDocumento():"");
		String texto4 = (oEEvaluacionSolicitudCreditoLegalData.getDireccionReal() != null ? oEEvaluacionSolicitudCreditoLegalData.getDireccionReal():"");
		String texto5 = (oEInformeLegalAdicionalData.getNumeroPartida() != null ? oEInformeLegalAdicionalData.getNumeroPartida():"");
		String texto6 = (oEInformeLegalAdicionalData.getRegistroPartida() != null ? oEInformeLegalAdicionalData.getRegistroPartida():"");
		String texto7 = (oEInformeLegalAdicionalData.getNumeroPartida() != null ? UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado2(lstTipoDuracionPartida, oEInformeLegalAdicionalData.getCodigoTipoDuracionPartida(), true):"");
		String texto8 = (oEInformeLegalAdicionalData.getDescripcionConstitucion() != null ? oEInformeLegalAdicionalData.getDescripcionConstitucion():"");
		//String texto9 = formato.format(oEInformeLegalAdicionalData.getFechaConstitucion());
		String texto9 = (oEInformeLegalAdicionalData.getFechaConstitucion() != null ? formato.format(oEInformeLegalAdicionalData.getFechaConstitucion()):"");
		String texto10 = (oEInformeLegalAdicionalData.getDescripcionNotario() != null ? oEInformeLegalAdicionalData.getDescripcionNotario():"");
		String texto11 = "";
		String texto12 = oEEvaluacionSolicitudCreditoLegalData.getMontoCapitalSocialRegistroPublicos() +" ("+ NumLetra.convertir(oEEvaluacionSolicitudCreditoLegalData.getMontoCapitalSocialRegistroPublicos()+"", false, "1") + ")";
		String texto14 = "";
		String texto15 = "";
		String texto16 = "";
		String texto19 = (oEInformeLegalAdicionalData.getDescripcionPatrimonio() != null ? oEInformeLegalAdicionalData.getDescripcionPatrimonio():"");
		String texto20 = (oEInformeLegalAdicionalData.getCodigoTipoNumeracionEstatuto() != 0 ? UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado2(lstTipoNumeracionEstatuto, oEInformeLegalAdicionalData.getCodigoTipoNumeracionEstatuto(), true):"");
		String texto21 = oEInformeLegalAdicionalData.getNumeracionEstatuto()+"";
		String texto22 = (oEInformeLegalAdicionalData.getAsiento() != null ? oEInformeLegalAdicionalData.getAsiento():"");
		String texto23 = (oEInformeLegalAdicionalData.getFechaPeriodoInicio() != null ? formato.format(oEInformeLegalAdicionalData.getFechaPeriodoInicio()):"");
		String texto24 = (oEInformeLegalAdicionalData.getFechaPeriodoVencimiento() != null ? formato.format(oEInformeLegalAdicionalData.getFechaPeriodoVencimiento()):"");
		String texto27 = (oEEvaluacionSolicitudCreditoLegalData.getObservacionLegal() != null ? oEEvaluacionSolicitudCreditoLegalData.getObservacionLegal():"");
		String texto28 = oEEvaluacionSolicitudCreditoLegalData.getNumeroSolicitud()+"";
				
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ACCIONISTAS) ||
		   oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.PARTICIPACIONISTAS)){
			if(oEInformeLegalAdicionalData.getCodigoTipoValorSuscripcion() == UTipoValorSuscripcion.PORCENTAJE){
				texto15 = "Porcentaje";
			}else if(oEInformeLegalAdicionalData.getCodigoTipoValorSuscripcion() == UTipoValorSuscripcion.NUMERO){
				texto15 = "Número";
			}
		}
		
		if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ACCIONISTAS)){
			texto14 = "Acciones";
			texto16 = oEInformeLegalAdicionalData.getNumeroAcciones() + " acciones con valor nominal de " + oEInformeLegalAdicionalData.getMontoValorNominal();
			if (lstSuscripcion.size() > 0){
				UtilPoi.crearTablaSuscripcion(documeWord, "@017", lstSuscripcion);
			}else {
				UtilPoi.reemplazarPalabraenParrafo(documeWord, "@017", "");
			}
		}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.PARTICIPACIONISTAS)){
			texto14 = "Participaciones";
			texto16 = oEInformeLegalAdicionalData.getNumeroAcciones() + " participaciones con un valor de cada uno " + oEInformeLegalAdicionalData.getMontoValorNominal();
			if (lstSuscripcion.size() > 0){
				UtilPoi.crearTablaSuscripcion(documeWord, "@017", lstSuscripcion);
			}else {
				UtilPoi.reemplazarPalabraenParrafo(documeWord, "@017", "");
			}
		}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.TITULARGERENTE)){
			texto14 = "Aporte";
			texto16 = (oEInformeLegalAdicionalData.getRegistroPartida() != null ? oEInformeLegalAdicionalData.getDescripcionAporte():"");
			UtilPoi.reemplazarPalabraenParrafo(documeWord, "@017", "");
		}else if(oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJODIRECTIVO) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.CONSEJOADMINISTRACION) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.ASAMBLEAGENERAL) ||
				oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago().equals(UTipoSuscripcionPago.GERENCIAGENERAL)){
			texto14 = (oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago() != null ? UFuncionesGenerales.obtieneDescripcionDeValorSeleccionado(lstSuscripcionPago, oEEvaluacionSolicitudCreditoLegalData.getCodigoTipoSuscripcionPago(), true):"");
		}
				
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@001", texto1);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@002", texto2);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@003", texto3);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@004", texto4);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@005", texto5, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@006", texto6, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@007", texto7, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@008", texto8, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10, false);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@011", texto11);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@012", texto12);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14, true);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@015", texto15);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@016", texto16);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@019", texto19);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@020", texto20);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@021", texto21);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@022", texto22, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@023", texto23, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@024", texto24, false);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@027", texto27);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@028", texto28);
		
		if (lstRepresentanteLegal.size() > 0){
			UtilPoi.crearTablaRepresentanteLegal(documeWord, "@018", lstRepresentanteLegal);
		}else {
			UtilPoi.reemplazarPalabraenParrafo(documeWord, "@018", "");
		}
		
		List<EOperacionDocumentoRevision> lstOperacionDocumentoRevisionRevisados = new ArrayList<EOperacionDocumentoRevision>();
		List<EOperacionDocumentoRevision> lstOperacionDocumentoRevisionFaltantes = new ArrayList<EOperacionDocumentoRevision>();
		
		int correlativo1 = 0;
		int correlativo2 = 0;
		
		/*
		for(int i=0;i<lstOperacionDocumentoRevision.size();i++){
			if (lstOperacionDocumentoRevision.get(i).getNombreDocumentoOriginal() != null){
				EOperacionDocumentoRevision obj = new EOperacionDocumentoRevision();
				correlativo1 = correlativo1 + 1;
				obj.setCodigoDocumento(correlativo1);
				obj.setNombreDocumento(lstOperacionDocumentoRevision.get(i).getNombreDocumento());
				lstOperacionDocumentoRevisionRevisados.add(obj);
			}else {
				EOperacionDocumentoRevision obj2 = new EOperacionDocumentoRevision();
				correlativo2 = correlativo2 + 1;
				obj2.setCodigoDocumento(correlativo2);
				obj2.setNombreDocumento(lstOperacionDocumentoRevision.get(i).getNombreDocumento());
				lstOperacionDocumentoRevisionFaltantes.add(obj2);
			}
		}
		*/
		for(int i=0;i<lstOperacionDocumentoRevision.size();i++){
			EOperacionDocumentoRevision obj = new EOperacionDocumentoRevision();
			correlativo1 = correlativo1 + 1;
			obj.setCodigoDocumento(correlativo1);
			obj.setNombreDocumento(lstOperacionDocumentoRevision.get(i).getNombreDocumento());
			lstOperacionDocumentoRevisionRevisados.add(obj);
		}
		
		lstDocumentoRequeridoFiltro = lstDocumentoRequeridoFiltro.stream()
				   .filter(x -> x.getCodigoDocumentoRequerido() != UDocumentoRequerido.OTROS)
				   .collect(Collectors.toList());
		for(int i=0;i<lstDocumentoRequeridoFiltro.size();i++){
			EOperacionDocumentoRevision obj2 = new EOperacionDocumentoRevision();
			correlativo2 = correlativo2 + 1;
			obj2.setCodigoDocumento(correlativo2);
			obj2.setNombreDocumento(lstDocumentoRequeridoFiltro.get(i).getDescripcionDocumentoRequerido());
			lstOperacionDocumentoRevisionFaltantes.add(obj2);
		}
		
		if (lstOperacionDocumentoRevisionRevisados.size() > 0){
			UtilPoi.crearTablaDocumentosRevisados(documeWord, "@025", lstOperacionDocumentoRevisionRevisados);
		}else {
			UtilPoi.reemplazarPalabraenParrafo(documeWord, "@025", "");
		}
		
		if (lstDocumentoRequeridoFiltro.size() > 0){
			UtilPoi.crearTablaDocumentosFaltantes(documeWord, "@026", lstOperacionDocumentoRevisionFaltantes);
		}else {
			UtilPoi.reemplazarPalabraenParrafo(documeWord, "@026", "");
		}
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");
		
		String rutaWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaBaseFormato + nombreArchivoWord);
		String rutaPdfGenerado = rutaBaseFormatoPdf + nombreArchivoPdf;
		//UManejadorArchivo.conviertirArchivoAPDF(rutaWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaWordGenerado);
		//UFuncionesGenerales.borrarArchivo(rutaPdfGenerado);
		
		/*
		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaWindowsWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaLinuxWordPdfGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
		*/
	}
	
	public void inicializar() {
		deshabilitar = false;
		visualizar = true;
		
		visualizarTabGarantia = false;
		visualizarTabCreditos = false;
		
		visualizarPnlContratante = false;
		visualizarPnlRepresentanteLegal = false;
		visualizarPnlDeudor = false;
		visualizarPnlAval = false;
		
		minimizarPnlRepresentanteLegal = true;
		minimizarPnlDeudor = true;
		minimizarPnlAval = true;
		minimizarPnlDocumento = true;
		minimizarPnlDocumentoRevision = true;
		minimizarPnlRevision = true;
		
		visualizarEstado1 = false;
		visualizarEstado2 = false;
		visualizarObservacionNegocios = false;
		visualizarFrmContratantePJ = false;
		
		visualizarBtnSalir = false;
		visualizarBtnGrabar = false;
		visualizarBtnAdjuntar = false;
		
		visualizarBtnAgregarSuscripcion = false;
		visualizarBtnModificarSuscripcion = false;
		visualizarBtnEliminarSuscripcion = false;
		visualizarBtnGrabarSuscripcion = false;
		
		visualizarBtnVisualizarRepresentanteLegal = false; //falta implementar
		visualizarBtnAgregarRepresentanteLegal = false;
		visualizarBtnModificarRepresentanteLegal = false;
		visualizarBtnEliminarRepresentanteLegal = false;
		visualizarBtnGrabarRepresentanteLegal = false;
		
		visualizarBtnVisualizarFacultad = false; //falta implementar
		visualizarBtnAgregarFacultad = false;
		visualizarBtnEliminarFacultad = false;
		visualizarBtnGrabarFacultad = false;
		
		visualizarBtnVisualizarDeudor = false;
		visualizarBtnAgregarDeudor = false;
		visualizarBtnModificarDeudor = false;
		visualizarBtnEliminarDeudor = false;
		visualizarBtnGrabarDeudor = false;
		
		visualizarBtnAgregarDocumentoRevision = false;
		visualizarBtnEliminarDocumentoRevision = false; //falta implementar
	}

	public void listarSolicitudesCredito(){
		visualizarFrmDetalleCredito = true;
		lstSolCredito = oBOSolicitudCredito.listarSolicitudCredito(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoCliente());
	}
	
	public void filtrarTipoProducto(){
		if(nombreBuscar.length()>0){
			lstSolCredito = lstSolCredito.stream()
					   .filter(x -> x.getDescripcionTipoProducto().matches("(?i).*"+ nombreBuscar +".*"))
					   .collect(Collectors.toList());
		}else{
			lstSolCredito = oBOSolicitudCredito.listarSolicitudCredito(oEEvaluacionSolicitudCreditoLegalLoad.getCodigoCliente());
		}
	}
	
	public void visualizarSolicitudCredito(ESolicitudCredito oESolicitudCreditoItem){
		if (oESolicitudCreditoItem != null) {
			inicializarSolCredito();		
			if(oESolicitudCreditoItem.getCodigoTipoProducto()== 3003 || oESolicitudCreditoItem.getCodigoTipoProducto()== 3004){
				visualizarFrmCartaFianza = true;
				visualizarFrmDetalleCredito= false;
				oESolicitudCreditoData = oBOSolicitudCredito.buscarSolicitudCartaFianza(oESolicitudCreditoItem.getNumeroSolicitud());
			   System.out.println(oESolicitudCreditoData);
			}else{
				visualizarFrmCartaFianza = false;
				visualizarFrmDetalleCredito = true;
				oESolicitudCreditoData = oBOSolicitudCredito.buscarSolicitudCredito(oESolicitudCreditoItem.getNumeroSolicitud());
				System.out.println(oESolicitudCreditoData);
			}
		}
	}
	
	private void inicializarSolCredito(){
		this.oESolicitudCreditoData = new ESolicitudCredito();
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public EOperacionSolicitud getoEOperacionSolicitudLoad() {
		return oEOperacionSolicitudLoad;
	}

	public void setoEOperacionSolicitudLoad(
			EOperacionSolicitud oEOperacionSolicitudLoad) {
		this.oEOperacionSolicitudLoad = oEOperacionSolicitudLoad;
	}
	
	public EOpcion getoEOpcionLoad() {
		return oEOpcionLoad;
	}

	public void setoEOpcionLoad(EOpcion oEOpcionLoad) {
		this.oEOpcionLoad = oEOpcionLoad;
	}
	
	public ESolicitudCredito getoESolicitudCreditoLoad() {
		return oESolicitudCreditoLoad;
	}

	public void setoESolicitudCreditoLoad(ESolicitudCredito oESolicitudCreditoLoad) {
		this.oESolicitudCreditoLoad = oESolicitudCreditoLoad;
	}

	public EEvaluacionSolicitudCreditoLegal getoEEvaluacionSolicitudCreditoLegalLoad() {
		return oEEvaluacionSolicitudCreditoLegalLoad;
	}

	public void setoEEvaluacionSolicitudCreditoLegalLoad(
			EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegalLoad) {
		this.oEEvaluacionSolicitudCreditoLegalLoad = oEEvaluacionSolicitudCreditoLegalLoad;
	}
	
	public EOperacionSolicitud getoEOperacionSolicitudData() {
		return oEOperacionSolicitudData;
	}

	public void setoEOperacionSolicitudData(
			EOperacionSolicitud oEOperacionSolicitudData) {
		this.oEOperacionSolicitudData = oEOperacionSolicitudData;
	}
	
	public EEvaluacionSolicitudCreditoLegal getoEEvaluacionSolicitudCreditoLegalData() {
		return oEEvaluacionSolicitudCreditoLegalData;
	}

	public void setoEEvaluacionSolicitudCreditoLegalData(
			EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegalData) {
		this.oEEvaluacionSolicitudCreditoLegalData = oEEvaluacionSolicitudCreditoLegalData;
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
	
	public EEmail getoEmailData() {
		return oEmailData;
	}
		
	public void setoEmailData(EEmail oEmailData) {
		this.oEmailData = oEmailData;
	}
	
	public ESolicitudCredito getoESolicitudCreditoData() {
		return oESolicitudCreditoData;
	}

	public void setoESolicitudCreditoData(ESolicitudCredito oESolicitudCreditoData) {
		this.oESolicitudCreditoData = oESolicitudCreditoData;
	}
	
	public EGarantiaSolicitud getoEGarantiaSolicitudData() {
		return oEGarantiaSolicitudData;
	}

	public void setoEGarantiaSolicitudData(
			EGarantiaSolicitud oEGarantiaSolicitudData) {
		this.oEGarantiaSolicitudData = oEGarantiaSolicitudData;
	}
	
	public EGarantiaDetalleSolicitud getoEGarantiaDetalleSolicitudData() {
		return oEGarantiaDetalleSolicitudData;
	}

	public void setoEGarantiaDetalleSolicitudData(
			EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitudData) {
		this.oEGarantiaDetalleSolicitudData = oEGarantiaDetalleSolicitudData;
	}
	
	public EGarantiaDocumentoSolicitado getoEGarantiaDocumentoSolicitadoData() {
		return oEGarantiaDocumentoSolicitadoData;
	}

	public void setoEGarantiaDocumentoSolicitadoData(
			EGarantiaDocumentoSolicitado oEGarantiaDocumentoSolicitadoData) {
		this.oEGarantiaDocumentoSolicitadoData = oEGarantiaDocumentoSolicitadoData;
	}
	
	public EOperacionDocumentoRevision getoEOperacionDocumentoRevisionData() {
		return oEOperacionDocumentoRevisionData;
	}

	public void setoEOperacionDocumentoRevisionData(
			EOperacionDocumentoRevision oEOperacionDocumentoRevisionData) {
		this.oEOperacionDocumentoRevisionData = oEOperacionDocumentoRevisionData;
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

	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}

	public ETercero getoENotarioSelected() {
		return oENotarioSelected;
	}

	public void setoENotarioSelected(ETercero oENotarioSelected) {
		this.oENotarioSelected = oENotarioSelected;
	}
	
	/*
	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public void setFileDownload(StreamedContent fileDownload) {
		this.fileDownload = fileDownload;
	}
	
	public List<UploadedFile> getFiles() {
		return files;
	}

	public void setFiles(List<UploadedFile> files) {
		this.files = files;
	}
	*/
}