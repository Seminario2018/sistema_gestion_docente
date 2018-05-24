package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoPrograma {

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
		this.setId(EstadoPrograma.getMaxID()+1);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProgramas";
			String campos = "id, Descripcion";			
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e){
			
		}
		
	}

	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProgramas";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "id = " + this.getId();
			
			md.update(tabla, campos, condicion);
			
		}catch (Exception e){
			
		}
		
	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProgramas";
			String campos = "id";
			String condicion = "Descripcion = '" + this.getDescripcion() + "'";
			
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
	public static List<EstadoPrograma> getLista() {
		try {
			List<EstadoPrograma> estados = new ArrayList<EstadoPrograma>();
			
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProgramas";
			String campos = "*";

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			for (Hashtable<String, String> reg : res) {
				EstadoPrograma estado = new EstadoPrograma();
				estado.setId(Integer.parseInt(reg.get("id")));
				estado.setDescripcion(reg.get("Descripcion"));
			}
			
			return estados;
		} catch (NumberFormatException e) {
			return new ArrayList<EstadoPrograma>();
		}		
		
	}
	
	public static EstadoPrograma getEstado(EstadoPrograma estado) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProgramas";
			String campos = "*";
			String condicion = "id = " + estado.getId();

			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos,condicion);
			Hashtable<String, String> reg = res.get(0);
			estado.setDescripcion(reg.get("Descripcion"));
			
			
			return estado;
		} catch (NumberFormatException e) {
			return new EstadoPrograma();
		}		
		
	}
	
	private static int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProgramas";
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
