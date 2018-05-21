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
    
    public void guardar() {
    	if(this.existe()) {
    		this.actualizar();
    	}else {
    		this.insertar();
    	}
    }

	private void insertar() {
		this.setId(EstadoDocente.getMaxID()+1);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosDocentes";
			String campos = "id, Descripcion";			
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e){
			
		}
		
	}

	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosDocentes";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "id = " + this.getId();
			
			md.update(tabla, campos, condicion);
			
		}catch (Exception e){
			
		}
		
	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosDocentes";
			String campos = "id";
			String condicion = "id = " + this.getId();
			
			if (md.select(tabla, campos, condicion).isEmpty()) {
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
	public static List<EstadoDocente> getLista() {
		try {
			List<EstadoDocente> estados = new ArrayList<EstadoDocente>(); 
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosDocentes";
			String campos = "*";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			for (Hashtable<String, String> reg : res) {
				EstadoDocente estado = new EstadoDocente();
				estado.setId(Integer.parseInt(reg.get("id")));
				estado.setDescripcion(reg.get("Descripcion"));
				estados.add(estado);
			}

			return estados;
		} catch (NumberFormatException e) {
			return new ArrayList<EstadoDocente>();
		}
	}

	public static EstadoDocente getEstado(EstadoDocente estado) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosDocentes";
			String campos = "*";
			String condicion = "`id` = " + estado.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			EstadoDocente estadoDocente = new EstadoDocente();
			estadoDocente.setId(Integer.parseInt(reg.get("id")));
			estadoDocente.setDescripcion(reg.get("Descripcion"));
			return estadoDocente;
		} catch (NumberFormatException e) {
			return new EstadoDocente();
		}
	}
	
	
	public static int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosDocentes";
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
