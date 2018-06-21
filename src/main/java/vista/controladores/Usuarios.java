package vista.controladores;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import controlador.ControlUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.persona.IPersona;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.IUsuario;
import modelo.usuario.Modulo;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Usuarios extends ControladorVista implements Initializable {

    private static final String TITULO = "Usuarios";

    // Tipos Respuesta:
    private static final String TIPO_PERSONA = "Persona";
    private static final String TIPO_USUARIO = "Usuario";

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    inicializarTabla("Disponibles");
	    tblDisponibles.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, filaSelection) -> {
            if (filaSelection != null) {
//                rolDisponibleSeleccion = filaSelection.getInstanciaRol();
                filaRolDisponibleSeleccion = filaSelection;
            }
        });

	    inicializarTabla("Usuario");
	    tblUsuario.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, filaSelection) -> {
            if (filaSelection != null) {
//                rolUsuarioSeleccion = filaSelection.getInstanciaRol();
                filaRolUsuarioSeleccion = filaSelection;
            }
        });
	}

	@Override
	public void inicializar() {
        /* Ocultar controles según roles del usuario: */
        boolean crear = false;
        boolean modificar = false;
        boolean eliminar = false;
        boolean listar = false;

        if (this.usuario == null) {
            throw new RuntimeException("Usuario es null");
        }
        for (IRol rol : this.usuario.getRoles()) {
            for (IPermiso permiso : rol.getPermisos()) {
            	if (permiso.getModulo() == Modulo.USUARIOS) {
            		this.permiso = permiso;
            		crear |= permiso.getCrear();
            		modificar |= permiso.getModificar();
            		eliminar |= permiso.getEliminar();
            		listar |= permiso.getListar();
            	}
            }
        }

        if (!crear) {
            btnNuevo.setVisible(false);
            btnPersona.setVisible(false);
        }

        if (!modificar) {
        	btnGuardar.setVisible(false);
        	btnDescartar.setVisible(false);
        	btnAgregar.setVisible(false);
        	btnQuitar.setVisible(false);
        	txtUsuario.setEditable(false);
        	txtContrasena.setEditable(false);
        	txtConfirmar.setEditable(false);
        	txtDescripcion.setEditable(false);
        }

        if (!eliminar) {
            btnEliminar.setVisible(false);
        }

        if (!listar) {
            btnBuscar.setVisible(false);
        }

        modoVer();
	}

	@SuppressWarnings("unused")
    private void setPersonaSeleccion(Object persona, String tipo) {
	    if (persona instanceof IPersona) {
	        usuarioSeleccion.setPersona((IPersona) persona);
            mostrarPersona();
        }
	}

	@SuppressWarnings("unused")
    private void setUsuarioSeleccion(Object usuario, String tipo) {
	    if (usuario instanceof IUsuario) {
            usuarioSeleccion = (IUsuario) usuario;
            modoModificar();
            mostrarUsuario();
        }
	}

	/**
     * Recibir parámetros de la pantalla de búsqueda
     */
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

    @Override
    protected void modoModificar() {
    	if (this.permiso.getModificar()) {
    		btnGuardar.setVisible(true);
    		btnDescartar.setVisible(true);
    		btnAgregar.setVisible(true);
    		btnQuitar.setVisible(true);
        	txtUsuario.setEditable(true);
        	txtContrasena.setEditable(true);
        	txtConfirmar.setEditable(true);
        	txtDescripcion.setEditable(true);
    	}
    	if (this.permiso.getEliminar()) {
    		btnEliminar.setVisible(true);
    	}

    	this.window.setTitle(TITULO + " - Modificar Usuario");
    	if (this.usuarioSeleccion != null) {
            this.gestorPantalla.mensajeEstado("Modificar al Usuario " + this.usuarioSeleccion.getUser());
        }
    }

    @Override
    protected void modoNuevo() {
        if (this.permiso.getCrear()) {
            btnAgregar.setVisible(true);
            btnDescartar.setVisible(true);
            btnGuardar.setVisible(true);
            btnNuevo.setVisible(true);
            btnPersona.setVisible(true);
            btnQuitar.setVisible(true);
            txtConfirmar.setEditable(true);
            txtContrasena.setEditable(true);
            txtDescripcion.setEditable(true);
            txtUsuario.setEditable(true);
        }

        this.window.setTitle(TITULO + " - Nuevo Usuario");
        this.gestorPantalla.mensajeEstado("Nuevo Usuario ");
    }

    @Override
    protected void modoVer() {
    	btnGuardar.setVisible(false);
        btnDescartar.setVisible(false);
        btnAgregar.setVisible(false);
        btnQuitar.setVisible(false);
        btnEliminar.setVisible(false);
        btnPersona.setVisible(false);
        txtUsuario.setEditable(false);
    	txtContrasena.setEditable(false);
    	txtConfirmar.setEditable(false);
    	txtDescripcion.setEditable(false);

    	this.window.setTitle(TITULO);
    	this.gestorPantalla.mensajeEstado("");
    }

