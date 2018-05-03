package persistencia;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public class PersistibleTest {
	
	@Test
	public void pruebaWhere() {
		
		List<String> filtro = new ArrayList<String>();
		filtro.add("> 140000");
		
		List<ColumnaInforme> columnas = new ArrayList<ColumnaInforme>();
		
		ColumnaInforme legajo = new ColumnaInforme(
				true,
				"Legajo",
				"DOCENTE.LEGAJO",
				filtro,
				null,
				ColumnaInforme.ASCENDENTE,
				1);
		columnas.add(legajo);
		
		ColumnaInforme apynom = new ColumnaInforme(
				true,
				"Apellido y nombre",
				"DOCENTE.APYNOM",
				null,
				null,
				ColumnaInforme.DESCENDENTE,
				2);
		columnas.add(apynom);
		
		ColumnaInforme sumcosto = new ColumnaInforme(
				true,
				"Último costo",
				"PLANTA.ULTIMO_COSTO",
				null,
				"SUM",
				ColumnaInforme.SIN_ORDEN,
				3);
		columnas.add(sumcosto);
		
		TipoInforme impactoDocente = new TipoInforme(
				0,
				"Último costo docente",
				"Últimos costos de docentes con legajo mayor a 140000",
				false,
				columnas,
				"DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO",
				"DOCENTE.LEGAJO");
		
		impactoDocente.select();
	}
}
