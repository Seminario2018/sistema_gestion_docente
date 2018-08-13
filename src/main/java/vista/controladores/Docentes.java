package vista.controladores;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.DateTimeException;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import controlador.ControlAuxiliar;
import controlador.ControlDocente;
import controlador.ControlInvestigacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.docente.IIncentivo;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProyecto;
import modelo.persona.IPersona;
import modelo.usuario.IPermiso;
import modelo.usuario.IRol;
import modelo.usuario.Modulo;
import utilidades.Utilidades;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de may. de 2018
 */
public class Docentes extends ControladorVista implements Initializable {

	public void setDocenteSeleccion(Object docenteSeleccion, String tipo) {
		if (docenteSeleccion instanceof IDocente) {
			this.docenteSeleccion = (IDocente) docenteSeleccion;
			modoModificar();
	        generalMostrarDocente();
		}
	}

	public void setPersonaSeleccion(Object personaSeleccion, String tipo) {
		if (personaSeleccion instanceof IPersona) {
		    IPersona persona = (IPersona) personaSeleccion;
			this.docenteSeleccion.setPersona(persona);
			txtDatosDocumento.setText(String.valueOf(persona.getNroDocumento()));
            txtDatosNombre.setText(persona.getNombreCompleto());
		}
	}

	public void setAreaSeleccion(Object areaSeleccion, String tipo) {
		if (areaSeleccion instanceof IArea) {
			this.cargoDocenteSeleccion.setArea((IArea) areaSeleccion);
			this.txtCargosArea.setText(((IArea) areaSeleccion).getDescripcion());
			this.btnCargosCargo.requestFocus();
		}
	}

	public void setCargoSeleccion(Object cargoSeleccion, String tipo) {
		if (cargoSeleccion instanceof ICargo) {
			this.cargoDocenteSeleccion.setCargo((ICargo) cargoSeleccion);
			this.txtCargosCargo.setText(((ICargo) cargoSeleccion).getDescripcion());
			this.cmbCargosEstado.requestFocus();
		}
	}

	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {

	}

	private ControlDocente controlDocente = new ControlDocente(this);
	private ControlInvestigacion controlInvestigacion = new ControlInvestigacion(this);
	public IDocente docenteSeleccion;

	public static final String TITULO = "Docentes";

	// Tipos respuesta:
	private static final String TIPO_AREA = "area";
	private static final String TIPO_CARGO = "cargo";
	private static final String TIPO_DOCENTE = "docente";
	private static final String TIPO_PERSONA = "persona";

	public static final String KEY_TAB = "pestaña";
	public static final int TAB_DATOS = 0;
	public static final int TAB_CARGOS = 1;
	public static final int TAB_INVESTIGACION = 2;
	public static final int TAB_INCENTIVOS = 3;
	public static final int TAB_OBSERVACIONES = 4;

	@Override
    public void inicializar() {
        /* Ocultar controles según roles del usuario: */
        boolean crear = false;
        boolean modificar = false;
        boolean eliminar = false;
        boolean listar = false;

        for (IRol rol : this.usuario.getRoles()) {
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
            btnDocentesNuevo.setVisible(false);
            btnDatosPersona.setVisible(false);
        }

        if (!modificar) {
            // General:
            btnDocentesCosto.setVisible(false);
            btnDocentesDescartar.setVisible(false);
            btnDocentesGuardar.setVisible(false);

            // Pestaña Datos:
//            btnDatosGuardar.setVisible(false);
//            btnDatosDescartar.setVisible(false);

            txtDatosLegajo.setEditable(false);
            cmbDatosEstado.setDisable(true);
            cmbDatosCategoria.setDisable(true);

            // Pestaña Cargos:
            btnCargosNuevo.setVisible(false);
            btnCargosGuardar.setVisible(false);
            btnCargosDescartar.setVisible(false);
            btnCargosEliminar.setVisible(false);

            btnCargosArea.setVisible(false);
            btnCargosCargo.setVisible(false);
            cmbCargosEstado.setDisable(true);
            cmbCargosTipo.setDisable(true);
            txtCargosDisp.setEditable(false);
            dtpCargosDispDesde.setEditable(false);
            dtpCargosDispHasta.setEditable(false);
            txtCargosRes.setEditable(false);
            dtpCargosResDesde.setEditable(false);
            dtpCargosResHasta.setEditable(false);

            // Pestaña Incentivos:
            btnIncentivosNuevo.setVisible(false);
            btnIncentivosGuardar.setVisible(false);
            btnIncentivosDescartar.setVisible(false);
            txtIncentivosAnio.setEditable(false);

            // Pestaña Observaciones:
//            btnObservacionesGuardar.setVisible(false);
//            btnObservacionesDescartar.setVisible(false);
            txtaObservaciones.setEditable(false);
        }

        if (!eliminar) {
            btnDocentesEliminar.setVisible(false);
            btnCargosEliminar.setVisible(false);
        }

        if (!listar) {
        }

        modoVer();
    }

