package vista.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import controlador.ControlDocente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.Cargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
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
        public Integer idCargo;
        public String area;
        public String cargo;
        public String estado;
        
		public FilaCargo(Integer idCargo, String area, String cargo, String estado) {
			super();
			this.idCargo = idCargo;
			this.area = area;
			this.cargo = cargo;
			this.estado = estado;
		}
		public Integer getIdCargo() {
			return idCargo;
		}
		public void setIdCargo(Integer idCargo) {
			this.idCargo = idCargo;
		}
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getCargo() {
			return cargo;
		}
		public void setCargo(String cargo) {
			this.cargo = cargo;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
    }

	private ObservableList<FilaCargo> filasCargosDocentes;
	private List<ICargoDocente> listaCargosDocentes;
    private ICargoDocente cargoDocenteSeleccionado;
    private IArea areaSeleccionada;
    private ICargo cargoSeleccionado;

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
		cargoDocenteSeleccionado = this.control.getICargoDocente();
//		vaciarCampos();
		/* Prueba */
		Area a = new Area("B1", "Biología 1", null, null, null, null, null, null);
		Cargo c = new Cargo(1, "Profesor adjunto semiexclusivo", 40);
		EstadoCargo ec = new EstadoCargo(0, "Activo");
		CargoDocente cd = new CargoDocente(1, a, c, null, null, null, null,
				0.0f, null, null, null, null, ec);
		FilaCargo fc = new FilaCargo(cd.getId(), cd.getArea().getDescripcion(),
				cd.getCargo().getDescripcion(), cd.getEstado().getDescripcion());
		
		this.filasCargosDocentes.add(fc);
		
	}

	@FXML private Button btnCargosGuardar;
	@FXML public void guardarCargo() {
	    String disposicion = txtCargosDisp.getText();
        LocalDate dispDesde = dtpCargosDispDesde.getValue();
        LocalDate dispHasta = dtpCargosDispHasta.getValue();
        float ultimoCosto = Float.parseFloat(txtCargosCosto.getText());
        LocalDate fechaUltCost = dtpCargosCosto.getValue();
        String resolucion = txtCargosRes.getText();
        LocalDate resDesde = dtpCargosResDesde.getValue();
        LocalDate resHasta = dtpCargosResHasta.getValue();
        EstadoCargo estado = cmbCargosEstado.getValue();
        TipoCargo tipoCargo = cmbCargosTipo.getValue();

		cargoDocenteSeleccionado.setArea(areaSeleccionada);
	    cargoDocenteSeleccionado.setCargo(cargoSeleccionado);
	    cargoDocenteSeleccionado.setDispDesde(dispDesde);
	    cargoDocenteSeleccionado.setDispHasta(dispHasta);
	    cargoDocenteSeleccionado.setDisposicion(disposicion);
	    cargoDocenteSeleccionado.setEstado(estado);
	    cargoDocenteSeleccionado.setFechaUltCost(fechaUltCost);
	    cargoDocenteSeleccionado.setResolucion(resolucion);
	    cargoDocenteSeleccionado.setResDesde(resDesde);
	    cargoDocenteSeleccionado.setResHasta(resHasta);
	    cargoDocenteSeleccionado.setTipoCargo(tipoCargo);
	    cargoDocenteSeleccionado.setUltimoCosto(ultimoCosto);

	    if (cargoDocenteSeleccionado.getId() == -1) {
	    	// Se agrega un nuevo Cargo Docente
	    	//this.control.agregarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
		} else {
			// Se modifica un Cargo Docente anterior
			//this.control.modificarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
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

	@FXML public void inicializarTablaCargos() {
		this.colCargosID.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,Integer>("idCargo"));
		this.colCargosArea.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,String>("area"));
		this.colCargosCargo.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,String>("cargo"));
		this.colCargosEstado.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,String>("estado"));
		
		this.filasCargosDocentes = FXCollections.observableArrayList();
		this.tblCargosDocentes.setItems(this.filasCargosDocentes);
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
