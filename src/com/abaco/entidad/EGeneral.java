package com.abaco.entidad;

public class EGeneral {

	protected String codigo;
	protected int codigo2;
	protected long codigo3;
	protected String nombreCorto;	
	protected String descripcion;

	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public int getCodigo2() {
		return codigo2;
	}
	
	public void setCodigo2(int codigo2) {
		this.codigo2 = codigo2;
	}
	
	public long getCodigo3() {
		return codigo3;
	}
	
	public void setCodigo3(long codigo3) {
		this.codigo3 = codigo3;
	}
	
	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "EGeneral [codigo=" + codigo + ", nombreCorto=" + nombreCorto + ", descripcion=" + descripcion
				+ ", codigo2=" + codigo2 
				+ ", codigo3=" + codigo3
				+ "]";
	}
}
