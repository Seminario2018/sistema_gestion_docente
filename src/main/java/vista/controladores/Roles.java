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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
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

	public class FilaPermiso {
	    private IPermiso permiso;

	    public FilaPermiso(IPermiso permiso) {
	        this.permiso = permiso;
	    }

	    public String getModulo() {
	        return this.permiso.getModulo().toString();
	    }

	    public String getVer() {
	        return this.permiso.getListar() ? "Si" : "";
	    }

	    public String getCrear() {
            return this.permiso.getCrear() ? "Si" : "";
        }

	    public String getModificar() {
            return this.permiso.getModificar() ? "Si" : "";
        }

	    public String getEliminar() {
            return this.permiso.getEliminar() ? "Si" : "";
        }

	    public IPermiso getInstanciaPermiso() {
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
	        txtRolesId.setText(String.valueOf(rolSeleccion.getId()));
	        txtRolesNombre.setText(rolSeleccion.getNombre());
	        txtRolesDescripcion.setText(rolSeleccion.getDescripcion());

	        actualizarTabla();
	    }
	}

	private void vaciarControles() {
	    txtRolesId.clear();
	    txtRolesNombre.clear();
	    txtRolesDescripcion.clear();

	    vaciarTablas();
	}

	@FXML private void buscarRol() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Roles.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_ROL);
        args.put(GestorPantalla.KEY_PADRE, Roles.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Roles.TITULO, args);
	}

	@FXML private void nuevoRol() {
	    rolSeleccion = controlUsuario.getIRol();
	    vaciarControles();
	}

	@FXML private void guardarRol() {
	    if (rolSeleccion != null) {
	        rolSeleccion.setNombre(txtRolesNombre.getText());
	        rolSeleccion.setDescripcion(txtRolesDescripcion.getText());

	        EstadoOperacion resultado = controlUsuario.guardarGrupo(rolSeleccion);
	        exitoGuardado(resultado, TITULO, "Guardar Cambios");
	    }
	}

	@FXML private void descartarRol() {
	    mostrarRol();
	}

	@FXML private void eliminarRol() {
	    if (rolSeleccion != null) {
	        EstadoOperacion resultado = controlUsuario.eliminarGrupo(rolSeleccion);
	        if (exitoEliminar(resultado, TITULO, "Eliminar Rol")) {
	            rolSeleccion = null;
	            vaciarControles();
	        }
	    }
	}

	@FXML private TextField txtRolesId;
	@FXML private TextField txtRolesNombre;
	@FXML private TextField txtRolesDescripcion;

	@FXML protected TableView<FilaPermiso> tblPermisos;
	@FXML protected TableColumn<FilaPermiso, String> colPermisosModulo;
	@FXML protected TableColumn<FilaPermiso, String> colPermisosVer;
	@FXML protected TableColumn<FilaPermiso, String> colPermisosCrear;
	@FXML protected TableColumn<FilaPermiso, String> colPermisosModificar;
	@FXML protected TableColumn<FilaPermiso, String> colPermisosEliminar;

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
            mostrarRol();
        }
    }
}
