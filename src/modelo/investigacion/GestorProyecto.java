package modelo.investigacion;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorProyecto {
    public EstadoOperacion nuevoProyecto(IProyecto proyecto) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El proyecto se creó correctamente");
    }

    public EstadoOperacion modificarProyecto(IProyecto proyecto) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                "El proyecto se modificó correctamente");
    }

    public EstadoOperacion eliminarProyecto(IProyecto proyecto) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El proyecto se eliminó correctamente");
    }

    public List<IProyecto> listarProyecto(IProyecto proyecto) {
        if (proyecto != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }

    public EstadoOperacion agregarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El cargo se agregó correctamente");
    }

    public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El cargo se quitó correctamente");
    }

    public EstadoOperacion agregarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El cargo se agregó correctamente");
    }

    public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El cargo se quitó correctamente");
    }

    public EstadoOperacion agregarProrroga(IProyecto proyecto, IProrroga prorroga) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El cargo se agregó correctamente");
    }

    public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El cargo se quitó correctamente");
    }
}