package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class TipoDocumento {
	private int id;
	private String descripcion;

	public TipoDocumento(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}


	public TipoDocumento() {
		super();
		this.id = -1;
		this.descripcion = null;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void guardarTipoDocumento() {
		if (this.existe()) {
			this.actualizar();
		}else {
			this.insertar();
		}

	}


	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposDocumentos";
			String campos = "`Descripcion` = '" + this.getDescripcion() + "'";
			String condicion = "id = " + this.getId();

			md.update(tabla, campos, condicion);

		} catch (Exception e) {

		}

	}


	private void insertar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposDocumentos";
			String campos = "`id`, `Descripcion`";
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";

			md.insertar(tabla, campos, valores);

		} catch (Exception e) {

		}
	}


	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposDocumentos";
			String campos = "id";
			String condicion = "Descripcion = '" + this.getDescripcion() + "'";
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);

			if (!res.isEmpty()) {
				this.id = Integer.parseInt(res.get(0).get("id"));
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * @return la lista de tipos de documento de la BD
	 */
	public static List<TipoDocumento> getLista() {
		try {
			List<TipoDocumento> documentos = new ArrayList<TipoDocumento>();
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposDocumentos";
			String campos = "*";

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			for (Hashtable<String, String> reg : res) {
				TipoDocumento doc = new TipoDocumento(Integer.parseInt(reg.get("id")), reg.get("Descripcion"));
				documentos.add(doc);
			}

			return documentos;
		} catch (Exception e) {
			return new ArrayList<TipoDocumento>();
		}
	}

	public static TipoDocumento getTipo(TipoDocumento doc) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposDocumentos";
			String campos = "*";
			String condicion = "id = " + doc.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			
			return new TipoDocumento(Integer.parseInt(reg.get("id")), reg.get("Descripcion"));
		} catch (NumberFormatException e) {
			return new TipoDocumento();
		}

	}

	@Override
    public String toString() {
        return this.descripcion;
    }
}



