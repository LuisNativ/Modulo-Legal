package com.abaco.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
/*import javax.faces.bean.SessionScoped;*/
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.MenuActionEvent;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
//import org.richfaces.component.UIPanelMenu;

import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.entidad.EBloque;
import com.abaco.entidad.EMenu;
import com.abaco.entidad.EPermiso;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;

@ManagedBean(name="menu")
@SessionScoped
public class BMenu implements Serializable{

	private static final long serialVersionUID = 1L;
	//private UIPanelMenu panelMenu;
	private EUsuario objEUsuario;
	private String selectedChild = null;
	
	private List<EMenu> lstMenu;
	private MenuModel model;
	private EPermiso oEPermiso;
	private List<EPermiso> lstPermiso;
	//private String idSeleccion;	
	
	/*
	@PostConstruct
	public void inicio() {
		objEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UConstante.UVariablesSesion.USUARIO);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		BOMenu objNMenu = null;
		if (objEUsuario != null) {
			objNMenu = new BOMenu();
			panelMenu = objNMenu.getPanelMenu(objEUsuario, facesContext);
		}

		if (panelMenu == null) {
			panelMenu = new UIPanelMenu();
		}
	}
	*/
	
	@PostConstruct
	public void inicio() {
		try {
			this.listarMenus();
			model = new DefaultMenuModel();
			this.mostrarMenus();
			this.oEPermiso = new EPermiso();
			lstPermiso = new ArrayList<EPermiso>();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void listarMenus(){
		lstMenu = objEUsuario.getLstMenu();
	}
	
	public void mostrarMenus(){
		for(EMenu m: lstMenu){
			DefaultSubMenu firstSubmenu = new DefaultSubMenu(m.get_NombreMenu());
			firstSubmenu.setStyleClass("iconoMenu0" +" "+ m.getNombreIcono());
			//firstSubmenu.setExpanded(false);
			for (EPermiso objPermiso : m.getLstPermisosMenu()) {
				if(m.get_CodigoMenu() == objPermiso.get_CodigoMenu()){
					DefaultMenuItem item = new DefaultMenuItem(objPermiso.get_NombreOpcion());
					item.setStyleClass("iconoItem1");
					//item.setOutcome(objPermiso.get_RutaWeb());
					item.setCommand("#{menu.enviarItem}");
					item.setParam("codigoitem", objPermiso.getCodRol());
					item.setParam("rutaweb", objPermiso.get_RutaWeb());
					item.setOnclick("PF('dlgPreload').show()");
					item.setOncomplete("PF('dlgPreload').hide()");
					firstSubmenu.addElement(item);
				}
			}
			
			model.addElement(firstSubmenu);
		}
	}
	
	public void enviarItem(MenuActionEvent event){
		DefaultMenuItem item = (DefaultMenuItem) event.getMenuItem();
		Map<String, List<String>> params = item.getParams();
		String ruta = "";
		
		if (objEUsuario != null) {
			//int codigo = Integer.parseInt(params.get("codigoitem").get(0));
			ruta = params.get("rutaweb").get(0);
			item.setStyleClass("iconoItem1 dsItemSelecion");
			//ruta = ruta+"?faces-redirect=true";
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.NAVEGACION_URL, ruta);
			//UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.CODIGO_ITEM, codigo);
			//UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
		}
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		//return ruta;
	}
	
	public BMenu() {
		objEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UConstante.UVariablesSesion.USUARIO);
	}
	
	/*
	public UIPanelMenu getPanelMenu() {
		return panelMenu;
	}
	*/
	
	/*
	public UIPanelMenu getPanelMenu() {
		BOMenu objNMenu = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		if (objEUsuario != null) {
			objNMenu = new BOMenu();
			panelMenu = objNMenu.getPanelMenu(objEUsuario, facesContext);
		}
		
		if (panelMenu == null) {
			panelMenu = new UIPanelMenu();
		}
		
		return panelMenu;
	}
	*/
	
	public void updateCurrent(String arg){
		selectedChild = arg;
	}
	/*
	public void setPanelMenu(UIPanelMenu panelMenu) {
		this.panelMenu = panelMenu;
	}
	*/
	public EUsuario getObjEUsuario() {
		return objEUsuario;
	}

	public void setObjEUsuario(EUsuario objEUsuario) {
		this.objEUsuario = objEUsuario;
	}

	public String getSelectedChild() {
		return selectedChild;
	}

	public void setSelectedChild(String selectedChild) {
		this.selectedChild = selectedChild;
	}
	
	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
	
	public EPermiso getoEPermiso() {
		return oEPermiso;
	}

	public void setoEPermiso(EPermiso oEPermiso) {
		this.oEPermiso = oEPermiso;
	}
	
	public List<EPermiso> getLstPermiso() {
		return lstPermiso;
	}

	public void setLstPermiso(List<EPermiso> lstPermiso) {
		this.lstPermiso = lstPermiso;
	}
	/*
	public String getIdSeleccion() {
		return idSeleccion;
	}

	public void setIdSeleccion(String idSeleccion) {
		this.idSeleccion = idSeleccion;
	}
	*/
}
