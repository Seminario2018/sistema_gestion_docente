package modelo.informe;

import java.util.List;

public class TipoInforme implements ITipoInforme {

	private int id;
	private String nombre;
	private String descripcion;
	private boolean editable;

	private List<ColumnaInforme> columnas;
	private String fromString; // solo from con joins
	private String groupByString; // groupby prearmado

	@Override
    public ITipoInforme clone() {
	    return new TipoInforme(
            this.id,
            this.nombre,
            this.descripcion,
            this.editable,
            this.columnas,
            this.fromString,
            this.groupByString
        );
	}

	public String armarConsulta() {
		String select = "SELECT";
		String where = "\r\nWHERE";
		String orderby = "\r\nORDER BY";
		boolean first_select = true;
		boolean b_where = false;
		boolean b_orderby = false;

		for (ColumnaInforme columna : columnas) {

			String atributo = columna.getAtributo();
			
			if (columna.isVisible()) {
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
			}

			// Armar el WHERE
			if (columna.getFiltros() != null && !columna.getFiltros().isEmpty()) {

				int i = 0;
				List<String> filtros = columna.getFiltros();

				if (!b_where) {
					// DOCENTE.LEGAJO > 140000
					where += " " + atributo + " " + filtros.get(i);
					b_where = true;
					i++;
				}

				while (i < filtros.size()) {
					where += " AND " + atributo + " " + filtros.get(i);
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

		String consulta = select + "\r\nFROM " + this.fromString;

		if (b_where) {
			consulta += where;
		}

		consulta += "\r\nGROUP BY " + this.groupByString;

		if (b_orderby) {
			consulta += orderby;
		}

		return consulta;
	}
	/*
	 * SELECT DOCENTE.LEGAJO, DOCENTE.APYNOM, SUM(PLANTA.ULTIMO_COSTO) FROM
	 * DOCENTE INNER JOIN PLANTA ON DOCENTE.LEGAJO = PLANTA.LEGAJO WHERE
	 * DOCENTE.LEGAJO > 140000 GROUPBY DOCENTE.LEGAJO ORDERBY DOCENTE.LEGAJO
	 * ASC, DOCENTE.APYNOM DESC
	 */

	public TipoInforme() {
	}

	public TipoInforme(int id, String nombre, String descripcion,
			boolean editable, List<ColumnaInforme> columnas,
			String from, String groupby) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.editable = editable;
		this.columnas = columnas;
		this.fromString = from;
		this.groupByString = groupby;
	}

	@Override
    public int getId() {
		return id;
	}

	@Override
    public void setId(int id) {
		this.id = id;
	}

	@Override
    public String getNombre() {
		return nombre;
	}

	@Override
    public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
    public String getDescripcion() {
		return descripcion;
	}

	@Override
    public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
    public boolean isEditable() {
		return editable;
	}

	@Override
    public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
    public List<ColumnaInforme> getColumnas() {
		return columnas;
	}

	@Override
    public void setColumnas(List<ColumnaInforme> columnas) {
		this.columnas = columnas;
	}

	@Override
    public String getFromString() {
		return fromString;
	}

	@Override
    public void setFromString(String from) {
		this.fromString = from;
	}

	@Override
    public String getGroupByString() {
		return groupByString;
	}

	@Override
    public void setGroupByString(String groupby) {
		this.groupByString = groupby;
	}
}