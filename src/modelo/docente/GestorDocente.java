package modelo.docente;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorDocente {

	public EstadoOperacion nuevoDocente(IDocente docente) {
		// TODO actualizar BD
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
				"El docente se creó correctamente");
	}

	public EstadoOperacion modificarDocente(IDocente docente) {
		// TODO actualizar BD
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK,
				"El docente se modificó correctamente");
	}

	public EstadoOperacion eliminarDocente(IDocente docente) {
		// TODO actualizar BD
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
				"El docente se eliminó correctamente");
	}

	public List<IDocente> listarDocente(IDocente docente) {
		if (docente != null) {
			// TODO Filtrar por los campos que ingresan
		}
		// TODO select BD
		return null;
	}

	public EstadoOperacion agregarPlanta(IDocente docente, IPlanta planta) {
		// TODO actualizar BD
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
				"El cargo se agregó correctamente");
	}

	public EstadoOperacion quitarPlanta(IDocente docente, IPlanta planta) {
		// TODO actualizar BD
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
				"El cargo se quitó correctamente");
	}
}