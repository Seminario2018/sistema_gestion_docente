package modelo.costeo;

import java.sql.Date;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import persistencia.ManejoDatos;

public class GestorCargosFaltantes {

	public EstadoOperacion agregarCargoFaltante(ICargoFaltante cargo) {
		ManejoDatos md = new ManejoDatos();
		String tabla = "cargosfaltantes";
		String campos = "`Legajo`, `Cargo`, `FechaUltimoCosto`, `Tipo`";
		String valores = cargo.getLegajo() + ", " + cargo.getCodigoCargo() + ", "
				+ "'" + Date.valueOf(cargo.getFechaUltimoCosto()) + "', " + (cargo.isTipo() ? 0 : 1);
		if (cargo.getApellido() != null && !cargo.getApellido().equals("")) {
			campos += ", Apellido";
			valores += ", '" + cargo.getApellido() + "'";
		}
		if (cargo.getNombre() != null && !cargo.getNombre().equals("")) {
			campos += ", Nombre";
			valores += ", '" + cargo.getNombre() + "'";
		}
		if (cargo.getUltimoCosto() != -1) {
			campos += ", UltimoCosto";
			valores += ", '" + cargo.getApellido() + "'";
		}
		
		
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
