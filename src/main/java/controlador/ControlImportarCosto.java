package controlador;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;

import mail.NotificacionCargo2;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.costeo.FilaCostoComparar;
import modelo.costeo.GestorImportarCosto;
import modelo.costeo.GestorImportarCosto.TipoAlta;
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
	public List<ICargoFaltante> getFaltantesCosteo() {
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
		EstadoOperacion resultado = this.gestorImportarCosto.guardar(vista);
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
	public boolean modificarEstado(ICargoDocente cargo, EstadoCargo estado) {		
		EstadoCargo estAnterior = cargo.getEstado();
		EstadoOperacion eo = this.gestorImportarCosto.modificarEstado(cargo, estado);
		// Notifico por mail el cambio
		if (eo.getEstado() == CodigoEstado.UPDATE_OK) {
			try {
				NotificacionCargo2.getInstance().notificar(
						vista.getUsuario(),
						cargo.getDocente(),
						cargo,
						estAnterior,
						cargo.getEstado(),
						eo);
			} catch (IllegalArgumentException e) {
	    		this.vista.mensajeEstado(e.getMessage());
	    	}
			return true;
		}
		return false;
	}
	
	/**
	 * Intenta cambiar el estado del cargo a "Activo".
	 * @param cargo - el cargo seleccionado en la vista.
	 * @return <b>True</b> si el estado se cambió, <b>False</b> en otro caso.
	 */
	public boolean altaEstado(ICargoFaltante cargof) {
		ICargoDocente cargo = this.getCargo(cargof);
		EstadoOperacion eo = this.gestorImportarCosto.altaEstado(cargo);
		// Notifico por mail el cambio
		if (eo.getEstado() == CodigoEstado.UPDATE_OK) {
			try {
				NotificacionCargo2.getInstance().notificar(
						vista.getUsuario(),
						cargo.getDocente(),
						cargo,
						EstadoCargo.getEstadoNuevo(),
						cargo.getEstado(),
						eo);
			} catch (IllegalArgumentException e) {
	    		this.vista.mensajeEstado(e.getMessage());
	    	}
			return true;
		}
		return false;
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

	
	public TipoAlta getTipoAlta(ICargoFaltante cargo) {
		return this.gestorImportarCosto.getTipoAlta(cargo);
	}
	
	/**
	 * 
	 * @param cargo - el ICargoFaltante de la vista
	 * @return el ICargoDocente correspondiente
	 */
	public ICargoDocente getCargo(ICargoFaltante cargo) {
		return this.gestorImportarCosto.getCargo(cargo.getCodigoCargo());
	}

	/**
	 * Convierte los datos de un ICargoFaltante en ICargoDocente
	 * para enviar a la pantalla Docentes para las altas.
	 * @param cargof
	 * @return un ICargoDocente con los datos de cargof
	 */
	public ICargoDocente prepararCargo(ICargoFaltante cargof) {
		return this.gestorImportarCosto.prepararCargo(cargof);
	}
}
