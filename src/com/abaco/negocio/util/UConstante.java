package com.abaco.negocio.util;

import java.io.File;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class UConstante {

	public static String NOMBRE_SISTEMA = "AbacoInLegal";
	public static int CODIGO_PROYECTO = 20;
	public static int BUFFER_SIZE = 2048;
	public static int DOMICILIO_POSTAL_DEFAULT = 1;
	public static String SELECCIONE = "0";
	public static String MARCADO = "X";

	public static interface UVariablesWebXML {
		String MENU_PRINCIPAL = "MENU_PRINCIPAL";
		String SERVICIOS_LASERFICHE = "SERVICIOS_LASERFICHE";
	}

	public static class URutaServicioWeb {
		public static final String MENU_PRINCIPAL = obtieneRutaMenuPrincipal();
		public static final String SERVICIOS_LASERFICHE = obtieneRutaServiciosLaserfiche();

		private static String obtieneRutaMenuPrincipal() {
			String strResultado = "";
			try {
				strResultado = UConfiguraciones.obtieneInstancia().obtieneValor(UVariablesWebXML.MENU_PRINCIPAL);
			} catch (Exception objEx) {
				UManejadorLog.log("No se pudo obtener variable MENU_PRINCIPAL desde el archivo web.xml");
			}
			return strResultado;
		}

		private static String obtieneRutaServiciosLaserfiche() {
			String strResultado = "";
			try {
				strResultado = UConfiguraciones.obtieneInstancia().obtieneValor(UVariablesWebXML.SERVICIOS_LASERFICHE);
			} catch (Exception objEx) {
				UManejadorLog.log(
						"No se pudo obtener variable desde el archivo web.xml. Se procede a probar con la ruta http://localhost:4574");
				strResultado = "http://localhost:4574/ServicioLaserFiche.svc?wsdl";
			}
			return strResultado;
		}

		public static String obtenerRutaAbsoluta() {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("/");
			return realPath;
		}
	}

	public static interface UVariablesQueryString {
		String USUARIO = "user";
		String IDMENU = "idmenu";
		String CLIENTE ="Cliente";
	}
	
	

	public static class UListaDesplegable {

		public enum ENListaDesplegable {
			TipoDocumentoAfilia(3), Pais(51), TipoPersona(382), TipoSexo(8), Profesion(61), EstadoCivil(7), 
			NivelInstruccion(24), TipoVivienda(190), TipoDireccion(397), CargoLaboral(71), Frecuencia(148), 
			RutaFormato(30), SubUnidad(273), TipoPatrimonio(191), Institucion(416), Ocupacion(74), Moneda(0), 
			TipoCuentaBanco(54), Gravedad(803), EmailControl(37), NaturalezaJuridica(383), TamanioEmpresa(79), 
			TamanioEmpresaxVenta(42), Division(173), Unidad(73), Proveedor(22), Aceptante(23), Aportacion(88), 
			TipoRelacion(21), ComunidadOrigen(412), TipoContribuyente(53);
			private int codTab;

			private ENListaDesplegable(int codTab) {
				this.codTab = codTab;
			}

			public int getCodTab() {
				return this.codTab;
			}
		}
	}
	
	public static interface URutaCarpetaCompartida{
		//Local
		/*
		String rutaBaseLinux =  File.separator + File.separator + "192.168.1.60" + 
					              File.separator +  "Documentos" + 
						          File.separator +  "Analistas" +
						          File.separator +  "Analistas" +
						          File.separator +  "ModelosAbacoTesting" +
						          File.separator +  "Socio" +
						          File.separator +  "FichaSocio" +
						          File.separator ;
        String rutaBaseWindows =  File.separator + File.separator + "192.168.1.60" + 
					              File.separator +  "Documentos" + 
						          File.separator +  "Analistas" +
						          File.separator +  "Analistas" +
						          File.separator +  "ModelosAbacoTesting" +
						          File.separator +  "Socio" +
						          File.separator +  "FichaSocio" +
						          File.separator ;
		*/         
		//Desarrollo .130
        
		String rutaBaseLinux =  File.separator + "media" + 
				                File.separator + "unidad" +
				              //  File.separator +  "Documentos" + 
				                File.separator +  "Analistas" +
				              //  File.separator +  "Analistas" +
				                File.separator +  "ModelosAbacoTesting" +
				                File.separator +  "Socio" +
				                File.separator +  "FichaSocio" +
				                File.separator ;
        String rutaBaseWindows =    File.separator + "media" + 
					                File.separator + "unidad" +
					              //  File.separator +  "Documentos" + 
					                File.separator +  "Analistas" +
					              //  File.separator +  "Analistas" +
					                File.separator +  "ModelosAbacoTesting" +
					                File.separator +  "Socio" +
					                File.separator +  "FichaSocio" +
					                File.separator ;
        
        
        String rutaBaseWindows2 = "\\\\192.168.1.60\\Documentos\\Analistas\\Analistas\\ModelosAbacoTesting\\Socio\\FichaSocio\\";
	}
	
	public static interface UF0901 {
		int TIPOMONEDA = 2;
		int TIPODOCUMENTO = 3;
		int TIPOPERSONA = 4;
		int ESTADOCIVIL = 7;
		int TIPOSEXO = 8;
		int TIPORELACION = 21;
		int PROVEEDOR = 22;
		int ACEPTANTE = 23;
		int NIVELINSTRUCCION = 24;
		int TIPORELACIONENTRECLIENTE = 30;
		int PAIS = 51;
		int TIPOCUENTABANCO = 54;
		int PROFESION = 61;
		int BANCO = 64;
		int CARGOLABORAL = 71;
		int UNIDAD = 73;
		int OCUPACION = 74;
		int TAMANIOEMPRESA = 79;
		int APORTACION = 88;
		int FRECUENCIA = 148;
		int DIVISION = 173;
		int OTORGAMIENTO = 182;
		int TIPOVIVIENDA = 190;
		int TIPOPATRIMONIO = 191;
		int SUBUNIDAD = 273;
		int TIPOPRENDAGARANTIA = 326;
		int TIPOGARANTIA = 345;
		int SITUACIONGARANTIA = 350;
		int TIPOCIASEGURO = 351;
		int ESTADOGARANTIA = 354;
		int TIPOFIANZA = 356;
		int TIPOPLAZO = 360;
		int TIPOFIADOR = 361;
		int TIPOALMACEN = 362;
		int VALOR_SI_NO = 366;
		int ESTADOLEGAL = 378;
		int NATURALEZAJURIDICA = 383;
		int FACULTADOPERAR = 384;
		int SUSCRIPCIONPAGO = 385;
		int TIPODIRECCION = 397;
		int COMUNIDADORIGEN = 412;
		int INSTITUCION = 416;
		int TIPOREGISTRAL = 660;
		int GRAVEDAD = 803;
		int TIPOMERCADERIAWARRANT = 854;
		int MARCAVEHICULO = 864;
		int MODELOVEHICULO = 865;
		int CLASEVEHICULO = 866;
		int COMBUSTIBLEVEHICULO = 867;
		int RIESGOVEHICULO = 868;
		int TIPOPAGO = 873;
		int FORMAPAGO = 874;
		int BROCKERSEGURO= 878;
		int TIPOPOLIZA = 926;
	}
	
	public static interface UGeneral {
		int ESTADODEUDOR = 1;
		int ENVIO = 2;
		int NIVEL = 3;
		int MOTIVO = 4;
		int SERVICIO = 5;
		int PERSONARELACION = 6;
		int AUTONOMIA = 7;
		int TIPODOCUMENTO = 8;
		int AUTORIZAJEFE = 9;
		int TIPOORIGENDOC = 10;
		int TIPODURACIONPARTIDA = 11;
		int TIPONUMERACIONESTATUTO = 12;
		int OFICINAREGITRAL = 13;
		int TIPOREGISTRAL = 14;
		int ESTADOOPERACIONCLIENTE = 15;
		
	}
	
	public static interface UData {
		int ESTADO = 1;
		int AREA = 2;
		int AREAEMISOR = 3;
		int AREARECEPTOR = 4;
		int PERMISO = 5;
		int FACULTAD = 6;
		int TERCERO = 7;
		int CIUU = 8;
		int TIPOPERSONAJURIDICA = 9;
		int TIPOEVALUACION = 10;
		int TIPOBANCO = 11;
		int ESTADOOPERACION = 12;
		int ESTADOLEVANTAMIENTO = 13;
		int ESTADOSOLICITUDGARANTIA = 14;
		int TIPOGARANTIA=15;
		int TIPOGARANTIANUEVO=16;
		int OFICINAREGISTRAL=17;
		int ZONAGEO = 18;
	}
	
	public static interface UGeneral2 {
		int RUTAFORMATO = 30;
	}

	public static interface UVariablesSesion {
		String USUARIO = "usuario";
		String CODIGO_PROVEEDOR = "codigoProveedor";
		String FICHA_PARAMETRO = "fichaparametro";
		String ID_PROCESO_MANTE_POSTULANTE = "idprocesomantepostulante";
		String ID_PROCESO_MANTE_AUTORIZA_POSTULANTE = "idprocesomanteautorizapostulante";
		String FICHA_AUTORIZA_PARAMETRO = "fichaautorizaparametro";
		String ID_PROCESO_MANTE_SOCIO = "idprocesomantenimientosocio";
		String ID_PROCESO_MANTE_SOCIO_JURIDICO = "idprocesomantesociojuridico";
		String FICHA_PARAMETRO_SOCIO = "fichaparametrosocio";
		String ID_PROCESO_MANTE_POSTULANTE_JURIDICO = "idprocesomantepostulantejuridico";
		String FICHA_PARAMETRO_SOCIO_HISTORICO = "fichaparametrosociohistorico";
		String ID_ASIGNAR_LOTE_PROSPECTO = "idasignarloteprospecto";
		String ID_EVALUAR_PROSPECTO = "idevaluarprospecto";
		String ID_PROCESO = "idproceso";
		/*Ini AHM*/
		String ACCION_EXTERNA = "accionexterna";
		String OPERACION_SESION = "operacionsesion";
		String REVISION_SESION = "revisionsesion";
		String FICHA_SOLICITUD = "fichasolicitud";
		String NAVEGACION_URL = "navegacionurl";
		String TABVIEWINDEX = "tabviewindex";
		/*Fin AHM*/
	}
	
	public static interface UFormatoTexto {
		String FORMATO_TEXTO_PLANO = "text/plain;charset=UTF-8";
		String FORMATO_TEXTO_HTML = "text/html";
	}
	
	public static interface UCorreoEnvio {
		int INTRANET = 1;
		int POLIZA = 2;
		int LEGALIZACION = 3;
	}
	
	public static interface UPlantillaEmail {
		int PLANTILLA_SEGURO_VEHICULAR_SGPS = 1;
		int PLANTILLA_SEGURO_VEHICULAR_CGPS = 2;
		int PLANTILLA_SEGURO_VEHICULAR_ANULA = 3;
		int PLANTILLA_SEGURO_VEHICULAR_ANULA_NC = 4;
		int PLANTILLA_SOLICITUD_DE_PAGO = 5;
		int PLANTILLA_LEGALIZACION_FIRMA = 7;
	}
	
	public static interface UTipoMensaje {
		String Error = "E";
		String Advertencia = "A";
		String Informacion = "I";
		String Confirmacion = "C";
		String SQLError = "S";
	}

	public static interface USeguridad {
		int USUARIO_AUTENTICADO = 1;
		int USUARIO_DESCONOCIDO = 2;
		int CLAVE_ERRADA = 3;
		String USUARIO_ADMINISTRADOR = "INTRANET";
	}

	public static interface UTipoCliente {
		/*
		String SOCIO = "1";
		String INVERSIONISTA = "2";
		String TERCERO = "3";
		String POSTULANTE = "4";
		String REPRESENTANTE_LEGAL = "5";
		String NO_SOCIO = "6";
		String TASADOR = "7";
		String ACCIONISTA = "8";
		*/
		int COD_SOCIO = 1;
		int COD_INVERSIONISTA = 2;
		int COD_TERCERO = 3;
		int COD_POSTULANTE = 4;
		int COD_REPRESENTANTE_LEGAL = 5;
		int COD_NO_SOCIO = 6;
		int COD_TASADOR = 7;
		int COD_ACCIONISTA = 8;
		int COD_PROSPECTO = 9;
	}
	
	public static interface UTipoClienteSolicitudCredito {
		int COD_SOCIO = 1;
		int COD_INVERSIONISTA = 2;
		int COD_POSTULANTE = 3;
	}

	public static interface UTipoDocumento {
		String CEDULA = "C";
		String DNI = "D";
		String CARNET_MARINA_CIMA = "M";
		String CARNET_MARINA = "N";
		String CARNET_POLICIA = "P";
		String TARJETA_IDENTIDAD = "T";
		String LIBRETA_ELECTORAL = "1";
		String LIBRETA_MILITAR = "2";
		String PASAPORTE = "3";
		String RUC = "4";
		String CARNET_MILITAR = "5";
		String PARTIDA_NACIMIENTO = "6";
		String CARNET_EXTRANJERIA = "7";
		String REGISTRO_PROFESIONAL = "8";
		String OTROS = "9";
		/*
		String RUC = "4";
		String DNI = "D";
		String CARNE_EXTRANJERIA = "7";
		String EN_TRAMITE = "9";
		String S_RUC = "R.U.C.";
		String S_DNI = "D.N.I.";
		String S_CARNET_EXTRAN = "CARNET EXTRANJERIA";
		String S_RUC_LARGO = "REGISTRO ÚNICO DE CONTRIBUYENTES";
		*/
	}

	public static interface UProcesoMantenimiento {
		int NUEVO = 0;
		int EDITAR = 1;
		int APROBAR = 2;
		int EDITA_APROBA = 3;
		int VER = 4;
		int VER_REPORTE = 5;
	}

	public static interface UEstadoCredito {
		int PENDIENTE_DESEMBOLSO = 1;
		int PENDIENTE_TRANSFER_CTA = 2;
		int CREDITO_PROCESADO = 3;
		int ANULADA = 9;
	}

	public static interface UMoneda {
		String SOLES = "1";
		String DOLARES = "2";
		String EUROS = "3";
		int COD_SOLES = 1;
		int COD_DOLARES = 2;
		int COD_EUROS = 3;
	}

	public static interface USistemaOperativo {
		String LINUX = "nux";
		String WINDOWS = "win";
		String SISTEMA_OPERATIVO = System.getProperty("os.name").toLowerCase();
		boolean ES_LINUX = (SISTEMA_OPERATIVO.contains(LINUX));
		boolean ES_WINDOWS = (SISTEMA_OPERATIVO.contains(WINDOWS));
	}

	public static interface UVariablesReporte {

		String RP_IMAGEN_LOGO = "resources" + File.separator + "images" + File.separator + "logo.jpg";

		String REPORTE_LISTA_CREDITO = UFuncionesGenerales.obtenerRutaAbsoluta() + "resources" + File.separator
				+ "reports" + File.separator + "reporte_lista_credito.jasper";
	}

	public static interface UResidencia {
		String LOCAL = "1";
		String EXTRANJERO = "2";
	}

	public static interface UTipoVivienda {
		String OTRA = "4";
	}

	public static interface UEstadoCivil {
		String CONVIVIENTE = "B";
		String CASADO = "C";
		String CASADO_SEP_BIENE = "P";
		String SOLTERO = "S";
	}

	public static interface UTipoDocumentoCorrelativo {
		String CODIGO_CLIENTE = "C";
		String CONSECUTIVO_GENERAL_TRANSACC = "O";
		String CONSECUTIVO_CENTRO_TRABAJO = "U";
	}

	public static interface UTipoGeneracion {
		String ULTIMO_NUMERO = "U";
		String SUMA_NUMERO = "A";
	}

	public static interface UTipoPersona {
		String NATURAL = "N";
		String JURIDICA_F_LUCRO = "J";
		String MANCOMUNADO = "M";
		String JURIDICA_S_LUCRO = "S";

		String S_NATURAL = "NATURAL";
		String S_JURIDICA_F_LUCRO = "JURÍDICA";

	}
	/*
	public static interface UFlagResultado {
		String SI = "S";
		String NO = "N";
		int iSI = 1;
		int iNO = 0;

	}
	*/
	
	/* Ini: AHM */
	public static interface UFlagResultado {
		String NO = "N";
		String SI = "S";
		int COD_NO = 0;
		int COD_SI = 1;
	}
	
	public static interface UMaximoTamanio {
		int DNI_MAXLGN = 8;
		int RUC_MAXLGN = 11;
		int OTROS_MAXLGN = 11;
	}
	
	public static interface UNivel {
		int BAJO = 1;
		int MEDIO = 2;
		int ALTO = 3;
		int URGENTE = 4;
	}
	
	public static interface UTipoAutonomia {
		int GERENCIAL = 1;
		int JEFE = 3;
		int NINGUNO = 10;
	}
	
	public static interface UTipoCorrelativo {
		int REVISIONSOLICITUD = 1;
		int REVISIONMENSAJE = 2;
		int REVISIONSESION = 3;
		int OPERACIONSOLICITUD = 4;
		int OPERACIONMENSAJE = 5;
		int OPERACIONSESION = 6;
		int REPRESENTATELEGAL = 7;
		//int EVALUACIONXNIVEL = 8;
	}
	
	public static interface UTipoSolicitud {
		int OPERACION = 1;
		int REVISION = 2;
	}
	
	public static interface UTipoPersonaGeneral {
		int NATURAL = 1;
		int JURIDICO = 2;
	}
	
	public static interface UTipoPersonaJuridica {
		String SA = "1";
		String ASOCIACION = "2";
		String FUNDACION = "3";
		String COOPERATIVA = "4";
		String SAC = "6";
		String SAA = "7";
		String SC = "8";
		String SENC = "9";
		String SRL = "10";
		String SCIVIL = "11";
		String EIRL = "12";
		String COMITE = "13";
	}
	
	public static interface UClaseGarantia {
		int LIQUIDAS = 1;
		int REALNUEVAS = 2;
		int REALEXISTENTES = 3;
		int OTROS = 4;
	}
	
	public static interface UTipoGarantia {
		int ACCIONES = 1;
		int EMBARCACIONES = 3;
		int FIANZAS = 4;
		int LETRAS = 5;
		int MAQUINARIA = 6;
		int MERCADERIAS = 7;
 		int PREDIO = 8;
		int SEGUROS_DE_VIDA = 9;
		int TANOMOSHI = 10;
 		int VEHICULAR = 11;
		int WARRANT = 12;
		int DOCUMENTOS_FACTORING = 13;
		int PAGARES= 14;
		int DOCUMENTOS_POR_COBRAR = 15;
		int STANDBYLETTERCREDIT = 16;
		int CARTERA = 17;
		int FIDEICOMISO_BIENES = 18;
		int FLUJOS = 19;
		int INVENTARIO = 20;
		int SALDOCUENTA = 21;
		int OTROS = 22;
		int DEPOSITOS = 88;
	}
	
	public static interface UTipoServicioPrestamo {
		int SERV_991 = 9091;
	}
	
	public static interface UTipologMovimiento {
		int HOJALEGALGENERADA = 501;
		int EVALUADOPORLEGAL = 505;
		int OBSERVADOPORLEGAL = 507;
		int CAMBIADTOSOCIO = 508;
		int CAMBIADTOCONYUGE = 509;
		int CAMBIADTOREPRESENTANTE = 513;
		int CAMBIADTODEUDOR = 514;
		int ENEVALUACIONDELEGAL = 515;
	}
	
	public static interface UTipoEvaluacion {
		int NOLEVANTAMIENTO = 0;
		int SOLICITUDCREDITO = 1;
		int PODERES = 2;
		int LEVANTAMIENTO = 3;
		int CONSTITUCION = 4;
		int CARTAFIANZA = 5;
		//int PERSONANATURAL = 2;
	}
	
	public static interface UTipoBusquedaTercero {
		int GENERAL = 1;
		int DEUDOR = 2;
		int TERCERO = 3;
	}
	
	public static interface UTipoEstadoUsuario {
		int INACTIVO = 0;
		int ACTIVO = 1;
		int TODOS = 2;
	}
	
	public static interface UArea {
		int LEGAL = 100;
		int NEGOCIOS = 102;
		int CREDITOS = 106;
		
		String LEGAL_DESC = "LEGAL";
		String NEGOCIOS_DESC = "NEGOCIOS";
		String CREDITOS_DESC = "CREDITOS";
		String NOTARIA_DESC = "NOTARIA";
	}
	
	public static interface UUbicacion {
		int LEGAL = 133;
	}
	
	public static interface UAccionExterna {
		int NUEVO = 0;
		int EDITAR = 1;
		int VER = 2;
	}
	
	public static interface UAccionInterna {
		int NUEVO = 0;
		int EDITAR = 1;
		int VER = 2;
	}
	
	public static interface UAccionTabla {
		int NINGUNO = 0;
		int INSERTAR = 1;
		int EDITAR = 2;
		int ELIMINAR = 3;
	}
	
	public static interface UMotivo {
		int OP1 = 1;
		int OP2 = 2;
		int OP3 = 3;
		int OP4 = 4;
		int OTROS = 99;
	}
	
	public static interface UDocumentoRequerido{
		int OTROS = 99;
	}
	
	public static interface UPersonaRelacion {
		int NINGUNO = 1;
		int EXTERNO = 2;
		int ABACO = 3;
	}
	
	public static interface UEstadoAutorizacionJefe {
		int NINGUNO = 0;
		int SOLICITADO = 1;
		int AUTORIZADO = 2;
		int RECHAZADO = 3;
		int AUTORIZADOCOMPLETADO = 4;
	}
	
	public static interface UEstadoLegal {
		String EVALUADO = "1";
		String OBSERVADO = "2";
		String DESAPROBADO = "3";
		String ENEVALUACION = "5";
		String APROBADO = "9";
	}
	
	public static interface UEstadoOperacionCliente {
		int EVALUADO = 1;
		int OBSERVADO = 2;
		int DESAPROBADO = 3;
		int ENEVALUACION = 5;
		int APROBADO = 9;
	}
	
	public static interface UEstadoOperacionLevantamiento {
		int SOLICITADO = 1;
		int CONFIRMADO = 2;
		int RECHAZADO = 3;
		int OBSERVADO = 4;
		int ENEVALUACION = 5;
		int LIBERADO = 6;
		int ARCHIVADO = 7;
	}
	
	public static interface UEstado {
		int PENDIENTEDEREGISTRO = 1;
		int PENDIENTEDEEVALUACION = 2;
		int ENPROCESODEEVALUACION = 3;
		int OBSERVADO = 4;
		int AUTORIZADO = 5;
		int CERRADO = 6;
		int DESAPROBADO = 7;
		int CANCELADO = 8;
		int ELIMINADO = 9;
		
		int ENEVALUACION = 10;
		int APROBADO = 11;
		int RECHAZADO = 13;
		/*
		int PENDIENTEDEDIGITALIZACION = 10;
		int PENDIENTEDECONFIRMACION = 11;
		int CONFIRMADO = 12;
		int RECHAZADO = 13;
		int LIBERADO = 15;
		int CERRADOSOLICITUD = 16;
		*/
		
		int ENPROCESOCONSTITUCION = 20;
		int REGISTROFIRMASPARTICIPANTES = 21;
		int PRESENTARDOCUMENTOSANOTARIA = 22;
		int RECEPCIONDEDOCUMENTOSANOTARIA = 23;
		int CONFIRMARINSCRICION = 24;
		int ACTUALIZARINSCRIPCION = 25;
		int CONSTITUIRGARANTIA= 26;
		
		int ENPROCESOCONFIRMACION = 30;
		int CONFIRMADOLEVANTAMIENTO = 31;
		int RECHAZADOLEVANTAMIENTO = 32;
		int LIBERADO = 33;
		int ENTREGADO = 34;
		int ENPROCESOLEVANTAMIENTO = 35;
		
		int ENPROCESOCARTAFIANZA = 40;
		int CONFIRMADOCARTAFIANZA = 41;
		int ENVIADOCARTAFIANZA = 42;
		
		int EVALUADO = 51;
		int PENDIENTEGARANTIAREGISTRO = 52;
		int SOLICITAGARANTIAREGISTRO = 53;
		int REGISTRADOGARANTIAPENDIENTE = 54;
		
		int PENDIENTEFIRMA = 55;
		int SOLICITADOFIRMA = 56;
		int FIRMADOSOCIO = 57;
		int FIRMADOABACO = 58;
		int CONSTITUIDODOCUMENTO = 59;
		int DOCUMENTOFIRMADO = 60;
		int DOCUMENTONOFIRMADO = 61;
		
		String PENDIENTEDEEVALUACION_DESCEST = "Pendiente de Evaluación";
		String OBSERVADO_DESCEST = "Observado";
		String AUTORIZADO_DESCEST = "Autorizado";
		String EVALUADO_DESCEST = "Evaluado";
		String DESAPROBADO_DESCEST = "Desaprobado";
		String CANCELADO_DESCEST = "Cancelado";
		String ENPROCESOCONSTITUCION_DESCEST = "En proceso de Constitución";
		String ENPROCESOSOLICITUDLIBERACION_DESCEST = "En proceso de solicitud Liberación";
		
		
		String ENPROCESOCONSTITUCION_DESCACC = "Gestionar firma de Socio";
		String ENPROCESOSOLICITUDLIBERACION_DESCACC = "Enviar Solicitud";
		
		
		String ENPROCESOSOLICITUDCARTAFIANZA_DESCACC = "Enviar Solicitud";
	}
	
	public static interface UEstadoGarantia {
		int ENEVALUACION = 1;
		int EVALUADO = 2;
		int TRAMITE = 3;
		int NOTARIA = 4;
		int BLOQUEADA = 5;
		int REGPUBLICOS = 6;
		int CONSTITUIDO = 7;
		int TRANSCARTERA = 8;
	}
	
	public static interface USituacionGarantia {
		int PORCONSTITUIR = 0;
		int VIGENTE = 1;
		int VENCIDO = 2;
		int JUDICIAL = 3;
		int LEVENPROCESO = 4;
		int LIBERADA = 5;
		int EJECUTADA = 6;
		int ANULADA = 7;
		int PERMUTA = 8;
	}
	
	public static interface UTipoSuscripcionPago {
		String ACCIONISTAS = "1";
		String PARTICIPACIONISTAS = "2";
		String TITULARGERENTE = "3";
		String CONSEJODIRECTIVO = "4";
		String CONSEJOADMINISTRACION = "5";
		String ASAMBLEAGENERAL = "6";
		String GERENCIAGENERAL = "7";
	}
	
	public static interface UTipoValorSuscripcion {
		int PORCENTAJE = 1;
		int NUMERO = 2;
	}
	
	public static interface UTipoServicio {
		int SISTEMA = 1;
	}
	
	public static interface UCalificacion {
		int NINGUNO = 0;
		int NO = 1;
		int SI = 2;
	}
	
	public static interface UIndicadorSesion {
		int NOOCUPADO = 0;
		int OCUPADO = 1;
	}
	
	public static interface UIndicadorTemporal {
		int NO = 0;
		int SI = 1;
	}
	
	public static interface UIndicadorDigitalizacion {
		int NO = 0;
		int SI = 1;
		/*
		int NOREQUIERE = 0;
		int PENDIENTEDIGITALIZACION = 1;
		*/
		int DIGITALIZADO = 2;
	}
	
	public static interface UTipoEnvio {
		int PUBLICO = 1;
		int PRIVADO = 2;
	}
	
	public static interface UIndicadorSolicitud {
		int NO = 0;
		int SI = 1;
	}
	
	public static interface UIndicadorTipoReasignar {
		String EMISOR = "E";
		String RECEPTOR = "R";
	}
	
	public static interface UTipoFirma {
		int INDIVIUAL = 1;
		int MANCOMUNADO = 2;
	}
	
	public static interface URegla {
		int LIBERAR_NUMERO_MIN_FIRMANTE = 2;
		int LIBERAR_NUMERO_MIN_ADJUNTA = 1;
	}
	
	public static interface UMensajeOperacion {
		String MSJ_1 = "Los datos se registraron correctamente";
		String MSJ_2 = "Los cambios se realizaron correctamente";
		String MSJ_3 = "En este momento la solicictud se encuentra ocupado";
		String MSJ_4 = "Ocurrio un error inesperado porfavor comuniquese con el Área de Sistemas: ";
		String MSJ_5 = "No se puede realizar esta acción porque ya existe mas de una revisión";
		String MSJ_6 = "No se puede realizar esta acción porque ya se encuentra en proceso de evaluación";
		String MSJ_7 = "No se puede realizar esta accion por que el usuario no cuenta con un codigo de Area.";
		String MSJ_8 = "";
		String MSJ_9 = "";
		
		String MSJ_21 = "Se ha liberado correctamente la garantia";
	}
	
	public static interface UMensajeValidacion {
		String MSJ_1 = "Seleccione prioridad";
		String MSJ_2 = "Ingrese destino";
		String MSJ_3 = "Ingrese asunto";
		String MSJ_4 = "Ingrese mensaje";
		String MSJ_5 = "No se puede realizar esta accion por que el usuario no cuenta con un codigo de Area.";
		String MSJ_6 = "";
		String MSJ_7 = "Coberturado Correctamente";
		String MSJ_8 = "El Préstamo cubre el Total de Garantía";
		String MSJ_9 = "Saldo insuficiente para cubrir préstamo";
		String MSJ_10 = "Ingrese Préstamo y Valor Realización";
		String MSJ_11 = "No se permite liberación" +  "\n" + "Créditos relacionados activos:";
		String MSJ_12 = "No se permite liberación" +  "\n" + "Créditos por cancelar:";
		
	}
	
	public static interface UMensajeConfirmacion {
		String MSJ_1 = "¿Estas seguro de registrar la solicitud de levantamiento de Garantía?";
		String MSJ_2 = "¿Estas seguro de rechazar la solicitud de levantamiento de Garantía?";
		String MSJ_3 = "¿Estas seguro de confirmar la solicitud de levantamiento de Garantía?";
		String MSJ_4 = "¿Estas seguro de Liberar la Garantìa?";
		String MSJ_5 = "El cierre de solicitud contempla la entrega de documentos de levantamiento de garantìa al socio, ¿Estas seguro de continuar?";
		
		String MSJ_6 = "¿Está seguro de enviar el documento para la firma del socio?";
		String MSJ_7 = "¿Está seguro de enviar el documento con la firma del socio?";
		String MSJ_8 = "¿Está seguro de enviar el documento al notario";
		String MSJ_9 = "¿Está seguro de enviar la ficha de inscripcion";
		String MSJ_10 = "¿Estas seguro de realizar esta acción?";
		String MSJ_11 = "¿Está seguro de constituir la garantía?";
		
		String MSJ_12 = "¿Estas seguro de registrar la solicitud de carta Fianza?";
		String MSJ_13 = "¿Estas seguro de rechazar la solicitud de carta Fianza?";
		String MSJ_14 = "¿Estas seguro de confirmar la solicitud de carta Fianza?";
		String MSJ_15 = "¿Está seguro de enviar el documento de carta Fianza";
		String MSJ_16 = "¿Está seguro de enviar el documento con la firma del socio?";
	}
	
	/* Fin: AHM */
	
	public static interface UProcesoMantePostulante {
		int NUEVO = 0;
		int EDITAR = 1;
		int VER = 2;
	}

	public static interface UEstadoAutorizacion {
		int PENDIENTE = 1;
		int AUTORIZADO = 2;
		int RECHAZADO = 3;
	}

	public static interface UPais {
		String ESTADOS_UNIDOS = "0021";
		String PERU = "0001";
		int iPERU = 1;
	}

	public static interface UFormatoAbaco {
		int FORMATO_SOCIO_LINUX = 1;
		int FORMATO_SOCIO_WINDOWS = 2;
	}

	public static interface UDomicilioPostal {
		int CASA = 1;
		int TRABAJO = 2;
		int OTRA = 3;
	}

	public static interface IntranetGeneral {
		String FORMATO_TEXTO_PLANO = "text/plain; charset = UTF-8";
		String FORMATO_TEXTO_HTML = "text/html; charset = UTF-8";
	}

	public static interface UProceso {
		int NUEVO = 1;
		int EDITAR = 2;
	}

	public static interface UTipoUsuario {
		String POSTULANTE = "P";
		String SOCIO = "S";
	}

	public static interface UTamanioEmpresa {
		int GRANDE = 1;
		int MEDIANA = 2;
		int PEQUENA = 3;
		int MICRO = 4;
		int CORPORATIVA = 5;
	}

	public static interface UNaturalezaJuridica {
		int COD_ASOCIACION = 2;
		int COD_FUNDACION = 3;
		int COD_COMITE = 13;
		int COD_COOPERATIVA = 4;
	}

	public static interface UEstadoRespuesta {
		String OK = "200";
		String ERR_EJECUCION = "400";
		String ERR_SERVICIO = "500";
	};

	public static interface UDivision {
		String COMERCIAL = "M";
		String EMPRESAS = "E";
		String PERSONAS = "P";
		String REGIONAL = "R";
	}

	public static interface UUnidad {
		String CORPORATIVA = "J";
		String MEDIANA = "K";
		String MYPE = "M";
		String INSTITUCIONAL = "G";
		String FINANCIERAS = "Q";
		String CONSUMO = "E";
		String PRIVADA = "D";
		String PERSONAS = "C";
	}

	public static interface UStatusMB {
		String PASSED = "PASSED";
		String ERROR = "ERROR";
		String UPDATED = "UPDATED";
		String EXISTING = "EXISTING";
	}

	public static interface UTipoEmpleo {
		int DEPENDIENTE = 0;
		int INDEPENDIENTE = 1;
		int DEPEN_INDEP = 2;
		int NINGUNA = 3;
	}

	public static interface UGrupoEvaluacion {
		int SIN_COINCIDENCIA = 7;
		int VINO_TINTO = 1;
		int ROJO = 2;
		int NARANJA = 3;
		int AMARILLO1 = 4;
		int AMARILLO2 = 5;
		int VERDE = 6;
	}

	public static interface UEstadoProspecto {
		int PENDIENTE = 1;
		int EVALUACION = 2;
		int APROBADO = 3;
		int AUTORIZACION = 4;
		int RECHAZADO = 5;
		int APROBADO_NI = 6;
	}
	
	public static interface UTipoBusqueda {
		String CARPETA = "C";
		String ARCHIVO = "A";
	}
	
	public static interface UCreditos {
		int POLITICACREDITOS = 2;

	}
}
