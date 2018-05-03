package persistencia;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 3 de may. de 2018
 */
public abstract class Persistible {
	protected String tabla;
	
	public List<Persistible> select() {
		System.out.println(getWhere());
		return null;
	}
	

	
	private String getWhere() {
		Map<String, Object> camposConDatos = getCamposConDatos();
		String where = "";
		boolean first = true;
		for (Map.Entry<String, Object> entrada : camposConDatos.entrySet()){
			if (first) {
				where = "WHERE ";
				first = false;
			} else {
				where += " AND ";
			}
			where += entrada.getKey() + " = " + campoParametrizado(entrada.getValue());
		}
		return where;
	}
	
	/**
	 * Devuelve un diccionario con los nombres de los campos no vacíos
	 * y sus valores (toma como campo vacío a aquel que esté en <b>null</b>).
	 * <br>
	 * No incluye colecciones.
	 * @return el Map con &lt;nombre, valor&gt;.
	 */
	private Map<String, Object> getCamposConDatos() {
		
		Map<String, Object> camposConDatos = new HashMap<String, Object>();
		
		Class<? extends Persistible> c = this.getClass();
		Field[] campos = c.getDeclaredFields();
		
		for (Field campo : campos) {
			Method method = null;
			try {
				if (campo.getType() == boolean.class) {
					method = c.getMethod("is" + primeraMayuscula(campo.getName()));
				} else {
					method = c.getMethod("get" + primeraMayuscula(campo.getName()));
				}
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
			Object dato = null;
			try {
				dato = method.invoke(this);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (dato != null) {
				if (!(dato instanceof Collection)) {
					camposConDatos.put(campo.getName(), dato);
				}
			}
		}
		
		return camposConDatos;
	}
	
	/**
	 * 
	 * @param original - el String que se quiere modificar.
	 * @return el String con la primer letra en mayúscula.
	 */
	private String primeraMayuscula(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}
	
	/* Parametrización casera */
	private String campoParametrizado(Object campo) {
		if (campo instanceof String) {
			return (String) "'" + campo + "'";
		} else {
			return String.valueOf(campo);
		}
	}
}
