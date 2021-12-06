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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import javax.persistence.metamodel.CollectionAttribute;
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
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UTipoPersona;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.servicio.laserfiche.Documento;


@ManagedBean(name = "mbmantenimientooperaciongarantia")
@ViewScoped
public class MBMantenimientoOperacionGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	//Entidades
	private EUsuario oEUsuario;
	private EMensaje oEMensaje;
	private EGarantia oEGarantiaLoad;
	private EGeneral oETipoGarantiaLoad;
	private EGarantia oEGarantiaData;
	private EGarantia oEGarantiaAnexoData;
	private EPersona oEPersonaSelected;
	private EPoliza oEPolizaSelected;
	private EOperacionDocumento oEOperacionDocumentoNotariaData;
	//BO
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private BOOperacion oBOOperacion;
	private BORepresentanteLegal oBORepresentanteLegal;
	
	//Indicadores para Habilitar/Deshabilitar 
	@Getter @Setter private boolean deshabilitarCampo;
	@Getter @Setter private boolean deshabilitarBusqueda;
	@Getter @Setter private boolean deshabilitarBotonEnviar;
	@Getter @Setter private boolean visualizarCamposOtrasGarantias;
	@Getter @Setter private boolean visualizarTextoRegistro;
	@Getter @Setter private boolean visualizarBtnAgregarPropietario,visualizarBtnEliminarPropietario;
	
	//Indicadores para Renderizar y Visualizar Paneles de Acuerdo al Tipo de Garantía
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaPredio;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaVehicular;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaAcciones;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaFianzas;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMaquinaria;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMercaderia;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaWarrant;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaFideicomiso;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaOtros;
	
	//Ubigeo para Garantia (Predios)
	@Getter @Setter private int codigoDepartamentoGarantia;
	@Getter @Setter private int codigoProvinciaGarantia;
	@Getter @Setter private int codigoDistritoGarantia;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia;
	
	//Listas Desplegables 
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoPrendaGarantia;
	@Getter @Setter private List<EGeneral> lstTipoMoneda;
	@Getter @Setter private List<EGeneral> lstTipoCiaSeguro;
	@Getter @Setter private List<EGeneral> lstTipoFiador;
	@Getter @Setter private List<EGeneral> lstTipoFianza;
	@Getter @Setter private List<EGeneral> lstTipoPlazo;
	@Getter @Setter private List<EGeneral> lstTipoBanco;
	@Getter @Setter private List<EGeneral> lstEstadoGarantia;
	@Getter @Setter private List<EGeneral> lstSituacionGarantia;
	@Getter @Setter private List<EGeneral> lstTipoAlmacen;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EGeneral> lstTipoMercaderiaWarrant;
	@Getter @Setter private List<EGeneral> lstOficinaRegistral;
	@Getter @Setter private List<EGeneral> lstTipoRegistral;
	
	private int accionExterna;
	private int indexPropietario;
	@Getter @Setter private Double montoAcumuladoAsignadoCredito;    
	@Getter @Setter private Double montoAcumuladoSaldoCredito;  
	@Getter @Setter private Double montoAcumuladoCoberturado;
	//Listas de Entidades
	@Getter @Setter private List<EAsignacionContratoGarantia> lstCreditoGarantia;
	@Getter @Setter private List<EPersona> lstPropietario;
	
	//Atributos para Búsqueda de Socio y Poliza
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;
	@Getter @Setter private int indicadorPersona; //1:Tasador, 2:Propietario, 3: Depositario, 4: Socio/Tercero
	@Getter @Setter private int indicadorCiaSeguro; //Para Buscar Cia Seguro con Poliza
	@Getter @Setter private List<EPoliza> lstPolizaSeguro ;
	
	
	//Atributos para Documentacion
	@Getter @Setter private boolean visualizarTabDocumento,visualizarTabPrestamos;
	@Getter @Setter private boolean visualizarGenerarDocumento,visualizarGenerarDocumentoHipotecario;
	@Getter @Setter private boolean  visualizarBotonIrTramite;
	@Getter @Setter private boolean deshabilitarGrabarDocumento;
	@Getter @Setter private boolean deshabilitarGrabarDocumentoNotaria;
	@Getter @Setter private boolean deshabilitarSolFirma;
	@Getter @Setter private boolean deshabilitarPanel;
	private String rutaBaseFormato;
	private String rutaBasePlantilla;
	
	/* Variables de carga File */
	private EOperacionDocumento oEOperacionDocumento;
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCargaNotaria;
	@Getter @Setter private String descripcionDocumentoCarga;
	@Getter @Setter private String descripcionDocumento,observacionDocumento;
	@Getter @Setter private int codigoFirmaSiNo,codigoFirmaDocumento;
	@Getter @Setter private String observacionDocumentoFirma;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstDocumentoNotaria;
	
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEOperacionDocumento = new EOperacionDocumento();
		oEPersonaSelected = new EPersona();
		oEPolizaSelected = new EPoliza();
		oEOperacionDocumentoNotariaData = new EOperacionDocumento();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oBOOperacion = new BOOperacion();
		oBORepresentanteLegal = new BORepresentanteLegal();
		
		lstDepartamentoGarantia = new ArrayList<EGeneral>();
		lstProvinciaGarantia = new ArrayList<EGeneral>();
		lstDistritoGarantia = new ArrayList<EGeneral>();		
		
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoPrendaGarantia = new ArrayList<EGeneral>();
		lstTipoMoneda = new ArrayList<EGeneral>();
		lstTipoCiaSeguro = new ArrayList<EGeneral>();
		lstTipoFiador = new ArrayList<EGeneral>();
		lstTipoFianza = new ArrayList<EGeneral>();
		lstTipoPlazo = new ArrayList<EGeneral>();
		lstTipoBanco = new ArrayList<EGeneral>();
		lstEstadoGarantia = new ArrayList<EGeneral>();
		lstSituacionGarantia = new ArrayList<EGeneral>();
		lstTipoAlmacen = new ArrayList<EGeneral>();
		lstValorSiNo = new ArrayList<EGeneral>();
		lstOficinaRegistral = new ArrayList<EGeneral>();
		lstTipoRegistral = new ArrayList<EGeneral>();
		lstTipoMercaderiaWarrant = new ArrayList<EGeneral>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		lstDocumentoCargaNotaria = new ArrayList<EDocumentoCarga>();
		lstDocumentoNotaria = new ArrayList<EOperacionDocumento>();
		files = new ArrayList<UploadedFile>(); 
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
		lstPropietario = new ArrayList<EPersona>();
		inicializar();

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
			if(UAccionExterna.NUEVO == accionExterna){
				oETipoGarantiaLoad = (EGeneral) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				visualizarTextoRegistro = true;
				deshabilitarBusqueda = false;
				oEGarantiaData = new EGarantia();
				oEGarantiaAnexoData = new EGarantia();
				oEGarantiaData.setLstPropietario(new ArrayList<EPersona>());
				oEGarantiaData.setFechaGravamen(new Date());
				visualizarTabDocumento = false;
				visualizarTabPrestamos= true;
				visualizarBotonIrTramite = true;
				visualizarBtnAgregarPropietario = true;
				visualizarBtnEliminarPropietario = true;
				//Data de F5101 o F5151
				EPersona ePersona = oBOCliente.listarSocioyTercero(1, oETipoGarantiaLoad.getCodigo3()+"").get(0);
				//Habilitar Paneles de Acuerdo al Tipo de Garantía
				switch(oETipoGarantiaLoad.getCodigo2()){
				case  UTipoGarantia.PREDIO:
					 indicadorPnlDetalleGarantiaPredio= true;
					 listarUbigeoGarantia();			 
					 break;
				case  UTipoGarantia.VEHICULAR: indicadorPnlDetalleGarantiaVehicular= true; break;
				case  UTipoGarantia.ACCIONES:  indicadorPnlDetalleGarantiaAcciones=true; break;
				case  UTipoGarantia.FIANZAS:   indicadorPnlDetalleGarantiaFianzas=true; break;
				case  UTipoGarantia.MAQUINARIA:indicadorPnlDetalleGarantiaMaquinaria=true; break;
				case  UTipoGarantia.MERCADERIAS:indicadorPnlDetalleGarantiaMercaderia=true; break;
				case  UTipoGarantia.WARRANT:indicadorPnlDetalleGarantiaWarrant = true; break;
				case  UTipoGarantia.FIDEICOMISO_BIENES: indicadorPnlDetalleGarantiaFideicomiso = true; break;
				case  UTipoGarantia.FLUJOS: 
				case  UTipoGarantia.SALDOCUENTA:
				case  UTipoGarantia.INVENTARIO:
					indicadorPnlDetalleGarantiaOtros = true; break;
				default:	
					 indicadorPnlDetalleGarantiaPredio= false;
					 indicadorPnlDetalleGarantiaVehicular= false;
					 indicadorPnlDetalleGarantiaAcciones=false;
					 indicadorPnlDetalleGarantiaFianzas=false;
					 indicadorPnlDetalleGarantiaMaquinaria=false;
					 indicadorPnlDetalleGarantiaMercaderia=false;
					 indicadorPnlDetalleGarantiaWarrant = false;
					 indicadorPnlDetalleGarantiaOtros = false;
					 indicadorPnlDetalleGarantiaFideicomiso = false;
					 
				}
				//Para el caso de Otras Garantías
				if(oETipoGarantiaLoad.getCodigo2()>21 && oETipoGarantiaLoad.getCodigo2()!=88){
					indicadorPnlDetalleGarantiaOtros = true;
					visualizarCamposOtrasGarantias = true; 
				}
				
				listarDesplegable();
				
				oEGarantiaData.setCodigoTipoGarantia(oETipoGarantiaLoad.getCodigo2());
				oEGarantiaData.setDescripcionTipoGarantia(oETipoGarantiaLoad.getNombreCorto());
				oEGarantiaData.setCodigoCliente(Integer.parseInt(String.valueOf(ePersona.getCodigo())));
				oEGarantiaData.setNombreCorto(ePersona.getNombreCorte());
				listarCreditosAsociadosGarantia();
	
			}else if(UAccionExterna.EDITAR == accionExterna){
				oEGarantiaLoad = (EGarantia) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				visualizarTextoRegistro = false; 
				visualizarBotonIrTramite = true;
				deshabilitarBusqueda = true;
				visualizarBtnAgregarPropietario = true;
				visualizarBtnEliminarPropietario = true;	
				//Data de F9201
				oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaLoad.getCodigoGarantia());
				oEGarantiaData.setLstPropietario(new ArrayList<EPersona>());
				//Data de F92011
				oEGarantiaAnexoData = oBOGarantia.buscarAnexoGarantia(oEGarantiaLoad.getCodigoGarantia());
				if(oEGarantiaAnexoData != null){
					oEGarantiaData.setUbicacion1(oEGarantiaAnexoData.getUbicacion1Largo() != null ? 
							oEGarantiaAnexoData.getUbicacion1Largo():"");
					oEGarantiaData.setPartidaRegistral(oEGarantiaAnexoData.getPartidaRegistral());
					oEGarantiaData.setOficinaRegistral(oEGarantiaAnexoData.getOficinaRegistral());
					oEGarantiaData.setTipoRegistral(oEGarantiaAnexoData.getTipoRegistral());
					oEGarantiaData.setFechaComercial(oEGarantiaAnexoData.getFechaComercial());
					oEGarantiaData.setMontoComercial(oEGarantiaAnexoData.getMontoComercial());
					oEGarantiaData.setLstPropietario(oEGarantiaAnexoData.getLstPropietario());
				}
				
				EPersona ePersona = new EPersona();
				ePersona.setCodigo(oEGarantiaData.getCodigoPropietario());
				ePersona.setNombreCorte(oEGarantiaData.getDescripcionPropietario());
				if(oEGarantiaData.getCodigoPropietario() != 0){
					oEGarantiaData.getLstPropietario().add(0, ePersona);
				}
				
				//Habilitar Paneles de Acuerdo al Tipo de Garantía
				switch(oEGarantiaLoad.getCodigoTipoGarantia()){
				case  UTipoGarantia.PREDIO:
					 indicadorPnlDetalleGarantiaPredio= true;		
					 listarUbigeoGarantia();
					 EGarantiaSolicitud obj = oBOGarantia.buscarSolicitudxGarantia(oEGarantiaData.getCodigoGarantia());
					 if(obj == null){
						 visualizarGenerarDocumento= true;
						 visualizarGenerarDocumentoHipotecario = false; 
					 }else{
						 visualizarGenerarDocumento= false;
						 visualizarGenerarDocumentoHipotecario = true;
					 }
					
					break;
				case  UTipoGarantia.VEHICULAR: indicadorPnlDetalleGarantiaVehicular= true; break;
				case  UTipoGarantia.ACCIONES: indicadorPnlDetalleGarantiaAcciones=true; break;
				case  UTipoGarantia.FIANZAS: indicadorPnlDetalleGarantiaFianzas=true; break;
				case  UTipoGarantia.MAQUINARIA: indicadorPnlDetalleGarantiaMaquinaria=true; break;
				case  UTipoGarantia.MERCADERIAS: indicadorPnlDetalleGarantiaMercaderia=true; break;
				case  UTipoGarantia.WARRANT: indicadorPnlDetalleGarantiaWarrant = true; break;
				case  UTipoGarantia.FIDEICOMISO_BIENES: indicadorPnlDetalleGarantiaFideicomiso = true; break;
				case  UTipoGarantia.FLUJOS: 
				case  UTipoGarantia.SALDOCUENTA:
				case  UTipoGarantia.INVENTARIO:
					indicadorPnlDetalleGarantiaOtros = true; break;
				default:	
					 indicadorPnlDetalleGarantiaPredio= false;
					 indicadorPnlDetalleGarantiaVehicular= false;
					 indicadorPnlDetalleGarantiaAcciones=false;
					 indicadorPnlDetalleGarantiaFianzas=false;
					 indicadorPnlDetalleGarantiaMaquinaria=false;
					 indicadorPnlDetalleGarantiaMercaderia=false;
					 indicadorPnlDetalleGarantiaWarrant=false;
					 indicadorPnlDetalleGarantiaFideicomiso = false;
					 indicadorPnlDetalleGarantiaOtros = false;
				}
				//Para el caso de Otras Garantías
				if(oEGarantiaData.getCodigoTipoGarantia()>21 && oEGarantiaData.getCodigoTipoGarantia()!=88){
					indicadorPnlDetalleGarantiaOtros = true;
					visualizarCamposOtrasGarantias = true; 
				}
				visualizarTabDocumento = true;
				visualizarTabPrestamos = true;
				listarDesplegable();
				listarDocumento();
				listarDocumentoNotario();
				listarCreditosAsociadosGarantia();
				
				//Acciones para Negocios y sus Areas
				if(oEUsuario.getCodigoArea() == 102 || 
				   oEUsuario.getCodigoArea()==103   ||
				   oEUsuario.getCodigoArea()==104   ||
				   oEUsuario.getCodigoArea()==105){
					deshabilitarCampo = true;
					deshabilitarBotonEnviar = true;
					visualizarGenerarDocumento = false;
					visualizarGenerarDocumentoHipotecario = false;
					visualizarTabPrestamos = false;
					visualizarBotonIrTramite = false;
					deshabilitarPanel = true;
				}
				
			}
			
			
		}
	}
	
	private void inicializar(){
		 indicadorPnlDetalleGarantiaPredio= false;
		 indicadorPnlDetalleGarantiaVehicular= false;
		 indicadorPnlDetalleGarantiaAcciones=false;
		 indicadorPnlDetalleGarantiaFianzas=false;
		 indicadorPnlDetalleGarantiaMaquinaria=false;
		 indicadorPnlDetalleGarantiaMercaderia=false;
		 indicadorPnlDetalleGarantiaWarrant = false;
		 indicadorPnlDetalleGarantiaFideicomiso = false;
		 indicadorPnlDetalleGarantiaOtros = false;
		 deshabilitarCampo = false;
		 visualizarCamposOtrasGarantias = false;
		 visualizarTextoRegistro = false;
		 deshabilitarGrabarDocumento = true;
		 deshabilitarGrabarDocumentoNotaria = true;
		 deshabilitarSolFirma = false;
		 deshabilitarBotonEnviar = false;
		 deshabilitarPanel = false;
		 descripcionDocumento="";
	     codigoFirmaDocumento=0;
	     observacionDocumento= "";

	}
	
	public void listarDesplegable(){
		lstTipoGarantia = oUManejadorListaDesplegable.obtieneTipoGarantia();
		lstTipoPrendaGarantia = oUManejadorListaDesplegable.obtieneTipoPrendaGarantia();
		lstTipoMoneda = oUManejadorListaDesplegable.obtieneTipoMoneda();
		lstTipoCiaSeguro = oUManejadorListaDesplegable.obtieneTipoCiaSeguro();
		lstTipoFiador = oUManejadorListaDesplegable.obtieneTipoFiador();
		lstTipoFianza = oUManejadorListaDesplegable.obtieneTipoFianza();
		lstTipoPlazo = oUManejadorListaDesplegable.obtieneTipoPlazo();
		lstTipoBanco = oUManejadorListaDesplegable.obtieneTipoBanco();
		lstEstadoGarantia = oUManejadorListaDesplegable.obtieneEstadoGarantia();
		lstSituacionGarantia = oUManejadorListaDesplegable.obtieneSituacionGarantia();
		lstTipoAlmacen = oUManejadorListaDesplegable.obtieneTipoAlmacen();
		lstTipoMercaderiaWarrant = oUManejadorListaDesplegable.obtieneTipoMercaderiaWarrant();
		lstOficinaRegistral = oUManejadorListaDesplegable.obtieneOficinaRegistral();
		lstTipoRegistral = oUManejadorListaDesplegable.obtieneTipoRegistral();
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
	}
	
	public void guardar(){

		generarCorrelativoDocumentoCarga();
		
		//Instancia de Objetos
		EGarantia oEGarantia = new EGarantia();
		
		//Pasar Datos Globales a Locales
		oEGarantia = oEGarantiaData;
		

		//Setear atributos de los Objetos Instanciados
		oEGarantia.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia))));	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
	
		oEGarantia.setUbicacion2(distribuirObservacionGarantia().getUbicacion2());
		oEGarantia.setDescripcionA(distribuirObservacionGarantia().getDescripcionA());
		oEGarantia.setDescripcionB(distribuirObservacionGarantia().getDescripcionB());
		oEGarantia.setDescripcionC(distribuirObservacionGarantia().getDescripcionC());
		oEGarantia.setComentario(distribuirObservacionGarantia().getComentario());
		if(oEGarantiaData.getLstPropietario() != null){
			oEGarantia.setCodigoPropietario(oEGarantiaData.getLstPropietario().size() >= 1 ? oEGarantiaData.getLstPropietario().get(0).getCodigo(): 0);
			oEGarantia.setCodigoPropietario2(oEGarantiaData.getLstPropietario().size() >= 2 ? oEGarantiaData.getLstPropietario().get(1).getCodigo(): 0);
			oEGarantia.setCodigoPropietario3(oEGarantiaData.getLstPropietario().size() >= 3 ? oEGarantiaData.getLstPropietario().get(2).getCodigo(): 0);
			oEGarantia.setCodigoPropietario4(oEGarantiaData.getLstPropietario().size() >= 4 ? oEGarantiaData.getLstPropietario().get(3).getCodigo(): 0);
			oEGarantia.setCodigoPropietario5(oEGarantiaData.getLstPropietario().size() >= 5 ? oEGarantiaData.getLstPropietario().get(4).getCodigo(): 0);
			oEGarantia.setCodigoPropietario6(oEGarantiaData.getLstPropietario().size() == 6 ? oEGarantiaData.getLstPropietario().get(5).getCodigo(): 0);
		}

	
		
		if(UAccionExterna.NUEVO == accionExterna){
			oEMensaje = oBOGarantia.agregarGarantiaMantenimiento(oEGarantia);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarGarantiaMantenimiento(oEGarantia);
			
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
		
	}
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			ruta = "ListaGarantiaPorConstituir.xhtml";
			
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.TABVIEWINDEX, 1); //Par que se muestre el mantenimiento
		}else if(oEUsuario.getCodigoArea()  == UArea.NEGOCIOS ||
				 oEUsuario.getCodigoArea()  == 103     ||
				 oEUsuario.getCodigoArea()  == 104     ||
				 oEUsuario.getCodigoArea()  == 105){
			ruta = "ListaConstitucionGarantia.xhtml";
		}else{
			ruta = "BandejaOperacionOtros.xhtml";
		}
		
		
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	public void actualizarDatoasAjax(){
		listarDocumento();
		listarDocumentoNotario();
	}
	
	//*************************************//
	//Begin: Metodos para Garantia (TAB=1)
	//*************************************//
	
	/*
	 * Metodo para distribuir el texto de la observacion de una garantía 
	 * hacia varios campos del Archivo F9201
	*/
	private EGarantia distribuirObservacionGarantia(){
		EGarantia eGarantia = oEGarantiaData;
		String observacion = eGarantia.getObervacionGarantia() == null ? "" : eGarantia.getObervacionGarantia() ;
		int cantCaracteresObservacion = observacion.length();
		
		switch(eGarantia.getCodigoTipoGarantia()){
		case UTipoGarantia.PREDIO:		
			if(cantCaracteresObservacion <= 0){
				eGarantia.setUbicacion2("");
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
			}else if(cantCaracteresObservacion < 50){
				eGarantia.setUbicacion2(observacion.substring(0,cantCaracteresObservacion));
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
			}else if(cantCaracteresObservacion < 110){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,cantCaracteresObservacion));
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
			}else if(cantCaracteresObservacion < 170 ){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,cantCaracteresObservacion));
				eGarantia.setComentario("");
			}else{
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,170));
				eGarantia.setComentario(observacion.substring(170));
			}


			break;
		case UTipoGarantia.VEHICULAR:	
			if(cantCaracteresObservacion <= 0){
				eGarantia.setDescripcionA("");
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
			}else if(cantCaracteresObservacion < 60){
				eGarantia.setDescripcionA(observacion.substring(0,cantCaracteresObservacion));
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
			}else if(cantCaracteresObservacion < 120 ){
				eGarantia.setDescripcionA(observacion.substring(0,60));
				eGarantia.setDescripcionB(observacion.substring(60,cantCaracteresObservacion));
				eGarantia.setDescripcionC("");
			}else{
				eGarantia.setDescripcionA(observacion.substring(0,60));
				eGarantia.setDescripcionB(observacion.substring(60,120));
				eGarantia.setDescripcionC(observacion.substring(120));
			}
			
		
			break;
		default:
	    }
		
		
		if(eGarantia.getCodigoTipoGarantia()>21 && eGarantia.getCodigoTipoGarantia()!=88){
			if(cantCaracteresObservacion <= 0){
				eGarantia.setUbicacion2("");
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
			}else if(cantCaracteresObservacion < 50){
				eGarantia.setUbicacion2(observacion.substring(0,cantCaracteresObservacion));
				eGarantia.setDescripcionB("");eGarantia.setDescripcionC("");eGarantia.setComentario("");
			}else if(cantCaracteresObservacion < 110){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,cantCaracteresObservacion));
				eGarantia.setDescripcionC("");eGarantia.setComentario("");
			}else if(cantCaracteresObservacion < 170 ){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,cantCaracteresObservacion));
				eGarantia.setComentario("");
			}else{
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,170));
				eGarantia.setComentario(observacion.substring(170));
			}

		}
		
		return eGarantia;
	}
	
	
	
	//Begin: Métodos de Ubigeo
	public void obtenerDepartamentoGarantia() {
		codigoProvinciaGarantia = 0;
		codigoDistritoGarantia = 0;
		lstProvinciaGarantia = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia);
		lstDistritoGarantia = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia);
	}
	
	public void obtenerProvinciaGarantia() {
		codigoDistritoGarantia = 0;
		lstDistritoGarantia = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia);
	}

	
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
	//End : Métodos de Ubigeo
	
	//*************************************//
  	//End: Metodos para Garantia (TAB=1)
  	//*************************************//
	
	//*************************************//
  	//Begin: Metodos para Préstamos Asociadas a Garantías (TAB=2)
  	//*************************************//
	
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
	
	
	//*************************************//
  	//End: Metodos para Préstamos Asociadas a Garantías (TAB=2)
  	//*************************************//
	
	 //*************************************//
   	//Begin: Metodos para Dialogs 
  	//*************************************//
	
	//->Begin: Para Dialog: dlgBuscarSocio
	public void buscarIndicadorPersona(int codigo){
		indicadorPersona = codigo;
		lstPersona = new ArrayList<EPersona>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarSocio').show();");
	}
	
	/*BÚSQUEDA SOCIO(F5101)/TERCERO(F5151)*/
	public void buscarSocio(){
		lstPersona = oBOCliente.listarSocioyTercero(codigoBuscar,descripcionBuscar);
	}
	
	
	public void asignarPersona(){
		if(oEPersonaSelected != null){						
			switch (indicadorPersona) {
			case 1:
				oEGarantiaData.setCodigoTasador(oEPersonaSelected.getCodigo());
				oEGarantiaData.setDescripcionTasador(oEPersonaSelected.getNombreCorte());
				break;
			case 2:
				oEGarantiaData.getLstPropietario().add(oEPersonaSelected);			
				visualizarBtnAgregarPropietario = oEGarantiaData.getLstPropietario().size() >= 6 ? false: true;
				break;
			case 3:
				oEGarantiaData.setDepositario(oEPersonaSelected.getCodigo());
				oEGarantiaData.setDescripcionDepositario(oEPersonaSelected.getNombreCorte());
				break;

			default:
				oEGarantiaData.setCodigoCliente(oEPersonaSelected.getCodigo());
				oEGarantiaData.setNombreCorto(oEPersonaSelected.getNombreCorte());
				break;
			}
			
			
		}
	}
	public void obtenerEliminarPropietario(EPersona oEPersonaItem){
		if(oEPersonaItem != null){
			indexPropietario = oEGarantiaData.getLstPropietario().indexOf(oEPersonaItem);			
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacion').show();");
		}
	}
	
	public void eliminarPropietario(){
		    oEGarantiaData.getLstPropietario().remove(indexPropietario);
			visualizarBtnAgregarPropietario = oEGarantiaData.getLstPropietario().size() < 6 ? true : false;
	}
	//->End: Para Dialog: dlgBuscarSocio
	
	//->Begin: Para Dialog: dlgBuscarCiaSeguro
	public void buscarIndicadorCiaSeguro(int codigo){
		indicadorCiaSeguro = codigo;
		lstPolizaSeguro = new ArrayList<EPoliza>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarCiaSeguro').show();");
	}
	/*BÚSQUEDA POLIZA / CIA SEGURO*/
	public void buscarPoliza(){	
		lstPolizaSeguro = oBOGarantia.listarPolizaSeguro(codigoBuscar, descripcionBuscar,"");
	}

	public void asignarPoliza(){
		if(oEPolizaSelected != null){
			switch (indicadorCiaSeguro) {
			case 1:
				oEGarantiaData.setCodigoCiaSeguro(oEPolizaSelected.getCodigoCiaSeguro());
				oEGarantiaData.setDescripcionCiaSeguro(oEPolizaSelected.getDescripcionCiaSeguro());
				oEGarantiaData.setPoliza(oEPolizaSelected.getNumeroPoliza());
				break;
			case 2:
				break;
			case 3:
				break;
			default:
			}
			
		}
	}
	//->End: Para Dialog: dlgBuscarCiaSeguro
	
	//*************************************//
   	//Begin: Metodos para Documentación
  	//*************************************//
	
	//Generar Documento de Constitución (Minuta de Hipoteca)
	public void generarDocumentoMinutaConstitucion() {
		
		//Instancia de Atributos Locales
		String nroDocumento = "";
		String direccion ="";
		String departamentoCliente="", provinciaCliente="",distritoCliente="";
		String plantilla = "";
		String nombreArchivo = "";
		List<ERepresentanteLegal> eRepresentante = null;
		UNumeroLetra numLetra = new UNumeroLetra();
		
		String codigoCliente = oEGarantiaData.getCodigoCliente()+"";
		String nombreSocio = oEGarantiaData.getNombreCorto() != null ? oEGarantiaData.getNombreCorto():"";
		
		//Obtener Datos del Socio(F5101) o Tercero(F5151)	 
		ECliente oECliente = oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)):null;
		ETercero oETercero = oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)):null;
		
		//Obtener Datos del Usuario de Sistema
		ECliente oUsuario = oBOCliente.buscarUsuarioDetalle(oEUsuario) != null ? oBOCliente.buscarUsuarioDetalle(oEUsuario):new ECliente();
		
		switch(oUsuario.getCodigoEstadoCivil()){
		case "C":
		case "P":
			oUsuario.setDescripcionEstadoCivil("CASADO");
			break;
		default:
		}
		
		if(oECliente != null){
			nroDocumento = oECliente.getDocumento() != null ? oECliente.getDocumento() : "";
			direccion = oECliente.getDireccion() != null ? oECliente.getDireccion() : "";
			List<Object> ubigeo = ubigeoCliente(oECliente.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoConstitucionHipoteca_PN_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PN_";
			}else if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) || 
					oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoConstitucionHipoteca_PJ_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PJ_";
				//Obtener Representantes de Cliente
				eRepresentante = oBORepresentanteLegal.listarRepresentanteLegal(1, oECliente.getCodigoCliente()).stream()
						         .filter(x -> x.getDocumentoRelacion().equals(oECliente.getDocumento()))
						         .filter(x -> x.getIndicadorFirma() == 1)
						         .collect(Collectors.toList());
			}
		}else if (oETercero != null){
			nroDocumento = oETercero.getDocumento() != null ? oETercero.getDocumento() : "";
			direccion = oETercero.getDireccion() != null ? oETercero.getDireccion() : "";
			List<Object> ubigeo = ubigeoCliente(oETercero.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoConstitucionHipoteca_PN_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PN_";
			}else if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||
					oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoConstitucionHipoteca_PJ_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PJ_";
				//Obtener Representantes de Tercero
				eRepresentante = oBORepresentanteLegal.listarRepresentanteLegal(1, oETercero.getCodigoCliente()).stream()
				         .filter(x -> x.getDocumentoRelacion().equals(oETercero.getDocumento()))
				         .filter(x -> x.getIndicadorFirma() == 1)
				         .collect(Collectors.toList());
			}
		}
		
		rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
	
		//Obtener la plantila del Documento Word 
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		// Lenado de Datos
		String texto1 = nombreSocio;
		String texto2 = nroDocumento;
		String texto3 = direccion;
		String texto4 = departamentoCliente;
		String texto5 = provinciaCliente;
		String texto6 = distritoCliente;
		String texto7 = getFechaActual();
		String texto8 = nombreSocio;
		String texto9 = nroDocumento;
		String texto10 = oEGarantiaData.getUbicacion1();
		String texto11 =  oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamentoGarantia ).findAny().orElse(null).getDescripcion();
		String texto12 = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoProvinciaGarantia ).findAny().orElse(null).getDescripcion();
		String texto13 = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoDistritoGarantia ).findAny().orElse(null).getDescripcion();
		String texto14 = numLetra.convertir(oEGarantiaData.getMontoGravamen()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto15 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoGravamen()+")";
		String texto16 = numLetra.convertir(oEGarantiaData.getMontoValoracion()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto17 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoValoracion()+")";
		String texto18 = oUsuario.getNombreLargo().trim();
		String texto19 = oUsuario.getDescripcionEstadoCivil().trim();
		String texto20 = oUsuario.getDescripcionProfesion().trim();
		String texto21 = oUsuario.getDocumento().trim();
		String texto22 = "debidamente representado por el señor ___________,"+ 
		                 " identificado con DNI __________,"+
				         " según poderes inscritos en la partida electrónica"+
		                 " ________ del Registro de Personas Jurídicas de ________";
		
		if(eRepresentante != null && eRepresentante.size()>0){
			texto22 = "";
			for(ERepresentanteLegal eRep : eRepresentante){
				if(eRep.getCodigoGenero().equals("M") && eRep.getNominativo().isEmpty()){
					eRep.setNominativo("el Sr.");
				}else if(eRep.getCodigoGenero().equals("F") && eRep.getNominativo().isEmpty()){
					eRep.setNominativo("la Srta.");
				}
				
				texto22 += "debidamente representado por " + eRep.getNominativo() + " " + eRep.getNombreLargo()+ 
						  ", identificado con DNI "+ eRep.getDocumento() + ", según poderes inscritos en la "
						  + (eRep.getInscripcionPoder1().isEmpty() ? "_______":eRep.getInscripcionPoder1())+
						  (eRepresentante.size()>1 && eRep != (eRepresentante.get(eRepresentante.size() - 1)) ? "y también, ":".");
				
			}
			
		}
		
		//Reemplazar Texto en el Documento Word
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@001", texto1,true);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@002", texto2,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@003", texto3,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@004", texto4,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@005", texto5,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@006", texto6,false);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", texto7);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@008", texto8,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@011", texto11,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@012", texto12,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@013", texto13,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@015", texto15,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@016", texto16,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@017", texto17,true);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@018", texto18);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@019", texto19);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@020", texto20);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@021", texto21);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@022", texto22,false);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");

		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
	}
	
	
	public void generarDocumentoCreditoHipotecario() {
		//Instancia de Atributos Locales
		String nroDocumento = "";
		String direccion ="";
		String departamentoCliente="", provinciaCliente="",distritoCliente="";
		String plantilla = "";
		String nombreArchivo = "";	
		UNumeroLetra numLetra = new UNumeroLetra();
		
		String codigoCliente = oEGarantiaData.getCodigoCliente()+"";
		
		//Obtener Datos del Socio(F5101) o Tercero(F5151)	 
		ECliente oECliente = oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)):null;
		ETercero oETercero = oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)):null;
		
		//Obtener Datos del Usuario de Sistema
		ECliente oUsuario = oBOCliente.buscarUsuarioDetalle(oEUsuario) != null ? oBOCliente.buscarUsuarioDetalle(oEUsuario):new ECliente();
		
		
		if(oECliente != null){
			nroDocumento = oECliente.getDocumento();
			direccion = oECliente.getDireccion();
			List<Object> ubigeo = ubigeoCliente(oECliente.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "CreditoHipotecarioBF_PN_";
			}else if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) || 
					oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "CreditoHipotecarioBF_PJ_";
			}
		}else if (oETercero != null){
			nroDocumento = oETercero.getDocumento();
			direccion = oETercero.getDireccion();
			List<Object> ubigeo = ubigeoCliente(oETercero.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PN_";
			}else if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||
					oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PJ_";
			}
		}
		
		rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		rutaBasePlantilla = rutaBaseFormato + "Legal";
			
		
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
	
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		
		String nombreSocio = oEGarantiaData.getNombreCorto() != null ? oEGarantiaData.getNombreCorto():"";
		String texto1 = nombreSocio;
		String texto2 = nroDocumento;
		String texto3 = direccion;
		String texto4 = departamentoCliente;
		String texto5 = provinciaCliente;
		String texto6 = distritoCliente;
		String texto7 = getFechaActual();
		String texto8 = nombreSocio;
		String texto9 = nroDocumento;
		String texto10 = oEGarantiaData.getUbicacion1();
		String texto11 =  oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamentoGarantia ).findAny().orElse(null).getDescripcion();
		String texto12 = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoProvinciaGarantia ).findAny().orElse(null).getDescripcion();
		String texto13 = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoDistritoGarantia ).findAny().orElse(null).getDescripcion();
		String texto14 = numLetra.convertir(oEGarantiaData.getMontoGravamen()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto15 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoGravamen()+")";
		String texto16 = numLetra.convertir(oEGarantiaData.getMontoValoracion()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto17 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoValoracion()+")";
		String texto18=oUManejadorListaDesplegable.obtieneTipoMoneda().stream().
				filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getDescripcion();

		
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@001", texto1);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@002", texto2);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@003", texto3);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@004", texto4);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@005", texto5);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", texto6);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", texto7);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@008", texto8,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@011", texto11,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@012", texto12,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@013", texto13,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@015", texto15,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@016", texto16,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@017", texto17,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@018", texto18,true);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");

		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWindowsWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
	}
	
	//Obtener Departamento, Provincia y Distrito para el Documento
	private List<Object> ubigeoCliente(String codigoUbigeo){
		List<Object> ubigeoCliente = new ArrayList<Object>();
		int codigoDepartamentoCliente=0,codigoProvinciaCliente=0,codigoDistritoCliente=0;
		String descDepartamentoCliente="",descProvinciaCliente="",descDistritoCliente="";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 5) {
			codigoDepartamentoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 1));
			codigoProvinciaCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(1, 3));
			codigoDistritoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 6) {
			codigoDepartamentoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 2));
			codigoProvinciaCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(2, 4));
			codigoDistritoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(4, 6));
		}
		final int codigoDepartamento = codigoDepartamentoCliente,codigoProvincia = codigoProvinciaCliente,codigoDistrito=codigoDistritoCliente ;
		
		descDepartamentoCliente= oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamento ).findAny().orElse(null).getDescripcion();
		descProvinciaCliente = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamento).stream()
				.filter(x -> x.getCodigo2() == codigoProvincia ).findAny().orElse(null).getDescripcion();
		descDistritoCliente = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamento, codigoProvincia).stream()
				.filter(x -> x.getCodigo2() == codigoDistrito ).findAny().orElse(null).getDescripcion();
		
		ubigeoCliente.add(descDepartamentoCliente != null ? descDepartamentoCliente : "");
		ubigeoCliente.add(descProvinciaCliente != null ? descProvinciaCliente: "");
		ubigeoCliente.add(descDistritoCliente != null ? descDistritoCliente: "");
		 
		return ubigeoCliente;
	}
	
	//Obtener Fecha Actual de Sistema con Formato Especificado
	private  String getFechaActual() {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("EEEE, d 'de' ", new Locale("es"));
		Date objFecha = UFuncionesGenerales.getFechaActual();
		String strDia, strMes, strFecha;
		strDia = objFecha.getDate()+ " de ";
		//strDia = objSimpleDateFormat.format(objFecha);
		//strDia = strDia.substring(0,1).toUpperCase() + strDia.substring(1);
		objSimpleDateFormat.applyPattern("MMMM");
		strMes = objSimpleDateFormat.format(objFecha);
		strMes = strMes.substring(0,1).toUpperCase() + strMes.substring(1);
		objSimpleDateFormat.applyPattern(" 'de' yyyy");
		strFecha =  strDia + strMes + objSimpleDateFormat.format(objFecha);
		return strFecha;
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
	
	//Documento Carga
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
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
	
	public void agregarDocumentoCargaNotaria(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCargaNotaria.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCargaNotaria.size();x++){
					if(lstDocumentoCargaNotaria.get(x).getNombre().equals(descripcionDocumentoCarga+UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCargaNotaria.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCargaNotaria.add(oEDocumentoCarga);
			}
		}
		if(lstDocumentoCargaNotaria.size()>0) deshabilitarGrabarDocumentoNotaria = false;
		descripcionDocumentoCarga = "";
		files = new ArrayList<UploadedFile>();
	}
	
	public void nuevoDocumentoNotaria(){
		oEOperacionDocumentoNotariaData = new EOperacionDocumento();

		RequestContext.getCurrentInstance().execute("PF('dlgNuevoDocumentoNotaria').show();");
		
	}
	
	public void modificarDocumentoNotaria(EOperacionDocumento eOperacionDocumentoItem){
		if(eOperacionDocumentoItem != null){
			oEOperacionDocumentoNotariaData = eOperacionDocumentoItem;
			oEOperacionDocumentoNotariaData.setCodigofirmaDocumento(oEOperacionDocumentoNotariaData.getFirmaDocumento().equals("SI") ? 1: 2);
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoDocumentoNotaria').show();");
		}
	}
	
	public void listarDocumento() {
		EGarantia eGarantia = new EGarantia();
		eGarantia = oEGarantiaLoad;
		eGarantia.setUsuarioRegistro(oEUsuario);
		lstOperacionDocumento = oBOOperacion.listarDocumentoGarantia(eGarantia,1);
		lstOperacionDocumentoFiltro = lstOperacionDocumento.stream()
				.filter(x -> !x.getAreaReceptora().equals("NOTARIA"))
				.collect(Collectors.toList());
	}
	
	public void listarDocumentoNotario(){
		lstDocumentoNotaria=lstOperacionDocumento.stream()
				.filter(x -> x.getAreaReceptora().equals("NOTARIA"))
				.collect(Collectors.toList());
	}
	
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
		if(lstDocumentoCarga.size()==0 ||lstDocumentoCarga == null) deshabilitarGrabarDocumento = true;
	}
	
	public void eliminarDocumentoCargaNotaria(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCargaNotaria.remove(oEDocumentoCargaItem);
		if(lstDocumentoCargaNotaria.size()==0 ||lstDocumentoCargaNotaria == null) deshabilitarGrabarDocumentoNotaria = true;
	}
	
	
	public void generarCorrelativoDocumentoCarga() {
		int correlativoDocumento = 0;
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
	
	public void procesarDocumentoNotaria(){
		RequestContext.getCurrentInstance().execute("PF('dlgNuevoDocumentoNotaria').hide();");
		EOperacionDocumento eoperacionDocumentoNotaria = new EOperacionDocumento();
		EGarantia eGarantia = new EGarantia();
		EDocumentoCarga eDocumentoCarga = new EDocumentoCarga();
		
		eoperacionDocumentoNotaria = oEOperacionDocumentoNotariaData;
		if(eoperacionDocumentoNotaria.getCodigofirmaDocumento() == 1){
			eoperacionDocumentoNotaria.setFirmaDocumento("SI");
			eoperacionDocumentoNotaria.setEstadoDocumento(UEstado.DOCUMENTOFIRMADO);
		}else{
			eoperacionDocumentoNotaria.setFirmaDocumento("NO");
			eoperacionDocumentoNotaria.setEstadoDocumento(UEstado.DOCUMENTONOFIRMADO);
		}
		
		eoperacionDocumentoNotaria.setUsuarioRegistro(oEUsuario);
		
		eGarantia = oEGarantiaData;
		eGarantia.setAreaEmisora(UArea.LEGAL_DESC);
		eGarantia.setAreaReceptora(UArea.NOTARIA_DESC);
		eGarantia.setDescripcionDocumento(eoperacionDocumentoNotaria.getDescripcionDocumento());
		eGarantia.setFirmaDocumento(eoperacionDocumentoNotaria.getFirmaDocumento());
		eGarantia.setEstadoDocumento(eoperacionDocumentoNotaria.getEstadoDocumento());
		eGarantia.setObservacionDocumento(eoperacionDocumentoNotaria.getObervacionDocumento());
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setOperacionDocumento(eoperacionDocumentoNotaria);
		
		eDocumentoCarga.setCodigoLaserFiche(eoperacionDocumentoNotaria.getCodigoDocumentoLaserFiche());
		eDocumentoCarga.setNombre(eoperacionDocumentoNotaria.getNombreDocumento());
		eDocumentoCarga.setNombreLaserFiche(eoperacionDocumentoNotaria.getNombreDocumentoLaserFiche());;
		eDocumentoCarga.setNombreOriginal(eoperacionDocumentoNotaria.getNombreDocumentoOriginal());
		
		if(UAccionExterna.EDITAR == accionExterna){
			if(eoperacionDocumentoNotaria.getCodigoSolicitud()<= 0){
				oEMensaje = oBOOperacion.agregarDocumentoGarantia(eGarantia, eDocumentoCarga);
			}else{
				oEMensaje = oBOOperacion.modificarDocumentoGarantia(eGarantia);
			}
			
			
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	
	
	public void redireccionarTramiteGarantia(){
		String ruta = "";
		
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			Object oEGarantiaTramite = oBOGarantia.buscarGarantiaTramite(oEGarantiaData.getCodigoGarantia());
			
			if(oEGarantiaTramite == null){
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			}else{
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			}
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaData);
			ruta = "TramiteOperacionGarantia.xhtml";
			
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
		
		
		
	
	}

	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}



	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}


	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}


	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}


	public EPoliza getoEPolizaSelected() {
		return oEPolizaSelected;
	}


	public void setoEPolizaSelected(EPoliza oEPolizaSelected) {
		this.oEPolizaSelected = oEPolizaSelected;
	}


	public EMensaje getoEMensaje() {
		return oEMensaje;
	}


	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}


	public EOperacionDocumento getoEOperacionDocumento() {
		return oEOperacionDocumento;
	}


	public void setoEOperacionDocumento(EOperacionDocumento oEOperacionDocumento) {
		this.oEOperacionDocumento = oEOperacionDocumento;
	}


	public EOperacionDocumento getoEOperacionDocumentoNotariaData() {
		return oEOperacionDocumentoNotariaData;
	}


	public void setoEOperacionDocumentoNotariaData(
			EOperacionDocumento oEOperacionDocumentoNotariaData) {
		this.oEOperacionDocumentoNotariaData = oEOperacionDocumentoNotariaData;
	}

	

	
	
	
	
	
	
	
	
	
	
}
