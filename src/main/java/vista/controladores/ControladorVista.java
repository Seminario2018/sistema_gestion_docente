package vista.controladores;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 10 de may. de 2018
 */
public abstract class ControladorVista {
	
	protected GestorPantalla gestor;
	
	public GestorPantalla getGestor() {
		return gestor;
	}

	public void setGestor(GestorPantalla gestor) {
		this.gestor = gestor;
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
	
	/**
	 * Lanza una alerta al usuario sobre un error del sistema.
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
}
