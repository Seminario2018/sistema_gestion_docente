package vista.controladores;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import controlador.ControlUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Roles extends ControladorVista implements Initializable {

    public static final String TITULO = "Roles";

    // Tipos respuesta:
    private static final String TIPO_ROL = "Rol";

    private ControlUsuario controlUsuario = new ControlUsuario(this);
    private IRol rolSeleccion = null;
    protected ObservableList<FilaPermiso> filasPermisos = FXCollections.observableArrayList();

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    inicializarTabla("Permisos");
	    actualizarTabla();
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
	            if (permiso.getModulo() == Modulo.ROLES) {
	                this.permiso = permiso;
	                crear |= permiso.getCrear();
	                modificar |= permiso.getModificar();
	                eliminar |= permiso.getEliminar();
	                listar |= permiso.getListar();
	            }
	        }
	    }

	    if (!crear) {
	        btnRolesNuevo.setVisible(false);
	    }

	    if (!modificar) {
	        btnRolesGuardar.setVisible(false);
	        btnRolesDescartar.setVisible(false);
	    }

	    if (!eliminar) {
	        btnRolesEliminar.setVisible(false);
	    }

	    if (!listar) {
	    }

	    modoVer();
	}

	@Override
	public void modoModificar() {
	    if (this.permiso.getModificar()) {
	        btnRolesGuardar.setVisible(true);
            btnRolesDescartar.setVisible(true);
	    }

	    if (this.permiso.getEliminar()) {
	        btnRolesEliminar.setVisible(true);
	    }

	    this.window.setTitle(TITULO + " - Modificar Rol");
	    if (this.rolSeleccion != null) {
	        this.gestorPantalla.mensajeEstado("Modificar al Rol " + this.rolSeleccion.getDescripcion());
	    }
	}

	@Override
    public void modoNuevo() {
	    if (this.permiso.getCrear()) {
	        btnRolesDescartar.setVisible(true);
	        btnRolesGuardar.setVisible(true);
	        btnRolesNuevo.setVisible(true);

	        txtRolesNombre.setEditable(true);
	        txtRolesDescripcion.setEditable(true);

	        this.window.setTitle(TITULO + "- Nuevo Rol");
	        this.gestorPantalla.mensajeEstado("Nuevo Rol");
	    }
	}

	@Override
	public void modoVer() {
	    btnRolesDescartar.setVisible(false);
	    btnRolesEliminar.setVisible(false);
	    btnRolesGuardar.setVisible(false);

        this.window.setTitle(TITULO);
        this.gestorPantalla.mensajeEstado("");
	}

	public class FilaPermiso {
	    private IPermiso permiso;
	    private CheckBox ver;
	    private CheckBox crear;
	    private CheckBox modificar;
	    private CheckBox eliminar;

	    public FilaPermiso(IPermiso permiso) {
	        this.permiso = permiso;

	        this.ver = new CheckBox();
	        this.ver.setSelected(permiso.getListar());

	        this.crear = new CheckBox();
	        this.crear.setSelected(permiso.getCrear());

	        this.modificar = new CheckBox();
            this.modificar.setSelected(permiso.getModificar());

            this.eliminar= new CheckBox();
            this.eliminar.setSelected(permiso.getEliminar());
	    }

	    public String getModulo() {
	        return this.permiso.getModulo().toString();
	    }

	    public CheckBox getVer() {
	        return this.ver;
	    }

	    public CheckBox getCrear() {
	        return this.crear;
	    }

	    public CheckBox getModificar() {
	        return this.modificar;
	    }

	    public CheckBox getEliminar() {
	        return this.eliminar;
	    }

	    public IPermiso getInstanciaPermiso() {
	        this.permiso.setCrear(this.crear.isSelected());
	        this.permiso.setEliminar(this.eliminar.isSelected());
	        this.permiso.setListar(this.ver.isSelected());
	        this.permiso.setModificar(this.modificar.isSelected());
	        return this.permiso;
	    }
	}

	private void actualizarTabla() {
	    if (rolSeleccion != null) {
	        filasPermisos.clear();
	        for (IPermiso permiso : rolSeleccion.getPermisos()) {
	            filasPermisos.add(
	                new FilaPermiso(permiso));
	        }
	    }
	}

	private void mostrarRol() {
	    if (rolSeleccion == null) {
	        vaciarControles();
	    } else {
	        txtRolesNombre.setText(rolSeleccion.getNombre());
	        txtRolesDescripcion.setText(rolSeleccion.getDescripcion());

	        actualizarTabla();
	    }
	}

	private void vaciarControles() {
	    txtRolesNombre.clear();
	    txtRolesDescripcion.clear();

	    vaciarTablas();

	    this.gestorPantalla.mensajeEstado("");
	}

	@FXML private Button btnRolesBuscar;
	@FXML private void buscarRol() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Roles.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_ROL);
        args.put(GestorPantalla.KEY_PADRE, Roles.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Roles.TITULO, args);
	}

	@FXML private Button btnRolesNuevo;
	@FXML private void nuevoRol() {
	    rolSeleccion = controlUsuario.getIRol();
	    modoNuevo();
	    vaciarControles();
	    actualizarTabla();
	}

	@FXML private Button btnRolesGuardar;
	@FXML private void guardarRol() {
	    if (rolSeleccion != null) {
	        rolSeleccion.setNombre(txtRolesNombre.getText());
	        rolSeleccion.setDescripcion(txtRolesDescripcion.getText());

	        // Actualizar permisos:
	        rolSeleccion.getPermisos().clear();
	        for (FilaPermiso fila : filasPermisos) {
	            rolSeleccion.agregarPermiso(fila.getInstanciaPermiso());
	        }

	        EstadoOperacion resultado = controlUsuario.guardarGrupo(rolSeleccion);
	        exitoGuardado(resultado, TITULO, "Guardar Cambios");

	        modoModificar();
	        mostrarRol();
	    }
	}

	@FXML private Button btnRolesDescartar;
	@FXML private void descartarRol() {
	    rolSeleccion = null;
	    vaciarControles();
	    modoVer();
	}

	@FXML private Button btnRolesEliminar;
	@FXML private void eliminarRol() {
	    if (rolSeleccion != null) {
	        EstadoOperacion resultado = controlUsuario.eliminarGrupo(rolSeleccion);
	        if (exitoEliminar(resultado, TITULO, "Eliminar Rol")) {
	            rolSeleccion = null;
	            vaciarControles();
	            modoVer();
	        }
	    }
	}

	@FXML private TextField txtRolesNombre;
	@FXML private TextField txtRolesDescripcion;

	@FXML protected TableView<FilaPermiso> tblPermisos;
	@FXML protected TableColumn<FilaPermiso, String> colPermisosModulo;
	@FXML protected TableColumn<FilaPermiso, CheckBox> colPermisosVer;
	@FXML protected TableColumn<FilaPermiso, CheckBox> colPermisosCrear;
	@FXML protected TableColumn<FilaPermiso, CheckBox> colPermisosModificar;
	@FXML protected TableColumn<FilaPermiso, CheckBox> colPermisosEliminar;

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

    public void setRoleSeleccion(Object rol, String tipo) {
        if (rol instanceof IRol) {
            rolSeleccion = (IRol) rol;
            modoModificar();
            mostrarRol();
        }
    }
}
