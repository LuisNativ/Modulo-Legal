package com.abaco.entidad;

public class EArchivo {

	private String Name;
	private String mime;
	private long length;
	private byte[] data;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
		int extDot = name.lastIndexOf('.');
		if (extDot > 0) {
			String extension = name.substring(extDot + 1);
			if ("bmp".equals(extension)) {
				mime = "image/bmp";
			} else if ("jpg".equals(extension)) {
				mime = "image/jpeg";
			} else if ("gif".equals(extension)) {
				mime = "image/gif";
			} else if ("png".equals(extension)) {
				mime = "image/png";
			} else if ("pdf".equals(extension)) {
				mime = "application/pdf";
			} else if ("xls".equals(extension)) {
				mime = "application/vnd.ms-excel";
			} else if ("xlsx".equals(extension)) {
				mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			} else if ("doc".equals(extension)) {
				mime = "application/msword";
			} else if ("docx".equals(extension)) {
				mime = "application/application/vnd.openxmlformats-officedocument.wordprocessingml.document";	
			} else {
				mime = "image/unknown";
			}
		}
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public String getMime() {
		return mime;
	}
}
