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
import persistencia.ManejoDatos;

public class GestorDivision {

    private String armarCondicion(IDivision division) {
        String condicion = "TRUE";
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
            if (division.getJefe().getLegajo() != 0) {
                if (!condicion.equals("")) {
                    condicion += " AND ";
                }
                condicion += " `Jefe` = '" + division.getJefe() + "'";
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

    public EstadoOperacion nuevaDivision(IDivision division) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Divisiones";
            String campos =
                "`Codigo`, `Descripcion`, `Jefe`, `Disposicion`, `Desde`, `Hasta`";
            String valores =
                "'" + division.getCodigo() + "', '" + division.getDescripcion()
                    + "', "
                    + "'" + division.getJefe().getLegajo() + "', "
                    + division.getDisposicion() + ", "
                    + "'" + Date.valueOf(division.getDispDesde()) + "', "
                    + "'" + Date.valueOf(division.getDispHasta()) + "'";
            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "La division se creó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear la division");
        }
    }

    public EstadoOperacion modificarDivision(IDivision division) {
        try {
            ManejoDatos e = new ManejoDatos();
            String tabla = "Divisiones";
            String campos =
                "`Descripcion` = '" + division.getDescripcion() + "', "
                    + "`Jefe`= '" + division.getJefe().getLegajo() + "', "
                    + "`Disposicion`= '" + division.getDisposicion() + "', "
                    + "`Desde` = '" + Date.valueOf(division.getDispDesde())
                    + "', "
                    + "`Hasta` = '" + Date.valueOf(division.getDispHasta())
                    + "'";
            String condicion = "`Codigo` = '" + division.getCodigo() + "'";
            e.update(tabla, campos, condicion);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.UPDATE_OK, "La division se modificó correctamente")
                : new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
        }
    }

    public EstadoOperacion eliminarDivision(IDivision division) {
        try {
            ManejoDatos e = new ManejoDatos();
            e.delete("`Divisiones`", "Codigo = " + division.getCodigo());
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.DELETE_OK, "La division se eliminó correctamente")
                : new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar la division");
        } catch (Exception var3) {
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
                GestorDocente gd = new GestorDocente();

                Docente profesor =
                    new Docente(null, Integer.parseInt(reg.get("Jefe")), null, null, null, null, null);
                profesor = (Docente) gd.listarDocente(profesor).get(0);
                d.setJefe(profesor);

                d.setDisposicion(reg.get("Disposicion"));

                String[] desde = reg.get("Desde").split("-");
                d.setDispDesde(LocalDate.of(Integer.parseInt(desde[0]), Integer.parseInt(desde[1]), Integer.parseInt(desde[2])));
                String[] hasta = reg.get("Desde").split("-");
                d.setDispDesde(LocalDate.of(Integer.parseInt(hasta[0]), Integer.parseInt(hasta[1]), Integer.parseInt(hasta[2])));
                divisiones.add(d);;
            }

        } catch (Exception e) {
            e.printStackTrace();
            divisiones = new ArrayList<IDivision>();
        }

        return divisiones;
    }

}
