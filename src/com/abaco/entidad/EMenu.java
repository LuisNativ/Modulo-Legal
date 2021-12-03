package com.abaco.entidad;

import java.util.List;

public class EMenu {

	private int aCodigoMenu;
	private int aCodigoMenuPadre;
	private String aNombreMenu;
	private int nOrdenMenu;
	private String nombreIcono;
	private List<EPermiso> lstPermisosMenu;
	
	public void set_CodigoMenu(int acodigomenu) {
		aCodigoMenu = acodigomenu;
	}
	public int get_CodigoMenu() {
		return aCodigoMenu;
	}
	public void set_CodigoMenuPadre(int acodigomenupadre) {
		aCodigoMenuPadre = acodigomenupadre;
	}
	public int get_CodigoMenuPadre() {
		return aCodigoMenuPadre;
	}
	public void set_NombreMenu(String anombremenu) {
		aNombreMenu = anombremenu;
	}
	public String get_NombreMenu() {
		return aNombreMenu;
	}
	public void set_OrdenMenu(int nordenmenu) {
		this.nOrdenMenu = nordenmenu;
	}
	public int get_OrderMenu() {
		return this.nOrdenMenu;
	}
	public String getNombreIcono() {
		return nombreIcono;
	}
	public void setNombreIcono(String nombreIcono) {
		this.nombreIcono = nombreIcono;
	}
	public List<EPermiso> getLstPermisosMenu() {
		return lstPermisosMenu;
	}
	public void setLstPermisosMenu(List<EPermiso> lstPermisosMenu) {
		this.lstPermisosMenu = lstPermisosMenu;
	}
}
