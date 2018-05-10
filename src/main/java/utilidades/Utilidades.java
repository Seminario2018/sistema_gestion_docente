package utilidades;

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
}
