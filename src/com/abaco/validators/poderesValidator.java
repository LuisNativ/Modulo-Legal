package com.abaco.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "poderesvalidator")
public class poderesValidator implements Validator{
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
		throws ValidatorException{
		
		context = FacesContext.getCurrentInstance();
		String texto = (String)value;
		texto = texto==null?"":texto;
		String id = ((UIInput)component).getId();
		
		if(id.equals("txtNominativoRepresentante")){
			if (texto.equals("")){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"prueba 1","ingrese nominativo");
				throw new ValidatorException(msg);
			}
		}else if(id.equals("txtRucRepresentante")){
			if (texto.equals("")){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"prueba 1","maximo 12 caracteres");
				throw new ValidatorException(msg);
			}
		}else if(id.equals("cbTipoDocumentoRepresentante")){
			if (texto.equals("")){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"prueba 1","seleccione un item");
				throw new ValidatorException(msg);
			}
		}
	}
		
}
