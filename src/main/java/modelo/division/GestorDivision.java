package modelo.division;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoDocumento;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.auxiliares.hash.HashSalt;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import modelo.persona.Persona;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import modelo.usuario.Usuario;
import persistencia.ManejoDatos;

public class GestorDivision {

  private String armarCondicion(IDivision division) {

    String condicion = "TRUE";
    if (division != null) {
      condicion = "";
      //division
      if (division.getCodigo() != 0) {
        condicion += " `Codigo` = " + division.getCodigo();
      }
      if (division.getDescripcion() != null) {
        condicion += " `Descripcion` = '" + division.getDescripcion() + "'";
      }
      if (division.getJefe().getLegajo() !=0 ) {
        condicion += " `Jefe` = '" + division.getJefe() + "'";
      }
      if (division.getDisposicion() != null) {
        condicion += " `Disposicion` = '" + division.getDisposicion() + "'";
      }
      if (division.getDispDesde()!= null) {
        condicion += " `Desde` = '" + Date.valueOf(division.getDispDesde()).toString() + "'";
      }
      if (division.getDispHasta() != null) {
        condicion += " `Hasta` = " +  Date.valueOf(division.getDispHasta()).toString() + "'";
      }
    }
    return condicion; 
  }
  
  public EstadoOperacion nuevaDivision(IDivision division) {
        // TODO actualizar BD
         try {
               ManejoDatos e = new ManejoDatos();
               String table = "divisiones";
               String campos = "`Codigo`, `Descripcion`, `Jefe`, `Disposicion`, `Desde`, `Hasta`";
               String valores = "\'" + division.getCodigo() + "\', \'" +division.getDescripcion() + "\', \'" + division.getJefe().getLegajo() + "\', " + division.getDisposicion() + ", \'" + division.getDispDesde() + "\', \'" + division.getDispHasta() + "`";
               e.insertar(table, campos, valores);
               return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "La division se creÃƒÂ³ correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
           } catch (Exception var6) {
               return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear la division");
           }
    }

    public EstadoOperacion modificarDivision(IDivision division) {
        // TODO actualizar BD
        try {
              ManejoDatos e = new ManejoDatos();
              String tabla = "divisiones";
              String campos = "`Codigo` = \'" + division.getCodigo() + "\', `Descripcion` = \'" + division.getDescripcion() + "\', `Jefe`= \'" + division.getJefe().getLegajo() + "\', `Disposicion`= \'" + division.getDisposicion() + "\', `Desde` = \'" + division.getDispDesde() + "\', `Hasta` =\'" + division.getDispHasta() + "'";
              String condicion = "`Codigo` = \'" + division.getCodigo() + "\'";
              e.update(tabla, campos, condicion);
              return new EstadoOperacion(CodigoEstado.UPDATE_OK, "La division se modificÃƒÂ³ correctamente");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
          }
    }

    public EstadoOperacion eliminarDivision(IDivision division) {
        // TODO actualizar BD
      
      try {
             ManejoDatos e = new ManejoDatos();
             e.delete("`Area`", "Responsable = " +division.getCodigo()  );
             e.delete("`Division`", "Codigo = " + division.getCodigo());
             return new EstadoOperacion(CodigoEstado.DELETE_OK, "La division se eliminÃƒÂ³ correctamente");
         } catch (Exception var3) {
             return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar la division");
         }
    }

<<<<<<< HEAD
    public List<IDivision> listarDivision(IDivision division) {
      ArrayList<Hashtable<String, String>> res = new ArrayList<Hashtable<String, String>>();
=======
    /*public List<IDivision> listarDivision(IDivision division) {
        ArrayList<Hashtable<String, String>> res = new ArrayList<Hashtable<String, String>>();
>>>>>>> fd0f5a384dcbd175d753fa033b03d5e4fb15c1df
        ArrayList<IDivision> divisiones = new ArrayList<IDivision>();
      
        String tabla = "Division";
        String campos = "*";
        String condicion = this.armarCondicion(division);
      
      
      
      try {
          ManejoDatos md = new ManejoDatos(); 
          res = md.select(tabla, campos, condicion);
          for (Hashtable<String, String> reg : res) {
        Division div = new Division(
              reg.get("Usuario").toString(),
              new HashSalt(reg.get("Hash").toString(), reg.get("Salt").toString()),
              reg.get("Descripcion").toString(),
              new ArrayList<IRol>()
            );
        
        GestorPersona gp = new GestorPersona();
        Persona p = new Persona();
        p.setTipoDocumento(TipoDocumento.getTipo(new TipoDocumento(Integer.parseInt(reg.get("TipoDocumento")), null)));
        p.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
        p = (Persona) gp.listarPersonas(p).get(0);
        
        divisiones.add(div);        
        
      }
          return divisiones;
        }catch(Exception e) {
          return divisiones;
        }
        
    }*/
    
    
  
  
}