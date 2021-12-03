package com.abaco.negocio.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import kotlin.text.Regex;

import org.jasypt.util.text.BasicTextEncryptor;
import org.primefaces.context.RequestContext;

import com.abaco.ageneral.EDocumentoRequerido;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.ENombre;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.UTipoMensaje;
import com.abaco.servicio.laserfiche.Mensaje;

public class UFuncionesGenerales {

	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	//private static final String PATTERN_DNI = "/(^([0-9]{8,8})|^)$/";
	//private static final String PATTERN_RUC = "/(^([0-9]{5,5})|^)$/";
	private static final String PATTERN_DNI = "[0-9]{8,8}";
	private static final String PATTERN_RUC = "[0-9]{11,11}";
	
	//private static final String PATTERN_DOCUMENTO_SIMBOLO = "/[^a-z0-9\x20]/i";
	//private static final String PATTERN_DOCUMENTO_SIMBOLO = "[!\"·$%&/()=¿¡?'_:;,|@#€*+.]";
	
	public static int convierteCadenaAEntero(String strCadena) {
		Integer intResultado = 0;
		strCadena = revisaCadena(strCadena);
		if (strCadena.length() > 0) {
			try {
				intResultado = Integer.parseInt(strCadena);
			} catch (Exception objEx) {
			}
		}
		return intResultado;
	}

	public static String revisaCadena(String strCadena) {
		String strResultado = strCadena;
		if (strResultado == null || strResultado.trim().length() == 0) {
			strResultado = "";
		}
		return strResultado.trim();
	}

	public static EMensaje formateaErrorSQL(String strErrorSQL) {
		EMensaje objEMensaje = new EMensaje();
		objEMensaje.setTipoMensaje(UTipoMensaje.SQLError);
		try {
			String[] strMensajes = strErrorSQL.split("]");
			objEMensaje.setIdMensaje(-Integer.parseInt(strMensajes[0].substring(4)));
			objEMensaje.setDescMensaje(strMensajes[1]);

		} catch (Exception objEx) {
			objEMensaje.setIdMensaje(-9999);
			objEMensaje.setDescMensaje("Error SQL: " + strErrorSQL);
		}
		return objEMensaje;
	}

