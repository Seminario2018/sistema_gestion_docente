package modelo.auxiliares;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import persistencia.ManejoDatos;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 17 de abr. de 2018
 */
public class EstadoCargo {

    private int id;
    private String descripcion;

    public EstadoCargo(
        int id,
        String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public EstadoCargo() {
    	this.id = -1;
    	this.descripcion = null;
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
    	if(this.existe()) {
    		this.actualizar();
    	}else {
    		this.insertar();
    	}
    }

	private void insertar() {
		this.setId(EstadoCargo.getMaxID()+1);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosCargos";
			String campos = "idestadocargo, Descripcion";			
			String valores = this.getId() + ", '" + this.getDescripcion() + "'";
			
			md.insertar(tabla, campos, valores);
			
		}catch (Exception e){
			
		}
		
	}

	private void actualizar() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosCargos";
			String campos = "Descripcion = '" + this.getDescripcion() + "'";
			String condicion = "idestadocargo = " + this.getId();
			
			md.update(tabla, campos, condicion);
			
		}catch (Exception e){
			
		}
		
	}

	private boolean existe() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosCargos";
			String campos = "idestadocargo";
			String condicion = "idestadocargo = " + this.getId();
			
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
     * @return la lista de estados de los cargos de la BD
     */
    public static List<EstadoCargo> getLista() {
        List<EstadoCargo> listaEstados = new ArrayList<EstadoCargo>();

        ManejoDatos md = new ManejoDatos();
        List<Hashtable<String, String>> filas = md.select("EstadosCargos", "*");
        for (Hashtable<String, String> fila : filas) {
            listaEstados.add(new EstadoCargo(Integer.parseInt(fila.get("idestadocargo")), fila.get("Descripcion")));
        }
        return listaEstados;
    }

    public static EstadoCargo getEstadoCargo(EstadoCargo estado) {
    	try {
    		ManejoDatos md = new ManejoDatos();
    		String tabla = "EstadosCargos";
    		String campos = "Descripcion";
    		String condicion = "idestadocargo = " + estado.getId();
    		
    		List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
    		estado.setDescripcion(res.get(0).get("Descripcion"));
    		
    		return estado;
    	}catch (Exception e) {
    		return new EstadoCargo();
    	}
    }
    
    public static int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "EstadosCargos";
			String campos = "Max(idestadocargo)";
			
			ArrayList<Hashtable<String, String>> res = md.select(tabla, campos);
			int maxID = Integer.parseInt(res.get(0).get("Max(idestadocargo)"));
			return maxID;
			
			
		}catch (Exception e){
			return 0;
		}

	}
    
    
    @Override public String toString() {
        return this.descripcion;
    }
}
