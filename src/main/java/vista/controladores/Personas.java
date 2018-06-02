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
import modelo.persona.IContacto;
import modelo.persona.IDomicilio;
import modelo.persona.IPersona;
import modelo.persona.ITitulo;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Personas extends ControladorVista implements Initializable {

    public static final String TITULO = "Personas";

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

	/** Vacía los controles de datos */
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
	protected ObservableList<FilaContacto> filasContactos = FXCollections.observableArrayList();

	public class FilaContacto {
	    private IContacto contacto;
	    public FilaContacto(IContacto contacto) {
	        this.contacto = contacto;
	    }
	    public String getTipo() {
	        return this.contacto.getTipo().getDescripcion();
	    }
	    public String getDato() {
	        return this.contacto.getDato();
	    }
	    public IContacto getContacto() {
	        return this.contacto;
	    }
	}

	/** Refresca la tabla de contactos */
	private void contactosActualizarTabla() {
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

	/** Vacía los controles de datos del contacto */
    private void contactosVaciarControles() {
        cmbContactosTipo.getSelectionModel().clearSelection();
        txtContactosDato.clear();
    }


	@FXML private void inicializarContactos() {
	    inicializarTabla("Contactos");

	    contactosActualizarTabla();

	    cmbContactosTipo.setItems(
	        FXCollections.observableArrayList(
	            TipoContacto.getLista()));
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

	    EstadoOperacion resultado = this.controlPersona.guardarContacto(personaSeleccionada, contactoSeleccionado);
        switch (resultado.getEstado()) {
            case INSERT_ERROR:
            case UPDATE_ERROR:
                alertaError(TITULO, "Guardar Contacto", resultado.getMensaje());
                break;
            case INSERT_OK:
            case UPDATE_OK:
                dialogoConfirmacion(TITULO, "Guardar Contacto", resultado.getMensaje());
                break;
            default:
                throw new RuntimeException("Estado de modificación no esperado: " 
                		+ resultado.getEstado().toString() + ": " + resultado.getMensaje());
        }
	    contactosActualizarTabla();
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

	    EstadoOperacion resultado = this.controlPersona.quitarContacto(personaSeleccionada, contactoSeleccionado);

	    switch(resultado.getEstado()) {
            case DELETE_ERROR:
                alertaError(TITULO, "Eliminar Contacto", resultado.getMensaje());
                break;
            case DELETE_OK:
                dialogoConfirmacion(TITULO, "Eliminar Contacto", resultado.getMensaje());
                contactoSeleccionado = null;
                contactosVaciarControles();
                break;
            default:
                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
        }
	    contactosActualizarTabla();
	}

	@FXML protected TableView<FilaContacto> tblContactos;
	@FXML protected TableColumn<FilaContacto, String> colContactosTipo;
	@FXML protected TableColumn<FilaContacto, String> colContactosDato;

	@FXML private ComboBox<TipoContacto> cmbContactosTipo;
	@FXML private TextField txtContactosDato;

// -------------------------- Pestaña Domicilios ---------------------------- //

	private IDomicilio domicilioSeleccionado = null;
    protected ObservableList<FilaDomicilio> filasDomicilios = FXCollections.observableArrayList();

    public class FilaDomicilio {
        private IDomicilio domicilio;
        public FilaDomicilio(IDomicilio domicilio) {
            this.domicilio = domicilio;
        }
        public String getProvincia() {
            return this.domicilio.getProvincia();
        }
        public String getCiudad() {
            return this.domicilio.getCiudad();
        }
        public String getCodigoPostal() {
            return this.domicilio.getCodigoPostal();
        }
        public String getDireccion() {
            return this.domicilio.getDireccion();
        }
        public IDomicilio getDomicilio() {
            return this.domicilio;
        }
    }

    /** Refresca la tabla de domicilios */
    private void domiciliosActualizarTabla() {
        filasDomicilios.clear();
        if (personaSeleccionada != null) {
            for (IDomicilio domicilio: personaSeleccionada.getDomicilios()) {
                filasDomicilios.add(
                    new FilaDomicilio(domicilio));
            }
        }
    }

    /** Muestra los datos del domicilio seleccionado: */
    private void domiciliosMostrarDomicilio() {
        if (domicilioSeleccionado != null) {
            cmbDomiciliosProvincia.getSelectionModel().select(domicilioSeleccionado.getProvincia());
            txtDomiciliosCiudad.setText(domicilioSeleccionado.getCiudad());
            txtDomiciliosCP.setText(domicilioSeleccionado.getCodigoPostal());
            txtDomiciliosDireccion.setText(domicilioSeleccionado.getDireccion());
        }
    }

    /** Vacía los controles de datos del domicilio */
    private void domiciliosVaciarControles() {
        cmbDomiciliosProvincia.getSelectionModel().clearSelection();
        txtDomiciliosCiudad.clear();
        txtDomiciliosCP.clear();
        txtDomiciliosDireccion.clear();
    }

    @FXML private void inicializarDomicilios() {
        inicializarTabla("Domicilios");

        domiciliosActualizarTabla();

        // TODO Llenar combobox de provincias
    }

	@FXML private Button btnDomiciliosNuevo;
	@FXML public void nuevoDomicilio(ActionEvent event) {
	    domicilioSeleccionado = this.controlPersona.getIDomicilio();
	    domiciliosVaciarControles();
	}

	@FXML private Button btnDomiciliosGuardar;
	@FXML public void guardarDomicilio(ActionEvent event) {
	    domicilioSeleccionado.setProvincia(cmbDomiciliosProvincia.getSelectionModel().getSelectedItem());
	    domicilioSeleccionado.setCiudad(txtDomiciliosCiudad.getText());
	    domicilioSeleccionado.setCodigoPostal(txtDomiciliosCP.getText());
	    domicilioSeleccionado.setDireccion(txtDomiciliosDireccion.getText());

        EstadoOperacion resultado = this.controlPersona.guardarDomicilio(personaSeleccionada, domicilioSeleccionado);
        switch (resultado.getEstado()) {
            case INSERT_ERROR:
            case UPDATE_ERROR:
                alertaError(TITULO, "Guardar Domicilio", resultado.getMensaje());
                break;
            case INSERT_OK:
            case UPDATE_OK:
                dialogoConfirmacion(TITULO, "Guardar Domicilio", resultado.getMensaje());
                break;
            default:
                throw new RuntimeException("Estado de modificación no esperado: " + resultado.getMensaje());
        }
        domiciliosActualizarTabla();
	}

	@FXML private Button btnDomicilioDescartar;
	@FXML public void descartarDomicilio(ActionEvent event) {
	    domicilioSeleccionado = null;
        domiciliosVaciarControles();
	}

	@FXML private Button btnDomiciliosEliminar;
	@FXML public void eliminarDomicilio(ActionEvent event) {
	    FilaDomicilio filaSeleccionada = this.tblDomicilios.getSelectionModel().getSelectedItem();
        domicilioSeleccionado = filaSeleccionada.getDomicilio();

        EstadoOperacion resultado = this.controlPersona.quitarDomicilio(personaSeleccionada, domicilioSeleccionado);

        switch(resultado.getEstado()) {
            case DELETE_ERROR:
                alertaError(TITULO, "Eliminar Domicilio", resultado.getMensaje());
                break;
            case DELETE_OK:
                dialogoConfirmacion(TITULO, "Eliminar Domicilio", resultado.getMensaje());
                domicilioSeleccionado = null;
                domiciliosVaciarControles();
                break;
            default:
                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
        }
        domiciliosActualizarTabla();
	}

	@FXML protected TableView<FilaDomicilio> tblDomicilios;
	@FXML protected TableColumn<FilaDomicilio, String> colDomiciliosProvincia;
	@FXML protected TableColumn<FilaDomicilio, String> colDomiciliosCiudad;
	@FXML protected TableColumn<FilaDomicilio, String> colDomiciliosCP;
	@FXML protected TableColumn<FilaDomicilio, String> colDomiciliosDireccion;

	@FXML private ComboBox<String> cmbDomiciliosProvincia;
	@FXML private TextField txtDomiciliosCiudad;
	@FXML private TextField txtDomiciliosCP;
	@FXML private TextField txtDomiciliosDireccion;

// ---------------------------- Pestaña Títulos ----------------------------- //

	private ITitulo tituloSeleccionado = null;
	protected ObservableList<FilaTitulo> filasTitulos = FXCollections.observableArrayList();

	public class FilaTitulo {
	    private ITitulo titulo;
	    public FilaTitulo(ITitulo titulo) {
	        this.titulo = titulo;
	    }
	    public String getNombre() {
	        return this.titulo.getNombre();
	    }
	    public boolean esMayor() {
	        return this.titulo.isEsMayor();
	    }
	    public ITitulo getTitulo() {
	        return this.titulo;
	    }
	}

	/** Refresca la tabla de títulos */
    private void titulosActualizarTabla() {
        filasTitulos.clear();
        if (personaSeleccionada != null) {
            for (ITitulo titulo : personaSeleccionada.getTitulos()) {
                filasTitulos.add(new FilaTitulo(titulo));
            }
        }
    }

    /** Muestra los datos del titulo seleccionado: */
    private void titulosMostrarTitulo() {
        if (domicilioSeleccionado != null) {
            txtTitulosTitulo.setText(tituloSeleccionado.getNombre());
        }
    }

    /** Vacía los campos de datos del título */
    private void titulosVaciarControles() {
        txtTitulosTitulo.clear();
    }

	@FXML private void inicializarTitulos() {
	    inicializarTabla("Titulos");
	    titulosActualizarTabla();
	}

	@FXML private TextField txtTitulosTitulo;

	@FXML private Button btnTitulosAgregar;
	@FXML public void agregarTitulo(ActionEvent event) {
	    ITitulo tituloNuevo = this.controlPersona.getITitulo();
	    tituloNuevo.setNombre(txtTitulosTitulo.getText());

	    EstadoOperacion resultado = this.controlPersona.guardarTitulo(personaSeleccionada, tituloSeleccionado);
	    switch (resultado.getEstado()) {
	        case INSERT_ERROR:
            case UPDATE_ERROR:
                alertaError(TITULO, "Guardar Título", resultado.getMensaje());
                break;
            case INSERT_OK:
            case UPDATE_OK:
                dialogoConfirmacion(TITULO, "Guardar Título", resultado.getMensaje());
                break;
            default:
                throw new RuntimeException("Estado de modificación no esperado: " + resultado.getMensaje());
        }
	    titulosActualizarTabla();
	}

	@FXML private Button btnTitulosQuitar;
	@FXML public void quitarTitulo(ActionEvent event) {
	    FilaTitulo filaSeleccionada = this.tblTitulos.getSelectionModel().getSelectedItem();
        tituloSeleccionado = filaSeleccionada.getTitulo();

        EstadoOperacion resultado = this.controlPersona.quitarTitulo(personaSeleccionada, tituloSeleccionado);
        switch(resultado.getEstado()) {
            case DELETE_ERROR:
                alertaError(TITULO, "Eliminar Titulo", resultado.getMensaje());
                break;
            case DELETE_OK:
                dialogoConfirmacion(TITULO, "Eliminar Titulo", resultado.getMensaje());
                tituloSeleccionado = null;
                titulosVaciarControles();
                break;
            default:
                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
        }
        titulosActualizarTabla();
	}

	@FXML protected TableView<FilaTitulo> tblTitulos;
	@FXML protected TableColumn<FilaTitulo, String> colTitulosTitulo;
	@FXML protected TableColumn<FilaTitulo, Boolean> colTituloMayor;

}
