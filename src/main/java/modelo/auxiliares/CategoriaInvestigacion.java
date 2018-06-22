package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Comparator;
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

	public void guardar() {
		if(this.existe()) {
			this.actualizar();
		}else {
			this.insertar();
		}
	}

	private void insertar() {
		this.setId(CategoriaInvestigacion.getMaxID()+1);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CategoriasInvestigacion";
			String campos = "id, Descripcion";
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";

			md.insertar(tabla, campos, valores);

		}catch (Exception e){

		}

	}

	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CategoriasInvestigacion";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "id = " + this.getId();

			md.update(tabla, campos, condicion);

		}catch (Exception e){

		}

	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CategoriasInvestigacion";
			String campos = "id";
			String condicion = "Descripcion = '" + this.getDescripcion() + "'";

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);

			if (!res.isEmpty()) {
				this.id = Integer.parseInt(res.get(0).get("id"));
				return true;
			}else {
				return false;
			}


		}catch (Exception e){
			return false;
		}

	}

	/**
	 * @return la lista de categorías de investigación de la BD
	 */
	public static List<CategoriaInvestigacion> getLista() {
		List<CategoriaInvestigacion> categorias = new ArrayList<CategoriaInvestigacion>();

		ManejoDatos md = new ManejoDatos();
		List<Hashtable<String, String>> res = md.select("CategoriasInvestigacion", "*");
		for (Hashtable<String, String> reg : res) {
			CategoriaInvestigacion cat = new CategoriaInvestigacion();
			cat.setId(Integer.parseInt(reg.get("id")));
			cat.setDescripcion(reg.get("Descripcion"));
			categorias.add(cat);
		}
		categorias.sort(new Comparator<CategoriaInvestigacion>() {
            @Override
            public int compare(CategoriaInvestigacion cat1, CategoriaInvestigacion cat2) {
                return cat1.id - cat2.id;
            }
		});
		return categorias;
	}


	public static CategoriaInvestigacion getCategoria(CategoriaInvestigacion categoria) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CategoriasInvestigacion";
			String campos = "*";
			String condicion = "`id` = " + categoria.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			CategoriaInvestigacion cat = new CategoriaInvestigacion();
			cat.setId(Integer.parseInt(reg.get("id")));
			cat.setDescripcion(reg.get("Descripcion"));
			return cat;
		} catch (NumberFormatException e) {
			return new CategoriaInvestigacion();
		}
	}

	public static int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "CategoriasInvestigacion";
			String campos = "Max(id)";

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			int maxID = Integer.parseInt(res.get(0).get("Max(id)"));
			return maxID;


		}catch (Exception e){
			return 0;
		}

	}

	@Override
	public String toString() {
		return this.descripcion;
	}

}
