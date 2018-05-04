
package modelo.investigacion;

import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProrroga;
import modelo.investigacion.IProyecto;
import modelo.investigacion.ISubsidio;
import persistencia.ManejoDatos;

public class GestorProyecto {
    public GestorProyecto() {
    }

    public EstadoOperacion nuevoProyecto(IProyecto proyecto) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Proyecto";
            String campos = "`Id`, `Nombre`, `Descripcion`, `Director`, `Fecha_Presentacion`, `Fecha_Aprobacion`,`Fecha_Inicio`,`Fecha_Fin`,`Programa`,`Estado`";
            String valores = "\'" + proyecto.getId() + "\', \'" + proyecto.getNombre() + "\', \'" + proyecto.getDescripcion() + "\', " + proyecto.getDirector().getLegajo() + ", \'" + proyecto.getFechaPresentacion() + "\', \'" + proyecto.getFechaAprobacion() + "\', \'" + proyecto.getFechaInicio() + "\', \'" + proyecto.getFechaFin() + "\', \'" + proyecto.getPrograma().getId() + "\', \'" + proyecto.getEstado() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El Proyecto se creÃ³ correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        } catch (Exception var6) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
        }
    }

    public EstadoOperacion modificarProyecto(IProyecto proyecto) {
        try {
            ManejoDatos e = new ManejoDatos();
            String tabla = "Proyecto";
            String campos = "`Id` = \'" + proyecto.getId() + "\', `Nombre` = \'" + proyecto.getNombre() + "\', `Descripcion`= \'" + proyecto.getDescripcion() + "\', `Director`= \'" + proyecto.getDirector().getLegajo() + "\', `Fecha_Presentacion` = \'" + proyecto.getFechaPresentacion() + "\', `Fecha_Aprobacion` =\'" + proyecto.getFechaAprobacion() + "\', `Fecha_Inicio` =\'" + proyecto.getFechaInicio() + "\', `Programa` =\'" + proyecto.getPrograma().getId() + "\', `Estado` =\'" + proyecto.getEstado() + "\'";
            String condicion = "`Id` = \'" + proyecto.getId() + "\'";
            e.update(tabla, campos, condicion);
            return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se modificÃ³ correctamente");
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
            return new EstadoOperacion(CodigoEstado.DELETE_OK, "El proyecto se eliminÃ³ correctamente");
        } catch (Exception var3) {
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el Proyecto");
        }
    }

    public List<IProyecto> listarProyecto(IProyecto proyecto) {
        return null;
    }

    public EstadoOperacion AgregarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "Integrante";
            String campos = "`Id`, `TipoDocumento`, `NroDocumento`, `Institucion`, `Cargo`, `HorasSemanales`,`Proyecto`";
            String valores = "\'" + integrante.getId() + "\', \'" + integrante.getTipoDocumento() + "\', \'" + integrante.getNroDocumento() + "\', " + integrante.getInstitucion() + ", \'" + integrante.getCargo() + "\', \'" + integrante.getHorasSemanales() + "\', \'" + proyecto.getId() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El integrante se agrego correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el integrante");
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
            return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El subsidio se agrego correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el subsidio");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el subsidio");
        }
    }

    public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        ManejoDatos md = new ManejoDatos();
        md.delete("`Subcidio`", "IdNew_Table = " + proyecto.getId() + "disposicion= " + subsidio.getDisposicion());
        return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitÃ³ correctamente");
    }

    public EstadoOperacion agregarProrroga(IProyecto proyecto, IProrroga prorroga) {
        try {
            ManejoDatos e = new ManejoDatos();
            String table = "prorroga";
            String campos = "`proyecto`, `disposicion`, `fecha_incio`, `fecha_fin`";
            String valores = "\'" + proyecto.getId() + "\', \'" + prorroga.getDisposicion() + "\', \'" + prorroga.getFechaInicio() + "\', " + prorroga.getFechaFin() + "`";
            e.insertar(table, campos, valores);
            return e.isEstado()?new EstadoOperacion(CodigoEstado.INSERT_OK, "El prorroga se agrego correctamente"):new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el prorroga");
        } catch (Exception var7) {
            return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el prorroga");
        }
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        ManejoDatos md = new ManejoDatos();
        md.delete("`prorroga`", "`proyecto` = " + proyecto.getId() + "disposicion= " + prorroga.getDisposicion());
        return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitÃ³ correctamente");
    }
}
