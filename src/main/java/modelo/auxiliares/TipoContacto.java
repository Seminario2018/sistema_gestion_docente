package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class TipoContacto {

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
    	if (this.existe()) {
    		this.actualizar();
    	}else {
    		this.insertar();
    	}
    	
    }

    private void insertar() {
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposContactos";
			String campos = "id, Descripcion";
			String valores = this.getId() +", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e) {
			
		}
		
		
	}

	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposContactos";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "id = " + this.getId();
			
			md.update(tabla, campos, condicion);
			
		}catch (Exception e) {
			
		}
		
	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposContactos";
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

	/**
     * @return la lista de estados del docente de la BD
     */
    public static List<TipoContacto> getLista() {
    	List<TipoContacto> tiposContactos = new ArrayList<TipoContacto>();
    	try {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res = md.select("TiposContactos", "*");
    		for (Hashtable<String, String> reg : res) {
				TipoContacto tc =  new TipoContacto();
				tc.setId(Integer.parseInt(reg.get("id")));
				tc.setDescripcion(reg.get("Descripcion"));
				tiposContactos.add(tc);
			}
    	}catch (Exception e) {
    		tiposContactos = new ArrayList<TipoContacto>();
    	}
        return tiposContactos;
    }
    
    public static TipoContacto getTipoContacto(TipoContacto tc) {
    	try {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res = md.select("TiposContactos", "*", "id = " + tc.getId());
    		tc.setDescripcion(res.get(0).get("Descripcion"));
    		return tc;
    	}catch (Exception e) {
    		return new TipoContacto();
    	}
    	
    }
    

    @Override
    public String toString() {
        return this.descripcion;
    }
}
