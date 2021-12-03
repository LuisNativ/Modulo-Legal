package com.abaco.ageneral;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;

public class BOPermisoUsuario {

	CPermisoUsuario oCPermisoUsuario= new CPermisoUsuario();
	
	public EMensaje agregar(EPermisoUsuario ePermisoUsuario){
		EMensaje mensaje = oCPermisoUsuario.agregar(ePermisoUsuario);
		return mensaje;
	}
	public EMensaje modificar(EPermisoUsuario ePermisoUsuario){
		EMensaje mensaje = oCPermisoUsuario.modificar(ePermisoUsuario);
		return mensaje;
	}
	public EMensaje eliminar(EPermisoUsuario ePermisoUsuario){
		EMensaje mensaje = oCPermisoUsuario.eliminar(ePermisoUsuario);
		return mensaje;
	}
	public List<EPermisoUsuario> listar(){
		List<EPermisoUsuario> resultado = oCPermisoUsuario.listar();
		return resultado;
	}
	public List<EPermisoUsuario> listarPorArea(EUsuario eUsuario){
		List<EPermisoUsuario> resultado = oCPermisoUsuario.listarPorArea(eUsuario);
		return resultado;
	}
}