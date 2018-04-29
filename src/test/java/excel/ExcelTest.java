package excel;

import java.io.File;
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
	
}
