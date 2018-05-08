package controlador;

import modelo.cargo.GestorCargo;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;

public class ControlDocente {

	private GestorCargo gestorCargo = new GestorCargo();
	private GestorDocente gestorDocente = new GestorDocente();
	
	public ICargoDocente getICargoDocente() {
		return this.gestorDocente.getICargoDocente();
	}

}