package modelo.division;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorArea {

    public EstadoOperacion nuevaArea(IArea area) {
        // TODO actualizar BD
          try {
              ManejoDatos e = new ManejoDatos();
              String table = "areas";
              String campos = "`Codigo`, `Descripcion`, `Division`,`Responsable`, `Disposicion`,`Desde`,`Hasta`,`SubAreaDe`";
              String valores = "\'" + area.getCodigo() + "\', \'" +area.getDescripcion() + "\', \'" + area.getDivision() +"\',\'"+area.getDocenteResponsable().getLegajo()+"\',\' "+area.getDispDesde() +"\',\' "+area.getDispHasta() +"\',\' "+area.getAreaDe().getCodigo() +"`";
              e.insertar(table, campos, valores);
              return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El Area se creo correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Area");
          }
    }

    public EstadoOperacion modificarArea(IArea area) {
        // TODO actualizar BD
          try {
              ManejoDatos e = new ManejoDatos();
              String tabla = "Areas";
              String campos = "`Codigo` = \'" + area.getCodigo() + "\', `Descripcion` = \'" + area.getDescripcion() + "\', `Division`= \'" + area.getDivision().getCodigo() +"\', `Responsable`= \'" + area.getDocenteResponsable().getLegajo()+"\'`Desde`"+area.getDispDesde()+"\',`Hasta`"+area.getDispHasta()+"\',`SubArea_De`=\'"+area.getAreaDe().getCodigo()+"'";
              String condicion = "`Codigo` = \'" + area.getCodigo() + "\'";
              e.update(tabla, campos, condicion);
              return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El Area se modificÃƒÂ³ correctamente");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Area");
          }
    }

    public EstadoOperacion eliminarArea(IArea area) {
        // TODO actualizar BD
         try {
             ManejoDatos e = new ManejoDatos();
           
             e.delete("`areas`", "Codigo = " + area.getCodigo());
             return new EstadoOperacion(CodigoEstado.DELETE_OK, "El area se eliminÃƒÂ³ correctamente");
         } catch (Exception var3) {
             return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el area");
         }
        
        
    }

    public List<IArea> listarArea(IArea area) {
        if (area != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }

}