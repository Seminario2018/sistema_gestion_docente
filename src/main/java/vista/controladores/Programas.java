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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.auxiliares.EstadoPrograma;
import modelo.docente.IDocente;
import modelo.investigacion.IPrograma;
import modelo.investigacion.IProyecto;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 4 de may. de 2018
 */
public class Programas extends ControladorVista implements Initializable {

    public static final String TITULO = "Programas";
    private static final String TIPO_DIRECTOR = "Director";
    private static final String TIPO_CODIRECTOR = "Codirector";
    private static final String TIPO_PROGRAMA = "Programa";

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

	@FXML private void buscarPrograma() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Programas.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PROGRAMA);
        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Programas.TITULO, args);
	}

	@FXML private void nuevoPrograma() {
	    programaSeleccion = controlInvestigacion.getIPrograma();
	    vaciarControles();
	}

	@FXML private void eliminarPrograma() {
	    if (programaSeleccion != null) {
            if (exitoEliminar(controlInvestigacion.eliminarPrograma(programaSeleccion), TITULO, "Eliminar Programa")) {
                programaSeleccion = null;
                vaciarControles();
            }
        }
	}

	@FXML private void guardarCambios() {
	    if (programaSeleccion != null) {
	        programaSeleccion.setNombre(txtProgramasNombre.getText());
	        programaSeleccion.setDirector(directorSeleccion);
	        programaSeleccion.setCodirector(codirectorSeleccion);
	        programaSeleccion.setEstado(cmbProgramasEstado.getValue());
	        programaSeleccion.setDisposicion(txtProgramasDisp.getText());

	        exitoGuardado(controlInvestigacion.guardarPrograma(programaSeleccion), TITULO, "Guardar Programa");
	    }
	}

	@FXML private void descartarCambios() {
	    mostrarPrograma();
	}

	@FXML private void seleccionarDirector() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_DIRECTOR);
        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + "Director", args);
	}

	@FXML private void seleccionarCodirector() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, true);
        args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CODIRECTOR);
        args.put(GestorPantalla.KEY_PADRE, Programas.TITULO);
        this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + "Codirector", args);
    }

	@FXML private void agregarProyecto() {
	    if (proyectoSeleccion != null) {
	        exitoGuardado(controlInvestigacion.guardarProyecto(proyectoSeleccion, programaSeleccion), TITULO, "Agregar Proyecto");
	    }
	}

	@FXML private void quitarProyecto() {
	    if (proyectoSeleccion != null) {
	        exitoEliminar(controlInvestigacion.eliminarProyecto(proyectoSeleccion), TITULO, "Quitar Proyecto");
	    }
	}

	@FXML private TextField txtProgramas;
	@FXML private TextField txtProgramasNombre;
	@FXML private TextField txtProgramasDirector;
	@FXML private TextField txtProgramasCodirector;
	@FXML private ComboBox<EstadoPrograma> cmbProgramasEstado;
	@FXML private TextField txtProgramasDisp;
	@FXML private DatePicker dtpProgramasPresentacion;
	@FXML private DatePicker dtpProgramasAprobacion;
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
                System.out.println("Método: \"" + metodo + "\"");
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
            mostrarPrograma();
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
        }
    }

    private void vaciarControles() {
        txtProgramasNombre.clear();
        txtProgramasDirector.clear();
        txtProgramasCodirector.clear();
        cmbProgramasEstado.getSelectionModel().clearSelection();
        txtProgramasDisp.clear();

        vaciarTablas();
    }
}