	@Override
    public void modoModificar() {
	    if (this.permiso.getModificar()) {
	        // General:
	        btnDocentesCosto.setVisible(true);
	        btnDocentesDescartar.setVisible(true);
	        btnDocentesGuardar.setVisible(true);

	        // Pestaña Datos:
//	        btnDatosGuardar.setVisible(true);
//	        btnDatosDescartar.setVisible(true);
	        txtDatosLegajo.setEditable(true);
	        cmbDatosEstado.setDisable(false);
	        cmbDatosCategoria.setDisable(false);

	        // Pestaña Cargos:
	        cargosModoVer();
	        btnCargosNuevo.setVisible(true);

	        // Pestaña Incentivos:
	        incentivosModoVer();
	        btnIncentivosNuevo.setVisible(true);

	        // Pestaña Observaciones:
//	        btnObservacionesGuardar.setVisible(true);
//	        btnObservacionesDescartar.setVisible(true);
	        txtaObservaciones.setEditable(true);
	    }

	    if (this.permiso.getEliminar()) {
	        btnDocentesEliminar.setVisible(true);
	    }

	    this.window.setTitle(TITULO + " - Modificar Docente");
	    if (this.docenteSeleccion != null) {
	        this.gestorPantalla.mensajeEstado("Modificar al Docente " + this.docenteSeleccion.getLegajo());
	    }
	}

	@Override
    public void modoNuevo() {
	    if (this.permiso.getCrear()) {
	        // General:
	        btnDocentesDescartar.setVisible(true);
	        btnDocentesGuardar.setVisible(true);
	        btnDocentesNuevo.setVisible(true);

	        // Pestaña Datos:
//	        btnDatosDescartar.setVisible(true);
//	        btnDatosGuardar.setVisible(true);
	        btnDatosPersona.setVisible(true);
	        txtDatosLegajo.setEditable(true);
	        cmbDatosEstado.setDisable(false);
	        cmbDatosCategoria.setDisable(false);

	        this.window.setTitle(TITULO + " - Nuevo Docente");
	        this.gestorPantalla.mensajeEstado("Nuevo Docente ");
        }
	}

	@Override
	public void modoVer() {
	    // General:
	    btnDocentesDescartar.setVisible(false);
	    btnDocentesEliminar.setVisible(false);
        btnDocentesGuardar.setVisible(false);
	    btnDocentesCosto.setVisible(true);

	    // Pestaña Datos:
//	    btnDatosGuardar.setVisible(false);
//	    btnDatosDescartar.setVisible(false);
	    btnDatosPersona.setVisible(false);
	    txtDatosLegajo.setEditable(false);
	    cmbDatosEstado.setDisable(true);
	    cmbDatosCategoria.setDisable(true);

	    // Pestaña Cargos:
	    cargosModoVer();
	    btnCargosNuevo.setVisible(false);

	    // Pestaña Incentivos:
	    incentivosModoVer();
	    btnIncentivosNuevo.setVisible(false);

	    // Pestaña Observaciones:
//	    btnObservacionesGuardar.setVisible(false);
//	    btnObservacionesDescartar.setVisible(false);
	    txtaObservaciones.setEditable(false);

	    this.window.setTitle(TITULO);
	    this.gestorPantalla.mensajeEstado("");
	}

// -------------------------------- General --------------------------------- //
	@FXML public TextField txtDocentesLegajo;
	@FXML public TextField txtDocentesNombre;

