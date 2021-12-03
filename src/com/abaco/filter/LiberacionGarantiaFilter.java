package com.abaco.filter;

import com.abaco.validators.FormValidatorException;

public class LiberacionGarantiaFilter extends FormFilter{
    @Override
    public void validate() throws FormValidatorException {
    	
    }
/*
    private final EDeclaracionJuradaUnica declaracionJuradaUnica;

    public DeclaracionJuradaFilter(EDeclaracionJuradaUnica declaracionJuradaUnica) {
        this.declaracionJuradaUnica = declaracionJuradaUnica;
    }

    @Override
    public void validate() throws FormValidatorException {
*/
        //            FormFilter filterColaborador = new ColaboradorFilter(colaborador);
//            filterColaborador.validate();
//
//            if (colaborador.tieneConyuge()) {
//                FormFilter filterConyuge = new ConyugeFilter(conyuge);
//                filterConyuge.validate();
//            }
/*
        FormFilter filterPatrimonio = new PatrimonioFilter(declaracionJuradaUnica.getPatrimonio());
        filterPatrimonio.validate();

        FormFilter filterObligacion = new ObligacionFilter(declaracionJuradaUnica.getInformacionComplementaria().getObligacion());
        filterObligacion.validate();

        FormFilter filterDeclaracionIntegridad = new DeclaracionIntegridadFilter(declaracionJuradaUnica.getDeclaracionIntegridad());
        filterDeclaracionIntegridad.validate();

        FormFilter filterIngresosEgresos = new IngresosEgresosFilter(declaracionJuradaUnica.getIngresosEgresos());
        filterIngresosEgresos.validate();

        FormFilter filterComplementaria = new InformacionComplementariaFilter(declaracionJuradaUnica.getInformacionComplementaria());
        filterComplementaria.validate();

    }
*/
}
