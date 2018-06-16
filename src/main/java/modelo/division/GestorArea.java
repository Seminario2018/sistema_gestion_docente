package modelo.division;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorArea {

    private String armarCondicion(IArea a) {

    	IAreag area = (IAreag) a;

        String condicion = "TRUE";
        if (area != null) {
            if (!area.getCodigo().equals("")) {
                condicion += " AND `Codigo` = '" + area.getCodigo() + "'";
            }
            if (!area.getDescripcion().equals("")) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Descripcion` = '" + area.getDescripcion() + "'";
            }
            if (area.getDivision2() != null && area.getDivision2().getCodigo() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Division` = '" + area.getDivision2().getCodigo() + "'";
            }
            if (area.getDocenteResponsable2() != null && area.getDocenteResponsable2().getLegajo() != -1) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Responsable` = " + area.getDocenteResponsable2().getLegajo();
            }
            if (!area.getDisposicion().equals("")) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Disposicion` = '" + area.getDisposicion() + "'";
            }
            if (area.getDispDesde() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Desde` = '" + Date.valueOf(area.getDispDesde()).toString() + "'";
            }

            if (area.getDispHasta() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Hasta` = '" + Date.valueOf(area.getDispHasta()).toString() + "'";
            }

            if (area.getAreaDe2() != null ) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Subarea_De` = '" + area.getAreaDe2().getCodigo() + "'";
            }
        }
        return condicion;
    }

    public EstadoOperacion nuevaArea(IArea a) {

        try {
        	IAreag area = (IAreag) a;

            ManejoDatos md = new ManejoDatos();
            String table = "Areas";
            String campos = "`Codigo`, `Division`";
            String valores = "'" + area.getCodigo() + "', '" + area.getDivision2().getCodigo() + "'";
            if (area.getDescripcion() != null && !area.getDescripcion().equals("")) {
                campos += ", Descripcion";
                valores += ", '" + area.getDocenteResponsable2().getLegajo() + "'";
            }
            if (area.getDocenteResponsable2() != null) {
                campos += ", Responsable";
                valores += ", " + area.getDocenteResponsable2().getLegajo();
            }
            if (area.getDisposicion() != null && !area.getDisposicion().equals("")) {
                campos += ", Disposicion";
                valores += ", '" + area.getDisposicion() + "'";
            }
            if (area.getDispDesde() != null) {
                campos += ", Desde";
                valores += ", '" + Date.valueOf(area.getDispDesde()) + "'";
            }
            if (area.getDispHasta() != null) {
                campos += ", Hasta";
                valores += ", '" + Date.valueOf(area.getDispHasta()) + "'";
            }
            if (area.getAreaDe2() != null) {
                campos += ", `SubAreaDe`";
                valores += ", '" + area.getAreaDe2().getCodigo() + "'";
            }
            md.insertar(table, campos, valores);
            return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El Area se creó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Area");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Area");
        }
    }

    public EstadoOperacion modificarArea(IArea area) {
        try {
            ManejoDatos md = new ManejoDatos();

            String tabla = "Areas";
            String campos = "";
            
            if (area.getDivision() != null) {
            	campos += "`Division`= '" + area.getDivision().getCodigo() + "'";
            }
            if (area.getDescripcion() != null && !area.getDescripcion().equals("")) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "`Descripcion` = '" + area.getDescripcion() + "'";
            }
            if (area.getDocenteResponsable() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "`Responsable`= " + area.getDocenteResponsable().getLegajo();
            }
            if (area.getDispDesde() != null) {
                campos += "`Desde`" + Date.valueOf(area.getDispDesde()) + "',";
            }
            if (area.getDispHasta() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "`Hasta` = '" + Date.valueOf(area.getDispHasta()) + "'";
            }
            if (area.getAreaDe() != null) {
            	if (!campos.equals("")) {
            		campos += ", ";
            	}
                campos += "`SubArea_De`= '" + area.getAreaDe().getCodigo() + "'";
            }

            String condicion = "`Codigo` = '" + area.getCodigo() + "'";
            md.update(tabla, campos, condicion);
            return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.UPDATE_OK, "El Área se modificó correctamente")
                : new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Área");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el Área");
        }
    }

    public EstadoOperacion eliminarArea(IArea area) {
        try {
            ManejoDatos md = new ManejoDatos();
            md.delete("`Areas`", "Codigo = '" + area.getCodigo() + "'");
            return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.DELETE_OK, "El area se eliminó correctamente")
                : new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el area");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el area");
        }

    }

    public List<IArea> listarAreas(IArea area) {
        ArrayList<IArea> areas = new ArrayList<IArea>();

        try {
            ManejoDatos md = new ManejoDatos();
            String table = "Areas";
            String campos = "*";
            String condicion = this.armarCondicion(area);

            ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);

            for (Hashtable<String, String> reg : res) {
                Area a = new Area();
                a.setCodigo(reg.get("Codigo"));
                a.setDescripcion(reg.get("Descripcion"));

                a.setDisposicion(reg.get("Disposicion"));

                // Fecha desde
                if (!reg.get("Desde").equals("")) {
                    LocalDate desde = LocalDate.parse(reg.get("Desde"));
                    a.setDispDesde(desde);
                }

                // Fecha hasta
                if (!reg.get("Hasta").equals("")) {
                    LocalDate hasta = LocalDate.parse(reg.get("Hasta"));
                    a.setDispDesde(hasta);
                }

                areas.add(a);
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

        } catch (Exception e) {
            return false;
        }
    }

	public IArea getIArea() {
		return new Area();
	}

}