	private void generalMostrarDocente() {
        generalVaciarControles();
        if (docenteSeleccion != null) {
            txtDocentesLegajo.setText(String.valueOf(docenteSeleccion.getLegajo()));
            txtDocentesNombre.setText(docenteSeleccion.getPersona().getNombreCompleto());

            datosMostrarDocente();
            cargosActualizarTabla();
            investigacionActualizarTabla();
            incentivosActualizarTabla();
            observacionesMostrarObservaciones();
        }
    }

	/** Vacía los controles generales y los de todas las pestañas */
    private void generalVaciarControles() {
        this.txtDocentesLegajo.clear();
        this.txtDocentesNombre.clear();

        // Vaciar controles de las pestañas:
        vaciarTablas();
        datosVaciarControles();
        cargosVaciarControles();
        observacionesVaciarControles();

        this.gestorPantalla.mensajeEstado("");
    }

    @FXML Button btnDocentesBuscar;
	@FXML private void buscarDocente() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, false);
		args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
		args.put(Busqueda.KEY_CONTROLADOR, this);
		args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_DOCENTE);
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Docentes.TITULO, args);
	}

	@FXML Button btnDocentesNuevo;
	@FXML private void nuevoDocente() {
		docenteSeleccion = controlDocente.getIDocente();
		generalVaciarControles();
//		modoModificar();
		modoNuevo();
//		this.window.setTitle(TITULO + " - Nuevo Docente");
//		this.gestorPantalla.mensajeEstado("Nuevo Docente");
    }

	@FXML Button btnDocentesEliminar;
	@FXML private void eliminarDocente() {
	    if (docenteSeleccion != null) {
	        if (exitoEliminar(controlDocente.eliminarDocente(docenteSeleccion), TITULO, "Eliminar Docente")) {
	            docenteSeleccion = null;
	            modoVer();
	            generalVaciarControles();
	        }
	    }
    }

	@FXML Button btnDocentesCosto;
	@FXML private void importarUltimoCosto() {
		this.gestorPantalla.lanzarPantalla(ImportarCosto.TITULO, null);
    }

	@FXML Button btnDocentesGuardar;
	@FXML private void guardarDocente() {
	    if (docenteSeleccion != null) {
            try {
                int legajo = Integer.parseInt(txtDatosLegajo.getText());

                // Pestaña "Datos"
                docenteSeleccion.setLegajo(legajo);
                docenteSeleccion.setEstado(cmbDatosEstado.getSelectionModel().getSelectedItem());
                docenteSeleccion.setCategoriaInvestigacion(cmbDatosCategoria.getSelectionModel().getSelectedItem());

                // Pestaña "Observaciones"
                docenteSeleccion.setObservaciones(txtaObservaciones.getText());

                exitoGuardado(controlDocente.guardarDocente(docenteSeleccion), TITULO, "Guardar Docente");
                generalMostrarDocente();

                modoModificar();

            } catch (NumberFormatException e) {
                alertaError(TITULO, "Guardar Docente", "El legajo tiene que ser numérico");
            }
        }
	}

	@FXML Button btnDocentesDescartar;
	@FXML private void descartarDocente() {
	    generalMostrarDocente();
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
		Object pestana = args.get(KEY_TAB);
		if (pestana != null) {
		    tabpDocentes.getSelectionModel().select((Integer) pestana);
		}
	}

