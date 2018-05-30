package vista.controladores;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controlador.ControlCargo;
import controlador.ControlDivision;
import controlador.ControlDocente;
import controlador.ControlInvestigacion;
import controlador.ControlPersona;
import controlador.ControlUsuario;
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
import modelo.docente.IDocente;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class Busqueda extends ControladorVista implements Initializable {
	
	public static final String KEY_TIPO = "tipo";
	public static final String KEY_CONTROLADOR = "controlador";
	public static final String KEY_RESULTADO = "resultado";
	public static final String TITULO = "Busqueda";
	
	private String tipo;
	private ControlCargo controlCargo;
	private ControlDivision controlDivision;
	private ControlDocente controlDocente;
	private ControlInvestigacion controlInvestigacion;
	private ControlPersona controlPersona;
	private ControlUsuario controlUsuario;
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
	private List<?> listaBusqueda;
	
	@FXML private TextField txtBusquedaCriterio;
	
	@FXML private Button btnBusquedaNuevo;
	@FXML public void nuevo(ActionEvent event) {
		this.gestorPantalla.lanzarPantalla(this.tipo, null);
	}
	
	@FXML private Button btnBusquedaSeleccionar;
	@FXML public void seleccionar(ActionEvent event) {
		Object fila = tblBusqueda.getSelectionModel().getSelectedItem();
		try {
			Method m = this.getClass().getDeclaredMethod("seleccionar" + this.tipo, Object.class);
			m.invoke(this, fila);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void recibirParametros(Map<String, Object> args) {
		this.tipo = (String) args.get(KEY_TIPO);
		this.controladorRespuesta = (ControladorVista) args.get(KEY_CONTROLADOR);
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
		for (Field campo : campos) {
			String varName = campo.getName();
			TableColumn columna = new TableColumn<>(Utilidades.primeraMayuscula(varName));
			columna.setCellValueFactory(new PropertyValueFactory(varName));
			this.tblBusqueda.getColumns().add(columna);
		}
		this.tblBusqueda.setItems(this.filasBusqueda);
	}

// ------------------------------- Específico ------------------------------- //
	
	public class FilaDocente {
		private int legajo;
		private String nombre;
		public FilaDocente(IDocente docente) {
			super();
			this.legajo = docente.getLegajo();
			this.nombre = docente.getPersona().getApellido() + " " + docente.getPersona().getNombre();
		}
		public int getLegajo() {
			return legajo;
		}
		public void setLegajo(int legajo) {
			this.legajo = legajo;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
	
	public void inicializarDocentes() {
		inicializarTabla(FilaDocente.class);
		this.controlDocente = new ControlDocente(this);
		
		this.listaBusqueda = new ArrayList<>(this.controlDocente.listarDocente(null));
		for (Object docente : this.listaBusqueda) {
			if (docente instanceof IDocente) {
				FilaDocente fc = new FilaDocente((IDocente) docente); 
				this.filasBusqueda.add(fc);
			}
		}
	}

}
