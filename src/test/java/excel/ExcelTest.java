package excel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 29 de abr. de 2018
 */
public class ExcelTest {
	
	@Test
	public void importar1() {
		
		File costeoFebrero = new File("src/test/resources/costeo_febrero.xls");
		
		String path = costeoFebrero.getAbsolutePath();
		
		List<List<String>> grilla = Excel.importar(path);
		
		for (List<String> row : grilla) {
			for (String cell : row) {
				System.out.print(cell + "\t|\t");
			}
			System.out.println();
		}
	}
	
	@Test
	public void exportar1() {
		
		int qFilas = 10;
		int qColumnas = 2;
		
		String path = "src/test/resources/test1-exportar-excel.xls";
		
		List<String> encabezados = new ArrayList<String>();
		
		for (int j = 1; j <= qColumnas; j++) {
			encabezados.add("Columna " + j);
		}
		
		List<List<String>> grilla = new ArrayList<List<String>>();
		
		for (int i = 1; i <= qFilas; i++) {
			List<String> fila = new ArrayList<String>();
			for (int j = 1; j <= qColumnas; j++) {
				fila.add("Dato " + i + "-" + j);
			}
			grilla.add(fila);
		}
		
		Excel.exportar(path, encabezados, grilla);
		
	}
	
}
