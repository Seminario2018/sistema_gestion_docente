package vista.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controlador.ControlDocente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;
import modelo.docente.ICargoDocente;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de may. de 2018
 */
public class Docentes {
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	private ControlDocente control = new ControlDocente();
	
	
// ----------------------------- Pestaña Cargos ----------------------------- //
	@FXML private Button btnCargosNuevo;
	@FXML public void nuevoCargo() {
		// Obtener un ICargoDocente vacío
		cargoDocenteSeleccionado = control.getICargoDocente();
		// TODO Vaciar todos los campos
	}
	
	@FXML private Button btnCargosGuardar;
	@FXML public void guardarCargo() {
		// TODO Enviar a persistir el cargoDocenteSeleccionado
	}
	
	@FXML private Button btnCargosDescartar;
	@FXML public void descartarCargo() {
		// TODO Vaciar todos los campos
	}
	
	@FXML private Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
		// TODO Enviar a eliminar el cargoDocenteSeleccionado
	}
	
	public class FilaCargo {
		private int idCargo;
		private String area;
		private String cargo;
		private String estado;
	}
	
	@FXML private TableView<FilaCargo> tblCargosDocentes;
	@FXML private TableColumn colCargosID;
	@FXML private TableColumn colCargosArea;
	@FXML private TableColumn colCargosCargo;
	@FXML private TableColumn colCargosEstado;
	private List<ICargoDocente> listaCargosDocentes;
	private ICargoDocente cargoDocenteSeleccionado;
	@FXML public void seleccionarCargoDocente() {
		// TODO cargoDocenteSeleccionado = seleccionado de tblCargoDocente;
	}
	
	@FXML private TextField txtCargosArea;
	@FXML private Button btnCargosArea;
	private IArea areaSeleccionada;
	
	@FXML private TextField txtCargosCargo;
	@FXML private Button btnCargosCargo;
	private ICargo cargoSeleccionado;
	
	@FXML private ComboBox<EstadoCargo> cmbCargosEstado;
	
	@FXML private ComboBox<TipoCargo> cmbCargosTipo;
	
	@FXML private TextField txtCargosDisp;
	@FXML private DatePicker dtpCargosDispDesde;
	@FXML private DatePicker dtpCargosDispHasta;
	
	@FXML private TextField txtCargosRes;
	@FXML private DatePicker dtpCargosResDesde;
	@FXML private DatePicker dtpCargosResHasta;
	
	@FXML private TextField txtCargosCosto;
	@FXML private DatePicker dtpCargosCosto;
	
}
