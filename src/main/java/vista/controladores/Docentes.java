package vista.controladores;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import controlador.ControlDocente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoCargo;
import modelo.auxiliares.TipoContacto;
import modelo.cargo.Cargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
import modelo.division.Division;
import modelo.division.IArea;
import modelo.division.IDivision;
import modelo.docente.CargoDocente;
import modelo.docente.Docente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.Contacto;
import modelo.persona.IContacto;
import modelo.persona.IPersona;
import modelo.persona.Persona;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de may. de 2018
 */
@SuppressWarnings("rawtypes")
public class Docentes extends ControladorVista {

	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	private ControlDocente control = new ControlDocente();


// ----------------------------- Pestaña Cargos ----------------------------- //
	public class FilaCargo {
		public int id;
		public String area;
		public String cargo;
		public String estado;
		public FilaCargo(int id, String area,
				String cargo, String estado) {
			super();
			this.id = id;
			this.area = area;
			this.cargo = cargo;
			this.estado = estado;
		}
		public int getId() { return id; }
		public void setId(int idCargo) { this.id = idCargo; }
		public String getArea() { return area; }
		public void setArea(String area) { this.area = area; }
		public String getCargo() { return cargo; }
		public void setCargo(String cargo) { this.cargo = cargo; }
		public String getEstado() { return estado; }
		public void setEstado(String estado) { this.estado = estado; }
	}

	public ICargoDocente cargoDocenteSeleccionado;
	public IDocente docenteSeleccionado;
	public List<ICargoDocente> listaCargos;
	public ObservableList<FilaCargo> filasCargos;

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

	@FXML public void seleccionarCargoDocente() {
		// TODO cargoDocenteSeleccionado = seleccionado de tblCargoDocente;
		FilaCargo fila = (FilaCargo) tblCargos.getSelectionModel().getSelectedItem();
		ICargoDocente cd = this.control.getCargoDocente();
		cd.setId(fila.getId());
		// Recuperar de la BD fila.getId()
		// cargoDocenteSeleccionado
	}

	@FXML public void inicializarTablaCargos() {
		inicializarTabla("Cargos");
	}

	@FXML public TableView tblCargos;
	@FXML public TableColumn colCargosId;
	@FXML public TableColumn colCargosArea;
	@FXML public TableColumn colCargosCargo;
	@FXML public TableColumn colCargosEstado;



	@FXML public Button btnCargosNuevo;
	@FXML public void nuevoCargo() {
		// Obtener un ICargoDocente vacío
		cargoDocenteSeleccionado = this.control.getCargoDocente();
//		vaciarCampos();

		/* Prueba Jefe de Area */
		TipoContacto tipoContactoJefe = new TipoContacto();
		tipoContactoJefe.setId(0);
		tipoContactoJefe.setDescripcion("MailLaboral");

		IContacto contactoJefe = new Contacto(1, tipoContactoJefe, "semint2018@gmail.com");

		IPersona personaJefe = new Persona();
		personaJefe.setContactos(Arrays.asList(contactoJefe));

	    IDocente docenteJefe = new Docente();
	    docenteJefe.setLegajo(121899);
	    docenteJefe.setPersona(personaJefe);

	    IDivision divisionBiologia = new Division(1, "Biología", docenteJefe, null, null, null);


		/* Prueba */
//	    Area a = new Area("B1", "Biología 1", null, null, null, null, null, null);
		Area a = new Area("B1", "Biología 1", divisionBiologia, null, null, null, null, null);
		Cargo c = new Cargo(1, "Profesor Titular Exclusiva", 40);
		EstadoCargo ec = new EstadoCargo(0, "Activo");
		TipoCargo tc = new TipoCargo(3, "Mi TipoCargo");
		CargoDocente cd = new CargoDocente(-1, a, c, tc, null, null, null,
				0.0f, null, null, null, null, ec);
		FilaCargo fc = new FilaCargo(cd.getId(), cd.getArea().getDescripcion(),
				cd.getCargo().getDescripcion(), cd.getEstado().getDescripcion());

		this.filasCargos.add(fc);
		
		this.cargoDocenteSeleccionado = cd; 
		
	}

