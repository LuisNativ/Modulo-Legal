
/***********************************************************************
Autor  :	David Fernandez Levano								
JScript:	Libreria de JScripts 
************************************************************************/

function filterInput(filterType, evt, allowDecimal, allowCustom){
    var keyCode, Char, inputField, filter = '';
    var alpha = 'abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ';
    var num   = '0123456789';
    var space = ' ';
    if(window.event){
        keyCode = window.event.keyCode;
        evt = window.event;
    }else if (evt)keyCode = evt.which;
    else return true;
    if(filterType == 0) filter = alpha;
    else if(filterType == 1) filter = num;
    else if(filterType == 2) filter = alpha + num;
    else if(filterType == 3) filter = alpha + space;    
    else if(filterType == 4) filter = alpha + space + num;    
    if(allowCustom)filter += allowCustom;
    if(filter == '')return true;
    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;
    Char = String.fromCharCode(keyCode);
    if((filter.indexOf(Char) > -1)) return true;
    else if(filterType == 1 && allowDecimal && (Char == '.') && inputField.value.indexOf('.') == -1)return true;
    else if(filterType == 1 && allowDecimal && (Char == '-') && inputField.value.indexOf('-') == -1)return true;
    else return false;
}


function Apellidos(filterType, evt, allowDecimal, allowCustom){
    var keyCode, Char, inputField, filter = '';
    var alpha = 'abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ-\'';
    var num   = '0123456789';
    var space = ' '
    var especial = '.,#"$&@/()'	
    if(window.event){
        keyCode = window.event.keyCode;
        evt = window.event;
    }else if (evt)keyCode = evt.which;
    else return true;
    if(filterType == 0) filter = alpha;
    else if(filterType == 1) filter = num;
    else if(filterType == 2) filter = alpha + num;
    else if(filterType == 3) filter = alpha + space;    
    else if(filterType == 4) filter = alpha + space + num;
    else if(filterType == 5) filter = alpha + space + num + especial;
    if(allowCustom)filter += allowCustom;
    if(filter == '')return true;
    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;
    Char = String.fromCharCode(keyCode);
    if((filter.indexOf(Char) > -1)) return true;
    else if(filterType == 1 && allowDecimal && (Char == '.') && inputField.value.indexOf('.') == -1)return true;
    else if(filterType == 1 && allowDecimal && (Char == '-') && inputField.value.indexOf('-') == -1)return true;
    else return false;
}

function filterInputGrupo(filterType, evt, allowDecimal, allowCustom) {
    var keyCode, Char, inputField, filter = '';
    var alpha = 'abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ';
    var num = '0123456789';
    if (window.event) {
        keyCode = window.event.keyCode;
        evt = window.event;
    } else if (evt) keyCode = evt.which;
    else return true;
    if (filterType == 0) filter = alpha;
    else if (filterType == 1) filter = num;
    else if (filterType == 2) filter = alpha + num;
    if (allowCustom) filter += allowCustom;
    if (filter == '') return true;
    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
    if ((keyCode == null) || (keyCode == 0) || (keyCode == 8) || (keyCode == 9) || (keyCode == 13) || (keyCode == 27)) return true;
    Char = String.fromCharCode(keyCode);
    if ((filter.indexOf(Char) > -1)) return true;
    else if (filterType == 1 && allowDecimal && (Char == '.') && inputField.value.indexOf('.') == -1) return true;
    else return false;
}      
function filterEnteros(evt){
    var keyCode, Char, inputField, filter = '';
    var num   = '0123456789';
    if(window.event){
        keyCode = window.event.keyCode;
        evt = window.event;
    }else if (evt)keyCode = evt.which;
    else return true;
    filter = num;
    inputField = evt.srcElement ? evt.srcElement : evt.target || evt.currentTarget;
    if((keyCode==null) || (keyCode==0) || (keyCode==8) || (keyCode==9) || (keyCode==13) || (keyCode==27) )return true;
    Char = String.fromCharCode(keyCode);
    if((filter.indexOf(Char) > -1)) return true;
    else return false;
}

