package modelo.informe;

import java.util.List;

public class ColumnaInforme {
	public static final int SIN_ORDEN = 0;
	public static final int ASCENDENTE = 1;
	public static final int DESCENDENTE = 2;
	
	// Tipos de valores
	public static final String INTEGER = "INTEGER";
	public static final String FLOAT = "FLOAT";
	public static final String STRING = "STRING";
	public static final String DATE = "DATE";
	
	private Boolean visible;
	private String nombre;
	private String atributo; // PLANTA.ULTIMO_COSTO
	private List<String> filtros; // >= 2017
	private String calculo; // COUNT
	private int ordenar; // SIN_ORDEN, ASCENDENTE O DESCENDENTE
	private int posicion; // Posición de la columna en el informe
	private String tipo; // Tipo de valor
	
	public ColumnaInforme(Boolean visible, String nombre, String atributo, List<String> filtros, String calculo, int ordenar, int posicion, String tipo) {
		super();
		this.visible = visible;
		this.nombre = nombre;  	
		this.atributo = atributo;
		this.filtros = filtros;
		this.calculo = calculo;
		this.ordenar = ordenar;
		this.posicion = posicion;
		this.tipo = tipo;
	}
	
	public ColumnaInforme() {
		this.filtros = null;
		this.ordenar = -1;
		this.posicion = -1;
	}

	public Boolean isVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAtributo() {
		return atributo;
	}
	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	public List<String> getFiltros() {
		return filtros;
	}
	public void setFiltros(List<String> filtros) {
		this.filtros = filtros;
	}
	public String getCalculo() {
		return calculo;
	}
	public void setCalculo(String calculo) {
		this.calculo = calculo;
	}
	public int getOrdenar() {
		return ordenar;
	}
	public void setOrdenar(int ordenar) {
		this.ordenar = ordenar;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}