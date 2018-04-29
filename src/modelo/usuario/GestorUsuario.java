package modelo.usuario;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorUsuario {

    public EstadoOperacion nuevoUsuario(IUsuario usuario) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El usuario se creó correctamente");
    }

    public EstadoOperacion modificarUsuario(IUsuario usuario) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
                "El usuario se modificó correctamente");
    }

    public EstadoOperacion eliminarUsuario(IUsuario usuario) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El usuario se eliminó correctamente");
    }

    public List<IUsuario> listarUsuario(IUsuario usuario) {
        if (usuario != null) {
            // TODO Filtrar por los campos que ingresan
        }
        // TODO select BD
        return null;
    }

    public EstadoOperacion agregarGrupo(IUsuario usuario, IRol grupo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
                "El grupo se agregó correctamente");
    }

    public EstadoOperacion quitarGrupo(IUsuario usuario, IRol grupo) {
        // TODO actualizar BD
        return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
                "El grupo se quitó correctamente");
    }
}