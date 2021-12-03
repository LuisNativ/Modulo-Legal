package com.abaco.negocio.util;

import java.util.ArrayList;
import java.util.List;


import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionListener;
import javax.faces.event.MethodExpressionActionListener;

//import org.richfaces.component.UIPanelMenuGroup;
//import org.richfaces.component.UIPanelMenuItem;

import com.abaco.entidad.EBloque;

public class UGeneradorMenuDinamico {

	private List<EBloque> lstBloque = new ArrayList<EBloque>();

	public List<EBloque> getListaBloque() {
		return lstBloque;
	}

	public void setListaBloque(List<EBloque> lstBloque) {
		this.lstBloque = lstBloque;
	}

	public void agregarBloque(EBloque oEBloque) {
		lstBloque.add(oEBloque);
	}
	/*
	public UIPanelMenuGroup createHtmlPanelMenuGroup(FacesContext fc,
			String NombreMenu, int intcodGroup) {
		
		UIPanelMenuGroup pMenuGroup = (UIPanelMenuGroup) fc.getApplication().createComponent(UIPanelMenuGroup.COMPONENT_TYPE);		
		pMenuGroup.setId("grupo_"+intcodGroup);
		pMenuGroup.setLabel(NombreMenu);
		
		return pMenuGroup;
	}
	*/
	/*
	public void agregarHtmlPanelMenuItem(FacesContext fc, UIPanelMenuGroup oHtmlPanelMenuGroup, String NombreOpcion,
			String accion, int intIdCodRol, int flagArgumentos, int intcodGroup) {
		try {
			StringBuilder str = new StringBuilder();
			
			UIPanelMenuItem pMenuItem = (UIPanelMenuItem) fc.getApplication()
					.createComponent(UIPanelMenuItem.COMPONENT_TYPE);
		
			pMenuItem.setId("item_"+ intIdCodRol);
			pMenuItem.setName(NombreOpcion);
			pMenuItem.setLabel(NombreOpcion);
			pMenuItem.setActionExpression(createMethodExpression(accion, String.class, new Class<?>[] {}));
			
			str.append("#").append("{").append("menu.updateCurrent").append("(").append("'").append("grupo_"+intcodGroup);
			str.append("')}");
			
			ActionListener actl = new MethodExpressionActionListener(createMethodExpression(str.toString(), String.class, new Class<?>[] {}));

			pMenuItem.addActionListener(actl);
			
			oHtmlPanelMenuGroup.getChildren().add(pMenuItem);

		} catch (Exception objEx) {
			UManejadorLog.log("Error al crear el menú: " + objEx.getMessage());
		}
	}
	*/
	public static MethodExpression createMethodExpression(String methodExpression, Class<?> expectedReturnType, Class<?>[] expectedParamTypes) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return context.getApplication().getExpressionFactory()
	            .createMethodExpression(context.getELContext(), methodExpression, expectedReturnType, expectedParamTypes);
	}
}
