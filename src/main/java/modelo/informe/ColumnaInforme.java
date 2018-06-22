package modelo.informe;

import modelo.auxiliares.Calculo;

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
	private FiltroColumna filtro; // >= 2017
	private Calculo calculo; // COUNT
	private int ordenar; // SIN_ORDEN, ASCENDENTE O DESCENDENTE
	private int posicion; // Posición de la columna en el informe
	private String tipo; // Tipo de valor
	
	public ColumnaInforme(Boolean visible, String nombre, String atributo, FiltroColumna filtro, Calculo calculo, int ordenar, int posicion, String tipo) {
		super();
		this.visible = visible;
		this.nombre = nombre;  	
		this.atributo = atributo;
		this.filtro = filtro;
		this.calculo = calculo;
		this.ordenar = ordenar;
		this.posicion = posicion;
		this.tipo = tipo;
	}
	
	public ColumnaInforme() {
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
	public FiltroColumna getFiltro() {
		return filtro;
	}
	public void setFiltro(FiltroColumna filtro) {
		this.filtro = filtro;
	}
	public Calculo getCalculo() {
		return calculo;
	}
	public void setCalculo(Calculo calculo) {
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