package vista.controladores;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controlador.ControlBusqueda;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.busqueda.BusquedaDocente;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Busqueda extends ControladorVista implements Initializable {
	
	public static final String KEY_NUEVO = "nuevo";
	public static final String KEY_TIPO = "tipo";
	public static final String KEY_CONTROLADOR = "controlador";
	// Devuelve el tipo de dato seleccionado, por ejemplo "Area"
	public static final String KEY_SELECCION = "seleccion";
	// Devuelve el dato seleccionado
	public static final String KEY_VALOR = "valor";
	public static final String TITULO = "Busqueda";
	
	private String tipo;
	private ControlBusqueda control = new ControlBusqueda(this);
	// Recibe la respuesta (selección)
	private ControladorVista controladorRespuesta;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
// -------------------------------- General --------------------------------- //
	
	@FXML private TableView tblBusqueda;
	@FXML private List<TableColumn> colsBusqueda = new ArrayList<TableColumn>();
	private ObservableList<Object> filasBusqueda = FXCollections.observableArrayList();
	
	@FXML private TextField txtBusquedaCriterio;
	
	@FXML private Button btnBusquedaNuevo;
	@FXML public void nuevo(ActionEvent event) {
		this.gestorPantalla.lanzarPantalla(this.tipo, null);
	}
	
	@FXML private Button btnBusquedaSeleccionar;
	@FXML public void seleccionar(ActionEvent event) {
		if (!tblBusqueda.getSelectionModel().isEmpty()) {
			Object fila = tblBusqueda.getSelectionModel().getSelectedItem();
			try {
				Method m = this.getClass().getDeclaredMethod("seleccionar" + this.tipo, Object.class);
				m.invoke(this, fila);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML public void actualizarLista() {
		// TODO seleccionar de las vistas filtrando por txtBusquedaCriterio
	}
	
	@Override
	public void recibirParametros(Map<String, Object> args) {
		this.tipo = (String) args.get(KEY_TIPO);
		this.controladorRespuesta = (ControladorVista) args.get(KEY_CONTROLADOR);
		this.btnBusquedaNuevo.setVisible((boolean) args.get(KEY_NUEVO));
		try {
			Method m = this.getClass().getDeclaredMethod("inicializar" + this.tipo);
			m.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void inicializarTabla(Class fila) {
		this.tblBusqueda.getColumns().clear();
		Field[] campos = fila.getDeclaredFields();
		for (int i = 0; i < campos.length; i++) {
			String varName = campos[i].getName();
			TableColumn columna = new TableColumn<>(Utilidades.primeraMayuscula(varName));
			columna.setCellValueFactory(new PropertyValueFactory(varName));
			this.tblBusqueda.getColumns().add(columna);
		}
		this.tblBusqueda.setItems(this.filasBusqueda);
	}

// -------------------------------- Docentes -------------------------------- //	
	public void inicializarDocentes() {
		inicializarTabla(BusquedaDocente.class);
		actualizarListaDocentes();
	}
	
	public void actualizarListaDocentes() {
		this.filasBusqueda.clear();
		this.filasBusqueda.addAll(
				this.control.listarDocente(
						this.txtBusquedaCriterio.getText()));
	}
	
	public void seleccionarDocentes() {
		
	}
}
