package com.abaco.ageneral;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import com.abaco.ageneral.BORevision;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMotivo;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UTipoBusquedaTercero;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoServicio;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.ULectorDeParametros;
import com.abaco.negocio.util.UManejadorCorreo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;

@ManagedBean(name = "mbrevisionmanual")
@ViewScoped
public class MBRevisionManual implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private ERevisionSolicitud oERevisionSolicitudLoad;
	private EOpcion oEOpcionLoad;
	
	private ERevisionSolicitud oERevisionSolicitudData;
	
	private EPersona oEPersonaSelected;
	private EGeneral oEEmisorSelected;
	
	private BORevision oBORevision;
	private BOGeneral oBOGeneral;
	private BOCliente oBOCliente;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstMotivo;
	@Getter @Setter private List<EGeneral> lstServicio;
	@Getter @Setter private List<EGeneral> lstServicioFiltro;
	@Getter @Setter private List<EGeneral> lstTipoPersonaRelacion;
	@Getter @Setter private List<EGeneral> lstAreaEmisor;
	@Getter @Setter private List<EGeneral> lstUsuarioEmisor;
	@Getter @Setter private List<EGeneral> lstUsuarioEmisorFiltro;
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private List<EGeneral> lstDOI;
	
	/* Variables Interfaz */
	@Getter @Setter private String descripcionMensajeSeleccion;
	@Getter @Setter private String descripcionMensajeUltimo;
	@Getter @Setter private String descripcionDocumentoCarga;
	@Getter @Setter private int codigoTipoClientePersonaBuscar;
	@Getter @Setter private int codigoPersonaBuscar;
	@Getter @Setter private String nombrePersonaBuscar;
	@Getter @Setter private String nombreEmisorBuscar;
	@Getter @Setter private String horaInicioHH;
	@Getter @Setter private String horaInicioMM;
	
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
	@Getter @Setter private String mensajeValidacion;

	/* Variables Internas */
	private int codigoEstado;
	
	private int accionExterna;

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oERevisionSolicitudLoad = new ERevisionSolicitud();
		this.oEOpcionLoad = new EOpcion();
		
		this.oERevisionSolicitudData = new ERevisionSolicitud();
		
		oEPersonaSelected = new EPersona();
		oEEmisorSelected = new EGeneral();
		
		oBORevision = new BORevision();
		oBOGeneral = new BOGeneral();
		oBOCliente = new BOCliente();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstNivel = new ArrayList<EGeneral>();
		lstMotivo = new ArrayList<EGeneral>();
		lstServicio = new ArrayList<EGeneral>();
		lstTipoPersonaRelacion = new ArrayList<EGeneral>();
		lstAreaEmisor = new ArrayList<EGeneral>();
		lstUsuarioEmisor = new ArrayList<EGeneral>();
		lstUsuarioEmisorFiltro = new ArrayList<EGeneral>();
		lstDOI = new ArrayList<EGeneral>();
		
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
				oEOpcionLoad.setIndicadorReenviar(1);
				oEOpcionLoad.setIndicadorCerrar(1);
				oEOpcionLoad.setIndicadorDescartar(1);
				oEOpcionLoad.setIndicadorCancelar(1);
				indicadorGuardar = 1;
				indicadorSalir = 1;
			}else if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oERevisionSolicitudLoad = (ERevisionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					//oEOpcionLoad = oBORevision.buscarOpcionPorSolicitud(oERevisionSolicitudLoad.getCodigoSolicitud(), oEUsuario);
					
					oERevisionSolicitudData = oERevisionSolicitudLoad;
					oEOpcionLoad.setIndicadorReenviar(1);
					oEOpcionLoad.setIndicadorCerrar(1);
					oEOpcionLoad.setIndicadorDescartar(1);
					oEOpcionLoad.setIndicadorCancelar(1);
					indicadorGuardar = 1;
					indicadorSalir = 1;
				}
			}
			
			listarDesplegable();
			listarMensajeTemporal();
			visualizarPersonaRelacion();
			deshabilitarPersonaRelacion();
		}
	}
	
	public void obtenerEstado(int ind){
		if(validar()){
			if (ind == 1) {
				codigoEstado = UEstado.PENDIENTEDEEVALUACION;
				RequestContext.getCurrentInstance().execute("PF('dlgEnviarDigitalizacion').show();");
			}else if(ind == 2){
				codigoEstado = UEstado.OBSERVADO;
				RequestContext.getCurrentInstance().execute("PF('dlgEnviarDigitalizacion').show();");
			}else if(ind == 3){
				codigoEstado = UEstado.CERRADO;
				guardar();
			}else if(ind == 4){
				codigoEstado = UEstado.DESAPROBADO;
				obtenerMotivo();
				RequestContext.getCurrentInstance().execute("PF('dlgDescartar').show();");
			}else if(ind == 5){
				codigoEstado = UEstado.CANCELADO;
				guardar();
			}else if(ind == 7){
				codigoEstado = UEstado.AUTORIZADO;
				RequestContext.getCurrentInstance().execute("PF('dlgEnviarDigitalizacion').show();");
			}else if(ind == 6){
				indicadorTemporal = UIndicadorTemporal.SI;
				guardar();
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void guardar() {
		ERevisionSolicitud oERevisionSolicitud = new ERevisionSolicitud();
		
		oERevisionSolicitud = oERevisionSolicitudData;
		oERevisionSolicitud.setCodigoEstado(codigoEstado);
		oERevisionSolicitud.setIndicadorTemporal(indicadorTemporal);
		oERevisionSolicitud.setIndicadorDigitalizacion(indicadorDigitalizacion);

		oERevisionSolicitud.setFechaRegistro(new Date());
		oERevisionSolicitud.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.NUEVO == accionExterna){
			oERevisionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REVISIONSOLICITUD, "", "", ""));
			oERevisionSolicitud.setCodigoTipoEnvio(UTipoEnvio.PRIVADO);
			oERevisionSolicitud.setCodigoAreaReceptor(oEUsuario.getCodigoArea());
			oERevisionSolicitud.setCodigoReceptor(oEUsuario.getIdUsuario());
			oERevisionSolicitud.setDescripcionReceptor(oEUsuario.getNombreCompleto());
			
			oEMensaje = oBORevision.agregarSolicitudManual(oERevisionSolicitud);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBORevision.modificarSolicitudManual(oERevisionSolicitud);
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
		
		if(oERevisionSolicitudData.getCodigoNivel() == 0){
			mensajeValidacion = "Seleccione nivel";
			ind = false;
		}else if(oERevisionSolicitudData.getCodigoEmisor() == 0){
			mensajeValidacion = "Ingrese Usuario de consulta";
			ind = false;
		}else if(oERevisionSolicitudData.getDescripcionAsunto().equals("")){
			mensajeValidacion = "Ingrese asunto";
			ind = false;
		}else if(oERevisionSolicitudData.getDescripcionMensaje().equals("")){
			mensajeValidacion = "Ingrese mensaje";
			ind = false;
		}else if(oERevisionSolicitudData.getHoraInicio() == null){
			mensajeValidacion = "Ingrese hora Inicio";
			ind = false;
		}else if(oERevisionSolicitudData.getHoraFin() == null){
			mensajeValidacion = "Ingrese hora Fin";
			ind = false;
		}else if(oERevisionSolicitudData.getCodigoPersonaRelacion() == UPersonaRelacion.EXTERNO){
			if(oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals("0")) {
				mensajeValidacion = "Ingrese tipo documento Persona";
				ind = false;
			}else if(oERevisionSolicitudData.getNumeroDocumentoPersona().equals("")) {
				mensajeValidacion = "Ingrese numero documento Persona";
				ind = false;
			}else if(oERevisionSolicitudData.getNombrePersona().equals("")) {
				mensajeValidacion = "Ingrese nombre Persona";
				ind = false;
			}
			
			if(oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.DNI)) {
				if(oERevisionSolicitudData.getNumeroDocumentoPersona().length() < 8 ) {
					mensajeValidacion = "Ingrese número documento valido";
					ind = false;
				}
			}else if(oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.RUC)) {
				if(oERevisionSolicitudData.getNumeroDocumentoPersona().length() < 11 ) {
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
		}

        return ind;
	}
	
	public void salir() {
		//String ruta = "volver_BandejaRevisionLegal";
		String ruta = "BandejaRevisionLegal.xhtml";
		
		inicializar();
		/*Cerramos Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		//return ruta;
	}
	
	public void inicializarUsuarioEmisor(){
		oERevisionSolicitudData.setCodigoEmisor(0);
		oERevisionSolicitudData.setDescripcionEmisor("");
		nombreEmisorBuscar = "";
	}
	
	public void buscarUsuarioEmisor(){
		lstUsuarioEmisor = oUManejadorListaDesplegable.obtieneUsuarioPorArea(oERevisionSolicitudData.getCodigoAreaEmisor(), UTipoEstadoUsuario.ACTIVO);
		lstUsuarioEmisorFiltro = lstUsuarioEmisor;
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarUsuarioEmisor').show();");
	}
	
	public void filtrarUsuarioEmisor(){
		lstUsuarioEmisorFiltro = lstUsuarioEmisor.stream()
				   .filter(x -> x.getDescripcion().matches("(?i).*"+ nombreEmisorBuscar +".*"))
				   .collect(Collectors.toList());
	}
	
	public void asignarUsuarioEmisor() {
		if(oEEmisorSelected != null){
			oERevisionSolicitudData.setCodigoEmisor(oEEmisorSelected.getCodigo2());
			oERevisionSolicitudData.setDescripcionEmisor(oEEmisorSelected.getDescripcion());
		}
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
	
	public void obtenerPersonaRelacion(){
		oERevisionSolicitudData.setCodigoClientePersona(0);
		oERevisionSolicitudData.setCodigoTipoClientePersona(UTipoCliente.COD_SOCIO);
		oERevisionSolicitudData.setCodigoTipoDocumentoPersona("");
		oERevisionSolicitudData.setNumeroDocumentoPersona("");
		oERevisionSolicitudData.setNombrePersona("");
		
		deshabilitarPersonaRelacion();
		visualizarPersonaRelacion();
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
	
	public void obtenerHoraInicio(){
		DateFormat hourFormat = new SimpleDateFormat("HH");
		DateFormat minFormat = new SimpleDateFormat("MM");
		horaInicioHH = hourFormat.format(oERevisionSolicitudData.getHoraInicio());
		horaInicioMM = minFormat.format(oERevisionSolicitudData.getHoraInicio());
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
	
	public void listarDesplegable(){
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstMotivo = oUManejadorListaDesplegable.obtieneMotivo();
		lstServicio = oUManejadorListaDesplegable.obtieneTipoServicio();
		lstTipoPersonaRelacion = oUManejadorListaDesplegable.obtieneTipoPersonaRelacion();
		lstAreaEmisor = oUManejadorListaDesplegable.obtieneAreaEmisor();
		
		lstServicioFiltro = lstServicio.stream()
							.filter(x -> x.getCodigo2() != UTipoServicio.SISTEMA)
							.collect(Collectors.toList());
		lstDOI = oUManejadorListaDesplegable.obtieneTipoDocumentoGeneral();
	}
	
	public void listarMensajeTemporal() {
		oERevisionSolicitudData.setDescripcionMensaje(oBORevision.buscarMensajeTemporal(oERevisionSolicitudLoad.getCodigoSolicitud()));
	}
	
	public void obtenerTipoDocumento() {
		if (oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.DNI)){
			maxLgnNumeroDocumentoPersona = 8;
			oERevisionSolicitudData.setNumeroDocumentoPersona("");
			oERevisionSolicitudData.setNombrePersona("");
		}else if (oERevisionSolicitudData.getCodigoTipoDocumentoPersona().equals(UTipoDocumento.RUC)){
			maxLgnNumeroDocumentoPersona = 11;
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
		oERevisionSolicitudData.setFechaInicio(hoy);
		oERevisionSolicitudData.setFechaFin(hoy);
		
		deshabilitar = true;
		deshabilitarTipoDocumentoPersona = true;
		deshabilitarNumeroDocumentoPersona = true;
		deshabilitarNombrePersona = true;
		deshabilitarBuscarPersona = true;
		visualizar = false;
		
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
	
	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}

	public EGeneral getoEEmisorSelected() {
		return oEEmisorSelected;
	}

	public void setoEEmisorSelected(EGeneral oEEmisorSelected) {
		this.oEEmisorSelected = oEEmisorSelected;
	}
}
