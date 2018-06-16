package vista.controladores;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import controlador.ControlAuxiliar;
import controlador.ControlInvestigacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoPrograma;
import modelo.docente.IDocente;
import modelo.investigacion.IPrograma;
import modelo.investigacion.IProyecto;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Programas extends ControladorVista implements Initializable {

    public static final String TITULO = "Programas";

    // Tipos respuesta:
    private static final String TIPO_DIRECTOR = "Director";
    private static final String TIPO_CODIRECTOR = "Codirector";
    private static final String TIPO_PROGRAMA = "Programa";
    private static final String TIPO_PROYECTO = "Proyecto";

    private ControlInvestigacion controlInvestigacion = new ControlInvestigacion();
    private IPrograma programaSeleccion = null;
    private IDocente directorSeleccion = null;
    private IDocente codirectorSeleccion = null;
    private IProyecto proyectoSeleccion = null;
    protected ObservableList<FilaProyecto> filasProyectos = FXCollections.observableArrayList();

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    inicializarTabla("Proyectos");
	    tblProyectos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, filaSelection) -> {
            if (filaSelection != null) {
                proyectoSeleccion = filaSelection.getInstanciaProyecto();
            }
        });

	    actualizarTabla();

	    // Popular estados de programa:
	    cmbProgramasEstado.setItems(
	        FXCollections.observableArrayList(
	            ControlAuxiliar.listarEstadosPrograma()));
	}

	protected class FilaProyecto {
	    private IProyecto proyecto;

	    public FilaProyecto(IProyecto proyecto) {
            this.proyecto = proyecto;
        }

	    public String getNombre() {
	        return this.proyecto.getNombre();
	    }

	    public IProyecto getInstanciaProyecto() {
	        return this.proyecto;
	    }
	}

	@FXML private Button btnProgramasBuscar;
	@FXML private void buscarPrograma() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Programas.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PROGRAMA);
        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Programas.TITULO, args);
	}

	@FXML private Button btnProgramasNuevo;
	@FXML private void nuevoPrograma() {
	    programaSeleccion = controlInvestigacion.getIPrograma();
	    vaciarControles();
	    modoModificar();
	    this.window.setTitle(TITULO + " - Nuevo Programa");
	    this.gestorPantalla.mensajeEstado("Nuevo Programa");
	}

	@FXML private Button btnProgramasEliminar;
	@FXML private void eliminarPrograma() {
	    if (programaSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.eliminarPrograma(programaSeleccion), TITULO, "Eliminar Programa")) {
                programaSeleccion = null;
                vaciarControles();
                modoVer();
            }
        }
	}

	@FXML private Button btnProgramasGuardar;
	@FXML private void guardarCambios() {
	    if (programaSeleccion != null) {
	        programaSeleccion.setNombre(txtProgramasNombre.getText());
	        programaSeleccion.setDirector(directorSeleccion);
	        programaSeleccion.setCodirector(codirectorSeleccion);
	        programaSeleccion.setEstado(cmbProgramasEstado.getValue());
	        programaSeleccion.setDisposicion(txtProgramasDisp.getText());
	        programaSeleccion.setFechaInicio(dtpProgramasInicio.getValue());
	        programaSeleccion.setFechaFin(dtpProgramasFinalizacion.getValue());

	        exitoGuardado(controlInvestigacion.guardarPrograma(programaSeleccion), TITULO, "Guardar Programa");
	    }
	}

	@FXML private Button btnProgramasDescartar;
	@FXML private void descartarCambios() {
	    programaSeleccion = null;
	    vaciarControles();
	    modoVer();
	}

	@FXML private Button btnProgramasDirector;
	@FXML private void seleccionarDirector() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_DIRECTOR);
        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + "Director", args);
	}

	@FXML private Button btnProgramasCodirector;
	@FXML private void seleccionarCodirector() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CODIRECTOR);
        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + "Codirector", args);
    }

	@FXML private Button btnProyectosAgregar;
	@FXML private void agregarProyecto() {
	    if (programaSeleccion != null) {
	        Map<String, Object> args = new HashMap<String, Object>();
	        args.put(Busqueda.KEY_NUEVO, true);
	        args.put(Busqueda.KEY_TIPO, Proyectos.TITULO);
	        args.put(Busqueda.KEY_CONTROLADOR, this);
	        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PROYECTO);
	        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
	        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Proyectos.TITULO, args);
	    } else {
	        System.out.println("agregarProyecto(): proyecto es null");
	    }
	}

	@FXML private Button btnProyectosQuitar;
	@FXML private void quitarProyecto() {
	    if (programaSeleccion != null && proyectoSeleccion != null) {
	        exitoEliminar(controlInvestigacion.eliminarProyecto(proyectoSeleccion), TITULO, "Quitar Proyecto");
	    }
	}

	@FXML private TextField txtProgramas;
	@FXML private TextField txtProgramasNombre;
	@FXML private TextField txtProgramasDirector;
	@FXML private TextField txtProgramasCodirector;
	@FXML private ComboBox<EstadoPrograma> cmbProgramasEstado;
	@FXML private TextField txtProgramasDisp;
	@FXML private DatePicker dtpProgramasInicio;
	@FXML private DatePicker dtpProgramasFinalizacion;

	@FXML protected TableView<FilaProyecto> tblProyectos;
	@FXML protected TableColumn<FilaProyecto, String> colProyectosNombre;

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

    public void setDocenteSeleccion(Object docente, String tipo) {
        if (docente instanceof IDocente) {
            switch (tipo) {
                case TIPO_DIRECTOR:
                    setDirectorSeleccion((IDocente) docente);
                    break;
                case TIPO_CODIRECTOR:
                    setCodirectorSeleccion((IDocente) docente);
                    break;
            }
        }
    }

    private void setCodirectorSeleccion(IDocente codirector) {
        codirectorSeleccion = codirector;
        txtProgramasCodirector.setText(codirectorSeleccion.getPersona().getNombreCompleto());
    }

    private void setDirectorSeleccion(IDocente director) {
        directorSeleccion = director;
        txtProgramasDirector.setText(directorSeleccion.getPersona().getNombreCompleto());
    }

    public void setProgramaSeleccion(Object programa, String tipo) {
        if (programa instanceof IPrograma) {
            programaSeleccion = (IPrograma) programa;
            modoModificar();
            mostrarPrograma();
        }
    }

    public void setProyectoSeleccion(Object proyecto, String tipo) {
        if (proyecto instanceof IProyecto) {
            // Recibo un objeto proyecto para agregarlo al programa
            IProyecto proyectoAgregar = (IProyecto) proyecto;
            EstadoOperacion resultado = controlInvestigacion.guardarProyecto(proyectoAgregar, programaSeleccion);
            exitoGuardado(resultado, TITULO, "Agregar Proyecto");
            actualizarTabla();
        }
    }

    private void actualizarTabla() {
        if (programaSeleccion != null) {
            filasProyectos.clear();
            for (IProyecto proyecto : programaSeleccion.getProyectos()) {
                filasProyectos.add(new FilaProyecto(proyecto));
            }
        }
    }

    private void mostrarPrograma() {
        if (programaSeleccion == null) {
            vaciarControles();
        } else {
            txtProgramas.setText(programaSeleccion.getNombre());
            txtProgramasNombre.setText(programaSeleccion.getNombre());
            txtProgramasDirector.setText(programaSeleccion.getDirector().getPersona().getNombreCompleto());
            txtProgramasCodirector.setText(programaSeleccion.getCodirector().getPersona().getNombreCompleto());
            cmbProgramasEstado.getSelectionModel().select(programaSeleccion.getEstado());
            txtProgramasDisp.setText(programaSeleccion.getDisposicion());
            dtpProgramasInicio.setValue(programaSeleccion.getFechaInicio());
            dtpProgramasFinalizacion.setValue(programaSeleccion.getFechaFin());

            actualizarTabla();
        }
    }

    private void vaciarControles() {
        txtProgramasNombre.clear();
        txtProgramasDirector.clear();
        txtProgramasCodirector.clear();
        cmbProgramasEstado.getSelectionModel().clearSelection();
        txtProgramasDisp.clear();
        dtpProgramasInicio.setValue(null);
        dtpProgramasFinalizacion.setValue(null);

        vaciarTablas();

        this.gestorPantalla.mensajeEstado("");
    }

    @Override
    public void inicializar() {
        /* Ocultar controles según roles del usuario: */
        boolean crear = false;
        boolean modificar = false;
        boolean eliminar = false;
        boolean listar = false;

        for (IRol rol : this.usuario.getGrupos()) {
            for (IPermiso permiso : rol.getPermisos()) {
                if (permiso.getModulo() == Modulo.DOCENTES) {
                    this.permiso = permiso;
                    crear |= permiso.getCrear();
                    modificar |= permiso.getModificar();
                    eliminar |= permiso.getEliminar();
                    listar |= permiso.getListar();
                }
            }
        }

        if (!crear) {
            btnProgramasNuevo.setVisible(false);
        }

        if (!modificar) {
            // Información del programa:
            btnProgramasGuardar.setVisible(false);
            btnProgramasDescartar.setVisible(false);

            txtProgramasNombre.setEditable(false);
            cmbProgramasEstado.setDisable(true);
            txtProgramasDisp.setEditable(false);
            dtpProgramasInicio.setEditable(false);
            dtpProgramasFinalizacion.setEditable(false);

            // Proyectos:
            btnProyectosAgregar.setVisible(false);
            btnProyectosQuitar.setVisible(false);
        }

        if (!eliminar) {
            btnProgramasEliminar.setVisible(false);
        }

        if (!listar) {
        }

        modoVer();
    }

    @Override
    public void modoModificar() {
        if (this.permiso.getModificar() || this.permiso.getCrear()) {
            // Información del programa:
            btnProgramasGuardar.setVisible(true);
            btnProgramasDescartar.setVisible(true);

            txtProgramasNombre.setEditable(true);
            cmbProgramasEstado.setDisable(false);
            txtProgramasDisp.setEditable(true);
            dtpProgramasInicio.setEditable(true);
            dtpProgramasFinalizacion.setEditable(true);

            // Proyectos:
            btnProyectosAgregar.setVisible(true);
            btnProyectosQuitar.setVisible(true);
        }
        if (this.permiso.getEliminar()) {
            btnProgramasEliminar.setVisible(true);
        }

        this.window.setTitle(TITULO + " - Modificar Programa");
        if (this.programaSeleccion != null) {
            this.gestorPantalla.mensajeEstado("Modificar al Programa" + this.programaSeleccion.getNombre());
        }
    }

    @Override
    public void modoVer() {
        // General:
        btnProgramasEliminar.setVisible(false);

        // Información del programa:
        btnProgramasGuardar.setVisible(false);
        btnProgramasDescartar.setVisible(false);

        txtProgramasNombre.setEditable(false);
        cmbProgramasEstado.setDisable(true);
        txtProgramasDisp.setEditable(false);
        dtpProgramasInicio.setEditable(false);
        dtpProgramasFinalizacion.setEditable(false);

        // Proyectos:
        btnProyectosAgregar.setVisible(false);
        btnProyectosQuitar.setVisible(false);

        this.window.setTitle(TITULO);
        this.gestorPantalla.mensajeEstado("");
    }
}
