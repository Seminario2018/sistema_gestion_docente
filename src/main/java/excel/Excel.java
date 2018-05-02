package excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

// https://www.callicoder.com/java-write-excel-file-apache-poi/

/**
 * @author Martín Tomás Juran
 * @version 1.0, 29 de abr. de 2018
 */
public class Excel {

	public static List<List<String>> importar(String path) {

		try {
			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(new File(path));

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

			return data;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean exportar(String path, List<String> encabezados, List<List<String>> grilla) {
		Workbook libro = new HSSFWorkbook();
		Sheet hoja = libro.createSheet("Hoja1");

		// Encabezados
		// TODO agregar estilo a los encabezados (para eso es la diferenciación)
		Row filaEncabezados = hoja.createRow(0);
		for (int i = 0; i < encabezados.size(); i++) {
		    filaEncabezados.createCell(i).setCellValue(encabezados.get(i));
		}

		// Grilla
		for (int j = 0; j < grilla.size(); j++) {
		    Row fila = hoja.createRow(j + 1);
		    for (int k = 0; k < grilla.get(j).size(); k++) {
		        fila.createCell(k).setCellValue(grilla.get(j).get(k));
		    }
		}

		try {
		    libro.write(new FileOutputStream(path));
		    libro.close();
		} catch (IOException e) {
		    e.printStackTrace();
		    return false;
		}

		return true;
	}

}
