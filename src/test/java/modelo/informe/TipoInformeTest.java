package modelo.informe;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import modelo.informe.ColumnaInforme;
import modelo.informe.TipoInforme;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 25 de abr. de 2018
 */
public class TipoInformeTest {
	
	@Test
	public void armarConsulta1() {
		/*
		SELECT DOCENTE.LEGAJO, DOCENTE.APYNOM, SUM(PLANTA.ULTIMO_COSTO)
		FROM DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO
		WHERE DOCENTE.LEGAJO > 140000
		GROUP BY DOCENTE.LEGAJO
		ORDER BY DOCENTE.LEGAJO ASC, DOCENTE.APYNOM DESC
		*/
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
		
		String consulta = "SELECT DOCENTE.LEGAJO, DOCENTE.APYNOM, SUM(PLANTA.ULTIMO_COSTO)\r\n"
				+ "FROM DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO\r\n" 
				+ "WHERE DOCENTE.LEGAJO > 140000\r\n"
				+ "GROUP BY DOCENTE.LEGAJO\r\n"
				+ "ORDER BY DOCENTE.LEGAJO ASC, DOCENTE.APYNOM DESC";
		
		Assert.assertEquals(consulta, impactoDocente.armarConsulta());
	}

}
