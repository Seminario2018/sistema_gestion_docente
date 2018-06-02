package vista.controladores;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Year;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.auxiliares.CategoriaInvestigacion;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoDocente;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.TipoCargo;
import modelo.cargo.ICargo;
import modelo.division.IArea;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.docente.IIncentivo;
import modelo.docente.Incentivo;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProyecto;
import modelo.persona.IPersona;
import utilidades.Utilidades;
import vista.GestorPantalla;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de may. de 2018
 */
@SuppressWarnings("rawtypes")
public class Docentes extends ControladorVista implements Initializable {

	public void setDocenteSeleccion(Object docenteSeleccion) {
		if (docenteSeleccion instanceof IDocente) {
			this.docenteSeleccion = (IDocente) docenteSeleccion;
	        actualizarCamposGeneral();
		}
	}
	
	public void setPersonaSeleccion(Object personaSeleccion) {
		if (personaSeleccion instanceof IPersona) {
			this.docenteSeleccion.setPersona((IPersona) personaSeleccion);
			txtDatosDocumento.setText(
                    String.valueOf(((IPersona) personaSeleccion).getNroDocumento()));
            txtDatosNombre.setText(
            		((IPersona) personaSeleccion).getApellido() + ", "
            		+ ((IPersona) personaSeleccion).getNombre());
		}
	}
	
	public void setAreaSeleccion(Object areaSeleccion) {
		if (areaSeleccion instanceof IArea) {
			this.cargoDocenteSeleccion.setArea((IArea) areaSeleccion);
			this.txtCargosArea.setText(((IArea) areaSeleccion).getDescripcion());
		}
	}
	
	public void setCargoSeleccion(Object cargoSeleccion) {
		if (cargoSeleccion instanceof ICargo) {
			this.cargoDocenteSeleccion.setCargo((ICargo) cargoSeleccion);
			this.txtCargosCargo.setText(((ICargo) cargoSeleccion).getDescripcion());
		}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	private ControlDocente controlDocente = new ControlDocente(this);
	private ControlInvestigacion controlInvestigacion = new ControlInvestigacion();
	public IDocente docenteSeleccion;

	public static final String TITULO = "Docentes";

// -------------------------------- General --------------------------------- //
	@FXML public TextField txtDocentesLegajo;
	@FXML public TextField txtDocentesNombre;

	@FXML private void buscarDocente() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, false);
		args.put(Busqueda.KEY_TIPO, Docentes.TITULO);
		args.put(Busqueda.KEY_CONTROLADOR, this);
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Docentes.TITULO, args);
	}

	@FXML private void nuevoDocente() {
		this.docenteSeleccion = this.controlDocente.getIDocente();
    }

	@FXML private void eliminarDocente() {
	    if (docenteSeleccion != null) {
	        this.controlDocente.eliminarDocente(docenteSeleccion);
	        docenteSeleccion = null;
	        vaciarCamposGeneral();
	        dialogoConfirmacion(TITULO, "Eliminar docente", "El docente fue eliminado con éxito.");
	    }
    }

	@FXML private void importarUltimoCosto() {

    }

	private void actualizarCamposGeneral() {
		vaciarCamposGeneral();
		if (this.docenteSeleccion != null) {
			this.txtDocentesLegajo.setText(
					String.valueOf(docenteSeleccion.getLegajo()));
			if (this.docenteSeleccion.getPersona() != null) {
				this.txtDocentesNombre.setText(
						this.docenteSeleccion
							.getPersona().getApellido()
						+ ", " +
						this.docenteSeleccion
							.getPersona().getNombre()
						);
			}
		}

		actualizarCamposDatos();
		actualizarTablaCargos();
//		TODO
//		actualizarTablaInvestigacion();
//		actualizarTablaIncentivos();
//		actualizarObservaciones();
	}

	private void vaciarCamposGeneral() {
		this.txtDocentesLegajo.clear();
		this.txtDocentesNombre.clear();
	}
	
	/**
	 * Recibir parámetros de la pantalla de búsqueda
	 */
	public void recibirParametros(Map<String, Object> args) {
		Object oSeleccion = args.get(Busqueda.KEY_SELECCION);
		if (oSeleccion != null) {
			String seleccion = (String) oSeleccion;
			Object valor = args.get(Busqueda.KEY_VALOR);
			try {
				Method m = this.getClass().getDeclaredMethod("set" + seleccion + "Seleccion", Object.class);
				m.invoke(this, valor);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

// ----------------------------- Pestaña Datos ------------------------------ //
	@FXML public TextField txtDatosDocumento;
	@FXML public TextField txtDatosNombre;
	@FXML public TextField txtDatosLegajo;
	@FXML public ComboBox<EstadoDocente> cmbDatosEstado;
	@FXML public ComboBox<CategoriaInvestigacion> cmbDatosCategoria;

	@FXML private void inicializarDatos() {
	    this.cmbDatosEstado.setItems(
            FXCollections.observableArrayList(
                ControlAuxiliar.listarEstadosDocente()));

	    this.cmbDatosCategoria.setItems(
	        FXCollections.observableArrayList(
                ControlAuxiliar.listarCategoriasInvestigacion()));
	}

	@FXML private void mostrarDatos() {
        if (docenteSeleccion != null) {
            IPersona persona = docenteSeleccion.getPersona();
            txtDatosDocumento.setText(
                    String.valueOf(persona.getNroDocumento()));
            txtDatosNombre.setText(
                    persona.getApellido() + " " + persona.getNombre());
            txtDatosLegajo.setText(
                    String.valueOf(docenteSeleccion.getLegajo()));
            cmbDatosEstado.getSelectionModel().select(
                    docenteSeleccion.getEstado());
            cmbDatosCategoria.getSelectionModel().select(
                    docenteSeleccion.getCategoriaInvestigacion());
        }
	}

	@FXML private void guardarDatos() {
	    // TODO Botón "Guardar Cambios"
	    if (docenteSeleccion != null) {
	        // TODO docenteSeleccionado.getPersona()
	        docenteSeleccion.setLegajo(
	                Integer.parseInt(txtDatosLegajo.getText()));
	        docenteSeleccion.setEstado(
	                cmbDatosEstado.getSelectionModel().getSelectedItem());
	        docenteSeleccion.setCategoriaInvestigacion(
	                cmbDatosCategoria.getSelectionModel().getSelectedItem());
	        // TODO Modificar persona en ControlPersona
	        this.controlDocente.modificarDocente(docenteSeleccion);
	    }
	}

	@FXML private void descartarDatos() {
	    // TODO Descartar cambios.
	    mostrarDatos();
	}

	@FXML private void seleccionarPersona() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, true);
		args.put(Busqueda.KEY_TIPO, Personas.TITULO);
		args.put(Busqueda.KEY_CONTROLADOR, this);
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " " + Personas.TITULO, args);
	}

	private void actualizarCamposDatos() {
		vaciarCamposDatos();
		mostrarDatos();
	}

	private void vaciarCamposDatos() {
		txtDatosDocumento.clear();
		txtDatosNombre.clear();
		txtDatosLegajo.clear();
		cmbDatosEstado.getSelectionModel().clearSelection();
		cmbDatosCategoria.getSelectionModel().clearSelection();
	}

