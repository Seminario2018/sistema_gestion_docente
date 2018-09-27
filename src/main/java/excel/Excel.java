package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

// https://www.callicoder.com/java-write-excel-file-apache-poi/

/**
 * @author Martín Tomás Juran
 * @version 1.0, 29 de abr. de 2018
 */
public class Excel {

	public static List<List<String>> importar(File archivo) throws EncryptedDocumentException, InvalidFormatException, IOException {

		FileInputStream fis = new FileInputStream(archivo);
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(fis);

		 // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

		List<List<String>> data = new ArrayList<List<String>>();

		int i = 0;

		for (Row row : sheet) {
			// Create the row
			data.add(i, new ArrayList<String>());

			for (Cell cell : row) {
				// Get the cell data
				data.get(i).add(dataFormatter.formatCellValue(cell));
			}

			i++;
		}
		
		fis.close();

		return data;
	}

	public static boolean exportar(String path,
			String titulo,
			String subtitulo,
			List<String> encabezados,
			List<List<String>> grilla) {
		
		Workbook libro = new HSSFWorkbook();
		Sheet hoja = libro.createSheet("Hoja1");

		// Titulo
		hoja.addMergedRegion(new CellRangeAddress(0, 0, 0, encabezados.size() - 1));

		Font fuenteTitulo = libro.createFont();
		fuenteTitulo.setBold(true);
		fuenteTitulo.setFontHeightInPoints((short)14);
		fuenteTitulo.setColor(IndexedColors.WHITE.getIndex());
		
		CellStyle estiloTitulo = libro.createCellStyle();
		estiloTitulo.setAlignment(HorizontalAlignment.CENTER);
		estiloTitulo.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloTitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estiloTitulo.setFont(fuenteTitulo);
			
		
		Row filaTitulo = hoja.createRow(0);
		Cell celdaTitulo = filaTitulo.createCell(0); 
		celdaTitulo.setCellValue(titulo);
		celdaTitulo.setCellStyle(estiloTitulo);

		
		
		// Subtitulo
		hoja.addMergedRegion(new CellRangeAddress(1, 1, 0, encabezados.size() - 1));
		
		Font fuenteSubtitulo = libro.createFont();
		fuenteSubtitulo.setBold(true);
		fuenteSubtitulo.setFontHeightInPoints((short)12);
		fuenteSubtitulo.setColor(IndexedColors.WHITE.getIndex());
		
		CellStyle estiloSubtitulo = libro.createCellStyle();
		estiloSubtitulo.setAlignment(HorizontalAlignment.CENTER);
		estiloSubtitulo.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		estiloSubtitulo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estiloSubtitulo.setFont(fuenteSubtitulo);
		
		Row filaSubtitulo = hoja.createRow(1);
		Cell celdaSubtitulo = filaSubtitulo.createCell(0);
		celdaSubtitulo.setCellValue(subtitulo);
		celdaSubtitulo.setCellStyle(estiloSubtitulo);

		
		
		// Encabezados
		Font fuenteEncabezados = libro.createFont();
		fuenteEncabezados.setBold(true);
		CellStyle estiloEncabezados = libro.createCellStyle();
		estiloEncabezados.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		estiloEncabezados.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		estiloEncabezados.setFont(fuenteEncabezados);

		Row filaEncabezados = hoja.createRow(2);
		for (int i = 0; i < encabezados.size(); i++) {
		    Cell celdaEncabezado = filaEncabezados.createCell(i);
		    celdaEncabezado.setCellValue(encabezados.get(i));
		    celdaEncabezado.setCellStyle(estiloEncabezados);
		}

		// Grilla
		for (int j = 0; j < grilla.size(); j++) {
		    Row fila = hoja.createRow(j + 3);
		    for (int k = 0; k < grilla.get(j).size(); k++) {
		        fila.createCell(k).setCellValue(grilla.get(j).get(k));
		    }
		}
		
		for (int p = 0; p < encabezados.size(); p++) {
			hoja.autoSizeColumn(p);
		}
		
		
		try {
			FileOutputStream fos= new FileOutputStream(path);
		    libro.write(fos);
		    libro.close();
		    fos.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    return false;
		}

		return true;
	}

}
