package com.abaco.negocio.util;

public class UGeneradorQueryString {

	private String url;
	private StringBuilder objStringBuilder;
	private int contadorParametros;

	public UGeneradorQueryString(String strUrl) {
		this.url = strUrl;
		this.objStringBuilder = new StringBuilder(this.url);
		this.contadorParametros = this.url.contains("?") ? 1 : 0;
	}

	public void agregaParametroQueryString(String strNombreParametro, String strValorParametro) {
		if (contadorParametros == 0) {
			this.objStringBuilder.append("?");
		} else {
			this.objStringBuilder.append("&");
		}
		this.objStringBuilder.append(strNombreParametro);
		this.objStringBuilder.append("=");
		this.objStringBuilder.append(strValorParametro);
		contadorParametros++;
	}

	public void agregaConjuntoParametroQueryString(String strConjuntoParametro) {
		if (contadorParametros == 0) {
			this.objStringBuilder.append("?");
		} else {
			this.objStringBuilder.append("&");
		}
		this.objStringBuilder.append(strConjuntoParametro);
		contadorParametros++;
	}

	public void agrega(String strQueryString) {
		if (strQueryString != null && strQueryString.length() > 0) {
			this.objStringBuilder.append(UFuncionesGenerales.revisaCadena(strQueryString));
		}
	}

	public String obtieneUrlConParametros() {
		return objStringBuilder.toString();
	}
}
