package com.abaco.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.abaco.negocio.util.UConstante.URutaServicioWeb;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorSesionWeb;

@ManagedBean(name="cerrarsession")
@ViewScoped
public class BCerrarSesion implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public void cierraSesion() {
		UManejadorSesionWeb.eliminaSesion();	
	}
	public String salir() {
		String salida = "";		
		Properties prop = new Properties();
		
		try{
			prop.load(getClass().getClassLoader().getResourceAsStream(
			"com/abaco/presentacion/properties/configuracion.properties"));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		UManejadorSesionWeb.eliminaSesion();	
		String sActivoMenu = prop.getProperty("menu.login");
		if(sActivoMenu.equalsIgnoreCase("true")){
			salida = "";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(URutaServicioWeb.MENU_PRINCIPAL);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}else{
			salida = "volverInicio";
		}
		return salida;
	}
	/*
	public String cierraSesion() {
		String salida = "";		
		Properties prop = new Properties();
		
		try{
			prop.load(getClass().getClassLoader().getResourceAsStream(
			"com/abaco/presentacion/properties/configuracion.properties"));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		UManejadorSesionWeb.eliminaSesion();	
		String sActivoMenu = prop.getProperty("menu.login");
		if(sActivoMenu.equalsIgnoreCase("true")){
			salida = "";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(URutaServicioWeb.MENU_PRINCIPAL);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}else{
			salida = "volverInicio";
		}
		return salida;
	}
	*/
}
