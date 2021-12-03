package com.abaco.entidad;

public class EPermiso {

	private int codRol;
	private String aNombreOpcion;
	private String aDescripcionOpcion;
	private String aRutaWeb;
	private int aCodigoMenu;
	private String aNombreMenu;
	private int nOrdenMenu;
	private int aCodigoMenuPadre;
	private int nFlagArgumentos;

	public int getCodRol() {
		return codRol;
	}

	public void setCodRol(int codRol) {
		this.codRol = codRol;
	}

	public void set_NombreOpcion(String anombreopcion) {
		aNombreOpcion = anombreopcion;
	}

	public String get_NombreOpcion() {
		return aNombreOpcion;
	}

	public void set_DescripcionOpcion(String adescripcionopcion) {
		aDescripcionOpcion = adescripcionopcion;
	}

	public String get_DescripcionOpcion() {
		return aDescripcionOpcion;
	}

	public void set_RutaWeb(String arutaweb) {
		aRutaWeb = arutaweb;
	}

	public String get_RutaWeb() {
		return aRutaWeb;
	}

	public void set_CodigoMenu(int acodigomenu) {
		aCodigoMenu = acodigomenu;
	}

	public int get_CodigoMenu() {
		return aCodigoMenu;
	}

	public void set_NombreMenu(String anombremenu) {
		aNombreMenu= anombremenu;	
	}

	public String get_NombreMenu() {
		return aNombreMenu;
	}

	public void set_OrdenMenu(int nordenmenu) {
		this.nOrdenMenu = nordenmenu;
	}

	public int get_OrderMenu() {
		return nOrdenMenu;
	}

	public void set_CodigoMenuPadre(int acodigomenupadre) {
		aCodigoMenuPadre = acodigomenupadre;
	}

	public int get_CodigoMenuPadre() {
		return aCodigoMenuPadre;
	}

	public int get_FlagArgumentos() {
		return nFlagArgumentos;
	}

	public void set_FlagArgumentos(int nFlagArgumentos) {
		this.nFlagArgumentos = nFlagArgumentos;
	}
}
