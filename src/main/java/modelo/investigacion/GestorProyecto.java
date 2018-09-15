
package modelo.investigacion;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import modelo.docente.IDocente;
import persistencia.ManejoDatos;

public class GestorProyecto {

	public GestorProyecto() {};

	public EstadoOperacion guardarTodo(IProyecto p) {
		try {

			IProyectog proyecto = (IProyectog) p;

			ArrayList<String> st = new ArrayList<String>();

			if (proyecto.getId() == -1) {
				proyecto.setId(GestorProyecto.getMaxID("Proyectos", "id") + 1);
			}

			ManejoDatos md = new ManejoDatos();
			String table = "Proyectos";
			String campos = "`id`, `Nombre`, `FechaPresentacion`, `Director`,  `Estado`";
			String valores = proyecto.getId() + ", '" + proyecto.getNombre() + "', "
					+ "'" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
					+ "" + proyecto.getDirector2().getLegajo() + ", " + proyecto.getEstado2().getId();

			if (proyecto.getResumen() != null && !proyecto.getResumen().equals("")) {
				campos += ", `Resumen`";
				valores += ", '" + proyecto.getResumen() + "'";
			}
			if (proyecto.getFechaAprobacion() != null) {
				campos += ", `FechaAprobacion`";
				valores += ", '" + Date.valueOf(proyecto.getFechaAprobacion()) + "'";
			}
			if (proyecto.getDescripcion() != null && !proyecto.getDescripcion().equals("")) {
				campos += ", `Descripcion`";
				valores += ", '" + proyecto.getDescripcion() + "'";
			}
			if (((IProyectog) proyecto).getCodirector2() != null) {
				campos += ", `Codirector`";
				valores += ", " + proyecto.getCodirector2().getLegajo();
			}
			if (proyecto.getFechaInicio() != null) {
				campos += ", `FechaInicio`";
				valores += ", '" + Date.valueOf(proyecto.getFechaInicio()) + "'";
			}
			if (proyecto.getFechaFin() != null) {
				campos += ", `Fecha_Fin`";
				valores += ", '" + Date.valueOf(proyecto.getFechaFin()) + "'";
			}

			for (IIntegrante i : proyecto.getIntegrantes2()) {
				st.add(this.agregarIntegrante2(proyecto, i));
			}
			for (ISubsidio s: proyecto.getSubsidios2()) {
				st.add(this.agregarSubsidio(proyecto, s));
				ISubsidiog subsidio = (ISubsidiog) s;

				for (IRendicion rendicion : subsidio.getRendiciones2()) {
					st.add(this.agregarRendicion(rendicion, proyecto, subsidio));
				}
			}
			for (IProrroga pro : proyecto.getProrrogas2()) {
				st.add(this.agregarProrroga(proyecto, pro));
			}

			st.add(md.insertQuery(table, campos, valores));

			md.ejecutarQuerys(st);

			return md.isEstado() ?
					new EstadoOperacion(CodigoEstado.INSERT_OK, "El Proyecto se creÃ³ correctamente") :
						new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		}
	}

	public EstadoOperacion nuevoProyecto(IProyecto p) {
		try {

			IProyectog proyecto = (IProyectog) p;


			if (proyecto.getId() == -1) {
				proyecto.setId(GestorProyecto.getMaxID("Proyectos", "id") + 1);
			}

			ManejoDatos md = new ManejoDatos();
			String table = "Proyectos";
			String campos = "`id`, `Nombre`, `FechaPresentacion`, `Director`,  `Estado`";
			String valores = proyecto.getId() + ", '" + proyecto.getNombre() + "', "
					+ "'" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
					+ "" + proyecto.getDirector2().getLegajo() + ", " + proyecto.getEstado2().getId();

			if (proyecto.getResumen() != null && !proyecto.getResumen().equals("")) {
				campos += ", `Resumen`";
				valores += ", '" + proyecto.getResumen() + "'";
			}
			if (proyecto.getFechaAprobacion() != null) {
				campos += ", `FechaAprobacion`";
				valores += ", '" + Date.valueOf(proyecto.getFechaAprobacion()) + "'";
			}
			if (proyecto.getDescripcion() != null && !proyecto.getDescripcion().equals("")) {
				campos += ", `Descripcion`";
				valores += ", '" + proyecto.getDescripcion() + "'";
			}
			if (((IProyectog) proyecto).getCodirector2() != null) {
				campos += ", `Codirector`";
				valores += ", " + proyecto.getCodirector2().getLegajo();
			}
			if (proyecto.getFechaInicio() != null) {
				campos += ", `FechaInicio`";
				valores += ", '" + Date.valueOf(proyecto.getFechaInicio()) + "'";
			}
			if (proyecto.getFechaFin() != null) {
				campos += ", `Fecha_Fin`";
				valores += ", '" + Date.valueOf(proyecto.getFechaFin()) + "'";
			}

			for (IIntegrante i : proyecto.getIntegrantes2()) {
				this.agregarIntegrante(p, i);
			}
			for (ISubsidio s: proyecto.getSubsidios2()) {
				this.agregarSubsidio(p, s);
			}
			for (IProrroga pro : proyecto.getProrrogas2()) {
				this.agregarProrroga(p, pro);
			}


			md.insertar(table, campos, valores);

			return md.isEstado() ?
					new EstadoOperacion(CodigoEstado.INSERT_OK, "El Proyecto se creÃ³ correctamente") :
						new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		}
	}

