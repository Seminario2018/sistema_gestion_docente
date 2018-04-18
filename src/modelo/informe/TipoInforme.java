package modelo.informe;

import java.util.ArrayList;

public class TipoInforme {
	
	// Estos atributos se levantan de la BD
	private ArrayList<ColumnaInforme> columnas;
	private String from; // solo from con joins
	
	public ArrayList<ArrayList<String>> armarConsulta() {
		String select = "SELECT";
		String where = "\r\nWHERE";
		String groupby = "\r\nGROUP BY";
		String orderby = "\r\nORDER BY";
		boolean b_where = false;
		boolean b_groupby = false;
		boolean b_orderby = false;
		
		for (ColumnaInforme columna: columnas) {

			// Armar el SELECT
			if (columna.getCalculo() != null) {
				// SUM(PLANTA.ULTIMO_COSTO)
				select += " " + columna.getCalculo().getOperacion() + "("
						+ columna.getAtributo() + ")";
			} else {
				// PLANTA.ULTIMO_COSTO
				select += " " + columna.getAtributo();
			}
			
			// Armar el WHERE
			if (columna.getFiltro() != null) {
				b_where = true;
				// DOCENTE.LEGAJO > 140000
				where += " " +columna.getAtributo()
						+ columna.getFiltro().getCondicion();
			}
			
			// Armar el GROUP BY
			if (columna.getCalculo() != null) {
				b_groupby = true;
				// GROUPBY DOCENTE.LEGAJO
				groupby += " " + columna.getAtributo();
			}
			
			// Armar el ORDER BY
			if (columna.getOrdenar() != ColumnaInforme.SIN_ORDEN) {
				b_orderby = true;
				orderby += " " + columna.getAtributo();
				switch (columna.getOrdenar()) {
				case ColumnaInforme.ASCENDENTE:
					// ORDERBY DOCENTE.LEGAJO ASC
					orderby += " ASC";
					break;
				case ColumnaInforme.DESCENDENTE:
					// ORDERBY DOCENTE.APYNOM DESC
					orderby += " DESC";
					break;
				default:
					break;
				}
			}
		}
		
		String consulta = select + this.from;
		if (b_where) {
			consulta += where;
		}
		if (b_groupby) {
			consulta += groupby;
		}
		if (b_orderby) {
			consulta += orderby;
		}
		
		// TODO enviar consulta a la BD
		
		return null;
	}
	/*
	SELECT DOCENTE.LEGAJO, DOCENTE.APYNOM, SUM(PLANTA.ULTIMO_COSTO)
	FROM DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO
	WHERE DOCENTE.LEGAJO > 140000
	GROUPBY DOCENTE.LEGAJO
	ORDERBY DOCENTE.LEGAJO ASC, DOCENTE.APYNOM DESC
	*/
}