package com.abaco.validators;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.abaco.ageneral.MBRegistroOperacionSolicitudCredito;
import com.abaco.negocio.util.UFuncionesGenerales;

@ManagedBean(name = "vlgeneral")
@Named
@RequestScoped
public class VLGeneral implements Serializable{
	
	public void validarGeneral(FacesContext context, UIComponent component, Object value)
		throws ValidatorException{
		String texto = (String)value;
		//texto = texto==null?"":texto;
		
		if(UFuncionesGenerales.revisaCadena(texto).equalsIgnoreCase("")){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Obligatorio");
			throw new ValidatorException(msg);
		}
	}
	
	public void validarDocumento(FacesContext context, UIComponent component, Object value)
		throws ValidatorException{
		//String texto = String.valueOf(value);
		String texto = (String)value;
		String mensajeRequerido = ((UIInput)component).getRequiredMessage();
		//texto = texto==null?"":texto;
		
        if(UFuncionesGenerales.revisaCadena(texto).equalsIgnoreCase("")){
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Obligatorio");
        	throw new ValidatorException(msg);
        }
        
        if(mensajeRequerido.equals("validaRuc")){ //Valida Tipo Documento Ruc
        	if(!UFuncionesGenerales.validateRuc(texto)) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
	        	throw new ValidatorException(msg);
        	}
        	/*
        	if(texto.length() != 11) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
	        	throw new ValidatorException(msg);
        	}else if(UFuncionesGenerales.validarContieneLetras(texto)) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
	        	throw new ValidatorException(msg);
        	}
        	*/
        }else if(mensajeRequerido.equals("validaDni")){ //Valida Tipo Documento Dni y Libreta Electoral
        	if(!UFuncionesGenerales.validateDni(texto)) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
	        	throw new ValidatorException(msg);
        	}
        	/*
        	if(texto.length() != 8) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
	        	throw new ValidatorException(msg);
        	}else if(UFuncionesGenerales.validarContieneLetras(texto)) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
	        	throw new ValidatorException(msg);
        	}
        	*/
		}
	}
	
	public void validarDni(FacesContext context, UIComponent component, Object value)
		throws ValidatorException{
		/*
		context = FacesContext.getCurrentInstance();
		String texto = (String)value;
		texto = texto==null?"":texto;
		*/
		
		String texto = (String)value;
		
		if(UFuncionesGenerales.revisaCadena(texto).equalsIgnoreCase("")){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Obligatorio");
			throw new ValidatorException(msg);
		}else if(!UFuncionesGenerales.validateDni(texto)) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
        	throw new ValidatorException(msg);
    	}
		/*
		}else if(texto.length() != 8) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
        	throw new ValidatorException(msg);
    	}else if(UFuncionesGenerales.validarContieneLetras(texto)) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
        	throw new ValidatorException(msg);
    	}
    	*/
	}
	
	public void validarRuc(FacesContext context, UIComponent component, Object value)
		throws ValidatorException{
		String texto = (String)value;
		
		if(UFuncionesGenerales.revisaCadena(texto).equalsIgnoreCase("")){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Obligatorio");
			throw new ValidatorException(msg);
		}else if(!UFuncionesGenerales.validateRuc(texto)) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
        	throw new ValidatorException(msg);
    	}
		/*
		}else if(texto.length() != 11) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
        	throw new ValidatorException(msg);
    	}else if(UFuncionesGenerales.validarContieneLetras(texto)) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
        	throw new ValidatorException(msg);
    	}
    	*/
	}
}