// ----------------------------- Pestaña Datos ------------------------------ //
	@FXML public TextField txtDatosDocumento;
	@FXML public TextField txtDatosNombre;
	@FXML public TextField txtDatosLegajo;
	@FXML public ComboBox<EstadoDocente> cmbDatosEstado;
	@FXML public ComboBox<CategoriaInvestigacion> cmbDatosCategoria;

	/** Muestra los datos de la persona seleccionada: */
	private void datosMostrarDocente() {
	    if (docenteSeleccion != null) {
            IPersona persona = docenteSeleccion.getPersona();
            txtDatosDocumento.setText(String.valueOf(persona.getNroDocumento()));
            txtDatosNombre.setText(persona.getApellido() + " " + persona.getNombre());
            txtDatosLegajo.setText(String.valueOf(docenteSeleccion.getLegajo()));
            cmbDatosEstado.getSelectionModel().select(docenteSeleccion.getEstado());
            cmbDatosCategoria.getSelectionModel().select(docenteSeleccion.getCategoriaInvestigacion());
        }
	}

	/** Vacía los controles de datos */
	private void datosVaciarControles() {
        txtDatosDocumento.clear();
        txtDatosNombre.clear();
        txtDatosLegajo.clear();
        cmbDatosEstado.getSelectionModel().clearSelection();
        cmbDatosCategoria.getSelectionModel().clearSelection();
    }

	@FXML private void inicializarDatos() {
	    this.cmbDatosEstado.setItems(
            FXCollections.observableArrayList(
                ControlAuxiliar.listarEstadosDocente()));

	    this.cmbDatosCategoria.setItems(
	        FXCollections.observableArrayList(
                ControlAuxiliar.listarCategoriasInvestigacion()));
	}

	@FXML private Button btnDatosDatos;
	@FXML private void mostrarDatos() {
	    Map<String, Object> args = new HashMap<String, Object>();
        args.put(Busqueda.KEY_NUEVO, false);
        args.put(Busqueda.KEY_TIPO, Personas.TITULO);
        args.put(Busqueda.KEY_CONTROLADOR, this);
        args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
        args.put(Busqueda.KEY_VALOR, docenteSeleccion.getPersona());
        args.put(Busqueda.KEY_SELECCION, "Persona");
        this.gestorPantalla.lanzarPantalla(Personas.TITULO, args);
	}

//	@FXML private Button btnDatosGuardar;
//	@FXML private void guardarDatos() {
//	    if (docenteSeleccion != null) {
//	        try {
//	            int legajo = Integer.parseInt(txtDatosLegajo.getText());
//
//    	        docenteSeleccion.setLegajo(legajo);
//    	        docenteSeleccion.setEstado(cmbDatosEstado.getSelectionModel().getSelectedItem());
//    	        docenteSeleccion.setCategoriaInvestigacion(cmbDatosCategoria.getSelectionModel().getSelectedItem());
//
//    	        exitoGuardado(controlDocente.guardarDocente(docenteSeleccion), TITULO, "Guardar Docente");
//    	        generalMostrarDocente();
//
//    	        modoModificar();
//
//	        } catch (NumberFormatException e) {
//	            alertaError(TITULO, "Guardar Docente", "El legajo tiene que ser numérico");
//	        }
//	    }
//	}

//	@FXML private Button btnDatosDescartar;
//	@FXML private void descartarDatos() {
//	    datosMostrarDocente();
//	}

	@FXML private Button btnDatosPersona;
	@FXML private void seleccionarPersona() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, true);
		args.put(Busqueda.KEY_TIPO, Personas.TITULO);
		args.put(Busqueda.KEY_CONTROLADOR, this);
		args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_PERSONA);
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Personas.TITULO, args);
	}

