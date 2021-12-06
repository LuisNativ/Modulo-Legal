package com.abaco.ageneral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UClaseGarantia;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;

@ManagedBean(name = "mblistagarantiaporconstituir")
@ViewScoped
public class MBListaGarantiaPorConstituir implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EGarantia oEGarantiaData;
	private EGeneral oEGeneralData;
	private EGarantia oEGarantiaSelected;
	
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitud;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitudNueva;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitudExistente;
	@Getter @Setter private List<EGarantia> lstGarantiaVigente;
	@Getter @Setter private List<ESaldoServicio> lstSaldoServicio;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaReal;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaOtros;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaFiltro;
	@Getter @Setter private List<EPersona> lstPersona;
	

	@Getter @Setter private int codigoBuscarGarantia,codigoBuscarSolicitud;
	@Getter @Setter private String descripcionBuscarGarantia,descripcionBuscarSolicitud;
	@Getter @Setter private int codigoTabview2Index;
	@Getter @Setter private String tipoGarantiaBuscar;
	@Getter @Setter private String nombreTipoGarantia;
	@Getter @Setter private String mensajeValidacion;
	@Getter @Setter private int cantidadCaracteres;
	
	//Para mostrar los Créditos Directos, Indirectos y Garantia
	@Getter @Setter private double creditoDirMonedaNacional;
	@Getter @Setter private double creditoDirMonedaExtranjera;
	@Getter @Setter private double creditoIndMonedaNacional;
	@Getter @Setter private double creditoIndMonedaExtranjera;
	@Getter @Setter private double garantiaMonedaNacional;
	@Getter @Setter private double garantiaMonedaExtranjera;
	
	//Indicadores
	@Getter @Setter private boolean indicadorBoton; 
	@Getter @Setter private boolean indicadorNuevoTipoGarantia;
	@Getter @Setter private int indicadorTabView;
	
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();	
		this.oEGarantiaData = new EGarantia();
		this.oEGeneralData = new EGeneral();	
		this.oEGarantiaSelected = new EGarantia();
		
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoGarantiaReal = new ArrayList<EGeneral>();
		lstTipoGarantiaOtros = new ArrayList<EGeneral>();
		lstTipoGarantiaFiltro = new ArrayList<EGeneral>();

		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		indicadorTabView= UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.TABVIEWINDEX) != null ? (int) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.TABVIEWINDEX): 0 ;
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.TABVIEWINDEX);
		inicializar();
		
		listarGarantiaSolicitudNueva();
		listarGarantiaSolicitudExistente();
		listarDesplegable();
	}
	
	public void inicializar(){
		indicadorBoton = true;
		nombreTipoGarantia = "";
		codigoTabview2Index = 0;
	}
	
	public void listarDesplegable(){
		lstTipoGarantiaReal = oUManejadorListaDesplegable.obtieneTipoGarantiaF9205();
		lstTipoGarantiaOtros = oUManejadorListaDesplegable.obtieneTipoGarantiaNuevoF9205();
	}
	
	
	/*
	 * MÉTODOS PARA SOLICITUDES ASOCIADAS A UNA GARANTIA 
	 * PENDIENTES DE REGISTRO (TAB = 1)
	 *  
	 */
	
	
	//Método para Identificar el Cambio de un Tab dentro de un Tabview
	public void onTabChange(TabChangeEvent event){
		String titulo = event.getTab().getTitle();
		if(titulo.equals("Garantía Real Nueva")) codigoTabview2Index = 0;
		else codigoTabview2Index = 1;
	}
	
	//Buscar Solicitudes según Codigo y Descripcion
	public void buscarGarantiaSolicitud(){
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(codigoBuscarSolicitud, descripcionBuscarSolicitud);
		if(codigoTabview2Index == 0){
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			if(lstGarantiaSol != null){
				for(EGarantiaSolicitud obj: lstGarantiaSol){
					if(obj.getCodigoEstadoGarantiaSolicitud() != UEstado.PENDIENTEGARANTIAREGISTRO && 
					   obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS && obj.getCodigoEstadoEvaluacionLegal() == 6){
						lstGarantiaSolicitudNueva.add(obj);
					}
				}
			}
		}else if (codigoTabview2Index == 1){
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
			if(lstGarantiaSol != null){
				for(EGarantiaSolicitud obj: lstGarantiaSol){
					if(obj.getCodigoEstadoGarantiaSolicitud() != UEstado.PENDIENTEGARANTIAREGISTRO &&
					   obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES && obj.getCodigoEstadoEvaluacionLegal() == 6){
						lstGarantiaSolicitudExistente.add(obj);
					}
				}
			}
		}else{
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		}
	}
	
	//Listado de Solicitudes con Garantia Real Nueva
	public void listarGarantiaSolicitudNueva() {
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(0, "");
		if(lstGarantiaSol != null){
			for(EGarantiaSolicitud obj: lstGarantiaSol){
				if(obj.getCodigoEstadoGarantiaSolicitud() != UEstado.PENDIENTEGARANTIAREGISTRO &&
						obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS && obj.getCodigoEstadoEvaluacionLegal() == 6){
					lstGarantiaSolicitudNueva.add(obj);
				}
			}
		}
	}
	
	//Listado de Solicitudes con Garantia Real Existente
	public void listarGarantiaSolicitudExistente() {
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(0, "");
		if(lstGarantiaSol != null){
			for(EGarantiaSolicitud obj: lstGarantiaSol){
				if(obj.getCodigoEstadoGarantiaSolicitud() != UEstado.PENDIENTEGARANTIAREGISTRO && 
				   obj.getCodigoEstadoGarantiaSolicitud() != UEstado.REGISTRADOGARANTIAPENDIENTE &&
				   obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES && obj.getCodigoEstadoEvaluacionLegal() == 6){
					lstGarantiaSolicitudExistente.add(obj);
				}
			}
		}
		
	}
	
	public void evaluarSolicitud(EGarantiaSolicitud oEGarantiaSolicitudItem) {
		String ruta = "";
		if (oEGarantiaSolicitudItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSolicitudItem);
			
			ruta = "RegistroOperacionGarantiaSolicitud.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	
	
	/*
	 * MÉTODOS PARA EL MANTENIMIENTO DE GARANTÍAS (TAB = 2)
	 *  
	 */
	
    //Listar Garantias Vigentes y Saldo de Servicios del Cliente
	public void buscarGarantiaVigente(){
		if(codigoBuscarGarantia!=0){
			if(descripcionBuscarGarantia.length()!=0){
				creditoDirMonedaNacional =  0;
				creditoDirMonedaExtranjera = 0;
				creditoIndMonedaNacional =  0;
				creditoIndMonedaExtranjera = 0;
				garantiaMonedaNacional = 0;
				garantiaMonedaExtranjera = 0; 
				
				lstGarantiaVigente = oBOGarantia.listarGarantiaVigente(codigoBuscarGarantia, descripcionBuscarGarantia);
				if(lstGarantiaVigente == null || lstGarantiaVigente.isEmpty()){
					lstGarantiaVigente = new ArrayList<EGarantia>();
					lstPersona = oBOCliente.listarSocioyTercero(1, descripcionBuscarGarantia);
					if(lstPersona == null || lstPersona.isEmpty()){
						oEGarantiaData = new EGarantia();
						indicadorBoton=true;
					}else{
						oEGarantiaData.setCodigoCliente(lstPersona.get(0).getCodigo());
						oEGarantiaData.setNombreCorto(lstPersona.get(0).getNombreCorte());
						indicadorBoton = false;
					}				
				}else{
					oEGarantiaData.setCodigoCliente(lstGarantiaVigente.get(0).getCodigoCliente());
					oEGarantiaData.setNombreCorto(lstGarantiaVigente.get(0).getNombreCorto());
					indicadorBoton = false;
					if(lstGarantiaVigente.get(0).getCodigoCliente() > 0){
						lstSaldoServicio = oBOGarantia.obtenerSaldosServiciosCliente(lstGarantiaVigente.get(0).getCodigoCliente());
						if(lstSaldoServicio != null){
							for (ESaldoServicio eSaldoServicio : lstSaldoServicio) {
								switch (eSaldoServicio.getClaseServicio()) {
								case 2://Para Credito Directo	
									if(creditoDirMonedaNacional == 0) creditoDirMonedaNacional =  eSaldoServicio.getCodigoMoneda() == 1 ? eSaldoServicio.getMontoSaldo() : 0;
									if(creditoDirMonedaExtranjera == 0) creditoDirMonedaExtranjera = eSaldoServicio.getCodigoMoneda() == 2 ? eSaldoServicio.getMontoSaldo() : 0;
									break;
								case 3://Para Credito Indirecto
									if(creditoIndMonedaNacional == 0) creditoIndMonedaNacional =  eSaldoServicio.getCodigoMoneda() == 1 ? eSaldoServicio.getMontoSaldo() : 0;
									if(creditoIndMonedaExtranjera == 0) creditoIndMonedaExtranjera = eSaldoServicio.getCodigoMoneda() == 2 ? eSaldoServicio.getMontoSaldo() : 0;
									break;
								case 5: //Para Garantia
									if(garantiaMonedaNacional == 0) garantiaMonedaNacional =  eSaldoServicio.getCodigoMoneda() == 1 ? eSaldoServicio.getMontoSaldo() : 0;
									if(garantiaMonedaExtranjera == 0) garantiaMonedaExtranjera = eSaldoServicio.getCodigoMoneda() == 2 ? eSaldoServicio.getMontoSaldo() : 0;
									break;
								default:
									break;
								}							
							}
						}
					}
				}
			}else{
				oEMensaje.setDescMensaje("Complete el Campo Descripción");
				RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
			}
		}else{
			oEMensaje.setDescMensaje("Seleccione un Tipo de Búsqueda");
			RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
		}
	}
	
	/*Metodo para Obtener la cantidad maxima de caracteres por cada opcion
	 * de busqueda*/
	public void validarLongitudCaracteres(){
		switch(codigoBuscarGarantia){
		  case 1: cantidadCaracteres = 9; break;
		  case 2: cantidadCaracteres = 30; break;
		  case 3: cantidadCaracteres = 9; break;
		  case 4: cantidadCaracteres = 10; break;
		  default: cantidadCaracteres= 250;
		}
	}
	
	//Listar Tipos de Garantia y llevarlo a un Dialog
	public void buscarTiposGarantia(){
		lstTipoGarantia = oUManejadorListaDesplegable.obtieneTipoGarantia();
		lstTipoGarantiaFiltro = lstTipoGarantia;
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarTipoGarantia').show();");
	}
	
	//Filtrar Tipo de Garantia segun Descripcion
	public void filtrarTipoGarantia(){
		lstTipoGarantiaFiltro = lstTipoGarantia.stream()
				   .filter(x -> x.getDescripcion().matches("(?i).*"+ tipoGarantiaBuscar +".*"))
				   .collect(Collectors.toList());
	}
	

	//Metodo para visualizar Vista de Registro de Nueva Garantia con Datos del Cliente
	public void nuevaGarantia(EGeneral eTipoGarantiaItem){
		if(eTipoGarantiaItem != null){
			String ruta = "";
			eTipoGarantiaItem.setCodigo3(oEGarantiaData.getCodigoCliente());
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, eTipoGarantiaItem);
			ruta = "MantenimientoOperacionGarantia.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	//Metodo para visualizar Vista de Modificación de una Garantía 
	public void modificarGarantia(EGarantia oEGarantiaItem){
		if (oEGarantiaItem != null) {
			oEGarantiaSelected = oEGarantiaItem;
			String ruta = "";
			if (oEGarantiaSelected != null) {
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSelected);
				
				ruta = "MantenimientoOperacionGarantia.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}
	}
	
	//Método para Visualizar Vista de Trámite de una Garantía
	public void tramiteGarantia(EGarantia oEGarantiaItem){
		if (oEGarantiaItem != null) {
			oEGarantiaSelected = oEGarantiaItem;
			String ruta = "";
			if (oEGarantiaSelected != null) {
				Object oEGarantiaTramite = oBOGarantia.buscarGarantiaTramite(oEGarantiaSelected.getCodigoGarantia());
				
				if(oEGarantiaTramite == null){
					UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
				}else{
					UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				}
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSelected);
				
				ruta = "TramiteOperacionGarantia.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}
	}
	
	
	/*
	 * MÉTODOS PARA LA GESTIÓN DE TIPOS DE GARANTÍAS (TAB = 3) 
	 *  
	 */
	
	//Método para mostrar Dialog de Registro de un Nuevo Tipo de Garantía
	public void nuevoTipoGarantia(){
		oEGeneralData=new EGeneral();
		RequestContext.getCurrentInstance().execute("PF('dlgNuevoTipoGarantia').show();");
		mensajeValidacion = "";
		indicadorNuevoTipoGarantia = true;
	}
	
	//Método para mostrar Dialog de Modificación de un Tipo de Garantía
	public void modificarTipoGarantia(EGeneral oEGeneralItem){
		if(oEGeneralItem != null){
			oEGeneralData = oEGeneralItem;
			mensajeValidacion = "";
			indicadorNuevoTipoGarantia = false;
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoTipoGarantia').show();");
		}
	}
	
	//Método para Registrar un Nuevo Tipo de Garantía
	public void guardarTipoGarantia(){
		EGeneral eTipoGarantia = new EGeneral();
		eTipoGarantia = oEGeneralData;
		
		if(!oEGeneralData.getDescripcion().trim().isEmpty()){
			if(eTipoGarantia.getCodigo2() == 0){
				oEMensaje = oBOGarantia.registrarTipoGarantia(eTipoGarantia);
			}else{
				oEMensaje = oBOGarantia.modificarTipoGarantia(eTipoGarantia);
				
			}
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoTipoGarantia').hide();");
			
		}else{
			mensajeValidacion = "Ingrese Campo Descripción";
		}
	}
	
	public void salir(){
		listarDesplegable();
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

	public EGeneral getoEGeneralData() {
		return oEGeneralData;
	}

	public void setoEGeneralData(EGeneral oEGeneralData) {
		this.oEGeneralData = oEGeneralData;
	}
	
	
	
}
