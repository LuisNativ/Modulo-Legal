package com.abaco.ageneral;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;

@ManagedBean(name = "mblistagarantiaporconstituircredito")
@ViewScoped
public class MBListaGarantiaPorConstituirCredito implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private EGarantia oEGarantiaData;
	private EPoliza oEPolizaSelected;
	private EPersona oEPersonaSelected;
	private EPoliza oEPolizaData;
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private BOSolicitudCredito oBOSolicitudCredito;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitudNueva;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitudExistente;
	@Getter @Setter private List<EGarantiaSolicitud> lstHistoricoGarantiaSolicitud;
	@Getter @Setter private List<EGarantia> lstGarantiaPoliza;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaFiltro;
	@Getter @Setter private List<EGeneral> lstEstadoGarantiaSolicitud;
	@Getter @Setter private List<EPoliza> lstPolizaSeguro ;
	@Getter @Setter private List<EGeneral> lstTipoCiaSeguro;
	@Getter @Setter private List<EGeneral> lstBrockerSeguro;
	@Getter @Setter private List<EGeneral> lstTipoPoliza;
	@Getter @Setter private List<EGeneral> lstMoneda;
	@Getter @Setter private List<EPersona> lstPersona;

	/* Variables Interfaz */
	@Getter @Setter private String tipoGarantiaBuscar;
	@Getter @Setter private int codigoBuscar,codigoBuscarH,codigoBuscarP,codigoBuscarPoliza,codigoEstadoGarSolicitud,codigoBuscarSocio;
	@Getter @Setter private int codigoTabviewIndex;
	@Getter @Setter private String descripcionBuscar,descripcionBuscarH,descripcionBuscarP,descripcionBuscarPoliza,descripcionBuscarPoliza2,descripcionBuscarSocio;
	@Getter @Setter private int indicadorTabView;
	@Getter @Setter private int indicadorCiaSeguro; 
	@Getter @Setter private long codigoGarantia;
	@Getter @Setter private int indicadorPersona;
	@Getter @Setter private boolean deshabilitarCampoPoliza;
	@Getter @Setter private int indicadorActualizador;
	@Getter @Setter private boolean visualizar1eraDescripcion,visualizar2daDescripcion;
	@Getter @Setter private int indicadorTituloDescripcionPoliza;


	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();	
		this.oEGarantiaData = new EGarantia();

		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oEPolizaData = new EPoliza();
		oEPersonaSelected = new EPersona();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		lstHistoricoGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaPoliza = new ArrayList<EGarantia>();
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoGarantiaFiltro = new ArrayList<EGeneral>();
		lstEstadoGarantiaSolicitud = new ArrayList<EGeneral>();
		lstTipoCiaSeguro = new ArrayList<EGeneral>();
		lstBrockerSeguro = new ArrayList<EGeneral>();
		lstTipoPoliza = new ArrayList<EGeneral>();
		lstMoneda = new ArrayList<EGeneral>();
		lstPersona = new ArrayList<EPersona>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();
		verificarGarantiaSolicitud();
		listarGarantiaSolicitudNueva();
		listarGarantiaSolicitudExistente();
		listarHistoricoGarantiaSolicitud();
		listarPolizasconGarantia();
		listarDesplegable();
	}
	
	public void inicializar(){
		visualizar1eraDescripcion = true;
		visualizar2daDescripcion = false;
		indicadorTituloDescripcionPoliza = 0;
		codigoTabviewIndex = 0;
		codigoGarantia = 0;
		deshabilitarCampoPoliza = false;
	}
	
	public void listarDesplegable(){
		lstEstadoGarantiaSolicitud = oUManejadorListaDesplegable.obtieneEstadoGarantiaSolicitud();
		lstTipoCiaSeguro = oUManejadorListaDesplegable.obtieneTipoCiaSeguro();
		lstBrockerSeguro = oUManejadorListaDesplegable.obtieneBrockerSeguros();
		lstTipoPoliza = oUManejadorListaDesplegable.obtieneTipoPoliza();
		lstMoneda = oUManejadorListaDesplegable.obtieneTipoMoneda();
	}
	
	/*
	 * METODOS DE GARANTIA PENDIENTES DE REGISTRO
	 * 
	 * */
	
	//Listado de Solicitudes con Garantia Real Nueva
	public void listarGarantiaSolicitudNueva() {
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(0, "");
		if(lstGarantiaSol != null){
			for(EGarantiaSolicitud obj: lstGarantiaSol){
				if(obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS && obj.getCodigoEstadoEvaluacionLegal() == 6){
					lstGarantiaSolicitudNueva.add(obj);
				}
			}
		}
		
	}
	
	//Listado de Solicitudes con Garantia Real Existente
	public void listarGarantiaSolicitudExistente() {
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(0, "");
		EEvaluacionSolicitudCreditoLegal evaluacion = null;
		if(lstGarantiaSol != null){
			for(EGarantiaSolicitud obj: lstGarantiaSol){
				if(obj.getCodigoEstadoGarantiaSolicitud() != UEstado.REGISTRADOGARANTIAPENDIENTE && 
				   obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES && obj.getCodigoEstadoEvaluacionLegal() == 6){
					    	lstGarantiaSolicitudExistente.add(obj);			
				}
			}
		}
		
	}
	
	//Buscar Solicitudes según Codigo y Descripcion
	public void buscarGarantiaSolicitud(){
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(codigoBuscar, descripcionBuscar);
		if(codigoTabviewIndex == 0){
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			if(lstGarantiaSol != null){
				for(EGarantiaSolicitud obj: lstGarantiaSol){
					if(obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS && obj.getCodigoEstadoEvaluacionLegal() == 6){
						lstGarantiaSolicitudNueva.add(obj);
					}
				}
			}
		}else if (codigoTabviewIndex == 1){
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
			if(lstGarantiaSol != null){
				for(EGarantiaSolicitud obj: lstGarantiaSol){
					if(obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES && obj.getCodigoEstadoEvaluacionLegal() == 6){
						lstGarantiaSolicitudExistente.add(obj);
					}
				}
			}
		}else{
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		}
	}
	
	
	//Listado de Solicitudes Filtrado por su Estado
	public void listarSolicitudPorEstado(){
		if(codigoTabviewIndex == 0){
			if(codigoEstadoGarSolicitud == 0 ){
				lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
				listarGarantiaSolicitudNueva();
			}else{
				lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
				List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(2, codigoEstadoGarSolicitud+"");
				if(lstGarantiaSol != null){
					for(EGarantiaSolicitud obj: lstGarantiaSol){
						if(obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS){
							lstGarantiaSolicitudNueva.add(obj);
						}
					}
				}
			}
		}else if(codigoTabviewIndex == 1){
			if(codigoEstadoGarSolicitud == 0 ){
				lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
				listarGarantiaSolicitudExistente();
			}else{
				lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
				List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(2, codigoEstadoGarSolicitud+"");
				if(lstGarantiaSol != null){
					for(EGarantiaSolicitud obj: lstGarantiaSol){
						if(obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES){
							lstGarantiaSolicitudExistente.add(obj);
						}
					}
				}
			}
		}else{
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		}
		
		
	}
	
	//Método para Identificar el Cambio de un Tab dentro de un Tabview
	public void onTabChange(TabChangeEvent event){
		String titulo = event.getTab().getTitle();
		if(titulo.equals("Garantía Real Nueva")) codigoTabviewIndex = 0;
		else codigoTabviewIndex = 1;
	}
	
	//Método para evaluar una solicitud con garantia asociada
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
	 * METODOS PARA HISTORICO
	 * */
	
	
	public void listarHistoricoGarantiaSolicitud(){
		lstHistoricoGarantiaSolicitud = oBOGarantia.listarHistoricoGarantiaSolicitud(0, "");
	}
	
	public void buscarHistoricoGarantiaSolicitud(){
		lstHistoricoGarantiaSolicitud = oBOGarantia.listarHistoricoGarantiaSolicitud(codigoBuscarH, descripcionBuscarH);
	}
	
	
	
	/*Metodo para añadir solicitudes en GarantiaAnexoF7325
	  cuando existan nuevos registros en la F7325
	*/
	public void verificarGarantiaSolicitud() {
		oBOGarantia.registrarGarantiaSolicitudAnexoF7325();
	}
	
	//Listado de Garantias para el Llenado de Pólizas
	public void listarPolizasconGarantia(){
		lstGarantiaPoliza = oBOGarantia.listarGarantia(0, "0");
	}
	
	public void buscarPolizaconGarantia(){
		lstGarantiaPoliza = oBOGarantia.listarGarantia(codigoBuscarP, descripcionBuscarP);
	}
	
	//Begin: Para Dialog: dlgBuscarCiaSeguro
	public void buscarPoliza(EGarantia eGarantiaItem){
		if(eGarantiaItem != null){
			codigoGarantia = eGarantiaItem.getCodigoGarantia();
			EPoliza ePoliza = new EPoliza();
			ePoliza.setCodigoCiaSeguro(eGarantiaItem.getCodigoCiaSeguro());
			ePoliza.setNumeroPoliza(eGarantiaItem.getPoliza());
			ePoliza.setCorrelativoPoliza(eGarantiaItem.getCodigoInspector());
			lstPolizaSeguro = oBOGarantia.buscarPolizaSeguro(ePoliza);
		}
		codigoBuscarPoliza = 0;
		descripcionBuscarPoliza = "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarCiaSeguro').show();");
	}
	
	public void filtrarPoliza(){	
		lstPolizaSeguro = oBOGarantia.listarPolizaSeguro(codigoBuscarPoliza, descripcionBuscarPoliza,descripcionBuscarPoliza2);
	}
	
	public void validarCriterioBusqueda(){
		switch(codigoBuscarPoliza){
		 case 1: 
		 case 2:
		 case 3:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = false; 
			 break;
		 case 4:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = true; 
			 break;
		 case 5:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = true; 
			 break;
		 default:
			 visualizar1eraDescripcion = true; 
			 visualizar2daDescripcion = false; 
		}
	}
		
	public void asignarPoliza(){
		if(oEPolizaSelected != null){
			EGarantia eGarantia = new EGarantia();
			eGarantia.setCodigoGarantia(codigoGarantia);
			eGarantia.setCodigoCiaSeguro(oEPolizaSelected.getCodigoCiaSeguro());
			eGarantia.setPoliza(oEPolizaSelected.getNumeroPoliza());
			eGarantia.setCodigoInspector(oEPolizaSelected.getCorrelativoPoliza());
			oEMensaje = oBOGarantia.modificarGarantiaPoliza(eGarantia);
			
			indicadorActualizador = 1;
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			
		}
		
	}
	
	public void nuevaPoliza(){
		oEPolizaData = new EPoliza();
		deshabilitarCampoPoliza = false;
		RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoPoliza').show();");
	}
	
    //End: Para Dialog: dlgBuscarCiaSeguro
	
	//Mantenimiento Poliza
	public void grabarPoliza(){
		if(oEPolizaData != null){
			
			EPoliza validarPoliza = oBOGarantia.buscarPoliza(oEPolizaData);
			
			EPoliza ePoliza = oEPolizaData;
			ePoliza.setUsuarioRegistro(oEUsuario);
			
			if(validarPoliza != null){
				oEMensaje = oBOGarantia.modificarPoliza(ePoliza);
				
			}else{
				oEMensaje = oBOGarantia.registrarPoliza(ePoliza);
			}
			indicadorActualizador = 2;
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoPoliza').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			

		}
	}
	
	
	
	public void modificarPoliza(EPoliza ePolizaItem){
		if(ePolizaItem != null){
			oEPolizaData = ePolizaItem;
			deshabilitarCampoPoliza = true;
			
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoPoliza').show();");
		}

		
	}
	
	public void eliminarPoliza(EPoliza ePolizaItem){
		if(ePolizaItem != null){

			oEMensaje = oBOGarantia.eliminarPoliza(ePolizaItem);
			indicadorActualizador = 2;
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}

		
	}
	
	//->Begin: Para Dialog: dlgBuscarSocio
	public void buscarIndicadorPersona(int codigo){
		indicadorPersona = codigo;
		lstPersona = new ArrayList<EPersona>();
		codigoBuscarSocio = 0;
		descripcionBuscarSocio= "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarSocio').show();");
	}
	
	/*BÚSQUEDA SOCIO(F5101)/TERCERO(F5151)*/
	public void buscarSocio(){
		lstPersona = oBOCliente.listarSocioyTercero(codigoBuscarSocio,descripcionBuscarSocio);
	}
		
		
	public void asignarPersona(){
		if(oEPersonaSelected != null){
			switch (indicadorPersona) {
			case 1:
				oEPolizaData.setCodigoClienteUltimoEndoso(oEPersonaSelected.getCodigo());
				oEPolizaData.setNombreClienteUltimoEndoso(oEPersonaSelected.getNombreCorte());
				break;
			default:
				oEPolizaData.setCodigoClienteUltimoEndoso(0);
				oEPolizaData.setNombreClienteUltimoEndoso("");
				break;
			}
			
			
		}
	}
	//->End: Para Dialog: dlgBuscarSocio

	public void actualizarDatoasAjax(){
		switch(indicadorActualizador){
		case 1: listarPolizasconGarantia(); break;
		case 2: lstPolizaSeguro = oBOGarantia.listarPolizaSeguro(0, "",""); break;
		default:
		}
		
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

	public EPoliza getoEPolizaSelected() {
		return oEPolizaSelected;
	}

	public void setoEPolizaSelected(EPoliza oEPolizaSelected) {
		this.oEPolizaSelected = oEPolizaSelected;
	}

	public EPoliza getoEPolizaData() {
		return oEPolizaData;
	}

	public void setoEPolizaData(EPoliza oEPolizaData) {
		this.oEPolizaData = oEPolizaData;
	}

	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}

	
	

}
