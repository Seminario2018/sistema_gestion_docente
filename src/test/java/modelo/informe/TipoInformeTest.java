package modelo.informe;

import java.util.ArrayList;
import java.util.List;

import modelo.informe.ColumnaInforme;
import modelo.informe.TipoInforme;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 25 de abr. de 2018
 */
public class TipoInformeTest {
	
	public static void main(String[] args) {
		prueba1();
	}
	
	private static void prueba1() {
		/*
		SELECT DOCENTE.LEGAJO, DOCENTE.APYNOM, SUM(PLANTA.ULTIMO_COSTO)
		FROM DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO
		WHERE DOCENTE.LEGAJO > 140000
		GROUPBY DOCENTE.LEGAJO
		ORDERBY DOCENTE.LEGAJO ASC, DOCENTE.APYNOM DESC
		*/
		List<String> filtro = new ArrayList<String>();
		filtro.add("> 140000");
		
		List<ColumnaInforme> columnas = new ArrayList<ColumnaInforme>();
		
		ColumnaInforme legajo = new ColumnaInforme("DOCENTE.LEGAJO",
				filtro,
				null,
				ColumnaInforme.ASCENDENTE,
				1);
		columnas.add(legajo);
		
		ColumnaInforme apynom = new ColumnaInforme("DOCENTE.APYNOM",
				null,
				null,
				ColumnaInforme.DESCENDENTE,
				2);
		columnas.add(apynom);
		
		ColumnaInforme sumcosto = new ColumnaInforme("PLANTA.ULTIMO_COSTO",
				null,
				"SUM",
				ColumnaInforme.SIN_ORDEN,
				3);
		columnas.add(sumcosto);
		
		TipoInforme impactoDocente = new TipoInforme(columnas,
				"DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO",
				"DOCENTE.LEGAJO");
		
		System.out.println(impactoDocente.armarConsulta());
	}

}
