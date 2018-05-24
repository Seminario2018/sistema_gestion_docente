
package modelo.investigacion;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.docente.Docente;
import persistencia.ManejoDatos;

public class GestorProyecto {

    public GestorProyecto() {}

    public EstadoOperacion nuevoProyecto(IProyecto proyecto, IPrograma programa) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Proyecto";
            String campos = "`Id`, `Nombre`, `Descripcion`, `Director`, `Fecha_Presentacion`, `Fecha_Aprobacion`,`Fecha_Inicio`,`Fecha_Fin`,`Programa`,`Estado`";
            String valores = "\'" + proyecto.getId() + "\', \'" + proyecto.getNombre() + "\', \'" + proyecto.getDescripcion() + "\', " + proyecto.getDirector().getLegajo() + ", \'" + proyecto.getFechaPresentacion() + "\', \'" + proyecto.getFechaAprobacion() + "\', \'" + proyecto.getFechaInicio() + "\', \'" + proyecto.getFechaFin() + "\', \'" + programa.getId() + "\', \'" + proyecto.getEstado() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El Proyecto se creó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        }
    }

    public EstadoOperacion modificarProyecto(IProyecto proyecto, IPrograma programa) {
        try {
            ManejoDatos e = new ManejoDatos();
            String tabla = "Proyecto";
            String campos = "`Id` = \'" + proyecto.getId() + "\', `Nombre` = \'" + proyecto.getNombre() + "\', `Descripcion`= \'" + proyecto.getDescripcion() + "\', `Director`= \'" + proyecto.getDirector().getLegajo() + "\', `Fecha_Presentacion` = \'" + proyecto.getFechaPresentacion() + "\', `Fecha_Aprobacion` =\'" + proyecto.getFechaAprobacion() + "\', `Fecha_Inicio` =\'" + proyecto.getFechaInicio() + "\', `Programa` =\'" + programa.getId() + "\', `Estado` =\'" + proyecto.getEstado() + "\'";
            String condicion = "`Id` = \'" + proyecto.getId() + "\'";
            e.update(tabla, campos, condicion);
            return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se modificó correctamente");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
        }
    }

    public EstadoOperacion eliminarProyecto(IProyecto proyecto) {
        try {
            ManejoDatos e = new ManejoDatos();
            e.delete("`Integrante`", "Proyecto = " + proyecto.getId());
            e.delete("`Prorroga`", "Proyecto = " + proyecto.getId());
            e.delete("`Subcidio`", "Proyecto = " + proyecto.getId());
            e.delete("`Proyecto`", "Id = " + proyecto.getId());
            return new EstadoOperacion(CodigoEstado.DELETE_OK, "El proyecto se eliminó correctamente");
        } catch (Exception var3) {
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el Proyecto");
        }
    }

    public List<IProyecto> listarProyecto(IProyecto proyecto) {

        List<IProyecto> proyectos = new ArrayList<IProyecto>();
        String condicion = "TRUE";
        condicion += this.armarCondicion(proyecto);
        try {
            ManejoDatos md = new ManejoDatos();
            String tabla = "proyecto";
            ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
            for (Hashtable<String, String> reg : res) {
                Proyecto p = new Proyecto();
                p.setId(Integer.parseInt(reg.get("Id")));
                p.setNombre(reg.get("Nombre"));
                p.setDescripcion(reg.get("Descripcion"));
                Docente profesor = new Docente(null, Integer.parseInt(reg.get("Jefe")), null, null, null, null, null);
                p.setDirector(profesor);
                String[] Fecha_Aprovacion = reg.get("Fecha_Aprovacion").split("-");
                p.setFechaAprobacion(LocalDate.of(Integer.parseInt(Fecha_Aprovacion[0]), Integer.parseInt(Fecha_Aprovacion[1]), Integer.parseInt(Fecha_Aprovacion[2])));
                String[] Fecha_Presentacion = reg.get("Fecha_Presentacion").split("-");
                p.setFechaPresentacion(LocalDate.of(Integer.parseInt(Fecha_Presentacion[0]), Integer.parseInt(Fecha_Presentacion[1]), Integer.parseInt(Fecha_Presentacion[2])));
                String[] Fecha_Inicio = reg.get("Fecha_Inicio").split("-");
                p.setFechaAprobacion(LocalDate.of(Integer.parseInt(Fecha_Inicio[0]), Integer.parseInt(Fecha_Inicio[1]), Integer.parseInt(Fecha_Inicio[2])));
                String[] Fecha_Fin = reg.get("Fecha_Fin").split("-");
                p.setFechaPresentacion(LocalDate.of(Integer.parseInt(Fecha_Fin[0]), Integer.parseInt(Fecha_Fin[1]), Integer.parseInt(Fecha_Fin[2])));
                proyectos.add(p);
            }

        } catch (Exception e) {
            proyectos = new ArrayList<IProyecto>();
        }

        return proyectos;
    }

    public EstadoOperacion AgregarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Integrante";
            String campos = "`Id`, `TipoDocumento`, `NroDocumento`, `Institucion`, `Cargo`, `HorasSemanales`,`Proyecto`";
            String valores = "\'" + integrante.getId() + "\', \'" + integrante.getInstitucion() + ", \'" + integrante.getCargo() + "\', \'" + integrante.getHorasSemanales() + "\', \'" + proyecto.getId() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El integrante se agregó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el integrante");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        }
    }

    public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        try {
            ManejoDatos e = new ManejoDatos();
            String tabla = "Integrante";
            e.delete(tabla, "IdNew_Table = \'" + integrante.getId() + "\'");
            return new EstadoOperacion(CodigoEstado.DELETE_OK, "El integrante se eliminÃ³ correctamente");
        } catch (Exception var5) {
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el integrante");
        }
    }

    public EstadoOperacion agregarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Subsidio";
            String campos = "`Proyecto`, `anio`, `disposicion`, `Monto`, `observaciones`";
            String valores = "\'" + proyecto.getId() + "\', \'" + subsidio.getFecha() + "\', \'" + subsidio.getDisposicion() + "\', " + subsidio.getMontoTotal() + ", \'" + subsidio.getObservaciones() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El subsidio se agregó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el subsidio");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el subsidio");
        }
    }

    public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        ManejoDatos md = new ManejoDatos();
        md.delete("`Subcidio`", "IdNew_Table = " + proyecto.getId() + "disposicion= " + subsidio.getDisposicion());
        return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitó correctamente");
    }

    public EstadoOperacion agregarProrroga(IProyecto proyecto, IProrroga prorroga) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "prorroga";
            String campos = "`proyecto`, `disposicion`, `fecha_incio`, `fecha_fin`";
            String valores = "\'" + proyecto.getId() + "\', \'" + prorroga.getDisposicion() + "\', \'" + prorroga.getFechaInicio() + "\', " + prorroga.getFechaFin() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()
                ? new EstadoOperacion(CodigoEstado.INSERT_OK, "El prorroga se agregó correctamente")
                : new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el prorroga");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el prorroga");
        }
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        ManejoDatos md = new ManejoDatos();
        md.delete("`prorroga`", "`proyecto` = " + proyecto.getId() + "disposicion= " + prorroga.getDisposicion());
        return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitó correctamente");
    }

    private String armarCondicion(IProyecto proyecto) {

        String condicion = "TRUE";
        if (proyecto != null) {
            condicion = "";
            // division
            if (proyecto.getId() != 0) {
                condicion += " `Id` = " + proyecto.getId();
            }
            if (proyecto.getNombre() != "") {
                condicion += " `Nombre` = '" + proyecto.getNombre() + "'";
            }
            if (proyecto.getDescripcion() != "") {
                condicion += " `Descripcion` = '" + proyecto.getDescripcion() + "'";
            }
            if (proyecto.getDirector() != null) {
                condicion += " `Director` = '" + proyecto.getDirector() + "'";
            }
            if (proyecto.getFechaPresentacion() != null) {
                condicion += " `Fecha_Presentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()).toString() + "'";
            }
            if (proyecto.getFechaAprobacion() != null) {
                condicion += " `Fecha_Aprovacion` = " + Date.valueOf(proyecto.getFechaAprobacion()).toString() + "'";
            }
            if (proyecto.getFechaInicio() != null) {
                condicion += " `Fecha_Inicio` = '" + Date.valueOf(proyecto.getFechaInicio()).toString() + "'";
            }
            if (proyecto.getFechaFin() != null) {
                condicion += " `Fecha_Fin` = " + Date.valueOf(proyecto.getFechaFin()).toString() + "'";
            }
            if (proyecto.getEstado() != null) {
                condicion += "`Estado`= '" + proyecto.getEstado() + "'";
            }
        }
        return condicion;
    }

    public IProyecto getIProyecto() {
        return new Proyecto();
    }

}