// ----------------------------- Pestaña Cargos ----------------------------- //
	private ICargoDocente cargoDocenteSeleccion;
    protected ObservableList<FilaCargo> filasCargos = FXCollections.observableArrayList();

	public class FilaCargo {
	    private ICargoDocente cargoDocente;

		/**
		 * Crea una FilaCargo a partir de un cargoDocente.
		 * @param cd Cargo docente
		 */
		public FilaCargo(ICargoDocente cd) {
		    this.cargoDocente = cd;
		}
		public int getId() {
		    return this.cargoDocente.getId();
		}
		public String getArea() {
		    return this.cargoDocente.getArea().getDescripcion();
		}
		public String getCargo() {
		    return this.cargoDocente.getCargo().getDescripcion();
		}
		public String getEstado() {
		    return this.cargoDocente.getEstado().getDescripcion();
		}
		public ICargoDocente getInstanciaCargoDocente() {
		    return this.cargoDocente;
		}
	}

	/** Refresca la tabla de cargos */
    public void cargosActualizarTabla() {
        filasCargos.clear();
        if (docenteSeleccion != null) {
            for (ICargoDocente cargoDocente : docenteSeleccion.getCargosDocentes()) {
                filasCargos.add(new FilaCargo(cargoDocente));
            }
        }
    }

    /** Muestra los datos del cargo seleccionado: */
    private void cargosMostrarCargoDocente() {
        if (cargoDocenteSeleccion != null) {
            // Si el cargo tiene asignada un área:
            txtCargosArea.setText(cargoDocenteSeleccion.getArea().getDescripcion());
            txtCargosCargo.setText(cargoDocenteSeleccion.getCargo().getDescripcion());
            cmbCargosEstado.getSelectionModel().select(cargoDocenteSeleccion.getEstado());
            cmbCargosTipo.getSelectionModel().select(cargoDocenteSeleccion.getTipoCargo());
            txtCargosDisp.setText(cargoDocenteSeleccion.getDisposicion());
            dtpCargosDispDesde.setValue(cargoDocenteSeleccion.getDispDesde());
            dtpCargosDispHasta.setValue(cargoDocenteSeleccion.getDispHasta());
            txtCargosRes.setText(cargoDocenteSeleccion.getResolucion());
            dtpCargosResDesde.setValue(cargoDocenteSeleccion.getResDesde());
            dtpCargosResHasta.setValue(cargoDocenteSeleccion.getResHasta());
            txtCargosCosto.setText(String.valueOf(cargoDocenteSeleccion.getUltimoCosto()));
            dtpCargosCosto.setValue(cargoDocenteSeleccion.getFechaUltCost());
        }
    }

    /** Vacía los controles de datos del cargo */
	private void cargosVaciarControles() {
		txtCargosArea.clear();
		txtCargosCargo.clear();
		cmbCargosEstado.getSelectionModel().clearSelection();
		cmbCargosTipo.getSelectionModel().clearSelection();
		txtCargosDisp.clear();
		dtpCargosDispDesde.setValue(null);
		dtpCargosDispHasta.setValue(null);
		txtCargosRes.clear();
		dtpCargosResDesde.setValue(null);
		dtpCargosResHasta.setValue(null);
		txtCargosCosto.clear();
		dtpCargosCosto.setValue(null);
	}

	private void cargosModoModificar() {
	    if (this.permiso.getModificar()) {
//	        btnCargosNuevo.setVisible(true);
            btnCargosGuardar.setVisible(true);
            btnCargosDescartar.setVisible(true);
            btnCargosEliminar.setVisible(true);
            btnCargosArea.setVisible(true);
            btnCargosCargo.setVisible(true);
            txtCargosDisp.setEditable(true);
            dtpCargosDispDesde.setEditable(true);
            dtpCargosDispHasta.setEditable(true);
            txtCargosRes.setEditable(true);
            dtpCargosResDesde.setEditable(true);
            dtpCargosResHasta.setEditable(true);

            cmbCargosEstado.setDisable(false);
            cmbCargosTipo.setDisable(false);
	    }
	}

	private void cargosModoNuevo() {
        if (this.permiso.getModificar()) {
            cargosModoModificar();
            btnCargosEliminar.setVisible(false);
        }
	}

	private void cargosModoVer() {
//	    btnCargosNuevo.setVisible(false);
        btnCargosGuardar.setVisible(false);
        btnCargosDescartar.setVisible(false);
        btnCargosEliminar.setVisible(false);
        btnCargosArea.setVisible(false);
        btnCargosCargo.setVisible(false);
        txtCargosDisp.setEditable(false);
        dtpCargosDispDesde.setEditable(false);
        dtpCargosDispHasta.setEditable(false);
        txtCargosRes.setEditable(false);
        dtpCargosResDesde.setEditable(false);
        dtpCargosResHasta.setEditable(false);

        cmbCargosEstado.setDisable(true);
        cmbCargosTipo.setDisable(true);
	}

	@FXML public void inicializarTablaCargos() {
		inicializarTabla("Cargos");
		tblCargos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        cargoDocenteSeleccion = newSelection.getInstanciaCargoDocente();
		        cargosMostrarCargoDocente();
		        cargosModoModificar();
		    }
		});

		cargosActualizarTabla();

	    // Popular estados de cargo y tipos de cargo:
	    cmbCargosEstado.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarEstadosCargo()));

        cmbCargosTipo.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarTiposCargo()));
	}

	@FXML public Button btnCargosNuevo;
	@FXML public void nuevoCargo() {
	    if (docenteSeleccion != null) {
    		cargoDocenteSeleccion = controlDocente.getICargoDocente();
    		cargosVaciarControles();
    		cargosModoNuevo();
	    }
	}

	@FXML public Button btnCargosGuardar;
	@FXML public void guardarCargo() {
	    if (docenteSeleccion != null && cargoDocenteSeleccion != null) {
    		try {

    		    float ultimoCosto = Utilidades.stringToFloat(txtCargosCosto.getText());

    			cargoDocenteSeleccion.setEstado(cmbCargosEstado.getValue());
    			cargoDocenteSeleccion.setTipoCargo(cmbCargosTipo.getValue());
    			cargoDocenteSeleccion.setDisposicion(txtCargosDisp.getText());
    			cargoDocenteSeleccion.setDispDesde(dtpCargosDispDesde.getValue());
    			cargoDocenteSeleccion.setDispHasta(dtpCargosDispHasta.getValue());
    			cargoDocenteSeleccion.setResolucion(txtCargosRes.getText());
    			cargoDocenteSeleccion.setResDesde(dtpCargosResDesde.getValue());
    			cargoDocenteSeleccion.setResHasta(dtpCargosResHasta.getValue());

    			cargoDocenteSeleccion.setUltimoCosto(ultimoCosto);
    			cargoDocenteSeleccion.setFechaUltCost(dtpCargosCosto.getValue());

    			exitoGuardado(controlDocente.guardarCargoDocente(docenteSeleccion, cargoDocenteSeleccion), TITULO, "Guardar Cargo");

    			cargosModoModificar();

    		} catch (Exception e) {
    		    e.printStackTrace();
    			mensajeEstado(e.getMessage());
    		}
    		cargosActualizarTabla();
	    }
	}

	@FXML public Button btnCargosDescartar;
	@FXML public void descartarCargo() {
	    cargosMostrarCargoDocente();
	}

	@FXML public Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
	    if (docenteSeleccion != null && cargoDocenteSeleccion != null) {
	        if (exitoEliminar(controlDocente.quitarCargoDocente(docenteSeleccion, cargoDocenteSeleccion), TITULO, "Eliminar Cargo")) {
	            cargoDocenteSeleccion = null;
                cargosVaciarControles();
                cargosModoVer();
	        }
            cargosActualizarTabla();
	    }
	}

	@FXML public TextField txtCargosArea;
	@FXML public Button btnCargosArea;
	@FXML private void seleccionarArea() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, false);
		args.put(Busqueda.KEY_TIPO, "Areas");
		args.put(Busqueda.KEY_CONTROLADOR, this);
		args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_AREA);
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " Areas", args);
    }

	@FXML public TextField txtCargosCargo;
	@FXML public Button btnCargosCargo;
	@FXML private void seleccionarCargo() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, false);
		args.put(Busqueda.KEY_TIPO, "Cargos");
		args.put(Busqueda.KEY_CONTROLADOR, this);
		args.put(Busqueda.KEY_TIPO_RESPUESTA, TIPO_CARGO);
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " Cargos", args);
    }

	@FXML public TableView<FilaCargo> tblCargos;
    @FXML public TableColumn<FilaCargo, Integer> colCargosId;
    @FXML public TableColumn<FilaCargo, String> colCargosArea;
    @FXML public TableColumn<FilaCargo, String> colCargosCargo;
    @FXML public TableColumn<FilaCargo, String> colCargosEstado;

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

