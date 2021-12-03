package com.abaco.persistencia.acceso;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UTipoMensaje;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.entidad.EListaParametroSql;
import com.abaco.persistencia.entidad.EParametroSql;
import com.abaco.persistencia.interfaces.IConexion;

public final class ConexionDB2 implements IConexion {

	private EMensaje mensajeUltimaOperacion;
	private Connection conexion;
	private final String db2DataSource = "java:/AS400DS";
	private boolean modoDebug = false;

	public ConexionDB2() {
		this.conexion = obtieneConexionDB2();
	}

	public ConexionDB2(boolean modoDebug) {
		this.modoDebug = modoDebug;
		this.conexion = obtieneConexionDB2();
		guardaLog("Conexión abierta.");
		imprimeUbicacion();
	}

	public void iniciaTransaccion() {
		guardaLog("Inicio de transaccion.");
		try {
			this.conexion.setAutoCommit(false);
			guardaLog("Inicio de transaccion exitoso.");
		} catch(SQLException objSQLEx) {
			UManejadorLog.error("AConexion: Problemas con la conexion SQL.", objSQLEx);
		}
	}

	public void ejecutaCommit() {
		guardaLog("Se ejecutará un commit.");
		try {
			this.conexion.commit();
			guardaLog("Ejecución de commit exitoso.");
		} catch(SQLException objSQLEx) {
			UManejadorLog.error("AConexion: Problemas con la conexion SQL.", objSQLEx);
		}
	}

	public void ejecutaRollback() {
		guardaLog("Se ejecutará un rollback.");
		try {
			this.conexion.rollback();
			guardaLog("Ejecución de rollback exitoso.");
		} catch(SQLException objSQLEx) {
			UManejadorLog.error("AConexion: Problemas con la conexion SQL.", objSQLEx);
		}
	}

	public void ejecutaRollback(Savepoint objSavepoint) {
		try {
			this.conexion.rollback(objSavepoint);
		} catch(SQLException objSQLEx) {
			UManejadorLog.error("AConexion: Problemas con la conexion SQL.", objSQLEx);
		}
	}

	public Connection getConexion() {
		return this.conexion;
	}

	public void cierraConexion() {
		try {
			guardaLog("Cerrando conexion...");
			this.conexion.close();			
			guardaLog("La conexión se cerró sin problemas.");
		} catch(SQLException objSQLEx) {
			UManejadorLog.error("AConexion: Problemas con la conexion SQL.", objSQLEx);
		}
	}

	public EMensaje getMensajeUltimaOperacion() {
		return this.mensajeUltimaOperacion;
	}

	public Connection obtieneConexionDB2() {
		Connection objConnection = null;
		try {
			InitialContext objInitialContext = new InitialContext();
			DataSource objDS = (DataSource) objInitialContext.lookup(db2DataSource);
			try {
				objConnection = objDS.getConnection();
			} catch (SQLException objSQLEx) {
				UManejadorLog.error("Acceso: Error obteniendo la conexión a la base de datos.", objSQLEx);
			}
			
		} catch (NamingException objNamingEx) {
			UManejadorLog.error("Acceso: Error en el método Conectar() de la clase AConexion.", objNamingEx);
		}
		return objConnection;
	}