function ValidaInputTextMessageAndLenMessage(e) {
    var valid = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ 0123456789()/\&_-:.,[]{}#';        
    var keyAscii = (document.all) ? event.keyCode : e.which;

    if (keyAscii != 13) {
        var key = String.fromCharCode(keyAscii);
        var keyAj = key.toUpperCase();
        if (valid == '') {
            return true;
        }
        if (valid.indexOf(keyAj) == "-1") {
            if (keyAscii != 8 && keyAscii != 0) { return false; };
            return true;
        }
        return true;
    }
}
function ValidaInputTextHora(e) {
    var valid = '0123456789:';
    var keyAscii = (document.all) ? event.keyCode : e.which;
    if (keyAscii != 13) {
        var key = String.fromCharCode(keyAscii);
        var keyAj = key.toUpperCase();
        if (valid == '') {
            return true;
        }
        if (valid.indexOf(keyAj) == "-1") {
            if (keyAscii != 8 && keyAscii != 0) { return false; };
            return true;
        }
        return true;
    }
}
function ValidaInputTextSoloLetras(e) {
    var valid = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var keyAscii = (document.all) ? event.keyCode : e.which;
    if (keyAscii != 13) {
        var key = String.fromCharCode(keyAscii);
        var keyAj = key.toUpperCase();
        if (valid == '') {
            return true;
        }
        if (valid.indexOf(keyAj) == "-1") {
            if (keyAscii != 8 && keyAscii != 0) { return false; };
            return true;
        }
        return true;
    }
}

function ValidaInputTextUpperCase(e) {
    var valid = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var keyAscii = (document.all) ? event.keyCode : e.which;
    if (keyAscii != 13) {
        var key = String.fromCharCode(keyAscii);
        var keyAj = key.toUpperCase();
        if (valid == '') {
            return true;
        }
        if (valid.indexOf(keyAj) > -1) {
            //if (keyAscii != 8 && keyAscii != 0) { return false; };
            return true;
        }
        return false;
    }
}


function onBlurMonto(field, decimal) {
	var cadena = field.value;
	var cadenaAux ='';
	var parteDecimal = "";
	var parteEntera = "";
	var posicion;
	var i=0;
	// Borro todas la comas
	for(i=0; i < cadena.length; i++ ){
		if(cadena.charAt(i)!=','){
			cadenaAux = cadenaAux + cadena.charAt(i);
		}		
	}
	field.value = cadenaAux;
	posicion = cadena.indexOf(".");
	// / validando solo un punto decimal
	if (posicion != -1) {
		cadena = cadena.substring(posicion + 1);
		posicion = cadena.indexOf(".");
		if (posicion != -1) {
			alert("por favor ingrese solo un punto decimal");
			field.focus();
			return;
		}

	}
	// validando la parte decimal
	cadena = field.value;
	posicion = cadena.indexOf(".");
	if (posicion != -1) {
		parteDecimal = cadena.substring(posicion + 1);
		if (parteDecimal.length > decimal) {			
			field.value = cadena.substring(0,posicion + + 1 + decimal);			
		}
	}
	// encontrando la parte decimal.
	var numDecinal = "00";
	switch (decimal) {
	case 2:
		numDecinal = "00";
		break;
	case 3:
		numDecinal = "000";
		break;
	case 4:
		numDecinal = "0000";
		break;
	}
	cadena = field.value;
	posicion = cadena.indexOf(".");
	parteDecimal = "";
	if (posicion != -1) {
		parteDecimal = cadena.substring(posicion + 1);
	}
	parteDecimal = parteDecimal + numDecinal;
	parteDecimal = parteDecimal.substring(0, decimal);
	// encontrando la parte entera
	cadena = field.value;
	posicion = cadena.indexOf(".");
	var fEntera;
	if (posicion != -1)
		cadena = cadena.substring(0, posicion);
	fEntera = new Number(cadena);
	cadena = fEntera.toString();
	// Dando formato
	var aCaracter = cadena.split("");
	var longitud = aCaracter.length;
	parteEntera = cadena;	
	if (longitud > 3) {
		parteEntera = "";
		while (longitud > 3) {
			parteEntera = "," + aCaracter[longitud - 3]
					+ aCaracter[longitud - 2] + aCaracter[longitud - 1]
					+ parteEntera;
			longitud = longitud - 3;
		}
		if (longitud == 1)
			parteEntera = aCaracter[0] + parteEntera;

		if (longitud == 2)
			parteEntera = aCaracter[0] + aCaracter[1] + parteEntera;

		if (longitud == 3)
			parteEntera = aCaracter[0] + aCaracter[1] + aCaracter[2]
					+ parteEntera;
	}
	field.value = parteEntera + "." + parteDecimal;
}

function onKeyPressFecha(field) {
	var key = (document.all) ? event.keyCode : event.which;
	
	if (key > 47 && key < 58) {
		if (field.value.length === 2) {
			field.value = field.value + "/";
		}
		if (field.value.length == 5)
			field.value = field.value + "/";
		if (field.value.length > 9)
			event.returnValue = false;
	} else {
		event.returnValue = false;
	}
}
