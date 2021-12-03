/**********TEXTAREA: Codigo para Permitir un Maximo de Caracteres por Linea****/
function limitaCaracter(limite, linea, id){
	var obs = document.getElementById(id);
	var charlimit = limite;  //Cantidad de Caracteres por Linea
	var lines = obs.value.split('\n');
	for (var i = 0; i< lines.length; i++) {
		if (lines[i].length <= charlimit) continue;
		var j = 0; space = charlimit;
		while (j++ <= charlimit) {
			if (lines[i].charAt(j) === ' ') space = j;
		}
		lines[i + 1] = lines[i].substring(space + 1) + (lines[i + 1] || "");
		lines[i] = lines[i].substring(0, space);
	}
	obs.value = lines.slice(0, linea).join('\n');
}

//Función para limpiar campos Input Text Numericos
function limpiarCampoInputText(objetoCampo){
	if(objetoCampo!= null){
		for(var i = 0;i<objetoCampo.length;i++){
			if(objetoCampo[i].value != null){
				if (objetoCampo[i].value == 0 )  
				{   
					objetoCampo[i].value = "";
				} 
			}else{
				console.log("");
			}
			
	 	}
	}
	
	
}

function mostrarLoader(){
	var over = document.getElementById("overlay");
	var prel = document.getElementById("preload");
	over.classList.remove("ocultarLoad");
	prel.classList.remove("ocultarLoad");
}
function ocultarLoader(){
	var over = document.getElementById("overlay");
	var prel = document.getElementById("preload");
	over.classList.add("ocultarLoad");
	prel.classList.add("ocultarLoad");
}
function invocaControlarSesion(){
	controlarSesion();
}
function invocaListarDocumentoCargaMultiple(){
	listarDocumentoCargaMultiple();
};
function invocaListarDocumentoRevisionSimple(){
	listarDocumentoRevisionSimple();
};
function invocaListarSolicitud(){
	listarSolicitud();
}
function invocaVerificarSolicitudSesion(){
	verificarSolicitudSesion();
}
function invocaGenerarDocumento(){
	generarDocumento();
}
/*
function contadorTiempo(){
	var l = document.getElementById("tiempo");
    l.innerHTML = n;
    n++;
}
function invocaEnviarTiempo(){
	var tiempo = document.getElementById("tiempo").innerHTML;
	enviarTiempo([{name:'tiempo', value:tiempo}]);
}
function invocaObtenerTiempo(){
	var tiempo = document.getElementById("tiempo").innerHTML;
	obtenerTiempo([{name:'tiempo', value:tiempo}]);
}
*/

/*Permite salir cuando se usa el required en los campos del formulario maestro o dialog
  Asi tambien se envia el tiempo al darle click en salir despues del proceso exitoso*/
function invocaSalir(){
	var tiempo = document.getElementById("tiempo").innerHTML;
	salir([{name:'tiempo', value:tiempo}]);
}

function invocaGuardar(){
	//Permite guardar cuando se agrega el required en los campos
	//var over = document.getElementsByClassName("dlgPrueba")[0];
	//over.classList.add("ocultarLoad");
	//var tiempo = document.getElementById("tiempo").innerHTML;
	//guardar([{name:'tiempo', value:tiempo}]);
	guardar();
}