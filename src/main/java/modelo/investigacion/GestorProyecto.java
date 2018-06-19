
package modelo.investigacion;

import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import modelo.auxiliares.EstadoOperacion;
import modelo.auxiliares.EstadoOperacion.CodigoEstado;
import persistencia.ManejoDatos;

public class GestorProyecto {

	public GestorProyecto() {}

	public EstadoOperacion nuevoProyecto(IProyecto proyecto, IPrograma programa) {
		try {
			if (proyecto.getId() == -1) {
				proyecto.setId(GestorProyecto.getMaxID("Proyectos", "id") + 1);
			}

			ManejoDatos md = new ManejoDatos();
			String table = "Proyectos";
			String campos = "`id`, `Nombre`, `FechaPresentacion`, `Director`,  `Estado`";
			String valores = proyecto.getId() + ", '" + proyecto.getNombre() + "', "
					+ "'" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
					+ "" + proyecto.getDirector().getLegajo() + ", " + proyecto.getEstado().getId();

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
			if (proyecto.getCodirector() != null) {
				campos += ", `Codirector`";
				valores += ", " + proyecto.getCodirector().getLegajo();
			}
			if (proyecto.getFechaInicio() != null) {
				campos += ", `FechaInicio`";
				valores += ", '" + Date.valueOf(proyecto.getFechaInicio()) + "'";
			}
			if (proyecto.getFechaFin() != null) {
				campos += ", `Fecha_Fin`";
				valores += ", '" + Date.valueOf(proyecto.getFechaFin()) + "'";
			}
			if (programa != null) {
				campos += ", `Programa`";
				valores += ", " + programa.getId();
			}

			for (IIntegrante i : proyecto.getIntegrantes()) {
				this.AgregarIntegrante(proyecto, i);
			}
			for (ISubsidio s: proyecto.getSubsidios()) {
				this.agregarSubsidio(proyecto, s);
			}
			for (IProrroga p : proyecto.getProrrogas()) {
				this.agregarProrroga(proyecto, p);
			}

			md.insertar(table, campos, valores);
			return md.isEstado() ?
			    new EstadoOperacion(CodigoEstado.INSERT_OK, "El Proyecto se creó correctamente") :
		        new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		}
	}

	public EstadoOperacion modificarProyecto(IProyecto proyecto, IPrograma programa) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Proyectos";
			String campos = "";/*,  + ", "
                    + "`FechaPresentacion` = '" + Date.valueOf(proyecto.getFechaPresentacion()) + "', "
                    + "`Estado` = " + proyecto.getEstado().getId();*/