	/***
	 * Función que permite la ejecución de una transacción simple: Insertar,
	 * Actualizar o Eliminar. Los Stored Procedures (SP) a ejecutarse en esta
	 * sentencia deben tener un conjunto de parámetros de entrada (0 a más) y al
	 * final 2 parámetros de salida: 1 de tipo DECIMAL y otro de tipo CHAR. En
	 * caso que el SP no cumpla con este standar, se generará un error en la
	 * ejecución.
	 * 
	 * @param strStoredProcedure
	 *            Nombre del Stored Procedure a ejecutar.
	 * @param lstParametrosEntrada
	 *            Lista de objetos que posee los parámetros de entrada. Los
	 *            objetos contenidos en esta lista deben pertenecer a tipos de
	 *            datos nativos de Java (String, int, Date, etc).
	 * @return Cadena con el resultado de la ejecución del Stored Procedure.
	 */
	public EMensaje ejecutaTransaccion(String strStoredProcedure,
		List<Object> lstParametrosEntrada) throws SQLException {
		EMensaje objEMensaje = new EMensaje();
		objEMensaje.setTipoMensaje(UTipoMensaje.SQLError);
		CallableStatement objCallableStatement = null;
		int intParametros = 0;
		try {
			objEMensaje.setIdMensaje(-1000);
			objEMensaje.setDescMensaje("Sin ejecutar");
			objCallableStatement = this.conexion.prepareCall(strStoredProcedure);
			intParametros = agregaParametrosEntrada(objCallableStatement, lstParametrosEntrada);
			objCallableStatement.registerOutParameter(intParametros, Types.DECIMAL);
			objCallableStatement.registerOutParameter(intParametros + 1, Types.CHAR);
			guardaLog("Se ejecuta el SP: " + strStoredProcedure);
			if (lstParametrosEntrada != null) {
				guardaLog("Parametros: " + Arrays.deepToString(lstParametrosEntrada.toArray()));
			}
			objCallableStatement.execute();
			objEMensaje.setIdMensaje(Integer.parseInt(objCallableStatement.getString(intParametros)));
			objEMensaje.setDescMensaje(objCallableStatement.getString(intParametros + 1));
		} catch (SQLException objSQLEx) {
			objEMensaje = UFuncionesGenerales.formateaErrorSQL(objSQLEx.getMessage());
			UManejadorLog.error("Conexión: Error al ejecutar el procedimiento " + strStoredProcedure + ": " + objSQLEx.getMessage());
			guardaLog("Conexión: Lista de parámetros: " + Arrays.deepToString(lstParametrosEntrada.toArray()));
		} catch (Exception objEx) {
			objEMensaje.setIdMensaje(ERROR_NO_SQL);
			objEMensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Conexión: Error en la funcion ejecutaTransaccionSimple: " , objEx);
			UManejadorLog.error("Conexión: Detalle del error: " + objEx.getStackTrace()[0] + " " + objEx.getStackTrace()[1]);
		} finally {
			if (objCallableStatement != null) {
				objCallableStatement.close();
			}
		}
		mensajeUltimaOperacion = objEMensaje;
		return objEMensaje;
	}
		
	public void cierraConsulta(ResultSet objResultSet) throws SQLException {
		if (objResultSet != null) {
			Statement objStatement = objResultSet.getStatement();
			objResultSet.close();
			objStatement.close();
		}
	}

	/**
	 * Función que permite la ejecución de un Stored Procedure de consulta de
	 * datos.
	 * @param strStoredProcedure
	 *            Nombre del Stored Procedure a ejecutar.
	 * @param lstParametrosEntrada
	 *            Lista de objetos que posee los parámetros de entrada. Los
	 *            objetos contenidos en esta lista deben pertenecer a tipos de
	 *            datos nativos de Java (String, int, Date, etc).
	 * @param lstParametrosSalida
	 *            Lista de objetos que posee los parámetros de salida. Los
	 *            objetos contenidos en esta lista deben pertenecer a tipos de
	 *            datos nativos de Java (String, int, Date, etc).
	 * @return ResultSet con el conjunto de datos obtenidos de la consulta.
	 */
	public ResultSet ejecutaConsulta(String strStoredProcedure,
			List<Object> lstParametrosEntrada, List<Object> lstParametrosSalida) {
		CallableStatement objCallableStatement = null;
		ResultSet objResultSet = null;
		int intParametrosEntrada = 0;
		int intParametrosSalida = 0;
		mensajeUltimaOperacion = new EMensaje();
		mensajeUltimaOperacion.setIdMensaje(0);
		mensajeUltimaOperacion.setDescMensaje("Proceso exitoso.");
		try {
			objCallableStatement = this.conexion.prepareCall(strStoredProcedure);
			intParametrosEntrada = agregaParametrosEntrada(objCallableStatement, lstParametrosEntrada);
			intParametrosSalida = agregaParametrosSalida(objCallableStatement, lstParametrosSalida, intParametrosEntrada);
			guardaLog("Se ejecuta el SP: " + strStoredProcedure);
			if (lstParametrosEntrada != null) {
				guardaLog("Parametros: " + Arrays.deepToString(lstParametrosEntrada.toArray()));
			}
			objCallableStatement.execute();
			intParametrosSalida = intParametrosEntrada;
			objResultSet = objCallableStatement.getResultSet();
			// recuperar los datos en el lstParametrosSalida
			if (lstParametrosSalida != null) {
				List<Object> lstResultadoSalida = obtieneParametrosSalida(lstParametrosSalida, intParametrosSalida, objCallableStatement);
				lstParametrosSalida.clear();
				for (Object object : lstResultadoSalida) {
					lstParametrosSalida.add(object);
				}
			}
		} catch (SQLException objSQLEx) {
			mensajeUltimaOperacion = UFuncionesGenerales.formateaErrorSQL(objSQLEx.getMessage());
			UManejadorLog.error("Conexión: Error al ejecutar el procedimiento " + strStoredProcedure + ": " + objSQLEx.getMessage());
			guardaLog("Conexión: Lista de parámetros: " + Arrays.deepToString(lstParametrosEntrada.toArray()));
		} catch (Exception objEx) {
			mensajeUltimaOperacion.setIdMensaje(ERROR_NO_SQL);
			mensajeUltimaOperacion.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Conexión: Detalle del error: " + objEx.getStackTrace()[0] + " " + objEx.getStackTrace()[1]);
		}
		return objResultSet;
	}

