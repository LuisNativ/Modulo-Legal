package com.abaco.negocio.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abaco.entidad.EGeneral;
import com.abaco.negocio.util.UConstante.UListaDesplegable.ENListaDesplegable;

public class UContenedorListas {

	private static volatile UContenedorListas instancia = new UContenedorListas();
	private Map<String, List<EGeneral>> tablaListas;
	private Map<String, Object> objetoAplicacion;
	
	private UContenedorListas() {
		this.tablaListas = new ConcurrentHashMap<String, List<EGeneral>>();
		this.objetoAplicacion = new ConcurrentHashMap<String, Object>();
	}

	public static UContenedorListas obtieneInstancia() {
		return instancia;
	}
	/*
	public List<EGeneral> obtieneListaGenerica(ENListaDesplegable enListaDesplegable) {
		String strListaDesplegable = enListaDesplegable.toString();
		List<EGeneral> lstGeneral = tablaListas.get(strListaDesplegable);
		if ((lstGeneral) == null || (lstGeneral.isEmpty())) {
			lstGeneral = UFabricaListaDesplegable.obtieneListaDesplegable(enListaDesplegable);
			tablaListas.put(strListaDesplegable, lstGeneral);
		}
		return lstGeneral;
	}
	
	public EGeneral obtieneDatoListaGenerica(ENListaDesplegable enListaDesplegable, String strCodigo) {
		EGeneral objEGeneralResultado = null;
		List<EGeneral> lstGeneral = obtieneListaGenerica(enListaDesplegable);
		for(EGeneral objEGeneral : lstGeneral) {
			if (UFuncionesGenerales.revisaCadena(objEGeneral.getCodigo()).equalsIgnoreCase(strCodigo)) {
				objEGeneralResultado = objEGeneral;
				break;
			}
		}
		return objEGeneralResultado;
	}
	*/
	
	private void liberaRecursosInternos() {
		this.tablaListas = null;
		this.objetoAplicacion = null;
	}

	public static void liberaRecursos() {
		if (instancia != null) {
			instancia.liberaRecursosInternos();
			instancia = null;
		}
	}
	
	public Object obtieneObjeto(String strClave) {
		return objetoAplicacion.get(strClave);
	}
	
	public void registraObjeto(String strClave, Object objValor) {
		objetoAplicacion.put(strClave, objValor);
	}
}
