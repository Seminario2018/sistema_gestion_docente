package controlador;

import modelo.cargo.GestorCargo;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;

public class ControlDocente {

	private GestorCargo gestorCargo = new GestorCargo();
	private GestorDocente gestorDocente = new GestorDocente();

	public ICargoDocente getICargoDocente() {
		return this.gestorDocente.getICargoDocente();
	}

    public void agregarCargoDocente(
        IDocente docenteSeleccionado,
        ICargoDocente cargoDocenteSeleccionado
    ) {
        // TODO Auto-generated method stub

    }

    public void modificarCargoDocente(
        IDocente docenteSeleccionado,
        ICargoDocente cargoDocenteSeleccionado
    ) {
        // TODO Auto-generated method stub

    }



}