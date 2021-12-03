package com.abaco.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.abaco.bo.BOUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EMenu;
import com.abaco.entidad.EPermiso;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EUsuarioParametro;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.URutaServicioWeb;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;

@ManagedBean(name="login")
@ViewScoped
public class BLogin  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String strUsuario = "";
	private String strContrasena = "";
	private String strMensaje;
	private String strNuevaContrasena;
	private String strRepetirContrasena;
	private String desContenido;
	private EMensaje mensajeErrorIngreso;	
	private boolean validarIngreso=false;		
	private String activoMenu;
	private int idMenu = 0;
    
	public BLogin() {
		
		mensajeErrorIngreso = new EMensaje();
		mensajeErrorIngreso.setIdMensaje(-1000);
		mensajeErrorIngreso.setDescMensaje("Ingrese datos por favor");	
		
		Properties prop = new Properties();
		try{
			prop.load(getClass().getClassLoader().getResourceAsStream(
			"com/abaco/presentacion/properties/configuracion.properties"));
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		activoMenu = prop.getProperty("menu.login");
		/*Ini Ahm*/
		//idMenu = 66;
		/*Fin Ahm*/
		
		if(activoMenu.equalsIgnoreCase("true")){
			String usuario = UManejadorSesionWeb.obtieneParametroQueryString(UVariablesQueryString.USUARIO);
			String codigomenu =  UManejadorSesionWeb.obtieneParametroQueryString(UVariablesQueryString.IDMENU);
			idMenu = UFuncionesGenerales.convierteCadenaAEntero(codigomenu);
			
			if(usuario != null){
				setStrUsuario(usuario);
				obtenerMenu();
			}else{
				String sUrlMenuPrincipal = URutaServicioWeb.MENU_PRINCIPAL;
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(sUrlMenuPrincipal);
				String sUrl = objUGeneradorQueryString.obtieneUrlConParametros();
				UManejadorSesionWeb.redirigePagina(sUrl);
			}
		}
		
	}

	public String Validar() {
		mensajeErrorIngreso = new EMensaje();
		mensajeErrorIngreso.setIdMensaje(-1000);
		mensajeErrorIngreso.setDescMensaje("Ingrese datos por favor");
		UManejadorSesionWeb.eliminaVariableSesion(UConstante.UVariablesSesion.USUARIO);
		validarIngreso = false;
		
		if ((UFuncionesGenerales.revisaCadena(getStrUsuario()).length() == 0) || (UFuncionesGenerales.revisaCadena(getStrContrasena()).length()== 0)) {
			mensajeErrorIngreso.setIdMensaje(-1);
			mensajeErrorIngreso.setDescMensaje("Ingrese su usuario y/o contraseña");
			mostrarMensajes("4");
			setValidarIngreso(false);
		} else {
			BOUsuario oDTOUsuario = new BOUsuario();
			String strCodigoAuto = oDTOUsuario.buscarCodigoAutorizacionUsuario(strUsuario);
			if (strCodigoAuto != null && !UFuncionesGenerales.revisaCadena(strCodigoAuto).isEmpty()) {
				EUsuarioParametro objEUsuarioParam = new EUsuarioParametro();
				objEUsuarioParam.setNombreUsuario(strUsuario);
				
				String claveCifrada = UFuncionesGenerales.EncriptarCredenciales(strContrasena, UFuncionesGenerales.revisaCadena(strCodigoAuto));
				objEUsuarioParam.setContrasena(claveCifrada);
				
				EUsuario objEUsuario = null;
				if(strUsuario.toUpperCase().equals(UConstante.USeguridad.USUARIO_ADMINISTRADOR)){
					objEUsuario = oDTOUsuario.buscarUsuario(objEUsuarioParam);
					setValidarIngreso(true);
				} else {
					objEUsuario = oDTOUsuario.buscarUsuarioSinValidacionClave(objEUsuarioParam);					
					if(objEUsuario != null){			
						EMensaje eMensaje = oDTOUsuario.autenticaUsuarioSistema(objEUsuarioParam);
						if(eMensaje!=null && eMensaje.getIdMensaje()==UConstante.USeguridad.USUARIO_AUTENTICADO){		
							UManejadorLog.log("Usuario autenticado en AS400");
							setValidarIngreso(true);
						} else {
							UManejadorLog.log("Usuario no autenticado en AS400");
							mensajeErrorIngreso.setIdMensaje(-2);
							mensajeErrorIngreso.setDescMensaje(eMensaje.getDescMensaje());
							setValidarIngreso(false);
							
						}
					}
				}
								
				if (objEUsuario != null && isValidarIngreso()) {
					
					//actualmente, solo se está manejando un rol para el usuario
					//temporalmente, se debe ejecutar el siguiente código hasta que se implemente
					//la ventana para elegir el rol del usuario:
					try {
						if(objEUsuario.getListaRoles()!= null && objEUsuario.getListaRoles().size() > 0){
							objEUsuario.setRolActivo(objEUsuario.getListaRoles().get(0));
							objEUsuario.setNombreAplicacion(UConstante.NOMBRE_SISTEMA);
							UManejadorSesionWeb.registraVariableSesion(UConstante.UVariablesSesion.USUARIO, objEUsuario);
							mensajeErrorIngreso.setIdMensaje(0);
							setValidarIngreso(true);
						}else{
							mensajeErrorIngreso.setIdMensaje(-1);
							mensajeErrorIngreso.setDescMensaje("El usuario no tiene un rol asignado");
							mostrarMensajes("5");
							setValidarIngreso(false);
						}
					} catch(Exception objEx) {
						objEx.printStackTrace();
						UManejadorLog.log("Ocurrio un problema al obtener los roles del usuario.");
					}					
				} else {
					mensajeErrorIngreso.setIdMensaje(-1);
					mensajeErrorIngreso.setDescMensaje("Contraseña incorrecta");
					mostrarMensajes("2");
					setValidarIngreso(false);
				}
			} else {
				mensajeErrorIngreso.setIdMensaje(-2);
				mensajeErrorIngreso.setDescMensaje("Usuario incorrecto");
				mostrarMensajes("1");
				setValidarIngreso(false);			
			}			
		}
		return Reenvia();
	}
    
    public void mostrarMensajes(String valor) {
    	FacesContext context = FacesContext.getCurrentInstance();
    	if(valor.equals("0")){
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto!", "Ingreso Satisfactorio"));
    	}else if (valor.equals("1")) {
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal!", "Usuario Incorrecto"));
    	}else if (valor.equals("2")) {
    		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Contraseña Incorrecto"));
		}else if (valor.equals("3")) {
			context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
		}else if (valor.equals("4")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn!", "Ingrese su usuario y/o contraseña"));
		}else if (valor.equals("5")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn!", "El usuario no tiene un rol asignado"));
		}
    }
    
	public String obtenerMenu() {
		mensajeErrorIngreso = new EMensaje();
		mensajeErrorIngreso.setIdMensaje(-1000);
		mensajeErrorIngreso.setDescMensaje("Ingrese datos por favor");
		UManejadorSesionWeb.eliminaVariableSesion(UConstante.UVariablesSesion.USUARIO);
		
		BOUsuario objNUsuario = new BOUsuario();
		EUsuarioParametro objEUsuarioParam = new EUsuarioParametro();
		objEUsuarioParam.setNombreUsuario(strUsuario);
		objEUsuarioParam.setIdMenu(idMenu);

		EUsuario objEUsuario = null;
		objEUsuario = objNUsuario.buscarMenuUsuario(objEUsuarioParam);
		if (objEUsuario != null) {
			try {
				if (objEUsuario.getListaRoles() != null && objEUsuario.getListaRoles().size() > 0) {
					objEUsuario.setRolActivo(objEUsuario.getListaRoles().get(0));
					objEUsuario.setNombreAplicacion(UConstante.NOMBRE_SISTEMA);
					for (EMenu objMenuBean : objEUsuario.getLstMenu()) {
						List<EPermiso> lstPermiso = new BOUsuario().listarOpcion(objEUsuario.getIdUsuario(), objMenuBean.get_CodigoMenu());
						objMenuBean.setLstPermisosMenu(lstPermiso);
					}
					UManejadorSesionWeb.registraVariableSesion(UConstante.UVariablesSesion.USUARIO, objEUsuario);
				}
			} catch (Exception objEx) {
				UManejadorLog.log("Ocurrio un problema al obtener los roles del usuario.");
			}
		} 

		return Reenvia();
	}
	
	public String Reenvia() {
		if (activoMenu.equalsIgnoreCase("true")) {
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString("fondo.xhtml");
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			return "";
		} else {
			if (isValidarIngreso()) {
				return "success";
			} else {
				return null;
			}
		}
		
		//Ini: AHM
		/*
		if (validarIngreso==true && activoMenu.equalsIgnoreCase("true")) {
			obtenerMenu2();
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString("Maestra.xhtml");
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			return "";
		} else {
			if (isValidarIngreso()) {
				return "success";
			} else {
				return null;
			}
		}
		*/
		//Fin: AHM*
	}
	
	public void obtenerMenu2() {
		mensajeErrorIngreso = new EMensaje();
		mensajeErrorIngreso.setIdMensaje(-1000);
		mensajeErrorIngreso.setDescMensaje("Ingrese datos por favor");
		UManejadorSesionWeb.eliminaVariableSesion(UConstante.UVariablesSesion.USUARIO);
		
		BOUsuario objNUsuario = new BOUsuario();
		EUsuarioParametro objEUsuarioParam = new EUsuarioParametro();
		objEUsuarioParam.setNombreUsuario(strUsuario);
		objEUsuarioParam.setIdMenu(idMenu);

		EUsuario objEUsuario = null;
		objEUsuario = objNUsuario.buscarMenuUsuario(objEUsuarioParam);
		if (objEUsuario != null) {
			try {
				if (objEUsuario.getListaRoles() != null && objEUsuario.getListaRoles().size() > 0) {
					objEUsuario.setRolActivo(objEUsuario.getListaRoles().get(0));
					objEUsuario.setNombreAplicacion(UConstante.NOMBRE_SISTEMA);
					for (EMenu objMenuBean : objEUsuario.getLstMenu()) {
						List<EPermiso> lstPermiso = new BOUsuario().listarOpcion(objEUsuario.getIdUsuario(), objMenuBean.get_CodigoMenu());
						objMenuBean.setLstPermisosMenu(lstPermiso);
					}
					UManejadorSesionWeb.registraVariableSesion(UConstante.UVariablesSesion.USUARIO, objEUsuario);
				}
			} catch (Exception objEx) {
				UManejadorLog.log("Ocurrio un problema al obtener los roles del usuario.");
			}
		} 
	}
	/*Fin AHM*/

	public String getStrUsuario() {
		return strUsuario;
	}

	public void setStrUsuario(String strUsuario) {
		this.strUsuario = strUsuario;
	}

	public String getStrContrasena() {
		return strContrasena;
	}

	public void setStrContrasena(String strContrasena) {
		this.strContrasena = strContrasena;
	}

	public String getStrNuevaContrasena() {
		return strNuevaContrasena;
	}

	public void setStrNuevaContrasena(String strNuevaContrasena) {
		this.strNuevaContrasena = strNuevaContrasena;
	}

	public String getStrRepetirContrasena() {
		return strRepetirContrasena;
	}

	public void setStrRepetirContrasena(String strRepetirContrasena) {
		this.strRepetirContrasena = strRepetirContrasena;
	}

	public String getDesContenido() {
		return desContenido;
	}

	public void setDesContenido(String desContenido) {
		this.desContenido = desContenido;
	}

	public EMensaje getMensajeErrorIngreso() {
		return mensajeErrorIngreso;
	}

	public void setMensajeErrorIngreso(EMensaje mensajeErrorIngreso) {
		this.mensajeErrorIngreso = mensajeErrorIngreso;
	}

	public boolean isValidarIngreso() {
		return validarIngreso;
	}

	public void setValidarIngreso(boolean validarIngreso) {
		this.validarIngreso = validarIngreso;
	}

	public String getStrMensaje() {
		return strMensaje;
	}

	public void setStrMensaje(String strMensaje) {
		this.strMensaje = strMensaje;
	}

	public String irFormCambiarClave() {
		return "cambioClave";
	}

	public String irFormRecordarClave() {
		return "recordarClave";
	}

	public String irFormLogin() {
		return "termined";
	}

	public String irFormRegistrarUsuario() {
		return "registroUsuario";
	}

	public String getActivoMenu() {
		return activoMenu;
	}

	public void setActivoMenu(String activoMenu) {
		this.activoMenu = activoMenu;
	}

	public int getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
}
