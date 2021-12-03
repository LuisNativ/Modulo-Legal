package com.abaco.persistencia.entidad;

public class EParametroSql {

	public static final int ENTRADA = 1;
	public static final int SALIDA = 2;
	public static final int ENTRADA_SALIDA = 3;

	private Object dato;
	private int tipoEntradaSalida;
	private int posicion;

	public EParametroSql(Object dato, int tipoEntradaSalida) {
		this.dato = dato;
		this.tipoEntradaSalida = (tipoEntradaSalida > 0 && tipoEntradaSalida < 4) ?
				tipoEntradaSalida : ENTRADA;
		this.posicion = 0;
	}

	public Object getDato() {
		return dato;
	}

	public void setDato(Object dato) {
		this.dato = dato;
	}

	public int getTipoEntradaSalida() {
		return tipoEntradaSalida;
	}

	public void setTipoEntradaSalida(int tipoEntradaSalida) {
		this.tipoEntradaSalida = tipoEntradaSalida;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		String objCadena = "(Dato: " + String.valueOf(dato) +
			", Dirección: " + String.valueOf(this.tipoEntradaSalida) +
			", Posición: " + String.valueOf(this.posicion) + ")";
		return objCadena;
	}
}
