package com.abaco.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneralTipoCambio;
import com.abaco.entidad.EGeneralTipoCambioParametro;
import com.abaco.entidad.ERol;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.URutaServicioWeb;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorSesionWeb;



@ManagedBean(name="principal",eager=true)
@SessionScoped
public class BPrincipal implements Serializable {

	private final String CERRAR_SESION = "cierraSesion";
	private String nombreUsuario;
	private EGeneralTipoCambio tipoCambio;
	private String selectedChild = null;
	private EUsuario objEUsuario;
	private String labelBtnSalir;
	private String activoMenu;
	//private boolean rolAuditor;
	
	private static final long serialVersionUID = 1L;
	
	public BPrincipal(){
		
		Properties prop = new Properties();		
		labelBtnSalir = "Cerrar Sesión";

		try{
			prop.load(getClass().getClassLoader().getResourceAsStream(
			"com/abaco/presentacion/properties/configuracion.properties"));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		EUsuario objEUsuario = (EUsuario)UManejadorSesionWeb.obtieneVariableSesion(UConstante.UVariablesSesion.USUARIO);
		//int rolAuditor= Integer.parseInt(prop.getProperty("rolAuditor").trim());
		
		if (objEUsuario != null) {
			nombreUsuario = objEUsuario.getNombreCompleto();			
			/*
			List<ERol> roles= objEUsuario.getListaRoles();
			this.rolAuditor=false;
			if(roles!=null && roles.size()>0){
				for(ERol rol: roles){										
					if( rolAuditor== rol.getIdRol()){
						this.rolAuditor=true;
						break;
					}
				}
			}	
			*/						
			
		}
		
		activoMenu = prop.getProperty("menu.login");
		if(activoMenu.equalsIgnoreCase("true")){
			labelBtnSalir = "Menú Principal";
		}	
		
		
	}
	
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
			salida = CERRAR_SESION;
		}
		return salida;
	}
	
	public void obtenerTipoCambio(){
		 BOGeneral objNGeneral = new BOGeneral();
		 EGeneralTipoCambioParametro objParam = new EGeneralTipoCambioParametro();
		 Calendar calendar = Calendar.getInstance();
		 calendar.get(Calendar.YEAR);
		 objParam.setAnio(calendar.get(Calendar.YEAR));
		 objParam.setMes(calendar.get(Calendar.MONTH)+1);
		 objParam.setDia(String.format("%02d", calendar.get(Calendar.DATE)));
		 objParam.setMoneda(2);
		 setTipoCambio(objNGeneral.buscarGeneralTipoCambio(objParam));
	}
	
	public String getFechaActual() {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("EEEE, d 'de' ", new Locale("es"));
		Date objFecha = UFuncionesGenerales.getFechaActual();
		String strDia, strMes, strFecha;
		strDia = objSimpleDateFormat.format(objFecha);
		strDia = strDia.substring(0,1).toUpperCase() + strDia.substring(1);
		objSimpleDateFormat.applyPattern("MMMM");
		strMes = objSimpleDateFormat.format(objFecha);
		strMes = strMes.substring(0,1).toUpperCase() + strMes.substring(1);
		objSimpleDateFormat.applyPattern(" 'de' yyyy");
		strFecha = strDia + strMes + objSimpleDateFormat.format(objFecha);
		return strFecha;
	}
	
	public EGeneralTipoCambio getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(EGeneralTipoCambio tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public String getSelectedChild() {
		return selectedChild;
	}

	public void setSelectedChild(String selectedChild) {
		this.selectedChild = selectedChild;
	}

	public EUsuario getObjEUsuario() {
		return objEUsuario;
	}

	public void setObjEUsuario(EUsuario objEUsuario) {
		this.objEUsuario = objEUsuario;
	}

	public String getLabelBtnSalir() {
		return labelBtnSalir;
	}

	public void setLabelBtnSalir(String labelBtnSalir) {
		this.labelBtnSalir = labelBtnSalir;
	}

	public String getActivoMenu() {
		return activoMenu;
	}

	public void setActivoMenu(String activoMenu) {
		this.activoMenu = activoMenu;
	}
	/*
	public boolean isRolAuditor() {
		return rolAuditor;
	}

	public void setRolAuditor(boolean rolAuditor) {
		this.rolAuditor = rolAuditor;
	}
	*/
}