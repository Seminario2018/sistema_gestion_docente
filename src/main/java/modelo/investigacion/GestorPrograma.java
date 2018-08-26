package modelo.investigacion;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorPrograma {


	public EstadoOperacion guardarTodos(IPrograma p) {
		try {

			IProgramag programa = (IProgramag) p;

			ArrayList<String> st = new ArrayList<String>();
			
			if (programa.getId() == -1) {
				programa.setId(this.getMaxID() + 1);
			}



			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "`id`, `Nombre`, `Director`, `Estado`";
			String valores = programa.getId() + ", '" + programa.getNombre() + "', "
					+ programa.getDirector2().getLegajo() + ", " + programa.getEstado2().getId();

			if (((IProgramag) programa).getCodirector2() != null) {
				campos += ", `Codirector`";
				valores += ", " + programa.getCodirector2().getLegajo();
			}

			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				campos += ", `Disposicion`";
				valores += ", '" + programa.getDisposicion() + "'";
			}

			if (programa.getFechaInicio() != null) {
				campos += ", `Desde`";
				valores += ", '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}

			if (programa.getFechaFin() != null) {
				campos += ", `Hasta`";
				valores += ", '" + Date.valueOf(programa.getFechaFin()) + "'";
			}
			
			st.add(md.insertQuery(table, campos, valores));
			
			for (IProyecto proy : programa.getProyectos2()) {
				IProyectog proyecto = (IProyectog) proy;
				

				if (proyecto.getId() == -1) {
					proyecto.setId(GestorProyecto.getMaxID("Proyectos", "id") + 1);
				}

				table = "Proyectos";
				campos = "`id`, `Nombre`, `FechaPresentacion`, `Director`,  `Estado`";
				valores = proyecto.getId() + ", '" + proyecto.getNombre() + "', "
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
					st.add(this.agregarIntegrante(proyecto, i));
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
				
			}
			
			md.ejecutarQuerys(st);
			
			
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El programa se guardo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo guardar el programa");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo guardar el programa");
		}
	}

	private String agregarRendicion(IRendicion rendicion, IProyectog proyecto, ISubsidiog subsidio) {
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

	private String agregarProrroga(IProyectog proyecto, IProrroga pro) {
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

	private String agregarSubsidio(IProyectog proyecto, ISubsidio s) {
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

	private String agregarIntegrante(IProyectog proyecto, IIntegrante i) {
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

	public EstadoOperacion nuevoPrograma (IPrograma p) {
		try {
			IProgramag programa = (IProgramag) p;

			if (programa.getId() == -1) {
				programa.setId(this.getMaxID() + 1);
			}



			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "`id`, `Nombre`, `Director`, `Estado`";
			String valores = programa.getId() + ", '" + programa.getNombre() + "', "
					+ programa.getDirector2().getLegajo() + ", " + programa.getEstado2().getId();

			if (((IProgramag) programa).getCodirector2() != null) {
				campos += ", `Codirector`";
				valores += ", " + programa.getCodirector2().getLegajo();
			}

			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				campos += ", `Disposicion`";
				valores += ", '" + programa.getDisposicion() + "'";
			}

			if (programa.getFechaInicio() != null) {
				campos += ", `Desde`";
				valores += ", '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}

			if (programa.getFechaFin() != null) {
				campos += ", `Hasta`";
				valores += ", '" + Date.valueOf(programa.getFechaFin()) + "'";
			}

			md.insertar(table, campos, valores);
			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_OK, "El programa se guardo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo guardar el programa");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.INSERT_ERROR, "No se pudo guardar el programa");
		}
	}

	public EstadoOperacion modificarPrograma(IPrograma programa) {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "ProgramasInvestigacion";
			String campos = "";
			String condicion = "id = " + programa.getId();

			if (programa.getNombre() != null && !programa.getNombre().equals("")) {
				campos += "`Nombre` = '"+ programa.getNombre() +"'";
			}

			if (((IProgramag) programa).getDirector2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Director` = " + programa.getDirector().getLegajo();
			}
			if (((IProgramag) programa).getEstado2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Estado` = " + programa.getEstado().getId();
			}

			if (((IProgramag) programa).getCodirector2() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Codirector` = " + programa.getCodirector().getLegajo();
			}

			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Disposicion` = '" + programa.getDisposicion() + "'";
			}

			if (programa.getFechaInicio() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Desde` = '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}

			if (programa.getFechaFin() != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Hasta` = '" + Date.valueOf(programa.getFechaFin()) + "'";
			}

			md.update(table, campos, condicion);

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_OK, "El programa se guardo correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo guardar el programa");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.UPDATE_ERROR, "No se pudo guardar el programa");
		}
	}

	public EstadoOperacion eliminarPrograma(IPrograma programa) {
		try {
			ManejoDatos md = new ManejoDatos();
			// Dejar huérfanos a proyectos asociados:
			md.update(
					"proyectos",
					"Programa = NULL",
					"Programa = " + programa.getId());

			// Eliminar programa:
			md.delete("programasinvestigacion", "id = " + programa.getId());

			if (md.isEstado()) {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_OK,"El programa se eliminó correctamente");
			} else {
				return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el programa");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(EstadoOperacion.CodigoEstado.DELETE_ERROR, "No se pudo eliminar el programa");
		}
	}

	public List<IPrograma> listarProgramas(IPrograma programa) {
		try {
			List<IPrograma> programas = new ArrayList<IPrograma>();

			ManejoDatos md = new ManejoDatos();
			String tabla = "ProgramasInvestigacion";
			String campos = "*";
			String condicion = this.armarCondicion(programa);

			List<Hashtable<String, String>> res = md.select(tabla, campos, condicion);
			for (Hashtable<String, String> reg : res) {
				IPrograma p = new Programa();
				p.setId(Integer.parseInt(reg.get("id")));
				p.setNombre(reg.get("Nombre"));

				if (!reg.get("Disposicion").equals("")) {
					p.setDisposicion(reg.get("Disposicion"));
				}
				if (!reg.get("Desde").equals("")) {
					p.setFechaInicio(Date.valueOf(reg.get("Desde")).toLocalDate());
				}
				if (!reg.get("Hasta").equals("")) {
					p.setFechaFin(Date.valueOf(reg.get("Hasta")).toLocalDate());
				}

				programas.add(p);
			}
			return programas;

		} catch (NumberFormatException e) {
			return new ArrayList<IPrograma>();
		}
	}

	public int getCantidadProyectos(IPrograma programa) {
		try {
			ManejoDatos md = new ManejoDatos();

			String campo = "count(*)";
			List<Hashtable<String, String>> res = md.select(
					"`proyectos`",
					campo,
					"Programa=" + programa.getId());

			return Integer.parseInt(res.get(0).get(campo));

		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al acceder a la base de datos.");
		}
	}

	private String armarCondicion(IPrograma p) {
		IProgramag programa = (IProgramag) p;

		String condicion = "TRUE";
		if (programa != null) {
			if (programa.getId() != -1) {
				condicion += " AND " + "id = " + programa.getId();
			}
			if (programa.getNombre() != null && !programa.getNombre().equals("")) {
				condicion += " AND " + "Nombre = '" + programa.getNombre() + "'";
			}
			if (programa.getDirector2() != null) {
				condicion += " AND " + "Director = " + programa.getDirector2().getLegajo();
			}
			if (programa.getCodirector2() != null) {
				condicion += " AND " + "Codirector = " + programa.getCodirector2().getLegajo();
			}
			if (programa.getDisposicion() != null && !programa.getDisposicion().equals("")) {
				condicion += " AND " + "Disposicion = '" + programa.getDisposicion() + "'";
			}
			if (programa.getFechaInicio() != null) {
				condicion += " AND " + "Desde = '" + Date.valueOf(programa.getFechaInicio()) + "'";
			}
			if (programa.getFechaFin() != null) {
				condicion += " AND " + "Hasta = '" + Date.valueOf(programa.getFechaFin()) + "'";
			}
			if (programa.getEstado2() != null) {
				condicion += " AND " + "Estado = " + programa.getEstado2().getId();
			}
		}
		return condicion;
	}

	private int getMaxID() {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "ProgramasInvestigacion";
			String campos = "MAX(id)";
			List<Hashtable<String, String>> res = md.select(tabla, campos);
			return Integer.parseInt(res.get(0).get(campos));

		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public IPrograma getIPrograma() {
		return new Programa();
	}

	public EstadoOperacion agregarProyecto(IPrograma programa, IProyecto proyecto) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "`proyectos`";
			String condicion = "id = " + proyecto.getId();
			String valores = "Programa = " + programa.getId();

			md.update(tabla, valores, condicion);
			if (md.isEstado()) {
				programa.setProyectos(new ArrayList<IProyecto>());
				return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se agregó al programa correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo agregar al programa");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo agregar al programa");
		}
	}

	public EstadoOperacion quitarProyecto(IPrograma programa, IProyecto proyecto) {
		try {
			ManejoDatos md = new ManejoDatos();
			md.update(
					"`proyectos`",
					"Programa = NULL",
					"id = " + proyecto.getId());

			if (md.isEstado()) {
				programa.setProyectos(new ArrayList<IProyecto>());
				return new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se quitó del programa correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo quitar del programa");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se pudo quitar del programa");
		}
	}

}
