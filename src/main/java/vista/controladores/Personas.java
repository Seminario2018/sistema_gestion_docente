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

	private IPersona personaSeleccion = null;
	private ControlPersona controlPersona = new ControlPersona();

	/** Muestra los datos de la persona en los controles generales */
	private void generalMostrarPersona() {
	    if (personaSeleccion != null) {
	        txtPersonasDocumento.setText(String.valueOf(personaSeleccion.getNroDocumento()));
	        txtPersonasNombre.setText(personaSeleccion.getNombreCompleto());
	    }
	}

	/** Vacía los controles generales */
	private void generalVaciarControles() {
	    txtPersonasDocumento.clear();
	    txtPersonasNombre.clear();

	    // Vaciar controles de las pestañas:
	    vaciarTablas();
	    datosVaciarControles();
	    contactosVaciarControles();
	    domiciliosVaciarControles();
	    titulosVaciarControles();
	}

	@FXML private TextField txtPersonasDocumento;
	@FXML private TextField txtPersonasNombre;

	@FXML private Button btnPersonasBuscar;
	@FXML public void buscarPersona(ActionEvent event) {
	    /* TEST General: Selección de persona */
        // Recuperar la persona con DNI = 17200893
        IPersona personaBusqueda = this.controlPersona.getIPersona();
        personaBusqueda.setNroDocumento(17200893);
        List<IPersona> listaPersonas = this.controlPersona.listarPersonas(personaBusqueda);
        personaSeleccion = listaPersonas.get(0);
        //*/

        generalMostrarPersona();
        datosMostrarPersona();
	}

	@FXML private Button btnPersonasNueva;
	@FXML public void nuevaPersona(ActionEvent event) {
	    personaSeleccion = this.controlPersona.getIPersona();
	    generalVaciarControles();
	}

	@FXML private Button btnPersonasEliminar;
	@FXML public void eliminarPersona(ActionEvent event) {
	    if (personaSeleccion != null) {
            EstadoOperacion resultado = this.controlPersona.eliminarPersona(personaSeleccion);
            switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Persona", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    dialogoConfirmacion(TITULO, "Eliminar Persona", resultado.getMensaje());
                    personaSeleccion = null;

                    generalVaciarControles();
                    break;
                default:
                    throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
            }
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
	    if (personaSeleccion != null) {
	        txtDatosApellido.setText(personaSeleccion.getApellido());
	        txtDatosNombre.setText(personaSeleccion.getNombre());
	        cmbDatosTipoDocumento.getSelectionModel().select(personaSeleccion.getTipoDocumento());
	        txtDatosDocumento.setText(String.valueOf(personaSeleccion.getNroDocumento()));
	        dtpDatosFechaNacimiento.setValue(personaSeleccion.getFechaNacimiento());
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
	    if (personaSeleccion != null) {
	        try {
	            personaSeleccion.setNroDocumento(Integer.parseInt(txtDatosDocumento.getText()));
    	        personaSeleccion.setApellido(txtDatosApellido.getText());
    	        personaSeleccion.setNombre(txtDatosNombre.getText());
    	        personaSeleccion.setTipoDocumento(cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
    	        personaSeleccion.setFechaNacimiento(dtpDatosFechaNacimiento.getValue());

    	        this.controlPersona.modificarPersona(personaSeleccion);
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

	private IContacto contactoSeleccion = null;
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
	    public IContacto getInstanciaContacto() {
	        return this.contacto;
	    }
	}

	/** Refresca la tabla de contactos */
	private void contactosActualizarTabla() {
	    filasContactos.clear();
	    if (personaSeleccion != null) {
            for (IContacto contacto : personaSeleccion.getContactos()) {
                filasContactos.add(
                    new FilaContacto(contacto));
            }
        }
	}

	/** Muestra los datos del contacto seleccionado: */
    private void contactosMostrarContacto() {
        if (contactoSeleccion != null) {
            cmbContactosTipo.getSelectionModel().select(contactoSeleccion.getTipo());;
            txtContactosDato.setText(contactoSeleccion.getDato());
        }
    }

	/** Vacía los controles de datos del contacto */
    private void contactosVaciarControles() {
        cmbContactosTipo.getSelectionModel().clearSelection();
        txtContactosDato.clear();
    }

	@FXML private void inicializarContactos() {
	    inicializarTabla("Contactos");
	    tblContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                contactoSeleccion = newSelection.getInstanciaContacto();
                contactosMostrarContacto();
            }
        });

	    contactosActualizarTabla();

	    cmbContactosTipo.setItems(
	        FXCollections.observableArrayList(
	            TipoContacto.getLista()));
	}

	@FXML private Button btnContactosNuevo;
	@FXML public void nuevoContacto(ActionEvent event) {
	    contactoSeleccion = this.controlPersona.getIContacto();
	    contactosVaciarControles();
	}

	@FXML private Button btnContactosGuardar;
	@FXML public void guardarContacto(ActionEvent event) {
	    if (personaSeleccion != null && contactoSeleccion != null) {
    	    contactoSeleccion.setTipo(cmbContactosTipo.getSelectionModel().getSelectedItem());
    	    contactoSeleccion.setDato(txtContactosDato.getText());

    	    EstadoOperacion resultado = this.controlPersona.guardarContacto(personaSeleccion, contactoSeleccion);
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
	}

	@FXML private Button btnContactosDescartar;
	@FXML public void descartarContacto(ActionEvent event) {
	    contactoSeleccion = null;
	    contactosVaciarControles();
	}

	@FXML private Button btnContactosEliminar;
	@FXML public void eliminarContacto(ActionEvent event) {
        if (personaSeleccion != null && contactoSeleccion != null) {
            EstadoOperacion resultado = this.controlPersona
                .quitarContacto(personaSeleccion, contactoSeleccion);

            switch (resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO,
                        "Eliminar Contacto",
                        resultado.getMensaje());
                    break;
                case DELETE_OK:
                    dialogoConfirmacion(TITULO,
                        "Eliminar Contacto",
                        resultado.getMensaje());
                    contactoSeleccion = null;
                    contactosVaciarControles();
                    break;
                default:
                    throw new RuntimeException(
                        "Estado de eliminación no esperado: " + resultado
                            .getMensaje());
            }
            contactosActualizarTabla();
        }
	}

	@FXML protected TableView<FilaContacto> tblContactos;
	@FXML protected TableColumn<FilaContacto, String> colContactosTipo;
	@FXML protected TableColumn<FilaContacto, String> colContactosDato;

	@FXML private ComboBox<TipoContacto> cmbContactosTipo;
	@FXML private TextField txtContactosDato;

// -------------------------- Pestaña Domicilios ---------------------------- //

	private IDomicilio domicilioSeleccion = null;
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
        public String getCP() {
            return this.domicilio.getCodigoPostal();
        }
        public String getDireccion() {
            return this.domicilio.getDireccion();
        }
        public IDomicilio getInstanciaDomicilio() {
            return this.domicilio;
        }
    }

    /** Refresca la tabla de domicilios */
    private void domiciliosActualizarTabla() {
        filasDomicilios.clear();
        if (personaSeleccion != null) {
            for (IDomicilio domicilio: personaSeleccion.getDomicilios()) {
                filasDomicilios.add(
                    new FilaDomicilio(domicilio));
            }
        }
    }

    /** Muestra los datos del domicilio seleccionado: */
    private void domiciliosMostrarDomicilio() {
        if (domicilioSeleccion != null) {
            cmbDomiciliosProvincia.getSelectionModel().select(domicilioSeleccion.getProvincia());
            txtDomiciliosCiudad.setText(domicilioSeleccion.getCiudad());
            txtDomiciliosCP.setText(domicilioSeleccion.getCodigoPostal());
            txtDomiciliosDireccion.setText(domicilioSeleccion.getDireccion());
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
        tblDomicilios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                domicilioSeleccion = newSelection.getInstanciaDomicilio();
                domiciliosMostrarDomicilio();
            }
        });

        domiciliosActualizarTabla();

        // TODO Llenar combobox de provincias
    }

	@FXML private Button btnDomiciliosNuevo;
	@FXML public void nuevoDomicilio(ActionEvent event) {
	    domicilioSeleccion = this.controlPersona.getIDomicilio();
	    domiciliosVaciarControles();
	}

	@FXML private Button btnDomiciliosGuardar;
	@FXML public void guardarDomicilio(ActionEvent event) {
	    if (personaSeleccion != null && domicilioSeleccion != null) {
    	    domicilioSeleccion.setProvincia(cmbDomiciliosProvincia.getSelectionModel().getSelectedItem());
    	    domicilioSeleccion.setCiudad(txtDomiciliosCiudad.getText());
    	    domicilioSeleccion.setCodigoPostal(txtDomiciliosCP.getText());
    	    domicilioSeleccion.setDireccion(txtDomiciliosDireccion.getText());

            EstadoOperacion resultado = this.controlPersona.guardarDomicilio(personaSeleccion, domicilioSeleccion);
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
	}

	@FXML private Button btnDomicilioDescartar;
	@FXML public void descartarDomicilio(ActionEvent event) {
	    domicilioSeleccion = null;
        domiciliosVaciarControles();
	}

	@FXML private Button btnDomiciliosEliminar;
	@FXML public void eliminarDomicilio(ActionEvent event) {
	    if (personaSeleccion != null && domicilioSeleccion != null) {
    	    EstadoOperacion resultado = this.controlPersona
    	        .quitarDomicilio(personaSeleccion, domicilioSeleccion);

            switch(resultado.getEstado()) {
                case DELETE_ERROR:
                    alertaError(TITULO, "Eliminar Domicilio", resultado.getMensaje());
                    break;
                case DELETE_OK:
                    dialogoConfirmacion(TITULO, "Eliminar Domicilio", resultado.getMensaje());
                    domicilioSeleccion = null;
                    domiciliosVaciarControles();
                    break;
                default:
                    throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
            }
            domiciliosActualizarTabla();
	    }
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

	private ITitulo tituloSeleccion = null;
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
	    public String getTitulo() {
	        return this.titulo.getNombre();
	    }
	    public ITitulo getInstanciaTitulo() {
	        return this.titulo;
	    }
	}

	/** Refresca la tabla de títulos */
    private void titulosActualizarTabla() {
        filasTitulos.clear();
        if (personaSeleccion != null) {
            for (ITitulo titulo : personaSeleccion.getTitulos()) {
                filasTitulos.add(new FilaTitulo(titulo));
            }
        }
    }

    /** Muestra los datos del titulo seleccionado: */
    private void titulosMostrarTitulo() {
        if (tituloSeleccion != null) {
            txtTitulosTitulo.setText(tituloSeleccion.getNombre());
        }
    }

    /** Vacía los campos de datos del título */
    private void titulosVaciarControles() {
        txtTitulosTitulo.clear();
    }

	@FXML private void inicializarTitulos() {
	    inicializarTabla("Titulos");
	    tblTitulos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tituloSeleccion = newSelection.getInstanciaTitulo();
                titulosMostrarTitulo();
            }
        });
	    titulosActualizarTabla();
	}

	@FXML private TextField txtTitulosTitulo;

	@FXML private Button btnTitulosAgregar;
	@FXML public void agregarTitulo(ActionEvent event) {
	    if (personaSeleccion != null && tituloSeleccion != null) {
    	    tituloSeleccion = this.controlPersona.getITitulo();
    	    tituloSeleccion.setNombre(txtTitulosTitulo.getText());

    	    EstadoOperacion resultado = this.controlPersona.guardarTitulo(personaSeleccion, tituloSeleccion);
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
	}

	@FXML private Button btnTitulosQuitar;
	@FXML public void quitarTitulo(ActionEvent event) {
	    if (personaSeleccion != null && tituloSeleccion != null) {
	        EstadoOperacion resultado = this.controlPersona.quitarTitulo(personaSeleccion, tituloSeleccion);
	        switch(resultado.getEstado()) {
	            case DELETE_ERROR:
	                alertaError(TITULO, "Eliminar Titulo", resultado.getMensaje());
	                break;
	            case DELETE_OK:
	                dialogoConfirmacion(TITULO, "Eliminar Titulo", resultado.getMensaje());
	                tituloSeleccion = null;
	                titulosVaciarControles();
	                break;
	            default:
	                throw new RuntimeException("Estado de eliminación no esperado: " + resultado.getMensaje());
	        }
	        titulosActualizarTabla();
	    }
	}
	
	@FXML private Button btnTitulosMayor;
	@FXML public void mayorTitulo(ActionEvent event) {
		// TODO Marcar como título mayor al título seleccionado, sobreescribiendo el anterior
	}

	@FXML protected TableView<FilaTitulo> tblTitulos;
	@FXML protected TableColumn<FilaTitulo, String> colTitulosTitulo;
	@FXML protected TableColumn<FilaTitulo, Boolean> colTituloMayor;

}
