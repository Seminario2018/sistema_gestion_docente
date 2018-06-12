package modelo.costeo;

import modelo.auxiliares.EstadoOperacion;

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

}
