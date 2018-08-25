package controlador;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.costeo.FilaCostoComparar;
import modelo.costeo.GestorImportarCosto;
import modelo.costeo.ICargoFaltante;
import modelo.docente.ICargoDocente;
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
	
	public List<ICargoFaltante> getFaltantesSistema() {
		return this.gestorImportarCosto.getFaltantesSistema();
	}
	public List<ICargoDocente> getFaltantesCosteo() {
		return this.gestorImportarCosto.getFaltantesCosteo();
	}

	public boolean importar(LocalDate fechaImportada) {
		String titulo = "Elegir la hoja de cálculo a importar";
    	String error = "Error al importar los datos";
	    try {
	    	List<String> extensiones = new ArrayList<String>();
	    	extensiones.add("*.xls");
	    	extensiones.add("*.xlsx");
	        File archivo = this.vista.elegirArchivo(titulo, "Hojas de cálculo", extensiones);
	        if (archivo != null) {
	        	this.gestorImportarCosto.setFechaImportadal(fechaImportada);
	        	EstadoOperacion eo = this.gestorImportarCosto.importar(archivo);
	        	switch (eo.getEstado()) {
	        	case OP_OK:
	        		this.vista.getGestorPantalla().mensajeEstado(eo.getMensaje());
	        		return true;
	        	default:
	        		this.vista.alertaError(tituloDialogo, error, eo.getMensaje());
	        		return false;
	        	}
	        }
        } catch (EncryptedDocumentException e) {
            this.vista.alertaError(tituloDialogo, error,
                "El archivo está protegido por contraseña.");
            return false;
        }
		return false;
	}

	public void guardar() {
		EstadoOperacion resultado = this.gestorImportarCosto.guardar();
		switch (resultado.getEstado()) {
    		case UPDATE_OK:
    		    vista.mensajeEstado(resultado.getMensaje());
    			break;
    		default:
    			this.vista.alertaError(tituloDialogo, "No se actualizaron datos", resultado.getMensaje());
		}
	}

	public void descartar() {
		this.gestorImportarCosto.descartar();
	}

	/**
	 * Modificar el estado del cargo
	 * @param estado
	 */
	public void modificarEstado(ICargoDocente cargo, EstadoCargo estado) {
		this.gestorImportarCosto.modificarEstado(cargo, estado);
	}

	/**
	 * @return
	 */
	public LocalDate getUltimaFecha() {
		return this.gestorImportarCosto.getUltimaFecha();
	}
	
	public List<FilaCostoComparar> listarComparacion() {
		return this.gestorImportarCosto.listarComparacion();
	}
}
