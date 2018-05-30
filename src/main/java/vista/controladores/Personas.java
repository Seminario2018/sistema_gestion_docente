package vista.controladores;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import controlador.ControlPersona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.Contacto;
import modelo.persona.IContacto;
import modelo.persona.IPersona;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Personas extends ControladorVista implements Initializable {

    private static final String TITULO = "Personas";

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

// -------------------------------- General --------------------------------- //

	private IPersona personaSeleccionada = null;
	private ControlPersona controlPersona = new ControlPersona();

	/** Muestra los datos de la persona en los controles generales */
	private void generalMostrarPersona() {
	    if (personaSeleccionada != null) {
	        txtPersonasDocumento.setText(String.valueOf(personaSeleccionada.getNroDocumento()));
	        txtPersonasNombre.setText(personaSeleccionada.getNombreCompleto());
	    }
	}

	/** Vacía los controles generales */
	private void generalVaciarControles() {
	    txtPersonasDocumento.clear();
	    txtPersonasNombre.clear();
	}

	@FXML private TextField txtPersonasDocumento;
	@FXML private TextField txtPersonasNombre;

	@FXML private Button btnPersonasBuscar;
	@FXML public void buscarPersona(ActionEvent event) {
	    /* TEST General: Selección de persona */
        // Recuperar la persona con DNI = 10071374
        IPersona personaBusqueda = this.controlPersona.getIPersona();
        personaBusqueda.setNroDocumento(10071374);
        List<IPersona> listaPersonas = this.controlPersona.listarPersonas(personaBusqueda);
        personaSeleccionada = listaPersonas.get(0);
        //*/
        generalMostrarPersona();
        datosMostrarPersona();
	}

	@FXML private Button btnPersonasNueva;
	@FXML public void nuevaPersona(ActionEvent event) {

	}

	@FXML private Button btnPersonasEliminar;
	@FXML public void eliminarPersona(ActionEvent event) {
	    EstadoOperacion resultado = this.controlPersona.eliminarPersona(personaSeleccionada);

	    switch(resultado.getEstado()) {
            case DELETE_ERROR:
                alertaError(TITULO, "Eliminar Persona", resultado.getMensaje());
                break;
            case DELETE_OK:
                dialogoConfirmacion(TITULO, "Eliminar Persona", resultado.getMensaje());
                personaSeleccionada = null;

                generalVaciarControles();
                datosVaciarControles();
                break;
            default:
                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
	    }
	}

// ----------------------------- Pestaña Datos ------------------------------ //

	@FXML private void inicializarDatos() {
	    cmbDatosTipoDocumento.setItems(
	        FXCollections.observableArrayList(
	            TipoDocumento.getLista()));

	    datosMostrarPersona();
	}

	/** Muestra los datos de la persona seleccionada: */
	private void datosMostrarPersona() {
	    if (personaSeleccionada != null) {
	        txtDatosApellido.setText(personaSeleccionada.getApellido());
	        txtDatosNombre.setText(personaSeleccionada.getNombre());
	        cmbDatosTipoDocumento.getSelectionModel().select(personaSeleccionada.getTipoDocumento());
	        txtDatosDocumento.setText(String.valueOf(personaSeleccionada.getNroDocumento()));
	        dtpDatosFechaNacimiento.setValue(personaSeleccionada.getFechaNacimiento());
	    }
	}

	/** Vacía los campos de datos */
	private void datosVaciarControles() {
	    txtDatosApellido.clear();
	    txtDatosNombre.clear();
	    cmbDatosTipoDocumento.getSelectionModel().clearSelection();
	    txtDatosDocumento.clear();
	    dtpDatosFechaNacimiento.setValue(null);
	}

	@FXML private Button btnDatosGuardar;
	@FXML public void guardarPersona(ActionEvent event) {
	    if (personaSeleccionada != null) {
	        try {
	            personaSeleccionada.setNroDocumento(Integer.parseInt(txtDatosDocumento.getText()));
    	        personaSeleccionada.setApellido(txtDatosApellido.getText());
    	        personaSeleccionada.setNombre(txtDatosNombre.getText());
    	        personaSeleccionada.setTipoDocumento(cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
    	        personaSeleccionada.setFechaNacimiento(dtpDatosFechaNacimiento.getValue());

    	        this.controlPersona.modificarPersona(personaSeleccionada);
	        } catch (NumberFormatException nfe) {
	            alertaError(TITULO, "Guardar Persona", "El documento tiene que ser numérico");
	        }
	    }
	}

	@FXML private Button btnDatosDescartar;
	@FXML public void descartarPersona(ActionEvent event) {
	    datosMostrarPersona();
	}

	@FXML private TextField txtDatosApellido;
	@FXML private TextField txtDatosNombre;
	@FXML private ComboBox<TipoDocumento> cmbDatosTipoDocumento;
	@FXML private TextField txtDatosDocumento;
	@FXML private DatePicker dtpDatosFechaNacimiento;


// --------------------------- Pestaña Contactos ---------------------------- //

	private IContacto contactoSeleccionado = null;
	private ObservableList<FilaContacto> filasContactos = FXCollections.observableArrayList();

	public class FilaContacto {
	    private int id;
	    private TipoContacto tipo;
	    private String dato;
	    public FilaContacto(IContacto contacto) {
	        this.id = contacto.getId();
	        this.tipo = contacto.getTipo();
	        this.dato = contacto.getDato();
	    }
	    public String getTipo() {
	        return this.tipo.getDescripcion();
	    }
	    public String getDato() {
	        return this.dato;
	    }
	    public IContacto getContacto() {
	        return new Contacto(this.id, this.tipo, this.dato);
	    }
	}

	/** Refresca la tabla de contactos */
	private void actualizarTablaContactos() {
	    filasContactos.clear();
	    if (personaSeleccionada != null) {
            for (IContacto contacto : personaSeleccionada.getContactos()) {
                filasContactos.add(
                    new FilaContacto(contacto));
            }
        }
	}

	/** Muestra los datos del contacto seleccionado: */
    private void contactosMostrarContacto() {
        if (contactoSeleccionado != null) {
            cmbContactosTipo.getSelectionModel().select(contactoSeleccionado.getTipo());;
            txtContactosDato.setText(contactoSeleccionado.getDato());
        }
    }

	/** Vacía los campos de datos del contacto */
    private void contactosVaciarControles() {
        cmbContactosTipo.getSelectionModel().clearSelection();
        txtContactosDato.clear();
    }


	@FXML private void inicializarContactos() {
	    inicializarTabla("Contactos");

	    actualizarTablaContactos();

	    cmbContactosTipo.setItems(
	        FXCollections.observableArrayList(
	            TipoContacto.getLista()));

	    contactosMostrarContacto();
	}

	@FXML private Button btnContactosNuevo;
	@FXML public void nuevoContacto(ActionEvent event) {
	    contactoSeleccionado = this.controlPersona.getIContacto();
	    contactosVaciarControles();
	}

	@FXML private Button btnContactosGuardar;
	@FXML public void guardarContacto(ActionEvent event) {
	    contactoSeleccionado.setTipo(cmbContactosTipo.getSelectionModel().getSelectedItem());
	    contactoSeleccionado.setDato(txtContactosDato.getText());

	    personaSeleccionada.getContactos().add(contactoSeleccionado);
	    this.controlPersona.modificarPersona(personaSeleccionada);

	    actualizarTablaContactos();
	}

	@FXML private Button btnContactosDescartar;
	@FXML public void descartarContacto(ActionEvent event) {
	    contactoSeleccionado = null;
	    contactosVaciarControles();
	}

	@FXML private Button btnContactosEliminar;
	@FXML public void eliminarContacto(ActionEvent event) {
	    FilaContacto filaSeleccionada = this.tblContactos.getSelectionModel().getSelectedItem();
	    contactoSeleccionado = filaSeleccionada.getContacto();

	    personaSeleccionada.getContactos().remove(contactoSeleccionado);
	    EstadoOperacion resultado = this.controlPersona.modificarPersona(personaSeleccionada);

	    switch(resultado.getEstado()) {
            case DELETE_ERROR:
                alertaError(TITULO, "Eliminar Contacto", resultado.getMensaje());
                personaSeleccionada.getContactos().add(contactoSeleccionado);
                break;
            case DELETE_OK:
                dialogoConfirmacion(TITULO, "Eliminar Contacto", resultado.getMensaje());
                contactoSeleccionado = null;
                contactosVaciarControles();
                break;
            default:
                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
        }
	    actualizarTablaContactos();
	}

	@FXML private TableView<FilaContacto> tblContactos;
	@FXML private TableColumn<FilaContacto, String> colContactosTipo;
	@FXML private TableColumn<FilaContacto, String> colContactosDato;

	@FXML private ComboBox<TipoContacto> cmbContactosTipo;
	@FXML private TextField txtContactosDato;

// -------------------------- Pestaña Domicilios ---------------------------- //

	@FXML private Button btnDomiciliosNuevo;
	@FXML public void nuevoDomicilio(ActionEvent event) {

	}

	@FXML private Button btnDomiciliosGuardar;
	@FXML public void guardarDomicilio(ActionEvent event) {

	}

	@FXML private Button btnDomicilioDescartar;
	@FXML public void descartarDomicilio(ActionEvent event) {

	}

	@FXML private Button btnDomiciliosEliminar;
	@FXML public void eliminarDomicilio(ActionEvent event) {

	}

	@FXML private TableView<?> tblDomicilios;
	@FXML private TableColumn<?, ?> colDomiciliosProvincia;
	@FXML private TableColumn<?, ?> colDomiciliosCiudad;
	@FXML private TableColumn<?, ?> colDomiciliosCP;
	@FXML private TableColumn<?, ?> colDomiciliosDireccion;

	@FXML private ComboBox<?> cmbDomiciliosProvincia;
	@FXML private TextField txtDomiciliosCiudad;
	@FXML private TextField txtDomiciliosCP;
	@FXML private TextField txtDomiciliosDireccion;

// ---------------------------- Pestaña Títulos ----------------------------- //

	@FXML private TextField txtTitulosTitulo;

	@FXML private Button btnTitulosAgregar;
	@FXML public void agregarTitulo(ActionEvent event) {

	}

	@FXML private Button btnTitulosQuitar;
	@FXML public void quitarTitulo(ActionEvent event) {

	}

	@FXML private TableView<?> tblTitulos;
	@FXML private TableColumn<?, ?> colTitulosTitulo;
	@FXML private TableColumn<?, ?> colTituloMayor;

}
