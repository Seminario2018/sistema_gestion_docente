package auxiliares;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 10 de may. de 2018
 */
public class Numeros {
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
	
	public static boolean isNumeric(String s) {
	  return s.matches("\\d+(\\.\\d+)?");
	}
}
