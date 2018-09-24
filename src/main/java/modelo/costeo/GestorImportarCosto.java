package modelo.costeo;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import excel.Excel;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.GestorPersona;
import modelo.persona.IPersona;
import persistencia.ManejoDatos;
import utilidades.Utilidades;
import vista.controladores.ControladorVista;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 12 de jun. de 2018
 */
public class GestorImportarCosto {

	public static final int COL_LEGAJO = 0;
	public static final int COL_APELLIDO = 1;
	public static final int COL_NOMBRE = 2;
	public static final int COL_TIPO_CARGO = 3;
	public static final int COL_CODIGO_CARGO = 4;
	public static final int COL_COSTO = 14;

	private LocalDate fechaImportada;

	public LocalDate getFechaImportada() {
		return fechaImportada;
	}

	public void setFechaImportadal(LocalDate fechaImportada) {
		this.fechaImportada = fechaImportada;
	}

	public enum TipoAlta {
		DOCENTE, CARGO, ESTADO;
	}

	// Cargos que faltan en el sistema pero aparecen en el costeo
	private List<ICargoFaltante> faltantesSistema = new ArrayList<ICargoFaltante>();
	// Cargos que faltan en el costeo pero aparecen en el sistema como "activos"
	private List<ICargoFaltante> faltantesCosteo = new ArrayList<ICargoFaltante>();
	// Cargos que no faltan en ningún lado
	private List<ICargoFaltante> faltantesNinguno = new ArrayList<ICargoFaltante>();
	// Cargos importados de la planilla
	private List<ICargoFaltante> cargosImportados = new ArrayList<ICargoFaltante>();

	private GestorDocente gestorDocente = new GestorDocente();
	private GestorPersona gestorPersona = new GestorPersona();
	private GestorCargosFaltantes gestorCargosFaltantes = new GestorCargosFaltantes();

	public List<ICargoFaltante> getFaltantesSistema() {
		return faltantesSistema;
	}

	public List<ICargoFaltante> getFaltantesCosteo() {
		return faltantesCosteo;
	}

	public List<ICargoFaltante> getFaltantesNinguno() {
		return faltantesNinguno;
	}

	public List<ICargoFaltante> getCargosImportados() {
		return cargosImportados;
	}

