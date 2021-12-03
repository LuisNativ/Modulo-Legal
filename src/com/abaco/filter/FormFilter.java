package com.abaco.filter;

import com.abaco.validators.FormValidatorException;

abstract public class FormFilter {

    final int ERROR = -1;
    final int SELECCIONE_VALUE = 0;
    final String SELECCIONE_STRING = "0";
    final String debeIngresar = "Debe ingresar ";
    final String debeSeleccionar = "Debe seleccionar ";
    final String debeEspecificar = "Debe especificar ";

    public abstract void validate() throws FormValidatorException;

}
