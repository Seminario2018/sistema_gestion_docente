package vista.controladores;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 10 de may. de 2018
 */
public abstract class ControladorVista {
	
	/**
	 * Inicializa una tabla genérica, obteniendo con Reflection el nombre
	 * de la variables TableView, TableColumn y ObservableList que se quieren
	 * inicializar  
	 * @param fila la Class que funciona como fila de la tabla,
	 * e.g. <i>FilaCargos</i>.
	 * @param nombre el String que sigue a la declaración del objeto gráfico,
	 * e.g. col<i>Cargos</i>X. 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void inicializarTabla(T fila, String nombre) {

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
				TableColumn col;
				col = (TableColumn) columna.get(this);
				String varName = columna.getName().replace("col" + nombre, "").toLowerCase();
				col.setCellValueFactory(new PropertyValueFactory<T, String>(varName));
			}
			
			Field campoTabla = clase.getDeclaredField("tbl" + nombre);
			Field campoFilas = clase.getDeclaredField("filas" + nombre);
			
			campoFilas.set(this, FXCollections.observableArrayList()); 
			
			TableView tabla = (TableView) campoTabla.get(this);
			ObservableList<T> filas = (ObservableList<T>) campoFilas.get(this);
			
			Method metodo = tabla.getClass().getDeclaredMethod("setItems", ObservableList.class);
			
			metodo.invoke(tabla, filas);
		
					
			
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
}