	public EstadoOperacion agregarProrroga(IProyecto p, IProrroga pro) {
		IProyectog proyecto = (IProyectog) p;
		ManejoDatos md = new ManejoDatos();
		String table = "Prorrogas";
		String campos = "`Proyecto`, `Disposicion`";
		String valores = proyecto.getId() + ", '" + pro.getDisposicion() + "'";

		if (pro.getFechaFin() != null) {
			campos += ", FechaFin";
			valores += ", '" + Date.valueOf(pro.getFechaFin()) + "'";
		}

		md.insertar(table, campos, valores);

		return md.isEstado() ?
				new EstadoOperacion(CodigoEstado.INSERT_OK, "La prorroga se creo correctamente") :
					new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear la prorroga");
	}

	public  EstadoOperacion agregarSubsidio(IProyecto p, ISubsidio subsidio) {
		IProyectog proyecto = (IProyectog) p;

		ManejoDatos md = new ManejoDatos();
		String table = "Subsidios";
		String campos = "`Proyecto`, `Year`, `Disposicion`, `MontoTotal`";
		String valores = proyecto.getId() + ", '" + subsidio.getFecha() + "', '" + subsidio.getDisposicion() + "', "
				+ subsidio.getMontoTotal();

		if (subsidio.getObservaciones() != null && !subsidio.getObservaciones().equals("")) {
			campos += ", Observaciones";
			valores += ", '" + subsidio.getObservaciones() + "'";
		}

		for (IRendicion rendicion : subsidio.getRendiciones()) {
			this.agregarRendicion(rendicion, p, subsidio);
		}

		md.insertar(table, campos, valores);

		if (md.isEstado()) {
			proyecto.setIntegrantes(new ArrayList<IIntegrante>());
			return new EstadoOperacion(CodigoEstado.INSERT_OK, "El subsidio se agrego correctamente");
		} else {
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo agregar el subsidio");
		}

	}