	/**
	 * Importa los datos de la planilla de costeos, permitiendo al usuario
	 * analizar diferencias entre los datos importados y los del sistema.
	 * 
	 * @param archivo
	 *            La Hoja de cálculo a importar.
	 */
	public EstadoOperacion importar(File archivo) {
		try {
			List<List<String>> grilla = Excel.importar(archivo);
			// Sacar los encabezados y la última fila con las fórmulas:
			grilla.remove(0);
			grilla.remove(0);
			grilla.remove(grilla.size() - 1);

			this.cargosImportados = new ArrayList<ICargoFaltante>();

			for (List<String> fila : grilla) {
				ICargoFaltante cargo = this.gestorCargosFaltantes.getICargoFaltante();
				cargo.setLegajo(Integer.parseInt(fila.get(COL_LEGAJO)));
				cargo.setApellido(fila.get(COL_APELLIDO));
				cargo.setNombre(fila.get(COL_NOMBRE));
				cargo.setCodigoCargo(Integer.parseInt(fila.get(COL_CODIGO_CARGO)));
				cargo.setUltimoCosto(Utilidades.stringToFloat(fila.get(COL_COSTO)));
				cargo.setFechaUltimoCosto(this.fechaImportada);
				this.cargosImportados.add(cargo);
			}

			return new EstadoOperacion(EstadoOperacion.CodigoEstado.OP_OK,
					"La importación del costeo se ha realizado con éxito.");
		} catch (InvalidFormatException | NumberFormatException e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.OP_ERROR,
					"El archivo no tiene el formato correcto. Asegúrese de que"
							+ " se trata de la planilla con el costeo del mes.");
		} catch (IOException e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.OP_ERROR,
					"No se pudo abrir el archivo. Asegúrese de que el archivo"
							+ " es del formato correcto (.xls o .xlsx)"
							+ " y que no está siendo usado por otro programa.");
		}
	}

	/**
	 * Actualiza las listas, cargando los cargos importados anteriormente si no
	 * se importó una planilla todavía
	 */
	public void actualizarListas() {
		this.faltantesSistema = new ArrayList<ICargoFaltante>();
		this.faltantesCosteo = new ArrayList<ICargoFaltante>();
		this.faltantesNinguno = new ArrayList<ICargoFaltante>();

		// No se importó una planilla. Recuperar los últimos cargos faltantes
		if (this.cargosImportados == null || this.cargosImportados.isEmpty()) {
			this.cargosImportados = this.gestorCargosFaltantes.listarUltimosCargosFaltantes();
		}

		if (this.cargosImportados.isEmpty())
			return;

		// Recuperar todos los cargos del sistema, para luego agregarlos
		// a los faltantes del costeo
		List<ICargoDocente> cargosSistema = this.gestorDocente.listarCargo(null, null);

		for (ICargoFaltante cargoImportado : this.cargosImportados) {
			ICargoDocente cargoActual = buscarCargo(cargoImportado.getCodigoCargo(), cargosSistema);

			// El cargo no está en el sistema, o está pero no activo
			if (cargoActual == null || cargoActual.getEstado().getId() != 0) {
				this.faltantesSistema.add(cargoImportado);

				// Sacarlo de los faltantes en el Costeo si está pero no activo
				if (cargoActual != null) {
					cargosSistema.remove(cargoActual);
				}

			} else {
				// El cargo está activo en el sistema y en el costeo
				ICargoFaltante cf = this.gestorCargosFaltantes.getICargoFaltante();
				cf.setCargo(cargoActual);
				this.faltantesNinguno.add(cf);

				// No hará falta agregarlo a los faltantes del costeo
				cargosSistema.remove(cargoActual);
			}
		}
		// Agregar a los faltantes del costeo los que están activos
		// en el sistema y no aparecen en el costeo
		for (ICargoDocente cargo : cargosSistema) {
			if (cargo.getEstado().getId() == 0) {
				ICargoFaltante cf = this.gestorCargosFaltantes.getICargoFaltante();
				cf.setCargo(cargo);
				this.faltantesCosteo.add(cf);
			}
		}
	}
	
	

	/**
	 * Listar comparación de costos actualizados con anteriores
	 */
	public List<FilaCostoComparar> listarComparacion() {
		if (this.faltantesNinguno == null || this.faltantesNinguno.isEmpty()) {
			return null;
		}
		List<FilaCostoComparar> listaComparar = new ArrayList<FilaCostoComparar>();

		for (ICargoFaltante cargoActual : this.faltantesNinguno) {
			ICargoDocente cargoAnterior = getCargo(cargoActual.getCodigoCargo());
			if (cargoAnterior != null) {
				if (cargoAnterior.getUltimoCosto() != cargoActual.getUltimoCosto()) {
					FilaCostoComparar fila = new FilaCostoComparar(cargoAnterior, cargoActual);
					listaComparar.add(fila);
				}
			}
		}

		return listaComparar;
	}

	/**
	 * Guarda los cambios realizados, actualizando los costos de los cargos y
	 * sus estados.
	 */
	public EstadoOperacion guardar(ControladorVista vista) {
		// No hay datos para importar
		if (this.cargosImportados == null || this.cargosImportados.isEmpty()) {
			// No hay datos para actualizar
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
					"No se han importado datos y no hay datos para actualizar."
							+ " Intente importar una planilla.");
		}

		actualizarListas();
		EstadoOperacion eo = guardarCostos();
		switch (eo.getEstado()) {
		case UPDATE_OK:
			EstadoOperacion eo2 = guardarFaltantes();
			switch (eo2.getEstado()) {
			case UPDATE_OK:
				return eo2;
			default:
				return eo2;
			}
		default:
			return eo;
		}
	}

	/**
	 * Actualiza los costos en la Base de Datos
	 * 
	 * @return
	 */
	private EstadoOperacion guardarCostos() {
		EstadoOperacion eo = new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
				"Ha ocurrido un error al intentar actualizar el último costo de los cargos.");
		try {
			// Actualizar los costos
			for (ICargoFaltante cargoImportado : this.faltantesNinguno) {
				// Revisar que existe el cargo
				ICargoDocente cargoActual = getCargo(cargoImportado.getCodigoCargo());
				if (cargoActual != null) {
					cargoActual.setUltimoCosto(cargoImportado.getUltimoCosto());
					cargoActual.setFechaUltCost(cargoImportado.getFechaUltimoCosto());
					EstadoOperacion eo2 = this.gestorDocente.modificarCargoDocente(null, cargoActual);
					switch (eo2.getEstado()) {
					case UPDATE_OK:
						break;
					default:
						throw new Exception(eo2.getMensaje());
					}
				}
			}
			// Todo salió bien
			eo.setEstado(EstadoOperacion.CodigoEstado.UPDATE_OK);
			eo.setMensaje("La actualización se realizó con éxito");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eo;
	}

	/**
	 * Actualiza los CargosFaltantes en la Base de Datos
	 */
	private EstadoOperacion guardarFaltantes() {
		EstadoOperacion eo = new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
				"Ha ocurrido un error al intentar guardar los cargos importados.");
		try {
			for (ICargoFaltante cargoImportado : this.cargosImportados) {
				cargoImportado.setTipo(CargoFaltante.FALTA_SISTEMA);
				this.gestorCargosFaltantes.agregarCargoFaltante(cargoImportado);
			}

			// Todo salió bien
			eo.setEstado(EstadoOperacion.CodigoEstado.UPDATE_OK);
			eo.setMensaje("La actualización se realizó con éxito");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eo;
	}

	/**
	 * Descarta los cambios realizados, volviendo al estado anterior a importar.
	 */
	public void descartar() {
		this.cargosImportados = new ArrayList<ICargoFaltante>();
		actualizarListas();
	}

	/**
	 * Método auxiliar que busca un CargoDocente en una lista de CargosFaltantes
	 * 
	 * @return
	 */
	private ICargoDocente buscarCargo(int codigoCargo, List<ICargoDocente> lista) {
		ICargoDocente encontrado = null;
		int i = 0;
		while (encontrado == null && i < lista.size()) {
			if (lista.get(i).getId() == codigoCargo) {
				encontrado = lista.get(i);
			}
			i++;
		}
		return encontrado;
	}

	/**
	 * Modifica el Estado del CargoDocente
	 * 
	 * @param cargo
	 *            El CargoDocente a modificar
	 * @param estado
	 *            El nuevo Estado del CargoDocente
	 */
	public EstadoOperacion modificarEstado(ICargoDocente cargo, EstadoCargo estado) {
		cargo.setEstado(estado);
		return this.gestorDocente.modificarCargoDocente(null, cargo);
	}

	/**
	 * Intenta cambiar el estado del cargo a "Activo".
	 * 
	 * @param cargo
	 *            - el cargo que se quiere modificar.
	 * @return <b>True</b> si el estado se cambió, <b>False</b> en otro caso.
	 */
	public EstadoOperacion altaEstado(ICargoDocente cargo) {
		EstadoCargo estado = new EstadoCargo(0, "Activo");
		return this.modificarEstado(cargo, estado);
	}

	/**
	 * Chequear cómo manejar el alta de un cargo, dependiendo de la situación:
	 * <ul>
	 * <li>
	 * <p>
	 * El cargo está inactivo en el sistema -> ESTADO
	 * </p>
	 * </li>
	 * <li>
	 * <p>
	 * El cargo no existe pero el docente sí -> CARGO
	 * </p>
	 * </li>
	 * <li>
	 * <p>
	 * El docente no existe -> DOCENTE
	 * </p>
	 * </li>
	 * </ul>
	 */
	public TipoAlta getTipoAlta(ICargoFaltante cargo) {
		if (getCargo(cargo.getCodigoCargo()) != null) {
			return TipoAlta.ESTADO;
		}

		IDocente docente = this.gestorDocente.getIDocente();
		docente.setLegajo(cargo.getLegajo());
		if (GestorDocente.existeDocente(docente)) {
			return TipoAlta.CARGO;
		}

		return TipoAlta.DOCENTE;
	}

	/**
	 * Método auxiliar para obtener un CargoDocente de la Base de Datos.
	 * 
	 * @param codigo
	 *            El código del cargo.
	 * @return El CargoDocente correspondiente.
	 */
	public ICargoDocente getCargo(int codigo) {
		ICargoDocente cargo = null;

		ICargoDocente cargoSelect = this.gestorDocente.getICargoDocente();
		cargoSelect.setId(codigo);
		List<ICargoDocente> listSelect = this.gestorDocente.listarCargo(null, cargoSelect);

		if (listSelect != null && !listSelect.isEmpty()) {
			cargo = listSelect.get(0);
		}

		return cargo;
	}

	/**
	 * @return última actualización del costo
	 */
	public LocalDate getUltimaFecha() {
		LocalDate ultima = null;
		try {
			ManejoDatos md = new ManejoDatos();
			List<Hashtable<String, String>> res = md.select("cargosdocentes", "MAX(FechaUltimoCosto)");
			if (res != null && !res.isEmpty()) {
				Hashtable<String, String> reg = res.get(0);
				if (!reg.isEmpty() && !reg.get("MAX(FechaUltimoCosto)").equals("")) {
					ultima = Date.valueOf(reg.get("MAX(FechaUltimoCosto)")).toLocalDate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ultima;
	}

	/**
	 * Convierte un ICargoFaltante en un ICargoDocente con los datos
	 * correspondientes
	 * 
	 * @param cargof
	 *            el ICargoFaltante
	 * @return un ICargoDocente con los datos de cargof
	 */
	public ICargoDocente prepararCargo(ICargoFaltante cargof) {
		ICargoDocente cargo = this.gestorDocente.getICargoDocente();

		IDocente docenteSeleccion = this.gestorDocente.getIDocente();
		docenteSeleccion.setLegajo(cargof.getLegajo());

		IDocente docente = null;
		if (GestorDocente.existeDocente(docenteSeleccion)) {
			docente = this.gestorDocente.listarDocentes(docenteSeleccion).get(0);
		} else {
			docente = this.gestorDocente.getIDocente();
			IPersona persona = this.gestorPersona.getIPersona();
			persona.setApellido(cargof.getApellido());
			persona.setNombre(cargof.getNombre());
			docente.setPersona(persona);
			docente.setLegajo(cargof.getLegajo());
		}

		cargo.setDocente(docente);
		cargo.setId(cargof.getCodigoCargo());
		cargo.setUltimoCosto(cargof.getUltimoCosto());
		if (cargof.getFechaUltimoCosto() != null)
			cargo.setFechaUltCost(cargof.getFechaUltimoCosto());
		cargo.setEstado(new EstadoCargo(0, "Activo"));

		return cargo;
	}

}