	@FXML public Button btnCargosGuardar;
	@FXML public void guardarCargo() {

		try {
			/*
			cargoDocenteSeleccionado.setUltimoCosto(
		    		Utilidades.stringToFloat(txtCargosCosto.getText()));

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
			 */
			if (cargoDocenteSeleccionado.getId() == -1) {
				// Se agrega un nuevo Cargo Docente
			    EstadoOperacion estado = this.control.agregarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
			    if (estado.getEstado() != EstadoOperacion.CodigoEstado.INSERT_OK) {
			        alertaError("Agregar Cargo-Docente", "No se pudo agregar el cargo.");
			    }
			} else {
				// Se modifica un Cargo Docente anterior
			    EstadoOperacion estado = this.control.modificarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
                if (estado.getEstado() != EstadoOperacion.CodigoEstado.UPDATE_OK) {
                    alertaError("Modificar Cargo-Docente", "No se pudo modificar el cargo.");
                }
			}
		} catch (IllegalArgumentException e) {
			alertaError("Error en el campo Último costo", e.getMessage());
		}
	}

	@FXML public Button btnCargosDescartar;
	@FXML public void descartarCargo() {
	    cargoDocenteSeleccionado = null;
	    vaciarCamposCargos();
	    // actualizarCamposCargos(); Se ejecuta cuando se selecciona un cargo docente
	}

	@FXML public Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
	    FilaCargo fila = (FilaCargo) tblCargos.getSelectionModel().getSelectedItem();
        this.filasCargos.remove(fila);

        EstadoOperacion estado = this.control.quitarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
        if (estado.getEstado() != EstadoOperacion.CodigoEstado.INSERT_OK) {
            alertaError("Quitar Cargo-Docente", "No se pudo quitar el cargo.");
        }
	}



	public IArea areaSeleccionada;
	@FXML public TextField txtCargosArea;
	@FXML public Button btnCargosArea;
	@FXML private void seleccionarArea() {
        // TODO Seleccionar Área
//        areaSeleccionada = (?)
        txtCargosArea.setText(areaSeleccionada.getDescripcion());
    }

	public ICargo cargoSeleccionado;
	@FXML public TextField txtCargosCargo;
	@FXML public Button btnCargosCargo;
	@FXML private void seleccionarCargo() {
        // TODO Seleccionar Cargo
//        cargoSeleccionado = (?);
        txtCargosCargo.setText(cargoSeleccionado.getDescripcion());
    }


	@FXML public ComboBox<EstadoCargo> cmbCargosEstado;

	@FXML public ComboBox<TipoCargo> cmbCargosTipo;

	@FXML public TextField txtCargosDisp;
	@FXML public DatePicker dtpCargosDispDesde;
	@FXML public DatePicker dtpCargosDispHasta;

	@FXML public TextField txtCargosRes;
	@FXML public DatePicker dtpCargosResDesde;
	@FXML public DatePicker dtpCargosResHasta;

	@FXML public TextField txtCargosCosto;
	@FXML public DatePicker dtpCargosCosto;

	/**
	 * PRUEBA
	 */
	@Override
	public void inicializarTabla(String nombre) {
	    super.inicializarTabla(nombre);

	    /* Docente de prueba: */
	    IPersona personaSeleccionada = new Persona(
	            "Juran", "Tomás",
	            null, null, 21345678, null, null, null, null);
 
	    docenteSeleccionado = new Docente();
	    docenteSeleccionado.setLegajo(2);
	    docenteSeleccionado.setPersona(personaSeleccionada);

	    /* TODO Popular estados y tipos (?) */
        this.cmbCargosEstado.setItems(
                FXCollections.observableArrayList(
                        EstadoCargo.getLista()));

        this.cmbCargosTipo.setItems(
                FXCollections.observableArrayList(
                        TipoCargo.getLista()));


        /* Cargos y tipos Prueba
        this.cmbCargosEstado.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList(
                                new EstadoCargo(0, "Estado1"),
                                new EstadoCargo(1, "Estado2"))));
        this.cmbCargosTipo.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList(
                                new TipoCargo(0, "Tipo1"),
                                new TipoCargo(1, "Tipo2"))));
        */
	}

	/**
	 * Lanza una alerta al usuario sobre un error del sistema.
	 * @param encabezado Encabezado del mensaje
	 * @param contenido Contenido del mensaje
	 */
	private void alertaError(String encabezado, String contenido) {
	    Alert alerta = new Alert(AlertType.ERROR);
	    alerta.setTitle("Docentes");
	    alerta.setHeaderText(encabezado);
	    alerta.setContentText(contenido);
	    alerta.showAndWait();
	}

}
