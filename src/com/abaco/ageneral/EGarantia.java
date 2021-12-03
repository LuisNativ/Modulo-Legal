package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;

public @Data class EGarantia {
	/* Correspondiente a la tabla F9201 */
	private int codigoServicio;
	private long codigoGarantia;
	private int codigoMoneda;
	private int codigoSucursal;
	private int codigoAgenciaEmisora;
	private int codigoDepartamentoEmisor;
	private String descripcionUsuarioEmisor;
	private int codigoCliente;
	private int grupoEconomico;
	private String nombreCorto;
	private int codigoPropietario;
	private int codigoAval2;
	private int codigoAval3;
	private int codigoAval4;
	private int codigoTipoAval1;
	private int codigoTipoAval2;
	private int codigoTipoAval3;
	private int codigoTipoAval4;
	private int codigoTipoGarantia;
	private int hojaIngresoLegal;
	private int tipoVariable1;
	private int tipoVariable2;
	private int tipoVariable3;
	private int codigoEstado;
	private int codigoSituacion;
	private int origenGarantia;
	private int desplazamiento;
	private int judicial;
	private int numeroRegistro;
	private String numeroDocumento;
	private String descripcionA;
	private String descripcionB;
	private String descripcionC;
	private String comentario;
	private int numeroPropuesta;
	private int transaccionOrigen;
	private int monedaCredito;
	private int garantizadoActivos;
	private int informacionAdicional;
	private int codigoTasador;
	private int codigoUbigeo;
	private Date fechaIngreso;
	private Date fechaUltimoMovimiento;	
	private Date fechaVencimiento;
	private Date fechaLiberacion;
	private Date fechaEjecucion;
	private Date fechaRemate;
	private Date fechaGravamen;
	private Date fechaValoracion;
	private Date fechaUltimoEstadoRegistrado;
	private Date fechaUltimaInspeccion;
	private Date fechaPropuesta;
	private Date fechaVariable1;
	private Date fechaVariable2;
	private Date fechaVariable3;
	private Date fechaVariable4;
	private double montoOriginalGarantia;
	private double montoGarantia;
	private double montoGravamen;
	private double montoValoracion;
	private double montoCredito;
	private double montoEjecucion;
	private double montoVariable1;
	private double montoVariable2;
	private double montoVariable3;
	private double montoVariable4;
	private int codigoInspector;
	private int codigoTipoBien;
	private int depositario;
	private int almacen;
	private String placa;
	private String clase;
	private String marca;
	private String modelo;
	private String carroceria;
	private int numeroVariable1;
	private int numeroVariable2;
	private int numeroVariable3;
	private int numeroVariable4;
	private int anioFabricacion;
	private int tipoAlmacen;
	private int bancoEmisor;
	private int cuenta;
	private String rangoHipoteca;
	private String usuarioUltimaModificacion;
	private int marcaProtestado;
	private int marcaRemate;
	private String ubicacion1;
	private String ubicacion2;
	private String poliza;
	private int codigoTipoPoliza;
	private Date fechaVencimientoPoliza;
	private double valorPoliza;
	private int codigoCiaSeguro;
	private String numeroEndoso;
	private Date fechaEndoso;
	private int servicioCredito;
	private int numeroCredito;
	private int codigoAsignadoCredito;
	private Date fechaUltimaAsignacion;
	private Date horaUltimaAsignacion;
	private String usuarioUltimaAsignacion;
	private double montoDisponible;
	private double saldoDisponible;
	private double tipocambio;
	private double porcentajeDisponible;
	private Date fechaPoliza;
	
	//Atributos de Registro
	private Date fechaRegistro;
	private Date horaRegistro;
	private EUsuario usuarioRegistro;
	
	
	//Extras Descripciones
	private String descripcionSituacion;
	private String descripcionTipoBien;
	private String abreviacionMoneda;
	private String descripcionMoneda;
	private String descripcionTipoGarantia;
	private String descripcionEstadoGarantia;
	private String descripcionTasador;		
	private String descripcionUsoGarantia;
	private String descripcionCiaSeguro;
	private String descripcionBanco;
	private String descripcionTipoFiador;
	private String descripcionTipoFianza;
	private String descripcionTipoPlazo;
	private String descripcionDepositario;
	private String descripcionPropietario;
	private String descripcionPropietario2;
	private String descripcionPropietario3;
	private String descripcionPropietario4;
	private String descripcionPropietario5;
	private String descripcionPropietario6;
	private String descripcionTipoPoliza;
	private String obervacionGarantia;
	
	//Atributos adicionales
	private double porcentajeCubierto;
	private double montoCubierto;
	private long numeroSolicitud;
	//private Date fechaPoliza;
	private String ubicacion1Largo;
	private String ubicacion2Largo;
	private double montoComercial;
	private Date fechaComercial;
	private String partidaRegistral;
	private int oficinaRegistral;
    private int tipoRegistral;
    private int codigoPropietario2;
    private int codigoPropietario3;
    private int codigoPropietario4;
    private int codigoPropietario5;
    private int codigoPropietario6;
    private List<EPersona> lstPropietario;
    
	
	
	
	//Atributos Para Documentacion
	private String areaEmisora;
	private String areaReceptora;
	private String descripcionDocumento;
	private String firmaDocumento;
	private String observacionDocumento;
	private int estadoDocumento;
	private String descripcionEstadoDocumento;
	private EOperacionDocumento operacionDocumento;
	private List<EOperacionDocumento> lstOperacionDocumento;
	
	
}
