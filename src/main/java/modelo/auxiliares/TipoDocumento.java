package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;

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
			String tabla = "tipos_documento";
			String campos = "`idTipo` = "+ this.getId() +", `Descripcion` = '" + this.getDescripcion() + "'";
			String condicion = "idTipo = " + this.getId();
			
			md.update(tabla, campos, condicion);
			
		} catch (Exception e) {
			
		}
		
	}


	private void insertar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "tipos_documento";
			String campos = "`idTipo`, `Descripcion`";
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		} catch (Exception e) {
			
		}
	}


	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "tipos_documento";
			String campos = "*";
			String condicion = "idTipo = " + this.getId();
			
			if(md.select(tabla, campos, condicion).isEmpty()) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * @return la lista de tipos de documento de la BD
	 */
	public static ArrayList<TipoDocumento> getLista() {
		try {
			ArrayList<TipoDocumento> documentos = new ArrayList<TipoDocumento>();
			ManejoDatos md = new ManejoDatos();
			String tabla = "tipos_documento";
			String campos = "*";
			
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			for (Hashtable<String, String> reg : res) {
				TipoDocumento doc = new TipoDocumento(Integer.parseInt(reg.get("idTipo")), reg.get("Descripcion"));
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
			String tabla = "tipos_documento";
			String campos = "*";
			String condicion = "idTipo = " + doc.getId();
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			return new TipoDocumento(Integer.parseInt(reg.get("idTipo")), reg.get("Descripcion"));
		} catch (NumberFormatException e) {
			return new TipoDocumento();
		}

	}
}