// ----------------------------- Pestaña Cargos ----------------------------- //
	public class FilaCargo {
		public int id;
		public String area;
		public String cargo;
		public String estado;
		public FilaCargo(int id, String area,
				String cargo, String estado) {
			super();
			this.id = id;
			this.area = area;
			this.cargo = cargo;
			this.estado = estado;
		}
		/**
		 * Crea una FilaCargo a partir de un cargoDocente.
		 * @param cd Cargo docente
		 */
		public FilaCargo(ICargoDocente cd) {
		    super();
		    this.id = cd.getId();
		    this.area = cd.getArea().getDescripcion();
		    this.cargo = cd.getCargo().getDescripcion();
		    this.estado = cd.getEstado().getDescripcion();
		}
		public int getId() { return id; }
		public void setId(int idCargo) { this.id = idCargo; }
		public String getArea() { return area; }
		public void setArea(String area) { this.area = area; }
		public String getCargo() { return cargo; }
		public void setCargo(String cargo) { this.cargo = cargo; }
		public String getEstado() { return estado; }
		public void setEstado(String estado) { this.estado = estado; }
	}

	public ICargoDocente cargoDocenteSeleccion;
	public List<ICargoDocente> listaCargos;
	public ObservableList<FilaCargo> filasCargos;

	private void vaciarCamposCargos() {
		txtCargosArea.clear();
		txtCargosCargo.clear();
		cmbCargosEstado.getSelectionModel().clearSelection();
		cmbCargosTipo.getSelectionModel().clearSelection();
		txtCargosDisp.clear();
		dtpCargosDispDesde.getEditor().clear();
		dtpCargosDispHasta.getEditor().clear();
		txtCargosRes.clear();
		dtpCargosResDesde.getEditor().clear();
		dtpCargosResHasta.getEditor().clear();
		txtCargosCosto.clear();
		dtpCargosCosto.getEditor().clear();
	}

	@FXML public void seleccionarCargoDocente() {
		// DONE cargoDocenteSeleccionado = seleccionado de tblCargoDocente;
		FilaCargo fila = (FilaCargo) tblCargos.getSelectionModel().getSelectedItem();
		ICargoDocente cd = this.controlDocente.getICargoDocente();
		// Busco el cargoDocente en la BD según su id:
		cd.setId(fila.getId());
		List<ICargoDocente> listaCargosDocente = this.controlDocente.listarCargosDocente(docenteSeleccion, cd);
		cargoDocenteSeleccion = listaCargosDocente.get(0);

		// DONE Mostrar datos del cargo docente seleccionado en los controles:
		txtCargosArea.setText(
		        cargoDocenteSeleccion.getArea().getDescripcion());
		txtCargosCargo.setText(
		        cargoDocenteSeleccion.getCargo().getDescripcion());
		cmbCargosEstado.getSelectionModel().select(
		        cargoDocenteSeleccion.getEstado());
		cmbCargosTipo.getSelectionModel().select(
		        cargoDocenteSeleccion.getTipoCargo());
		txtCargosDisp.setText(
		        cargoDocenteSeleccion.getDisposicion());
		dtpCargosDispDesde.setValue(
		        cargoDocenteSeleccion.getDispDesde());
		dtpCargosDispHasta.setValue(
		        cargoDocenteSeleccion.getDispHasta());
		txtCargosRes.setText(
		        cargoDocenteSeleccion.getResolucion());
		dtpCargosResDesde.setValue(
		        cargoDocenteSeleccion.getResDesde());
		dtpCargosResHasta.setValue(
                cargoDocenteSeleccion.getResHasta());
		txtCargosCosto.setText(
		        String.valueOf(cargoDocenteSeleccion.getUltimoCosto()));
		dtpCargosCosto.setValue(
		        cargoDocenteSeleccion.getFechaUltCost());
	}

	@SuppressWarnings("unchecked")
	@FXML public void inicializarTablaCargos() {
		inicializarTabla("Cargos");
		this.tblCargos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		        seleccionarCargoDocente();
		    }
		});

	    /* DONE Popular estados y tipos */
	    this.cmbCargosEstado.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarEstadosCargo()));

        this.cmbCargosTipo.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarTiposCargo()));

        /* DONE Popular tabla con cargosDocente de docenteSeleccionado */
        // Agarro todos los cargos docente del docente seleccionado:
        if (docenteSeleccion != null) {
        	List<ICargoDocente> listaCD = this.controlDocente.listarCargosDocente(docenteSeleccion, null);
	        for (ICargoDocente cd : listaCD) {
	            // Muestro los cargosDocente en la tabla:
	            this.filasCargos.add(
	                    new FilaCargo(cd));
	        }
        }
        //*/
	}

	public void actualizarTablaCargos() {
		if (this.filasCargos != null) {
			this.filasCargos.clear();
			if (this.docenteSeleccion != null) {
				this.listaCargos = this.controlDocente.listarCargosDocente(this.docenteSeleccion, null);
				for (ICargoDocente cargo : this.listaCargos) {
					FilaCargo fc = new FilaCargo(
							cargo.getId(),
							cargo.getArea().getDescripcion(),
							cargo.getCargo().getDescripcion(),
							cargo.getEstado().getDescripcion()
							);
					this.filasCargos.add(fc);
				}
			}
		}
	}
	
	/**
	 * Método que se ejecuta cuando se selecciona un Cargo Docente
	 */
	public void actualizarCamposCargos() {
		
	}

	@FXML public TableView tblCargos;
	@FXML public TableColumn colCargosId;
	@FXML public TableColumn colCargosArea;
	@FXML public TableColumn colCargosCargo;
	@FXML public TableColumn colCargosEstado;

	// Pruebas
	int idCargoDocente = 0;

	@FXML public Button btnCargosNuevo;
	@FXML public void nuevoCargo() {
		this.cargoDocenteSeleccion = this.controlDocente.getICargoDocente();
		vaciarCamposCargos();
	}

	@FXML public Button btnCargosGuardar;
	@FXML public void guardarCargo() {
		try {
			this.cargoDocenteSeleccion.setEstado(cmbCargosEstado.getValue());
			this.cargoDocenteSeleccion.setTipoCargo(cmbCargosTipo.getValue());
			this.cargoDocenteSeleccion.setDisposicion(txtCargosDisp.getText());
			this.cargoDocenteSeleccion.setDispDesde(dtpCargosDispDesde.getValue());
			this.cargoDocenteSeleccion.setDispHasta(dtpCargosDispHasta.getValue());
			this.cargoDocenteSeleccion.setResolucion(txtCargosRes.getText());
			this.cargoDocenteSeleccion.setResDesde(dtpCargosResDesde.getValue());
			this.cargoDocenteSeleccion.setResHasta(dtpCargosResHasta.getValue());

			this.cargoDocenteSeleccion.setUltimoCosto(
					Utilidades.stringToFloat(txtCargosCosto.getText()));
			this.cargoDocenteSeleccion.setFechaUltCost(dtpCargosCosto.getValue());

			this.controlDocente.guardarCargoDocente(this.docenteSeleccion, this.cargoDocenteSeleccion);
		} catch (IllegalArgumentException e) {
			alertaError("Cargos", "Error en el campo Último costo", e.getMessage());
		}
		actualizarTablaCargos();
	}

	@FXML public Button btnCargosDescartar;
	@FXML public void descartarCargo() {
	    cargoDocenteSeleccion = null;
	    vaciarCamposCargos();
	    // TODO actualizarCamposCargos(); Se ejecuta cuando se selecciona un cargo docente
	}

	@FXML public Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
		if (!this.tblCargos.getSelectionModel().isEmpty()) {
			FilaCargo fila = (FilaCargo) tblCargos.getSelectionModel().getSelectedItem();
			ICargoDocente cd = this.controlDocente.getICargoDocente();
			cd.setId(fila.getId());
	        EstadoOperacion estado = this.controlDocente.quitarCargoDocente(docenteSeleccion, cd);
	        if (estado.getEstado() != EstadoOperacion.CodigoEstado.DELETE_OK) {
	            alertaError("Cargos", "Quitar Cargo Docente", "No se pudo quitar el cargo.");
	        } else {
	        	this.filasCargos.remove(fila);
	        }
		}
	}

	
	@FXML public TextField txtCargosArea;
	@FXML public Button btnCargosArea;
	@FXML private void seleccionarArea() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put(Busqueda.KEY_NUEVO, false);
		args.put(Busqueda.KEY_TIPO, "Areas");
		args.put(Busqueda.KEY_CONTROLADOR, this);
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
		args.put(GestorPantalla.KEY_PADRE, Docentes.TITULO);
		this.gestorPantalla.lanzarPantalla(Busqueda.TITULO + " Cargos", args);
    }


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
	// TODO Pestaña "Investigación"
	@FXML private TextField txtInvestigacionCategoria;
	@FXML protected TableView<FilaInvestigacion> tblInvestigacion;
	protected ObservableList<FilaInvestigacion> filasInvestigacion;

	public class FilaInvestigacion {
	    private int id;
	    private String nombre;
	    private String area;
	    private String cargo;

        public FilaInvestigacion(int id, String nombre, String area, String cargo) {
            this.id = id;
            this.nombre = nombre;
            this.area = area;
            this.cargo = cargo;
        }
	    public FilaInvestigacion(IProyecto proyecto, ICargoDocente cargoDocente) {
	        this.id = proyecto.getId();
	        this.nombre = proyecto.getNombre();
	        this.setArea(cargoDocente.getArea().getDescripcion());
	        this.setCargo(cargoDocente.getCargo().getDescripcion());
	    }
	    public int getId() {
	        return this.id;
	    }
	    public void setId(int id) {
	        this.id = id;
	    }
	    public String getNombre() {
	        return this.nombre;
	    }
	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }
		public String getArea() {
			return area;
		}
		public void setArea(String area) {
			this.area = area;
		}
		public String getCargo() {
			return cargo;
		}
		public void setCargo(String cargo) {
			this.cargo = cargo;
		}
	}

	@FXML private void inicializarInvestigacion() {
	    inicializarTabla("Investigacion");
	    if (docenteSeleccion != null) {
	        CategoriaInvestigacion ci = docenteSeleccion.getCategoriaInvestigacion();
	        txtInvestigacionCategoria.setText(ci.getDescripcion());
	    }

	    // Lista con todos los proyectos (?):
        List<IProyecto> listaProyectos = this.controlInvestigacion.listarProyecto(null);
        for (IProyecto proyecto : listaProyectos) {
            for (IIntegrante integrante : proyecto.getIntegrantes()) {
                if (integrante.getLegajo() == docenteSeleccion.getLegajo()) {
                    // TODO Investigación: Falta el área
                    FilaInvestigacion fila = new FilaInvestigacion(proyecto.getId(), proyecto.getNombre(), "", integrante.getCargo());
                    filasInvestigacion.add(fila);
                    break;
                }
            }
        }
	}
