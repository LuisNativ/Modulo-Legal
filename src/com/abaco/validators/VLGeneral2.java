package com.abaco.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

//@ManagedBean(name = "vlgeneral2")
//@RequestScoped
@FacesValidator(value = "vlgeneral2")
public class VLGeneral2 implements Validator {

    //@EJB
    //private UserService userService;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //String texto = (String)value;
        String texto = String.valueOf(value);
        //int ruc = Integer.parseInt(texto);
        /*
		String id = ((UIInput)component).getClientId();
		String id2 = ((UIInput)component).getId();
		String id3 = ((UIInput)component).getValidatorMessage();
		String id4 = ((UIInput)component).getContainerClientId(context);
		*/
		String id5 = ((UIInput)component).getRequiredMessage();
		
        if(texto.equals("")) {
        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Dato no permitido","Campo reque");
        	throw new ValidatorException(msg);
        }
        
        if(id5.equals("CampoPJ")){
        	if(texto.length() < 11) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Dato no permitido","Campo inva ruc");
	        	throw new ValidatorException(msg);
        	}
        }else {
        	if(texto.length() < 8) {
	        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Dato no permitido","Campo inva dni");
	        	throw new ValidatorException(msg);
        	}
		}
        
        /*
        if (texto == null || texto.isEmpty()) {
            return;
        }
        
        if (userService.exist(username)) {
            throw new ValidatorException(new FacesMessage("Username already in use, choose another"));
        }
        */
    }

}
