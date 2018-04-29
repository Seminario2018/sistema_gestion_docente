package excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
}
