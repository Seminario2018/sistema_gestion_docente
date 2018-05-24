package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoProyecto {

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
		this.setId(EstadoProyecto.getMaxID() + 1);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProyectos";
			String campos = "idEstadoProyecto, Descripcion";			
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e){
			
		}
		
	}


	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProyectos";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "idEstadoProyecto = " + this.getId();
			
			md.update(tabla, campos, condicion);
			
		}catch (Exception e){
			
		}
		
	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProyectos";
			String campos = "idEstadoProyecto";
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
    public static List<EstadoProyecto> getLista() {
        try {
        	List<EstadoProyecto> estados = new ArrayList<EstadoProyecto>();
        	ManejoDatos md = new ManejoDatos();
        	String tabla = "EstadosProyectos";
        	String campos = "*";
        	
        	ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
        	for (Hashtable<String, String> reg : res) {
				EstadoProyecto estado = new EstadoProyecto();
				estado.setId(Integer.parseInt(reg.get("idEstadoProyecto")));
				estado.setDescripcion(reg.get("Descripcion"));
				estados.add(estado);
			}
        	
        	return estados;        	
        }catch (Exception e) {
        	return new ArrayList<EstadoProyecto>();
        }
    }

    
    public static int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosProyectos";
			String campos = "Max(idEstadoProyecto)";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			int maxID = Integer.parseInt(res.get(0).get("Max(idEstadoProyecto)"));
			
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
