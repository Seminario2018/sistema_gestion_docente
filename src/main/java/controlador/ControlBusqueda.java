package controlador;

import java.util.List;

import modelo.busqueda.BusquedaArea;
import modelo.busqueda.BusquedaDocente;
import modelo.busqueda.BusquedaPersona;
import modelo.busqueda.GestorBusqueda;
import modelo.division.GestorArea;
import modelo.division.IArea;
import modelo.docente.GestorDocente;
import modelo.docente.IDocente;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import vista.controladores.ControladorVista;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 31 de may. de 2018
 */
public class ControlBusqueda {
	
	private ControladorVista vista;

	private GestorBusqueda gestorBusqueda = new GestorBusqueda();
	private GestorDocente gestorDocente;
	private GestorPersona gestorPersona;
	private GestorArea gestorArea;

	
	public ControlBusqueda(ControladorVista vista) {
		super();
		this.vista = vista;
	}
	
	public List<BusquedaDocente> listarDocentes(String criterio) {
		return this.gestorBusqueda.listarDocentes(criterio);
	}
	
	public List<BusquedaPersona> listarPersonas(String criterio) {
		return this.gestorBusqueda.listarPersonas(criterio);
	}
	
	public Object seleccionar(Object fila) {
		if (fila instanceof BusquedaDocente) {
			BusquedaDocente bd = (BusquedaDocente) fila;
			this.gestorDocente = new GestorDocente();
			IDocente doc = this.gestorDocente.getIDocente();
			doc.setLegajo(bd.getLegajo());
			return this.gestorDocente.listarDocentes(doc).get(0);
		}
		
		if (fila instanceof BusquedaPersona) {
			BusquedaPersona bp = (BusquedaPersona) fila;
			this.gestorPersona = new GestorPersona();
			IPersona per = this.gestorPersona.getIPersona();
			per.setNroDocumento(bp.getDocumento());
			return this.gestorPersona.listarPersonas(per).get(0);
		}
		
		if (fila instanceof BusquedaArea) {
			BusquedaArea ba = (BusquedaArea) fila;
			this.gestorArea = new GestorArea();
			IArea area = this.gestorArea.getIArea();
			area.setCodigo(ba.getCodigo());
			return this.gestorArea.listarAreas(area).get(0);
		}
		
		return null;
	}
}
