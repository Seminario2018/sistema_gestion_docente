package controlador;

import java.util.ArrayList;

import modelo.auxiliares.EstadoOperacion;
import modelo.informe.ColumnaInforme;
import modelo.informe.GestorInforme;
import modelo.informe.ITipoInforme;
import modelo.informe.TipoInforme;

public class ControlInforme {
	
	private GestorInforme gestorInforme=new GestorInforme();
	
	public EstadoOperacion nuevoInforme(ITipoInforme informe) {
		return this.gestorInforme.nuevoInforme(informe);
	}
	
	public EstadoOperacion  modificaInforme(ITipoInforme informe) {
		return this.gestorInforme.modificarInforme(informe);
	}
	
	
	public EstadoOperacion modificarInforme(ITipoInforme informe) {
		return this.gestorInforme.nuevoInforme(informe);
	}
	
	public ArrayList<ITipoInforme> listarProyecto(ITipoInforme informe) {
		
		return this.gestorInforme.listarProyecto(informe);
	}
	
	
	

}