package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.informe.ColumnaInforme;
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
	
	
	

}