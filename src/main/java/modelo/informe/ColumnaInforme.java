package modelo.informe;

import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.Calculo;

public class ColumnaInforme {
	public static final int SIN_ORDEN = 0;
	public static final int ASCENDENTE = 1;
	public static final int DESCENDENTE = 2;
	
	public static final String SEPARADOR = FiltroColumna.SEPARADOR;
	
	public enum TipoColumna {
		INTEGER("INTEGER"),
		FLOAT("FLOAT"),
		STRING("STRING"),
		DATE("DATE");
		
		private String descripcion;
		private TipoColumna(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
	}
	
	public enum SepFiltro {
		AND("y", "AND"),
		OR("o", "OR");
		
		private String descripcion;
		private String separador;
		private SepFiltro(String descripcion, String separador) {
			this.descripcion = descripcion;
			this.separador = separador;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getSeparador() {
			return separador;
		}
		public void setSeparador(String separador) {
			this.separador = separador;
		}
	}
	
	private Boolean visible;
	private String nombre;
	private String atributo; // PLANTA.ULTIMO_COSTO
	private List<FiltroColumna> filtros; // >= 2017
	private List<SepFiltro> sepFiltros; // OR y AND 
	private Calculo calculo; // COUNT
	private int ordenar; // SIN_ORDEN, ASCENDENTE O DESCENDENTE
	private int posicion; // Posición de la columna en el informe
	private TipoColumna tipo; // Tipo de valor
	
	/**
	 * Devuelve un String con los filtros.
	 * @param consulta <b>True</b> si se está realizando la consulta 
	 * (hay que parametrizar los Strings y Dates, por ejemplo),
	 * <b>False</b> en caso contrario.
	 * @return el String convertido
	 */
	public String stringFiltros(boolean consulta) {
		String res = "";
		try {
			if (this.filtros != null && !this.filtros.isEmpty()) {
				if (consulta)
					res += this.atributo + " " + this.filtros.get(0).toString(this.tipo);
				else
					res += this.filtros.get(0).toString();
				
				if (this.sepFiltros != null && !this.sepFiltros.isEmpty())
					for (int i = 0; i < this.sepFiltros.size(); i++) {
						res += SEPARADOR
								+ this.sepFiltros.get(i).getSeparador()
								+ SEPARADOR;
						if (consulta)
							res += this.atributo + " " + this.filtros.get(i+1).toString(this.tipo);
						else
							res += this.filtros.get(i+1).toString();
					}
			}
		} catch (IndexOutOfBoundsException e) {
			System.err.println("El número de filtros y separadores no es correcto.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Devuelve un String con los filtros, presentables en la vista
	 * @return el String convertido
	 */
	public String stringFiltrosUI() {
		String res = "";
		try {
			if (this.filtros != null && !this.filtros.isEmpty()) {
				res += "(" + this.filtros.get(0).getTipo().getDescripcion() + " ";
				res += this.filtros.get(0).getValor() + ")";
				
				if (this.sepFiltros != null && !this.sepFiltros.isEmpty())
					for (int i = 0; i < this.sepFiltros.size(); i++) {
						res += SEPARADOR
								+ this.sepFiltros.get(i).getDescripcion()
								+ SEPARADOR;
						res += "(" + this.filtros.get(i+1).getTipo().getDescripcion() + " ";
						res += this.filtros.get(i+1).getValor() + ")";
					}
			}
		} catch (IndexOutOfBoundsException e) {
			System.err.println("El número de filtros y separadores no es correcto.");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Método alternativo para setear los filtros.
	 * @param s el string recuperado de la BD
	 * @param consulta <b>True</b> si se está realizando la consulta 
	 * (hay que parametrizar los Strings y Dates, por ejemplo),
	 * <b>False</b> en caso contrario.
	 */
	public void setFiltros(String s, boolean consulta) {
		if (s == null || s.length() < 1) return;
		
		this.filtros = new ArrayList<FiltroColumna>();
		this.sepFiltros = new ArrayList<SepFiltro>();
		
		// Buscar por separadores
		boolean tieneSeparadores = false;
		SepFiltro sepFiltro = null;
		for (SepFiltro sep : SepFiltro.values()) {
			if (s.contains(sep.separador)) {
				tieneSeparadores = true;
				sepFiltro = sep;
			}
		}
		
		// Parsear el string, agregando filtros y separadores
		while (tieneSeparadores) {
			String fi = s.substring(0, s.indexOf(sepFiltro.separador) - SEPARADOR.length());
			FiltroColumna f;
			if (consulta)
				f = new FiltroColumna(fi, this.tipo);
			else
				f = new FiltroColumna(fi);
			
			this.filtros.add(f);
			this.sepFiltros.add(sepFiltro);
			
			s = s.substring(
					s.indexOf(sepFiltro.separador)
					+ sepFiltro.separador.length()
					+ SEPARADOR.length(),
				s.length());
			
			tieneSeparadores = false;
			
			for (SepFiltro sep : SepFiltro.values()) {
				if (s.contains(sep.separador)) {
					tieneSeparadores = true;
					sepFiltro = sep;
				}
			}
		}
		
		// Añadir el último / único filtro
		if (consulta)
			this.filtros.add(new FiltroColumna(s, this.tipo));
		else
			this.filtros.add(new FiltroColumna(s));
	}
	
	public ColumnaInforme(Boolean visible, String nombre, String atributo,
			List<FiltroColumna> filtros, List<SepFiltro> sepFiltros, Calculo calculo,
			int ordenar, int posicion, TipoColumna tipo) {
		super();
		this.visible = visible;
		this.nombre = nombre;  	
		this.atributo = atributo;
		this.filtros = filtros;
		this.sepFiltros = sepFiltros;
		this.calculo = calculo;
		this.ordenar = ordenar;
		this.posicion = posicion;
		this.tipo = tipo;
	}
	
	@Override
	public ColumnaInforme clone() {
		return new ColumnaInforme(
				this.visible,
				this.nombre,
				this.atributo,
				new ArrayList<FiltroColumna>(this.filtros),
				new ArrayList<SepFiltro>(this.sepFiltros),
				this.calculo,
				this.ordenar,
				this.posicion,
				this.tipo);
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
	public List<FiltroColumna> getFiltros() {
		return filtros;
	}
	public void setFiltros(List<FiltroColumna> filtros) {
		this.filtros = filtros;
	}
	public List<SepFiltro> getSepFiltros() {
		return sepFiltros;
	}
	public void setSepFiltros(List<SepFiltro> sepFiltros) {
		this.sepFiltros = sepFiltros;
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
	public TipoColumna getTipo() {
		return tipo;
	}
	public void setTipo(TipoColumna tipo) {
		this.tipo = tipo;
	}
}