	public ResultSet ejecutaConsulta(String strStoredProcedure,
			List<Object> lstParametrosEntrada) {
		return ejecutaConsulta(strStoredProcedure, lstParametrosEntrada, null);
	}

	/***
	 * Permite obtener el tipo de dato SQL a partir del tipo de dato Java.
	 * 
	 * @param obj
	 *            Objeto con tipo de dato de Java nativo (int, double, String,
	 *            Date, etc).
	 * @return Valor entero que indica el tipo de dato SQL.
	 */
	public int obtieneTipoSQL(Object obj) {
		int resultado = Types.JAVA_OBJECT;
		if (obj.getClass() == Integer.class)
			resultado = Types.INTEGER;
		if (obj.getClass() == Long.class)
			resultado = Types.INTEGER;
		if (obj.getClass() == java.util.Date.class)
			resultado = Types.DATE;
		if (obj.getClass() == Double.class)
			resultado = Types.DOUBLE;
		if (obj.getClass() == String.class)
			resultado = Types.VARCHAR;
		return resultado;
	}

	/***
	 * Permite agregar parámetros de entrada para la ejecución de una sentencia
	 * SQL.
	 * 
	 * @param objCallableStatement
	 *            Objeto que contiene la sentencia SQL. Ejemplos: "SELECT COL2,
	 *            COL3 FROM TB000_TABLA WHERE COL1 = ?", "CALL
	 *            MI_ESQUEMA.SP_SEL_TB000_TABLA(?)".
	 * @param lstParametrosEntrada
	 *            Lista de objetos de parámetros de entrada para la sentencia
	 *            SQL. Los objetos de la lista deben encontrarse en el orden en
	 *            que deben asignarse los parámetros.
	 * @return La ubicación de registro de parámetros.
	 * @throws SQLException
	 *             Excepción SQL en caso que la asignación de variables sea
	 *             incorrecta.
	 */
	private int agregaParametrosEntrada(CallableStatement objCallableStatement,
			List<Object> lstParametrosEntrada) throws SQLException {
		return agregaParametrosEntrada(objCallableStatement,
				lstParametrosEntrada, 1);
	}

	/***
	 * Permite agregar parámetros de entrada para la ejecución de una sentencia
	 * SQL.
	 * 
	 * @param objCallableStatement
	 *            Objeto que contiene la sentencia SQL. Ejemplos: "SELECT COL2,
	 *            COL3 FROM TB000_TABLA WHERE COL1 = ?", "CALL
	 *            MI_ESQUEMA.SP_SEL_TB000_TABLA(?)".
	 * @param lstParametrosEntrada
	 *            Lista de objetos de parámetros de entrada para la sentencia
	 *            SQL. Los objetos de la lista deben encontrarse en el orden en
	 *            que deben asignarse los parámetros.
	 * @param intIndiceParametro
	 *            El índice donde comenzará a insertar los parámetros.
	 * @return La ubicación de registro de parámetros.
	 * @throws SQLException
	 *             Excepción SQL en caso que la asignación de variables sea
	 *             incorrecta.
	 */
	public int agregaParametrosEntrada(CallableStatement objCallableStatement,
			List<Object> lstParametrosEntrada, int intIndiceParametro)
			throws SQLException {
		int intParametrosEntrada = intIndiceParametro;
		if (lstParametrosEntrada != null) {			
			for (Object object : lstParametrosEntrada) {
				objCallableStatement.setObject(intParametrosEntrada++, object);
			}
		}
		else
			intIndiceParametro = 0;
		
		return intParametrosEntrada;
	}

