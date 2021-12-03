package com.abaco.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.abaco.ageneral.EDocumentoCarga;
import com.abaco.ageneral.ERevisionSolicitud;
import com.abaco.ageneral.ERevisionMensaje;
import com.abaco.entidad.EDepartamento;
import com.abaco.entidad.EDireccion;
import com.abaco.entidad.EDistrito;
import com.abaco.entidad.EMenu;
import com.abaco.entidad.EPermiso;
import com.abaco.entidad.EProvincia;
import com.abaco.entidad.ERol;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EUsuarioDetalle;
import com.abaco.entidad.EUsuarioParametro;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOUsuario extends InstanciaAcceso {

	private static final String SP_SEL_CODAUTORIZAUSER = "{ CALL APP_WEBSI.SP_SEL_CODAUTORIZAUSER(?) }";
	private static final String SP_SEL_MENU = "{ CALL APP_WEBSI.SP_SEL_MENU_PROYECTO(?) }";
	private static final String SP_SEL_PERMISOS = "{ CALL APP_WEBSI.SP_SEL_PERMISOS(?) }";
	private static final String SP_SEL_ROL_USUARIO = "{ CALL APP_WEBSI.SP_SEL_ROL_USUARIO(?) }";
	private static final String SP_GET_MENU = "{ CALL APP_WEBSI.SP_GET_MENU(?) }";
	private static final String SP_GET_PERMISOS = "{ CALL APP_WEBSI.SP_GET_PERMISOS(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_MENU_USUARIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_MENU_USUARIO(?,?) }";
	private static final String SP_ABACOINLEGAL_BUS_USUARIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_USUARIO(?) }";
	
	public DAOUsuario(IConexion objConexion) {
		super(objConexion);
	}
	
	public List<EMenu> listarMenu() {
		List<EMenu> lstMenu = null;
		EMenu objEMenu = null;
		ResultSet objResultSet = null;
		List<Object> lstParametrosEntrada = null;		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(UConstante.CODIGO_PROYECTO);
			objResultSet = objConexion.ejecutaConsulta(SP_SEL_MENU, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstMenu = new ArrayList<EMenu>();
				while (objResultSet.next()) {
					objEMenu = new EMenu();
					objEMenu.set_CodigoMenu(objResultSet.getInt("N017_CODMENU"));
					objEMenu.set_CodigoMenuPadre(objResultSet.getInt("N017_CODMENU_PADRE"));
					objEMenu.set_NombreMenu(UFuncionesGenerales.revisaCadena(objResultSet.getString("A017_NOMBRE")));
					objEMenu.set_OrdenMenu(Integer.parseInt(UFuncionesGenerales.revisaCadena(objResultSet.getString("N017_ORDEN"))));
					lstMenu.add(objEMenu);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener los menus.",
					objEx);
		}
		return lstMenu;
	}

	public List<EPermiso> listarPermiso(int intIdUsuario) {
		List<EPermiso> lstEPermiso = new ArrayList<EPermiso>();
		EPermiso objEPermiso = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(intIdUsuario);
			objResultSet = objConexion.ejecutaConsulta(SP_SEL_PERMISOS,
					lstParametrosEntrada, null);
			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPermiso = new EPermiso();
					objEPermiso.setCodRol(objResultSet.getInt("N018_CODOPCION"));
					objEPermiso.set_NombreOpcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A018_NOMBRE")));
					objEPermiso.set_DescripcionOpcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A018_DESCRIPCION")));
					objEPermiso.set_RutaWeb(UFuncionesGenerales.revisaCadena(objResultSet.getString("A018_RUTA_WEB")));
					objEPermiso.set_CodigoMenu(objResultSet.getInt("N017_CODMENU"));
					objEPermiso.set_NombreMenu(UFuncionesGenerales.revisaCadena(objResultSet.getString("A017_NOMBRE")));
					objEPermiso.set_OrdenMenu(Integer.parseInt(UFuncionesGenerales.revisaCadena(objResultSet.getString("N017_ORDEN"))));
					objEPermiso.set_CodigoMenuPadre(objResultSet.getInt("N017_CODMENU_PADRE"));
					lstEPermiso.add(objEPermiso);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Problemas al obtener los permisos del usuario.",
					objEx);
		}
		return lstEPermiso;
	}
	
	public List<ERol> listarRolUsuario(int intIdUsuario) {
		List<ERol> lstRoles = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ERol objERol = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(intIdUsuario);
			objResultSet = objConexion.ejecutaConsulta(SP_SEL_ROL_USUARIO,
					lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstRoles = new ArrayList<ERol>();
				while (objResultSet.next()) {
					objERol = new ERol();
					objERol.setIdRol(objResultSet.getInt("CODIGO"));
					objERol.setNombre(UFuncionesGenerales.revisaCadena(objResultSet
							.getString("NOMBRE")));
					objERol.setDescripcion(UFuncionesGenerales
							.revisaCadena(objResultSet.getString("DESCRIPCION")));
					objERol.setIdRolSupervisor(objResultSet
							.getInt("SUPERVISOR"));
					lstRoles.add(objERol);
				}
			}
			objConexion.cierraConsulta(objResultSet);
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener los roles del usuario.", objEx);
		}
		return lstRoles;
	}
	
	public List<EMenu> listarMenu(int intIdMenu) {
		List<EMenu> lstMenu = null;
		EMenu objEMenu = null;
		ResultSet objResultSet = null;
		List<Object> lstParametrosEntrada = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(intIdMenu);
			
			objResultSet = objConexion.ejecutaConsulta(SP_GET_MENU, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstMenu = new ArrayList<EMenu>();
				while (objResultSet.next()) {
					objEMenu = new EMenu();
					objEMenu.set_CodigoMenu(objResultSet.getInt("N017_CODMENU"));
					objEMenu.set_CodigoMenuPadre(objResultSet.getInt("N017_CODMENU_PADRE"));
					objEMenu.set_NombreMenu(UFuncionesGenerales.revisaCadena(objResultSet.getString("A017_NOMBRE")));
					objEMenu.set_OrdenMenu(Integer.parseInt(UFuncionesGenerales.revisaCadena(objResultSet.getString("N017_ORDEN"))));
					lstMenu.add(objEMenu);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener los menus.", objEx);
		}
		return lstMenu;
	}

	public List<EMenu> listarMenuUsuario(int intIdUsuario, int intIdMenu) {
		List<EMenu> lstMenu = null;
		EMenu objEMenu = null;
		ResultSet objResultSet = null;
		List<Object> lstParametrosEntrada = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(intIdUsuario);
			lstParametrosEntrada.add(intIdMenu);
			
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_MENU_USUARIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				lstMenu = new ArrayList<EMenu>();
				while (objResultSet.next()) {
					objEMenu = new EMenu();
					objEMenu.set_CodigoMenu(objResultSet.getInt("N017_CODMENU"));
					objEMenu.set_CodigoMenuPadre(objResultSet.getInt("N017_CODMENU_PADRE"));
					objEMenu.set_NombreMenu(UFuncionesGenerales.revisaCadena(objResultSet.getString("A017_NOMBRE")));
					objEMenu.set_OrdenMenu(Integer.parseInt(UFuncionesGenerales.revisaCadena(objResultSet.getString("N017_ORDEN"))));
					objEMenu.setNombreIcono(objResultSet.getString("A017_ICONO_ON"));
					lstMenu.add(objEMenu);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener los menus.", objEx);
		}
		return lstMenu;
	}
	
	public List<EPermiso> listarOpcion(int intIdUsuario, int intIdMenu) {
		List<EPermiso> lstEPermiso = new ArrayList<EPermiso>();
		EPermiso objEPermiso = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(intIdUsuario);
			lstParametrosEntrada.add(intIdMenu);
			objResultSet = objConexion.ejecutaConsulta(SP_GET_PERMISOS, lstParametrosEntrada, null);
			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPermiso = new EPermiso();
					objEPermiso.setCodRol(objResultSet.getInt("N018_CODOPCION"));
					objEPermiso.set_NombreOpcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A018_NOMBRE")));
					objEPermiso.set_DescripcionOpcion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A018_DESCRIPCION")));
					objEPermiso.set_RutaWeb(UFuncionesGenerales.revisaCadena(objResultSet.getString("A018_RUTA_WEB")));
					objEPermiso.set_CodigoMenu(objResultSet.getInt("N017_CODMENU"));
					objEPermiso.set_NombreMenu(UFuncionesGenerales.revisaCadena(objResultSet.getString("A017_NOMBRE")));
					objEPermiso.set_OrdenMenu(Integer.parseInt(UFuncionesGenerales.revisaCadena(objResultSet.getString("N017_ORDEN"))));
					objEPermiso.set_CodigoMenuPadre(objResultSet.getInt("N017_CODMENU_PADRE"));
					lstEPermiso.add(objEPermiso);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener los permisos del usuario.", objEx);
		}
		return lstEPermiso;
	}
	
	public String buscarCodigoAutorizacionUsuario(String nombreUsuario) {
		String Resultado = "";
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(nombreUsuario);
			objResultSet = objConexion.ejecutaConsulta(SP_SEL_CODAUTORIZAUSER, lstParametrosEntrada, null);
			if (objResultSet != null) {
				while (objResultSet.next()) {
					Resultado = objResultSet.getString("A020_CODAUTORIZ");
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			objEx.printStackTrace();
			UManejadorLog.error("Acceso: Error al obtener el código de autorización del usuario.",
							objEx);
		}
		return Resultado;
	}
	
	public EUsuario buscarUsuario(EUsuarioParametro objEUsuarioParam) {
		EUsuario objEUsuario = null;
		EUsuarioDetalle objEUsuarioDetalle = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEUsuarioParam.getNombreUsuario());
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_USUARIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEUsuario = new EUsuario();
					objEUsuarioDetalle = new EUsuarioDetalle();
					objEUsuarioDetalle.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_RUC")));
					objEUsuarioDetalle.setRazonSocial(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_RAZONSOCIAL")));
					objEUsuarioDetalle.setCodigoSocio(objResultSet.getInt("N020_CODIGO_SOCIO"));					

					EDireccion objEDireccion = new EDireccion();					
					objEDireccion.setDireccionComercial(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_DIRECCION")));

					EDepartamento objEDepartamento = new EDepartamento();
					objEDepartamento.setIdDepartamento(objResultSet.getInt("N020_DEPARTAMENTO"));
					objEDireccion.setDepartamento(objEDepartamento);

					EProvincia objEProvincia = new EProvincia();
					objEProvincia.setIdProvincia(objResultSet.getInt("N020_PROVINCIA"));
					objEDireccion.setProvincia(objEProvincia);

					EDistrito objEDistrito = new EDistrito();
					objEDistrito.setIdDistrito(objResultSet.getInt("N020_DISTRITO"));
					objEDireccion.setDistrito(objEDistrito);
					
					objEDireccion.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_TELEFONO")));
					objEUsuarioDetalle.setDireccion(objEDireccion);					
					objEUsuarioDetalle.setEmail(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_EMAIL")));
					
					objEUsuarioDetalle.setCodigoAutorizacion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_CODAUTORIZ")));					
					objEUsuario.setUsuarioDetalle(objEUsuarioDetalle);
					objEUsuario.setIdUsuario(objResultSet.getInt("N015_IDUSUARIO"));
					objEUsuario.setCodigoCliente(objEUsuarioDetalle.getCodigoSocio());
					objEUsuario.setContrasena(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_CONTRASEÑA")));
					objEUsuario.setNombreCompleto(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_NOMBRECOMPLETO")));
					objEUsuario.setNombreUsuarioSIAF(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_USUARI")));
					objEUsuario.setCodigoArea(objResultSet.getInt("CODAREA"));
					
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Problemas al obtener los datos del usuario.",
					objEx);
		}
		if (objEUsuario != null) {
			String strClaveIngresada = UFuncionesGenerales.DesencriptarCredenciales(objEUsuarioParam.getContrasena(),
							objEUsuario.getUsuarioDetalle()
									.getCodigoAutorizacion());
			String strClaveRegistrada = UFuncionesGenerales.DesencriptarCredenciales(objEUsuario
					.getContrasena(), objEUsuario.getUsuarioDetalle()
					.getCodigoAutorizacion());
			if (strClaveIngresada.equals(strClaveRegistrada)) {
				objEUsuario.setLstMenu(listarMenu());
				objEUsuario.setLstPermisos(listarPermiso(objEUsuario.getIdUsuario()));
				objEUsuario.setListaRoles(listarRolUsuario(objEUsuario
						.getIdUsuario()));
				objEUsuario.setNombreUsuario(objEUsuarioParam
						.getNombreUsuario());
				objEUsuario.setContrasena(null);
			} else {
				objEUsuario = null;
			}
		}
		return objEUsuario;
	}
	
	public EUsuario buscarUsuarioSinValidacionClave(EUsuarioParametro objEUsuarioParam) {
		EUsuario objEUsuario = null;
		EUsuarioDetalle objEUsuarioDetalle = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEUsuarioParam.getNombreUsuario());
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_USUARIO,
					lstParametrosEntrada, null);
			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEUsuario = new EUsuario();
					objEUsuarioDetalle = new EUsuarioDetalle();
					objEUsuarioDetalle.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_RUC")));
					objEUsuarioDetalle.setRazonSocial(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_RAZONSOCIAL")));
					objEUsuarioDetalle.setCodigoSocio(objResultSet.getInt("N020_CODIGO_SOCIO"));					

					EDireccion objEDireccion = new EDireccion();					
					objEDireccion.setDireccionComercial(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_DIRECCION")));

					EDepartamento objEDepartamento = new EDepartamento();
					objEDepartamento.setIdDepartamento(objResultSet.getInt("N020_DEPARTAMENTO"));
					objEDireccion.setDepartamento(objEDepartamento);

					EProvincia objEProvincia = new EProvincia();
					objEProvincia.setIdProvincia(objResultSet.getInt("N020_PROVINCIA"));

					objEDireccion.setProvincia(objEProvincia);

					EDistrito objEDistrito = new EDistrito();
					objEDistrito.setIdDistrito(objResultSet.getInt("N020_DISTRITO"));
					objEDireccion.setDistrito(objEDistrito);
					
					objEDireccion.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_TELEFONO")));
					objEUsuarioDetalle.setDireccion(objEDireccion);					
					objEUsuarioDetalle.setEmail(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_EMAIL")));
					objEUsuarioDetalle.setCodigoAutorizacion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_CODAUTORIZ")));					

					objEUsuario.setUsuarioDetalle(objEUsuarioDetalle);
					objEUsuario.setIdUsuario(objResultSet.getInt("N015_IDUSUARIO"));
					objEUsuario.setCodigoCliente(objEUsuarioDetalle.getCodigoSocio());
					objEUsuario.setContrasena(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_CONTRASEÑA")));
					objEUsuario.setNombreCompleto(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_NOMBRECOMPLETO")));
					objEUsuario.setNombreUsuarioSIAF(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_USUARI")));
					objEUsuario.setCodigoArea(objResultSet.getInt("CODAREA"));

				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Problemas al obtener los datos del usuario.",
					objEx);
		}
		if (objEUsuario != null) {
			objEUsuario.setLstMenu(listarMenu());
			objEUsuario
					.setLstPermisos(listarPermiso(objEUsuario.getIdUsuario()));
			objEUsuario.setListaRoles(listarRolUsuario(objEUsuario
					.getIdUsuario()));
			objEUsuario.setNombreUsuario(objEUsuarioParam.getNombreUsuario());
			objEUsuario.setContrasena(null);
		}
		return objEUsuario;
	}
	
	public EUsuario buscarMenuUsuario(EUsuarioParametro objEUsuarioParam) {
		EUsuario objEUsuario = null;
		EUsuarioDetalle objEUsuarioDetalle = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEUsuarioParam.getNombreUsuario());
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_USUARIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEUsuario = new EUsuario();
					objEUsuarioDetalle = new EUsuarioDetalle();
					objEUsuarioDetalle.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_RUC")));
					objEUsuarioDetalle.setRazonSocial(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_RAZONSOCIAL")));
					objEUsuarioDetalle.setCodigoSocio(objResultSet.getInt("N020_CODIGO_SOCIO"));					

					EDireccion objEDireccion = new EDireccion();					
					objEDireccion.setDireccionComercial(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_DIRECCION")));

					EDepartamento objEDepartamento = new EDepartamento();
					objEDepartamento.setIdDepartamento(objResultSet.getInt("N020_DEPARTAMENTO"));
					objEDireccion.setDepartamento(objEDepartamento);
					
					EProvincia objEProvincia = new EProvincia();
					objEProvincia.setIdProvincia(objResultSet.getInt("N020_PROVINCIA"));
					objEDireccion.setProvincia(objEProvincia);
					
					EDistrito objEDistrito = new EDistrito();
					objEDistrito.setIdDistrito(objResultSet.getInt("N020_DISTRITO"));
					objEDireccion.setDistrito(objEDistrito);
					
					objEDireccion.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_TELEFONO")));
					objEUsuarioDetalle.setDireccion(objEDireccion);
					
					objEUsuarioDetalle.setEmail(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_EMAIL")));
					
					objEUsuarioDetalle.setCodigoAutorizacion(UFuncionesGenerales.revisaCadena(objResultSet.getString("A020_CODAUTORIZ")));					
					objEUsuario.setUsuarioDetalle(objEUsuarioDetalle);
					objEUsuario.setIdUsuario(objResultSet.getInt("N015_IDUSUARIO"));
					objEUsuario.setCodigoCliente(objEUsuarioDetalle.getCodigoSocio());
					objEUsuario.setContrasena(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_CONTRASEÑA")));
					objEUsuario.setNombreCompleto(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_NOMBRECOMPLETO")));
					objEUsuario.setNombreUsuarioSIAF(UFuncionesGenerales.revisaCadena(objResultSet.getString("A015_USUARI")));
					objEUsuario.setCodigoArea(objResultSet.getInt("CODAREA"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener los datos del usuario.", objEx);
		}
		if (objEUsuario != null) {
			//objEUsuario.setLstMenu(obtenerMenu(objEUsuarioParam.getIdMenu()));
			objEUsuario.setLstMenu(listarMenuUsuario(objEUsuario.getIdUsuario(), objEUsuarioParam.getIdMenu()));
			objEUsuario.setLstPermisos(listarOpcion(objEUsuario.getIdUsuario(), objEUsuarioParam.getIdMenu()));
			objEUsuario.setListaRoles(listarRolUsuario(objEUsuario.getIdUsuario()));
			objEUsuario.setNombreUsuario(objEUsuarioParam.getNombreUsuario());
			objEUsuario.setContrasena(null);			
		}
		return objEUsuario;
	}
}
