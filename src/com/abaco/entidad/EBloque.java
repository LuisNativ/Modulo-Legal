package com.abaco.entidad;

//import org.richfaces.component.UIPanelMenuGroup;

public class EBloque {

	//private UIPanelMenuGroup objHtmlPanelMenuGroup;
	private int codMenu;
	private int codMenuPadre;
	private boolean tieneHijos;
	
	/*
	public UIPanelMenuGroup getObjHtmlPanelMenuGroup() {
		return objHtmlPanelMenuGroup;
	}
	public void setObjHtmlPanelMenuGroup(UIPanelMenuGroup objHtmlPanelMenuGroup) {
		this.objHtmlPanelMenuGroup = objHtmlPanelMenuGroup;
	}
	*/
	public int getCodMenu() {
		return codMenu;
	}
	public void setCodMenu(int codMenu) {
		this.codMenu = codMenu;
	}
	public int getCodMenuPadre() {
		return codMenuPadre;
	}
	public void setCodMenuPadre(int codMenuPadre) {
		this.codMenuPadre = codMenuPadre;
	}
	public boolean getTieneHijos() {
		return tieneHijos;
	}
	public void setTieneHijos(boolean tieneHijos) {
		this.tieneHijos = tieneHijos;
	}
}
