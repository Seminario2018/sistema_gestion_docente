package modelo.cargo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorCargo {
    public EstadoOperacion nuevoCargo(ICargo cargo) {
          try {
              ManejoDatos e = new ManejoDatos();
              String table = "cargo";
              String campos = "`Codigo`, `Descripcion`, `CargaHoraria`";
              if (cargo.getCodigo() == -1) {
            	  cargo.setCodigo(this.getCodigoMax() + 1);
              }
              String valores = "\'" + cargo.getCodigo() + "\', \'" +cargo.getDescripcion() + "\', \'" + cargo.getCargaHoraria() + "`";
              e.insertar(table, campos, valores);
              return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El cargo se creo correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el cargo");
          }
    }

    public EstadoOperacion modificarCargo(ICargo cargo) {
          try {
              ManejoDatos e = new ManejoDatos();
              String tabla = "cargo";
              String campos = "`Codigo` = \'" + cargo.getCodigo() + "\', `Descripcion` = \'" + cargo.getDescripcion() + "\', `CargaHoraria`= \'" + cargo.getCargaHoraria() + "'";
              String condicion = "`Codigo` = \'" + cargo.getCodigo() + "\'";
              e.update(tabla, campos, condicion);
              return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El cargo se modificÃƒÂ³ correctamente");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el cargo");
          }
    }

    public EstadoOperacion eliminarCargo(ICargo cargo) {
         try {
             ManejoDatos e = new ManejoDatos();           
             e.delete("`cargo`", "Codigo = " + cargo.getCodigo());
             return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se eliminÃƒÂ³ correctamente");
         } catch (Exception var3) {
             return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el cargo");
         }
    }

    public List<ICargo> listarCargo(ICargo cargo) {
        String condicion = "TRUE";
        String tabla = "cargos";
        String campos = "*";
        ArrayList<ICargo> cargos = new ArrayList<ICargo>();
    	if (cargo != null) {
    		condicion  = armarCondicion(cargo);
        }
    	try {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
    		for (Hashtable<String, String> reg : res) {
				Cargo c = new Cargo(Integer.parseInt(reg.get("Codigo")), reg.get("Descripcion"), Integer.parseInt(reg.get("CargaHoraria")));
				cargos.add(c);
    		}
    	}catch(Exception e) {
    		cargos = new ArrayList<ICargo>();
    	}
    	
        return cargos;
    }
    
    public String armarCondicion(ICargo cargo) {
    	String condicion = "";
    	//, , 
    	if (cargo.getCodigo() > -0) {
			condicion += "`Codigo` = " + cargo.getCodigo();
		}
    	if (cargo.getDescripcion() != null) {
    		if (!condicion.equals("")) {
    			condicion += " AND ";
    		}
    		condicion += "`Descripcion` = '" + cargo.getDescripcion() + "'";
    	}
    	if (cargo.getCargaHoraria() > 0) {
    		if (!condicion.equals("")) {
    			condicion += " AND ";
    		}
    		condicion += "`CargaHoraria` = " + cargo.getCargaHoraria();
    	}
    	
    	return condicion;
    }
    
    
    private int getCodigoMax() {
    	int cod = 1;
    	try {
    		ManejoDatos md = new ManejoDatos();
    		ArrayList<Hashtable<String, String>> res = md.select("cargo", "MAX(codigo");
    		for (Hashtable<String, String> reg : res) {
				cod = Integer.parseInt(reg.get("MAX(codigo"));
			}
    	}catch(Exception e){
    		cod = 1;
    	}
    	return cod;
    }
    
    
    
}