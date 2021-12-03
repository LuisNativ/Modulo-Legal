package com.abaco.negocio.util;


import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class UtilWeb {
	
	public static String obtenerRutaAbsoluta(){
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();		
		String realPath = ctx.getRealPath("/");		
		return realPath;		
	}

	public static long getYYYMMDD(Date date) {
	    long result = 0;
	    if (date != null) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        result = cal.get(Calendar.YEAR)*10000+cal.get(Calendar.MONTH+1)*100+cal.get(Calendar.DATE);
	    }
	    return result;
	}
	
	
	
	public String getCodigoAutorizacion(){
		
		String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$";        
        int tamano=6;
        String codigoAutorizacion="";
        int longitud = base.length();

        for(int i=0; i<tamano;i++){ 
            int numero = (int)(Math.random()*(longitud));
            String caracter=base.substring(numero, numero+1); 
            codigoAutorizacion=codigoAutorizacion+caracter; 
        }
        
        return codigoAutorizacion;
	}
	
	public static String obtenerNombreMes(int numMes){
		
		  String nombreMes="";
	        if(numMes==1){
	           nombreMes="Enero";
	        }
	        if(numMes==2){
	           nombreMes="Febrero";            
	        }
	        if(numMes==3){
	           nombreMes="Marzo";
	        }
	        if(numMes==4){
	           nombreMes="Abril";
	        }
	        if(numMes==5){
	           nombreMes="Mayo";
	        }
	        if(numMes==6){
	           nombreMes="Junio";
	        }        
	        if(numMes==7){
	           nombreMes="Julio";
	        }
	        if(numMes==8){
	           nombreMes="Agosto";
	        }
	        if(numMes==9){
	           nombreMes="Septiembre";
	        }
	        if(numMes==10){
	           nombreMes="Octubre";
	        }
	        if(numMes==11){
	           nombreMes="Noviembre";
	        }
	        if(numMes==12){
	           nombreMes="Diciembre";
	        }   
	        
	        return  nombreMes;
	}

	public static void addMessageInfo(String titulo, String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje));
		
	}
	
	public static void addMessageError(String titulo, String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje));
		
	}
	
	public static void addMessageWarn(String titulo, String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje));
		
	}
	
	public static String reemplazarCamposEnTexto(String cadenaOrigen, String campoValor){
		String[] aCampoValor = campoValor.split("##");
		for (String item : aCampoValor) {
			String[] arr = item.split(";");
			if (arr.length == 2) {
				String campo = arr[0];
				String valor = arr[1];
				cadenaOrigen = cadenaOrigen.replace("{" + campo + "}", valor);
			} else if (arr.length == 1){
				String campo = arr[0];
				String valor = "";
				cadenaOrigen = cadenaOrigen.replace("{" + campo + "}", valor);
			}
		}
		return cadenaOrigen;
	}
	
	
	
}
