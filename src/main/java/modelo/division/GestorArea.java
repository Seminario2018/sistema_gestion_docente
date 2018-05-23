package modelo.division;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.docente.Docente;
import modelo.docente.GestorDocente;
import persistencia.ManejoDatos;

public class GestorArea {

	private String armarCondicion(IArea area) {

		String condicion = "TRUE";
		if (area != null) {
			condicion = "";
			if (!area.getCodigo().equals("") ) {
				condicion += " `Codigo` = " + area.getCodigo();
			}
			if (area.getDescripcion() != null) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Descripcion` = '" + area.getDescripcion() + "'";
			}
			if (area.getDivision() !=null ) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Division` = '" + area.getDivision().getCodigo()+ "'";
			}
			if (area.getDocenteResponsable() != null) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Responsable` = '" + area.getDocenteResponsable().getLegajo() + "'";
			}
			if (!area.getDisposicion().equals("")) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Disposicion` = '" +area.getDisposicion()+"'";
			}
			if (area.getDispDesde() != null) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Desde` = " +  Date.valueOf(area.getDispDesde()).toString() + "'";
			}

			if (area.getDispHasta() != null) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Hasta` = " +  Date.valueOf(area.getDispHasta()).toString() + "'";
			}

			if (area.getAreaDe() != null) {
				if (!condicion.equals("")) {
					condicion += " AND ";
				}
				condicion += " `Subarea_De` = " +  Date.valueOf(area.getDispHasta()).toString() + "'";
			}
		}
		return condicion;
	}


	public EstadoOperacion nuevaArea(IArea area) {

		try {
			ManejoDatos e = new ManejoDatos();
			String table = "areas";
			String campos = "`Codigo`, `Descripcion`, `Division`,`Responsable`, `Disposicion`,`Desde`,`Hasta`,`SubAreaDe`";
			String valores = "'" + area.getCodigo() + "', '" +area.getDescripcion() + "', '" + area.getDivision() +"', "
					+ "'"+area.getDocenteResponsable().getLegajo()+"', '"+area.getDispDesde() +"', "
					+ "'"+area.getDispHasta() +"', "
					+ "'"+area.getAreaDe().getCodigo() +"'";
			e.insertar(table, campos, valores);
			return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El Area se creó correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		} catch (Exception var6) {
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Area");
		}
	}

	public EstadoOperacion modificarArea(IArea area) {
		try {
			ManejoDatos e = new ManejoDatos();
			
			if (!GestorDivision.existeDivision(area.getDivision())){
				GestorDivision gd = new GestorDivision();
				gd.nuevaDivision(area.getDivision());
			}
			
			if (area.getDocenteResponsable() != null && !GestorDocente.existeDocente(area.getDocenteResponsable())) {
				GestorDocente gd = new GestorDocente();
				gd.nuevoDocente(area.getDocenteResponsable());
			}
			
			
			String tabla = "Areas";
			String campos = "`Descripcion` = '" + area.getDescripcion() + "', `Division`= '" + area.getDivision().getCodigo() +"', "
					+ "`Responsable`= '" + area.getDocenteResponsable().getLegajo()+"', `Desde`"+area.getDispDesde()+"', "
					+ "`Hasta`"+area.getDispHasta()+"',`SubArea_De`= '"+ area.getAreaDe().getCodigo()+"'";
			String condicion = "`Codigo` = '" + area.getCodigo() + "'";
			e.update(tabla, campos, condicion);
			return e.isEstado()?new EstadoOperacion(CodigoEstado.UPDATE_OK, "El Area se modificó correctamente"):
				new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Area");
		} catch (Exception var6) {
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Area");
		}
	}

	public EstadoOperacion eliminarArea(IArea area) {
		try {
			ManejoDatos e = new ManejoDatos();
			e.delete("`Areas`", "Codigo = " + area.getCodigo());
			return e.isEstado()?new EstadoOperacion(CodigoEstado.DELETE_OK, "El area se eliminó correctamente"):
				new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el area");
		} catch (Exception var3) {
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el area");
		}


	}

	public List<IArea> listarArea(IArea area) {
		ArrayList<IArea> areas = new ArrayList<IArea>();

		try {
			ManejoDatos md = new ManejoDatos();
			String table = "areas";
			String campos = "*";
			String condicion =  this.armarCondicion(area);

			ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);

			for (Hashtable<String, String> reg : res) {
				Area a = new Area();
				a.setCodigo(reg.get("Codigo"));
				a.setDescripcion(reg.get("Descripcion"));
				Area sa = new Area(reg.get("SubAreaDe"),null,null,null,null,null,null,null);
				Docente responsable = new Docente(null,Integer.parseInt(reg.get("Responsable")),null,null,null,null,null);
				GestorDocente gd = new GestorDocente();
				responsable = (Docente) gd.listarDocente(responsable).get(0);

				GestorDivision gestorDivision = new GestorDivision();
				Division d = (Division) gestorDivision.listarDivision(null).get(0);

				a.setDocenteResponsable(responsable);
				a.setDivision(d);
				a.setDisposicion(reg.get("Disposicion"));

				LocalDate desde = Timestamp.valueOf(reg.get("Desde")).toLocalDateTime().toLocalDate();
				LocalDate hasta = Timestamp.valueOf(reg.get("Hasta")).toLocalDateTime().toLocalDate();

				a.setDispDesde(desde);
				a.setDispDesde(hasta);
				a.setAreaDe(sa);
				areas.add(a);;
			}

		} catch (Exception e) {
		    e.printStackTrace();
			areas = new ArrayList<IArea>();
		}
		return areas;
	}
	
	
	public static boolean existeArea(IArea area) {
    	String tabla = "Areas";
		if (area == null || area.getCodigo() == null) {
			return false;
		}
		String condicion = "Codigo = '" + area.getCodigo() + "'";
		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			return !(res.isEmpty());

		}catch (Exception e) {
			return false;
		}
    }


}

