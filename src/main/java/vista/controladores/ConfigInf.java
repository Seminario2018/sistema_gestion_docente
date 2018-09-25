package vista.controladores;

import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.w3c.dom.Document;
import controlador.ControlInforme;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import utilidades.Utilidades;

/**
 * @author Martín Tomás Juran
 * @version 1.0, 15 de set. de 2018
 */
public class ConfigInf extends ControladorVista implements Initializable {

	public static final String TITULO = "ConfigInf";
	
	public static final String ARCHIVO_CONFIGURACION_INFORMES = ControlInforme.ARCHIVO_CONFIGURACION_INFORMES;
	public static final String XML_INFORMES_DIRECTORIO = ControlInforme.XML_INFORMES_DIRECTORIO;
	public static final String XML_INFORMES_FORMATO_FECHA = ControlInforme.XML_INFORMES_FORMATO_FECHA;
	public static final String XML_INFORMES_SEPARADOR_FECHA = ControlInforme.XML_INFORMES_SEPARADOR_FECHA;
	public static final String XML_INFORMES_FORMATO_HORA = ControlInforme.XML_INFORMES_FORMATO_HORA;
	public static final String XML_INFORMES_SEPARADOR_HORA = ControlInforme.XML_INFORMES_SEPARADOR_HORA;
	public static final String INFORMES_SEPARADOR = ControlInforme.INFORMES_SEPARADOR;
	
	public enum FormatoFecha {
		aaaa_MM_dd("aaaa/MM/dd", "yyyy MM dd"),
		dd_MM_aaaa("dd/MM/aaaa", "dd MM yyyy"),
		dd_MM_aa("dd/MM/aa", "dd MM yy"),
		d_M_aa("d/M/aa", "d M yy");
		
		private String descripcion;
		private String formato;
		
		private FormatoFecha(String descripcion, String formato) {
			this.descripcion = descripcion;
			this.formato = formato;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getFormato() {
			return formato;
		}
		public void setFormato(String formato) {
			this.formato = formato;
		}
		
		@Override
		public String toString() {
			return this.descripcion;
		}
	}
	
	public enum FormatoHora {
		HH_mm_ss("HH:mm:ss", "HH mm ss"),
		H_mm_ss("H:mm:ss", "H mm ss");
		
		private String descripcion;
		private String formato;
		
		private FormatoHora(String descripcion, String formato) {
			this.descripcion = descripcion;
			this.formato = formato;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getFormato() {
			return formato;
		}
		public void setFormato(String formato) {
			this.formato = formato;
		}
		
		@Override
		public String toString() {
			return this.descripcion;
		}
	}
	
	public enum SeparadorArchivo {
		GUION("Guión (-)", "-"),
		GUION_BAJO("Guión bajo (_)", "_"),
		PUNTO("Punto (.)", "."),
		COMA("Coma (,)", ","),
		PUNTO_COMA("Punto y coma (;)", ";");
		
