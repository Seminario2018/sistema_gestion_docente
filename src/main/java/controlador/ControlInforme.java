package controlador;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import excel.Excel;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.informe.ColumnaInforme;
import modelo.informe.FiltroColumna;
import modelo.informe.GestorInforme;
import modelo.informe.ITipoInforme;
import utilidades.Utilidades;
import vista.controladores.ConfigInf.FormatoFecha;
import vista.controladores.ConfigInf.FormatoHora;
import vista.controladores.ConfigInf.SeparadorArchivo;
import vista.controladores.ControladorVista;

public class ControlInforme {

	public static final String ARCHIVO_CONFIGURACION_INFORMES = "Informes.xml";
	public static final String XML_INFORMES_DIRECTORIO = "directorio";
	public static final String XML_INFORMES_FORMATO_FECHA = "formato_fecha";
	public static final String XML_INFORMES_SEPARADOR_FECHA = "separador_fecha";
	public static final String XML_INFORMES_FORMATO_HORA = "formato_hora";
	public static final String XML_INFORMES_SEPARADOR_HORA = "separador_hora";
	public static final String INFORMES_SEPARADOR = "_";

	private GestorInforme gestorInforme = new GestorInforme();
	private ControladorVista vista;

	public ControlInforme(ControladorVista vista) {
		this.vista = vista;
	}

	public EstadoOperacion nuevoInforme(ITipoInforme informe) {
		return this.gestorInforme.nuevoInforme(informe);
	}

	public EstadoOperacion modificarInforme(ITipoInforme informe) {
		return this.gestorInforme.nuevoInforme(informe);
	}

	public EstadoOperacion eliminarInforme(ITipoInforme informe) {
		return this.gestorInforme.eliminarInforme(informe);
	}

	public ArrayList<ITipoInforme> listarInforme(ITipoInforme informe) {
		return this.gestorInforme.listarInforme(informe);
	}

	public void setInformeActual(ITipoInforme informeActual) {
		this.gestorInforme.setInformeActual(informeActual);
	}

	public ITipoInforme getInformeActual() {
		return this.gestorInforme.getInformeActual();
	}

	public List<List<String>> vistaPrevia() {
		return this.gestorInforme.vistaPrevia();
	}

	public void guardar(ITipoInforme informe) {
		String titulo = "Guardar Informe";
		String encabezado = "Error al guardar";
		String contenido = "Debe ingresar un nombre para el Informe.";

		String nombre = informe.getNombre();
		if (nombre == null || "".equals(nombre)) {
			this.vista.alertaError(titulo, encabezado, contenido);
			return;
		}

		this.gestorInforme.setInformeActual(informe);
		EstadoOperacion eo = this.gestorInforme.guardar();
		switch (eo.getEstado()) {
		case INSERT_OK:
		case UPDATE_OK:
			this.vista.mensajeEstado(eo.getMensaje());
			break;
		default:
			this.vista.alertaError("Guardar Informe", "Error al guardar el Informe", eo.getMensaje());
		}
	}

	public void guardarComo(ITipoInforme informe) {
		String titulo = "Guardar Informe";
		String encabezado = "Error al guardar";
		String contenido = "Ya existe un Informe con el nombre \"" + informe.getNombre() + "\".";

		ITipoInforme informeActual = getInformeActual();
		if (informeActual.getNombre().equals(informe.getNombre())) {
			this.vista.alertaError(titulo, encabezado, contenido);
			return;
		}

		informe.setId(-1);
		guardar(informe);
	}