			if (proyecto.getNombre() != null && !proyecto.getNombre().equals("")) {
				campos += "`Nombre` = '" + proyecto.getNombre() + "'";
			}
			if (proyecto.getDirector() != null) {
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
			if (proyecto.getEstado() != null) {
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
			if (proyecto.getCodirector() != null) {
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
			if (programa != null) {
				if (!campos.equals("")) {
					campos += ", ";
				}
				campos += "`Programa` = " + programa.getId();
			}
			String condicion = "`Id` = " + proyecto.getId();
			md.update(tabla, campos, condicion);
			return (md.isEstado()) ?
                new EstadoOperacion(CodigoEstado.UPDATE_OK, "El proyecto se modificó correctamente") :
                new EstadoOperacion(CodigoEstado.UPDATE_ERROR, "El proyecto no se modificó");

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
			    new EstadoOperacion(CodigoEstado.DELETE_OK, "El proyecto se eliminó correctamente") :
			    new EstadoOperacion(CodigoEstado.DELETE_ERROR, "El proyecto no se eliminó");

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

	public EstadoOperacion AgregarIntegrante(IProyecto proyecto, IIntegrante i) {
		try {
			IIntegranteg integrante = (IIntegranteg) i;
			if (integrante.getId() == -1) {
				integrante.setId(GestorProyecto.getMaxID("Integrantes", "id"));
			}
			ManejoDatos md = new ManejoDatos();
			String table = "Integrantes";
			String campos = "`id`, `Proyecto`";
			String valores = integrante.getId() + ", " + proyecto.getId();

			if (integrante.getCargoDocente2() != null) {
				campos = ", CargoDocente";
				valores = ", " + integrante.getCargoDocente2();
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
				return new EstadoOperacion(CodigoEstado.INSERT_OK, "El integrante se agregó correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el integrante");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el Proyecto");
		}
	}

	public EstadoOperacion quitarIntegrante(IProyecto proyecto, IIntegrante integrante) {
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Integrante";
			md.delete(tabla, "id = " + integrante.getId() + "");

			if (md.isEstado()) {
			    proyecto.setIntegrantes(new ArrayList<IIntegrante>());
			    return new EstadoOperacion(CodigoEstado.DELETE_OK, "El integrante se eliminó correctamente");
			} else {
			    return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "El integrante no se eliminó");
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
			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
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


	public EstadoOperacion agregarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
		try {
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
				this.agregarRendicion(rendicion, proyecto, subsidio);
			}

			md.insertar(table, campos, valores);

			if (md.isEstado()) {
				proyecto.setSubsidios(new ArrayList<ISubsidio>());
				return new EstadoOperacion(CodigoEstado.INSERT_OK, "El subsidio se agregó correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar el subsidio");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear el subsidio");
		}
	}

	public EstadoOperacion quitarSubsidio(IProyecto proyecto, ISubsidio subsidio) {
	    try {
    		ManejoDatos md = new ManejoDatos();
    		md.delete("`Subsidios`", "Proyecto = " + proyecto.getId() + ", Disposicion= '" + subsidio.getDisposicion() +  "'");

    		if (md.isEstado()) {
    		    proyecto.setSubsidios(new ArrayList<ISubsidio>());
                return new EstadoOperacion(CodigoEstado.DELETE_OK, "El subsidio se quitó correctamente");
    		} else {
    		    return new EstadoOperacion(CodigoEstado.DELETE_OK, "El subsidio no se pudo quitar");
    		}

	    } catch (Exception e) {
            e.printStackTrace();
            return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "No se pudo quitar el subsidio");
        }
	}

	public List<ISubsidio> listarSubsidios(IProyecto proyecto, ISubsidio subsidio){
		List<ISubsidio> subsidios = new ArrayList<ISubsidio>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Subsidios";
			String condicion = this.armarCondicion(proyecto, subsidio);

			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Subsidio sub = new Subsidio();
				sub.setFecha(Year.parse(reg.get("Year")));
				sub.setDisposicion(reg.get("Disposicion"));
				sub.setMontoTotal(Float.parseFloat(reg.get("MontoTotal")));
				sub.setObservaciones(reg.get("Observaciones").equals("") ? null : reg.get("Observaciones"));

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

	public EstadoOperacion agregarProrroga(IProyecto proyecto, IProrroga prorroga) {
		try {
			ManejoDatos md = new ManejoDatos();
			String table = "Prorrogas";
			String campos = "`Proyecto`, `Disposicion`";
			String valores = proyecto.getId() + ", '" + prorroga.getDisposicion() + "'";

			if (prorroga.getFechaFin() != null) {
				campos += ", FechaFin";
				valores += ", '" + Date.valueOf(prorroga.getFechaFin()) + "'";
			}

			md.insertar(table, campos, valores);

			if (md.isEstado()) {
				proyecto.setProrrogas(new ArrayList<IProrroga>());
				return new EstadoOperacion(CodigoEstado.INSERT_OK, "La prorroga se agregó correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la prorroga");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo crear la prorroga");
		}
	}

	public IProrroga getIProrroga() {
		return new Prorroga();
	}


	public EstadoOperacion quitarProrroga(IProyecto proyecto, IProrroga prorroga) {
		ManejoDatos md = new ManejoDatos();
		md.delete("`Prorrogas`", "`Proyecto` = " + proyecto.getId() + ", Disposicion = '" + prorroga.getDisposicion() + "'");

		proyecto.setProrrogas(new ArrayList<IProrroga>());
		return new EstadoOperacion(CodigoEstado.DELETE_OK, "El cargo se quitó correctamente");
	}

	public List<IProrroga> listarProrrogas(IProyecto proyecto, IProrroga prorroga){
		List<IProrroga> prorrogas = new ArrayList<IProrroga>();
		try {
			ManejoDatos md = new ManejoDatos();
			String tabla = "Prorrogas";
			String condicion = this.armarCondicion(proyecto, prorroga);

			ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
			for (Hashtable<String, String> reg : res) {
				Prorroga p = new Prorroga();
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

	private static int getMaxID(String tabla, String campo) {
		try {
			ManejoDatos md = new ManejoDatos();
			String c = "MAX("+ campo + ")";
			ArrayList<Hashtable<String, String>> res = md.select(tabla, c);
			Hashtable<String, String> reg = res.get(0);
			return Integer.parseInt(reg.get(c));

		} catch (NullPointerException | NumberFormatException e) {
			return 0;
		}
	}

	public EstadoOperacion agregarRendicion(IRendicion rendicion, IProyecto proyecto, ISubsidio subsidio) {
		try {
			rendicion.setId(GestorProyecto.getMaxID("Rendiciones", "id") + 1);
			ManejoDatos md = new ManejoDatos();
			String tabla = "Rendisiones";
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
				return new EstadoOperacion(CodigoEstado.INSERT_OK, "La rendicion se agregó correctamente");
			} else {
				return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la rendicion");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new EstadoOperacion(CodigoEstado.INSERT_ERROR, "No se pudo agregar la rendicion");
		}
	}


	public IRendicion getIRendicion() {
		return new Rendicion();
	}

	/**yearSubsidio lo puse con ''*/
	public EstadoOperacion quitarRendicion(IProyecto proyecto,ISubsidio subsidio, IRendicion rendicion ) {
		try {
    	    ManejoDatos md = new ManejoDatos();
    		md.delete("`Rendiciones`", "Proyecto = " + proyecto.getId() + ", id = " + rendicion.getId() + ", YearSubsidio= '"+subsidio.getFecha()+"'");

    		if (md.isEstado()) {
    		    subsidio.setRendiciones(new ArrayList<IRendicion>());
    		    return new EstadoOperacion(CodigoEstado.DELETE_OK, "La rendición se quitó correctamente");
    		} else {
    		    return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "La rendición no se pudo quitar");
    		}

		} catch (Exception e) {
		    e.printStackTrace();
		    return new EstadoOperacion(CodigoEstado.DELETE_ERROR, "La rendición no se pudo quitar");
		}
	}




	public List<IRendicion> listarRendiciones(IRendicion rendicion){
		List<IRendicion> rendiciones = new ArrayList<IRendicion>();
		ManejoDatos md = new ManejoDatos();
		String tabla = "Rendiciones";
		String condicion = this.armarCondicion(rendicion);

		ArrayList<Hashtable<String, String>> res = md.select(tabla, "*", condicion);
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
	}

	private String armarCondicion(IRendicion rendicion) {
		String condicion = "TRUE";
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


}
