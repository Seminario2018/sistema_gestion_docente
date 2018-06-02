package mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.auxiliares.EstadoOperacion;
import modelo.docente.ICargoDocente;
import modelo.docente.IDocente;
import modelo.persona.IContacto;
import utilidades.Utilidades;

public class NotificacionCargo2 {

	private static NotificacionCargo2 noti;

    public static final String mailDesde = "semint2018@gmail.com";
    public static final String contrasena = "semintunlu";
    
    private List<Map<String, String>> mails = new ArrayList<Map<String, String>>();
    public static final String KEY_DESTINO = "destino";
    public static final String KEY_ASUNTO = "asunto";
    public static final String KEY_MENSAJE = "mensaje";

    public static final String pie = "Este mensaje ha sido enviado automáticamente por"
    		+ " el Sistema de Gestión Docente Plumas 2\n"
    		+ "NO intente responder a este mensaje\n\n"
    		+ "Plumas 2 - Sistema de Gestión Docente @2018\n"
    		+ "Departamento de Ciencias Básicas\n"
    		+ "Universidad Nacional de Luján";
    
    private NotificacionCargo2() {
    }
    
    public static NotificacionCargo2 getInstance() {
    	if (noti == null) {
    		noti = new NotificacionCargo2();
    	}
    	return noti;
    }
    
    private Thread threadMail;
    
    /** Thread que envía mails cada {@code ThreadMail.MILISEGUNDOS}. */
    class ThreadMail implements Runnable {
    	private static final int MILISEGUNDOS = 60000; 
    	private NotificacionCargo2 noti;
    	public ThreadMail(NotificacionCargo2 noti) {
    		this.noti = noti; 
    	}
		@Override
		public void run() {
			try {
				System.out.println("Esperando para enviar mails");
				Thread.sleep(ThreadMail.MILISEGUNDOS);
				List<Map<String, String>> mails = this.noti.getMails(); 
				if (mails != null && !mails.isEmpty()) {
					IMail mailSend = new Mail();
					for (Map<String, String> mail : mails) {
						System.out.println("Enviando un mail");
						mailSend.enviarEmail(NotificacionCargo2.mailDesde,
								mail.get(NotificacionCargo2.KEY_DESTINO),
								mail.get(NotificacionCargo2.KEY_ASUNTO),
								mail.get(NotificacionCargo2.KEY_MENSAJE)
									+ NotificacionCargo2.pie,
								NotificacionCargo2.contrasena);
					}
					System.out.println("Se han enviado todos los mails");
					this.noti.setMails(new ArrayList<Map<String, String>>());
				}
			} catch (InterruptedException e) {
				
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
    	
    	if (destinos == null || destinos.equals(""))
    		throw new IllegalArgumentException("El Jefe de la División no tiene Mail Laboral.");
    	
    	// Averiguar si hay que actualizar un mail o crear uno nuevo
    	boolean mailNuevo = true;
    	Map<String, String> mailActual = null;
    	for (Map<String, String> mail : this.mails) {
    		if (mail.get(NotificacionCargo2.KEY_DESTINO).equals(destinos)) {
    			mailActual = mail;
    			mailNuevo = false;
    		}
    	}
    	
    	String asunto;
    	String mensaje;
    	
    	// Si se crea un nuevo mail, agregar asunto y saludo
    	if (mailActual == null || mailActual.isEmpty()) {
			mailActual = new HashMap<String, String>();
			asunto = "Se han actualizado Cargos en la División " 
		        		+ cargo.getArea().getDivision().getDescripcion();
    	        
	        mensaje = "Estimado/a Sr/a. " + cargo.getArea().getDivision().getJefe().getPersona().getApellido() + ",\n\n"
	        		+ "\tSe le informa que se han realizado actualizaciones en los siguientes Cargos de la División "
	        		+ cargo.getArea().getDivision().getDescripcion() + ":\n\n";
    	} else {
    		asunto = mailActual.get(NotificacionCargo2.KEY_ASUNTO);
    		mensaje = mailActual.get(NotificacionCargo2.KEY_MENSAJE);
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
    	
    	mensaje += "Al docente:\n\n"
    			+ "\tLegajo: " + docente.getLegajo()+ "\n"
	            + "\tApellido: " + docente.getPersona().getApellido() + "\n"
	            + "\tNombre: " + docente.getPersona().getNombre() + "\n"
	           
	            + "\nSe le " + operacionMensaje + " el siguiente cargo:\n\n"
	            + "\tCargo: " + cargo.getCargo().getDescripcion() + "\n"
	            + "\tÁrea: " + cargo.getArea().getDescripcion() + "\n"
	            + "\tDivisión: " + cargo.getArea().getDivision().getDescripcion() + "\n"
	            + "\tTipo de cargo: " + cargo.getTipoCargo().getDescripcion() + "\n"
	            + "\tEstado de cargo: " + cargo.getEstado().getDescripcion() + "\n\n"
	            + "--------------------------------------------------------------------------------";

    	
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
}
