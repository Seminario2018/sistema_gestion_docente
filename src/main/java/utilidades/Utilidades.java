package utilidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
		if (s == null || s.equals("")) {
            return 0.0f;
        }

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
	 * Convierte LocalDate a String con formato dd-mm-yyyy
	 * @param fecha la fecha a convertir
	 */
	public static String localDateToString(LocalDate fecha) {
		return fecha.toString();
	}

	/**
	 * Convierte float a String con formato .2f
	 * @param f el float a convertir
	 */
	public static String floatToString(float f) {
		return String.format("%.2f", f);
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
	 * @param original el String que se quiere modificar.
	 * @return el String con la primer letra en minúscula.
	 */
	public static String primeraMinuscula(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toLowerCase() + original.substring(1);
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

	/**
	 * Extrae el contenido de un archivo XML en un objeto.
	 * @param nombreArchivo Nombre del archivo XML
	 * @return Objeto que representa el contenido del archivo XML
	 */
	public static Document leerXML(File archivo) {
	    try {
            Document documento = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse(
                    archivo);
            documento.getDocumentElement().normalize();
            return documento;

        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return null;
        }
	}

	public static void guardarXML(File archivo, Document documento)
	    throws TransformerException {

        TransformerFactory
            .newInstance()
            .newTransformer()
            .transform(
                new DOMSource(documento),
                new StreamResult(archivo));
	}

	/**
	 * Guarda el texto en el archivo, grabándolo al final.
	 * @param archivo Archivo a donde grabar el texto
	 * @param texto Texo a grabar
	 */
	public static void guardarTexto(File archivo, String texto) {
	    FileWriter fw = null;
	    PrintWriter pw = null;
	    BufferedWriter bw = null;
	    try {
	        fw = new FileWriter(archivo, true);
	        bw = new BufferedWriter(fw);
	        pw = new PrintWriter(bw);

	        pw.println(texto);
	        pw.flush();

	    } catch (IOException e) {
	        e.printStackTrace();

	    } finally {
	        try {
    	        pw.close();
    	        bw.close();
    	        fw.close();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
