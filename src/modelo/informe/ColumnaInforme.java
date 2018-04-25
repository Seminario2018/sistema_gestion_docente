package modelo.informe;

import java.util.List;

public class ColumnaInforme {
	public static final int SIN_ORDEN = 0;
	public static final int ASCENDENTE = 1;
	public static final int DESCENDENTE = 2;
	
	private String atributo; // PLANTA.ULTIMO_COSTO
	private List<String> filtros; // >= 2017
	private String calculo; // COUNT
//	GrupoColumna grupo;
	private int ordenar; // SIN_ORDEN, ASCENDENTE O DESCENDENTE
	private int posicion; // Posición de la columna en el informe
	
	public ColumnaInforme(String atributo, List<String> filtros, String calculo, int ordenar, int posicion) {
		super();
		this.atributo = atributo;
		this.filtros = filtros;
		this.calculo = calculo;
		this.ordenar = ordenar;
		this.posicion = posicion;
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
}