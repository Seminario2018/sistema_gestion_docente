package modelo.auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public enum Calculo {
	SUM ("Suma", "SUM"),
	COUNT ("Contar", "COUNT"),
	MAX ("Máximo", "MAX"),
	MIN ("Mínimo", "MIN");
	
	private String descripcion;
	private String calculo;
	
	Calculo(String descripcion, String calculo) {
		this.descripcion = descripcion;
		this.calculo = calculo;
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
	
	@Override
	public String toString() {
		return this.getDescripcion();
	}
}
