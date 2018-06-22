package modelo.auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public enum Filtro {
	IGUAL ("Igual", "="),
	DISTI ("Distinto", "<>"),
	MENOR ("Menor", "<"),
	MENIG ("Menor o igual", "<="),
	MAYOR ("Mayor", ">"),
	MAYIG ("Mayor o igual", ">="),
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
}