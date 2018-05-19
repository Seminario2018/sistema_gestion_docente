package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class CategoriaInvestigacion {

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
	 * @return la lista de categorías de investigación de la BD
	 */
	public static List<CategoriaInvestigacion> getLista() {
		ArrayList<CategoriaInvestigacion> categorias = new ArrayList<CategoriaInvestigacion>();
		ManejoDatos md = new ManejoDatos();
		ArrayList<Hashtable<String, String>> res = md.select("CategoriaInvestigacion", "`idCategoriaInvestigacion`, `Descripcion`");
		for (Hashtable<String, String> reg : res) {
			CategoriaInvestigacion cat = new CategoriaInvestigacion();
			cat.setId(Integer.parseInt(reg.get("idCategoriaInvestigacion")));
			cat.setDescripcion(reg.get("Descripcion"));
			categorias.add(cat);
		}
		return categorias;
	}


	public static CategoriaInvestigacion getCategoria(CategoriaInvestigacion categoria) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CategoriaInvestigacion";
			String campos = "*";
			String condicion = "`idCategoriaInvestigacion` = " + categoria.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			CategoriaInvestigacion cat = new CategoriaInvestigacion();
			cat.setId(Integer.parseInt(reg.get("idCategoriaInvestigacion")));
			cat.setDescripcion(reg.get("Descripcion"));
			return cat;
		} catch (NumberFormatException e) {
			return new CategoriaInvestigacion();
		}
	}

	@Override
    public String toString() {
        return this.descripcion;
    }
}
