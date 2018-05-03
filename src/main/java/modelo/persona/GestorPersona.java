package modelo.persona;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoDocumento;
import persistencia.ManejoDatos;

public class GestorPersona {

	public EstadoOperacion nuevoUsuario(IPersona persona) {

		try {
			ManejoDatos md = new ManejoDatos();
			String table = "EstadoPersona";
			String campos = "";
			String valores = persona.getEstado().ordinal() + ", '" + persona.getEstado().name() + "'";
			String condicion = "descripcion = '" + persona.getEstado().name() + "'";
			
			if (md.select(table, "*", condicion).isEmpty()) {
				md.insertar(table, "idEstado, Descripcion", valores);
			}
			
			ArrayList<Hashtable<String, String>> res = md.select(table, "*", condicion);
			String estado = res.get(0).get("idEstado");
			
			persona.getTipoDocumento().guardarTipoDocumento();
			
			String tipoDoc = String.valueOf(persona.getTipoDocumento().getId());
			String nroDoc = String.valueOf(persona.getNroDocumento());
			String fechaNac = Date.valueOf(persona.getFechaNacimiento()).toString();

			table = "Persona";
			campos = "`TipoDocumento`, `NroDocumento`, `Apellido`, `Nombre`, `FechaNacimiento`, `Estado`";
			valores = tipoDoc + ", '" + nroDoc + "'," + " '" + persona.getApellido() + "', "
					+ "'" + persona.getNombre() + "',"
					+ " '" + fechaNac + "', " + estado;
			
			md.insertar(table, campos, valores);
			
			
			if (persona.getContactos() != null) {
				if(!persona.getContactos().isEmpty()) {
					this.insertarContactos(persona);
				}
			}
			
			if (persona.getDomicilios() != null) {
				if (!persona.getDomicilios().isEmpty()) {
					this.insetarDomicilios(persona);
				}
			}
			
			if (persona.getTitulos() != null) {
				if (!persona.getTitulos().isEmpty()) {
					this.insertarTitulos(persona);
				}
			}
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK,
						"La persona se creo correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,
						"No se pudo crear la persona");
			}
			
			
		}catch (Exception e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR,
					"No se pudo crear la persona");

		}

	}

	private void insertarTitulos(IPersona persona) {
		ManejoDatos md = new ManejoDatos();
		for (ITitulo titulo : persona.getTitulos()) {
			int esMayor = titulo.isEsMayor() ? 1 : 0;
			String table = "titulos";
			String campos = "`idtitulos`, `TipoDocumento`, `NroDocumento`, `Nombre`, `EsMayor`";
			String valores = titulo.getId() + ", " + persona.getTipoDocumento().getId() + ", "
					+ "'" + persona.getNroDocumento() + "', '" + titulo.getNombre() + "', " + esMayor ;
			
			md.insertar(table, campos, valores);
		}
		
		
		
	}

	private void insetarDomicilios(IPersona persona) {
		ManejoDatos md = new ManejoDatos();
		for (IDomicilio domicilio : persona.getDomicilios()) {
			String table = "domicilios";
			String campos = "`iddomicilios`, `TipoDocumento`, `NroDocumento`, `Provincia`, `Ciudad`,"
					+ "`CodigoPostal`, `Domicilio`";
			String valores = domicilio.getId() + ", " + persona.getTipoDocumento().getId() + ", "
					+ "'" + persona.getNroDocumento() + "', '" + domicilio.getProvincia() + "', "
					+ "'" + domicilio.getCiudad() + "', '" + domicilio.getCodigoPostal() + "', "
					+ "'" + domicilio.getDireccion() + "'";
			
			md.insertar(table, campos, valores);
		}
		
	}

	private void insertarContactos(IPersona persona) {
		ManejoDatos md = new ManejoDatos();
		for (IContacto contacto : persona.getContactos()) {
			String table = "contacto";
			String campos = "`idcontacto`, `TipoDocumento`, `NroDocumento`, `Nombre`, `Tipo`, `Valor`";
			String valores = contacto.getId() + ", " + persona.getTipoDocumento().getId() + ", "
					+ "'" + persona.getNroDocumento() + "', '" + contacto.getNombre() + "', "
					+ "'" + contacto.getTipo() + "', '" + contacto.getValor() + "'";
			
			md.insertar(table, campos, valores);
		}
		
	}

	public EstadoOperacion eliminarPersona(IPersona persona) {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Persona";
			String condicion = " TipoDocumento = " + persona.getTipoDocumento() + ", "
					+ "NroDocumento = '" + persona.getNroDocumento() + "'";
			
			md.delete(table, condicion);	
			
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
						"La persona se eliminó correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
						"No se pudo eliminar la persona");
			}
		} catch (Exception e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
					"No se pudo eliminar la persona");
		}
	}
	
	public EstadoOperacion modificarPersona (IPersona persona) {
		
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Persona";
			String condicion = " TipoDocumento = " + persona.getTipoDocumento() + ", "
					+ "NroDocumento = '" + persona.getNroDocumento() + "'";
			
			String campos = "`TipoDocumento` = " + persona.getTipoDocumento().getId() +  ", "
					+ "`NroDocumento` = '" + persona.getNroDocumento() + "', "
					+ "`Apellido` = '" + persona.getApellido() + "', `Nombre` = '" + persona.getNombre() + "', "
					+ "`FechaNacimiento` = '" + Date.valueOf(persona.getFechaNacimiento()).toString() + "', "
					+ "`Estado` = " + persona.getEstado().ordinal();
			
			md.update(table, campos, condicion);
			
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,
						"La persona se eliminó correctamente");
			}else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
						"No se pudo eliminar la persona");
			}
		} catch (Exception e) {
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR,
					"No se pudo eliminar la persona");
		}
	}
	
	public List<IPersona> listarPersonas(IPersona persona){
		ArrayList<IPersona> personas = new ArrayList<IPersona>();
		
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Persona";
			String campos = "*";
			String condicion =  this.armarCondicion(persona);
			
			ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);
			
			for (Hashtable<String, String> reg : res) {
				Persona p = new Persona();
				p.setTipoDocumento(TipoDocumento.getTipo(new TipoDocumento(Integer.parseInt(reg.get("TipoDocumento")), null)));
				p.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
				p.setApellido(reg.get("Apellido"));
				p.setNombre("Nombre");
				String[] fnac = reg.get("FechaNacimiento").split("-");
				p.setFechaNacimiento(LocalDate.of(Integer.parseInt(fnac[0]),
						Integer.parseInt(fnac[1]), Integer.parseInt(fnac[2])));
				p.setEstado(EstadoPersona.values()[Integer.parseInt(reg.get("Estado"))]);
				this.agregarTitulos(p);
				this.agregarContactos(p);
				this.agregarDomicilios(p);
				personas.add(p);
				
				
			}
			
			
		}catch (Exception e) {
			personas = new ArrayList<IPersona>();
		}
		
		return personas;		
	}

	private void agregarDomicilios(Persona p) {
		ArrayList<Domicilio> domicilios = new ArrayList<Domicilio>();
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "domicilios";
			String campos = "*";
			String condicion = this.armarCondicion2(p);
			ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				Domicilio d = new Domicilio(Integer.parseInt(reg.get("iddomicilios")), 
						reg.get("Provincia"), reg.get("Ciudad"), reg.get("CodigoPostal"), reg.get("Domicilio"));
				domicilios.add(d);
			}
			p.setDomicilios(domicilios);
		}catch (Exception e) {
			p.setDomicilios(new ArrayList<Domicilio>());
		}
		
	}

	private String armarCondicion2(Persona p) {
		String condicion = "TRUE";
		if (p != null) {
			condicion = "";
			condicion += " `TipoDocumento` = " + p.getTipoDocumento().getId();
			condicion += " `NroDocumento` = '" + p.getNroDocumento() + "'";
			
		}
		return condicion;
	}

	private void agregarContactos(Persona p) {
		ArrayList<Contacto> contactos = new ArrayList<Contacto>();
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "contacto";
			String campos = "*";
			String condicion = this.armarCondicion2(p);
			ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				Contacto c = new Contacto(Integer.parseInt(reg.get("idContacto")), reg.get("Nombre"),
						reg.get("Tipo"), reg.get("Valor"));
				contactos.add(c);
			}
			p.setContactos(contactos);
		}catch (Exception e) {
			p.setContactos(new ArrayList<Contacto>());
		}
		
	}

	private void agregarTitulos(Persona p) {
		ArrayList<ITitulo> titulos = new ArrayList<ITitulo>();
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "titulos";
			String campos = "*";
			String condicion = this.armarCondicion2(p);
			ArrayList<Hashtable<String, String>> res = md.select(table, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				Titulo t = new Titulo(Integer.parseInt(reg.get("idtitulos")),
						reg.get("Nombre"), Integer.parseInt(reg.get("EsMayor")) == 1);
				titulos.add(t);
			}
			p.setTitulos(titulos);
		}catch (Exception e) {
			p.setTitulos(new ArrayList<ITitulo>());
		}
		
	}

	private String armarCondicion(IPersona persona) {
		String condicion = "TRUE";
		if (persona != null) {
			condicion = "";
			//usuario
			if (persona.getTipoDocumento() != null) {
				condicion += " `TipoDocumento` = " + persona.getTipoDocumento().getId();
			}
			if (persona.getNroDocumento() != 0) {
				condicion += " `NroDocumento` = '" + persona.getNroDocumento() + "'";
			}
			if (persona.getApellido() != null) {
				condicion += " `Apellido` = '" + persona.getApellido() + "'";
			}
			if (persona.getNombre() != null) {
				condicion += " `Nombre` = '" + persona.getNombre() + "'";
			}
			if (persona.getFechaNacimiento() != null) {
				condicion += " `FechaNacimiento` = '" + Date.valueOf(persona.getFechaNacimiento()).toString() + "'";
			}
			if (persona.getEstado() != null) {
				condicion += " `Estado` = " + persona.getEstado().ordinal();
			}
		}
		return condicion; 
	}
	
	

}