package com.abaco.negocio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;

import com.abaco.ageneral.EOperacionDocumentoRevision;
import com.abaco.ageneral.ERepresentanteLegal;
import com.abaco.ageneral.ESuscripcion;

public class UtilPoi {

	public static void crearTituloCell(HSSFWorkbook wb, Row row, int column, HorizontalAlignment halign,
			VerticalAlignment valign, String strContenido, int iTamanioTitulo) {
		CreationHelper ch = wb.getCreationHelper();
		// Cell cell = row.createCell(column);
		Cell cell = row.createCell(column);
		cell.setCellValue(ch.createRichTextString(strContenido));

		HSSFFont cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) iTamanioTitulo);
		cellFont.setFontName(HSSFFont.FONT_ARIAL);
		cellFont.setUnderline((byte) 1);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cellStyle.setFont(cellFont);
		cellStyle.setWrapText(true);
		cell.setCellStyle(cellStyle);
	}

	public static void crearCelda(Sheet sheet, int iRow, int iCol, Object objContenido, CellStyle csDescrip) {
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.getCell(iCol);

		if (cell == null) {
			cell = row.createCell(iCol);
			sheet.autoSizeColumn(cell.getColumnIndex());
		}

		if (objContenido instanceof String) {
			cell.setCellValue((String) objContenido);
		} else if (objContenido instanceof Double) {
			cell.setCellValue((Double) objContenido);
		} else if (objContenido instanceof Integer) {
			cell.setCellValue((Integer) objContenido);
		} else if (objContenido instanceof Date) {
			cell.setCellValue((Date) objContenido);
		}
		if (csDescrip != null) {
			cell.setCellStyle(csDescrip);
		}
	}

	public static void crearCeldaFormula(Sheet sheet, int iRow, int iCol, String strFormula) {
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.createCell(iCol);
		// cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		cell.setCellFormula(strFormula);
	}

	public static Double obtenerValorCelda(Sheet sheet, int iRow, int iCol) {
		Double valor = new Double(0);
		Row row = sheet.getRow(iRow);
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(iCol);
			if (cell != null) {
				if (cell.getCellType() == CellType.NUMERIC) {
					valor = cell.getNumericCellValue();
				}
			}
		}
		return valor;
	}

	public static String obtenerValorCeldaString(Sheet sheet, int iRow, int iCol) {
		String valor = null;
		Row row = sheet.getRow(iRow);
		Cell cell = null;
		if (row != null) {
			cell = row.getCell(iCol);
			if (cell != null) {
				if (cell.getCellType() == CellType.NUMERIC) {
					valor = cell.getStringCellValue();
				}
			}
		}
		return valor;
	}

	public static XSSFWorkbook obtenerArchivoExcel(String fileName) {
		XSSFWorkbook libro = null;
		try {
			libro = new XSSFWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return libro;
	}

	public static HSSFWorkbook obtenerArchivoExcel2(String fileName) {
		HSSFWorkbook libro = null;
		try {
			libro = new HSSFWorkbook(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return libro;
	}
	
	public static CellStyle obtenerEstilo(Sheet sheet, int iRow, int iCol) {
		CellStyle cellStyle = null;
		try {
			Cell cell = sheet.getRow(iRow).getCell(iCol);
			cellStyle = cell.getCellStyle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cellStyle;
	}

	public static void crearCeldaConEstilo(Sheet sheet, int iRow, int iCol, Object objContenido, CellStyle cellStyle) {
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row == null)
			row = sheet.createRow(iRow);
		cell = row.createCell(iCol);
		if (objContenido instanceof String) {
			cell.setCellValue((String) objContenido);
		} else if (objContenido instanceof Double) {
			cell.setCellValue((Double) objContenido);
		}
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}

	}

	public static void crearCeldaCombinada(Sheet sheet, int iRow, int iRow2, int iCol, int iCol2, Object objContenido,
			CellStyle csDescrip) {
		for (int i = iRow; i <= iRow2; i++) {
			for (int j = iCol; j <= iCol2; j++) {
				UtilPoi.crearCelda(sheet, i, j, "", csDescrip);
			}
		}
		UtilPoi.crearCelda(sheet, iRow, iCol, objContenido, csDescrip);
		sheet.addMergedRegion(new CellRangeAddress(iRow, iRow2, iCol, iCol2));
	}

	public static void evaluarCelda(Sheet sheet, int iRow, int iCol) {
		FormulaEvaluator evaluador = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
		Cell cell = null;
		Row row = sheet.getRow(iRow);
		if (row != null) {
			cell = row.getCell(iCol);
			if (cell != null) {
				evaluador.evaluateFormulaCell(cell);
			}
		}
	}

	public static String generarArchivo(XSSFWorkbook wb, String nombreArchivo) {
		String rutaArchivo = "";
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyy");
		StringBuilder sbNombre = new StringBuilder();

		sbNombre.append(nombreArchivo).append("_").append(sufijo).append(".xlsx");
		File strRuta = new File(sbNombre.toString());
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}

	public static String generarArchivo2(HSSFWorkbook wb, String nombreArchivo) {
		String rutaArchivo = "";
		StringBuilder sbNombre = new StringBuilder();
		sbNombre.append(nombreArchivo);
		File strRuta = new File(sbNombre.toString());
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}
	
	public static void eliminarFila(Sheet sheet, int iRow) {
		Row row = sheet.getRow(iRow);
		if (row != null) {
			sheet.removeRow(row);
		}
	}

	public static XWPFDocument obtenerArchivoWord(String fileName) {
		XWPFDocument docWord = null;
		try {
			docWord = new XWPFDocument(new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return docWord;
	}

	/**
	 * Ubica un marcador y lo reemplaza por texto
	 * 
	 * @param docx
	 *            Documento a buscar
	 * @param buscar
	 *            Marca a buscar
	 * @param reemplazo
	 *            Texto que reemplazo
	 * @return Numero de parrafo
	 */
	public static void reemplazarPalabraenParrafo(XWPFDocument docx, String buscar, String reemplazo) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						for (int k = runs.size() - 1; k > 0; k--) {
							parrafos.get(i).removeRun(k);
						}
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						XWPFRun run = runs.get(0);
						run.setText(cadena, 0);
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	public static void reemplazarPalabraenParrafoBold(XWPFDocument docx, String buscar, String reemplazo,boolean bold) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					if(runs.get(j).toString().contains(buscar)){
						String textoReemplazado = "";
						String[] cad = runs.get(j).toString().split(" ");
						for(int m=0;m<cad.length;m++){
							if(cad[m].trim().equals(buscar)){
								textoReemplazado = textoReemplazado +" " + reemplazo;
							}else{
								textoReemplazado = textoReemplazado +" " + cad[m];
							}
							
						}
						runs.get(j).setText(textoReemplazado,0);
						runs.get(j).setBold(bold);
					}
					//cadena = cadena + runs.get(j);
					
				}
				
				/*
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						for (int k = runs.size() - 1; k > 0; k--) {
							parrafos.get(i).removeRun(k);
						}
						
						String cad[]= cadena.split(" ");
                        for(int m=0;m<cad.length;m++){
							if(cad[m].equals(buscar)){
								cadena = cad[m];
							}
						}
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						
						XWPFRun run = runs.get(0);
						run.setText(cadena, 0);
						run.setBold(bold);
					}
				}*/
			}
		} catch (Exception e) {
		}
	}

	public static void reemplazarPalabraenParrafoSinBorrado(XWPFDocument docx, String buscar, String reemplazo) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						// for (int k = runs.size()-1; k > 0; k--) {
						// parrafos.get(i).removeRun(k);
						// }
						// obtengo el valor a reemplazar
						cadena = cadena.replace(buscar, reemplazo);
						XWPFRun run = runs.get(0);
						run.setText(cadena, 0);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Ubica un marcador y lo reemplaza por texto
	 * 
	 * @param docx
	 *            Documento a buscar
	 * @param buscar
	 *            Marca a buscar
	 * @param reemplazo
	 *            Texto que reemplazo
	 * @return Numero de parrafo
	 */
	public static void reemplazarPalabraenTabla(XWPFDocument docx, String buscar, String reemplazo) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						XWPFParagraph xwpfParagraph = xwpfTableCell.getParagraphArray(0);

						List<XWPFRun> runs = xwpfParagraph.getRuns();
						String cadena = "";
						for (int i = 0; i < runs.size(); i++) {
							cadena = cadena + runs.get(i);
						}

						if (!cadena.equals("")) {
							// busca en la cadena si hay algun campo que
							// reemplazar
							if (cadena.contains(buscar)) {
								// quito la marca
								int tamanoParrafo = runs.size();
								for (int k = 0; k < tamanoParrafo; k++) {
									xwpfParagraph.removeRun(k);
								}
								// obtengo el valor a reemplazar
								cadena = cadena.replace(buscar, reemplazo);
								XWPFRun run = runs.get(0);
								run.setText(cadena, 0);
							}
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}
	
	
	public static void reemplazarPalabraenTabla2(XWPFDocument docx, String buscar, String reemplazo,boolean bold) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						for (XWPFParagraph p : xwpfTableCell.getParagraphs()) {
						      for (XWPFRun r : p.getRuns()) {
							       String text = r.getText(0);						       
							       if (!text.equals("")) {
							    	   if (text.contains(buscar)) {
							    		   // p.removeRun(0);		    		   
									        text = text.replace(buscar, reemplazo);
									        r.setText(text,0);
									        r.setBold(bold);
									       
									   } 
							       }
						       
						      }
						}
					}
				}
			}
		} catch (Exception e) {

		}
	}
	

	public static String generarArchivoWord(XWPFDocument docx, String rutaLinuxWord) {
		String rutaArchivo = "";
		File strRuta = new File(rutaLinuxWord);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(strRuta);
			docx.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		} catch (IOException e) {
			UManejadorLog.log("Error al crear archivo : " + e.getMessage());
		}
		rutaArchivo = strRuta.getAbsolutePath();
		UManejadorLog.log("Ruta de archivo generado: " + rutaArchivo);
		return rutaArchivo;

	}

	public static XWPFTable crearTabla(XWPFDocument docx, String buscar) {

		XWPFTable tablaCreada = null;

		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
											
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
					}
				}
			}
		} catch (Exception e) {
		}

		return tablaCreada;
	}
	
	public static XWPFTable crearTablaSuscripcion(XWPFDocument docx, String buscar, List<ESuscripcion> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNumeroSuscripcion()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreLargo(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNumeroSuscripcion()+"", false);
							paragraph = tableRowOne.getCell(2).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreLargo(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaRepresentanteLegal(XWPFDocument docx, String buscar, List<ERepresentanteLegal> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoOrden()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCargoLaboral(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getDocumento(), false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreLargo(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoOrden()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCargoLaboral()!= null ? lista.get(e).getCargoLaboral(): lista.get(e).getCargoLaboral().equals("") ? "-":"-", false);
							paragraph = tableRowOne.getCell(2).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getDocumento(), false);
							paragraph = tableRowOne.getCell(3).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreLargo(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaDocumentosRevisados(XWPFDocument docx, String buscar, List<EOperacionDocumentoRevision> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoDocumento()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreDocumento(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoDocumento()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreDocumento(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFTable crearTablaDocumentosFaltantes(XWPFDocument docx, String buscar, List<EOperacionDocumentoRevision> lista) {
		XWPFTable tablaCreada = null;
		
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}
	
				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// quito la marca
						deleteParagraph(parrafos.get(i));
						// obtengo el valor a reemplazar
	
						XmlCursor cursor = parrafos.get(i).getCTP().newCursor();
						tablaCreada = docx.insertNewTbl(cursor);
						
						XWPFTableRow tableRowOne = tablaCreada.getRow(0);
						XWPFParagraph paragraph = tableRowOne.getCell(0).getParagraphArray(0);
						
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getCodigoDocumento()+"", false);
						paragraph = tableRowOne.addNewTableCell().getParagraphArray(0);
						setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(0).getNombreDocumento(), false);
						
						for (int e = 1; e < lista.size(); e++) {
							tableRowOne = tablaCreada.createRow();
							
							paragraph = tableRowOne.getCell(0).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getCodigoDocumento()+"", false);
							paragraph = tableRowOne.getCell(1).getParagraphArray(0);
							setRun(paragraph.createRun(), "Arial Narrow", 11, lista.get(e).getNombreDocumento(), false);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	
		return tablaCreada;
	}
	
	public static XWPFRun setRun(XWPFRun run, String fontFamily, int fontSize, String text, boolean bold) {
		run.setFontFamily(fontFamily);
		run.setFontSize(fontSize);
		run.setText(text);
		run.setBold(bold);
		return run;
	}

	public static void eliminarTabla(XWPFDocument docx, String buscar) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			boolean existe = false;
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						XWPFParagraph xwpfParagraph = xwpfTableCell.getParagraphArray(0);

						List<XWPFRun> runs = xwpfParagraph.getRuns();
						String cadena = "";
						for (int i = 0; i < runs.size(); i++) {
							cadena = cadena + runs.get(i);
						}

						if (UFuncionesGenerales.revisaCadena(cadena).contains(buscar)) {
							existe = true;
							break;
						}
					}
					if (existe) {
						break;
					}
				}
				if (existe) {
					while (xwpfTable.getNumberOfRows() > 0) {
						xwpfTable.removeRow(0);

					}
				}
				existe = false;
			}
		} catch (Exception e) {
		}
	}

	public static void eliminarFilaTabla(XWPFDocument docx, String buscar) {
		try {
			// ubica en que parrafo se encuentra una etiqueta que buscas
			boolean existe = false;
			List<XWPFTable> tablas = docx.getTables();
			for (XWPFTable xwpfTable : tablas) {
				int row = -1;
				for (XWPFTableRow xwpfTableRow : xwpfTable.getRows()) {
					row += 1;
					for (XWPFTableCell xwpfTableCell : xwpfTableRow.getTableCells()) {
						XWPFParagraph xwpfParagraph = xwpfTableCell.getParagraphArray(0);

						List<XWPFRun> runs = xwpfParagraph.getRuns();
						String cadena = "";
						for (int i = 0; i < runs.size(); i++) {
							cadena = cadena + runs.get(i);
						}

						if (UFuncionesGenerales.revisaCadena(cadena).contains(buscar)) {
							existe = true;
							break;
						}
					}
					if (existe) {
						break;
					}
				}
				if (existe) {
					xwpfTable.removeRow(row);
					break;
				}
				existe = false;
			}
		} catch (Exception e) {
		}
	}

	public static void saltoPaginaParrafo(XWPFDocument docx, String buscar) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						// obtengo el valor a reemplazar
						XWPFRun run = runs.get(0);
						run.setText("   ", 0);
						run.addBreak(BreakType.PAGE);
						// quito la marca
						// int tamanoParrafo = runs.size();
						// for (int k = 0; k < tamanoParrafo; k++) {
						// parrafos.get(i).removeRun(k);
						// }
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public static void borroParrafo(XWPFDocument docx, String buscar) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		try {
			List<XWPFParagraph> parrafos = docx.getParagraphs();
			for (int i = 0; i < parrafos.size(); i++) {
				List<XWPFRun> runs = parrafos.get(i).getRuns();
				String cadena = "";
				for (int j = 0; j < runs.size(); j++) {
					cadena = cadena + runs.get(j);
				}

				if (!cadena.equals("")) {
					// busca en la cadena si hay algun campo que reemplazar
					if (cadena.contains(buscar)) {
						deleteParagraph(parrafos.get(i));

					}
				}
			}
		} catch (Exception e) {
		}
	}

	public static void deleteParagraph(XWPFParagraph p) {
		XWPFDocument doc = p.getDocument();
		int pPos = doc.getPosOfParagraph(p);
		// doc.getDocument().getBody().removeP(pPos);
		doc.removeBodyElement(pPos);
	}

	public static int copiarParrafoWordIniWordFin(XWPFDocument docPartida, XWPFDocument docDestino, int posicion) {

		for (IBodyElement e : docPartida.getBodyElements()) {
			try {
				if (e instanceof XWPFParagraph) {
					XWPFParagraph p = (XWPFParagraph) e;
					if (p.getCTP().getPPr() != null && p.getCTP().getPPr().getSectPr() != null) {
						continue;
					} else {
						docDestino.createParagraph();
						docDestino.setParagraph(p, posicion);
						posicion++;
					}
				} else if (e instanceof XWPFTable) {
					XWPFTable t = (XWPFTable) e;
					docDestino.createTable();
					docDestino.setTable(posicion, t);
					posicion++;
				}
			} catch (Exception ex) {
			}
		}

		return posicion;
	}

	public void passaStili(XWPFDocument docPartida, XWPFDocument docDestino) {
		try {
			CTStyles c1 = docPartida.getStyle();
			docDestino.createStyles().setStyles(c1);
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int ubica1PosParrafo(XWPFDocument docx, String buscar) {
		// ubica en que parrafo se encuentra una etiqueta que buscas
		int retorno = 0;
		List<XWPFParagraph> parrafos = docx.getParagraphs();
		for (int i = 0; i < parrafos.size(); i++) {
			List<XWPFRun> runs = parrafos.get(i).getRuns();
			String cadena = "";
			for (int j = 0; j < runs.size(); j++) {
				cadena = cadena + runs.get(j);
			}

			if (!cadena.equals("")) {
				// busca en la cadena si hay algun campo que reemplazar
				if (cadena.contains(buscar)) {
					retorno = i;
					break;
				}
			}
		}
		return retorno;
	}

	public static short getHColour( HSSFWorkbook workbook, String colour ) {
		String[] rgb =  colour.split(",");
		
		byte rgb1 = (byte) Short.parseShort(rgb[0]);
		byte rgb2 = (byte) Short.parseShort(rgb[1]);
		byte rgb3 = (byte) Short.parseShort(rgb[2]);
		
		byte[] rgbByte = new byte[] { rgb1, rgb2, rgb3 };
		HSSFPalette palette = workbook.getCustomPalette();
		
		HSSFColor result = palette.findColor(rgbByte[0], rgbByte[1], rgbByte[2]);
		if( result == null) {
			 palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.LAVENDER.getIndex(), rgb1 , rgb2, rgb3);
			 result = palette.getColor(HSSFColor.HSSFColorPredefined.LAVENDER.getIndex() );
		}
		return result.getIndex();
	}
}