// -------------------------------- General --------------------------------- //

	private IUsuario usuarioSeleccion = null;
    private ControlUsuario controlUsuario = new ControlUsuario(this);

    private void mostrarPersona() {
        if (usuarioSeleccion != null &&
            usuarioSeleccion.getPersona() != null) {

            txtDocumento.setText(String.valueOf(usuarioSeleccion.getPersona().getNroDocumento()));
            txtNombre.setText(usuarioSeleccion.getPersona().getNombreCompleto());
        }
    }

    private void mostrarUsuario() {
        if (usuarioSeleccion == null) {
            vaciarControles();
        } else {
            mostrarPersona();
            txtUsuario.setText(usuarioSeleccion.getUser());
            txtDescripcion.setText(usuarioSeleccion.getDescripcion());

            actualizarTablaDisponibles();
            actualizarTablaUsuario();
        }
    }

    private void vaciarControles() {
        txtDocumento.clear();
        txtNombre.clear();
        txtUsuario.clear();
        txtContrasena.clear();
        txtConfirmar.clear();
        txtDescripcion.clear();

        vaciarTablas();

        this.gestorPantalla.mensajeEstado("");
    }

	@FXML private TextField txtDocumento;
	@FXML private TextField txtNombre;

	@FXML private Button btnPersona;
	@FXML public void seleccionarPersona(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Personas.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PERSONA);
        args.put(GestorPantalla.KEY_PADRE, Usuarios.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Personas.TITULO, args);
	}

	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtContrasena;
	@FXML private PasswordField txtConfirmar;
	@FXML private TextField txtDescripcion;

	@FXML private Button btnBuscar;
	@FXML public void buscarUsuario(ActionEvent event) {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Usuarios.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_USUARIO);
        args.put(GestorPantalla.KEY_PADRE, Usuarios.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Usuarios.TITULO, args);
	}

	@FXML private Button btnNuevo;
	@FXML public void nuevoUsuario(ActionEvent event) {
	    usuarioSeleccion = controlUsuario.getIUsuario();
        vaciarControles();

        actualizarTablaDisponibles();
        actualizarTablaUsuario();

        modoNuevo();
	}

	@FXML private Button btnGuardar;
	@FXML public void guardarUsuario(ActionEvent event) {
	    if (usuarioSeleccion != null) {
	        if (usuarioSeleccion.getPersona() != null) {
	            String contrasena = txtContrasena.getText();
	            String confirmar = txtConfirmar.getText();
	            if (contrasena.equals(confirmar)) {
	                usuarioSeleccion.setUser(txtUsuario.getText());
	                usuarioSeleccion.setPass(txtContrasena.getText());
	                usuarioSeleccion.setDescripcion(txtDescripcion.getText());

	                List<IRol> rolesNuevos = new ArrayList<IRol>();
	                for (FilaRol fila : filasUsuario) {
	                    rolesNuevos.add(fila.getInstanciaRol());
	                }
	                usuarioSeleccion.setGrupos(rolesNuevos);

	                EstadoOperacion resultado = controlUsuario.guardarUsuario(usuarioSeleccion);
	                exitoGuardado(resultado, TITULO, "Guardar Usuario");

	                mostrarUsuario();
	                modoModificar();

	            } else { alertaError(TITULO, "Guardar Usuario", "Las contraseñas no coinciden"); }
	        } else { alertaError(TITULO, "Guardar Usuario", "Tiene que haber una persona seleccionada"); }
	    }
	}

	@FXML private Button btnDescartar;
	@FXML public void descartarUsuario(ActionEvent event) {
	    usuarioSeleccion = null;
        vaciarControles();
        modoVer();
	}

	@FXML private Button btnEliminar;
	@FXML public void eliminarUsuario(ActionEvent event) {
	    if (usuarioSeleccion != null) {
	        EstadoOperacion resultado = controlUsuario.eliminarUsuario(usuarioSeleccion);
	        if (exitoEliminar(resultado, TITULO, "Eliminar Usuario")) {
	            usuarioSeleccion = null;
	            vaciarControles();
	            modoVer();
	        }
	    }
	}

