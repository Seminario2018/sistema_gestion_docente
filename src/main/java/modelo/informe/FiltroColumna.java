package modelo.informe;

import modelo.auxiliares.Filtro;
import modelo.informe.ColumnaInforme.TipoColumna;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 18 de jun. de 2018
 */
public class FiltroColumna {
	private Filtro tipo;
	private String valor;
	
	public static final String SEPARADOR = " ";
	
	public FiltroColumna() {
		super();
	}
	public FiltroColumna(Filtro tipo, String valor) {
		super();
		this.tipo = tipo;
		this.valor = valor;
	}
	
	
	public FiltroColumna(String filtro) {
		super();
		String tipo = FiltroColumna.tipoFiltro(filtro);
		String valor = FiltroColumna.valorFiltro(filtro);
		
		Filtro tipoFiltro = null;
		int i = 0;
		while (tipoFiltro == null && i < Filtro.getLista().length) {
			Filtro f = Filtro.getLista()[i];
			if (tipo.equals(f.getFiltro()))
				// Si tiene LIKE, hay que saber de qué tipo se trata
				if (f.getFiltro().equals(Filtro.CONTI.getFiltro())) {
					
					if (valor.startsWith("%")) {
						if (valor.endsWith("%") && !valor.endsWith("\\%"))
							// Contiene
							tipoFiltro = Filtro.CONTI;
						else
							// Empieza con
							tipoFiltro = Filtro.EMPIE;
					} else { 
						// Termina con
						tipoFiltro = Filtro.TERMI;
					}
				} else {
					tipoFiltro = f;					
				}
			i++;
		}
		
		this.tipo = tipoFiltro;
		this.valor = valor;
	}
	
	
	public FiltroColumna(String filtro, TipoColumna tipoColumna) {
		super();
		String tipo = FiltroColumna.tipoFiltro(filtro);
		String valor = FiltroColumna.valorFiltro(filtro);
		
		switch (tipoColumna) {
		case DATE:
			valor = valor.replace("-", "/");
		case STRING:
			valor = valor.substring(1, valor.length()-1);
			break;
		default:
			break;
		}
		
		Filtro tipoFiltro = null;
		int i = 0;
		while (tipoFiltro == null && i < Filtro.getLista().length) {
			Filtro f = Filtro.getLista()[i];
			if (tipo.equals(f.getFiltro()))
				// Si tiene LIKE, hay que saber de qué tipo se trata
				if (f.getFiltro().equals(Filtro.CONTI.getFiltro())) {
					
					if (valor.startsWith("%")) {
						if (valor.endsWith("%") && !valor.endsWith("\\%"))
							// Contiene
							tipoFiltro = Filtro.CONTI;
						else
							// Empieza con
							tipoFiltro = Filtro.EMPIE;
					} else { 
						// Termina con
						tipoFiltro = Filtro.TERMI;
					}
				} else {
					tipoFiltro = f;					
				}
			i++;
		}
		
		this.tipo = tipoFiltro;
		this.valor = valor;
	}
	
	
	public Filtro getTipo() {
		return tipo;
	}
	public void setTipo(Filtro tipo) {
		this.tipo = tipo;
	}
	public String getValor() {
		return Utilidades.desparametrizarValor(valor);
	}
	public void setValor(String valor) {
		this.valor = Utilidades.parametrizarValor(valor);
	}
	
	@Override
	public String toString() {
		String resultado = this.tipo.getFiltro() + SEPARADOR;
		switch (this.tipo) {
		case CONTI:
			resultado += "%" + this.valor + "%";
			break;
		case EMPIE:
			resultado += this.valor + "%";
			break;
		case TERMI:
			resultado += "%" + this.valor;
			break;
		default:
			resultado += this.valor;
			break;
		}
		return resultado;
	}
	
	
	/**
	 * Método alternativo para sanitizar la entrada dependiendo el tipo.
	 * Sirve para persistir en la Base de Datos.
	 * @param tipo
	 * @return
	 */
	public String toString(TipoColumna tipoColumna) {
		String tipo = this.tipo.getFiltro() + SEPARADOR;
		String valor = this.valor;
		switch (this.tipo) {
		case CONTI:
			valor = "%" + valor + "%";
			break;
		case EMPIE:
			valor = valor + "%";
			break;
		case TERMI:
			valor = "%" + valor;
			break;
		default:
			break;
		}
		switch (tipoColumna) {
		case DATE:
			valor = valor.replace("/", "-");
		case STRING:
			valor = "'" + valor + "'";
			break;
		default:
			break;
		}
		return tipo + valor;
	}
	
	/**
	 * Devuelve el valor del filtro.
	 * @param filtro El String correspondiente al filtro.
	 * @return El valor del filtro.
	 */
	public static String valorFiltro(String filtro) {
		return filtro.substring(filtro.indexOf(SEPARADOR) + 1);
	}
	
	/**
	 * Devuelve el tipo de filtro, según el enum {@code modelo.auxiliares.Filtro}.
	 * @param filtro El String correspondiente al filtro.
	 * @return El tipo de filtro.
	 */
	public static String tipoFiltro(String filtro) {
		return filtro.substring(0, filtro.indexOf(SEPARADOR));
	}
	
	@Override
	public FiltroColumna clone() {
		return new FiltroColumna(this.tipo, this.valor);
	}

}