// ----------------------------- Pestaña Incentivos ------------------------- //
	// DONE Pestaña "Incentivos"
	@FXML protected TextField txtIncentivosAnio;
	@FXML protected TableView<FilaIncentivo> tblIncentivos;
	@FXML protected TableColumn<FilaIncentivo, Integer> colIncentivosAnio;
	protected ObservableList<FilaIncentivo> filasIncentivos = FXCollections.observableArrayList();

	// Cambios en los incentivos:
	protected Set<IIncentivo> incentivosNuevos = new HashSet<IIncentivo>();
	protected Set<IIncentivo> incentivosBorrados = new HashSet<IIncentivo>();

	public class FilaIncentivo {
	    private int anio;
	    public FilaIncentivo(IIncentivo incentivo) {
	        this.anio = incentivo.getFecha().getValue();
	    }
	    public int getAnio() {
	        return this.anio;
	    }
	    public void setAnio(int fecha) {
	        this.anio = fecha;
	    }
	    public IIncentivo getIncentivo() {
	        return new Incentivo(Year.of(this.anio));
	    }
	}

	private void listarIncentivosTabla() {
	    if (docenteSeleccion != null) {
	        filasIncentivos.clear();
	        for (IIncentivo incentivo : docenteSeleccion.getIncentivos()) {
                filasIncentivos.add(new FilaIncentivo(incentivo));
            }
	    }
	}

	@FXML public void inicializarTablaIncentivos() {
		inicializarTabla("Incentivos");
	    listarIncentivosTabla();

	    incentivosNuevos.clear();
	    incentivosBorrados.clear();
	}

	@FXML private void nuevoIncentivo() {
	    try {
	        // Creo incentivo:
    	    IIncentivo nuevoIncentivo = new Incentivo(Year.of(Integer.parseInt(txtIncentivosAnio.getText())));
    	    incentivosNuevos.add(nuevoIncentivo);

    	    // Agrego a tabla de incentivos:
    	    filasIncentivos.add(new FilaIncentivo(nuevoIncentivo));
    	    incentivosNuevos.add(nuevoIncentivo);
	    } catch (NumberFormatException nfe) {
	        alertaError(TITULO, "Error de formato", "La fecha tiene que ser un número");
	    }
	}

	@FXML private void guardarIncentivo() {
	    // Borrar incentivos:
	    for (IIncentivo incentivoBorrado : incentivosBorrados) {
	        docenteSeleccion.quitarIncentivo(incentivoBorrado);
	        this.controlDocente.quitarIncentivo(docenteSeleccion, incentivoBorrado);
	    }
	    incentivosBorrados.clear();

	    // Incentivos nuevos:
	    for (IIncentivo incentivoNuevo : incentivosNuevos) {
	        docenteSeleccion.agregarIncentivo(incentivoNuevo);
	        this.controlDocente.agregarIncentivo(docenteSeleccion, incentivoNuevo);
	    }
	    incentivosNuevos.clear();
	}

	@FXML private void descartarIncentivo() {
	    // Quito todos los cambios a los incentivos del docente:
	    incentivosBorrados.clear();
	    incentivosNuevos.clear();

	    // Cambio la tabla para mostrar los incentivos originales:
	    listarIncentivosTabla();
	}

	@FXML private void eliminarIncentivo() {
	    FilaIncentivo fila = tblIncentivos.getSelectionModel().getSelectedItem();
	    incentivosBorrados.add(fila.getIncentivo());
	    filasIncentivos.remove(fila);
	}

// ----------------------------- Pestaña Observaciones ---------------------- //
	// DONE Pestaña "Observaciones"
	@FXML private TextArea txtaObservaciones;

	@FXML private void guardarObservaciones() {
	    if (docenteSeleccion != null) {
            docenteSeleccion.setObservaciones(txtaObservaciones.getText());
            this.controlDocente.modificarDocente(docenteSeleccion);
        }
	}

	@FXML private void mostrarObservaciones() {
	    if (docenteSeleccion != null) {
	        txtaObservaciones.setText(docenteSeleccion.getObservaciones());
	    }
	}

	@FXML private void descartarObservaciones() {
	    // TODO "Descartar cambios"
	    mostrarObservaciones();
	}
}
