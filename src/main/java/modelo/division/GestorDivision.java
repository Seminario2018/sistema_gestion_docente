package modelo.division;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorDivision {

    private String armarCondicion(IDivision div) {
        String condicion = "TRUE";
        IDivisiong division = (IDivisiong) div;
        if (division != null) {
            condicion = "";
            if (division.getCodigo() != null) {
                condicion += " `Codigo` = '" + division.getCodigo() + "'";
            }
            if (division.getDescripcion() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion +=
                     " `Descripcion` = '" + division.getDescripcion() + "'";
            }
            if (division.getJefe2() != null && division.getJefe2().getLegajo() != -1) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Jefe` = " + division.getJefe2().getLegajo();
            }
            if (division.getDisposicion() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion +=
                    " `Disposicion` = '" + division.getDisposicion() + "'";
            }
            if (division.getDispDesde() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion +=
                    " `Desde` = '"
                        + Date.valueOf(division.getDispDesde()).toString()
                        + "'";
            }
            if (division.getDispHasta() != null) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion +=
                    " `Hasta` = "
                        + Date.valueOf(division.getDispHasta()).toString()
                        + "'";
            }
        }
        return condicion;
    }

    public EstadoOperacion nuevaDivision(IDivision div) {
        try {
        	IDivisiong division = (IDivisiong) div;

            ManejoDatos md = new ManejoDatos();
            String table = "Divisiones";
            String campos = "`Codigo`, `Descripcion`";
            String valores = "'" + division.getCodigo() + "', '" + division.getDescripcion() + "'";
            //`Jefe`, `Disposicion`, `Desde`, `Hasta`
            if (division.getJefe2() != null) {
            	campos += ", Jefe";
            	valores += ", " + division.getJefe2().getLegajo();
            }
            if (division.getDisposicion() != null && !division.getDisposicion().equals("")) {
            	campos += ", Disposicion";
            	valores += ", '" + division.getDisposicion() + "'";
            }
            if (division.getDispDesde() != null) {
            	campos += ", Desde";
            	valores += ", '" + Date.valueOf(division.getDispDesde()) + "'";
            }
            if (division.getDispHasta() != null) {
            	campos += ", Hasta";
            	valores += ", '" + Date.valueOf(division.getDispHasta()) + "'";
            }
            md.insertar(table, campos, valores);
            return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "La division se creó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear la division");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear la division");
        }
    }

    public EstadoOperacion modificarDivision(IDivision division) {
        try {

            ManejoDatos md = new ManejoDatos();
            String tabla = "Divisiones";
            String campos = "`Descripcion` = '" + division.getDescripcion() + "'";
            if (division.getJefe() != null) {
            	campos += ", `Jefe`= " + division.getJefe().getLegajo();
            }
            if (division.getDisposicion() != null && !division.getDisposicion().equals("")) {
            	campos += ", Disposicion = '" + division.getDisposicion() + "'";
            }
            if (division.getDispDesde() != null) {
            	campos += ", Desde = '" + division.getDisposicion() + "'";
            }
            if (division.getDispHasta() != null) {
            	campos += ", Hasta= '" + Date.valueOf(division.getDispHasta()) +  "'";
            }
            String condicion = "Codigo = " + division.getCodigo() + "'";
            md.update(tabla, campos, condicion);
            return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.UPDATE_OK, "La division se modificó correctamente")
                : new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
        }
    }

    public EstadoOperacion eliminarDivision(IDivision division) {
        try {
            ManejoDatos md = new ManejoDatos();
            md.delete("`Divisiones`", "Codigo = '" + division.getCodigo() + "'");
            return md.isEstado()
                ? new EstadoOperacion(CodigoEstado.DELETE_OK, "La division se eliminó correctamente")
                : new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar la division");
        } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar la division");
        }
    }

    public List<IDivision> listarDivision(IDivision division) {
        ArrayList<IDivision> divisiones = new ArrayList<IDivision>();

        try {
            ManejoDatos md = new ManejoDatos();
            String table = "Divisiones";
            String campos = "*";
            String condicion = this.armarCondicion(division);

            ArrayList<Hashtable<String, String>> res =
                md.select(table, campos, condicion);

            for (Hashtable<String, String> reg : res) {
                Division d = new Division();
                d.setCodigo(reg.get("Codigo"));
                d.setDescripcion(reg.get("Descripcion"));

				if (!reg.get("Disposicion").equals("")) {
					d.setDisposicion(reg.get("Disposicion"));
				}

				if (!reg.get("Desde").equals("")) {
				    LocalDate desde = Date.valueOf(reg.get("Desde")).toLocalDate();
					d.setDispDesde(desde);
				}

				if (!reg.get("Hasta").equals("")) {
				    LocalDate hasta = Date.valueOf(reg.get("Hasta")).toLocalDate();
					d.setDispHasta(hasta);
				}

				divisiones.add(d);;
            }

        } catch (Exception e) {
            divisiones = new ArrayList<IDivision>();
        }

        return divisiones;
    }


    public static boolean existeDivision(IDivision division) {
    	String tabla = "Divisiones";
		if (division == null || division.getCodigo() == null) {
			return false;
		}
		String condicion = "Codigo = '" + division.getCodigo() + "'";
		try {
			ManejoDatos md = new ManejoDatos();
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			return !(res.isEmpty());

		}catch (Exception e) {
			return false;
		}
    }

}
