package vista.controladores;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import auxiliares.Numeros;
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
@SuppressWarnings("rawtypes")
public class Docentes {

	public void initialize(URL arg0, ResourceBundle arg1) {
	
	}

	private ControlDocente control = new ControlDocente();


// ----------------------------- Pestaña Cargos ----------------------------- //
	public class FilaCargo {
		public Integer id;
		public String area;
		public String cargo;
		public String estado;
		public FilaCargo(Integer id, String area,
				String cargo, String estado) {
			super();
			this.id = id;
			this.area = area;
			this.cargo = cargo;
			this.estado = estado;
		}
		public Integer getId() { return id; }
		public void setId(Integer idCargo) { this.id = idCargo; }
		public String getArea() { return area; }
		public void setArea(String area) { this.area = area; }
		public String getCargo() { return cargo; }
		public void setCargo(String cargo) { this.cargo = cargo; }
		public String getEstado() { return estado; }
		public void setEstado(String estado) { this.estado = estado; }
	}

	private ICargoDocente cargoDocenteSeleccionado;
	private List<ICargoDocente> listaCargosDocentes;
	private ObservableList<FilaCargo> filasCargosDocentes;

	private void vaciarCamposCargos() {
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

	@SuppressWarnings("unchecked")
	@FXML public void inicializarTablaCargos() {

		this.colCargosId.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,Integer>("id"));
		this.colCargosArea.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,String>("area"));
		this.colCargosCargo.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,String>("cargo"));
		this.colCargosEstado.setCellValueFactory(
				new PropertyValueFactory<FilaCargo,String>("estado"));

		this.filasCargosDocentes = FXCollections.observableArrayList();
		this.tblCargosDocentes.setItems(this.filasCargosDocentes);
	}

	@FXML private TableView tblCargosDocentes;
	@FXML private TableColumn colCargosId;
	@FXML private TableColumn colCargosArea;
	@FXML private TableColumn colCargosCargo;
	@FXML private TableColumn colCargosEstado;

	@FXML public void seleccionarCargoDocente() {
		// TODO cargoDocenteSeleccionado = seleccionado de tblCargoDocente;
		FilaCargo fila = (FilaCargo) tblCargosDocentes.getSelectionModel().getSelectedItem();
		ICargoDocente cd = this.control.getICargoDocente();
		cd.setId(fila.getId());
		// Recuperar de la BD fila.getId()
		// cargoDocenteSeleccionado
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
		/*
		EstadoCargo estado = cmbCargosEstado.getValue();
		TipoCargo tipoCargo = cmbCargosTipo.getValue();
	    String disposicion = txtCargosDisp.getText();
        LocalDate dispDesde = dtpCargosDispDesde.getValue();
        LocalDate dispHasta = dtpCargosDispHasta.getValue();
        String resolucion = txtCargosRes.getText();
        LocalDate resDesde = dtpCargosResDesde.getValue();
        LocalDate resHasta = dtpCargosResHasta.getValue();
        float ultimoCosto = Float.parseFloat(txtCargosCosto.getText());
        LocalDate fechaUltCost = dtpCargosCosto.getValue();
        
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
	    */ 
	    
		try {
			cargoDocenteSeleccionado.setUltimoCosto(
		    		Numeros.stringToFloat(txtCargosCosto.getText()));
			
			cargoDocenteSeleccionado.setArea(areaSeleccionada);
			cargoDocenteSeleccionado.setCargo(cargoSeleccionado);
			cargoDocenteSeleccionado.setEstado(cmbCargosEstado.getValue());
			cargoDocenteSeleccionado.setTipoCargo(cmbCargosTipo.getValue());
			cargoDocenteSeleccionado.setDisposicion(txtCargosDisp.getText());
			cargoDocenteSeleccionado.setDispDesde(dtpCargosDispDesde.getValue());
			cargoDocenteSeleccionado.setDispHasta(dtpCargosDispHasta.getValue());
			cargoDocenteSeleccionado.setResolucion(txtCargosRes.getText());
			cargoDocenteSeleccionado.setResDesde(dtpCargosResDesde.getValue());
			cargoDocenteSeleccionado.setResHasta(dtpCargosResHasta.getValue());
			
			cargoDocenteSeleccionado.setFechaUltCost(dtpCargosCosto.getValue());
			
			if (cargoDocenteSeleccionado.getId() == -1) {
				// Se agrega un nuevo Cargo Docente
				//this.control.agregarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
			} else {
				// Se modifica un Cargo Docente anterior
				//this.control.modificarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML private Button btnCargosDescartar;
	@FXML public void descartarCargo() {
	    vaciarCamposCargos();
	    // actualizarCamposCargos(); Se ejecuta cuando se selecciona un cargo docente
	}

	@FXML private Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
		// TODO Enviar a eliminar el cargoDocenteSeleccionado
	}


	
	
	private IArea areaSeleccionada;
	@FXML private TextField txtCargosArea;
	@FXML private Button btnCargosArea;

	private ICargo cargoSeleccionado;
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
