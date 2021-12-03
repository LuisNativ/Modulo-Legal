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
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;


@ManagedBean(name = "mbregistrooperaciontramitegarantia")
@ViewScoped
public class MBRegistroOperacionTramiteGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	private EUsuario oEUsuario;
	private EMensaje oEMensaje;
	private EGarantia oEGarantiaLoad;
	private EGarantia oEGarantiaData;
	private EGeneral oETipoGarantiaLoad;
	private EGarantiaTramite oEGarantiaTramiteData;
	private EGarantiaTramite oEGarantiaTramiteHistoricoData;
	private EGarantiaTramite oEGarantiaTramiteAsientoData;
	private ETercero oETerceroData;
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private BOSolicitudCredito oBOSolicitudCredito;
	private int accionExterna;
	@Getter @Setter private boolean deshabilitarCampo;
	@Getter @Setter private boolean deshabilitarCampoAsientoTramiteGarantia;
	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean visualizar;
	
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaPredio;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaVehicular;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaAcciones;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaFianzas;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMaquinaria;
	@Getter @Setter private boolean indicadorPnlBloqueoReq;
	@Getter @Setter private boolean indicadorLegalFirma;
	@Getter @Setter private boolean deshabilitarHipoteca;
	@Getter @Setter private boolean colapsarPanel;
	@Getter @Setter private boolean tooglePanel;
	@Getter @Setter private boolean indicadorBotonCancelar,indicadorBotonGrabarAsiento;
	@Getter @Setter private boolean visualizarDatosPN,visualizarDatosPJ;
	
	@Getter @Setter private int codigoDepartamentoGarantia,codigoDepartamentoGarantiaPostal;
	@Getter @Setter private int codigoProvinciaGarantia,codigoProvinciaGarantiaPostal;
	@Getter @Setter private int codigoDistritoGarantia,codigoDistritoGarantiaPostal;	
	
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EGeneral> lstTipoDocumento;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia,lstDepartamentoGarantiaPostal;
	@Getter @Setter private List<EGeneral> lstClasePersona;
	@Getter @Setter private List<EGeneral> lstProveedor;
	@Getter @Setter private List<EGeneral> lstAceptante;
	@Getter @Setter private List<EGeneral> lstComunidad;
	@Getter @Setter private List<EGeneral> lstBancaUnidad;
	@Getter @Setter private List<EGeneral> lstZonaGeografica;
	@Getter @Setter private List<EGeneral> lstEstadoCivil;
	@Getter @Setter private List<EGeneral> lstSexo;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia,lstProvinciaGarantiaPostal;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia,lstDistritoGarantiaPostal;
	
	//Para Buscar Socio y/o Tercero
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private List<ETercero> lstNotario;
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;
	@Getter @Setter private int indicadorPersona; //Para Buscar el Tipo de Persona (Propietario,Tasador,Depositario)
	@Getter @Setter private boolean visualizarDatosPersonaNatural;
	
	private EPersona oEPersonaSelected;
	private ETercero oETerceroSelected;
	
	private int indicadorTipoGarantia;
	private int indicadorDialogNotario;
	@Getter @Setter private List<EGarantiaTramite> lstHistoricoTramiteGarantia;
	@Getter @Setter private List<EGarantiaTramite> lstAsientoTramiteGarantia;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oBOSolicitudCredito = new BOSolicitudCredito();
		lstValorSiNo = new ArrayList<EGeneral>();
		lstTipoDocumento = new ArrayList<EGeneral>();
		lstClasePersona = new ArrayList<EGeneral>();
		lstProveedor = new ArrayList<EGeneral>();
		lstAceptante = new ArrayList<EGeneral>();
		lstComunidad = new ArrayList<EGeneral>();
	    lstBancaUnidad = new ArrayList<EGeneral>();
	    lstZonaGeografica = new ArrayList<EGeneral>();
	    lstEstadoCivil = new ArrayList<EGeneral>();
	    lstSexo = new ArrayList<EGeneral>();
		lstDepartamentoGarantia = new ArrayList<EGeneral>();
		lstProvinciaGarantia = new ArrayList<EGeneral>();
		lstDistritoGarantia = new ArrayList<EGeneral>();
		lstHistoricoTramiteGarantia = new ArrayList<EGarantiaTramite>();
		lstAsientoTramiteGarantia = new ArrayList<EGarantiaTramite>();
		lstNotario = new ArrayList<ETercero>();
		oEPersonaSelected = new EPersona();
		oETerceroSelected = new ETercero();
		oEGarantiaTramiteHistoricoData = new EGarantiaTramite();
		oEGarantiaTramiteAsientoData = new EGarantiaTramite();
		oETerceroData = new ETercero();
		colapsarPanel = true;
		tooglePanel = false;
		indicadorBotonCancelar = false;
		indicadorBotonGrabarAsiento = false;
		inicializar();
		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
						
			if(UAccionExterna.NUEVO == accionExterna){
				oEGarantiaLoad = (EGarantia) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				oEGarantiaData = oEGarantiaLoad;
				oEGarantiaTramiteData = new EGarantiaTramite();
				if(oEGarantiaData.getCodigoTipoGarantia()== UTipoGarantia.PREDIO){
					indicadorPnlBloqueoReq = true; 
					deshabilitarHipoteca=false;
					oEGarantiaTramiteData.setFechaVigenciaAsientoB(new Date());
				}
				if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR || oEGarantiaData.getCodigoTipoGarantia()== UTipoGarantia.ACCIONES 
						|| oEGarantiaData.getCodigoTipoGarantia()== UTipoGarantia.MERCADERIAS){
					indicadorLegalFirma = true;
				}			
				oEGarantiaTramiteData.setFechaVigenciaAsiento(new Date());			
			}else if(UAccionExterna.EDITAR == accionExterna){
				oEGarantiaLoad = (EGarantia) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				oEGarantiaData = oEGarantiaLoad;		
				if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){
					indicadorPnlBloqueoReq = true; 
					deshabilitarHipoteca=false;
				}

				if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR || oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.ACCIONES 
						|| oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.MERCADERIAS){
					indicadorLegalFirma = true;
				}
				oEGarantiaTramiteData = oBOGarantia.buscarGarantiaTramite(oEGarantiaLoad.getCodigoGarantia());
				EGarantiaTramite eTitulos = oBOGarantia.buscarUltimoAsientoGarantiaTramite(oEGarantiaLoad.getCodigoGarantia());
				oEGarantiaTramiteData.setTituloA(eTitulos.getTituloA());
				oEGarantiaTramiteData.setTituloB(eTitulos.getTituloB());
				listarHistoricoTramiteGarantia();
				listarAsientoTramiteGarantia();
			}
			listarDesplegable();
			deshabilitarCampo = false;
			
		}
	}
	
	private void inicializar(){
		indicadorPnlBloqueoReq = false;
		deshabilitarHipoteca=true;
	}
	
	public void guardar(){
		
		EGarantiaTramite oEGarantiaTramite = new EGarantiaTramite();
		oEGarantiaTramite = oEGarantiaTramiteData;	
				
		oEGarantiaTramite.setCodigoGarantia(oEGarantiaData.getCodigoGarantia());
		oEGarantiaTramite.setCodigoMoneda(oEGarantiaData.getCodigoMoneda());
		oEGarantiaTramite.setCodigoServicio(611);
		//oEGarantiaTramite.setTipoGarantia(oEGarantiaData.getCodigoTipoGarantia());
		oEGarantiaTramite.setUsuarioRegistro(oEUsuario);
		EGarantiaTramite oEGarantiaAsientoTramite = null;
		if(oEGarantiaTramiteAsientoData != null && tooglePanel==true){
			oEGarantiaAsientoTramite = new EGarantiaTramite();
			oEGarantiaAsientoTramite = oEGarantiaTramiteAsientoData;	
			oEGarantiaAsientoTramite.setCodigoGarantia(oEGarantiaData.getCodigoGarantia());
			oEGarantiaAsientoTramite.setCodigoMoneda(oEGarantiaData.getCodigoMoneda());
			oEGarantiaAsientoTramite.setCodigoServicio(611);
			//oEGarantiaTramite.setTipoGarantia(oEGarantiaData.getCodigoTipoGarantia());
			oEGarantiaAsientoTramite.setUsuarioRegistro(oEUsuario);
		}
	
	
		
		if(UAccionExterna.NUEVO == accionExterna){
			//oEMensaje = oBOGarantia.agregarGarantiaTramite(oEGarantiaTramite);
			oEMensaje = oBOGarantia.agregarModificarGarantiaTramite(oEGarantiaTramite,oEGarantiaAsientoTramite);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.agregarModificarGarantiaTramite(oEGarantiaTramite,oEGarantiaAsientoTramite);
			//oEMensaje = oBOGarantia.modificarGarantiaTramite(oEGarantiaTramite);
			
		}
		
     	UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
		
	}
	
	public void guardarAsientoTramite(){
		EGarantiaTramite oEGarantiaAsientoTramite = null;
		if(oEGarantiaTramiteAsientoData != null && tooglePanel==true){
			oEGarantiaAsientoTramite = new EGarantiaTramite();
			oEGarantiaAsientoTramite = oEGarantiaTramiteAsientoData;	
			oEGarantiaAsientoTramite.setCodigoGarantia(oEGarantiaData.getCodigoGarantia());
			oEGarantiaAsientoTramite.setCodigoMoneda(oEGarantiaData.getCodigoMoneda());
			oEGarantiaAsientoTramite.setCodigoServicio(611);
			//oEGarantiaTramite.setTipoGarantia(oEGarantiaData.getCodigoTipoGarantia());
			oEGarantiaAsientoTramite.setUsuarioRegistro(oEUsuario);
		}
		
		oEMensaje = oBOGarantia.agregarAsientoGarantiaTramite(oEGarantiaAsientoTramite);
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	public void grabarNotario(){
		ETercero eTercero = new ETercero();
		if(oETerceroData != null){
			eTercero = oETerceroData;
			eTercero.setUsuarioRegistro(oEUsuario);
			eTercero.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia))));
			eTercero.setCodigoUbigeoPostal((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantiaPostal) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantiaPostal) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantiaPostal))));
			
			if(eTercero.getCodigoCliente()!=0){
				oEMensaje = oBOCliente.modificarTercero(eTercero);
				
			}else{
				oEMensaje = oBOCliente.registrarTercero(eTercero);
			}
			
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoNotario').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			

		}
	}
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			ruta = "ListaGarantiaPorConstituir.xhtml";
		}else{
			ruta = "BandejaOperacionOtros.xhtml";
		}
		
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.TABVIEWINDEX, 1);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	
	
	
	public void listarDesplegable(){
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
		lstTipoDocumento = oUManejadorListaDesplegable.obtieneTipoDocumento();
		lstClasePersona = oUManejadorListaDesplegable.obtieneTipoPersona();
		lstProveedor =oUManejadorListaDesplegable.obtieneProveedor();
		lstAceptante =oUManejadorListaDesplegable.obtieneAceptante();
		lstComunidad = oUManejadorListaDesplegable.obtieneComunidadOrigen();
	    lstBancaUnidad = oUManejadorListaDesplegable.obtieneUnidad();
	    lstZonaGeografica = oUManejadorListaDesplegable.obtieneZonaGeograficaF0713();
	    lstEstadoCivil = oUManejadorListaDesplegable.obtieneEstadoCivil();
	    lstSexo = oUManejadorListaDesplegable.obtieneTipoSexo();
	}
	
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
		String codigoUbigeoGarantia = oETerceroData.getCodigoUbigeo()+"";
		
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
	
	public void obtenerDepartamentoGarantiaPostal() {
		codigoProvinciaGarantiaPostal = 0;
		codigoDistritoGarantiaPostal = 0;
		lstProvinciaGarantiaPostal = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaPostal);
		lstDistritoGarantiaPostal = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaPostal, codigoProvinciaGarantiaPostal);
	}
	
	public void obtenerProvinciaGarantiaPostal() {
		codigoDistritoGarantiaPostal = 0;
		lstDistritoGarantiaPostal = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaPostal, codigoProvinciaGarantiaPostal);
	}

	
	public void listarUbigeoGarantiaPostal() {
		String codigoUbigeoGarantia = oETerceroData.getCodigoUbigeoPostal()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 5) {
			codigoDepartamentoGarantiaPostal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 1));
			codigoProvinciaGarantiaPostal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(1, 3));
			codigoDistritoGarantiaPostal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 6) {
			codigoDepartamentoGarantiaPostal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 2));
			codigoProvinciaGarantiaPostal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(2, 4));
			codigoDistritoGarantiaPostal = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(4, 6));
		}
		
		lstDepartamentoGarantiaPostal = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaGarantiaPostal = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaPostal);
		lstDistritoGarantiaPostal = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaPostal, codigoProvinciaGarantiaPostal);
	}
	
	
	public void buscarNotario(){
		lstNotario = new ArrayList<ETercero>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		lstNotario = oBOCliente.listarNotarios(0, "");
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarNotario').show();");
		indicadorDialogNotario = 1;
	}
	
	
	public void buscarSocio(){
		lstNotario = oBOCliente.listarNotarios(codigoBuscar,descripcionBuscar);
	}
	
	public void nuevoNotario(){
		oETerceroData = new ETercero();
		oETerceroData.setCodigoTipoPersona("");
		oETerceroData.setTipoProveedor(-1);
		oETerceroData.setTipoAceptante(-1);
		oETerceroData.setCodigoTipoDocumento("");
		 codigoDepartamentoGarantia=0;
		 codigoDepartamentoGarantiaPostal=0;
		 codigoProvinciaGarantia=0;
		 codigoProvinciaGarantiaPostal=0;
		 codigoDistritoGarantia=0;
		 codigoDistritoGarantiaPostal=0;
		 listarUbigeoGarantia();
		 listarUbigeoGarantiaPostal();
		 validarClasePersona();
		 validarDocumentoIdentidad();
		RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoNotario').show();");
	}
	
	public void modificarNotario(ETercero eTerceroItem){
		if(eTerceroItem != null){			
			oETerceroData = oBOCliente.buscarTercero(eTerceroItem.getCodigoCliente());
			EEmail oEmail = oBOSolicitudCredito.buscarEmailCliente(eTerceroItem.getCodigoCliente());
			ETercero oEAnexoTercero = oBOCliente.buscarTerceroAnexo(eTerceroItem.getCodigoCliente());
			ETercero oEClienteInforPerNatural = oBOCliente.buscarCliente_Info_PersonaNatural(eTerceroItem.getCodigoCliente());
			
			if(oEAnexoTercero != null){
				oETerceroData.setApellidoPaterno(oEAnexoTercero.getApellidoPaterno());
				oETerceroData.setApellidoMaterno(oEAnexoTercero.getApellidoMaterno());
				oETerceroData.setNombres(oEAnexoTercero.getNombres());
				oETerceroData.setNombreLargo(oEAnexoTercero.getNombreLargo());
				oETerceroData.setNombreSuperLargo(oEAnexoTercero.getNombreSuperLargo());
			}
			
			if(oEmail != null){
				oETerceroData.setEmail1(oEmail.getEmail1());
				oETerceroData.setEmail2(oEmail.getEmail2());
			}
			
			if(oEClienteInforPerNatural != null){
				oETerceroData.setCodigoEstadoCivil(oEClienteInforPerNatural.getCodigoEstadoCivil());
				oETerceroData.setCodigoSexo(oEClienteInforPerNatural.getCodigoSexo());
			}
			validarClasePersona();
			validarDocumentoIdentidad();
			listarUbigeoGarantia();
			listarUbigeoGarantiaPostal();
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoNotario').show();");
		}
	}
	
	
	public void buscarNotarioAsiento(){
		lstNotario = new ArrayList<ETercero>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		indicadorDialogNotario = 2;
		lstNotario = oBOCliente.listarNotarios(0, "");
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarNotario').show();");
	}
	
	public void asignarPersona(){
		if(oETerceroSelected != null){
			if(indicadorDialogNotario ==1){
				oEGarantiaTramiteData.setCodigoNotario(oETerceroSelected.getCodigoCliente());	
				oEGarantiaTramiteData.setDescripcionNotario(oETerceroSelected.getNombreLargo());
			}else if(indicadorDialogNotario ==2){
				oEGarantiaTramiteAsientoData.setCodigoNotario(oETerceroSelected.getCodigoCliente());
				oEGarantiaTramiteAsientoData.setDescripcionNotario(oETerceroSelected.getNombreLargo());
			}
			

			
		}
	}
	
	public void validarClasePersona(){
		if(oETerceroData.getCodigoTipoPersona().equals("N")){
			visualizarDatosPersonaNatural = true;
		}else{
			visualizarDatosPersonaNatural = false;
		}
	}
	
	
	
	public void generarNuevoAsientoTramite(){
		inicializarGarantiaTramiteAsiento();
		colapsarPanel = false;
		tooglePanel = true;
		indicadorBotonCancelar = true;
		indicadorBotonGrabarAsiento = true;
		deshabilitarCampoAsientoTramiteGarantia = false;
	}
	
	public void cancelarProcesoAsiento(){
		colapsarPanel = true;
		tooglePanel = false;
		indicadorBotonCancelar = false;
		indicadorBotonGrabarAsiento = false;
	}
	
	public void listarAsientoTramiteGarantia(){
		lstAsientoTramiteGarantia = oBOGarantia.listarAsientosTramiteGarantia(oEGarantiaTramiteData.getCodigoGarantia());
	}
	
	public void visualizarGarantiaAsientoTramite(EGarantiaTramite oEGarantiaTramiteAsientoItem){
		if (oEGarantiaTramiteAsientoItem != null) {
			inicializarGarantiaTramiteAsiento();		
			oEGarantiaTramiteAsientoData = oEGarantiaTramiteAsientoItem;
			colapsarPanel = false;
			tooglePanel = true;
			deshabilitarCampoAsientoTramiteGarantia = true;
		}
	}
	
	
	
	public void listarHistoricoTramiteGarantia(){
		lstHistoricoTramiteGarantia = oBOGarantia.listarHistoricoTramiteGarantia(oEGarantiaTramiteData.getCodigoGarantia());
	}
	
	
	public void visualizarGarantiaTramiteHistorico(EGarantiaTramite oEGarantiaTramiteItem){
		if (oEGarantiaTramiteItem != null) {
			inicializarGarantiaTramiteHistorico();		
			oEGarantiaTramiteHistoricoData = oEGarantiaTramiteItem;
		}
	}
	
	private void inicializarGarantiaTramiteHistorico(){
		this.oEGarantiaTramiteHistoricoData = new EGarantiaTramite();
	}

	private void inicializarGarantiaTramiteAsiento(){
		this.oEGarantiaTramiteAsientoData = new EGarantiaTramite();
	}
	
	
	public void actualizar(){
		//lstPersona = oBOCliente.listarSocioyTercero(2,"NOTARIA");
		lstNotario = oBOCliente.listarNotarios(0, "");
	}
	
	public void redireccionarMantenimientoGarantia(){
        String ruta = "";
		
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);	
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaData);
			ruta = "MantenimientoOperacionGarantia.xhtml";
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
		
		
	}
	
	public void validarDocumentoIdentidad(){
		switch(oETerceroData.getCodigoTipoDocumento()){
		case "D": 
			visualizarDatosPN = true;
			visualizarDatosPJ = false;
			oETerceroData.setNombreSuperLargo("");
			break;
		case "4": 
			visualizarDatosPN = false;
			visualizarDatosPJ = true;
			oETerceroData.setApellidoPaterno("");
			oETerceroData.setApellidoMaterno("");
			oETerceroData.setNombres("");
			break;
		default:
			visualizarDatosPN = false;
			visualizarDatosPJ = true;
			oETerceroData.setApellidoPaterno("");
			oETerceroData.setApellidoMaterno("");
			oETerceroData.setNombres("");
		}
		
	}

	public EGarantiaTramite getoEGarantiaTramiteData() {
		return oEGarantiaTramiteData;
	}



	public void setoEGarantiaTramiteData(EGarantiaTramite oEGarantiaTramiteData) {
		this.oEGarantiaTramiteData = oEGarantiaTramiteData;
	}


	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}


	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}
	
	


	public EMensaje getoEMensaje() {
		return oEMensaje;
	}


	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}


	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}


	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}


	public EGarantiaTramite getoEGarantiaTramiteHistoricoData() {
		return oEGarantiaTramiteHistoricoData;
	}


	public void setoEGarantiaTramiteHistoricoData(
			EGarantiaTramite oEGarantiaTramiteHistoricoData) {
		this.oEGarantiaTramiteHistoricoData = oEGarantiaTramiteHistoricoData;
	}


	public EGarantiaTramite getoEGarantiaTramiteAsientoData() {
		return oEGarantiaTramiteAsientoData;
	}


	public void setoEGarantiaTramiteAsientoData(
			EGarantiaTramite oEGarantiaTramiteAsientoData) {
		this.oEGarantiaTramiteAsientoData = oEGarantiaTramiteAsientoData;
	}

	public ETercero getoETerceroData() {
		return oETerceroData;
	}

	public void setoETerceroData(ETercero oETerceroData) {
		this.oETerceroData = oETerceroData;
	}

	public ETercero getoETerceroSelected() {
		return oETerceroSelected;
	}

	public void setoETerceroSelected(ETercero oETerceroSelected) {
		this.oETerceroSelected = oETerceroSelected;
	}








	
	
	
	
	
	
	
	
	
}