	public EstadoOperacion agregarRendicion(IRendicion rendicion, IProyecto p, ISubsidio subsidio) {
		IProyectog proyecto = (IProyectog) p;

		try {
			rendicion.setId(GestorProyecto.getMaxID("Rendiciones", "id") + 1);
			ManejoDatos md = new ManejoDatos();
			String tabla = "rendiciones";
			String campos = "`id`, `Proyecto`, `YearSubsidio`, `Fecha`, `Monto`";
			String valores = rendicion.getId() + ", " + proyecto.getId() + ", " + subsidio.getFecha()  + ", "
					+ "'" + Date.valueOf(rendicion.getFecha()) + "', " + rendicion.getMonto();

			if (rendicion.getObservaciones() != null && !rendicion.getObservaciones().equals("")) {
				campos += ", Observaciones";
				valores += ", '" + rendicion.getObservaciones() + "'";
			}

			md.insertar(tabla, campos, valores);

			if (md.isEstado()) {
				subsidio.setRendiciones(new ArrayList<IRendicion>());
				return new EstadoOperacion(CodigoEstado.INSERT_OK, "La rendicion se agregÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la rendicion");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la rendicion");
		}
	}

	private String agregarIntegrante2(IProyectog proyecto, IIntegrante i) {
		IIntegranteg integrante = (IIntegranteg) i;
		if (integrante.getId() == -1) {
			integrante.setId(GestorProyecto.getMaxID("Integrantes", "id"));
		}
		ManejoDatos md = new ManejoDatos();
		String table = "Integrantes";
		String campos = "`id`, `Proyecto`";
		String valores = integrante.getId() + ", " + proyecto.getId();

		if (integrante.getCargoDocente2() != null) {
			campos += ", CargoDocente";
			valores += ", " + integrante.getCargoDocente2().getId();
		}
		if (integrante.getApellido() != null && !integrante.getApellido().equals("")) {
			campos += ", Apellido";
			valores += ", '" + integrante.getApellido() + "'";
		}
		if (integrante.getNombre() != null && !integrante.getApellido().equals("")) {
			campos += ", Nombre";
			valores += ", '" + integrante.getNombre() + "'";
		}
		if (integrante.getCargo() != null && !integrante.getCargo().equals("")) {
			campos += ", Cargo";
			valores += ", '" + integrante.getCargo() + "'";
		}
		if (integrante.getInstitucion() != null && !integrante.getInstitucion().equals("")) {
			campos += ", Institucion";
			valores += ", '" + integrante.getInstitucion() + "'";
		}
		if (integrante.getHorasSemanales() != -1) {
			campos += ", HorasSemanales";
			valores += ", " + integrante.getHorasSemanales();
		}

		return md.insertQuery(table, campos, valores);

	}

	public EstadoOperacion modificarProyecto(IProyecto proyecto) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Proyectos";
			String campos = "";/*,  + ", "
                    + "`FechaPresentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
                    + "`Estado` = " + proyecto.getEstado().getId();*/

			if (proyecto.getNombre() != null && !proyecto.getNombre().equals("")) {
				campos += "`Nombre` = '" + proyecto.getNombre() + "'";
			}
			if (((IProyectog) proyecto).getDirector2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Director`= " + proyecto.getDirector().getLegajo();
			}
			if (proyecto.getFechaPresentacion() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`FechaPresentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()) + "'";
			}
			if (((IProyectog) proyecto).getEstado2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Estado` = " + proyecto.getEstado().getId();
			}
			if (proyecto.getResumen() != null && !proyecto.getResumen().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Resumen`= '" + proyecto.getResumen() + "'";
			}
			if (proyecto.getFechaAprobacion() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`FechaAprobacion` = '" + Date.valueOf(proyecto.getFechaAprobacion()) + "'";
			}
			if (proyecto.getDescripcion() != null && !proyecto.getDescripcion().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Descripcion` = '" + proyecto.getDescripcion() + "'";
			}
			if (((IProyectog) proyecto).getCodirector2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Codirector` = " + proyecto.getCodirector().getLegajo();
			}
			if (proyecto.getFechaInicio() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`FechaInicio` = '" + Date.valueOf(proyecto.getFechaInicio()) + "'";
			}
			if (proyecto.getFechaFin() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Fecha_Fin` = '" + Date.valueOf(proyecto.getFechaFin()) + "'";
			}

			String condicion = "`Id` = " + proyecto.getId();
			md.update(tabla, campos, condicion);
			return (md.isEstado()) ?
					new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se modificÃ³ correctamente") :
						new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se modificÃ³");

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo modificar el proyecto");
		}
	}

	public EstadoOperacion eliminarProyecto(IProyecto proyecto) {
		try {
			ManejoDatos md = new ManejoDatos();
			String condicion = "Proyecto = " + proyecto.getId();
			md.delete("`Integrantes`", condicion);
			md.delete("`Prorrogas`", condicion);
			md.delete("`Subsidios`", condicion);
			md.delete("`Proyectos`", "id = " + proyecto.getId());
			return (md.isEstado()) ?
					new EstadoOperacion(CodigoEstado.DELETE_OK, "El proyecto se eliminÃ³ correctamente") :
						new EstadoOperacion(CodigoEstado.DELETE_ERROR, "El proyecto no se eliminÃ³");

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el Proyecto");
		}
	}

	public List<IProyecto> listarProyecto(IProyecto proyecto, IPrograma programa) {
		List<IProyecto> proyectos = new ArrayList<IProyecto>();
		String condicion = this.armarCondicion(proyecto, programa);
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Proyectos";
			List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				IProyecto p = new Proyecto();
				p.setId(Integer.parseInt(reg.get("id")));
				p.setNombre(reg.get("Nombre"));
				p.setFechaPresentacion(Date.valueOf(reg.get("FechaPresentacion")).toLocalDate());

				if (!reg.get("Resumen").equals("")) {
					p.setResumen(reg.get("Resumen"));
				}

				if (!reg.get("FechaAprobacion").equals("")) {
					p.setFechaAprobacion(Date.valueOf(reg.get("FechaAprobacion")).toLocalDate());
				}
				if (!reg.get("Descripcion").equals("")) {
					p.setDescripcion(reg.get("Descripcion"));
				}

				if (!reg.get("FechaInicio").equals("")) {
					p.setFechaInicio(Date.valueOf(reg.get("FechaInicio")).toLocalDate());
				}
				if (!reg.get("Fecha_Fin").equals("")) {
					p.setFechaFin(Date.valueOf(reg.get("Fecha_Fin")).toLocalDate());
				}
				proyectos.add(p);
			}
			return proyectos;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IProyecto>();
		}
	}

	public EstadoOperacion agregarIntegrante(IProyecto proyecto, IIntegrante i) {

		IIntegranteg integrante = (IIntegranteg) i;
		if (integrante.getId() == -1) {
			integrante.setId(GestorProyecto.getMaxID("Integrantes", "id"));
		}
		ManejoDatos md = new ManejoDatos();
		String table = "Integrantes";
		String campos = "`id`, `Proyecto`";
		String valores = integrante.getId() + ", " + proyecto.getId();

		if (integrante.getCargoDocente2() != null) {
			campos += ", CargoDocente";
			valores += ", " + integrante.getCargoDocente2().getId();
		}
		if (integrante.getApellido() != null && !integrante.getApellido().equals("")) {
			campos += ", Apellido";
			valores += ", '" + integrante.getApellido() + "'";
		}
		if (integrante.getNombre() != null && !integrante.getApellido().equals("")) {
			campos += ", Nombre";
			valores += ", '" + integrante.getNombre() + "'";
		}
		if (integrante.getCargo() != null && !integrante.getCargo().equals("")) {
			campos += ", Cargo";
			valores += ", '" + integrante.getCargo() + "'";
		}
		if (integrante.getInstitucion() != null && !integrante.getInstitucion().equals("")) {
			campos += ", Institucion";
			valores += ", '" + integrante.getInstitucion() + "'";
		}
		if (integrante.getHorasSemanales() != -1) {
			campos += ", HorasSemanales";
			valores += ", " + integrante.getHorasSemanales();
		}

		md.insertar(table, campos, valores);
		if (md.isEstado()) {
			proyecto.setIntegrantes(new ArrayList<IIntegrante>());
			return new EstadoOperacion(CodigoEstado.INSERT_OK, "El integrante se agrego correctamente");
		} else {
			return new EstadoOperacion(CodigoEstado.INSERT_OK, "No se pudo agregar el integrante");
		}

	}

	public EstadoOperacion modificarIntegrante(IProyecto proyecto, IIntegrante i) {
		try {
			IIntegranteg integrante = (IIntegranteg) i;
			ManejoDatos md =  new ManejoDatos();
			String tabla = "Integrantes";
			String condicion = "id=" + integrante.getId();

			String campos = "";
			if (integrante.getCargoDocente2() != null) {
				campos += "CargoDocente = " + integrante.getCargoDocente2().getId();
			}
			if (integrante.getApellido() != null && !integrante.getApellido().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "Apellido = '" + integrante.getApellido() + "'";
			}
			if (integrante.getNombre() != null && !integrante.getNombre().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos +=  "Nombre = '" + integrante.getNombre() + "'";
			}
			if (integrante.getCargo() != null && !integrante.getCargo().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos +=  "Cargo = '" + integrante.getCargo() + "'";
			}
			if (integrante.getInstitucion() != null && !integrante.getInstitucion().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos +=  "Institucion = '" + integrante.getInstitucion() + "'";
			}
			if (integrante.getHorasSemanales() != -1) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos +=  "HorasSemanales = " + integrante.getHorasSemanales();
			}

			md.update(tabla, campos, condicion);

			if (md.isEstado()) {
				proyecto.setIntegrantes(new ArrayList<IIntegrante>());
				return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El integrante se actualizÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar el integrante");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar el integrante");
		}
	}


	public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
		try {
			ManejoDatos md = new ManejoDatos();

			String tabla = "Integrantes";
			String condicion = "id=" + integrante.getId();
			md.delete(tabla, condicion);

			if (md.isEstado()) {
				proyecto.setIntegrantes(new ArrayList<IIntegrante>());
				return new EstadoOperacion(CodigoEstado.DELETE_OK, "El integrante se eliminÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "El integrante no se eliminÃ³");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo eliminar el integrante");
		}
	}

	public List<IIntegrante> listarIntegrantes (IProyecto proyecto, IIntegrante integrante){
		List<IIntegrante> integrantes = new ArrayList<IIntegrante>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Integrantes";
			String condicion = this.armarCondicion(proyecto, integrante);
			List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Integrante i = new Integrante();
				i.setId(Integer.parseInt(reg.get("id")));

				if (!reg.get("Apellido").equals("")) {
					i.setApellido(reg.get("Apellido"));
				}
				if (!reg.get("Nombre").equals("")) {
					i.setNombre(reg.get("Nombre"));
				}
				if (!reg.get("Cargo").equals("")) {
					i.setCargo(reg.get("Cargo"));
				}
				if (!reg.get("Institucion").equals("")) {
					i.setInstitucion(reg.get("Institucion"));
				}
				if (!reg.get("HorasSemanales").equals("")) {
					i.setHorasSemanales(Integer.parseInt(reg.get("HorasSemanales")));
				}
				integrantes.add(i);
			}
			return integrantes;

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ArrayList<IIntegrante>();
		}
	}

	public IIntegrante getIIntegrante() {
		return new Integrante();
	}

	private String armarCondicion(IProyecto proyecto, IIntegrante integrante) {
		String condicion = "TRUE";
		if (integrante != null) {
			if (integrante.getId() != -1) {
				condicion += " AND id = " + integrante.getId();
			}
			if (integrante.getApellido() != null && !integrante.getApellido().equals("")) {
				condicion += " AND Apellido = '" + integrante.getApellido() + "'";
			}
			if (integrante.getNombre() != null && !integrante.getNombre().equals("")) {
				condicion += " AND Nombre = '" + integrante.getNombre() + "'";
			}
			if (integrante.getCargo() != null && !integrante.getCargo().equals("")) {
				condicion += " AND Cargo = '" + integrante.getCargo() + "'";
			}
			if (integrante.getInstitucion() != null && !integrante.getInstitucion().equals("")) {
				condicion += " AND Institucion = '" + integrante.getInstitucion() + "'";
			}
			if (integrante.getHorasSemanales() != -1) {
				condicion += " AND HorasSemanales = " + integrante.getHorasSemanales();
			}
		}
		if (proyecto != null) {
			condicion += " AND Proyecto = " + proyecto.getId();
		}

		return condicion;
	}

	public ISubsidio getISubsidio() {
		return new Subsidio();
	}

	public String agregarSubsidio(IProyectog proyecto, ISubsidio s) {
		ManejoDatos md = new ManejoDatos();

		ISubsidiog subsidio = (ISubsidiog) s;

		String table = "Subsidios";
		String campos = "`Proyecto`, `Year`, `Disposicion`, `MontoTotal`";
		String valores = proyecto.getId() + ", '" + subsidio.getFecha() + "', '" + subsidio.getDisposicion() + "', "
				+ subsidio.getMontoTotal();

		if (subsidio.getObservaciones() != null && !subsidio.getObservaciones().equals("")) {
			campos += ", Observaciones";
			valores += ", '" + subsidio.getObservaciones() + "'";
		}



		return md.insertQuery(table, campos, valores);

	}

	public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "`Subsidios`";
			String condicion =
					"Proyecto=" + proyecto.getId() + " AND " +
							"Year = '" + subsidio.getFecha().toString() + "'";

			// Borrar las rendiciones del subsidio:
			for (IRendicion rendicion : subsidio.getRendiciones()) {
				EstadoOperacion resultado = quitarRendicion(proyecto, subsidio, rendicion);
				switch (resultado.getEstado()) {
				case DELETE_ERROR:
					return new EstadoOperacion(CodigoEstado.DELETE_ERROR,
							"No se pudo quitar una rendiciÃ³n del subsidio [id: " + rendicion.getId() + "]");
				case DELETE_OK:
					// Seguir iterando
					break;
				default:
					throw new RuntimeException("Estado de modificaciÃ³n no esperado: "
							+ resultado.getEstado().toString() + ": " + resultado.getMensaje());
				}
			}

			md.delete(tabla, condicion);

			if (md.isEstado()) {
				proyecto.setSubsidios(new ArrayList<ISubsidio>());
				return new EstadoOperacion(CodigoEstado.DELETE_OK, "El subsidio se quitÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "El subsidio no se pudo quitar");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo quitar el subsidio");
		}
	}

	public EstadoOperacion modificarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
		try {
			ManejoDatos md =  new ManejoDatos();
			String tabla = "Subsidios";
			String condicion = "Proyecto = " + proyecto.getId() + " AND Year = " + subsidio.getFecha().toString();
			String campos = "";

			if (subsidio.getDisposicion() != null && !subsidio.getDisposicion().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "Disposicion = '" + subsidio.getDisposicion() + "'";
			}
			if (subsidio.getMontoTotal() != -1) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "MontoTotal = " + subsidio.getMontoTotal();
			}
			if (subsidio.getObservaciones() != null && !subsidio.getObservaciones().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "Observaciones = '" + subsidio.getObservaciones() + "'";
			}

			md.update(tabla, campos, condicion);

			if (md.isEstado()) {
				proyecto.setSubsidios(new ArrayList<ISubsidio>());
				return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El subsidio se actualizÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar el subsidio");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar el subsidio");
		}
	}

	public List<ISubsidio> listarSubsidios(IProyecto proyecto, ISubsidio subsidio){
		List<ISubsidio> subsidios = new ArrayList<ISubsidio>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Subsidios";
			String condicion = this.armarCondicion(proyecto, subsidio);

			List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				ISubsidio sub = new Subsidio();
				sub.setFecha(Year.parse(reg.get("Year")));
				sub.setDisposicion(reg.get("Disposicion"));
				sub.setMontoTotal(Float.parseFloat(reg.get("MontoTotal")));
				sub.setObservaciones(reg.get("Observaciones").equals("") ? null : reg.get("Observaciones"));
				subsidios.add(sub);
			}
			return subsidios;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ISubsidio>();
		}
	}

	private String armarCondicion(IProyecto proyecto, ISubsidio subsidio) {
		String condicion = "TRUE";
		if (subsidio != null) {
			if (subsidio.getFecha() != null) {
				condicion += " AND Year = " + subsidio.getFecha();
			}
			if (subsidio.getDisposicion() != null && !subsidio.getDisposicion().equals("")) {
				condicion += " AND Disposicion = '" + subsidio.getDisposicion() + "'";
			}
			if (subsidio.getMontoTotal() != -1) {
				condicion += " AND MontoTotal = " + subsidio.getMontoTotal();
			}
			if (subsidio.getObservaciones() != null && !subsidio.getObservaciones().equals("")) {
				condicion += " AND Observaciones = '" + subsidio.getObservaciones() + "'";
			}
		}
		if (proyecto != null) {
			condicion += " AND Proyecto = " + proyecto.getId();
		}

		return condicion;
	}

	public String agregarProrroga(IProyectog proyecto, IProrroga pro) {

		ManejoDatos md = new ManejoDatos();
		String table = "Prorrogas";
		String campos = "`Proyecto`, `Disposicion`";
		String valores = proyecto.getId() + ", '" + pro.getDisposicion() + "'";

		if (pro.getFechaFin() != null) {
			campos += ", FechaFin";
			valores += ", '" + Date.valueOf(pro.getFechaFin()) + "'";
		}

		return md.insertQuery(table, campos, valores);


	}

	public IProrroga getIProrroga() {
		return new Prorroga();
	}

	public EstadoOperacion modificarProrroga(IProyecto proyecto, IProrroga prorroga) {
		try {
			ManejoDatos md =  new ManejoDatos();
			String tabla = "prorrogas";
			String condicion = "Proyecto = " + proyecto.getId() + " AND Disposicion = '" + prorroga.getDisposicion() + "'";
			String campos = "FechaFin = '" + Date.valueOf(prorroga.getFechaFin()) + "'";

			md.update(tabla, campos, condicion);

			if (md.isEstado()) {
				proyecto.setProrrogas(new ArrayList<IProrroga>());
				return new EstadoOperacion(CodigoEstado.UPDATE_OK, "La prÃ³rroga se actualizÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar la prÃ³rroga");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar la prÃ³rroga");
		}
	}

	public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
		try {
			ManejoDatos md = new ManejoDatos();

			String tabla = "`Prorrogas`";
			String condicion =
					"`Proyecto`=" + proyecto.getId() + " AND " +
							"`Disposicion` = '" + prorroga.getDisposicion() + "'";

			md.delete(tabla, condicion);

			if (md.isEstado()) {
				proyecto.setProrrogas(new ArrayList<IProrroga>());
				return new EstadoOperacion(CodigoEstado.DELETE_OK, "La prÃ³rroga se quitÃ³ correctamente");

			} else {
				return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo quitar la prÃ³rroga");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo quitar la prÃ³rroga");
		}
	}

	public List<IProrroga> listarProrrogas(IProyecto proyecto, IProrroga prorroga){
		List<IProrroga> prorrogas = new ArrayList<IProrroga>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "prorrogas";
			String condicion = this.armarCondicion(proyecto, prorroga);

			List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				IProrroga p = new Prorroga();
				p.setDisposicion(reg.get("Disposicion"));

				if (!reg.get("FechaFin").equals("")) {
					p.setFechaFin(Date.valueOf(reg.get("FechaFin")).toLocalDate());
				}

				prorrogas.add(p);
			}
			return prorrogas;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IProrroga>();
		}
	}

	private String armarCondicion(IProyecto proyecto, IProrroga prorroga) {
		String condicion = "TRUE";
		if (prorroga != null) {
			if (prorroga.getDisposicion() != null && !prorroga.getDisposicion().equals("")) {
				condicion += " AND Disposicion = '" + prorroga.getDisposicion() + "'";
			}
			if (prorroga.getFechaFin() != null) {
				condicion += " AND FechaFin = '" + Date.valueOf(prorroga.getFechaFin()) + "'";
			}
		}
		if (proyecto != null) {
			condicion += " AND Proyecto = " + proyecto.getId();
		}

		return condicion;
	}

	private String armarCondicion(IProyecto p, IPrograma programa) {

		IProyectog proyecto = (IProyectog) p;

		String condicion = "TRUE";
		if (proyecto != null) {
			if (proyecto.getId() != 0) {
				condicion += " AND `Id` = " + proyecto.getId();
			}
			if (proyecto.getNombre() != "") {
				condicion += " AND `Nombre` = '" + proyecto.getNombre() + "'";
			}
			if (proyecto.getDescripcion() != "") {
				condicion += " AND `Descripcion` = '" + proyecto.getDescripcion() + "'";
			}
			if (proyecto.getDirector2() != null) {
				condicion += " AND `Director` = " + proyecto.getDirector2().getLegajo();
			}
			if (proyecto.getFechaPresentacion() != null) {
				condicion += " AND `Fecha_Presentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()).toString() + "'";
			}
			if (proyecto.getFechaAprobacion() != null) {
				condicion += " AND `Fecha_Aprovacion` = " + Date.valueOf(proyecto.getFechaAprobacion()).toString() + "'";
			}
			if (proyecto.getFechaInicio() != null) {
				condicion += " AND `Fecha_Inicio` = '" + Date.valueOf(proyecto.getFechaInicio()).toString() + "'";
			}
			if (proyecto.getFechaFin() != null) {
				condicion += " AND `Fecha_Fin` = " + Date.valueOf(proyecto.getFechaFin()).toString() + "'";
			}
			if (proyecto.getEstado2() != null) {
				condicion += " AND `Estado`= " + proyecto.getEstado2();
			}
		}
		if (programa != null) {
			if (programa.getId() != -1) {
				condicion += " AND `Programa` = " + programa.getId();
			}
		}

		return condicion;
	}

	static int getMaxID(String tabla, String campo) {
		try {
			ManejoDatos md = new ManejoDatos();
			String c = "MAX("+ campo + ")";
			List<Hashtable<String, String>> res = md.select(tabla, c);
			Hashtable<String, String> reg = res.get(0);

			String salida = reg.get(c);
			if (salida.equals("")) {
				return 0;
			} else {
				return Integer.parseInt(salida);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String agregarRendicion(IRendicion rendicion, IProyectog proyecto, ISubsidiog subsidio) {
			rendicion.setId(GestorProyecto.getMaxID("Rendiciones", "id") + 1);
			ManejoDatos md = new ManejoDatos();
			String tabla = "rendiciones";
			String campos = "`id`, `Proyecto`, `YearSubsidio`, `Fecha`, `Monto`";
			String valores = rendicion.getId() + ", " + proyecto.getId() + ", " + subsidio.getFecha()  + ", "
					+ "'" + Date.valueOf(rendicion.getFecha()) + "', " + rendicion.getMonto();

			if (rendicion.getObservaciones() != null && !rendicion.getObservaciones().equals("")) {
				campos += ", Observaciones";
				valores += ", '" + rendicion.getObservaciones() + "'";
			}

			return md.insertQuery(tabla, campos, valores);

	}

	public IRendicion getIRendicion() {
		return new Rendicion();
	}

	public EstadoOperacion quitarRendicion(IProyecto proyecto,ISubsidio subsidio, IRendicion rendicion ) {
		try {
			ManejoDatos md = new ManejoDatos();

			String tabla = "`Rendiciones`";
			// yearSubsidio lo puse con ''
			String condicion =
					"Proyecto=" + proyecto.getId() + " AND " +
							"id=" + rendicion.getId() + " AND " +
							"YearSubsidio = '" + subsidio.getFecha() + "'";
			md.delete(tabla, condicion);

			if (md.isEstado()) {
				subsidio.setRendiciones(new ArrayList<IRendicion>());
				return new EstadoOperacion(CodigoEstado.DELETE_OK, "La rendiciÃ³n se quitÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "La rendiciÃ³n no se pudo quitar");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "La rendiciÃ³n no se pudo quitar");
		}
	}

	public EstadoOperacion modificarRendicion(IProyecto proyecto, ISubsidio subsidio, IRendicion rendicion) {
		try {
			ManejoDatos md =  new ManejoDatos();
			String tabla = "Rendiciones";
			String condicion =
					"Proyecto = " + proyecto.getId() + " AND " +
							"YearSubsidio = " + subsidio.getFecha().toString() + " AND " +
							"id = " + rendicion.getId();
			String campos = "";

			if (rendicion.getFecha() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "Fecha = '" + Date.valueOf(rendicion.getFecha()) + "'";
			}

			if (!campos.equals("")) {
				campos += ", ";
			}
			campos += "Monto = " + rendicion.getMonto();

			if (rendicion.getObservaciones() != null && !rendicion.getObservaciones().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "Observaciones = '" + rendicion.getObservaciones() + "'";
			}

			md.update(tabla, campos, condicion);

			if (md.isEstado()) {
				proyecto.setSubsidios(new ArrayList<ISubsidio>());
				return new EstadoOperacion(CodigoEstado.UPDATE_OK, "La rendiciÃ³n se actualizÃ³ correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar la rendiciÃ³n");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "No se pudo actualizar la rendiciÃ³n");
		}
	}

	public List<IRendicion> listarRendiciones(IProyecto proyecto, ISubsidio subsidio, IRendicion rendicion){
		List<IRendicion> rendiciones = new ArrayList<IRendicion>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Rendiciones";
			String condicion = this.armarCondicion(proyecto, subsidio, rendicion);

			List<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Rendicion r = new Rendicion();
				r.setId(Integer.parseInt(reg.get("id")));

				r.setFecha(Date.valueOf(reg.get("Fecha")).toLocalDate());
				r.setMonto(Float.parseFloat(reg.get("Monto")));
				if (!reg.get("Observaciones").equals("")) {
					r.setObservaciones(reg.get("Observaciones"));
				}
				rendiciones.add(r);
			}

			return rendiciones;

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<IRendicion>();
		}
	}

	private String armarCondicion(IProyecto proyecto, ISubsidio subsidio, IRendicion rendicion) {
		String condicion = "TRUE";

		if (proyecto != null && proyecto.getId() != -1) {
			condicion += " AND Proyecto = " + proyecto.getId();
		}

		if (subsidio != null && subsidio.getFecha() != null) {
			condicion += " AND YearSubsidio = '" + subsidio.getFecha() + "'";
		}

		if (rendicion != null) {
			if (rendicion.getId() != -1) {
				condicion += " AND id = " + rendicion.getId();
			}
			if (rendicion.getFecha() != null) {
				condicion += " AND Fecha = '" + Date.valueOf(rendicion.getFecha()) + "'";
			}
			if (rendicion.getMonto() != -1) {
				condicion += " AND Monto =" + rendicion.getMonto();
			}
			if (rendicion.getObservaciones() != null && !rendicion.getObservaciones().equals("")) {
				condicion += " AND Observaciones = '" + rendicion.getObservaciones() + "'";
			}
		}
		return condicion;
	}

	public IProyecto getIProyecto() {
		return new Proyecto();
	}

    /**
     * Busca en la base de datos todos los proyectos en que el docente
     * seleccionado es un integrante y devuelve filas con el ID de proyecto
     * (P.ID), el nombre del proyecto (P.Nombre), la descripción del cargo
     * (C.Descripcion) y la descripción del área del cargo (A.Descripcion).
     * @param docente El docente a buscar
     * @return Tabla con las filas de la búsqueda
     */
	public List<Hashtable<String,String>> integranteDe(IDocente docente) {
        if (docente != null && docente.getLegajo() != -1) {
            try {
                ManejoDatos md = new ManejoDatos();
                String campos =
                    "P.ID, " +
                    "P.Nombre, " +
                    "C.Descripcion, " +
                    "A.Descripcion";
                String tabla = "Proyectos P " +
                    "Join Integrantes I     On I.Proyecto = P.id " +
                    "Join CargosDocentes CD On CD.Codigo = I.CargoDocente " +
                    "Join Areas A           On A.Codigo = CD.Area " +
                    "Join Cargos C          On C.Codigo = CD.Cargo";
                String condicion = "CD.Legajo = " + docente.getLegajo();

                return md.select(tabla, campos, condicion);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
