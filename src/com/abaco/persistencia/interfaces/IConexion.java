/**
* IConexion.java 09/02/2012
* Creado por: B-IT Solutions
*/
package com.abaco.persistencia.interfaces;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.persistencia.entidad.EListaParametroSql;

public interface IConexion {

	final int ERROR_NO_SQL = -9999;
	Connection getConexion();
	int agregaParametrosEntrada(CallableStatement objCallableStatement, List<Object> lstParametrosEntrada, int intIndiceParametro) throws SQLException;
	int agregaParametrosSalida(CallableStatement objCallableStatement, List<Object> lstParametrosSalida, int intIndiceParametro) throws SQLException;
	void cierraConexion();
	void cierraConsulta(ResultSet objResultSet) throws SQLException;
	void ejecutaCommit();
	ResultSet ejecutaConsulta(String strStoredProcedure, List<Object> lstParametrosEntrada);
	ResultSet ejecutaConsulta(String strStoredProcedure, List<Object> lstParametrosEntrada, List<Object> lstParametrosSalida);
	void ejecutaRollback();
	void ejecutaRollback(Savepoint objSavepoint);
	EMensaje ejecutaTransaccion(String strStoredProcedure, List<Object> lstParametrosEntrada) throws SQLException;	
	EMensaje getMensajeUltimaOperacion();
	void iniciaTransaccion();
	int obtieneTipoSQL(Object obj);
	void cambiaNivelIsolacion(int intNivelIsolacion);
	ResultSet ejecutaTransaccionRPG(String strStoredProcedure, EListaParametroSql lstParametros);
}