	/***
	 * Permite registrar los tipos de datos de los parámetros de salida para la
	 * ejecución de una sentencia SQL.
	 * 
	 * @param objCallableStatement
	 *            Objeto que contiene la sentencia SQL. Ejemplos:
	 *            "CALL MI_ESQUEMA.SP_INS_TB000_TABLA(?, ?, <OUT> ?, <OUT> ?)".
	 * @param lstParametrosSalida
	 *            Lista de objetos de parámetros de salida para la sentencia
	 *            SQL. Los objetos de la lista deben encontrarse en el orden en
	 *            que deben asignarse los parámetros.
	 * @param intIndiceParametro
	 *            El índice donde comenzará a registrar los tipos de datos de
	 *            los parámetros de salida.
	 * @return La ubicación de registro de parámetros.
	 * @throws SQLException
	 *             Excepción SQL en caso que la asignación de tipos de variables
	 *             sea incorrecta.
	 */
	public int agregaParametrosSalida(CallableStatement objCallableStatement,
			List<Object> lstParametrosSalida, int intIndiceParametro)
			throws SQLException {
		int intParametrosSalida = intIndiceParametro;
		if (lstParametrosSalida != null) {
			for (Object object : lstParametrosSalida) {
				objCallableStatement.registerOutParameter(
						intParametrosSalida++, obtieneTipoSQL(object));
			}
		}
		return intParametrosSalida;
	}


	private List<Object> obtieneParametrosSalida(
			List<Object> lstParametrosSalida, int intInicio,
			CallableStatement objCallableStatement) throws SQLException {
		List<Object> lstParametrosSalidaDevuelto = new ArrayList<Object>();
		if (lstParametrosSalida != null) {
			for (Object objObject : lstParametrosSalida) {
				if (objObject.getClass() == Integer.class)
					lstParametrosSalidaDevuelto.add(objCallableStatement.getInt(intInicio));
				if (objObject.getClass() == Long.class)
					lstParametrosSalidaDevuelto.add(objCallableStatement.getLong(intInicio));
				if (objObject.getClass() == java.util.Date.class)
					lstParametrosSalidaDevuelto.add(objCallableStatement.getTimestamp(intInicio));
				if (objObject.getClass() == Double.class)					
					lstParametrosSalidaDevuelto.add(objCallableStatement.getDouble(intInicio));
				if (objObject.getClass() == String.class) {
					if (objCallableStatement.getString(intInicio) != null)
						lstParametrosSalidaDevuelto.add(objCallableStatement.getString(intInicio));
				}
				intInicio++;
			}
		}
		return lstParametrosSalidaDevuelto;
	}

	/* (non-Javadoc)
	 * @see com.abaco.acceso.IConexion#cambiaNivelIsolacion(int)
	 */
	public void cambiaNivelIsolacion(int intNivelIsolacion) {
		try {
			guardaLog("Cambiando el nivel de isolación: " + String.valueOf(intNivelIsolacion));
			conexion.setTransactionIsolation(intNivelIsolacion);
		} catch (Exception objEx) {
			UManejadorLog.error("Conexión: Error al cambiar el nivel de isoluación." , objEx);
		}
	}

	public boolean isModoDebug() {
		return modoDebug;
	}

	public void setModoDebug(boolean modoDebug) {
		this.modoDebug = modoDebug;
	}

	private void imprimeUbicacion() {
		if (modoDebug) {
			try {
				StackTraceElement[] st = Thread.currentThread().getStackTrace();
				imprimeLog(st[4].toString());
			} catch (Exception objEx) {
			}
		}
	}

	private void guardaLog(String strMensaje) {
		if (modoDebug) {
			imprimeLog(strMensaje);
		}
	}

	private void imprimeLog(String strMensaje) {
		UManejadorLog.log(String.format("Conexion: %s. Mensaje: %s", conexion.toString(), strMensaje));
	}
	
