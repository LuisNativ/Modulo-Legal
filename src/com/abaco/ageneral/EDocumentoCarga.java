package com.abaco.ageneral;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EDocumentoCarga {
	private int correlativo;
	private String codigoLaserFiche;
	private String nombre;
	private String nombreLaserFiche;
	private String nombreOriginal;
	private byte[] data;
	private long size;
	private String descripcionSize;
}
