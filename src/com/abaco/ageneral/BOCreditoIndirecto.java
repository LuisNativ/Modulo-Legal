package com.abaco.ageneral;
import java.util.ArrayList;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.ESolicitudCredito;

public class BOCreditoIndirecto {

	CCreditoIndirecto oCCreditoIndirecto= new CCreditoIndirecto();
	
	public List<ECreditoIndirecto> listarCreditoIndirectoPorSolicitar(int codigo, String descripcion){
		List<ECreditoIndirecto> lista=oCCreditoIndirecto.listarCreditoIndirectoPorSolicitar(codigo, descripcion);
		return lista;
	}
	public ECreditoIndirecto buscarCreditoIndirecto(long codigo){
		ECreditoIndirecto resultado=oCCreditoIndirecto.buscarCreditoIndirecto(codigo);
		return resultado;
	}
}
