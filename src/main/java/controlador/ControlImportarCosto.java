package controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.costeo.Costeo;
import modelo.costeo.GestorImportarCosto;
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

	public void importar() {
		String titulo = "Elegir la hoja de cálculo a importar";
    	String error = "Error al importar los datos";
	    try {
	    	List<String> extensiones = new ArrayList<String>();
	    	extensiones.add("*.xls");
	    	extensiones.add("*.xlsx");
	        File archivo = this.vista.elegirArchivo(titulo, "Hojas de cálculo", extensiones);
	        if (archivo != null) {
	        	Costeo.importar(archivo);
                // TODO llevar a la capa de abajo
	        }
        } catch (EncryptedDocumentException e) {
            this.vista.alertaError(tituloDialogo, error,
                "El archivo está protegido por contraseña.");
        } catch (InvalidFormatException e) {
        	this.vista.alertaError(tituloDialogo, error,
                "El archivo no tiene el formato correcto. Asegúrese de que"
                + " se trata de la planilla con el costeo del mes.");
        } catch (IOException e) {
        	this.vista.alertaError(tituloDialogo, error,
                "Error al abrir el archivo. Asegúrese de que el archivo"
                + " es del formato correcto (.xls o .xlsx)"
                + " y que no está siendo usado por otro programa.");
        }
	}

	public void guardar() {
		EstadoOperacion resultado = this.gestorImportarCosto.guardar();
		switch (resultado.getEstado()) {
    		case UPDATE_OK:
    		    vista.mensajeEstado(resultado.getMensaje());
    			break;
    		default:
    			this.vista.alertaError(tituloDialogo, "No se pudieron actualizar los datos", resultado.getMensaje());
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
}
