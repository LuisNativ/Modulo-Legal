package com.abaco.ageneral;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;

public class BODocumentoControlVersion {

	CDocumentoControlVersion oCDocumentoControlVersion= new CDocumentoControlVersion();
	
	public EMensaje agregar(EDocumentoControlVersion eDocumentoControlVersion){
		EMensaje mensaje = oCDocumentoControlVersion.agregar(eDocumentoControlVersion);
		return mensaje;
	}
	public List<EDocumentoControlVersion> listar(){
		List<EDocumentoControlVersion> resultado = oCDocumentoControlVersion.listar();
		return resultado;
	}
}