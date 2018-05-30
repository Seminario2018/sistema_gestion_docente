package vista.controladores;

import java.net.URL;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import controlador.ControlAuxiliar;
import controlador.ControlDivision;
import controlador.ControlDocente;
import controlador.ControlInvestigacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import modelo.cargo.Cargo;
import modelo.cargo.ICargo;
import modelo.division.Area;
import modelo.division.IArea;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.docente.IIncentivo;
import modelo.docente.Incentivo;
import modelo.investigacion.IIntegrante;
import modelo.investigacion.IProyecto;
import modelo.persona.IPersona;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 1 de may. de 2018
 */
@SuppressWarnings("rawtypes")
public class Docentes extends ControladorVista {

	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	private ControlDivision controlDivision = new ControlDivision(this);
	private ControlDocente controlDocente = new ControlDocente(this);
	private ControlInvestigacion controlInvestigacion = new ControlInvestigacion();
	public IDocente docenteSeleccionado = this.controlDocente.getIDocente();

	private static final String TITULO = "Docentes";

// -------------------------------- General --------------------------------- //
	@FXML public TextField txtDocentesLegajo;
	@FXML public TextField txtDocentesNombre;

	@FXML private void buscarDocente() {
	    /* TEST Docentes: Selección de docente */
        // Recupera al docente legajo 143191
	    IDocente docenteBusqueda = this.controlDocente.getIDocente();
	    docenteBusqueda.setLegajo(143191);
	    docenteSeleccionado = this.controlDocente
	        .listarDocente(docenteBusqueda)
	        .get(0);
        actualizarCamposGeneral();
        //*/
	}

	@FXML private void nuevoDocente() {

    }

	@FXML private void eliminarDocente() {
	    if (docenteSeleccionado != null) {
	        this.controlDocente.eliminarDocente(docenteSeleccionado);
	        docenteSeleccionado = null;
	        vaciarCamposGeneral();
	        dialogoConfirmacion(TITULO, "Eliminar docente", "El docente fue eliminado con éxito.");
	    }
    }

	@FXML private void importarUltimoCosto() {

    }

