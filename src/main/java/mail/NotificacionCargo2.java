package mail;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import modelo.auxiliares.EstadoOperacion;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IContacto;
import utilidades.Plantilla;
import utilidades.Utilidades;

public class NotificacionCargo2 {

	private static NotificacionCargo2 noti;

    private List<Map<String, String>> mails = new ArrayList<Map<String, String>>();
    public static final String KEY_DESTINO = "destino";
    public static final String KEY_ASUNTO = "asunto";
    public static final String KEY_MENSAJE = "mensaje";

    private NotificacionCargo2() {
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
        private static final int MILISEGUNDOS = 60000;
    	private NotificacionCargo2 noti;
    	public ThreadMail(NotificacionCargo2 noti) {
    		this.noti = noti;
    	}
		@Override
		public void run() {
			System.out.println("Se prepararon mails para enviar."
					+ " Se enviarán después de " + (MILISEGUNDOS / 1000) + " segundos.");
			try {
				Thread.sleep(MILISEGUNDOS);
    			List<Map<String, String>> mails = this.noti.getMails();
    			if (mails != null && !mails.isEmpty()) {
    				IMail mailSend = new Mail();
    				for (Map<String, String> mail : mails) {
    					System.out.println("Enviando un mail");
    					mailSend.enviarEmail(
							mail.get(NotificacionCargo2.KEY_DESTINO),
							mail.get(NotificacionCargo2.KEY_ASUNTO),
							mail.get(NotificacionCargo2.KEY_MENSAJE)
						);
    				}
    				System.out.println("Se han enviado todos los mails");
    				this.noti.setMails(new ArrayList<Map<String, String>>());
    			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

    public void setMails(List<Map<String, String>> mails) {
    	this.mails = mails;
    }

    public List<Map<String, String>> getMails() {
    	return this.mails;
    }

    public void setThreadMail(Thread threadMail) {
    	this.threadMail = threadMail;
    }

    /**
     * Genera un mail con el resultado de la actualización de un cargo,
     * indicando el alta, baja o modificación del mismo, y almacenándolo en
     * {@code mails}.
     * @param estadoOperacion el resultado de la operación.
     */
    public void notificar(IDocente docente, ICargoDocente cargo, EstadoOperacion estado) {
    	String destinos = null;
    	try {
    		List<IContacto> contactos = cargo.getArea()
    				.getDivision()
    				.getJefe()
    				.getPersona()
    				.getContactos();
    		List<String> mails = new ArrayList<String>();
    		for (IContacto contacto : contactos) {
                if (contacto.getTipo().getDescripcion().equals("Mail Laboral")) {
                    mails.add(contacto.getDato());
                }
            }
    		destinos = Utilidades.joinString(mails, ",");
    	} catch (Exception e) {
    		System.err.println("El Jefe de la División no tiene Mail Laboral.");
    		e.printStackTrace();
    	}

    	if (destinos == null || destinos.equals("")) {
            throw new IllegalArgumentException("El Jefe de la División no tiene Mail Laboral.");
        }

    	// Averiguar si hay que actualizar un mail o crear uno nuevo
    	boolean mailNuevo = true;
    	Map<String, String> mailActual = null;
    	for (Map<String, String> mail : this.mails) {
    		if (mail.get(NotificacionCargo2.KEY_DESTINO).equals(destinos)) {
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
    	if (mailActual == null || mailActual.isEmpty()) {
			mailActual = new HashMap<String, String>();
			asunto = armarAsunto(plantillaXML, parametros);

    	} else {
    		asunto = mailActual.get(NotificacionCargo2.KEY_ASUNTO);
    		mensaje = mailActual.get(NotificacionCargo2.KEY_MENSAJE);
    	}

    	mensaje = armarMensaje(plantillaXML, parametros);

    	// Guardar el mail para enviar
    	mailActual.put(NotificacionCargo2.KEY_ASUNTO, asunto);
    	mailActual.put(NotificacionCargo2.KEY_MENSAJE, mensaje);
    	mailActual.put(NotificacionCargo2.KEY_DESTINO, destinos);
    	if (mailNuevo) {
    		this.mails.add(mailActual);
    	}
    	System.out.println("Se agregó el mail a la lista");
    	if (this.threadMail == null) {
    		this.threadMail = new Thread(new ThreadMail(this));
    		threadMail.start();
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

    public static String armarAsunto(Document plantillaXML, Map<String, String> parametros) {
        return Plantilla.armar(
            plantillaXML
                .getElementsByTagName("asunto")
                .item(0)
                .getTextContent(),
            parametros);
    }

    public static String armarMensaje(Document plantillaXML, Map<String, String> parametros) {
        String plantilla =
            plantillaXML.getElementsByTagName("encabezado").item(0).getTextContent()
            + plantillaXML.getElementsByTagName("mensaje").item(0).getTextContent();
        return Plantilla.armar(plantilla, parametros);
    }
}