	public static String EncriptarCredenciales(String strTextoAEncriptar, String strLlaveParaEncriptar) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(strLlaveParaEncriptar);
		return textEncryptor.encrypt(strTextoAEncriptar);
	}

	public static String DesencriptarCredenciales(String strTextoADesencriptar, String strLlaveParaDesencriptar) {
		String strResultado = "";
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		try {
			textEncryptor.setPassword(revisaCadena(strLlaveParaDesencriptar));
			strResultado = textEncryptor.decrypt(revisaCadena(strTextoADesencriptar));
		} catch (Exception e) {

		}
		return strResultado;
	}

	public static Date getFechaActual() {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date datFecha = null;
		try {
			datFecha = objSimpleDateFormat.parse(objSimpleDateFormat.format(Calendar.getInstance().getTime()));
		} catch (ParseException objParseEx) {
			UManejadorLog.error("Error al obtener la fecha: " + objParseEx.getMessage());
		} catch (Exception objEx) {
			UManejadorLog.error("Error al obtener la fecha: ", objEx);
		}
		return datFecha;
	}

	public static Date getHoraActual() {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		Date datFecha = null;
		try {
			datFecha = objSimpleDateFormat.parse(objSimpleDateFormat.format(Calendar.getInstance().getTime()));
		} catch (ParseException objParseEx) {
			UManejadorLog.error("Error al obtener la hora: " + objParseEx.getMessage());
		} catch (Exception objEx) {
			UManejadorLog.error("Error al obtener la hora: ", objEx);
		}
		return datFecha;
	}
	
	public static String convierteCadenaMayuscula(String strCadena) {
		return revisaCadena(strCadena).toUpperCase();
	}

	public static String convierteCadenaMinuscula(String strCadena) {
		return revisaCadena(strCadena).toLowerCase();
	}
	
	public static long convierteCadenaAEnteroLargo(String strCadena) {
		Long lngResultado = 0L;
		strCadena = revisaCadena(strCadena);
		if (strCadena.length() > 0) {
			try {
				lngResultado = Long.parseLong(strCadena);
			} catch (Exception objEx) {
			}
		}
		return lngResultado;
	}

	public static Date convertirCadenaAFecha(String sFecha, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {
			if (!revisaCadena(sFecha).equalsIgnoreCase("")) {
				date = formatter.parse(sFecha);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String convertirFechaACadena(Date date, String format) {
		String result = "";
		Locale local_espanol = new Locale("es","PE");
		SimpleDateFormat objDateFormat = new SimpleDateFormat(format, local_espanol);

		if (date != null) {
			result = objDateFormat.format(date);
		}
		return result;
	}

	public static boolean validaMensaje(EMensaje objEMensaje) {
		boolean blnResultado = false;
		if (objEMensaje != null && objEMensaje.getIdMensaje() >= 0)
			blnResultado = true;
		return blnResultado;
	}
	
	public static boolean validaMensajeLaserFiche(Mensaje objEMensaje) {
		boolean blnResultado = false;
		if (objEMensaje != null && objEMensaje.getCodigo() >= 0)
			blnResultado = true;
		return blnResultado;
	}

	public static String completaNumeroCerosIzquierda(String strNumero, int intLongitudCadena) {
		String strFormato = "%" + String.valueOf(intLongitudCadena) + "s";
		String strResultado = String.format(strFormato, strNumero).replace(' ', '0');
		return strResultado;
	}
	
	public static String completaCorrelativoTramite(String strNumero, int intLongitudCadena) {
		String strFormato = "%" + String.valueOf(intLongitudCadena) + "s";
		String strResultado = String.format(strFormato, strNumero).replace(' ', '0');
		strResultado = "TR"+strResultado;
		return strResultado;
	}
	
	public static String completaCorrelativoOperacion(String strNumero, int intLongitudCadena) {
		String strFormato = "%" + String.valueOf(intLongitudCadena) + "s";
		String strResultado = String.format(strFormato, strNumero).replace(' ', '0');
		strResultado = "OP"+strResultado;
		return strResultado;
	}
	
	public static String completaCorrelativoRevision(String strNumero, int intLongitudCadena) {
		String strFormato = "%" + String.valueOf(intLongitudCadena) + "s";
		String strResultado = String.format(strFormato, strNumero).replace(' ', '0');
		strResultado = "RV"+strResultado;
		return strResultado;
	}

	public static double convierteCadenaAReal(String strCadena) {
		Double dblResultado = 0D;
		strCadena = revisaCadena(strCadena);
		if (strCadena.length() > 0) {
			try {
				dblResultado = Double.parseDouble(strCadena);
			} catch (Exception objEx) {
			}
		}
		return dblResultado;
	}

	public static String obtenerDescripcionPorCodigoDeMap(int clave, Map<String, String> mapList) {
		String retVal = "";
		for (Map.Entry<String, String> entry : mapList.entrySet()) {
			if (Integer.valueOf(entry.getValue()) == clave) {
				retVal = revisaCadena(entry.getKey());
				break;
			}
		}
		return retVal;
	}

	public static String obtenerDescripcionPorCodigoDeMap(String clave, Map<String, String> mapList) {
		String retVal = "";
		for (Map.Entry<String, String> entry : mapList.entrySet()) {
			if (UFuncionesGenerales.revisaCadena(entry.getValue()).equalsIgnoreCase(clave)) {
				retVal = revisaCadena(entry.getKey());
				break;
			}
		}
		return retVal;
	}
	
	
	public static String obtieneDescripcionDeValorSeleccionado(final Map<String, String> lstListaValores,
			final String strValor) {

		String strResultado = "";
		if (lstListaValores != null && lstListaValores.size() > 0) {
			for (Map.Entry<String, String> objEntrada : lstListaValores.entrySet()) {
				if (objEntrada.getValue().equals(UFuncionesGenerales.revisaCadena(strValor))) {
					strResultado = objEntrada.getKey();
					break;
				}
			}
		}
		return strResultado;
	}

	public static String obtieneDescripcionDeValorSeleccionado(final List<EGeneral> lstListaValores,
			final String strValor, final boolean descripcionLarga) {

		String strResultado = "";
		if (lstListaValores != null && lstListaValores.size() > 0) {
			for (EGeneral objEntrada : lstListaValores) {
				if (objEntrada.getCodigo().equals(UFuncionesGenerales.revisaCadena(strValor))) {
					if(descripcionLarga){
						strResultado = objEntrada.getDescripcion();
					}else{
						strResultado = objEntrada.getNombreCorto();
					}
					break;
				}
			}
		}
		return strResultado;
	}
	
	public static String obtieneDescripcionDeValorSeleccionado2(final List<EGeneral> lstListaValores,
			final int strValor, final boolean descripcionLarga) {

		String strResultado = "";
		if (lstListaValores != null && lstListaValores.size() > 0) {
			for (EGeneral objEntrada : lstListaValores) {
				if (objEntrada.getCodigo2() == strValor) {
					if(descripcionLarga){
						strResultado = objEntrada.getDescripcion();
					}else{
						strResultado = objEntrada.getNombreCorto();
					}
					break;
				}
			}
		}
		return strResultado;
	}
	
	public static String obtieneDescripcionDeValorSeleccionado3(final List<EDocumentoRequerido> lstListaValores,
			final int strValor, final boolean descripcionLarga) {

		String strResultado = "";
		if (lstListaValores != null && lstListaValores.size() > 0) {
			for (EDocumentoRequerido objEntrada : lstListaValores) {
				if (objEntrada.getCodigoDocumentoRequerido() == strValor) {
					if(descripcionLarga){
						strResultado = objEntrada.getDescripcionDocumentoRequerido();
					}else{
						strResultado = objEntrada.getDescripcionDocumentoRequerido();
					}
					break;
				}
			}
		}
		return strResultado;
	}
	
	public static String obtieneDescripcionDeValorIndicador(final int strValor) {
		String strResultado = "";
		
		if(strValor == 0){
			strResultado = "No";
		}else if(strValor == 1){
			strResultado = "Si";
		}
		
		return strResultado;
	}
	
	public static String obtieneDescripcionPorSaltoLinea(final String valor, final int linea) {
		String strResultado = "";
		
        List<String> lista = new ArrayList<String>(Arrays.asList(valor.split("\n")));
        try {
        	strResultado = lista.get(linea);
        }catch (Exception objEx){
        	strResultado = "";
        }
		
		return strResultado;
	}
	
	public static String obtieneCodigoDeValorSeleccionadoPorDescripcion(final Map<String, String> lstListaValores, final String strValor) {

		String strResultado = "";
		if (lstListaValores != null && lstListaValores.size() > 0) {
			for (Map.Entry<String, String> objEntrada : lstListaValores.entrySet()) {
				if (objEntrada.getKey().equals(UFuncionesGenerales.revisaCadena(strValor))) {
					strResultado = objEntrada.getValue();
					break;
				}
			}
		}
		return strResultado;
	}
	
	public static String obtieneNombreArchivo(String strRutaAbsoluta) {
		char chrSeparadorRuta = '/';
		if (USistemaOperativo.ES_WINDOWS) {
			chrSeparadorRuta = '\\';
		}
		if (USistemaOperativo.ES_LINUX) {
			chrSeparadorRuta = '/';
		}
		return obtieneNombreArchivo(strRutaAbsoluta, chrSeparadorRuta);
	}

	public static String obtieneNombreArchivo(String strRutaAbsoluta, char chrSeparadorRuta) {
		String strNombreArchivo = revisaCadena(strRutaAbsoluta);
		int intSeparador = strNombreArchivo.lastIndexOf(chrSeparadorRuta);
		if (intSeparador > -1) {
			strNombreArchivo = strNombreArchivo.substring(intSeparador + 1);
		}
		return strNombreArchivo;
	}
	
	public static String obtieneTipoArchivo(String strArchivo) {
		String strTipoArchivo = "";
		int intSeparador = strArchivo.lastIndexOf(".");
		if (intSeparador > -1) {
			strTipoArchivo = strArchivo.substring(intSeparador);
		}
		return strTipoArchivo;
	}
	
	public static void descargaArchivo(String strNombreCompletoArchivo) {
		HttpServletResponse objResponse;
		FileInputStream objFileInputStream;
		String strNombreArchivo;
		byte[] arrDatosArchivo;
		try {
			strNombreArchivo = obtieneNombreArchivo(strNombreCompletoArchivo);
			objResponse = UManejadorSesionWeb.obtieneHttpResponse();
			objResponse.setContentType("application/octet-stream");
			objResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strNombreArchivo + "\"");
			objFileInputStream = new FileInputStream(strNombreCompletoArchivo);
			arrDatosArchivo = new byte[UConstante.BUFFER_SIZE];
			while (objFileInputStream.read(arrDatosArchivo, 0, UConstante.BUFFER_SIZE) != -1) {
				objResponse.getOutputStream().write(arrDatosArchivo, 0, UConstante.BUFFER_SIZE);
			}
			objFileInputStream.close();
			objResponse.getOutputStream().flush();
			objResponse.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception objEx) {
			objEx.printStackTrace();
		}
	}

	public static void descargaArchivo(String strNombreCompletoArchivo,
			byte[] arrArchivoBinario) {
			HttpServletResponse objResponse;
			try {
				objResponse = UManejadorSesionWeb.obtieneHttpResponse();
				objResponse.setContentType("application/octet-stream");
				objResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strNombreCompletoArchivo + "\"");
				objResponse.getOutputStream().write(arrArchivoBinario, 0, arrArchivoBinario.length);
				objResponse.getOutputStream().flush();
				objResponse.getOutputStream().close();
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception objEx) {
				objEx.printStackTrace();
			}
	}
/*
	public static void descargaArchivo(String strNombreCompletoArchivo) {
		HttpServletResponse objResponse;
		FileInputStream objFileInputStream;
		String strNombreArchivo;
		byte[] arrDatosArchivo;
		try {
			strNombreArchivo = obtieneNombreArchivo(strNombreCompletoArchivo);
			objResponse = UManejadorSesionWeb.obtieneHttpResponse();
			objResponse.setContentType("application/octet-stream");
			objResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strNombreArchivo + "\"");
			objFileInputStream = new FileInputStream(strNombreCompletoArchivo);
			arrDatosArchivo = new byte[UConstante.BUFFER_SIZE];
			while (objFileInputStream.read(arrDatosArchivo, 0, UConstante.BUFFER_SIZE) != -1) {
				objResponse.getOutputStream().write(arrDatosArchivo, 0, UConstante.BUFFER_SIZE);
			}
			objFileInputStream.close();
			objResponse.getOutputStream().flush();
			objResponse.getOutputStream().close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception objEx) {
			objEx.printStackTrace();
		}
	}

	public static void descargaArchivo(String strNombreCompletoArchivo,
			byte[] arrArchivoBinario) {
			HttpServletResponse objResponse;
			try {
				objResponse = UManejadorSesionWeb.obtieneHttpResponse();
				objResponse.setContentType("application/octet-stream");
				objResponse.setHeader("Content-Disposition", "attachment; filename=\"" + strNombreCompletoArchivo + "\"");
				objResponse.getOutputStream().write(arrArchivoBinario, 0, arrArchivoBinario.length);
				objResponse.getOutputStream().flush();
				objResponse.getOutputStream().close();
				FacesContext.getCurrentInstance().responseComplete();
			} catch (Exception objEx) {
				objEx.printStackTrace();
			}
	}
*/	
	public static void borrarArchivo(String rutaReporte) {
		File file = new File(rutaReporte);
		boolean res = file.delete();
		UManejadorLog.log("Resultado de eliminacion de archivo " + rutaReporte + " : " + res);
	}

	public static String obtenerRutaAbsoluta() {
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("/");
		return realPath;
	}

	public static ENombre obtenerApellidosNonbres(String nombreLargo) {
		String nombres = "";
		String prev = "";
		String[] nombreArr = revisaCadena(nombreLargo).split(" ");
		List<String> names = new ArrayList<String>();
		ENombre eNombre = new ENombre();
		int num_nombres = 0;

		for (String sNombre : nombreArr) {
			String nombreAux = revisaCadena(sNombre);
			switch (nombreAux) {
			case "DA":
			case "DE":
			case "DEL":
			case "LA":
			case "LAS":
			case "LOS":
			case "MAC":
			case "MC":
			case "VAN":
			case "VON":
			case "Y":
			case "I":
			case "SAN":
			case "SANTA":
				prev = nombreAux;
				break;
			default:
				names.add((prev + (" " + nombreAux.replace(",", ""))));
				prev = "";
				break;
			}
		}

		num_nombres = names.size();
		switch (num_nombres) {
		case 0:
			nombres = "";		
			eNombre.setNombres("");
			eNombre.setApellidoPaterno("");
			eNombre.setApellidoMaterno("");
			break;
		case 1:
			eNombre.setNombres(revisaCadena(names.get(0)));
			eNombre.setApellidoPaterno("");
			eNombre.setApellidoMaterno("");
			break;
		case 2:
			eNombre.setApellidoPaterno(revisaCadena(names.get(0)));
			eNombre.setNombres(revisaCadena(names.get(1)));
			eNombre.setApellidoMaterno("");
			break;
		default:
			eNombre.setApellidoPaterno(revisaCadena(names.get(0)));
			eNombre.setApellidoMaterno(revisaCadena(names.get(1)));
			names.remove(0);
			names.remove(0);

			for (String sname : names) {
				nombres = nombres + revisaCadena(sname).replace(",", "") + " ";
			}
			eNombre.setNombres(revisaCadena(nombres));
			break;
		}

		return eNombre;
	}

	public static String completarParametrosContenido(List<Object> lstParametros, String contenido) {
		String replace = "";
		int i = 0;
		for (Object param : lstParametros) {
			if (param == null) {
				replace = "";
			} else {
				replace = (String) param;
			}
			contenido = contenido.replace("{" + i + "}", replace);
			i++;
		}
		return contenido;
	}

	public static boolean validateEmail(String email) {
		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}
	
	public static boolean validateDni(String cadena) {
		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_DNI);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(cadena);
		return matcher.matches();

	}
	
	public static boolean validateRuc(String cadena) {
		// Compiles the given regular expression into a pattern.
		Pattern pattern = Pattern.compile(PATTERN_RUC);

		// Match the given input against this pattern
		Matcher matcher = pattern.matcher(cadena);
		return matcher.matches();

	}
	
    public static boolean validarContieneLetras(String cadena) {
        for (int x = 0; x < cadena.length(); x++) {
            char c = cadena.charAt(x);
            // Si está entre a y z, ni entre A y Z, ni es un espacio
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return true;
            }
        }
        return false;
    }
	
	public static List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }
	
	public static String revisaValorLista(String strCadena) {
		String strResultado = strCadena;
		if (strResultado == null || strResultado.trim().length() == 0 ||  strResultado.trim().equalsIgnoreCase("00") ||
				strResultado.trim().equalsIgnoreCase("000") || strResultado.trim().equalsIgnoreCase("0000")) {
			strResultado = "0";
		}
		return strResultado.trim();
	}
	
	public static Date getFechaActualLargo() {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS");
		Date datFecha = null;
		try {
			datFecha = objSimpleDateFormat.parse(objSimpleDateFormat.format(Calendar.getInstance().getTime()));
		} catch (ParseException objParseEx) {
			UManejadorLog.error("Error al obtener la fecha: " + objParseEx.getMessage());
		} catch (Exception objEx) {
			UManejadorLog.error("Error al obtener la fecha: " , objEx);
		}
		return datFecha;
	}
	
	public static void mostrarMensaje(String Tipo, String titulo, String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		if(Tipo.equals("info")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje));
		}else if(Tipo.equals("warn")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo, mensaje));
		}else if(Tipo.equals("error")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo, mensaje));
		}else if(Tipo.equals("fatal")){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo, mensaje));
		}
	}
	
    public static String getSize(long size) {
        long n = 1000;
        String s = "";
        double kb = size / n;
        double mb = kb / n;
        double gb = mb / n;
        double tb = gb / n;
        if(size < n) {
            s = size + " Bytes";
        } else if(size >= n && size < (n * n)) {
            s =  String.format("%.0f", kb) + " KB";
        } else if(size >= (n * n) && size < (n * n * n)) {
            s = String.format("%.0f", mb) + " MB";
        } else if(size >= (n * n * n) && size < (n * n * n * n)) {
            s = String.format("%.0f", gb) + " GB";
        } else if(size >= (n * n * n * n)) {
            s = String.format("%.0f", tb) + " TB";
        }
        return s;
    }
    
    public static String convertirCadenaATime(String numero) {
    	String tiempo = "";
    	String num, hor="", min="", seg="";
        num = numero+"";
        
        if (num.length()==1){
        	num = "00000" + num;
        }else if (num.length()==2){
        	num = "0000" + num;
        }else if (num.length()==3){
	    	num = "000" + num;
        }else if (num.length()==4){
	    	num = "00" + num;
        }else if (num.length()==5){
	    	num = "0" + num;
        }else if (num.length()==6){
	    }
        
        hor = num.substring(0, 2);
        min = num.substring(2, 4);
        seg = num.substring(4, 6);
        
        tiempo =  hor+":"+min+":"+seg+"";
        return tiempo;
    }
    
    public static String convertirEnteroATime(int numero) {
    	String tiempo = "";
    	String num, hor="", min="", seg="";
        num = numero+"";
        
        if (num.length()==1){
        	num = "00000" + num;
        }else if (num.length()==2){
        	num = "0000" + num;
        }else if (num.length()==3){
	    	num = "000" + num;
        }else if (num.length()==4){
	    	num = "00" + num;
        }else if (num.length()==5){
	    	num = "0" + num;
        }else if (num.length()==6){
	    }
        
        hor = num.substring(0, 2);
        min = num.substring(2, 4);
        seg = num.substring(4, 6);
        
        tiempo =  hor+":"+min+":"+seg+"";
        return tiempo;
    }
    /*
    public static String convertirSexagesimalATime(int sexagesimal) {
    	String tiempo = "";
    	String num, hor="", min="", seg="";
        num = sexagesimal+"";
        if (num.length()==1){
        	num = "0000" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==2){
        	num = "000" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==3){
	    	num = "00" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==4){
	    	num = "0" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==5){
	    	//num = "0" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==6){
	    	//num = "0" + num;
            hor = num.substring(0, 2);
            min = num.substring(2, 4);
            seg = num.substring(4, 6);
	    }
        
        tiempo =  hor+":"+min+":"+seg+"";
        return tiempo;
    }
    */
    /*
    public static String convertirSegundosASexagesimal2(int segundos) {
    	String sexagesimal = "";
        int num,hor,min,seg;
        num = segundos;
        hor = num/3600;
        min = (num-(3600*hor))/60;
        seg = num-((hor*3600)+(min*60));
        sexagesimal = hor+"h "+min+"m "+seg+"s";
        return sexagesimal;
    }
    */
    public static String convertirSegundosASexagesimal(int segundos) {
    	String sexagesimal = "";
        int dia,hor,min,seg;

    	seg = segundos % 60;
    	min = segundos % 3600 / 60; 
    	hor = segundos % 86400 / 3600; 
    	dia = segundos / 86400;
    	
        sexagesimal = dia+"d "+hor+"h "+min+"m "+seg+"s";
        return sexagesimal;
    }
    
    public static String convertirValoresASexagesimal(int dias, int numero) {
    	String tiempo = "";
    	String num, hor="", min="", seg="";
    	int hor1=0, min1=0, seg1=0;
        num = numero+"";
    	//num = sexagesimal;
        if (num.length()==1){
        	num = "0000" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==2){
        	num = "000" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==3){
	    	num = "00" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==4){
	    	num = "0" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==5){
	    	//num = "0" + num;
            hor = num.substring(0, 1);
            min = num.substring(1, 3);
            seg = num.substring(3, 5);
        }else if (num.length()==6){
	    	//num = "0" + num;
            hor = num.substring(0, 2);
            min = num.substring(2, 4);
            seg = num.substring(4, 6);
	    }
        
        //Validar datos del sexagesimal
        seg1 = (Integer.parseInt(seg));
        min1 = (Integer.parseInt(min));
        hor1 = (Integer.parseInt(hor));
        
    	if (seg1 >= 60){
    		seg1 = seg1 - 60;
    		min1 = min1 + 1;
    	}else if(seg1 >= 120){
    		seg1 = seg1 - 120;
    		min1 = min1 + 2;
    	}
    	
    	if (min1 >= 60){
    		min1 = min1 - 60;
    		hor1 = hor1 + 1;
    	}else if(min1 >= 120){
    		min1 = seg1 - 120;
    		hor1 = hor1 + 2;
    	}
    	
    	seg = seg1+"";
    	min = min1+"";
    	hor = hor1+"";
        
        tiempo = dias+"d "+hor+"h "+min+"m "+seg+"s";
        return tiempo;
    }
    
	public static boolean convierteCadenaABoolean(String strCadena) {
		boolean dblResultado = false;
		strCadena = revisaCadena(strCadena);
		if (strCadena.length() > 0) {
			try {
				dblResultado = Boolean.parseBoolean(strCadena);
			} catch(Exception objEx) {
				//
			}
		}
		return dblResultado;
	}
	
    public static String convertirEnteroACadenaUbigeo(int numero) {
    	String num = "";
        num = numero+"";
        if (num.length()==1){
        	num = "0" + num;
        }
        
        return num;
    }
    
    public static String convertirEnteroACadenaDecimal(Double numero) {
        DecimalFormat dfmt = new DecimalFormat("###,###,###.##");
        return dfmt.format(numero);
    }
    
    public static String recortarCadena(String descripcion, int inicio, int fin){
    	String strResultado = "";
    	
    	if(descripcion != null){
        	int cadenaTamanio = descripcion.length();
        	
        	if(cadenaTamanio >= fin){
        		strResultado = descripcion.substring(inicio, fin);
        	}else {
        		strResultado = descripcion.substring(inicio, cadenaTamanio);
    		}
    	}
    	
    	return strResultado;
    }
}
