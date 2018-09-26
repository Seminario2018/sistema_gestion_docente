package mail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import modelo.auxiliares.EstadoCargo;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IContacto;
import modelo.usuario.IUsuario;
import utilidades.Plantilla;
import utilidades.Utilidades;

public class NotificacionCargo2 {

    private static final String ARCHIVO_CONFIG = "Mail.xml";

    private int milisegundos;
    private boolean habilitado;

	private static NotificacionCargo2 noti;

    private List<Mensaje> mails = new ArrayList<Mensaje>();
//    public static final String KEY_DESTINO = "destino";
//    public static final String KEY_ASUNTO = "asunto";
//    public static final String KEY_MENSAJE = "mensaje";
//
//    public static final String KEY_RESULTADO = "resultado";
//    public static final String KEY_USUARIO = "usuario";
//    public static final String KEY_EMISOR = "emisor";
//    public static final String KEY_LEGAJO = "legajo";
//    public static final String KEY_CARGO = "cargo";
//    public static final String KEY_ESTANTERIOR = "estanterior";
//    public static final String KEY_ESTNUEVO = "estnuevo";

    private NotificacionCargo2() {
        // Importo la configuración desde el xml:
        Document xml = Utilidades.leerXML(new File(ARCHIVO_CONFIG));
        // Intervalo de envío de mails en segundos:
        milisegundos = Integer.parseInt(
            xml.getElementsByTagName("intervalo").item(0).getTextContent())
            * 1000;
        // Si los mails están habilitados:
        habilitado = Boolean.valueOf(
            xml.getElementsByTagName("habilitado").item(0).getTextContent());
    }

    public static NotificacionCargo2 getInstance() {
    	if (noti == null) {
    		noti = new NotificacionCargo2();

    	}
    	return noti;
    }

    private Thread threadMail;

    /** Thread que envía mails después de {@code ThreadMail.MILISEGUNDOS}. */
    class ThreadMail implements Runnable {
//        private static final int MILISEGUNDOS = 60000;
        private int MILISEGUNDOS;
    	private NotificacionCargo2 noti;

    	public ThreadMail(NotificacionCargo2 noti) {
    		this.noti = noti;
    		MILISEGUNDOS = milisegundos;

    		// Importar intervalo desde xml:
//    		Document xml = Utilidades.leerXML(new File("Mail.xml"));
//    		String intervalo = xml.getElementsByTagName("intervalo").item(0).getTextContent();
//    		MILISEGUNDOS = Integer.parseInt(intervalo) * 1000;
    	}

