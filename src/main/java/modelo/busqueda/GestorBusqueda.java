package modelo.busqueda;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class GestorBusqueda {

	public List<BusquedaDocente> listarDocentes(String criterio) {
		String tabla = "ViewDocente";
		String campos = "legajo,apellido,nombre";
		String condicion = "TRUE";
		List<BusquedaDocente> docentes = new ArrayList<BusquedaDocente>();
		
		if (criterio != null && !criterio.equals("")) {
			condicion = armarCondicion(campos.split(","), criterio);
		}
		
		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				BusquedaDocente doc = new BusquedaDocente(
						Integer.parseInt(reg.get("legajo")),
						reg.get("apellido"),
						reg.get("nombre"));
				docentes.add(doc);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return docentes;
	}
	
	public String armarCondicion(String[] campos, String criterio) {
		String[] condicion = new String[campos.length];
		for (int i = 0; i < campos.length; i++) {
			condicion[i] = campos[i] + " LIKE " + criterio;
		}
		return Utilidades.joinString(condicion, " OR ");
	}
}