	/**
	 * Obtiene el archivo donde se exportará a Excel a partir de la plantilla
	 * "Informes.xml"
	 * 
	 * @return El archivo a exportar
	 */
	public EstadoOperacion getFile(String nombreInforme, String nombreUsuario) {
		EstadoOperacion resultado = new EstadoOperacion(CodigoEstado.OP_ERROR,
				"Ha ocurrido un error al obtener el nombre del archivo.");

		Document xml = null;

		try {
			xml = Utilidades.leerXML(new File(ARCHIVO_CONFIGURACION_INFORMES));
		} catch (Exception e) {
			resultado = new EstadoOperacion(CodigoEstado.OP_ERROR,
					"El archivo de configuración de informes no existe o está dañado.");
		}

		if (xml != null) {
			try {
				String path = "";
				// Directorio raíz
				path += xml.getElementsByTagName(XML_INFORMES_DIRECTORIO).item(0).getTextContent();
				if (!(new File(path).exists())) {
					return new EstadoOperacion(CodigoEstado.OP_ERROR,
							"La ruta al archivo no existe. Verifique la Configuración de Informes.");
				}

				// Usuario
				if (nombreUsuario != null && !"".equals(nombreUsuario))
					path += nombreUsuario;

				// Informe
				if (nombreInforme != null && !"".equals(nombreInforme))
					path += INFORMES_SEPARADOR + nombreInforme;

				// Fecha y hora
				FormatoFecha ff = FormatoFecha
						.valueOf(xml.getElementsByTagName(XML_INFORMES_FORMATO_FECHA).item(0).getTextContent());

				FormatoHora fh = FormatoHora
						.valueOf(xml.getElementsByTagName(XML_INFORMES_FORMATO_HORA).item(0).getTextContent());

				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(ff.getFormato());
				DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern(fh.getFormato());

				String fecha = now.format(formatoFecha);
				SeparadorArchivo sepFecha = SeparadorArchivo.valueOf(
						xml.getElementsByTagName(XML_INFORMES_SEPARADOR_FECHA).item(0).getTextContent());
				fecha = fecha.replace(" ", sepFecha.getValor());

				String hora = now.format(formatoHora);
				SeparadorArchivo sepHora = SeparadorArchivo.valueOf(
						xml.getElementsByTagName(XML_INFORMES_SEPARADOR_HORA).item(0).getTextContent());
				hora = hora.replace(" ", sepHora.getValor());

				path += INFORMES_SEPARADOR + fecha + INFORMES_SEPARADOR + hora;

				resultado = new EstadoOperacion(CodigoEstado.OP_OK,
						"La configuración se ha cargado con éxito.",
						new File(path + ".xls"));
				
			} catch (Exception e) {
				resultado = new EstadoOperacion(CodigoEstado.OP_ERROR,
						"El archivo de configuración de informes está dañado o tiene un formato incorrecto.");
			}
		}
		return resultado;
	}

	public void exportar(File archivo) {
		if (archivo != null) {
			ITipoInforme informeActual = getInformeActual();
			List<String> encabezados = new ArrayList<String>();
			String subtitulo = "Usuario: " + this.vista.getUsuario().getUser() + " - Filtros: ";
			
			for (ColumnaInforme columna : informeActual.getColumnas()) {
				if (columna.isVisible())
					encabezados.add(columna.getNombre());
				if (columna.getFiltros() != null && !columna.getFiltros().isEmpty()) {
					subtitulo += columna.getNombre() + " " + columna.stringFiltrosUI() + " - ";
				}
			}
			
			if (Excel.exportar(archivo.getPath(),
					informeActual.getNombre(),
					subtitulo,
					encabezados, vistaPrevia())) {
				this.vista.mensajeEstado("El informe se exportó correctamente en " + archivo.getPath());
			} else {
				this.vista.alertaError("Exportar a Excel",
						"Error al exportar el Informe a Excel (" + archivo.getPath() + ")",
						"Asegúrese de que el archivo no fue abierto por otra aplicación.");
			}
		}
	}

	public void actualizarColumna(ColumnaInforme columna) {
		this.gestorInforme.actualizarColumna(columna);
	}

	public void swapColumna(int a, int b) {
		this.gestorInforme.swapColumna(a, b);
	}
}