package modelo.informe;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.docente.Docente;
import modelo.investigacion.IProyecto;
import modelo.investigacion.Programa;
import modelo.investigacion.Proyecto;
import persistencia.ManejoDatos;
import vista.controladores.Informes;

public class GestorInforme {
	
	   public EstadoOperacion nuevoInforme(TipoInforme informe) {
	        try {
	            ManejoDatos e = new ManejoDatos();
	            String table = "TipoInforme";
	            String campos = "`IdTipoInforme`, `Nombre`, `from`, `TipoInformecol`";
	            String valores = "\'" + informe.getId() + "\', \'" + informe.getNombre()+ "\', \'" + informe.getFromString() + "\', " + informe.getColumnas()+ "`";
	            e.insertar(table, campos, valores);
	            return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El Informe se creÃƒÂ³ correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
	        } catch (Exception var6) {
	            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Informe");
	        }
	    }
	   
	   
	   public EstadoOperacion modificarProyecto(TipoInforme informe) {
	        try {
	            ManejoDatos e = new ManejoDatos();
	            String tabla = "TipoInforme";
	            String campos = "`IdTipoInforme` = \'" + informe.getId() + "\', `Nombre` = \'" + informe.getNombre() + "\', `from`= \'" +informe.getFromString()+ "\', `t`= \'" +informe.getFromString()+ "\'";
	            String condicion = "`IdTipoInforme` = \'" + informe.getId() + "\'";
	            e.update(tabla, campos, condicion);
	            return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El informe se modificÃƒÂ³ correctamente");
	        } catch (Exception var6) {
	            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el informe");
	        }
	    }
	   
	   
	   public EstadoOperacion eliminarProyecto(TipoInforme informe) {
	        try {
	            ManejoDatos e = new ManejoDatos();
	           
	            e.delete("`TipoInforme`", "Id = " + informe.getId());
	            return new EstadoOperacion(CodigoEstado.DELETE_OK, "El informe se eliminÃƒÂ³ correctamente");
	        } catch (Exception var3) {
	            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el informe");
	        }
	    }
	
	   
	   
	   
	   public ArrayList<ITipoInforme> listarProyecto(ITipoInforme informe) {
		   
   		ArrayList<ITipoInforme> informes = new ArrayList<ITipoInforme>();
   		String condicion = "TRUE";
   		condicion += this.armarCondicion(informe);
   		try {
   			ManejoDatos md = new ManejoDatos();
   			String tabla = "TipoInforme";
   			ArrayList<Hashtable<String,String>> res = md.select(tabla, "*", condicion);
   			for (Hashtable<String, String> reg : res) {
   				TipoInforme t=new TipoInforme();
   				t.setId(Integer.parseInt(reg.get("Id")));
   				t.setNombre(reg.get("Nombre"));
   				t.setFromString(reg.get("from"));
   				//t.setColumnas(columnas);
   			
   				
   				informes.add(t);	
   			}
   					
   		}catch (Exception e) {
   			informes = new ArrayList<ITipoInforme>();
   		}
   		
   		
   		return informes;
   	}
   	
	   
	   
	   
	   private String armarCondicion(ITipoInforme informe) {

	        String condicion = "TRUE";
	        if (informe != null) {
	          condicion = "";
	          //division
	          if (informe.getId() != 0) {
	            condicion += " `IdTipoInforme` = " + informe.getId();
	          }
	          if (informe.getNombre() != "") {
	            condicion += " `Nombre` = '" + informe.getNombre() + "'";
	          }
	          if (informe.getFromString() !="" ) {
	            condicion += " `from` = '" + informe.getFromString() + "'";
	          }
	          
	          if (informe.getColumnas()!= null) {
	            condicion += " `TipoInformecol` = '" +informe.getColumnas() + "'";
	          }
	        
	            
	         
	          
	          
	          
	          
	        }
	        return condicion;
	      }
	    

}