package modelo.persona;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoPersona;
import modelo.auxiliares.TipoContacto;
import modelo.auxiliares.TipoDocumento;
import persistencia.ManejoDatos;

public class GestorPersona {
	
	
	public EstadoOperacion guardarTodo(IPersona p) {
		try {
			
			ManejoDatos md = new ManejoDatos();
			
			ArrayList<String> st = new ArrayList<String>();
			
			IPersonag persona = (IPersonag) p;
			
			String tipoDoc = String.valueOf(persona.getTipoDocumento2().getId());
			String nroDoc = String.valueOf(persona.getNroDocumento());

			String table = "Personas";
			String campos =	"`TipoDocumento`, `NroDocumento`, `Apellido`, `Nombre`";
			String valores = tipoDoc + ", '" + nroDoc + "', "
					+ "'" + persona.getApellido() + "', '" + persona.getNombre() + "'";

			if (persona.getFechaNacimiento() != null) {
				String fechaNac = Date.valueOf(persona.getFechaNacimiento()).toString();
				campos += ", `FechaNacimiento`";
				valores += ", '" + fechaNac + "'";
			}

			if (persona.getEstado2() != null) {
				persona.getEstado2().guardar();
				campos += ", `Estado`";
				valores += ", " + persona.getEstado2().getId();
			}

			st.add(md.insertQuery(table, campos, valores));

			if (persona.getContactos2() != null && !persona.getContactos2().isEmpty()) {
				for (IContacto contacto : persona.getContactos2()) {
					st.add(this.insertarContactos(persona, contacto));
				}
			}

			if (persona.getDomicilios2() != null && !persona.getDomicilios2().isEmpty()) {
				for(IDomicilio domicilio : persona.getDomicilios2()) {
					st.add(this.insertarDomicilios(persona, domicilio));
				}

			}

			if (persona.getTitulos2() != null) {
				if (!persona.getTitulos2().isEmpty()) {
					for(ITitulo titulo : persona.getTitulos2()) {
						st.add(this.insertarTitulos(persona, titulo));
					}

				}
			}
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "La persona se creó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear la persona");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear la persona");

		}
	}

	private String insertarTitulos(IPersonag persona, ITitulo titulo) {
		ManejoDatos md = new ManejoDatos();
		int esMayor = titulo.isEsMayor() ? 1 : 0;

		if (titulo.getId() == -1) {
			titulo.setId(this.getMax("Titulos", "id") + 1);
		}

		String table = "Titulos";
		String campos = "`id`, `TipoDocumento`, `NroDocumento`, `Nombre`, `EsMayor`";
		String valores = titulo.getId() + ", "
				+ persona.getTipoDocumento2().getId() + ", '" + persona.getNroDocumento() + "', "
				+ "'" + titulo.getNombre() + "', " + esMayor;

		return md.insertQuery(table, campos, valores);
	}

	private String insertarDomicilios(IPersonag persona, IDomicilio domicilio) {
		ManejoDatos md = new ManejoDatos();

		if (domicilio.getId() == -1) {
			domicilio.setId(this.getMax("Domicilios", "iddomicilios") + 1);
		}

		String table = "Domicilios";
		String campos =	"`iddomicilios`, `TipoDocumento`, `NroDocumento`, `Provincia`, `Ciudad`, `CodigoPostal`, `Direccion`";
		String valores =domicilio.getId() + ", "
				+ persona.getTipoDocumento2().getId() + ", '" + persona.getNroDocumento() + "', '"
				+ domicilio.getProvincia() + "', '" + domicilio.getCiudad() + "', "
				+ "'" + domicilio.getCodigoPostal() + "', '" + domicilio.getDireccion() + "'";

		return md.insertQuery(table, campos, valores);
	}

	private String insertarContactos(IPersonag persona, IContacto contacto) {
		ManejoDatos md = new ManejoDatos();

		contacto.getTipo().guardar();

		if (contacto.getId() == -1) {
			contacto.setId(this.getMax("Contactos", "idcontacto") + 1);
		}

		String table = "Contactos";
		String campos = "`idcontacto`, `TipoDocumento`, `NroDocumento`, `Tipo`, `Valor`";
		String valores = contacto.getId() + ", "
				+ persona.getTipoDocumento2().getId() + ", '" + persona.getNroDocumento() + "', "
				+ contacto.getTipo().getId() + ", '" + contacto.getDato() + "'";

		return md.insertQuery(table, campos, valores);

	}

	public EstadoOperacion nuevaPersona(IPersona persona) {
		try {
			ManejoDatos md = new ManejoDatos();

			String tipoDoc = String.valueOf(persona.getTipoDocumento().getId());
			String nroDoc = String.valueOf(persona.getNroDocumento());

			String table = "Personas";
			String campos =	"`TipoDocumento`, `NroDocumento`, `Apellido`, `Nombre`";
			String valores = tipoDoc + ", '" + nroDoc + "', "
					+ "'" + persona.getApellido() + "', '" + persona.getNombre() + "'";

			if (persona.getFechaNacimiento() != null) {
				String fechaNac = Date.valueOf(persona.getFechaNacimiento()).toString();
				campos += ", `FechaNacimiento`";
				valores += ", '" + fechaNac + "'";
			}

			if (persona.getEstado() != null) {
				persona.getEstado().guardar();
				campos += ", `Estado`";
				valores += ", " + persona.getEstado().getId();
			}

			md.insertar(table, campos, valores);

			if (persona.getContactos() != null && !persona.getContactos().isEmpty()) {
				for (IContacto contacto : persona.getContactos()) {
					this.insertarContactos(persona, contacto);
				}
			}

			if (persona.getDomicilios() != null && !persona.getDomicilios().isEmpty()) {
				for(IDomicilio domicilio : persona.getDomicilios()) {
					this.insertarDomicilios(persona, domicilio);
				}

			}

			if (persona.getTitulos() != null) {
				if (!persona.getTitulos().isEmpty()) {
					for(ITitulo titulo : persona.getTitulos()) {
						this.insertarTitulos(persona, titulo);
					}

				}
			}

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "La persona se creó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear la persona");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear la persona");

		}

	}

	private void insertarTitulos(IPersona persona, ITitulo titulo) {
		ManejoDatos md = new ManejoDatos();
		int esMayor = titulo.isEsMayor() ? 1 : 0;

		if (titulo.getId() == -1) {
			titulo.setId(this.getMax("Titulos", "id") + 1);
		}

		String table = "Titulos";
		String campos = "`id`, `TipoDocumento`, `NroDocumento`, `Nombre`, `EsMayor`";
		String valores = titulo.getId() + ", "
				+ persona.getTipoDocumento().getId() + ", '" + persona.getNroDocumento() + "', "
				+ "'" + titulo.getNombre() + "', " + esMayor;

		md.insertar(table, campos, valores);


	}

	private void insertarDomicilios(IPersona persona, IDomicilio domicilio) {
		ManejoDatos md = new ManejoDatos();

		if (domicilio.getId() == -1) {
			domicilio.setId(this.getMax("Domicilios", "iddomicilios") + 1);
		}

		String table = "Domicilios";
		String campos =	"`iddomicilios`, `TipoDocumento`, `NroDocumento`, `Provincia`, `Ciudad`, `CodigoPostal`, `Direccion`";
		String valores =domicilio.getId() + ", "
				+ persona.getTipoDocumento().getId() + ", '" + persona.getNroDocumento() + "', '"
				+ domicilio.getProvincia() + "', '" + domicilio.getCiudad() + "', "
				+ "'" + domicilio.getCodigoPostal() + "', '" + domicilio.getDireccion() + "'";

		md.insertar(table, campos, valores);

	}

	public void insertarContactos(IPersona persona, IContacto contacto) {
		ManejoDatos md = new ManejoDatos();

		contacto.getTipo().guardar();

		if (contacto.getId() == -1) {
			contacto.setId(this.getMax("Contactos", "idcontacto") + 1);
		}

		String table = "Contactos";
		String campos = "`idcontacto`, `TipoDocumento`, `NroDocumento`, `Tipo`, `Valor`";
		String valores = contacto.getId() + ", "
				+ persona.getTipoDocumento().getId() + ", '" + persona.getNroDocumento() + "', "
				+ contacto.getTipo().getId() + ", '" + contacto.getDato() + "'";

		md.insertar(table, campos, valores);

	}

	public EstadoOperacion eliminarPersona(IPersona persona) {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Personas";
			String condicion = " TipoDocumento = " + persona.getTipoDocumento().getId() + " and NroDocumento = '" + persona.getNroDocumento() + "'";

			md.delete(table, condicion);

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "La persona se eliminó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar la persona");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar la persona");
		}
	}

	public EstadoOperacion modificarPersona(IPersona persona) {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Personas";
			String condicion = " TipoDocumento = " + persona.getTipoDocumento().getId() + " AND NroDocumento = '" + persona.getNroDocumento() + "'";

			String campos =	"";

			if (persona.getApellido() != null && !persona.getApellido().equals("")) {
				campos += "`Apellido` = '" + persona.getApellido() + "'";
			}

			if (persona.getNombre() != null && !persona.getNombre().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Nombre` = '" + persona.getNombre() + "'";
			}

			if (persona.getFechaNacimiento() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`FechaNacimiento` = '" + Date.valueOf(persona.getFechaNacimiento()).toString() + "'";
			}
			if (persona.getEstado() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				persona.getEstado().guardar();
				campos += "`Estado` = " + persona.getEstado().getId();
			}
			md.update(table, campos, condicion);

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "La persona se modificó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar la persona");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar la persona");
		}
	}

	public List<IPersona> listarPersonas(IPersona persona) {
		List<IPersona> personas = new ArrayList<IPersona>();

		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Personas";
			String campos = "*";
			String condicion = this.armarCondicion(persona);

			List<Hashtable<String, String>> res = md.select(table, campos, condicion);


			for (Hashtable<String, String> reg : res) {
				Persona p = new Persona();
				p.setTipoDocumento(TipoDocumento.getTipo(new TipoDocumento(Integer.parseInt(reg.get("TipoDocumento")), null)));
				p.setNroDocumento(Integer.parseInt(reg.get("NroDocumento")));
				p.setApellido(reg.get("Apellido"));
				p.setNombre(reg.get("Nombre"));


				if (!reg.get("FechaNacimiento").equals("")) {
					String[] fnac = reg.get("FechaNacimiento").split("-");
					p.setFechaNacimiento(LocalDate.of(Integer.parseInt(fnac[0]), Integer.parseInt(fnac[1]),
							Integer.parseInt(fnac[2])));
				}
				if (!reg.get("Estado").equals("")) {
					p.setEstado(this.getEstado(reg.get("Estado")));
				}

				personas.add(p);
			}

		} catch (Exception e) {
		    e.printStackTrace();
			personas = new ArrayList<IPersona>();
		}

		return personas;
	}

	private EstadoPersona getEstado(String estado) {
		EstadoPersona e = new EstadoPersona();
		e.setDescripcion(estado);
		return EstadoPersona.getEstado(e);
	}

	public List<IDomicilio> getDomicilios(IPersona persona) {
		List<IDomicilio> domicilios = new ArrayList<IDomicilio>();
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Domicilios";
			String campos = "*";
			String condicion = this.armarCondicion2(persona);
			List<Hashtable<String, String>> res = md.select(table, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				IDomicilio d = new Domicilio(Integer.parseInt(reg.get("iddomicilios")), reg.get("Provincia"), reg.get("Ciudad"), reg.get("CodigoPostal"), reg.get("Direccion"));
				domicilios.add(d);
			}
			return domicilios;

		} catch (Exception e) {
		    e.printStackTrace();
			return new ArrayList<IDomicilio>();
		}

	}

	private String armarCondicion2(IPersona persona) {
		String condicion = "TRUE";
		if (persona != null) {
			condicion = "";
			if (persona.getTipoDocumento() != null)
				condicion += " `TipoDocumento` = " 
						+ persona.getTipoDocumento().getId() + " and";
			condicion += " `NroDocumento` = '" + persona.getNroDocumento() + "'";

		}
		return condicion;
	}

	public List<IContacto> getContactos(IPersona persona) {
		List<IContacto> contactos = new ArrayList<IContacto>();
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Contactos";
			String campos = "*";
			String condicion = this.armarCondicion2(persona);
			List<Hashtable<String, String>> res = md.select(table, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				TipoContacto tc = new TipoContacto();
				tc.setId(Integer.parseInt(reg.get("Tipo")));;
				tc = TipoContacto.getTipoContacto(tc);
				IContacto c =
						new Contacto(Integer.parseInt(reg.get("idcontacto")), tc, reg.get("Valor"));
				contactos.add(c);
			}
			return contactos;
		} catch (Exception e) {
		    e.printStackTrace();
			return new ArrayList<IContacto>();
		}
	}

	public List<ITitulo> getTitulos(IPersona persona) {
		List<ITitulo> titulos = new ArrayList<ITitulo>();
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Titulos";
			String campos = "*";
			String condicion = this.armarCondicion2(persona);
			List<Hashtable<String, String>> res = md.select(table, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				ITitulo t = new Titulo(Integer.parseInt(reg.get("id")), reg.get("Nombre"), Integer.parseInt(reg.get("EsMayor")) == 1);
				titulos.add(t);
			}
			return titulos;
		} catch (Exception e) {
		    e.printStackTrace();
			return new ArrayList<ITitulo>();
		}
	}

	private String armarCondicion(IPersona p) {
		IPersonag persona = (IPersonag) p;

		String condicion = "TRUE";
		if (persona != null) {
			condicion = "";

			List<String> condiciones = new ArrayList<String>();

			if (persona.getTipoDocumento2() != null) {
				condiciones.add("`TipoDocumento` = "
						+ persona.getTipoDocumento2().getId());
			}
			if (persona.getNroDocumento() != 0) {
				condiciones.add("`NroDocumento` = '" + persona.getNroDocumento()
				+ "'");
			}
			if (persona.getApellido() != null) {
				condiciones.add("`Apellido` = '" + persona.getApellido() + "'");
			}
			if (persona.getNombre() != null) {
				condiciones.add("`Nombre` = '" + persona.getNombre() + "'");
			}
			if (persona.getFechaNacimiento() != null) {
				condiciones.add("`FechaNacimiento` = '"
						+ Date.valueOf(persona.getFechaNacimiento()).toString()
						+ "'");
			}
			if (persona.getEstado2() != null) {
				condiciones.add("`Estado` = " + persona.getEstado2().getId());
			}

			if (!condiciones.isEmpty()) {
				condicion = String.join(" AND ", condiciones);
			}
		}
		return condicion;
	}

	private int getMax(String tabla,String campo) {
		String campos = "MAX(" + campo + ")";

		ManejoDatos md = new ManejoDatos();

		List<Hashtable<String, String>> res = md.select(tabla, campos);

		String maximoActual = res.get(0).get(campos);
		if (maximoActual.equals("")) {
		    return 0;
		} else {
		    return Integer.parseInt(maximoActual);
		}
	}


	public static boolean existePersona(IPersona persona) {
		String tabla = "Personas";
		if (persona == null || persona.getTipoDocumento() == null || persona.getNroDocumento() == -1) {
			return false;
		}
		String condicion = "TipoDocumento = " + persona.getTipoDocumento().getId() + " and "
				+ "NroDocumento = '" + persona.getNroDocumento() + "'";
		try {
			ManejoDatos md = new ManejoDatos();
			List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			return !(res.isEmpty());

		} catch (Exception e) {
		    e.printStackTrace();
			return false;
		}
	}


	// Plantillas vacías ======================================================
	public IPersona getIPersona() {
        return new Persona();
    }

	public IContacto getIContacto() {
        return new Contacto();
    }

	public IDomicilio getIDomicilio() {
        return new Domicilio();
    }

	public ITitulo getITitulo() {
        return new Titulo();
    }

	// Contactos ==============================================================
	public EstadoOperacion agregarContacto(IPersona persona, IContacto contacto) {
		try {
			ManejoDatos md = new ManejoDatos();

			String tipoDoc = String.valueOf(persona.getTipoDocumento().getId());
			String nroDoc = String.valueOf(persona.getNroDocumento());

			if (contacto.getId() == -1) {
				contacto.setId(this.getMax("Contactos", "idcontacto") + 1);
			}

			String table = "Contactos";
			String campos = "`idcontacto`, `TipoDocumento`, `NroDocumento`, `Tipo`, `Valor`";
			String valores =contacto.getId() + ", "
					+ tipoDoc + ", '"
					+ nroDoc + "', "
					+ contacto.getTipo().getId() + ", '"
					+ contacto.getDato() + "'";

			md.insertar(table, campos, valores);

			if (md.isEstado()) {
			    persona.setContactos(new ArrayList<IContacto>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El contacto se creó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el contacto");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el contacto");
		}
    }

    public EstadoOperacion modificarContacto(IPersona persona, IContacto contacto) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String table = "Contactos";
			String condicion =	" `TipoDocumento` = " + persona.getTipoDocumento().getId() + " AND `NroDocumento` = '" + persona.getNroDocumento() + "' AND `idcontacto`="+contacto.getId();

			String campos =	"`Tipo` = " + contacto.getTipo().getId() + ", "
							+ "`Valor` = '" + contacto.getDato() + "'";

			md.update(table, campos, condicion);

			if (md.isEstado()) {
			    persona.setContactos(new ArrayList<IContacto>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El contacto se modificó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el contacto");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el contacto");
		}
    }

    public EstadoOperacion quitarContacto(IPersona persona, IContacto contacto) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String table = "Contactos";
			String condicion = " TipoDocumento = " + persona.getTipoDocumento().getId() + " AND NroDocumento = '" + persona.getNroDocumento()+ "' AND Valor = '" + contacto.getDato() + "'";

			md.delete(table, condicion);

			if (md.isEstado()) {
			    persona.setContactos(new ArrayList<IContacto>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El Cantacto se eliminó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el contacto");
			}
		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el contacto");
		}
    }

    // Domicilios =============================================================
    public EstadoOperacion agregarDomicilio(IPersona persona, IDomicilio domicilio) {
    	try {
			ManejoDatos md = new ManejoDatos();

			String tipoDoc = String.valueOf(persona.getTipoDocumento().getId());
			String nroDoc = String.valueOf(persona.getNroDocumento());

			if (domicilio.getId() == -1) {
				domicilio.setId(this.getMax("Domicilios", "iddomicilios") + 1);
			}

			String table = "Domicilios";
			String campos =	"`iddomicilios`, `TipoDocumento`, `NroDocumento`, `Provincia`, `Ciudad`, `CodigoPostal`, `Direccion`";
			String valores =domicilio.getId() + ", "
					+ tipoDoc + ", '"
					+ nroDoc + "', '"
					+ domicilio.getProvincia() + "', '"
					+ domicilio.getCiudad() + "', '"
					+ domicilio.getCodigoPostal() + "', '"
					+ domicilio.getDireccion() + "'";

			md.insertar(table, campos, valores);

			if (md.isEstado()) {
			    persona.setDomicilios(new ArrayList<IDomicilio>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El Domicilio se creo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el domicilio");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el domicilio");

		}
    }

    public EstadoOperacion modificarDomicilio(IPersona persona, IDomicilio domicilio) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String table = "Domicilios";
			String condicion =	" `TipoDocumento` = " + persona.getTipoDocumento().getId() + " AND `NroDocumento` = '" + persona.getNroDocumento() + "' AND `iddomicilios`="+domicilio.getId();

			String campos =	"";

			if (domicilio.getProvincia() != null && !domicilio.getProvincia().equals("")) {
				campos += "`Provincia` = '" + domicilio.getProvincia() + "'";
			}

			if (domicilio.getCiudad() != null && !domicilio.getCiudad().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Ciudad` = '" + domicilio.getCiudad() + "'";
			}

			if (domicilio.getCodigoPostal() != null && !domicilio.getCodigoPostal().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`CodigoPostal` = '" + domicilio.getCodigoPostal() + "'";
			}

			if (domicilio.getDireccion() != null && !domicilio.getDireccion().equals("")) {
			    if (!campos.equals("")) {
                    campos += ", ";
                }
                campos += "`Direccion` = '" + domicilio.getDireccion() + "'";
			}

			md.update(table, campos, condicion);

			if (md.isEstado()) {
			    persona.setDomicilios(new ArrayList<IDomicilio>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El domicilio se modificó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el domicilio");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el domicilio");
		}
    }

    public EstadoOperacion quitarDomicilio(IPersona persona, IDomicilio domicilio) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String table = "Domicilios";
			String condicion = " TipoDocumento = " + persona.getTipoDocumento().getId() + " AND NroDocumento = '" + persona.getNroDocumento()+ "' AND idDomicilios = " + domicilio.getId();

			md.delete(table, condicion);

			if (md.isEstado()) {
			    persona.setDomicilios(new ArrayList<IDomicilio>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El Domicilio se eliminó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el Domicilio");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el Domicilio");
		}
    }

    // Títulos ================================================================
    public EstadoOperacion agregarTitulo(IPersona persona, ITitulo titulo) {
    	try {
			ManejoDatos md = new ManejoDatos();

			String tipoDoc = String.valueOf(persona.getTipoDocumento().getId());
			String nroDoc = String.valueOf(persona.getNroDocumento());

			int esMayor = titulo.isEsMayor() ? 1 : 0;

			if (titulo.getId() == -1) {
				titulo.setId(this.getMax("Titulos", "id") + 1);
			}

			String table = "Titulos";
			String campos = "`id`, `TipoDocumento`, `NroDocumento`, `Nombre`, `EsMayor`";
			String valores = titulo.getId() + ", "
					+ tipoDoc+ ", '"
					+ nroDoc + "', "+ "'"
					+ titulo.getNombre() + "', "
					+ esMayor;

			md.insertar(table, campos, valores);

			if (md.isEstado()) {
			    persona.setTitulos(new ArrayList<ITitulo>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El título se creo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el título");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo crear el título");

		}
    }

    public EstadoOperacion modificarTitulo(IPersona persona, ITitulo titulo) {
    	try {
			int esMayor = titulo.isEsMayor() ? 1 : 0;
			ManejoDatos md = new ManejoDatos();
			String table = "Titulos";
			String condicion =	" `TipoDocumento` = " + persona.getTipoDocumento().getId() + " AND `NroDocumento` = '" + persona.getNroDocumento() + "' AND `id`="+titulo.getId();

			String campos =	"`Nombre` = '" + titulo.getNombre() + "', "
							+ "`EsMayor` = " + esMayor;

			md.update(table, campos, condicion);

			if (md.isEstado()) {
			    persona.setTitulos(new ArrayList<ITitulo>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El título se modifico correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el título");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo modificar el título");
		}
    }

    public EstadoOperacion quitarTitulo(IPersona persona, ITitulo titulo) {
    	try {
			ManejoDatos md = new ManejoDatos();
			String table = "Titulos";
			String condicion = "TipoDocumento = " + persona.getTipoDocumento().getId() + " AND NroDocumento = '" + persona.getNroDocumento()+ "' AND id = " + titulo.getId();

			md.delete(table, condicion);

			if (md.isEstado()) {
			    persona.setTitulos(new ArrayList<ITitulo>());
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK, "El título se eliminó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo título el Domicilio");
			}

		} catch (Exception e) {
		    e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo título el Domicilio");
		}
    }

}
