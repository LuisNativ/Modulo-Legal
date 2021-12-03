package com.abaco.entidad;

public class ECriterio {
private int id;
private String nombre;
private String observacion;
private String nombreEstado;
private int peso;
private int puntos;
private int pesoPonderado;
private int estado;
private String usuario;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getObservacion() {
	return observacion;
}
public void setObservacion(String observacion) {
	this.observacion = observacion;
}
public int getPeso() {
	return peso;
}
public void setPeso(int peso) {
	this.peso = peso;
}
public int getPuntos() {
	return puntos;
}
public void setPuntos(int puntos) {
	this.puntos = puntos;
}
public int getPesoPonderado() {
	return pesoPonderado;
}
public void setPesoPonderado(int pesoPonderado) {
	this.pesoPonderado = pesoPonderado;
}
public int getEstado() {
	return estado;
}
public void setEstado(int estado) {
	this.estado = estado;
}
public String getNombreEstado() {
	return nombreEstado;
}
public void setNombreEstado(String nombreEstado) {
	this.nombreEstado = nombreEstado;
}
public String getUsuario() {
	return usuario;
}
public void setUsuario(String usuario) {
	this.usuario = usuario;
}

}
