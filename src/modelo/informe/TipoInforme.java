package modelo.informe;

import java.util.List;

public class TipoInforme implements ITipoInforme {

	// Estos atributos se levantan de la BD
	private List<ColumnaInforme> columnas;
	private String from; // solo from con joins
	private String groupby; // groupby prearmado

	public TipoInforme(List<ColumnaInforme> columnas, String from, String groupby) {
		super();
		this.columnas = columnas;
		this.from = from;
		this.groupby = groupby;
	}

	public String armarConsulta() {
		String select = "SELECT";
		String where = "\r\nWHERE";
		String orderby = "\r\nORDER BY";
		boolean first_select = true;
		boolean b_where = false;
		boolean b_orderby = false;

		for (ColumnaInforme columna: columnas) {

			String atributo = columna.getAtributo();
			
			// Armar el SELECT
			if (first_select) {
				select += " ";
				first_select = false;
			} else {
				select += ", ";
			}
			
			if (columna.getCalculo() != null) {
				// SUM(PLANTA.ULTIMO_COSTO)
				select += columna.getCalculo() + "(" + atributo + ")";
			} else {
				// PLANTA.ULTIMO_COSTO
				select += atributo;
			}

			// Armar el WHERE			
			if (columna.getFiltros() != null 
					&& !columna.getFiltros().isEmpty()) {
				
				int i = 0;
				List<String> filtros = columna.getFiltros();
				
				if (!b_where) {
					// DOCENTE.LEGAJO > 140000
					where += " " + atributo + " " + filtros.get(i);
					b_where = true;
					i++;
				}
				
				while(i < filtros.size()) {
					where += " AND " + atributo + filtros.get(i);
					i++;
				}
			}

			// Armar el ORDER BY
			if (columna.getOrdenar() != ColumnaInforme.SIN_ORDEN) {
				if (!b_orderby) {
					b_orderby = true;
					orderby += " " + atributo;
				} else {
					orderby += ", " + atributo;
				}
				
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

		String consulta = select + "\r\nFROM " + this.from;
		
		if (b_where) {
			consulta += where;
		}
		
		consulta += "\r\nGROUPBY " + this.groupby;
		
		if (b_orderby) {
			consulta += orderby;
		}
		
		return consulta;
	}
	/*
	SELECT DOCENTE.LEGAJO, DOCENTE.APYNOM, SUM(PLANTA.ULTIMO_COSTO)
	FROM DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO
	WHERE DOCENTE.LEGAJO > 140000
	GROUPBY DOCENTE.LEGAJO
	ORDERBY DOCENTE.LEGAJO ASC, DOCENTE.APYNOM DESC
	*/
}