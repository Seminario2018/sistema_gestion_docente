package controlador;

import java.util.List;

import modelo.auxiliares.EstadoOperacion;
import modelo.costeo.GestorImportarCosto;
import vista.controladores.ControladorVista;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 12 de jun. de 2018
 */
public class ControlImportarCosto {

	private GestorImportarCosto gestorImportarCosto = new GestorImportarCosto();
	private ControladorVista vista;
	
	private static final String tituloDialogo = "Importar último costo";

	public ControlImportarCosto(ControladorVista vista) {
		super();
		this.vista = vista;
	}

	public void guardar() {
		EstadoOperacion eo = this.gestorImportarCosto.guardar();
		switch (eo.getEstado()) {
		case UPDATE_OK:
			this.vista.dialogoConfirmacion(tituloDialogo, "Éxito al importar datos", eo.getMensaje());
			break;
		default:
			this.vista.alertaError(tituloDialogo, "No se pudieron importar los datos", eo.getMensaje());
		}
	}
	
	public void descartar() {
		this.gestorImportarCosto.descartar();
	}
}
