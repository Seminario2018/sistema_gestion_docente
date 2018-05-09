package vista.controladores;

import java.net.URL;
import java.time.LocalDate;
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
import modelo.docente.CargoDocente;
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
	public class FilaCargo {
        public int idCargo;
        public String area;
        public String cargo;
        public String estado;
    }

	private List<ICargoDocente> listaCargosDocentes;
    private ICargoDocente cargoDocenteSeleccionado;
    private IArea areaSeleccionada;
    private ICargo cargoSeleccionado;
    private TipoCargo tipoCargoSeleccionado;

    private void vaciarCampos() {
        txtCargosArea.clear();
        txtCargosCargo.clear();
        txtCargosDisp.clear();
        dtpCargosDispDesde.getEditor().clear();
        dtpCargosDispHasta.getEditor().clear();
        txtCargosRes.clear();
        dtpCargosResDesde.getEditor().clear();
        dtpCargosResHasta.getEditor().clear();
        txtCargosCosto.clear();
        dtpCargosCosto.getEditor().clear();
    }

	@FXML private Button btnCargosNuevo;
	@FXML public void nuevoCargo() {
		// Obtener un ICargoDocente vacío
		cargoDocenteSeleccionado = control.getICargoDocente();
		vaciarCampos();
	}

	@FXML private Button btnCargosGuardar;
	@FXML public void guardarCargo() {
		// TODO Enviar a persistir el cargoDocenteSeleccionado
	    String disposicion = txtCargosDisp.getText();
        LocalDate dispDesde = dtpCargosDispDesde.getValue();
        LocalDate dispHasta = dtpCargosDispHasta.getValue();
        float ultimoCosto = Float.parseFloat(txtCargosCosto.getText());
        LocalDate fechaUltCost = dtpCargosCosto.getValue();
        String resolucion = txtCargosRes.getText();
        LocalDate resDesde = dtpCargosResDesde.getValue();
        LocalDate resHasta = dtpCargosResHasta.getValue();
        EstadoCargo estado = new EstadoCargo();

		if (cargoDocenteSeleccionado.getCargo().getCodigo() == 0) {
		    // TODO CargoDocente id?
		    ICargoDocente cargoDocente = new CargoDocente(
	                areaSeleccionada,
	                cargoSeleccionado, tipoCargoSeleccionado,
	                disposicion, dispDesde, dispHasta,
	                ultimoCosto, fechaUltCost,
	                resolucion, resDesde, resHasta,
	                estado);
		    // TODO CargoDocente nuevo
		} else {
		    cargoDocenteSeleccionado.setArea(areaSeleccionada);
		    cargoDocenteSeleccionado.setCargo(cargoSeleccionado);
		    cargoDocenteSeleccionado.setDispDesde(dispDesde);
		    cargoDocenteSeleccionado.setDispHasta(dispHasta);
		    cargoDocenteSeleccionado.setDisposicion(disposicion);
		    cargoDocenteSeleccionado.setEstado(estado);
		    cargoDocenteSeleccionado.setFechaUltCost(fechaUltCost);
		    cargoDocenteSeleccionado.setResDesde(resDesde);
		    cargoDocenteSeleccionado.setResHasta(resHasta);
		    cargoDocenteSeleccionado.setTipoCargo(tipoCargoSeleccionado);
		    cargoDocenteSeleccionado.setUltimoCosto(ultimoCosto);
		    // TODO Actualizar CargoDocente
		}
	}

	@FXML private Button btnCargosDescartar;
	@FXML public void descartarCargo() {
	    vaciarCampos();
	}

	@FXML private Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
		// TODO Enviar a eliminar el cargoDocenteSeleccionado
	}

	@FXML private TableView<FilaCargo> tblCargosDocentes;
	@FXML private TableColumn colCargosID;
	@FXML private TableColumn colCargosArea;
	@FXML private TableColumn colCargosCargo;
	@FXML private TableColumn colCargosEstado;

	@FXML public void seleccionarCargoDocente() {
		// TODO cargoDocenteSeleccionado = seleccionado de tblCargoDocente;
	    FilaCargo fila = tblCargosDocentes.getSelectionModel().getSelectedItem();

	}

	@FXML private TextField txtCargosArea;
	@FXML private Button btnCargosArea;

	@FXML private TextField txtCargosCargo;
	@FXML private Button btnCargosCargo;

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
