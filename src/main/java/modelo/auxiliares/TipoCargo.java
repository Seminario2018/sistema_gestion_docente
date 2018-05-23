package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class TipoCargo {

	private int id;
	private String descripcion;

	public TipoCargo(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

	public TipoCargo() {
		// TODO Auto-generated constructor stub
	}

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
    	if (this.existe()) {
    		this.actualizar();
    	}else {
    		this.insertar();
    	}
    }
    

    private boolean existe() {
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposCargos";
			String campos = "*";
			String condicion = "id = " + this.id;
			
			if (md.select(tabla, campos, condicion).isEmpty()) {
				return false;
			}else {
				return true;
			}
			
		}catch (Exception e){
			return false;
		}
	}

	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposCargos";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "id = " + this.id;
			
			md.update(tabla, campos, condicion);
			
		}catch (Exception e){
			
		}
		
	}

	private void insertar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposCargos";
			String campos = "id, Descripcion";
			String valores = this.getId() +", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e) {
			
		}
	}

	/**
     * @return la lista de tipos de cargo de la BD
     */
    public static List<TipoCargo> getLista() {
        try {
			List<TipoCargo> listaTipos = new ArrayList<TipoCargo>();
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposCargos";
			String campos = "Descripcion";
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			
			for (Hashtable<String, String> reg : res) {
				TipoCargo tc = new TipoCargo();
				tc.setId(Integer.parseInt(reg.get("id")));
				tc.setDescripcion(reg.get("Descripcion"));
				listaTipos.add(tc);
			}
			
			return listaTipos;
		} catch (NumberFormatException e) {
			return new ArrayList<TipoCargo>();
		}
    }
    
    public static TipoCargo getTipoCargo(TipoCargo tc) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposCargos";
			String campos = "Descripcion";
			String condicion = "id = " + tc.id;
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			
			if (res.isEmpty()) {
				return null;
			}else {
				tc.setDescripcion(res.get(0).get("Descripcion"));
				return tc;
			}
			
		}catch (Exception e){
			return null;
		}
    }

    @Override
    public String toString() {
        return this.descripcion;
    }
}
