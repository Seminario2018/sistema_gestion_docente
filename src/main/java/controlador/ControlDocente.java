package controlador;

import java.util.ArrayList;
import java.util.List;
import mail.NotificacionCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.docente.IIncentivo;
import vista.controladores.ControladorVista;

public class ControlDocente {

	private ControladorVista vista;

	private GestorCargo gestorCargo = new GestorCargo();
	private GestorDocente gestorDocente = new GestorDocente();


	public ControlDocente(ControladorVista vista) {
		super();
		this.vista = vista;
	}

//  Docentes

	public IDocente getIDocente() {
		return this.gestorDocente.getIDocente();
	}

	public EstadoOperacion nuevoDocente(IDocente docente) {
	    return this.gestorDocente.nuevoDocente(docente);
	}

	public EstadoOperacion modificarDocente(IDocente docente) {
	    return this.gestorDocente.modificarDocente(docente);
	}

	public EstadoOperacion eliminarDocente(IDocente docente) {
	    return this.gestorDocente.eliminarDocente(docente);
	}

	public List<IDocente> listarDocente(IDocente docente) {
		return this.gestorDocente.listarDocentes(docente);
	}


//  CargosDocente

	public ICargoDocente getICargoDocente() {
		return this.gestorDocente.getICargoDocente();
	}

    public EstadoOperacion guardarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        NotificacionCargo notificacion = new NotificacionCargo(docente, cargoDocente);
        if (cargoDocente.getId() == -1) {
            // Se agrega un nuevo Cargo Docente
            EstadoOperacion resultado = gestorDocente.agregarCargoDocente(docente, cargoDocente);
            switch (resultado.getEstado()) {
                case INSERT_OK:
                    notificacion.notificar(resultado);
                    break;
                default:
                    System.out.printf("%s\n", resultado.getMensaje());
                    vista.alertaError("Cargos", "No se pudo agregar el cargo docente", resultado.getMensaje());
            }
            return resultado;
        } else {
            // Se modifica un Cargo Docente anterior
            EstadoOperacion resultado = gestorDocente.modificarCargoDocente(docente, cargoDocente);
            switch (resultado.getEstado()) {
                case UPDATE_OK:
                    notificacion.notificar(resultado);
                    break;
                default:
                    System.out.printf("%s\n", resultado.getMensaje());
                    vista.alertaError("Cargos", "No se pudo modificar el cargo docente", resultado.getMensaje());
            }
            return resultado;
        }
    }

    public EstadoOperacion quitarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        NotificacionCargo notificacion = new NotificacionCargo(docente, cargoDocente);
        EstadoOperacion resultado = gestorDocente.quitarCargoDocente(docente, cargoDocente);
        switch (resultado.getEstado()) {
            case DELETE_OK:
                notificacion.notificar(resultado);
                break;
            default:
                throw new RuntimeException("Estado de eliminación de cargoDocente no esperado: " + resultado.getMensaje());
        }
        return resultado;
    }

    public List<ICargoDocente> listarCargosDocente(IDocente docente, ICargoDocente cargoDocente) {
        return this.gestorDocente.listarCargo(docente, cargoDocente);
    }

//  Cargos
    public EstadoOperacion nuevoCargo(ICargo cargo) {
        return this.gestorCargo.nuevoCargo(cargo);
    }

    public EstadoOperacion modificarCargo(ICargo cargo) {
        return this.gestorCargo.modificarCargo(cargo);
    }

    public EstadoOperacion eliminarCargo(ICargo cargo) {
        return this.gestorCargo.eliminarCargo(cargo);
    }

    public List<ICargo> listarCargos(ICargo cargo) {
        return this.gestorCargo.listarCargos(cargo);
    }

//  Incentivos
    public EstadoOperacion agregarIncentivo(IDocente docente, IIncentivo incentivo) {
        return this.gestorDocente.agregarIncentivo(docente, incentivo);
    }

    public EstadoOperacion quitarIncentivo(IDocente docente, IIncentivo incentivo) {
        return this.gestorDocente.quitarIncentivo(docente, incentivo);
    }

    public List<IIncentivo> listarIncentivos(IDocente docente, IIncentivo incentivo) {
        // TODO sacar incentivos de GestorDocente.
        return new ArrayList<IIncentivo>();
    }

}