// ----------------------------- Pestaña Investigación ---------------------- //
	protected ObservableList<FilaInvestigacion> filasInvestigacion = FXCollections.observableArrayList();

	public class FilaInvestigacion {
	    private IIntegrante integrante;
	    private IProyecto proyecto;

	    public FilaInvestigacion(IProyecto proyecto, IIntegrante integrante) {
	        this.integrante = integrante;
	        this.proyecto = proyecto;
	    }

	    public int getId() {
	        return this.proyecto.getId();
	    }

	    public String getNombre() {
	        return this.proyecto.getNombre();
	    }

		public String getArea() {
			return this.integrante.getCargoDocente().getArea().getDescripcion();
		}

		public String getCargo() {
		    return integrante.getCargoDocente().getCargo().getDescripcion();
		}
	}

	/** Refresca la tabla de investigación */
	private void investigacionActualizarTabla() {
	    filasInvestigacion.clear();
	    if (docenteSeleccion != null) {
	        // Lista con todos los proyectos (?):
	        List<IProyecto> listaProyectos = controlInvestigacion.listarProyecto(null);
	        for (IProyecto proyecto : listaProyectos) {
	            for (IIntegrante integrante : proyecto.getIntegrantes()) {
	                if (integrante.getCargoDocente().getDocente().getLegajo() == docenteSeleccion.getLegajo()) {
	                    filasInvestigacion.add(new FilaInvestigacion(proyecto, integrante));
	                    break;
	                }
	            }
	        }
	    }
	}

	@FXML private void inicializarInvestigacion() {
	    inicializarTabla("Investigacion");
	    if (docenteSeleccion != null) {
	        CategoriaInvestigacion ci = docenteSeleccion.getCategoriaInvestigacion();
	        txtInvestigacionCategoria.setText(ci.getDescripcion());
	    }
	    investigacionActualizarTabla();
	}

	@FXML private TextField txtInvestigacionCategoria;
	@FXML protected TableView<FilaInvestigacion> tblInvestigacion;
    @FXML protected TableColumn<FilaInvestigacion, Integer> colInvestigacionId;
    @FXML protected TableColumn<FilaInvestigacion, String> colInvestigacionNombre;
    @FXML protected TableColumn<FilaInvestigacion, String> colInvestigacionArea;
    @FXML protected TableColumn<FilaInvestigacion, String> colInvestigacionCargo;

    @FXML private TabPane tabpDocentes;

