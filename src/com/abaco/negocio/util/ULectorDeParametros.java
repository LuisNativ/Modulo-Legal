package com.abaco.negocio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class ULectorDeParametros {

    private final Properties properties = new Properties();

    private ULectorDeParametros() {
        try {
            InputStream entrada = getClass()
                    .getClassLoader()
                    .getResourceAsStream("com/abaco/presentacion/properties/configuracion.properties");
            properties.load(entrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LazyHolder {
        private static final ULectorDeParametros INSTANCIA = new ULectorDeParametros();
    }

    public static ULectorDeParametros getInstancia() {
        return LazyHolder.INSTANCIA;
    }

    public String getValorParametro(String parametro) {
        return properties.getProperty(parametro);
    }

    public Set<String> getNombresDeParametros() {
        return properties.stringPropertyNames();
    }

    public boolean contieneParametro(String parametro) {
        return properties.containsKey(parametro);
    }

}
