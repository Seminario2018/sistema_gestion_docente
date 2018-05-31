package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.busqueda.BusquedaDocente;
import modelo.busqueda.GestorBusqueda;
import modelo.docente.IDocente;
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
		// TEST
		ControlDocente control = new ControlDocente(this.vista);
		List<IDocente> listaBusqueda = new ArrayList<>(control.listarDocente(null));
		List<BusquedaDocente> filasBusqueda = new ArrayList<>();
		for (Object docente : listaBusqueda) {
			if (docente instanceof IDocente) {
				BusquedaDocente bd = new BusquedaDocente(
						((IDocente) docente).getLegajo(),
						((IDocente) docente).getPersona().getApellido(),
						((IDocente) docente).getPersona().getNombre());
				filasBusqueda.add(bd);
			}
		}
		return filasBusqueda;
		//return this.gestor.listarDocente(criterio);
	}
}
