package vista.controladores;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import modelo.persona.IContacto;
import modelo.persona.IDomicilio;
import modelo.persona.IPersona;
import modelo.persona.ITitulo;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Personas extends ControladorVista implements Initializable {

    public void setPersonaSeleccion(Object persona, String tipo) {
        if (persona instanceof IPersona) {
            personaSeleccion = (IPersona) persona;
            modoModificar();
            generalMostrarPersona();
        }
    }

    public static final String TITULO = "Personas";

    // Tipos respuesta:
    private static final String TIPO_PERSONA = "Persona";

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@Override
    public void inicializar() {
        /* Ocultar controles según roles del usuario: */
        boolean crear = false;
        boolean modificar = false;
        boolean eliminar = false;
        boolean listar = false;

        for (IRol rol : this.usuario.getRoles()) {
            for (IPermiso permiso : rol.getPermisos()) {
                if (permiso.getModulo() == Modulo.PERSONAS) {
                    this.permiso = permiso;
                    crear |= permiso.getCrear();
                    modificar |= permiso.getModificar();
                    eliminar |= permiso.getEliminar();
                    listar |= permiso.getListar();
                }
            }
        }

        if (!crear) {
            btnPersonasNueva.setVisible(false);
        }

        if (!modificar) {
            // General:
            btnPersonasDescartar.setVisible(false);
            btnPersonasGuardar.setVisible(false);

            // Pestaña Datos:
//            btnDatosGuardar.setVisible(false);
//            btnDatosDescartar.setVisible(false);
            txtDatosApellido.setEditable(false);
            cmbDatosTipoDocumento.setDisable(true);
            txtDatosDocumento.setEditable(false);
            dtpDatosFechaNacimiento.setEditable(false);

            // Pestaña Contactos:
            btnContactosNuevo.setVisible(false);
            btnContactosGuardar.setVisible(false);
            btnContactosDescartar.setVisible(false);
            btnContactosEliminar.setVisible(false);

            cmbContactosTipo.setDisable(true);
            txtContactosDato.setEditable(false);

            // Pestaña Domicilios:
            btnDomiciliosNuevo.setVisible(false);
            btnDomiciliosGuardar.setVisible(false);
            btnDomicilioDescartar.setVisible(false);
            btnDomiciliosEliminar.setVisible(false);

            cmbDomiciliosProvincia.setDisable(true);
            txtDomiciliosCiudad.setEditable(false);
            txtDomiciliosCP.setEditable(false);
            txtDomiciliosDireccion.setEditable(false);

            // Pestaña Títulos:
            txtTitulosTitulo.setEditable(false);
            btnTitulosAgregar.setVisible(false);
            btnTitulosQuitar.setVisible(false);
            btnTitulosMayor.setVisible(false);
        }

        if (!eliminar) {
            btnPersonasEliminar.setVisible(false);
        }

        if (!listar) {
        }

        modoVer();
    }

	@Override
	public void modoModificar() {
	    if (this.permiso.getModificar()) {
	        // General:
	        btnPersonasDescartar.setVisible(true);
            btnPersonasGuardar.setVisible(true);

	        // Pestaña Datos:
//            btnDatosGuardar.setVisible(true);
//            btnDatosDescartar.setVisible(true);
            txtDatosApellido.setEditable(true);
            cmbDatosTipoDocumento.setDisable(false);
            txtDatosDocumento.setEditable(true);
            dtpDatosFechaNacimiento.setEditable(true);

            // Pestaña Contactos:
            contactosModoVer();
            btnContactosNuevo.setVisible(true);

            // Pestaña Domicilios:
            domiciliosModoVer();
            btnDomiciliosNuevo.setVisible(true);

            // Pestaña Títulos:
            txtTitulosTitulo.setEditable(true);
            btnTitulosAgregar.setVisible(true);
            btnTitulosQuitar.setVisible(true);
            btnTitulosMayor.setVisible(true);
	    }

	    if (this.permiso.getEliminar()) {
	        btnPersonasEliminar.setVisible(true);
	    }

	    this.window.setTitle(TITULO + " - Modificar Persona");
	    if (this.personaSeleccion != null) {
	        this.gestorPantalla.mensajeEstado("Modificar a la Persona " + this.personaSeleccion.getNombreCompleto());
	    }
	}

	@Override
    public void modoNuevo() {
	    if (this.permiso.getCrear()) {
	        btnPersonasNueva.setVisible(true);
	        btnPersonasDescartar.setVisible(true);
            btnPersonasGuardar.setVisible(true);
//	        btnDatosGuardar.setVisible(true);
//	        btnDatosDescartar.setVisible(true);
	        txtDatosApellido.setEditable(true);
	        txtDatosNombre.setEditable(true);
	        cmbDatosTipoDocumento.setDisable(false);
	        txtDatosDocumento.setEditable(true);
	        dtpDatosFechaNacimiento.setEditable(true);

	        this.window.setTitle(TITULO + " - Nueva Persona");
	        this.gestorPantalla.mensajeEstado("Nueva Persona ");
	    }
	}

	@Override
	public void modoVer() {
	    // General:
	    btnPersonasDescartar.setVisible(false);
	    btnPersonasEliminar.setVisible(false);
	    btnPersonasGuardar.setVisible(false);

	    // Pestaña Datos:
//        btnDatosGuardar.setVisible(false);
//        btnDatosDescartar.setVisible(false);
        txtDatosApellido.setEditable(false);
        cmbDatosTipoDocumento.setDisable(true);
        txtDatosDocumento.setEditable(false);
        dtpDatosFechaNacimiento.setDisable(false);

        // Pestaña Contactos:
        contactosModoVer();
        btnContactosNuevo.setVisible(false);

        // Pestaña Domicilios:
        domiciliosModoVer();
        btnDomiciliosNuevo.setVisible(false);

        // Pestaña Títulos:
        txtTitulosTitulo.setEditable(false);
        btnTitulosAgregar.setVisible(false);
        btnTitulosQuitar.setVisible(false);
        btnTitulosMayor.setVisible(false);

	    this.window.setTitle(TITULO);
	    this.gestorPantalla.mensajeEstado("");
	}

// -------------------------------- General --------------------------------- //

	private IPersona personaSeleccion = null;
	private ControlPersona controlPersona = new ControlPersona();

	/** Muestra los datos de la persona en los controles generales */
	private void generalMostrarPersona() {
	    if (personaSeleccion != null) {
	        txtPersonasDocumento.setText(String.valueOf(personaSeleccion.getNroDocumento()));
	        txtPersonasNombre.setText(personaSeleccion.getNombreCompleto());

	        datosMostrarPersona();
	        contactosActualizarTabla();
	        domiciliosActualizarTabla();
	        titulosActualizarTabla();
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

	    this.gestorPantalla.mensajeEstado("");
	}

	@FXML private TextField txtPersonasDocumento;
	@FXML private TextField txtPersonasNombre;

	@FXML private Button btnPersonasBuscar;
	@FXML public void buscarPersona(ActionEvent event) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Personas.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(GestorPantalla.KEY_PADRE, Personas.TITULO);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PERSONA);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Personas.TITULO, args);
	}

	@FXML private Button btnPersonasNueva;
	@FXML public void nuevaPersona(ActionEvent event) {
	    personaSeleccion = controlPersona.getIPersona();
	    generalVaciarControles();
//	    modoModificar();
	    modoNuevo();
//	    this.window.setTitle(TITULO + " - Nueva Persona");
//	    this.gestorPantalla.mensajeEstado("Nueva Persona");
	}

	@FXML private Button btnPersonasEliminar;
	@FXML public void eliminarPersona(ActionEvent event) {
	    if (personaSeleccion != null) {
            if (exitoEliminar(controlPersona.eliminarPersona(personaSeleccion), TITULO, "Eliminar Persona")) {
                personaSeleccion = null;
                generalVaciarControles();
                modoVer();
            }
        }
	}

	@FXML private Button btnPersonasGuardar;
	@FXML private void guardarPersona(ActionEvent event) {
	    if (personaSeleccion != null) {
            try {
                personaSeleccion.setNroDocumento(Integer.parseInt(txtDatosDocumento.getText()));
                personaSeleccion.setApellido(txtDatosApellido.getText());
                personaSeleccion.setNombre(txtDatosNombre.getText());
                personaSeleccion.setTipoDocumento(cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
                personaSeleccion.setFechaNacimiento(dtpDatosFechaNacimiento.getValue());

                exitoGuardado(controlPersona.guardarPersona(personaSeleccion), TITULO, "Guardar Persona");
                generalMostrarPersona();

                modoModificar();

            } catch (NumberFormatException e) {
                alertaError(TITULO, "Guardar Persona", "El documento tiene que ser numérico");
            }
        }
	}

	@FXML private Button btnPersonasDescartar;
	@FXML private void descartarPersona(ActionEvent event) {
	    datosMostrarPersona();
	}

	@Override
	public void recibirParametros(Map<String, Object> args) {
	    Object oSeleccion = args.get(Busqueda.KEY_SELECCION);
        if (oSeleccion != null) {
            String seleccion = (String) oSeleccion;
            Object valor = args.get(Busqueda.KEY_VALOR);
            String tipo_respuesta = (String) args.get(Busqueda.KEY_TIPO_RESPUESTA);
            try {
                String metodo = "set" + seleccion + "Seleccion";
                Method m = this.getClass().getDeclaredMethod(metodo, Object.class, String.class);
                m.invoke(this, valor, tipo_respuesta);
            } catch (Exception e) {
                e.printStackTrace();
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

//	@FXML private Button btnDatosGuardar;
//	@FXML public void guardarPersona(ActionEvent event) {
//	    if (personaSeleccion != null) {
//	        try {
//	            personaSeleccion.setNroDocumento(Integer.parseInt(txtDatosDocumento.getText()));
//    	        personaSeleccion.setApellido(txtDatosApellido.getText());
//    	        personaSeleccion.setNombre(txtDatosNombre.getText());
//    	        personaSeleccion.setTipoDocumento(cmbDatosTipoDocumento.getSelectionModel().getSelectedItem());
//    	        personaSeleccion.setFechaNacimiento(dtpDatosFechaNacimiento.getValue());
//
//    	        exitoGuardado(controlPersona.guardarPersona(personaSeleccion), TITULO, "Guardar Persona");
//    	        generalMostrarPersona();
//
//    	        modoModificar();
//
//	        } catch (NumberFormatException e) {
//	            alertaError(TITULO, "Guardar Persona", "El documento tiene que ser numérico");
//	        }
//	    }
//	}
//
//	@FXML private Button btnDatosDescartar;
//	@FXML public void descartarPersona(ActionEvent event) {
//	    datosMostrarPersona();
//	}

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

    private void contactosModoModificar() {
        if (this.permiso.getModificar()) {
            btnContactosGuardar.setVisible(true);
            btnContactosDescartar.setVisible(true);
            btnContactosEliminar.setVisible(true);

            cmbContactosTipo.setDisable(false);
            txtContactosDato.setEditable(true);
        }
    }

    private void contactosModoNuevo() {
        if (this.permiso.getModificar()) {
            contactosModoModificar();
            btnContactosEliminar.setVisible(false);
        }
    }

    private void contactosModoVer() {
        btnContactosDescartar.setVisible(false);
        btnContactosEliminar.setVisible(false);
        btnContactosGuardar.setVisible(false);
        cmbContactosTipo.setDisable(true);
        txtContactosDato.setEditable(false);
    }

	@FXML private void inicializarContactos() {
	    inicializarTabla("Contactos");
	    tblContactos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                contactoSeleccion = newSelection.getInstanciaContacto();
                contactosMostrarContacto();
                contactosModoModificar();
            }
        });

	    contactosActualizarTabla();

	    cmbContactosTipo.setItems(
	        FXCollections.observableArrayList(
	            TipoContacto.getLista()));
	}

	@FXML private Button btnContactosNuevo;
	@FXML public void nuevoContacto(ActionEvent event) {
	    if (personaSeleccion != null) {
    	    contactoSeleccion = controlPersona.getIContacto();
    	    contactosVaciarControles();
    	    contactosModoNuevo();
	    }
	}

	@FXML private Button btnContactosGuardar;
	@FXML public void guardarContacto(ActionEvent event) {
	    if (personaSeleccion != null && contactoSeleccion != null) {
    	    contactoSeleccion.setTipo(cmbContactosTipo.getSelectionModel().getSelectedItem());
    	    contactoSeleccion.setDato(txtContactosDato.getText());

    	    EstadoOperacion resultado = controlPersona.guardarContacto(personaSeleccion, contactoSeleccion);
            exitoGuardado(resultado, TITULO, "Guardar Contacto");
            contactosModoModificar();
    	    contactosActualizarTabla();
	    }
	}

	@FXML private Button btnContactosDescartar;
	@FXML public void descartarContacto(ActionEvent event) {
	    contactosMostrarContacto();
	}

	@FXML private Button btnContactosEliminar;
	@FXML public void eliminarContacto(ActionEvent event) {
        if (personaSeleccion != null && contactoSeleccion != null) {
            EstadoOperacion resultado = controlPersona.quitarContacto(personaSeleccion, contactoSeleccion);
            if (exitoEliminar(resultado, TITULO, "Eliminar Contacto")) {
                contactoSeleccion = null;
                contactosModoVer();
                contactosVaciarControles();
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

    private void domiciliosModoModificar() {
        if (this.permiso.getModificar()) {
            btnDomiciliosGuardar.setVisible(true);
            btnDomicilioDescartar.setVisible(true);
            btnDomiciliosEliminar.setVisible(true);
            cmbDomiciliosProvincia.setDisable(false);
            txtDomiciliosCiudad.setEditable(true);
            txtDomiciliosCP.setEditable(true);
            txtDomiciliosDireccion.setEditable(true);
        }
    }

    private void domiciliosModoNuevo() {
        if (this.permiso.getModificar()) {
            domiciliosModoModificar();
            btnDomiciliosEliminar.setVisible(false);
        }
    }

    private void domiciliosModoVer() {
        btnDomiciliosGuardar.setVisible(false);
        btnDomicilioDescartar.setVisible(false);
        btnDomiciliosEliminar.setVisible(false);

        cmbDomiciliosProvincia.setDisable(true);
        txtDomiciliosCiudad.setEditable(false);
        txtDomiciliosCP.setEditable(false);
        txtDomiciliosDireccion.setEditable(false);
    }

    @FXML private void inicializarDomicilios() {
        inicializarTabla("Domicilios");
        tblDomicilios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                domicilioSeleccion = newSelection.getInstanciaDomicilio();
                domiciliosMostrarDomicilio();
                domiciliosModoModificar();
            }
        });

        domiciliosActualizarTabla();

        // Llenar combobox de provincias:
        List<String> provincias = new ArrayList<String>();
        provincias.add("Buenos Aires");
        provincias.add("Catamarca");
        provincias.add("Chaco");
        provincias.add("Chubut");
        provincias.add("Córdoba");
        provincias.add("Corrientes");
        provincias.add("Entre Ríos");
        provincias.add("Formosa");
        provincias.add("Jujuy");
        provincias.add("La Pampa");
        provincias.add("La Rioja");
        provincias.add("Mendoza");
        provincias.add("Misiones");
        provincias.add("Neuquén");
        provincias.add("Rio Negro");
        provincias.add("Salta");
        provincias.add("San Juan");
        provincias.add("San Luis");
        provincias.add("Santa Cruz");
        provincias.add("Santa Fe");
        provincias.add("Santiago del Estero");
        provincias.add("Tierra del Fuego");
        provincias.add("Tucumán");

        cmbDomiciliosProvincia.setItems(
            FXCollections.observableArrayList(
                provincias));
    }

	@FXML private Button btnDomiciliosNuevo;
	@FXML public void nuevoDomicilio(ActionEvent event) {
	    domicilioSeleccion = controlPersona.getIDomicilio();
	    domiciliosVaciarControles();
	    domiciliosModoNuevo();
	}

	@FXML private Button btnDomiciliosGuardar;
	@FXML public void guardarDomicilio(ActionEvent event) {
	    if (personaSeleccion != null && domicilioSeleccion != null) {
    	    domicilioSeleccion.setProvincia(cmbDomiciliosProvincia.getSelectionModel().getSelectedItem());
    	    domicilioSeleccion.setCiudad(txtDomiciliosCiudad.getText());
    	    domicilioSeleccion.setCodigoPostal(txtDomiciliosCP.getText());
    	    domicilioSeleccion.setDireccion(txtDomiciliosDireccion.getText());

            exitoGuardado(controlPersona.guardarDomicilio(personaSeleccion, domicilioSeleccion), TITULO, "Guardar Domicilio");
            domiciliosModoModificar();
            domiciliosActualizarTabla();
	    }
	}

	@FXML private Button btnDomicilioDescartar;
	@FXML public void descartarDomicilio(ActionEvent event) {
	    domiciliosMostrarDomicilio();
	}

	@FXML private Button btnDomiciliosEliminar;
	@FXML public void eliminarDomicilio(ActionEvent event) {
	    if (personaSeleccion != null && domicilioSeleccion != null) {
    	    if (exitoEliminar(controlPersona.quitarDomicilio(personaSeleccion, domicilioSeleccion), TITULO, "Eliminar Domicilio")) {
    	        domicilioSeleccion = null;
    	        domiciliosModoVer();
                domiciliosVaciarControles();
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
	    public String getMayor() {
	        return this.titulo.isEsMayor() ? "Mayor" : "";
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
	    if (personaSeleccion != null) {
    	    tituloSeleccion = controlPersona.getITitulo();
    	    tituloSeleccion.setNombre(txtTitulosTitulo.getText());

    	    exitoGuardado(controlPersona.guardarTitulo(personaSeleccion, tituloSeleccion), TITULO, "Guardar Título");
    	    titulosActualizarTabla();
	    }
	}

	@FXML private Button btnTitulosQuitar;
	@FXML public void quitarTitulo(ActionEvent event) {
	    if (personaSeleccion != null && tituloSeleccion != null) {
	        if (exitoEliminar(controlPersona.quitarTitulo(personaSeleccion, tituloSeleccion), TITULO, "Eliminar Título")) {
	            tituloSeleccion = null;
                titulosVaciarControles();
	        }
	        titulosActualizarTabla();
	    }
	}

	@FXML private Button btnTitulosMayor;
	@FXML public void mayorTitulo(ActionEvent event) {
		// DONE Marcar como título mayor al título seleccionado, sobreescribiendo el anterior
	    if (personaSeleccion != null && tituloSeleccion != null) {
	        if (!tituloSeleccion.isEsMayor()) {
	            /* Si el título seleccionado no es el mayor entonces desmarco
	               todos los títulos. */
	            for (ITitulo titulo : personaSeleccion.getTitulos()) {
	                titulo.setEsMayor(false);
	            }

	            // Y marco el título actual como el mayor:
	            tituloSeleccion.setEsMayor(true);

	            // Y persisto los cambios en todos los títulos:
	            boolean exito = true;
	            for (ITitulo titulo : personaSeleccion.getTitulos()) {
	                EstadoOperacion resultado = controlPersona.guardarTitulo(personaSeleccion, titulo);
	                if (resultado.getEstado() != CodigoEstado.UPDATE_OK) {
	                    exito = false;
	                }
	            }

	            if (exito) {
	                gestorPantalla.mensajeEstado("El título fue seleccionado como el mayor.");
	            } else {
	                alertaError(TITULO, "Seleccionar mayor título", "El título no pudo modificarse.");
	            }

	            titulosActualizarTabla();
	        }
	    }
	}

	@FXML protected TableView<FilaTitulo> tblTitulos;
	@FXML protected TableColumn<FilaTitulo, String> colTitulosTitulo;
	@FXML protected TableColumn<FilaTitulo, String> colTitulosMayor;

}
