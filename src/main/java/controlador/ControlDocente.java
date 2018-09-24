package controlador;

import java.util.List;
import mail.NotificacionCargo2;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.cargo.GestorCargo;
import modelo.cargo.ICargo;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.docente.IDocenteg;
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

	public EstadoOperacion guardarDocente(IDocente docente) {
	    if (!GestorDocente.existeDocente(docente)) {
	        return this.gestorDocente.nuevoDocente(docente);
	    } else {
	        return this.gestorDocente.modificarDocente(docente);
	    }
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
    	// Buscar si ya existe el cargo
    	ICargoDocente cargoDocenteBusqueda = gestorDocente.getICargoDocente();
        cargoDocenteBusqueda.setId(cargoDocente.getId());
        List<ICargoDocente> listaCargosDocentes = gestorDocente.listarCargo(docente, cargoDocenteBusqueda);
        if (listaCargosDocentes.size() < 1 || cargoDocente.getId() < 1) {
        	// Cargo nuevo
        	EstadoOperacion resultado = gestorDocente.agregarCargoDocente((IDocenteg) docente, cargoDocente);
            if (resultado.getEstado() == CodigoEstado.INSERT_OK) {
                /* Notifico por mail cuando hay un nuevo cargo (si se insertó
                 * exitosamente):
                 */
            	try {
            		NotificacionCargo2.getInstance().notificar(
            				vista.getUsuario(),
            				docente,
            				cargoDocente,
		                    EstadoCargo.getEstadoNuevo(),
		                    cargoDocente.getEstado(),
		                    resultado);
            	} catch (IllegalArgumentException e) {
            		this.vista.mensajeEstado(e.getMessage());
            	}
            }
            return resultado;
        	
        } else {
        	// Modificar un cargo existente 
            EstadoCargo estadoCargoReferencia = listaCargosDocentes.get(0).getEstado();

            EstadoOperacion resultado = gestorDocente.modificarCargoDocente(docente, cargoDocente);
            if (resultado.getEstado() == CodigoEstado.UPDATE_OK) {
                /* Notifico por mail cuando se modifique exitosamente el estado
                 * de un cargoDocente:
                 */
                EstadoCargo estadoCargoNuevo = cargoDocente.getEstado();
                if (!estadoCargoNuevo.equals(estadoCargoReferencia)) {
                	try {
	                    NotificacionCargo2.getInstance().notificar(
	                        vista.getUsuario(),
	                        docente,
	                        cargoDocente,
	                        estadoCargoReferencia,
	                        estadoCargoNuevo,
	                        resultado);
                	} catch (IllegalArgumentException e) {
                		this.vista.mensajeEstado(e.getMessage());
                	}
                	pasarAInactivo(docente);
                }
            }
            return resultado;
        }
    }            

        

    public EstadoOperacion quitarCargoDocente(IDocente docente, ICargoDocente cargoDocente) {
        /* DONE Saqué la notificación por mail cuando se borra un cargodocente de la BD: *
        // NotificacionCargo2 notificacion = new NotificacionCargo2(docente, cargoDocente);
        EstadoOperacion resultado = gestorDocente.quitarCargoDocente(docente, cargoDocente);
        switch (resultado.getEstado()) {
            case DELETE_OK:
                // notificacion.notificar(resultado);
            	NotificacionCargo2.getInstance().notificar(docente, cargoDocente, resultado);
                break;
            default:
                throw new RuntimeException("Estado de eliminación de cargoDocente no esperado: " + resultado.getMensaje());
        }
        return resultado;
        //*/

        EstadoOperacion resultado = gestorDocente.quitarCargoDocente(docente, cargoDocente);
        switch (resultado.getEstado()) {
            case DELETE_OK:
                pasarAInactivo(docente);
                break;

            default:
                throw new RuntimeException("Estado de eliminación de cargoDocente no esperado: " + resultado.getMensaje());
        }
        return resultado;
    }

    /**
     * Comprueba si a un docente no le quedan cargos activos y
     * pregunta al usuario si cambia el estado del docente a inactivo.
     * @param docente El docente
     */
    public void pasarAInactivo(IDocente docente) {
        if (!gestorDocente.tieneCargosActivos(docente)) {
            // Compruebo que el docente no tenga cargos activos
             if (vista.dialogoConfirmacion(
                 "Quitar cargo docente",
                 "A este docente no le quedan cargos activos",
                 "¿Desea cambiar su estado a inactivo?")) {

                 EstadoDocente estadoActual = docente.getEstado();
                 if (estadoActual.getId() != 1) {
                     // Cambio el estado del docente a "Inactivo"
                     EstadoDocente estadoNuevo = new EstadoDocente();
                     estadoNuevo.setId(1);
                     estadoNuevo.setDescripcion("Inactivo");
                     docente.setEstado(estadoNuevo);

                     // Aplico el cambio a la base de datos
                     gestorDocente.modificarDocente(docente);
                 }
             }
        }
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
    public IIncentivo getIIncentivo() {
        return this.gestorDocente.getIIncentivo();
    }

    public EstadoOperacion guardarIncentivo(IDocente docente, IIncentivo incentivo) {
        return this.gestorDocente.agregarIncentivo(docente, incentivo);
    }

    public EstadoOperacion quitarIncentivo(IDocente docente, IIncentivo incentivo) {
        return this.gestorDocente.quitarIncentivo(docente, incentivo);
    }

    public List<IIncentivo> listarIncentivos(IDocente docente, IIncentivo incentivo) {
        return this.gestorDocente.listarIncentivos(docente, incentivo);
    }
}