// ----------------------------- Pestaña Incentivos ------------------------- //

    private IIncentivo incentivoSeleccion = null;
    protected ObservableList<FilaIncentivo> filasIncentivos = FXCollections.observableArrayList();

    public class FilaIncentivo {
        private IIncentivo incentivo;
        public FilaIncentivo(IIncentivo incentivo) {
            this.incentivo = incentivo;
        }
        public int getAnio() {
            return this.incentivo.getFecha().getValue();
        }
        public IIncentivo getInstanciaIncentivo() {
            return this.incentivo;
        }
    }

    /** Refresca la tabla de incentivos */
    private void incentivosActualizarTabla() {
        filasIncentivos.clear();
        if (docenteSeleccion != null) {
            for (IIncentivo incentivo: docenteSeleccion.getIncentivos()) {
                filasIncentivos.add(new FilaIncentivo(incentivo));
            }
        }
    }

    /** Muestra los datos del incentivo seleccionado: */
    private void incentivosMostrarIncentivo() {
        if (incentivoSeleccion != null) {
            txtIncentivosAnio.setText(incentivoSeleccion.getFecha().toString());
        }
    }

    /** Vacía los controles de datos del incentivo */
    private void incentivosVaciarControles() {
        txtIncentivosAnio.clear();
    }

    private void incentivosModoModificar() {
        if (this.permiso.getModificar()) {
            btnIncentivosGuardar.setVisible(true);
            btnIncentivosDescartar.setVisible(true);
            btnIncentivosEliminar.setVisible(true);
            txtIncentivosAnio.setEditable(true);
        }
    }

    private void incentivosModoNuevo() {
        if (this.permiso.getModificar()) {
            btnIncentivosGuardar.setVisible(true);
            btnIncentivosDescartar.setVisible(true);
            btnIncentivosEliminar.setVisible(false);
            txtIncentivosAnio.setEditable(true);
        }
    }

    private void incentivosModoVer() {
//        btnIncentivosNuevo.setVisible(false);
        btnIncentivosGuardar.setVisible(false);
        btnIncentivosDescartar.setVisible(false);
        txtIncentivosAnio.setEditable(false);
        btnIncentivosEliminar.setVisible(false);
    }

	@FXML public void inicializarTablaIncentivos() {
		inicializarTabla("Incentivos");
		tblIncentivos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                incentivoSeleccion = newSelection.getInstanciaIncentivo();
                incentivosMostrarIncentivo();
                incentivosModoModificar();
            }
        });
		incentivosActualizarTabla();
	}

	@FXML private Button btnIncentivosNuevo;
	@FXML private void nuevoIncentivo() {
	    if (docenteSeleccion != null) {
    	    incentivoSeleccion = controlDocente.getIIncentivo();
    	    incentivosVaciarControles();
    	    incentivosModoNuevo();
	    }
	}

	@FXML private Button btnIncentivosGuardar;
	@FXML private void guardarIncentivo() {
	    if (docenteSeleccion != null && incentivoSeleccion != null) {
	        try {
	            incentivoSeleccion.setFecha(Year.of(Integer.parseInt(txtIncentivosAnio.getText())));
	            exitoGuardado(controlDocente.guardarIncentivo(docenteSeleccion, incentivoSeleccion), TITULO, "Guardar Incentivo");
	            incentivosActualizarTabla();
	            incentivosModoModificar();

	        } catch (DateTimeException e) {
	            alertaError(TITULO, "Guardar Incentivo", "El año ingresado es inválido");
	        } catch (NumberFormatException e) {
	            alertaError(TITULO, "Guardar Incentivo", "El año debe ser numérico");
	        }
	    }
	}

	@FXML private Button btnIncentivosDescartar;
	@FXML private void descartarIncentivo() {
	    incentivosMostrarIncentivo();
	}

	@FXML private Button btnIncentivosEliminar;
	@FXML private void eliminarIncentivo() {
	    if (docenteSeleccion != null && incentivoSeleccion != null) {

	        if (exitoEliminar(controlDocente.quitarIncentivo(docenteSeleccion, incentivoSeleccion), TITULO, "Eliminar Incentivo")) {
	            cargoDocenteSeleccion = null;
                incentivosVaciarControles();
                incentivosModoVer();
	        }
            incentivosActualizarTabla();
	    }
	}

	@FXML protected TextField txtIncentivosAnio;
    @FXML protected TableView<FilaIncentivo> tblIncentivos;
    @FXML protected TableColumn<FilaIncentivo, Integer> colIncentivosAnio;

