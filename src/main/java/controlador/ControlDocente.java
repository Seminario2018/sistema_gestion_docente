package controlador;

import mail.NotificacionCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.GestorCargo;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import vista.controladores.ControladorVista;

public class ControlDocente {

	private ControladorVista vista;
	
	private GestorCargo gestorCargo = new GestorCargo();
	private GestorDocente gestorDocente = new GestorDocente();

	
	public ControlDocente(ControladorVista vista) {
		super();
		this.vista = vista;
	}
	
	public ICargoDocente getCargoDocente() {
		return this.gestorDocente.getCargoDocente();
	}

	public void guardarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
		if (cargoDocente.getId() == -1) {
			// Se agrega un nuevo Cargo Docente
			EstadoOperacion eo = gestorDocente.agregarCargoDocente(docente, cargoDocente);
	        switch (eo.getEstado()) {
	            case INSERT_OK:
	                NotificacionCargo.notificar(eo, docente, cargoDocente);
	                break;
	            default:
	                System.out.printf("%s\n", eo.getMensaje());
	                vista.alertaError("Cargos", "No se pudo agregar el cargo docente", eo.getMensaje());
	        }
		} else {
			// Se modifica un Cargo Docente anterior
			EstadoOperacion eo = gestorDocente.modificarCargoDocente(docente, cargoDocente);
	        switch (eo.getEstado()) {
	            case UPDATE_OK:
	                NotificacionCargo.notificar(eo, docente, cargoDocente);
	                break;
	            default:
	                System.out.printf("%s\n", eo.getMensaje());
	                vista.alertaError("Cargos", "No se pudo modificar el cargo docente", eo.getMensaje());
	        }
        }
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