package modelo.division;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import persistencia.ManejoDatos;

public class GestorArea {

    public EstadoOperacion nuevaArea(IArea area) {
          try {
              ManejoDatos e = new ManejoDatos();
              String table = "areas";
              String campos = "`Codigo`, `Descripcion`, `Division`,`Responsable`, `Disposicion`,`Desde`,`Hasta`,`SubAreaDe`";
              String valores = "'" + area.getCodigo() + "', '" +area.getDescripcion() + "',"
              		+ " '" + area.getDivision() +"', '"+area.getDocenteResponsable().getLegajo()+"', '"+ Date.valueOf(area.getDispDesde()) +"', "
              		+ "'"+Date.valueOf(area.getDispHasta()) +"', '"+area.getAreaDe().getCodigo() +"'";
              e.insertar(table, campos, valores);
              return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El Area se creo correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Area");
          }
    }

    public EstadoOperacion modificarArea(IArea area) {
          try {
              ManejoDatos e = new ManejoDatos();
              String tabla = "Areas";
              String campos = "`Codigo` = '" + area.getCodigo() + "', `Descripcion` = '" + area.getDescripcion() + "', "
              		+ "`Division`= '" + area.getDivision().getCodigo() +"', `Responsable`= '" + area.getDocenteResponsable().getLegajo()+"', "
              		+ "`Desde` = '"+ Date.valueOf(area.getDispDesde()) +"', `Hasta` = '"+Date.valueOf(area.getDispHasta()) +"', "
              		+ "`SubArea_De`= '"+area.getAreaDe().getCodigo()+"'";
              String condicion = "`Codigo` = '" + area.getCodigo() + "'";
              e.update(tabla, campos, condicion);
              return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El Area se modificadp correctamente");
          } catch (Exception var6) {
              return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Area");
          }
    }

    public EstadoOperacion eliminarArea(IArea area) {
         try {
             ManejoDatos e = new ManejoDatos();
             e.delete("`areas`", "Codigo = " + area.getCodigo());
             return new EstadoOperacion(CodigoEstado.DELETE_OK, "El area se eliminado correctamente");
         } catch (Exception var3) {
             return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el area");
         }
        
        
    }

    public List<IArea> listarArea(IArea area) {
        String tabla = "Area";
    	String campos = "*";
    	String condicion = "TRUE";
    	if (area != null) {
            condicion = this.armarCondicion(area);
        }
        ArrayList<IArea> areas = new ArrayList<IArea>();
        ManejoDatos md = new ManejoDatos();
        try {
        	ArrayList<Hashtable<String,String>> res = md.select(tabla, campos, condicion);
        	for (Hashtable<String, String> reg : res) {
        		//`Codigo`, `Descripcion`, `Division`, `Responsable`, `Disposicion`, `Desde`, `Hasta`, `Subarea_De`
        		Division d = new Division(Integer.parseInt(reg.get("Division")), null, null, null, null, null);
        		d = (Division) new GestorDivision().listarDivision(d).get(0);
        		Docente doc = new Docente();
        		doc.setLegajo(Integer.parseInt(reg.get("Responsable")));
        		doc = (Docente) new GestorDocente().listarDocente(doc).get(0);
        		String[] desde = reg.get("Desde").split("-");
        		String[] hasta = reg.get("Hasta").split("-");
        		Area subAreaDe = null;
				Area a = new Area(reg.get("Codigo"), reg.get("Descripcion"), 
						d, doc, reg.get("Disposicion"), LocalDate.of(Integer.parseInt(desde[0]),
								Integer.parseInt(desde[1]), Integer.parseInt(desde[2])), 
						LocalDate.of(Integer.parseInt(hasta[0]),
								Integer.parseInt(hasta[1]), Integer.parseInt(hasta[2])), subAreaDe);
				areas.add(a);
			}
        	
        }catch (Exception e) {
        	areas = new ArrayList<IArea>();
        }
        return areas;
    }

	private String armarCondicion(IArea area) {
		//`Disposicion`, `Desde`, `Hasta`, `Subarea_De`
		String condicion = "";
		if(area.getCodigo() != null && !area.getCodigo().equals("")) {
			condicion += "Codigo = '" + area.getCodigo() + "'";
		}
		if (area.getDescripcion() != null && !area.getDescripcion().equals("")) {
			condicion += "Descripcion = '" + area.getDescripcion() + "'";
		}
		if (area.getDivision() != null) {
			condicion += "Division = '" + area.getDivision().getCodigo() + "'";
		}
		if (area.getDocenteResponsable() != null) {
			condicion += "Responsable = " + area.getDocenteResponsable().getLegajo();
		}
		if (area.getDisposicion() != null && !area.getDisposicion().equals("")) {
			
		}
		
		return condicion;
	}
}