// ----------------------------- Pestaña Observaciones ---------------------- //
	@FXML private TextArea txtaObservaciones;

	/** Vacía los controles de observaciones */
	private void observacionesVaciarControles() {
	    txtaObservaciones.clear();
	}

	/** Muestra las observaciones del docente */
	@FXML private void observacionesMostrarObservaciones() {
	    if (docenteSeleccion != null) {
	        txtaObservaciones.setText(docenteSeleccion.getObservaciones());
	    }
	}

//	@FXML private Button btnObservacionesGuardar;
//	@FXML private void guardarObservaciones() {
//	    if (docenteSeleccion != null) {
//            docenteSeleccion.setObservaciones(txtaObservaciones.getText());
//            exitoGuardado(controlDocente.guardarDocente(docenteSeleccion), TITULO, "Guardar Observaciones");
//        }
//	}
//
//	@FXML private Button btnObservacionesMostrar;
//	@FXML private void mostrarObservaciones() {
//	    if (docenteSeleccion != null) {
//	        txtaObservaciones.setText(docenteSeleccion.getObservaciones());
//	    }
//	}
//
//	@FXML private Button btnObservacionesDescartar;
//	@FXML private void descartarObservaciones() {
//	    if (docenteSeleccion != null) {
//            txtaObservaciones.setText(docenteSeleccion.getObservaciones());
//        } else {
//            txtaObservaciones.clear();
//        }
//	}
}
