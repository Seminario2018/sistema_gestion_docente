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
    		this.obtenerID();
    }else {
    		this.insertar();
    	}
    	
    }

    private void insertar() {
    	try {
    		this.setId(TipoContacto.getMaxID() + 1);
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposContactos";
			String campos = "id, Descripcion";
			String valores = this.getId() +", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e) {
			
		}
		
		
	}

	private void obtenerID() {
		try {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res = md.select("TiposContactos", "id", "Descripcion = '" + this.getDescripcion() + "'");
    		this.setId(Integer.parseInt(res.get(0).get("id")));
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
		
	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposContactos";
			String campos = "id";
			String condicion = "Descripcion = '" +  this.getDescripcion() + "'";
			
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
    
    public static int getMaxID() {
    	try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "TiposContactos";
			String campos = "Max(id)";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			int maxID = Integer.parseInt(res.get(0).get("Max(id)"));
			
			return maxID;
		}catch (Exception e) {
			return 0;
		}
    }
    

    @Override
    public String toString() {
        return this.descripcion;
    }
}
