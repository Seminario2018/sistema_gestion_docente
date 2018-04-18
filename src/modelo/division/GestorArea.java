package modelo.division;

import java.util.ArrayList;

import modelo.auxiliares.EstadoOperacion;

public class GestorArea {

    public EstadoOperacion nuevaArea(IArea area) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El área se creó correctamente.");
    }

    public EstadoOperacion modificarArea(IArea area) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                "El área se modificó correctamente");
    }

    public EstadoOperacion eliminarArea(IArea area) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El área se eliminó correctamente");
    }

    public ArrayList<IArea> listarArea(IArea area) {
        if (area != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }

}