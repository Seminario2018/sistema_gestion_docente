package modelo.auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public enum Calculo {
	SUM ("SUM", "Suma"),
	COUNT ("COUNT", "Contar"),
	MAX ("MAX", "Máximo"),
	MIN ("MIN", "Mínimo"),
	GROUP_CONCAT ("GROUP_CONCAT", "Agrupar");
	
	private String descripcion;
	private String calculo;
	
	Calculo(String calculo, String descripcion) {
		this.calculo = calculo;
		this.descripcion = descripcion;
	}	
	
	public String getDescripcion() {
		return descripcion;
	}
	public String getCalculo() {
		return calculo;
	}
	
	public static Calculo[] getLista() {
		return Calculo.values();
	}
	
	
	public static Calculo getEnum(String arg0) {
		for (Calculo c : Calculo.values()) {
			if (c.getCalculo().equals(arg0))
				return c;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.getDescripcion();
	}
}
