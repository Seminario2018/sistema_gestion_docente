package controlador;

import mail.NotificacionCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.GestorCargo;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;

public class ControlDocente {

	private GestorCargo gestorCargo = new GestorCargo();
	private GestorDocente gestorDocente = new GestorDocente();

	public ICargoDocente getCargoDocente() {
		return this.gestorDocente.getCargoDocente();
	}

    public EstadoOperacion agregarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        EstadoOperacion eo = gestorDocente.agregarCargoDocente(docente, cargoDocente);
        switch (eo.getEstado()) {
            case INSERT_OK:
                NotificacionCargo.notificar(eo, docente, cargoDocente);
                break;
            default:
                System.out.printf("%s\n", eo.getMensaje());
        }
        return eo;
    }

    public EstadoOperacion modificarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        EstadoOperacion eo = gestorDocente.modificarCargoDocente(docente, cargoDocente);
        switch (eo.getEstado()) {
            case UPDATE_OK:
                NotificacionCargo.notificar(eo, docente, cargoDocente);
                break;
            default:
                System.out.printf("%s\n", eo.getMensaje());
        }
        return eo;
    }

    public EstadoOperacion quitarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        EstadoOperacion eo = gestorDocente.quitarCargoDocente(docente, cargoDocente);
        switch (eo.getEstado()) {
            case DELETE_OK:
                NotificacionCargo.notificar(eo, docente, cargoDocente);
                break;
            default:
                System.out.printf("%s\n", eo.getMensaje());
        }
        return eo;
    }

}