// --------------------------------- Roles ---------------------------------- //

//	private IRol rolDisponibleSeleccion = null;
//	private IRol rolUsuarioSeleccion = null;

	private FilaRol filaRolDisponibleSeleccion = null;
	private FilaRol filaRolUsuarioSeleccion = null;

	protected ObservableList<FilaRol> filasDisponibles = FXCollections.observableArrayList();
	protected ObservableList<FilaRol> filasUsuario = FXCollections.observableArrayList();

	private void actualizarTablaDisponibles() {
	    if (usuarioSeleccion != null) {
            filasDisponibles.clear();

            List<IRol> rolesDisponibles = controlUsuario.listarGrupo(null);
            List<IRol> rolesUsuario = usuarioSeleccion.getRoles();
            rolesDisponibles.removeAll(rolesUsuario);

            for (IRol rol : rolesDisponibles) {
                filasDisponibles.add(
                    new FilaRol(rol));
            }
        }
	}

	private void actualizarTablaUsuario() {
	    if (usuarioSeleccion != null) {
	        filasUsuario.clear();
	        for (IRol rol : usuarioSeleccion.getRoles()) {
	            filasUsuario.add(
	                new FilaRol(rol));
	        }
	    }
	}

	public class FilaRol {
	    private IRol rol;

	    public FilaRol(IRol rol) {
	        this.rol = rol;
	    }

	    public String getRol() {
	        return this.rol.getNombre();
	    }

	    public IRol getInstanciaRol() {
	        return this.rol;
	    }
	}

	@FXML private Button btnAgregar;
	@FXML public void agregarRol(ActionEvent event) {
	    if (usuarioSeleccion != null) {
//	        if (rolDisponibleSeleccion != null) {
//	            EstadoOperacion resultado = controlUsuario.agregarRol(usuarioSeleccion, rolDisponibleSeleccion);
//	            if (exitoGuardado(resultado, TITULO, "Agregar Rol")) {
//	                rolDisponibleSeleccion = null;
//	            }
//                mostrarUsuario();
            if (filaRolDisponibleSeleccion != null) {
            	filasUsuario.add(filaRolDisponibleSeleccion);
                filasDisponibles.remove(filaRolDisponibleSeleccion);

	        } else { alertaError(TITULO, "Agregar Rol", "No hay un rol seleccionado"); }
	    }
	}

	@FXML private Button btnQuitar;
	@FXML public void quitarRol(ActionEvent event) {
	    if (usuarioSeleccion != null) {
//	        if (rolUsuarioSeleccion != null) {
//	            EstadoOperacion resultado = controlUsuario.quitarRol(usuarioSeleccion, rolUsuarioSeleccion);
//	            if (exitoEliminar(resultado, TITULO, "Quitar Rol")) {
//	                rolUsuarioSeleccion = null;
//	            }
//	            mostrarUsuario();
	        if (filaRolUsuarioSeleccion != null) {
	            filasDisponibles.add(filaRolUsuarioSeleccion);
	            filasUsuario.remove(filaRolUsuarioSeleccion);

	        } else { alertaError(TITULO, "Quitar Rol", "No hay un rol seleccionado"); }
	    }
	}

	@FXML protected TableView<FilaRol> tblDisponibles;
	@FXML protected TableColumn<FilaRol, String> colDisponiblesRol; // Descripción del rol

	@FXML protected TableView<FilaRol> tblUsuario;
	@FXML protected TableColumn<FilaRol, String> colUsuarioRol; // Descripción del rol

}