		private String valor;
		private String descripcion;
		private SeparadorArchivo(String descripcion, String valor) {
			this.descripcion = descripcion;
			this.valor = valor;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public String getValor() {
			return valor;
		}
		public void setValor(String valor) {
			this.valor = valor;
		}
		
		@Override
		public String toString() {
			return this.descripcion;
		}
	}
	
	
	@Override
	public void inicializar() {
		this.window.setTitle("Configuración de informes");
		
		this.cmbFechaFor.setItems(FXCollections.observableArrayList(FormatoFecha.values()));
		this.cmbHoraFor.setItems(FXCollections.observableArrayList(FormatoHora.values()));
		this.cmbFechaSep.setItems(FXCollections.observableArrayList(SeparadorArchivo.values()));
		this.cmbHoraSep.setItems(FXCollections.observableArrayList(SeparadorArchivo.values()));
		
		cargarConfiguracion();
		actualizarEjemplo();
		
		this.cmbFechaFor.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldSel, newSel) -> {
					if (oldSel != newSel)
						actualizarEjemplo();
				});
		this.cmbHoraFor.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldSel, newSel) -> {
					if (oldSel != newSel)
						actualizarEjemplo();
				});
		this.cmbFechaSep.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldSel, newSel) -> {
					if (oldSel != newSel)
						actualizarEjemplo();
				});
		this.cmbHoraSep.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldSel, newSel) -> {
					if (oldSel != newSel)
						actualizarEjemplo();
				});
	}
	
	/**
	 * Lee del archivo de configuración y carga la vista con los datos correspondientes.
	 */
	private void cargarConfiguracion() {
		try {
			Document xml = Utilidades.leerXML(new File(ARCHIVO_CONFIGURACION_INFORMES));
			
			// Directorio
			String path = xml.getElementsByTagName(XML_INFORMES_DIRECTORIO).item(0).getTextContent();
			if (!(new File(path).exists())) {
				throw new FileNotFoundException();
			} else {
				this.txtDirectorio.setText(path);
			}

			// Formato Fecha
			FormatoFecha ff = FormatoFecha
					.valueOf(xml.getElementsByTagName(XML_INFORMES_FORMATO_FECHA).item(0).getTextContent());
			this.cmbFechaFor.getSelectionModel().select(ff);

			// Formato Hora
			FormatoHora fh = FormatoHora
					.valueOf(xml.getElementsByTagName(XML_INFORMES_FORMATO_HORA).item(0).getTextContent());
			this.cmbHoraFor.getSelectionModel().select(fh);

			// Separador Fecha
			SeparadorArchivo sepFecha = SeparadorArchivo.valueOf(
					xml.getElementsByTagName(XML_INFORMES_SEPARADOR_FECHA).item(0).getTextContent());
			this.cmbFechaSep.getSelectionModel().select(sepFecha);

			// Separador Hora
			SeparadorArchivo sepHora = SeparadorArchivo.valueOf(
					xml.getElementsByTagName(XML_INFORMES_SEPARADOR_HORA).item(0).getTextContent());
			this.cmbHoraSep.getSelectionModel().select(sepHora);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.alertaError("Configuración de Informes",
					"Hubo un error al intentar cargar el Archivo de Configuración de Informes \""
							+ ARCHIVO_CONFIGURACION_INFORMES + "\".",
							"Se cargará la configuración por defecto.");
			this.txtDirectorio.setText(System.getProperty("user.dir"));
			this.cmbFechaFor.getSelectionModel().selectFirst();
			this.cmbFechaSep.getSelectionModel().selectFirst();
			this.cmbHoraFor.getSelectionModel().selectFirst();
			this.cmbHoraSep.getSelectionModel().selectFirst();
		}
	}
	
	/**
	 * Pone en el ejemplo un nombre de informe de prueba.
	 */
	private void actualizarEjemplo() {
		String nombre = "Usuario1" + INFORMES_SEPARADOR + "Informe1";
		
		FormatoFecha ff = this.cmbFechaFor.getValue();
		FormatoHora fh = this.cmbHoraFor.getValue();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(ff.getFormato());
		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern(fh.getFormato());

		String fecha = now.format(formatoFecha);
		SeparadorArchivo sepFecha = this.cmbFechaSep.getValue();
		fecha = fecha.replace(" ", sepFecha.getValor());

		String hora = now.format(formatoHora);
		SeparadorArchivo sepHora = this.cmbHoraSep.getValue();
		hora = hora.replace(" ", sepHora.getValor());
		
		nombre += INFORMES_SEPARADOR + fecha;
		nombre += INFORMES_SEPARADOR + hora;
		
		this.txtNombre.setText(nombre + ".xls");
	}
	
	
	
	@FXML private TextField txtDirectorio;
	@FXML private Button btnBuscar;
	@FXML void buscarDirectorio(ActionEvent event) {
		File ruta = this.elegirDirectorio("Elija un directorio por defecto donde se guardarán los informes");
		if (ruta != null && ruta.exists()) {
			this.txtDirectorio.setText(ruta.getPath() + File.separator);
		}
	}

	@FXML private ComboBox<FormatoFecha> cmbFechaFor;
	@FXML private ComboBox<SeparadorArchivo> cmbFechaSep;

	@FXML private ComboBox<FormatoHora> cmbHoraFor;
	@FXML private ComboBox<SeparadorArchivo> cmbHoraSep;

	@FXML private TextField txtNombre;

	@FXML private Button btnAceptar;
	/**
	 * Guardar la nueva configuración y cerrar la pantalla
	 */
	@FXML void aceptar(ActionEvent event) {
		String titulo = "Configuración de Informes";
		String encabezado = "Hubo un error al guardar la nueva configuración.";
		
		try {
			Document xml = Utilidades.crearXMLInformes();
			
			// Directorio
			String path = this.txtDirectorio.getText();
			if (!path.endsWith(File.separator))
				path += File.separator;
			if (!(new File(path).exists())) {
				this.alertaError(titulo, encabezado, "El directorio seleccionado no es correcto");
				return;
			}
			
			xml.getElementsByTagName(XML_INFORMES_DIRECTORIO).item(0).setTextContent(path);
			
			xml.getElementsByTagName(XML_INFORMES_FORMATO_FECHA).item(0)
					.setTextContent(this.cmbFechaFor.getValue().name());
			
			xml.getElementsByTagName(XML_INFORMES_SEPARADOR_FECHA).item(0)
					.setTextContent(this.cmbFechaSep.getValue().name());
			
			xml.getElementsByTagName(XML_INFORMES_FORMATO_HORA).item(0)
					.setTextContent(this.cmbHoraFor.getValue().name());
			
			xml.getElementsByTagName(XML_INFORMES_SEPARADOR_HORA).item(0)
					.setTextContent(this.cmbHoraSep.getValue().name());
			
			Utilidades.guardarXML(new File(ARCHIVO_CONFIGURACION_INFORMES), xml);
			this.mensajeEstado("La configuración de informes se guardó exitosamente");
			this.gestorPantalla.cerrarPantalla(TITULO);
		} catch (Exception e) {
			this.alertaError(titulo, encabezado, "No se pudo crear el archivo "
					+ ARCHIVO_CONFIGURACION_INFORMES + ".\r\n"
					+ "Verifique que tenga permisos para crear/modificar archivos.");
		}
		
		
	}
	
	@FXML private Button btnCancelar;
	@FXML void cancelar(ActionEvent event) {
		this.gestorPantalla.cerrarPantalla(TITULO);
	}
}