	public ResultSet ejecutaTransaccionRPG(String strStoredProcedure, EListaParametroSql lstParametros) {
		ResultSet objResultSet = null;
		CallableStatement objCallableStatement = null;
		EListaParametroSql objParametrosSalida = null;
		mensajeUltimaOperacion = new EMensaje();
		mensajeUltimaOperacion.setIdMensaje(0);
		mensajeUltimaOperacion.setDescMensaje("Proceso exitoso.");
		try {
			objCallableStatement = this.conexion.prepareCall(strStoredProcedure);
			objParametrosSalida = agregaParametros(objCallableStatement, lstParametros);
			guardaLog("Se ejecuta el SP: " + strStoredProcedure);
			guardaLog("Parametros: " + Arrays.deepToString(lstParametros.toArray()));
			objCallableStatement.execute();
			objResultSet = objCallableStatement.getResultSet();
			// recuperar los datos en el lstParametrosSalida
			if (objParametrosSalida != null) {
				for(EParametroSql objAParametroSql : objParametrosSalida) {
					obtieneParametroSalida(objCallableStatement, objAParametroSql);
				}
			}
		} catch (SQLException objSQLEx) {
			mensajeUltimaOperacion = UFuncionesGenerales.formateaErrorSQL(objSQLEx.getMessage());
			UManejadorLog.error("Conexión: Error al ejecutar el procedimiento " + strStoredProcedure + ": " + objSQLEx.getMessage());
			guardaLog("Conexión: Lista de parámetros: " + Arrays.deepToString(lstParametros.toArray()));
		} catch (Exception objEx) {
			mensajeUltimaOperacion.setIdMensaje(ERROR_NO_SQL);
			mensajeUltimaOperacion.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Conexión: Detalle del error: " + objEx.getStackTrace()[0] + " " + objEx.getStackTrace()[1]);
		}
		return objResultSet;
	}
	
	private EListaParametroSql agregaParametros(CallableStatement objCallableStatement,
			EListaParametroSql objAListaParametroSql) {
		EListaParametroSql objParametrosSalida = null;
		if (objAListaParametroSql != null && objAListaParametroSql.size() > 0) {
			try {
				for(EParametroSql objAParametroSql : objAListaParametroSql) {
					switch(objAParametroSql.getTipoEntradaSalida()) {
					case EParametroSql.ENTRADA:
						objCallableStatement.setObject(
								objAParametroSql.getPosicion(), objAParametroSql.getDato());
						break;
					case EParametroSql.SALIDA:
						objCallableStatement.registerOutParameter(
								objAParametroSql.getPosicion(), obtieneTipoSQL(objAParametroSql.getDato()));
						if (objParametrosSalida == null) {
							objParametrosSalida = new EListaParametroSql();
						}
						objParametrosSalida.add(objAParametroSql, false);
						break;
					case EParametroSql.ENTRADA_SALIDA:
						objCallableStatement.setObject(
								objAParametroSql.getPosicion(), objAParametroSql.getDato());
						objCallableStatement.registerOutParameter(
								objAParametroSql.getPosicion(), obtieneTipoSQL(objAParametroSql.getDato()));
						if (objParametrosSalida == null) {
							objParametrosSalida = new EListaParametroSql();
						}
						objParametrosSalida.add(objAParametroSql, false);
						break;
					}
				}
			} catch (Exception objEx) {
				UManejadorLog.error("Conexion: Error al agregar los parametros del procedimiento." , objEx);
			}
		}
		return objParametrosSalida;
	}
	
	private void obtieneParametroSalida(CallableStatement objCallableStatement,
			EParametroSql objAParametroSql) throws SQLException{
		if (objAParametroSql != null) {
			if (objAParametroSql.getDato().getClass() == Integer.class)
				objAParametroSql.setDato((objCallableStatement.getInt(objAParametroSql.getPosicion())));
			if (objAParametroSql.getDato().getClass() == Long.class)
				objAParametroSql.setDato(objCallableStatement.getLong(objAParametroSql.getPosicion()));
			if (objAParametroSql.getDato().getClass() == java.util.Date.class)
				objAParametroSql.setDato(objCallableStatement.getTimestamp(objAParametroSql.getPosicion()));
			if (objAParametroSql.getDato().getClass() == Double.class)
				objAParametroSql.setDato(objCallableStatement.getDouble(objAParametroSql.getPosicion()));
			if (objAParametroSql.getDato().getClass() == String.class) {
				if (objCallableStatement.getString(objAParametroSql.getPosicion()) != null)
					objAParametroSql.setDato(objCallableStatement.getString(objAParametroSql.getPosicion()));
			}
		}
	}
}
