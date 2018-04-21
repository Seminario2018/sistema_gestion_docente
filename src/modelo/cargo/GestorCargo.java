package modelo.cargo;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorCargo {
    public EstadoOperacion nuevoCargo(ICargo cargo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El cargo se creó correctamente.");
    }

    public EstadoOperacion modificarCargo(ICargo cargo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                "El cargo se modificó correctamente");
    }

    public EstadoOperacion eliminarCargo(ICargo cargo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El cargo se eliminó correctamente");
    }

    public List<ICargo> listarCargo(ICargo cargo) {
        if (cargo != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }
}