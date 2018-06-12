package modelo.costeo;

import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.ICargoDocente;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 12 de jun. de 2018
 */
public class GestorImportarCosto {

	/**
	 * Guarda los cambios realizados, actualizando los costos y los estados de los cargos.
	 */
	public EstadoOperacion guardar() {
		return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "La importación se realizó con éxito");
	}
	
	
	/**
	 * Descarta los cambios realizados, volviendo al estado anterior a importar.
	 */
	public void descartar() {
		
	}


	/**
	 * Modifica el Estado del CargoDocente en memoria
	 * @param cargo El CargoDocente a modificar
	 * @param estado El nuevo Estado del CargoDocente 
	 */
	public void modificarEstado(ICargoDocente cargo, EstadoCargo estado) {
		
	}

}
