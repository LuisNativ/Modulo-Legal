package com.abaco.persistencia.acceso;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UManejadorLog;

public class ConexionAS400 {

	private String driverClassName = "";
	private String url = "";
	private Connection connection = null;		
	public static final String DB2 = "com.ibm.as400.access.AS400JDBCDriver";
	public static final String FILECONFIG = "com/abaco/presentacion/properties/conexion.properties";
	public static final String JDBC = "jdbc";
	
	public ConexionAS400() {
		this.driverClassName = DB2;		
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			UManejadorLog.error(e.getMessage());
		}		
		try {
			Properties prop = new Properties();
			prop.load(this.getClass().getClassLoader().getResourceAsStream(FILECONFIG));
			url = (String) prop.get(JDBC);
		} catch (IOException e) {
			UManejadorLog.error(e.getMessage());
		}
	}

	public void closeConnection() {
		if (connection != null){
			try {
				connection.setAutoCommit(true);
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connection = null;
	}
	
	public EMensaje openConnection(String usuario, String clave) {
		EMensaje eMensaje = new EMensaje(); 
		eMensaje.setIdMensaje(0);
		try {
			Class.forName(driverClassName);					
			Properties prop = new java.util.Properties();
			prop.put("user", usuario);
			prop.put("password",clave);
			prop.put("prompt","false");				
			connection = DriverManager.getConnection(url,prop);
			connection.createStatement();			
			connection.setAutoCommit(false);
			eMensaje.setIdMensaje(UConstante.USeguridad.USUARIO_AUTENTICADO);
			
		} catch (SQLException sqlException) {								
			String message = sqlException.getMessage();
			eMensaje.setDescMensaje(sqlException.getMessage());
			if(message!=null && message.contains("Password")){
				eMensaje.setIdMensaje(UConstante.USeguridad.CLAVE_ERRADA);
				eMensaje.setDescMensaje("Contraseña es incorrecta");
			} 
			
			if(message!=null && message.toUpperCase().contains("ID")){
				eMensaje.setIdMensaje(UConstante.USeguridad.USUARIO_DESCONOCIDO);
				eMensaje.setDescMensaje("Usuario es desconocido");
			}			
			UManejadorLog.error(sqlException.getMessage());			
		} catch (ClassNotFoundException classNotFoundException) {
			
			eMensaje.setDescMensaje(classNotFoundException.getMessage());
			UManejadorLog.error(classNotFoundException.getMessage());			
		}
		return eMensaje;
	}	

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
