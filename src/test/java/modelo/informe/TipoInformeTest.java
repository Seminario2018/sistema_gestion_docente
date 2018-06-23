package modelo.informe;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import modelo.auxiliares.Calculo;
import modelo.informe.ColumnaInforme;
import modelo.informe.ColumnaInforme.TipoColumna;
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
		FiltroColumna filtro = new FiltroColumna("> 140000");
		
		List<ColumnaInforme> columnas = new ArrayList<ColumnaInforme>();
		
		ColumnaInforme legajo = new ColumnaInforme(
				true,
				"Legajo",
				"DOCENTE.LEGAJO",
				filtro,
				null,
				ColumnaInforme.ASCENDENTE,
				1,
				TipoColumna.INTEGER);
		columnas.add(legajo);
		
		ColumnaInforme apynom = new ColumnaInforme(
				true,
				"Apellido y nombre",
				"DOCENTE.APYNOM",
				null,
				null,
				ColumnaInforme.DESCENDENTE,
				2,
				TipoColumna.STRING);
		columnas.add(apynom);
		
		ColumnaInforme sumcosto = new ColumnaInforme(
				true,
				"Último costo",
				"PLANTA.ULTIMO_COSTO",
				null,
				Calculo.SUM,
				ColumnaInforme.SIN_ORDEN,
				3,
				TipoColumna.FLOAT);
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
	
	/*
	SELECT DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE, COUNT(CARGOSDOCENTES.CODIGO), SUM(CARGOSDOCENTES.ULTIMOCOSTO), SUM(CARGOS.CARGAHORARIA), COUNT(INTEGRANTES.ID), SUM(INTEGRANTES.HORASSEMANALES)
	FROM DOCENTES
	INNER JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO
	LEFT JOIN CARGOSDOCENTES ON DOCENTES.LEGAJO = CARGOSDOCENTES.LEGAJO
	INNER JOIN CARGOS ON CARGOSDOCENTES.CARGO = CARGOS.CODIGO
	LEFT JOIN INTEGRANTES ON CARGOSDOCENTES.CODIGO = INTEGRANTES.CARGODOCENTE
	WHERE DOCENTES.ESTADO = 0
	AND CARGOSDOCENTES.ESTADOCARGO = 0
	GROUP BY DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE;
	*/
/*	
	@Test
	public void armarConsulta2() {
		GestorInforme gi = new GestorInforme();
		ITipoInforme impactoDocente = gi.listarInforme(null).get(0);
		
		System.out.println();
		System.out.println(impactoDocente.armarConsulta());
		System.out.println();
		
		String consulta = "SELECT DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE, COUNT(CARGOSDOCENTES.CODIGO), SUM(CARGOSDOCENTES.ULTIMOCOSTO), SUM(CARGOS.CARGAHORARIA), COUNT(INTEGRANTES.ID), SUM(INTEGRANTES.HORASSEMANALES)\r\n"
				+ "FROM DOCENTES INNER JOIN PERSONAS ON DOCENTES.NRODOCUMENTO = PERSONAS.NRODOCUMENTO LEFT JOIN CARGOSDOCENTES ON DOCENTES.LEGAJO = CARGOSDOCENTES.LEGAJO INNER JOIN CARGOS ON CARGOSDOCENTES.CARGO = CARGOS.CODIGO LEFT JOIN INTEGRANTES ON CARGOSDOCENTES.CODIGO = INTEGRANTES.CARGODOCENTE\r\n"
				+ "WHERE DOCENTES.ESTADO = 0 AND CARGOSDOCENTES.ESTADOCARGO = 0\r\n"
				+ "GROUP BY DOCENTES.LEGAJO, PERSONAS.APELLIDO, PERSONAS.NOMBRE";
		System.out.println(consulta);
		
		Assert.assertEquals(consulta, impactoDocente.armarConsulta());
	}
*/
	@Test
	public void vistaPrevia1() {
		try {
			GestorInforme gi = new GestorInforme();
			ITipoInforme informeSelect = gi.getITipoInforme();
			informeSelect.setId(0);
			ITipoInforme impactoDocente = gi.listarInforme(informeSelect).get(0);
			
			List<List<String>> grilla = gi.vistaPrevia(impactoDocente);
			
			for (int i = 0; i < grilla.size(); i++) {
				List<String> fila = grilla.get(i);
				for (int k = 0; k < fila.size(); k++) {
					System.out.print(fila.get(k) + "\t");
				}
				System.out.println();
			}
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
