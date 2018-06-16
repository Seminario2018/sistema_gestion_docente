package modelo.costeo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import excel.Excel;
import mail.NotificacionCargo2;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.GestorDocente;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import utilidades.Utilidades;

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
	
	public enum TipoAlta {
		DOCENTE, CARGO, ESTADO;
	}
	
	// Cargos que faltan en el sistema pero aparecen en el costeo
	private List<ICargoFaltante> faltantesSistema = new ArrayList<ICargoFaltante>();
	// Cargos que faltan en el costeo pero aparecen en el sistema como "activos"
	private List<ICargoDocente> faltantesCosteo = new ArrayList<ICargoDocente>();
	// Cargos importados de la planilla
	private List<ICargoFaltante> cargosImportados = new ArrayList<ICargoFaltante>();
	
	private GestorDocente gestorDocente = new GestorDocente();
	private GestorCargosFaltantes gestorCargosFaltantes = new GestorCargosFaltantes();
	
	public List<ICargoFaltante> getFaltantesSistema() {
		actualizarListas();
		return faltantesSistema;
	}
	public List<ICargoDocente> getFaltantesCosteo() {
		actualizarListas();
		return faltantesCosteo;
	}
	public List<ICargoFaltante> getCargosImportados() {
		return cargosImportados;
	}
	
	/**
	 * Importa los datos de la planilla de costeos, permitiendo al usuario
	 * analizar diferencias entre los datos importados y los del sistema.
	 * @param archivo La Hoja de cálculo a importar.
	 */
	public EstadoOperacion importar(File archivo) {
		try {
	        List<List<String>> grilla = Excel.importar(archivo);
	        // Sacar los encabezados y las últimas filas con las fórmulas:
	        grilla.remove(0);
	        grilla.remove(0);
	        grilla.remove(grilla.size() - 1);
	        grilla.remove(grilla.size() - 1);

	        this.cargosImportados = new ArrayList<ICargoFaltante>();
	        
	        for (List<String> fila : grilla) {
	        	ICargoFaltante cargo = this.gestorCargosFaltantes.getICargoFaltante();
	        	cargo.setLegajo(Integer.parseInt(fila.get(COL_LEGAJO)));
	        	cargo.setApellido(fila.get(COL_APELLIDO));
	        	cargo.setNombre(fila.get(COL_NOMBRE));
	        	cargo.setCodigoCargo(Integer.parseInt(fila.get(COL_CODIGO_CARGO)));
	        	cargo.setUltimoCosto(Utilidades.stringToFloat(fila.get(COL_COSTO)));
	        	cargo.setFechaUltimoCosto(LocalDate.now());
	        	this.cargosImportados.add(cargo);
	        }
	        
	        return new EstadoOperacion(EstadoOperacion.CodigoEstado.OP_OK,
	        		"La importación del costeo se ha realizado con éxito.");
		} catch (InvalidFormatException e) {
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
	 * Listar comparación de costos actualizados con anteriores
	 */
	public List<FilaCostoComparar> listarComparacion() {
		if (this.cargosImportados == null || this.cargosImportados.isEmpty()) {
			return null;
		}
		List<FilaCostoComparar> listaComparar = new ArrayList<FilaCostoComparar>();
		
		for (ICargoFaltante cargoActual : this.cargosImportados) {
			ICargoDocente cargoAnterior = getCargo(cargoActual.getCodigoCargo());
			if (cargoAnterior != null) {
				FilaCostoComparar fila = new FilaCostoComparar(cargoAnterior, cargoActual);
				listaComparar.add(fila);
			}
		}
		
		return listaComparar;
	}
	
	/**
	 * Guarda los cambios realizados, actualizando los costos de los cargos y sus estados.
	 */
	public EstadoOperacion guardar() {
		// No hay datos para importar
		if (this.cargosImportados == null || this.cargosImportados.isEmpty()) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
					"No se han importado datos o no hay datos para actualizar.");
		}
		EstadoOperacion eo = guardarEstado();
		switch (eo.getEstado()) {
		case UPDATE_OK:
			EstadoOperacion eo2 = guardarCostos();
			switch (eo2.getEstado()) {
			case UPDATE_OK:
				EstadoOperacion eo3 = guardarFaltantes();
				return eo3;
			default:
				return eo2;
			}
		default:
			return eo;
		}
	}
	
	/**
	 * Actualiza el Estado de los CargosDocentes en la Base de Datos
	 * @return
	 */
	private EstadoOperacion guardarEstado() {
		EstadoOperacion eo = new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
				"Ha ocurrido un error al intentar actualizar los estados de los cargos.");
		try {
			// Actualizar los estados
			for (ICargoDocente cargoFaltanteSistema : this.faltantesCosteo) {
				// Recuperar el cargo actual y fijarse si cambió
				ICargoDocente cargoActual = getCargo(cargoFaltanteSistema.getId());
				if (cargoActual != null) {
					// El estado del cargo actual es distinto al anterior
					if (cargoActual.getEstado().getId() != cargoFaltanteSistema.getEstado().getId()) {
						cargoActual.setEstado(cargoFaltanteSistema.getEstado().clone());
						EstadoOperacion eo2 = this.gestorDocente.modificarCargoDocente(null, cargoActual);
						switch (eo2.getEstado()) {
						case UPDATE_OK:
							this.faltantesCosteo.remove(cargoFaltanteSistema);
							this.faltantesCosteo.add(cargoActual);
							NotificacionCargo2.getInstance().notificar(
									cargoActual.getDocente(), cargoActual, eo2);
							break;
						default:
							throw new Exception(eo2.getMensaje());
						}
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
	 * Actualiza los costos en la Base de Datos
	 * @return
	 */
	private EstadoOperacion guardarCostos() {
		EstadoOperacion eo = new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR,
				"Ha ocurrido un error al intentar actualizar el último costo de los cargos.");
		try {
			// Actualizar los costos
			for (ICargoFaltante cargoImportado : this.cargosImportados) {
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
				"Ha ocurrido un error al intentar guardar los cargos faltantes.");
		try {
			for (ICargoFaltante faltanteSistema : this.faltantesSistema) {
				this.gestorCargosFaltantes.agregarCargoFaltante(faltanteSistema);
			}
			for (ICargoDocente faltanteCosteo : this.faltantesCosteo) {
				ICargoFaltante faltante = this.gestorCargosFaltantes.getICargoFaltante();
				faltante.setLegajo(faltanteCosteo.getDocente().getLegajo());
				faltante.setApellido(faltanteCosteo.getDocente().getPersona().getApellido());
				faltante.setNombre(faltanteCosteo.getDocente().getPersona().getNombre());
				faltante.setCodigoCargo(faltanteCosteo.getId());
				faltante.setUltimoCosto(faltanteCosteo.getUltimoCosto());
				faltante.setFechaUltimoCosto(faltanteCosteo.getFechaUltCost());
				faltante.setTipo(CargoFaltante.FALTA_COSTEO);
				this.gestorCargosFaltantes.agregarCargoFaltante(faltante);
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
	 * Actualiza las listas, cargando los cargos faltantes tanto en el sistema
	 * como en la planilla importada
	 */
	private void actualizarListas() {
		this.faltantesSistema = new ArrayList<ICargoFaltante>();
		this.faltantesCosteo = new ArrayList<ICargoDocente>();
		if (this.cargosImportados != null && !this.cargosImportados.isEmpty()) {
			// Recuperar todos los cargos del sistema, para luego agregarlos
			// a los faltantes del costeo
			List<ICargoDocente> cargosSistema = this.gestorDocente.listarCargo(null, null);
			
			for (ICargoFaltante cargoImportado : this.cargosImportados) {
				ICargoDocente cargoActual = buscarCargo(
						cargoImportado.getCodigoCargo(), cargosSistema);
				
				// El cargo no está en el sistema, o está pero no activo
				if (cargoActual == null || cargoActual.getEstado().getId() != 0) {
					this.faltantesSistema.add(cargoImportado);
					
					// Sacarlo de los faltantes en el Costeo si está pero no activo
					if (cargoActual != null) 
						cargosSistema.remove(cargoActual);
					
				// El cargo está activo en el sistema y en el costeo,
				// por lo que no hará falta agregarlo a los faltantes del costeo 
				} else {
					cargosSistema.remove(cargoActual);
				}
			}	
			// Agregar a los faltantes del costeo los que están activos 
			// en el sistema y no aparecen en el costeo
			for (ICargoDocente cargo : cargosSistema) { 
				if (cargo.getEstado().getId() == 0) {
					this.faltantesCosteo.add(cargo);
				}
			}
		}
	}
	
	/**
	 * Método auxiliar que busca un CargoDocente en una lista de CargosFaltantes
	 * @return 
	 */
	private ICargoDocente buscarCargo(int codigoCargo, List<ICargoDocente> lista) {
		ICargoDocente encontrado = null;
		int i = 0;
		while (encontrado == null && i < lista.size()) {
			if (lista.get(i).getId() == codigoCargo)
				encontrado = lista.get(i);
			i++;
		}
		return encontrado;
	}

	/**
	 * Modifica el Estado del CargoDocente en memoria
	 * @param cargo El CargoDocente a modificar
	 * @param estado El nuevo Estado del CargoDocente 
	 */
	public void modificarEstado(ICargoDocente cargo, EstadoCargo estado) {
		ICargoDocente cargoActualizar = buscarCargo(cargo.getId(), this.faltantesCosteo);
		cargoActualizar.setEstado(estado);
	}
	
	/**
	 * Chequear cómo manejar el alta de un cargo, dependiendo de la situación:
	 * <ul>
	 * 	<li><p>El cargo está inactivo en el sistema -> ESTADO</p></li>
	 * 	<li><p>El cargo no existe pero el docente sí -> CARGO</p></li>
	 * 	<li><p>El docente no existe -> DOCENTE</p></li>
	 * </ul>
	 */
	public TipoAlta getTipoAlta(ICargoFaltante cargo) {
		if (getCargo(cargo.getCodigoCargo()) != null) return TipoAlta.ESTADO;
		
		IDocente docente = this.gestorDocente.getIDocente();
		docente.setLegajo(cargo.getLegajo());
		if (GestorDocente.existeDocente(docente)) return TipoAlta.CARGO;
		
		return TipoAlta.DOCENTE;
	}

	/**
	 * Método auxiliar para obtener un CargoDocente de la Base de Datos.
	 * @param codigo El código del cargo.
	 * @return El CargoDocente correspondiente.
	 */
	private ICargoDocente getCargo(int codigo) {
		ICargoDocente cargo = null;
		
		ICargoDocente cargoSelect = this.gestorDocente.getICargoDocente();
		cargoSelect.setId(codigo);
		List<ICargoDocente> listSelect = this.gestorDocente.listarCargo(null, cargoSelect);
		
		if (listSelect != null && !listSelect.isEmpty())
			cargo = listSelect.get(0);
		
		return cargo;
	}


}