		@Override
		public void run() {
			System.out.println("Se prepararon mails para enviar."
					+ " Se enviarán después de " + (MILISEGUNDOS / 1000) + " segundos.");
			try {
				Thread.sleep(MILISEGUNDOS);
    			List<Mensaje> mails = this.noti.getMails();
    			if (mails != null && !mails.isEmpty()) {
    				IMail mailSend = new Mail();
    				for (Mensaje mail : mails) {
    					System.out.println("Enviando un mail");
    					boolean exito = mailSend.enviarEmail(
							mail.getDestino(),
							mail.getAsunto(),
							mail.getMensaje()
						);

    					mail.setResultado((exito)? "Enviado" : "Falló");
    					mail.setEmisor(mailSend.getUsuario());
    					loggearMail(mail);
    				}
    				System.out.println("Se han enviado todos los mails");
    				this.noti.setMails(new ArrayList<Mensaje>());
    			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

        private void loggearMail(Mensaje mail) {
            StringBuilder sb = new StringBuilder();

            sb.append("[");
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            sb.append("] [");
            sb.append(mail.getResultado());
            sb.append("] - Usuario: ");
            sb.append(mail.getUsuario());
            sb.append(" - Emisor: ");
            sb.append(mail.getEmisor());
            sb.append(" - Destino: ");
            sb.append(mail.getDestino());
            int i = 1;
            for (Mensaje.Item item : mail.getItems()) {
                sb.append(" - [Datos del cargo " + i + "] Legajo: ");
                sb.append(item.getLegajo());
                sb.append(" - Código Cargo: ");
                sb.append(item.getCargo());
                sb.append(" - Estado: ");
                sb.append(item.getEstadoAnterior());
                sb.append(" -> ");
                sb.append(item.getEstadoNuevo());
                i++;
            }

            System.out.println("Log:" + sb.toString());
            Utilidades.guardarTexto(
                new File("logMail.txt"), sb.toString());
        }
    }

    public void setMails(List<Mensaje> mails) {
    	this.mails = mails;
    }

    public List<Mensaje> getMails() {
    	return this.mails;
    }

    public void setThreadMail(Thread threadMail) {
    	this.threadMail = threadMail;
    }

    /**
     * Genera un mail con el resultado de la actualización de un cargo,
     * indicando el alta, baja o modificación del mismo, y almacenándolo en
     * {@code mails}. Si los mails están deshabilitados esta función no surte
     * efecto.
     * @param estadoOperacion el resultado de la operación.
     */
    public void notificar(
        IUsuario usuario,
        IDocente docente,
        ICargoDocente cargo,
        EstadoCargo estAnterior,
        EstadoCargo estNuevo,
        EstadoOperacion estado) {

    	if (habilitado) {
            String destinos = null;
            try {
                List<IContacto> contactos = cargo.getArea().getDivision().getJefe().getPersona().getContactos();
                List<String> mails = new ArrayList<String>();
                for (IContacto contacto : contactos) {
                    if (contacto.getTipo().getDescripcion().equals("Mail Laboral")) {
                        mails.add(contacto.getDato());
                    }
                }
                destinos = Utilidades.joinString(mails, ",");

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (destinos == null || destinos.equals("")) {
                String error = "No se pudo enviar el mail porque el Jefe de la División " + cargo.getArea().getDivision().getDescripcion() + " no tiene Mail Laboral.";

                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                sb.append("] [Falló] ");
                sb.append(error);
                sb.append(" - Usuario: ");
                sb.append(usuario.getUser());
                sb.append(" - [Datos del cargo] Legajo: ");
                sb.append(cargo.getDocente().getLegajo());
                sb.append(" - Código Cargo: ");
                sb.append(cargo.getId());

                System.out.println("Log: " + sb.toString());

                Utilidades.guardarTexto(new File("logMail.txt"), sb.toString());

                throw new IllegalArgumentException(error);
            }
            // Averiguar si hay que actualizar un mail o crear uno nuevo
            boolean mailNuevo = true;
            Mensaje mailActual = null;
            for (Mensaje mail : this.mails) {
                if (mail.getDestino().equals(destinos)) {
                    mailActual = mail;
                    mailNuevo = false;
                }
            }
            // Parámetros del mensaje:
            Map<String, String> parametros = armarParametrosPlantilla(docente, cargo, estado);
            // Obtengo la plantilla para el asunto y el mensaje:
            Document plantillaXML = Utilidades.leerXML(new File("Plantilla.xml"));
            String asunto;
            String mensaje;
            // Si se crea un nuevo mail, agregar asunto y saludo
            if (mailActual == null) {
                mailActual = new Mensaje();
                asunto = armarTextoPlantilla("asunto", plantillaXML, parametros);
                mensaje = armarTextoPlantilla("encabezado", plantillaXML, parametros);

            } else {
                asunto = mailActual.getAsunto();
                mensaje = mailActual.getMensaje();
            }
            mensaje = mensaje + armarTextoPlantilla("mensaje", plantillaXML, parametros);
            // Guardar el mail para enviar
            mailActual.setAsunto(asunto);
            mailActual.setDestino(destinos);
            mailActual.setMensaje(mensaje);
            mailActual.setUsuario(usuario.getUser());
            mailActual.addItem(String.valueOf(docente.getLegajo()), String.valueOf(cargo.getId()), estAnterior.getDescripcion(), estNuevo.getDescripcion());
            if (mailNuevo) {
                this.mails.add(mailActual);
            }
            System.out.println("Se agregó el mail a la lista");
            if (this.threadMail == null || this.threadMail.getState() == Thread.State.TERMINATED) {
                /* Compruebo si el hilo de mail no se inicio o si ya
                terminó su ejecución. */
                this.threadMail = new Thread(new ThreadMail(this));
                threadMail.start();
            }
        }
    }

    /**
     * Arma el mapa con los parámetros para después pasar a la plantilla
     * @param docente Docente
     * @param cargo CargoDocente
     * @return Mapa con los parámetros
     */
    public static Map<String, String> armarParametrosPlantilla(IDocente docente, ICargoDocente cargo, EstadoOperacion estado) {
        Map<String, String> parametros = new HashMap<>();

        // Datos docente:
        parametros.put("DOCUMENTO", String.valueOf(docente.getPersona().getNroDocumento()));
        parametros.put("LEGAJO", String.valueOf(docente.getLegajo()));
        parametros.put("APELLIDO", docente.getPersona().getApellido());
        parametros.put("NOMBRE", docente.getPersona().getNombre());

        // Datos cargo:

        parametros.put("APELLIDOJEFE", cargo.getArea().getDivision().getJefe().getPersona().getApellido());
        parametros.put("DIVISION", cargo.getArea().getDivision().getDescripcion());
        parametros.put("AREA", cargo.getArea().getDescripcion());
        parametros.put("CARGO", cargo.getCargo().getDescripcion());
        parametros.put("TIPOCARGO", cargo.getTipoCargo().getDescripcion());
        parametros.put("ESTADO", cargo.getEstado().getDescripcion());
        parametros.put("DISPOSICION", cargo.getDisposicion());

        if (cargo.getDispDesde() == null) {
            parametros.put("DISPOSICIONDESDE", "");
        } else {
            parametros.put("DISPOSICIONDESDE", cargo.getDispDesde().toString());
        }
        if (cargo.getDispHasta() == null) {
            parametros.put("DISPOSICIONHASTA", "");
        } else {
            parametros.put("DISPOSICIONHASTA", cargo.getDispHasta().toString());
        }

        parametros.put("RESOLUCION", cargo.getResolucion());

        if (cargo.getResDesde() == null) {
            parametros.put("RESOLUCIONDESDE", "");
        } else {
            parametros.put("RESOLUCIONDESDE", cargo.getResDesde().toString());
        }
        if (cargo.getResHasta() == null) {
            parametros.put("RESOLUCIONHASTA", "");
        } else {
            parametros.put("RESOLUCIONHASTA", cargo.getResHasta().toString());
        }

        parametros.put("ULTIMOCOSTO", String.valueOf(cargo.getUltimoCosto()));

        if (cargo.getFechaUltCost() == null) {
            parametros.put("ULTIMOCOSTOFECHA", "");
        } else {
            parametros.put("ULTIMOCOSTOFECHA", cargo.getFechaUltCost().toString());
        }

        String operacionMensaje;
        switch (estado.getEstado()) {
            case INSERT_OK:
                operacionMensaje = "añadió";
                break;
            case DELETE_OK:
                operacionMensaje = "eliminó";
                break;
            default:
                operacionMensaje = "actualizó";
        }
        parametros.put("OPERACION", operacionMensaje);

        return parametros;
    }

    /**
     * Arma un texto en base a la plantilla seleccionada y a los parámetros del
     * mensaje a enviar.
     * @param tag Plantilla a usar
     * @param plantillaXML Plantillas a usar
     * @param parametros Parámetros del mensaje
     * @return
     */
    public static String armarTextoPlantilla(String tag, Document plantillaXML, Map<String, String> parametros) {
        return Plantilla.armar(
            plantillaXML
                .getElementsByTagName(tag)
                .item(0)
                .getTextContent(),
            parametros);
    }
}
