package controlador;

import java.util.List;

import modelo.busqueda.BusquedaDocente;
import modelo.busqueda.GestorBusqueda;
import vista.controladores.ControladorVista;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class ControlBusqueda {
	
	private ControladorVista vista;

	GestorBusqueda gestor = new GestorBusqueda();
	
	public ControlBusqueda(ControladorVista vista) {
		super();
		this.vista = vista;
	}
	
	public List<BusquedaDocente> listarDocente(String criterio) {
		return this.gestor.listarDocente(criterio);
	}
}
