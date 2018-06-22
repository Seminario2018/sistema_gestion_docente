package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.informe.GestorInforme;
import modelo.informe.ITipoInforme;
import vista.controladores.ControladorVista;

public class ControlInforme {
	
	private GestorInforme gestorInforme = new GestorInforme();
	private ControladorVista vista;
	
	public ControlInforme(ControladorVista vista) {
		this.vista = vista;
	}

	public EstadoOperacion nuevoInforme(ITipoInforme informe) {
		return this.gestorInforme.nuevoInforme(informe);
	}
	
	public EstadoOperacion modificarInforme(ITipoInforme informe) {
		return this.gestorInforme.nuevoInforme(informe);
	}
	
	public EstadoOperacion eliminarInforme(ITipoInforme informe) {
		return this.gestorInforme.eliminarInforme(informe);
	}
	
	public ArrayList<ITipoInforme> listarInforme(ITipoInforme informe) {
		return this.gestorInforme.listarInforme(informe);
	}

	public void setInformeActual(ITipoInforme informeActual) {
		this.gestorInforme.setInformeActual(informeActual);
	}

	public ITipoInforme getInformeActual() {
		return this.gestorInforme.getInformeActual();
	}

	public List<List<String>> vistaPrevia() {
		return this.gestorInforme.vistaPrevia();
	}

	public void guardar(ITipoInforme informe) {
		String titulo = "Guardar Informe";
		String encabezado = "Error al guardar";
		String contenido = "Debe ingresar un nombre para el Informe.";
		
		String nombre = informe.getNombre();
		if (nombre == null || "".equals(nombre)) {
			this.vista.alertaError(titulo, encabezado, contenido);
			return;
		}
			
		this.gestorInforme.setInformeActual(informe);
		EstadoOperacion eo = this.gestorInforme.guardar();
		switch (eo.getEstado()) {
		case INSERT_OK:
		case UPDATE_OK:
			this.vista.mensajeEstado(eo.getMensaje());
			break;
		default:
			this.vista.alertaError("Guardar Informe", "Error al guardar el Informe", eo.getMensaje());
		}
	}
	
	public void guardarComo(ITipoInforme informe) {
		String titulo = "Guardar Informe";
		String encabezado = "Error al guardar";
		String contenido = "Ya existe un Informe con ese nombre.";

		ITipoInforme informeActual = getInformeActual();
		if (informeActual.getNombre().equals(informe.getNombre())) {
			this.vista.alertaError(titulo, encabezado, contenido);
			return;
		}
		
		informe.setId(-1);
		guardar(informe);
	}
	

}