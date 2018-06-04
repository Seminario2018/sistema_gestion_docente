package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoPersona {

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
    

	private void actualizar() {
		try {
			ManejoDatos  md = new ManejoDatos();
			String tabla = "EstadosPersonas";
			String campos = "Descripcion = '"+this.descripcion + "'";
			String condicion = "idEstado = " + this.getId();
			
			md.update(tabla, campos, condicion);

		}catch (Exception e) {
			
		}
		
	}
	
	
	private void insertar() {
		try {
			this.setId(EstadoPersona.getMaxID() + 1);
			ManejoDatos  md = new ManejoDatos();
			String tabla = "EstadosPersonas";
			String campos = "idEstado, Descripcion";
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e) {
			
		}
	}

	private boolean existe() {
		try {
			ManejoDatos  md = new ManejoDatos();
			String tabla = "EstadosPersonas";
			String campos = "idEstado";
			String condicion = "Descripcion = '" + this.getDescripcion() + "'";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);

			if (!res.isEmpty()) {
				this.id = Integer.parseInt(res.get(0).get("idEstado"));
				return true;
			}else {
				return false;
			}
			
			
		}catch (Exception e) {
			return false;
		}
		
	}

	/**
	 * @return la lista de estados del docente de la BD
	 */
	public static List<EstadoPersona> getLista() {
		List<EstadoPersona> estados = new ArrayList<EstadoPersona>();
		try {
			ManejoDatos  md = new ManejoDatos();		
			String tabla = "EstadosPersonas";
			String campos = "*";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			for (Hashtable<String, String> reg : res) {
				EstadoPersona estado =  new EstadoPersona();
				estado.setId(Integer.parseInt(reg.get("idEstado")));
				estado.setDescripcion("Descripcion");
				
				estados.add(estado);
			}
			
			
		}catch (Exception e) {
			estados = new ArrayList<EstadoPersona>();
		}
		
		return estados;
	}

	
	public static EstadoPersona getEstado(EstadoPersona estado) {
		try {
			ManejoDatos  md = new ManejoDatos();
			String tabla = "EstadosPersonas";
			String campos = "*";
			String condicion = "idEstado = " + estado.getId();
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			
			estado.setDescripcion(res.get(0).get("Descripcion"));
			
			return estado;
			
		}catch (Exception e) {
			return new EstadoPersona();
		}
	}
	
	@Override
	public String toString() {
	    return this.descripcion;
	}
	
	public static int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosPersonas";
			String campos = "Max(idEstado)";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			int maxID = Integer.parseInt(res.get(0).get("Max(idEstado)"));
			return maxID;
			
			
		}catch (Exception e){
			return 0;
		}

	}
}
