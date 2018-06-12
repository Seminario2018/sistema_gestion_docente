package modelo.docente;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;

public class GestorCargosFaltantes {

	public EstadoOperacion agregarCargoFaltante(ICargoFaltante cargo) {
		//TODO insertar el cargo enviado
		return null;
	}
	
	public EstadoOperacion eliminarCargoFaltante(ICargoFaltante cargo) {
		//TODO eliminar el cargo enviado
		return null;
	}
	
	public List<ICargoFaltante> listarCargosFaltantes() {
		//TODO buscar los cargos faltantes
		return null;
	}
	
	public List<ICargoFaltante> listarCargosEnSistema(){
		//TODO listar los cargos que estan en la tabla cargos docentes y no en el costeo
		return null;

	}
	
	public List<ICargoFaltante> listarCargosEnCosteo(){
		//TODO listar los cargos que estan en el costeo y no en la tabla cargos docentes
		return null;
	}

	/**
	 * @return
	 */
	public ICargoFaltante getICargoFaltante() {
		return (ICargoFaltante) new CargoFaltante();
	}
	
}
