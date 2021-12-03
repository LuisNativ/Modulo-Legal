package com.abaco.negocio.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abaco.entidad.EGeneral;
import com.abaco.negocio.util.UConstante.UVariablesSesion;

public class UGeneradorSelectItem {

	public static Map<String, String> generaComboxMap(List<EGeneral> lstGeneral, boolean blnUsaNombre) {
		Map<String, String> lstMap = new LinkedHashMap<String, String>();
		if (lstGeneral != null) {
			for (EGeneral objEGeneral : lstGeneral) {
				if (blnUsaNombre) {
					lstMap.put(objEGeneral.getNombreCorto(), UFuncionesGenerales.revisaCadena(objEGeneral.getCodigo()));
				} else {
					lstMap.put(objEGeneral.getDescripcion(), UFuncionesGenerales.revisaCadena(objEGeneral.getCodigo()));
				}
			}
		}
		return lstMap;
	}
	
	public static Map<String, String> generaComboxTipoPersona(List<EGeneral> lstGeneral) {
		Map<String, String> lstMap = new LinkedHashMap<String, String>();
		int codigo = 0;
		int codigoAux = 0;
		
		if (lstGeneral != null) {
			for (EGeneral objEGeneral : lstGeneral) {
				codigo = UFuncionesGenerales.convierteCadenaAEntero(objEGeneral.getCodigo());
				if(codigo > codigoAux){
					codigoAux = codigo;
				}
				lstMap.put(objEGeneral.getDescripcion(), UFuncionesGenerales.revisaCadena(objEGeneral.getCodigo()));
			}
			codigoAux++;
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.CODIGO_PROVEEDOR, codigoAux);
			lstMap.put("PROVEEDOR",String.valueOf(codigoAux));
		}
		return lstMap;
	}
	
}
