package com.abaco.ageneral;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;






import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;

import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mbdialog")
@ViewScoped
public class MBDialog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String mostrarTitulo(int codigo) {
		String resultado = "";
		if(codigo == 0){
			resultado = "aa";
		}else if (codigo == 1){
			resultado = "bb";
		}
		return resultado;
	}
}
