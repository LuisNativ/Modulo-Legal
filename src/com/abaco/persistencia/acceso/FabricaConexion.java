package com.abaco.persistencia.acceso;

import com.abaco.negocio.util.UConfiguraciones;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.persistencia.interfaces.IConexion;

public class FabricaConexion {

	public static final int DB2 = 1;

	@Deprecated
	public static IConexion creaConexion(int motorBaseDatos) {
		IConexion conexion = null;
		switch (motorBaseDatos) {
		case DB2:
			conexion = new ConexionDB2();
			((ConexionDB2)conexion).setModoDebug(getModoDebug());
			break;
		}
		return conexion;
	}

	public static IConexion creaConexion() {
		int intMotorBaseDatos = UFuncionesGenerales.convierteCadenaAEntero(
			UConfiguraciones.obtieneInstancia().obtieneValor(
				"MOTOR_BASE_DATOS"));
		IConexion conexion = null;
		switch (intMotorBaseDatos) {
		case DB2:
			conexion = new ConexionDB2(getModoDebug());
			break;
		}
		return conexion;
	}

	private static boolean getModoDebug() {
		String strDebug = UConfiguraciones.obtieneInstancia().obtieneValor("DEBUG");
		boolean blnDebug = Boolean.parseBoolean(strDebug);
		return blnDebug;
	}
}
