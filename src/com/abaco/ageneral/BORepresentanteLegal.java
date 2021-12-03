package com.abaco.ageneral;
import java.util.List;

import com.abaco.entidad.EMensaje;

public class BORepresentanteLegal {

	CRepresentanteLegal oCRepresentanteLegal= new CRepresentanteLegal();
	
	public List<ERepresentanteLegal> listarRepresentanteLegal(int codigoTipoCliente, int codigoCliente){
		List<ERepresentanteLegal> lista=oCRepresentanteLegal.listarRepresentanteLegal(codigoTipoCliente, codigoCliente);
		return lista;
	}
	public List<EFacultadPoder> listarFacultadPoder(int codigoTipoCliente, int codigoCliente, int codigoRepresentante){
		List<EFacultadPoder> lista=oCRepresentanteLegal.listarFacultadPoder(codigoTipoCliente, codigoCliente, codigoRepresentante);
		return lista;
	}
}
