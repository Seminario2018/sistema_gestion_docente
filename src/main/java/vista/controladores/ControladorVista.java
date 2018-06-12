package vista.controladores;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import jfxtras.scene.control.window.Window;
import modelo.auxiliares.EstadoOperacion;
import utilidades.Utilidades;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 10 de may. de 2018
 */
public abstract class ControladorVista implements Initializable {

	protected GestorPantalla gestorPantalla;
	protected Window window;

	public GestorPantalla getGestorPantalla() {
		return gestorPantalla;
	}

	public void setGestorPantalla(GestorPantalla gestor) {
		this.gestorPantalla = gestor;
	}
	
	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}
	
	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	/**
	 * Método que se llama cuando la vista ya está en pantalla
	 */
	public void inicializar() {
		
	}

	/**
	 * Recibe argumentos durante la inicialización para setear parámetros,
	 * e.g la pantalla Búsqueda se inicializa con parámetros para buscar Docentes
	 * Como es opcional, se debe agregar comportamiento con @Override
	 */
	public void recibirParametros(Map<String, Object> args) {

	}

	/**
	 * Inicializa una tabla genérica, obteniendo con Reflection el nombre
	 * de la variables TableView, TableColumn y ObservableList que se quieren
	 * inicializar.
	 * <br>
	 * Es necesario seguir cierta convención de nombres para que funcione:
	 * <ul>
	 * 	<li>TableColumn: col + nombre + campo, e.g. colCargosId</li>
	 * 	<li>TableView: tbl + nombre, e.g. tblCargos</li>
	 * 	<li>ObservableList: filas + nombre, e.g. filasCargos</li>
	 * </ul>
	 * @param <T> el Type de fila
	 * @param fila la Class que funciona como fila de la tabla,
	 * e.g. <i>FilaCargos</i>.
	 * @param nombre el String que sigue a la declaración del objeto gráfico,
	 * e.g. col<i>Cargos</i>X.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void inicializarTabla(String nombre) {

		try {

			Class<? extends ControladorVista> clase = this.getClass();
			Field[] campos = clase.getDeclaredFields();

			List<Field> columnas = new ArrayList<Field>();

			for (Field campo : campos) {
				// Se trata de una columna, e.g. colCargosX
				if (campo.getName().startsWith("col" + nombre)) {
					columnas.add(campo);
				}
			}

			for (Field columna : columnas) {
				TableColumn col = (TableColumn) columna.get(this);
				String varName = columna.getName()
						.replace("col" + nombre, "").toLowerCase();
				col.setCellValueFactory(new PropertyValueFactory(varName));
			}

			Field campoTabla = clase.getDeclaredField("tbl" + nombre);
			Field campoFilas = clase.getDeclaredField("filas" + nombre);

			campoFilas.set(this, FXCollections.observableArrayList());

			TableView tabla = (TableView) campoTabla.get(this);
			ObservableList filas = (ObservableList) campoFilas.get(this);

			tabla.setItems(filas);

			/*
			this.colCargosId.setCellValueFactory(
					new PropertyValueFactory<T, Integer>("id"));
			this.colCargosArea.setCellValueFactory(
					new PropertyValueFactory<FilaCargo, String>("area"));
			this.colCargosCargo.setCellValueFactory(
					new PropertyValueFactory<FilaCargo, String>("cargo"));
			this.colCargosEstado.setCellValueFactory(
					new PropertyValueFactory<FilaCargo, String>("estado"));

			this.filasCargos = FXCollections.observableArrayList();
			this.tblCargos.setItems(this.filasCargos);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Vacía todas las tablas de la vista.<br>
	 * Lo hace vaciando todas las listas observables asignadas a cada tabla. */
	@SuppressWarnings("rawtypes")
    public void vaciarTablas() {
	    try {
            Class<? extends ControladorVista> clase = this.getClass();

            // Busca todos los atributos que sean "filasX":
            for (Field campo : clase.getDeclaredFields()) {
                if (campo.getName().startsWith("filas")) {
                    ObservableList filas = (ObservableList) campo.get(this);
                    filas.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * Lanza una alerta al usuario sobre un error del sistema.
	 * @param titulo Título del mensaje
	 * @param encabezado Encabezado del mensaje
	 * @param contenido Contenido del mensaje
	 */
	public void alertaError(String titulo, String encabezado, String contenido) {
	    Alert alerta = new Alert(AlertType.ERROR);
	    alerta.setTitle(titulo);
	    alerta.setHeaderText(encabezado);
	    alerta.setContentText(contenido);
	    alerta.showAndWait();
	}

	/**
     * Lanza un diálogo de confirmación para el usuario.
     * @param titulo Título del mensaje
     * @param encabezado Encabezado del mensaje
     * @param contenido Contenido del mensaje
     */
	public void dialogoConfirmacion(String titulo, String encabezado, String contenido) {
	    Alert alerta = new Alert(AlertType.CONFIRMATION);
	    alerta.setTitle(titulo);
	    alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
	}

	/**
	 * Abre una ventana para permitir seleccionar un archivo.
	 * @param titulo Título de la ventana
	 * @return Archivo seleccionado
	 */
	public File elegirArchivo(String titulo, String descripcion, List<String> extensiones) {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle(titulo);
	    descripcion += " (" + Utilidades.joinString(extensiones, "; ") + ")";
	    ExtensionFilter extensionFilter = new ExtensionFilter(descripcion, extensiones);
	    fileChooser.getExtensionFilters().add(extensionFilter);
	    return fileChooser.showOpenDialog(new Stage());
	}

	/**
	 * Analiza el resultado de una operación de eliminación, y lanza una ventana
     * de aviso.
	 * @param resultado Resultado de la operación.
	 * @param titulo Título de la ventana.
	 * @param comando Descripción de la operación.
	 * @return Si la operación fue exitosa.
	 */
	protected boolean exitoEliminar(EstadoOperacion resultado, String titulo, String comando) {
	    switch (resultado.getEstado()) {
	        case DELETE_ERROR:
                alertaError(titulo, comando, resultado.getMensaje());
                return false;
            case DELETE_OK:
                dialogoConfirmacion(titulo, comando, resultado.getMensaje());
                return true;
            default:
                throw new RuntimeException("Estado de modificación no esperado: "
                    + resultado.getEstado().toString() + ": " + resultado.getMensaje());
	    }
	}

	/**
	 * Analiza el resultado de una operación de guardado, y lanza una ventana
	 * de aviso.
     * @param resultado Resultado de la operación.
     * @param titulo Título de la ventana.
     * @param comando Descripción de la operación.
     * @return Si la operación fue exitosa.
	 */
	protected boolean exitoGuardado(EstadoOperacion resultado, String titulo, String comando) {
	    switch (resultado.getEstado()) {
            case INSERT_ERROR:
            case UPDATE_ERROR:
                alertaError(titulo, comando, resultado.getMensaje());
                return false;
            case INSERT_OK:
            case UPDATE_OK:
                dialogoConfirmacion(titulo, comando, resultado.getMensaje());
                return true;
            default:
                throw new RuntimeException("Estado de modificación no esperado: "
                        + resultado.getEstado().toString() + ": " + resultado.getMensaje());
        }
	}
}