	private void actualizarCamposGeneral() {
		vaciarCamposGeneral();
		if (this.docenteSeleccionado != null) {
			this.txtDocentesLegajo.setText(
					String.valueOf(docenteSeleccionado.getLegajo()));
			if (this.docenteSeleccionado.getPersona() != null) {
				this.txtDocentesNombre.setText(
						this.docenteSeleccionado
							.getPersona().getApellido()
						+ ", " +
						this.docenteSeleccionado
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
        if (docenteSeleccionado != null) {
            IPersona persona = docenteSeleccionado.getPersona();
            txtDatosDocumento.setText(
                    String.valueOf(persona.getNroDocumento()));
            txtDatosNombre.setText(
                    persona.getApellido() + " " + persona.getNombre());
            txtDatosLegajo.setText(
                    String.valueOf(docenteSeleccionado.getLegajo()));
            cmbDatosEstado.getSelectionModel().select(
                    docenteSeleccionado.getEstado());
            cmbDatosCategoria.getSelectionModel().select(
                    docenteSeleccionado.getCategoriaInvestigacion());
        }
	}

	@FXML private void guardarDatos() {
	    // TODO Botón "Guardar Cambios"
	    if (docenteSeleccionado != null) {
	        // TODO docenteSeleccionado.getPersona()
	        docenteSeleccionado.setLegajo(
	                Integer.parseInt(txtDatosLegajo.getText()));
	        docenteSeleccionado.setEstado(
	                cmbDatosEstado.getSelectionModel().getSelectedItem());
	        docenteSeleccionado.setCategoriaInvestigacion(
	                cmbDatosCategoria.getSelectionModel().getSelectedItem());
	        // TODO Modificar persona en ControlPersona
	        this.controlDocente.modificarDocente(docenteSeleccionado);
	    }
	}

	@FXML private void descartarDatos() {
	    // TODO Descartar cambios.
	    mostrarDatos();
	}

	@FXML private void buscarPersona() {
	    // TODO "Seleccionar Persona"
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

	public ICargoDocente cargoDocenteSeleccionado;
	public List<ICargoDocente> listaCargos;
	public ObservableList<FilaCargo> filasCargos;

	private void vaciarCamposCargos() {
		txtCargosArea.clear();
		txtCargosCargo.clear();
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
		List<ICargoDocente> listaCargosDocente = this.controlDocente.listarCargosDocente(docenteSeleccionado, cd);
		cargoDocenteSeleccionado = listaCargosDocente.get(0);

		// DONE Mostrar datos del cargo docente seleccionado en los controles:
		txtCargosArea.setText(
		        cargoDocenteSeleccionado.getArea().getDescripcion());
		txtCargosCargo.setText(
		        cargoDocenteSeleccionado.getCargo().getDescripcion());
		cmbCargosEstado.getSelectionModel().select(
		        cargoDocenteSeleccionado.getEstado());
		cmbCargosTipo.getSelectionModel().select(
		        cargoDocenteSeleccionado.getTipoCargo());
		txtCargosDisp.setText(
		        cargoDocenteSeleccionado.getDisposicion());
		dtpCargosDispDesde.setValue(
		        cargoDocenteSeleccionado.getDispDesde());
		dtpCargosDispHasta.setValue(
		        cargoDocenteSeleccionado.getDispHasta());
		txtCargosRes.setText(
		        cargoDocenteSeleccionado.getResolucion());
		dtpCargosResDesde.setValue(
		        cargoDocenteSeleccionado.getResDesde());
		dtpCargosResHasta.setValue(
                cargoDocenteSeleccionado.getResHasta());
		txtCargosCosto.setText(
		        String.valueOf(cargoDocenteSeleccionado.getUltimoCosto()));
		dtpCargosCosto.setValue(
		        cargoDocenteSeleccionado.getFechaUltCost());
	}

	@FXML public void inicializarTablaCargos() {
		inicializarTabla("Cargos");

		/* TEST Docente de prueba: *
	    IPersona personaSeleccionada = new Persona(
	            "Juran", "Martín Tomás",
	            null, null, 21345678, null, null, null, null);

	    docenteSeleccionado = new Docente();
	    docenteSeleccionado.setLegajo(143191);
	    docenteSeleccionado.setPersona(personaSeleccionada);

	    this.txtDocentesLegajo.setText(
	    		String.valueOf(docenteSeleccionado.getLegajo())
	    		);

	    this.txtDocentesNombre.setText(
	    		docenteSeleccionado.getPersona().getApellido()
	    		+ ", " +
	    		docenteSeleccionado.getPersona().getNombre()
	    		);
	    //*/

	    /* DONE Popular estados y tipos */
	    this.cmbCargosEstado.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarEstadosCargo()));

        this.cmbCargosTipo.setItems(
                FXCollections.observableArrayList(
                        ControlAuxiliar.listarTiposCargo()));

        /* DONE Popular tabla con cargosDocente de docenteSeleccionado */
        // Agarro todos los cargos docente del docente seleccionado:
        List<ICargoDocente> listaCD = this.controlDocente.listarCargosDocente(docenteSeleccionado, null);
        for (ICargoDocente cd : listaCD) {
            // Muestro los cargosDocente en la tabla:
            this.filasCargos.add(
                    new FilaCargo(cd));
        }
        //*/
	}

	public void actualizarTablaCargos() {
		// TODO actualizarTablaCargos()
		/*
		this.listaCargos = this.control.getListaCargosDocentes();
		for (ICargoDocente cargo : this.listaCargos) {
			FilaCargo fc = new FilaCargo(
					cargo.getId(),
					cargo.getArea().getDescripcion(),
					cargo.getCargo().getDescripcion(),
					cargo.getEstado().getDescripcion()
					);
			this.filasCargos.add(fc);
		}
		 */
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
		// Obtener un ICargoDocente vacío
		cargoDocenteSeleccionado = this.controlDocente.getICargoDocente();
		/* TEST *
		cargoDocenteSeleccionado.setId(idCargoDocente++);
		//*/
		vaciarCamposCargos();
		System.out.println(btnCargosNuevo.getStyleClass());
	}

	@FXML public Button btnCargosGuardar;
	@FXML public void guardarCargo() {

		try {
			cargoDocenteSeleccionado.setUltimoCosto(
		    		Utilidades.stringToFloat(txtCargosCosto.getText()));

			cargoDocenteSeleccionado.setArea(areaSeleccionada);
			cargoDocenteSeleccionado.setCargo(cargoSeleccionado);
			cargoDocenteSeleccionado.setEstado(cmbCargosEstado.getValue());
			cargoDocenteSeleccionado.setTipoCargo(cmbCargosTipo.getValue());
			cargoDocenteSeleccionado.setDisposicion(txtCargosDisp.getText());
			cargoDocenteSeleccionado.setDispDesde(dtpCargosDispDesde.getValue());
			cargoDocenteSeleccionado.setDispHasta(dtpCargosDispHasta.getValue());
			cargoDocenteSeleccionado.setResolucion(txtCargosRes.getText());
			cargoDocenteSeleccionado.setResDesde(dtpCargosResDesde.getValue());
			cargoDocenteSeleccionado.setResHasta(dtpCargosResHasta.getValue());

			cargoDocenteSeleccionado.setFechaUltCost(dtpCargosCosto.getValue());

			this.controlDocente.guardarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);

			/* FilaCargo fc = new FilaCargo(
					cargoDocenteSeleccionado.getId(),
					cargoDocenteSeleccionado.getArea().getDescripcion(),
					cargoDocenteSeleccionado.getCargo().getDescripcion(),
					cargoDocenteSeleccionado.getEstado().getDescripcion()
					);

			this.filasCargos.add(fc);
			*/

			// TODO Reemplazar por actualizarTablaCargos()
			this.filasCargos.add(
			        new FilaCargo(cargoDocenteSeleccionado));

		} catch (IllegalArgumentException e) {
			alertaError("Cargos", "Error en el campo Último costo", e.getMessage());
		}
	}

	@FXML public Button btnCargosDescartar;
	@FXML public void descartarCargo() {
	    cargoDocenteSeleccionado = null;
	    vaciarCamposCargos();
	    // TODO actualizarCamposCargos(); Se ejecuta cuando se selecciona un cargo docente
	}

	@FXML public Button btnCargosEliminar;
	@FXML public void eliminarCargo() {
	    // DONE Eliminar cargo
	    FilaCargo fila = (FilaCargo) tblCargos.getSelectionModel().getSelectedItem();
        this.filasCargos.remove(fila);

        EstadoOperacion estado = this.controlDocente.quitarCargoDocente(docenteSeleccionado, cargoDocenteSeleccionado);
        if (estado.getEstado() != EstadoOperacion.CodigoEstado.INSERT_OK) {
            alertaError("Cargos", "Quitar Cargo Docente", "No se pudo quitar el cargo.");
        }
	}



	public IArea areaSeleccionada;
	@FXML public TextField txtCargosArea;
	@FXML public Button btnCargosArea;
	@FXML private void seleccionarArea() {
        // TODO Seleccionar Área

		/* TEST Prueba Área *
		TipoContacto tipoContactoJefe = new TipoContacto();
		tipoContactoJefe.setId(0);
		tipoContactoJefe.setDescripcion("MailLaboral");

		IContacto contactoJefe = new Contacto(1, tipoContactoJefe, "semint2018@gmail.com");

		IPersona personaJefe = new Persona();
		personaJefe.setContactos(Arrays.asList(contactoJefe));

	    IDocente docenteJefe = new Docente();
	    docenteJefe.setLegajo(121899);
	    docenteJefe.setPersona(personaJefe);

	    IDivision divisionBiologia = new Division(1, "Biología", docenteJefe, null, null, null);

		IArea a = new Area("B1", "Biología 1", divisionBiologia, null, null, null, null, null);

		areaSeleccionada = a;
        /* TEST Prueba Area 02*/
	    System.out.printf(">>> seleccionarArea <<<");
	    IArea a = new Area();
	    a.setCodigo("CO.01.00");
	    areaSeleccionada = this.controlDivision.listarAreas(a).get(0);

	    //*/
		// TODO Sacar lista de áreas de la BD (?)
//	    List<IArea> listaAreas = this.controlDivision.listarAreas(null);
//	    assert(!listaAreas.isEmpty());
//	    areaSeleccionada = listaAreas.get(0);

		txtCargosArea.setText(areaSeleccionada.getDescripcion());
    }

	public ICargo cargoSeleccionado;
	@FXML public TextField txtCargosCargo;
	@FXML public Button btnCargosCargo;
	@FXML private void seleccionarCargo() {
        // TODO Seleccionar Cargo
	    /* TEST Prueba Cargo*/
		ICargo c = new Cargo(213, "Profesor Titular Exclusiva", 40);
		cargoSeleccionado = c;

        //*/
		// TODO Sacar lista de cargos de la BD (?)
		List<ICargo> listaCargos = this.controlDocente.listarCargos(null);
		txtCargosCargo.setText(cargoSeleccionado.getDescripcion());
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
	    if (docenteSeleccionado != null) {
	        CategoriaInvestigacion ci = docenteSeleccionado.getCategoriaInvestigacion();
	        txtInvestigacionCategoria.setText(ci.getDescripcion());
	    }

	    // Lista con todos los proyectos (?):
        List<IProyecto> listaProyectos = this.controlInvestigacion.listarProyecto(null);
        for (IProyecto proyecto : listaProyectos) {
            for (IIntegrante integrante : proyecto.getIntegrantes()) {
                if (integrante.getLegajo() == docenteSeleccionado.getLegajo()) {
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

	class FilaIncentivo {
	    private int fecha;
	    public FilaIncentivo(IIncentivo incentivo) {
	        this.fecha = incentivo.getFecha().getValue();
	    }
	    public int getFecha() {
	        return this.fecha;
	    }
	    public void setFecha(int fecha) {
	        this.fecha = fecha;
	    }
	    public IIncentivo getIncentivo() {
	        return new Incentivo(Year.of(this.fecha));
	    }
	}

	private void listarIncentivosTabla() {
	    if (docenteSeleccionado != null) {
	        filasIncentivos.clear();
	        for (IIncentivo incentivo : docenteSeleccionado.getIncentivos()) {
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
	        alertaError("Docentes", "Error de formato", "La fecha tiene que ser un número");
	    }
	}

	@FXML private void guardarIncentivo() {
	    // Borrar incentivos:
	    for (IIncentivo incentivoBorrado : incentivosBorrados) {
	        docenteSeleccionado.quitarIncentivo(incentivoBorrado);
	        this.controlDocente.quitarIncentivo(docenteSeleccionado, incentivoBorrado);
	    }
	    incentivosBorrados.clear();

	    // Incentivos nuevos:
	    for (IIncentivo incentivoNuevo : incentivosNuevos) {
	        docenteSeleccionado.agregarIncentivo(incentivoNuevo);
	        this.controlDocente.agregarIncentivo(docenteSeleccionado, incentivoNuevo);
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
	    if (docenteSeleccionado != null) {
            docenteSeleccionado.setObservaciones(txtaObservaciones.getText());
            this.controlDocente.modificarDocente(docenteSeleccionado);
        }
	}

	@FXML private void mostrarObservaciones() {
	    if (docenteSeleccionado != null) {
	        txtaObservaciones.setText(docenteSeleccionado.getObservaciones());
	    }
	}

	@FXML private void descartarObservaciones() {
	    // TODO "Descartar cambios"
	    mostrarObservaciones();
	}
}
