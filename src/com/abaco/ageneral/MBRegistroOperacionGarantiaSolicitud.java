package com.abaco.ageneral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UClaseGarantia;
import com.abaco.negocio.util.UConstante.UCreditos;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UMensajeValidacion;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;

@ManagedBean(name = "mbregistrooperaciongarantiasolicitud")
@ViewScoped
public class MBRegistroOperacionGarantiaSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;

	private EGarantiaSolicitud oEGarantiaSolicitudLoad;	
	private ETipoCambio oETipoCambioData;
	private EGarantiaTramite oEGarantiaTramiteData;
	private EGarantiaSolicitud oEGarantiaSolicitudData;
	private EGarantiaSolicitud oEGarantiaAsociadaSolicitudData;
	private EGarantia oEGarantiaData;
	private EGarantia oEGarantiaAnexoData;
	private EGarantia oEDlgGarantiaData;
	private EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitudData;
	private EPersona oEPersonaSelected;
	private EPoliza oEPolizaSelected;

	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;	
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	//Listas Específicas
	@Getter @Setter private List<EGarantia> lstGarantiaVinculada ;
	@Getter @Setter private List<EAsignacionContratoGarantia> lstGarantiaDetalle ;
	@Getter @Setter private List<EAsignacionContratoGarantia> lstCreditoGarantia;
	
	//Datos de formulario Garantia
	@Getter @Setter private int codigoDepartamentoGarantia,codigoDepartamentoGarantiaConsulta;
	@Getter @Setter private int codigoProvinciaGarantia,codigoProvinciaGarantiaConsulta;
	@Getter @Setter private int codigoDistritoGarantia,codigoDistritoGarantiaConsulta;
		

	//Listas Desplegables 
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstClaseVehiculo;
	@Getter @Setter private List<EGeneral> lstMarcaVehiculo;
	@Getter @Setter private List<EGeneral> lstModeloVehiculo;
	@Getter @Setter private List<EGeneral> lstCombustibleVehiculo;
	@Getter @Setter private List<EGeneral> lstRiesgoVehiculo;
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
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EGeneral> lstOficinaRegistral;
	@Getter @Setter private List<EGeneral> lstTipoRegistral;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantiaConsulta;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantiaConsulta;
	@Getter @Setter private List<EGeneral> lstDistritoGarantiaConsulta;
	
	//Para Buscar Socio y/o Tercero
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar,descripcionBuscar2;
	@Getter @Setter private int indicadorPersona; //Para Buscar el Tipo de Persona (Propietario,Tasador,Depositario)
	
	//Para Buscar Poliza
	@Getter @Setter private int indicadorCiaSeguro; //Para Buscar Cia Seguro con Poliza
	@Getter @Setter private List<EPoliza> lstPolizaSeguro ;
	
	//Atributos de Mensaje
	@Getter @Setter private String mensajePorcentaje;

	
	//Indicadores
	@Getter @Setter private boolean indicadorPnlBloqueoReq;
	@Getter @Setter private boolean indicadorLegalFirma;
	@Getter @Setter private int indicadorBotonRegistro;
	@Getter @Setter private int indicadorBoton;
	@Getter @Setter private int indicadorSalir;
	@Getter @Setter private boolean deshabilitarHipoteca;
	@Getter @Setter private boolean deshabilitarCampoAdicional,deshabilitarPorcentaje;
	@Getter @Setter private boolean deshabilitarCampo, deshabilitarDlgCampo,deshabilitarCampoTramite; 
	@Getter @Setter private boolean deshabilitarCampoDetGenGarantia;
	@Getter @Setter private boolean visualizarFrmGarantiaVehicular;
	@Getter @Setter private boolean visualizarFrmGarantiaPredio;
	@Getter @Setter private boolean visualizarFrmGarantiaMaquinaria;
	@Getter @Setter private boolean visualizarFrmdeCredito;
	@Getter @Setter private boolean visualizarFrmdeLegal;
	@Getter @Setter private boolean visualizarPorcentaje;
	@Getter @Setter private boolean visualizar1eraDescripcion,visualizar2daDescripcion;
	@Getter @Setter private boolean visualizarBtnAgregarPropietario,visualizarBtnEliminarPropietario;
	@Getter @Setter private boolean renderizarTab1;
	@Getter @Setter private boolean renderizarTab2;
	@Getter @Setter private boolean renderizarTab3;
	@Getter @Setter private double montoAcumuladoSaldoCredito;
	
	@Getter @Setter private boolean colapsarPanel;
	
	//Indicadores para Renderizar y Visualizar Paneles de Acuerdo al Tipo de Garantía
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaPredio;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaVehicular;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaAcciones;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaFianzas;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMaquinaria;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMercaderia;

	/* Variables Internas */
	private int accionExterna;
	private int indexPropietario;
	

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oEGarantiaSolicitudLoad = new EGarantiaSolicitud();
		this.oEGarantiaTramiteData = new EGarantiaTramite();	
		this.oETipoCambioData = new ETipoCambio();
		this.oEGarantiaSolicitudData = new EGarantiaSolicitud();
		this.oEGarantiaAsociadaSolicitudData = new EGarantiaSolicitud();
		this.oEGarantiaData = new EGarantia();
		this.oEDlgGarantiaData = new EGarantia();
		this.oEGarantiaDetalleSolicitudData = new EGarantiaDetalleSolicitud();
	
		oEPolizaSelected = new EPoliza();
		oEPersonaSelected = new EPersona();
		

		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();		
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstNivel = new ArrayList<EGeneral>();
		lstClaseVehiculo = new ArrayList<EGeneral>();
		lstMarcaVehiculo = new ArrayList<EGeneral>();
		lstModeloVehiculo = new ArrayList<EGeneral>();
		lstCombustibleVehiculo = new ArrayList<EGeneral>();
		lstRiesgoVehiculo = new ArrayList<EGeneral>();
		lstValorSiNo = new ArrayList<EGeneral>();
		lstOficinaRegistral = new ArrayList<EGeneral>();
		lstTipoRegistral = new ArrayList<EGeneral>();		
		lstDepartamentoGarantia = new ArrayList<EGeneral>();
		lstProvinciaGarantia = new ArrayList<EGeneral>();
		lstDistritoGarantia = new ArrayList<EGeneral>();
		lstDepartamentoGarantiaConsulta = new ArrayList<EGeneral>();
		lstProvinciaGarantiaConsulta = new ArrayList<EGeneral>();
		lstDistritoGarantiaConsulta = new ArrayList<EGeneral>();
		lstGarantiaVinculada = new ArrayList<EGarantia>();
		lstGarantiaDetalle = new ArrayList<EAsignacionContratoGarantia>();
		lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
			
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
			
			if(UAccionExterna.NUEVO == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					//Data de F7325
					oEGarantiaSolicitudData = oBOGarantia.buscarGarantiaSolicitud(oEGarantiaSolicitudLoad.getNumeroSolicitud(), oEGarantiaSolicitudLoad.getSecuenciaGarantia());
					//Data de F7363
					oEGarantiaDetalleSolicitudData = oBOGarantia.buscarGarantiaDetalleSolicitud(oEGarantiaSolicitudLoad.getNumeroSolicitud(), oEGarantiaSolicitudLoad.getSecuenciaGarantia());
					//Data de GarantiaAnexoF7325 
					oEGarantiaAsociadaSolicitudData = oBOGarantia.buscarGarantiaAsociadaSolicitud(oEGarantiaSolicitudLoad.getNumeroSolicitud(), oEGarantiaSolicitudLoad.getSecuenciaGarantia());
					//Data de Tipo de Cambio
					oETipoCambioData = oBOGarantia.buscarTipoCambioDiario();
					if(oEGarantiaSolicitudData == null){
						this.oEGarantiaSolicitudData = new EGarantiaSolicitud();
					}
					
					oEGarantiaSolicitudData.setCodigoCliente(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaSolicitudData.setNombreLargo(oEGarantiaSolicitudLoad.getNombreLargo());
					oEGarantiaSolicitudData.setCodigoMonedaSolicitud(oEGarantiaSolicitudLoad.getCodigoMonedaSolicitud());
					oEGarantiaSolicitudData.setAbreviacionMonedaSolicitud(oEGarantiaSolicitudLoad.getAbreviacionMonedaSolicitud());
					oEGarantiaSolicitudData.setDescripcionMonedaSolicitud(oEGarantiaSolicitudLoad.getDescripcionMonedaSolicitud());
					oEGarantiaSolicitudData.setMontoSolicitud(oEGarantiaSolicitudLoad.getMontoSolicitud());
					

					if(oEGarantiaDetalleSolicitudData == null){
						this.oEGarantiaDetalleSolicitudData = new EGarantiaDetalleSolicitud();
					}
					

					indicadorSalir = 1;
					renderizarTab1 = true;
					
					//Visualizar Panel de Detalle General de la Garantía (Datos Generales)
					switch(oEGarantiaSolicitudLoad.getCodigoTipoGarantiaReal()){
						case UTipoGarantia.VEHICULAR: 	visualizarFrmGarantiaVehicular = true; break;
						case UTipoGarantia.PREDIO:   	
							visualizarFrmGarantiaPredio = true; 
							listarUbigeoGarantia(); 
							break;
						case UTipoGarantia.MAQUINARIA:  visualizarFrmGarantiaMaquinaria = true; break;
						default: 
							visualizarFrmGarantiaVehicular=false; 
							visualizarFrmGarantiaPredio = false;
							visualizarFrmGarantiaMaquinaria	= false;
					}
					
					//ACCIONES PARA EL AREA DE CREDITOS
					if(oEUsuario.getCodigoArea() == UArea.CREDITOS){
						
						renderizarTab3 = false;
						
						
						visualizarFrmdeCredito = true;
						deshabilitarPorcentaje = true;
						if(oEGarantiaSolicitudLoad.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS){
							visualizarFrmdeLegal = false;
							renderizarTab2 = false;
							indicadorBoton = 1;
							indicadorBotonRegistro = 2;
						}
						else if(oEGarantiaSolicitudLoad.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES){
							visualizarFrmdeLegal = true;
							renderizarTab2 = true;
							indicadorBoton = 1; 
							indicadorBotonRegistro = 3;
							visualizarPorcentaje = false;
							oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getNumeroGarantiaReal());
							
							if(oEGarantiaAsociadaSolicitudData.getPorcentajeAsignado() != 0){
								oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(oEGarantiaData.getPorcentajeDisponible());
							}
							
							//Visualizar Panel según Tipo de Garantías
							switch(oEGarantiaData.getCodigoTipoGarantia()){
							case  UTipoGarantia.PREDIO:
								 indicadorPnlDetalleGarantiaPredio= true;
								 listarUbigeoGarantia();
								break;
							case  UTipoGarantia.VEHICULAR:   indicadorPnlDetalleGarantiaVehicular= true; break;
							case  UTipoGarantia.ACCIONES:    indicadorPnlDetalleGarantiaAcciones=true;   break;
							case  UTipoGarantia.FIANZAS:     indicadorPnlDetalleGarantiaFianzas=true;    break;
							case  UTipoGarantia.MAQUINARIA:  indicadorPnlDetalleGarantiaMaquinaria=true; break;
							case  UTipoGarantia.MERCADERIAS: indicadorPnlDetalleGarantiaMercaderia=true; break;
							default:	
								 indicadorPnlDetalleGarantiaPredio= false;
								 indicadorPnlDetalleGarantiaVehicular= false;
								 indicadorPnlDetalleGarantiaAcciones=false;
								 indicadorPnlDetalleGarantiaFianzas=false;
								 indicadorPnlDetalleGarantiaMaquinaria=false;
								 indicadorPnlDetalleGarantiaMercaderia=false;
								 
							}
							
							deshabilitarCampo = true;
							
						}
					
						//ACCIONES PARA EL AREA LEGAL
					}else if(oEUsuario.getCodigoArea() == UArea.LEGAL){
						renderizarTab2 = true;
						
						if(oEGarantiaSolicitudLoad.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS){
							renderizarTab3 = false;
							indicadorBoton = 3;
							indicadorBotonRegistro = 1;
							oEGarantiaAnexoData = new EGarantia();
							oEGarantiaData = oBOGarantia.buscarGarantiaSolicitudCompleto(oEGarantiaSolicitudLoad.getNumeroSolicitud(), oEGarantiaSolicitudLoad.getSecuenciaGarantia());
							oEGarantiaData.setPorcentajeCubierto(oEGarantiaAsociadaSolicitudData.getPorcentajeAsignado());
							oEGarantiaData.setCodigoCiaSeguro(oEGarantiaAsociadaSolicitudData.getCodigoCiaSeguro());
							oEGarantiaData.setDescripcionCiaSeguro(oEGarantiaAsociadaSolicitudData.getDescripcionCiaSeguro());
							oEGarantiaData.setPoliza(oEGarantiaAsociadaSolicitudData.getPoliza());
							oEGarantiaData.setCodigoInspector(oEGarantiaAsociadaSolicitudData.getCorrelativoPoliza());
							oEGarantiaData.setFechaComercial(oEGarantiaAsociadaSolicitudData.getFechaComercial());
							oEGarantiaData.setMontoComercial(oEGarantiaAsociadaSolicitudData.getMontoComercial());
							oEGarantiaData.setLstPropietario(new ArrayList<EPersona>());
							deshabilitarCampo = false;
						}
						else if(oEGarantiaSolicitudLoad.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES){ 
							renderizarTab3 = true;
							indicadorBoton = 4; 
							visualizarPorcentaje = false;
							oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getNumeroGarantiaReal());
							oEGarantiaData.setLstPropietario(new ArrayList<EPersona>());
							//Data de F92011
							oEGarantiaAnexoData = oBOGarantia.buscarAnexoGarantia(oEGarantiaSolicitudLoad.getNumeroGarantiaReal());
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
							if(oEGarantiaData.getCodigoTipoGarantia()== UTipoGarantia.PREDIO){
								indicadorPnlBloqueoReq = true; 
								deshabilitarHipoteca=false;
								oEGarantiaTramiteData.setFechaVigenciaAsientoB(new Date());
							}
							if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR || oEGarantiaData.getCodigoTipoGarantia()== UTipoGarantia.ACCIONES 
									|| oEGarantiaData.getCodigoTipoGarantia()== UTipoGarantia.MERCADERIAS){
								indicadorLegalFirma = true;
							}
							oEGarantiaData.setCodigoCiaSeguro(oEGarantiaAsociadaSolicitudData.getCodigoCiaSeguro());
							oEGarantiaData.setDescripcionCiaSeguro(oEGarantiaAsociadaSolicitudData.getDescripcionCiaSeguro());
							oEGarantiaData.setPoliza(oEGarantiaAsociadaSolicitudData.getPoliza());
							oEGarantiaData.setCodigoInspector(oEGarantiaAsociadaSolicitudData.getCorrelativoPoliza());
							/*oEGarantiaData.setMontoGarantia(oEGarantiaSolicitudData.getMontoGarantia());
							oEGarantiaData.setMontoGravamen(oEGarantiaAsociadaSolicitudData.getMontoGarantia());
							oEGarantiaData.setMontoVariable1(oEGarantiaAsociadaSolicitudData.getMontoTasacion());
							oEGarantiaData.setMontoValoracion(oEGarantiaAsociadaSolicitudData.getMontoValorRealizacion());*/
							deshabilitarCampo = false;
						}
						deshabilitarCampoDetGenGarantia= true;
						colapsarPanel = true;
						deshabilitarCampoAdicional = true;
						deshabilitarPorcentaje= true;
						visualizarFrmdeLegal = true;
						visualizarFrmdeCredito = false;
						
						
						switch(oEGarantiaData.getCodigoTipoGarantia()){
						case  UTipoGarantia.PREDIO:
							 indicadorPnlDetalleGarantiaPredio= true;
							 listarUbigeoGarantia();
							break;
						case  UTipoGarantia.VEHICULAR:   indicadorPnlDetalleGarantiaVehicular= true; break;
						case  UTipoGarantia.ACCIONES:    indicadorPnlDetalleGarantiaAcciones=true;   break;
						case  UTipoGarantia.FIANZAS:     indicadorPnlDetalleGarantiaFianzas=true;    break;
						case  UTipoGarantia.MAQUINARIA:  indicadorPnlDetalleGarantiaMaquinaria=true; break;
						case  UTipoGarantia.MERCADERIAS: indicadorPnlDetalleGarantiaMercaderia=true; break;
						default:	
							 indicadorPnlDetalleGarantiaPredio= false;
							 indicadorPnlDetalleGarantiaVehicular= false;
							 indicadorPnlDetalleGarantiaAcciones=false;
							 indicadorPnlDetalleGarantiaFianzas=false;
							 indicadorPnlDetalleGarantiaMaquinaria=false;
							 indicadorPnlDetalleGarantiaMercaderia=false;
							 
						}
						
					}
						
					
				}
			}
			
			listarDesplegable();
		}
	}
	
	public void inicializar() {

		deshabilitarCampoAdicional = false;
		deshabilitarPorcentaje = false;
		deshabilitarCampoTramite = false;
		visualizarFrmGarantiaVehicular = false;
		visualizarFrmGarantiaPredio = false;
		deshabilitarCampo = false;
		deshabilitarDlgCampo = true;
		indicadorPnlDetalleGarantiaPredio= false;
		indicadorPnlDetalleGarantiaVehicular= false;
		indicadorPnlDetalleGarantiaAcciones=false;
		indicadorPnlDetalleGarantiaFianzas=false;
		indicadorPnlDetalleGarantiaMaquinaria=false;
		indicadorPnlDetalleGarantiaMercaderia=false;
		indicadorBotonRegistro = 0;
		colapsarPanel= false;
		indicadorSalir = 0;
		deshabilitarCampoDetGenGarantia = false;
		renderizarTab1 = false;
		renderizarTab2 = false;
		renderizarTab3 = false;
		visualizarPorcentaje = true;
		visualizar1eraDescripcion = true;
		visualizar2daDescripcion = false;
		visualizarBtnAgregarPropietario = true;
		visualizarBtnEliminarPropietario = true;
		descripcionBuscar = "";
		descripcionBuscar2 = "";
		montoAcumuladoSaldoCredito = 0;
	}
	
	public void listarDesplegable(){
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstClaseVehiculo = oUManejadorListaDesplegable.obtieneClaseVehiculo();
		lstMarcaVehiculo = oUManejadorListaDesplegable.obtieneMarcaVehiculo();
		lstModeloVehiculo = oUManejadorListaDesplegable.obtieneModeloVehiculo();
		lstCombustibleVehiculo = oUManejadorListaDesplegable.obtieneCombustibleVehiculo();
		lstRiesgoVehiculo = oUManejadorListaDesplegable.obtieneRiesgoVehiculo();
		
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
		lstOficinaRegistral = oUManejadorListaDesplegable.obtieneOficinaRegistral();
		lstTipoRegistral = oUManejadorListaDesplegable.obtieneTipoRegistral();
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
	}
	
	
	//Metodo para Registrar o Actualizar Solicitud y/o Garantia
	public void guardar() {
		
		//Instancia de Objetos
		EGarantiaSolicitud oEGarantiaSolicitud = new EGarantiaSolicitud();
		EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitud = new EGarantiaDetalleSolicitud();
		EGarantiaSolicitud oEGarantiaAsociadaSolicitud = new EGarantiaSolicitud();
		EGarantia oEGarantia = new EGarantia();
		
		//Pasar Datos Globales a Locales
		oEGarantiaSolicitud = oEGarantiaSolicitudData;
		oEGarantiaDetalleSolicitud = oEGarantiaDetalleSolicitudData;
		oEGarantiaAsociadaSolicitud = oEGarantiaAsociadaSolicitudData;
		oEGarantia = oEGarantiaData;
		
		//Setear atributos de los Objetos Instanciados
		oEGarantiaAsociadaSolicitud.setFechaRegistro(new Date());
		oEGarantiaAsociadaSolicitud.setUsuarioRegistro(oEUsuario);
		oEGarantiaSolicitud.setFechaRegistro(new Date());
		oEGarantiaSolicitud.setUsuarioRegistro(oEUsuario);
		oEGarantia.setUsuarioRegistro(oEUsuario);
		oEGarantia.setPorcentajeDisponible(obtenerPorcentajeDisponibleGarantia());
		oEGarantiaDetalleSolicitud.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia)));
		oEGarantia.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia)));
		
		if(oEGarantiaData.getLstPropietario() != null){
			oEGarantia.setCodigoPropietario(oEGarantiaData.getLstPropietario().size() >= 1 ? oEGarantiaData.getLstPropietario().get(0).getCodigo(): 0);
			oEGarantia.setCodigoPropietario2(oEGarantiaData.getLstPropietario().size() >= 2 ? oEGarantiaData.getLstPropietario().get(1).getCodigo(): 0);
			oEGarantia.setCodigoPropietario3(oEGarantiaData.getLstPropietario().size() >= 3 ? oEGarantiaData.getLstPropietario().get(2).getCodigo(): 0);
			oEGarantia.setCodigoPropietario4(oEGarantiaData.getLstPropietario().size() >= 4 ? oEGarantiaData.getLstPropietario().get(3).getCodigo(): 0);
			oEGarantia.setCodigoPropietario5(oEGarantiaData.getLstPropietario().size() >= 5 ? oEGarantiaData.getLstPropietario().get(4).getCodigo(): 0);
			oEGarantia.setCodigoPropietario6(oEGarantiaData.getLstPropietario().size() == 6 ? oEGarantiaData.getLstPropietario().get(5).getCodigo(): 0);
		}
		
		//Valida Usuario y se procede a guardar o actualizar la información  
		if(UAccionExterna.NUEVO == accionExterna){
			if(oEUsuario.getCodigoArea() == UArea.CREDITOS){
				oEGarantiaAsociadaSolicitud.setCodigoEstadoGarantiaSolicitud(UEstado.SOLICITAGARANTIAREGISTRO);
				oEMensaje = oBOGarantia.modificarGarantiaSolicitud(oEGarantiaAsociadaSolicitud);
			}else if(oEUsuario.getCodigoArea() == UArea.LEGAL){
				
				oEGarantia.setUbicacion2(distribuirObservacionGarantia().getUbicacion2());
				oEGarantia.setDescripcionA(distribuirObservacionGarantia().getDescripcionA());
				oEGarantia.setDescripcionB(distribuirObservacionGarantia().getDescripcionB());
				oEGarantia.setDescripcionC(distribuirObservacionGarantia().getDescripcionC());
				oEGarantia.setComentario(distribuirObservacionGarantia().getComentario());

				oEGarantiaSolicitud.setCodigoEstadoGarantiaSolicitud(UEstado.REGISTRADOGARANTIAPENDIENTE);
				oEMensaje = oBOGarantia.agregarGarantiaPendienteRegistro(oEGarantiaSolicitud,oEGarantia);
			}
			
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	//Método para Actualizar Información de la Solicitud 
	public void actualizarDatos(){
		EGarantiaSolicitud oEGarantiaSolicitud = new EGarantiaSolicitud();
		EGarantiaSolicitud oEGarantiaAsociadaSolicitud = new EGarantiaSolicitud();
		EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitud = new EGarantiaDetalleSolicitud();
		
		oEGarantiaSolicitud = oEGarantiaSolicitudData;
		oEGarantiaDetalleSolicitud = oEGarantiaDetalleSolicitudData;
		oEGarantiaAsociadaSolicitud = oEGarantiaAsociadaSolicitudData;
		
		oEGarantiaAsociadaSolicitud.setFechaRegistro(new Date());
		oEGarantiaAsociadaSolicitud.setUsuarioRegistro(oEUsuario);
		
		oEGarantiaDetalleSolicitud.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia)));
		
		if(UAccionExterna.NUEVO == accionExterna){
			if(oEUsuario.getCodigoArea() == UArea.CREDITOS){
				//Actualizará Datos del Archivo F7325,F7363 y GarantiaAnexoF7325
				oEMensaje = oBOGarantia.actualizarGarantiaSolicitud(oEGarantiaSolicitud,
						oEGarantiaDetalleSolicitud,oEGarantiaAsociadaSolicitud);
			}
			
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
		
	}
	
	//Método para Registrar Asiento (No se Usa Por el Momento)
	public void registrarAsientoGarantia(){
		//double montoAcumAsignadoCredito= 0;
		double montoAcumSaldoCredito = 0;
		//double montoAcumCoberturado = 0;
		lstCreditoGarantia = oBOGarantia.listarCreditosAsociadosGarantia(oEGarantiaData.getCodigoGarantia());
		for(int i=0;i<lstCreditoGarantia.size();i++){
			//montoAcumAsignadoCredito += lstCreditoGarantia.get(i).getMontoImporteCubierto();
			montoAcumSaldoCredito += lstCreditoGarantia.get(i).getMontoSaldoCredito();
		}
		
		//montoAcumuladoAsignadoCredito=montoAcumAsignadoCredito;
		montoAcumuladoSaldoCredito = montoAcumSaldoCredito;	
		//Instancia de Objetos
		EGarantiaTramite oEGarantiaAsientoTramite = null;
		EGarantiaSolicitud oEGarantiaAsociadaSolicitud = new EGarantiaSolicitud();
		EGarantia oEGarantia = new EGarantia();
		
		//Pasar Datos Globales a Locales
		oEGarantiaAsociadaSolicitud = oEGarantiaAsociadaSolicitudData;
		oEGarantia = oEGarantiaData;
		oEGarantiaAsociadaSolicitud.setFechaRegistro(new Date());
		oEGarantiaAsociadaSolicitud.setUsuarioRegistro(oEUsuario);
		oEGarantia.setUsuarioRegistro(oEUsuario);
		oEGarantia.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia)));
		oEGarantia.setMontoDisponible(oEGarantia.getMontoValoracion());
		oEGarantia.setSaldoDisponible((oEGarantia.getMontoValoracion() - montoAcumuladoSaldoCredito));
		if(montoAcumuladoSaldoCredito != 0){
			oEGarantia.setPorcentajeDisponible((oEGarantia.getMontoValoracion() / montoAcumuladoSaldoCredito));
		}
		
		if(oEGarantiaTramiteData != null ){
			oEGarantiaAsientoTramite = new EGarantiaTramite();
			oEGarantiaAsientoTramite = oEGarantiaTramiteData;	
			oEGarantiaAsientoTramite.setCodigoGarantia(oEGarantiaData.getCodigoGarantia());
			oEGarantiaAsientoTramite.setCodigoMoneda(oEGarantiaData.getCodigoMoneda());
			oEGarantiaAsientoTramite.setCodigoServicio(611);
			oEGarantiaAsientoTramite.setUsuarioRegistro(oEUsuario);
			
			if(UAccionExterna.NUEVO == accionExterna){
				
                if(oEUsuario.getCodigoArea() == UArea.LEGAL){
                	
                	oEGarantia.setUbicacion2(distribuirObservacionGarantia().getUbicacion2());
    				oEGarantia.setDescripcionA(distribuirObservacionGarantia().getDescripcionA());
    				oEGarantia.setDescripcionB(distribuirObservacionGarantia().getDescripcionB());
    				oEGarantia.setDescripcionC(distribuirObservacionGarantia().getDescripcionC());
    				oEGarantia.setComentario(distribuirObservacionGarantia().getComentario());
					
					oEGarantiaAsociadaSolicitud.setCodigoEstadoGarantiaSolicitud(UEstado.REGISTRADOGARANTIAPENDIENTE);
					oEMensaje = oBOGarantia.agregarAsientoGarantiaTramite(oEGarantiaAsientoTramite);
					oEMensaje = oBOGarantia.modificarGarantiaSolicitud(oEGarantiaAsociadaSolicitud);
					oEMensaje = oBOGarantia.modificarGarantiaMantenimiento(oEGarantia);
				}
					
				
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}
		}	
		

		
	}
	
	public void salir() {
		String ruta = "";
		
	
		/*Cerrar Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			if(indicadorBotonRegistro == 1){
				ruta = "MantenimientoOperacionGarantia.xhtml";
				EGarantia eGarantia = oBOGarantia.buscarGarantia(oBOGarantia.buscarGarantiaAsociadaSolicitud
						(oEGarantiaSolicitudLoad.getNumeroSolicitud(), oEGarantiaSolicitudLoad.getSecuenciaGarantia()).getCodigoNroIngresoGarantia()); 
						;
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, eGarantia);
			}else if(indicadorBoton == 4){
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaData);
				ruta = "TramiteOperacionGarantia.xhtml";
			}else{
				ruta = "ListaGarantiaPorConstituir.xhtml";
			}
			
		}else if(oEUsuario.getCodigoArea() == UArea.CREDITOS){
			ruta = "ListaGarantiaPorConstituirCredito.xhtml";
		}else{
			ruta = "BandejaOperacionOtros.xhtml";
		}
			
		
		inicializar();
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	
	
	//*****************************************************//
	//Begin: Metodos para la Solicitud Asociada a la Garantía (TAB=1)
	//******************************************************//
	
	/* Método para Calcular el Porcentaje de Una Garantía de acuerdo 
	 * al monto de la Solicitud de Crédito y el Monto de Valor de Realización
	 * Inmediato de la Garantía. Nota: Se considera los tipos de cambio para el cálculo
	 * */ 
	
	/*
	public void calcularPorcentaje(){
		double porcentajeGarantia = 0;
		double montoSolicitud = oEGarantiaSolicitudData.getMontoSolicitud();
		double montoGarantia = oEGarantiaAsociadaSolicitudData.getMontoValorRealizacion();
		//Evaluamos la Moneda de la Solicitud y Garantía
		if(oEGarantiaSolicitudData.getCodigoMonedaGarantia() == oEGarantiaSolicitudData.getCodigoMonedaSolicitud() ){
			
			//Evaluamos los Montos de Solicitud y Garantia
			if( montoSolicitud != 0 && montoGarantia !=0){
				if(montoSolicitud < montoGarantia ){
					if((montoSolicitud*UCreditos.POLITICACREDITOS) < montoGarantia){
						porcentajeGarantia = ((montoSolicitud * UCreditos.POLITICACREDITOS) / montoGarantia)*100;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						mensajePorcentaje = UMensajeValidacion.MSJ_7;
					}else if((montoSolicitud*UCreditos.POLITICACREDITOS) >= montoGarantia){
						porcentajeGarantia = ((montoSolicitud) / montoGarantia)*100;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						mensajePorcentaje = UMensajeValidacion.MSJ_7;
					}else{
						porcentajeGarantia = 0;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					}
				}else if(montoSolicitud== montoGarantia){
					porcentajeGarantia = 100;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					mensajePorcentaje = UMensajeValidacion.MSJ_8;
				}else{			
					porcentajeGarantia = 0;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					mensajePorcentaje = UMensajeValidacion.MSJ_9;
				}
			}else{	
				porcentajeGarantia = 0;
				oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
				mensajePorcentaje = UMensajeValidacion.MSJ_10;
			}
						
		}else if(oEGarantiaSolicitudData.getCodigoMonedaGarantia() != oEGarantiaSolicitudData.getCodigoMonedaSolicitud()){
			
			if(oEGarantiaSolicitudData.getCodigoMonedaGarantia() == UMoneda.COD_DOLARES
					&& oEGarantiaSolicitudData.getCodigoMonedaSolicitud() == UMoneda.COD_SOLES){
				montoSolicitud = oEGarantiaSolicitudData.getMontoSolicitud()/ (oETipoCambioData.getVentaDiaria() !=0 ? oETipoCambioData.getVentaDiaria():1);
				//Evaluamos los Montos de Solicitud y Garantia
				if( montoSolicitud != 0 && montoGarantia!=0){
					if(montoSolicitud < montoGarantia ){
						if((montoSolicitud*UCreditos.POLITICACREDITOS) < montoGarantia ){
							porcentajeGarantia = ((montoSolicitud* UCreditos.POLITICACREDITOS) / montoGarantia)*100;
							oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
							mensajePorcentaje = UMensajeValidacion.MSJ_7;
						}else if((montoSolicitud*UCreditos.POLITICACREDITOS) >= montoGarantia){
							porcentajeGarantia = ((montoSolicitud) / montoGarantia)*100;
							oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
							mensajePorcentaje = UMensajeValidacion.MSJ_7;
						}else{
							porcentajeGarantia = 0;
							oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						}
					}else if(montoSolicitud == montoGarantia){
						porcentajeGarantia = 100;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						mensajePorcentaje = UMensajeValidacion.MSJ_8;
					}else{			
						porcentajeGarantia = 0;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						mensajePorcentaje = UMensajeValidacion.MSJ_9;
					}
				}else{	
					porcentajeGarantia = 0;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					mensajePorcentaje = UMensajeValidacion.MSJ_10;
				}
				
				
				
			}else if (oEGarantiaSolicitudData.getCodigoMonedaGarantia() == UMoneda.COD_SOLES 
					&& oEGarantiaSolicitudData.getCodigoMonedaSolicitud() == UMoneda.COD_DOLARES){
				montoSolicitud = oEGarantiaSolicitudData.getMontoSolicitud() * oETipoCambioData.getCompraDiaria();
				//Evaluamos los Montos de Solicitud y Garantia
				if( montoSolicitud != 0 && montoGarantia!=0){
					if(montoSolicitud < montoGarantia ){
						if((montoSolicitud*UCreditos.POLITICACREDITOS) < montoGarantia ){
							porcentajeGarantia = ((montoSolicitud* UCreditos.POLITICACREDITOS) / montoGarantia)*100;
							oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
							mensajePorcentaje = UMensajeValidacion.MSJ_7;
						}else if((montoSolicitud*UCreditos.POLITICACREDITOS) >= montoGarantia){
							porcentajeGarantia = ((montoSolicitud) / montoGarantia)*100;
							oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
							mensajePorcentaje = UMensajeValidacion.MSJ_7;
						}else{
							porcentajeGarantia = 0;
							oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						}
					}else if(montoSolicitud == montoGarantia){
						porcentajeGarantia = 100;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						mensajePorcentaje = UMensajeValidacion.MSJ_8;
					}else{			
						porcentajeGarantia = 0;
						oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
						mensajePorcentaje = UMensajeValidacion.MSJ_9;
					}
				}else{	
					porcentajeGarantia = 0;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					mensajePorcentaje = UMensajeValidacion.MSJ_10;
				}
			}
			
		}else{
			mensajePorcentaje = "No se puede Calcular";
		}
	}
	*/
	
	public void calcularPorcentaje(){
		double porcentajeGarantia = 0;
		double montoSolicitud = oEGarantiaSolicitudData.getMontoSolicitud() != 0 ? oEGarantiaSolicitudData.getMontoSolicitud() : 1;
		double montoVRI = oEGarantiaAsociadaSolicitudData.getMontoValorRealizacion() != 0 ? oEGarantiaAsociadaSolicitudData.getMontoValorRealizacion() : 1;
		//double montoGarantia = oEGarantiaSolicitudData.getMontoGarantia() != 0 ? oEGarantiaSolicitudData.getMontoGarantia(): 1;
		double montoDisponible = 0;
		
		//Evaluamos la Moneda de la Solicitud y Garantía
		if(oEGarantiaSolicitudData.getCodigoMonedaGarantia() == oEGarantiaSolicitudData.getCodigoMonedaSolicitud() ){
			
			//Evaluamos los Montos de Solicitud y Garantia
			if( montoSolicitud != 0 && montoVRI !=0){
				porcentajeGarantia = montoVRI / montoSolicitud;
				montoDisponible =  montoVRI - montoSolicitud;
				oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
				oEGarantiaAsociadaSolicitudData.setSaldoDisponible(montoDisponible);
				mensajePorcentaje = UMensajeValidacion.MSJ_7;
				
			}else{	
				porcentajeGarantia = 0;
				oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
				mensajePorcentaje = UMensajeValidacion.MSJ_10;
			}
						
		}else if(oEGarantiaSolicitudData.getCodigoMonedaGarantia() != oEGarantiaSolicitudData.getCodigoMonedaSolicitud()){
			
			if(oEGarantiaSolicitudData.getCodigoMonedaGarantia() == UMoneda.COD_DOLARES
					&& oEGarantiaSolicitudData.getCodigoMonedaSolicitud() == UMoneda.COD_SOLES){
				montoSolicitud = montoSolicitud / (oETipoCambioData.getTipoCambioSBS() !=0 ? oETipoCambioData.getTipoCambioSBS():1);
				
				//Evaluamos los Montos de Solicitud y Garantia
				if( montoSolicitud != 0 && montoVRI !=0){
					porcentajeGarantia = montoVRI / montoSolicitud;
					montoDisponible =  montoVRI - montoSolicitud;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					oEGarantiaAsociadaSolicitudData.setSaldoDisponible(montoDisponible);
					mensajePorcentaje = UMensajeValidacion.MSJ_7;
					
				}else{	
					porcentajeGarantia = 0;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					mensajePorcentaje = UMensajeValidacion.MSJ_10;
				}
				

			}else if (oEGarantiaSolicitudData.getCodigoMonedaGarantia() == UMoneda.COD_SOLES 
					&& oEGarantiaSolicitudData.getCodigoMonedaSolicitud() == UMoneda.COD_DOLARES){
				montoSolicitud = montoSolicitud * (oETipoCambioData.getTipoCambioSBS() !=0 ? oETipoCambioData.getTipoCambioSBS():1);
				
				//Evaluamos los Montos de Solicitud y Garantia
				if( montoSolicitud != 0 && montoVRI !=0){
					porcentajeGarantia = montoVRI / montoSolicitud;
					montoDisponible =  montoVRI - montoSolicitud;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					oEGarantiaAsociadaSolicitudData.setSaldoDisponible(montoDisponible);
					mensajePorcentaje = UMensajeValidacion.MSJ_7;
					
				}else{	
					porcentajeGarantia = 0;
					oEGarantiaAsociadaSolicitudData.setPorcentajeAsignado(porcentajeGarantia);
					mensajePorcentaje = UMensajeValidacion.MSJ_10;
				}
			}
			
		}else{
			mensajePorcentaje = "No se puede Calcular";
		}
		
	}
	
	//*****************************************************//
	//End: Metodos para la Solicitud Asociada a la Garantía (TAB=1)
	//******************************************************//

	
	
	
	//*************************************//
	//Begin: Metodos para Garantia (TAB=2)
	//*************************************//
	
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
	//End : Métodos de Ubigeo
	

	
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
	
	
	//Metodo para Obtener el Porcentaje Disponible de una Garantía 
    private double obtenerPorcentajeDisponibleGarantia(){
    	double porcentaje = 0;
    	if(oEGarantiaData.getPorcentajeDisponible() != 0){
    		porcentaje = oEGarantiaData.getPorcentajeDisponible() - oEGarantiaData.getPorcentajeCubierto();
    	}else{    		
    		porcentaje = 100 - oEGarantiaData.getPorcentajeCubierto();
    	}
		return porcentaje;
	}
	
    //Método para Habilitar Edición manual del Porcentaje de la Garantía
  	public void editarPorcentaje(){		
  		EGarantiaSolicitud eGarantiaSolicitud = oEGarantiaAsociadaSolicitudData;
  		if(deshabilitarPorcentaje == true) {
  			deshabilitarPorcentaje = false;
  			eGarantiaSolicitud.setTipoIngreso("MANUAL");
  			eGarantiaSolicitud.setUsuarioRegistro(oEUsuario);
  			oEMensaje = oBOGarantia.modificarTipoIngresoPorcentaje(eGarantiaSolicitud);
  			mensajePorcentaje = "";	
  		}
  		else {		
  			deshabilitarPorcentaje = true;
  			eGarantiaSolicitud.setTipoIngreso("AUTOMATICO");
  			eGarantiaSolicitud.setUsuarioRegistro(oEUsuario);
  			oEMensaje = oBOGarantia.modificarTipoIngresoPorcentaje(eGarantiaSolicitud);
  			calcularPorcentaje();
  		}
  		
  	}
	
    //*************************************//
  	//End: Metodos para Garantia (TAB=2)
  	//*************************************//
  	
    //*************************************//
   	//Begin: Metodos para Dialogs 
  	//*************************************//
  	
  	//Begin: Para Dialog: dlgBuscarSocio
  	public void buscarIndicadorPersona(int codigo){
		indicadorPersona = codigo;
		lstPersona = new ArrayList<EPersona>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarSocio').show();");
	}
  	
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
			case 4:
				oEGarantiaTramiteData.setCodigoNotario(oEPersonaSelected.getCodigo());
				oEGarantiaTramiteData.setDescripcionNotario(oEPersonaSelected.getNombreCorte());
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
	//End: Para Dialog: dlgBuscarSocio
	
	//Begin: Para Dialog: dlgBuscarCiaSeguro
	public void buscarIndicadorCiaSeguro(int codigo){
		indicadorCiaSeguro = codigo;
		lstPolizaSeguro = new ArrayList<EPoliza>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		descripcionBuscar2 = "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarCiaSeguro').show();");
	}
	
	public void validarCriterioBusqueda(){
		switch(codigoBuscar){
		 case 1: 
		 case 2:
		 case 3:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = false; 
			 descripcionBuscar= "";
			 descripcionBuscar2 = "";
			 break;
		 case 4:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = true; 
			 descripcionBuscar= "";
			 descripcionBuscar2 = "";
			 break;
		 case 5:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = true; 
			 descripcionBuscar= "";
			 descripcionBuscar2 = "";
			 break;
		 default:
			 descripcionBuscar= "";
			 descripcionBuscar2 = "";
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = false; 
		}
	}
	
	public void buscarPoliza(){	
		lstPolizaSeguro = oBOGarantia.listarPolizaSeguro(codigoBuscar, descripcionBuscar,descripcionBuscar2);
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
				oEGarantiaAsociadaSolicitudData.setCodigoCiaSeguro(oEPolizaSelected.getCodigoCiaSeguro());
				oEGarantiaAsociadaSolicitudData.setDescripcionCiaSeguro(oEPolizaSelected.getDescripcionCiaSeguro());
				oEGarantiaAsociadaSolicitudData.setPoliza(oEPolizaSelected.getNumeroPoliza());
				oEGarantiaAsociadaSolicitudData.setCorrelativoPoliza(oEPolizaSelected.getCorrelativoPoliza());
				oEGarantiaAsociadaSolicitudData.setTipoPoliza(oEPolizaSelected.getTipoPoliza());
				oEGarantiaAsociadaSolicitudData.setFechaVencimientoPoliza(oEPolizaSelected.getFechaVencimientoPoliza());
				oEGarantiaAsociadaSolicitudData.setValorPoliza(oEPolizaSelected.getValorPoliza());
				break;
			case 3:
				break;
			default:
				break;
			}
			
		}
	}
	//End: Para Dialog: dlgBuscarCiaSeguro
	
	//Begin: Para Dialog: dlgBuscarGarantia
	public void buscarGarantiaVinculada(){
		oEGarantiaData.setCodigoUbigeo(UFuncionesGenerales.convierteCadenaAEntero(
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
				UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia)));
		if(oEGarantiaSolicitudLoad.getNumeroGarantiaReal() != 0){
			oEGarantiaData.setCodigoGarantia(oEGarantiaSolicitudLoad.getNumeroGarantiaReal());
		}
		
		lstGarantiaVinculada = oBOGarantia.listarGarantiaVinculada(oEGarantiaData);
		
		if( lstGarantiaVinculada!= null && !lstGarantiaVinculada.isEmpty()){
			//lstGarantiaDetalle = oBOGarantia.listarGarantiaDetalle(lstGarantiaVinculada.get(0));
			lstGarantiaDetalle = oBOGarantia.listarClientesGarantizados(lstGarantiaVinculada.get(0));
		}else{
			lstGarantiaDetalle = new ArrayList<EAsignacionContratoGarantia>();
		}
		
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarGarantia').show();");
	}
	
	public void solicitarNuevoAsiento(){

		EGarantiaSolicitud eGarantiaAsociadaSolicitud = new EGarantiaSolicitud();
		eGarantiaAsociadaSolicitud = oEGarantiaAsociadaSolicitudData;
		eGarantiaAsociadaSolicitud.setUsuarioRegistro(oEUsuario);
		if(UAccionExterna.NUEVO == accionExterna){
			if(oEUsuario.getCodigoArea() == UArea.CREDITOS){
				eGarantiaAsociadaSolicitud.setCodigoEstadoGarantiaSolicitud(UEstado.SOLICITAGARANTIAREGISTRO);
				oEMensaje = oBOGarantia.modificarGarantiaSolicitud(eGarantiaAsociadaSolicitud);
						
			}
			
		}
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	//End: Para Dialog: dlgBuscarGarantia
	
	//Begin: Para Dialog: dlgConsultaGarantia
	public void consultarGarantia(EGarantia eGarantiaItem){
		if(eGarantiaItem !=null){
			oEDlgGarantiaData = oBOGarantia.buscarGarantia(eGarantiaItem.getCodigoGarantia());
			listarUbigeoGarantiaConsulta();
			RequestContext.getCurrentInstance().execute("PF('dlgConsultaGarantia').show();");
		}
		
	}
	
	public void listarUbigeoGarantiaConsulta() {
		String codigoUbigeoGarantia = oEDlgGarantiaData.getCodigoUbigeo()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 5) {
			codigoDepartamentoGarantiaConsulta = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 1));
			codigoProvinciaGarantiaConsulta = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(1, 3));
			codigoDistritoGarantiaConsulta = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 6) {
			codigoDepartamentoGarantiaConsulta = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 2));
			codigoProvinciaGarantiaConsulta = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(2, 4));
			codigoDistritoGarantiaConsulta = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(4, 6));
		}
		
		lstDepartamentoGarantiaConsulta = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaGarantiaConsulta = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaConsulta);
		lstDistritoGarantiaConsulta = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaConsulta, codigoProvinciaGarantiaConsulta);
	}
	//End: Para Dialog: dlgConsultaGarantia
	
	//*************************************//
   	// End: Metodos para Dialogs 
  	//*************************************//

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
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



	public EGarantiaSolicitud getoEGarantiaSolicitudData() {
		return oEGarantiaSolicitudData;
	}

	public void setoEGarantiaSolicitudData(EGarantiaSolicitud oEGarantiaSolicitudData) {
		this.oEGarantiaSolicitudData = oEGarantiaSolicitudData;
	}

	public EGarantiaDetalleSolicitud getoEGarantiaDetalleSolicitudData() {
		return oEGarantiaDetalleSolicitudData;
	}

	public void setoEGarantiaDetalleSolicitudData(
			EGarantiaDetalleSolicitud oEGarantiaDetalleSolicitudData) {
		this.oEGarantiaDetalleSolicitudData = oEGarantiaDetalleSolicitudData;
	}


	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}

	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}

	public EGarantiaSolicitud getoEGarantiaAsociadaSolicitudData() {
		return oEGarantiaAsociadaSolicitudData;
	}

	public void setoEGarantiaAsociadaSolicitudData(
			EGarantiaSolicitud oEGarantiaAsociadaSolicitudData) {
		this.oEGarantiaAsociadaSolicitudData = oEGarantiaAsociadaSolicitudData;
	}


	public EGarantia getoEDlgGarantiaData() {
		return oEDlgGarantiaData;
	}


	public void setoEDlgGarantiaData(EGarantia oEDlgGarantiaData) {
		this.oEDlgGarantiaData = oEDlgGarantiaData;
	}


	public ETipoCambio getoETipoCambioData() {
		return oETipoCambioData;
	}


	public void setoETipoCambioData(ETipoCambio oETipoCambioData) {
		this.oETipoCambioData = oETipoCambioData;
	}


	public EGarantiaTramite getoEGarantiaTramiteData() {
		return oEGarantiaTramiteData;
	}


	public void setoEGarantiaTramiteData(EGarantiaTramite oEGarantiaTramiteData) {
		this.oEGarantiaTramiteData = oEGarantiaTramiteData;
	}
	
	
	
	
	
	
	
}
