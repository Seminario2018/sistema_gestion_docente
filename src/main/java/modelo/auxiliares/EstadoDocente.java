package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoDocente {

	private int id;
	private String descripcion;

	public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	/**
	 * @return la lista de estados del docente de la BD
	 */
	public static List<EstadoDocente> getLista() {
		return null;
	}
	
	public static EstadoDocente getEstado(EstadoDocente estado) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadoDocente";
			String campos = "*";
			String condicion = "``idEstadoDocente` = " + estado.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			EstadoDocente estadoDocente = new EstadoDocente();
			estadoDocente.setId(Integer.parseInt(reg.get("idEstadoDocente")));
			estadoDocente.setDescripcion(reg.get("Descripcion"));
			return estadoDocente;
		} catch (NumberFormatException e) {
			return new EstadoDocente();
		}
	}
}
