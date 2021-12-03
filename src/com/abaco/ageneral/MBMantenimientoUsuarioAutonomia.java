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

@ManagedBean(name = "mbmantenimientousuarioautonomia")
@ViewScoped
public class MBMantenimientoUsuarioAutonomia implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EPermisoUsuario oEPermisoUsuarioData;
	
	private BOGeneral oBOGeneral;
	private BOPermisoUsuario oBOPermisoUsuario;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EPermisoUsuario> lstPermisoUsuario;
	@Getter @Setter private List<EGeneral> lstAutonomia;
	@Getter @Setter private List<EGeneral> lstArea;
	
	/* Variables Interfaz */
	@Getter @Setter private String nombreUsuarioBuscar;
	@Getter @Setter private int accionInternaUsuario;
	
	/* Variables Internas */

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEPermisoUsuarioData = new EPermisoUsuario();
		
		oBOGeneral = new BOGeneral();
		oBOPermisoUsuario = new BOPermisoUsuario();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstPermisoUsuario = new ArrayList<EPermisoUsuario>();
		lstAutonomia = new ArrayList<EGeneral>();
		lstArea = new ArrayList<EGeneral>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEMensaje = UManejadorUrl.validarUrl("MantenimientoUsuarioAutonomia.xhtml");
		
		if(oEMensaje.getIdMensaje() == 0){
			inicializar();
			listarDesplegable();
			listarPermisoUsuario();
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacionUrl').show();");
		}
	}
	
	public void inicializarUsuario() {
		this.oEPermisoUsuarioData = new EPermisoUsuario();
	}
	
	public void modificarUsuario(EPermisoUsuario oEPermisoUsuarioItem) {
		if (oEPermisoUsuarioItem != null) {
			accionInternaUsuario = UAccionInterna.EDITAR;
			inicializarUsuario();
			oEPermisoUsuarioData = oEPermisoUsuarioItem;
			
			RequestContext.getCurrentInstance().execute("PF('dlgModificarPermisoUsuario').show();");
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
		lstPermisoUsuario = oBOPermisoUsuario.listarPorArea(oEUsuario);
	}
	
	public void listarDesplegable() {
		lstAutonomia = oUManejadorListaDesplegable.obtieneTipoAutonomia();
		lstArea = oUManejadorListaDesplegable.obtieneArea();
	}
	
	public void inicializar() {
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
}
