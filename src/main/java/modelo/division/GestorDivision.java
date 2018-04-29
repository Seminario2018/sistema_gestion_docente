package modelo.division;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorDivision {

    public EstadoOperacion nuevaDivision(IDivision division) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "La división se creó correctamente.");
    }

    public EstadoOperacion modificarDivision(IDivision division) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                "La división se modificó correctamente");
    }

    public EstadoOperacion eliminarDivision(IDivision division) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "La división se eliminó correctamente");
    }

    public List<IDivision> listarDivision(IDivision division) {
        if (division != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }

}