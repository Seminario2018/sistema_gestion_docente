package modelo.usuario;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorGrupo {
    public EstadoOperacion nuevoGrupo(IGrupo grupo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El grupo se creó correctamente");
    }

    public EstadoOperacion modificarGrupo(IGrupo grupo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                "El grupo se modificó correctamente");
    }

    public EstadoOperacion eliminarGrupo(IGrupo grupo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El grupo se eliminó correctamente");
    }

    public List<IGrupo> listarGrupo(IGrupo grupo) {
        if (grupo != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }

}