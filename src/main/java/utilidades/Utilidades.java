package utilidades;

import java.util.List;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 10 de may. de 2018
 */
public class Utilidades {
	/**
	 * Obtiene un número con decimal de un String, donde el separador puede ser
	 * una coma o un punto.
	 * @param s el String del cual se obtiene el número.
	 * @return el valor obtenido almacenado en un float.
	 * @throws IllegalArgumentException si el String no tiene formato de número
	 * con decimal.
	 */
	public static float stringToFloat(String s) throws IllegalArgumentException {
		if (s == null || s.equals("")) return 0.0f;
		
		String ss = s.replace(",", ".");
		try {
			return Float.valueOf(ss);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"El formato de \"" + s + "\" no corresponde "
					+ "a un número con decimal (e.g. 0,1 o 1.2)");
		}
	}
	
	/**
	 * Verifica si un String representa un número con decimal.
	 * @param s el String que representa a un número.
	 * @return <b>True</b> si el String representa un número,
	 * <b>False</b> en caso contrario.
	 */
	public static boolean isNumeric(String s) {
	  return s.matches("\\d+(\\.\\d+)?");
	}
	
	/**
	 * @param original el String que se quiere modificar.
	 * @return el String con la primer letra en mayúscula.
	 */
	public static String primeraMayuscula(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
	/**
	 * Devuelve un String, resultado de juntar la List (parecido a implode en PHP)
	 * @param list la lista a juntar
	 * @param separator separador entre los elementos del list
	 * @return el String resultado de juntar la List
	 */
	public static String joinString(List<String> list, String separator) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		separator = separator == null ? "" : separator;
		String res = "";
		int i = 0;
		while (i < list.size()-1) {			
			res += list.get(i) + separator;
			i++;
		}
		res += list.get(i);
		return res;
	}
	
	/**
	 * Devuelve un String, resultado de juntar la List (parecido a implode en PHP)
	 * @param list la lista a juntar
	 * @param separator separador entre los elementos del list
	 * @return el String resultado de juntar la List
	 */
	public static String joinString(String[] list, String separator) {
		if (list == null || list.length <= 0) {
			return "";
		}
		separator = separator == null ? "" : separator;
		String res = "";
		int i = 0;
		while (i < list.length-1) {			
			res += list[i] + separator;
			i++;
		}
		res += list[i];
		return res;
	}
	
	/**
	 * Reemplaza caracteres especiales como % o \.
	 * @param valor El valor a parametrizar.
	 */
	public static String parametrizarValor(String valor) {
		String valorParametrizado = valor.replace("\\", "\\\\");
		valorParametrizado = valorParametrizado.replace("%", "\\%");
		valorParametrizado = valorParametrizado.replace("_", "\\_");
		valorParametrizado = valorParametrizado.replace("\\0", "");
		valorParametrizado = valorParametrizado.replace("'", "\\'");
		valorParametrizado = valorParametrizado.replace("\"", "\\\"");
		return valorParametrizado;
	}
	
	/**
	 * Agrega caracteres especiales como % o \.
	 * @param valor El valor a desparametrizar.
	 */
	public static String desparametrizarValor(String valor) {
		String valorParametrizado = valor.replace("\\\"", "\"");
		valorParametrizado = valorParametrizado.replace("\\'", "'");
		valorParametrizado = valorParametrizado.replace("\\_", "_");
		valorParametrizado = valorParametrizado.replace("\\%", "%");
		valorParametrizado = valorParametrizado.replace("\\\\", "\\");
		return valorParametrizado;
	}
}
