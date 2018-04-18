package modelo.informe;

public class ColumnaInforme {
	public static final int SIN_ORDEN = 0;
	public static final int ASCENDENTE = 1;
	public static final int DESCENDENTE = 2;
	
	private String atributo; // PLANTA.ULTIMO_COSTO
	private FiltroColumna filtro; // >= 2017
	private CalculoColumna calculo; // COUNT()
//	GrupoColumna grupo;
	private int ordenar;
	
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
	public CalculoColumna getCalculo() {
		return calculo;
	}
	public void setCalculo(CalculoColumna calculo) {
		this.calculo = calculo;
	}
	public int getOrdenar() {
		return ordenar;
	}
	public void setOrdenar(int orden) {
		this.ordenar = orden;
	}
}