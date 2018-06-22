package modelo.auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public enum Filtro {
	IGUAL ("Igual a", "="),
	DISTI ("Distinto de", "<>"),
	MENOR ("Menor a", "<"),
	MENIG ("Menor o igual a", "<="),
	MAYOR ("Mayor a", ">"),
	MAYIG ("Mayor o igual a", ">="),
	CONTI ("Contiene", "LIKE"),
	EMPIE ("Empieza con", "LIKE"),
	TERMI ("Termina con", "LIKE");
	
	private String descripcion;
	private String filtro;
	
	private Filtro(String descripcion, String filtro) {
		this.descripcion = descripcion;
		this.filtro = filtro;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public String getFiltro() {
		return filtro;
	}

	public static Filtro[] getLista() {
		return Filtro.values();
	}
	
	@Override
	public String toString() {
		return this.getDescripcion();
	}
}