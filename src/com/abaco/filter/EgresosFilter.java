package com.abaco.filter;

import com.abaco.ageneral.ERevisionMensaje;
import com.abaco.validators.FormValidatorException;

import static com.abaco.negocio.util.UFuncionesGenerales.revisaCadena;

public class EgresosFilter extends FormFilter {

    private final ERevisionMensaje egreso;

    public EgresosFilter(ERevisionMensaje egreso) {
        this.egreso = egreso;
    }

    @Override
    public void validate() throws FormValidatorException {

        String tabName = "Tab(Datos de Ingresos/Egresos): <br/><br/>";
        /*
        if (egreso.getCampo1().equals("")) {
            throw new FormValidatorException(ERROR, tabName + debeIngresar + "campo 1.");
        }

        if (egreso.getCampo2().equals("")) {
            throw new FormValidatorException(ERROR, tabName + debeIngresar + "campo 2.");
        }
        
        if (egreso.getCampo3().equals("")) {
            throw new FormValidatorException(ERROR, tabName + debeIngresar + "campo 3.");
        }
         */
    }
}
