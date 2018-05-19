package modelo.division;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.sun.webkit.graphics.Ref;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoDocumento;
import modelo.docente.Docente;
import modelo.docente.ICargoDocente;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.persona.IPersona;
import modelo.persona.Persona;
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
               return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "La division se creÃƒÆ’Ã‚Â³ correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
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
              return new EstadoOperacion(CodigoEstado.UPDATE_OK, "La division se modificÃƒÆ’Ã‚Â³ correctamente");
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
             return new EstadoOperacion(CodigoEstado.DELETE_OK, "La division se eliminÃƒÆ’Ã‚Â³ correctamente");
         } catch (Exception var3) {
             return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar la division");
         }
    }

    public ArrayList<IDivision> listarDivision(IDivision division) {
        // TODO Completar
      ArrayList<IDivision> divisiones = new ArrayList<IDivision>();
    
    try {
      ManejoDatos md = new ManejoDatos();
      String table = "divisiones";
      String campos = "*";
      String condicion =  this.armarCondicion(division);
      
      ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);
      
      for (Hashtable<String, String> reg : res) {
        Division d = new Division();
        d.setCodigo(Integer.parseInt(reg.get("Codigo")));//(TipoDocumento.getTipo(new TipoDocumento(Integer.parseInt(reg.get("TipoDocumento")), null)));
        d.setDescripcion(reg.get("Descripcion"));//;Integer.parseInt(reg.get("NroDocumento")));
        Docente profesor =new Docente(null,Integer.parseInt(reg.get("Jefe")),null,null,null,null,null);
        d.setJefe(profesor);
        
        d.setDisposicion(reg.get("Disposicion"));
        
        String[] desde = reg.get("Desde").split("-");
        d.setDispDesde(LocalDate.of(Integer.parseInt(desde[0]),Integer.parseInt(desde[1]), Integer.parseInt(desde[2])));
        String[] hasta = reg.get("Desde").split("-");
        d.setDispDesde(LocalDate.of(Integer.parseInt(hasta[0]),Integer.parseInt(hasta[1]), Integer.parseInt(hasta[2])));
        divisiones.add(d);;
    }

    }catch (Exception e) {
      divisiones = new ArrayList<IDivision>();
    }
    
    
    return divisiones;
    }
    
}