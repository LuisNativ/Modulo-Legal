package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
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

import com.abaco.bo.BOGeneral;
import com.abaco.bo.BOUsuario;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UManejadorUrl;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mbmantenimientousuario")
@ViewScoped
public class MBMantenimientoUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EPermisoUsuario oEPermisoUsuarioData;
	
	private EGeneral oEUsuarioSelected;
	
	private BOGeneral oBOGeneral;
	private BOPermisoUsuario oBOPermisoUsuario;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EPermisoUsuario> lstPermisoUsuario;
	@Getter @Setter private List<EPermisoUsuario> lstPermisoUsuarioFiltro;
	@Getter @Setter private List<EGeneral> lstUsuario;
	@Getter @Setter private List<EGeneral> lstUsuarioFiltro;
	@Getter @Setter private List<EGeneral> lstAutonomia;
	@Getter @Setter private List<EGeneral> lstArea;
	
	/* Variables Interfaz */
	@Getter @Setter private String nombreUsuarioBuscar;
	@Getter @Setter private String nombrePermisoUsuarioBuscar;
	@Getter @Setter private int accionInternaUsuario;
	
	/* Variables Internas */

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEPermisoUsuarioData = new EPermisoUsuario();
		
		oEUsuarioSelected = new EGeneral();
		
		oBOGeneral = new BOGeneral();
		oBOPermisoUsuario = new BOPermisoUsuario();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstPermisoUsuario = new ArrayList<EPermisoUsuario>();
		lstPermisoUsuarioFiltro = new ArrayList<EPermisoUsuario>();
		lstUsuario = new ArrayList<EGeneral>();
		lstUsuarioFiltro = new ArrayList<EGeneral>();
		lstAutonomia = new ArrayList<EGeneral>();
		lstArea = new ArrayList<EGeneral>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEMensaje = UManejadorUrl.validarUrl("MantenimientoUsuario.xhtml");
		
		if(oEMensaje.getIdMensaje() == 0){
			inicializar();
			listarDesplegable();
			listarPermisoUsuario();
			listarUsuario();
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacionUrl').show();");
		}
	}
	
	public void inicializarUsuario() {
		this.oEPermisoUsuarioData = new EPermisoUsuario();
	}
	
	public void agregarUsuario() {
		accionInternaUsuario = UAccionInterna.NUEVO;
		inicializarUsuario();
		RequestContext.getCurrentInstance().execute("PF('dlgAgregarPermisoUsuario').show();");
	}
	
	public void modificarUsuario(EPermisoUsuario oEPermisoUsuarioItem) {
		if (oEPermisoUsuarioItem != null) {
			accionInternaUsuario = UAccionInterna.EDITAR;
			inicializarUsuario();
			oEPermisoUsuarioData = oEPermisoUsuarioItem;
			
			RequestContext.getCurrentInstance().execute("PF('dlgModificarPermisoUsuario').show();");
		}
	}
	
	public void eliminarUsuario(EPermisoUsuario oEPermisoUsuarioItem) {
		if (oEPermisoUsuarioItem != null) {			
			this.oEMensaje = new EMensaje();
			oEMensaje = oBOPermisoUsuario.eliminar(oEPermisoUsuarioItem);
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listarPermisoUsuario();
		}
	}
	
	public void guardarUsuario() {
		if (oEPermisoUsuarioData != null) {
			this.oEMensaje = new EMensaje();
			oEPermisoUsuarioData.setUsuarioRegistro(oEUsuario);
			
			if (accionInternaUsuario == UAccionInterna.NUEVO){
				oEMensaje = oBOPermisoUsuario.agregar(oEPermisoUsuarioData);
			}else if (accionInternaUsuario == UAccionInterna.EDITAR) {
				oEMensaje = oBOPermisoUsuario.modificar(oEPermisoUsuarioData);
			}
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			listarPermisoUsuario();
		}
	}
	
	public void listarPermisoUsuario() {
		lstPermisoUsuario = oBOPermisoUsuario.listar();
		lstPermisoUsuarioFiltro = lstPermisoUsuario;
	}
	
	public void listarUsuario() {
		lstUsuario = oUManejadorListaDesplegable.obtieneUsuarioPorPermisoUsuario(UTipoEstadoUsuario.ACTIVO);
		lstUsuarioFiltro = lstUsuario;
	}
	
	public void listarDesplegable() {
		lstAutonomia = oUManejadorListaDesplegable.obtieneTipoAutonomia();
		lstArea = oUManejadorListaDesplegable.obtieneArea();
	}
	
	public void buscarUsuario(){
		listarUsuario();
		nombreUsuarioBuscar = "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarUsuario').show();");
	}
	
	public void asignarUsuario(){
		if (oEUsuarioSelected != null){
			oEPermisoUsuarioData.setCodigoUsuario(oEUsuarioSelected.getCodigo2());
			oEPermisoUsuarioData.setNombreUsuario(oEUsuarioSelected.getNombreCorto());
		}
	}
	
	public void filtrarUsuario(){
		lstUsuarioFiltro = lstUsuario.stream()
				   .filter(x -> x.getDescripcion().matches("(?i).*"+ nombreUsuarioBuscar +".*"))
				   .filter(x -> x.getNombreCorto().matches("(?i).*"+ nombreUsuarioBuscar +".*"))
				   .collect(Collectors.toList());
	}
	
	public void filtrarPermisoUsuario(){
		lstPermisoUsuarioFiltro = lstPermisoUsuario.stream()
				   .filter(x -> x.getNombreUsuario().matches("(?i).*"+ nombrePermisoUsuarioBuscar +".*"))
				   .collect(Collectors.toList());
	}
	
	public void inicializar() {
		nombreUsuarioBuscar = "";
	}

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
	
	public EPermisoUsuario getoEPermisoUsuarioData() {
		return oEPermisoUsuarioData;
	}

	public void setoEPermisoUsuarioData(EPermisoUsuario oEPermisoUsuarioData) {
		this.oEPermisoUsuarioData = oEPermisoUsuarioData;
	}
	
	public EGeneral getoEUsuarioSelected() {
		return oEUsuarioSelected;
	}

	public void setoEUsuarioSelected(EGeneral oEUsuarioSelected) {
		this.oEUsuarioSelected = oEUsuarioSelected;
	}


}
