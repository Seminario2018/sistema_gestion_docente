package modelo.informe;


public class FiltroColumna {
	private FiltroColumna filtro;
	private String condicion; // Ejemplo: " >= 2018"
	
	public String getCondicion() {
		String resultado = condicion;
		
		if (filtro != null) {
			resultado += " AND " + filtro.getCondicion();
		}
		return resultado;
	}
}